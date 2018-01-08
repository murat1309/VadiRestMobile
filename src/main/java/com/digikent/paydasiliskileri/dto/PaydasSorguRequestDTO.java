package com.digikent.paydasiliskileri.dto;

import java.math.BigDecimal;

/**
 * Created by Medet on 1/2/2018.
 */
public class PaydasSorguRequestDTO {
    private String tcNo;
    private String paydasNo;
    private String sorguAdi;
    private String vergiNo;

    public PaydasSorguRequestDTO() {
    }

    public String getTcNo() {
        return tcNo;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }

    public String getPaydasNo() {
        return paydasNo;
    }

    public void setPaydasNo(String paydasNo) {
        this.paydasNo = paydasNo;
    }

    public String getSorguAdi() {
        return sorguAdi;
    }

    public void setSorguAdi(String sorguAdi) {
        this.sorguAdi = sorguAdi;
    }

    public String getVergiNo() {
        return vergiNo;
    }

    public void setVergiNo(String vergiNo) {
        this.vergiNo = vergiNo;
    }
}
