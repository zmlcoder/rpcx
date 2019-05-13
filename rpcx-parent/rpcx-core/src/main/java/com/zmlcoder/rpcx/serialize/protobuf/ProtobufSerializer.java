package com.zmlcoder.rpcx.serialize.protobuf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.zmlcoder.rpcx.serialize.IRpcxSerializer;

public class ProtobufSerializer implements IRpcxSerializer {

	public <T> OutputStream serialize(T t) {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream("");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputStream;
	}

	public <T> T deserialize(InputStream inputStream, Class<T> clazz) {
		T a = null;
		return a;
	}
}
