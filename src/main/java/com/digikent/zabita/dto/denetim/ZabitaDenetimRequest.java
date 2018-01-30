package com.digikent.zabita.dto.denetim;

import com.digikent.zabita.dto.adres.ZabitaOlayYeriAdresi;
import com.digikent.zabita.dto.adres.ZabitaTebligatAdresi;
import com.digikent.zabita.dto.paydas.ZabitaPaydasDTO;

import java.io.Serializable;

/**
 * Created by Kadir on 26.01.2018.
 */
public class ZabitaDenetimRequest implements Serializable{

    private ZabitaPaydasDTO zabitaPaydasDTO;
    private ZabitaTebligatAdresi zabitaTebligatAdresi;
    private ZabitaOlayYeriAdresi zabitaOlayYeriAdresi;

    public ZabitaPaydasDTO getZabitaPaydasDTO() {
        return zabitaPaydasDTO;
    }

    public void setZabitaPaydasDTO(ZabitaPaydasDTO zabitaPaydasDTO) {
        this.zabitaPaydasDTO = zabitaPaydasDTO;
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
}
