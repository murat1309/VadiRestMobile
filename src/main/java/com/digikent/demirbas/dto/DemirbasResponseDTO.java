package com.digikent.demirbas.dto;

import com.digikent.surecyonetimi.dto.SanalDepoHareketDTO;

import java.util.List;

/**
 * Created by Serkan on 17/07/17.
 */
public class DemirbasResponseDTO {

    private DemirbasDTO demirbasDTO;
    private List<DemirbasHareketDTO> demirbasHareketDTOList;
    private List<SanalDepoHareketDTO> sanalDepoHareketDTOList;


    public List<SanalDepoHareketDTO> getSanalDepoHareketDTOList() {
        return sanalDepoHareketDTOList;
    }

    public void setSanalDepoHareketDTOList(List<SanalDepoHareketDTO> sanalDepoHareketDTOList) {
        this.sanalDepoHareketDTOList = sanalDepoHareketDTOList;
    }

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
