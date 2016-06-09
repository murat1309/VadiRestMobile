package com.digikent.vadirest.dto;

public class ZabitaDenetim {
	
	private long mahalle_id;
	private long bagbolum_id;
	private long sokak_id;
	private String tarih;
	private String faaliyetturu;
	private String guncelleme_tarihi;
	private long id;
	 
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGuncelleme_tarihi() {
		return guncelleme_tarihi;
	}
	public void setGuncelleme_tarihi(String guncelleme_tarihi) {
		this.guncelleme_tarihi = guncelleme_tarihi;
	}
	public long getMahalle_id() {
		return mahalle_id;
	}
	public void setMahalle_id(long mahalle_id) {
		this.mahalle_id = mahalle_id;
	}
	public long getBagbolum_id() {
		return bagbolum_id;
	}
	public void setBagbolum_id(long bagbolum_id) {
		this.bagbolum_id = bagbolum_id;
	}
	public long getSokak_id() {
		return sokak_id;
	}
	public void setSokak_id(long sokak_id) {
		this.sokak_id = sokak_id;
	}
	public String getFaaliyetturu() {
		return faaliyetturu;
	}
	public void setFaaliyetturu(String faaliyetturu) {
		this.faaliyetturu = faaliyetturu;
	}
	public String getTarih() {
		return tarih;
	}
	public void setTarih(String tarih) {
		this.tarih = tarih;
	}

}