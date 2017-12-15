package com.digikent.mesajlasma.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Medet on 12/13/2017.
 */
public class GroupInfoResponseDTO implements Serializable {

    List<MessageUserDTO> userList;

    public List<MessageUserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<MessageUserDTO> userList) {
        this.userList = userList;
    }
}
