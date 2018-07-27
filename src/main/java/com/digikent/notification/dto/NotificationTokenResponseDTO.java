package com.digikent.notification.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

public class NotificationTokenResponseDTO {
    public NotificationTokenResponseDTO() {
    }

    private ErrorDTO errorDTO;

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}

