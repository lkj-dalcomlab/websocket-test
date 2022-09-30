package com.dalcom.serialization.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

import com.dalcom.object.Dog;

public class DogTextEncoder implements Encoder.Text<Dog> {

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Dog object) throws EncodeException {
		JSONObject dog = new JSONObject();
		dog.put("name", object.getName());
		dog.put("type", object.getType());
		dog.put("age", object.getAge());
		System.out.println("dog encoded : " + dog.toString());
		return dog.toString();
	}

}
