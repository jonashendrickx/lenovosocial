package com.lenovo.social;

public class GoogleEvent extends Event{
	private String event_url = "";
	private String recording_url = "";

	public GoogleEvent() {
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
	
	public String toString() {
		return name;
	}
}