package com.digikent.denetimyonetimi.rest;

import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.service.DenetimDocumentService;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/denetim/document")
public class DenetimDocumentController {
    private final Logger LOG = LoggerFactory.getLogger(DenetimDocumentController.class);

    @Autowired
    DenetimDocumentService denetimDocumentService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/save/photo/{denetimId}", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<UtilDenetimSaveDTO> saveDenetimPhoto (@PathVariable Long denetimId, @RequestPart("files") MultipartFile[] uploadfiles) throws Exception {
        LOG.debug("REST / Denetim icin fotograf yuklenecek denetimId : " + denetimId);
        LOG.debug("yuklenecek fotograf sayisi = " + uploadfiles.length);
        UtilDenetimSaveDTO utilDenetimSaveDTO = new UtilDenetimSaveDTO();
        try {
            for (int i=0; i<uploadfiles.length; i++) {
                LOG.debug((i+1) + ".ci foto kaydedilecek. Adi= " + uploadfiles[i].getOriginalFilename());
                utilDenetimSaveDTO = denetimDocumentService.saveDenetimPhotoToDocumentum("BDNTDENETIM", denetimId, uploadfiles[i].getBytes());
                LOG.debug("Kayit gerceklesti. sirasi ve docName = " + (i+1) + " " + uploadfiles[i].getOriginalFilename());
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
