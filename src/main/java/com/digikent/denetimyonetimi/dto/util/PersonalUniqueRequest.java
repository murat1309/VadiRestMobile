package com.digikent.denetimyonetimi.dto.util;

/**
 * Created by Kadir on 6.03.2018.
 */
public class PersonalUniqueRequest {

    private Long fsm1UsersId;
    private Long ikyPersonelId;
    private String fsm1UsersUSERID;

    public Long getFsm1UsersId() {
        return fsm1UsersId;
    }

    public void setFsm1UsersId(Long fsm1UsersId) {
        this.fsm1UsersId = fsm1UsersId;
    }

    public Long getIkyPersonelId() {
        return ikyPersonelId;
    }

    public void setIkyPersonelId(Long ikyPersonelId) {
        this.ikyPersonelId = ikyPersonelId;
    }

    public String getFsm1UsersUSERID() {
        return fsm1UsersUSERID;
    }

    public void setFsm1UsersUSERID(String fsm1UsersUSERID) {
        this.fsm1UsersUSERID = fsm1UsersUSERID;
    }
}
