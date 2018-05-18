package com.digikent.sosyalyardim.yeni.rest;

import com.digikent.sosyalyardim.eski.dao.SY1TespitRepository;
import com.digikent.sosyalyardim.eski.dto.SY1TespitDTO;
import com.digikent.sosyalyardim.yeni.dto.VSY1TespitSorulariResponse;
import com.digikent.sosyalyardim.yeni.service.VSY1TespitService;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Tespit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Serkan on 8/16/16.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/sosyalYardim/sy1tespit")
public class VSY1TespitResource {

    private final Logger LOG = LoggerFactory.getLogger(VSY1TespitResource.class);

    @Inject
    private SY1TespitRepository repository;

    @Inject
    private VSY1TespitService vsy1TespitService;

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


    //YENI

    @RequestMapping(method = GET, value = "/soru")
    @Transactional
    public ResponseEntity<VSY1TespitSorulariResponse> getTespitSorulari() {
        LOG.info("tespit sorulari getirilecek");
        VSY1TespitSorulariResponse results = vsy1TespitService.getTespitSorulariResponse();
        return new ResponseEntity<VSY1TespitSorulariResponse>(results, OK);
    }

    @RequestMapping(method = POST, value = "/kaydet")
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<VSY1TespitSorulariResponse> saveTespit(@RequestBody ) {
        LOG.info("tespit kaydedilecek");
        VSY1TespitSorulariResponse results = vsy1TespitService.getTespitSorulariResponse();
        return new ResponseEntity<VSY1TespitSorulariResponse>(results, OK);
    }

}
