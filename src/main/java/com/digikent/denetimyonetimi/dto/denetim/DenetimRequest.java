package com.digikent.denetimyonetimi.dto.denetim;

import com.digikent.denetimyonetimi.dto.adres.DenetimOlayYeriAdresi;
import com.digikent.denetimyonetimi.dto.adres.DenetimTebligatAdresi;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;

import java.io.Serializable;

/**
 * Created by Kadir on 26.01.2018.
 */
public class DenetimRequest implements Serializable{

    private DenetimPaydasDTO denetimPaydasDTO;
    private DenetimTebligatAdresi denetimTebligatAdresi;
    private DenetimOlayYeriAdresi denetimOlayYeriAdresi;
    private Long isletmeId;

    public DenetimPaydasDTO getDenetimPaydasDTO() {
        return denetimPaydasDTO;
    }

    public void setDenetimPaydasDTO(DenetimPaydasDTO denetimPaydasDTO) {
        this.denetimPaydasDTO = denetimPaydasDTO;
    }

    public DenetimTebligatAdresi getDenetimTebligatAdresi() {
        return denetimTebligatAdresi;
    }

    public void setDenetimTebligatAdresi(DenetimTebligatAdresi denetimTebligatAdresi) {
        this.denetimTebligatAdresi = denetimTebligatAdresi;
    }

    public DenetimOlayYeriAdresi getDenetimOlayYeriAdresi() {
        return denetimOlayYeriAdresi;
    }

    public void setDenetimOlayYeriAdresi(DenetimOlayYeriAdresi denetimOlayYeriAdresi) {
        this.denetimOlayYeriAdresi = denetimOlayYeriAdresi;
    }

    public Long getIsletmeId() {
        return isletmeId;
    }

    public void setIsletmeId(Long isletmeId) {
        this.isletmeId = isletmeId;
    }
}
