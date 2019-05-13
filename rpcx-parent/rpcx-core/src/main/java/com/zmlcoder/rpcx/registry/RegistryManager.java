package com.zmlcoder.rpcx.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.zmlcoder.rpcx.common.RpcxConstants;
import com.zmlcoder.rpcx.common.RpcxUrl;
import com.zmlcoder.rpcx.config.RpcxConfigManager;
import com.zmlcoder.rpcx.config.RpcxProtocolConfig;
import com.zmlcoder.rpcx.config.RpcxReferenceConfig;
import com.zmlcoder.rpcx.config.RpcxRegistryConfig;
import com.zmlcoder.rpcx.config.RpcxServiceConfig;

public enum RegistryManager {

	INSTANCE;

	private List<IRegistry> registries = new ArrayList<>();

	public void init() {
		Set<RpcxRegistryConfig> registryConfigs = RpcxConfigManager.INSTANCE.getRegistryConfigs();
		for (RpcxRegistryConfig config : registryConfigs) {
			ZookeeperRegistry registry = new ZookeeperRegistry();
			registry.init(config);
			registries.add(registry);
		}
	}

	public void registerProvider(RpcxProtocolConfig protocolConfig, RpcxServiceConfig serviceConfig) {
		IRegistry activeRegistry = getRegistry();
		if (activeRegistry == null) {
			return;
		}

		RpcxUrl url = new RpcxUrl(protocolConfig.getId(), protocolConfig.getHost(), protocolConfig.getPort());
		url.setPath(serviceConfig.getInterface());
		url.addParam(RpcxConstants.KEY_CATAGORY, RpcxConstants.REGISTRY_CATAGORY_PROVIDERS);
		activeRegistry.register(url);
	}

	public void registerConsumer(RpcxProtocolConfig protocolConfig, RpcxReferenceConfig referenceConfig) {
		IRegistry activeRegistry = getRegistry();
		if (activeRegistry == null) {
			return;
		}

		RpcxUrl url = new RpcxUrl(protocolConfig.getId(), protocolConfig.getHost(), protocolConfig.getPort());
		url.setPath(referenceConfig.getInterface());
		url.addParam(RpcxConstants.KEY_CATAGORY, RpcxConstants.REGISTRY_CATAGORY_CONSUMERS);
		activeRegistry.register(url);
	}

	public RpcxProtocolConfig lookupProvider(RpcxReferenceConfig referenceConfig) {
		IRegistry activeRegistry = getRegistry();
		if (activeRegistry == null) {
			return null;
		}

		RpcxRegistryConfig defRegistryConfig = RpcxConfigManager.INSTANCE.getDefaultRegistryConfig();
		RpcxUrl url = defRegistryConfig.toUrl();
		url.addParam(RpcxConstants.KEY_INTERFACE, referenceConfig.getInterface());
		url.addParam(RpcxConstants.KEY_CATAGORY, RpcxConstants.REGISTRY_CATAGORY_PROVIDERS);

		// TODO
		List<RpcxUrl> providerUrls = activeRegistry.lookup(referenceConfig);
		int balanceIndex = new Random().nextInt(providerUrls.size());
		RpcxUrl providerUrl = providerUrls.get(balanceIndex);

		RpcxProtocolConfig protocolConfig = new RpcxProtocolConfig();
		protocolConfig.setId(providerUrl.getProtocol());
		protocolConfig.setHost(providerUrl.getHost());
		protocolConfig.setPort(providerUrl.getPort());
		return protocolConfig;

	}

	public void subscribeUrl(RpcxUrl url) {
		getRegistry().subscribe(url);
	}

	public IRegistry getRegistry() {
		// TODO
		return registries.get(0);
	}

}
