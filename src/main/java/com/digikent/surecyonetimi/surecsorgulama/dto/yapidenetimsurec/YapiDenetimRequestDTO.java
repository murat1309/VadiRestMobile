package com.digikent.surecyonetimi.surecsorgulama.dto.yapidenetimsurec;


import com.digikent.surecyonetimi.surecsorgulama.dto.imarsurec.BasvuruTuruRequestDTO;

import java.util.List;

public class YapiDenetimRequestDTO {

    private List<BasvuruTuruRequestDTO> yapiDenetimBasvuruTuruRequestDTOList;
    private DenetimParametersRequestDTO denetimParametersRequestDTO;

    public List<BasvuruTuruRequestDTO> getYapiDenetimBasvuruTuruRequestDTOList() {
        return yapiDenetimBasvuruTuruRequestDTOList;
    }

    public void setYapiDenetimBasvuruTuruRequestDTOList(List<BasvuruTuruRequestDTO> yapiDenetimBasvuruTuruRequestDTOList) {
        this.yapiDenetimBasvuruTuruRequestDTOList = yapiDenetimBasvuruTuruRequestDTOList;
    }

    public DenetimParametersRequestDTO getDenetimParametersRequestDTO() {
        return denetimParametersRequestDTO;
    }

    public void setDenetimParametersRequestDTO(DenetimParametersRequestDTO denetimParametersRequestDTO) {
        this.denetimParametersRequestDTO = denetimParametersRequestDTO;
    }
}
