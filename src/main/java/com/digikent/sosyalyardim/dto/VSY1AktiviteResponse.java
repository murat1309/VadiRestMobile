package com.digikent.sosyalyardim.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.util.List;

public class VSY1AktiviteResponse {

    public List<VSY1AktiviteDTO> getVsy1AktiviteListDTO() {
        return vsy1AktiviteListDTO;
    }

    public void setVsy1AktiviteListDTO(List<VSY1AktiviteDTO> vsy1AktiviteListDTO) {
        this.vsy1AktiviteListDTO = vsy1AktiviteListDTO;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }

    private List<VSY1AktiviteDTO> vsy1AktiviteListDTO;
    private ErrorDTO errorDTO;

}
