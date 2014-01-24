package com.lenovo.social;

import java.util.GregorianCalendar;

public class Event implements Comparable<Event> {
	protected String name = "";
	protected GregorianCalendar time = null;
	
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
	public int compareTo(Event another) {
		return -getTime().compareTo(another.getTime());
	}
}
