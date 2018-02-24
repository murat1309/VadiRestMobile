package com.digikent.denetimyonetimi.dto.velocity;

public class UserDTO {
  private String adiSoyadi;
  private Long tckn;
  private String gorevi;
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

  public String getGorevi() {
    return gorevi;
  }

  public void setGorevi(String gorevi) {
    this.gorevi = gorevi;
  }

  public String getTarafTuru() {
    return tarafTuru;
  }

  public void setTarafTuru(String tarafTuru) {
    this.tarafTuru = tarafTuru;
  }
}
