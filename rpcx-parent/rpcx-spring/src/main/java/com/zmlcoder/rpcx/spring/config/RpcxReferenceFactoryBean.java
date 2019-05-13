package com.zmlcoder.rpcx.spring.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.zmlcoder.rpcx.client.RpcxClientManager;
import com.zmlcoder.rpcx.config.RpcxConfigManager;
import com.zmlcoder.rpcx.config.RpcxReferenceConfig;

public class RpcxReferenceFactoryBean extends RpcxReferenceConfig implements FactoryBean<Object>, InitializingBean {

	private Class<?> targetClass;

	@Override
	public void afterPropertiesSet() throws Exception {
		RpcxConfigManager.INSTANCE.addReference(this);
		targetClass = Class.forName(getInterface());
	}

	@Override
	public Object getObject() throws Exception {

		// return proxy object.
		return RpcxClientManager.INSTANCE.createRefService(this);
	}

	@Override
	public Class<?> getObjectType() {
		return targetClass;
	}

}
