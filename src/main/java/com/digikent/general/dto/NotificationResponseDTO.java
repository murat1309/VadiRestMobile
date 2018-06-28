package com.digikent.general.dto;

public class NotificationResponseDTO {
    private Long ebysNotificationCount;
    private Long mesajNotificationCount;

    public Long getEbysNotificationCount() {
        return ebysNotificationCount;
    }

    public void setEbysNotificationCount(Long ebysNotificationCount) {
        this.ebysNotificationCount = ebysNotificationCount;
    }

    public Long getMesajNotificationCount() {
        return mesajNotificationCount;
    }

    public void setMesajNotificationCount(Long mesajNotificationCount) {
        this.mesajNotificationCount = mesajNotificationCount;
    }
}
