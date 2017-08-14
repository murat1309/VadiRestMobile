package com.digikent.vadirest.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Serkan on 24/05/17.
 */
public class ResponseBaseUrlDTO implements Serializable {

    List<BaseUrlDTO> baseUrlDTOList;
    int messageId;

    public List<BaseUrlDTO> getBaseUrlDTOList() {
        return baseUrlDTOList;
    }

    public void setBaseUrlDTOList(List<BaseUrlDTO> baseUrlDTOList) {
        this.baseUrlDTOList = baseUrlDTOList;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
