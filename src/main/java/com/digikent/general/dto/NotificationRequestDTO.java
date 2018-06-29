package com.digikent.general.dto;

import java.io.Serializable;

public class NotificationRequestDTO implements Serializable {
    private Long fsm1userId;
    private Long ihr1personelId;


    public Long getFsm1userId() {
        return fsm1userId;
    }

    public void setFsm1userId(Long fsm1userId) {
        this.fsm1userId = fsm1userId;
    }

    public Long getIhr1personelId() {
        return ihr1personelId;
    }

    public void setIhr1personelId(Long ihr1personelId) {
        this.ihr1personelId = ihr1personelId;
    }
}
