package com.zmlcoder.rpcx.client;

import java.nio.ByteOrder;

import com.zmlcoder.rpcx.common.RpcxDecoder;
import com.zmlcoder.rpcx.common.RpcxEncoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class RpcxClientChannelInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline channelPipeline = ch.pipeline();
		channelPipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 0, 4, 0, 4, true));
		channelPipeline.addLast(new LengthFieldPrepender(ByteOrder.BIG_ENDIAN, 4, 0, false));

		channelPipeline.addLast(new RpcxEncoder());
		channelPipeline.addLast(new RpcxDecoder());

		// core logic
		channelPipeline.addLast(new RpcxClientChannelHandler());
	}

}
