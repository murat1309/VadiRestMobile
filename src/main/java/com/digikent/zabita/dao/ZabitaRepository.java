package com.digikent.zabita.dao;

import com.digikent.mesajlasma.dao.MesajlasmaRepository;
import com.digikent.paydasiliskileri.dto.PaydasSorguDTO;
import com.digikent.zabita.dto.adres.BelediyeDTO;
import com.digikent.zabita.dto.adres.MahalleSokakDTO;
import com.digikent.zabita.dto.denetim.ZabitaDenetimRequest;
import com.digikent.zabita.entity.BDNTDenetim;
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
public class ZabitaRepository {

    private final Logger LOG = LoggerFactory.getLogger(ZabitaRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public Boolean saveZabitaDenetim(ZabitaDenetimRequest zabitaDenetimRequest) {

        LOG.debug("Denetim KAYDI paydas ID = " + zabitaDenetimRequest.getPaydasId());

        BDNTDenetim bdntDenetim = new BDNTDenetim();

        bdntDenetim.setMpi1PaydasId(zabitaDenetimRequest.getPaydasId());
        bdntDenetim.setDenetimTarihi(new Date());
        //olay yeri adresi
        bdntDenetim.setPaftaOlayYeri(zabitaDenetimRequest.getZabitaOlayYeriAdresi().getPaftaOlayYeri());
        bdntDenetim.setAdaNoOlayYeri(zabitaDenetimRequest.getZabitaOlayYeriAdresi().getAdaNoOlayYeri());
        bdntDenetim.setBlokNoOlayYeri(zabitaDenetimRequest.getZabitaOlayYeriAdresi().getBlokNoOlayYeri());
        bdntDenetim.setSiteAdiOlayYeri(zabitaDenetimRequest.getZabitaOlayYeriAdresi().getSiteAdiOlayYeri());
        bdntDenetim.setDaireNoHarfOlayYeri(zabitaDenetimRequest.getZabitaOlayYeriAdresi().getDaireNoHarfOlayYeri());
        bdntDenetim.setDaireNoSayiOlayYeri(zabitaDenetimRequest.getZabitaOlayYeriAdresi().getDaireNoSayiOlayYeri());
        bdntDenetim.setDre1MahalleOlayYeri(zabitaDenetimRequest.getZabitaOlayYeriAdresi().getDre1MahalleOlayYeri());
        bdntDenetim.setKapiNoHarfOlayYeri(zabitaDenetimRequest.getZabitaOlayYeriAdresi().getKapiNoHarfOlayYeri());
        bdntDenetim.setKapiNoSayiOlayYeri(zabitaDenetimRequest.getZabitaOlayYeriAdresi().getKapiNoSayiOlayYeri());
        bdntDenetim.setRre1IlceOlayYeri(zabitaDenetimRequest.getRre1IlceId());
        bdntDenetim.setSre1SokakOlayYeri(zabitaDenetimRequest.getZabitaOlayYeriAdresi().getSre1SokakOlayYeri());
        //tebligat adresi
        bdntDenetim.setSiteAdiTebligat(zabitaDenetimRequest.getZabitaTebligatAdresi().getSiteAdiTebligat());
        bdntDenetim.setDaireNoHarfTebligat(zabitaDenetimRequest.getZabitaTebligatAdresi().getDaireNoHarfTebligat());
        bdntDenetim.setDaireNoSayiTebligat(zabitaDenetimRequest.getZabitaTebligatAdresi().getDaireNoSayiTebligat());
        bdntDenetim.setDre1MahalleTebligat(zabitaDenetimRequest.getZabitaTebligatAdresi().getDre1MahalleTebligat());
        bdntDenetim.setKapiHarfNoTebligat(zabitaDenetimRequest.getZabitaTebligatAdresi().getKapiHarfNoTebligat());
        bdntDenetim.setKapiNoSayiTebligat(zabitaDenetimRequest.getZabitaTebligatAdresi().getKapiNoSayiTebligat());
        bdntDenetim.setRre1ilceTebligat(zabitaDenetimRequest.getRre1IlceId());
        bdntDenetim.setSre1SokakTebligat(zabitaDenetimRequest.getZabitaTebligatAdresi().getSre1SokakTebligat());

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
        String sql = "SELECT ID,TANIM FROM RRE1ILCE WHERE PRE1IL_ID = (SELECT PRE1IL_ID FROM NSM2PARAMETRE) AND TURU='I' AND NVL(ISACTIVE,'E') = 'E'";
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

}
