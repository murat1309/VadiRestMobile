package com.digikent.general.rest;

import com.digikent.general.dto.BelediyeParamResponseDTO;
import com.digikent.general.service.UtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Medet on 4/9/2018.
 */

@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/utility")
public class UtilityResource {

    private final Logger LOG = LoggerFactory.getLogger(UtilityResource.class);

    @Autowired
    UtilityService utilityService;

    @RequestMapping(value="/get/belediye/parameters", method = RequestMethod.GET)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    public ResponseEntity<BelediyeParamResponseDTO> getBelediyeParams() {
        BelediyeParamResponseDTO belediyeParamResponseDTO = null;

        belediyeParamResponseDTO = utilityService.getBelediyeParams();

        return new ResponseEntity<BelediyeParamResponseDTO>(belediyeParamResponseDTO, OK);
    }


}
