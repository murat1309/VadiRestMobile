package com.digikent.surecyonetimi.surecsorgulama.dao;

import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.basvurudetay.*;
import com.digikent.surecyonetimi.surecsorgulama.dto.imarsurec.BasvuruTuruDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.imarsurec.BasvuruTuruRequestDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.imarsurec.ImarRequestDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.imarsurec.ImarSurecDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.yapidenetimsurec.YapiDenetimDTO;
import com.digikent.surecyonetimi.surecsorgulama.dto.yapidenetimsurec.YapiDenetimRequestDTO;
import org.hibernate.annotations.Nationalized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.*;
import org.springframework.stereotype.Repository;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;

/**
 * Created by Medet on 12/28/2017.
 */

@Repository
public class SurecYonetimiRepository {

    private final Logger LOG = LoggerFactory.getLogger(SurecYonetimiRepository.class);

    @Inject
    private Environment env;

    @Autowired
    SessionFactory sessionFactory;

    @Nationalized
    public SurecSorguResponseDTO getSurecSorguListesiBySorguNo(SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO = new SurecSorguResponseDTO();
        List<SurecSorguDTO> surecSorguDTOList = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO();

        try {
            String schemaName = env.getProperty("bpmSchema");

            String sql = "SELECT TO_CHAR(A.SUBJECT) as SUBJECT, TO_CHAR(A.CLOSE_DATETIME, 'YYYY-MM-DD hh24:mi:ss') as date2, TO_CHAR(B.FULL_NAME) as FULL_NAME " +
                         " FROM " + schemaName + ".LSW_TASK A, " + schemaName + ".LSW_USR_XREF B, " +  schemaName + ".LSW_BPD_INSTANCE C " +
                         " WHERE A.USER_ID = B.USER_ID " +
                         " AND A.USER_ID != 9 " +
                         " AND A.USER_ID>0 " +
                         " AND A.BPD_INSTANCE_ID = C.BPD_INSTANCE_ID " +
                         " AND C.BPD_INSTANCE_ID = '" + surecSorguRequestDTO.getSorguNo() + "' " +
                         " union " +
                         " SELECT TO_CHAR(A.SUBJECT) as SUBJECT, " +
                         " TO_CHAR(A.CLOSE_DATETIME, 'YYYY-MM-DD hh24:mi:ss') as date2, " +
                         " TO_CHAR(E.DISPLAY_NAME) as DISPLAY_NAME " +
                         " FROM " + schemaName + ".LSW_TASK A, " + schemaName + ".LSW_BPD_INSTANCE C, " + schemaName + ".LSW_USR_GRP_XREF E " +
                         " WHERE " +
                         " A.GROUP_ID=E.GROUP_ID " +
                         " AND A.BPD_INSTANCE_ID = C.BPD_INSTANCE_ID " +
                         " AND E.GROUP_ID>0 " +
                         " AND C.BPD_INSTANCE_ID = '" + surecSorguRequestDTO.getSorguNo() + "' " +
                         " order by date2 asc ";

            List list = new ArrayList<>();
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();
            for(Object o : list) {
                Map map = (Map) o;

                String surecAdimi = (String) map.get("SUBJECT");
                String bitisTarihi = (String) map.get("DATE2");
                String personelIsmi = (String) map.get("FULL_NAME");

                SurecSorguDTO surecSorguDTO = new SurecSorguDTO();

                if(surecAdimi != null)
                    surecSorguDTO.setSurecAdimi(surecAdimi);
                if(personelIsmi != null)
                    surecSorguDTO.setPersonelIsmi(personelIsmi);
                if(bitisTarihi != null){
                    surecSorguDTO.setBitisTarihi(bitisTarihi);
                }

                surecSorguDTOList.add(surecSorguDTO);
            }

            errorDTO.setErrorMessage(null);
            errorDTO.setError(false);

            surecSorguResponseDTO.setSurecSorguDTOList(surecSorguDTOList);
            surecSorguResponseDTO.setErrorDTO(errorDTO);

        } catch (Exception e) {

            errorDTO.setErrorMessage("Bir hata meydana geldi.");
            errorDTO.setError(true);

            surecSorguResponseDTO.setSurecSorguDTOList(null);
            surecSorguResponseDTO.setErrorDTO(errorDTO);

        }
        return surecSorguResponseDTO;
    }

