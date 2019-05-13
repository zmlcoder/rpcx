package com.rpcx.test.integration;

public abstract class AbstractAuthService {

	public String getVersion() {
		return "V1.0.0";
	}

	public abstract String login(String name, String psw);

	public abstract boolean check(String token);
}
