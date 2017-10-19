package com.digikent.ruhsat.dto;

/**
 * Created by Kadir on 17/10/17.
 */
public class SRE1SokakDTO {

    private Long id;
    private String tanim;

    public SRE1SokakDTO() {
    }

    public SRE1SokakDTO(Long id, String tanim) {
        this.id = id;
        this.tanim = tanim;
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
