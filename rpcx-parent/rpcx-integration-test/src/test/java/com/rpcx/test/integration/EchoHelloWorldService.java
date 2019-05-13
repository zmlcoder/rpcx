package com.rpcx.test.integration;

import javax.annotation.PostConstruct;

public class EchoHelloWorldService implements HelloWorldService {

	@Override
	@PostConstruct
	public String say() {
		return "Hello World!";
	}

}
