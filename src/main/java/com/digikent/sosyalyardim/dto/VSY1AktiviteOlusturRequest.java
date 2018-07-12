package com.digikent.sosyalyardim.dto;

import com.digikent.sosyalyardim.entity.TSY1AktiviteIslem;

public class VSY1AktiviteOlusturRequest {
    private Long dosyaId;
    private Long fsm1UsersId;
    private Long ihr1PersonelVerenId;
    private Long ihr1PersonelVerilenId;
    private Long tsy1AktiviteIslemId;

    public Long getTsy1AktiviteIslemId() {
        return tsy1AktiviteIslemId;
    }

    public void setTsy1AktiviteIslemId(Long tsy1AktiviteIslemId) {
        this.tsy1AktiviteIslemId = tsy1AktiviteIslemId;
    }

    public Long getIhr1PersonelVerenId() {
        return ihr1PersonelVerenId;
    }

    public void setIhr1PersonelVerenId(Long ihr1PersonelVerenId) {
        this.ihr1PersonelVerenId = ihr1PersonelVerenId;
    }

    public Long getIhr1PersonelVerilenId() {
        return ihr1PersonelVerilenId;
    }

    public void setIhr1PersonelVerilenId(Long ihr1PersonelVerilenId) {
        this.ihr1PersonelVerilenId = ihr1PersonelVerilenId;
    }

    public Long getDosyaId() {
        return dosyaId;
    }

    public void setDosyaId(Long dosyaId) {
        this.dosyaId = dosyaId;
    }

    public Long getFsm1UsersId() {
        return fsm1UsersId;
    }

    public void setFsm1UsersId(Long fsm1UsersId) {
        this.fsm1UsersId = fsm1UsersId;
    }
}
