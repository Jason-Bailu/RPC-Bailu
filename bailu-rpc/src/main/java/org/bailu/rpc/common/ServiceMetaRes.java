package org.bailu.rpc.common;

import java.util.ArrayList;
import java.util.Collection;

public class ServiceMetaRes {
    // 当前服务节点
    private ServiceMeta curServiceMeta;
    // 剩余服务节点
    private Collection<ServiceMeta> otherServiceMeta;

    public ServiceMeta getCurServiceMeta() {
        return curServiceMeta;
    }

    public Collection<ServiceMeta> getOtherServiceMeta() {
        return otherServiceMeta;
    }

    public static ServiceMetaRes build(ServiceMeta curServiceMeta, Collection<ServiceMeta> otherServiceMeta) {
        final ServiceMetaRes serviceMetaRes = new ServiceMetaRes();
        serviceMetaRes.curServiceMeta = curServiceMeta;
        if (otherServiceMeta.size() == 1) {
            otherServiceMeta = new ArrayList<>();
        } else {
            otherServiceMeta.remove(curServiceMeta);
        }
        serviceMetaRes.otherServiceMeta = otherServiceMeta;
        return serviceMetaRes;
    }
}