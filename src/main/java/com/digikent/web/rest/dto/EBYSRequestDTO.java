package com.digikent.web.rest.dto;

import java.io.Serializable;

public class EBYSRequestDTO implements Serializable {
    private String startDate;
    private String endDate;
    private long fsm1UserId;
    private long rolId;
    private String ada;
    private String parsel;
    private String adSoyad;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getFsm1UserId() {
        return fsm1UserId;
    }

    public void setFsm1UserId(long fsm1UserId) {
        this.fsm1UserId = fsm1UserId;
    }

    public long getRolId() {
        return rolId;
    }

    public void setRolId(long rolId) {
        this.rolId = rolId;
    }

    public String getAda() {
        return ada;
    }

    public void setAda(String ada) {
        this.ada = ada;
    }

    public String getParsel() {
        return parsel;
    }

    public void setParsel(String parsel) {
        this.parsel = parsel;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }
}
