package com.digikent.denetimyonetimi.dao;

import com.digikent.denetimyonetimi.dto.adres.*;
import com.digikent.security.SecurityUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kadir on 5.03.2018.
 */
@Repository
public class DenetimAddressRepository {

    private final Logger LOG = LoggerFactory.getLogger(DenetimAddressRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public List<MahalleSokakDTO> findMahalleAndSokakListByBelediyeId(Long belediyeId) {

        String sql = "SELECT A.ID AS MAHALLEID, A.TANIM AS MAHALLEADI, A.RRE1ILCE_ID AS ILCEID, B.ID AS SOKAKID, B.TANIM AS SOKAKADI FROM DRE1MAHALLE A, SRE1SOKAK B\n" +
                "WHERE MAHALLEADI IS NOT NULL AND SOKAKADI IS NOT NULL AND RRE1ILCE_ID=" + belediyeId +
                " AND A.ID=B.DRE1MAHALLE_ID AND NVL(A.ISACTIVE,'E') = 'E' AND NVL(B.ISACTIVE,'E') = 'E' ORDER BY A.ID";

        List list = new ArrayList<>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        User authenticatedUser = SecurityUtils.getAuthenticatedUser();

        List<MahalleSokakDTO> mahalleSokakDTOList = new ArrayList<>();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                MahalleSokakDTO mahalleSokakDTO= new MahalleSokakDTO();

                BigDecimal mahalleId = (BigDecimal) map.get("MAHALLEID");
                String mahalleAdi = (String) map.get("MAHALLEADI");
                BigDecimal sokakId = (BigDecimal) map.get("SOKAKID");
                String sokakAdi = (String) map.get("SOKAKADI");
                BigDecimal ilceId = (BigDecimal) map.get("ILCEID");

                if(mahalleId != null)
                    mahalleSokakDTO.setMahalleId(mahalleId.longValue());
                if(mahalleAdi != null)
                    mahalleSokakDTO.setMahalleAdi(mahalleAdi);
                if(sokakId != null)
                    mahalleSokakDTO.setSokakId(sokakId.longValue());
                if(sokakAdi != null)
                    mahalleSokakDTO.setSokakAdi(sokakAdi);
                if(ilceId != null)
                    mahalleSokakDTO.setIlceId(ilceId.longValue());

                mahalleSokakDTOList.add(mahalleSokakDTO);
            }
        }

