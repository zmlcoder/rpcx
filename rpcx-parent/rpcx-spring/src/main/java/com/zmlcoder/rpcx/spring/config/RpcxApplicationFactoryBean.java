package com.zmlcoder.rpcx.spring.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.zmlcoder.rpcx.config.RpcxApplicationConfig;
import com.zmlcoder.rpcx.config.RpcxConfigManager;
import com.zmlcoder.rpcx.spring.RpcxSpringStarter;

public class RpcxApplicationFactoryBean extends RpcxApplicationConfig implements FactoryBean<Object>, InitializingBean, ApplicationListener<ContextRefreshedEvent> {

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
		RpcxConfigManager.INSTANCE.setApplicationConfig(this);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		new RpcxSpringStarter().start();
	}

}
