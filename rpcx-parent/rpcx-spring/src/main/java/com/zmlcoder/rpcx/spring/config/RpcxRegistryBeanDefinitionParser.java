package com.zmlcoder.rpcx.spring.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class RpcxRegistryBeanDefinitionParser implements BeanDefinitionParser {

	private static final String ATTR_ID = "id";
	private static final String ATTR_HOST = "host";
	private static final String ATTR_PORT = "port";
	private static final String ATTR_PROTOCOL = "protocol";

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String id = element.getAttribute(ATTR_ID);
		String host = element.getAttribute(ATTR_HOST);
		String port = element.getAttribute(ATTR_PORT);
		String protocol = element.getAttribute(ATTR_PROTOCOL);

		RootBeanDefinition beanDefinition = new RootBeanDefinition(RpcxRegistryFactoryBean.class);
		beanDefinition.getPropertyValues().add(ATTR_ID, id);
		beanDefinition.getPropertyValues().add(ATTR_HOST, host);
		beanDefinition.getPropertyValues().add(ATTR_PORT, port);
		beanDefinition.getPropertyValues().add(ATTR_PROTOCOL, protocol);
		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

		return beanDefinition;
	}

}
