package com.digikent.surecyonetimi.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.util.List;

/**
 * Created by Medet on 12/28/2017.
 */
public class SurecSorguResponseDTO {

    private List<SurecSorguDTO> surecSorguDTOList;
    private SurecInfoDTO surecInfoDTO;
    private ErrorDTO errorDTO;

    public SurecSorguResponseDTO() {
    }

    public List<SurecSorguDTO> getSurecSorguDTOList() {
        return surecSorguDTOList;
    }

    public void setSurecSorguDTOList(List<SurecSorguDTO> surecSorguDTOList) {
        this.surecSorguDTOList = surecSorguDTOList;
    }

    public SurecInfoDTO getSurecInfoDTO() {
        return surecInfoDTO;
    }

    public void setSurecInfoDTO(SurecInfoDTO surecInfoDTO) {
        this.surecInfoDTO = surecInfoDTO;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
