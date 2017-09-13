package com.digikent.mesajlasma.dao;

import com.digikent.mesajlasma.dto.MessageDTO;
import com.digikent.mesajlasma.dto.MessageUserDTO;
import com.digikent.mesajlasma.entity.TeilMesajIletimGrubu;
import com.digikent.mesajlasma.entity.VeilMesaj;
import com.digikent.vadirest.dto.Message;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Kadir on 11/09/17.
 */
@Repository
public class MesajlasmaRepository {

    private final Logger LOG = LoggerFactory.getLogger(MesajlasmaRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public void savePersonalMessage(MessageDTO messageDTO) {

        Date date = new Date();
        VeilMesaj veilMesaj = convertMessageDTOToVeilMesaj(messageDTO);
        veilMesaj.setGonderimZamani(date);
        veilMesaj.setOkunmaZamani(null);

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(veilMesaj);
            tx.commit();
            LOG.debug("Message saved. Time = " + veilMesaj.getGonderimZamani());
        }catch(Exception e){
            LOG.debug("while save message to database, An error occured. ");
            if(tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
    }

    public List<MessageUserDTO> getUserList(String userName) {

        List<MessageUserDTO> messageUserDTOList = new ArrayList<MessageUserDTO>();

        String sql = "SELECT A.USERID, A.FIRSTNAME, A.LASTNAME, A.ACTIVEDIRECTORYUSERNAME, A.IKY_PERSONEL_ID FROM FSM1USERS A,IHR1PERSONEL B" +
        " WHERE A.IKY_PERSONEL_ID = B.ID" +
        " AND B.PERSONELDURUMU='CALISAN'" +
        " AND A.ACTIVE = 'E'" +
        " AND A.IKY_PERSONEL_ID>0" +
        " AND A.USERID!='" + userName + "'";

        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        list = query.list();

        for(Object o : list){
            Map map = (Map) o;
            BigDecimal personelId = (BigDecimal) map.get("IKY_PERSONEL_ID");
            String firstName = (String) map.get("FIRSTNAME");
            String lastName = (String) map.get("LASTNAME");
            String ldapName = (String) map.get("ACTIVEDIRECTORYUSERNAME");
            String userId = (String) map.get("USERID");

            MessageUserDTO userDTO = new MessageUserDTO();

            if(personelId != null)
                userDTO.setPersonelId(personelId.longValue());

            if(firstName != null)
                userDTO.setFirstName(firstName);
            if(lastName != null)
                userDTO.setLastName(lastName);
            if(ldapName != null)
                userDTO.setActiveDirectoryUserName(ldapName);
            if(userId != null)
                userDTO.setUserId(userId);

            messageUserDTOList.add(userDTO);
        }

        return messageUserDTOList;
    }

    public void saveGroupMessage(MessageDTO messageDTO) {

        //TeilMesajIletimGrubu teilMesajIletimGrubu =  (TeilMesajIletimGrubu) session.get(TeilMesajIletimGrubu.class, messageDTO.getGroupId());
        //veilMesaj.setTeilMesajIletimGrubu(teilMesajIletimGrubu);

    }

    public VeilMesaj convertMessageDTOToVeilMesaj(MessageDTO messageDTO) {
        VeilMesaj veilMesaj = new VeilMesaj();
        veilMesaj.setGönderimTuru(messageDTO.getGonderimTuru());
        veilMesaj.setIhr1PersonelIletilenId(messageDTO.getIhr1PersonelIletilenId());
        veilMesaj.setIhr1PersonelYazanId(messageDTO.getIhr1PersonelYazanId());
        veilMesaj.setMesaj(messageDTO.getMesaj());

        return veilMesaj;
    }


}

