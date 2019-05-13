package com.zmlcoder.rpcx.spring;

import java.util.Set;

import com.zmlcoder.rpcx.common.RpcxException;
import com.zmlcoder.rpcx.config.RpcxConfigManager;
import com.zmlcoder.rpcx.config.RpcxProtocolConfig;
import com.zmlcoder.rpcx.config.RpcxServiceConfig;
import com.zmlcoder.rpcx.registry.RegistryManager;
import com.zmlcoder.rpcx.server.RpcxServer;

public class RpcxSpringStarter {

	public void start() {
		try {

			RegistryManager.INSTANCE.init();

			Set<RpcxServiceConfig> serviceConfigs = RpcxConfigManager.INSTANCE.getServiceConfigs();
			if (serviceConfigs.size() > 0) {

				RpcxProtocolConfig protocolConfig = RpcxConfigManager.INSTANCE.getDefaultProtocolConfig();
				RpcxServer server = new RpcxServer(protocolConfig.getHost(), protocolConfig.getPort());
				server.start();

				for (RpcxServiceConfig serviceConfig : serviceConfigs) {
					RegistryManager.INSTANCE.registerProvider(protocolConfig, serviceConfig);
				}

			}
		} catch (RpcxException e) {
			e.printStackTrace();
		}
	}
}
