package com.digikent.surecyonetimi.dao;

import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.surecyonetimi.dto.*;
import com.digikent.surecyonetimi.service.SurecYonetimiService;
import oracle.sql.TIMESTAMP;
import org.hibernate.annotations.Nationalized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.*;
import org.springframework.stereotype.Repository;
import org.springframework.core.env.Environment;
import tr.com.ega.em;

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

            String sql = "SELECT TO_CHAR(INSTANCE_NAME) AS IZAHAT FROM " + schemaName + ".LSW_BPD_INSTANCE WHERE BPD_INSTANCE_ıd="+ surecSorguRequestDTO.getSorguNo();

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
    public SurecSorguResponseDTO getSurecCommentBySorguNo(SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO = new SurecSorguResponseDTO();
        SurecCommentDTO surecCommentDTO = new SurecCommentDTO();
        List<SurecCommentDTO> surecCommentDTOList = new ArrayList<>();

        ErrorDTO errorDTO = new ErrorDTO();

        try {
            String tableName = new String();
            String sql = "SELECT TABLENAME FROM vbpmdigikent where processinstanceid =" + surecSorguRequestDTO.getSorguNo();

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
            if(tableName.equalsIgnoreCase("ELI1RUHSATDOSYA")){
                sql = "select kullaniciaciklama from eli1ruhsatdosya where vbpmprocessinstance_id ='" + surecSorguRequestDTO.getSorguNo() + "'";
            }else{
                sql = "select kullaniciaciklama from vimrbasvuru where vbpmprocessinstance_id ='" + surecSorguRequestDTO.getSorguNo() + "'";
            }

            query = session.createSQLQuery(sql);
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

            String tableName = new String();
            String sql = "SELECT TABLENAME FROM vbpmdigikent where processinstanceid =" + surecSorguRequestDTO.getSorguNo();

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
            if(tableName.equalsIgnoreCase("VIMRBASVURU")){
                sql = "SELECT BT.TANIM AS BELGE_ADI, V.EBYSDOCUMENT_ID AS DOKUMAN_NUMARASI FROM VIMRBASVURUBELGE V " +
                        "JOIN TIMRBASVURUTURUBELGE BTB ON BTB.ID = V.TIMRBASVURUTURUBELGE_ID " +
                        "JOIN TIMRBELGETURU BT ON BT.ID = BTB.TIMRBELGETURU_ID " +
                        "WHERE V.VIMRBASVURU_ID = " +
                        "(SELECT ANAHTARALAN FROM VBPMDIGIKENT WHERE PROCESSINSTANCEID ='" + surecSorguRequestDTO.getSorguNo() + "')";
            }else{
                sql = "SELECT C.BELGEADI AS BELGE_ADI, H.EBYSDOCUMENT_ID AS DOKUMAN_NUMARASI FROM HLI1DOSYABELGELER H "
                        + "JOIN CLI1BELGETURU C ON C.ID = H.CLI1BELGETURU_ID "
                        + "WHERE ELI1RUHSATDOSYA_ID=(select ANAHTARALAN from vbpmdigikent where processinstanceid='" + surecSorguRequestDTO.getSorguNo() + "')";
            }

            query = session.createSQLQuery(sql);
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

    public List<ImarBasvuruTuruDTO> getBasvuruTuruList() {

        String sql = "SELECT ID, TANIM FROM TIMRBASVURUTURU WHERE ISACTIVE = 'E'";

        List list = new ArrayList<>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        List<ImarBasvuruTuruDTO> basvuruTuruDTOList = new ArrayList<>();
        for (Object o: list) {
            Map map = (Map)o;
            BigDecimal id = (BigDecimal)map.get("ID");
            String tanim = (String)map.get("TANIM");
            ImarBasvuruTuruDTO imarBasvuruTuruDTO =  new ImarBasvuruTuruDTO();

            if(id != null)
                imarBasvuruTuruDTO.setId(id.longValue());

            if(tanim != null)
                imarBasvuruTuruDTO.setTanim(tanim);

            basvuruTuruDTOList.add(imarBasvuruTuruDTO);
        }
        return basvuruTuruDTOList;
    }
    public String addConditionWordToSql(String sql){
        String conditionWord;
        if(sql.substring(sql.indexOf("MPI1PAYDAS.ID")+ 13, sql.length()).equalsIgnoreCase("")){
            conditionWord = " WHERE ";
        }else {
            conditionWord = " AND "; }
        return sql + conditionWord;
    }
    public List<ImarSurecDTO> getSurecList(ImarSurecRequestDTO imarSurecRequestDTO) {

        List<ImarSurecDTO> imarSurecDTOList = new ArrayList<>();

        String sql = "SELECT VBPMPROCESSINSTANCE_ID, (SELECT T.TANIM FROM TIMRBASVURUTURU T WHERE T.ID ="
                + " TIMRBASVURUTURU_ID AND T.ISACTIVE = 'E') TANIM, PAFTANO, ADANO, PARSELNO,"
                + " (SELECT I.ADISOYADI FROM IHR1PERSONEL I WHERE I.ID = IHR1PERSONEL_RAPORTOR) RAPORTOR, "
                + " MPI1PAYDAS_ID, (SELECT TANIM FROM TIMRBASVURUDURUMU WHERE TIMRBASVURUDURUMU.ID = TIMRBASVURUDURUMU_ID) BASVURUDURUMU, "
                + " MPI1PAYDAS.ADI, MPI1PAYDAS.SOYADI, MPI1PAYDAS.TCKIMLIKNO FROM VIMRBASVURU "
                + " JOIN MPI1PAYDAS ON VIMRBASVURU.MPI1PAYDAS_ID = MPI1PAYDAS.ID";

        if(imarSurecRequestDTO.getPaftaNo() != null && !imarSurecRequestDTO.getPaftaNo().equalsIgnoreCase(""))
            sql = addConditionWordToSql(sql) + "PAFTANO = '" + imarSurecRequestDTO.getPaftaNo() + "'";
        if(imarSurecRequestDTO.getParselNo() != null && !imarSurecRequestDTO.getParselNo().equalsIgnoreCase(""))
            sql = addConditionWordToSql(sql) + "PARSELNO = '" + imarSurecRequestDTO.getParselNo() + "'";
        if(imarSurecRequestDTO.getAdaNo() != null && !imarSurecRequestDTO.getAdaNo().equalsIgnoreCase(""))
            sql = addConditionWordToSql(sql) + "ADANO = '" + imarSurecRequestDTO.getAdaNo() + "'";
        if(imarSurecRequestDTO.getPaydasNo() != null)
            sql = addConditionWordToSql(sql) + "MPI1PAYDAS_ID = " + imarSurecRequestDTO.getPaydasNo();
        if(imarSurecRequestDTO.getTcNo() != null)
            sql = addConditionWordToSql(sql) + "TCKIMLIKNO = " + imarSurecRequestDTO.getTcNo();

        List list = new ArrayList<>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query =session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        for(Object o : list) {
            Map map = (Map) o;
            String adaNo = (String) map.get("ADANO");
            String paftaNo = (String) map.get("PAFTANO");
            String parselNo = (String) map.get("PARSELNO");
            String surecNo = (String) map.get("VBPMPROCESSINSTANCE_ID");
            String basvuruDurumu = (String) map.get("BASVURUDURUMU");
            String paydasAdı = (String) map.get("ADI");
            String paydasSoyadı = (String) map.get("SOYADI");
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
                imarSurecDTO.setSurecNo(surecNo);
            if (basvuruDurumu != null)
                imarSurecDTO.setBasvuruDurumu(basvuruDurumu);
            if (paydasAdı != null)
                imarSurecDTO.setPaydasAdı(paydasAdı);
            if (paydasSoyadı != null)
                imarSurecDTO.setPaydasSoyadı(paydasSoyadı);
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
}
