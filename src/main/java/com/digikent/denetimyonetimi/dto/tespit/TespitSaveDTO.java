package com.digikent.denetimyonetimi.dto.tespit;

import java.io.Serializable;

/**
 * Created by Kadir on 13.02.2018.
 */
public class TespitSaveDTO implements Serializable {

    private Long tespitId;
    private Long ekSure;
    private String secenekTuru;
    private TespitCevapDTO tespitCevap;
    private Long tutari;
    private Boolean isCezaSelected;

    public Long getTespitId() {
        return tespitId;
    }

    public void setTespitId(Long tespitId) {
        this.tespitId = tespitId;
    }

    public Long getEkSure() {
        return ekSure;
    }

    public void setEkSure(Long ekSure) {
        this.ekSure = ekSure;
    }

    public String getSecenekTuru() {
        return secenekTuru;
    }

    public void setSecenekTuru(String secenekTuru) {
        this.secenekTuru = secenekTuru;
    }

    public TespitCevapDTO getTespitCevap() {
        return tespitCevap;
    }

    public void setTespitCevap(TespitCevapDTO tespitCevap) {
        this.tespitCevap = tespitCevap;
    }

    public Long getTutari() {
        return tutari;
    }

    public void setTutari(Long tutari) {
        this.tutari = tutari;
    }

    public Boolean getCezaSelected() {
        return isCezaSelected;
    }

    public void setCezaSelected(Boolean cezaSelected) {
        isCezaSelected = cezaSelected;
    }
}
