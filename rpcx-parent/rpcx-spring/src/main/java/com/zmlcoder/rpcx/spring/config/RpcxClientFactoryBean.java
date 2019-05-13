package com.zmlcoder.rpcx.spring.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zmlcoder.rpcx.config.RpcxClientConfig;

public class RpcxClientFactoryBean extends RpcxClientConfig implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;

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

		// RpcxConfigManager.INSTANCE.addClientConfig(this);
		//
		// RpcxClientManager.INSTANCE.addClient(getId(), getHost(), getPort());
		//
		// if (applicationContext.getBeansOfType(RpcxServerFactoryBean.class).size() >
		// 0) {
		// // wait until server started.
		// RpcxEventManager.INSTANCE.subscribe(RpcxEventTypes.EVENT_SERVER_CONNECTED,
		// (event) -> {
		// initClient();
		// });
		// } else {
		// initClient();
		// }
	}

	// private void initClient() {
	// try {
	// RpcxClientManager.INSTANCE.initClient(getId());
	// } catch (RpcxException e) {
	// e.printStackTrace();
	// }
	// }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
