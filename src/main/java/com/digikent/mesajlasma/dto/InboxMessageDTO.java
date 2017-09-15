package com.digikent.mesajlasma.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kadir on 14/09/17.
 */
public class InboxMessageDTO implements Serializable {

    private String personelName;
    private String groupName;
    private Boolean isGroup;
    private Long groupId;
    private Long iletilenPersonelId;
    private String lastMessage;
    private Date okunmaZamani;
    private Date sendDate;

    public InboxMessageDTO() {
    }

    public InboxMessageDTO(String personelName, String groupName, Boolean isGroup, Long groupId, Long iletilenPersonelId, String lastMessage, Date okunmaZamani, Date sendDate) {
        this.personelName = personelName;
        this.groupName = groupName;
        this.isGroup = isGroup;
        this.groupId = groupId;
        this.iletilenPersonelId = iletilenPersonelId;
        this.lastMessage = lastMessage;
        this.okunmaZamani = okunmaZamani;
        this.sendDate = sendDate;
    }

    public String getPersonelName() {
        return personelName;
    }

    public void setPersonelName(String personelName) {
        this.personelName = personelName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

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

    public Long getIletilenPersonelId() {
        return iletilenPersonelId;
    }

    public void setIletilenPersonelId(Long iletilenPersonelId) {
        this.iletilenPersonelId = iletilenPersonelId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getOkunmaZamani() {
        return okunmaZamani;
    }

    public void setOkunmaZamani(Date okunmaZamani) {
        this.okunmaZamani = okunmaZamani;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
