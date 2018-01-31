package com.digikent.denetimyonetimi.dao;

import com.digikent.denetimyonetimi.dto.adres.BelediyeDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleSokakDTO;
import com.digikent.denetimyonetimi.dto.adres.SokakDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimRequest;
import com.digikent.denetimyonetimi.entity.BDNTDenetim;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Kadir on 26.01.2018.
 */
@Repository
public class DenetimRepository {

    private final Logger LOG = LoggerFactory.getLogger(DenetimRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public Boolean saveDenetim(DenetimRequest denetimRequest) {

        LOG.debug("Denetim KAYDI paydas ID = " + denetimRequest.getDenetimPaydasDTO().getPaydasNo());

        BDNTDenetim bdntDenetim = new BDNTDenetim();

        bdntDenetim.setMpi1PaydasId(denetimRequest.getDenetimPaydasDTO().getPaydasNo());
        bdntDenetim.setDenetimTarihi(new Date());
        bdntDenetim.setIzahat(null);
        bdntDenetim.setVsynRoleTeamId(null);
        if (denetimRequest.getDenetimPaydasDTO().getPaydasTuru().equalsIgnoreCase("Kurum")) {
            //TODO uygun setlemeyi yap
            bdntDenetim.setBislIsletme(null);
        } else if(denetimRequest.getDenetimPaydasDTO().getPaydasTuru().equalsIgnoreCase("Şahıs")) {
            bdntDenetim.setTcKimlikNo(denetimRequest.getDenetimPaydasDTO().getTcKimlikNo());
            bdntDenetim.setBislIsletme(null);
        }

        //olay yeri adresi
        bdntDenetim.setPaftaOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getPaftaOlayYeri());
        bdntDenetim.setAdaNoOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getAdaNoOlayYeri());
        bdntDenetim.setBlokNoOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getBlokNoOlayYeri());
        bdntDenetim.setSiteAdiOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getSiteAdiOlayYeri());
        bdntDenetim.setDaireNoHarfOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getDaireNoHarfOlayYeri());
        bdntDenetim.setDaireNoSayiOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getDaireNoSayiOlayYeri());
        bdntDenetim.setDre1MahalleOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getDre1MahalleOlayYeri());
        bdntDenetim.setKapiNoHarfOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getKapiNoHarfOlayYeri());
        bdntDenetim.setKapiNoSayiOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getKapiNoSayiOlayYeri());
        bdntDenetim.setRre1IlceOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getRre1IlceOlayYeri());
        bdntDenetim.setSre1SokakOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getSre1SokakOlayYeri());
        //tebligat adresi
        bdntDenetim.setSiteAdiTebligat(denetimRequest.getDenetimTebligatAdresi().getSiteAdiTebligat());
        bdntDenetim.setDaireNoHarfTebligat(denetimRequest.getDenetimTebligatAdresi().getDaireNoHarfTebligat());
        bdntDenetim.setDaireNoSayiTebligat(denetimRequest.getDenetimTebligatAdresi().getDaireNoSayiTebligat());
        bdntDenetim.setKapiHarfNoTebligat(denetimRequest.getDenetimTebligatAdresi().getKapiHarfNoTebligat());
        bdntDenetim.setKapiNoSayiTebligat(denetimRequest.getDenetimTebligatAdresi().getKapiNoSayiTebligat());
        bdntDenetim.setBlokNotebligat(denetimRequest.getDenetimTebligatAdresi().getBlokNotebligat());
        bdntDenetim.setDre1MahalleTebligat(denetimRequest.getDenetimTebligatAdresi().getDre1MahalleTebligat());
        bdntDenetim.setRre1ilceTebligat(denetimRequest.getDenetimTebligatAdresi().getRre1ilceTebligat());
        bdntDenetim.setSre1SokakTebligat(denetimRequest.getDenetimTebligatAdresi().getSre1SokakTebligat());

        bdntDenetim.setCrUser(1l);
        bdntDenetim.setCrDate(new Date());
        bdntDenetim.setDeleteFlag("H");
        bdntDenetim.setIsActive(true);
        bdntDenetim.setDenetimTarafTipi("I");

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.save(bdntDenetim);
        tx.commit();

        return true;
    }

    public List<MahalleSokakDTO> findMahalleAndSokakListByBelediyeId(Long belediyeId) {

        String sql = "SELECT A.ID AS MAHALLEID, A.TANIM AS MAHALLEADI, A.RRE1ILCE_ID AS ILCEID, B.ID AS SOKAKID, B.TANIM AS SOKAKADI FROM DRE1MAHALLE A, SRE1SOKAK B\n" +
                "WHERE RRE1ILCE_ID=" + belediyeId +
                " AND A.ID=B.DRE1MAHALLE_ID AND NVL(A.ISACTIVE,'E') = 'E' AND NVL(B.ISACTIVE,'E') = 'E' ORDER BY A.ID";

        List list = new ArrayList<>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        List<MahalleSokakDTO> mahalleSokakDTOList = new ArrayList<>();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                MahalleSokakDTO mahalleSokakDTO= new MahalleSokakDTO();

                BigDecimal mahalleId = (BigDecimal) map.get("MAHALLEID");
                String mahalleAdi = (String) map.get("MAHALLEADI");
                BigDecimal sokakId = (BigDecimal) map.get("SOKAKID");
                String sokakAdi = (String) map.get("SOKAKADI");
                BigDecimal ilceId = (BigDecimal) map.get("ILCEID");

                if(mahalleId != null)
                    mahalleSokakDTO.setMahalleId(mahalleId.longValue());
                if(mahalleAdi != null)
                    mahalleSokakDTO.setMahalleAdi(mahalleAdi);
                if(sokakId != null)
                    mahalleSokakDTO.setSokakId(sokakId.longValue());
                if(sokakAdi != null)
                    mahalleSokakDTO.setSokakAdi(sokakAdi);
                if(ilceId != null)
                    mahalleSokakDTO.setIlceId(ilceId.longValue());

                mahalleSokakDTOList.add(mahalleSokakDTO);
            }
        }

        return mahalleSokakDTOList;
    }

    public List<BelediyeDTO> findBelediyeList() {
        List<BelediyeDTO> belediyeDTOList = new ArrayList<>();
        String sql = "SELECT ID,TANIM FROM RRE1ILCE WHERE PRE1IL_ID = (SELECT PRE1IL_ID FROM NSM2PARAMETRE) AND TURU='I' AND NVL(ISACTIVE,'E') = 'E' ORDER BY TANIM ";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                BelediyeDTO belediyeDTO = new BelediyeDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");

                if(id != null)
                    belediyeDTO.setId(id.longValue());
                if(tanim != null)
                    belediyeDTO.setTanim(tanim);

                belediyeDTOList.add(belediyeDTO);
            }
        }


        return belediyeDTOList;
    }

    public List<MahalleDTO> findMahalleListByBelediyeId(Long belediyeId) {
        List<MahalleDTO> mahalleDTOList = new ArrayList<>();
        String sql = "SELECT ID, TANIM, RRE1ILCE_ID FROM DRE1MAHALLE WHERE RRE1ILCE_ID=" + belediyeId +" AND NVL(ISACTIVE,'E') = 'E' ORDER BY TANIM";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                MahalleDTO mahalleDTO = new MahalleDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");
                BigDecimal rre1IlceId = (BigDecimal) map.get("RRE1ILCE_ID");

                if(id != null)
                    mahalleDTO.setId(id.longValue());
                if(tanim != null)
                    mahalleDTO.setTanim(tanim);
                if(rre1IlceId != null)
                    mahalleDTO.setRre1IlceId(rre1IlceId.longValue());

                mahalleDTOList.add(mahalleDTO);
            }
        }
        return mahalleDTOList;
    }

    public List<SokakDTO> findSokakListByMahalleId(Long mahalleId) {
        List<SokakDTO> sokakDTOList = new ArrayList<>();
        String sql = "SELECT ID, TANIM FROM SRE1SOKAK WHERE DRE1MAHALLE_ID=" + mahalleId +" AND NVL(ISACTIVE,'E') = 'E' ORDER BY TANIM";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                SokakDTO sokakDTO = new SokakDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");

                if(id != null)
                    sokakDTO.setId(id.longValue());
                if(tanim != null)
                    sokakDTO.setTanim(tanim);

                sokakDTOList.add(sokakDTO);
            }
        }
        return sokakDTOList;
    }

    public List<MahalleDTO> findMahalleListByCurrentBelediye() {
        List<MahalleDTO> mahalleDTOList = new ArrayList<>();
        String sql = "SELECT ID,TANIM,RRE1ILCE_ID FROM DRE1MAHALLE WHERE RRE1ILCE_ID = (SELECT RRE1ILCE_ID FROM NSM2PARAMETRE) AND NVL(ISACTIVE,'E') = 'E' ORDER BY TANIM";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                MahalleDTO mahalleDTO = new MahalleDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");
                BigDecimal rre1IlceId = (BigDecimal) map.get("RRE1ILCE_ID");

                if(id != null)
                    mahalleDTO.setId(id.longValue());
                if(tanim != null)
                    mahalleDTO.setTanim(tanim);
                if(rre1IlceId != null)
                    mahalleDTO.setRre1IlceId(rre1IlceId.longValue());

                mahalleDTOList.add(mahalleDTO);
            }
        }
        return mahalleDTOList;
    }
}
