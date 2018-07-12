package com.digikent.denetimyonetimi.dto.denetim;

import java.io.Serializable;

/**
 * Created by Kadir on 12.02.2018.
 */
public class DenetimTespitRequest implements Serializable {

    private Long denetimId;
    private Long denetimTuruId;
    private Long tespitGrubuId;
    private Long denetimTespitId;
    private String kararVerilebilirMi;

    public Long getDenetimId() {
        return denetimId;
    }

    public void setDenetimId(Long denetimId) {
        this.denetimId = denetimId;
    }

    public Long getDenetimTuruId() {
        return denetimTuruId;
    }

    public void setDenetimTuruId(Long denetimTuruId) {
        this.denetimTuruId = denetimTuruId;
    }

    public Long getTespitGrubuId() {
        return tespitGrubuId;
    }

    public void setTespitGrubuId(Long tespitGrubuId) {
        this.tespitGrubuId = tespitGrubuId;
    }

    public Long getDenetimTespitId() {
        return denetimTespitId;
    }

    public void setDenetimTespitId(Long denetimTespitId) {
        this.denetimTespitId = denetimTespitId;
    }

    public String getKararVerilebilirMi() {
        return kararVerilebilirMi;
    }

    public void setKararVerilebilirMi(String kararVerilebilirMi) {
        this.kararVerilebilirMi = kararVerilebilirMi;
    }
}
