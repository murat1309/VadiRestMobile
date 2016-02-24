package com.digikent.vadirest.dto;

public class BinaAsinmaOranlari {
	
	private String yilAraligi;
	private long asinmaOrani;
	private String binaTuru;

	public String getYilAraligi() {
		return yilAraligi;
	}
	public void setAYilAraligi(String yilAraligi) {
		this.yilAraligi = yilAraligi;
	}
	public long getAsinmaOrani() {
		return asinmaOrani;
	}
	public void setAsinmaOrani(long asinmaOrani) {
		this.asinmaOrani = asinmaOrani;
	}
	
	public String getBinaTuru() {
		return binaTuru;
	}
	public void setBinaTuru(String binaTuru) {
		this.binaTuru = binaTuru;
	}
}