package com.digikent.denetimyonetimi.rest;

import com.digikent.basvuruyonetimi.dto.DM1IsAkısıAdımDTO;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.service.DenetimDocumentService;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.vadi.smartkent.datamodel.domains.icerikyonetimi.dm1.DM1IsAkisiAdim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.MultipartConfigElement;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Kadir on 7.03.2018.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/denetim/document")
public class DenetimDocumentController {
    private final Logger LOG = LoggerFactory.getLogger(DenetimDocumentController.class);

    @Autowired
    DenetimDocumentService denetimDocumentService;

    @RequestMapping(value = "/save/photo/{denetimtespitid}", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<UtilDenetimSaveDTO> saveDenetimPhoto (@PathVariable Long denetimtespitid, @RequestPart("files") MultipartFile[] uploadfiles) throws Exception {
        LOG.debug("REST / Denetim Tespiti icin fotograf yuklenecek");
        LOG.debug("dokuman sayisi = " + uploadfiles.length);
        UtilDenetimSaveDTO utilDenetimSaveDTO = new UtilDenetimSaveDTO();
        try {
            for (int i=0; i<uploadfiles.length; i++) {
                LOG.debug(i + ".ci foto kaydedilecek. Adi= " + uploadfiles[i].getName());
                denetimDocumentService.saveDenetimPhotoToDocumentum("BDNTDENETIMTESPIT", denetimtespitid, uploadfiles[i].getBytes());
                LOG.debug("Kayit gerceklesti. sirasi ve docName = " + i + uploadfiles[i].getName());
            }
        } catch (Exception ex) {
            LOG.debug("HATA OLUSTU");
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false,new ErrorDTO(true,"Fotograf kaydedilirken bir hata olustu"),null);
        }
        return new ResponseEntity<UtilDenetimSaveDTO>(utilDenetimSaveDTO, OK);
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
