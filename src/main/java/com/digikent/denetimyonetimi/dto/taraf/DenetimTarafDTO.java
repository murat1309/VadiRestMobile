package com.digikent.denetimyonetimi.dto.taraf;

import com.digikent.denetimyonetimi.dto.takim.VsynMemberShipDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 23.02.2018.
 */
public class DenetimTarafDTO {

    private Long roleTeamId;
    private List<VsynMemberShipDTO> memberShipDTOList = new ArrayList<>();

    public Long getRoleTeamId() {
        return roleTeamId;
    }

    public void setRoleTeamId(Long roleTeamId) {
        this.roleTeamId = roleTeamId;
    }

    public List<VsynMemberShipDTO> getMemberShipDTOList() {
        return memberShipDTOList;
    }

    public void setMemberShipDTOList(List<VsynMemberShipDTO> memberShipDTOList) {
        this.memberShipDTOList = memberShipDTOList;
    }
}
