package com.lenovo.social;

import java.util.GregorianCalendar;

public class HangoutEvent extends Event{
	private String event_url = "";
	

	public HangoutEvent() {
	}
	
	public void setEventURL(String url) {
		this.event_url = event_url;
	}
	
	public String getURL() {
		return event_url;
	}
	
	public String toString() {
		return name;
	}
}