package com.digikent.mesajlasma.dto;

import java.io.Serializable;

/**
 * Created by Medet on 12/15/2017.
 */

public class GroupDeleteRequestDTO implements Serializable {

    private Long groupId;
    private String olusturanPersonelName;
    private String groupName;
    private String fullName;

    public GroupDeleteRequestDTO() {

    }

    public GroupDeleteRequestDTO(Long groupId, String olusturanPersonelName, String groupName) {
        this.groupId = groupId;
        this.olusturanPersonelName = olusturanPersonelName;
        this.groupName = groupName;
    }

    public GroupDeleteRequestDTO(Long groupId) {
        this.groupId = groupId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOlusturanPersonelName() {
        return olusturanPersonelName;
    }

    public void setOlusturanPersonelName(String olusturanPersonelName) {
        this.olusturanPersonelName = olusturanPersonelName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }




}
