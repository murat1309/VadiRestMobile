package com.digikent.denetimyonetimi.rest;

import com.digikent.denetimyonetimi.dto.takim.TeamRequest;
import com.digikent.denetimyonetimi.dto.takim.TeamResponse;
import com.digikent.denetimyonetimi.dto.takim.VsynRoleTeamDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitlerRequest;
import com.digikent.denetimyonetimi.dto.util.PersonalUniqueRequest;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.service.TarafService;
import com.digikent.general.dto.Fsm1UserDTO;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Kadir on 22.02.2018.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/denetim/team")
public class DenetimTeamResource {
    private final Logger LOG = LoggerFactory.getLogger(DenetimTeamResource.class);

    @Autowired
    TarafService tarafService;

    /*
        aktif kullanıcının bulunduğu takim bilgilerini getirir
    */
    @RequestMapping(value = "/list/currentuser", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<TeamResponse> getPaydasListByCriteria(@RequestBody TeamRequest teamRequest) {
        LOG.debug("Takim bilgileri isteniyor. current userID="+teamRequest.getUserId());

        TeamResponse teamResponse = new TeamResponse();
        List<VsynRoleTeamDTO> vsynRoleTeamDTOList = null;
        vsynRoleTeamDTOList = tarafService.getTeamByUserId(teamRequest.getUserId());

        if (vsynRoleTeamDTOList == null) {
            teamResponse.setErrorDTO(new ErrorDTO(true,"takim bulunamadi"));
        } else {
            teamResponse.setErrorDTO(new ErrorDTO(false,null));
        }

        teamResponse.setVsynRoleTeamDTOList(vsynRoleTeamDTOList);
        return new ResponseEntity<TeamResponse>(teamResponse, OK);
    }

    /*
        denetim takımına kullanıcı eklemek için, müdürlük bazlı kullanıcılar çekilir
    */
    @RequestMapping(value = "/list/otherusers", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<Fsm1UserDTO>> getPersonelListByCurrentUserService(@RequestBody PersonalUniqueRequest personalUniqueRequest) {
        LOG.debug("denetim/team/list/otherusers");
        LOG.debug("mudurluk bilgisine göre kullanici listesi cekilecek");
        List<Fsm1UserDTO> fsm1UserDTOList = new ArrayList<>();
        fsm1UserDTOList = tarafService.getPersonelListByCurrentUserService(personalUniqueRequest);
        LOG.debug("Getirilen kullanici sayisi = " + (fsm1UserDTOList != null ? fsm1UserDTOList.size() : 0));
        return new ResponseEntity<List<Fsm1UserDTO>>(fsm1UserDTOList, OK);
    }

}
