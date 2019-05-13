package com.zmlcoder.rpcx.client;

import java.util.concurrent.ConcurrentHashMap;

import com.zmlcoder.rpcx.common.RpcxConstants;
import com.zmlcoder.rpcx.common.RpcxException;
import com.zmlcoder.rpcx.common.RpcxRuntimeException;
import com.zmlcoder.rpcx.common.RpcxUrl;
import com.zmlcoder.rpcx.config.RpcxConfigManager;
import com.zmlcoder.rpcx.config.RpcxProtocolConfig;
import com.zmlcoder.rpcx.config.RpcxReferenceConfig;
import com.zmlcoder.rpcx.config.RpcxRegistryConfig;
import com.zmlcoder.rpcx.events.RpcxEventManager;
import com.zmlcoder.rpcx.registry.RegistryManager;

import io.netty.channel.nio.NioEventLoopGroup;

public enum RpcxClientManager {

	INSTANCE;

	ConcurrentHashMap<String, RpcxClient> rpcxClients = new ConcurrentHashMap<>();
	NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

	public RpcxClient getClient(String id) {
		return rpcxClients.get(id);
	}

	public RpcxClient getClient(RpcxReferenceConfig refConfig) {

		RpcxClient client = rpcxClients.get(refConfig.getId());
		if (client != null) {
			return client;
		}

		client = openClient(refConfig);
		rpcxClients.put(refConfig.getId(), client);

		RpcxRegistryConfig defRegistryConfig = RpcxConfigManager.INSTANCE.getDefaultRegistryConfig();
		RpcxUrl url = new RpcxUrl(RpcxConstants.PROTOCOL_PROVIDER, defRegistryConfig.getHost(), defRegistryConfig.getPort());
		url.setPath(refConfig.getInterface());
		url.addParam(RpcxConstants.KEY_CATAGORY, RpcxConstants.REGISTRY_CATAGORY_PROVIDERS);
		url.addParam("event", refConfig.getId());

		RpcxEventManager.INSTANCE.subscribe(refConfig.getId(), (event) -> {
			RpcxClient newClient = openClient(refConfig);
			rpcxClients.put(refConfig.getId(), newClient);
		});

		RegistryManager.INSTANCE.subscribeUrl(url);

		return client;

	}

	private RpcxClient openClient(RpcxReferenceConfig refConfig) {
		RpcxProtocolConfig clientConfig = RegistryManager.INSTANCE.lookupProvider(refConfig);
		RpcxClient client = new RpcxClient(eventLoopGroup, clientConfig.getHost(), clientConfig.getPort());
		client.init();
		try {
			client.connect();
		} catch (RpcxException e) {
			e.printStackTrace();
		}
		RegistryManager.INSTANCE.registerConsumer(clientConfig, refConfig);

		return client;
	}

	public Object createRefService(RpcxReferenceConfig refConfig) {

		getClient(refConfig);
		Class<?> targetClass = null;
		try {
			targetClass = Class.forName(refConfig.getInterface());
		} catch (ClassNotFoundException e) {
			throw new RpcxRuntimeException(e.getMessage(), e);
		}
		return new RpcxClientServiceProxyFactory(refConfig.getId()).createProxy(targetClass);
	}

	public <T> T createRefService(Class<T> interfaceClass) {

		RpcxReferenceConfig refConfig = new RpcxReferenceConfig();
		refConfig.setId(interfaceClass.getName());
		refConfig.setInterface(interfaceClass.getName());

		return (T) createRefService(refConfig);
	}

}
