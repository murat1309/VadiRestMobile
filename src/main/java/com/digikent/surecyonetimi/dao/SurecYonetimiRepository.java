package com.digikent.surecyonetimi.dao;

import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.surecyonetimi.dto.*;
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

            /*Object o = list.get(0);
            Map map = (Map) o;
            String fsa = new String();
            fsa = clob2Str((Clob) map.get("KULLANICIACIKLAMA"));*/

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




}
