package com.digikent.denetimyonetimi.dto.takim;

import java.io.Serializable;

/**
 * Created by Kadir on 22.02.2018.
 */
public class VsynMemberShipDTO implements Serializable {

    private Long id;
    private VsynRoleTeamDTO vsynRoleTeamDTO;
    private Long fsm1UsersId;
    private String parentObjectName;
    private String childObjectName;
    private String parentName;
    private String childName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VsynRoleTeamDTO getVsynRoleTeamDTO() {
        return vsynRoleTeamDTO;
    }

    public void setVsynRoleTeamDTO(VsynRoleTeamDTO vsynRoleTeamDTO) {
        this.vsynRoleTeamDTO = vsynRoleTeamDTO;
    }

    public Long getFsm1UsersId() {
        return fsm1UsersId;
    }

    public void setFsm1UsersId(Long fsm1UsersId) {
        this.fsm1UsersId = fsm1UsersId;
    }

    public String getParentObjectName() {
        return parentObjectName;
    }

    public void setParentObjectName(String parentObjectName) {
        this.parentObjectName = parentObjectName;
    }

    public String getChildObjectName() {
        return childObjectName;
    }

    public void setChildObjectName(String childObjectName) {
        this.childObjectName = childObjectName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
