package com.digikent.denetimyonetimi.dto.paydas;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kadir on 8.02.2018.
 */
public class DenetimPaydasSaveResponseDTO implements Serializable {

    private ErrorDTO errorDTO;
    private List<DenetimIsletmeDTO> denetimIsletmeDTOList;
    private Long paydasId;

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }

    public List<DenetimIsletmeDTO> getDenetimIsletmeDTOList() {
        return denetimIsletmeDTOList;
    }

    public void setDenetimIsletmeDTOList(List<DenetimIsletmeDTO> denetimIsletmeDTOList) {
        this.denetimIsletmeDTOList = denetimIsletmeDTOList;
    }

    public Long getPaydasId() {
        return paydasId;
    }

    public void setPaydasId(Long paydasId) {
        this.paydasId = paydasId;
    }
}
