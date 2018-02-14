package com.digikent.denetimyonetimi.service;

import com.digikent.denetimyonetimi.dto.velocity.*;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kadir on 1.02.2018.
 */
@Service
public class ReportService {

    private final Logger LOG = LoggerFactory.getLogger(ReportService.class);

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

        List<TespitDTO> tespitDTOs = new ArrayList<TespitDTO>();
        for (int i = 0; i < 2; i++) {
            tespitDTOs.add(new TespitDTO("Hileli olarak karışık veya standartlara aykırı mal satılması " + i, "50 " + i, " Sebze ve Meyveler ile Yeterli Arz ve Talep Derinliği Bulunan Diğer Malların Ticaretinin Düzenlenmesi Kanunu/Pazar yerleri Yönetmeliği " + i, "5957 " + i, "750 TL " + i,
                    "Mallara ilişkin künyenin ya da malın kalitesine standardına veya gıda güvenliğine ilişkin belgelerde bilerek değişiklik yapılması, bunların tahrif veya taklit edilmesi ya da bunlarda üçüncü şahısları yanıltıcı ifadelere yer verilmesi" + i));
        }
        vc.put("tespitDTOs", tespitDTOs);



        StringWriter sw = new StringWriter();
        t.merge(vc, sw);
        //System.out.println(sw);

        return sw.toString();
    }


}
