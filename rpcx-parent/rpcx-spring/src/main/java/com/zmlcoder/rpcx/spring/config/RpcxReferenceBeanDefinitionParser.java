package com.zmlcoder.rpcx.spring.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class RpcxReferenceBeanDefinitionParser implements BeanDefinitionParser {

	private static final String ATTR_ID = "id";
	private static final String ATTR_INTERFACE = "interface";
	private static final String ATTR_CLIENT = "client";

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String id = element.getAttribute(ATTR_ID);
		String interfaceName = element.getAttribute(ATTR_INTERFACE);
		// String client = element.getAttribute(ATTR_CLIENT);

		RootBeanDefinition beanDefinition = new RootBeanDefinition(RpcxReferenceFactoryBean.class);
		beanDefinition.getPropertyValues().add(ATTR_ID, id);
		beanDefinition.getPropertyValues().add(ATTR_INTERFACE, interfaceName);
		// beanDefinition.getPropertyValues().add(ATTR_CLIENT, client);

		// beanDefinition.setDependsOn(client);

		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

		return beanDefinition;
	}

}
