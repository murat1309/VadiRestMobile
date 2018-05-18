package com.digikent.sosyalyardim.yeni.rest;

import com.digikent.sosyalyardim.yeni.dao.SY1DosyaRepository;
import com.digikent.sosyalyardim.yeni.dto.SY1DosyaDTO;
import com.digikent.sosyalyardim.yeni.dto.SYS1DosyaRequest;
import com.digikent.sosyalyardim.yeni.service.VSY1DosyaService;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Dosya;
import org.springframework.http.ResponseEntity;
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
 * Edited by Kadir on 05/11/18.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/sosyalYardim/sy1dosya")
public class VSY1DosyaResource {

    @Inject
    private SY1DosyaRepository sy1DosyaRepository;

    @Inject
    private VSY1DosyaService VSY1DosyaService;

    @RequestMapping(method = GET, value = "/list", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<SY1DosyaDTO>> list() {
        List<SY1DosyaDTO> results = sy1DosyaRepository.list();
        return new ResponseEntity<List<SY1DosyaDTO>>(results, OK);
    }

    @RequestMapping(method = POST, value = "/search", produces = APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<SY1DosyaDTO>> search(@RequestBody SY1Dosya sy1Dosya) {
        List<SY1DosyaDTO> results = sy1DosyaRepository.search(sy1Dosya);
        return new ResponseEntity<List<SY1DosyaDTO>>(results, OK);
    }


    //YENI

    @RequestMapping(method = POST, value = "/arama")
    @Transactional
    public ResponseEntity<List<SY1DosyaDTO>> getDosyaByCriteria(@RequestBody SYS1DosyaRequest sys1DosyaRequest) {
        List<SY1DosyaDTO> results = VSY1DosyaService.getDosyaByCriteria(sys1DosyaRequest);
        return new ResponseEntity<List<SY1DosyaDTO>>(results, OK);
    }


}
