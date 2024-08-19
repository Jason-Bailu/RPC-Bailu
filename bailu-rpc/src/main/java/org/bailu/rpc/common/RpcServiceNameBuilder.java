package org.bailu.rpc.common;

public class RpcServiceNameBuilder {
    public static String buildServiceKey(String serviceName, String serviceVersion) {
        return String.join("$", serviceName, serviceVersion);
    }
}
