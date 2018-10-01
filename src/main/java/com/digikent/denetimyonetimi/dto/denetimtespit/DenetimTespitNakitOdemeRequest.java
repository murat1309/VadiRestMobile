package com.digikent.denetimyonetimi.dto.denetimtespit;

/**
 * Created by Kadir on 28.08.2018.
 */
public class DenetimTespitNakitOdemeRequest {

    private Long denetimTespitId;
    private Boolean isNakitOdeme;

    public Long getDenetimTespitId() {
        return denetimTespitId;
    }

    public void setDenetimTespitId(Long denetimTespitId) {
        this.denetimTespitId = denetimTespitId;
    }

    public Boolean getNakitOdeme() {
        return isNakitOdeme;
    }

    public void setNakitOdeme(Boolean nakitOdeme) {
        isNakitOdeme = nakitOdeme;
    }
}
