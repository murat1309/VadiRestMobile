package com.digikent.ruhsat.dto;

/**
 * Created by Kadir on 16/10/17.
 */
public class TLI3RuhsatTuruRequestDTO {

    private Long value;
    private String label;

    public TLI3RuhsatTuruRequestDTO() {
    }

    public TLI3RuhsatTuruRequestDTO(Long value, String label) {
        this.value = value;
        this.label = label;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
