package com.digikent.mesajlasma.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Serkan on 14/09/17.
 */
public class MessageLineDTO implements Serializable {

    private String personelName;
    private Long personelId;
    private String message;
    private Date sendDate;
    private String type;

    public MessageLineDTO() {
    }

    public MessageLineDTO(String personelName, Long personelId, String message, Date sendDate, String type) {
        this.personelName = personelName;
        this.personelId = personelId;
        this.message = message;
        this.sendDate = sendDate;
        this.type = type;
    }

    public String getPersonelName() {
        return personelName;
    }

    public void setPersonelName(String personelName) {
        this.personelName = personelName;
    }

    public Long getPersonelId() {
        return personelId;
    }

    public void setPersonelId(Long personelId) {
        this.personelId = personelId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
