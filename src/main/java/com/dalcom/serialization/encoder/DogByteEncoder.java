package com.dalcom.serialization.encoder;

import java.nio.ByteBuffer;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

import com.dalcom.object.Dog;

public class DogByteEncoder implements Encoder.Binary<Dog> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ByteBuffer encode(Dog object) throws EncodeException {
		JSONObject dog = new JSONObject();
		dog.put("name", object.getName());
		dog.put("type", object.getType());
		dog.put("age", object.getAge());
		System.out.println("dog encoded : " + dog.toString());
		return ByteBuffer.wrap(dog.toString().getBytes());
	}

}
