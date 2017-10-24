package com.digikent.ruhsat.dto;

/**
 * Created by Kadir on 23/10/17.
 */
public class RuhsatDurumuDTO {

    public Long paydasId;
    private Long yili;
    private Long dosyaNumarasi;
    private Long kullanimAlani;
    private String adiSoyadi;
    private String isyeriAnaFaaliyet;
    private String mahalle;
    private String sokak;
    private String kapi;
    private String daire;
    private String ruhsatTuru;
    private String ruhsatDurumu;

    public Long getPaydasId() {
        return paydasId;
    }

    public void setPaydasId(Long paydasId) {
        this.paydasId = paydasId;
    }

    public Long getDosyaNumarasi() {
        return dosyaNumarasi;
    }

    public void setDosyaNumarasi(Long dosyaNumarasi) {
        this.dosyaNumarasi = dosyaNumarasi;
    }

    public Long getYili() {
        return yili;
    }

    public void setYili(Long yili) {
        this.yili = yili;
    }

    public Long getKullanimAlani() {
        return kullanimAlani;
    }

    public void setKullanimAlani(Long kullanimAlani) {
        this.kullanimAlani = kullanimAlani;
    }

    public String getAdiSoyadi() {
        return adiSoyadi;
    }

    public void setAdiSoyadi(String adiSoyadi) {
        this.adiSoyadi = adiSoyadi;
    }

    public String getIsyeriAnaFaaliyet() {
        return isyeriAnaFaaliyet;
    }

    public void setIsyeriAnaFaaliyet(String isyeriAnaFaaliyet) {
        this.isyeriAnaFaaliyet = isyeriAnaFaaliyet;
    }

    public String getMahalle() {
        return mahalle;
    }

    public void setMahalle(String mahalle) {
        this.mahalle = mahalle;
    }

    public String getSokak() {
        return sokak;
    }

    public void setSokak(String sokak) {
        this.sokak = sokak;
    }

    public String getKapi() {
        return kapi;
    }

    public void setKapi(String kapi) {
        this.kapi = kapi;
    }

    public String getDaire() {
        return daire;
    }

    public void setDaire(String daire) {
        this.daire = daire;
    }

    public String getRuhsatTuru() {
        return ruhsatTuru;
    }

    public void setRuhsatTuru(String ruhsatTuru) {
        this.ruhsatTuru = ruhsatTuru;
    }

    public String getRuhsatDurumu() {
        return ruhsatDurumu;
    }

    public void setRuhsatDurumu(String ruhsatDurumu) {
        this.ruhsatDurumu = ruhsatDurumu;
    }
}
