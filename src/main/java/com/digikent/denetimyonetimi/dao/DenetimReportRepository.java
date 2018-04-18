package com.digikent.denetimyonetimi.dao;

import com.digikent.denetimyonetimi.dto.rapor.Nsm2Parametre;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.entity.BDNTDenetimTespit;
import com.digikent.general.util.ErrorCode;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.digikent.denetimyonetimi.service.DenetimReportService.getDenetimTespitReportSequenceIdentifierSql;

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

    public BDNTDenetimTespit getDenetimTespitById(Long denetimTespitId) {
        BDNTDenetimTespit bdntDenetimTespit;
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Object o = session.get(BDNTDenetimTespit.class,denetimTespitId);
        bdntDenetimTespit = (BDNTDenetimTespit)o;
        session.close();
        return bdntDenetimTespit;
    }

    public Long getDenetimTespitReportSequenceIdentifierValue(String sql) throws Exception {
        BigDecimal raporNo = null;
        try {

            List list = new ArrayList<>();
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();
            Map map = (Map) list.get(0);
            raporNo = (BigDecimal) map.get("NEXTVAL");
            session.close();

        } catch (Exception e) {
            LOG.debug("Denetim Tespit Rapor : rapor icin sekans getirilirken bir hata ile karsilasildi : " + e.getMessage());
            throw new Exception();
        }

        return raporNo.longValue();
    }

    public Boolean insertDenetimTespitReportNoAndYear(BDNTDenetimTespit bdntDenetimTespit, Long raporNo) throws Exception {
        Long year = (long) Calendar.getInstance().get(Calendar.YEAR);

        bdntDenetimTespit.setYil(year);
        if(bdntDenetimTespit.getDenetimAksiyonu().equalsIgnoreCase("CEZA"))
            bdntDenetimTespit.setCezaNo(raporNo);
        else
            bdntDenetimTespit.setTutanakNo(raporNo);

        try {
            Session session = sessionFactory.openSession();
            Transaction tx = null;
            tx = session.beginTransaction();
            session.update(bdntDenetimTespit);
            tx.commit();
            session.close();
            return true;

        } catch (Exception e) {
            throw new Exception();
        }

    }

    public UtilDenetimSaveDTO insertDenetimTespitReportIdentifier(Long denetimTespitId) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        try {
            BDNTDenetimTespit bdntDenetimTespit = getDenetimTespitById(denetimTespitId);
            String sql = getDenetimTespitReportSequenceIdentifierSql(bdntDenetimTespit.getDenetimAksiyonu());
            Long raporNo = getDenetimTespitReportSequenceIdentifierValue(sql);
            insertDenetimTespitReportNoAndYear(bdntDenetimTespit, raporNo);
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,null);
        } catch (Exception ex) {
            LOG.debug("Rapor numarasi kaydedilirken bir hata ile karsilasildi");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true, ErrorCode.ERROR_CODE_506), null);
        }

        return utilDenetimSaveDTO;
    }
}
