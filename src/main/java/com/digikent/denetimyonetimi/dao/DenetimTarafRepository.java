package com.digikent.denetimyonetimi.dao;

import com.digikent.config.Constants;
import com.digikent.denetimyonetimi.dto.denetim.DenetimRequest;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.takim.VsynMemberShipDTO;
import com.digikent.denetimyonetimi.dto.taraf.DenetimTarafDTO;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.entity.BDNTDenetimTespitTaraf;
import com.digikent.general.service.TeamService;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
                        if (bdntDenetimTespitTaraf.getIhr1PersonelId() != null && item.getFsm1UserDTO() != null && item.getFsm1UserDTO().getIhr1PersonelDTO().getId() != null){
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
        //TODO görevi alanını doğru setle, fsm1usersdaki karşılığını öğren
        bdntDenetimTespitTaraf.setGorevi(Constants.DENETIM_TARAF_MEMUR_GOREV);
        bdntDenetimTespitTaraf.setIhr1PersonelId(vsynMemberShipDTO.getFsm1UserDTO().getIhr1PersonelDTO().getId());
        bdntDenetimTespitTaraf.setSoyadi(vsynMemberShipDTO.getFsm1UserDTO().getSoyadi());
        //TODO taraftürü alanını doğru setle
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
        //TODO görevi alanını doğru setle, paydaş için karşılığını öğren
        bdntDenetimTespitTaraf.setGorevi(Constants.DENETIM_TARAF_PAYDAS_GOREV);
        bdntDenetimTespitTaraf.setIhr1PersonelId(null);
        bdntDenetimTespitTaraf.setSoyadi(item.getSoyAdi());
        //TODO taraftürü alanını doğru setle
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

}
