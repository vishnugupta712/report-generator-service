package com.natwest.code.model;

import java.io.Serializable;

public class OutputModel implements Serializable{

	private static final long serialVersionUID = 7487369222096178607L;
	private String country;
	private String gender;
	private double amount;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public OutputModel(String country, String gender, double amount) {
		super();
		this.country = country;
		this.gender = gender;
		this.amount = amount;
	}
	
	
	
}
