package com.lenovo.social;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Release {
	private ArrayList<Product> products;
	private GregorianCalendar date;
	
	public Release() {
		products = new ArrayList<Product>(0);
	}
	
	public boolean addProduct(Product product) {
		return products.add(product);
	}
	
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	
	public Product getProduct(int index) {
		return products.get(index);
	}
	
	public int getProductsCount() {
		return products.size();
	}
	
	public GregorianCalendar getDate() {
		return date;
	}
}
