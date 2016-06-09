package com.digikent.basinyayin.dao;

import com.digikent.basinyayin.dto.AsmaIndirmeIslemiDTO;
import com.digikent.basinyayin.dto.TtnyekipDTO;
import com.digikent.basinyayin.dto.TtnylokasyonDTO;
import com.digikent.basinyayin.dto.VtnytanitimDTO;
import com.digikent.web.rest.errors.CustomParameterizedException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Serkan on 4/1/2016.
 */
@Repository
public class BasinYayinRepository {
    @PersistenceContext
    EntityManager entityManager;

    protected Session getCurrentSession()  {
        return entityManager.unwrap(Session.class);
    }

    String asmaIndirmeIslemiGeneral =
            "select A.ID as id, \n" +
            "         A.KONUSU as tanitimKonusu,\n" +
            "         A.BARKODNUMARASI as tanitimBarkodNumarasi,\n" +
            "         C.ASMATARIHI as asmaTarihi,\n" +
            "         C.INDIRMETARIHI as indirmeTarihi,\n" +
            "         D.TANIM as lokasyon,\n" +
            "         E.TANIM as lokasyonAlani,\n" +
            "         E.BARKODNUMARASI as lokasyonAlaniBarkodNumarasi\n" +
            "from VTNYTANITIM a,VTNYTANITIMEKIP b,VTNYTANITIMUYGULAMA c,TTNYLOKASYON d,TTNYLOKASYONALAN e\n" +
            " where a.ID = b.VTNYTANITIM_ID\n" +
            "     and b.ID = c.VTNYTANITIMEKIP_ID\n" +
            "     and d.ID = c.TTNYLOKASYON_ID\n" +
            "     and e.ID = c.TTNYLOKASYONALAN_ID\n" +
            "     and B.TTNYEKIP_ID = :ekipId\n";

    public List<TtnyekipDTO> getEkipList(){
        Session session = getCurrentSession();
        String sqlQuery = "select ID as id, KODU as kodu, TANIM as tanim, ISACTIVE as isActive " +
                          "from TTNYEKIP";

        SQLQuery query = session.createSQLQuery(sqlQuery);
        List<TtnyekipDTO> ttnyekipDTOs =query
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("isActive", StandardBasicTypes.STRING)
                .addScalar("kodu", StandardBasicTypes.STRING)
                .addScalar("tanim", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(TtnyekipDTO.class))
                .list();

        return ttnyekipDTOs;
    }

    public List<AsmaIndirmeIslemiDTO> getAsmaIslemiBekleyen(@Param("ekipId") Long ekipId){
        Session session = getCurrentSession();
        String asmaIslemiBekleyen = asmaIndirmeIslemiGeneral + "and c.ASMATARIHI IS NULL";

        SQLQuery query = session.createSQLQuery(asmaIslemiBekleyen);
        List<AsmaIndirmeIslemiDTO> asmaIndirmeIslemiDTOs =
                query
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("tanitimKonusu", StandardBasicTypes.STRING)
                .addScalar("tanitimBarkodNumarasi", StandardBasicTypes.INTEGER)
                .addScalar("asmaTarihi", StandardBasicTypes.DATE)
                .addScalar("indirmeTarihi", StandardBasicTypes.DATE)
                .addScalar("lokasyon", StandardBasicTypes.STRING)
                .addScalar("lokasyonAlani", StandardBasicTypes.STRING)
                .addScalar("lokasyonAlaniBarkodNumarasi", StandardBasicTypes.INTEGER)
                .setResultTransformer(Transformers.aliasToBean(AsmaIndirmeIslemiDTO.class))
                .setParameter("ekipId", ekipId)
                .list();

        return asmaIndirmeIslemiDTOs;
    }

