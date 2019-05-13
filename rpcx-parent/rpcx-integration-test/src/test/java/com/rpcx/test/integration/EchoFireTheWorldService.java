package com.rpcx.test.integration;

import javax.annotation.PostConstruct;

public class EchoFireTheWorldService implements HelloWorldService {

	@Override
	@PostConstruct
	public String say() {
		return "Fire The World!";
	}

}
