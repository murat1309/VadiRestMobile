package com.digikent.denetimyonetimi.dto.velocity;

public class TespitDTO {
  private String aciklamamalar;
  private String deger;
  private String dayanakKanunu;
  private String dayanakMaddesi;
  private String cezaTutarı;
  private String aciklama;


  public TespitDTO(String aciklamamalar, String deger, String dayanakKanunu, String dayanakMaddesi, String cezaTutarı, String aciklama) {
    super();
    this.aciklamamalar = aciklamamalar;
    this.deger = deger;
    this.dayanakKanunu = dayanakKanunu;
    this.dayanakMaddesi = dayanakMaddesi;
    this.cezaTutarı = cezaTutarı;
    this.aciklama = aciklama;
  }

  public String getAciklamamalar() {
    return aciklamamalar;
  }

  public void setAciklamamalar(String aciklamamalar) {
    this.aciklamamalar = aciklamamalar;
  }

  public String getDeger() {
    return deger;
  }

  public void setDeger(String deger) {
    this.deger = deger;
  }

  public String getDayanakKanunu() {
    return dayanakKanunu;
  }

  public void setDayanakKanunu(String dayanakKanunu) {
    this.dayanakKanunu = dayanakKanunu;
  }

  public String getDayanakMaddesi() {
    return dayanakMaddesi;
  }

  public void setDayanakMaddesi(String dayanakMaddesi) {
    this.dayanakMaddesi = dayanakMaddesi;
  }

  public String getCezaTutarı() {
    return cezaTutarı;
  }

  public void setCezaTutarı(String cezaTutarı) {
    this.cezaTutarı = cezaTutarı;
  }

  public String getAciklama() {
    return aciklama;
  }

  public void setAciklama(String aciklama) {
    this.aciklama = aciklama;
  }
}
