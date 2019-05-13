package com.zmlcoder.rpcx.common;

import org.junit.Test;

public class RpcxUrlTest {

	@Test
	public void testParse() {
		RpcxUrl.parse("rpcx://zmlcoder:psw@127.0.0.1:8080/com.zmlcoder.hello?p1=v1&p2=v2");
		RpcxUrl.parse("rpcx://127.0.0.1:8080/com.zmlcoder.hello?p1=v1");
		RpcxUrl.parse("rpcx://127.0.0.1:8080/com.zmlcoder.hello");
		RpcxUrl.parse("rpcx://127.0.0.1/com.zmlcoder.hello");
		RpcxUrl.parse("rpcx://127.0.0.1:8080");
		RpcxUrl.parse("rpcx://127.0.0.1");
	}
}
