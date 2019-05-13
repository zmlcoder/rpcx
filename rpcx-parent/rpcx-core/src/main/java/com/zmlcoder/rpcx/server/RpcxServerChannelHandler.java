package com.zmlcoder.rpcx.server;

import com.zmlcoder.rpcx.common.RpcxRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RpcxServerChannelHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		RpcxRequest request = (RpcxRequest) msg;
		System.out.println("id:" + request.getId());
		System.out.println(request.getClassName());
		System.out.println(request.getMethodName());
		System.out.println("---------------------------");

		RpcxTask task = new RpcxTask(request, ctx);
		RpcxTaskExecutor.getInstance().submit(task);
	}

}
