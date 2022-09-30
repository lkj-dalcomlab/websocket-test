package com.dalcom.tomcat.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
@ServerEndpoint("/chat/text")
public class ServerChat3 {
	
	@OnOpen
	public void chatOpen(Session session) {
		System.out.println("chatTextOpen: " + session.getRequestURI() + " - id: " + session.getId());
		session.setMaxBinaryMessageBufferSize(1024);
		int maxBinarySize = session.getMaxBinaryMessageBufferSize();
		int maxTextSize = session.getMaxTextMessageBufferSize();
		System.out.println("maxBinarySize : " + maxBinarySize);
		System.out.println("maxTextSize : " + maxTextSize);
    }

    @OnMessage//(maxMessageSize = 1024)
	public void chatMessage(String message, Session session, boolean last) throws IOException {
    	System.out.println("message : " + message);
    	System.out.println("message lenght : " + message.length());
    }
    
//    @OnMessage
//    public void charMessage(String message) {
//    	
//    }

    @OnClose
	public void chatClose(CloseReason reason, Session session) {
		System.out.println("Closing chat: " + reason.getReasonPhrase());
    }
    
    @OnError
    public void chatError(Session session, Throwable e) {
    	System.out.println("chat Error[" + session.getRequestURI() + "] " + e.getMessage());
    	try {
			session.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

}
