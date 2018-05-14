package com.digikent.sosyalyardim.dao;

import com.digikent.sosyalyardim.dto.SY1TespitDTO;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Tespit;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Serkan on 8/23/16.
 */
@Repository
public class SY1TespitRepository {

    private final Logger LOG = LoggerFactory.getLogger(SY1DosyaRepository.class);

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    SessionFactory sessionFactory;


    public List<SY1TespitDTO> list(){
        return null;
    }

    public List<SY1TespitDTO> search(SY1Tespit sy1Tespit) {

        String sql = "SELECT id,vsy1dosya_id,izahat,bilgitelefon,tespitbilgisi," +
                "       (SELECT adi || ' ' || soyadi" +
                "          FROM ihr1personel" +
                "         WHERE id = IHR1PERSONEL_TESPITIVEREN) TESPITVEREN," +
                "       tespitverilistarihi,TSY1TESPITDURUMU_ID," +
                "       (SELECT TANIM" +
                "          FROM TSY1TESPITDURUMU" +
                "         WHERE ID = TSY1TESPITDURUMU_ID) TANIM" +
                "  FROM VSY1TESPIT" +
                " WHERE vsy1dosya_id = " + sy1Tespit.getVsy1dosyaId();

        List<Object> list = new ArrayList();

        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        List<SY1TespitDTO> sy1TespitDTOList = new ArrayList();
        for(Object o : list){
            Map map = (Map)o;
            SY1TespitDTO sy1TespitDTO = new SY1TespitDTO();

            BigDecimal id = (BigDecimal)map.get("ID");
            BigDecimal vsy1dosyaId = (BigDecimal)map.get("VSY1DOSYA_ID");
            String izahat = (String)map.get("IZAHAT");
            String bilgitelefon = (String) map.get("BILGITELEFON");
            String tespitbilgisi = (String)map.get("TESPITBILGISI");
            String tespitveren = (String)map.get("TESPITVEREN");
            Date tespitverilistarihi = (Date) map.get("TESPITVERILISTARIHI");
            BigDecimal tespitdurumuid=(BigDecimal)map.get("TSY1TESPITDURUMU_ID");
            String tespitdurumu = (String)map.get("TANIM");

            if(id != null)
                sy1TespitDTO.setId(id.longValue());
            if(vsy1dosyaId != null)
                sy1TespitDTO.setVsy1dosyaId(vsy1dosyaId.longValue());
            if(izahat != null)
                sy1TespitDTO.setIzahat(izahat);
            if(bilgitelefon != null)
                sy1TespitDTO.setBilgitelefon(bilgitelefon);
            if(tespitbilgisi != null)
                sy1TespitDTO.setTespitbilgisi(tespitbilgisi);
            if(tespitveren != null)
                sy1TespitDTO.setTespitveren(tespitveren);
            if(tespitverilistarihi != null)
                sy1TespitDTO.setTespitverilistarihi(dateFormat.format(tespitverilistarihi));
            if(tespitdurumu != null)
                sy1TespitDTO.setTespitdurumu(tespitdurumu);
            if(tespitdurumuid != null)
                sy1TespitDTO.setTespitdurumuid(tespitdurumuid.longValue());

            sy1TespitDTOList.add(sy1TespitDTO);
        }

        return sy1TespitDTOList;
    }
}
