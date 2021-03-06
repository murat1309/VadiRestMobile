package com.digikent.vadirest.dto;

public class Person {
	private Long id;
	private String adi;
	private String soyadi;
	private String adSoyad;
	private Long tcKimlikNo;
	private String dogumTarihi;
	private String cepTelefonu;
	private String dogumYeri;
	private String gorevi;
	private String gorevMudurlugu;
	private String kadroMudurlugu;
	private String kadroSinif;
	private Long kadroDerecesi;
	private String kadro;
	private String turu;
	private String kanGrubu;
	private String dahili;
	private String tanim;
	private String dosyaAdi;
	private String dosyaTuru;
	private byte[] icerik;
	private String seflik;
	private String iseBaslamaTarihi;
	private String meslek;
	private String telefonNumaralari;
	private String mailler;
	private String dahiliTelNolari;

	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAdSoyad() {
		return adSoyad;
	}
	public void setAdSoyad(String adSoyad) {
		this.adSoyad = adSoyad;
	}
	public Long getTcKimlikNo() {
		return tcKimlikNo;
	}
	public void setTcKimlikNo(Long tcKimlikNo) {
		this.tcKimlikNo = tcKimlikNo;
	}
	public String getGorevi() {
		return gorevi;
	}
	public void setGorevi(String gorevi) {
		this.gorevi = gorevi;
	}
	public String getGorevMudurlugu() {
		return gorevMudurlugu;
	}
	public void setGorevMudurlugu(String gorevMudurlugu) {
		this.gorevMudurlugu = gorevMudurlugu;
	}
	public String getKadroMudurlugu() {
		return kadroMudurlugu;
	}
	public void setKadroMudurlugu(String kadroMudurlugu) {
		this.kadroMudurlugu = kadroMudurlugu;
	}
	public String getKadroSinif() {
		return kadroSinif;
	}
	public void setKadroSinif(String kadroSinif) {
		this.kadroSinif = kadroSinif;
	}
	public Long getKadroDerecesi() {
		return kadroDerecesi;
	}
	public void setKadroDerecesi(Long kadroDerecesi) {
		this.kadroDerecesi = kadroDerecesi;
	}
	public String getKadro() {
		return kadro;
	}
	public void setKadro(String kadro) {
		this.kadro = kadro;
	}
	public String getTuru() {
		return turu;
	}
	public void setTuru(String turu) {
		this.turu = turu;
	}
	public String getKanGrubu() {
		return kanGrubu;
	}
	public void setKanGrubu(String kanGrubu) {
		this.kanGrubu = kanGrubu;
	}
	public String getDahili() {
		return dahili;
	}
	public void setDahili(String dahili) {
		this.dahili = dahili;
	}
	public String getAdi() {
		return adi;
	}
	public void setAdi(String adi) {
		this.adi = adi;
	}
	public String getSoyadi() {
		return soyadi;
	}
	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}
	public String getTanim() {
		return tanim;
	}
	public void setTanim(String tanim) {
		this.tanim = tanim;
	}
	public String getDogumTarihi() {
		return dogumTarihi;
	}
	public void setDogumTarihi(String dogumTarihi) {
		this.dogumTarihi = dogumTarihi;
	}
	public String getCepTelefonu() {
		return cepTelefonu;
	}

	public void setCepTelefonu(String cepTelefonu) {
		this.cepTelefonu = cepTelefonu;
	}

	public String getDogumYeri() {
		return dogumYeri;
	}

	public void setDogumYeri(String dogumYeri) {
		this.dogumYeri = dogumYeri;
	}

	public String getSeflik() {
		return seflik;
	}

	public void setSeflik(String seflik) {
		this.seflik = seflik;
	}

	public String getIseBaslamaTarihi() {
		return iseBaslamaTarihi;
	}

	public void setIseBaslamaTarihi(String iseBaslamaTarihi) {
		this.iseBaslamaTarihi = iseBaslamaTarihi;
	}

	public String getMeslek() {
		return meslek;
	}

	public void setMeslek(String meslek) {
		this.meslek = meslek;
	}

	public String getTelefonNumaralari() {
		return telefonNumaralari;
	}

	public void setTelefonNumaralari(String telefonNumaralari) {
		this.telefonNumaralari = telefonNumaralari;
	}

	public String getDahiliTelNolari() {
		return dahiliTelNolari;
	}

	public void setDahiliTelNolari(String dahiliTelNolari) {
		this.dahiliTelNolari = dahiliTelNolari;
	}

	public String getMailler() {
		return mailler;
	}

	public void setMailler(String mailler) {
		this.mailler = mailler;
	}
}
