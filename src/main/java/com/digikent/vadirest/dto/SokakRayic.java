package com.digikent.vadirest.dto;

public class SokakRayic {
	
	private String sokakAdi;
	private double rayicTutar; 
	private long sokakId;
	
	
	public long getSokakId() {
		return sokakId;
	}
	public void setSokakId(long sokakId) {
		this.sokakId = sokakId; 
	}
	public String getSokakAdi() {
		return sokakAdi;
	}
	public void setSokakAdi(String sokakAdi) {
		this.sokakAdi = sokakAdi;
	}
	
	public double getRayicTutar() {
		return rayicTutar;
	}
	public void setRayicTutar(double rayicTutar) {
		this.rayicTutar = rayicTutar;
	}	
}