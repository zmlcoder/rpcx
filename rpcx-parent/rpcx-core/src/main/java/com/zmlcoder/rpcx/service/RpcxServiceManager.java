package com.zmlcoder.rpcx.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zmlcoder.rpcx.common.RpcxRuntimeException;

public enum RpcxServiceManager {

	INSTANCE;

	public static RpcxServiceManager getInstance() {
		return INSTANCE;
	}

	private Map<String, IRpcxServiceProvider> serviceRegistry = new ConcurrentHashMap<>();

	public void init() {
		// TODO
	}

	public Object get(String interfaceName) {

		IRpcxServiceProvider provider = serviceRegistry.get(interfaceName);
		if (provider == null) {
			throw new RpcxRuntimeException("can not find service privider for:" + interfaceName);
		}

		return provider.getService();
	}

	public void add(IRpcxServiceProvider provider) {
		serviceRegistry.put(provider.getInterface(), provider);
	}

}
