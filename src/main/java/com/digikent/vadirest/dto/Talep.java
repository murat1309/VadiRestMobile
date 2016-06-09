package com.digikent.vadirest.dto;

public class Talep {

	private long id;
	private String konuTuru;
	private int talepSayisi;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKonuTuru() {
		return konuTuru;
	}
	public void setKonuTuru(String konuTuru) {
		this.konuTuru = konuTuru;
	}
	public int getTalepSayisi() {
		return talepSayisi;
	}
	public void setTalepSayisi(int talepSayisi) {
		this.talepSayisi = talepSayisi;
	}
}
