package com.digikent.demirbas.web.rest;

import com.digikent.basvuruyonetimi.dao.BasvuruYonetimRepository;
import com.digikent.demirbas.dao.DemirbasRepository;
import com.digikent.demirbas.dto.DemirbasDTO;
import com.digikent.demirbas.dto.DemirbasHareketDTO;
import com.digikent.demirbas.dto.DemirbasResponseDTO;
import com.digikent.demirbas.dto.TokenDTO;
import org.apache.commons.collections.list.TypedList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.keycloak.common.util.UriUtils;
import org.keycloak.util.JsonSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Kadir on 17/07/17.
 */
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/demirbas")
public class DemirbasResource {

    private final Logger LOG = LoggerFactory.getLogger(DemirbasResource.class);

    @Inject
    DemirbasRepository demirbasRepository;

    @Inject
    private BasvuruYonetimRepository basvuruYonetimRepository;

    @RequestMapping(value = "/{barcodeId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<DemirbasResponseDTO> getDemirbasDTOAndDemirbasHareket(@PathVariable("barcodeId") Long barcodeId) {
        LOG.debug("REST request to get demirbas with id : {}", barcodeId);
        DemirbasDTO demirbasDTO = demirbasRepository.getDemirbasById(barcodeId);
        List<DemirbasHareketDTO> demirbasHareketByDemirbasDTOList = demirbasRepository.getDemirbasHareketByDemirbasDTO(demirbasDTO);
        DemirbasResponseDTO demirbasResponseDTO = new DemirbasResponseDTO();
        demirbasResponseDTO.setDemirbasDTO(demirbasDTO);
        demirbasResponseDTO.setDemirbasHareketDTOList(demirbasHareketByDemirbasDTOList);
        return new ResponseEntity<DemirbasResponseDTO>(demirbasResponseDTO, OK);
    }
    

}
