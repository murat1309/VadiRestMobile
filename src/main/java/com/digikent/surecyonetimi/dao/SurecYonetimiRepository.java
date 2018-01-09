package com.digikent.surecyonetimi.dao;

import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.surecyonetimi.dto.SurecSorguDTO;
import com.digikent.surecyonetimi.dto.SurecSorguRequestDTO;
import com.digikent.surecyonetimi.dto.SurecSorguResponseDTO;
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
                         " order by A.CLOSE_DATETIME asc ";



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

}