    @Nationalized
    public SurecSorguResponseDTO getSurecInfoBySorguNo(SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO = new SurecSorguResponseDTO();
        SurecInfoDTO surecInfoDTO = new SurecInfoDTO();
        ErrorDTO errorDTO = new ErrorDTO();

        try {
            String schemaName = env.getProperty("bpmSchema");

            String sql = "SELECT TO_CHAR(INSTANCE_NAME) AS IZAHAT FROM " + schemaName + ".LSW_BPD_INSTANCE WHERE BPD_INSTANCE_ID="+ surecSorguRequestDTO.getSorguNo();

            List list = new ArrayList<>();
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            for(Object o : list) {
                Map map = (Map) o;

                String surecInfo = (String) map.get("IZAHAT");

                if(surecInfo != null)
                    surecInfoDTO.setSurecInfo(surecInfo);
            }
            errorDTO.setErrorMessage(null);
            errorDTO.setError(false);
            surecSorguResponseDTO.setErrorDTO(errorDTO);
            surecSorguResponseDTO.setSurecInfoDTO(surecInfoDTO);

        } catch (Exception e) {

            errorDTO.setErrorMessage("Bir hata meydana geldi.");
            errorDTO.setError(true);
            surecSorguResponseDTO.setErrorDTO(errorDTO);
        }
        return surecSorguResponseDTO;
    }
    public String returnTableName(String sorguNo){
        String tableName = "";
        String sql = "SELECT TABLENAME FROM VBPMDIGIKENT WHERE PROCESSINSTANCEID =" + sorguNo;

        List list = new ArrayList<>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        for(Object o : list) {
            Map map = (Map) o;
            tableName = (String) map.get("TABLENAME");
            if(tableName != null)
                break;
        }
        return tableName;
    }
    public SurecSorguResponseDTO getSurecCommentBySorguNo(SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO = new SurecSorguResponseDTO();
        SurecCommentDTO surecCommentDTO = new SurecCommentDTO();
        List<SurecCommentDTO> surecCommentDTOList = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO();

        try {
            String sql = "";
            List list = new ArrayList<>();
            if(surecSorguRequestDTO.getTabName().equalsIgnoreCase("imar")) {
                String tableName = returnTableName(surecSorguRequestDTO.getSorguNo());
                if (tableName.equalsIgnoreCase("ELI1RUHSATDOSYA")) {
                    sql = "SELECT KULLANICIACIKLAMA FROM ELI1RUHSATDOSYA WHERE VBPMPROCESSINSTANCE_ID ='" + surecSorguRequestDTO.getSorguNo() + "'";
                } else {
                    sql = "SELECT KULLANICIACIKLAMA FROM VIMRBASVURU WHERE VBPMPROCESSINSTANCE_ID ='" + surecSorguRequestDTO.getSorguNo() + "'";
                }
            }else if(surecSorguRequestDTO.getTabName().equalsIgnoreCase("yapidenetim")){
                sql = "SELECT KULLANICIACIKLAMA FROM VYDEBASVURU WHERE VBPMPROCESSINSTANCE_ID = '" + surecSorguRequestDTO.getSorguNo() + "'";
            }
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            for(Object o : list) {
                Map map = (Map) o;
                String kullaniciYorum = clob2Str((Clob) map.get("KULLANICIACIKLAMA"));

                if(kullaniciYorum != null && !kullaniciYorum.trim().equals(""))
                    surecCommentDTO.setKullaniciYorum(kullaniciYorum);
            }
            errorDTO.setErrorMessage(null);
            errorDTO.setError(false);
            surecSorguResponseDTO.setErrorDTO(errorDTO);
            surecSorguResponseDTO.setSurecCommentDTO(surecCommentDTO);
        } catch (Exception e) {
            errorDTO.setErrorMessage("Bir hata meydana geldi.");
            errorDTO.setError(true);
            surecSorguResponseDTO.setErrorDTO(errorDTO);
        }
        return surecSorguResponseDTO;
    }
    private String clob2Str(Clob clob) {
        try {
            if (clob == null)
                return "";
            else if (((int) clob.length()) > 0) {
                return clob.getSubString(1, (int) clob.length());
            }
        } catch (SQLException e) {
        }
        return "";
    }

