package com.dalcom.client;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ClientEndpoint
@ServerEndpoint("/client/{userId}")
class ServerClient {
	@OnOpen
	public void chatOpen(Session session) {
		System.out.println("clientOpen: " + session.getRequestURI() + " - id: " + session.getId());
    }

    @OnMessage
	public void chatMessage(String message, Session session) throws IOException {
    	String chatMessage = "msg[" + session.getId() + "]: " + message;
    	System.out.println(chatMessage);
    	session.getBasicRemote().sendText("return :" + chatMessage);
    }

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
