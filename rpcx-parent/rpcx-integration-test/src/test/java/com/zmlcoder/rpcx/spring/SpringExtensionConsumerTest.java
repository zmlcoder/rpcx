package com.zmlcoder.rpcx.spring;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rpcx.test.integration.HelloWorldService;

public class SpringExtensionConsumerTest {

	public static void main(String[] args) throws Exception {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("rpcx-spring-consumer.xml");
		HelloWorldService service = applicationContext.getBean("helloWorldService", HelloWorldService.class);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				try {

					System.err.println(service.say());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, 0, 10000);

		System.in.read();
	}

}
