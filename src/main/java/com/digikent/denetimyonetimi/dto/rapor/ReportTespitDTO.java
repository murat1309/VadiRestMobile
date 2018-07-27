package com.digikent.denetimyonetimi.dto.rapor;

public class ReportTespitDTO {
  private String tespitAciklamasi;
  private String deger;
  private String dayanakKanunu;
  private String aciklama;
  private String tur;
  private String madde;

  public ReportTespitDTO() {
  }

  public ReportTespitDTO(String tespitAciklamasi, String deger, String dayanakKanunu, String dayanakMaddesi, String aciklama) {
    super();
    this.tespitAciklamasi = tespitAciklamasi;
    this.deger = deger;
    this.dayanakKanunu = dayanakKanunu;
    this.aciklama = aciklama;
  }

  public String getTespitAciklamasi() {
    return tespitAciklamasi;
  }

  public void setTespitAciklamasi(String tespitAciklamasi) {
    this.tespitAciklamasi = tespitAciklamasi;
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

  public String getAciklama() {
    return aciklama;
  }

  public void setAciklama(String aciklama) {
    this.aciklama = aciklama;
  }

  public String getTur() {
    return tur;
  }

  public void setTur(String tur) {
    this.tur = tur;
  }

  public String getMadde() {
    return madde;
  }

  public void setMadde(String madde) {
    this.madde = madde;
  }
}
