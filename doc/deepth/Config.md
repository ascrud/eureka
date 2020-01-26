# class

## EurekaServerConfig

```java
public interface EurekaServerConfig 
{

}

@Singleton
public class DefaultEurekaServerConfig implements EurekaServerConfig {

}
```

| Property | Configuration |Default Value| 
| :------| :------ |:------ |
getAWSAccessId | awsAccessId|  null|
getAWSSecretKey | awsSecretKey| null
getEIPBindRebindRetries | eipBindRebindRetries|3
getEIPBindingRetryIntervalMsWhenUnbound |eipBindRebindRetryIntervalMsWhenUnbound | 1 * 60 * 1000|
getEIPBindingRetryIntervalMs|eipBindRebindRetryIntervalMs |5 * 60 * 1000 |
shouldEnableSelfPreservation | enableSelfPreservation| true|
getRenewalPercentThreshold | renewalPercentThreshold | 0.85|
getRenewalThresholdUpdateIntervalMs | renewalThresholdUpdateIntervalMs | 15 * 60 * 1000
getExpectedClientRenewalIntervalSeconds |expectedClientRenewalIntervalSeconds | 30
getPeerEurekaNodesUpdateIntervalMs | peerEurekaNodesUpdateIntervalMs | 10 * 60 * 1000|
shouldEnableReplicatedRequestCompression | enableReplicatedRequestCompression |false|
getNumberOfReplicationRetries | numberOfReplicationRetries|5|
getPeerEurekaStatusRefreshTimeIntervalMs | peerEurekaStatusRefreshTimeIntervalMs| 30 * 1000|
getWaitTimeInMsWhenSyncEmpty| waitTimeInMsWhenSyncEmpty |1000 * 60 * 5|
getPeerNodeConnectTimeoutMs | peerNodeConnectTimeoutMs | 1000|
getPeerNodeReadTimeoutMs |peerNodeReadTimeoutMs | 5000
getPeerNodeTotalConnections |peerNodeTotalConnections | 1000
getPeerNodeTotalConnectionsPerHost | peerNodeTotalConnectionsPerHost |500
getPeerNodeConnectionIdleTimeoutSeconds| peerNodeConnectionIdleTimeoutSeconds| 30
getRetentionTimeInMSInDeltaQueue | retentionTimeInMSInDeltaQueue| 3 * 60 * 1000
getDeltaRetentionTimerIntervalInMs | deltaRetentionTimerIntervalInMs| 30 * 1000
getEvictionIntervalTimerInMs |  evictionIntervalTimerInMs | 60 * 1000
shouldUseAwsAsgApi | shouldUseAwsAsgApi | true
getASGQueryTimeoutMs | asgQueryTimeoutMs | 300
getASGUpdateIntervalMs | asgUpdateIntervalMs | 5 * 60 * 1000
getASGCacheExpiryTimeoutMs | asgCacheExpiryTimeoutMs | 10 * 60 * 1000
getResponseCacheAutoExpirationInSeconds | responseCacheAutoExpirationInSeconds | 180
getResponseCacheUpdateIntervalMs | responseCacheUpdateIntervalMs | 30 * 1000
shouldUseReadOnlyResponseCache   | shouldUseReadOnlyResponseCache | true
shouldDisableDelta  |disableDelta | false
getMaxIdleThreadInMinutesAgeForStatusReplication | maxIdleThreadAgeInMinutesForStatusReplication | 10
getMinThreadsForStatusReplication | minThreadsForStatusReplication | 1
getMaxThreadsForStatusReplication | maxThreadsForStatusReplication | 1
getMaxElementsInStatusReplicationPool | maxElementsInStatusReplicationPool | 10000
shouldSyncWhenTimestampDiffers | syncWhenTimestampDiffers |  true
getRegistrySyncRetries | numberRegistrySyncRetries | 5
getRegistrySyncRetryWaitMs | registrySyncRetryWaitMs | 30 * 1000
getMaxElementsInPeerReplicationPool | maxElementsInPeerReplicationPool | 10000
getMaxIdleThreadAgeInMinutesForPeerReplication | maxIdleThreadAgeInMinutesForPeerReplication | 15
getMinThreadsForPeerReplication | minThreadsForPeerReplication| 5
getMaxThreadsForPeerReplication | maxThreadsForPeerReplication| 20
getMaxTimeForReplication | maxTimeForReplication | 30000|
shouldPrimeAwsReplicaConnections | primeAwsReplicaConnections| true
shouldDisableDeltaForRemoteRegions | disableDeltaForRemoteRegions| false
getRemoteRegionConnectTimeoutMs | remoteRegionConnectTimeoutMs | 2000
getRemoteRegionReadTimeoutMs | remoteRegionReadTimeoutMs | 5000
getRemoteRegionTotalConnections | remoteRegionTotalConnections| 1000
getRemoteRegionTotalConnectionsPerHost | remoteRegionTotalConnectionsPerHost | 500
getRemoteRegionConnectionIdleTimeoutSeconds | remoteRegionConnectionIdleTimeoutSeconds | 30
shouldGZipContentFromRemoteRegion |remoteRegion.gzipContent | true
getRemoteRegionUrlsWithName | remoteRegionUrlsWithName | null
getRemoteRegionUrls | remoteRegionUrls | null
getRemoteRegionAppWhitelist | remoteRegion.{}.appWhiteList| null
getRemoteRegionRegistryFetchInterval | remoteRegion.registryFetchIntervalInSeconds | 30
getRemoteRegionFetchThreadPoolSize   | remoteRegion.fetchThreadPoolSize | 20
getRemoteRegionTrustStore | remoteRegion.trustStoreFileName | ""
getRemoteRegionTrustStorePassword | remoteRegion.trustStorePassword | changeit
disableTransparentFallbackToOtherRegion | remoteRegion.disable.transparent.fallback | false
shouldBatchReplication | shouldBatchReplication | false
getMyUrl | myUrl | null
shouldLogIdentityHeaders | auth.shouldLogIdentityHeaders | true
isRateLimiterEnabled     | rateLimiter.enabled | falseｖ
isRateLimiterThrottleStandardClients | rateLimiter.throttleStandardClients | false
getRateLimiterPrivilegedClients      | rateLimiter.privilegedClients |
getRateLimiterBurstSize | rateLimiter.burstSize | 10
getRateLimiterRegistryFetchAverageRate | rateLimiter.registryFetchAverageRate | 500
getRateLimiterFullFetchAverageRate | rateLimiter.fullFetchAverageRate | 100
getListAutoScalingGroupsRoleName | listAutoScalingGroupsRoleName | ListAutoScalingGroups
getJsonCodecName | jsonCodecName | null
getXmlCodecName   | xmlCodecName | null
getBindingStrategy | awsBindingStrategy |
getRoute53DomainTTL |  route53DomainTTL | 30
getRoute53BindRebindRetries | route53BindRebindRetries | 3
getRoute53BindingRetryIntervalMs | route53BindRebindRetryIntervalMs | 5 * 60 * 1000
getExperimental | experimental. | null
getHealthStatusMinNumberOfAvailablePeers| minAvailableInstancesForPeerReplication| -1
getInitialCapacityOfResponseCache | initialCapacityOfResponseCache | 1000

