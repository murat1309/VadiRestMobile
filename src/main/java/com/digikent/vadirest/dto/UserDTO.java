package com.digikent.vadirest.dto;

/**
 * Created by Serkan on 5/21/16.
 */
public class UserDTO {
    private String userName;
    private String password;
    private String activeDirectoryUserName;
    private Long Id;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getActiveDirectoryUserName() {
        return activeDirectoryUserName;
    }

    public void setActiveDirectoryUserName(String activeDirectoryUserName) {
        this.activeDirectoryUserName = activeDirectoryUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
