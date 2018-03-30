package com.digikent.denetimyonetimi.dto.velocity;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.io.Serializable;

/**
 * Created by Kadir on 31.01.2018.
 */
public class ReportResponse implements Serializable {

    private String htmlContent;
    private ErrorDTO errorDTO;

    public ReportResponse() {}

    public ReportResponse(String htmlContent, ErrorDTO errorDTO) {
        this.htmlContent = htmlContent;
        this.errorDTO = errorDTO;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
