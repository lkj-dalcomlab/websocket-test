package com.dalcom.tomcat.binary;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.CloseReason;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/file/binary")
public class BinaryMessage {
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("session Open: " + session.getRequestURI());
		session.setMaxBinaryMessageBufferSize(1024);
	}
	@OnMessage
    public void onMessage(ByteBuffer msg, Session session, boolean last) throws IOException {
        session.getBasicRemote().sendBinary(msg);
        String text = new String(msg.array());
        System.out.println("message : " + text);
        System.out.println("message lenght : " + msg.remaining() + ", last : " + last);
    }
	@OnError
	public void onError(Throwable t) {
		System.out.println(t.getMessage());
	}
}
