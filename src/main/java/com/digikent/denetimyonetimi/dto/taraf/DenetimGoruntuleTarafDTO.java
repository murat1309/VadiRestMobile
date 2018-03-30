package com.digikent.denetimyonetimi.dto.taraf;

/**
 * Created by Medet on 3/27/2018.
 */
public class DenetimGoruntuleTarafDTO {
    private String personelAdi;
    private String personelSoyadi;
    private String personelGorev;
    private String tarafTuru;

    public DenetimGoruntuleTarafDTO() {
    }

    public String getPersonelAdi() {
        return personelAdi;
    }

    public void setPersonelAdi(String personelAdi) {
        this.personelAdi = personelAdi;
    }

    public String getPersonelSoyadi() {
        return personelSoyadi;
    }

    public void setPersonelSoyadi(String personelSoyadi) {
        this.personelSoyadi = personelSoyadi;
    }

    public String getPersonelGorev() {
        return personelGorev;
    }

    public void setPersonelGorev(String personelGorev) {
        this.personelGorev = personelGorev;
    }

    public String getTarafTuru() {
        return tarafTuru;
    }

    public void setTarafTuru(String tarafTuru) {
        this.tarafTuru = tarafTuru;
    }
}
