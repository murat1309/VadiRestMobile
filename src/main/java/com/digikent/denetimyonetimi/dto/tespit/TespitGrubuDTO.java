package com.digikent.denetimyonetimi.dto.tespit;

import java.io.Serializable;

/**
 * Created by Kadir on 26.01.2018.
 */
public class TespitGrubuDTO implements Serializable {

    private Long id;
    private String tanim;
    private String kayitOzelIsmi;
    private String izahat;

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

    public String getKayitOzelIsmi() {
        return kayitOzelIsmi;
    }

    public void setKayitOzelIsmi(String kayitOzelIsmi) {
        this.kayitOzelIsmi = kayitOzelIsmi;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

}
