package com.digikent.sosyalyardim.rest;

import com.digikent.sosyalyardim.dto.VSY1TespitKayitRequest;
import com.digikent.sosyalyardim.dto.VSY1TespitResponse;
import com.digikent.sosyalyardim.dto.VSY1TespitSorulariResponse;
import com.digikent.sosyalyardim.service.VSY1TespitService;
import com.digikent.sosyalyardim.util.UtilSosyalYardimSaveDTO;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Kadir on 8/16/16.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/sosyalYardim/sy1tespit")
public class VSY1TespitResource {

    private final Logger LOG = LoggerFactory.getLogger(VSY1TespitResource.class);

    @Inject
    private VSY1TespitService vsy1TespitService;

    //Sosyal yardım soruları getirilecek
    @RequestMapping(method = GET, value = "/soru")
    @Transactional
    public ResponseEntity<VSY1TespitSorulariResponse> getTespitSorulari() {
        LOG.info("tespit sorulari getirilecek");
        VSY1TespitSorulariResponse results = vsy1TespitService.getTespitSorulariResponse();
        return new ResponseEntity<VSY1TespitSorulariResponse>(results, OK);
    }

    //Sosyal yardım tespiti kayıt edilecek
    @RequestMapping(method = POST, value = "/kaydet")
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UtilSosyalYardimSaveDTO> saveTespit(@RequestBody VSY1TespitKayitRequest tespitKayitRequest) throws Exception {
        LOG.info("tespit kaydedilecek");
        UtilSosyalYardimSaveDTO results = null;
        try {
            results = vsy1TespitService.saveSosyalYardimTespit(tespitKayitRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return new ResponseEntity<UtilSosyalYardimSaveDTO>(results, OK);
    }

    /**
     * bir dosyaya ait tespitler getirilecek
     * @return
     */
    @RequestMapping(method = GET, value = "/tespitler/{dosyaId}")
    @Transactional
    public ResponseEntity<VSY1TespitResponse> getTespitlerByDosyaId(@PathVariable("dosyaId") Long dosyaId) {
        LOG.info("tespitler getirilecek. dosyaId="+dosyaId);
        VSY1TespitResponse results = vsy1TespitService.getTespitByDosyaId(dosyaId);

        return new ResponseEntity<VSY1TespitResponse>(results, OK);
    }

}
