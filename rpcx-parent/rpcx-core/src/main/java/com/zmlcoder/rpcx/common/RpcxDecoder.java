package com.zmlcoder.rpcx.common;

import java.io.ObjectInputStream;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcxDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		ByteBufInputStream bis = new ByteBufInputStream(in);
		ObjectInputStream ois = new ObjectInputStream(bis);
		out.add(ois.readObject());
		ois.close();
	}

}
