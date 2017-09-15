package com.digikent.mesajlasma.dto;

import java.io.Serializable;

/**
 * Created by Kadir on 13/09/17.
 */
public class GroupInformationDTO implements Serializable {

    private String grupAdi;
    private Long olusturanPersonel;
    private String grupTuru;

    public GroupInformationDTO(String grupAdi, Long olusturanPersonel, String grupTuru) {
        this.grupAdi = grupAdi;
        this.olusturanPersonel = olusturanPersonel;
        this.grupTuru = grupTuru;
    }

    public GroupInformationDTO() {
    }

    public String getGrupAdi() {
        return grupAdi;
    }

    public void setGrupAdi(String grupAdi) {
        this.grupAdi = grupAdi;
    }

    public Long getOlusturanPersonel() {
        return olusturanPersonel;
    }

    public void setOlusturanPersonel(Long olusturanPersonel) {
        this.olusturanPersonel = olusturanPersonel;
    }

    public String getGrupTuru() {
        return grupTuru;
    }

    public void setGrupTuru(String grupTuru) {
        this.grupTuru = grupTuru;
    }
}
