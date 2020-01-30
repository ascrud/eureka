# Registry

## Class Diagram

## LookupService

```java
public interface LookupService<T> {
Application getApplication(String appName);
Applications getApplications();
List<InstanceInfo> getInstancesById(String id);
InstanceInfo getNextServerFromEureka(String virtualHostname, boolean secure);
}
```

## LeaseManager

```java
public interface LeaseManager<T> { 
void register(T r, int leaseDuration, boolean isReplication);
boolean cancel(String appName, String id, boolean isReplication);
boolean renew(String appName, String id, boolean isReplication);
 void evict();
}
```

## InstanceRegistry

```java
public interface InstanceRegistry extends LeaseManager<InstanceInfo>, LookupService<String> {

    void openForTraffic(ApplicationInfoManager applicationInfoManager, int count);

    void shutdown();

    @Deprecated
    void storeOverriddenStatusIfRequired(String id, InstanceStatus overriddenStatus);

    void storeOverriddenStatusIfRequired(String appName, String id, InstanceStatus overriddenStatus);

    boolean statusUpdate(String appName, String id, InstanceStatus newStatus,
                         String lastDirtyTimestamp, boolean isReplication);

    boolean deleteStatusOverride(String appName, String id, InstanceStatus newStatus,
                                 String lastDirtyTimestamp, boolean isReplication);

    Map<String, InstanceStatus> overriddenInstanceStatusesSnapshot();

    Applications getApplicationsFromLocalRegionOnly();

    List<Application> getSortedApplications();
   
    Application getApplication(String appName, boolean includeRemoteRegion);
 
    InstanceInfo getInstanceByAppAndId(String appName, String id);
 
    InstanceInfo getInstanceByAppAndId(String appName, String id, boolean includeRemoteRegions);

    void clearRegistry();

    void initializedResponseCache();

    ResponseCache getResponseCache();

    long getNumOfRenewsInLastMin();

    int getNumOfRenewsPerMinThreshold();

    int isBelowRenewThresold();

    List<Pair<Long, String>> getLastNRegisteredInstances();

    List<Pair<Long, String>> getLastNCanceledInstances();

    /**
     * Checks whether lease expiration is enabled.
     *
     * @return true if enabled
     */
    boolean isLeaseExpirationEnabled();

    boolean isSelfPreservationModeEnabled();
}
```

## RemoteRegionRegistry

```java
public class RemoteRegionRegistry implements LookupService<String> {

    private final ApacheHttpClient4 discoveryApacheClient;
    private final EurekaJerseyClient discoveryJerseyClient;
    private final com.netflix.servo.monitor.Timer fetchRegistryTimer;
    private final URL remoteRegionURL;

    private final ScheduledExecutorService scheduler;

    // monotonically increasing generation counter to ensure stale threads do not reset registry to an older version
    private final AtomicLong fetchRegistryGeneration = new AtomicLong(0);

    private final Lock fetchRegistryUpdateLock = new ReentrantLock();

    private final AtomicReference<Applications> applications = new AtomicReference<Applications>(new Applications());
    private final AtomicReference<Applications> applicationsDelta = new AtomicReference<Applications>(new Applications());
    private final EurekaServerConfig serverConfig;
    private volatile boolean readyForServingData;
    private final EurekaHttpClient eurekaHttpClient;

    @Inject
    public RemoteRegionRegistry(EurekaServerConfig serverConfig,
                                EurekaClientConfig clientConfig,
                                ServerCodecs serverCodecs,
                                String regionName,
                                URL remoteRegionURL) {}
}
```

## AbstractInstanceRegistry

