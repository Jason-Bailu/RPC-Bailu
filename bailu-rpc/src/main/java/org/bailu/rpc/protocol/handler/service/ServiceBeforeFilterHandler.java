package org.bailu.rpc.protocol.handler.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bailu.rpc.common.RpcRequest;
import org.bailu.rpc.common.RpcResponse;
import org.bailu.rpc.constant.MsgStatus;
import org.bailu.rpc.filter.FilterConfig;
import org.bailu.rpc.filter.FilterData;
import org.bailu.rpc.protocol.MsgHeader;
import org.bailu.rpc.protocol.RpcProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceBeforeFilterHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {
    private Logger logger = LoggerFactory.getLogger(ServiceBeforeFilterHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> protocol) throws Exception {
        final RpcRequest request = protocol.getBody();
        final FilterData filterData = new FilterData(request);
        RpcResponse response = new RpcResponse();
        MsgHeader header = protocol.getHeader();
        try {
            FilterConfig.getServiceBeforeFilterChain().doFilter(filterData);
        } catch (Exception e) {
            RpcProtocol<RpcResponse> resProtocol = new RpcProtocol<>();
            header.setStatus((byte) MsgStatus.FAILED.ordinal());
            response.setException(e);
            logger.error("before process request {} error", header.getRequestId(), e);
            resProtocol.setHeader(header);
            resProtocol.setBody(response);
            ctx.writeAndFlush(resProtocol);
            return;
        }
        ctx.fireChannelRead(protocol);
    }
}
