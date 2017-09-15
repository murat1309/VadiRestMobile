package com.digikent.mesajlasma.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kadir on 14/09/17.
 */
public class InboxResponseDTO implements Serializable {

    private ErrorDTO errorDTO;
    private List<InboxMessageDTO> inboxMessageDTOList;

    public InboxResponseDTO() {
    }

    public InboxResponseDTO(ErrorDTO errorDTO, List<InboxMessageDTO> inboxMessageDTOList) {
        this.errorDTO = errorDTO;
        this.inboxMessageDTOList = inboxMessageDTOList;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }

    public List<InboxMessageDTO> getInboxMessageDTOList() {
        return inboxMessageDTOList;
    }

    public void setInboxMessageDTOList(List<InboxMessageDTO> inboxMessageDTOList) {
        this.inboxMessageDTOList = inboxMessageDTOList;
    }
}
