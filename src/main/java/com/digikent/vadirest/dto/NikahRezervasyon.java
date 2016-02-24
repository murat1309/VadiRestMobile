package com.digikent.vadirest.dto;

public class NikahRezervasyon {
	
	private String baslangicsaati;
	private String bitissaati;
	private long sure;
	private String haftaningunu;
	private String salon;
	private long nikah_id;
	
	
	public String getBaslangicsaati() {
		return baslangicsaati;
	}
	public void setBaslangicsaati(String baslangicsaati) {
		this.baslangicsaati = baslangicsaati;
	}
	public String getBitissaati() {
		return bitissaati;
	}
	public void setBitissaati(String bitissaati) {
		this.bitissaati = bitissaati;
	}
	public long getSure() {
		return sure;
	}
	public void setSure(long sure) {
		this.sure = sure;
	}
	public String getHaftaningunu() {
		return haftaningunu;
	}
	public void setHaftaningunu(String haftaningunu) {
		this.haftaningunu = haftaningunu;
	}
	public String getSalon() {
		return salon;
	}
	public void setSalon(String salon) {
		this.salon = salon;
	}
	public long getNikah_id() {
		return nikah_id;
	}
	public void setNikah_id(long nikah_id) {
		this.nikah_id = nikah_id;
	}
}