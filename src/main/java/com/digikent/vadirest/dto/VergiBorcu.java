package com.digikent.vadirest.dto;

import java.util.Date;

public class VergiBorcu {

	private String gelirturu;
	private String geliradi;
	private long beyanayrinti;
	private String borcyilidonem;
	private String tahakkuktarihi;
	private String vadetarihi;
	private double borctutari;
	private double gecikmezammi;
	private double toplam;
	
	public String getGelirturu() {
		return gelirturu;
	}
	public void setGelirturu(String gelirturu) {
		this.gelirturu = gelirturu;
	}
	public String getGeliradi() {
		return geliradi;
	}
	public void setGeliradi(String geliradi) {
		this.geliradi = geliradi;
	}
	public long getBeyanayrinti() {
		return beyanayrinti;
	}
	public void setBeyanayrinti(long beyanayrinti) {
		this.beyanayrinti = beyanayrinti;
	}
	public String getBorcyilidonem() {
		return borcyilidonem;
	}
	public void setBorcyilidonem(String borcyilidonem) {
		this.borcyilidonem = borcyilidonem;
	}
	public String getTahakkuktarihi() {
		return tahakkuktarihi;
	}
	public void setTahakkuktarihi(String tahakkuktarihi) {
		this.tahakkuktarihi = tahakkuktarihi;
	}
	public String getVadetarihi() {
		return vadetarihi;
	}
	public void setVadetarihi(String vadetarihi) {
		this.vadetarihi = vadetarihi;
	}
	public double getBorctutari() {
		return borctutari;
	}
	public void setBorctutari(double borctutari) {
		this.borctutari = borctutari;
	}
	public double getGecikmezammi() {
		return gecikmezammi;
	}
	public void setGecikmezammi(double gecikmezammi) {
		this.gecikmezammi = gecikmezammi;
	}
	public double getToplam() {
		return toplam;
	}
	public void setToplam(double toplam) {
		this.toplam = toplam;
	}
}