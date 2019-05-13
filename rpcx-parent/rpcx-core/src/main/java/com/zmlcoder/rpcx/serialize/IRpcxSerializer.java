package com.zmlcoder.rpcx.serialize;

import java.io.InputStream;
import java.io.OutputStream;

public interface IRpcxSerializer {

	<T> OutputStream serialize(T t);

	<T> T deserialize(InputStream inputStream, Class<T> clazz);
}
