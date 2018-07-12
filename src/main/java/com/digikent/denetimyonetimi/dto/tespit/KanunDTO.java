package com.digikent.denetimyonetimi.dto.tespit;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kadir on 14.02.2018.
 */
public class KanunDTO implements Serializable {

    private Long id;
    private String tanim;
    private String izahat;
    private Date yayimTarihi;

    public KanunDTO() {
    }

    public KanunDTO(Long id, String tanim, String izahat, Date yayimTarihi) {
        this.id = id;
        this.tanim = tanim;
        this.izahat = izahat;
        this.yayimTarihi = yayimTarihi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public Date getYayimTarihi() {
        return yayimTarihi;
    }

    public void setYayimTarihi(Date yayimTarihi) {
        this.yayimTarihi = yayimTarihi;
    }
}
