package com.digikent.mesajlasma.dto;

import java.io.Serializable;

/**
 * Created by Kadir on 14/09/17.
 */
public class MessageLineRequestDTO implements Serializable {

    private Long personelId;
    private Long ilentilenPersonelId;
    private Long groupId;
    private String iletimTuru;

    public MessageLineRequestDTO() {
    }

    public MessageLineRequestDTO(Long personelId, Long ilentilenPersonelId, Long groupId, String iletimTuru) {
        this.personelId = personelId;
        this.ilentilenPersonelId = ilentilenPersonelId;
        this.groupId = groupId;
        this.iletimTuru = iletimTuru;
    }

    public Long getPersonelId() {
        return personelId;
    }

    public void setPersonelId(Long personelId) {
        this.personelId = personelId;
    }

    public Long getIlentilenPersonelId() {
        return ilentilenPersonelId;
    }

    public void setIlentilenPersonelId(Long ilentilenPersonelId) {
        this.ilentilenPersonelId = ilentilenPersonelId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getIletimTuru() {
        return iletimTuru;
    }

    public void setIletimTuru(String iletimTuru) {
        this.iletimTuru = iletimTuru;
    }
}
