package com.dalcom.serialization.decoder;

import java.io.IOException;
import java.io.Reader;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

//import org.apache.maven.surefire.shade.org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.dalcom.object.Dog;

public class DogTextDecoder1 implements Decoder.TextStream<Dog> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dog decode(Reader s) throws DecodeException, IOException {
		JSONObject dog = new JSONObject(s);
		return new Dog(dog.getString("name")+"1", dog.getString("type")+"1", dog.getInt("age"));
	}	
}
