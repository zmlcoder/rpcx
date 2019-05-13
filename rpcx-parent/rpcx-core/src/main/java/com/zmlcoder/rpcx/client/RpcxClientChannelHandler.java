package com.zmlcoder.rpcx.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zmlcoder.rpcx.common.RpcxCallback;
import com.zmlcoder.rpcx.common.RpcxRequest;
import com.zmlcoder.rpcx.common.RpcxResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcxClientChannelHandler extends SimpleChannelInboundHandler<RpcxResponse> {

	private ChannelHandlerContext ctx;
	private Map<String, RpcxCallback> callingMap = new ConcurrentHashMap<>();

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcxResponse msg) throws Exception {
		RpcxResponse response = msg;

		RpcxCallback callback = callingMap.get(response.getRequestId());
		if (callback != null) {
			callback.end(response);
			callingMap.remove(response.getRequestId());
		}

		// System.err.println(response.getId());
		// System.err.println(response.getRequestId());
		// System.err.println(response.getStatus());
		// System.err.println(response.getResult());
		// System.err.println("-----------------------------");
	}

	public RpcxCallback send(RpcxRequest request) {

		RpcxCallback callback = new RpcxCallback();
		callingMap.put(request.getId(), callback);
		callback.begin(request);

		ctx.writeAndFlush(request);

		return callback;

	}
}
