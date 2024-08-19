package org.bailu.rpc.filter.service.impl;

import org.bailu.rpc.config.RpcProperties;
import org.bailu.rpc.filter.FilterData;
import org.bailu.rpc.filter.service.ServiceBeforeFilter;

import java.util.Map;

public class ServiceTokenFilter implements ServiceBeforeFilter {
    @Override
    public void doFilter(FilterData filterData) {
        final Map<String, Object> attachments = filterData.getClientAttachments();
        final Map<String, Object> serviceAttachments = RpcProperties.getInstance().getServiceAttachments();
        if (!attachments.getOrDefault("token","").equals(serviceAttachments.getOrDefault("token",""))){
            throw new IllegalArgumentException("token不正确");
        }
    }
}
