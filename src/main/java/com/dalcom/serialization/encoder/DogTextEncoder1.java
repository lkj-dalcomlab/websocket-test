package com.dalcom.serialization.encoder;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

import com.dalcom.object.Dog;

public class DogTextEncoder1 implements Encoder.TextStream<Dog> {


	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public Reader encode(Dog object) throws EncodeException {
		JSONObject dog = new JSONObject();
		dog.put("name", object.getName());
		dog.put("type", object.getType());
		dog.put("age", object.getAge());
		System.out.println("dog encoded : " + dog.toString());
		return new StringReader(dog.toString());
	}

	@Override
	public void encode(Dog object, Writer writer) throws EncodeException, IOException {
		JSONObject dog = new JSONObject();
		dog.put("name", object.getName());
		dog.put("type", object.getType());
		dog.put("age", object.getAge());
		writer.write(dog.toString());
	}

}
