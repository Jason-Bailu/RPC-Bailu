package org.bailu.rpc.protocol.serialization;

import org.bailu.rpc.spi.ExtensionLoader;

public class SerializationFactory {
    public static RpcSerialization get(String serialization) throws Exception {
        return ExtensionLoader.getInstance().get(serialization);
    }

    public static void init() throws Exception {
        ExtensionLoader.getInstance().loadExtension(RpcSerialization.class);
    }
}
