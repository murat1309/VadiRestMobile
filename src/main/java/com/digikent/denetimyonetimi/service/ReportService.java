package com.digikent.denetimyonetimi.service;

import com.digikent.config.Constants;
import com.digikent.denetimyonetimi.dao.DenetimRepository;
import com.digikent.denetimyonetimi.dto.velocity.*;
import com.digikent.denetimyonetimi.entity.BDNTDenetimTespit;
import com.digikent.denetimyonetimi.entity.BDNTDenetimTespitLine;
import com.digikent.denetimyonetimi.entity.LDNTTespit;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Kadir on 1.02.2018.
 */
@Service
public class ReportService {

    private final Logger LOG = LoggerFactory.getLogger(ReportService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    DenetimRepository denetimRepository;

    public String createDenetimReport() {

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();
        Template t = ve.getTemplate("templates/template.vm", "UTF-8");
        VelocityContext vc = new VelocityContext();

        UserDTO userDTO = new UserDTO("Ahmet", "Korkmaz", "12345678901", "+90 212 123 4567");
        vc.put("userDTO", userDTO);

        DocumentDTO documentDTO = new DocumentDTO(new Date(), "147852369");
        vc.put("documentDTO", documentDTO);

        LocationDTO locationDTO = new LocationDTO("Batı", "Aydınlı", "Bahar", "36", "12", "23", "44");
        vc.put("locationDTO", locationDTO);

        List<InformationDTO> informationDTOs = new ArrayList<InformationDTO>();
        for (int i = 0; i < 2; i++) {
            informationDTOs.add(new InformationDTO("Araç Marka " + i, "Audi " + i,
                    "Ekipman eksikliği " + i));
        }
        vc.put("informationDTOs", informationDTOs);

        List<ReportTespitDTO> reportTespitDTOs = new ArrayList<ReportTespitDTO>();
        for (int i = 0; i < 2; i++) {
            reportTespitDTOs.add(new ReportTespitDTO("Hileli olarak karışık veya standartlara aykırı mal satılması " + i, "50 " + i, " Sebze ve Meyveler ile Yeterli Arz ve Talep Derinliği Bulunan Diğer Malların Ticaretinin Düzenlenmesi Kanunu/Pazar yerleri Yönetmeliği " + i, "5957 " + i,
                    "Mallara ilişkin künyenin ya da malın kalitesine standardına veya gıda güvenliğine ilişkin belgelerde bilerek değişiklik yapılması, bunların tahrif veya taklit edilmesi ya da bunlarda üçüncü şahısları yanıltıcı ifadelere yer verilmesi" + i));
        }
        vc.put("reportTespitDTOs", reportTespitDTOs);



        StringWriter sw = new StringWriter();
        t.merge(vc, sw);
        //System.out.println(sw);

        return sw.toString();
    }

    public String createCezaDenetimReport(Long denetimTespitId) {

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();
        Template t = ve.getTemplate("templates/template.vm", "UTF-8");
        VelocityContext vc = new VelocityContext();

        BDNTDenetimTespit bdntDenetimTespit = denetimRepository.findDenetimTespitById(denetimTespitId);
        vc.put("reportTespitDTOs", getTespitReportData(bdntDenetimTespit));

        StringWriter sw = new StringWriter();
        t.merge(vc, sw);

        return sw.toString();
    }

    public List<ReportTespitDTO> getTespitReportData(BDNTDenetimTespit bdntDenetimTespit) {
        List<Long> tespitIdList = new ArrayList<>();
        for (BDNTDenetimTespitLine tespitLine:bdntDenetimTespit.getBdntDenetimTespitLineList()) {
            tespitIdList.add(tespitLine.getTespitId());
        }
        Map<Long,LDNTTespit> tespitMap = denetimRepository.findTespitMapByTespitIdList(tespitIdList);

        List<ReportTespitDTO> reportTespitDTOs = new ArrayList<>();
        for (BDNTDenetimTespitLine denetimTespitLine:bdntDenetimTespit.getBdntDenetimTespitLineList()) {
            LDNTTespit ldntTespit = tespitMap.get(denetimTespitLine.getTespitId());
            if (ldntTespit.getAksiyon().equalsIgnoreCase(Constants.TESPIT_AKSIYON_TYPE_CEZA) || ldntTespit.getAksiyon().equalsIgnoreCase(Constants.TESPIT_AKSIYON_TYPE_EKBILGI)) {
                ReportTespitDTO reportTespitDTO = new ReportTespitDTO();
                reportTespitDTO.setTespitAciklamasi(ldntTespit.getTanim());
                reportTespitDTO.setAciklama(denetimTespitLine.getTextValue());
                reportTespitDTO.setDayanakKanunu(ldntTespit.getLsm2Kanun().getTanim());
                //tespit secenek türüne göre setleme yapılıyor
                if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_CHECHBOX) && denetimTespitLine.getStringValue() != null) {
                    reportTespitDTO.setDeger(denetimTespitLine.getStringValue());
                } else if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_TEXT) && denetimTespitLine.getStringValue() != null) {
                    reportTespitDTO.setDeger(denetimTespitLine.getStringValue());
                } else if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_DATE) && denetimTespitLine.getDateValue() != null) {
                    reportTespitDTO.setDeger(denetimTespitLine.getDateValue().toString());
                } else if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_NUMBER) && denetimTespitLine.getNumberValue() != null) {
                    reportTespitDTO.setDeger(denetimTespitLine.getNumberValue().toString());
                } else {
                    reportTespitDTO.setDeger(" ");
                }
                reportTespitDTOs.add(reportTespitDTO);
            }
        }
        return reportTespitDTOs;
    }


}
