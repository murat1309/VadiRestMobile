package com.digikent.zabita.dto.paydas;

import java.io.Serializable;

/**
 * Created by Kadir on 25.01.2018.
 */
public class ZabitaPaydasRequestDTO implements Serializable {

    private String filter;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
