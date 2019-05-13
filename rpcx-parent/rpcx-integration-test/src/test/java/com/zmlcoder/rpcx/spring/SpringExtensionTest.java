package com.zmlcoder.rpcx.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rpcx.test.integration.HelloWorldService;

public class SpringExtensionTest {

	public static void main(String[] args) throws Exception {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("rpcx-spring.xml");
		HelloWorldService service = applicationContext.getBean("helloWorldService", HelloWorldService.class);
		System.out.println(service.say());

		System.in.read();
	}

}
