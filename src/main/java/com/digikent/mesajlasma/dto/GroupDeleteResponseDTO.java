package com.digikent.mesajlasma.dto;
import java.io.Serializable;

/**
 * Created by Medet on 12/15/2017.
 */
public class GroupDeleteResponseDTO implements Serializable {


    private ErrorDTO errorDTO;

    public GroupDeleteResponseDTO(ErrorDTO errorDTO) {

        this.errorDTO = errorDTO;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }


}
