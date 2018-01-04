package com.digikent.vadirest.dto;

import java.util.Date;

public class EBYS {

	private long id;
	private long ebysDocumentId;
	private String konu;
	private String message;
	private String name;
	private long docId;
	private String creationDate;
	private long paketId;
	private long workFlowId;

	public long getPaketId() {
		return paketId;
	}
	public void setPaketId(long paketId) {
		this.paketId = paketId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getEbysDocumentId() {
		return ebysDocumentId;
	}
	public void setEbysDocumentId(long ebysDocumentId) {
		this.ebysDocumentId = ebysDocumentId;
	}
	public String getKonu() {
		return konu;
	}
	public void setKonu(String konu) {
		this.konu = konu;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getDocId() {
		return docId;
	}
	public void setDocId(long docId) {
		this.docId = docId;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public long getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(long workFlowId) {
		this.workFlowId = workFlowId;
	}
	
}
