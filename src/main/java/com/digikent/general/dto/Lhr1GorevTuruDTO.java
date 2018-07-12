package com.digikent.general.dto;

import java.io.Serializable;

/**
 * Created by Kadir on 27.02.2018.
 */
public class Lhr1GorevTuruDTO implements Serializable {

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
