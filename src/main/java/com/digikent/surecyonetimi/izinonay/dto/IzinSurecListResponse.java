package com.digikent.surecyonetimi.izinonay.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.util.List;

/**
 * Created by Kadir on 13.06.2018.
 */
public class IzinSurecListResponse {

    private List<IzinSurecDTO> izinSurecDTO;
    private ErrorDTO errorDTO;

    public IzinSurecListResponse() {
    }

    public IzinSurecListResponse(List<IzinSurecDTO> izinSurecDTO, ErrorDTO errorDTO) {
        this.izinSurecDTO = izinSurecDTO;
        this.errorDTO = errorDTO;
    }

    public List<IzinSurecDTO> getIzinSurecDTO() {
        return izinSurecDTO;
    }

    public void setIzinSurecDTO(List<IzinSurecDTO> izinSurecDTO) {
        this.izinSurecDTO = izinSurecDTO;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
