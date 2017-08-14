package com.digikent.demirbas.dto;

import java.util.List;

/**
 * Created by Serkan on 17/07/17.
 */
public class DemirbasResponseDTO {

    private DemirbasDTO demirbasDTO;
    private List<DemirbasHareketDTO> demirbasHareketDTOList;

    public DemirbasDTO getDemirbasDTO() {
        return demirbasDTO;
    }

    public void setDemirbasDTO(DemirbasDTO demirbasDTO) {
        this.demirbasDTO = demirbasDTO;
    }

    public List<DemirbasHareketDTO> getDemirbasHareketDTOList() {
        return demirbasHareketDTOList;
    }

    public void setDemirbasHareketDTOList(List<DemirbasHareketDTO> demirbasHareketDTOList) {
        this.demirbasHareketDTOList = demirbasHareketDTOList;
    }
}
