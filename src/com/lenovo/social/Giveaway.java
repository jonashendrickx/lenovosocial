package com.lenovo.social;

import java.util.GregorianCalendar;

public class Giveaway {
	private String name;
	private String restrictions;
	private GregorianCalendar expiration_date;
	private String url;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRestrictions() {
		return restrictions;
	}
	
	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}
	
	public GregorianCalendar getExpirationDate() {
		return expiration_date;
	}
	
	public void setExpirationDate(GregorianCalendar expiration_date) {
		this.expiration_date = expiration_date;
	}
	public String getURL() {
		return url;
	}
	public void setURL(String url) {
		this.url = url;
	}
}
