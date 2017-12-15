package com.digikent.mesajlasma.dto;

import java.io.Serializable;

/**
 * Created by Medet on 12/15/2017.
 */
public class GroupDeleteRequestDTO implements Serializable {

    private Long groupId;

    public GroupDeleteRequestDTO(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }




}
