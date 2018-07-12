package com.digikent.denetimyonetimi.dto.takim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 22.02.2018.
 */
public class VsynRoleTeamDTO implements Serializable {

    private Long id;
    private String rolName;
    private String roleType;
    private List<VsynMemberShipDTO> userList = new ArrayList<>();

    public VsynRoleTeamDTO() {
    }

    public VsynRoleTeamDTO(Long id, String rolName, String roleType) {
        this.id = id;
        this.rolName = rolName;
        this.roleType = roleType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public List<VsynMemberShipDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<VsynMemberShipDTO> userList) {
        this.userList = userList;
    }
}
