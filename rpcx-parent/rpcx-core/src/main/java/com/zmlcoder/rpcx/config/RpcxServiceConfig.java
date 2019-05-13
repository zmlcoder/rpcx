package com.zmlcoder.rpcx.config;

public class RpcxServiceConfig {
	private String id;
	private String interfaceName;
	private String ref;

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

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
}