## EurekaInstanceConfig

```java
@ImplementedBy(CloudInstanceConfig.class)
public interface EurekaInstanceConfig {
}

public abstract class AbstractInstanceConfig implements EurekaInstanceConfig {

}

public interface RefreshableInstanceConfig {}

public abstract class PropertiesInstanceConfig extends AbstractInstanceConfig implements EurekaInstanceConfig {}

@Singleton
@ProvidedBy(CloudInstanceConfigProvider.class)
public class CloudInstanceConfig extends PropertiesInstanceConfig implements RefreshableInstanceConfig {}

```

| Property | Configuration |Default Value| 
| :------| :------ |:------ |
getInstanceId | |
getAppname | | 
getAppGroupName | |
isInstanceEnabledOnit | | false
getNonSecurePort | | 80| 
getSecurePort    | | 443
isNonSecurePortEnabled | | true
getSecurePortEnabled  | | false
getLeaseRenewalIntervalInSeconds | | 30 | 
getLeaseExpirationDurationInSeconds | |90 |
getVirtualHostName | | |
getSecureVirtualHostName | | |
getASGName | | |
getHostName | | |
getMetadataMap | | |
getDataCenterInfo | | |
getIpAddress | | |
getStatusPageUrlPath | | |
getStatusPageUrl | | |
getHomePageUrlPath | | |
getHomePageUrl | | |
getHealthCheckUrlPath | | |
getHealthCheckUrl | | |
getSecureHealthCheckUrl | | |
getDefaultAddressResolutionOrder | | |
getNamespace | | |

