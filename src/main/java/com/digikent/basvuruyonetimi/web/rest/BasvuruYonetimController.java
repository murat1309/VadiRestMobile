package com.digikent.basvuruyonetimi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digikent.basvuruyonetimi.dao.BasvuruYonetimRepository;
import com.digikent.basvuruyonetimi.dto.DM1IsAkısıAdımDTO;
import com.vadi.smartkent.datamodel.domains.icerikyonetimi.dm1.DM1IsAkisi;
import com.vadi.smartkent.datamodel.domains.icerikyonetimi.dm1.DM1IsAkisiAdim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.inject.Inject;
import javax.servlet.MultipartConfigElement;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Serkan on 4/2/2016.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/basvuruYonetim")
public class BasvuruYonetimController {

    private final Logger LOG = LoggerFactory.getLogger(BasvuruYonetimController.class);

    @Inject
    private BasvuruYonetimRepository basvuruYonetimRepository;

    @RequestMapping(value = "/{id}/ddm1isakisi", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<DM1IsAkisi> getDdm1isakisi(@PathVariable Long id) {
        LOG.debug("REST request to get DM1IsAkisi : {}", id);

        return Optional.ofNullable(basvuruYonetimRepository.getDdm1isakisi(id))
                .map(dM1IsAkisi -> new ResponseEntity<>(dM1IsAkisi, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{id}/edm1isakisiadim", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<DM1IsAkısıAdımDTO> getEdm1isakisiadim(@PathVariable Long id) {
        LOG.debug("REST request to get DM1IsAkisiAdim : {}", id);

        DM1IsAkisiAdim edm1isakisiadim = basvuruYonetimRepository.getEdm1isakisiadim(id);

        DM1IsAkısıAdımDTO dm1IsAkısıAdımDTO = new DM1IsAkısıAdımDTO();
        dm1IsAkısıAdımDTO.setId(edm1isakisiadim.getID());
        dm1IsAkısıAdımDTO.setIzahat(edm1isakisiadim.getIzahat());
        dm1IsAkısıAdımDTO.setSonucDurumu(edm1isakisiadim.getSonucDurumu());

        return new ResponseEntity<DM1IsAkısıAdımDTO>(dm1IsAkısıAdımDTO, OK);

    }


    @RequestMapping(value = "/edm1isakisiadim/{personelId}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<DM1IsAkısıAdımDTO> updateEdm1isakisiadim(@PathVariable Long personelId,
                                                                @RequestBody DM1IsAkısıAdımDTO dm1IsAkısıAdımDTO) throws Exception {

        try {
            LOG.debug("REST request to update updateEdm1isakisiadim : {}", dm1IsAkısıAdımDTO.getId());

            DM1IsAkisiAdim edm1isakisiadim = basvuruYonetimRepository.getEdm1isakisiadim(dm1IsAkısıAdımDTO.getId());
            edm1isakisiadim.setIzahat(dm1IsAkısıAdımDTO.getIzahat());
            edm1isakisiadim.setSonucDurumu(dm1IsAkısıAdımDTO.getSonucDurumu());
            basvuruYonetimRepository.updateEdm1isakisiadim(personelId, edm1isakisiadim);

            LOG.debug("This approve was updated a successfully ");
        } catch (Exception ex) {
            dm1IsAkısıAdımDTO.setError("ERROR");
        }


        return new ResponseEntity<DM1IsAkısıAdımDTO>(dm1IsAkısıAdımDTO, OK);

    }

    @RequestMapping(value = "/edm1isakisiadimWithAttachment/{isAkisiId}", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<DM1IsAkısıAdımDTO> updateEdm1isakisiadimWithAttachment(
            @PathVariable Long isAkisiId,
            @RequestPart("files") MultipartFile[] uploadfiles) throws Exception {
        LOG.debug("REST request to update updateEdm1isakisiadimWithAttachment, iskisiId: {}", isAkisiId);
        LOG.debug("count of uploaded document = " + uploadfiles.length);
        DM1IsAkısıAdımDTO dm1IsAkısıAdımDTO = new DM1IsAkısıAdımDTO();
        try {
            DM1IsAkisiAdim edm1isakisiadim = basvuruYonetimRepository.getEdm1isakisiadim(isAkisiId);

            for (int i=0; i<uploadfiles.length; i++) {
                basvuruYonetimRepository.saveToDocumentum("DDM1ISAKISI", edm1isakisiadim.getDdm1isakisiId(), uploadfiles[i].getBytes());
                LOG.debug("This document saved Documentum, docName = (" + i + uploadfiles[i].getName());
            }

        } catch (Exception ex) {
            dm1IsAkısıAdımDTO.setError("ERROR");
        }

        LOG.debug("images was upload");

        return new ResponseEntity<DM1IsAkısıAdımDTO>(dm1IsAkısıAdımDTO, OK);
    }

    @Bean(name = "commonsMultipartResolver")
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        factory.setMaxFileSize("30MB");
        factory.setMaxRequestSize("30MB");

        return factory.createMultipartConfig();
    }

}
