package com.digikent.ruhsat.dto;

/**
 * Created by Kadir on 17/10/17.
 */
public class DRE1MahalleDTO {

    private Long id;
    private String tanim;

    public DRE1MahalleDTO() {
    }

    public DRE1MahalleDTO(Long id, String tanim) {
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