```java
public abstract class AbstractInstanceRegistry implements InstanceRegistry {

    private static final String[] EMPTY_STR_ARRAY = new String[0];

    private final ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>> registry
            = new ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>>();

    protected Map<String, RemoteRegionRegistry> regionNameVSRemoteRegistry =
     new HashMap<String, RemoteRegionRegistry>();

    protected final ConcurrentMap<String, InstanceStatus> overriddenInstanceStatusMap = CacheBuilder
            .newBuilder().initialCapacity(500)
            .expireAfterAccess(1, TimeUnit.HOURS)
            .<String, InstanceStatus>build().asMap();

    // CircularQueues here for debugging/statistics purposes only

    private final CircularQueue<Pair<Long, String>> recentRegisteredQueue;
    private final CircularQueue<Pair<Long, String>> recentCanceledQueue;
    private ConcurrentLinkedQueue<RecentlyChangedItem> recentlyChangedQueue = new ConcurrentLinkedQueue<RecentlyChangedItem>();

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock read = readWriteLock.readLock();
    private final Lock write = readWriteLock.writeLock();
    protected final Object lock = new Object();

    private Timer deltaRetentionTimer = new Timer("Eureka-DeltaRetentionTimer", true);
    private Timer evictionTimer = new Timer("Eureka-EvictionTimer", true);
    private final MeasuredRate renewsLastMin;

    private final AtomicReference<EvictionTask> evictionTaskRef = new AtomicReference<EvictionTask>();

    protected String[] allKnownRemoteRegions = EMPTY_STR_ARRAY;
    protected volatile int numberOfRenewsPerMinThreshold;
    protected volatile int expectedNumberOfClientsSendingRenews;

    protected final EurekaServerConfig serverConfig;
    protected final EurekaClientConfig clientConfig;
    protected final ServerCodecs serverCodecs;
    protected volatile ResponseCache responseCache;

    /**
     * Create a new, empty instance registry.
     */
    protected AbstractInstanceRegistry(EurekaServerConfig serverConfig,
                                       EurekaClientConfig clientConfig,
                                       ServerCodecs serverCodecs) {
        this.serverConfig = serverConfig;
        this.clientConfig = clientConfig;
        this.serverCodecs = serverCodecs;
        this.recentCanceledQueue = new CircularQueue<Pair<Long, String>>(1000);
        this.recentRegisteredQueue = new CircularQueue<Pair<Long, String>>(1000);

        this.renewsLastMin = new MeasuredRate(1000 * 60 * 1);

        this.deltaRetentionTimer.schedule(getDeltaRetentionTask(),
                serverConfig.getDeltaRetentionTimerIntervalInMs(),
                serverConfig.getDeltaRetentionTimerIntervalInMs());
    }
}
```

## PeerAwareInstanceRegistry

```java
public interface PeerAwareInstanceRegistry extends InstanceRegistry {
   
    void init(PeerEurekaNodes peerEurekaNodes) throws Exception;
   
    int syncUp();
   
    boolean shouldAllowAccess(boolean remoteRegionRequired);

    void register(InstanceInfo info, boolean isReplication);

    void statusUpdate(final String asgName, final ASGResource.ASGStatus newStatus, final boolean isReplication);
}
```

## PeerAwareInstanceRegistryImpl

