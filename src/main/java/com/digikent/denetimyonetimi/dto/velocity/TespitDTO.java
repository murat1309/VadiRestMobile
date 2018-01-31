package com.digikent.denetimyonetimi.dto.velocity;

public class TespitDTO {
  private String aciklamamalar;
  private String deger;
  private String dayanakKanunu;
  private String dayanakMaddesi;
  private String cezaTutari;
  private String aciklama;


  public TespitDTO(String aciklamamalar, String deger, String dayanakKanunu, String dayanakMaddesi, String cezaTutari, String aciklama) {
    super();
    this.aciklamamalar = aciklamamalar;
    this.deger = deger;
    this.dayanakKanunu = dayanakKanunu;
    this.dayanakMaddesi = dayanakMaddesi;
    this.cezaTutari = cezaTutari;
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

  public String getCezaTutari() {
    return cezaTutari;
  }

  public void setCezaTutari(String cezaTutari) {
    this.cezaTutari = cezaTutari;
  }

  public String getAciklama() {
    return aciklama;
  }

  public void setAciklama(String aciklama) {
    this.aciklama = aciklama;
  }
}
