package com.digikent.denetimyonetimi.dto.denetim;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.io.Serializable;

/**
 * Created by Serkan on 26.01.2018.
 */
public class DenetimResponse implements Serializable {

    private Boolean isSuccessful;
    private ErrorDTO errorDTO;

    public Boolean getSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(Boolean successful) {
        isSuccessful = successful;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
