package com.digikent.denetimyonetimi.dto.tespit;

import java.io.Serializable;

/**
 * Created by Serkan on 14.02.2018.
 */
public class TespitTarifeDTO implements Serializable {

    private Long id;
    private Long altLimitTutari;
    private Long ustLimitTutari;
    private Long tespitId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAltLimitTutari() {
        return altLimitTutari;
    }

    public void setAltLimitTutari(Long altLimitTutari) {
        this.altLimitTutari = altLimitTutari;
    }

    public Long getUstLimitTutari() {
        return ustLimitTutari;
    }

    public void setUstLimitTutari(Long ustLimitTutari) {
        this.ustLimitTutari = ustLimitTutari;
    }

    public Long getTespitId() {
        return tespitId;
    }

    public void setTespitId(Long tespitId) {
        this.tespitId = tespitId;
    }
}
