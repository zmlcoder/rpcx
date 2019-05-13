package com.zmlcoder.rpcx.config;

public class RpcxReferenceConfig extends RpcxConfig {
	private String id;
	private String interfaceName;
	private String client;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterface() {
		return interfaceName;
	}

	public void setInterface(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
}
