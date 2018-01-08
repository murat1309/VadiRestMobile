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
}
