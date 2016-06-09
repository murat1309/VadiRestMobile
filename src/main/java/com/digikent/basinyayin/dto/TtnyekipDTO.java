package com.digikent.basinyayin.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Serkan on 4/4/2016.
 */
public class TtnyekipDTO {


    private Long id;
    private String isActive;
    private String kodu;
    private String tanim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getKodu() {
        return kodu;
    }

    public void setKodu(String kodu) {
        this.kodu = kodu;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }


}
