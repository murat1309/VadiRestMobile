package com.digikent.sosyalyardim.eski.rest;

import com.digikent.sosyalyardim.eski.dao.SY1TespitDetayRepository;
import com.digikent.sosyalyardim.eski.dto.SY1TespitDetayDTO;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1TespitDetay;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Serkan on 8/16/16.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/sosyalYardim/sy1tespitline")
public class VSY1TespitDetayResource {

    @Inject
    private SY1TespitDetayRepository repository;

    @RequestMapping(method = GET, value = "/list", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<SY1TespitDetayDTO>> list() {
        List<SY1TespitDetayDTO> results = repository.list();
        return new ResponseEntity<List<SY1TespitDetayDTO>>(results, OK);
    }

    @RequestMapping(method = POST, value = "/new", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Integer> create(@RequestBody List<SY1TespitDetay> sy1TespitDetay) throws Exception {
        Integer results = repository.create(sy1TespitDetay);
        return new ResponseEntity<Integer>(results, OK);
    }

    @RequestMapping(method = POST, value = "/search", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<SY1TespitDetayDTO>> search(@RequestBody SY1TespitDetay sy1TespitDetay) {
        List<SY1TespitDetayDTO> results = repository.search(sy1TespitDetay);
        return new ResponseEntity<List<SY1TespitDetayDTO>>(results, OK);
    }

    @RequestMapping(method = DELETE, value = "/delete", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Integer> delete(@RequestBody SY1TespitDetay sy1TespitDetay) throws Exception {
        int deletedRows = repository.delete(sy1TespitDetay);
        Integer results = new Integer(deletedRows);
        return new ResponseEntity<Integer>(results, OK);
    }

}
