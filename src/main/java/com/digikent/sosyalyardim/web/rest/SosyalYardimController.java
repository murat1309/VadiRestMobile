package com.digikent.sosyalyardim.web.rest;

import com.digikent.sosyalyardim.service.SosyalYardimService;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Dosya;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.vadi.dto.SY1DosyaDTO;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Serkan on 8/16/16.
 */
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/sosyalYardim/sy1dosya")
public class SosyalYardimController {

    @Inject
    private SosyalYardimService service;

//    @RequestMapping(method = GET, value = "/list", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<SY1Dosya>> list() {
//
//        List<SY1Dosya> results = service.list();
//        return new ResponseEntity<List<SY1Dosya>>(results, OK);
//    }
//
//    @RequestMapping(method = POST, value = "/new", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<SY1Dosya> create(@RequestBody SY1Dosya sy1Dosya) {
//        SY1Dosya saved = service.save(sy1Dosya);
//        return new ResponseEntity<SY1Dosya>(saved, OK);
//    }
//
//    @RequestMapping(method = PUT, value = "/update/{id}", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<SY1Dosya> update(@PathVariable(value = "id") Long id, @RequestBody SY1Dosya sy1Dosya) {
//        SY1Dosya updated = service.save(id, sy1Dosya);
//        return new ResponseEntity<SY1Dosya>(updated, OK);
//    }

    @RequestMapping(method = POST, value = "/search", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SY1DosyaDTO>> search(@RequestBody SY1Dosya sy1Dosya) {
        List<SY1DosyaDTO> results = service.search(sy1Dosya);
        return new ResponseEntity<List<SY1DosyaDTO>>(results, OK);
    }

//    @RequestMapping(method = PUT, value = "/delete/{id}", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<SY1Dosya> softDelete(@PathVariable(value = "id") String id, @RequestBody SY1Dosya saklamaplaniDTO) {
//        try {
//            service.delete(Long.valueOf(id));
//            return new ResponseEntity<SY1Dosya>(OK);
//        } catch (NumberFormatException e) {
//            return new ResponseEntity<SY1Dosya>(INTERNAL_SERVER_ERROR);
//        }
//    }
}
