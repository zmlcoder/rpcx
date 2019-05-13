package com.zmlcoder.rpcx.spring.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class RpcxServerBeanDefinitionParser implements BeanDefinitionParser {

	private static final String ATTR_ID = "id";
	private static final String ATTR_HOST = "host";
	private static final String ATTR_PORT = "port";

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String id = element.getAttribute(ATTR_ID);
		String host = element.getAttribute(ATTR_HOST);
		String port = element.getAttribute(ATTR_PORT);

		RootBeanDefinition beanDefinition = new RootBeanDefinition(RpcxServerFactoryBean.class);
		beanDefinition.getPropertyValues().add(ATTR_ID, id);
		beanDefinition.getPropertyValues().add(ATTR_HOST, host);
		beanDefinition.getPropertyValues().add(ATTR_PORT, port);
		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

		return beanDefinition;
	}

}
