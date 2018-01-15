package com.digikent.mesajlasma.dto;

/**
 * Created by Medet on 12/16/2017.
 */
public class GroupLeaveRequestDTO {

    private Long userId;
    private Long groupId;
    private String personelName;
    private String discardedUserName;
    private Long groupAdmin;

    public GroupLeaveRequestDTO() {
    }

    public String getDiscardedUserName() {
        return discardedUserName;
    }

    public void setDiscardedUserName(String discardedUserName) {
        this.discardedUserName = discardedUserName;
    }

    public String getPersonelName() {
        return personelName;
    }

    public void setPersonelName(String personelName) {
        this.personelName = personelName;
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

    public Long getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Long groupAdmin) {
        this.groupAdmin = groupAdmin;
    }
}
