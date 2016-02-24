package com.digikent.vadirest.dto;

public class BankaDurumuDetay {
	private long yevmiyeNumarasi;
	private String yevmiyeTarihi;
	private String izahat;
	private String hesapKodu;
	private String hesapAdi;
	private double toplamAlacak;
	private double toplamBorc;
	public long getYevmiyeNumarasi() {
		return yevmiyeNumarasi;
	}
	public void setYevmiyeNumarasi(long yevmiyeNumarasi) {
		this.yevmiyeNumarasi = yevmiyeNumarasi;
	}
	public String getYevmiyeTarihi() {
		return yevmiyeTarihi;
	}
	public void setYevmiyeTarihi(String yevmiyeTarihi) {
		this.yevmiyeTarihi = yevmiyeTarihi;
	}
	public String getIzahat() {
		return izahat;
	}
	public void setIzahat(String izahat) {
		this.izahat = izahat;
	}
	public String getHesapKodu() {
		return hesapKodu;
	}
	public void setHesapKodu(String hesapKodu) {
		this.hesapKodu = hesapKodu;
	}
	public String getHesapAdi() {
		return hesapAdi;
	}
	public void setHesapAdi(String hesapAdi) {
		this.hesapAdi = hesapAdi;
	}
	public double getToplamAlacak() {
		return toplamAlacak;
	}
	public void setToplamAlacak(double toplamAlacak) {
		this.toplamAlacak = toplamAlacak;
	}
	public double getToplamBorc() {
		return toplamBorc;
	}
	public void setToplamBorc(double toplamBorc) {
		this.toplamBorc = toplamBorc;
	}
	
	
}
