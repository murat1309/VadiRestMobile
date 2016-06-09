package com.digikent.vadirest.dto;

public class KurumBorc {
	
	private long id;
	private String tur;
	private String konu;
	private double tahakkukEden;
	private double odenen;
	private double borc;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTur() {
		return tur;
	}
	public void setTur(String tur) {
		this.tur = tur;
	}
	public String getKonu() {
		return konu;
	}
	public void setKonu(String konu) {
		this.konu = konu;
	}
	public double getTahakkukEden() {
		return tahakkukEden;
	}
	public void setTahakkukEden(double tahakkukEden) {
		this.tahakkukEden = tahakkukEden;
	}
	public double getOdenen() {
		return odenen;
	}
	public void setOdenen(double odenen) {
		this.odenen = odenen;
	}
	public double getBorc() {
		return borc;
	}
	public void setBorc(double borc) {
		this.borc = borc;
	}
	

}