    public List<AsmaIndirmeIslemiDTO> getAsmaIslemiYapilan(@Param("ekipId") Long ekipId){
        Session session = getCurrentSession();
        String asmaIslemiYapilan = asmaIndirmeIslemiGeneral + "and c.ASMATARIHI IS NOT NULL";

        SQLQuery query = session.createSQLQuery(asmaIslemiYapilan);
        List<AsmaIndirmeIslemiDTO> asmaIndirmeIslemiDTOs =
                query
                        .addScalar("id", StandardBasicTypes.LONG)
                        .addScalar("tanitimKonusu", StandardBasicTypes.STRING)
                        .addScalar("tanitimBarkodNumarasi", StandardBasicTypes.INTEGER)
                        .addScalar("asmaTarihi", StandardBasicTypes.DATE)
                        .addScalar("indirmeTarihi", StandardBasicTypes.DATE)
                        .addScalar("lokasyon", StandardBasicTypes.STRING)
                        .addScalar("lokasyonAlani", StandardBasicTypes.STRING)
                        .addScalar("lokasyonAlaniBarkodNumarasi", StandardBasicTypes.INTEGER)
                        .setResultTransformer(Transformers.aliasToBean(AsmaIndirmeIslemiDTO.class))
                        .setParameter("ekipId", ekipId)
                        .list();

        return asmaIndirmeIslemiDTOs;
    }

    public List<AsmaIndirmeIslemiDTO> getIndirmeIslemiBekleyen(@Param("ekipId") Long ekipId){
        Session session = getCurrentSession();
        String indirmeIslemiBekleyen = asmaIndirmeIslemiGeneral + "and c.ASMATARIHI IS NOT NULL and c.INDIRMETARIHI IS NULL";

        SQLQuery query = session.createSQLQuery(indirmeIslemiBekleyen);
        List<AsmaIndirmeIslemiDTO> asmaIndirmeIslemiDTOs =
                query
                        .addScalar("id", StandardBasicTypes.LONG)
                        .addScalar("tanitimKonusu", StandardBasicTypes.STRING)
                        .addScalar("tanitimBarkodNumarasi", StandardBasicTypes.INTEGER)
                        .addScalar("asmaTarihi", StandardBasicTypes.DATE)
                        .addScalar("indirmeTarihi", StandardBasicTypes.DATE)
                        .addScalar("lokasyon", StandardBasicTypes.STRING)
                        .addScalar("lokasyonAlani", StandardBasicTypes.STRING)
                        .addScalar("lokasyonAlaniBarkodNumarasi", StandardBasicTypes.INTEGER)
                        .setResultTransformer(Transformers.aliasToBean(AsmaIndirmeIslemiDTO.class))
                        .setParameter("ekipId", ekipId)
                        .list();

        return asmaIndirmeIslemiDTOs;
    }

    public List<AsmaIndirmeIslemiDTO> getIndirmeIslemiYapilan(@Param("ekipId") Long ekipId){
        Session session = getCurrentSession();
        String indirmeIslemiYapilan = asmaIndirmeIslemiGeneral + "and c.INDIRMETARIHI IS NOT NULL";

        SQLQuery query = session.createSQLQuery(indirmeIslemiYapilan);
        List<AsmaIndirmeIslemiDTO> asmaIndirmeIslemiDTOs =
                query
                        .addScalar("id", StandardBasicTypes.LONG)
                        .addScalar("tanitimKonusu", StandardBasicTypes.STRING)
                        .addScalar("tanitimBarkodNumarasi", StandardBasicTypes.INTEGER)
                        .addScalar("asmaTarihi", StandardBasicTypes.DATE)
                        .addScalar("indirmeTarihi", StandardBasicTypes.DATE)
                        .addScalar("lokasyon", StandardBasicTypes.STRING)
                        .addScalar("lokasyonAlani", StandardBasicTypes.STRING)
                        .addScalar("lokasyonAlaniBarkodNumarasi", StandardBasicTypes.INTEGER)
                        .setResultTransformer(Transformers.aliasToBean(AsmaIndirmeIslemiDTO.class))
                        .setParameter("ekipId", ekipId)
                        .list();

        return asmaIndirmeIslemiDTOs;
    }

