package com.dalcom.tomcat.entertainment.dto;

import javax.websocket.Session;

public class User {
	private String id;
	private Session session;
	private User matchUser = null;
	
	public User(String id, Session session) {
		this.id = id;
		this.session = session;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public User getMatchUser() {
		return matchUser;
	}

	public void setMatchUser(User matchUser) {
		this.matchUser = matchUser;
	}
}
