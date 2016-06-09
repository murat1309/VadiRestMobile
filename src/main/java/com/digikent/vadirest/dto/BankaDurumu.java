package com.digikent.vadirest.dto;

public class BankaDurumu {
	
	private String hesapKodu;
	private String hesapAdi;
	private double tahakkukEden;
	private double odenen;
	private double bakiye;
	
	public String getHesapKodu() {
		return hesapKodu;
	}
	public void setHesapKodu(String hesapKodu) {
		this.hesapKodu = hesapKodu;
	}
	public String getHesapAdi() {
		return hesapAdi;
	}
	public void setHesapAdi(String hesapAdi) {
		this.hesapAdi = hesapAdi;
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
	public double getBakiye() {
		return bakiye;
	}
	public void setBakiye(double bakiye) {
		this.bakiye = bakiye;
	}
}
