package com.rpcx.test.integration;

import java.lang.reflect.Method;

import com.zmlcoder.rpcx.common.RpcxRequest;
import com.zmlcoder.rpcx.common.RpcxResponse;
import com.zmlcoder.rpcx.service.RpcxServiceManager;

import io.netty.channel.ChannelHandlerContext;
import net.sf.cglib.reflect.FastClass;

public class RpcxTaskTest implements Runnable {

	private final RpcxRequest request;
	private final ChannelHandlerContext context;

	static FastClass clazz;

	public RpcxTaskTest(RpcxRequest request, ChannelHandlerContext context) {
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

			// default reflect
			long current = System.nanoTime();
			Method method = service.getClass().getMethod(request.getMethodName());
			Object result = method.invoke(service);
			System.err.println("def:" + (System.nanoTime() - current));

			// cglib reflect
			current = System.nanoTime();
			FastClass.create(service.getClass()).invoke(request.getMethodName(), new Class[] {}, service, new Object[] {});
			System.out.println("cg:" + (System.nanoTime() - current));

			// cglib reflect with cache
			current = System.nanoTime();
			if (clazz == null) {
				clazz = FastClass.create(service.getClass());
			}
			clazz.invoke(request.getMethodName(), new Class[] {}, service, new Object[] {});
			System.out.println("cccg:" + (System.nanoTime() - current));

			// normal call
			current = System.nanoTime();
			((HelloWorldService) service).say();
			System.out.println("nor:" + (System.nanoTime() - current));

			response.setStatus("200");
			response.setResult(result + request.getId());

		} catch (Exception e) {
			response.setStatus("500");
			e.printStackTrace();
		}

		context.writeAndFlush(response);
	}

}
