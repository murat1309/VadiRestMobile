package com.digikent.denetimyonetimi.dto.rapor;

public class LocationDTO {

  private String ilceAdi;
  private String mahalleAdi;
  private String sokakAdi;
  private String daireBilgisi;
  private String kapiBilgisi;
  private String tamAdres;

  public LocationDTO() {
  }

  public String getIlceAdi() {
    return ilceAdi;
  }

  public void setIlceAdi(String ilceAdi) {
    this.ilceAdi = ilceAdi;
  }

  public String getMahalleAdi() {
    return mahalleAdi;
  }

  public void setMahalleAdi(String mahalleAdi) {
    this.mahalleAdi = mahalleAdi;
  }

  public String getSokakAdi() {
    return sokakAdi;
  }

  public void setSokakAdi(String sokakAdi) {
    this.sokakAdi = sokakAdi;
  }

  public String getDaireBilgisi() {
    return daireBilgisi;
  }

  public void setDaireBilgisi(String daireBilgisi) {
    this.daireBilgisi = daireBilgisi;
  }

  public String getKapiBilgisi() {
    return kapiBilgisi;
  }

  public void setKapiBilgisi(String kapiBilgisi) {
    this.kapiBilgisi = kapiBilgisi;
  }

  public String getTamAdres() {
    return tamAdres;
  }

  public void setTamAdres(String tamAdres) {
    this.tamAdres = tamAdres;
  }
}
