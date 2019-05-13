package com.zmlcoder.rpcx.client;

import com.zmlcoder.rpcx.common.RpcxCallback;
import com.zmlcoder.rpcx.common.RpcxException;
import com.zmlcoder.rpcx.common.RpcxRequest;
import com.zmlcoder.rpcx.events.RpcxEventManager;
import com.zmlcoder.rpcx.events.RpcxEventTypes;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RpcxClient {

	private final EventLoopGroup eventLoopGroup;
	private final String host;
	private final int port;

	private RpcxClientChannelHandler handler;

	public RpcxClient(EventLoopGroup eventLoopGroup, String host, int port) {
		this.eventLoopGroup = eventLoopGroup;
		this.host = host;
		this.port = port;
	}

	public void init() {
		RpcxEventManager.INSTANCE.init();
	}

	public ChannelFuture connect() throws RpcxException {
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).remoteAddress(host, port).handler(new RpcxClientChannelInitializer());

			// wait until connecting action is finished.
			ChannelFuture channelFuture = bootstrap.connect().await();

			if (channelFuture.isSuccess()) {
				handler = channelFuture.channel().pipeline().get(RpcxClientChannelHandler.class);
				RpcxEventManager.INSTANCE.publish(RpcxEventTypes.EVENT_CLIENT_CONNECTED);
			} else {
				throw new RpcxException("can not connect to server!");
			}

			return channelFuture;

		} catch (Throwable e) {
			throw new RpcxException(e);
		}
	}

	public void disconnect() {

	}

	public Object send(RpcxRequest request) {

		RpcxCallback callback = handler.send(request);

		// block call
		Object result = callback.get();

		return result;

	}

}