```java
@Singleton
public class PeerAwareInstanceRegistryImpl extends AbstractInstanceRegistry implements PeerAwareInstanceRegistry {

    private static final String US_EAST_1 = "us-east-1";

    private static final int PRIME_PEER_NODES_RETRY_MS = 30000;

    private long startupTime = 0;
    private boolean peerInstancesTransferEmptyOnStartup = true;

    public enum Action {
        Heartbeat,
        Register,
        Cancel,
        StatusUpdate,
        DeleteStatusOverride;

        private com.netflix.servo.monitor.Timer timer = Monitors.newTimer(this.name());

        public com.netflix.servo.monitor.Timer getTimer() {
            return this.timer;
        }
    }

    private static final Comparator<Application> APP_COMPARATOR = new Comparator<Application>() {
        @Override
        public int compare(Application l, Application r) {
            return l.getName().compareTo(r.getName());
        }
    };

    private final MeasuredRate numberOfReplicationsLastMin;

    protected final EurekaClient eurekaClient;

    protected volatile PeerEurekaNodes peerEurekaNodes;

    private final InstanceStatusOverrideRule instanceStatusOverrideRule;

    private Timer timer = new Timer(
            "ReplicaAwareInstanceRegistry - RenewalThresholdUpdater", true);

    @Inject
    public PeerAwareInstanceRegistryImpl(
            EurekaServerConfig serverConfig,
            EurekaClientConfig clientConfig,
            ServerCodecs serverCodecs,
            EurekaClient eurekaClient) {
        super(serverConfig, clientConfig, serverCodecs);
        this.eurekaClient = eurekaClient;
        this.numberOfReplicationsLastMin = new MeasuredRate(1000 * 60 * 1);
        // We first check if the instance is STARTING or DOWN, then we check explicit overrides,
        // then we check the status of a potentially existing lease.
        this.instanceStatusOverrideRule = new FirstMatchWinsCompositeRule(new DownOrStartingRule(),
                new OverrideExistsRule(overriddenInstanceStatusMap), new LeaseExistsRule());
    }
}
```

## AwsInstanceRegistry

```java
@Singleton
public class AwsInstanceRegistry extends PeerAwareInstanceRegistryImpl {

    private AwsAsgUtil awsAsgUtil;

    private InstanceStatusOverrideRule instanceStatusOverrideRule;

    @Inject
    public AwsInstanceRegistry(EurekaServerConfig serverConfig,
                               EurekaClientConfig clientConfig,
                               ServerCodecs serverCodecs,
                               EurekaClient eurekaClient) {
        super(serverConfig, clientConfig, serverCodecs, eurekaClient);
    }
}
```

## EurekaClient

```java
@ImplementedBy(DiscoveryClient.class)
public interface EurekaClient extends LookupService {
  
    Applications getApplicationsForARegion(@Nullable String region);
  
    Applications getApplications(String serviceUrl);
  
    List<InstanceInfo> getInstancesByVipAddress(String vipAddress, boolean secure);
 
    List<InstanceInfo> getInstancesByVipAddress(String vipAddress, boolean secure, @Nullable String region);
  
    List<InstanceInfo> getInstancesByVipAddressAndAppName(String vipAddress, String appName, boolean secure);
 
    Set<String> getAllKnownRegions();
   
    InstanceInfo.InstanceStatus getInstanceRemoteStatus();

    @Deprecated
    List<String> getDiscoveryServiceUrls(String zone);
 
    @Deprecated
    List<String> getServiceUrlsFromConfig(String instanceZone, boolean preferSameZone);
 
    @Deprecated
    List<String> getServiceUrlsFromDNS(String instanceZone, boolean preferSameZone);
  
    @Deprecated
    void registerHealthCheckCallback(HealthCheckCallback callback);

    void registerHealthCheck(HealthCheckHandler healthCheckHandler);

    void registerEventListener(EurekaEventListener eventListener);
 
    boolean unregisterEventListener(EurekaEventListener eventListener);
   
    HealthCheckHandler getHealthCheckHandler();

    void shutdown();
   
    EurekaClientConfig getEurekaClientConfig();
   
    ApplicationInfoManager getApplicationInfoManager();
}
```

## DiscoveryClient

