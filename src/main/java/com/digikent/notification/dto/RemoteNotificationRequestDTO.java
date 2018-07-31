package com.digikent.notification.dto;

public class RemoteNotificationRequestDTO {
    public RemoteNotificationRequestDTO() {
    }
    private Long fsm1UserId;
    private String notificationMessage;

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Long getFsm1UserId() {
        return fsm1UserId;
    }

    public void setFsm1UserId(Long fsm1UserId) {
        this.fsm1UserId = fsm1UserId;
    }
}
