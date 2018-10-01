package com.digikent.notification.dto;

public class RemoteNotificationRequestDTO {
    public RemoteNotificationRequestDTO() {
    }
    private Long fsm1UserId;
    private String notificationType;

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Long getFsm1UserId() {
        return fsm1UserId;
    }

    public void setFsm1UserId(Long fsm1UserId) {
        this.fsm1UserId = fsm1UserId;
    }
}
