package com.zmlcoder.rpcx.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public final class RpcxUrl {

	private String protocol;

	private String username;

	private String password;

	private String host;

	private int port = -1;

	private String path;

	private final Map<String, String> params = new HashMap<>();

	public RpcxUrl(String protocol, String host, int port) {
		this(protocol, host, port, null);
	}

	public RpcxUrl(String protocol, String host, int port, String path) {
		this(protocol, host, port, path, null, null);
	}

	public RpcxUrl(String protocol, String host, int port, String path, String username, String password) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.path = path;
		this.username = username;
		this.password = password;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public String getParam(String key) {
		return params.get(key);
	}

	public String removeParam(String key) {
		return params.remove(key);
	}

	public String addParam(String key, String value) {
		return params.put(key, value);
	}

	@Override
	public String toString() {

		// protocol://username:password@host:port/path?param1=value1&param2=value2
		//
		StringBuilder sb = new StringBuilder(protocol);
		sb.append("://");

		// username:password
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			sb.append(username).append(":").append(password).append("@");
		}

		// host:port
		sb.append(host);
		if (port > 0) {
			sb.append(":").append(port);
		}

		// path
		if (StringUtils.isNotBlank(path)) {
			sb.append("/").append(path);
		}

		// parameters
		boolean isFirst = true;
		for (Entry<String, String> entry : params.entrySet()) {
			if (isFirst) {
				sb.append("?");
				isFirst = false;
			} else {
				sb.append("&");
			}
			sb.append(entry.getKey()).append("=").append(entry.getValue());
		}

		return sb.toString();
	}

	public static RpcxUrl parse(String urlStr) {

		int index = -1;
		int nextIndex = -1;

		// protocol
		nextIndex = urlStr.indexOf("://");
		String protocol = urlStr.substring(0, nextIndex);
		index = nextIndex + 3;

		// username:password
		String username = StringUtils.EMPTY;
		String password = StringUtils.EMPTY;
		nextIndex = urlStr.indexOf("@", index);
		if (nextIndex >= 0) {
			String[] ss = urlStr.substring(index, nextIndex).split(":");
			username = ss[0];
			password = ss[1];
			index = nextIndex + 1;
		}

		// host:port
		String host = StringUtils.EMPTY;
		int port = -1;
		if (urlStr.length() > index) {
			nextIndex = urlStr.indexOf("/", index);
			if (nextIndex < 0) {
				nextIndex = urlStr.length();
			}
			String[] hp = urlStr.substring(index, nextIndex).split(":");
			host = hp[0];
			if (hp.length > 1) {
				port = Integer.parseInt(hp[1]);
			}
			index = nextIndex + 1;
		}

		// path
		String path = StringUtils.EMPTY;
		if (urlStr.length() > index) {
			nextIndex = urlStr.indexOf("?", index);
			if (nextIndex < 0) {
				nextIndex = urlStr.length();
			}
			path = urlStr.substring(index, nextIndex);
			index = nextIndex + 1;
		}

		RpcxUrl rpcxUrl = new RpcxUrl(protocol, host, port, path);
		rpcxUrl.setUsername(username);
		rpcxUrl.setPassword(password);

		// parameters
		if (urlStr.length() > index) {
			String[] params = urlStr.substring(index).split("&");
			if (params.length > 0) {
				for (String param : params) {
					String[] ss = param.split("=");
					rpcxUrl.addParam(ss[0], ss[1]);
				}
			}
		}

		return rpcxUrl;
	}

	public static String encode(String value) {
		if (StringUtils.isBlank(value)) {
			return StringUtils.EMPTY;
		}
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String decode(String value) {
		if (StringUtils.isBlank(value)) {
			return StringUtils.EMPTY;
		}
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
