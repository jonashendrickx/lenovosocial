package com.lenovo.social;

import java.util.GregorianCalendar;

public class GoogleEvent extends SocialEvent{
	private String event_url = "";
	private String recording_url = "";

	public GoogleEvent() {};
	
	public GoogleEvent(String name, GregorianCalendar time, String event_url, String recording_url) {
		super(name, time);
		setEventURL(event_url);
		setRecordingURL(recording_url);
	}
	
	public void setEventURL(String event_url) {
		this.event_url = event_url;
	}
	
	public void setRecordingURL(String recording_url) {
		this.recording_url = recording_url;
	}
	
	public String getEventURL() {
		return event_url;
	}
	
	public String getRecordingURL() {
		return recording_url;
	}
	
	@Override
	public String toString() {
		return name;
	}
}