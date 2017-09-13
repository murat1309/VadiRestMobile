package com.digikent.mesajlasma.dto;

/**
 * Created by Serkan on 5/21/16.
 */
public class MessageUserDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String activeDirectoryUserName;
    private Long personelId;

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

    public Long getPersonelId() {
        return personelId;
    }

    public void setPersonelId(Long personelId) {
        this.personelId = personelId;
    }
}
