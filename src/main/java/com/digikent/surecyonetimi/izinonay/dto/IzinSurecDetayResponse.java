package com.digikent.surecyonetimi.izinonay.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

/**
 * Created by Kadir on 19.06.2018.
 */
public class IzinSurecDetayResponse {

    private IzinSurecDetayDTO izinSurecDetayDTO;
    private ErrorDTO errorDTO;

    public IzinSurecDetayResponse() {
    }

    public IzinSurecDetayResponse(IzinSurecDetayDTO izinSurecDetayDTO, ErrorDTO errorDTO) {
        this.izinSurecDetayDTO = izinSurecDetayDTO;
        this.errorDTO = errorDTO;
    }

    public IzinSurecDetayDTO getIzinSurecDetayDTO() {
        return izinSurecDetayDTO;
    }

    public void setIzinSurecDetayDTO(IzinSurecDetayDTO izinSurecDetayDTO) {
        this.izinSurecDetayDTO = izinSurecDetayDTO;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
