package com.digikent.paydasiliskileri.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Medet on 1/5/2018.
 *
 *
 *
 * Name        Type (DB)
 ----------- ---------
 KAYITTARIHI DATE
 TARIFETURU  NUMBER
 TABELAENI   NUMBER
 BOY         NUMBER
 TABELAYUZU  NUMBER
 ILANADEDI   NUMBER
 ILANALANI   NUMBER
 IZAHAT      VARCHAR2

 */
public class PaydasIlanSorguDTO {
    private Date kayitTarihi;
    private BigDecimal tarifeTuru;
    private BigDecimal tabelaEni;
    private BigDecimal tabelaBoyu;
    private BigDecimal tabelaYuzu;
    private BigDecimal ilanAdedi;
    private BigDecimal ilanAlani;
    private String izahat;


    public PaydasIlanSorguDTO() {
    }

    public Date getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(Date kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    public BigDecimal getTarifeTuru() {
        return tarifeTuru;
    }

    public void setTarifeTuru(BigDecimal tarifeTuru) {
        this.tarifeTuru = tarifeTuru;
    }

    public BigDecimal getTabelaEni() {
        return tabelaEni;
    }

    public void setTabelaEni(BigDecimal tabelaEni) {
        this.tabelaEni = tabelaEni;
    }

    public BigDecimal getTabelaBoyu() {
        return tabelaBoyu;
    }

    public void setTabelaBoyu(BigDecimal tabelaBoyu) {
        this.tabelaBoyu = tabelaBoyu;
    }

    public BigDecimal getTabelaYuzu() {
        return tabelaYuzu;
    }

    public void setTabelaYuzu(BigDecimal tabelaYuzu) {
        this.tabelaYuzu = tabelaYuzu;
    }

    public BigDecimal getIlanAdedi() {
        return ilanAdedi;
    }

    public void setIlanAdedi(BigDecimal ilanAdedi) {
        this.ilanAdedi = ilanAdedi;
    }

    public BigDecimal getIlanAlani() {
        return ilanAlani;
    }

    public void setIlanAlani(BigDecimal ilanAlani) {
        this.ilanAlani = ilanAlani;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }
}
