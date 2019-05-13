package com.zmlcoder.rpcx.spring.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.zmlcoder.rpcx.config.RpcxConfigManager;
import com.zmlcoder.rpcx.config.RpcxServerConfig;

public class RpcxServerFactoryBean extends RpcxServerConfig implements FactoryBean<Object>, InitializingBean {

	@Override
	public Object getObject() throws Exception {
		return this;
	}

	@Override
	public Class<?> getObjectType() {
		return getClass();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		RpcxConfigManager.INSTANCE.setServerConfig(this);
	}

}
