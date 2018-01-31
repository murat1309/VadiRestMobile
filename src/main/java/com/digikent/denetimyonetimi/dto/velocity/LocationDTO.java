package com.digikent.denetimyonetimi.dto.velocity;

public class LocationDTO {
  private String mahalle;
  private String cadde;
  private String sokak;
  private String no;
  private String ada;
  private String pafta;
  private String parsel;

  public LocationDTO(String mahalle, String cadde, String sokak, String no, String ada, String pafta, String parsel) {
    super();
    this.mahalle = mahalle;
    this.cadde = cadde;
    this.sokak = sokak;
    this.no = no;
    this.ada = ada;
    this.pafta = pafta;
    this.parsel = parsel;
  }

  public String getMahalle() {
    return mahalle;
  }

  public void setMahalle(String mahalle) {
    this.mahalle = mahalle;
  }

  public String getCadde() {
    return cadde;
  }

  public void setCadde(String cadde) {
    this.cadde = cadde;
  }

  public String getSokak() {
    return sokak;
  }

  public void setSokak(String sokak) {
    this.sokak = sokak;
  }

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  public String getAda() {
    return ada;
  }

  public void setAda(String ada) {
    this.ada = ada;
  }

  public String getPafta() {
    return pafta;
  }

  public void setPafta(String pafta) {
    this.pafta = pafta;
  }

  public String getParsel() {
    return parsel;
  }

  public void setParsel(String parsel) {
    this.parsel = parsel;
  }



}
