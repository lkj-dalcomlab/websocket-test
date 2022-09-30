package com.dalcom.tomcat.sync;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/sync")
public class sync {
	@OnOpen
	public void onOpen(Session session) throws ExecutionException {
		System.out.println("open:" + session.getRequestURI());
		
//		RemoteEndpoint.Async asyncRemote = session.getAsyncRemote();
//		Future<Void> future = asyncRemote.sendText("aaa");
		
		RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
//		try {
//			basicRemote.sendText("hello");
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		File file = new File("/Users/clipsoft/download/asyncBasicTest.txt");
		Scanner textReader;
		try {
			textReader = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
//		boolean isLast = !textReader.hasNext();
		StringBuilder readText = new StringBuilder();
		while(textReader.hasNext()) {
//			try {
			readText.append(textReader.nextLine());
//				basicRemote.sendText(readLine, false);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		try {
			basicRemote.sendText(readText.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		FutureTask<V>
//		future.get();
//		Future<Void> future1 = session.getAsyncRemote().sendText("bb");
//		future1.get();
	}
	@OnMessage
	public void onMessage(String msg) {
		System.out.println(msg);
	}

//	@OnMessage
//	public void onMessage2(String msg, Session session) {
////		System.out.println("start sleep:" + msg);
////		Thread.sleep(3000);
////		System.out.println(msg);
////		System.out.println("end sleep:" + msg);
//	}

	
	
	@OnMessage
	public void onMessage(byte[] msg, Session session) {
		
	}
	
	@OnClose
	public void onClose(CloseReason reason, Session session) {
		System.out.println(reason.getReasonPhrase());
	}
}
