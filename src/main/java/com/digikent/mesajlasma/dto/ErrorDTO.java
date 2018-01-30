package com.digikent.mesajlasma.dto;

import com.documentum.xml.xpath.operations.Bool;

/**
 * Created by Serkan on 13/09/17.
 */
public class ErrorDTO {

    private Boolean isError;
    private String errorMessage;

    public ErrorDTO() {
    }

    public ErrorDTO(Boolean isError) {
        this.isError = isError;
    }

    public ErrorDTO(Boolean isError, String errorMessage) {
        this.isError = isError;
        this.errorMessage = errorMessage;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
