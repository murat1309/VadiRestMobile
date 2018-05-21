package com.digikent.sosyalyardim.yeni.dao;

import com.digikent.sosyalyardim.yeni.dto.VSY1TespitKayitRequest;
import com.digikent.sosyalyardim.yeni.entity.TSY1TespitKategori;
import com.digikent.sosyalyardim.yeni.entity.VSY1Tespit;
import com.digikent.sosyalyardim.yeni.entity.VSY1TespitLine;
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
import java.util.Date;
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

    public Long saveTespit(VSY1TespitKayitRequest tespitKayitRequest, List<VSY1TespitLine> tespitLineList, VSY1Tespit vsy1Tespit) throws Exception {
        Object o = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = null;
            tx = session.beginTransaction();
            vsy1Tespit.setDosyaId(tespitKayitRequest.getDosyaId());
            vsy1Tespit.setIhr1PersonelId(tespitKayitRequest.getIhr1personelId());
            vsy1Tespit.setCrUser(tespitKayitRequest.getFsm1UsersId());
            vsy1Tespit.setVsy1TespitLineList(tespitLineList);
            vsy1Tespit.setCrDate(new Date());
            o = session.save(vsy1Tespit);
            tx.commit();
            LOG.info("Tespit basariyla kayit edildi");
            return ((Long) o).longValue();

        } catch (Exception ex) {
            LOG.error("Tespit kaydedilirken hata olustu");
            ex.printStackTrace();
            throw new Exception("Tespit kaydedilirken hata olustu");
        }




    }

    public void saveTespitLineByTespitId(List<VSY1TespitLine> tespitLineList, Long tespitId) throws Exception {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = null;
            tx = session.beginTransaction();
            VSY1Tespit vsy1Tespit = null;
            vsy1Tespit = new VSY1Tespit(tespitId);
            vsy1Tespit.setVsy1TespitLineList(tespitLineList);
            session.update(vsy1Tespit);
            tx.commit();
        } catch (Exception ex) {
            LOG.error("Tespit kaydedilirken hata oluştu");
            throw new Exception("Tespit kaydedilirken hata oluştu");
        }
    }
}
