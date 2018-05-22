package com.digikent.sosyalyardim.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.util.List;

/**
 * Created by Kadir on 14.05.2018.
 */
public class VSY1TespitSorulariResponse {

    private List<TSY1TespitKategoriDTO> tespitKategoriDTOList;
    private ErrorDTO errorDTO;

    public VSY1TespitSorulariResponse() {
    }

    public VSY1TespitSorulariResponse(List<TSY1TespitKategoriDTO> tespitKategoriDTOList, ErrorDTO errorDTO) {
        this.tespitKategoriDTOList = tespitKategoriDTOList;
        this.errorDTO = errorDTO;
    }

    public List<TSY1TespitKategoriDTO> getTespitKategoriDTOList() {
        return tespitKategoriDTOList;
    }

    public void setTespitKategoriDTOList(List<TSY1TespitKategoriDTO> tespitKategoriDTOList) {
        this.tespitKategoriDTOList = tespitKategoriDTOList;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
