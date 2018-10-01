package com.digikent.general.util;

import com.digikent.general.dao.UtilityRepository;
import com.digikent.general.entity.TSM2Params;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Params {

    private static final Logger LOG = LoggerFactory.getLogger(UtilityRepository.class);


    public static Map getParametersByGroup(String paramGroupKodu, SessionFactory sessionFactory) {

        Map<String, String> paramDict = new HashMap<>();
        try {
            Session session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(TSM2Params.class)
                    .createAlias("rsm2ParamGroup", "r")
                    .add(Restrictions.eq("r.kodu", paramGroupKodu));
            List list = criteria.list();
            if (!list.isEmpty()) {
                for (Object aList : list) {
                    TSM2Params tsm2Params = (TSM2Params) aList;
                    String tanim = tsm2Params.getSsm2ParamName().getKodu();
                    String kodu = tsm2Params.getParamValue();
                    if (tanim == null || kodu == null) {
                        LOG.info(ErrorCode.ERROR_CODE_705);
                        throw new Exception("Iziin surecleri parametrelerini getir: Parametre degeri null");
                    }
                    paramDict.put(tanim, kodu);
                }
            }
        } catch (Exception e) {
            LOG.error("Belediye parametreleri getirilirken bir hata ile karsilasildi. Hata kodu : " + e.getMessage());
        }

        return paramDict;
    }
}

