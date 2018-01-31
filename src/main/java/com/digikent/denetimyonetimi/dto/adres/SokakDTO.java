package com.digikent.denetimyonetimi.dto.adres;

import java.io.Serializable;

/**
 * Created by Kadir on 29.01.2018.
 */
public class SokakDTO implements Serializable {

    private Long id;
    private String tanim;

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
