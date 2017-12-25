package com.digikent.mesajlasma.dto;

import java.io.Serializable;

/**
 * Created by Kadir on 13/09/17.
 */
public class GroupInformationDTO implements Serializable {

    private String grupAdi;
    private Long olusturanPersonel;
    private String grupTuru;
    private Long groupId;
    private String olusturanPersonelName;

    public GroupInformationDTO(String grupAdi, Long olusturanPersonel, String grupTuru) {
        this.grupAdi = grupAdi;
        this.olusturanPersonel = olusturanPersonel;
        this.grupTuru = grupTuru;
    }

    public GroupInformationDTO(String grupAdi, Long olusturanPersonel, String grupTuru, String olusturanPersonelName) {
        this.grupAdi = grupAdi;
        this.olusturanPersonel = olusturanPersonel;
        this.grupTuru = grupTuru;
        this.olusturanPersonelName = olusturanPersonelName;
    }

    public GroupInformationDTO() {
    }

    public String getOlusturanPersonelName() {
        return olusturanPersonelName;
    }

    public void setOlusturanPersonelName(String olusturanPersonelName) {
        this.olusturanPersonelName = olusturanPersonelName;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
