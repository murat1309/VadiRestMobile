package com.digikent.denetimyonetimi.dao;

import com.digikent.config.Constants;
import com.digikent.denetimyonetimi.dto.adres.IlDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimRequest;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.takim.VsynMemberShipDTO;
import com.digikent.denetimyonetimi.dto.taraf.DenetimTarafDTO;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.entity.BDNTDenetimTespitTaraf;
import com.digikent.denetimyonetimi.entity.FSM1Users;
import com.digikent.denetimyonetimi.entity.VSYNMemberShip;
import com.digikent.denetimyonetimi.entity.VSYNRoleTeam;
import com.digikent.general.dto.Fsm1UserDTO;
import com.digikent.general.dto.Ihr1PersonelDTO;
import com.digikent.general.dto.Lhr1GorevTuruDTO;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Kadir on 5.03.2018.
 */
@Repository
public class DenetimTarafRepository {

    private final Logger LOG = LoggerFactory.getLogger(DenetimTarafRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public UtilDenetimSaveDTO saveDenetimTespitTaraf(DenetimRequest denetimRequest, Long denetimId, Boolean isSave) {

        UtilDenetimSaveDTO utilDenetimSaveDTO = null;

        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            DenetimTarafDTO denetimTarafDTO = denetimRequest.getDenetimTarafDTO();

            if (!isSave) {
                LOG.debug("denetim taraflarda guncelleme yapilacak. isSave="+ isSave);
                List<BDNTDenetimTespitTaraf> bdntDenetimTespitTarafList = findDenetimTespitTarafListByDenetimIdAndTarafTuru(denetimId, Constants.DENETIM_TARAF_TURU_BELEDIYE);

                for (BDNTDenetimTespitTaraf bdntDenetimTespitTaraf:bdntDenetimTespitTarafList) {
                    Boolean isExist = false;
                    for (VsynMemberShipDTO item:denetimTarafDTO.getMemberShipDTOList()) {
                        if (bdntDenetimTespitTaraf.getIhr1PersonelId() != null && item.getFsm1UserDTO() != null && item.getFsm1UserDTO().getIhr1PersonelDTO() != null && item.getFsm1UserDTO().getIhr1PersonelDTO().getId() != null){
                            if (bdntDenetimTespitTaraf.getIhr1PersonelId().longValue() == item.getFsm1UserDTO().getIhr1PersonelDTO().getId().longValue()) {
                                isExist = true;
                                break;
                            }
                        }
                    }
                    if (!isExist) {
                        //demek ki sonradan taraflardan kaldırıldı bu memur
                        LOG.debug("sonradan taraf kaldirilan memur. denetimTarafID="+bdntDenetimTespitTaraf.getID());
                        bdntDenetimTespitTaraf.setIsActive(false);
                        session.update(bdntDenetimTespitTaraf);
                    }
                }

                for (VsynMemberShipDTO item:denetimTarafDTO.getMemberShipDTOList()) {
                    Boolean isExist = false;
                    for (BDNTDenetimTespitTaraf bdntDenetimTespitTaraf:bdntDenetimTespitTarafList) {
                        if (item.getFsm1UserDTO() != null && item.getFsm1UserDTO().getIhr1PersonelDTO() != null && item.getFsm1UserDTO().getIhr1PersonelDTO().getId() != null && bdntDenetimTespitTaraf.getIhr1PersonelId() != null) {
                            if (item.getFsm1UserDTO().getIhr1PersonelDTO().getId().longValue() == bdntDenetimTespitTaraf.getIhr1PersonelId().longValue()) {
                                isExist = true;
                                break;
                            }
                        }

                    }
                    if (!isExist) {
                        //demek ki yeni bir memur eklendi
                        LOG.debug("sonradan taraf listesine eklenen memur. ihr1PersonelId="+item.getFsm1UserDTO().getIhr1PersonelDTO().getId());
                        BDNTDenetimTespitTaraf bdntDenetimTespitTaraf = createDenetimTespitTarafByMemur(item,denetimId);
                        session.save(bdntDenetimTespitTaraf);
                    }
                }
                LOG.debug("taraf guncelleme islemleri sona erdi. denetimID="+denetimId);

            } else {
                LOG.debug("DenetimTespitTaraf kayitlari olusturulacak");
                LOG.debug("ekipID="+denetimId);
                LOG.debug("ekipteki memur sayisi="+denetimTarafDTO.getMemberShipDTOList().size());
                //belediye memur tarafı
                for (VsynMemberShipDTO item:denetimTarafDTO.getMemberShipDTOList()) {
                    BDNTDenetimTespitTaraf bdntDenetimTespitTaraf = createDenetimTespitTarafByMemur(item,denetimId);
                    session.save(bdntDenetimTespitTaraf);
                }
                LOG.debug("Memur taraf kayitlari olusturuldu");

                LOG.debug("paydas taraf kaydi olusturulacak. paydasID="+denetimRequest.getDenetimPaydasDTO().getPaydasNo());
                //paydaş
                if (denetimRequest.getDenetimPaydasDTO() != null) {
                    DenetimPaydasDTO item = denetimRequest.getDenetimPaydasDTO();
                    BDNTDenetimTespitTaraf bdntDenetimTespitTaraf = createDenetimTespitTarafByPaydas(item,denetimId);
                    session.save(bdntDenetimTespitTaraf);
                    LOG.debug("Paydas taraf kaydi olusturuldu. paydasID="+item.getPaydasNo());
                } else {
                    LOG.debug("PAYDAS gelmedigi icin paydas kaydi olusturulamadi");
                }
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,denetimRequest.getBdntDenetimId());
            }
            session.getTransaction().commit();


        } catch (Exception ex) {
            LOG.debug("bdntDenetimTespitTaraf kaydi esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        }

        return utilDenetimSaveDTO;
    }

    public List<BDNTDenetimTespitTaraf> findDenetimTespitTarafListByDenetimId(Long denetimId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(BDNTDenetimTespitTaraf.class);
        criteria.add(Restrictions.eq("bdntDenetimId", denetimId));
        criteria.add(Restrictions.eq("isActive", true));
        List<BDNTDenetimTespitTaraf> list = criteria.list();
        return list;
    }

    public BDNTDenetimTespitTaraf createDenetimTespitTarafByMemur(VsynMemberShipDTO vsynMemberShipDTO, Long denetimId) {
        BDNTDenetimTespitTaraf bdntDenetimTespitTaraf = new BDNTDenetimTespitTaraf();
        bdntDenetimTespitTaraf.setAdi(vsynMemberShipDTO.getFsm1UserDTO().getAdi());
        bdntDenetimTespitTaraf.setBdntDenetimId(denetimId);
        bdntDenetimTespitTaraf.setGorevi(vsynMemberShipDTO.getFsm1UserDTO().getIhr1PersonelDTO().getLhr1GorevTuruDTO().getTanim());
        bdntDenetimTespitTaraf.setIhr1PersonelId(vsynMemberShipDTO.getFsm1UserDTO().getIhr1PersonelDTO().getId());
        bdntDenetimTespitTaraf.setSoyadi(vsynMemberShipDTO.getFsm1UserDTO().getSoyadi());
        bdntDenetimTespitTaraf.setTarafTuru(Constants.DENETIM_TARAF_TURU_BELEDIYE);
        bdntDenetimTespitTaraf.setIzahat(null);
        bdntDenetimTespitTaraf.setCrDate(new Date());
        bdntDenetimTespitTaraf.setCrUser(0l);
        bdntDenetimTespitTaraf.setUpdUser(0l);
        bdntDenetimTespitTaraf.setDeleteFlag("H");
        bdntDenetimTespitTaraf.setIsActive(true);

        return bdntDenetimTespitTaraf;
    }

    public BDNTDenetimTespitTaraf createDenetimTespitTarafByPaydas(DenetimPaydasDTO item, Long denetimId) {
        BDNTDenetimTespitTaraf bdntDenetimTespitTaraf = new BDNTDenetimTespitTaraf();
        bdntDenetimTespitTaraf.setAdi(item.getAdi());
        bdntDenetimTespitTaraf.setBdntDenetimId(denetimId);
        bdntDenetimTespitTaraf.setGorevi(null);
        bdntDenetimTespitTaraf.setIhr1PersonelId(null);
        bdntDenetimTespitTaraf.setSoyadi(item.getSoyAdi());
        bdntDenetimTespitTaraf.setTarafTuru(Constants.DENETIM_TARAF_TURU_PAYDAS);
        bdntDenetimTespitTaraf.setTcKimlikNo(item.getTcKimlikNo());
        bdntDenetimTespitTaraf.setMpi1PaydasId(item.getPaydasNo());
        bdntDenetimTespitTaraf.setCrDate(new Date());
        bdntDenetimTespitTaraf.setCrUser(0l);
        bdntDenetimTespitTaraf.setUpdUser(0l);
        bdntDenetimTespitTaraf.setDeleteFlag("H");
        bdntDenetimTespitTaraf.setIsActive(true);

        return bdntDenetimTespitTaraf;
    }

    public List<BDNTDenetimTespitTaraf> findDenetimTespitTarafListByDenetimIdAndTarafTuru(Long denetimId, String tarafTuru) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(BDNTDenetimTespitTaraf.class);
        criteria.add(Restrictions.eq("bdntDenetimId", denetimId));
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("tarafTuru", tarafTuru));
        List<BDNTDenetimTespitTaraf> list = criteria.list();
        return list;
    }

    /*
* kullanıcının bulunduğu grupları bulur
* */
    public List<VSYNMemberShip> findVSNYMemberShipListByUserId(Long userId) {

        //FSM1Users fsm1Users = findFsm1UsersById(userId);
        FSM1Users fsm1Users = new FSM1Users();
        fsm1Users.setID(userId);

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(VSYNMemberShip.class);
        criteria.add(Restrictions.eq("childObjectName", "FSM1USERS"));
        criteria.add(Restrictions.eq("parentObjectName", "VSYNROLETEAM"));
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("fsm1Users", fsm1Users));

        List<VSYNMemberShip> list = criteria.list();
        //findVSNYMemberShipListByVSYNRoleTeamId(list.get(0).getVsynRoleTeam());
        return list;
    }

    /*
    * grupların içerisindeki kullanıcıları bulur
    * */
    public List<VSYNMemberShip> findVSNYMemberShipListByVSYNRoleTeamIdList(List<VSYNRoleTeam> vsynRoleTeamList) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(VSYNMemberShip.class);
        criteria.add(Restrictions.eq("childObjectName", "FSM1USERS"));
        criteria.add(Restrictions.eq("parentObjectName", "VSYNROLETEAM"));
        criteria.add(Restrictions.eq("isActive", true));
        Object[] obj = new Object[] {};
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.addAll(vsynRoleTeamList);
        criteria.add(Restrictions.in("vsynRoleTeam", temp.toArray()));
        List<VSYNMemberShip> list = criteria.list();

        return list;
    }

    public FSM1Users findFsm1UsersById(Long id) {
        FSM1Users fsm1Users = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(FSM1Users.class);
        Object userObj = criteria.add(Restrictions.eq("ID", id)).uniqueResult();
        fsm1Users = (FSM1Users)userObj;
        return fsm1Users;
    }

    public List<Fsm1UserDTO> findFsm1UsersByUserServisGorev(String fsm1UsersUSERID) {
        List<Fsm1UserDTO> fsm1UserDTOList = new ArrayList<>();
        String sql = " SELECT A.FIRSTNAME, A.LASTNAME, A.ID AS FSM1USERS_ID,A.IKY_PERSONEL_ID,B.ID AS IHR1PERSONEL_ID, B.BSM2SERVIS_GOREV, B.ADI, B.SOYADI, C.ID AS LHR1GOREVTURU_ID, C.TANIM \n" +
                " FROM FSM1USERS A,IHR1PERSONEL B, LHR1GOREVTURU C\n" +
                " WHERE A.IKY_PERSONEL_ID = B.ID\n" +
                " AND B.LHR1GOREVTURU_ID=C.ID\n" +
                " AND B.PERSONELDURUMU='CALISAN'\n" +
                " AND A.ACTIVE = 'E'\n" +
                " AND A.IKY_PERSONEL_ID>0\n" +
                " AND B.BSM2SERVIS_GOREV = (SELECT B.BSM2SERVIS_GOREV FROM FSM1USERS A,IHR1PERSONEL B WHERE A.USERID='"+ fsm1UsersUSERID +"' AND A.IKY_PERSONEL_ID = B.ID AND rownum = 1)";

        List list = new ArrayList<>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                Fsm1UserDTO fsm1UserDTO = new Fsm1UserDTO();
                Ihr1PersonelDTO ihr1PersonelDTO = new Ihr1PersonelDTO();
                Lhr1GorevTuruDTO lhr1GorevTuruDTO = new Lhr1GorevTuruDTO();

                BigDecimal fsm1UsersId = (BigDecimal) map.get("FSM1USERS_ID");
                BigDecimal ikyPersonelId = (BigDecimal) map.get("IKY_PERSONEL_ID");
                String firstName = (String) map.get("FIRSTNAME");
                String lastName = (String) map.get("LASTNAME");
                BigDecimal ihrPersonelId = (BigDecimal) map.get("IHR1PERSONEL_ID");
                BigDecimal bsm2ServisGorev = (BigDecimal) map.get("BSM2SERVIS_GOREV");
                String adi = (String) map.get("ADI");
                String soyadi = (String) map.get("SOYADI");
                BigDecimal lhr1GorevTuruId = (BigDecimal) map.get("LHR1GOREVTURU_ID");
                String tanim = (String) map.get("TANIM");

                if(fsm1UsersId != null)
                    fsm1UserDTO.setId(fsm1UsersId.longValue());
                if(ikyPersonelId != null)
                    fsm1UserDTO.setIkyPersonelId(ikyPersonelId.longValue());
                if(ihrPersonelId != null)
                    ihr1PersonelDTO.setId(ihrPersonelId.longValue());
                if(lhr1GorevTuruId != null)
                    lhr1GorevTuruDTO.setId(lhr1GorevTuruId.longValue());
                if(bsm2ServisGorev != null)
                    ihr1PersonelDTO.setBsm2ServisGorev(bsm2ServisGorev.longValue());
                if(firstName != null)
                    fsm1UserDTO.setAdi(firstName);
                if(lastName != null)
                    fsm1UserDTO.setSoyadi(lastName);
                if(adi != null)
                    ihr1PersonelDTO.setAdi(adi);
                if(soyadi != null)
                    ihr1PersonelDTO.setSoyadi(soyadi);
                if(tanim != null)
                    lhr1GorevTuruDTO.setTanim(tanim);

                ihr1PersonelDTO.setLhr1GorevTuruDTO(lhr1GorevTuruDTO);
                fsm1UserDTO.setIhr1PersonelDTO(ihr1PersonelDTO);

                fsm1UserDTOList.add(fsm1UserDTO);
            }
        }
        return fsm1UserDTOList;
    }
}
