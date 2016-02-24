package com.digikent.vadirest.dto;

public class Basvuru {
	
	private long id;
	private String birimAdi;
	private int basvuruSayisi;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBirimAdi() {
		return birimAdi;
	}
	public void setBirimAdi(String birimAdi) {
		this.birimAdi = birimAdi;
	}
	public int getBasvuruSayisi() {
		return basvuruSayisi;
	}
	public void setBasvuruSayisi(int basvuruSayisi) {
		this.basvuruSayisi = basvuruSayisi;
	}

}
