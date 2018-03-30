package com.digikent.denetimyonetimi.dto.denetim;

/**
 * Created by Medet on 3/21/2018.
 */
public class DenetimObjectRequestDTO {

    private Long denetimId;
    private Long paydasId;
    private Long denetimTespitId;
    private Long tespitGrubuId;

    public DenetimObjectRequestDTO() {
    }

    public Long getDenetimId() {
        return denetimId;
    }

    public void setDenetimId(Long denetimId) {
        this.denetimId = denetimId;
    }

    public Long getPaydasId() {
        return paydasId;
    }

    public void setPaydasId(Long paydasId) {
        this.paydasId = paydasId;
    }

    public Long getDenetimTespitId() {
        return denetimTespitId;
    }

    public void setDenetimTespitId(Long denetimTespitId) {
        this.denetimTespitId = denetimTespitId;
    }

    public Long getTespitGrubuId() {
        return tespitGrubuId;
    }

    public void setTespitGrubuId(Long tespitGrubuId) {
        this.tespitGrubuId = tespitGrubuId;
    }
}
