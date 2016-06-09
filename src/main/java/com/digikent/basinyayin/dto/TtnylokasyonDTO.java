package com.digikent.basinyayin.dto;

/**
 * Created by Serkan on 4/6/2016.
 */
public class TtnylokasyonDTO {
    private Long id;
    private String tanim;
    private String kodu;
    private Long ttnyBolgeId;

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

    public String getKodu() {
        return kodu;
    }

    public void setKodu(String kodu) {
        this.kodu = kodu;
    }

    public Long getTtnyBolgeId() {
        return ttnyBolgeId;
    }

    public void setTtnyBolgeId(Long ttnyBolgeId) {
        this.ttnyBolgeId = ttnyBolgeId;
    }
}
