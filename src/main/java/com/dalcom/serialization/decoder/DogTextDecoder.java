package com.dalcom.serialization.decoder;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

import com.dalcom.object.Dog;

public class DogTextDecoder implements Decoder.Text<Dog> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dog decode(String s) throws DecodeException {
		JSONObject dog = new JSONObject(s);
		return new Dog(dog.getString("name"), dog.getString("type"), dog.getInt("age"));
	}

	@Override
	public boolean willDecode(String s) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
