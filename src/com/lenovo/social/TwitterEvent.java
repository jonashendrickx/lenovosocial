package com.lenovo.social;

public class TwitterEvent extends Event {
	private final static String twitter_url = "http://www.twitter.com";
	private String hash_tags;
	
	public TwitterEvent() {
		
	}
	
	public void setHashTags(String hash_tags) {
		this.hash_tags = hash_tags;
	}
	
	public String getHashTags() {
		return hash_tags;
	}
	public String getTwitterURL() {
		return twitter_url;
	}
}
