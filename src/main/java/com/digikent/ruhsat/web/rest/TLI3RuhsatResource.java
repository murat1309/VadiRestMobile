package com.digikent.ruhsat.web.rest;

import com.digikent.config.Constants;
import com.digikent.ruhsat.dao.TLI3RuhsatRepository;
import com.digikent.ruhsat.dto.*;
import com.digikent.sosyalyardim.dao.SY1DosyaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Serkan on 10/14/16.
 */
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/ruhsat")
public class TLI3RuhsatResource {

    private final Logger LOG = LoggerFactory.getLogger(TLI3RuhsatResource.class);

    @Inject
    private TLI3RuhsatRepository repository;

    @RequestMapping(method = POST, value = "/tli3Ruhsat/barcode", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<TLI3RuhsatDTO>> getRuhsatByBarcodeId(@RequestBody TLI3RuhsatDTO tli3RuhsatDTO) {
        LOG.debug("REST request to get ruhsat with barcode id : {}", tli3RuhsatDTO.getId());
        List<TLI3RuhsatDTO> results = repository.getRuhsatByBarcodeId(tli3RuhsatDTO);
        return new ResponseEntity<List<TLI3RuhsatDTO>>(results, OK);
    }

    @RequestMapping(method = POST, value = "/tli3Ruhsat/paydasNo", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<TLI3RuhsatDTO>> getRuhsatByPaydasNo(@RequestBody TLI3RuhsatDTO tli3RuhsatDTO) {
        LOG.debug("REST request to get ruhsat with paydas no : {}", tli3RuhsatDTO.getMpi1PaydasId());
        List<TLI3RuhsatDTO> results = null;
        results = repository.getRuhsatByPaydasNo(tli3RuhsatDTO);
        return new ResponseEntity<List<TLI3RuhsatDTO>>(results, OK);
    }


    @RequestMapping(method = POST, value = "/tli3Ruhsat/basvuru", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<RuhsatDurumuDTO>> getRuhsatBasvuruDurumuByPaydasNo(@RequestBody TLI3RuhsatDTO tli3RuhsatDTO) {
        LOG.debug("REST request to get ruhsat with paydas no : {}", tli3RuhsatDTO.getMpi1PaydasId());
        List<RuhsatDurumuDTO> results = null;
        results = repository.getRuhsatBasvuruByPaydasNo(tli3RuhsatDTO.getMpi1PaydasId().longValue());
        return new ResponseEntity<List<RuhsatDurumuDTO>>(results, OK);
    }

    @RequestMapping(method = POST, value = "/tli3Ruhsat/address", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<TLI3RuhsatDTO>> getRuhsatByAdres(@RequestBody TLI3RuhsatDTO tli3RuhsatDTO) {
        LOG.debug("REST request to get ruhsat with adres");
        List<TLI3RuhsatDTO> results = null;

        if (tli3RuhsatDTO.getBinaId() == null) {
            results = repository.getRuhsatByAddressWithoutBina(tli3RuhsatDTO);
        } else {
            results = repository.getRuhsatByAddressWithBina(tli3RuhsatDTO);
        }

        return new ResponseEntity<List<TLI3RuhsatDTO>>(results, OK);
    }

    @RequestMapping(method = GET, value = "/tli3Ruhsat/ruhsatturu", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<TLI3RuhsatTuruDTO>> getRuhsatTuru() {
        LOG.debug("REST request to get all ruhsat turu");
        List<TLI3RuhsatTuruDTO> results = repository.getRuhsatTuru();
        return new ResponseEntity<List<TLI3RuhsatTuruDTO>>(results, OK);
    }

    @RequestMapping(method = POST, value = "/tli3Ruhsat/tur", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<TLI3RuhsatDTO>> getRuhsatByRuhsatTuru(@RequestBody TLI3RuhsatTuruRequestDTOList tLI3RuhsatTuruRequestDTOList) {
        LOG.debug("REST request to get ruhsat by ruhsatturu ");
        List<TLI3RuhsatDTO> results = repository.getRuhsatByRuhsatTuru(tLI3RuhsatTuruRequestDTOList);
        return new ResponseEntity<List<TLI3RuhsatDTO>>(results, OK);
    }

    @RequestMapping(method = GET, value = "/tli3Ruhsat/mahalle", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<DRE1MahalleDTO>> getMahalleList() {
        LOG.debug("REST request to get all mahalle");
        List<DRE1MahalleDTO> results = repository.getMahalleList();
        return new ResponseEntity<List<DRE1MahalleDTO>>(results, OK);
    }

    @RequestMapping(method = GET, value = "/tli3Ruhsat/sokak/{mahalleId}", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<SRE1SokakDTO>> getSokakListByMahalle(@PathVariable("mahalleId") Long mahalleId) {
        LOG.debug("REST request to get all sokak by mahalleID = " + mahalleId);
        List<SRE1SokakDTO> results = repository.getSokakByMahalleId(mahalleId);
        return new ResponseEntity<List<SRE1SokakDTO>>(results, OK);
    }

    @RequestMapping(method = GET, value = "/tli3Ruhsat/bina/{sokakId}", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<ERE1YapiDTO>> getBinaByMahalleAndSokak(@PathVariable("sokakId") Long sokakId) {
        LOG.debug("REST request to get all bina by sokakId = " + sokakId);
        List<ERE1YapiDTO> results = repository.getBinaBySokakId(sokakId);
        return new ResponseEntity<List<ERE1YapiDTO>>(results, OK);
    }


}
