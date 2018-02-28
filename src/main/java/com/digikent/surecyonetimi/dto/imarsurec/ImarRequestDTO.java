package com.digikent.surecyonetimi.dto.imarsurec;

import java.util.List;

public class ImarRequestDTO {

    private List<BasvuruTuruRequestDTO> imarBasvuruTuruRequestDTOList;
    private ImarSurecRequestDTO imarSurecRequestDTO;

    public ImarRequestDTO() {
    }

    public List<BasvuruTuruRequestDTO> getImarBasvuruTuruRequestDTOList() {
        return imarBasvuruTuruRequestDTOList;
    }

    public void setImarBasvuruTuruRequestDTOList(List<BasvuruTuruRequestDTO> imarBasvuruTuruRequestDTOList) {
        this.imarBasvuruTuruRequestDTOList = imarBasvuruTuruRequestDTOList;
    }

    public ImarSurecRequestDTO getImarSurecRequestDTO() {
        return imarSurecRequestDTO;
    }

    public void setImarSurecRequestDTO(ImarSurecRequestDTO imarSurecRequestDTO) {
        this.imarSurecRequestDTO = imarSurecRequestDTO;
    }
}
