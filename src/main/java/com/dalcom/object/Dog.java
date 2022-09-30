package com.dalcom.object;

public class Dog {
	public Dog(String name, String type, int age) {
		this.name = name;
		this.type = type;
		this.age = age;
	}
	private String name;
	private String type;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("name : ").append(name)
		.append("\ntype : ").append(type)
		.append("\nage : ").append(age);
		return s.toString();
	}
}
