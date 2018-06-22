package com.digikent.vadirest.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserAuthenticationInfo {

	private long id;
	private long personelId;
	private String firstName;
	private String lastName;
	private String activeDirectoryUserName;
	private long msm2PersonelId;
	private long servisId;
	private long bsm2ServisMudurluk;
	private long masterId;
	private String userName;
	private String dosyaAdi;
	private String dosyaTuru;
	private byte[] icerik;
	private String dogumTarihi;

	public String getActiveDirectoryUserName() {
		return activeDirectoryUserName;
	}
	public void setActiveDirectoryUserName(String activeDirectoryUserName) {
		this.activeDirectoryUserName = activeDirectoryUserName;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPersonelId() {
		return personelId;
	}
	public void setPersonelId(long personelId) {
		this.personelId = personelId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getMsm2PersonelId() {
		return msm2PersonelId;
	}
	public void setMsm2PersonelId(long msm2PersonelId) {
		this.msm2PersonelId = msm2PersonelId;
	}
	public long getMasterId() {
		return masterId;
	}
	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}
	public long getBsm2ServisMudurluk() {
		return bsm2ServisMudurluk;
	}
	public void setBsm2ServisMudurluk(long bsm2ServisMudurluk) {
		this.bsm2ServisMudurluk = bsm2ServisMudurluk;
	}
	public long getServisId() {
		return servisId;
	}
	public void setServisId(long servisId) {
		this.servisId = servisId;
	}
	public String getDogumTarihi() {
		return dogumTarihi;
	}
	public void setDogumTarihi(String dogumTarihi) {
		this.dogumTarihi = dogumTarihi;
	}
}
