package com.netflix.discovery;

/**
 * Listener for receiving {@link EurekaClient} events such as {@link StatusChangeEvent}.  Register
 * a listener by calling {@link EurekaClient#registerEventListener(EurekaEventListener)}
 *
 * @author weixiaoyao
 */
public interface EurekaEventListener {

    /**
     * Notification of an event within the {@link EurekaClient}.
     * <p>
     * {@link EurekaEventListener#onEvent} is called from the context of an internal eureka thread
     * and must therefore return as quickly as possible without blocking.
     *
     * @param event 　event
     */
    void onEvent(EurekaEvent event);
}
