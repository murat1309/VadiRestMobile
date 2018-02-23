package com.digikent.denetimyonetimi.rest;

import com.digikent.general.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Kadir on 22.02.2018.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/denetim/team")
public class DenetimTeamResource {
    private final Logger LOG = LoggerFactory.getLogger(DenetimResource.class);

    @Autowired
    TeamService teamService;

}
