package com.digikent.surecyonetimi.rest;

import com.digikent.surecyonetimi.dao.SurecYonetimiRepository;
import com.digikent.surecyonetimi.dto.*;
import com.digikent.surecyonetimi.service.SurecYonetimiService;
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Medet on 12/28/2017.
 */

@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/surecyonetimi")
public class SurecYonetimiResource {

    private final Logger LOG = LoggerFactory.getLogger(SurecYonetimiResource.class);

    @Inject
    SurecYonetimiRepository surecYonetimiRepository;

    @Autowired
    SurecYonetimiService surecYonetimiService;

    @RequestMapping(value = "/surecsorgu", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    public SurecSorguResponseDTO getSurecSorguListesiBySorguNo(@RequestBody SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO;

        LOG.debug("Gelen Surec Sorgu No: " + surecSorguRequestDTO.getSorguNo());

        surecSorguResponseDTO = surecYonetimiService.getSurecSorguListesiBySorguNo(surecSorguRequestDTO);

        return surecSorguResponseDTO;
    }
    @RequestMapping(value = "/basvurubilgi", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    public SurecSorguResponseDTO getSurecInfoBySorguNo(@RequestBody SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO;

        LOG.debug("Gelen Surec Sorgu No: " + surecSorguRequestDTO.getSorguNo());

        surecSorguResponseDTO = surecYonetimiService.getSurecInfoBySorguNo(surecSorguRequestDTO);

        return surecSorguResponseDTO;
    }
    @RequestMapping(value = "/kullaniciyorum", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    public SurecSorguResponseDTO getSurecCommentBySorguNo(@RequestBody SurecSorguRequestDTO surecSorguRequestDTO){

        SurecSorguResponseDTO surecSorguResponseDTO;
        LOG.debug("Gelen Surec Sorgu No: " + surecSorguRequestDTO.getSorguNo());
        surecSorguResponseDTO = surecYonetimiService.getSurecCommentBySorguNo(surecSorguRequestDTO);

        return surecSorguResponseDTO;
    }
    @RequestMapping(value = "/basvurubelge", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    public SurecSorguResponseDTO getSurecDocumentBySorguNo(@RequestBody SurecSorguRequestDTO surecSorguRequestDTO){

        SurecSorguResponseDTO surecSorguResponseDTO;
        LOG.debug("Gelen Surec Sorgu No: " + surecSorguRequestDTO.getSorguNo());
        surecSorguResponseDTO = surecYonetimiService.getSurecDocumentBySorguNo(surecSorguRequestDTO);

        return surecSorguResponseDTO;
    }
    @RequestMapping(method = GET, value = "/basvuruturu", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<ImarBasvuruTuruDTO>> getBasvuruTuru() {
        LOG.debug("REST request to get basvuru turu checkbox list");
        List<ImarBasvuruTuruDTO> results = surecYonetimiRepository.getBasvuruTuruList();
        return new ResponseEntity<List<ImarBasvuruTuruDTO>>(results, OK);
    }
    @RequestMapping(method = POST, value = "/imar", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<ImarSurecDTO>> getList(@RequestBody ImarRequestDTO imarRequestDTO) {
        LOG.debug("REST request to get surec parameters");
        List<ImarSurecDTO> results = surecYonetimiRepository.getSurecListBySelected(imarRequestDTO);
        return new ResponseEntity<List<ImarSurecDTO>>(results, OK);
    }
}
