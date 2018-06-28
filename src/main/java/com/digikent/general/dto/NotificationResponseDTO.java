package com.digikent.general.dto;

public class NotificationResponseDTO {
    private Long ebysNotificationCount;
    private Long mesajNotificationCount;
    private Long gelenBasvuruNotificationCount;

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

    public Long getGelenBasvuruNotificationCount() {
        return gelenBasvuruNotificationCount;
    }

    public void setGelenBasvuruNotificationCount(Long gelenBasvuruNotificationCount) {
        this.gelenBasvuruNotificationCount = gelenBasvuruNotificationCount;
    }
}
