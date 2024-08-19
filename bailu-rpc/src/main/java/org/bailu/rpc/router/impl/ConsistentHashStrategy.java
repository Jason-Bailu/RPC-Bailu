package org.bailu.rpc.router.impl;

import org.bailu.rpc.common.ServiceMeta;
import org.bailu.rpc.config.RpcProperties;
import org.bailu.rpc.registry.RegistryService;
import org.bailu.rpc.router.LoadBalancer;
import org.bailu.rpc.common.ServiceMetaRes;
import org.bailu.rpc.spi.ExtensionLoader;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ConsistentHashStrategy implements LoadBalancer {
    private final static int VIRTUAL_NODE_SIZE = 10;
    private final static String VIRTUAL_NODE_SPLIT = "$";

    @Override
    public ServiceMetaRes select(Object[] params, String serviceName) {
        RegistryService registryService = ExtensionLoader.getInstance().get(RpcProperties.getInstance().getRegisterType());
        List<ServiceMeta> discoveries = registryService.discoveries(serviceName);
        final ServiceMeta curServiceMeta = allocateNode(makeConsistentHashRing(discoveries), params[0].hashCode());
        return ServiceMetaRes.build(curServiceMeta,discoveries);
    }

    private ServiceMeta allocateNode(TreeMap<Integer, ServiceMeta> ring, int hashCode) {
        Map.Entry<Integer, ServiceMeta> entry = ring.ceilingEntry(hashCode);
        if (entry == null) {
            entry = ring.firstEntry();
        }
        return entry.getValue();
    }

    private TreeMap<Integer, ServiceMeta> makeConsistentHashRing(List<ServiceMeta> serviceMetas) {
        TreeMap<Integer, ServiceMeta> ring = new TreeMap<>();
        for (ServiceMeta instance : serviceMetas) {
            for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
                ring.put((buildServiceInstanceKey(instance) + VIRTUAL_NODE_SPLIT + i).hashCode(), instance);
            }
        }
        return ring;
    }

    private String buildServiceInstanceKey(ServiceMeta serviceMeta) {
        return String.join(":", serviceMeta.getServiceAddr(), String.valueOf(serviceMeta.getServicePort()));
    }
}
