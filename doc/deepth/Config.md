# class

## Eureka服务端配置 EurekaServerConfig

```java
public interface EurekaServerConfig 
{

}

@Singleton
public class DefaultEurekaServerConfig implements EurekaServerConfig {

}
```

|# | Property | Configuration |Default Value| Description |
| :---: | :------| :------ |:------ |:------ |
|1|getAWSAccessId | `awsAccessId`|  null| 获取aws访问的id，主要用于弹性ip绑定，此配置是用于aws上的，默认为null
|2|getAWSSecretKey | `awsSecretKey`| null | 获取aws私有秘钥，主要用于弹性ip绑定，此配置是用于aws上的，默认为null
|3|getEIPBindRebindRetries | `eipBindRebindRetries`|3 | 获取服务器尝试绑定到候选的EIP的次数，默认为3
|4|getEIPBindingRetryIntervalMsWhenUnbound |`eipBindRebindRetryIntervalMsWhenUnbound` | 1 * 60 * 1000| 服务器检查ip绑定的时间间隔，单位为毫秒，默认为1 * 60 * 1000
|5|getEIPBindingRetryIntervalMs|`eipBindRebindRetryIntervalMs` |5 * 60 * 1000 | 与上面的是同一作用，仅仅是稳定状态检查，默认为5 * 60 * 1000
|6|shouldEnableSelfPreservation | `enableSelfPreservation`| true| 自我保护模式，当出现出现网络分区、eureka在短时间内丢失过多客户端时，会进入自我保护模式，即一个服务长时间没有发送心跳，eureka也不会将其删除，默认为true
|7|getRenewalPercentThreshold | `renewalPercentThreshold` | 0.85| 阈值因子，默认是0.85，如果阈值比最小值大，则自我保护模式开启
|8|getRenewalThresholdUpdateIntervalMs | `renewalThresholdUpdateIntervalMs` | 15 * 60 * 1000| 阈值更新的时间间隔，单位为毫秒，默认为15 * 60 * 1000
|9|getExpectedClientRenewalIntervalSeconds |`expectedClientRenewalIntervalSeconds` | 30 | 
|10|getPeerEurekaNodesUpdateIntervalMs | `peerEurekaNodesUpdateIntervalMs` | 10 * 60 * 1000| 集群里eureka节点的变化信息更新的时间间隔，单位为毫秒，默认为10 * 60 * 1000
|11|shouldEnableReplicatedRequestCompression | `enableReplicatedRequestCompression` |false| 复制的数据在发送请求时是否被压缩，默认为false
|12|getNumberOfReplicationRetries | `numberOfReplicationRetries`|5| 获取集群里服务器尝试复制数据的次数，默认为5
|13|getPeerEurekaStatusRefreshTimeIntervalMs | `peerEurekaStatusRefreshTimeIntervalMs`| 30 * 1000| 服务器节点的状态信息被更新的时间间隔，单位为毫秒，默认为30 * 1000
|14|getWaitTimeInMsWhenSyncEmpty| `waitTimeInMsWhenSyncEmpty` |1000 * 60 * 5| 在Eureka服务器获取不到集群里对等服务器上的实例时，需要等待的时间，单位为毫秒，默认为1000 * 60 * 5
|15|getPeerNodeConnectTimeoutMs | `peerNodeConnectTimeoutMs` | 1000| 连接对等节点服务器复制的超时的时间，单位为毫秒，默认为200
|16|getPeerNodeReadTimeoutMs |`peerNodeReadTimeoutMs` | 5000 | 读取对等节点服务器复制的超时的时间，单位为毫秒，默认为200
|17|getPeerNodeTotalConnections |`peerNodeTotalConnections` | 1000 | 获取对等节点上http连接的总数，默认为1000
|18|getPeerNodeTotalConnectionsPerHost | `peerNodeTotalConnectionsPerHost` |500 | 获取特定的对等节点上http连接的总数，默认为500
|19|getPeerNodeConnectionIdleTimeoutSeconds| `peerNodeConnectionIdleTimeoutSeconds`| 30 | http连接被清理之后服务器的空闲时间，默认为30秒
|20|getRetentionTimeInMSInDeltaQueue | `retentionTimeInMSInDeltaQueue`| 3 * 60 * 1000 | 客户端保持增量信息缓存的时间，从而保证不会丢失这些信息，单位为毫秒，默认为3 * 60 * 1000
|21|getDeltaRetentionTimerIntervalInMs | `deltaRetentionTimerIntervalInMs`| 30 * 1000 | 清理任务程序被唤醒的时间间隔，清理过期的增量信息，单位为毫秒，默认为30 * 1000
|22|getEvictionIntervalTimerInMs |  `evictionIntervalTimerInMs` | 60 * 1000 | 过期实例应该启动并运行的时间间隔，单位为毫秒，默认为60 * 1000
|23|shouldUseAwsAsgApi | `shouldUseAwsAsgApi` | true |
|24|getASGQueryTimeoutMs | `asgQueryTimeoutMs` | 300 | 查询AWS上ASG（自动缩放组）信息的超时值，单位为毫秒，默认为300
|25|getASGUpdateIntervalMs | `asgUpdateIntervalMs` | 5 * 60 * 1000 | 从AWS上更新ASG信息的时间间隔，单位为毫秒，默认为5 * 60 * 1000
|26|getASGCacheExpiryTimeoutMs | `asgCacheExpiryTimeoutMs` | 10 * 60 * 1000 | 缓存ASG信息的到期时间，单位为毫秒，默认为10 * 60 * 1000
|27|getResponseCacheAutoExpirationInSeconds | `responseCacheAutoExpirationInSeconds` | 180 | 当注册表信息被改变时，则其被保存在缓存中不失效的时间，默认为180秒
|28|getResponseCacheUpdateIntervalMs | `responseCacheUpdateIntervalMs` | 30 * 1000 | 客户端的有效负载缓存应该更新的时间间隔，默认为30 * 1000毫秒
|29|shouldUseReadOnlyResponseCache   | `shouldUseReadOnlyResponseCache` | true | 目前采用的是二级缓存策略，一个是读写高速缓存过期策略，另一个没有过期只有只读缓存，默认为true，表示只读缓存
|30|shouldDisableDelta  |`disableDelta` | false | 增量信息是否可以提供给客户端看，默认为false
|31|getMaxIdleThreadInMinutesAgeForStatusReplication | `maxIdleThreadAgeInMinutesForStatusReplication` | 10 | 状态复制线程可以保持存活的空闲时间，默认为10分钟
|32|getMinThreadsForStatusReplication | `minThreadsForStatusReplication` | 1 | 被用于状态复制的线程的最小数目，默认为1
|33|getMaxThreadsForStatusReplication | `maxThreadsForStatusReplication` | 1 | 被用于状态复制的线程的最大数目，默认为1
|34|getMaxElementsInStatusReplicationPool | `maxElementsInStatusReplicationPool` | 10000 | 可允许的状态复制池备份复制事件的最大数量，默认为10000
|35|shouldSyncWhenTimestampDiffers | `syncWhenTimestampDiffers` | true | 当时间变化实例是否跟着同步，默认为true
|36|getRegistrySyncRetries | `numberRegistrySyncRetries` | 5 | 当eureka服务器启动时尝试去获取集群里其他服务器上的注册信息的次数，默认为5
|37|getRegistrySyncRetryWaitMs | `registrySyncRetryWaitMs` | 30 * 1000 | 当eureka服务器启动时获取其他服务器的注册信息失败时，会再次尝试获取，期间需要等待的时间，默认为30 * 1000毫秒
|38|getMaxElementsInPeerReplicationPool | `maxElementsInPeerReplicationPool` | 10000 | 复制池备份复制事件的最大数量，默认为10000
|39|getMaxIdleThreadAgeInMinutesForPeerReplication | `maxIdleThreadAgeInMinutesForPeerReplication` | 15 | 复制线程可以保持存活的空闲时间，默认为15分钟
|40|getMinThreadsForPeerReplication | `minThreadsForPeerReplication`| 5 | 获取将被用于复制线程的最小数目，默认为5
|41|getMaxThreadsForPeerReplication | `maxThreadsForPeerReplication`| 20 | 获取将被用于复制线程的最大数目，默认为20
|42|getMaxTimeForReplication | `maxTimeForReplication` | 30000| 尝试在丢弃复制事件之前进行复制的时间，默认为30000毫秒
|43|shouldPrimeAwsReplicaConnections | `primeAwsReplicaConnections`| true | 对集群中服务器节点的连接是否应该准备，默认为true
|44|shouldDisableDeltaForRemoteRegions | `disableDeltaForRemoteRegions`| false | 增量信息是否可以提供给客户端或一些远程地区，默认为false
|45|getRemoteRegionConnectTimeoutMs | `remoteRegionConnectTimeoutMs` | 2000 |  连接到对等远程地eureka节点的超时时间，默认为1000毫秒
|46|getRemoteRegionReadTimeoutMs | `remoteRegionReadTimeoutMs` | 5000 | 获取从远程地区eureka节点读取信息的超时时间，默认为1000毫秒
|47|getRemoteRegionTotalConnections | `remoteRegionTotalConnections`| 1000 | 获取远程地区对等节点上http连接的总数，默认为1000
|48|getRemoteRegionTotalConnectionsPerHost | `remoteRegionTotalConnectionsPerHost` | 500 | 获取远程地区特定的对等节点上http连接的总数，默认为500
|49|getRemoteRegionConnectionIdleTimeoutSeconds | `remoteRegionConnectionIdleTimeoutSeconds` | 30 | http连接被清理之后远程地区服务器的空闲时间，默认为30秒
|50|shouldGZipContentFromRemoteRegion |`remoteRegion.gzipContent` | true | eureka服务器中获取的内容是否在远程地区被压缩，默认为true
|51|getRemoteRegionUrlsWithName | `remoteRegionUrlsWithName` | null | 针对远程地区发现的网址域名的map
|52|getRemoteRegionUrls | `remoteRegionUrls` | null | 远程地区的URL列表
|53|getRemoteRegionAppWhitelist | `remoteRegion.{}.appWhiteList`| null | 必须通过远程区域中检索的应用程序的列表
|54|getRemoteRegionRegistryFetchInterval | `remoteRegion.registryFetchIntervalInSeconds` | 30 | 从远程区域取出该注册表的信息的时间间隔，默认为30秒
|55|getRemoteRegionFetchThreadPoolSize   | `remoteRegion.fetchThreadPoolSize` | 20 | 用于执行远程区域注册表请求的线程池的大小，默认为20
|56|getRemoteRegionTrustStore | `remoteRegion.trustStoreFileName` | "" | 用来合格请求远程区域注册表的信任存储文件，默认为空
|57|getRemoteRegionTrustStorePassword | `remoteRegion.trustStorePassword` | changeit | 获取偏远地区信任存储文件的密码，默认为“changeit”
|58|disableTransparentFallbackToOtherRegion | `remoteRegion.disable.transparent.fallback` | false | 如果在远程区域本地没有实例运行，对于应用程序回退的旧行为是否被禁用， 默认为false
|59|shouldBatchReplication | `shouldBatchReplication` | false | 表示集群节点之间的复制是否为了网络效率而进行批处理，默认为false
|60|getMyUrl | `myUrl` | null |
|61|shouldLogIdentityHeaders | `auth.shouldLogIdentityHeaders` | true | Eureka服务器是否应该登录clientAuthHeaders，默认为true
|62|isRateLimiterEnabled     | `rateLimiter.enabled` | false | 限流是否应启用或禁用，默认为false
|63|isRateLimiterThrottleStandardClients | `rateLimiter.throttleStandardClients` | false | 是否对标准客户端进行限流，默认false
|64|getRateLimiterPrivilegedClients      | `rateLimiter.privilegedClients` | |认证的客户端列表，这里是除了标准的eureka Java客户端
|65|getRateLimiterBurstSize | `rateLimiter.burstSize` | 10 | 速率限制的burst size ，默认为10，这里用的是令牌桶算法
|66|getRateLimiterRegistryFetchAverageRate | `rateLimiter.registryFetchAverageRate` | 500 | 速率限制器用的是令牌桶算法，此配置指定平均执行注册请求速率，默认为500
|67|getRateLimiterFullFetchAverageRate | `rateLimiter.fullFetchAverageRate` | 100 | 速率限制器用的是令牌桶算法，此配置指定平均执行请求速率，默认为100
|68|getListAutoScalingGroupsRoleName | `listAutoScalingGroupsRoleName` | ListAutoScalingGroups | 用来描述从AWS第三账户的自动缩放组中的角色名称，默认为“ListAutoScalingGroups”
|69|getJsonCodecName | `jsonCodecName` | null | 如果没有设置默认的编解码器将使用全JSON编解码器，获取的是编码器的类名称
|70|getXmlCodecName   | `xmlCodecName` | null | 如果没有设置默认的编解码器将使用xml编解码器，获取的是编码器的类名称
|71|getBindingStrategy | `awsBindingStrategy` | 获取配置绑定EIP或Route53的策略
|72|getRoute53DomainTTL |  `route53DomainTTL` | 30 | 用于建立route53域的ttl，默认为30
|73|getRoute53BindRebindRetries | `route53BindRebindRetries` | 3 | 服务器尝试绑定到候选Route53域的次数，默认为3
|74|getRoute53BindingRetryIntervalMs | `route53BindRebindRetryIntervalMs` | 5 * 60 * 1000 | 服务器应该检查是否和Route53域绑定的时间间隔，默认为5 * 60 * 1000毫秒
|75|getExperimental | `experimental.` | null | 当尝试新功能迁移过程时，为了避免配置API污染，相应的配置即可投入实验配置部分，默认为null
|76|getHealthStatusMinNumberOfAvailablePeers| `minAvailableInstancesForPeerReplication`| -1 |
|77|getInitialCapacityOfResponseCache | `initialCapacityOfResponseCache` | 1000 |

