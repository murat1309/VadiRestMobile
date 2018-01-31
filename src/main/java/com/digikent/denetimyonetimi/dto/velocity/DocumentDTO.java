package com.digikent.denetimyonetimi.dto.velocity;

import java.util.Date;

public class DocumentDTO {
  private Date date;
  private String number;

  public DocumentDTO(Date date, String number) {
    super();
    this.date = date;
    this.number = number;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

}
