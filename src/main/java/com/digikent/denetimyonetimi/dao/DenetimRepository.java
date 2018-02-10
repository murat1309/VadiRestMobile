package com.digikent.denetimyonetimi.dao;

import com.digikent.denetimyonetimi.dto.adres.BelediyeDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleSokakDTO;
import com.digikent.denetimyonetimi.dto.adres.SokakDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimRequest;
import com.digikent.denetimyonetimi.dto.denetim.DenetimTuruDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimIsletmeDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.tespit.SecenekTuruDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitGrubuDTO;
import com.digikent.denetimyonetimi.entity.BDNTDenetim;
import com.digikent.denetimyonetimi.entity.MPI1Paydas;
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

    public List<DenetimTuruDTO> getDenetimTuruDTOList() {
        List<DenetimTuruDTO> denetimTuruDTOs = new ArrayList<>();
        String sql = "SELECT ID,TANIM,KAYITOZELISMI,IZAHAT FROM LDNTDENETIMTURU WHERE ISACTIVE='E'";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                DenetimTuruDTO denetimTuruDTO = new DenetimTuruDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");
                String kayitOzelIsmi = (String) map.get("KAYITOZELISMI");
                String izahat = (String) map.get("IZAHAT");

                if(id != null)
                    denetimTuruDTO.setId(id.longValue());
                if(tanim != null)
                    denetimTuruDTO.setTanim(tanim);
                if(kayitOzelIsmi != null)
                    denetimTuruDTO.setKayitOzelIsmi(kayitOzelIsmi);
                if(izahat != null)
                    denetimTuruDTO.setIzahat(izahat);

                denetimTuruDTOs.add(denetimTuruDTO);
            }
        }
        return denetimTuruDTOs;
    }

    public List<TespitGrubuDTO> findTespitGrubuDTOListByDenetimTuruId(Long denetimTuruId) {
        List<TespitGrubuDTO> tespitGrubuDTOList = new ArrayList<>();
        String sql = "SELECT A.ID,A.TANIM,A.KAYITOZELISMI,A.IZAHAT FROM LDNTTESPITGRUBU A JOIN ADNTDENETIMTURUTESPITGRUBU B " +
                " ON A.ISACTIVE='E' AND A.ID=B.LDNTTESPITGRUBU_ID AND B.LDNTDENETIMTURU_ID=" + denetimTuruId;
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                TespitGrubuDTO tespitGrubuDTO = new TespitGrubuDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");
                String kayitOzelIsmi = (String) map.get("KAYITOZELISMI");
                String izahat = (String) map.get("IZAHAT");

                if(id != null)
                    tespitGrubuDTO.setId(id.longValue());
                if(tanim != null)
                    tespitGrubuDTO.setTanim(tanim);
                if(kayitOzelIsmi != null)
                    tespitGrubuDTO.setKayitOzelIsmi(kayitOzelIsmi);
                if(izahat != null)
                    tespitGrubuDTO.setIzahat(izahat);

                tespitGrubuDTOList.add(tespitGrubuDTO);
            }
        }
        return tespitGrubuDTOList;
    }


    public List<TespitDTO> findTespitDTOListByTespitGrubuId(Long tespitGrubuId) {

        List<TespitDTO> tespitDTOList = new ArrayList<>();
        String sql = "SELECT ID,TANIM,SIRASI,KAYITOZELISMI,SECENEKTURU,AKSIYON,EKSUREVERILEBILIRMI,EKSURE,IZAHAT " +
                " FROM LDNTTESPIT WHERE ISACTIVE = 'E' AND LDNTTESPITGRUBU_ID = " + tespitGrubuId +
                " ORDER BY ID ";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                TespitDTO tespitDTO = new TespitDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                String tanim = (String) map.get("TANIM");
                String kayitOzelIsmi = (String) map.get("KAYITOZELISMI");
                String izahat = (String) map.get("IZAHAT");
                String secenekTuru = (String) map.get("SECENEKTURU");
                String aksiyon = (String) map.get("AKSIYON");
                String ekSureVerileblirMi = (String) map.get("EKSUREVERILEBILIRMI");
                BigDecimal ekSure = (BigDecimal) map.get("EKSURE");
                BigDecimal sirasi = (BigDecimal) map.get("SIRASI");

                if(id != null)
                    tespitDTO.setId(id.longValue());
                if(tanim != null)
                    tespitDTO.setTanim(tanim);
                if(kayitOzelIsmi != null)
                    tespitDTO.setKayitOzelIsmi(kayitOzelIsmi);
                if(izahat != null)
                    tespitDTO.setIzahat(izahat);
                if(aksiyon != null)
                    tespitDTO.setAksiyon(aksiyon);
                if(secenekTuru != null)
                    tespitDTO.setSecenekTuru(secenekTuru);
                if(ekSureVerileblirMi != null)
                    tespitDTO.setEkSureVerilebilirMi(ekSureVerileblirMi);
                if(ekSure != null)
                    tespitDTO.setEkSure(ekSure.longValue());
                if(sirasi != null)
                    tespitDTO.setSirasi(sirasi.longValue());

                tespitDTOList.add(tespitDTO);
            }
        }
        return tespitDTOList;


    }

    public List<SecenekTuruDTO> findSecenekDTOListByTespitGrubuId(Long tespitGrubuId) {
        List<SecenekTuruDTO> secenekTuruDTOList = new ArrayList<>();
        String sql = "SELECT B.ID, A.ID AS TESPITID,B.SIRASI, B.DEGERI " +
                " FROM LDNTTESPIT A, LDNTTESPITSECENEK B " +
                " WHERE A.LDNTTESPITGRUBU_ID = " + tespitGrubuId +
                " AND A.ID=B.LDNTTESPIT_ID AND A.SECENEKTURU='CHECKBOX' AND B.ISACTIVE='E' AND A.ISACTIVE = 'E' ORDER BY B.SIRASI";
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if (!list.isEmpty()) {
            for (Object o : list) {
                Map map = (Map) o;
                SecenekTuruDTO secenekTuruDTO = new SecenekTuruDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                BigDecimal tespitId = (BigDecimal) map.get("TESPITID");
                BigDecimal sirasi = (BigDecimal) map.get("SIRASI");
                String degeri = (String) map.get("DEGERI");

                if (id != null)
                    secenekTuruDTO.setId(id.longValue());
                if (tespitId != null)
                    secenekTuruDTO.setTespitId(tespitId.longValue());
                if (sirasi != null)
                    secenekTuruDTO.setSirasi(sirasi.longValue());
                if (degeri != null)
                    secenekTuruDTO.setDegeri(degeri);

                secenekTuruDTOList.add(secenekTuruDTO);
            }
        }

        return secenekTuruDTOList;
    }

    public List<DenetimIsletmeDTO> findIsletmeDTOListByPaydasId(Long paydasId) {
        List<DenetimIsletmeDTO> denetimIsletmeDTOList = new ArrayList<>();
        String sql = "SELECT ID,MPI1PAYDAS_ID,ISLETMAADI,TABELAUNVANI,FAALIYETKONUSU, " +
                "SORUMLUADI || ' ' || SORUMLUSOYADI AS ADSOYAD FROM BISLISLETME WHERE ISACTIVE='E' AND MPI1PAYDAS_ID="+paydasId;
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if (!list.isEmpty()) {
            for (Object o : list) {
                Map map = (Map) o;
                DenetimIsletmeDTO denetimIsletmeDTO = new DenetimIsletmeDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                BigDecimal mpi1PaydasId = (BigDecimal) map.get("MPI1PAYDAS_ID");
                String isletmeAdi = (String) map.get("ISLETMAADI");
                String tabelaUnvani = (String) map.get("TABELAUNVANI");
                String faaliyetKonusu = (String) map.get("FAALIYETKONUSU");
                String sorumluAdSoyad = (String) map.get("ADSOYAD");

                if (id != null)
                    denetimIsletmeDTO.setId(id.longValue());
                if (mpi1PaydasId != null)
                    denetimIsletmeDTO.setPaydasId(mpi1PaydasId.longValue());
                if (isletmeAdi != null)
                    denetimIsletmeDTO.setIsletmeAdi(isletmeAdi);
                if (tabelaUnvani != null)
                    denetimIsletmeDTO.setTabelaUnvani(tabelaUnvani);
                if (faaliyetKonusu != null)
                    denetimIsletmeDTO.setFaaliyetKonusu(faaliyetKonusu);
                if (sorumluAdSoyad != null)
                    denetimIsletmeDTO.setSorumluAdSoyad(sorumluAdSoyad);

                denetimIsletmeDTOList.add(denetimIsletmeDTO);
            }
        }

        return denetimIsletmeDTOList;
    }

    public Boolean saveSahisPaydas(DenetimPaydasDTO denetimPaydasDTO) {
        LOG.debug("Şahıs Paydaş kaydı ADI = " + denetimPaydasDTO.getAdi());

        MPI1Paydas mpi1Paydas = new MPI1Paydas();
        mpi1Paydas.setKayitDurumu("A");
        mpi1Paydas.setPaydasTuru("S");
        mpi1Paydas.setRaporAdi("VADI YAZILIM");
        mpi1Paydas.setSorguAdi("VADI BILISIM");

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.save(mpi1Paydas);
        tx.commit();

        return true;
    }

    public void savePaydas() {

        MPI1Paydas mpi1Paydas = new MPI1Paydas();
        mpi1Paydas.setKayitDurumu("A");
        mpi1Paydas.setPaydasTuru("S");
        mpi1Paydas.setRaporAdi("VADI YAZILIM");
        mpi1Paydas.setSorguAdi("VADI BILISIM");

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.save(mpi1Paydas);
        tx.commit();
    }
}
