package com.digikent.sosyalyardim.util;

import com.digikent.mesajlasma.dto.ErrorDTO;

/**
 * Created by Kadir on 18.05.2018.
 */
public class UtilSosyalYardimSaveDTO {

    private Boolean isSaved;
    private ErrorDTO errorDTO;
    private Long recordId;

    public UtilSosyalYardimSaveDTO() {
    }

    public UtilSosyalYardimSaveDTO(Boolean isSaved, ErrorDTO errorDTO, Long recordId) {
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