## AmazonInfoConfig

```java
public interface AmazonInfoConfig { 

}

public class Archaius1AmazonInfoConfig implements AmazonInfoConfig { 

}

@Singleton
@ConfigurationSource(CommonConstants.CONFIG_FILE_NAME)
public class Archaius2AmazonInfoConfig implements AmazonInfoConfig {

}
```

## EurekaClientConfig

```java
@ImplementedBy(DefaultEurekaClientConfig.class)
public interface EurekaClientConfig {

}

@Singleton
@ProvidedBy(DefaultEurekaClientConfigProvider.class)
public class DefaultEurekaClientConfig implements EurekaClientConfig {}

@Singleton
@ConfigurationSource(CommonConstants.CONFIG_FILE_NAME)
public class EurekaArchaius2ClientConfig implements EurekaClientConfig {}

```

| Property | Configuration |Default Value| 
| :------| :------ |:------ |
| getRegistryFetchIntervalSeconds| |
| getInstanceInfoReplicationIntervalSeconds| |
| getInitialInstanceInfoReplicationIntervalSeconds| |
| getEurekaServiceUrlPollIntervalSeconds| |
| getProxyHost| |
| getProxyPort| |
| getProxyUserName| |
| getProxyPassword| |
| shouldGZipContent| |
| getEurekaServerReadTimeoutSeconds| |
| getEurekaServerConnectTimeoutSeconds| |
| getBackupRegistryImpl| |
| getEurekaServerTotalConnections| |
| getEurekaServerTotalConnectionsPerHost| |
| getEurekaServerURLContext| |
| getEurekaServerPort| |
| getEurekaServerDNSName| |
| shouldUseDnsForFetchingServiceUrls| |
| shouldRegisterWithEureka| |
| shouldUnregisterOnShutdown| |
| shouldPreferSameZoneEureka| |
| allowRedirects| |
| shouldLogDeltaDiff| |
| shouldDisableDelta| |
| fetchRegistryForRemoteRegions| |
| getRegion| |
| getAvailabilityZones| |
| getEurekaServerServiceUrls| |
| shouldFilterOnlyUpInstances | |
| getEurekaConnectionIdleTimeoutSeconds| |
| shouldFetchRegistry| |
| getRegistryRefreshSingleVipAddress| |
| getHeartbeatExecutorThreadPoolSize| |
| getHeartbeatExecutorExponentialBackOffBound| |
| getCacheRefreshExecutorThreadPoolSize| |
| getCacheRefreshExecutorExponentialBackOffBound| |
| getDollarReplacement| |
| getEscapeCharReplacement| |
| shouldOnDemandUpdateStatusChange| |
| shouldEnforceRegistrationAtInit| |
| getEncoderName| |
| getDecoderName| |
| getClientDataAccept| |
| getExperimental| |
| getTransportConfig| |

## EurekaTransportConfig

```java
public interface EurekaTransportConfig { }

public class DefaultEurekaTransportConfig implements EurekaTransportConfig {

}

@Singleton
@ConfigurationSource(CommonConstants.CONFIG_FILE_NAME)
public class EurekaArchaius2TransportConfig implements EurekaTransportConfig {}

```

| Property | Configuration |Default Value| 
| :------| :------ |:------ |
|getSessionedClientReconnectIntervalSeconds |sessionedClientReconnectIntervalSeconds | 20 * 60 |
|getRetryableClientQuarantineRefreshPercentage|retryableClientQuarantineRefreshPercentage | 0.66 |
|getApplicationsResolverDataStalenessThresholdSeconds | applicationsResolverDataStalenessThresholdSeconds | 5 * 60  |
|applicationsResolverUseIp |applicationsResolverUseIp | false |
|getAsyncResolverRefreshIntervalMs|asyncResolverRefreshIntervalMs | 5 * 60 * 1000 |
|getAsyncResolverWarmUpTimeoutMs | asyncResolverWarmupTimeoutMs | 5000 |
|getAsyncExecutorThreadPoolSize| asyncExecutorThreadPoolSize |  5 |
|getWriteClusterVip | writeClusterVip | null |
|getReadClusterVip | readClusterVip | null  |
|getBootstrapResolverStrategy | bootstrapResolverStrategy|  null |
|useBootstrapResolverForQuery | useBootstrapResolverForQuery | true |
