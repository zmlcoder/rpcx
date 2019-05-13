package com.zmlcoder.rpcx.events;

import java.util.HashMap;
import java.util.Map;

public class RpcxEvent {
	private String type = "";
	private Map<String, Object> params = new HashMap<>();

	public RpcxEvent(String eventType) {
		type = eventType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
