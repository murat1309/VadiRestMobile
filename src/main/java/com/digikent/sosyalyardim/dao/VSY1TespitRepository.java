package com.digikent.sosyalyardim.dao;

import com.digikent.config.Constants;
import com.digikent.denetimyonetimi.dto.tespit.TespitDTO;
import com.digikent.denetimyonetimi.entity.IHR1Personel;
import com.digikent.sosyalyardim.dto.VSY1TespitKayitRequest;
import com.digikent.sosyalyardim.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public void saveTespit(VSY1TespitKayitRequest tespitKayitRequest, List<VSY1TespitLine> tespitLineList, VSY1Tespit vsy1Tespit) throws Exception {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = null;
            tx = session.beginTransaction();
            vsy1Tespit.setDosyaId(tespitKayitRequest.getDosyaId());
            vsy1Tespit.setTarih(new Date());
            vsy1Tespit.setIsActive(true);
            vsy1Tespit.setIhr1PersonelId(tespitKayitRequest.getIhr1personelId());

            if (tespitKayitRequest.getAktiviteId() != null) {
                VSY1Aktivite vsy1Aktivite = (VSY1Aktivite) session.get(VSY1Aktivite.class, tespitKayitRequest.getAktiviteId());

                TSY1AktiviteIslem tsy1AktiviteIslem = new TSY1AktiviteIslem();
                tsy1AktiviteIslem.setID(2);

                vsy1Aktivite.setTsy1AktiviteIslem(tsy1AktiviteIslem);
                vsy1Aktivite.setBitisTarihi(new Date());
                vsy1Tespit.setVsy1Aktivite(vsy1Aktivite);
            }
            IHR1Personel ihr1Personel = new IHR1Personel();
            ihr1Personel.setID(tespitKayitRequest.getIhr1personelId());
            vsy1Tespit.setIhr1PersonelTespitYapan(ihr1Personel);

            vsy1Tespit.setCrUser(tespitKayitRequest.getFsm1UsersId());
            vsy1Tespit.setVsy1TespitLineList(tespitLineList);
            vsy1Tespit.setCrDate(new Date());
            session.save(vsy1Tespit);
            tx.commit();
            LOG.info("Tespit basariyla kayit edildi");
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

    public List<VSY1Tespit> findTespitByDosyaId(Long dosyaId, Long aktiviteId) throws Exception {

        List<VSY1Tespit> list = null;

        try {
            Session session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(VSY1Tespit.class);
            criteria.add(Restrictions.or(Restrictions.eq("isActive", true), Restrictions.isNull("isActive")));
            criteria.add(Restrictions.eq("dosyaId", dosyaId));
            criteria.addOrder(Order.desc("tarih"));
            if (aktiviteId != null) {
                criteria.createAlias("vsy1Aktivite", "v");
                criteria.add(Restrictions.eq("v.ID", aktiviteId));
            }

            list = criteria.list();
        } catch (Exception ex) {
            LOG.error("Tespit listesi çekilirken hata oluştu");
            ex.printStackTrace();
            throw new Exception("");
        }

        return list;
    }
}
