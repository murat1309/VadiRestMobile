package com.digikent.denetimyonetimi.dto.rapor;

public class UserDTO {
  private String sahis;
  private String adiSoyadi;
  private String tckn;
  private String tarafTuru;
  private String kurum;
  private String vergiNo;
  private String unvan;

  public String getAdiSoyadi() {
    return adiSoyadi;
  }

  public void setAdiSoyadi(String adiSoyadi) {
    this.adiSoyadi = adiSoyadi;
  }

  public String getTckn() {
    return tckn;
  }

  public void setTckn(String tckn) {
    this.tckn = tckn;
  }

  public String getTarafTuru() {
    return tarafTuru;
  }

  public void setTarafTuru(String tarafTuru) {
    this.tarafTuru = tarafTuru;
  }

  public String getSahis() {
    return sahis;
  }

  public void setSahis(String sahis) {
    this.sahis = sahis;
  }

  public String getKurum() {
    return kurum;
  }

  public void setKurum(String kurum) {
    this.kurum = kurum;
  }

  public String getVergiNo() {
    return vergiNo;
  }

  public void setVergiNo(String vergiNo) {
    this.vergiNo = vergiNo;
  }

  public String getUnvan() {
    return unvan;
  }

  public void setUnvan(String unvan) {
    this.unvan = unvan;
  }
}
