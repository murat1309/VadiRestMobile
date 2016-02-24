package com.digikent.vadirest.dto;

import java.util.Date;

public class Egitim {
	
	private String tanim;
	private String konusu;
	private String aciklama;
	private String katilimDurumu;
	private String egitmenAdi;
	private Long yili;
	private Long toplamSaat;
	private String verilecekYer;
	private String tarih;
	private String bitisTarihi;
	
	public String getTanim() {
		return tanim;
	}
	public void setTanim(String tanim) {
		this.tanim = tanim;
	}
	public String getKonusu() {
		return konusu;
	}
	public void setKonusu(String konusu) {
		this.konusu = konusu;
	}
	public String getEgitmenAdi() {
		return egitmenAdi;
	}
	public void setEgitmenAdi(String egitmenAdi) {
		this.egitmenAdi = egitmenAdi;
	}
	public Long getYili() {
		return yili;
	}
	public void setYili(Long yili) {
		this.yili = yili;
	}
	public Long getToplamSaat() {
		return toplamSaat;
	}
	public void setToplamSaat(Long toplamSaat) {
		this.toplamSaat = toplamSaat;
	}
	public String getVerilecekYer() {
		return verilecekYer;
	}
	public void setVerilecekYer(String verilecekYer) {
		this.verilecekYer = verilecekYer;
	}
	
	public String getKatilimDurumu() {
		return katilimDurumu;
	}
	public void setKatilimDurumu(String katilimDurumu) {
		this.katilimDurumu = katilimDurumu;
	}
	public String getAciklama() {
		return aciklama;
	}
	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}
	public String getTarih() {
		return tarih;
	}
	public void setTarih(String tarih) {
		this.tarih = tarih;
	}
	public String getBitisTarihi() {
		return bitisTarihi;
	}
	public void setBitisTarihi(String bitisTarihi) {
		this.bitisTarihi = bitisTarihi;
	}

}
