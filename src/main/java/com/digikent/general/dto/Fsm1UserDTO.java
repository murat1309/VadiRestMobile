package com.digikent.general.dto;

/**
 * Created by Kadir on 23.02.2018.
 */
public class Fsm1UserDTO {

    private Long id;
    private String adi;
    private String soyadi;
    private Long ihr1PersonelId;
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

    public Long getIhr1PersonelId() {
        return ihr1PersonelId;
    }

    public void setIhr1PersonelId(Long ihr1PersonelId) {
        this.ihr1PersonelId = ihr1PersonelId;
    }

    public Long getIkyPersonelId() {
        return ikyPersonelId;
    }

    public void setIkyPersonelId(Long ikyPersonelId) {
        this.ikyPersonelId = ikyPersonelId;
    }
}
