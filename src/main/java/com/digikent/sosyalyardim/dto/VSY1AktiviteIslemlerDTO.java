package com.digikent.sosyalyardim.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.util.List;

public class VSY1AktiviteIslemlerDTO {
    private List<VSY1AktiviteIslemlerLineDTO> vsy1AktiviteIslemlerLineDTOList;
    private ErrorDTO errorDTO;

    public VSY1AktiviteIslemlerDTO() {
    }

    public VSY1AktiviteIslemlerDTO(List<VSY1AktiviteIslemlerLineDTO> vsy1AktiviteIslemlerLineDTOList, ErrorDTO errorDTO) {
        this.vsy1AktiviteIslemlerLineDTOList = vsy1AktiviteIslemlerLineDTOList;
        this.errorDTO = errorDTO;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }

    public List<VSY1AktiviteIslemlerLineDTO> getVsy1AktiviteIslemlerLineDTOList() {
        return vsy1AktiviteIslemlerLineDTOList;
    }

    public void setVsy1AktiviteIslemlerLineDTOList(List<VSY1AktiviteIslemlerLineDTO> vsy1AktiviteIslemlerLineDTOList) {
        this.vsy1AktiviteIslemlerLineDTOList = vsy1AktiviteIslemlerLineDTOList;
    }
}
