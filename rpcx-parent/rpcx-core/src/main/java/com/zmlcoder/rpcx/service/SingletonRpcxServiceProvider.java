package com.zmlcoder.rpcx.service;

public class SingletonRpcxServiceProvider implements IRpcxServiceProvider {

	private Class<?> interfaceClass;
	private Object instance;

	public SingletonRpcxServiceProvider(Class<?> interfaceClass, Object instance) {
		this.interfaceClass = interfaceClass;
		this.instance = instance;
	}

	@Override
	public Object getService() {
		return instance;
	}

	@Override
	public Class<?> getServiceType() {
		return interfaceClass;
	}

	@Override
	public String getInterface() {
		return interfaceClass.getName();
	}

}
