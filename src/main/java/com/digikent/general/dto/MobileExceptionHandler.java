package com.digikent.general.dto;

/**
 * Created by Kadir on 16.04.2018.
 */
public class MobileExceptionHandler {

    private Boolean isFatalError;
    private String errorTitle;
    private String errorMessage;
    private String errorDetails;

    public Boolean getFatalError() {
        return isFatalError;
    }

    public void setFatalError(Boolean fatalError) {
        isFatalError = fatalError;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}
