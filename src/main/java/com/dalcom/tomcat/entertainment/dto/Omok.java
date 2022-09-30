package com.dalcom.tomcat.entertainment.dto;

public class Omok {
	private String state;
	private int x;
	private int y;
	private boolean turnOff;
	private String turnColor;
	
	public Omok(String state, int x, int y, String turnColor) {
		this.state = state;
		this.x = x;
		this.y = y;
		this.turnColor = turnColor;
	}
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isTurnOff() {
		return turnOff;
	}
	public void setTurnOff(boolean turnOff) {
		this.turnOff = turnOff;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTurnColor() {
		return turnColor;
	}
	public void setTurnColor(String turnColor) {
		this.turnColor = turnColor;
	}
}
