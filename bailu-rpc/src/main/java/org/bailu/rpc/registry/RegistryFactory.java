package org.bailu.rpc.registry;

import org.bailu.rpc.spi.ExtensionLoader;

public class RegistryFactory {
    public static RegistryService get(String registryService) throws Exception {
        return ExtensionLoader.getInstance().get(registryService);
    }

    public static void init() throws Exception {
        ExtensionLoader.getInstance().loadExtension(RegistryService.class);
    }
}