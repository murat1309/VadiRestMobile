package com.digikent.ruhsat.dto;

/**
 * Created by Medet on 4/10/2018.
 */
public class RuhsatAdresSorguDTO {
    private Long mahalleId;
    private Long sokakId;
    private String kapiValue;

    public RuhsatAdresSorguDTO() {
    }

    public Long getMahalleId() {
        return mahalleId;
    }

    public void setMahalleId(Long mahalleId) {
        this.mahalleId = mahalleId;
    }

    public Long getSokakId() {
        return sokakId;
    }

    public void setSokakId(Long sokakId) {
        this.sokakId = sokakId;
    }

    public String getKapiValue() {
        return kapiValue;
    }

    public void setKapiValue(String kapiValue) {
        this.kapiValue = kapiValue;
    }
}
