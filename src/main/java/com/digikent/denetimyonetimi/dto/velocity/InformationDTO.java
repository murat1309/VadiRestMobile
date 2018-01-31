package com.digikent.denetimyonetimi.dto.velocity;

public class InformationDTO {

  private String info;
  private String value;
  private String description;

  public InformationDTO(String info, String value, String description) {
    super();
    this.info = info;
    this.value = value;
    this.description = description;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }



}
