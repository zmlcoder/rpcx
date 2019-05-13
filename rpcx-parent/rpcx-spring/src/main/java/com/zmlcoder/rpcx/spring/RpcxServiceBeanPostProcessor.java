package com.zmlcoder.rpcx.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import com.zmlcoder.rpcx.client.RpcxClient;
import com.zmlcoder.rpcx.spring.annotations.RpcxService;

public class RpcxServiceBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanDefinitionRegistryPostProcessor {

	private RpcxClient client;

	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		RpcxService annotation = beanClass.getAnnotation(RpcxService.class);
		// if (annotation != null) {
		// return client.createService(beanClass);
		// }
		return null;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		// beanFactory.registerSingleton(beanName, singletonObject);
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub

	}

}
