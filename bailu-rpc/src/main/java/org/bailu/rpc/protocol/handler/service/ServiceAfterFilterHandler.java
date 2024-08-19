package org.bailu.rpc.protocol.handler.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bailu.rpc.common.RpcResponse;
import org.bailu.rpc.constant.MsgStatus;
import org.bailu.rpc.filter.FilterConfig;
import org.bailu.rpc.filter.FilterData;
import org.bailu.rpc.filter.client.impl.ClientLogFilter;
import org.bailu.rpc.protocol.MsgHeader;
import org.bailu.rpc.protocol.RpcProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceAfterFilterHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {
    private Logger logger = LoggerFactory.getLogger(ClientLogFilter.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> protocol) throws Exception {
        final FilterData filterData = new FilterData();
        filterData.setData(protocol.getBody());
        RpcResponse response = new RpcResponse();
        MsgHeader header = protocol.getHeader();
        try {
            FilterConfig.getServiceAfterFilterChain().doFilter(filterData);
        } catch (Exception e) {
            header.setStatus((byte) MsgStatus.FAILED.ordinal());
            response.setException(e);
            logger.error("after process request {} error", header.getRequestId(), e);
        }
        ctx.writeAndFlush(protocol);
    }
}
