package com.digikent.vadirest.dto;

public class PersonelBilgileriDetay {

	private long id;
	private long bsm2ServisGorev;
	private long bsm2ServisKadro;
	private String birimAdi;
	private String turu;
	private String adiSoyadi;
	private long tcKimlikNo;
	private String cepTelefonu;
	private String telefonNumarasi;
	private String elektronikPosta;
	private String dogumYeri;
	private String adres;
	private String dosyaAdi;
	private String dosyaTuru;
	private byte[] icerik;

	public long getBsm2ServisKadro() {
		return bsm2ServisKadro;
	}

	public void setBsm2ServisKadro(long bsm2ServisKadro) {
		this.bsm2ServisKadro = bsm2ServisKadro;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDosyaAdi() {
		return dosyaAdi;
	}
	public void setDosyaAdi(String dosyaAdi) {
		this.dosyaAdi = dosyaAdi;
	}
	public String getDosyaTuru() {
		return dosyaTuru;
	}
	public void setDosyaTuru(String dosyaTuru) {
		this.dosyaTuru = dosyaTuru;
	}
	public byte[] getIcerik() {
		return icerik;
	}
	public void setIcerik(byte[] icerik) {
		this.icerik = icerik;
	}
	public long getBsm2ServisGorev() {
		return bsm2ServisGorev;
	}
	public void setBsm2ServisGorev(long bsm2ServisGorev) {
		this.bsm2ServisGorev = bsm2ServisGorev;
	}
	public String getBirimAdi() {
		return birimAdi;
	}
	public void setBirimAdi(String birimAdi) {
		this.birimAdi = birimAdi;
	}
	public String getTuru() {
		return turu;
	}
	public void setTuru(String turu) {
		this.turu = turu;
	}
	public String getAdiSoyadi() {
		return adiSoyadi;
	}
	public void setAdiSoyadi(String adiSoyadi) {
		this.adiSoyadi = adiSoyadi;
	}
	public long getTcKimlikNo() {
		return tcKimlikNo;
	}
	public void setTcKimlikNo(long tcKimlikNo) {
		this.tcKimlikNo = tcKimlikNo;
	}
	public String getCepTelefonu() {
		return cepTelefonu;
	}
	public void setCepTelefonu(String cepTelefonu) {
		this.cepTelefonu = cepTelefonu;
	}
	public String getTelefonNumarasi() {
		return telefonNumarasi;
	}
	public void setTelefonNumarasi(String telefonNumarasi) {
		this.telefonNumarasi = telefonNumarasi;
	}
	public String getElektronikPosta() {
		return elektronikPosta;
	}
	public void setElektronikPosta(String elektronikPosta) {
		this.elektronikPosta = elektronikPosta;
	}
	public String getDogumYeri() {
		return dogumYeri;
	}
	public void setDogumYeri(String dogumYeri) {
		this.dogumYeri = dogumYeri;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
}
