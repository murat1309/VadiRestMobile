package com.digikent.denetimyonetimi.dto.util;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.io.Serializable;

/**
 * Created by Kadir on 9.02.2018.
 */
public class UtilDenetimSaveDTO implements Serializable {

    private Boolean isSaved;
    private ErrorDTO errorDTO;
    private Long recordId;

    public UtilDenetimSaveDTO() {};

    public UtilDenetimSaveDTO(Boolean isSaved, ErrorDTO errorDTO, Long recordId) {
        this.isSaved = isSaved;
        this.errorDTO = errorDTO;
        this.recordId = recordId;
    }

    public Boolean getSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