## 实例配置 EurekaInstanceConfig

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

|# | Property | Configuration |Default Value| Description |
| :---: | :------| :------ |:------ |:------ |
|1| getInstanceId | | | 此实例注册到eureka服务端的唯一的实例ID,其组成为${spring.application.name}:${spring.application.instance_id:${random.value}}
|2|getAppname | | | 获得在eureka服务上注册的应用程序的名字，默认为unknow
|3|getAppGroupName | | | 获得在eureka服务上注册的应用程序组的名字，默认为unknow
|4|isInstanceEnabledOnit | | false | 实例注册到eureka服务器时，是否开启通讯，默认为false
|5|getNonSecurePort | | 80 |  获取该实例应该接收通信的非安全端口。默认为80
|6|getSecurePort    | | 443 | 获取该实例应该接收通信的安全端口，默认为443
|7|isNonSecurePortEnabled | | true | 该实例应该接收通信的非安全端口是否启用，默认为true
|8|getSecurePortEnabled  | | false | 该实例应该接收通信的安全端口是否启用，默认为false
|9|getLeaseRenewalIntervalInSeconds | | 30 |  eureka客户需要多长时间发送心跳给eureka服务器，表明它仍然活着,默认为30 秒
|10|getLeaseExpirationDurationInSeconds | | 90  | Eureka服务器在接收到实例的最后一次发出的心跳后，需要等待多久才可以将此实例删除，默认为90秒
|11|getVirtualHostName | | | 此实例定义的虚拟主机名，其他实例将通过使用虚拟主机名找到该实例
|12|getSecureVirtualHostName | | | 此实例定义的安全虚拟主机名
|13|getASGName | | |  与此实例相关联 AWS自动缩放组名称。此项配置是在AWS环境专门使用的实例启动，它已被用于流量停用后自动把一个实例退出服务
|14|getHostName | | | 与此实例相关联的主机名，是其他实例可以用来进行请求的准确名称
|15|getMetadataMap | | | 获取与此实例相关联的元数据(key,value)。这个信息被发送到eureka服务器，其他实例可以使用
|16|getDataCenterInfo | | | 该实例被部署在数据中心
|17|getIpAddress | | | 获取实例的ip地址
|18|getStatusPageUrlPath | | | 获取此实例状态页的URL路径，然后构造出主机名，安全端口等，默认为/info
|19|getStatusPageUrl | | | 获取此实例绝对状态页的URL路径，为其他服务提供信息时来找到这个实例的状态的路径，默认为null
|20|getHomePageUrlPath | | | 获取此实例的相关主页URL路径，然后构造出主机名，安全端口等，默认为/
|21|getHomePageUrl | | | 获取此实例的绝对主页URL路径，为其他服务提供信息时使用的路径,默认为null
|22|getHealthCheckUrlPath | | | 获取此实例的相对健康检查URL路径，默认为/health
|23|getHealthCheckUrl | | | 获取此实例的绝对健康检查URL路径,默认为null
|24|getSecureHealthCheckUrl | | | 获取此实例的绝对安全健康检查网页的URL路径，默认为null
|25|getDefaultAddressResolutionOrder | | | 获取实例的网络地址，默认为[]
|26|getNamespace | | | 获取用于查找属性的命名空间，默认为eureka

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

