package com.digikent.denetimyonetimi.service;

import com.digikent.denetimyonetimi.dao.DenetimTarafRepository;
import com.digikent.denetimyonetimi.dto.takim.VsynRoleTeamDTO;
import com.digikent.denetimyonetimi.dto.util.PersonalUniqueRequest;
import com.digikent.denetimyonetimi.entity.*;
import com.digikent.denetimyonetimi.dto.takim.VsynMemberShipDTO;
import com.digikent.general.dto.Fsm1UserDTO;
import com.digikent.general.dto.Ihr1PersonelDTO;
import com.digikent.general.dto.Lhr1GorevTuruDTO;
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
public class DenetimTarafService {
    private final Logger LOG = LoggerFactory.getLogger(DenetimTarafService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    DenetimTarafRepository denetimTarafRepository;

    public List<VsynRoleTeamDTO> getTeamByUserId(Long userId) {
        List<VSYNMemberShip> vsnyMemberShipList = denetimTarafRepository.findVSNYMemberShipListByUserId(userId);
        List<VSYNRoleTeam> vsynRoleTeamList = new ArrayList<>();
        for (VSYNMemberShip vsnyMemberShip:vsnyMemberShipList) {
            vsynRoleTeamList.add(vsnyMemberShip.getVsynRoleTeam());
        }
        if (vsnyMemberShipList != null && vsnyMemberShipList.size() > 0) {
            List<VSYNMemberShip> vsnyMemberShips = denetimTarafRepository.findVSNYMemberShipListByVSYNRoleTeamIdList(vsynRoleTeamList);
            List<VsynMemberShipDTO> vsynMemberShipDTOList = convertVsynMemberShipDTOList(vsnyMemberShips);

            if (vsynMemberShipDTOList != null && vsynMemberShipDTOList.size() != 0) {
                return groupingByVsynMemberShipDTOList(vsynMemberShipDTOList);
            }

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

    public Fsm1UserDTO convertFsm1UsersDTO(FSM1Users fsm1Users) {
        Fsm1UserDTO fsm1UserDTO = new Fsm1UserDTO();
        if (fsm1Users != null) {
            fsm1UserDTO.setId(fsm1Users.getID());
            fsm1UserDTO.setAdi(fsm1Users.getFirstName());
            fsm1UserDTO.setSoyadi(fsm1Users.getLastName());
            fsm1UserDTO.setIhr1PersonelDTO(convertIhr1PersonelDTO(fsm1Users.getIhr1Personel()));
            fsm1UserDTO.setIkyPersonelId(fsm1Users.getIkyPersonelId());
        }
        return fsm1UserDTO;
    }

    public FSM1Users convertFsm1Users(Fsm1UserDTO fsm1UserDTO) {

        FSM1Users fsm1Users = new FSM1Users();
        if (fsm1Users != null) {
            fsm1Users.setFirstName(fsm1UserDTO.getAdi());
            fsm1Users.setLastName(fsm1UserDTO.getSoyadi());
            fsm1Users.setIhr1Personel(convertIhr1Personel(fsm1UserDTO.getIhr1PersonelDTO()));
            fsm1Users.setIkyPersonelId(fsm1UserDTO.getIkyPersonelId());
        }

        return fsm1Users;
    }

    public Ihr1PersonelDTO convertIhr1PersonelDTO(IHR1Personel ihr1Personel) {

        Ihr1PersonelDTO ihr1PersonelDTO = new Ihr1PersonelDTO();
        if (ihr1Personel != null) {
            ihr1PersonelDTO.setId(ihr1Personel.getID());
            ihr1PersonelDTO.setAdi(ihr1Personel.getAdi());
            ihr1PersonelDTO.setSoyadi(ihr1Personel.getSoyadi());
            ihr1PersonelDTO.setLhr1GorevTuruDTO(convertLhrGorevTuruDTO(ihr1Personel.getLhr1GorevTuru()));
        }

        return ihr1PersonelDTO;
    }

    public IHR1Personel convertIhr1Personel(Ihr1PersonelDTO ihr1PersonelDTO) {
        IHR1Personel ihr1Personel = new IHR1Personel();
        if (ihr1PersonelDTO != null) {
            ihr1Personel.setID(ihr1PersonelDTO.getId());
            ihr1Personel.setAdi(ihr1PersonelDTO.getAdi());
            ihr1Personel.setSoyadi(ihr1PersonelDTO.getSoyadi());
            ihr1Personel.setLhr1GorevTuru(convertLhrGorevTuru(ihr1PersonelDTO.getLhr1GorevTuruDTO()));
        }
        return ihr1Personel;
    }

    private Lhr1GorevTuruDTO convertLhrGorevTuruDTO(LHR1GorevTuru lhrGorevTuru) {
        Lhr1GorevTuruDTO lhrGorevTuruDTO = new Lhr1GorevTuruDTO();
        if (lhrGorevTuru != null) {
            lhrGorevTuruDTO.setId(lhrGorevTuru.getID());
            lhrGorevTuruDTO.setTanim(lhrGorevTuru.getTanim());
        }

        return lhrGorevTuruDTO;
    }

    public LHR1GorevTuru convertLhrGorevTuru(Lhr1GorevTuruDTO lhrGorevTuruDTO) {
        LHR1GorevTuru lhr1GorevTuru = new LHR1GorevTuru();
        if (lhrGorevTuruDTO != null) {
            lhr1GorevTuru.setID(lhrGorevTuruDTO.getId());
            lhr1GorevTuru.setTanim(lhrGorevTuruDTO.getTanim());
        }
        return lhr1GorevTuru;
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

    public List<BDNTDenetimTespitTaraf> getDenetimTarafListByDenetimId(Long denetimId) {
        return denetimTarafRepository.findDenetimTespitTarafListByDenetimId(denetimId);
    }

    public List<BDNTDenetimTespitTaraf> getDenetimTarafListByDenetimIdAndTarafTuru(Long denetimId, String tarafTuru) {
        return denetimTarafRepository.findDenetimTespitTarafListByDenetimIdAndTarafTuru(denetimId,tarafTuru);
    }

    public List<Fsm1UserDTO> getPersonelListByCurrentUserService(PersonalUniqueRequest personalUniqueRequest) {
        return denetimTarafRepository.findFsm1UsersByUserServisGorev(personalUniqueRequest.getFsm1UsersUSERID());
    }

    public MPI1Paydas getMpi1PaydasById(Long id) {
        return denetimTarafRepository.findMpi1PaydasById(id);
    }
}
