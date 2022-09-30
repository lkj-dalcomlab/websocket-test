package com.dalcom.tomcat.entertainment.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class MatchRoom {
	enum State {
		NONE,
		READY,
		START
	}
	private State state = State.NONE;
	private User user1 = null;
	private User user2 = null;
	private List<User> lookers = new ArrayList<>();
	private History history = new History();
	
	public void addUser(User user) {
		if (user1 == null) {
			user1 = user;
		} else if (user2 == null) {
			user2 = user;
		} else {
			lookers.add(user);
		}
	}
	
	public List<User> getLookers() {
		return lookers;
	}
	
	public boolean isReady() {
		if (state == State.NONE) {
			if (user1 != null && user2 != null) {
				state = State.READY;
			}
		}
		return state == State.READY;
	}
	
	public boolean isStart() {
		return state == State.START;
	}
	
	public void start() throws IOException {
		sendReadyMessage(user1, "black", true);
		sendReadyMessage(user2, "white", false);
		user1.setMatchUser(user2);
		user2.setMatchUser(user1);
		state = State.START;
	}
	
	private void sendReadyMessage(User user, String color, boolean first) throws IOException {
		JSONObject omok = new JSONObject();
		omok.put("state", "ready");
		omok.put("turnId", user.getId());
		omok.put("turnColor", color);
		omok.put("turnOff", first);    		
		user.getSession().getBasicRemote().sendText(omok.toString());
	}
	
	public History getHistory() {
		return history;
	}
}
