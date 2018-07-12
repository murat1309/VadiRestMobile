package com.digikent.sosyalyardim.rest;

import com.digikent.sosyalyardim.dto.*;
import com.digikent.sosyalyardim.service.VSY1AktiviteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("sosyalyardim/aktivite")
public class VSY1AktiviteResource {

    private final Logger LOG = LoggerFactory.getLogger(VSY1AktiviteResource.class);

    @Inject
    private VSY1AktiviteService vsy1AktiviteService;

    @RequestMapping(method = GET, value = "/list/{dosyaId}")
    @Transactional
    public ResponseEntity<VSY1AktiviteResponse> getAktiviteByDosyaId(@PathVariable("dosyaId") Long dosyaId) {
        LOG.info("aktiviteler getirilecek. dosyaId=" + dosyaId);
        VSY1AktiviteResponse results = vsy1AktiviteService.getAktiviteByDosyaId(dosyaId);
        return new ResponseEntity<>(results, OK);
    }

    @RequestMapping(method = POST, value = "/olustur")
    @Transactional
    public ResponseEntity<VSY1AktiviteOlusturDTO> createAktivite(@RequestBody VSY1AktiviteOlusturRequest vsy1AktiviteOlusturRequest) throws Exception {

        LOG.info("aktivite oluşturulacak.");
        VSY1AktiviteOlusturDTO results;
        try {
            results = vsy1AktiviteService.createAktivite(vsy1AktiviteOlusturRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return new ResponseEntity<>(results, OK);

    }

    @RequestMapping(method = GET, value = "/list/islemler")
    public ResponseEntity<VSY1AktiviteIslemlerDTO> getAktiviteIslemler(){
        VSY1AktiviteIslemlerDTO results;
        results = vsy1AktiviteService.getAktiviteIslemler();
        return new ResponseEntity<>(results, OK);
    }

    @RequestMapping(method = GET, value = "/list/calisanlar")
    public ResponseEntity<List<VSY1AktivitePersonDTO>> getSosyalYardimCalisanlar() {
        List<VSY1AktivitePersonDTO> results;
        results = vsy1AktiviteService.getAktivitePeopleList();
        return new ResponseEntity<>(results, OK);
    }

}
