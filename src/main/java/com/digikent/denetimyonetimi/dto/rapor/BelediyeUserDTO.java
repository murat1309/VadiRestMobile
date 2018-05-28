package com.digikent.denetimyonetimi.dto.rapor;

/**
 * Created by Kadir on 24.02.2018.
 */
public class BelediyeUserDTO {

    private String adiSoyadi;
    private String gorevi;
    private String tarafTuru;
    private Long sicilNo;

    public String getAdiSoyadi() {
        return adiSoyadi;
    }

    public void setAdiSoyadi(String adiSoyadi) {
        this.adiSoyadi = adiSoyadi;
    }

    public String getGorevi() {
        return gorevi;
    }

    public void setGorevi(String gorevi) {
        this.gorevi = gorevi;
    }

    public String getTarafTuru() {
        return tarafTuru;
    }

    public void setTarafTuru(String tarafTuru) {
        this.tarafTuru = tarafTuru;
    }

    public Long getSicilNo() {
        return sicilNo;
    }

    public void setSicilNo(Long sicilNo) {
        this.sicilNo = sicilNo;
    }
}
