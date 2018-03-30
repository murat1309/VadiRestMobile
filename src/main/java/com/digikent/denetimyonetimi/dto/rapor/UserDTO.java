package com.digikent.denetimyonetimi.dto.rapor;

public class UserDTO {
  private String adiSoyadi;
  private Long tckn;
  private String tarafTuru;

  public String getAdiSoyadi() {
    return adiSoyadi;
  }

  public void setAdiSoyadi(String adiSoyadi) {
    this.adiSoyadi = adiSoyadi;
  }

  public Long getTckn() {
    return tckn;
  }

  public void setTckn(Long tckn) {
    this.tckn = tckn;
  }

  public String getTarafTuru() {
    return tarafTuru;
  }

  public void setTarafTuru(String tarafTuru) {
    this.tarafTuru = tarafTuru;
  }
}
