package com.lenovo.social;

import java.util.GregorianCalendar;

public class TwitterEvent extends SocialEvent {
	private String hashtags;
	
	public TwitterEvent() {};
	
	public TwitterEvent(String name, GregorianCalendar time, String hashtags) {
		super(name, time);
		setHashTags(hashtags);
	}
	
	public void setHashTags(String hashtags) {
		this.hashtags = hashtags;
	}
	
	public String getHashTags() {
		return hashtags;
	}
}
