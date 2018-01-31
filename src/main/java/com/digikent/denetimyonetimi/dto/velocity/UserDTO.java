package com.digikent.denetimyonetimi.dto.velocity;

public class UserDTO {
  private String name;
  private String surname;
  private String tckn;
  private String phone;


  public UserDTO(String name, String surname, String tckn, String phone) {
    super();
    this.name = name;
    this.surname = surname;
    this.tckn = tckn;
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getTckn() {
    return tckn;
  }

  public void setTckn(String tckn) {
    this.tckn = tckn;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


}