    public SurecSorguResponseDTO getSurecDocumentBySorguNo(SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO = new SurecSorguResponseDTO();
        List<SurecDocumentDTO> surecDocumentDTOList = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO();

        try {
            String sql = "";
            List list = new ArrayList<>();
            if(surecSorguRequestDTO.getTabName().equalsIgnoreCase("imar")) {
                String tableName = returnTableName(surecSorguRequestDTO.getSorguNo());
                if (tableName.equalsIgnoreCase("VIMRBASVURU")) {
                    sql = "SELECT BT.TANIM AS BELGE_ADI, V.EBYSDOCUMENT_ID AS DOKUMAN_NUMARASI FROM VIMRBASVURUBELGE V " +
                            "JOIN TIMRBASVURUTURUBELGE BTB ON BTB.ID = V.TIMRBASVURUTURUBELGE_ID " +
                            "JOIN TIMRBELGETURU BT ON BT.ID = BTB.TIMRBELGETURU_ID " +
                            "WHERE V.VIMRBASVURU_ID = " +
                            "(SELECT ANAHTARALAN FROM VBPMDIGIKENT WHERE PROCESSINSTANCEID ='" + surecSorguRequestDTO.getSorguNo() + "')";
                } else {
                    sql = "SELECT C.BELGEADI AS BELGE_ADI, H.EBYSDOCUMENT_ID AS DOKUMAN_NUMARASI FROM HLI1DOSYABELGELER H "
                            + "JOIN CLI1BELGETURU C ON C.ID = H.CLI1BELGETURU_ID "
                            + "WHERE ELI1RUHSATDOSYA_ID=(SELECT ANAHTARALAN FROM VBPMDIGIKENT WHERE PROCESSINSTANCEID='" + surecSorguRequestDTO.getSorguNo() + "')";
                }
            }else if(surecSorguRequestDTO.getTabName().equalsIgnoreCase("yapidenetim")){
                sql = "SELECT TYDEBELGETURU.TANIM AS BELGE_ADI, VYDEBASVURUBELGE.EBYSDOCUMENT_ID AS DOKUMAN_NUMARASI " +
                        "FROM VYDEBASVURUBELGE, TYDEBELGETURU, VYDEBASVURU WHERE VYDEBASVURUBELGE.TYDEBELGETURU_ID = TYDEBELGETURU.ID " +
                        "AND VYDEBASVURUBELGE.VYDEBASVURU_ID = VYDEBASVURU.ID " +
                        "AND VYDEBASVURU.VBPMPROCESSINSTANCE_ID='"+ surecSorguRequestDTO.getSorguNo() + "'";
            }
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            for(Object o : list) {
                Map map = (Map) o;

                String belgeAdi = (String) map.get("BELGE_ADI");
                BigDecimal documentId = (BigDecimal) map.get("DOKUMAN_NUMARASI");
                SurecDocumentDTO surecDocumentDTO = new SurecDocumentDTO();

                if(belgeAdi != null)
                    surecDocumentDTO.setBelgeAdi(belgeAdi);
                if(documentId != null)
                    surecDocumentDTO.setDocumentId(documentId.longValue());

                surecDocumentDTOList.add(surecDocumentDTO);
            }
            errorDTO.setErrorMessage(null);
            errorDTO.setError(false);

            surecSorguResponseDTO.setSurecDocumentDTOList(surecDocumentDTOList);
            surecSorguResponseDTO.setErrorDTO(errorDTO);

        }catch (Exception e){

            errorDTO.setErrorMessage("Bir hata meydana geldi.");
            errorDTO.setError(true);
            surecSorguResponseDTO.setErrorDTO(errorDTO);
        }
        return surecSorguResponseDTO;
    }

