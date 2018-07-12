package com.digikent.surecyonetimi.izinonay.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.util.List;

/**
 * Created by Kadir on 13.06.2018.
 */
public class IzinSurecListResponse {

    private List<IzinSurecDTO> izinSurecDTOList;
    private ErrorDTO errorDTO;

    public IzinSurecListResponse() {
    }

    public IzinSurecListResponse(List<IzinSurecDTO> izinSurecDTOList, ErrorDTO errorDTO) {
        this.izinSurecDTOList = izinSurecDTOList;
        this.errorDTO = errorDTO;
    }

    public List<IzinSurecDTO> getIzinSurecDTOList() {
        return izinSurecDTOList;
    }

    public void setIzinSurecDTOList(List<IzinSurecDTO> izinSurecDTOList) {
        this.izinSurecDTOList = izinSurecDTOList;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
