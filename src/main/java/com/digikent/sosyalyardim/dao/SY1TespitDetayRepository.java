package com.digikent.sosyalyardim.dao;

import com.digikent.sosyalyardim.web.dto.SY1TespitDetayDTO;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1TespitDetay;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
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

    public Integer create(List <SY1TespitDetay> tespitDetayList) throws Exception {

        for(SY1TespitDetay tespitDetay : tespitDetayList){
            List<Object> list = new ArrayList();
            String sql = "select VSY1TESPITLINE_ID.nextval from DUAL";
            Long tespitLineId = null;
            try {
                SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
                list = query.list();
                tespitLineId = ((BigDecimal) list.get(0)).longValue();
            }catch(Exception e){
                LOG.debug("Id Alınırken Hata Olustu:{]", tespitDetay);
                throw new Exception("ID alınırken hata olustu");
            }

            if(tespitLineId != null){
                try {
                    Query sqlQuery = sessionFactory.getCurrentSession().
                            createSQLQuery("insert into VSY1TESPITLINE(ID,VSY1TESPIT_ID,VSY1DOSYA_ID, TSY1TESPITSORU_ID, BILGI, DEGER)" +
                                    " values (:id, :tespitId, :dosyaId, :tespitSoruId, :bilgi, :deger)");
                    sqlQuery.setParameter("id", tespitLineId);
                    sqlQuery.setParameter("tespitId", tespitDetay.getVsy1tespit2().getID());
                    sqlQuery.setParameter("dosyaId", tespitDetay.getVsy1dosyaId());
                    sqlQuery.setParameter("tespitSoruId", tespitDetay.getTsy1tespitsoruId());
                    sqlQuery.setParameter("bilgi", tespitDetay.getBilgi());
                    sqlQuery.setParameter("deger", tespitDetay.getDeger());
                    sqlQuery.executeUpdate();
                }catch (Exception e){
                    LOG.debug("Id Alınırken Hata Olustu:{]", tespitDetay);
                    throw new Exception("Kayıt olusturuken hata olustu");
                }

            }
        }
        return new Integer(tespitDetayList.size());
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
            BigDecimal cbrbdegeri = (BigDecimal)map.get("CBRBDEGERI");
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
                sy1TespitDetayDTO.setCbrbdegeri(cbrbdegeri.toString());
            if(stnmdegeri != null)
                sy1TespitDetayDTO.setStnmdegeri(stnmdegeri);
            if(countcevap != null)
                sy1TespitDetayDTO.setCountcevap(countcevap.intValue());

            sy1TespitDetayDTOList.add(sy1TespitDetayDTO);
        }

        return sy1TespitDetayDTOList;
    }

    public int delete(SY1TespitDetay sy1TespitDetay) throws Exception {
        Long tespitId = sy1TespitDetay.getVsy1tespit2().getID();
        int result = 0;
        try{
            Query query = sessionFactory.getCurrentSession().createQuery("delete SY1TespitDetay v where v.vsy1tespit2.id = :tespitId");
            query.setParameter("tespitId", tespitId);
            result = query.executeUpdate();
        }catch (Exception e){
            LOG.debug("Kayıt Silerken Hata Olustu:{]", sy1TespitDetay.getID());
            throw new Exception("Kayıt Silerken hata olustu");
        }


        return result;
    }
}
