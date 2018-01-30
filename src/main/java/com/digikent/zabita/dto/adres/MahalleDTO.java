package com.digikent.zabita.dto.adres;

import java.io.Serializable;

/**
 * Created by Kadir on 29.01.2018.
 */
public class MahalleDTO implements Serializable {

    private Long id;
    private String tanim;
    private Long rre1IlceId;

    public Long getRre1IlceId() {
        return rre1IlceId;
    }

    public void setRre1IlceId(Long rre1IlceId) {
        this.rre1IlceId = rre1IlceId;
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
}
