package com.digikent.general.dto;

/**
 * Created by Kadir on 23.02.2018.
 */
public class Fsm1UserDTO {

    private Long id;
    private String adi;
    private String soyadi;
    //private Long ihr1PersonelId;
    private Ihr1PersonelDTO ihr1PersonelDTO;
    private Long ikyPersonelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public Long getIkyPersonelId() {
        return ikyPersonelId;
    }

    public void setIkyPersonelId(Long ikyPersonelId) {
        this.ikyPersonelId = ikyPersonelId;
    }

    public Ihr1PersonelDTO getIhr1PersonelDTO() {
        return ihr1PersonelDTO;
    }

    public void setIhr1PersonelDTO(Ihr1PersonelDTO ihr1PersonelDTO) {
        this.ihr1PersonelDTO = ihr1PersonelDTO;
    }
}
