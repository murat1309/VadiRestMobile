package com.digikent.vadirest.dto;

public class FinansmanYonetimiGelirGider {
	private long id;
	private long year;
	private double gelir;
	private double gider;
	private String tanim;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getYear() {
		return year;
	}
	public void setYear(long year) {
		this.year = year;
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
	public String getTanim() {
		return tanim;
	}
	public void setTanim(String tanim) {
		this.tanim = tanim;
	}

}
