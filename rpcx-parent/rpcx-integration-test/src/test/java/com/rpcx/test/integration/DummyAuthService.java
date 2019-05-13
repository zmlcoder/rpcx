package com.rpcx.test.integration;

public class DummyAuthService extends AbstractAuthService {

	@Override
	public boolean check(String token) {
		return false;
	}

	@Override
	public String login(String name, String psw) {
		String token = name + "_" + psw;
		return token;
	}

}
