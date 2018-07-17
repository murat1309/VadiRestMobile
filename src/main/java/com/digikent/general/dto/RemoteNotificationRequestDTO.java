package com.digikent.general.dto;

public class RemoteNotificationRequestDTO {
    public RemoteNotificationRequestDTO() {
    }
    private String deviceToken;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
