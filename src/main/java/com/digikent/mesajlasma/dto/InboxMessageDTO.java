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
    private Long yazanPersonelId;
    private String lastMessage;
    private Date okunmaZamani;
    private Date sendDate;
    private Boolean me;
    private Boolean hasunread;

    public InboxMessageDTO() {
    }

    public InboxMessageDTO(String personelName, String groupName, Boolean isGroup, Long groupId, Long iletilenPersonelId, Long yazanPersonelId, String lastMessage, Date okunmaZamani, Date sendDate, Boolean me, Boolean hasunread) {
        this.personelName = personelName;
        this.groupName = groupName;
        this.isGroup = isGroup;
        this.groupId = groupId;
        this.iletilenPersonelId = iletilenPersonelId;
        this.yazanPersonelId = yazanPersonelId;
        this.lastMessage = lastMessage;
        this.okunmaZamani = okunmaZamani;
        this.sendDate = sendDate;
        this.me = me;
        this.hasunread = hasunread;
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

    public Long getYazanPersonelId() {
        return yazanPersonelId;
    }

    public void setYazanPersonelId(Long yazanPersonelId) {
        this.yazanPersonelId = yazanPersonelId;
    }

    public Boolean getMe() {
        return me;
    }

    public void setMe(Boolean me) {
        this.me = me;
    }

    public Boolean getHasunread() {
        return hasunread;
    }

    public void setHasunread(Boolean hasunread) {
        this.hasunread = hasunread;
    }
}
