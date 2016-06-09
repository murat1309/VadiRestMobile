package com.digikent.vadirest.dto;

import java.sql.Timestamp;

public class BelgeSorgula {
	
	private long referansno;
	private String kimde;
	private String konuozeti;
	private String tarih;
	private String belgedurumu;
	public long getReferansno() {
		return referansno;
	}
	public void setReferansno(long referansno) {
		this.referansno = referansno;
	}
	public String getKimde() {
		return kimde;
	}
	public void setKimde(String kimde) {
		this.kimde = kimde;
	}
	public String getKonuozeti() {
		return konuozeti;
	}
	public void setKonuozeti(String konuozeti) {
		this.konuozeti = konuozeti;
	}
	public String getTarih() {
		return tarih;
	}
	public void setTarih(String tarih) {
		this.tarih = tarih;
	}
	public String getBelgedurumu() {
		return belgedurumu;
	}
	public void setBelgedurumu(String belgedurumu) {
		this.belgedurumu = belgedurumu;
	}
	
	
}