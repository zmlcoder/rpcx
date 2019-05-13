package com.zmlcoder.rpcx.spring.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class RpcxBeanDefinitionParser<T> implements BeanDefinitionParser {

	private static final String ATTR_ID = "id";

	private final Class<T> factoryClass;

	public RpcxBeanDefinitionParser(Class<T> factoryClass) {
		this.factoryClass = factoryClass;
	}

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {

		RootBeanDefinition beanDefinition = new RootBeanDefinition(factoryClass);

		NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			beanDefinition.getPropertyValues().add(attr.getNodeName(), attr.getNodeValue());
		}

		String id = element.getAttribute(ATTR_ID);
		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

		return beanDefinition;
	}

}
