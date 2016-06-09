package com.digikent.vadirest.dto;

import java.util.Date;

/**
 * Created by Serkan on 4/15/16.
 */
public class FirmaOdeme {

    private long yevmiyeNumarasi;
    private Date yevmiyeTarihi;
    private String izahat;
    private double tutar;

    public long getYevmiyeNumarasi() {
        return yevmiyeNumarasi;
    }

    public void setYevmiyeNumarasi(long yevmiyeNumarasi) {
        this.yevmiyeNumarasi = yevmiyeNumarasi;
    }

    public Date getYevmiyeTarihi() {
        return yevmiyeTarihi;
    }

    public void setYevmiyeTarihi(Date yevmiyeTarihi) {
        this.yevmiyeTarihi = yevmiyeTarihi;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public double getTutar() {
        return tutar;
    }

    public void setTutar(double tutar) {
        this.tutar = tutar;
    }
}
