package com.zmlcoder.rpcx.service;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.zmlcoder.rpcx.common.RpcxRuntimeException;

public enum RpcxServiceManagerOOOld {

	INSTANCE;

	private Map<String, RpcxServiceProvider<?>> serviceRegistry = new ConcurrentHashMap<>();

	private Lock lock = new ReentrantLock();

	public static RpcxServiceManagerOOOld getInstance() {
		return INSTANCE;
	}

	private RpcxServiceManagerOOOld() {
	}

	public void init() {
		// TODO
	}

	public Object get(String className) {

		RpcxServiceProvider<?> provider = serviceRegistry.get(className);
		if (provider == null) {
			throw new RpcxRuntimeException("can not find service privider for:" + className);
		}

		Object service = null;
		SoftReference<?> reference = provider.cachedInstanceRef;
		if (reference != null) {
			service = reference.get();
		}
		if (service == null) {
			try {
				lock.tryLock(5000, TimeUnit.MILLISECONDS);
				service = instantiateProvider(provider);
			} catch (Exception e) {
				throw new RpcxRuntimeException(e);
			} finally {
				lock.unlock();
			}
		}
		return service;
	}

	public <T> void add(Class<T> service, Class<? extends T> serviceImpl) {
		RpcxServiceProvider<T> provider = new RpcxServiceProvider<>(service, serviceImpl);
		serviceRegistry.put(service.getName(), provider);
	}

	public <T> T instantiateProvider(RpcxServiceProvider<T> provider) {
		try {
			T service = null;
			if (provider.cachedInstanceRef != null) {
				service = (T) provider.cachedInstanceRef.get();
			}
			if (service == null) {
				service = provider.implClass.newInstance();
				provider.cachedInstanceRef = new SoftReference<>(service);
			}
			return service;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RpcxRuntimeException("can not instanite service privider for:" + provider.apiClass.getName());
		}
	}

	public static class RpcxServiceProvider<T> {
		private Class<T> apiClass;
		private Class<? extends T> implClass;
		private SoftReference<?> cachedInstanceRef;
		private Map<String, String> attrs = new HashMap<>();

		public RpcxServiceProvider(Class<T> apiClass, Class<? extends T> implClass) {
			this.apiClass = apiClass;
			this.implClass = implClass;
		}
	}
}
