package com.digikent.vadirest.dto;

public class GelirlerYonetimiReklamBeyani {
	private long mpi1PaydasId;
	private String  adSoyad;
	private String bildirimTarihi;
	
	public long getMpi1PaydasId() {
		return mpi1PaydasId;
	}
	public void setMpi1PaydasId(long mpi1PaydasId) {
		this.mpi1PaydasId = mpi1PaydasId;
	}
	public String getAdSoyad() {
		return adSoyad;
	}
	public void setAdSoyad(String adSoyad) {
		this.adSoyad = adSoyad;
	}
	public String getBildirimTarihi() {
		return bildirimTarihi;
	}
	public void setBildirimTarihi(String bildirimTarihi) {
		this.bildirimTarihi = bildirimTarihi;
	}
}
