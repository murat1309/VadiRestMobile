package com.digikent.mesajlasma.dao;

import com.digikent.config.Constants;
import com.digikent.crypt.UtilCrypto;
import com.digikent.mesajlasma.dto.*;
import com.digikent.mesajlasma.entity.TeilMesajIletimGrubu;
import com.digikent.mesajlasma.entity.TeilMesajİletimGrubuLine;
import com.digikent.mesajlasma.entity.VeilMesaj;
import com.digikent.security.SecurityUtils;
import net.sf.saxon.trans.Err;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kadir on 11/09/17.
 */
@Repository
public class MesajlasmaRepository {

    private final Logger LOG = LoggerFactory.getLogger(MesajlasmaRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    UtilCrypto utilCrypto;

    public Boolean savePersonalMessage(MessageDTO messageDTO) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            VeilMesaj veilMesaj = convertMessageDTOToVeilMesaj(messageDTO);
            if (messageDTO.getGroupId() != null) {
                veilMesaj.setTeilMesajIletimGrubu(getMesajIletimGrubuById(session, messageDTO.getGroupId()));
            }

            tx = session.beginTransaction();
            session.save(veilMesaj);
            tx.commit();
            LOG.debug("Message saved. Time = " + veilMesaj.getGonderimZamani());
            LOG.debug("procedure will Call ");
            callProcedure(session, veilMesaj);
        }catch(Exception e){
            LOG.error("while save message to database, An error occured. ");
            if(tx != null){
                tx.rollback();
            }
            session.close();
            return false;
        }

