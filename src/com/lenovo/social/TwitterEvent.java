package com.lenovo.social;

public class TwitterEvent extends Event {
	private String hashtags;
	
	public void setHashTags(String hashtags) {
		this.hashtags = hashtags;
	}
	
	public String getHashTags() {
		return hashtags;
	}
}
