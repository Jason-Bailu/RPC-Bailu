package org.bailu.rpc.router;

import org.bailu.rpc.common.ServiceMetaRes;

public interface LoadBalancer<T> {
    ServiceMetaRes select(Object[] params, String serviceName);
}
