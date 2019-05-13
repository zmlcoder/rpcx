package com.rpcx.test.integration;

import java.io.IOException;

import com.zmlcoder.rpcx.common.RpcxException;
import com.zmlcoder.rpcx.server.RpcxServer;
import com.zmlcoder.rpcx.service.RpcxServiceManager;
import com.zmlcoder.rpcx.service.SingletonRpcxServiceProvider;

public class HelloWorldServer {

	public static void main(String[] args) throws IOException {

		try {

			RpcxServiceManager.getInstance().init();

			SingletonRpcxServiceProvider provider = new SingletonRpcxServiceProvider(HelloWorldService.class, new EchoHelloWorldService());
			RpcxServiceManager.getInstance().add(provider);

			RpcxServer server = new RpcxServer("localhost", 6667);
			server.start();

			System.in.read();

		} catch (RpcxException e) {
			e.printStackTrace();
		}

	}
}
