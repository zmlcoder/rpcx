package com.zmlcoder.rpcx.spring.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class RpcxNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("reference", new RpcxReferenceBeanDefinitionParser());
		registerBeanDefinitionParser("service", new RpcxServiceBeanDefinitionParser());
		registerBeanDefinitionParser("server", new RpcxServerBeanDefinitionParser());
		registerBeanDefinitionParser("client", new RpcxClientBeanDefinitionParser());
		registerBeanDefinitionParser("registry", new RpcxRegistryBeanDefinitionParser());

		registerBeanDefinitionParser("protocol", new RpcxBeanDefinitionParser<>(RpcxProtocolFactoryBean.class));
		registerBeanDefinitionParser("application", new RpcxBeanDefinitionParser<>(RpcxApplicationFactoryBean.class));
	}

}
