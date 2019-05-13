package com.zmlcoder.rpcx.common;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RpcxCallback {

	private RpcxRequest request;
	private RpcxResponse response;

	private Lock lock;
	private Condition finishCondition;

	public RpcxCallback() {

	}

	public void begin(RpcxRequest request) {
		this.request = request;
		lock = new ReentrantLock();
		finishCondition = lock.newCondition();
	}

	public void end(RpcxResponse response) {
		try {
			lock.lock();
			this.response = response;
			finishCondition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public Object get() {

		if (response != null) {
			return getResult();
		}

		try {
			lock.lock();
			finishCondition.await(3000, TimeUnit.MILLISECONDS);
			return getResult();
		} catch (InterruptedException e) {
			throw new RpcxRuntimeException(e);
		} finally {
			lock.unlock();
		}
	}

	private Object getResult() {

		if (response != null && "200".equals(response.getStatus())) {
			return response.getResult();
		}

		throw new RpcxRuntimeException("Failed call requst:" + request.getId());
	}
}
