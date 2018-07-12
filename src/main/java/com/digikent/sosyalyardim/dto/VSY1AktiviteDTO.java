package com.digikent.sosyalyardim.dto;


public class VSY1AktiviteDTO {

    private Long aktiviteId;
    private String baslamaTarihi;
    private String ihr1PersonelVeren;
    private String ihr1PersonelVerilen;
    private String tsy1AktiviteIslem;

    public Long getAktiviteId() {
        return aktiviteId;
    }

    public void setAktiviteId(Long aktiviteId) {
        this.aktiviteId = aktiviteId;
    }
    public String getBaslamaTarihi() {
        return baslamaTarihi;
    }

    public void setBaslamaTarihi(String baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public String getIhr1PersonelVeren() {
        return ihr1PersonelVeren;
    }

    public void setIhr1PersonelVeren(String ihr1PersonelVeren) {
        this.ihr1PersonelVeren = ihr1PersonelVeren;
    }

    public String getIhr1PersonelVerilen() {
        return ihr1PersonelVerilen;
    }

    public void setIhr1PersonelVerilen(String ihr1PersonelVerilen) {
        this.ihr1PersonelVerilen = ihr1PersonelVerilen;
    }

    public String getTsy1AktiviteIslem() {
        return tsy1AktiviteIslem;
    }

    public void setTsy1AktiviteIslem(String tsy1AktiviteIslem) {
        this.tsy1AktiviteIslem = tsy1AktiviteIslem;
    }
}
