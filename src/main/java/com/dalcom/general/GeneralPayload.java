package com.dalcom.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/general")
public class GeneralPayload {
	@OnOpen
	public void open(Session session) {
		System.out.println("/general open : " + session.getRequestURI());
	}
	
	@OnMessage
	public void message(String msg, Session session) {
		System.out.println("/general_StringMessge : " + msg);
	}
//	@OnMessage
//	public void message(Reader rd, Session session) {
//		BufferedReader br = new BufferedReader(rd);
//		try {
//			String msg = br.readLine();
//			System.out.println("BufferedReader: " + msg);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@OnMessage
	public void message(byte[] msg, Session session) {
		System.out.println("/general byteMessage:" + new String(msg));
	}
//	public void message(InputStream is, Session session) {
//		InputStreamReader streamReader = new InputStreamReader(is);
//		char[] chars = new char[15];
//		try {
//			streamReader.read(chars);
//			System.out.println(chars);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
