package org.bailu.rpc.router;

import org.bailu.rpc.spi.ExtensionLoader;

public class LoadBalancerFactory {
    public static LoadBalancer get(String serviceLoadBalancer) throws Exception {
        return ExtensionLoader.getInstance().get(serviceLoadBalancer);
    }

    public static void init() throws Exception {
        ExtensionLoader.getInstance().loadExtension(LoadBalancer.class);
    }
}
