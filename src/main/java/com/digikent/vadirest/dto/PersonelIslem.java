package com.digikent.vadirest.dto;

import java.util.Date;



public class PersonelIslem {

	private static final long serialVersionUID = 4522461914333244077L;

	private String gorevYeri;
	private String adSoyad;
	private String gorevi;
	private String eskiGorevYeri;
	private String turu;
	private Long kadroDerecesi;
	private String gorevlendirmeTarihi;


	public String getGorevYeri() {
		return gorevYeri;
	}

	public void setGorevYeri(String gorevYeri) {
		this.gorevYeri = gorevYeri;
	}
	
	public String getAdSoyad(){
		return this.adSoyad;
	}

	public void setAdSoyad(String adSoyad){
		this.adSoyad = adSoyad;
	}
	public String getGorevi() {
		return gorevi;
	}

	public void setGorevi(String gorevi) {
		this.gorevi = gorevi;
	}

	public String getEskiGorevYeri() {
		return eskiGorevYeri;
	}

	public void setEskiGorevYeri(String eskiGorevYeri) {
		this.eskiGorevYeri = eskiGorevYeri;
	}

	public String getGorevlendirmeTarihi() {
		return gorevlendirmeTarihi;
	}

	public void setGorevlendirmeTarihi(String gorevlendirmeTarihi) {
		this.gorevlendirmeTarihi = gorevlendirmeTarihi;
	}

	public String getTuru() {
		return turu;
	}

	public void setTuru(String turu) {
		this.turu = turu;
	}

	public Long getKadroDerecesi() {
		return kadroDerecesi;
	}

	public void setKadroDerecesi(Long kadroDerecesi) {
		this.kadroDerecesi = kadroDerecesi;
	}


}
