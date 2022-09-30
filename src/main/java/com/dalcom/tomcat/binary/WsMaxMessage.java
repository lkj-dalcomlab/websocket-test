package com.dalcom.tomcat.binary;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import javax.websocket.CloseReason;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.RemoteEndpoint;

@ServerEndpoint("/binary")
public class WsMaxMessage {
	private BufferedOutputStream bos = null;
	@OnOpen
	public void open(Session session) {
		System.out.println("session Open: " + session.getRequestURI());
		int maxBinarySize = session.getMaxBinaryMessageBufferSize();
		int maxTextSize = session.getMaxTextMessageBufferSize();
		System.out.println("maxBinarySize : " + maxBinarySize);
		System.out.println("maxTextSize : " + maxTextSize);
//		session.getMessageHandlers().add(new MessageHandler.Whole<ByteBuffer>() {
//			public void onMessage(ByteBuffer message) {
//				System.out.println(message);
//			};
//		});
	}
	
//	@OnMessage
//	public void message(String msg) {
//		System.out.println("msg : " + msg);
//	}
	
//	@OnMessage
//	public void stream(ByteBuffer buf, Session session) {
//		System.out.println(buf.toString());
//		
//	}
	@OnMessage
	public void message(String msg, Session session) throws IOException { // check throws catch
		if(msg.indexOf("end") == 0) {
			System.out.println("file end, close session.");
			closeBOS();
			return;
		}
		int fnStIndex = msg.indexOf("fn:") + 3;
		if(0 < fnStIndex) {
	        String filePath = "/Users/sky/download/" + msg.substring(fnStIndex);
	        System.out.println(filePath);
	        File file = new File(filePath);
	        bos = new BufferedOutputStream(new FileOutputStream(file));
		}
		else if(fnStIndex == 0 || fnStIndex > msg.length()) {
			session.getAsyncRemote().sendText("The file format is wrong.`fn:[filename]`");
			session.close();
		}
	}
//	@OnMessage
//	public void message(byte[] msg, Session session, boolean isLast) throws IOException {
//		bos.write(msg);
//		System.out.println(session.getMaxBinaryMessageBufferSize() + ", " + isLast);
//	}
	@OnClose
	public void closes(CloseReason reason, Session session) throws IOException {
		System.out.println("Closing session: " + reason.getReasonPhrase());
		closeBOS();
	}
	@OnError
	public void error(Session session, Throwable e) {
		System.out.println("error[" + session.getRequestURI() + "] " + e.getMessage());
	}
	
	private void closeBOS() throws IOException {
		if(bos != null) {
			bos.flush();
			bos.close();
		}
		bos = null;
	}
}
