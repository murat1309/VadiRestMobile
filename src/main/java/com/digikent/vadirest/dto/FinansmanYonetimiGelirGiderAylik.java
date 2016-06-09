package com.digikent.vadirest.dto;

public class FinansmanYonetimiGelirGiderAylik {
	private long ay;
	private String month;
	private double gelir;
	private double gider;
	
	public long getAy() {
		return ay;
	}
	public void setAy(long ay) {
		this.ay = ay;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public double getGelir() {
		return gelir;
	}
	public void setGelir(double gelir) {
		this.gelir = gelir;
	}
	public double getGider() {
		return gider;
	}
	public void setGider(double gider) {
		this.gider = gider;
	}

}
