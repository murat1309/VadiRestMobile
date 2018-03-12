package com.digikent.denetimyonetimi.dto.denetim;

import com.digikent.denetimyonetimi.enums.TebligSecenegi;

/**
 * Created by Kadir on 12.03.2018.
 */
public class DenetimTebligDTO {

    private TebligSecenegi tebligSecenegi;
    private String tebligAdi;
    private String tebligSoyadi;
    private Long tebligTC;

    public TebligSecenegi getTebligSecenegi() {
        return tebligSecenegi;
    }

    public void setTebligSecenegi(TebligSecenegi tebligSecenegi) {
        this.tebligSecenegi = tebligSecenegi;
    }

    public String getTebligAdi() {
        return tebligAdi;
    }

    public void setTebligAdi(String tebligAdi) {
        this.tebligAdi = tebligAdi;
    }

    public String getTebligSoyadi() {
        return tebligSoyadi;
    }

    public void setTebligSoyadi(String tebligSoyadi) {
        this.tebligSoyadi = tebligSoyadi;
    }

    public Long getTebligTC() {
        return tebligTC;
    }

    public void setTebligTC(Long tebligTC) {
        this.tebligTC = tebligTC;
    }
}
