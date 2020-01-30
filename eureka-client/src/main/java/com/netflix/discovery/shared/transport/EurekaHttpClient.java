package com.netflix.discovery.shared.transport;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;

/**
 * Low level Eureka HTTP client API.
 *
 * @author Tomasz Bak
 */
public interface EurekaHttpClient {

    /**
     * register an instanceInfo
     *
     * @param info
     * @return
     */
    EurekaHttpResponse<Void> register(InstanceInfo info);

    /**
     * cancel an instance by appName and instance id
     *
     * @param appName
     * @param id
     * @return
     */
    EurekaHttpResponse<Void> cancel(String appName, String id);

    /**
     * sendHeartBeat
     *
     * @param appName
     * @param id
     * @param info
     * @param overriddenStatus
     * @return
     */
    EurekaHttpResponse<InstanceInfo> sendHeartBeat(String appName, String id, InstanceInfo info, InstanceStatus overriddenStatus);

    /**
     * statusUpdate
     *
     * @param appName
     * @param id
     * @param newStatus
     * @param info
     * @return
     */
    EurekaHttpResponse<Void> statusUpdate(String appName, String id, InstanceStatus newStatus, InstanceInfo info);

    /**
     * deleteStatusOverride
     *
     * @param appName
     * @param id
     * @param info
     * @return
     */
    EurekaHttpResponse<Void> deleteStatusOverride(String appName, String id, InstanceInfo info);

    /**
     * getApplications
     *
     * @param regions
     * @return
     */
    EurekaHttpResponse<Applications> getApplications(String... regions);

    /**
     * getDelta
     *
     * @param regions
     * @return
     */
    EurekaHttpResponse<Applications> getDelta(String... regions);

    /**
     * getVip
     *
     * @param vipAddress
     * @param regions
     * @return
     */
    EurekaHttpResponse<Applications> getVip(String vipAddress, String... regions);

    /**
     * getSecureVip
     *
     * @param secureVipAddress
     * @param regions
     * @return
     */
    EurekaHttpResponse<Applications> getSecureVip(String secureVipAddress, String... regions);

    /**
     * getApplication
     *
     * @param appName
     * @return
     */
    EurekaHttpResponse<Application> getApplication(String appName);

    /**
     * getInstance
     *
     * @param appName
     * @param id
     * @return
     */
    EurekaHttpResponse<InstanceInfo> getInstance(String appName, String id);

    /**
     * getInstance
     *
     * @param id
     * @return
     */
    EurekaHttpResponse<InstanceInfo> getInstance(String id);

    /**
     * shutdown
     */
    void shutdown();
}
