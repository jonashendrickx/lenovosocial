package com.lenovo.social;

import java.util.GregorianCalendar;

public class SocialEvent implements Comparable<SocialEvent> {
	protected String name = "";
	protected GregorianCalendar time = null;
	
	public SocialEvent() {};
	
	public SocialEvent(String name, GregorianCalendar time) {
		setName(name);
		setTime(time);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTime(GregorianCalendar time) {
		this.time = time;
	}
	
	public String getName() {
		return name;
	}
	
	public GregorianCalendar getTime() {
		return time;
	}

	@Override
	public int compareTo(SocialEvent another) {
		return -getTime().compareTo(another.getTime());
	}
}
