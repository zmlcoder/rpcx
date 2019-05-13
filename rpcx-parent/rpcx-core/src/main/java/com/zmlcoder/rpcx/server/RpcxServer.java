package com.zmlcoder.rpcx.server;

import com.zmlcoder.rpcx.common.RpcxException;
import com.zmlcoder.rpcx.events.RpcxEventManager;
import com.zmlcoder.rpcx.events.RpcxEventTypes;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RpcxServer {
	private final String host;
	private final int port;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	public RpcxServer(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() throws RpcxException {
		try {

			bossGroup = new NioEventLoopGroup();
			workerGroup = new NioEventLoopGroup();

			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).localAddress(host, port).childHandler(new RpcxServerChannelInitializer());
			ChannelFuture future = bootstrap.bind().sync();

			if (future.isSuccess()) {
				RpcxEventManager.INSTANCE.publish(RpcxEventTypes.EVENT_SERVER_CONNECTED);
			} else {
				throw new RpcxException("Failed to start server!");
			}

			// block until close.
			// future.channel().closeFuture().sync();

		} catch (Exception e) {

			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();

			throw new RpcxException(e);
		}
	}

}
