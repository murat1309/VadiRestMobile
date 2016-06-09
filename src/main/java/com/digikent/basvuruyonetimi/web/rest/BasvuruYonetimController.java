package com.digikent.basvuruyonetimi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digikent.basinyayin.dao.BasinYayinRepository;
import com.digikent.basinyayin.dto.AsmaIndirmeIslemiDTO;
import com.digikent.basinyayin.dto.TtnyekipDTO;
import com.digikent.basinyayin.dto.TtnylokasyonDTO;
import com.digikent.basinyayin.dto.VtnytanitimDTO;
import com.digikent.basvuruyonetimi.dao.BasvuruYonetimRepository;
import com.digikent.basvuruyonetimi.dto.Dm1IsakisiAdimAttachmentDTO;
import com.digikent.web.rest.errors.CustomParameterizedException;
import com.digikent.web.rest.util.HeaderUtil;
import com.vadi.smartkent.datamodel.domains.icerikyonetimi.dm1.DM1IsAkisi;
import com.vadi.smartkent.datamodel.domains.icerikyonetimi.dm1.DM1IsAkisiAdim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by Serkan on 4/2/2016.
 */
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
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
    @Timed
    @Transactional
    public ResponseEntity<DM1IsAkisiAdim> getEdm1isakisiadim(@PathVariable Long id) {
        LOG.debug("REST request to get DM1IsAkisiAdim : {}", id);

        return Optional.ofNullable(basvuruYonetimRepository.getEdm1isakisiadim(id))
                .map(dM1IsAkisiAdim -> new ResponseEntity<>(dM1IsAkisiAdim, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "/edm1isakisiadim/{personelId}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<DM1IsAkisiAdim> updateEdm1isakisiadim(@PathVariable Long personelId,
                                                                @RequestBody DM1IsAkisiAdim dM1IsAkisiAdim) throws Exception {
        LOG.debug("REST request to update DM1IsAkisiAdim : {}", dM1IsAkisiAdim.getID());

        basvuruYonetimRepository.updateEdm1isakisiadim(personelId, dM1IsAkisiAdim);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("DM1IsAkisiAdim", String.valueOf(dM1IsAkisiAdim.getID())))
                .body(dM1IsAkisiAdim);

    }

    @RequestMapping(value = "/edm1isakisiadimWithAttachment/{personelId}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<DM1IsAkisiAdim> updateEdm1isakisiadimWithAttachment(
                                                                @PathVariable Long personelId,
                                                                @RequestBody Dm1IsakisiAdimAttachmentDTO dm1IsakisiAdimAttachmentDTO) throws Exception {
        byte[] attachment = dm1IsakisiAdimAttachmentDTO.getAttachment();
        DM1IsAkisiAdim dm1IsAkisiAdim = dm1IsakisiAdimAttachmentDTO.getDm1IsAkisiAdim();
        LOG.debug("REST request to update DM1IsAkisiAdim : {}", dm1IsAkisiAdim.getID());

        basvuruYonetimRepository.saveToDocumentum("EDM1ISAKISI", dm1IsAkisiAdim.getID(), attachment);
        basvuruYonetimRepository.updateEdm1isakisiadim(personelId, dm1IsAkisiAdim);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("DM1IsAkisiAdim", String.valueOf(dm1IsAkisiAdim.getID())))
                .body(dm1IsAkisiAdim);

    }

}
