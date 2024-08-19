package org.bailu.rpc.registry;

import org.bailu.rpc.common.ServiceMeta;

import java.io.IOException;
import java.util.List;

public interface RegistryService {
    // 服务注册
    void register(ServiceMeta serviceMeta) throws Exception;
    // 服务注销
    void unRegister(ServiceMeta serviceMeta) throws Exception;
    // 获取服务
    List<ServiceMeta> discoveries(String serviceName);
    // 销毁
    void destroy() throws IOException;

}
