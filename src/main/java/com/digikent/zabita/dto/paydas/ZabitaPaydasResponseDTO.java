package com.digikent.zabita.dto.paydas;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Serkan on 25.01.2018.
 */
public class ZabitaPaydasResponseDTO implements Serializable {

    private List<ZabitaPaydasDTO> responseZabitaPaydasList;
    private ErrorDTO errorDTO;



    public List<ZabitaPaydasDTO> getResponseZabitaPaydasList() {
        return responseZabitaPaydasList;
    }

    public void setResponseZabitaPaydasList(List<ZabitaPaydasDTO> responseZabitaPaydasList) {
        this.responseZabitaPaydasList = responseZabitaPaydasList;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
