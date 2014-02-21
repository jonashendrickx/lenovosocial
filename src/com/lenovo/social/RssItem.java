package com.lenovo.social;

import java.util.GregorianCalendar;

public class RssItem {
	private String title;
	private String description;
	private String link;
	private GregorianCalendar pubDate;
	
	public void setTitle(String title) {
		this.title = stripTags(title);
	}
	
	public void setDescription(String description) {
		this.description = stripTags(description);
	}
	
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public void setPublicationDate(GregorianCalendar pubDate) {
		this.pubDate = pubDate;
	}
	
	public GregorianCalendar getPublicationDate() {
		return pubDate;
	}
	
	private String stripTags(String text) {
		text = text.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
		text = text.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
		text = text.replaceFirst("(.*?)\\>", " ");
		text = text.replaceAll("&nbsp;"," ");
		text = text.replaceAll("&amp;"," ");
		text = text.replaceAll("&quot;","'");
		text = text.replaceAll("ndash;","-");
		text = text.replaceAll("nbsp;","");
		text = text.replaceAll("&rsquo;","’");
		return text;
	}
}
