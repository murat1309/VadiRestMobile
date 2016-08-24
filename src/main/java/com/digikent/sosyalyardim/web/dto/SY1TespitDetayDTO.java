package com.digikent.sosyalyardim.web.dto;

/**
 * Created by Serkan on 8/24/16.
 */
public class SY1TespitDetayDTO {
    private Long kategoriid;
    private String sorugrubu;
    private Long soruid;
    private String soru;
    private String alanturu;
    private String cbrbdegeri;
    private String stnmdegeri;
    private Integer countcevap;

    public Long getKategoriid() {
        return kategoriid;
    }

    public void setKategoriid(Long kategoriid) {
        this.kategoriid = kategoriid;
    }

    public String getSorugrubu() {
        return sorugrubu;
    }

    public void setSorugrubu(String sorugrubu) {
        this.sorugrubu = sorugrubu;
    }

    public Long getSoruid() {
        return soruid;
    }

    public void setSoruid(Long soruid) {
        this.soruid = soruid;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getAlanturu() {
        return alanturu;
    }

    public void setAlanturu(String alanturu) {
        this.alanturu = alanturu;
    }

    public String getCbrbdegeri() {
        return cbrbdegeri;
    }

    public void setCbrbdegeri(String cbrbdegeri) {
        this.cbrbdegeri = cbrbdegeri;
    }

    public String getStnmdegeri() {
        return stnmdegeri;
    }

    public void setStnmdegeri(String stnmdegeri) {
        this.stnmdegeri = stnmdegeri;
    }

    public Integer getCountcevap() {
        return countcevap;
    }

    public void setCountcevap(Integer countcevap) {
        this.countcevap = countcevap;
    }

}
