package com.dalcom.serialization.decoder;

import java.nio.ByteBuffer;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

import com.dalcom.object.Dog;

public class DogByteDecoder implements Decoder.Binary<Dog> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dog decode(ByteBuffer bytes) throws DecodeException {
		JSONObject dog = new JSONObject(new String(bytes.array()));
		return new Dog(dog.getString("name"), dog.getString("type"), dog.getInt("age"));
	}

	@Override
	public boolean willDecode(ByteBuffer bytes) {
		// TODO Auto-generated method stub
		return true;
	}

}