    public Long getLastAsmaId(@Param("locationBarkod") Long locationBarkod,
                            @Param("presentationBarkod") Long presentationBarkod,
                            @Param("ekipId") Long ekipId) throws Exception {
        Session session = getCurrentSession();

        String sqlQuery="select Max(C.ID)\n" +
                "from VTNYTANITIM a,VTNYTANITIMEKIP b,VTNYTANITIMUYGULAMA c,TTNYLOKASYON d,TTNYLOKASYONALAN e\n" +
                " where a.ID = b.VTNYTANITIM_ID\n" +
                "     and b.ID = c.VTNYTANITIMEKIP_ID\n" +
                "     and d.ID = c.TTNYLOKASYON_ID\n" +
                "     and e.ID = c.TTNYLOKASYONALAN_ID\n" +
                "     and B.TTNYEKIP_ID = :ekipId\n" +
                "     and E.BARKODNUMARASI = :locationBarkod\n" +
                "     and A.BARKODNUMARASI = :presentationBarkod\n" +
                "     AND c.ASMATARIHI IS NULL";

        SQLQuery query = session.createSQLQuery(sqlQuery);
        List maxAsma =query
                .setParameter("ekipId", ekipId)
                .setParameter("locationBarkod", locationBarkod)
                .setParameter("presentationBarkod", presentationBarkod)
                .list();

        if(maxAsma == null || maxAsma.get(0) == null)
            throw new Exception("Girilen bilgiler icin asma islemi yapilamaz");

        BigDecimal maxAsmaId = (BigDecimal)maxAsma.get(0);
        return maxAsmaId.longValue();

    }

    public void updateAsmaIslemi(@Param("id")Long id,
                                 @Param("koordinatX")Double koordinatX,
                                 @Param("koordinatY")Double koordinatY){
        Session session = getCurrentSession();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();

        String sqlQuery =  "Update VTNYTANITIMUYGULAMA a SET a.ASMATARIHI = :currentDate,\n " +
                "A.ASMALOKASYONLATITUDE = :koordinatX, A.ASMALOKASYONLONGITUDE = :koordinatY\n" +
                " Where ID = :id";

        SQLQuery query = session.createSQLQuery(sqlQuery);
        int updatedRows = query
                .setParameter("currentDate", currentDate)
                .setParameter("koordinatX", koordinatX)
                .setParameter("koordinatY", koordinatY)
                .setParameter("id", id)
                .executeUpdate();

    }

    public Long getLastIndirmeId(@Param("locationBarkod") Long locationBarkod,
                              @Param("presentationBarkod") Long presentationBarkod,
                              @Param("ekipId") Long ekipId) throws Exception {
        Session session = getCurrentSession();

        String sqlQuery="select Max(C.ID)\n" +
                "from VTNYTANITIM a,VTNYTANITIMEKIP b,VTNYTANITIMUYGULAMA c,TTNYLOKASYON d,TTNYLOKASYONALAN e\n" +
                " where a.ID = b.VTNYTANITIM_ID\n" +
                "     and b.ID = c.VTNYTANITIMEKIP_ID\n" +
                "     and d.ID = c.TTNYLOKASYON_ID\n" +
                "     and e.ID = c.TTNYLOKASYONALAN_ID\n" +
                "     and B.TTNYEKIP_ID = :ekipId\n" +
                "     and E.BARKODNUMARASI = :locationBarkod\n" +
                "     and A.BARKODNUMARASI = :presentationBarkod\n" +
                "     and c.ASMATARIHI IS NOT NULL and c.INDIRMETARIHI IS NULL";

        SQLQuery query = session.createSQLQuery(sqlQuery);
        List maxIndirme =query
                .setParameter("ekipId", ekipId)
                .setParameter("locationBarkod", locationBarkod)
                .setParameter("presentationBarkod", presentationBarkod)
                .list();


        if(maxIndirme == null || maxIndirme.get(0) == null)
            throw new Exception("Girilen bilgiler icin indirme islemi yapilamaz");

        BigDecimal maxIndirmeId = (BigDecimal)maxIndirme.get(0);
        return maxIndirmeId.longValue();

    }