        return mahalleSokakDTOList;
    }

    /**
     * ilId bilgisi null ise, geçerli ildeki belediyeleri döner
     * ilId bilgisi nullden farklı ise ilId bilgisine göre ilçeleri döner
     * @param ilId
     * @return
     */
    public List<BelediyeDTO> findBelediyeList(Long ilId) {
        List<BelediyeDTO> belediyeDTOList = new ArrayList<>();
        String sql = "";
        if (ilId != null) {
            sql = "SELECT ID,TANIM FROM RRE1ILCE WHERE TANIM IS NOT NULL AND PRE1IL_ID = " + ilId + " AND ID > 0  AND NVL(ISACTIVE,'E') = 'E' ORDER BY NLSSORT(TANIM, 'NLS_SORT=turkish')";
        } else {
            sql = "SELECT ID,TANIM FROM RRE1ILCE WHERE TANIM IS NOT NULL AND PRE1IL_ID = (SELECT PRE1IL_ID FROM NSM2PARAMETRE) AND ID > 0  AND NVL(ISACTIVE,'E') = 'E' ORDER BY NLSSORT(TANIM, 'NLS_SORT=turkish') ";
        }
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                BelediyeDTO belediyeDTO = new BelediyeDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");

                if(id != null)
                    belediyeDTO.setId(id.longValue());
                if(tanim != null)
                    belediyeDTO.setTanim(tanim);

                belediyeDTOList.add(belediyeDTO);
            }
        }


        return belediyeDTOList;
    }

    public List<MahalleDTO> findMahalleListByBelediyeId(Long belediyeId) {
        List<MahalleDTO> mahalleDTOList = new ArrayList<>();
        String sql = "SELECT ID, TANIM, RRE1ILCE_ID FROM DRE1MAHALLE WHERE TANIM IS NOT NULL AND ID > 0 AND RRE1ILCE_ID=" + belediyeId +" AND NVL(ISACTIVE,'E') = 'E' ORDER BY NLSSORT(TANIM, 'NLS_SORT=turkish')";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                MahalleDTO mahalleDTO = new MahalleDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");
                BigDecimal rre1IlceId = (BigDecimal) map.get("RRE1ILCE_ID");

                if(id != null)
                    mahalleDTO.setId(id.longValue());
                if(tanim != null)
                    mahalleDTO.setTanim(tanim);
                if(rre1IlceId != null)
                    mahalleDTO.setRre1IlceId(rre1IlceId.longValue());

                mahalleDTOList.add(mahalleDTO);
            }
        }
        return mahalleDTOList;
    }

    public List<SokakDTO> findSokakListByMahalleId(Long mahalleId) {
        List<SokakDTO> sokakDTOList = new ArrayList<>();
        String sql = "SELECT A.ID,A.TANIM FROM DRE1MAHALLESOKAK B INNER JOIN SRE1SOKAK A ON B.SRE1SOKAK_ID=A.ID \n" +
                " WHERE A.TANIM IS NOT NULL AND B.DRE1MAHALLE_ID=" + mahalleId + " AND B.ID>0 AND A.ID>0 \n" +
                " AND NVL(A.ISACTIVE,'E') = 'E' AND NVL(B.ISACTIVE,'E') = 'E' \n" +
                " ORDER BY NLSSORT(TANIM, 'NLS_SORT=turkish')";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                SokakDTO sokakDTO = new SokakDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");

                if(id != null)
                    sokakDTO.setId(id.longValue());
                if(tanim != null)
                    sokakDTO.setTanim(tanim);

                sokakDTOList.add(sokakDTO);
            }
        }
        return sokakDTOList;
    }

    public List<MahalleDTO> findMahalleListByCurrentBelediye() {
        List<MahalleDTO> mahalleDTOList = new ArrayList<>();
        String sql = "SELECT ID,TANIM,RRE1ILCE_ID FROM DRE1MAHALLE WHERE TANIM IS NOT NULL AND RRE1ILCE_ID = (SELECT RRE1ILCE_ID FROM NSM2PARAMETRE) AND NVL(ISACTIVE,'E') = 'E' ORDER BY NLSSORT(TANIM, 'NLS_SORT=turkish')";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                MahalleDTO mahalleDTO = new MahalleDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");
                BigDecimal rre1IlceId = (BigDecimal) map.get("RRE1ILCE_ID");

                if(id != null)
                    mahalleDTO.setId(id.longValue());
                if(tanim != null)
                    mahalleDTO.setTanim(tanim);
                if(rre1IlceId != null)
                    mahalleDTO.setRre1IlceId(rre1IlceId.longValue());

                mahalleDTOList.add(mahalleDTO);
            }
        }
        return mahalleDTOList;
    }

    public List<IlDTO> findIlList() {
        List<IlDTO> ilDTOList = new ArrayList<>();
        String sql = "SELECT ID, TANIM FROM PRE1IL WHERE TANIM IS NOT NULL AND ID > 0 AND NVL(ISACTIVE,'E') = 'E' ORDER BY NLSSORT(TANIM, 'NLS_SORT=turkish')";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                IlDTO ilDTO = new IlDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");

                if(id != null)
                    ilDTO.setId(id.longValue());
                if(tanim != null)
                    ilDTO.setTanim(tanim);

                ilDTOList.add(ilDTO);
            }
        }
        return ilDTOList;
    }

    public String findBelediyeAdres() {
        String sql = "SELECT NVL(SM2.F_PARAMETRE('BELEDIYE', 'ADRES'),'-') AS BELEDIYEADRES FROM DUAL";
        List list = new ArrayList<>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                String adres = (String) map.get("BELEDIYEADRES");

                if(adres != null)
                    return adres;
            }
        }
        return null;
    }


}
