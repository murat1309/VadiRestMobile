package com.digikent.sosyalyardim.web.dto;

import java.util.Date;

/**
 * Created by Serkan on 8/23/16.
 */
public class SY1TespitDTO {

    private Long id;
    private Long vsy1dosyaId;
    private String izahat;
    private String bilgitelefon;
    private String tespitbilgisi;
    private String tespitveren;
    private String tespitverilistarihi;
    private Long tespitdurumuid;
    private String tespitdurumu;

    public String getTespitdurumu() {
        return tespitdurumu;
    }

    public void setTespitdurumu(String tespitdurumu) {
        this.tespitdurumu = tespitdurumu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVsy1dosyaId() {
        return vsy1dosyaId;
    }

    public void setVsy1dosyaId(Long vsy1dosyaId) {
        this.vsy1dosyaId = vsy1dosyaId;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public String getBilgitelefon() {
        return bilgitelefon;
    }

    public void setBilgitelefon(String bilgitelefon) {
        this.bilgitelefon = bilgitelefon;
    }

    public String getTespitbilgisi() {
        return tespitbilgisi;
    }

    public void setTespitbilgisi(String tespitbilgisi) {
        this.tespitbilgisi = tespitbilgisi;
    }

    public String getTespitveren() {
        return tespitveren;
    }

    public void setTespitveren(String tespitveren) {
        this.tespitveren = tespitveren;
    }

    public String getTespitverilistarihi() {
        return tespitverilistarihi;
    }

    public void setTespitverilistarihi(String tespitverilistarihi) {
        this.tespitverilistarihi = tespitverilistarihi;
    }

    public Long getTespitdurumuid() {
        return tespitdurumuid;
    }

    public void setTespitdurumuid(Long tespitdurumuid) {
        this.tespitdurumuid = tespitdurumuid;
    }
}