    public void updateIndirmeIslemi(@Param("id")Long id,
                                 @Param("koordinatX")Double koordinatX,
                                 @Param("koordinatY")Double koordinatY){
        Session session = getCurrentSession();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();

        String sqlQuery =  "Update VTNYTANITIMUYGULAMA a SET a.INDIRMETARIHI = :currentDate,\n " +
                "A.INDIRMELOKASYONLATITUDE = :koordinatX, A.INDIRMELOKASYONLONGITUDE = :koordinatY\n" +
                " Where ID = :id";

        SQLQuery query = session.createSQLQuery(sqlQuery);
        int updatedRows = query
                .setParameter("currentDate", currentDate)
                .setParameter("koordinatX", koordinatX)
                .setParameter("koordinatY", koordinatY)
                .setParameter("id", id)
                .executeUpdate();

    }

    public List<VtnytanitimDTO> getVtnytanitimByBarcodeNumber(@Param("barcodeNumber") Long barcodeNumber){
        Session session = getCurrentSession();

        String sqlQuery = "select \n" +
                "PLANLANANBASLANGIC as planlananBaslangic,\n" +
                "PLANLANANBITIS as planlananBitis,\n" +
                "GERCEKLESENBASLANGIC as gerceklesenBaslangic,\n" +
                "GERCEKLESENBITIS as gerceklesenBitis,\n" +
                "KONUSU as konusu,\n" +
                "IZAHAT as izahat,\n" +
                "BARKODNUMARASI as barkodNumarasi\n" +
                " from VTNYTANITIM\n" +
                " where BARKODNUMARASI = :barcodeNumber";

        SQLQuery query = session.createSQLQuery(sqlQuery);
        List<VtnytanitimDTO> vtnytanitimDTOs = query
                .addScalar("planlananBaslangic", StandardBasicTypes.DATE)
                .addScalar("planlananBitis", StandardBasicTypes.DATE)
                .addScalar("gerceklesenBaslangic", StandardBasicTypes.DATE)
                .addScalar("gerceklesenBitis", StandardBasicTypes.DATE)
                .addScalar("konusu", StandardBasicTypes.STRING)
                .addScalar("izahat", StandardBasicTypes.STRING)
                .addScalar("barkodNumarasi", StandardBasicTypes.LONG)
                .setResultTransformer(Transformers.aliasToBean(VtnytanitimDTO.class))
                .setParameter("barcodeNumber", barcodeNumber)
                .list();

            return vtnytanitimDTOs;
    }

    public List<TtnylokasyonDTO> getTtnylokasyonByBarcodeNumber(@Param("barcodeNumber") Long barcodeNumber){
        Session session = getCurrentSession();

        String sqlQuery = " select \n" +
                "     id as id,\n" +
                "     TANIM as tanim,\n" +
                "     KODU as kodu,\n" +
                "      TTNYBOLGE_ID as ttnyBolgeId\n" +
                "     from TTNYLOKASYON \n" +
                "     where id=(select TTNYLOKASYON_ID from TTNYLOKASYONALAN\n" +
                "      where barkodnumarasi = :barcodeNumber )";

        SQLQuery query = session.createSQLQuery(sqlQuery);
        List<TtnylokasyonDTO> ttnylokasyonDTOs = query
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("tanim", StandardBasicTypes.STRING)
                .addScalar("kodu", StandardBasicTypes.STRING)
                .addScalar("ttnyBolgeId", StandardBasicTypes.LONG)
                .setResultTransformer(Transformers.aliasToBean(TtnylokasyonDTO.class))
                .setParameter("barcodeNumber", barcodeNumber)
                .list();

        return ttnylokasyonDTOs;
    }

}
