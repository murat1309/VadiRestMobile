package com.digikent.sosyalyardim.dao;

import com.digikent.denetimyonetimi.entity.IHR1Personel;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.sosyalyardim.dto.*;
import com.digikent.sosyalyardim.entity.TSY1AktiviteIslem;
import com.digikent.sosyalyardim.entity.VSY1Aktivite;
import com.digikent.config.Constants;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class VSY1AktiviteRepository {

    private final Logger LOG = LoggerFactory.getLogger(VSY1AktiviteRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public ArrayList<VSY1Aktivite> getAktiviteByDosyaId(Long dosyaId) throws Exception {
        VSY1AktiviteResponse vsy1AktiviteResponse = new VSY1AktiviteResponse();
        ErrorDTO errorDTO = new ErrorDTO();
        ArrayList<VSY1Aktivite> list = new ArrayList<>();

        try {
            Session session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(VSY1Aktivite.class);
            criteria.createAlias("tsy1AktiviteIslem", "t");
            criteria.add(Restrictions.or(Restrictions.eq("isActive", true), Restrictions.isNull("isActive")));
            criteria.add(Restrictions.ne("t.tanim", Constants.SOSYAL_YARDIM_AKTIVITE_TANIM_TAMAMLANDI));
            criteria.add(Restrictions.eq("dosyaId", dosyaId));
            criteria.addOrder(Order.desc("baslamaTarihi"));

            list = (ArrayList<VSY1Aktivite>) criteria.list();
        } catch (Exception ex) {
            LOG.error("Aktivite listesi çekilirken hata oluştu");
            ex.printStackTrace();
            errorDTO.setError(true);
            errorDTO.setErrorMessage("Aktivite listesi çekilirken hata oluştu");
            vsy1AktiviteResponse.setErrorDTO(errorDTO);
        }

        return list;

    }

    public VSY1AktiviteOlusturDTO createAktivite(VSY1AktiviteOlusturRequest vsy1AktiviteOlusturRequest) throws Exception {
        VSY1AktiviteOlusturDTO vsy1AktiviteOlusturDTO = new VSY1AktiviteOlusturDTO();
        VSY1Aktivite vsy1Aktivite = new VSY1Aktivite();
        try {
            Session session = sessionFactory.openSession();
            Transaction tx;
            tx = session.beginTransaction();

            vsy1Aktivite.setCrDate(new Date());
            vsy1Aktivite.setBaslamaTarihi(new Date());
            vsy1Aktivite.setIsActive(true);
            vsy1Aktivite.setDosyaId(vsy1AktiviteOlusturRequest.getDosyaId());
            vsy1Aktivite.setCrUser(vsy1AktiviteOlusturRequest.getFsm1UsersId());

            TSY1AktiviteIslem tsy1AktiviteIslem = new TSY1AktiviteIslem();
            tsy1AktiviteIslem.setID(vsy1AktiviteOlusturRequest.getTsy1AktiviteIslemId());
            vsy1Aktivite.setTsy1AktiviteIslem(tsy1AktiviteIslem);

            IHR1Personel ihr1PersonelVeren = new IHR1Personel();
            ihr1PersonelVeren.setID(vsy1AktiviteOlusturRequest.getIhr1PersonelVerenId());
            vsy1Aktivite.setIhr1PersonelVeren(ihr1PersonelVeren);

            IHR1Personel ihr1PersonelVerilen = new IHR1Personel();
            ihr1PersonelVerilen.setID(vsy1AktiviteOlusturRequest.getIhr1PersonelVerilenId());
            vsy1Aktivite.setIhr1PersonelVerilen(ihr1PersonelVerilen);

            session.save(vsy1Aktivite);
            tx.commit();
            vsy1AktiviteOlusturDTO.setAktiviteId(vsy1Aktivite.getID());

        } catch (Exception ex) {
            LOG.error("Aktivite oluşturulurken hata olustu!");
            ex.printStackTrace();
            throw new Exception("Aktivite oluşturulurken hata olustu!");
        }


        return vsy1AktiviteOlusturDTO;

    }

    public VSY1AktiviteIslemlerDTO getAktiviteIslemler() {
        VSY1AktiviteIslemlerDTO vsy1AktiviteIslemlerDTO = new VSY1AktiviteIslemlerDTO();
        List<VSY1AktiviteIslemlerLineDTO> vsy1AktiviteIslemlerLineDTOList;
        try {
            Session session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(TSY1AktiviteIslem.class);
            vsy1AktiviteIslemlerLineDTOList = (ArrayList<VSY1AktiviteIslemlerLineDTO>) criteria.list();
            vsy1AktiviteIslemlerDTO.setVsy1AktiviteIslemlerLineDTOList(vsy1AktiviteIslemlerLineDTOList);
        } catch (Exception e) {
            LOG.error("Aktivite işlem listesi getirilemedi!");
            e.printStackTrace();
            vsy1AktiviteIslemlerDTO.setErrorDTO(new ErrorDTO(true, "Aktivite işlem listesi getirilemedi!"));
        }
        return vsy1AktiviteIslemlerDTO;
    }

    public List<VSY1AktivitePersonDTO> getAktivitePeopleList() {
        String sql = "select (select adisoyadi from ihr1personel where id = ihr1personel_id) as ADSOYAD , (select ID from ihr1personel where id = ihr1personel_id) AS ID" +
                " from IHR1ISLEV where IHR1ISLEVTIPI_ID in " +
                "(select id from IHR1ISLEVTIPI where kayitozelismi = 'SOSYALYARDIMCALISANI' ) order by ADSOYAD";

        List list;

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        list = query.list();
        VSY1AktivitePersonDTO vsy1AktivitePersonDTO;
        List<VSY1AktivitePersonDTO> vsy1AktivitePersonDTOList = new ArrayList<>();


        if (!list.isEmpty()) {
            for (Object o : list) {
                Map map = (Map) o;
                vsy1AktivitePersonDTO = new VSY1AktivitePersonDTO();
                String adSoyad = (String) map.get("ADSOYAD");
                BigDecimal id = (BigDecimal) map.get("ID");

                if (adSoyad != null) {
                    vsy1AktivitePersonDTO.setAdSoyad(adSoyad);
                    vsy1AktivitePersonDTO.setId(id.longValue());
                }
                vsy1AktivitePersonDTOList.add(vsy1AktivitePersonDTO);

            }
        }
        return vsy1AktivitePersonDTOList;
    }

}
