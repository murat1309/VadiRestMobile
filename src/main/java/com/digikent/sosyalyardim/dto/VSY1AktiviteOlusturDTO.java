package com.digikent.sosyalyardim.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

public class VSY1AktiviteOlusturDTO {

    private Long aktiviteId;
    private ErrorDTO errorDTO;

    public VSY1AktiviteOlusturDTO() {
    }
    public VSY1AktiviteOlusturDTO(Long aktiviteId, ErrorDTO errorDTO) {
        this.aktiviteId = aktiviteId;
        this.errorDTO = errorDTO;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }


    public Long getAktiviteId() {
        return aktiviteId;
    }

    public void setAktiviteId(Long aktiviteId) {
        this.aktiviteId = aktiviteId;
    }


}
