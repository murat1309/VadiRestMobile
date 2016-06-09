package com.digikent.vadirest.dto;

public class InsanKaynaklari {
	private long id;
	private String tanim;
	private long yas;
	private String tur;
	private String turAciklama;
	private long toplam;
	
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
	public long getYas() {
		return yas;
	}
	public void setYas(long yas) {
		this.yas = yas;
	}
	public String getTur() {
		return tur;
	}
	public void setTur(String tur) {
		this.tur = tur;
	}
	public String getTurAciklama() {
		return turAciklama;
	}
	public void setTurAciklama(String turAciklama) {
		this.turAciklama = turAciklama;
	}
	public long getToplam() {
		return toplam;
	}
	public void setToplam(long toplam) {
		this.toplam = toplam;
	}
	

}
