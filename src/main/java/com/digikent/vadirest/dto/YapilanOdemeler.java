package com.digikent.vadirest.dto;

public class YapilanOdemeler {
	
	private long id;
	private String firmaAdi;
	private double tutar;
	private String bankaAdi;
	private long yevmiyeNo;
	private String date;
	
	public long getYevmiyeNo() {
		return yevmiyeNo;
	}
	public void setYevmiyeNo(long yevmiyeNo) {
		this.yevmiyeNo = yevmiyeNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirmaAdi() {
		return firmaAdi;
	}
	public void setFirmaAdi(String firmaAdi) {
		this.firmaAdi = firmaAdi;
	}
	public double getTutar() {
		return tutar;
	}
	public void setTutar(double tutar) {
		this.tutar = tutar;
	}
	public String getBankaAdi() {
		return bankaAdi;
	}
	public void setBankaAdi(String bankaAdi) {
		this.bankaAdi = bankaAdi;
	}
	

}
