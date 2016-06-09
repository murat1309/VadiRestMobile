package com.digikent.basinyayin.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Serkan on 4/1/2016.
 */
public class AsmaIndirmeIslemiDTO {
    private Long id;
    private String tanitimKonusu;
    private Integer tanitimBarkodNumarasi;
    private Date asmaTarihi;
    private Date indirmeTarihi;
    private String lokasyon;
    private String lokasyonAlani;
    private Integer lokasyonAlaniBarkodNumarasi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTanitimKonusu() {
        return tanitimKonusu;
    }

    public void setTanitimKonusu(String tanitimKonusu) {
        this.tanitimKonusu = tanitimKonusu;
    }

    public Integer getTanitimBarkodNumarasi() {
        return tanitimBarkodNumarasi;
    }

    public void setTanitimBarkodNumarasi(Integer tanitimBarkodNumarasi) {
        this.tanitimBarkodNumarasi = tanitimBarkodNumarasi;
    }

    public Date getAsmaTarihi() {
        return asmaTarihi;
    }

    public void setAsmaTarihi(Date asmaTarihi) {
        this.asmaTarihi = asmaTarihi;
    }

    public Date getIndirmeTarihi() {
        return indirmeTarihi;
    }

    public void setIndirmeTarihi(Date indirmeTarihi) {
        this.indirmeTarihi = indirmeTarihi;
    }

    public String getLokasyon() {
        return lokasyon;
    }

    public void setLokasyon(String lokasyon) {
        this.lokasyon = lokasyon;
    }

    public String getLokasyonAlani() {
        return lokasyonAlani;
    }

    public void setLokasyonAlani(String lokasyonAlani) {
        this.lokasyonAlani = lokasyonAlani;
    }

    public Integer getLokasyonAlaniBarkodNumarasi() {
        return lokasyonAlaniBarkodNumarasi;
    }

    public void setLokasyonAlaniBarkodNumarasi(Integer lokasyonAlaniBarkodNumarasi) {
        this.lokasyonAlaniBarkodNumarasi = lokasyonAlaniBarkodNumarasi;
    }
}
