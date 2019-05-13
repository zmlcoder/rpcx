package com.zmlcoder.rpcx.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcxTaskExecutor {

	private static RpcxTaskExecutor INSTANCE = new RpcxTaskExecutor();

	private ExecutorService executorService;

	private RpcxTaskExecutor() {
		executorService = Executors.newCachedThreadPool();
	}

	public void submit(RpcxTask task) {
		executorService.submit(task);
	}

	public static RpcxTaskExecutor getInstance() {
		return INSTANCE;
	}
}
