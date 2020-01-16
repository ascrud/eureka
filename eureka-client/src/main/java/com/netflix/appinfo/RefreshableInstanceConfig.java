package com.netflix.appinfo;

/**
 * @author supos
 */
public interface RefreshableInstanceConfig {

	/**
	 * resolve the default address
	 *
	 * @param refresh
	 * @return
	 */
	String resolveDefaultAddress(boolean refresh);
}
