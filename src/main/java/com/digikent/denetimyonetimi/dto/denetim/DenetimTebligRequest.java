package com.digikent.denetimyonetimi.dto.denetim;

import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;

/**
 * Created by Kadir on 12.03.2018.
 */
public class DenetimTebligRequest {

    private DenetimTebligDTO denetimTebligDTO;
    private Long bdntDenetimId;
    private DenetimPaydasDTO denetimPaydasDTO;

    public DenetimTebligDTO getDenetimTebligDTO() {
        return denetimTebligDTO;
    }

    public void setDenetimTebligDTO(DenetimTebligDTO denetimTebligDTO) {
        this.denetimTebligDTO = denetimTebligDTO;
    }

    public Long getBdntDenetimId() {
        return bdntDenetimId;
    }

    public void setBdntDenetimId(Long bdntDenetimId) {
        this.bdntDenetimId = bdntDenetimId;
    }

    public DenetimPaydasDTO getDenetimPaydasDTO() {
        return denetimPaydasDTO;
    }

    public void setDenetimPaydasDTO(DenetimPaydasDTO denetimPaydasDTO) {
        this.denetimPaydasDTO = denetimPaydasDTO;
    }
}