    public List<BasvuruTuruDTO> getBasvuruTuruList(String tabName) {

        String sql = "";
        if(tabName.equals("imar")){
            sql = "SELECT ID, TANIM FROM TIMRBASVURUTURU WHERE ISACTIVE = 'E'";
        }else if(tabName.equals("yapidenetim")){
            sql = "SELECT ID, TANIM FROM TYDESEVIYETURU WHERE ISACTIVE = 'E'";
        }
        List list = new ArrayList<>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query =session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        List<BasvuruTuruDTO> basvuruTuruDTOList = new ArrayList<>();
        for (Object o: list) {
            Map map = (Map)o;
            BigDecimal id = (BigDecimal)map.get("ID");
            String tanim = (String)map.get("TANIM");
            BasvuruTuruDTO basvuruTuruDTO =  new BasvuruTuruDTO();

            if(id != null)
                basvuruTuruDTO.setId(id.longValue());

            if(tanim != null)
                basvuruTuruDTO.setTanim(tanim);

            basvuruTuruDTOList.add(basvuruTuruDTO);
        }
        return basvuruTuruDTOList;
    }
    public List<ImarSurecDTO> getImarSurecList(ImarRequestDTO imarRequestDTO) {

        List<ImarSurecDTO> imarSurecDTOList = new ArrayList<>();

        String sql = "SELECT T.TANIM, T.ISACTIVE, PAFTANO, ADANO, PARSELNO, VBPMPROCESSINSTANCE_ID, "
                + "VBPMDIGIKENT.PROCESSINSTANCEID, "
                + "(SELECT I.ADISOYADI FROM IHR1PERSONEL I WHERE I.ID = IHR1PERSONEL_RAPORTOR) RAPORTOR, "
                + "MPI1PAYDAS_ID, (SELECT TANIM FROM TIMRBASVURUDURUMU WHERE TIMRBASVURUDURUMU.ID = TIMRBASVURUDURUMU_ID) BASVURUDURUMU, "
                + "MPI1PAYDAS.ADI || ' ' || MPI1PAYDAS.SOYADI AS PAYDASADSOYAD, MPI1PAYDAS.TCKIMLIKNO FROM VIMRBASVURU "
                + "JOIN MPI1PAYDAS ON VIMRBASVURU.MPI1PAYDAS_ID = MPI1PAYDAS.ID "
                + "JOIN TIMRBASVURUTURU T ON T.ID = VIMRBASVURU.TIMRBASVURUTURU_ID "
                + "JOIN VBPMDIGIKENT ON VBPMDIGIKENT.ANAHTARALAN = VIMRBASVURU.ID "
                + "WHERE T.ISACTIVE = 'E' AND ABS(VBPMPROCESSINSTANCE_ID)>0";

        if(imarRequestDTO.getImarSurecRequestDTO().getPaftaNo() != null && !imarRequestDTO.getImarSurecRequestDTO().getPaftaNo().equalsIgnoreCase(""))
            sql = sql + " AND PAFTANO = '" + imarRequestDTO.getImarSurecRequestDTO().getPaftaNo() + "'";
        if(imarRequestDTO.getImarSurecRequestDTO().getParselNo() != null && !imarRequestDTO.getImarSurecRequestDTO().getParselNo().equalsIgnoreCase(""))
            sql = sql + " AND PARSELNO = '" + imarRequestDTO.getImarSurecRequestDTO().getParselNo() + "'";
        if(imarRequestDTO.getImarSurecRequestDTO().getAdaNo() != null && !imarRequestDTO.getImarSurecRequestDTO().getAdaNo().equalsIgnoreCase(""))
            sql = sql + " AND ADANO = '" + imarRequestDTO.getImarSurecRequestDTO().getAdaNo() + "'";
        if(imarRequestDTO.getImarSurecRequestDTO().getSurecNo() != null)
            sql = sql + " AND VIMRBASVURU.ID = (SELECT ANAHTARALAN FROM VBPMDIGIKENT WHERE TABLENAME = 'VIMRBASVURU' AND PROCESSINSTANCEID = " + imarRequestDTO.getImarSurecRequestDTO().getSurecNo() + ")";
        if(imarRequestDTO.getImarSurecRequestDTO().getPaydasNo() != null)
            sql = sql + " AND MPI1PAYDAS_ID = " + imarRequestDTO.getImarSurecRequestDTO().getPaydasNo();
        if(imarRequestDTO.getImarSurecRequestDTO().getTcNo() != null)
            sql = sql + " AND MPI1PAYDAS.TCKIMLIKNO = " + imarRequestDTO.getImarSurecRequestDTO().getTcNo();

        List<Long> basvuruturulist = new ArrayList<>();
        for (BasvuruTuruRequestDTO item : imarRequestDTO.getImarBasvuruTuruRequestDTOList()) {
            basvuruturulist.add(item.getValue());
        }
        sql = sql + " AND TIMRBASVURUTURU_ID IN (:basvuruturulist) ORDER BY VBPMDIGIKENT.PROCESSINSTANCEID ASC";
        List list = new ArrayList<>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query =session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        query.setParameterList("basvuruturulist", basvuruturulist);
        list = query.list();

        for(Object o : list) {
            Map map = (Map) o;
            String adaNo = (String) map.get("ADANO");
            String paftaNo = (String) map.get("PAFTANO");
            String parselNo = (String) map.get("PARSELNO");
            BigDecimal surecNo = (BigDecimal) map.get("PROCESSINSTANCEID");
            String basvuruDurumu = (String) map.get("BASVURUDURUMU");
            String paydasAdSoyad = (String) map.get("PAYDASADSOYAD");
            String basvuruTuru = (String) map.get("TANIM");
            String raportor = (String) map.get("RAPORTOR");
            BigDecimal tcNo = (BigDecimal) map.get("TCKIMLIKNO");
            BigDecimal paydasNo = (BigDecimal) map.get("MPI1PAYDAS_ID");

            ImarSurecDTO imarSurecDTO = new ImarSurecDTO();

            if (adaNo != null)
                imarSurecDTO.setAdaNo(adaNo);
            if (paftaNo != null)
                imarSurecDTO.setPaftaNo(paftaNo);
            if (parselNo != null)
                imarSurecDTO.setParselNo(parselNo);
            if (surecNo != null)
                imarSurecDTO.setSurecNo(surecNo.longValue());
            if (basvuruDurumu != null)
                imarSurecDTO.setBasvuruDurumu(basvuruDurumu);
            if (paydasAdSoyad != null)
                imarSurecDTO.setPaydasAdSoyad(paydasAdSoyad);
            if (basvuruTuru != null)
                imarSurecDTO.setBasvuruTuru(basvuruTuru);
            if(raportor != null)
                imarSurecDTO.setRaportor(raportor);
            if (tcNo != null)
                imarSurecDTO.setTcNo(tcNo.longValue());
            if (paydasNo != null)
                imarSurecDTO.setPaydasNo(paydasNo.longValue());

            imarSurecDTOList.add(imarSurecDTO);
        }
        return imarSurecDTOList;
    }

