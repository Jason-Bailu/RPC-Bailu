package org.bailu.rpc.filter;

public class ClientAfterFilter implements org.bailu.rpc.filter.client.ClientAfterFilter {
    @Override
    public void doFilter(FilterData filterData) {
        System.out.println("客户端后置处理器启动咯");
    }
}
