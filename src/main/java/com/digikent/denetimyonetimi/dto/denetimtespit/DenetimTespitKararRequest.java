package com.digikent.denetimyonetimi.dto.denetimtespit;

import com.digikent.denetimyonetimi.enums.DenetimTespitKararAksiyon;

import java.util.Date;

/**
 * Created by Kadir on 16.03.2018.
 */
public class DenetimTespitKararRequest {

    private Long denetimTespitId;
    private DenetimTespitKararAksiyon aksiyon;
    private Long cezaMiktari;
    private Date kapamaBaslangicTarihi;
    private Date kapamaBitisTarihi;
    private Long ekSure;

    public Long getDenetimTespitId() {
        return denetimTespitId;
    }

    public void setDenetimTespitId(Long denetimTespitId) {
        this.denetimTespitId = denetimTespitId;
    }

    public DenetimTespitKararAksiyon getAksiyon() {
        return aksiyon;
    }

    public void setAksiyon(DenetimTespitKararAksiyon aksiyon) {
        this.aksiyon = aksiyon;
    }

    public Long getCezaMiktari() {
        return cezaMiktari;
    }

    public void setCezaMiktari(Long cezaMiktari) {
        this.cezaMiktari = cezaMiktari;
    }

    public Date getKapamaBaslangicTarihi() {
        return kapamaBaslangicTarihi;
    }

    public void setKapamaBaslangicTarihi(Date kapamaBaslangicTarihi) {
        this.kapamaBaslangicTarihi = kapamaBaslangicTarihi;
    }

    public Date getKapamaBitisTarihi() {
        return kapamaBitisTarihi;
    }

    public void setKapamaBitisTarihi(Date kapamaBitisTarihi) {
        this.kapamaBitisTarihi = kapamaBitisTarihi;
    }

    public Long getEkSure() {
        return ekSure;
    }

    public void setEkSure(Long ekSure) {
        this.ekSure = ekSure;
    }
}
