package com.digikent.sosyalyardim.dto;

/**
 * Created by Kadir on 29.05.2018.
 */
public class VSY1TespitLineDTO {

    private String soru;
    private String bilgi;
    private Long deger;
    private String soruTuru;
    private Long soruId;

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getBilgi() {
        return bilgi;
    }

    public void setBilgi(String bilgi) {
        this.bilgi = bilgi;
    }

    public Long getDeger() {
        return deger;
    }

    public void setDeger(Long deger) {
        this.deger = deger;
    }

    public String getSoruTuru() {
        return soruTuru;
    }

    public void setSoruTuru(String soruTuru) {
        this.soruTuru = soruTuru;
    }

    public Long getSoruId() {
        return soruId;
    }

    public void setSoruId(Long soruId) {
        this.soruId = soruId;
    }
}
