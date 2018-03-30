package com.digikent.denetimyonetimi.dto.rapor;

/**
 * Created by Kadir on 13.03.2018.
 */
public class TebligEdilenDTO {

    private String adi;
    private String soyadi;
    private Long TCKimlikNo;

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

    public Long getTCKimlikNo() {
        return TCKimlikNo;
    }

    public void setTCKimlikNo(Long TCKimlikNo) {
        this.TCKimlikNo = TCKimlikNo;
    }
}
