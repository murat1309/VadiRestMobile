package com.digikent.vadirest.dto;

public class GelirlerYonetimiMahalle {
	private long mp1PaydasId;
	private String adSoyad;
	private long ein1GelirGrubuId;
	private long ginGelirTuruId;
	private long year;
	private double tutar;
	private String date;
	private long donemi;
	
	public long getMp1PaydasId() {
		return mp1PaydasId;
	}
	public void setMp1PaydasId(long mp1PaydasId) {
		this.mp1PaydasId = mp1PaydasId;
	}	
	public String getAdSoyad() {
		return adSoyad;
	}
	public void setAdSoyad(String adSoyad) {
		this.adSoyad = adSoyad;
	}
	public long getEin1GelirGrubuId() {
		return ein1GelirGrubuId;
	}
	public void setEin1GelirGrubuId(long ein1GelirGrubuId) {
		this.ein1GelirGrubuId = ein1GelirGrubuId;
	}
	public long getGinGelirTuruId() {
		return ginGelirTuruId;
	}
	public void setGinGelirTuruId(long ginGelirTuruId) {
		this.ginGelirTuruId = ginGelirTuruId;
	}
	public long getYear() {
		return year;
	}
	public void setYear(long year) {
		this.year = year;
	}
	public double getTutar() {
		return tutar;
	}
	public void setTutar(double tutar) {
		this.tutar = tutar;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getDonemi() {
		return donemi;
	}
	public void setDonemi(long donemi) {
		this.donemi = donemi;
	}
}
