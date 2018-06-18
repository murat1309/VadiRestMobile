package com.digikent.surecyonetimi.surecsorgulama.dto.basvurudetay;

/**
 * Created by Medet on 12/28/2017.
 */
public class SurecSorguDTO {

    private String surecAdimi;
    private String personelIsmi;
    private String bitisTarihi;

    public SurecSorguDTO() {
    }

    public String getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(String bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public String getSurecAdimi() {
        return surecAdimi;
    }

    public void setSurecAdimi(String surecAdimi) {
        this.surecAdimi = surecAdimi;
    }

    public String getPersonelIsmi() {
        return personelIsmi;
    }

    public void setPersonelIsmi(String personelIsmi) {
        this.personelIsmi = personelIsmi;
    }


}
