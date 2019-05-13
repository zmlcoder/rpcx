package com.zmlcoder.rpcx.registry;

import java.util.List;

import com.zmlcoder.rpcx.common.RpcxUrl;
import com.zmlcoder.rpcx.config.RpcxReferenceConfig;
import com.zmlcoder.rpcx.config.RpcxRegistryConfig;

public interface IRegistry {

	void init(RpcxRegistryConfig config);

	List<RpcxUrl> lookup(RpcxReferenceConfig config);

	List<RpcxUrl> lookup(RpcxUrl url);

	void register(RpcxUrl url);

	void subscribe(RpcxUrl url);
}
