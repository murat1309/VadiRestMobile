package com.digikent.paydasiliskileri.dto;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Medet on 1/2/2018.
 */

/*
Name          Label (Alias) Type (DB)
------------- ------------- ---------
ADI           ADI           VARCHAR2
SOYADI        SOYADI        VARCHAR2
UNVAN         UNVAN         VARCHAR2
VERGINUMARASI VERGINUMARASI VARCHAR2
YCEPTELEFONU  YCEPTELEFONU  NUMBER
YEVTELEFONU   YEVTELEFONU   NUMBER
YISTELEFONU   YISTELEFONU   NUMBER
PAYDASTURU    PAYDASTURU    VARCHAR2
TABELAADI     TABELAADI     VARCHAR2
MAHALLEADI    MAHALLEADI    VARCHAR2
SOKAKADI      SOKAKADI      VARCHAR2
BINAADI       BINAADI       VARCHAR2
BLOKNO        BLOKNO        VARCHAR2
KAPINO        KAPINO        VARCHAR2
DAIRENO       DAIRENO       VARCHAR2

 */

public class PaydasSorguResponseDTO {

    private List<PaydasIlanSorguDTO> paydasIlanSorguResponse;
    private List<PaydasBorcSorguDTO> paydasBorcSorguResponse;
    private List<PaydasSorguDTO> paydasSorguResponse;
    private ErrorDTO errorDTO;

    public PaydasSorguResponseDTO() {
    }

    public List<PaydasIlanSorguDTO> getPaydasIlanSorguResponse() {
        return paydasIlanSorguResponse;
    }

    public void setPaydasIlanSorguResponse(List<PaydasIlanSorguDTO> paydasIlanSorguResponse) {
        this.paydasIlanSorguResponse = paydasIlanSorguResponse;
    }

    public List<PaydasBorcSorguDTO> getPaydasBorcSorguResponse() {
        return paydasBorcSorguResponse;
    }

    public void setPaydasBorcSorguResponse(List<PaydasBorcSorguDTO> paydasBorcSorguResponse) {
        this.paydasBorcSorguResponse = paydasBorcSorguResponse;
    }

    public List<PaydasSorguDTO> getPaydasSorguResponse() {
        return paydasSorguResponse;
    }

    public void setPaydasSorguResponse(List<PaydasSorguDTO> paydasSorguResponse) {
        this.paydasSorguResponse = paydasSorguResponse;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
