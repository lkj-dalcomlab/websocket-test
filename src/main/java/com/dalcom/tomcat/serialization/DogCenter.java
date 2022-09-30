package com.dalcom.tomcat.serialization;

import java.nio.ByteBuffer;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.dalcom.object.Dog;
import com.dalcom.serialization.decoder.DogByteDecoder;
import com.dalcom.serialization.decoder.DogTextDecoder;
import com.dalcom.serialization.decoder.DogTextDecoder1;
import com.dalcom.serialization.encoder.DogTextEncoder;

@ServerEndpoint(value = "/Dog", decoders = {DogTextDecoder.class})
public class DogCenter {
	@OnOpen
	public void onOpen(EndpointConfig config, Session session) {
		System.out.println("open DogCenter : " + session.getRequestURI());
//		for(Class<? extends Decoder> decoder : config.getDecoders()) {
//			System.out.println("Decoder Name : " + decoder.getName());
//		}
//		session.addMessageHandler(Dog.class, (obj, isLast)-> {
//			System.out.println(obj.toString());
//		});
	}
	
//	@OnMessage
//	public void onMessage(Session ssesion, String msg) {
//		System.out.println(msg);
//	}
//	
	@OnMessage
	public void onMessage(Session session, Dog dog) {
		System.out.println(dog.toString());
	}
	
//	@OnMessage
//	public void onMessage(String msg) {
//		
//	}
	
	@OnError
	public void onError(Session session, Throwable e) {
		System.out.println(this.getClass().getName() + " - error : " + e.getMessage());
	}
	
	@OnMessage
	public void onMessage(PongMessage pong) {
		ByteBuffer pongBuf = pong.getApplicationData();
		byte bytes[] = new byte[pongBuf.limit()];
		pongBuf.get(bytes);
		String pongMsg = new String(bytes);
		System.out.println("Server PongMessage : " + pongMsg);
	}
}
