package com.rpcx.test.integration;

import com.zmlcoder.rpcx.client.RpcxClient;
import com.zmlcoder.rpcx.common.RpcxRequest;

public class StaticProxy implements HelloWorldService {

	private RpcxClient client;

	public StaticProxy(RpcxClient client) {
		this.client = client;

	}

	@Override
	public String say() {
		RpcxRequest request = new RpcxRequest();
		request.setId(String.valueOf(System.nanoTime()));
		request.setClassName(HelloWorldService.class.getName());
		request.setMethodName("say");

		return (String) client.send(request);
	}

}
