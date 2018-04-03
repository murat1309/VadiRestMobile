package com.digikent.denetimyonetimi.dto.rapor;

/**
 * Created by Kadir on 13.03.2018.
 */
public class TebligEdilenDTO {

    private String tebligSecenegi;
    private String adi;
    private String soyadi;
    private String TCKimlikNo;
    private String imtina;
    private String paydasYok;

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public String getTCKimlikNo() {
        return TCKimlikNo;
    }

    public void setTCKimlikNo(String TCKimlikNo) {
        this.TCKimlikNo = TCKimlikNo;
    }

    public String getTebligSecenegi() {
        return tebligSecenegi;
    }

    public void setTebligSecenegi(String tebligSecenegi) {
        this.tebligSecenegi = tebligSecenegi;
    }

    public String getImtina() {
        return imtina;
    }

    public void setImtina(String imtina) {
        this.imtina = imtina;
    }

    public String getPaydasYok() {
        return paydasYok;
    }

    public void setPaydasYok(String paydasYok) {
        this.paydasYok = paydasYok;
    }
}
