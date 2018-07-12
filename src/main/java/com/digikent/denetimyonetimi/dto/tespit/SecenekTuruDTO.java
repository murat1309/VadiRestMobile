package com.digikent.denetimyonetimi.dto.tespit;

import com.digikent.denetimyonetimi.dto.util.UtilRadioButtonFormat;

import java.io.Serializable;

/**
 * Created by Kadir on 2.02.2018.
 */
public class SecenekTuruDTO implements Serializable {

    private Long id;
    private Long sirasi;
    private String degeri;
    private Long tespitId;
    private String label;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSirasi() {
        return sirasi;
    }

    public void setSirasi(Long sirasi) {
        this.sirasi = sirasi;
    }

    public String getDegeri() {
        return degeri;
    }

    public void setDegeri(String degeri) {
        this.degeri = degeri;
    }

    public Long getTespitId() {
        return tespitId;
    }

    public void setTespitId(Long tespitId) {
        this.tespitId = tespitId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
