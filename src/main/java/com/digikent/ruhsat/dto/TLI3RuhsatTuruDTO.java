package com.digikent.ruhsat.dto;

/**
 * Created by Kadir on 16/10/17.
 */
public class TLI3RuhsatTuruDTO {

    private Long id;
    private String tanim;

    public TLI3RuhsatTuruDTO() {
    }

    public TLI3RuhsatTuruDTO(Long id, String tanim) {
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
