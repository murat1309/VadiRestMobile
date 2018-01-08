package com.digikent.paydasiliskileri.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.math.BigDecimal;

/**
 * Created by Medet on 1/3/2018.
 */
public class PaydasSorguDTO {

    private BigDecimal paydasNo;
    private String adi;
    private String soyAdi;
    private String unvan;
    private String vergiNo;
    private String telefon;
    private String paydasTuru;
    private String tabelaAdi;
    private String izahat;
    private String adres;
    private String kayitDurumu;


    public PaydasSorguDTO() {
    }

    public String getKayitDurumu() {
        return kayitDurumu;
    }

    public void setKayitDurumu(String kayitDurumu) {
        this.kayitDurumu = kayitDurumu;
    }

    public BigDecimal getPaydasNo() {
        return paydasNo;
    }

    public void setPaydasNo(BigDecimal paydasNo) {
        this.paydasNo = paydasNo;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyAdi() {
        return soyAdi;
    }

    public void setSoyAdi(String soyAdi) {
        this.soyAdi = soyAdi;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public String getVergiNo() {
        return vergiNo;
    }

    public void setVergiNo(String vergiNo) {
        this.vergiNo = vergiNo;
    }

    public String getPaydasTuru() {
        return paydasTuru;
    }

    public void setPaydasTuru(String paydasTuru) {
        this.paydasTuru = paydasTuru;
    }

    public String getTabelaAdi() {
        return tabelaAdi;
    }

    public void setTabelaAdi(String tabelaAdi) {
        this.tabelaAdi = tabelaAdi;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
