package com.digikent.denetimyonetimi.dto.takim;

import com.digikent.mesajlasma.dto.ErrorDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 22.02.2018.
 */
public class TeamResponse implements Serializable {

    private List<VsynRoleTeamDTO> vsynRoleTeamDTOList = new ArrayList<>();
    private ErrorDTO errorDTO;

    public List<VsynRoleTeamDTO> getVsynRoleTeamDTOList() {
        return vsynRoleTeamDTOList;
    }

    public void setVsynRoleTeamDTOList(List<VsynRoleTeamDTO> vsynRoleTeamDTOList) {
        this.vsynRoleTeamDTOList = vsynRoleTeamDTOList;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
