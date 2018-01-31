package com.digikent.denetimyonetimi.dto.paydas;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Serkan on 25.01.2018.
 */
public class DenetimPaydasResponseDTO implements Serializable {

    private List<DenetimPaydasDTO> responseDenetimPaydasList;
    private ErrorDTO errorDTO;



    public List<DenetimPaydasDTO> getResponseDenetimPaydasList() {
        return responseDenetimPaydasList;
    }

    public void setResponseDenetimPaydasList(List<DenetimPaydasDTO> responseDenetimPaydasList) {
        this.responseDenetimPaydasList = responseDenetimPaydasList;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
