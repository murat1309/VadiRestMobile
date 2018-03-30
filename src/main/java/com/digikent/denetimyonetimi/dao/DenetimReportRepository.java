package com.digikent.denetimyonetimi.dao;

import com.digikent.denetimyonetimi.dto.rapor.Nsm2Parametre;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kadir on 6.02.2018.
 */
@Repository
public class DenetimReportRepository {

    private final Logger LOG = LoggerFactory.getLogger(DenetimReportRepository.class);

    @Autowired
    SessionFactory sessionFactory;


    public Nsm2Parametre getNSM2Parametre() {
        List<Nsm2Parametre> nsm2ParametreList = new ArrayList<>();
        String sql = "SELECT PRE1IL_ADI,KURUMKISALTMA FROM NSM2PARAMETRE";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if (!list.isEmpty()) {
            for (Object o : list) {
                Map map = (Map) o;
                Nsm2Parametre nsm2Parametre = new Nsm2Parametre();

                String kurumAdi = (String) map.get("KURUMKISALTMA");
                String ilAdi = (String) map.get("PRE1IL_ADI");

                if (kurumAdi != null)
                    nsm2Parametre.setBelediyeAdi(kurumAdi);
                if (ilAdi != null)
                    nsm2Parametre.setIlAdi(ilAdi);

                return nsm2Parametre;
            }
        }

        return new Nsm2Parametre();
    }
}
