package com.digikent.denetimyonetimi.dto.paydas;

import java.io.Serializable;

/**
 * Created by Kadir on 25.01.2018.
 */
public class DenetimPaydasRequestDTO implements Serializable {

    private String filter;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
