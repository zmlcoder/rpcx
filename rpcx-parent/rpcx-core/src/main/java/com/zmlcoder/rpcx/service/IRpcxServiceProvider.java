package com.zmlcoder.rpcx.service;

public interface IRpcxServiceProvider {

	Object getService();

	Class<?> getServiceType();

	String getInterface();
}
