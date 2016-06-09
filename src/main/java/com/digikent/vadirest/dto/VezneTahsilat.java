package com.digikent.vadirest.dto;

public class VezneTahsilat {
	private long id;
	private String tanim;
	private double tutar;
	private long kisiSayisi;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTanim() {
		return tanim;
	}
	public void setTanim(String tanim) {
		this.tanim = tanim;
	}
	public double getTutar() {
		return tutar;
	}
	public void setTutar(double tutar) {
		this.tutar = tutar;
	}
	public long getKisiSayisi() {
		return kisiSayisi;
	}
	public void setKisiSayisi(long kisiSayisi) {
		this.kisiSayisi = kisiSayisi;
	}

}
