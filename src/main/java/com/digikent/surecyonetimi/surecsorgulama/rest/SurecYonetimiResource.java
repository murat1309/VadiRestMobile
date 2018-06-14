package com.digikent.surecyonetimi.surecsorgulama.rest;

import com.digikent.surecyonetimi.surecsorgulama.dao.SurecYonetimiRepository;
import com.digikent.surecyonetimi.surecsorgulama.dto.basvurudetay.SurecSorguRequestDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.basvurudetay.SurecSorguResponseDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.imarsurec.BasvuruTuruDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.imarsurec.ImarRequestDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.imarsurec.ImarSurecDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.yapidenetimsurec.YapiDenetimDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.yapidenetimsurec.YapiDenetimRequestDTO;
import com.digikent.surecyonetimi.surecsorgulama.service.SurecYonetimiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @RequestMapping(method = POST, value = "/basvuruturu", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<BasvuruTuruDTO>> getBasvuruTuru(@RequestBody String tabName) {
        LOG.debug("REST request to get basvuruTuru checkbox list for imar or yapidenetim");
        List<BasvuruTuruDTO> results = surecYonetimiRepository.getBasvuruTuruList(tabName);
        return new ResponseEntity<List<BasvuruTuruDTO>>(results, OK);
    }
    @RequestMapping(method = POST, value = "/imar", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<ImarSurecDTO>> getList(@RequestBody ImarRequestDTO imarRequestDTO) {
        LOG.debug("REST request to get imar surec parameters");
        List<ImarSurecDTO> results = surecYonetimiRepository.getImarSurecList(imarRequestDTO);
        return new ResponseEntity<List<ImarSurecDTO>>(results, OK);
    }
    @RequestMapping(method = POST, value = "/yapidenetim", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<YapiDenetimDTO>> getList(@RequestBody YapiDenetimRequestDTO yapiDenetimRequestDTO) {
        LOG.debug("REST request to get yapi denetim surec parameters");
        List<YapiDenetimDTO> results = surecYonetimiRepository.getDenetimSurecList(yapiDenetimRequestDTO);
        return new ResponseEntity<List<YapiDenetimDTO>>(results, OK);
    }

}