    public List<YapiDenetimDTO> getDenetimSurecList(YapiDenetimRequestDTO yapiDenetimRequestDTO) {

        List<YapiDenetimDTO> yapiDenetimDTOList = new ArrayList<>();
        String sql = "SELECT VYDEBASVURU.VBPMPROCESSINSTANCE_ID, VYDEBASVURU.MPI1PAYDAS_ID, TYDESEVIYETURU.TANIM, " +
                "(SELECT TANIM FROM DRE1MAHALLE WHERE DRE1MAHALLE.ID = VIMRYAPIRUHSATI.DRE1MAHALLE_ID) AS MAHALLEADI, " +
                "(SELECT ADI || ' ' || SOYADI from MPI1PAYDAS WHERE MPI1PAYDAS.ID = VYDEBASVURU.MPI1PAYDAS_ID) AS PAYDASADSOYAD, " +
                "(SELECT ADI || ' ' || SOYADI from MPI1PAYDAS WHERE MPI1PAYDAS.ID = VYDEBASVURU.MPI1PAYDAS_VEKIL) AS VEKILADSOYAD, " +
                "(SELECT ADI || ' ' || SOYADI from IHR1PERSONEL WHERE IHR1PERSONEL.ID = VYDEBASVURU.IHR1PERSONEL_RAPORTOR) AS RAPORTOR, " +
                "(SELECT TANIM FROM TYDEBASVURUDURUMU WHERE TYDEBASVURUDURUMU.ID = VYDEBASVURU.TYDEBASVURUDURUMU_ID) AS BASVURUDURUMU, " +
                "VIMRYAPIRUHSATI.ADANO, " +
                "VIMRYAPIRUHSATI.PAFTANO, " +
                "VIMRYAPIRUHSATI.PARSELNO, " +
                "VIMRYAPIRUHSATI.YIBFREF, " +
                "MPI1PAYDAS.TCKIMLIKNO FROM VYDEBASVURU, VIMRYAPIRUHSATI, TYDESEVIYETURU, MPI1PAYDAS WHERE " +
                "VYDEBASVURU.VIMRYAPIRUHSATI_ID = VIMRYAPIRUHSATI.ID AND " +
                "VYDEBASVURU.TYDESEVIYETURU_ID = TYDESEVIYETURU.ID AND " +
                "VYDEBASVURU.MPI1PAYDAS_ID = MPI1PAYDAS.ID";

        if(yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getPaftaNo() != null && !yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getPaftaNo().equalsIgnoreCase(""))
            sql = sql + " AND PAFTANO = '" + yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getPaftaNo() + "'";
        if(yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getParselNo() != null && !yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getParselNo().equalsIgnoreCase(""))
            sql = sql + " AND PARSELNO = '" + yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getParselNo() + "'";
        if(yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getAdaNo() != null && !yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getAdaNo().equalsIgnoreCase(""))
            sql = sql + " AND ADANO = '" + yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getAdaNo() + "'";
        if(yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getYibf() != null && !yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getYibf().equalsIgnoreCase(""))
            sql = sql + " AND YIBFREF = '" + yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getYibf() + "'";
        if(yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getSurecNo() != null && !yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getSurecNo().equalsIgnoreCase(""))
            sql = sql + " AND VBPMPROCESSINSTANCE_ID = '" + yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getSurecNo() + "'";
        if(yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getPaydasNo() != null)
            sql = sql + " AND MPI1PAYDAS_ID = " + yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getPaydasNo();
        if(yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getTcNo() != null)
            sql = sql + " AND TCKIMLIKNO = " + yapiDenetimRequestDTO.getDenetimParametersRequestDTO().getTcNo();

        List<Long> basvuruturulist = new ArrayList<>();
        for (BasvuruTuruRequestDTO item : yapiDenetimRequestDTO.getYapiDenetimBasvuruTuruRequestDTOList()) {
            basvuruturulist.add(item.getValue());
        }
        sql = sql + " AND VYDEBASVURU.TYDESEVIYETURU_ID IN (:basvuruturulist)";

        List list = new ArrayList<>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query =session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        query.setParameterList("basvuruturulist", basvuruturulist);
        list = query.list();

        for(Object o : list) {
            Map map = (Map) o;
            String adaNo = (String) map.get("ADANO");
            String paftaNo = (String) map.get("PAFTANO");
            String parselNo = (String) map.get("PARSELNO");
            String mahalleAdı = (String) map.get("MAHALLEADI");
            String yibf = (String) map.get("YIBFREF");
            String surecNo = (String) map.get("VBPMPROCESSINSTANCE_ID");
            String basvuruDurumu = (String) map.get("BASVURUDURUMU");
            String paydasAdSoyad = (String) map.get("PAYDASADSOYAD");
            String vekilAdSoyad = (String) map.get("VEKILADSOYAD");
            String basvuruTuru = (String) map.get("TANIM");
            String raportor = (String) map.get("RAPORTOR");
            BigDecimal paydasNo = (BigDecimal) map.get("MPI1PAYDAS_ID");

            YapiDenetimDTO yapiDenetimDTO = new YapiDenetimDTO();

            if (adaNo != null)
                yapiDenetimDTO.setAdaNo(adaNo);
            if (paftaNo != null)
                yapiDenetimDTO.setPaftaNo(paftaNo);
            if (parselNo != null)
                yapiDenetimDTO.setParselNo(parselNo);
            if(mahalleAdı != null)
                yapiDenetimDTO.setMahalleAdı(mahalleAdı);
            if(yibf != null)
                yapiDenetimDTO.setYibf(yibf);
            if (surecNo != null)
                yapiDenetimDTO.setSurecNo(surecNo);
            if (basvuruDurumu != null)
                yapiDenetimDTO.setBasvuruDurumu(basvuruDurumu);
            if (paydasAdSoyad != null)
                yapiDenetimDTO.setPaydasAdSoyad(paydasAdSoyad);
            if(vekilAdSoyad != null)
                yapiDenetimDTO.setVekilAdSoyad(vekilAdSoyad);
            if (basvuruTuru != null)
                yapiDenetimDTO.setBasvuruTuru(basvuruTuru);
            if(raportor != null)
                yapiDenetimDTO.setRaportor(raportor);
            if (paydasNo != null)
                yapiDenetimDTO.setPaydasNo(paydasNo.longValue());

            yapiDenetimDTOList.add(yapiDenetimDTO);
        }
        return yapiDenetimDTOList;

    }
}
