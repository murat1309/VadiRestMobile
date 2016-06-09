package com.digikent.vadirest.dto;

public class GelirlerYonetimiTahakkuk {
	private String adSoyad;
	private double tutar;
	private long yil;
	private long donem;
	private String izahat;
	
	public String getAdSoyad() {
		return adSoyad;
	}
	public void setAdSoyad(String adSoyad) {
		this.adSoyad = adSoyad;
	}
	public double getTutar() {
		return tutar;
	}
	public void setTutar(double tutar) {
		this.tutar = tutar;
	}
	public long getYil() {
		return yil;
	}
	public void setYil(long yil) {
		this.yil = yil;
	}
	public long getDonem() {
		return donem;
	}
	public void setDonem(long donem) {
		this.donem = donem;
	}
	public String getIzahat() {
		return izahat;
	}
	public void setIzahat(String izahat) {
		this.izahat = izahat;
	}
}
