package com.digikent.mesajlasma.dto;

/**
 * Created by Kadir on 5/21/16.
 */
public class MessageUserDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String activeDirectoryUserName;
    private Long iletilenPersonelId;
    private Boolean groupAdmin;
    private Long personelId;

    public MessageUserDTO() {
    }

    public MessageUserDTO(String userId, String firstName, String lastName, String activeDirectoryUserName, Long iletilenPersonelId, Boolean groupAdmin, Long personelId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activeDirectoryUserName = activeDirectoryUserName;
        this.iletilenPersonelId = iletilenPersonelId;
        this.groupAdmin = groupAdmin;
        this.personelId = personelId;
    }

    public Long getPersonelId() { return personelId; }

    public void setPersonelId(Long personelId) { this.personelId = personelId; }

    public Boolean getGroupAdmin() { return groupAdmin; }

    public void setGroupAdmin(Boolean groupAdmin) { this.groupAdmin = groupAdmin; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getActiveDirectoryUserName() {
        return activeDirectoryUserName;
    }

    public void setActiveDirectoryUserName(String activeDirectoryUserName) {
        this.activeDirectoryUserName = activeDirectoryUserName;
    }

    public Long getIletilenPersonelId() {
        return iletilenPersonelId;
    }

    public void setIletilenPersonelId(Long iletilenPersonelId) {
        this.iletilenPersonelId = iletilenPersonelId;
    }
}
