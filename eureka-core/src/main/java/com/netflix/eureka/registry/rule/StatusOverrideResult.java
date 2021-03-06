package com.netflix.eureka.registry.rule;

import com.netflix.appinfo.InstanceInfo;

/**
 * Container for a result computed by an {@link InstanceStatusOverrideRule}.
 * <p>
 *
 * @author Nikos Michalakis
 * @date 7/13/16
 */
public class StatusOverrideResult {

    public static StatusOverrideResult NO_MATCH = new StatusOverrideResult(false, null);

    public static StatusOverrideResult matchingStatus(InstanceInfo.InstanceStatus status) {
        return new StatusOverrideResult(true, status);
    }

    /**
     * Does the rule match?
     */
    private final boolean matches;

    /**
     * The status computed by the rule.
     */
    private final InstanceInfo.InstanceStatus status;

    private StatusOverrideResult(boolean matches, InstanceInfo.InstanceStatus status) {
        this.matches = matches;
        this.status = status;
    }

    public boolean matches() {
        return matches;
    }

    public InstanceInfo.InstanceStatus status() {
        return status;
    }
}
