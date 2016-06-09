package com.digikent.vadirest.dto;

public class IsyeriRuhsat {
	
	private long ruhsat_yili;
	private long bagbolum_id;
	private long mahalle_id;
	private long sokak_id;
	private String ruhsat_turu;
	public long getRuhsat_yili() {
		return ruhsat_yili;
	}
	public void setRuhsat_yili(long ruhsat_yili) {
		this.ruhsat_yili = ruhsat_yili;
	}
	public long getBagbolum_id() {
		return bagbolum_id;
	}
	public void setBagbolum_id(long bagbolum_id) {
		this.bagbolum_id = bagbolum_id;
	}
	public long getMahalle_id() {
		return mahalle_id;
	}
	public void setMahalle_id(long mahalle_id) {
		this.mahalle_id = mahalle_id;
	}
	public long getSokak_id() {
		return sokak_id;
	}
	public void setSokak_id(long sokak_id) {
		this.sokak_id = sokak_id;
	}
	public String getRuhsat_turu() {
		return ruhsat_turu;
	}
	public void setRuhsat_turu(String ruhsat_turu) {
		this.ruhsat_turu = ruhsat_turu;
	}
	
	
}