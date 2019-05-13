package com.zmlcoder.rpcx.events;

public enum RpcxEventManager {
	INSTANCE;

	private RpcxEventRouter appRouter = new RpcxEventRouter();

	public void init() {

	}

	public void publish(String eventType) {
		RpcxEvent event = new RpcxEvent(eventType);
		appRouter.publish(event);
	}

	public void publish(RpcxEvent event) {
		appRouter.publish(event);
	}

	public void subscribe(String eventType, IRpcxEventCallback callback) {
		appRouter.subscribe(eventType, callback);
	}
}
