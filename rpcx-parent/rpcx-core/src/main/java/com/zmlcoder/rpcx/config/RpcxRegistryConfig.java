package com.zmlcoder.rpcx.config;

import com.zmlcoder.rpcx.common.RpcxConstants;
import com.zmlcoder.rpcx.common.RpcxUrl;

public class RpcxRegistryConfig {
	private String id;
	private String host;
	private int port;
	private String protocol;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public RpcxUrl toUrl() {
		RpcxUrl rpcxUrl = new RpcxUrl(RpcxConstants.PROTOCOL_REGISTRY, host, port);
		rpcxUrl.addParam("id", id);
		return rpcxUrl;
	}

}
