package com.digikent.vadirest.dto;

import java.util.Date;

public class BelgeBasvuru {
	
	private long id;
	private String tarih;
	private String isAkisiKonuOzeti;
	private long girisDefterNumarasi;
	private String turu;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTarih() {
		return tarih;
	}
	public void setTarih(String tarih) {
		this.tarih = tarih;
	}
	public String getIsAkisiKonuOzeti() {
		return isAkisiKonuOzeti;
	}
	public void setIsAkisiKonuOzeti(String isAkisiKonuOzeti) {
		this.isAkisiKonuOzeti = isAkisiKonuOzeti;
	}
	public long getGirisDefterNumarasi() {
		return girisDefterNumarasi;
	}
	public void setGirisDefterNumarasi(long girisDefterNumarasi) {
		this.girisDefterNumarasi = girisDefterNumarasi;
	}
	public String getTuru() {
		return turu;
	}
	public void setTuru(String turu) {
		this.turu = turu;
	}
	
}
