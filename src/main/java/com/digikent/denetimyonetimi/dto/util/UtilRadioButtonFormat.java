package com.digikent.denetimyonetimi.dto.util;

import java.io.Serializable;

/**
 * Created by Kadir on 15.02.2018.
 */
public class UtilRadioButtonFormat implements Serializable {

    private String label;
    private String value;

    public UtilRadioButtonFormat() {
    }

    public UtilRadioButtonFormat(String label, String value) {
        this.label = label;
        this.value = value;
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
