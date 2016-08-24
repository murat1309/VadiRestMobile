package com.digikent.sosyalyardim.dao;

import com.digikent.sosyalyardim.web.dto.SY1TespitDTO;
import com.digikent.sosyalyardim.web.dto.SY1TespitDetayDTO;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Tespit;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1TespitDetay;
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
public class SY1TespitDetayRepository {

    private final Logger LOG = LoggerFactory.getLogger(SY1DosyaRepository.class);

    @Autowired
    SessionFactory sessionFactory;


    public List<SY1TespitDetayDTO> list(){
        return null;
    }

    public List<SY1TespitDetayDTO> search(SY1TespitDetay sy1TespitDetay) {

        String sql = "  SELECT B.ID KATEGORIID, " +
                "         B.TANIM SORUGRUBU, " +
                "         A.ID SORUID, " +
                "         A.TANIM SORU, " +
                "         C.TANIM ALANTURU, " +
                "         DECODE (C.TANIM, " +
                "                 'chb', SY1.F_TespitDegeri ("+ sy1TespitDetay.getVsy1tespit2().getID() +", A.ID), " +
                "                 'rdb', SY1.F_TespitDegeri ("+ sy1TespitDetay.getVsy1tespit2().getID() +", A.ID), " +
                "                 '') " +
                "            CBRBDEGERI, " +
                "         DECODE (C.TANIM, " +
                "                 'txtNumber', SY1.F_TespitDegeri ("+ sy1TespitDetay.getVsy1tespit2().getID() +", A.ID), " +
                "                 'txtString', SY1.F_TespitDegeri ("+ sy1TespitDetay.getVsy1tespit2().getID() +", A.ID), " +
                "                 '') " +
                "            STNMDEGERI, " +
                "         (SELECT COUNT (*) " +
                "            FROM VSY1TESPITLINE " +
                "           WHERE     TSY1TESPITKATEGORI_ID = b.id " +
                "                 AND (DEGER > 0 OR bilgi IS NOT NULL) " +
                "                 AND VSY1TESPIT_ID = "+ sy1TespitDetay.getVsy1tespit2().getID() +") " +
                "            AS COUNTCEVAP " +
                "    FROM TSY1TESPITSORU A, TSY1TESPITKATEGORI B, TSY1TESPITSORUTURU C " +
                "   WHERE     B.ID = A.TSY1TESPITKATEGORI_ID " +
                "         AND C.ID = A.TSY1TESPITSORUTURU_ID " +
                "         AND B.ISACTIVE = 'E' " +
                "         AND A.ISACTIVE = 'E' " +
                "ORDER BY DECODE (B.SIRA, '0', '', B.SIRA), B.ID, A.SIRA";

        List<Object> list = new ArrayList();

        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        List<SY1TespitDetayDTO> sy1TespitDetayDTOList = new ArrayList();
        for(Object o : list){
            Map map = (Map)o;
            SY1TespitDetayDTO sy1TespitDetayDTO = new SY1TespitDetayDTO();

            BigDecimal kategoriid = (BigDecimal)map.get("KATEGORIID");
            String sorugrubu = (String)map.get("SORUGRUBU");
            BigDecimal soruid = (BigDecimal) map.get("SORUID");
            String soru = (String) map.get("SORU");
            String alanturu = (String)map.get("ALANTURU");
            String cbrbdegeri = (String)map.get("CBRBDEGERI");
            String stnmdegeri = (String) map.get("STNMDEGERI");
            BigDecimal countcevap=(BigDecimal)map.get("COUNTCEVAP");

            if(kategoriid != null)
                sy1TespitDetayDTO.setKategoriid(kategoriid.longValue());
            if(sorugrubu != null)
                sy1TespitDetayDTO.setSorugrubu(sorugrubu);
            if(soruid != null)
                sy1TespitDetayDTO.setSoruid(soruid.longValue());
            if(soru != null)
                sy1TespitDetayDTO.setSoru(soru);
            if(alanturu != null)
                sy1TespitDetayDTO.setAlanturu(alanturu);
            if(cbrbdegeri != null)
                sy1TespitDetayDTO.setCbrbdegeri(cbrbdegeri);
            if(stnmdegeri != null)
                sy1TespitDetayDTO.setStnmdegeri(stnmdegeri);
            if(countcevap != null)
                sy1TespitDetayDTO.setCountcevap(countcevap.intValue());

            sy1TespitDetayDTOList.add(sy1TespitDetayDTO);
        }

        return sy1TespitDetayDTOList;
    }
}
