package com.digikent.denetimyonetimi.dto.tespit;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kadir on 13.02.2018.
 */
public class TespitlerRequest implements Serializable {

    private Long denetimTespitId;
    List<TespitSaveDTO> tespitSaveDTOList;
    private Boolean isSave; //ilk kayitta true, güncelleme olursa false gelmesi bekleniyor.

    public Long getDenetimTespitId() {
        return denetimTespitId;
    }

    public void setDenetimTespitId(Long denetimTespitId) {
        this.denetimTespitId = denetimTespitId;
    }

    public List<TespitSaveDTO> getTespitSaveDTOList() {
        return tespitSaveDTOList;
    }

    public void setTespitSaveDTOList(List<TespitSaveDTO> tespitSaveDTOList) {
        this.tespitSaveDTOList = tespitSaveDTOList;
    }

    public Boolean getSave() {
        return isSave;
    }

    public void setSave(Boolean save) {
        isSave = save;
    }
}
