package com.digikent.vadirest.dto;

public class BelgeYonetim {
	private String turu;
	private String turuText;
	private String sonucDurumu;
	private String sonucDurumuText;
	private String birimAdi;
	private long toplam;
	private long id;
	
	public String getTuru() {
		return turu;
	}
	public void setTuru(String turu) {
		this.turu = turu;
	}
	public String getTuruText() {
		return turuText;
	}
	public void setTuruText(String turuText) {
		this.turuText = turuText;
	}
	public String getSonucDurumu() {
		return sonucDurumu;
	}
	public void setSonucDurumu(String sonucDurumu) {
		this.sonucDurumu = sonucDurumu;
	}
	public String getSonucDurumuText() {
		return sonucDurumuText;
	}
	public void setSonucDurumuText(String sonucDurumuText) {
		this.sonucDurumuText = sonucDurumuText;
	}
	public String getBirimAdi() {
		return birimAdi;
	}
	public void setBirimAdi(String birimAdi) {
		this.birimAdi = birimAdi;
	}
	public long getToplam() {
		return toplam;
	}
	public void setToplam(long toplam) {
		this.toplam = toplam;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
