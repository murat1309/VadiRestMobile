package com.digikent.denetimyonetimi.dao;

import com.digikent.denetimyonetimi.dto.adres.BelediyeDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleSokakDTO;
import com.digikent.denetimyonetimi.dto.adres.SokakDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimRequest;
import com.digikent.denetimyonetimi.dto.denetim.DenetimTespitRequest;
import com.digikent.denetimyonetimi.dto.denetim.DenetimTuruDTO;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimIsletmeDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.taraf.DenetimTarafDTO;
import com.digikent.denetimyonetimi.dto.tespit.*;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.entity.*;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kadir on 26.01.2018.
 */
@Repository
public class DenetimRepository {

    private final Logger LOG = LoggerFactory.getLogger(DenetimRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public UtilDenetimSaveDTO saveDenetim(DenetimRequest denetimRequest) {

        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        BDNTDenetim bdntDenetim = null;

        try {
            if (denetimRequest.getBdntDenetimId() != null) {
                LOG.debug("Denetim Guncellemesi paydasID=" + denetimRequest.getDenetimPaydasDTO().getPaydasNo() + " BdntDenetimId=" + denetimRequest.getBdntDenetimId());
                Object o = session.get(BDNTDenetim.class,denetimRequest.getBdntDenetimId());
                bdntDenetim = (BDNTDenetim)o;
                bdntDenetim = getBDNTDenetim(denetimRequest, bdntDenetim);
                session.update(bdntDenetim);
                tx.commit();
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,denetimRequest.getBdntDenetimId());
            } else {
                bdntDenetim = new BDNTDenetim();
                LOG.debug("Denetim Kaydi paydas ID = " + denetimRequest.getDenetimPaydasDTO().getPaydasNo());
                bdntDenetim = getBDNTDenetim(denetimRequest, bdntDenetim);
                Object o = session.save(bdntDenetim);
                tx.commit();
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,(Long)o);
            }
        } catch (Exception ex) {
            LOG.debug("bdntDenetimTespit kaydı/guncellemesi esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        } finally {
            return utilDenetimSaveDTO;
        }
    }

