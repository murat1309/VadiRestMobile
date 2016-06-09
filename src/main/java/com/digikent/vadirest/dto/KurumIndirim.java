package com.digikent.vadirest.dto;

import java.util.Date;

public class KurumIndirim {
	
	private Long id;
	private String adres;
	private String tanim;
	private Long oraniYuzde;
	private String bitisTarihi;
	private String baslangicTarihi;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getTanim() {
		return tanim;
	}
	public void setTanim(String tanim) {
		this.tanim = tanim;
	}
	public Long getOraniYuzde() {
		return oraniYuzde;
	}
	public void setOraniYuzde(Long oraniYuzde) {
		this.oraniYuzde = oraniYuzde;
	}
	public String getBitisTarihi() {
		return bitisTarihi;
	}
	public void setBitisTarihi(String bitisTarihi) {
		this.bitisTarihi = bitisTarihi;
	}
	public String getBaslangicTarihi() {
		return baslangicTarihi;
	}
	public void setBaslangicTarihi(String baslangicTarihi) {
		this.baslangicTarihi = baslangicTarihi;
	}
	
}
