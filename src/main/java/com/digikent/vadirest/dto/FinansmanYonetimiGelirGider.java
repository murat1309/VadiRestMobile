package com.digikent.vadirest.dto;

public class FinansmanYonetimiGelirGider {
	private long id;
	private long year;
	private double gelir;
	private double gider;
	private double gelirGerceklesme;
	private double giderGerceklesme;
	private String tanim;
	private String kodu;
	private String giderGerceklesmeYuzdesi;
	private String gelirGerceklesmeYuzdesi;
	private String butcePayi;
	private String enAltDuzey;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getYear() {
		return year;
	}
	public void setYear(long year) {
		this.year = year;
	}
	public double getGelir() {
		return gelir;
	}
	public void setGelir(double gelir) {
		this.gelir = gelir;
	}
	public double getGider() {
		return gider;
	}
	public void setGider(double gider) {
		this.gider = gider;
	}
	public String getTanim() {
		return tanim;
	}
	public void setTanim(String tanim) {
		this.tanim = tanim;
	}
	public double getGelirGerceklesme() {
		return gelirGerceklesme;
	}
	public void setGelirGerceklesme(double gelirGerceklesme) {
		this.gelirGerceklesme = gelirGerceklesme;
	}
	public double getGiderGerceklesme() {
		return giderGerceklesme;
	}
	public void setGiderGerceklesme(double giderGerceklesme) {
		this.giderGerceklesme = giderGerceklesme;
	}

	public String getKodu() {
		return kodu;
	}

	public void setKodu(String kodu) {
		this.kodu = kodu;
	}

	public String getButcePayi() {
		return butcePayi;
	}

	public void setButcePayi(String butcePayi) {
		this.butcePayi = butcePayi;
	}

	public String getGiderGerceklesmeYuzdesi() {
		return giderGerceklesmeYuzdesi;
	}

	public void setGiderGerceklesmeYuzdesi(String giderGerceklesmeYuzdesi) {
		this.giderGerceklesmeYuzdesi = giderGerceklesmeYuzdesi;
	}

	public String getGelirGerceklesmeYuzdesi() {
		return gelirGerceklesmeYuzdesi;
	}

	public void setGelirGerceklesmeYuzdesi(String gelirGerceklesmeYuzdesi) {
		this.gelirGerceklesmeYuzdesi = gelirGerceklesmeYuzdesi;
	}

	public String getEnAltDuzey() {
		return enAltDuzey;
	}

	public void setEnAltDuzey(String enAltDuzey) {
		this.enAltDuzey = enAltDuzey;
	}
}
