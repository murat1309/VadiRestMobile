package com.digikent.vadirest.dto;

public class EmlakBeyan {
	
	private long mahalle_id;
	private long sokak_id;
	private String ada;
	private String parsel;

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
	public String getAda() {
		return ada;
	} 
	public void setAda(String ada) {
		this.ada = ada;
	}
	public String getParsel() {
		return parsel;
	}
	public void setParsel(String parsel) {
		this.parsel = parsel;
	}
	

	}

	
