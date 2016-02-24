package com.digikent.vadirest.dto;

import java.util.Date;


public class Announcement {
private static final long serialVersionUID = 752402762702860418L;
		

	private String baslik;
    private String izahat;
    private String tarih;
    private String sonTarih;
    private String kisaIzahat;
    private char[] resim;
    private String kisaBaslik;
    
    
	

    public String getKisaIzahat() {
		return kisaIzahat;
	}

	public void setKisaIzahat(String kisaIzahat) {
		this.kisaIzahat = kisaIzahat;
	}

	public char[] getResim() {
		return resim;
	}

	public void setResim(char[] resim) {
		this.resim = resim;
	}

	public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

   
    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

   
    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

	public String getKisaBaslik() {
		return kisaBaslik;
	}

	public void setKisaBaslik(String kisaBaslik) {
		this.kisaBaslik = kisaBaslik;
	}

	public String getSonTarih() {
		return sonTarih;
	}

	public void setSonTarih(String sonTarih) {
		this.sonTarih = sonTarih;
	}
    
    
	
}
