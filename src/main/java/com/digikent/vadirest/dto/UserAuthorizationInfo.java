package com.digikent.vadirest.dto;

public class UserAuthorizationInfo {
	private long vsm1ProgsId;
	private String type;
	private String name;
	private String description;
	private long parentId;
	private String link;
	private String parameter;
	private long kayitDuzeyi;
	private long menuId;
	
	public long getVsm1ProgsId() {
		return vsm1ProgsId;
	}
	public void setVsm1ProgsId(long vsm1ProgsId) {
		this.vsm1ProgsId = vsm1ProgsId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public long getKayitDuzeyi() {
		return kayitDuzeyi;
	}
	public void setKayitDuzeyi(long kayitDuzeyi) {
		this.kayitDuzeyi = kayitDuzeyi;
	}
	public long getMenuId() {
		return menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

}