|# | Property | Configuration |Default Value| Description |
|:---:| :------| :------ |:------ |:------ |
|1| getRegistryFetchIntervalSeconds| | | 从eureka服务器注册表中获取注册信息的时间间隔（s），默认为30秒
|2| getInstanceInfoReplicationIntervalSeconds| | | 复制实例变化信息到eureka服务器所需要的时间间隔（s），默认为30秒
|3| getInitialInstanceInfoReplicationIntervalSeconds| | | 最初复制实例信息到eureka服务器所需的时间（s），默认为40秒
|4| getEurekaServiceUrlPollIntervalSeconds| | | 询问Eureka服务url信息变化的时间间隔（s），默认为300秒
|5| getProxyHost| | | 获取eureka服务的代理主机，默认为nul
|6| getProxyPort| | | 获取eureka服务的代理端口, 默认为null
|7| getProxyUserName| | | 获取eureka服务的代理用户名，默认为null
|8| getProxyPassword| | | 获取eureka服务的代理密码，默认为null
|9| shouldGZipContent| | | eureka注册表的内容是否被压缩，默认为true，并且是在最好的网络流量下被压缩
|10| getEurekaServerReadTimeoutSeconds| | | eureka需要超时读取之前需要等待的时间，默认为8秒
|11| getEurekaServerConnectTimeoutSeconds| | | eureka需要超时连接之前需要等待的时间，默认为5秒
|12| getBackupRegistryImpl| | | 获取实现了eureka客户端在第一次启动时读取注册表的信息作为回退选项的实现名称
|13| getEurekaServerTotalConnections| | | eureka客户端允许所有eureka服务器连接的总数目，默认是200
|14| getEurekaServerTotalConnectionsPerHost| | | eureka客户端允许eureka服务器主机连接的总数目，默认是50
|15| getEurekaServerURLContext| | | 表示eureka注册中心的路径，如果配置为eureka，则为http://x.x.x.x:x/eureka/，在eureka的配置文件中加入此配置表示eureka作为客户端向注册中心注册，从而构成eureka集群。此配置只有在eureka服务器ip地址列表是在DNS中才会用到，默认为null
|16| getEurekaServerPort| | | 获取eureka服务器的端口，此配置只有在eureka服务器ip地址列表是在DNS中才会用到。默认为null
|17| getEurekaServerDNSName| | | 获取要查询的DNS名称来获得eureka服务器，此配置只有在eureka服务器ip地址列表是在DNS中才会用到。默认为null
|18| shouldUseDnsForFetchingServiceUrls| | | eureka客户端是否应该使用DNS机制来获取eureka服务器的地址列表，默认为false
|19| shouldRegisterWithEureka| | | 实例是否在eureka服务器上注册自己的信息以供其他服务发现，默认为true
|20| shouldUnregisterOnShutdown| |
|21| shouldPreferSameZoneEureka| | | 实例是否使用同一zone里的eureka服务器，默认为true，理想状态下，eureka客户端与服务端是在同一zone下
|22| allowRedirects| | | 服务器是否能够重定向客户端请求到备份服务器。 如果设置为false，服务器将直接处理请求，如果设置为true，它可能发送HTTP重定向到客户端。默认为false
|23| shouldLogDeltaDiff| | | 是否记录eureka服务器和客户端之间在注册表的信息方面的差异，默认为false
|24| shouldDisableDelta| | | 默认为false
|25| fetchRegistryForRemoteRegions| | | eureka服务注册表信息里的以逗号隔开的地区名单，如果不这样返回这些地区名单，则客户端启动将会出错。默认为null
|26| getRegion| | | 获取实例所在的地区。默认为us-east-1
|27| getAvailabilityZones| | | 获取实例所在的地区下可用性的区域列表，用逗号隔开
|28| getEurekaServerServiceUrls| | | Eureka服务器的连接，默认为http：//XXXX：X/eureka/,但是如果采用DNS方式获取服务地址，则不需要配置此设置。
|29| shouldFilterOnlyUpInstances | | | 是否获得处于开启状态的实例的应用程序过滤之后的应用程序。默认为true
|30| getEurekaConnectionIdleTimeoutSeconds| | | Eureka服务的http请求关闭之前其响应的时间，默认为30 秒
|31| shouldFetchRegistry| | | 此客户端是否获取eureka服务器注册表上的注册信息，默认为true
|32| getRegistryRefreshSingleVipAddress| | | 此客户端只对一个单一的VIP注册表的信息感兴趣。默认为null
|33| getHeartbeatExecutorThreadPoolSize| | | 心跳执行程序线程池的大小,默认为5
|34| getHeartbeatExecutorExponentialBackOffBound| | | 心跳执行程序回退相关的属性，是重试延迟的最大倍数值，默认为10
|35| getCacheRefreshExecutorThreadPoolSize| | | 执行程序缓存刷新线程池的大小，默认为5
|36| getCacheRefreshExecutorExponentialBackOffBound| | | 执行程序指数回退刷新的相关属性，是重试延迟的最大倍数值，默认为10
|37| getDollarReplacement| | | eureka服务器序列化/反序列化的信息中获取“$”符号的的替换字符串。默认为“_-”
|38| getEscapeCharReplacement| | | eureka服务器序列化/反序列化的信息中获取“_”符号的的替换字符串。默认为“__”
|39| shouldOnDemandUpdateStatusChange| | | 如果设置为true,客户端的状态更新将会点播更新到远程服务器上，默认为true
|40| shouldEnforceRegistrationAtInit| |
|41| getEncoderName| | | 这是一个短暂的编码器的配置，如果最新的编码器是稳定的，则可以去除，默认为null
|42| getDecoderName| | | 这是一个短暂的解码器的配置，如果最新的解码器是稳定的，则可以去除，默认为null
|43| getClientDataAccept| | | 客户端数据接收
|44| getExperimental| |   | 当尝试新功能迁移过程时，为了避免配置API污染，相应的配置即可投入实验配置部分，默认为null
|45| getTransportConfig| |

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
