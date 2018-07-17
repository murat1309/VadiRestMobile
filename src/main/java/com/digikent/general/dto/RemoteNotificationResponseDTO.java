package com.digikent.general.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

public class RemoteNotificationResponseDTO {

    public RemoteNotificationResponseDTO() {
    }

    private ErrorDTO errorDTO;

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
