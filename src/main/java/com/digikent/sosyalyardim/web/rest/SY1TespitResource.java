package com.digikent.sosyalyardim.web.rest;

import com.digikent.sosyalyardim.dao.SY1TespitRepository;
import com.digikent.sosyalyardim.web.dto.SY1TespitDTO;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Tespit;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Serkan on 8/16/16.
 */
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/sosyalYardim/sy1tespit")
public class SY1TespitResource {

    @Inject
    private SY1TespitRepository repository;

    @RequestMapping(method = GET, value = "/list", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<SY1TespitDTO>> list() {
        List<SY1TespitDTO> results = repository.list();
        return new ResponseEntity<List<SY1TespitDTO>>(results, OK);
    }


    @RequestMapping(method = POST, value = "/search", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<SY1TespitDTO>> search(@RequestBody SY1Tespit sy1Tespit) {
        List<SY1TespitDTO> results = repository.search(sy1Tespit);
        return new ResponseEntity<List<SY1TespitDTO>>(results, OK);
    }

}
