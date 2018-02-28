package com.digikent.general.dto;

import java.io.Serializable;

/**
 * Created by Kadir on 27.02.2018.
 */
public class Ihr1PersonelDTO implements Serializable {

    private Long id;
    private String adi;
    private String soyadi;
    private Lhr1GorevTuruDTO lhr1GorevTuruDTO;

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

    public Lhr1GorevTuruDTO getLhr1GorevTuruDTO() {
        return lhr1GorevTuruDTO;
    }

    public void setLhr1GorevTuruDTO(Lhr1GorevTuruDTO lhr1GorevTuruDTO) {
        this.lhr1GorevTuruDTO = lhr1GorevTuruDTO;
    }
}
