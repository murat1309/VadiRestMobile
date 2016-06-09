package com.digikent.vadirest.dto;

import java.util.Date;

public class BasvuruOzet {
	
	private long basvuruNo;
	private long adimId;
	private String bildirimTuru;
	private String adi;
	private String soyAdi;
	private String isAkisOzeti;
	private String konusu;
	private String ilceAdi;
	private String mahalleAdi;
	private String sokakAdi;
	private long evTelefonu;
	private long isTelefonu;
	private long cepTelefonu;
	private String tarih;
	private String sonucDurumu;
	private String sonuc;
	
	public long getBasvuruNo() {
		return basvuruNo;
	}
	public void setBasvuruNo(long basvuruNo) {
		this.basvuruNo = basvuruNo;
	}
	public long getAdimId() {
		return adimId;
	}

	public void setAdimId(long adimId) {
		this.adimId = adimId;
	}
	public String getBildirimTuru() {
		return bildirimTuru;
	}
	public void setBildirimTuru(String bildirimTuru) {
		this.bildirimTuru = bildirimTuru;
	}
	public String getAdi() {
		return adi;
	}
	public void setAdi(String adi) {
		this.adi = adi;
	}
	public String getSoyAdi() {
		return soyAdi;
	}
	public void setSoyAdi(String soyAdi) {
		this.soyAdi = soyAdi;
	}
	public String getIsAkisOzeti() {
		return isAkisOzeti;
	}
	public void setIsAkisOzeti(String isAkisOzeti) {
		this.isAkisOzeti = isAkisOzeti;
	}
	public String getKonusu() {
		return konusu;
	}
	public void setKonusu(String konusu) {
		this.konusu = konusu;
	}
	public String getIlceAdi() {
		return ilceAdi;
	}
	public void setIlceAdi(String ilceAdi) {
		this.ilceAdi = ilceAdi;
	}
	public String getMahalleAdi() {
		return mahalleAdi;
	}
	public void setMahalleAdi(String mahalleAdi) {
		this.mahalleAdi = mahalleAdi;
	}
	public String getSokakAdi() {
		return sokakAdi;
	}
	public void setSokakAdi(String sokakAdi) {
		this.sokakAdi = sokakAdi;
	}
	public long getEvTelefonu() {
		return evTelefonu;
	}
	public void setEvTelefonu(long evTelefonu) {
		this.evTelefonu = evTelefonu;
	}
	public long getCepTelefonu() {
		return cepTelefonu;
	}
	public void setCepTelefonu(long cepTelefonu) {
		this.cepTelefonu = cepTelefonu;
	}
	public String getTarih() {
		return tarih;
	}
	public void setTarih(String tarih) {
		this.tarih = tarih;
	}
	public String getSonucDurumu() {
		return sonucDurumu;
	}
	public void setSonucDurumu(String sonucDurumu) {
		this.sonucDurumu = sonucDurumu;
	}
	public String getSonuc() {
		return sonuc;
	}
	public void setSonuc(String sonuc) {
		this.sonuc = sonuc;
	}
	public long getIsTelefonu() {
		return isTelefonu;
	}
	public void setIsTelefonu(long isTelefonu) {
		this.isTelefonu = isTelefonu;
	}
}
