package org.bailu.rpc.protocol.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.bailu.rpc.common.RpcFuture;
import org.bailu.rpc.common.RpcLinkHolder;
import org.bailu.rpc.common.RpcResponse;
import org.bailu.rpc.protocol.RpcProtocol;

public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {
    public RpcResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {
        long requestId = msg.getHeader().getRequestId();
        RpcFuture<RpcResponse> future = RpcLinkHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getBody());
    }
}