    private BDNTDenetim getBDNTDenetim(DenetimRequest denetimRequest, BDNTDenetim bdntDenetim) {
        bdntDenetim.setMpi1PaydasId(denetimRequest.getDenetimPaydasDTO().getPaydasNo());
        bdntDenetim.setDenetimTarihi(new Date());
        bdntDenetim.setIzahat(null);
        if (denetimRequest.getDenetimPaydasDTO().getPaydasTuru().equalsIgnoreCase("K")) {
            bdntDenetim.setBislIsletmeId(denetimRequest.getIsletmeId());
            //işletme için I, paydaş için P
            bdntDenetim.setDenetimTarafTipi("I");
        } else if(denetimRequest.getDenetimPaydasDTO().getPaydasTuru().equalsIgnoreCase("S")) {
            bdntDenetim.setTcKimlikNo(denetimRequest.getDenetimPaydasDTO().getTcKimlikNo());
            bdntDenetim.setBislIsletmeId(null);
            //işletme için I, paydaş için P
            bdntDenetim.setDenetimTarafTipi("P");
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

        //role team
        VSYNRoleTeam vsynRoleTeam = new VSYNRoleTeam();
        vsynRoleTeam.setID(denetimRequest.getDenetimTarafDTO().getRoleTeamId());
        bdntDenetim.setVsynRoleTeam(vsynRoleTeam);

        if (bdntDenetim.getCrUser() == null)
            bdntDenetim.setCrUser(1l);
        if (bdntDenetim.getCrDate() == null)
            bdntDenetim.setCrDate(new Date());
        bdntDenetim.setDeleteFlag("H");
        bdntDenetim.setIsActive(true);

        return bdntDenetim;
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
                if (degeri != null) {
                    secenekTuruDTO.setDegeri(degeri);
                    secenekTuruDTO.setLabel(degeri);
                    secenekTuruDTO.setValue(degeri);
                }

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

    public UtilDenetimSaveDTO savePaydas(DenetimPaydasDTO denetimPaydasDTO) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        LOG.debug("Paydas kayit islemi basladi");;
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = null;
            tx = session.beginTransaction();
            if (denetimPaydasDTO.getPaydasTuru().equalsIgnoreCase("S") && denetimPaydasDTO.getTcKimlikNo() != null) {
                //şahıs paydaşı
                //unique lik kontrolü
                MPI1Paydas mpi1PaydasObj = null;
                Criteria criteria = session.createCriteria(MPI1Paydas.class);
                criteria.add(Restrictions.eq("tcKimlikNo", denetimPaydasDTO.getTcKimlikNo()));
                criteria.add(Restrictions.eq("kayitDurumu", "A"));
                Object mpi1PaydasCriteria = criteria.uniqueResult();

                if (mpi1PaydasCriteria != null) {
                    LOG.debug("Ayni TC'e ait paydaş bulunmustur. TC=" + denetimPaydasDTO.getTcKimlikNo());
                    utilDenetimSaveDTO = new UtilDenetimSaveDTO(false,new ErrorDTO(true, "Aynı TC'e ait paydaş bulunmuştur."),null);
                } else {
                    LOG.debug("Girilen TC'e ait kayit bulunamadi. Yeni kayit edilecek. TC=" + denetimPaydasDTO.getTcKimlikNo());
                    MPI1Paydas mpi1Paydas = new MPI1Paydas();
                    mpi1Paydas.setKayitDurumu("A");
                    mpi1Paydas.setPaydasTuru(denetimPaydasDTO.getPaydasTuru());
                    mpi1Paydas.setRaporAdi(denetimPaydasDTO.getAdi() + " " + denetimPaydasDTO.getSoyAdi());
                    mpi1Paydas.setSorguAdi(denetimPaydasDTO.getAdi() + " " + denetimPaydasDTO.getSoyAdi());
                    mpi1Paydas.setTcKimlikNo(denetimPaydasDTO.getTcKimlikNo());
                    mpi1Paydas.setAdi(denetimPaydasDTO.getAdi());
                    mpi1Paydas.setSoyadi(denetimPaydasDTO.getSoyAdi());

                    Object o = session.save(mpi1Paydas);
                    tx.commit();
                    utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,(Long)o);
                }
            } else if (denetimPaydasDTO.getPaydasTuru().equalsIgnoreCase("K")) {
                //Kurum paydaşı
                MPI1Paydas mpi1Paydas = new MPI1Paydas();
                mpi1Paydas.setKayitDurumu("A");
                mpi1Paydas.setPaydasTuru(denetimPaydasDTO.getPaydasTuru());
                mpi1Paydas.setRaporAdi(denetimPaydasDTO.getUnvan());
                mpi1Paydas.setSorguAdi(denetimPaydasDTO.getUnvan());
                mpi1Paydas.setTcKimlikNo(denetimPaydasDTO.getTcKimlikNo());
                mpi1Paydas.setTicaretSicilNo(denetimPaydasDTO.getTicaretSicilNo());
                mpi1Paydas.setVergiNumarasi(denetimPaydasDTO.getVergiNo());
                mpi1Paydas.setVergiDairesi(denetimPaydasDTO.getVergiDairesi());
                mpi1Paydas.setTabelaAdi(denetimPaydasDTO.getUnvan());
                mpi1Paydas.setUnvan(denetimPaydasDTO.getUnvan());

                Object o = session.save(mpi1Paydas);
                tx.commit();
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,(Long)o);
            } else {
                //paydaş türü istenilen kriterde gelmedi
                LOG.debug("paydas istenilen kriterde gelmedi. paydas turu = " + denetimPaydasDTO.getPaydasTuru() + " TC=" + denetimPaydasDTO.getTcKimlikNo());
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(false,new ErrorDTO(true, "Paydaş Türü hatası"),null);
            }
        } catch (Exception ex) {
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false,new ErrorDTO(true, ex.getMessage()),null);
            LOG.debug("paydas kayit esnasinda hata meydana geldi : " + ex.getStackTrace());
        } finally {
            LOG.debug("Paydas kayit islemi tamamlandi. SONUC = " + utilDenetimSaveDTO.getSaved());
            return utilDenetimSaveDTO;
        }
    }

    public UtilDenetimSaveDTO saveTelefon(Long telefonCep, Long telefonIs,Long paydasId) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = null;
            tx = session.beginTransaction();

            EILTelefonTuru eilTelefonTuruCep = null;
            EILTelefonTuru eilTelefonTuruIs = null;

            if (telefonCep != null) {
                LOG.debug("Cep Telefonu kayıt edilecek cepTel = " + telefonCep);
                Criteria criteria = session.createCriteria(EILTelefonTuru.class);
                Object telefonTuruObj = criteria.add(Restrictions.eq("kayitozelismi", "CEP")).uniqueResult();
                eilTelefonTuruCep = (EILTelefonTuru)telefonTuruObj;

                EILTelefon eilTelefon = new EILTelefon();
                eilTelefon.setAhr1adresId(0l);
                eilTelefon.setBpi1adresId(0l);
                eilTelefon.setDahili(null);
                eilTelefon.setEILTelefonTuru(eilTelefonTuruCep);
                eilTelefon.setIhr1personelId(0l);
                eilTelefon.setIzahat(null);
                eilTelefon.setMpi1paydasId(paydasId);
                eilTelefon.setTelefonnumarasi(telefonCep);
                eilTelefon.setCrDate(new Date());
                eilTelefon.setCrUser(0l);
                eilTelefon.setUpdUser(0l);

                Object o = session.save(eilTelefon);
                LOG.debug("Cep Telefonu id = " + (Long)o);
            }

            if (telefonIs != null) {
                LOG.debug("Is Telefonu kayit edilecek isTel = " + telefonIs);
                Criteria criteria = session.createCriteria(EILTelefonTuru.class);
                Object telefonTuruObj = criteria.add(Restrictions.eq("kayitozelismi", "IS")).uniqueResult();
                eilTelefonTuruIs = (EILTelefonTuru)telefonTuruObj;

                EILTelefon eilTelefon = new EILTelefon();
                eilTelefon.setAhr1adresId(0l);
                eilTelefon.setBpi1adresId(0l);
                eilTelefon.setDahili(null);
                eilTelefon.setEILTelefonTuru(eilTelefonTuruIs);
                eilTelefon.setIhr1personelId(0l);
                eilTelefon.setIzahat(null);
                eilTelefon.setMpi1paydasId(paydasId);
                eilTelefon.setTelefonnumarasi(telefonIs);
                eilTelefon.setCrDate(new Date());
                eilTelefon.setCrUser(0l);
                eilTelefon.setUpdUser(0l);

                Object o = session.save(eilTelefon);
                LOG.debug("Is Telefonu id = " + (Long)o);
            }

            if (telefonCep != null || telefonIs != null) {
                tx.commit();
            }

            LOG.debug("telefon kayit islemleri basarili. PaydasId = " + paydasId);
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true, null, null);
        } catch (Exception ex) {
            LOG.debug("Telefon kayit esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        } finally {
            return utilDenetimSaveDTO;
        }
    }

    public UtilDenetimSaveDTO saveAdres(DenetimPaydasDTO denetimPaydasDTO, Long paydasId) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        try {
            LOG.debug("BPI1Adres icin kayit olusturulacak paydasID = " + paydasId);
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();

            BPI1Adres bpi1Adres = new BPI1Adres();
            bpi1Adres.setMpi1paydasId(paydasId);
            bpi1Adres.setBlokNo(denetimPaydasDTO.getBlokNo());
            bpi1Adres.setDaireNoHarf(denetimPaydasDTO.getDaireNoHarf());
            bpi1Adres.setDaireNoSayi(denetimPaydasDTO.getDaireNoSayi());
            bpi1Adres.setDre1MahalleId(denetimPaydasDTO.getDre1MahalleId());
            bpi1Adres.setKapiNoHarf(denetimPaydasDTO.getKapiNoHarf());
            bpi1Adres.setKapiNoSayi(denetimPaydasDTO.getKapiNoSayi());
            bpi1Adres.setRre1IlceId(denetimPaydasDTO.getRre1IlceId());
            bpi1Adres.setRre1SiteAdi(denetimPaydasDTO.getSiteAdi());
            bpi1Adres.setSre1SokakId(denetimPaydasDTO.getSre1SokakId());
            bpi1Adres.setPre1IlId(0l);
            bpi1Adres.setDre1BagBolumId(0l);
            bpi1Adres.setCrDate(new Date());
            bpi1Adres.setCrUser(0l);
            bpi1Adres.setUpdUser(0l);
            bpi1Adres.setActive(true);
            bpi1Adres.setMektupGonderimAdresiMi("E");

            Object o = session.save(bpi1Adres);
            session.getTransaction().commit();
            LOG.debug("bpi1Adres eklendi. bpi1AdresID = " + (Long)o);
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,(Long)o);
        } catch (Exception ex) {
            LOG.debug("Adres kayit esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        } finally {
            return utilDenetimSaveDTO;
        }
    }

    public UtilDenetimSaveDTO saveIsletme(DenetimIsletmeDTO denetimIsletmeDTO) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        try {
            LOG.debug("Isletme icin kayit olusturulacak. Isletme Adi = " + denetimIsletmeDTO.getIsletmeAdi());
            BISLIsletme isletme = new BISLIsletme();
            isletme.setVergiDairesi(denetimIsletmeDTO.getVergiDairesi());
            isletme.setIsActive(true);
            isletme.setCrUser(1l);
            isletme.setCrDate(new Date());
            isletme.setBislIsletmeAdresId(null);
            isletme.setTabelaUnvani(denetimIsletmeDTO.getTabelaUnvani());
            isletme.setMpi1PaydasId(denetimIsletmeDTO.getPaydasId());
            isletme.setIsletmeAdi(denetimIsletmeDTO.getTabelaUnvani());
            isletme.setVergiNumarasi(denetimIsletmeDTO.getVergiNo());
            isletme.setDeleteFlag("H");

            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            Object o = session.save(isletme);
            session.getTransaction().commit();
            LOG.debug("isletme eklendi. isletmeID = " + (Long)o);
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,(Long)o);
        } catch (Exception ex) {
            LOG.debug("Isletme kayit esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        } finally {
            return utilDenetimSaveDTO;
        }
    }

    public UtilDenetimSaveDTO saveIsletmeAdresi(DenetimIsletmeDTO denetimIsletmeDTO, Long isletmeId) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        try {
            LOG.debug("BISLIsletmeAdresi icin kayit olusturulacak. isletmeId = " + isletmeId);
            BISLIsletmeAdres bislIsletmeAdres = new BISLIsletmeAdres();
            bislIsletmeAdres.setBlokNo(denetimIsletmeDTO.getBlokNo());
            bislIsletmeAdres.setDaireNoHarf(denetimIsletmeDTO.getDaireNoHarf());
            bislIsletmeAdres.setDaireNoSayi(denetimIsletmeDTO.getDaireNoSayi());
            bislIsletmeAdres.setDre1MahalleId(denetimIsletmeDTO.getDre1MahalleId());
            bislIsletmeAdres.setKapiNoHarf(denetimIsletmeDTO.getKapiNoHarf());
            bislIsletmeAdres.setKapiNoSayi(denetimIsletmeDTO.getKapiNoSayi());
            bislIsletmeAdres.setRre1IlceId(denetimIsletmeDTO.getRre1IlceId());
            bislIsletmeAdres.setSiteAdi(denetimIsletmeDTO.getSiteAdi());
            bislIsletmeAdres.setSre1SokakId(denetimIsletmeDTO.getSre1SokakId());
            bislIsletmeAdres.setDre1BagBolumId(0l);
            bislIsletmeAdres.setIletisimAdresiMi("EVET");
            bislIsletmeAdres.setBislIsletmeId(isletmeId);
            bislIsletmeAdres.setCrDate(new Date());
            bislIsletmeAdres.setCrUser(1l);
            bislIsletmeAdres.setUpdUser(1l);
            bislIsletmeAdres.setDeleteFlag("H");

            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            Object o = session.save(bislIsletmeAdres);
            session.getTransaction().commit();
            LOG.debug("bislIsletmeAdres eklendi. bislIsletmeAdresID = " + (Long)o);
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,(Long)o);
        } catch (Exception ex) {
            LOG.debug("bislIsletmeAdres kayit esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        } finally {
            return utilDenetimSaveDTO;
        }
    }

    public void updateIsletme(Long isletmeId, Long isletmeAdresId) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Object o = session.get(BISLIsletme.class,isletmeId);
        BISLIsletme bisletme = (BISLIsletme)o;
        bisletme.setBislIsletmeAdresId(isletmeAdresId);
        session.update(bisletme);
        session.getTransaction().commit();
        LOG.debug("bislIsletme icin bislIsletmeAdresID guncellendi. bislIsletmeId=" + isletmeId);
    }

    public UtilDenetimSaveDTO saveDenetimTespit(DenetimTespitRequest denetimTespitRequest) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        BDNTDenetimTespit bdntDenetimTespit = null;
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();

        try {
            if (denetimTespitRequest.getDenetimTespitId() != null) {
                Object o = session.get(BDNTDenetimTespit.class,denetimTespitRequest.getDenetimTespitId());
                bdntDenetimTespit = (BDNTDenetimTespit)o;

                bdntDenetimTespit.setDenetimTuruId(denetimTespitRequest.getDenetimTuruId());
                bdntDenetimTespit.setTespitGrubuId(denetimTespitRequest.getTespitGrubuId());

                session.update(bdntDenetimTespit);
                session.getTransaction().commit();
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,denetimTespitRequest.getDenetimTespitId());
            } else {
                bdntDenetimTespit = new BDNTDenetimTespit();
                bdntDenetimTespit.setDenetimId(denetimTespitRequest.getDenetimId());
                bdntDenetimTespit.setDenetimTuruId(denetimTespitRequest.getDenetimTuruId());
                bdntDenetimTespit.setTespitGrubuId(denetimTespitRequest.getTespitGrubuId());
                bdntDenetimTespit.setBdntDenetimTespitLineList(null);
                //TODO buranın doğru şeylerle setlenmesi lazım
                bdntDenetimTespit.setDenetimAksiyonu("TUTANAK");
                bdntDenetimTespit.setIzahat(null);
                bdntDenetimTespit.setVerilenSure(null);
                bdntDenetimTespit.setCrDate(new Date());
                bdntDenetimTespit.setDeleteFlag("H");
                bdntDenetimTespit.setIsActive(true);
                bdntDenetimTespit.setCrUser(1l);

                Object o = session.save(bdntDenetimTespit);
                session.getTransaction().commit();
                LOG.debug("bdntDenetimTespit eklendi. bdntDenetimTespitID = " + (Long)o);
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,(Long)o);
            }


        } catch (Exception ex) {
            LOG.debug("bdntDenetimTespit kayit esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        } finally {
            return utilDenetimSaveDTO;
        }
    }

    public UtilDenetimSaveDTO saveTespitler(TespitlerRequest tespitlerRequest) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        List<BDNTDenetimTespitLine> bdntDenetimTespitLineList = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            Object o = session.get(BDNTDenetimTespit.class,tespitlerRequest.getDenetimTespitId());
            BDNTDenetimTespit bdntDenetimTespitDB = (BDNTDenetimTespit)o;

            if (bdntDenetimTespitDB != null && bdntDenetimTespitDB.getBdntDenetimTespitLineList().size() > 0) {
                //güncelleme
                for (BDNTDenetimTespitLine bdntDenetimTespitLine:bdntDenetimTespitDB.getBdntDenetimTespitLineList()) {
                    for (TespitSaveDTO tespitSaveDTO: tespitlerRequest.getTespitSaveDTOList()) {
                        if (bdntDenetimTespitLine.getTespitId() == tespitSaveDTO.getTespitId()) {
                            bdntDenetimTespitLine.setTespitId(tespitSaveDTO.getTespitId());
                            bdntDenetimTespitLine.setTutari(tespitSaveDTO.getTutari());
                            bdntDenetimTespitLine.setStringValue(tespitSaveDTO.getTespitCevap().getStringValue());
                            bdntDenetimTespitLine.setNumberValue(tespitSaveDTO.getTespitCevap().getNumberValue());
                            bdntDenetimTespitLine.setTextValue(tespitSaveDTO.getTespitCevap().getTextValue());
                            //date value
                            if (tespitSaveDTO.getTespitCevap().getDateValue() != null) {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = formatter.parse(tespitSaveDTO.getTespitCevap().getDateValue());
                                bdntDenetimTespitLine.setDateValue(date);
                            } else {
                                bdntDenetimTespitLine.setDateValue(null);
                            }
                        }
                    }
                }
                session.saveOrUpdate(bdntDenetimTespitDB);
                session.getTransaction().commit();
                LOG.debug("bdntDenetimTespit guncellendi. bdntDenetimTespitID = " + bdntDenetimTespitDB.getID());
                LOG.debug("bdntTespitLine'lar eklendi. Adet="+bdntDenetimTespitDB.getBdntDenetimTespitLineList().size());
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,null);

            } else if (bdntDenetimTespitDB != null && bdntDenetimTespitDB.getBdntDenetimTespitLineList().size() == 0){
                //yeni kayit
                for (TespitSaveDTO tespitSaveDTO: tespitlerRequest.getTespitSaveDTOList()) {
                    BDNTDenetimTespitLine bdntDenetimTespitLine = new BDNTDenetimTespitLine();
                    //bdntDenetimTespitLine.setID(tespitSaveDTO.getTespitId());
                    bdntDenetimTespitLine.setBdntDenetimTespit(bdntDenetimTespitDB);
                    bdntDenetimTespitLine.setTespitId(tespitSaveDTO.getTespitId());
                    bdntDenetimTespitLine.setTutari(tespitSaveDTO.getTutari());
                    bdntDenetimTespitLine.setStringValue(tespitSaveDTO.getTespitCevap().getStringValue());
                    bdntDenetimTespitLine.setNumberValue(tespitSaveDTO.getTespitCevap().getNumberValue());
                    bdntDenetimTespitLine.setTextValue(tespitSaveDTO.getTespitCevap().getTextValue());
                    bdntDenetimTespitLine.setIzahat(null);
                    bdntDenetimTespitLine.setCrDate(new Date());
                    bdntDenetimTespitLine.setDeleteFlag("H");
                    bdntDenetimTespitLine.setIsActive(true);
                    bdntDenetimTespitLine.setCrUser(1l);
                    //date value
                    if (tespitSaveDTO.getTespitCevap().getDateValue() != null) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = formatter.parse(tespitSaveDTO.getTespitCevap().getDateValue());
                        bdntDenetimTespitLine.setDateValue(date);
                    } else {
                        bdntDenetimTespitLine.setDateValue(null);
                    }
                    bdntDenetimTespitDB.getBdntDenetimTespitLineList().add(bdntDenetimTespitLine);
                    //bdntDenetimTespitLineList.add(bdntDenetimTespitLine);
                }
                //bdntDenetimTespitDB.setBdntDenetimTespitLineList(bdntDenetimTespitLineList);
                session.saveOrUpdate(bdntDenetimTespitDB);
                session.getTransaction().commit();
                LOG.debug("bdntDenetimTespit guncellendi. bdntDenetimTespitID = " + bdntDenetimTespitDB.getID());
                LOG.debug("bdntTespitLine'lar eklendi. Adet="+bdntDenetimTespitDB.getBdntDenetimTespitLineList().size());
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,null);
            } else {
                LOG.debug("HATA = bdntDenetimTespit database den null geldi. Bu yuzden tespit kayitlari olusturulamadi");
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,"bdntDenetimTespit bulunamadi"), null);
            }
        } catch (Exception ex) {
            LOG.debug("bdntDenetimTespit veya bdntTespitLine da kayit esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        } finally {
            return utilDenetimSaveDTO;
        }
    }

    public List<LDNTTespit> findTespitListByTespitGrubuId(Long tespitGrubuId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LDNTTespit.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("tespitGrubuId", tespitGrubuId));
        //Object[] objects = new Object[] { 1l };
        //criteria.add(Restrictions.in("tespitGrubuId", objects));
        List<LDNTTespit> list = criteria.list();
        return list;
    }

    public List<LDNTTespitTarife> findTespitTarifeListByTespitIdList(List<Long> tespitIdList) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LDNTTespitTarife.class);
        criteria.add(Restrictions.eq("isActive", true));
        Object[] obj = new Object[] {};
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.addAll(tespitIdList);
        criteria.add(Restrictions.in("ldntTespitId", temp.toArray()));
        List<LDNTTespitTarife> list = criteria.list();
        return list;
    }

    public BDNTDenetimTespit findDenetimTespitById(Long denetimTespitId) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Object o = session.get(BDNTDenetimTespit.class,denetimTespitId);
        BDNTDenetimTespit bdntDenetimTespitDB = (BDNTDenetimTespit)o;

        return bdntDenetimTespitDB;
    }

    public Map<Long,LDNTTespit> findTespitMapByTespitIdList(List<Long> tespitIdList) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LDNTTespit.class);
        criteria.add(Restrictions.eq("isActive", true));
        Object[] obj = new Object[] {};
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.addAll(tespitIdList);
        criteria.add(Restrictions.in("id", temp.toArray()));
        List<LDNTTespit> list = criteria.list();

        Map<Long,LDNTTespit> tespitMap = new HashedMap();

        for (LDNTTespit ldntTespit: list) {
            tespitMap.put(ldntTespit.getID(),ldntTespit);
        }

        return tespitMap;
    }

    public List<DenetimTespitDTO> getDenetimTespitListByTespitId(Long denetimId) {
        //TODO medetten sql gelecek

        List<DenetimTespitDTO> denetimTespitDTOList = new ArrayList<>();

        String sql = "SELECT ID, " +
                " LDNTDENETIMTURU_ID AS DENETIMTURUID," +
                " (SELECT TANIM FROM LDNTDENETIMTURU WHERE BDNTDENETIMTESPIT.LDNTDENETIMTURU_ID = LDNTDENETIMTURU.ID) DENETIMTURUTANIM,\n" +
                " LDNTTESPITGRUBU_ID AS TESPITGRUBUID," +
                " (SELECT TANIM FROM LDNTTESPITGRUBU WHERE LDNTTESPITGRUBU.ID = BDNTDENETIMTESPIT.LDNTTESPITGRUBU_ID) TESPITGRUBUTANIM,\n" +
                " (SELECT VSYNROLETEAM_ID FROM BDNTDENETIM WHERE BDNTDENETIM.ID = BDNTDENETIMTESPIT.BDNTDENETIM_ID) AS VSYNROLETEAM_ID\n" +
                " FROM BDNTDENETIMTESPIT\n" +
                " WHERE BDNTDENETIMTESPIT.ISACTIVE = 'E' AND BDNTDENETIMTESPIT.BDNTDENETIM_ID = " + denetimId;

        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        list = query.list();

        for(Object o : list){
            Map map = (Map) o;
            BigDecimal id = (BigDecimal) map.get("ID");
            BigDecimal denetimTuruId = (BigDecimal) map.get("DENETIMTURUID");
            String denetimTuruTanim = (String) map.get("DENETIMTURUTANIM");
            BigDecimal tespitGrubuId = (BigDecimal) map.get("TESPITGRUBUID");
            String tespitGrubuTanim = (String) map.get("TESPITGRUBUTANIM");
            BigDecimal roleTeamId = (BigDecimal) map.get("VSYNROLETEAM_ID");

            DenetimTespitDTO denetimTespitDTO = new DenetimTespitDTO();

            if(id != null)
                denetimTespitDTO.setId(id.longValue());

            if(denetimTuruId != null)
                denetimTespitDTO.setDenetimTuruId(denetimTuruId.longValue());

            if(tespitGrubuId != null)
                denetimTespitDTO.setTespitGrubuId(tespitGrubuId.longValue());

            if(roleTeamId != null)
                denetimTespitDTO.setRoleTeamId(roleTeamId.longValue());

            if(denetimTuruTanim != null)
                denetimTespitDTO.setDenetimTuruAdi(denetimTuruTanim);
            if(tespitGrubuTanim != null)
                denetimTespitDTO.setTespitGrubuAdi(tespitGrubuTanim);

            denetimTespitDTO.setDenetimId(denetimId);

            denetimTespitDTOList.add(denetimTespitDTO);
        }

        return denetimTespitDTOList;
    }

    public UtilDenetimSaveDTO saveDenetimTespitTaraf(DenetimTarafDTO denetimTarafDTO) {



        return null;
    }
}
