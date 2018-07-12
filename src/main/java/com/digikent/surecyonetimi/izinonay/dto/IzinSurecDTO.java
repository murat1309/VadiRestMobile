package com.digikent.surecyonetimi.izinonay.dto;

/**
 * Created by Kadir on 13.06.2018.
 */
public class IzinSurecDTO {

    private String instanceName;
    private Long instanceId;

    public IzinSurecDTO() {
    }

    public IzinSurecDTO(String instanceName, Long instanceId) {
        this.instanceName = instanceName;
        this.instanceId = instanceId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }
}
