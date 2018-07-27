package com.digikent.notification.dto;

public class NotificationTokenRequestDTO {

    public NotificationTokenRequestDTO() {
    }

    private String notificationToken;
    private Long fsm1UserId;
    private Long oldFsm1UserId;


    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public Long getFsm1UserId() {
        return fsm1UserId;
    }

    public void setFsm1UserId(Long fsm1UserId) {
        this.fsm1UserId = fsm1UserId;
    }

    public Long getOldFsm1UserId() {
        return oldFsm1UserId;
    }

    public void setOldFsm1UserId(Long oldFsm1UserId) {
        this.oldFsm1UserId = oldFsm1UserId;
    }
}
