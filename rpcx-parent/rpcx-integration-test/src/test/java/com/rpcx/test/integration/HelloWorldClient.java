package com.rpcx.test.integration;

import java.util.Timer;
import java.util.TimerTask;

import com.zmlcoder.rpcx.client.RpcxClient;
import com.zmlcoder.rpcx.client.RpcxClientManager;

import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;

public class HelloWorldClient {
	public static void main(String[] args) {

		NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

		try {

			// TODO
			RpcxClient client = new RpcxClient(eventLoopGroup, "localhost", 6667);
			client.init();
			ChannelFuture future = client.connect();

			// HelloWorldService service = new StaticProxy(client);
			HelloWorldService helloService = RpcxClientManager.INSTANCE.createRefService(HelloWorldService.class);
			Timer timer = new Timer(true);
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					try {
						System.err.println(helloService.say());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, 1000, 1000 * 5);

			// TODO
			future.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}

	}
}
