package com.digikent.vadirest.dto;

public class Maas {
	
	private Long id;
	private Long ay;
	private Double kesintiTutari;
	private Double netTutar;
	private Double brutTutar;
	private String maasAdi;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAy() {
		return ay;
	}
	public void setAy(Long ay) {
		this.ay = ay;
	}
	public Double getKesintiTutari() {
		return kesintiTutari;
	}
	public void setKesintiTutari(Double kesintiTutari) {
		this.kesintiTutari = kesintiTutari;
	}
	public Double getNetTutar() {
		return netTutar;
	}
	public void setNetTutar(Double netTutar) {
		this.netTutar = netTutar;
	}
	public Double getBrutTutar() {
		return brutTutar;
	}
	public void setBrutTutar(Double brutTutar) {
		this.brutTutar = brutTutar;
	}
	public String getMaasAdi() {
		return maasAdi;
	}
	public void setMaasAdi(String maasAdi) {
		this.maasAdi = maasAdi;
	}
	
}
