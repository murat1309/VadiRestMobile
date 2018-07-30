package com.digikent.vadirest.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

public class FavoriteWebSite {

    private Long id;
    private String Tanim;
    private String Kopru;
    private Long Sira;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTanim() {
        return Tanim;
    }

    public void setTanim(String tanim) {
        Tanim = tanim;
    }

    public String getKopru() {
        return Kopru;
    }

    public void setKopru(String kopru) {
        Kopru = kopru;
    }

    public Long getSira() {
        return Sira;
    }

    public void setSira(Long sira) {
        Sira = sira;
    }

}
