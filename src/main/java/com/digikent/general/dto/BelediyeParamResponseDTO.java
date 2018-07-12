package com.digikent.general.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

/**
 * Created by Medet on 4/9/2018.
 */
public class BelediyeParamResponseDTO {

    private BelediyeParamsDTO belediyeParamsDTO;
    private ErrorDTO errorDTO;

    public BelediyeParamResponseDTO() {
    }

    public BelediyeParamsDTO getBelediyeParamsDTO() {
        return belediyeParamsDTO;
    }

    public void setBelediyeParamsDTO(BelediyeParamsDTO belediyeParamsDTO) {
        this.belediyeParamsDTO = belediyeParamsDTO;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
