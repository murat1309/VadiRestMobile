package com.digikent.denetimyonetimi.dto.adres;

import java.io.Serializable;

/**
 * Created by Kadir on 28.01.2018.
 */
public class MahalleSokakDTO implements Serializable {

    private String mahalleAdi;
    private Long mahalleId;
    private String sokakAdi;
    private Long sokakId;
    private Long ilceId;

    public String getMahalleAdi() {
        return mahalleAdi;
    }

    public void setMahalleAdi(String mahalleAdi) {
        this.mahalleAdi = mahalleAdi;
    }

    public Long getMahalleId() {
        return mahalleId;
    }

    public void setMahalleId(Long mahalleId) {
        this.mahalleId = mahalleId;
    }

    public String getSokakAdi() {
        return sokakAdi;
    }

    public void setSokakAdi(String sokakAdi) {
        this.sokakAdi = sokakAdi;
    }

    public Long getSokakId() {
        return sokakId;
    }

    public void setSokakId(Long sokakId) {
        this.sokakId = sokakId;
    }

    public Long getIlceId() {
        return ilceId;
    }

    public void setIlceId(Long ilceId) {
        this.ilceId = ilceId;
    }
}
