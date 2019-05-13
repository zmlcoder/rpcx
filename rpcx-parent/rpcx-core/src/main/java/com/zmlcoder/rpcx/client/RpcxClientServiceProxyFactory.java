package com.zmlcoder.rpcx.client;

import java.lang.reflect.Method;

import com.zmlcoder.rpcx.common.RpcxRequest;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class RpcxClientServiceProxyFactory implements MethodInterceptor {

	private String clientId;

	public RpcxClientServiceProxyFactory(String clientId) {
		this.clientId = clientId;
	}

	public <T> T createProxy(Class<T> clazz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return (T) enhancer.create();
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		RpcxRequest request = new RpcxRequest();
		request.setId(String.valueOf(System.nanoTime()));
		request.setClassName(method.getDeclaringClass().getName());
		request.setMethodName(method.getName());
		return RpcxClientManager.INSTANCE.getClient(clientId).send(request);
	}

}
