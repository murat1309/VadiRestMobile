package com.digikent.denetimyonetimi.dto.denetim;

import com.digikent.denetimyonetimi.dto.adres.DenetimOlayYeriAdresi;
import com.digikent.denetimyonetimi.dto.adres.DenetimTebligatAdresi;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitKararRequest;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.taraf.DenetimGoruntuleTarafDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitlerRequest;

import java.util.List;

/**
 * Created by Medet on 3/21/2018.
 */
public class DenetimObjectDTO {
    private DenetimOlayYeriAdresi denetimOlayYeriAdresi;
    private DenetimTebligatAdresi denetimTebligatAdresi;
    private DenetimTebligDTO denetimTebligDTO;
    private DenetimPaydasDTO denetimPaydasDTO;
    private DenetimTespitKararRequest denetimTespitKararDTO;
    private TespitlerRequest tespitAnswersResponseData;
    private List<TespitDTO> tespitQuestionsData;
    private List<DenetimGoruntuleTarafDTO> denetimGoruntuleTarafDTOList;


    public DenetimObjectDTO() {
    }

    public DenetimOlayYeriAdresi getDenetimOlayYeriAdresi() {
        return denetimOlayYeriAdresi;
    }

    public void setDenetimOlayYeriAdresi(DenetimOlayYeriAdresi denetimOlayYeriAdresi) {
        this.denetimOlayYeriAdresi = denetimOlayYeriAdresi;
    }

    public DenetimTebligatAdresi getDenetimTebligatAdresi() {
        return denetimTebligatAdresi;
    }

    public void setDenetimTebligatAdresi(DenetimTebligatAdresi denetimTebligatAdresi) {
        this.denetimTebligatAdresi = denetimTebligatAdresi;
    }

    public DenetimTebligDTO getDenetimTebligDTO() {
        return denetimTebligDTO;
    }

    public void setDenetimTebligDTO(DenetimTebligDTO denetimTebligDTO) {
        this.denetimTebligDTO = denetimTebligDTO;
    }

    public DenetimPaydasDTO getDenetimPaydasDTO() {
        return denetimPaydasDTO;
    }

    public void setDenetimPaydasDTO(DenetimPaydasDTO denetimPaydasDTO) {
        this.denetimPaydasDTO = denetimPaydasDTO;
    }

    public DenetimTespitKararRequest getDenetimTespitKararDTO() {
        return denetimTespitKararDTO;
    }

    public void setDenetimTespitKararDTO(DenetimTespitKararRequest denetimTespitKararDTO) {
        this.denetimTespitKararDTO = denetimTespitKararDTO;
    }

    public TespitlerRequest getTespitAnswersResponseData() {
        return tespitAnswersResponseData;
    }

    public void setTespitAnswersResponseData(TespitlerRequest tespitAnswersResponseData) {
        this.tespitAnswersResponseData = tespitAnswersResponseData;
    }

    public List<TespitDTO> getTespitQuestionsData() {
        return tespitQuestionsData;
    }

    public void setTespitQuestionsData(List<TespitDTO> tespitQuestionsData) {
        this.tespitQuestionsData = tespitQuestionsData;
    }

    public List<DenetimGoruntuleTarafDTO> getDenetimGoruntuleTarafDTOList() {
        return denetimGoruntuleTarafDTOList;
    }

    public void setDenetimGoruntuleTarafDTOList(List<DenetimGoruntuleTarafDTO> denetimGoruntuleTarafDTOList) {
        this.denetimGoruntuleTarafDTOList = denetimGoruntuleTarafDTOList;
    }
}
