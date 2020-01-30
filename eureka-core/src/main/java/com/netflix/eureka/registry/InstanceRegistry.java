package com.netflix.eureka.registry;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.discovery.shared.LookupService;
import com.netflix.discovery.shared.Pair;
import com.netflix.eureka.lease.LeaseManager;

import java.util.List;
import java.util.Map;

/**
 * 实例注册中心
 *
 * @author Tomasz Bak
 */
public interface InstanceRegistry extends LeaseManager<InstanceInfo>, LookupService<String> {

    /**
     * 准备接受请求流量
     *
     * @param applicationInfoManager
     * @param count
     */
    void openForTraffic(ApplicationInfoManager applicationInfoManager, int count);

    /**
     * 关闭
     */
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

    /**
     * Get application information.
     *
     * @param appName             The name of the application
     * @param includeRemoteRegion true, if we need to include applications from remote regions
     *                            as indicated by the region {@link java.net.URL} by this property
     *                            {@link com.netflix.eureka.EurekaServerConfig#getRemoteRegionUrls()}, false otherwise
     * @return the application
     */
    Application getApplication(String appName, boolean includeRemoteRegion);

    /**
     * Gets the {@link InstanceInfo} information.
     *
     * @param appName the application name for which the information is requested.
     * @param id      the unique identifier of the instance.
     * @return the information about the instance.
     */
    InstanceInfo getInstanceByAppAndId(String appName, String id);

    /**
     * Gets the {@link InstanceInfo} information.
     *
     * @param appName              the application name for which the information is requested.
     * @param id                   the unique identifier of the instance.
     * @param includeRemoteRegions true, if we need to include applications from remote regions
     *                             as indicated by the region {@link java.net.URL} by this property
     *                             {@link com.netflix.eureka.EurekaServerConfig#getRemoteRegionUrls()}, false otherwise
     * @return the information about the instance.
     */
    InstanceInfo getInstanceByAppAndId(String appName, String id, boolean includeRemoteRegions);

    void clearRegistry();

    void initializedResponseCache();

    ResponseCache getResponseCache();

    /**
     * 获取上一分钟续约数统计
     *
     * @return
     */
    long getNumOfRenewsInLastMin();

    /**
     * 获取每分钟续约数阈值
     *
     * @return
     */
    int getNumOfRenewsPerMinThreshold();

    /**
     * 是否低于续约数阈值
     *
     * @return
     */
    int isBelowRenewThreshold();

    /**
     * Get the N instances that are most recently registered.
     *
     * @return
     */
    List<Pair<Long, String>> getLastNRegisteredInstances();

    /**
     * Get the N instances that have most recently canceled.
     *
     * @return
     */
    List<Pair<Long, String>> getLastNCanceledInstances();

    /**
     * Checks whether lease expiration is enabled.
     *
     * @return true if enabled
     */
    boolean isLeaseExpirationEnabled();

    /**
     * Checks whether Self-Preservation Mode is enabled.
     *
     * @return
     */
    boolean isSelfPreservationModeEnabled();

}
