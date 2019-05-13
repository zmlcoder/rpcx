package com.zmlcoder.rpcx.common;

import java.io.Serializable;

public class RpcxRequest implements Serializable {

	private String id;
	private String className;
	private String methodName;
	private Class[] parameterTypes;
	private Object[] parammeterValus;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParammeterValus() {
		return parammeterValus;
	}

	public void setParammeterValus(Object[] parammeterValus) {
		this.parammeterValus = parammeterValus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
