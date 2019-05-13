package com.zmlcoder.rpcx.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RpcxEventRouter {

	private Map<String, List<IRpcxEventCallback>> callbacksMap = new HashMap<>();

	public void subscribe(String eventType, IRpcxEventCallback callback) {

		List<IRpcxEventCallback> callbacks = callbacksMap.get(eventType);
		if (callbacks == null) {
			callbacks = new ArrayList<>();
			callbacksMap.put(eventType, callbacks);
		}
		callbacks.add(callback);
	}

	public void publish(RpcxEvent event) {

		List<IRpcxEventCallback> callbacks = callbacksMap.get(event.getType());
		if (callbacks != null) {
			for (IRpcxEventCallback callback : callbacks) {
				callback.callback(event);
			}
		}
	}
}
