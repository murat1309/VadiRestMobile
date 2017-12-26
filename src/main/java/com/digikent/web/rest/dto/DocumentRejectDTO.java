package com.digikent.web.rest.dto;

import java.io.Serializable;

/**
 * Created by Kadir on 18.12.2017.
 */
public class DocumentRejectDTO implements Serializable {

    private String ebysdocument_id;
    private String abpmworkflow_id;
    private String abpmworkitem_id;
    private String iptalAciklamasi;

    public String getEbysdocument_id() {
        return ebysdocument_id;
    }

    public void setEbysdocument_id(String ebysdocument_id) {
        this.ebysdocument_id = ebysdocument_id;
    }

    public String getAbpmworkflow_id() {
        return abpmworkflow_id;
    }

    public void setAbpmworkflow_id(String abpmworkflow_id) {
        this.abpmworkflow_id = abpmworkflow_id;
    }

    public String getAbpmworkitem_id() {
        return abpmworkitem_id;
    }

    public void setAbpmworkitem_id(String abpmworkitem_id) {
        this.abpmworkitem_id = abpmworkitem_id;
    }

    public String getIptalAciklamasi() {
        return iptalAciklamasi;
    }

    public void setIptalAciklamasi(String iptalAciklamasi) {
        this.iptalAciklamasi = iptalAciklamasi;
    }
}
