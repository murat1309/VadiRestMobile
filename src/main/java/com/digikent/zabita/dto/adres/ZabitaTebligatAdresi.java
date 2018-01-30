package com.digikent.zabita.dto.adres;

import java.io.Serializable;

/**
 * Created by Kadir on 28.01.2018.
 */
public class ZabitaTebligatAdresi implements Serializable {

    private String siteAdiTebligat;
    private String blokNotebligat;
    private String kapiHarfNoTebligat;
    private Long kapiNoSayiTebligat;
    private String daireNoHarfTebligat;
    private Long daireNoSayiTebligat;
    private Long rre1ilceTebligat;
    private Long dre1MahalleTebligat;
    private Long sre1SokakTebligat;


    public String getSiteAdiTebligat() {
        return siteAdiTebligat;
    }

    public void setSiteAdiTebligat(String siteAdiTebligat) {
        this.siteAdiTebligat = siteAdiTebligat;
    }

    public String getBlokNotebligat() {
        return blokNotebligat;
    }

    public void setBlokNotebligat(String blokNotebligat) {
        this.blokNotebligat = blokNotebligat;
    }

    public String getKapiHarfNoTebligat() {
        return kapiHarfNoTebligat;
    }

    public void setKapiHarfNoTebligat(String kapiHarfNoTebligat) {
        this.kapiHarfNoTebligat = kapiHarfNoTebligat;
    }

    public String getDaireNoHarfTebligat() {
        return daireNoHarfTebligat;
    }

    public void setDaireNoHarfTebligat(String daireNoHarfTebligat) {
        this.daireNoHarfTebligat = daireNoHarfTebligat;
    }

    public Long getRre1ilceTebligat() {
        return rre1ilceTebligat;
    }

    public void setRre1ilceTebligat(Long rre1ilceTebligat) {
        this.rre1ilceTebligat = rre1ilceTebligat;
    }

    public Long getDre1MahalleTebligat() {
        return dre1MahalleTebligat;
    }

    public void setDre1MahalleTebligat(Long dre1MahalleTebligat) {
        this.dre1MahalleTebligat = dre1MahalleTebligat;
    }

    public Long getSre1SokakTebligat() {
        return sre1SokakTebligat;
    }

    public void setSre1SokakTebligat(Long sre1SokakTebligat) {
        this.sre1SokakTebligat = sre1SokakTebligat;
    }

    public Long getKapiNoSayiTebligat() {
        return kapiNoSayiTebligat;
    }

    public void setKapiNoSayiTebligat(Long kapiNoSayiTebligat) {
        this.kapiNoSayiTebligat = kapiNoSayiTebligat;
    }

    public Long getDaireNoSayiTebligat() {
        return daireNoSayiTebligat;
    }

    public void setDaireNoSayiTebligat(Long daireNoSayiTebligat) {
        this.daireNoSayiTebligat = daireNoSayiTebligat;
    }
}