        return true;
    }

    public List<MessageUserDTO> getUserList(String userName) {

        List<MessageUserDTO> messageUserDTOList = new ArrayList<>();

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
                userDTO.setIletilenPersonelId(personelId.longValue());

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

    public Long createGroup(GroupRequest groupRequest) {

        Date date = getcurrentDate();
        List<TeilMesajİletimGrubuLine> teilMesajİletimGrubuLineList = new ArrayList<>();
        TeilMesajIletimGrubu group = new TeilMesajIletimGrubu();

        for (MessageUserDTO user: groupRequest.getMessageUserDTOList()) {
            TeilMesajİletimGrubuLine groupLine = new TeilMesajİletimGrubuLine();
            groupLine.setIhr1PersonelId(user.getIletilenPersonelId());
            groupLine.setAsm2ServisTuruId(null);
            groupLine.setBsm2ServisTuruId(null);
            groupLine.setLhr1GorevTuruId(null);
            groupLine.setIsActive(true);
            groupLine.setCrUser(user.getIletilenPersonelId());
            groupLine.setDeleteFlag("H");

            teilMesajİletimGrubuLineList.add(groupLine);
        }

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            group.setIsActive(true);
            group.setGrupTuru(groupRequest.getGroupInformationDTO().getGrupTuru());
            group.setIhr1PersonelOlusturanId(groupRequest.getGroupInformationDTO().getOlusturanPersonel());
            group.setTanim(groupRequest.getGroupInformationDTO().getGrupAdi());
            group.setTeilMesajİletimGrubuLineList(teilMesajİletimGrubuLineList);
            group.setCrUser(groupRequest.getGroupInformationDTO().getOlusturanPersonel());
            group.setDeleteFlag("H");

            tx = session.beginTransaction();
            session.save(group);
            tx.commit();

            LOG.debug("Group created id of group = " + group.getID() + " on Time = " + date);
            groupRequest.getGroupInformationDTO().setGroupId(group.getID());
            sendDefaultGroupMessage(groupRequest, group);
            sendDefaultGroupMessageWhenUserAdded(groupRequest);
        }catch(Exception e){
            LOG.error("while create group to database, An error occured. ");
            if(tx != null){
                tx.rollback();
            }
            return 0l;
        }
        session.close();

        return group.getID();
    }

    public List<InboxMessageDTO> getMessageLineList(Long personelId) {
        List<InboxMessageDTO> inboxMessageDTOList = new ArrayList<>();

        String sql = "WITH  SONILETILER AS ( " +
                "    SELECT MAX(A.ID) VEILMESAJ_ID  " +
                "      FROM VEILMESAJ A,VEILMESAJLINE B " +
                "     WHERE B.VEILMESAJ_ID = A.ID " +
                "        AND (B.IHR1PERSONEL_ILETILEN = "+ personelId +" OR A.IHR1PERSONEL_YAZAN = "+ personelId +") " +
                "    GROUP BY DECODE(NVL(TEILMESAJILETIMGRUBU_ILETILEN,0),0,  DECODE(A.IHR1PERSONEL_YAZAN,"+ personelId +", A.IHR1PERSONEL_YAZAN||B.IHR1PERSONEL_ILETILEN,B.IHR1PERSONEL_ILETILEN||A.IHR1PERSONEL_YAZAN) ,TEILMESAJILETIMGRUBU_ILETILEN) " +
                "    ORDER BY DECODE(NVL(TEILMESAJILETIMGRUBU_ILETILEN,0),0,  DECODE(A.IHR1PERSONEL_YAZAN,"+ personelId +", A.IHR1PERSONEL_YAZAN||B.IHR1PERSONEL_ILETILEN,B.IHR1PERSONEL_ILETILEN||A.IHR1PERSONEL_YAZAN) ,TEILMESAJILETIMGRUBU_ILETILEN) " +
                ") " +
                "SELECT  SONILETILER.VEILMESAJ_ID, VEILMESAJ.MESAJ , VEILMESAJ.GONDERIMZAMANI, IHR1PERSONEL.ID, VEILMESAJ.IHR1PERSONEL_YAZAN " +
                ",DECODE( NVL(TEILMESAJILETIMGRUBU_ILETILEN,0) ,0,IHR1PERSONEL.ADI||' '||IHR1PERSONEL.SOYADI " +
                ",(SELECT TANIM FROM TEILMESAJILETIMGRUBU WHERE ID= TEILMESAJILETIMGRUBU_ILETILEN)) AS ILETILEN " +
                ",(SELECT ADI||' '||SOYADI FROM IHR1PERSONEL X WHERE X.ID = VEILMESAJ.IHR1PERSONEL_YAZAN) AS YAZAN" +
                ",(SELECT OKUNMAZAMANI FROM VEILMESAJLINE WHERE VEILMESAJLINE.ID =  (SELECT MAX(BB.ID) FROM VEILMESAJLINE BB where  BB.VEILMESAJ_ID =  SONILETILER.VEILMESAJ_ID AND  BB.IHR1PERSONEL_ILETILEN = "+ personelId +"  )) OKUNMAZAMANI " +
                ",( CASE NVL(TEILMESAJILETIMGRUBU_ILETILEN,0) WHEN 0 THEN " +
                "    0 ELSE " +
                "  (SELECT COUNT(1) FROM VEILMESAJ XX , VEILMESAJLINE YY WHERE YY.VEILMESAJ_ID = XX.ID AND  XX.TEILMESAJILETIMGRUBU_ILETILEN = VEILMESAJ.TEILMESAJILETIMGRUBU_ILETILEN  AND YY.IHR1PERSONEL_ILETILEN = "+ personelId +" " +
                "   AND YY.OKUNMAZAMANI IS NULL ) " +
                "  END ) OKUNMAYANCOUNT " +
                ",TEILMESAJILETIMGRUBU_ILETILEN GROUPID " +
                "FROM SONILETILER ,VEILMESAJ , IHR1PERSONEL " +
                "WHERE VEILMESAJ.ID  = SONILETILER.VEILMESAJ_ID " +
                "AND IHR1PERSONEL.ID(+) = VEILMESAJ.IHR1PERSONEL_ILETILEN " +
                "ORDER BY  VEILMESAJ.GONDERIMZAMANI DESC";

        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        list = query.list();

        for(Object o : list){
            Map map = (Map) o;
            Date gonderimZamani = (Date) map.get("GONDERIMZAMANI");
            String mesaj = (String) map.get("MESAJ");
            String iletilenAdi = (String) map.get("ILETILEN");
            String yazanAdi = (String) map.get("YAZAN");
            Date okunmaZamani = (Date) map.get("OKUNMAZAMANI");
            String groupName = (String) map.get("GROUPNAME");
            BigDecimal groupId = (BigDecimal) map.get("GROUPID");
            BigDecimal iletilenPersonelId = (BigDecimal) map.get("ID");
            BigDecimal yazanPersonelId = (BigDecimal) map.get("IHR1PERSONEL_YAZAN");
            BigDecimal groupOkunmayan = (BigDecimal) map.get("OKUNMAYANCOUNT");

            InboxMessageDTO inboxMessageDTO = new InboxMessageDTO();

            //bire bir konuşma
            if(iletilenPersonelId != null && yazanPersonelId != null) {
                if (iletilenPersonelId.longValue() == personelId) {
                    inboxMessageDTO.setPersonelName(yazanAdi);
                    inboxMessageDTO.setMe(true);
                } else {
                    inboxMessageDTO.setPersonelName(iletilenAdi);
                    inboxMessageDTO.setMe(false);
                }
                inboxMessageDTO.setIletilenPersonelId(iletilenPersonelId.longValue());
                inboxMessageDTO.setYazanPersonelId(yazanPersonelId.longValue());
                if(mesaj != null) {
                    try {
                        inboxMessageDTO.setLastMessage(utilCrypto.decryptMessage(mesaj, yazanPersonelId.longValue()));
                    } catch (Exception e) {
                    }
                }
            }

            //grup konuşması
            if(groupId != null && yazanPersonelId != null) {
                inboxMessageDTO.setPersonelName(yazanAdi);
                inboxMessageDTO.setMe(false);
                inboxMessageDTO.setGroupId(groupId.longValue());
                inboxMessageDTO.setYazanPersonelId(yazanPersonelId.longValue());
                inboxMessageDTO.setGroup(true);
                inboxMessageDTO.setGroupName(iletilenAdi);

                if(mesaj != null) {
                    try {
                        inboxMessageDTO.setLastMessage(utilCrypto.decryptMessage(mesaj, groupId.longValue()));
                    } catch (Exception e) {
                    }
                }
            }

            if(groupId != null) {
                inboxMessageDTO.setGroup(true);
            } else {
                inboxMessageDTO.setGroup(false);
            }

            if(groupName != null)
                inboxMessageDTO.setGroupName(groupName);

            if(gonderimZamani != null) {
                inboxMessageDTO.setSendDate(gonderimZamani);
            }
            if(okunmaZamani != null) {
                inboxMessageDTO.setOkunmaZamani(okunmaZamani);
                inboxMessageDTO.setHasunread(false);
            }
            if(groupOkunmayan != null && groupOkunmayan.longValue() > 0) {
                inboxMessageDTO.setHasunread(true);
            }
            inboxMessageDTOList.add(inboxMessageDTO);

        }

        return inboxMessageDTOList;

    }

    public MessageLineResponseDTO getMessageLinesByPersonelId(MessageLineRequestDTO messageLineRequestDTO) {
        /*String userName = SecurityUtils.getCurrentUserLogin();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();*/
        MessageLineResponseDTO messageLineResponseDTO =  null;
        List<MessageLineDTO> messageLineDTOList = new ArrayList<>();
        String sql = "";
        Map map = null;

        Long yazanPId = messageLineRequestDTO.getPersonelId();
        Long iletinlenPId = messageLineRequestDTO.getIlentilenPersonelId();

        if (messageLineRequestDTO.getIletimTuru().equalsIgnoreCase("KISIYEILETIM")) {
            sql = "SELECT A.GONDERIMZAMANI, C.ADI||' '||C.SOYADI MESAJSAHIBI, A.IHR1PERSONEL_YAZAN, A.MESAJ, B.OKUNMAZAMANI" +
                    " FROM VEILMESAJ A,VEILMESAJLINE B, IHR1PERSONEL C " +
                    "WHERE ((A.IHR1PERSONEL_YAZAN =" +  yazanPId + " AND A.IHR1PERSONEL_ILETILEN = " + iletinlenPId  + " ) " +
                    "        OR (A.IHR1PERSONEL_YAZAN = " + iletinlenPId  + "  AND A.IHR1PERSONEL_ILETILEN = " + yazanPId + " )) " +
                    "        AND A.ID = B.VEILMESAJ_ID " +
                    "        AND (A.IHR1PERSONEL_YAZAN = C.ID)" +
                    "        ORDER BY A.GONDERIMZAMANI DESC";
        } else if (messageLineRequestDTO.getIletimTuru().equalsIgnoreCase("GRUBAILETIM")) {
            sql = "select A.ID,C.ADI||' '||C.SOYADI MESAJSAHIBI,A.GONDERIMZAMANI,A.MESAJ, A.IHR1PERSONEL_YAZAN, B.OKUNMAZAMANI, D.ISACTIVE " +
                    "  from VEILMESAJ A,VEILMESAJLINE B, IHR1PERSONEL C, TEILMESAJILETIMGRUBULINE D " +
                    " Where B.VEILMESAJ_ID = A.ID " +
                    "    And A.IHR1PERSONEL_YAZAN = C.ID " +
                    "    And B.IHR1PERSONEL_ILETILEN = " + yazanPId +
                    "    And A.TEILMESAJILETIMGRUBU_ILETILEN = " + messageLineRequestDTO.getGroupId() +
                    "    And D.TEILMESAJILETIMGRUBU_ID = " + messageLineRequestDTO.getGroupId() +
                    "    And D.IHR1PERSONEL_ID = " + yazanPId +
                    " union all " +
                    " select A.ID, 'SİSTEM' as MESAJSAHIBI, A.GONDERIMZAMANI, A.MESAJ, A.IHR1PERSONEL_YAZAN, B.OKUNMAZAMANI, D.ISACTIVE " +
                    " from VEILMESAJ A, VEILMESAJLINE B, TEILMESAJILETIMGRUBULINE D " +
                    " where B.VEILMESAJ_ID = A.ID " +
                    " and B.IHR1PERSONEL_ILETILEN = " + yazanPId +
                    " and A.TEILMESAJILETIMGRUBU_ILETILEN = " + messageLineRequestDTO.getGroupId() +
                    " and D.TEILMESAJILETIMGRUBU_ID = " + messageLineRequestDTO.getGroupId() +
                    " and D.IHR1PERSONEL_ID = " + yazanPId +
                    " and A.IHR1PERSONEL_YAZAN = " + Constants.MESSAGE_SYSTEM_USER_ID +
                    " order by GONDERIMZAMANI DESC ";
        }





        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        list = query.list();

        Boolean controlPersonal=true;
        Boolean controlGroup=false;
        int counter = 0;
        int counter2 = 0;

        for(Object o : list){
            map = (Map) o;
            Date sendDate = (Date) map.get("GONDERIMZAMANI");
            Date okunmaZamani = (Date) map.get("OKUNMAZAMANI");
            String personelName = (String) map.get("MESAJSAHIBI");
            BigDecimal personelId = (BigDecimal) map.get("IHR1PERSONEL_YAZAN");
            String message = (String) map.get("MESAJ");

            MessageLineDTO messageLineDTO = new MessageLineDTO();

            if(personelId != null)
                messageLineDTO.setPersonelId(personelId.longValue());
            if(sendDate != null)
                messageLineDTO.setSendDate(sendDate);
            if(personelName != null)
                messageLineDTO.setPersonelName(personelName);
            messageLineDTO.setType(Constants.MESSAGE_TYPE_REAL_MESSAGE);

            //messageLineDTO.setActive(isActive);

            if (messageLineRequestDTO.getIletimTuru().equalsIgnoreCase("KISIYEILETIM")) {
                //kişisel mesaj
                counter++;
                if (personelId.longValue() != yazanPId.longValue()) {
                    if (controlPersonal && okunmaZamani != null) {
                        controlPersonal = false;
                        if (counter2 != 0) {
                            messageLineDTOList.add(counter2, new MessageLineDTO(null, null, null, null, Constants.MESSAGE_TYPE_LINE_MESSAGE, null)); //!!!
                        }
                    }
                    counter2 = counter;
                }
                try {
                    if(message != null) {
                        messageLineDTO.setMessage(utilCrypto.decryptMessage(message, personelId.longValue()));
                    }
                } catch (Exception e) {
                }

            } else if (messageLineRequestDTO.getIletimTuru().equalsIgnoreCase("GRUBAILETIM")) {
                //Grup mesajı
                counter++;
                if (personelId.longValue() != yazanPId.longValue()) {
                    if (controlPersonal && okunmaZamani != null) {
                        controlPersonal = false;
                        if (counter2 != 0) {
                            messageLineDTOList.add(counter2, new MessageLineDTO(null, null, null, null, Constants.MESSAGE_TYPE_LINE_MESSAGE, null)); //!!!
                        }
                    }
                    counter2 = counter;
                }
                try {
                    if(message != null) {
                        messageLineDTO.setMessage(utilCrypto.decryptMessage(message, messageLineRequestDTO.getGroupId()));
                    }
                } catch (Exception e) {
                }


            }

            messageLineDTOList.add(messageLineDTO);

        }

        //okundu
        if (messageLineRequestDTO.getGroupId() != null && messageLineRequestDTO.getGroupId() > 0) {
            doReadMessagesGroupChat(messageLineRequestDTO.getPersonelId(), messageLineRequestDTO.getGroupId());
        } else {
            doReadMessagesPersonalChat(messageLineRequestDTO.getPersonelId(), messageLineRequestDTO.getIlentilenPersonelId());
        }

        messageLineResponseDTO = new MessageLineResponseDTO();

        if (map != null && !map.isEmpty()) {
            String isActive = (String) map.get("ISACTIVE");
            if (isActive != null && isActive.equalsIgnoreCase("H")) {
                messageLineResponseDTO.setActive(false);
            } else {
                messageLineResponseDTO.setActive(true);
            }
        } else {
            messageLineResponseDTO.setActive(true);
        }
        messageLineResponseDTO.setErrorDTO(new ErrorDTO(false,null));
        messageLineResponseDTO.setMessageLineDTOList(messageLineDTOList);

        return messageLineResponseDTO;
    }

    public Boolean doReadMessagesGroupChat(Long personelId, Long groupId) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        String sql = "UPDATE VEILMESAJLINE SET OKUNMAZAMANI= TO_DATE(:date,'YYYY-MM-DD HH24:mi:ss') " +
                " WHERE VEILMESAJLINE.OKUNMAZAMANI is NULL AND VEILMESAJLINE.IHR1PERSONEL_ILETILEN=" + personelId +" AND VEILMESAJ_ID IN  " +
                " (SELECT ID FROM VEILMESAJ A " +
                " WHERE (A.TEILMESAJILETIMGRUBU_ILETILEN =  "+ groupId +" ))";

        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter("date", dateString);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        query.executeUpdate();

        return true;
    }

    public Boolean doReadMessagesPersonalChat(Long personelId, Long iletilenPersonelId) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        String sql = "UPDATE VEILMESAJLINE SET OKUNMAZAMANI= TO_DATE(:date,'YYYY-MM-DD HH24:mi:ss') " +
                " WHERE VEILMESAJLINE.OKUNMAZAMANI is NULL AND VEILMESAJ_ID IN  " +
                " (SELECT ID FROM VEILMESAJ A " +
                " WHERE (A.IHR1PERSONEL_ILETILEN = " + personelId + " AND A.IHR1PERSONEL_YAZAN =" + iletilenPersonelId + "))";

        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter("date", dateString);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        query.executeUpdate();

        return true;
    }

    //kullanıcı mesaj attıktan sonra kendi mesajını kendisine okundu yapmak için
    public Boolean doReadMessagesPersonalSelf(Long personelId) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        String sql = "UPDATE VEILMESAJLINE SET OKUNMAZAMANI= TO_DATE(:date,'YYYY-MM-DD HH24:mi:ss') " +
                " WHERE VEILMESAJLINE.OKUNMAZAMANI is NULL AND VEILMESAJ_ID IN  " +
                " (SELECT ID FROM VEILMESAJ A " +
                " WHERE (A.IHR1PERSONEL_ILETILEN = " + personelId + " AND A.IHR1PERSONEL_YAZAN =" + personelId + "))";

        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter("date", dateString);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        query.executeUpdate();

        return true;
    }

    //kullanın gruba mesaj attıktan sonra kendi mesajını kendisine okundu yapmak için
    public Boolean doReadMessagesGroupSelf(Long personelId, Long groupId, Long yazanPersonelId) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        Transaction tx = null;

        String sql = "UPDATE VEILMESAJLINE SET OKUNMAZAMANI= TO_DATE(:date,'YYYY-MM-DD HH24:mi:ss') " +
                " WHERE VEILMESAJLINE.OKUNMAZAMANI is NULL AND VEILMESAJLINE.IHR1PERSONEL_ILETILEN=" + personelId +" AND VEILMESAJ_ID IN  " +
                " (SELECT ID FROM VEILMESAJ A " +
                " WHERE (A.TEILMESAJILETIMGRUBU_ILETILEN =  "+ groupId +" ) AND A.IHR1PERSONEL_YAZAN = " + yazanPersonelId + ")";

        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        tx = session.beginTransaction();
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter("date", dateString);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        query.executeUpdate();
        tx.commit();

        return true;
    }

    public void callProcedure(Session session, VeilMesaj veilMesaj) {
        Query query = session.createSQLQuery(
                "CALL EIL.P_MESAJGONDER(:veilMesajId,:crUser,:errcode,:errtext)")
                .setParameter("veilMesajId", veilMesaj.getID())
                .setParameter("crUser", veilMesaj.getIhr1PersonelYazanId())
                .setParameter("errcode", 0)
                .setParameter("errtext", "");
        query.executeUpdate();
        LOG.debug("procedure executed.");
    }

    public TeilMesajIletimGrubu getMesajIletimGrubuById(Session session, Long id) {
        return (TeilMesajIletimGrubu) session.get(TeilMesajIletimGrubu.class, id);
    }

    public VeilMesaj convertMessageDTOToVeilMesaj(MessageDTO messageDTO) throws Exception {
        VeilMesaj veilMesaj = new VeilMesaj();
        veilMesaj.setGönderimTuru(messageDTO.getGonderimTuru());
        veilMesaj.setIhr1PersonelIletilenId(messageDTO.getIhr1PersonelIletilenId());
        veilMesaj.setIhr1PersonelYazanId(messageDTO.getIhr1PersonelYazanId());
        try {
            if (messageDTO.getGonderimTuru().equalsIgnoreCase("GRUBAILETIM")) {
                veilMesaj.setMesaj(utilCrypto.encryptMessage(messageDTO.getMesaj(), messageDTO.getGroupId()));
            } else if (messageDTO.getGonderimTuru().equalsIgnoreCase("KISIYEILETIM")) {
                veilMesaj.setMesaj(utilCrypto.encryptMessage(messageDTO.getMesaj(), messageDTO.getIhr1PersonelYazanId()));
            }

        } catch (Exception e) {
            throw new Exception();
        }
        veilMesaj.setCrUser(messageDTO.getIhr1PersonelYazanId());
        veilMesaj.setIsActive(true);
        veilMesaj.setDeleteFlag("H");
        veilMesaj.setOkunmaZamani(null);

        return veilMesaj;
    }

    /*
    send default message when  create group
     */
    public void sendDefaultGroupMessage(GroupRequest groupRequest, TeilMesajIletimGrubu teilMesajIletimGrubu) {
        MessageDTO messageDTO = new MessageDTO(
                Constants.MESSAGE_SYSTEM_USER_ID,
                "GRUBAILETIM",
                null,
                "" + groupRequest.getGroupInformationDTO().getOlusturanPersonelName() + " tarafından " + groupRequest.getGroupInformationDTO().getGrupAdi() + " grubu oluşturuldu",
                teilMesajIletimGrubu.getID()
                );
        savePersonalMessage(messageDTO);
        readMessageByCriteria(messageDTO, groupRequest.getGroupInformationDTO().getOlusturanPersonel());
    }


    public void sendDefaultGroupMessageWhenUserAdded(GroupRequest groupRequest) {
        MessageDTO messageDTO = null;
        for(MessageUserDTO messageUserDTO : groupRequest.getMessageUserDTOList()) {
            if (groupRequest.getGroupInformationDTO().getOlusturanPersonel() == null || messageUserDTO.getIletilenPersonelId().longValue() != groupRequest.getGroupInformationDTO().getOlusturanPersonel().longValue()) {
                //gruba sonradan ekleme yapılıyor ve kendisi değil
                messageDTO = new MessageDTO(
                        Constants.MESSAGE_SYSTEM_USER_ID,
                        "GRUBAILETIM",
                        null,
                        "" + groupRequest.getGroupInformationDTO().getOlusturanPersonelName() + ", " + messageUserDTO.getFirstName()+ " " + messageUserDTO.getLastName() + " kişisini ekledi",
                        groupRequest.getGroupInformationDTO().getGroupId()
                );
                savePersonalMessage(messageDTO);
            }
        }
        readMessageByCriteria(messageDTO,groupRequest.getGroupInformationDTO().getOlusturanPersonel());
    }

    /*
        Grup silindiği zaman default mesaj yayınlanır
     */
    public void sendDefaultGroupDeleteMessage(GroupDeleteRequestDTO groupDeleteRequestDTO) {

        MessageDTO messageDTO = new MessageDTO(
                Constants.MESSAGE_SYSTEM_USER_ID,
                "GRUBAILETIM",
                null,
                "" + groupDeleteRequestDTO.getOlusturanPersonelName() + " tarafından " + groupDeleteRequestDTO.getGroupName() + " grubu silindi",
                groupDeleteRequestDTO.getGroupId()
        );
        savePersonalMessage(messageDTO);
        readMessageByCriteria(messageDTO, groupDeleteRequestDTO.getOlusturanPersonelId());
    }

    /*
        Gruptan birisi ayrıldığı zaman sistem mesajı yayınlanır
     */
    public void sendDefaultGroupLeaveMessage(GroupLeaveRequestDTO groupLeaveRequestDTO) {

        MessageDTO messageDTO = new MessageDTO(
                Constants.MESSAGE_SYSTEM_USER_ID,
                "GRUBAILETIM",
                null,
                "" + groupLeaveRequestDTO.getPersonelName() +  " gruptan ayrıldı",
                groupLeaveRequestDTO.getGroupId()
        );
        savePersonalMessage(messageDTO);
        readMessageByCriteria(messageDTO, groupLeaveRequestDTO.getUserId());
    }

    /*
        Grup yoneticisi gruptan birini çıkardığı zaman sistem mesajı yayınlanır
     */
    public void sendDefaultGroupDiscardMessage(GroupLeaveRequestDTO groupLeaveRequestDTO) {

        MessageDTO messageDTO = new MessageDTO(
                Constants.MESSAGE_SYSTEM_USER_ID,
                "GRUBAILETIM",
                null,
                "" + groupLeaveRequestDTO.getDiscardedUserName() + ", " + groupLeaveRequestDTO.getPersonelName() +  " tarafından gruptan çıkarıldı",
                groupLeaveRequestDTO.getGroupId()
        );
        savePersonalMessage(messageDTO);
        readMessageByCriteria(messageDTO, groupLeaveRequestDTO.getGroupAdmin());
    }

    public void readMessageByCriteria(MessageDTO messageDTO, Long sessionUserId) {
        if (messageDTO.getGonderimTuru().equalsIgnoreCase("GRUBAILETIM") && (messageDTO.getIhr1PersonelYazanId() != null && messageDTO.getIhr1PersonelYazanId().longValue() != Constants.MESSAGE_SYSTEM_USER_ID)) {
            doReadMessagesGroupSelf(messageDTO.getIhr1PersonelYazanId(), messageDTO.getGroupId(), messageDTO.getIhr1PersonelYazanId());
        } else if (messageDTO.getGonderimTuru().equalsIgnoreCase("GRUBAILETIM") && (messageDTO.getIhr1PersonelYazanId() != null && messageDTO.getIhr1PersonelYazanId().longValue() == Constants.MESSAGE_SYSTEM_USER_ID)) {
            doReadMessagesGroupSelf(sessionUserId.longValue(), messageDTO.getGroupId(), messageDTO.getIhr1PersonelYazanId());
        }
    }


    //TODO check method
    public Date getcurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        try {
            date =  dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public List<MessageUserDTO> getGroupUsersByGroupId(Long groupId) {
        List<MessageUserDTO> messageUserDTOList = new ArrayList<>();

        String sql = "select f.LASTNAME, f.FIRSTNAME, f.IHR1PERSONEL_ID, c.IHR1PERSONEL_OLUSTURAN from FSM1USERS f " +
                    "INNER JOIN (select a.IHR1PERSONEL_ID, b.IHR1PERSONEL_OLUSTURAN from TEILMESAJILETIMGRUBULINE a " +
                    "INNER JOIN TEILMESAJILETIMGRUBU b on a.TEILMESAJILETIMGRUBU_ID = b.ID where a.TEILMESAJILETIMGRUBU_ID = " +
                    groupId +
                    " AND a.ISACTIVE = 'E') c ON c.IHR1PERSONEL_ID = f.IHR1PERSONEL_ID ";
        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        list = query.list();

        for(Object o : list){
            Map map = (Map) o;
            BigDecimal personelId = (BigDecimal) map.get("IHR1PERSONEL_ID");
            String firstName = (String) map.get("FIRSTNAME");
            String lastName = (String) map.get("LASTNAME");
            BigDecimal groupCreator = (BigDecimal) map.get("IHR1PERSONEL_OLUSTURAN");

            MessageUserDTO userDTO = new MessageUserDTO();

            if(personelId != null)
                userDTO.setPersonelId(personelId.longValue());
            if(firstName != null)
                userDTO.setFirstName(firstName);
            if(lastName != null)
                userDTO.setLastName(lastName);
            if(groupCreator != null) {
                if(!groupCreator.equals(personelId))
                    userDTO.setGroupAdmin(false);
                else
                    userDTO.setGroupAdmin(true);

            }

            messageUserDTOList.add(userDTO);
        }

        return messageUserDTOList;
    }

    public ErrorDTO deleteGroupByGroupId(GroupDeleteRequestDTO groupDeleteRequestDTO) {

        ErrorDTO errorDTO = new ErrorDTO();
        sendDefaultGroupDeleteMessage(groupDeleteRequestDTO);
        try {

            String sql2 = "update TEILMESAJILETIMGRUBU " +
                          " set ISACTIVE = 'H' " +
                          " where ID = " + groupDeleteRequestDTO.getGroupId();
            Session session2 = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query2 = session2.createSQLQuery(sql2);
            query2.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query2.executeUpdate();

            String sql = "update TEILMESAJILETIMGRUBULINE " +
                         " set ISACTIVE = 'H' " +
                         " where TEILMESAJILETIMGRUBU_ID = " + groupDeleteRequestDTO.getGroupId();
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query.executeUpdate();


        } catch (Exception e) {
            errorDTO.setError(true);
            errorDTO.setErrorMessage("Değişiklikler gerçekleştirilirken bir hata gerçekleşti");

            return errorDTO;
        }

        errorDTO.setError(false);
        errorDTO.setErrorMessage(null);

        return errorDTO;
    }

    /*
    @param isActive type H or E
     */
    public ErrorDTO updateIsActiveInGroupLineByUserIdAndGroupId(GroupLeaveRequestDTO groupLeaveRequestDTO, Character isActive) {

        ErrorDTO errorDTO = new ErrorDTO();

        try {

            String sql = " update TEILMESAJILETIMGRUBULINE " +
                         " set ISACTIVE = '" + isActive +
                         "' where TEILMESAJILETIMGRUBU_ID = " + groupLeaveRequestDTO.getGroupId() + " and IHR1PERSONEL_ID = " + groupLeaveRequestDTO.getUserId();
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query.executeUpdate();

        } catch (Exception e) {
            errorDTO.setError(true);
            errorDTO.setErrorMessage("Değişiklikler gerçekleştirilirken bir hata gerçekleşti.");
        }

        errorDTO.setError(false);
        errorDTO.setErrorMessage(null);

        return errorDTO;
    }

    public void sendDefaultMessageWhenDiscardOrLeave(GroupLeaveRequestDTO groupLeaveRequestDTO) {
        if(groupLeaveRequestDTO.getDiscardedUserName() != null) {
            sendDefaultGroupDiscardMessage(groupLeaveRequestDTO);
        } else {
            sendDefaultGroupLeaveMessage(groupLeaveRequestDTO);
        }
    }

    public ErrorDTO groupUserAddByUserList(GroupRequest groupRequest) {

        ErrorDTO errorDTO = new ErrorDTO();
        Date date = getcurrentDate();
        List<TeilMesajİletimGrubuLine> teilMesajİletimGrubuLineList = new ArrayList<>();



        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();

            List<TeilMesajİletimGrubuLine> groupLineList = getAllGroupLineListByGroupId(groupRequest.getGroupInformationDTO().getGroupId());

            for (MessageUserDTO user: groupRequest.getMessageUserDTOList()) {
                Boolean control = false;
                for (TeilMesajİletimGrubuLine teilMesajİletimGrubuLine : groupLineList) {
                    if (teilMesajİletimGrubuLine.getIhr1PersonelId() != null && teilMesajİletimGrubuLine.getIhr1PersonelId().longValue() == user.getIletilenPersonelId().longValue()) {
                        control = true;
                        GroupLeaveRequestDTO  groupLeaveRequestDTO= new GroupLeaveRequestDTO();
                        groupLeaveRequestDTO.setGroupId(groupRequest.getGroupInformationDTO().getGroupId());
                        groupLeaveRequestDTO.setUserId(teilMesajİletimGrubuLine.getIhr1PersonelId());
                        updateIsActiveInGroupLineByUserIdAndGroupId(groupLeaveRequestDTO,'E');
                        break;
                    }
                }
                if (!control) {
                    //set new object
                    TeilMesajİletimGrubuLine userLine = new TeilMesajİletimGrubuLine();
                    userLine.setIhr1PersonelId(user.getIletilenPersonelId());
                    userLine.setAsm2ServisTuruId(null);
                    userLine.setBsm2ServisTuruId(null);
                    userLine.setLhr1GorevTuruId(null);
                    userLine.setIsActive(true);
                    userLine.setCrUser(user.getIletilenPersonelId());
                    userLine.setDeleteFlag("H");

                    teilMesajİletimGrubuLineList.add(userLine);
                }
            }

            TeilMesajIletimGrubu mesajIletimGrubu = getMesajIletimGrubuById(session, groupRequest.getGroupInformationDTO().getGroupId());
            mesajIletimGrubu.setTeilMesajİletimGrubuLineList(teilMesajİletimGrubuLineList);


            session.update(mesajIletimGrubu);
            tx.commit();
            sendDefaultGroupMessageWhenUserAdded(groupRequest);


            errorDTO.setError(false);
            errorDTO.setErrorMessage(null);
            return errorDTO;
        } catch (Exception e){

            LOG.debug("Gruba kullanici eklerken bir hata olustu");
        }

        errorDTO.setError(true);
        errorDTO.setErrorMessage("Gruba kullanici eklerken bir hata olustu");

        return errorDTO;
    }

    public List<TeilMesajİletimGrubuLine> getAllGroupLineListByGroupId(Long groupId) {
        List<TeilMesajİletimGrubuLine> teilMesajİletimGrubuLines = new ArrayList<>();

        String sql = " SELECT * FROM TEILMESAJILETIMGRUBULINE WHERE TEILMESAJILETIMGRUBU_ID = " + groupId;
        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        list = query.list();

        for(Object o : list){
            Map map = (Map) o;
            BigDecimal personelId = (BigDecimal) map.get("IHR1PERSONEL_ID");
            BigDecimal id = (BigDecimal) map.get("ID");
            String isActive = (String) map.get("ISACTIVE");

            TeilMesajİletimGrubuLine teilMesajİletimGrubuLine = new TeilMesajİletimGrubuLine();

            if(personelId != null)
                teilMesajİletimGrubuLine.setIhr1PersonelId(personelId.longValue());
            if(id != null)
                teilMesajİletimGrubuLine.setID(id.longValue());
            if(isActive != null) {
                if (isActive.equalsIgnoreCase("E")) {
                    teilMesajİletimGrubuLine.setIsActive(true);
                } else {
                    teilMesajİletimGrubuLine.setIsActive(false);
                }
            }

            teilMesajİletimGrubuLines.add(teilMesajİletimGrubuLine);
        }

        return teilMesajİletimGrubuLines;
    }




}

