package com.digikent.vadirest.dto;

public class ToplantiYonetimi {
	private long ay;
	private long yil;
	private String ayText;
	private long toplam;
	
	public long getAy() {
		return ay;
	}
	public void setAy(long ay) {
		this.ay = ay;
	}
	public long getYil() {
		return yil;
	}
	public void setYil(long yil) {
		this.yil = yil;
	}
	public String getAyText() {
		return ayText;
	}
	public void setAyText(String ayText) {
		this.ayText = ayText;
	}
	public long getToplam() {
		return toplam;
	}
	public void setToplam(long toplam) {
		this.toplam = toplam;
	}

}
