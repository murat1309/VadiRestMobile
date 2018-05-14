package com.digikent.sosyalyardim.yeni.dto;

/**
 * Created by Kadir on 10.05.2018.
 */
public class SYS1DosyaRequest {

    private String paydasFilter;
    private String dosyaFilter;

    public String getPaydasFilter() {
        return paydasFilter;
    }

    public void setPaydasFilter(String paydasFilter) {
        this.paydasFilter = paydasFilter;
    }

    public String getDosyaFilter() {
        return dosyaFilter;
    }

    public void setDosyaFilter(String dosyaFilter) {
        this.dosyaFilter = dosyaFilter;
    }
}
