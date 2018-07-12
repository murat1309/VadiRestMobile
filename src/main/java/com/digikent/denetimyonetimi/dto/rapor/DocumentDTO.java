package com.digikent.denetimyonetimi.dto.rapor;

public class DocumentDTO {
  private String dateString;
  private String number;

  public DocumentDTO(String dateString, String number) {
    super();
    this.dateString = dateString;
    this.number = number;
  }

  public String getDateString() {
    return dateString;
  }

  public void setDateString(String dateString) {
    this.dateString = dateString;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

}
