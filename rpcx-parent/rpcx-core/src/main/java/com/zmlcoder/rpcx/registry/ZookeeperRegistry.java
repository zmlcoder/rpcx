package com.zmlcoder.rpcx.registry;

import java.util.List;
import java.util.stream.Collectors;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;

import com.zmlcoder.rpcx.common.RpcxConstants;
import com.zmlcoder.rpcx.common.RpcxUrl;
import com.zmlcoder.rpcx.config.RpcxReferenceConfig;
import com.zmlcoder.rpcx.config.RpcxRegistryConfig;
import com.zmlcoder.rpcx.events.RpcxEvent;
import com.zmlcoder.rpcx.events.RpcxEventManager;

public class ZookeeperRegistry implements IRegistry {

	private static final String SEPERATOR = "/";
	private static final String PATH_BASE = "/rpcx";

	private ZkClient zkClient;

	public void init(RpcxRegistryConfig config) {
		this.zkClient = new ZkClient(config.getHost() + ":" + config.getPort(), Integer.MAX_VALUE);
	}

	@Override
	public List<RpcxUrl> lookup(RpcxReferenceConfig config) {

		String servicePath = buildPath(PATH_BASE, config.getInterface(), RpcxConstants.REGISTRY_CATAGORY_PROVIDERS);
		List<String> services = zkClient.getChildren(servicePath);

		return services.stream().map(urlStr -> RpcxUrl.parse(RpcxUrl.decode(urlStr))).collect(Collectors.toList());

	}

	@Override
	public void register(RpcxUrl url) {

		String category = url.getParam(RpcxConstants.KEY_CATAGORY);

		String categoryPath = buildPath(PATH_BASE, url.getPath(), category);
		if (!zkClient.exists(categoryPath)) {
			zkClient.createPersistent(categoryPath, true);
		}

		String fullPath = buildPath(categoryPath, RpcxUrl.encode(url.toString()));
		if (!zkClient.exists(fullPath)) {
			zkClient.createEphemeral(fullPath);
		}
	}

	@Override
	public void subscribe(RpcxUrl url) {

		String category = url.getParam(RpcxConstants.KEY_CATAGORY);
		String subscribedPath = buildPath(PATH_BASE, url.getPath(), category);

		zkClient.subscribeChildChanges(subscribedPath, new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				RpcxEvent event = new RpcxEvent(url.getParam("event"));
				event.getParams().put("parentPath", parentPath);
				event.getParams().put("currentChilds", currentChilds);
				RpcxEventManager.INSTANCE.publish(event);
			}
		});

	}

	private String buildPath(String... path) {
		return StringUtils.join(path, SEPERATOR);
	}

	@Override
	public List<RpcxUrl> lookup(RpcxUrl url) {

		String interfaceName = url.getParam(RpcxConstants.KEY_INTERFACE);
		String catagory = url.getParam(RpcxConstants.KEY_CATAGORY);

		String registryPath = buildPath(PATH_BASE, interfaceName, catagory);
		List<String> registeredInfo = zkClient.getChildren(registryPath);

		return registeredInfo.stream().map(urlStr -> RpcxUrl.parse(RpcxUrl.decode(urlStr))).collect(Collectors.toList());
	}
}
