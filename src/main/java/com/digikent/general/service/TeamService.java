package com.digikent.general.service;

import com.digikent.denetimyonetimi.dto.takim.VsynRoleTeamDTO;
import com.digikent.denetimyonetimi.entity.VSYNMemberShip;
import com.digikent.denetimyonetimi.entity.VSYNRoleTeam;
import com.digikent.general.dao.TeamRepository;
import com.digikent.denetimyonetimi.dto.takim.VsynMemberShipDTO;
import com.digikent.general.dto.Fsm1UserDTO;
import com.digikent.denetimyonetimi.entity.FSM1Users;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Kadir on 22.02.2018.
 */
@Service
public class TeamService {
    private final Logger LOG = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    TeamRepository teamRepository;

    public List<VsynRoleTeamDTO> getTeamByUserId(Long userId) {
        List<VSYNMemberShip> vsnyMemberShipList = teamRepository.findVSNYMemberShipListByUserId(userId);
        List<VSYNRoleTeam> vsynRoleTeamList = new ArrayList<>();
        for (VSYNMemberShip vsnyMemberShip:vsnyMemberShipList) {
            vsynRoleTeamList.add(vsnyMemberShip.getVsynRoleTeam());
        }
        List<VSYNMemberShip> vsnyMemberShips = teamRepository.findVSNYMemberShipListByVSYNRoleTeamIdList(vsynRoleTeamList);
        List<VsynMemberShipDTO> vsynMemberShipDTOList = convertVsynMemberShipDTOList(vsnyMemberShips);

        if (vsynMemberShipDTOList != null && vsynMemberShipDTOList.size() != 0) {
            return groupingByVsynMemberShipDTOList(vsynMemberShipDTOList);
        }

        return null;
    }

    private List<VsynRoleTeamDTO> groupingByVsynMemberShipDTOList(List<VsynMemberShipDTO> vsynMemberShipDTOList) {

        Map<Long, List<VsynMemberShipDTO>> vsynRoleTeamMap =
                vsynMemberShipDTOList.stream().collect(Collectors.groupingBy(w -> w.getVsynRoleTeamDTO().getId()));

        List<VsynRoleTeamDTO> vsynRoleTeamDTOList = new ArrayList<>();

        for (Map.Entry<Long, List<VsynMemberShipDTO>> entry : vsynRoleTeamMap.entrySet()) {
            VsynRoleTeamDTO vsynRoleTeamDTO = new VsynRoleTeamDTO();
            vsynRoleTeamDTO.setId(entry.getValue().get(0).getVsynRoleTeamDTO().getId());
            vsynRoleTeamDTO.setRolName(entry.getValue().get(0).getVsynRoleTeamDTO().getRolName());
            vsynRoleTeamDTO.setRoleType(entry.getValue().get(0).getVsynRoleTeamDTO().getRoleType());
            vsynRoleTeamDTO.setUserList(entry.getValue());
            vsynRoleTeamDTOList.add(vsynRoleTeamDTO);
            //System.out.println(entry.getKey() + "/" + entry.getValue());
        }
        return vsynRoleTeamDTOList;
    }

    private List<VsynMemberShipDTO> convertVsynMemberShipDTOList(List<VSYNMemberShip> vsnyMemberShips) {
        List<VsynMemberShipDTO> vsynMemberShipDTOList = new ArrayList<>();
        for (VSYNMemberShip vsynMemberShip:vsnyMemberShips) {
            if (vsynMemberShip.getFsm1Users() != null) {
                VsynMemberShipDTO vsnyMemberShipDTO = convertVsynMemberShipDTO(vsynMemberShip);
                vsynMemberShipDTOList.add(vsnyMemberShipDTO);
            }
        }
        return vsynMemberShipDTOList;
    }

    private Fsm1UserDTO convertFsm1UsersDTO(FSM1Users fsm1Users) {
        Fsm1UserDTO fsm1UserDTO = new Fsm1UserDTO();
        if (fsm1Users != null) {
            fsm1UserDTO.setId(fsm1Users.getID());
            fsm1UserDTO.setAdi(fsm1Users.getFirstName());
            fsm1UserDTO.setSoyadi(fsm1Users.getLastName());
        }
        return fsm1UserDTO;
    }

    private VsynMemberShipDTO convertVsynMemberShipDTO(VSYNMemberShip vsnyMemberShip) {
        VsynMemberShipDTO vsynMemberShipDTO = new VsynMemberShipDTO();
        if (vsynMemberShipDTO != null) {
            vsynMemberShipDTO.setId(vsnyMemberShip.getID());
            vsynMemberShipDTO.setChildName(vsnyMemberShip.getChildName());
            vsynMemberShipDTO.setChildObjectName(vsnyMemberShip.getChildObjectName());
            vsynMemberShipDTO.setFsm1UserDTO(convertFsm1UsersDTO(vsnyMemberShip.getFsm1Users()));
            vsynMemberShipDTO.setParentName(vsnyMemberShip.getParentName());
            vsynMemberShipDTO.setParentObjectName(vsnyMemberShip.getParentObjectName());
            vsynMemberShipDTO.setVsynRoleTeamDTO(new VsynRoleTeamDTO(vsnyMemberShip.getVsynRoleTeam().getID(),vsnyMemberShip.getVsynRoleTeam().getRolName(),vsnyMemberShip.getVsynRoleTeam().getRoleType()));
        }
        return vsynMemberShipDTO;
    }
}
