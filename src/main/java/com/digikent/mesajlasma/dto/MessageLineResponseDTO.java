package com.digikent.mesajlasma.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Serkan on 14/09/17.
 */
public class MessageLineResponseDTO implements Serializable {

    private ErrorDTO errorDTO;
    private List<MessageLineDTO> messageLineDTOList;
    private Boolean isActive;

    public MessageLineResponseDTO() {
    }

    public MessageLineResponseDTO(ErrorDTO errorDTO, List<MessageLineDTO> messageLineDTOList) {
        this.errorDTO = errorDTO;
        this.messageLineDTOList = messageLineDTOList;
    }

    public MessageLineResponseDTO(ErrorDTO errorDTO, List<MessageLineDTO> messageLineDTOList, Boolean isActive) {
        this.errorDTO = errorDTO;
        this.messageLineDTOList = messageLineDTOList;
        this.isActive = isActive;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        this.isActive = active;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }

    public List<MessageLineDTO> getMessageLineDTOList() {
        return messageLineDTOList;
    }

    public void setMessageLineDTOList(List<MessageLineDTO> messageLineDTOList) {
        this.messageLineDTOList = messageLineDTOList;
    }
}
