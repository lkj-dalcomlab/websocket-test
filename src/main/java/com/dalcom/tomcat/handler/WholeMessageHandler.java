package com.dalcom.tomcat.handler;

import javax.websocket.MessageHandler.Whole;

public class WholeMessageHandler<T> implements Whole<T> {
	private T message = null;
	@Override
	public void onMessage(T message) {
		this.message = message;
		System.out.println(message);
	}
	public T getMessage() {
		return message;
	}
}
