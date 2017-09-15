package com.digikent.mesajlasma.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Serkan on 14/09/17.
 */
public class MessageLineResponseDTO implements Serializable {

    private ErrorDTO errorDTO;
    private List<MessageLineDTO> messageLineDTOList;

    public MessageLineResponseDTO() {
    }

    public MessageLineResponseDTO(ErrorDTO errorDTO, List<MessageLineDTO> messageLineDTOList) {
        this.errorDTO = errorDTO;
        this.messageLineDTOList = messageLineDTOList;
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