```java
@Singleton
public class DiscoveryClient implements EurekaClient {

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryClient.class);

    // Constants
    public static final String HTTP_X_DISCOVERY_ALLOW_REDIRECT = "X-Discovery-AllowRedirect";

    private static final String VALUE_DELIMITER = ",";
    private static final String COMMA_STRING = VALUE_DELIMITER;

    /**
     * @deprecated here for legacy support as the client config has moved to be an instance variable
     */
    @Deprecated
    private static EurekaClientConfig staticClientConfig;

    // Timers
    private static final String PREFIX = "DiscoveryClient_";

    private final Counter RECONCILE_HASH_CODES_MISMATCH = Monitors.newCounter(PREFIX + "ReconcileHashCodeMismatch");

    private final com.netflix.servo.monitor.Timer FETCH_REGISTRY_TIMER = Monitors
            .newTimer(PREFIX + "FetchRegistry");

    private final Counter REREGISTER_COUNTER = Monitors.newCounter(PREFIX
            + "Reregister");

    // instance variables
    /**
     * A scheduler to be used for the following 3 tasks:
     * - updating service urls
     * - scheduling a TimedSuperVisorTask
     */
    private final ScheduledExecutorService scheduler;
    // additional executors for supervised subtasks
    private final ThreadPoolExecutor heartbeatExecutor;
    private final ThreadPoolExecutor cacheRefreshExecutor;

    private final Provider<HealthCheckHandler> healthCheckHandlerProvider;
    private final Provider<HealthCheckCallback> healthCheckCallbackProvider;
    private final PreRegistrationHandler preRegistrationHandler;
    private final AtomicReference<Applications> localRegionApps = new AtomicReference<Applications>();
    private final Lock fetchRegistryUpdateLock = new ReentrantLock();
    // monotonically increasing generation counter to ensure stale threads do not reset registry to an older version
    private final AtomicLong fetchRegistryGeneration;
    private final ApplicationInfoManager applicationInfoManager;
    private final InstanceInfo instanceInfo;
    private final AtomicReference<String> remoteRegionsToFetch;
    private final AtomicReference<String[]> remoteRegionsRef;
    private final InstanceRegionChecker instanceRegionChecker;

    private final EndpointUtils.ServiceUrlRandomizer urlRandomizer;
    private final EndpointRandomizer endpointRandomizer;
    private final Provider<BackupRegistry> backupRegistryProvider;
    private final EurekaTransport eurekaTransport;

    private final AtomicReference<HealthCheckHandler> healthCheckHandlerRef = new AtomicReference<>();
    private volatile Map<String, Applications> remoteRegionVsApps = new ConcurrentHashMap<>();
    private volatile InstanceInfo.InstanceStatus lastRemoteInstanceStatus = InstanceInfo.InstanceStatus.UNKNOWN;
    private final CopyOnWriteArraySet<EurekaEventListener> eventListeners = new CopyOnWriteArraySet<>();

    private String appPathIdentifier;
    private ApplicationInfoManager.StatusChangeListener statusChangeListener;

    private InstanceInfoReplicator instanceInfoReplicator;

    private volatile int registrySize = 0;
    private volatile long lastSuccessfulRegistryFetchTimestamp = -1;
    private volatile long lastSuccessfulHeartbeatTimestamp = -1;
    private final ThresholdLevelsMetric heartbeatStalenessMonitor;
    private final ThresholdLevelsMetric registryStalenessMonitor;

    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    protected final EurekaClientConfig clientConfig;
    protected final EurekaTransportConfig transportConfig;

    private final long initTimestampMs;
}
```

## InstanceRegistry

```java
public class InstanceRegistry extends PeerAwareInstanceRegistryImpl
		implements ApplicationContextAware {

	private static final Log log = LogFactory.getLog(InstanceRegistry.class);

	private ApplicationContext ctxt;

	private int defaultOpenForTrafficCount;

	public InstanceRegistry(EurekaServerConfig serverConfig,
							EurekaClientConfig clientConfig, ServerCodecs serverCodecs,
							EurekaClient eurekaClient,
							int expectedNumberOfClientsSendingRenews,
							int defaultOpenForTrafficCount) {
		super(serverConfig, clientConfig, serverCodecs, eurekaClient);

		this.expectedNumberOfClientsSendingRenews = expectedNumberOfClientsSendingRenews;
		this.defaultOpenForTrafficCount = defaultOpenForTrafficCount;
	}
}
```

