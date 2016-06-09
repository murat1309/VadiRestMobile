package com.digikent.vadirest.dto;

public class FinansmanYonetimiTahakkuk {
	private String kodu;
	private String duzeyKodu;
	private String tanim;
	private long yil;
	private double tutar;
	
	public String getKodu() {
		return kodu;
	}
	public void setKodu(String kodu) {
		this.kodu = kodu;
	}
	public String getDuzeyKodu() {
		return duzeyKodu;
	}
	public void setDuzeyKodu(String duzeyKodu) {
		this.duzeyKodu = duzeyKodu;
	}
	public String getTanim() {
		return tanim;
	}
	public void setTanim(String tanim) {
		this.tanim = tanim;
	}
	public long getYil() {
		return yil;
	}
	public void setYil(long yil) {
		this.yil = yil;
	}
	public double getTutar() {
		return tutar;
	}
	public void setTutar(double tutar) {
		this.tutar = tutar;
	}	
}
