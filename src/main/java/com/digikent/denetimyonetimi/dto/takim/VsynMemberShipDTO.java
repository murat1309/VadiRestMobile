package com.digikent.denetimyonetimi.dto.takim;

import com.digikent.general.dto.Fsm1UserDTO;

import java.io.Serializable;

/**
 * Created by Kadir on 22.02.2018.
 */
public class VsynMemberShipDTO implements Serializable {

    private Long id;
    private VsynRoleTeamDTO vsynRoleTeamDTO;
    private Fsm1UserDTO fsm1UserDTO;
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

    public Fsm1UserDTO getFsm1UserDTO() {
        return fsm1UserDTO;
    }

    public void setFsm1UserDTO(Fsm1UserDTO fsm1UserDTO) {
        this.fsm1UserDTO = fsm1UserDTO;
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
