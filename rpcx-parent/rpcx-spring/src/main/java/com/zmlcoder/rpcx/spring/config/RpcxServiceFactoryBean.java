package com.zmlcoder.rpcx.spring.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zmlcoder.rpcx.config.RpcxConfigManager;
import com.zmlcoder.rpcx.config.RpcxServiceConfig;
import com.zmlcoder.rpcx.service.RpcxServiceManager;
import com.zmlcoder.rpcx.service.SingletonRpcxServiceProvider;

public class RpcxServiceFactoryBean extends RpcxServiceConfig implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		RpcxConfigManager.INSTANCE.addService(this);

		Class<?> interfaceClass = Class.forName(getInterface());
		Object serviceImpl = applicationContext.getBean(getRef());
		RpcxServiceManager.INSTANCE.add(new SingletonRpcxServiceProvider(interfaceClass, serviceImpl));
	}

	@Override
	public Object getObject() throws Exception {
		return this;
	}

	@Override
	public Class<?> getObjectType() {
		return getClass();
	}

}
