package com.zmlcoder.rpcx.server;

import java.lang.reflect.Method;

import com.zmlcoder.rpcx.common.RpcxRequest;
import com.zmlcoder.rpcx.common.RpcxResponse;
import com.zmlcoder.rpcx.service.RpcxServiceManager;

import io.netty.channel.ChannelHandlerContext;

public class RpcxTask implements Runnable {

	private final RpcxRequest request;
	private final ChannelHandlerContext context;

	public RpcxTask(RpcxRequest request, ChannelHandlerContext context) {
		this.request = request;
		this.context = context;
	}

	@Override
	public void run() {

		RpcxResponse response = new RpcxResponse();
		response.setId(String.valueOf(System.nanoTime()));
		response.setRequestId(request.getId());

		try {
			Object service = RpcxServiceManager.getInstance().get(request.getClassName());
			Method method = service.getClass().getMethod(request.getMethodName());
			Object result = method.invoke(service);

			response.setStatus("200");
			response.setResult(result + request.getId());

		} catch (Exception e) {
			response.setStatus("500");
			e.printStackTrace();
		}

		context.writeAndFlush(response);
	}

}
