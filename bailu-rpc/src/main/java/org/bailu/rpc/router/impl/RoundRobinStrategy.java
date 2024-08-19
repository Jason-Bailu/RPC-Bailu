package org.bailu.rpc.router.impl;

import org.bailu.rpc.common.ServiceMeta;
import org.bailu.rpc.config.RpcProperties;
import org.bailu.rpc.registry.RegistryService;
import org.bailu.rpc.router.LoadBalancer;
import org.bailu.rpc.common.ServiceMetaRes;
import org.bailu.rpc.spi.ExtensionLoader;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinStrategy implements LoadBalancer {
    private static AtomicInteger roundRobinId = new AtomicInteger(0);

    @Override
    public ServiceMetaRes select(Object[] params, String serviceName) {
        RegistryService registryService = ExtensionLoader.getInstance().get(RpcProperties.getInstance().getRegisterType());
        List<ServiceMeta> discoveries = registryService.discoveries(serviceName);
        int size = discoveries.size();
        roundRobinId.addAndGet(1);
        if (roundRobinId.get() == Integer.MIN_VALUE) {
            roundRobinId.set(0);
        }
        return ServiceMetaRes.build(discoveries.get(roundRobinId.get() % size), discoveries);
    }
}
