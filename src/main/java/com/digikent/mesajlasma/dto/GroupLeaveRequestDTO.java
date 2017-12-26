package com.digikent.mesajlasma.dto;

/**
 * Created by Medet on 12/16/2017.
 */
public class GroupLeaveRequestDTO {

    private Long userId;
    private Long groupId;

    public GroupLeaveRequestDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
