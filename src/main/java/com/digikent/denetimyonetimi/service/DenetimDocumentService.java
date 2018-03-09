package com.digikent.denetimyonetimi.service;

import com.digikent.basvuruyonetimi.dao.BasvuruYonetimRepository;
import com.digikent.denetimyonetimi.dao.DenetimDocumentRepository;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by Kadir on 7.03.2018.
 */
@Service
public class DenetimDocumentService {

    private final Logger LOG = LoggerFactory.getLogger(DenetimReportService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    DenetimDocumentRepository denetimDocumentRepository;

    @Inject
    BasvuruYonetimRepository basvuruYonetimRepository;

    public UtilDenetimSaveDTO saveDenetimPhotoToDocumentum(String tableName, Long denetimtespitid, byte[] bytes) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;

        try {
            denetimDocumentRepository.saveToDocumentum(tableName, denetimtespitid, bytes);
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true, null, null);
        } catch (Exception e) {
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false,new ErrorDTO(true,"Fotograf kaydedilirken bir hata olustu"),null);
            LOG.debug("Denetime Fotograf kaydedilirken hata olustu, hata mesaji = " + e.getMessage());
            e.printStackTrace();
        }
        return utilDenetimSaveDTO;
    }
}
