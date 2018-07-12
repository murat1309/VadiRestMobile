package com.digikent.basvuruyonetimi.dto;

/**
 * Created by Serkan on 21/07/17.
 */
public class DM1IsAkısıAdımDTO {

    private Long id;
    private String sonucDurumu;
    private String izahat;
    private String error;

    public DM1IsAkısıAdımDTO() {
    }

    public DM1IsAkısıAdımDTO(Long id, String sonucDurumu, String izahat, String error) {
        this.id = id;
        this.sonucDurumu = sonucDurumu;
        this.izahat = izahat;
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSonucDurumu() {
        return sonucDurumu;
    }

    public void setSonucDurumu(String sonucDurumu) {
        this.sonucDurumu = sonucDurumu;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
