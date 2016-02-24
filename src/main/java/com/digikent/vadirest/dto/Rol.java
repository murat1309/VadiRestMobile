package com.digikent.vadirest.dto;

public class Rol {
	
	private long id;
	private String tanim;
	private long msm2OrganizationId;
	
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
	public long getMsm2OrganizationId() {
		return msm2OrganizationId;
	}
	public void setMsm2OrganizationId(long msm2OrganizationId) {
		this.msm2OrganizationId = msm2OrganizationId;
	}

}
