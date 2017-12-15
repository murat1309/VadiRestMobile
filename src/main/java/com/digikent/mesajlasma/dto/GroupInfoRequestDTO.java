package com.digikent.mesajlasma.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Medet on 12/13/2017.
 */


public class GroupInfoRequestDTO implements Serializable {

    private Boolean isGroup;
    private Long groupId;
    private BigDecimal senderId;

    public Boolean getGroup() {
        return isGroup;
    }

    public void setGroup(Boolean group) {
        isGroup = group;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public BigDecimal getSenderId() {
        return senderId;
    }

    public void setSenderId(BigDecimal senderId) {
        this.senderId = senderId;
    }
}
