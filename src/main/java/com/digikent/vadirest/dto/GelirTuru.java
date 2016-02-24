package com.digikent.vadirest.dto;

public class GelirTuru {
	
	private long id;
	private String gelirTuruAdi;
	private double tutar;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGelirTuruAdi() {
		return gelirTuruAdi;
	}
	public void setGelirTuruAdi(String gelirTuruAdi) {
		this.gelirTuruAdi = gelirTuruAdi;
	}
	public double getTutar() {
		return tutar;
	}
	public void setTutar(double tutar) {
		this.tutar = tutar;
	}	

}
