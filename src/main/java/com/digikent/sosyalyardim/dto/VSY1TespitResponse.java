package com.digikent.sosyalyardim.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.util.List;

/**
 * Created by Kadir on 29.05.2018.
 */
public class VSY1TespitResponse {

    private List<VSY1TespitDTO> vsy1TespitDTOList;
    private ErrorDTO errorDTO;

    public List<VSY1TespitDTO> getVsy1TespitDTOList() {
        return vsy1TespitDTOList;
    }

    public void setVsy1TespitDTOList(List<VSY1TespitDTO> vsy1TespitDTOList) {
        this.vsy1TespitDTOList = vsy1TespitDTOList;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
