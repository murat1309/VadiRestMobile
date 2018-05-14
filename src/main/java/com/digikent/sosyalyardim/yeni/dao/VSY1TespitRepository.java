package com.digikent.sosyalyardim.yeni.dao;

import com.digikent.sosyalyardim.yeni.dto.VSY1TespitSorulariDTO;
import com.digikent.sosyalyardim.yeni.entity.TSY1TespitKategori;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 14.05.2018.
 */
@Repository
public class VSY1TespitRepository {
    private final Logger LOG = LoggerFactory.getLogger(VSY1TespitRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public List<TSY1TespitKategori> findTespitSorulariList() {
        List<TSY1TespitKategori> kategoriCriteriaList = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        TSY1TespitKategori tsy1TespitKategori = null;

        try {
            Criteria criteria = session.createCriteria(TSY1TespitKategori.class);
            criteria.add(Restrictions.eq("isActive", "E"));
            kategoriCriteriaList = criteria.list();
        } catch (Exception ex) {
            LOG.error("Tespit kategorileri/sorulari getirilirken bir hata olustu. Mesaj : " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }

        return kategoriCriteriaList;
    }
}
