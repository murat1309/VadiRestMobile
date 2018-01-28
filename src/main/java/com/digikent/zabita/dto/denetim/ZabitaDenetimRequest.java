package com.digikent.zabita.dto.denetim;

import com.digikent.zabita.dto.adres.ZabitaOlayYeriAdresi;
import com.digikent.zabita.dto.adres.ZabitaTebligatAdresi;

import java.io.Serializable;

/**
 * Created by Kadir on 26.01.2018.
 */
public class ZabitaDenetimRequest implements Serializable{

    private Long paydasId;
    private Long rre1IlceId;
    private ZabitaTebligatAdresi zabitaTebligatAdresi;
    private ZabitaOlayYeriAdresi zabitaOlayYeriAdresi;

    public Long getPaydasId() {
        return paydasId;
    }

    public void setPaydasId(Long paydasId) {
        this.paydasId = paydasId;
    }

    public ZabitaTebligatAdresi getZabitaTebligatAdresi() {
        return zabitaTebligatAdresi;
    }

    public void setZabitaTebligatAdresi(ZabitaTebligatAdresi zabitaTebligatAdresi) {
        this.zabitaTebligatAdresi = zabitaTebligatAdresi;
    }

    public ZabitaOlayYeriAdresi getZabitaOlayYeriAdresi() {
        return zabitaOlayYeriAdresi;
    }

    public void setZabitaOlayYeriAdresi(ZabitaOlayYeriAdresi zabitaOlayYeriAdresi) {
        this.zabitaOlayYeriAdresi = zabitaOlayYeriAdresi;
    }

    public Long getRre1IlceId() {
        return rre1IlceId;
    }

    public void setRre1IlceId(Long rre1IlceId) {
        this.rre1IlceId = rre1IlceId;
    }
}
