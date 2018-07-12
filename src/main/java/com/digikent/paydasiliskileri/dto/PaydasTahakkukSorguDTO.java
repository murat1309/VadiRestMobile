package com.digikent.paydasiliskileri.dto;

import java.math.BigDecimal;

/**
 * Created by Medet on 1/19/2018.
 */
public class PaydasTahakkukSorguDTO {

    private BigDecimal tahakkukTutar;
    private BigDecimal borcTutar;
    private BigDecimal gecikmeZammiTutar;


    public PaydasTahakkukSorguDTO() {
    }

    public BigDecimal getTahakkukTutar() {
        return tahakkukTutar;
    }

    public void setTahakkukTutar(BigDecimal tahakkukTutar) {
        this.tahakkukTutar = tahakkukTutar;
    }

    public BigDecimal getBorcTutar() {
        return borcTutar;
    }

    public void setBorcTutar(BigDecimal borcTutar) {
        this.borcTutar = borcTutar;
    }

    public BigDecimal getGecikmeZammiTutar() {
        return gecikmeZammiTutar;
    }

    public void setGecikmeZammiTutar(BigDecimal gecikmeZammiTutar) {
        this.gecikmeZammiTutar = gecikmeZammiTutar;
    }
}
