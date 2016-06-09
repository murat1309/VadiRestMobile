package com.digikent.vadirest.dto;

public class CagriMerkezi {
	
	private long mahalle_id;
	private long sokak_id;
	private String tarih;
	private String guncelleme_tarihi;
	private String konu_turu;
	private long id;

	
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
	public long getMahalle_id() {
		return mahalle_id;
	}
	public void setMahalle_id(long mahalle_id) {
		this.mahalle_id = mahalle_id;
	}
	public long getSokak_id() {
		return sokak_id;
	}
	public void setSokak_id(long sokak_id) {
		this.sokak_id = sokak_id;
	}
	public String getKonu_turu() {
		return konu_turu;
	}
	public void setKonu_turu(String konu_turu) {
		this.konu_turu = konu_turu;
	}
	public String getGuncelleme_tarihi() {
		return guncelleme_tarihi;
	}
	public void setGuncelleme_tarihi(String guncelleme_tarihi) {
		this.guncelleme_tarihi = guncelleme_tarihi;
	}
	
	
	
}