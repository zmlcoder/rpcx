package com.zmlcoder.rpcx.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringExtensionProviderTest {

	public static void main(String[] args) throws Exception {

		ApplicationContext acProvider = new ClassPathXmlApplicationContext("rpcx-spring-provider.xml");

		System.in.read();
	}

}
