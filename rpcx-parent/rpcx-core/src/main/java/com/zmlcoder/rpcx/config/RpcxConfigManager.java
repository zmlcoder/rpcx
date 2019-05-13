package com.zmlcoder.rpcx.config;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.zmlcoder.rpcx.common.RpcxConstants;
import com.zmlcoder.rpcx.common.RpcxUrl;

public enum RpcxConfigManager {

	INSTANCE;

	private Set<RpcxRegistryConfig> registryConfigs = new LinkedHashSet<>();
	private Set<RpcxReferenceConfig> referenceConfigs = new LinkedHashSet<>();
	private Set<RpcxServiceConfig> serviceConfigs = new LinkedHashSet<>();
	private Set<RpcxClientConfig> clientConfigs = new LinkedHashSet<>();
	private List<RpcxProtocolConfig> protocolConfigs = new ArrayList<>();

	private RpcxServerConfig serverConfig;
	private RpcxApplicationConfig applicationConfig;

	///////////////////////////////////////////////////////////////////////////////////////////

	public RpcxProtocolConfig getDefaultProtocolConfig() {
		return protocolConfigs.get(0);
	}

	public RpcxRegistryConfig getDefaultRegistryConfig() {
		// TODO
		return registryConfigs.iterator().next();
	}

	///////////////////////////////////////////////////////////////////////////////////////////

	public void addRegistryConfig(RpcxRegistryConfig config) {
		registryConfigs.add(config);
	}

	public Set<RpcxRegistryConfig> getRegistryConfigs() {
		return registryConfigs;
	}

	public void addReference(RpcxReferenceConfig config) {
		referenceConfigs.add(config);
	}

	public Set<RpcxReferenceConfig> getReferenceConfigs() {
		return referenceConfigs;
	}

	public void addService(RpcxServiceConfig config) {
		serviceConfigs.add(config);
	}

	public Set<RpcxServiceConfig> getServiceConfigs() {
		return serviceConfigs;
	}

	public void setServerConfig(RpcxServerConfig config) {
		this.serverConfig = config;
	}

	public RpcxServerConfig getServerConfig() {
		return serverConfig;
	}

	public void addClientConfig(RpcxClientConfig config) {
		clientConfigs.add(config);
	}

	public Set<RpcxClientConfig> getClientConfigs() {
		return clientConfigs;
	}

	public List<RpcxProtocolConfig> getProtocolConfigs() {
		return protocolConfigs;
	}

	public void addProtocolConfig(RpcxProtocolConfig config) {
		protocolConfigs.add(config);
	}

	public RpcxApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(RpcxApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public RpcxUrl toUrl(RpcxReferenceConfig refConfig) {

		RpcxRegistryConfig defRegistryConfig = RpcxConfigManager.INSTANCE.getDefaultRegistryConfig();
		RpcxProtocolConfig defProtocolConfig = RpcxConfigManager.INSTANCE.getDefaultProtocolConfig();

		String protocol = null;
		String host = null;
		int port = -1;

		do {
			if (defRegistryConfig != null) {
				protocol = RpcxConstants.PROTOCOL_REGISTRY;
				host = defRegistryConfig.getHost();
				port = defRegistryConfig.getPort();
				break;
			}

			if (defProtocolConfig != null) {
				protocol = RpcxConstants.PROTOCOL_RPCX;
				host = defProtocolConfig.getHost();
				port = defProtocolConfig.getPort();
				break;
			}

			break;
		} while (true);

		RpcxUrl rpcxUrl = new RpcxUrl(protocol, host, port);
		rpcxUrl.addParam(RpcxConstants.KEY_ID, refConfig.getId());
		rpcxUrl.addParam(RpcxConstants.KEY_INTERFACE, refConfig.getInterface());
		rpcxUrl.addParam(RpcxConstants.KEY_CATAGORY, RpcxConstants.REGISTRY_CATAGORY_CONSUMERS);
		return rpcxUrl;

	}

}
