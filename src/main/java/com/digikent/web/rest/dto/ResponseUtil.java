package com.digikent.web.rest.dto;

import com.digikent.web.rest.errors.ErrorDTO;

import java.io.Serializable;

/**
 * Created by Serkan on 18.12.2017.
 */
public class ResponseUtil implements Serializable {

    private Boolean isError;
    private String message;
    private ErrorDTO errorDTO;

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
