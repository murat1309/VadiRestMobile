package com.digikent.vadirest.dto;

public class AracTalep {
	
	private long id;
	private String kalkisYeri;
	private String gidecegiYer;
	private String kalkisTarihi;
	private String talepEdilenTarih;
	private String cikisTarihi;
	private String donusTarihi;
	
	public String getCikisTarihi() {
		return cikisTarihi;
	}
	public void setCikisTarihi(String cikisTarihi) {
		this.cikisTarihi = cikisTarihi;
	}
	public String getDonusTarihi() {
		return donusTarihi;
	}
	public void setDonusTarihi(String donusTarihi) {
		this.donusTarihi = donusTarihi;
	}
	private String izahat;
	private String sonucDurumu;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKalkisYeri() {
		return kalkisYeri;
	}
	public void setKalkisYeri(String kalkisYeri) {
		this.kalkisYeri = kalkisYeri;
	}
	public String getGidecegiYer() {
		return gidecegiYer;
	}
	public void setGidecegiYer(String gidecegiYer) {
		this.gidecegiYer = gidecegiYer;
	}
	public String getKalkisTarihi() {
		return kalkisTarihi;
	}
	public void setKalkisTarihi(String kalkisTarihi) {
		this.kalkisTarihi = kalkisTarihi;
	}
	public String getTalepEdilenTarih() {
		return talepEdilenTarih;
	}
	public void setTalepEdilenTarih(String talepEdilenTarih) {
		this.talepEdilenTarih = talepEdilenTarih;
	}
	public String getIzahat() {
		return izahat;
	}
	public void setIzahat(String izahat) {
		this.izahat = izahat;
	}
	public String getSonucDurumu() {
		return sonucDurumu;
	}
	public void setSonucDurumu(String sonucDurumu) {
		this.sonucDurumu = sonucDurumu;
	}


}
