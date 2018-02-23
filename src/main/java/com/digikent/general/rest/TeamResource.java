package com.digikent.general.rest;

import com.digikent.denetimyonetimi.dto.takim.VsynMemberShipDTO;
import com.digikent.denetimyonetimi.dto.takim.TeamRequest;
import com.digikent.denetimyonetimi.dto.takim.TeamResponse;
import com.digikent.denetimyonetimi.dto.takim.VsynRoleTeamDTO;
import com.digikent.denetimyonetimi.rest.DenetimResource;
import com.digikent.general.service.TeamService;
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
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Kadir on 22.02.2018.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/team")
public class TeamResource {
    private final Logger LOG = LoggerFactory.getLogger(DenetimResource.class);

    @Autowired
    TeamService teamService;

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
        vsynRoleTeamDTOList = teamService.getTeamByUserId(teamRequest.getUserId());

        teamResponse.setVsynRoleTeamDTOList(vsynRoleTeamDTOList);
        teamResponse.setErrorDTO(new ErrorDTO(false,null));

        return new ResponseEntity<TeamResponse>(teamResponse, OK);
    }

}
