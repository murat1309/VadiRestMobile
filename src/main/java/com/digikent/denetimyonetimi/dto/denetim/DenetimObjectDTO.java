package com.digikent.denetimyonetimi.dto.denetim;

import com.digikent.denetimyonetimi.dto.adres.DenetimOlayYeriAdresi;
import com.digikent.denetimyonetimi.dto.adres.DenetimTebligatAdresi;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;

/**
 * Created by Medet on 3/21/2018.
 */
public class DenetimObjectDTO {
    private DenetimOlayYeriAdresi denetimOlayYeriAdresi;
    private DenetimTebligatAdresi denetimTebligatAdresi;
    private DenetimTebligDTO denetimTebligDTO;
    private DenetimPaydasDTO denetimPaydasDTO;


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
}
