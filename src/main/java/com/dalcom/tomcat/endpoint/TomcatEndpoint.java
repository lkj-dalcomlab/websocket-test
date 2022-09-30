package com.dalcom.tomcat.endpoint;
import javax.websocket.Encoder;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/endpoint")
public class TomcatEndpoint {
	@OnOpen
	public void onOpen() {
		System.out.println("endpoint Open");
	}
	
//	class MessageEncoder implements Encoder.Text<String> {
//		
//	}
}

