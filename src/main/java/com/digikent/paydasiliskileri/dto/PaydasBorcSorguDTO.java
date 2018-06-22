package com.digikent.paydasiliskileri.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Medet on 1/4/2018.
 */
public class PaydasBorcSorguDTO {
    private Date tarihIslem;
    private String gelirAdi;
    private BigDecimal borcTutar;
    private BigDecimal gecikmeTutar;

    public PaydasBorcSorguDTO() {
    }

    public Date getTarihIslem() {
        return tarihIslem;
    }

    public void setTarihIslem(Date tarihIslem) {
        this.tarihIslem = tarihIslem;
    }

    public String getGelirAdi() {
        return gelirAdi;
    }

    public void setGelirAdi(String gelirAdi) {
        this.gelirAdi = gelirAdi;
    }

    public BigDecimal getBorcTutar() {
        return borcTutar;
    }

    public void setBorcTutar(BigDecimal borcTutar) {
        this.borcTutar = borcTutar;
    }

    public BigDecimal getGecikmeTutar() {
        return gecikmeTutar;
    }

    public void setGecikmeTutar(BigDecimal gecikmeTutar) {
        this.gecikmeTutar = gecikmeTutar;
    }
}
