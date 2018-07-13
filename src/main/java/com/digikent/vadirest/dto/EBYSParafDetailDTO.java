package com.digikent.vadirest.dto;

import java.io.Serializable;

public class EBYSParafDetailDTO implements Serializable {
    private String tanim;
    private long ebysDocumentId;
    private String turu;
    private String adSoyad;
    private long ihr1PersonelId;

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public long getEbysDocumentId() {
        return ebysDocumentId;
    }

    public void setEbysDocumentId(long ebysDocumentId) {
        this.ebysDocumentId = ebysDocumentId;
    }

    public String getTuru() {
        return turu;
    }

    public void setTuru(String turu) {
        this.turu = turu;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public long getIhr1PersonelId() {
        return ihr1PersonelId;
    }

    public void setIhr1PersonelId(long ihr1PersonelId) {
        this.ihr1PersonelId = ihr1PersonelId;
    }
}
