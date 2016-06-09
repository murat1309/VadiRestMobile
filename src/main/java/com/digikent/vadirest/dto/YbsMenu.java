package com.digikent.vadirest.dto;

import java.util.List;

public class YbsMenu {
	private String id;
	private String parent;
	private String title;
	private String data;
	private String isLeaf;
	private String type;
	private String description;
	private String parameter;
	private int kayitDuzeyi;
	private List<YbsMenu> nodes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public int getKayitDuzeyi() {
		return kayitDuzeyi;
	}
	public void setKayitDuzeyi(int kayitDuzeyi) {
		this.kayitDuzeyi = kayitDuzeyi;
	}
	public List<YbsMenu> getNodes() {
		return nodes;
	}
	public void setNodes(List<YbsMenu> nodes) {
		this.nodes = nodes;
	}
	
	
}
