package com.digikent.zabita.dto.denetim;

import java.io.Serializable;

/**
 * Created by Serkan on 26.01.2018.
 */
public class ZabitaDenetimResponse implements Serializable {

    private Long paydasId;

    public Long getPaydasId() {
        return paydasId;
    }

    public void setPaydasId(Long paydasId) {
        this.paydasId = paydasId;
    }
}
