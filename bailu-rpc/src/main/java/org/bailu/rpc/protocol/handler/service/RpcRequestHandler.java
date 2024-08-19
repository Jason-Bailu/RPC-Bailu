package org.bailu.rpc.protocol.handler.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bailu.rpc.common.RpcRequest;
import org.bailu.rpc.poll.ThreadPollFactory;
import org.bailu.rpc.protocol.RpcProtocol;

public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {
    public RpcRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> protocol) throws Exception {
        ThreadPollFactory.submitRequest(ctx,protocol);
    }
}
