package com.digikent.sosyalyardim.web.rest;

import com.digikent.sosyalyardim.dao.SY1DosyaRepository;
import com.digikent.sosyalyardim.web.dto.SY1DosyaDTO;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Dosya;
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
@RequestMapping("/sosyalYardim/sy1dosya")
public class SY1DosyaResource {

    @Inject
    private SY1DosyaRepository repository;

    @RequestMapping(method = GET, value = "/list", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<SY1DosyaDTO>> list() {

        List<SY1DosyaDTO> results = repository.list();
        return new ResponseEntity<List<SY1DosyaDTO>>(results, OK);
    }

    @RequestMapping(method = POST, value = "/search", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<SY1DosyaDTO>> search(@RequestBody SY1Dosya sy1Dosya) {
        List<SY1DosyaDTO> results = repository.search(sy1Dosya);
        return new ResponseEntity<List<SY1DosyaDTO>>(results, OK);
    }


}
