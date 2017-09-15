package com.digikent.mesajlasma.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 13/09/17.
 */
public class GroupRequest implements Serializable {

    private List<MessageUserDTO> messageUserDTOList = new ArrayList<>();
    private GroupInformationDTO groupInformationDTO;

    public GroupRequest() {
    }

    public GroupRequest(List<MessageUserDTO> messageUserDTOList, GroupInformationDTO groupInformationDTO) {
        this.messageUserDTOList = messageUserDTOList;
        this.groupInformationDTO = groupInformationDTO;
    }

    public List<MessageUserDTO> getMessageUserDTOList() {
        return messageUserDTOList;
    }

    public void setMessageUserDTOList(List<MessageUserDTO> messageUserDTOList) {
        this.messageUserDTOList = messageUserDTOList;
    }

    public GroupInformationDTO getGroupInformationDTO() {
        return groupInformationDTO;
    }

    public void setGroupInformationDTO(GroupInformationDTO groupInformationDTO) {
        this.groupInformationDTO = groupInformationDTO;
    }
}
