package com.digikent.vadirest.dto;

public class CevreTemizlikTarife {
	
	private long yil;
	private long grup;
	private long derece;
	private double yillikTutar;
	
	public long getYil() {
		return yil;
	}
	public void setYil(long yil) {
		this.yil = yil;
	}
	public long getGrup() {
		return grup;
	}
	public void setGrup(long grup) {
		this.grup = grup;
	}
	public long getDerece() {
		return derece;
	}
	public void setDerece(long derece) {
		this.derece = derece;
	}
	public double getYillikTutar() {
		return yillikTutar;
	}
	public void setYillikTutar(double yillikTutar) {
		this.yillikTutar = yillikTutar;
	}

	
}