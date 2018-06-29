package com.digikent.surecyonetimi.izinonay.dto;

/**
 * Created by Kadir on 26.06.2018.
 */
public class IzinSurecKararDTO {

    private Boolean karar;
    private Long instanceId;

    public Boolean getKarar() {
        return karar;
    }

    public void setKarar(Boolean karar) {
        this.karar = karar;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }
}
