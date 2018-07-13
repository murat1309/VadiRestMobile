package com.digikent.denetimyonetimi.dao;

import com.digikent.config.Constants;
import com.digikent.denetimyonetimi.dto.denetim.*;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitDTO;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitKararRequest;
import com.digikent.denetimyonetimi.dto.paydas.DenetimIsletmeDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.tespit.*;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.entity.*;
import com.digikent.denetimyonetimi.enums.DenetimTespitKararAksiyon;
import com.digikent.denetimyonetimi.service.DenetimTarafService;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.digikent.denetimyonetimi.enums.TebligSecenegi.*;

/**
 * Created by Kadir on 26.01.2018.
 */
@Repository
public class DenetimRepository {

    private final Logger LOG = LoggerFactory.getLogger(DenetimRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    DenetimTarafService denetimTarafService;

    public UtilDenetimSaveDTO saveDenetim(DenetimRequest denetimRequest, HttpServletRequest request) {

        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        BDNTDenetim bdntDenetim = null;

        try {
            //daha önce bir denetimtespitId objesi oluşturulmuşsa güncelleme yapması gerekir.
            if (denetimRequest.getBdntDenetimId() != null) {
                LOG.debug("Denetim Guncellemesi paydasID=" + denetimRequest.getDenetimPaydasDTO().getPaydasNo() + " BdntDenetimId=" + denetimRequest.getBdntDenetimId());
                Object o = session.get(BDNTDenetim.class,denetimRequest.getBdntDenetimId());
                bdntDenetim = (BDNTDenetim)o;
                bdntDenetim = getBDNTDenetim(denetimRequest, bdntDenetim, request);
                session.update(bdntDenetim);
                tx.commit();
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,denetimRequest.getBdntDenetimId());
            } else {
                bdntDenetim = new BDNTDenetim();
                LOG.debug("Denetim Kaydi paydas ID = " + denetimRequest.getDenetimPaydasDTO().getPaydasNo());
                bdntDenetim = getBDNTDenetim(denetimRequest, bdntDenetim, request);
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

    private BDNTDenetim getBDNTDenetim(DenetimRequest denetimRequest, BDNTDenetim bdntDenetim, HttpServletRequest request) {

        String crUser = request.getHeader("UserId");

        bdntDenetim.setMpi1PaydasId(denetimRequest.getDenetimPaydasDTO().getPaydasNo());
        bdntDenetim.setDenetimTarihi(new Date());
        bdntDenetim.setIzahat(null);
        if (denetimRequest.getDenetimPaydasDTO().getPaydasTuru().equalsIgnoreCase(Constants.PAYDAS_TURU_KURUM)) {
            bdntDenetim.setBislIsletmeId(denetimRequest.getIsletmeId());
            //işletme için I, paydaş için P
            bdntDenetim.setDenetimTarafTipi("I");
        } else if(denetimRequest.getDenetimPaydasDTO().getPaydasTuru().equalsIgnoreCase(Constants.PAYDAS_TURU_SAHIS)) {
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
        bdntDenetim.setPre1IlOlayYeri(denetimRequest.getDenetimOlayYeriAdresi().getPre1IlOlayYeri());
        //tebligat adresi
        bdntDenetim.setSiteAdiTebligat(denetimRequest.getDenetimTebligatAdresi().getSiteAdiTebligat());
        bdntDenetim.setDaireNoHarfTebligat(denetimRequest.getDenetimTebligatAdresi().getDaireNoHarfTebligat());
        bdntDenetim.setDaireNoSayiTebligat(denetimRequest.getDenetimTebligatAdresi().getDaireNoSayiTebligat());
        bdntDenetim.setKapiHarfNoTebligat(denetimRequest.getDenetimTebligatAdresi().getKapiNoHarfTebligat());
        bdntDenetim.setKapiNoSayiTebligat(denetimRequest.getDenetimTebligatAdresi().getKapiNoSayiTebligat());
        bdntDenetim.setBlokNotebligat(denetimRequest.getDenetimTebligatAdresi().getBlokNotebligat());
        bdntDenetim.setDre1MahalleTebligat(denetimRequest.getDenetimTebligatAdresi().getDre1MahalleTebligat());
        bdntDenetim.setRre1ilceTebligat(denetimRequest.getDenetimTebligatAdresi().getRre1ilceTebligat());
        bdntDenetim.setSre1SokakTebligat(denetimRequest.getDenetimTebligatAdresi().getSre1SokakTebligat());
        bdntDenetim.setPre1IlTebligat(denetimRequest.getDenetimTebligatAdresi().getPre1IlTebligat());

        //role team
        VSYNRoleTeam vsynRoleTeam = new VSYNRoleTeam();
        vsynRoleTeam.setID(denetimRequest.getDenetimTarafDTO().getRoleTeamId());
        bdntDenetim.setVsynRoleTeam(vsynRoleTeam);

        if (crUser != null)
            bdntDenetim.setCrUser(Long.parseLong(crUser));
        if (bdntDenetim.getCrDate() == null)
            bdntDenetim.setCrDate(new Date());
        bdntDenetim.setDeleteFlag("H");
        bdntDenetim.setIsActive(true);

        return bdntDenetim;
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
        String sql = "SELECT A.KARARVERILEBILIRMI, A.ID,A.TANIM,A.KAYITOZELISMI,A.IZAHAT,A.RAPORBASLIK FROM LDNTTESPITGRUBU A JOIN ADNTDENETIMTURUTESPITGRUBU B " +
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
                String raporBaslik = (String) map.get("RAPORBASLIK");
                String kararVerilebilirMi = (String) map.get("KARARVERILEBILIRMI");

                if(id != null)
                    tespitGrubuDTO.setId(id.longValue());
                if(tanim != null)
                    tespitGrubuDTO.setTanim(tanim);
                if(kayitOzelIsmi != null)
                    tespitGrubuDTO.setKayitOzelIsmi(kayitOzelIsmi);
                if(izahat != null)
                    tespitGrubuDTO.setIzahat(izahat);
                if(raporBaslik != null)
                    tespitGrubuDTO.setRaporBaslik(raporBaslik);
                if(kararVerilebilirMi != null)
                    tespitGrubuDTO.setKararVerilebilirMi(kararVerilebilirMi);

                tespitGrubuDTOList.add(tespitGrubuDTO);
            }
        }
        return tespitGrubuDTOList;
    }


    public List<TespitDTO> findTespitDTOListByTespitGrubuId(Long tespitGrubuId) {

        List<TespitDTO> tespitDTOList = new ArrayList<>();
        String sql = "SELECT ID,TANIM,SIRASI,KAYITOZELISMI,SECENEKTURU,AKSIYON,EKSUREVERILEBILIRMI,EKSURE,IZAHAT,TUR " +
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
                String tur = (String) map.get("TUR");

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
                if(tur != null)
                    tespitDTO.setTur(tur);

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

    public UtilDenetimSaveDTO savePaydas(DenetimPaydasDTO denetimPaydasDTO, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0;
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        LOG.debug("Paydas kayit islemi basladi");;
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = null;
            tx = session.beginTransaction();
            if (denetimPaydasDTO.getPaydasTuru().equalsIgnoreCase(Constants.PAYDAS_TURU_SAHIS) && denetimPaydasDTO.getTcKimlikNo() != null) {
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
                    mpi1Paydas.setCrDate(new Date());
                    mpi1Paydas.setCrUser(crUser);

                    Object o = session.save(mpi1Paydas);
                    tx.commit();
                    utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,(Long)o);
                }
            } else if (denetimPaydasDTO.getPaydasTuru().equalsIgnoreCase(Constants.PAYDAS_TURU_KURUM)) {
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
                mpi1Paydas.setCrDate(new Date());
                mpi1Paydas.setCrUser(crUser);

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

    public UtilDenetimSaveDTO saveTelefon(Long telefonCep, Long telefonIs, Long paydasId, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0;
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
                eilTelefon.setCrUser(crUser);
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
                eilTelefon.setCrUser(crUser);
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

    public UtilDenetimSaveDTO saveAdres(DenetimPaydasDTO denetimPaydasDTO, Long paydasId, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0;
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
            bpi1Adres.setPre1IlId((denetimPaydasDTO.getPre1IlId() != null ? denetimPaydasDTO.getPre1IlId() : 0l));
            bpi1Adres.setDre1BagBolumId(0l);
            bpi1Adres.setCrDate(new Date());
            bpi1Adres.setCrUser(crUser);
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

    public UtilDenetimSaveDTO saveIsletme(DenetimIsletmeDTO denetimIsletmeDTO, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0;
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        try {
            LOG.debug("Isletme icin kayit olusturulacak. Isletme Adi = " + denetimIsletmeDTO.getIsletmeAdi());
            BISLIsletme isletme = new BISLIsletme();
            isletme.setVergiDairesi(denetimIsletmeDTO.getVergiDairesi());
            isletme.setIsActive(true);
            isletme.setCrUser(crUser);
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

    public UtilDenetimSaveDTO saveIsletmeAdresi(DenetimIsletmeDTO denetimIsletmeDTO, Long isletmeId, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0;
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
            bislIsletmeAdres.setPre1IlId(denetimIsletmeDTO.getPre1IlId());
            bislIsletmeAdres.setRre1IlceId(denetimIsletmeDTO.getRre1IlceId());
            bislIsletmeAdres.setSiteAdi(denetimIsletmeDTO.getSiteAdi());
            bislIsletmeAdres.setSre1SokakId(denetimIsletmeDTO.getSre1SokakId());
            bislIsletmeAdres.setDre1BagBolumId(0l);
            bislIsletmeAdres.setIletisimAdresiMi("EVET");
            bislIsletmeAdres.setBislIsletmeId(isletmeId);
            bislIsletmeAdres.setCrDate(new Date());
            bislIsletmeAdres.setCrUser(crUser);
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

    public void updateIsletme(Long isletmeId, Long isletmeAdresId, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0;
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Object o = session.get(BISLIsletme.class,isletmeId);
        BISLIsletme bisletme = (BISLIsletme)o;
        bisletme.setBislIsletmeAdresId(isletmeAdresId);
        bisletme.setCrUser(crUser);
        session.update(bisletme);
        session.getTransaction().commit();
        LOG.debug("bislIsletme icin bislIsletmeAdresID guncellendi. bislIsletmeId=" + isletmeId);
    }

    public UtilDenetimSaveDTO saveDenetimTespit(DenetimTespitRequest denetimTespitRequest, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0;
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        BDNTDenetimTespit bdntDenetimTespit = null;
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();

        try {
            if (denetimTespitRequest.getDenetimTespitId() != null) {
                //denetimtespit te güncelleme yapacak
                Object o = session.get(BDNTDenetimTespit.class,denetimTespitRequest.getDenetimTespitId());
                bdntDenetimTespit = (BDNTDenetimTespit)o;

                //bdntDenetimTespit.setDenetimTuruId(denetimTespitRequest.getDenetimTuruId());
                LDNTDenetimTuru ldntDenetimTuru = new LDNTDenetimTuru();
                ldntDenetimTuru.setID(denetimTespitRequest.getDenetimTuruId());
                bdntDenetimTespit.setLdntDenetimTuru(ldntDenetimTuru);

                LDNTTespitGrubu ldntTespitGrubu = new LDNTTespitGrubu();
                ldntTespitGrubu.setID(denetimTespitRequest.getTespitGrubuId());
                bdntDenetimTespit.setLdntTespitGrubu(ldntTespitGrubu);

                session.update(bdntDenetimTespit);
                session.getTransaction().commit();
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,denetimTespitRequest.getDenetimTespitId());
            } else {
                //yeni bir denetimtespit kaydı oluşturuluyor.
                bdntDenetimTespit = new BDNTDenetimTespit();
                bdntDenetimTespit.setDenetimId(denetimTespitRequest.getDenetimId());

                LDNTDenetimTuru ldntDenetimTuru = new LDNTDenetimTuru();
                ldntDenetimTuru.setID(denetimTespitRequest.getDenetimTuruId());
                bdntDenetimTespit.setLdntDenetimTuru(ldntDenetimTuru);

                LDNTTespitGrubu ldntTespitGrubu = new LDNTTespitGrubu();
                ldntTespitGrubu.setID(denetimTespitRequest.getTespitGrubuId());
                bdntDenetimTespit.setLdntTespitGrubu(ldntTespitGrubu);
                bdntDenetimTespit.setBdntDenetimTespitLineList(null);
                bdntDenetimTespit.setIzahat(null);
                bdntDenetimTespit.setCrDate(new Date());
                bdntDenetimTespit.setDeleteFlag("H");
                bdntDenetimTespit.setIsActive(true);
                bdntDenetimTespit.setCrUser(crUser);

                String aksiyon = denetimTespitRequest.getKararVerilebilirMi().equals("E") ? "BELIRSIZ" : "TUTANAK";
                bdntDenetimTespit.setDenetimAksiyonu(aksiyon);
                bdntDenetimTespit.setVerilenSure(null);
                bdntDenetimTespit.setKapamaBaslangicTarihi(null);
                bdntDenetimTespit.setKapamaBitisTarihi(null);
                bdntDenetimTespit.setCezaMiktari(null);

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

    public List<LDNTTespit> findTespitListByTespitGrubuId(Long tespitGrubuId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LDNTTespit.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("tespitGrubuId", tespitGrubuId));
        criteria.addOrder(Order.asc("tur"));
        criteria.addOrder(Order.asc("sirasi"));

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

    public Map<Long,LDNTTespit> findTespitMapByTespitIdList(Set<Long> tespitIdSet) {
        try {
            Session session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(LDNTTespit.class);
            //criteria.add(Restrictions.eq("isActive", true));
            Object[] obj = new Object[] {};
            ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
            temp.addAll(tespitIdSet);
            criteria.add(Restrictions.in("id", temp.toArray()));
            List<LDNTTespit> list = criteria.list();

            Map<Long,LDNTTespit> tespitMap = new HashedMap();

            for (LDNTTespit ldntTespit: list) {
                tespitMap.put(ldntTespit.getID(),ldntTespit);
            }

            return (tespitMap.size() == 0 ? null : tespitMap);
        } catch (Exception ex) {
            LOG.error("findTespitMapByTespitIdList() hata olustu");
            return null;
        }
    }

    public List<DenetimTespitDTO> getDenetimTespitListByTespitId(Long denetimId) {
        List<DenetimTespitDTO> denetimTespitDTOList = new ArrayList<>();

        String sql = "SELECT ID, DENETIMAKSIYONU, " +
                " LDNTDENETIMTURU_ID AS DENETIMTURUID," +
                " (SELECT TANIM FROM LDNTDENETIMTURU WHERE BDNTDENETIMTESPIT.LDNTDENETIMTURU_ID = LDNTDENETIMTURU.ID) DENETIMTURUTANIM,\n" +
                " LDNTTESPITGRUBU_ID AS TESPITGRUBUID," +
                " (SELECT TANIM FROM LDNTTESPITGRUBU WHERE LDNTTESPITGRUBU.ID = BDNTDENETIMTESPIT.LDNTTESPITGRUBU_ID) TESPITGRUBUTANIM,\n" +
                " (SELECT RNAME FROM VSYNROLETEAM WHERE ID= (SELECT VSYNROLETEAM_ID FROM BDNTDENETIM WHERE BDNTDENETIM.ID = BDNTDENETIMTESPIT.BDNTDENETIM_ID)) AS VSYNROLENAME, \n" +
                " (SELECT ADI ||' '||SOYADI FROM MPI1PAYDAS WHERE ID= (SELECT MPI1PAYDAS_ID FROM BDNTDENETIM WHERE BDNTDENETIM.ID = BDNTDENETIMTESPIT.BDNTDENETIM_ID)) AS ADISOYADI, \n" +
                " (SELECT VSYNROLETEAM_ID FROM BDNTDENETIM WHERE BDNTDENETIM.ID = BDNTDENETIMTESPIT.BDNTDENETIM_ID) AS VSYNROLETEAM_ID\n" +
                " FROM BDNTDENETIMTESPIT\n" +
                " WHERE BDNTDENETIMTESPIT.ISACTIVE = 'E' AND DELETEFLAG='H' AND BDNTDENETIMTESPIT.BDNTDENETIM_ID = " + denetimId;

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
            String vsynRoleName = (String) map.get("VSYNROLENAME");
            String denetimAksiyonu = (String) map.get("DENETIMAKSIYONU");
            String adiSoyadi = (String) map.get("ADISOYADI");

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

            if(vsynRoleName != null)
                denetimTespitDTO.setVsynRoleName(vsynRoleName);

            if(denetimAksiyonu != null)
                denetimTespitDTO.setDenetimAksiyonu(denetimAksiyonu);

            if(adiSoyadi != null)
                denetimTespitDTO.setPaydasAdiSoyadi(adiSoyadi);

            denetimTespitDTO.setDenetimId(denetimId);

            denetimTespitDTOList.add(denetimTespitDTO);
        }

        return denetimTespitDTOList;
    }

    public List<DenetimDTO> findDenetimListBySql(String sql) {
        List<DenetimDTO> denetimDTOList = new ArrayList<>();
        List list = new ArrayList<>();

        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        if(!list.isEmpty()) {
            for(Object o : list) {
                Map map = (Map) o;
                DenetimDTO denetimDTO = new DenetimDTO();

                BigDecimal id = (BigDecimal) map.get("ID");
                BigDecimal paydasId = (BigDecimal) map.get("MPI1PAYDAS_ID");
                Date denetimTarihi = (Date) map.get("DENETIMTARIHI");
                String paydasAdi = (String) map.get("PAYDASADI");
                String olayYeriIlceAdi = (String) map.get("ILCEADI");
                String olayYeriMahalleAdi = (String) map.get("MAHALLEADI");
                String olayYeriSokakAdi = (String) map.get("SOKAKADI");
                String takimAdi = (String) map.get("TAKIMADI");
                String olayYeriSiteAdi = (String) map.get("SITEADI_OLAYYERI");
                String olayYeriBlokNo = (String) map.get("BLOKNO_OLAYYERI");
                String olayYeriKapiNoHarf = (String) map.get("KAPINOHARF_OLAYYERI");
                String olayYeriDaireNoHarf = (String) map.get("DAIRENOHARF_OLAYYERI");
                BigDecimal olayYeriKapiNoSayi = (BigDecimal) map.get("KAPINOSAYI_OLAYYERI");
                BigDecimal olayYeriDaireNoSayi = (BigDecimal) map.get("DAIRENOSAYI_OLAYYERI");
                String tebligSecenegi = (String) map.get("TEBLIG_SECENEGI");
                String tebligAdi = (String) map.get("TEBLIG_ADI");
                String tebligSoyadi = (String) map.get("TEBLIG_SOYADI");
                BigDecimal tebligTCKimlikNo = (BigDecimal) map.get("TEBLIG_TC");
                String denetimTarafTipi = (String) map.get("DENETIMTARAFTIPI");

                if(id != null)
                    denetimDTO.setId(id.longValue());
                if(denetimTarihi != null)
                    denetimDTO.setDenetimTarihi(denetimTarihi);
                if(paydasAdi != null)
                    denetimDTO.setPaydasAdi(paydasAdi);
                if(olayYeriIlceAdi != null)
                    denetimDTO.setOlayYeriIlce(olayYeriIlceAdi);
                if(olayYeriMahalleAdi != null)
                    denetimDTO.setOlayYeriMahalle(olayYeriMahalleAdi);
                if(olayYeriSokakAdi != null)
                    denetimDTO.setOlayYeriSokak(olayYeriSokakAdi);
                if(takimAdi != null)
                    denetimDTO.setTakimAdi(takimAdi);
                if(olayYeriSiteAdi != null)
                    denetimDTO.setOlayYeriSiteAdi(olayYeriSiteAdi);
                if(olayYeriBlokNo != null)
                    denetimDTO.setOlayYeriBlokNo(olayYeriBlokNo);
                if(olayYeriKapiNoHarf != null)
                    denetimDTO.setOlayYeriKapiNoHarf(olayYeriKapiNoHarf);
                if(olayYeriDaireNoHarf != null)
                    denetimDTO.setOlayYeriDaireNoHarf(olayYeriDaireNoHarf);
                if(olayYeriKapiNoSayi != null)
                    denetimDTO.setOlayYeriKapiNoSayi(olayYeriKapiNoSayi.longValue());
                if(olayYeriDaireNoSayi != null)
                    denetimDTO.setOlayYeriDaireNoSayi(olayYeriDaireNoSayi.longValue());
                if(tebligSecenegi != null)
                    denetimDTO.setTebligSecenegi(tebligSecenegi);
                if(tebligAdi != null)
                    denetimDTO.setTebligAdi(tebligAdi);
                if(tebligSoyadi != null)
                    denetimDTO.setTebligSoyadi(tebligSoyadi);
                if(tebligTCKimlikNo != null)
                    denetimDTO.setTebligTCKimlikNo(tebligTCKimlikNo.longValue());
                if(paydasId != null)
                    denetimDTO.setPaydasId(paydasId.longValue());
                if(denetimTarafTipi != null)
                    denetimDTO.setDenetimTarafTipi(denetimTarafTipi);

                denetimDTOList.add(denetimDTO);
            }
        }
        return denetimDTOList;
    }

    public UtilDenetimSaveDTO saveTespitler(TespitlerRequest tespitlerRequest, HttpServletRequest request) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            Object o = session.get(BDNTDenetimTespit.class,tespitlerRequest.getDenetimTespitId());
            BDNTDenetimTespit bdntDenetimTespitDB = (BDNTDenetimTespit)o;

            for (TespitSaveDTO tespitSaveDTO: tespitlerRequest.getTespitSaveDTOList()) {
                bdntDenetimTespitDB.getBdntDenetimTespitLineList().add(createDenetimTespitLine(tespitSaveDTO,bdntDenetimTespitDB, request));
                //bdntDenetimTespitLineList.add(bdntDenetimTespitLine);
            }
            session.saveOrUpdate(bdntDenetimTespitDB);
            session.getTransaction().commit();
            LOG.debug("bdntTespitLine'lar eklendi. Adet="+bdntDenetimTespitDB.getBdntDenetimTespitLineList().size());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,null);
        } catch (Exception ex) {
            LOG.debug("bdntDenetimTespit veya bdntTespitLine da kayit esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        } finally {
            return utilDenetimSaveDTO;
        }
    }

    public UtilDenetimSaveDTO updateTespitler(TespitlerRequest tespitlerRequest, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0;
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;

        try {

            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            Object o = session.get(BDNTDenetimTespit.class,tespitlerRequest.getDenetimTespitId());
            BDNTDenetimTespit bdntDenetimTespitDB = (BDNTDenetimTespit)o;

            for (BDNTDenetimTespitLine bdntDenetimTespitLine:bdntDenetimTespitDB.getBdntDenetimTespitLineList()) {
                Boolean isExist = false;
                for (TespitSaveDTO item:tespitlerRequest.getTespitSaveDTOList()) {
                    if (bdntDenetimTespitLine.getTespitId() != null && item.getTespitId() != null){
                        if (bdntDenetimTespitLine.getTespitId().longValue() == item.getTespitId().longValue()) {
                            isExist = true;
                            break;
                        }
                    }
                }
                if (!isExist) {
                    //demek ki sonradan seçilen tespitlerden kaldırıldı
                    LOG.debug("sonradan kaldirilan tespit. tespitId="+bdntDenetimTespitLine.getTespitId());
                    bdntDenetimTespitLine.setIsActive(false);
                    bdntDenetimTespitLine.setDeleteFlag("E");
                    bdntDenetimTespitLine.setCrUser(crUser);
                    bdntDenetimTespitLine.setCrDate(new Date());
                    session.update(bdntDenetimTespitLine);
                }
            }

            for (TespitSaveDTO item:tespitlerRequest.getTespitSaveDTOList()) {
                Boolean isExist = false;
                for (BDNTDenetimTespitLine bdntDenetimTespitLine:bdntDenetimTespitDB.getBdntDenetimTespitLineList()) {
                    if (bdntDenetimTespitLine.getTespitId() != null && item.getTespitId() != null) {
                        if (bdntDenetimTespitLine.getTespitId().longValue() == item.getTespitId().longValue()) {
                            isExist = true;
                            break;
                        }
                    }

                }
                if (!isExist) {
                    //demek ki yeni bir tespit eklendi
                    LOG.debug("sonradan tepsit listesine eklenen tespitID=" + item.getTespitId());
                    BDNTDenetimTespitLine bdntDenetimTespitLine = createDenetimTespitLine(item,bdntDenetimTespitDB, request);
                    session.save(bdntDenetimTespitLine);
                }
            }
            LOG.debug("tespit guncelleme islemleri sona erdi. bdntDenetimTespitID="+tespitlerRequest.getDenetimTespitId());

            //session.saveOrUpdate(bdntDenetimTespitDB);
            session.getTransaction().commit();
            LOG.debug("bdntDenetimTespit guncellendi. bdntDenetimTespitID = " + bdntDenetimTespitDB.getID());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,null);
        } catch (Exception ex) {
            LOG.debug("bdntDenetimTespit veya bdntTespitLine da kayit esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        } finally {
            return utilDenetimSaveDTO;
        }

    }

    public BDNTDenetimTespitLine createDenetimTespitLine(TespitSaveDTO tespitSaveDTO, BDNTDenetimTespit bdntDenetimTespitDB, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0L;
        BDNTDenetimTespitLine bdntDenetimTespitLine = null;
        try {
            bdntDenetimTespitLine = new BDNTDenetimTespitLine();
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
            bdntDenetimTespitLine.setCrUser(crUser);
            //date value
            if (tespitSaveDTO.getTespitCevap().getDateValue() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = formatter.parse(tespitSaveDTO.getTespitCevap().getDateValue());
                bdntDenetimTespitLine.setDateValue(date);
            } else {
                bdntDenetimTespitLine.setDateValue(null);
            }

        } catch (Exception ex) {
            LOG.debug("denetim tespit olusturulurken hata meydana geldi");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
        } finally {
            return  bdntDenetimTespitLine;
        }
    }

    public UtilDenetimSaveDTO saveDenetimTeblig(DenetimTebligRequest denetimTebligRequest, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0;
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = null;
            tx = session.beginTransaction();
            BDNTDenetim bdntDenetim = null;
            Criteria criteria = session.createCriteria(BDNTDenetim.class);
            criteria.add(Restrictions.eq("ID", denetimTebligRequest.getBdntDenetimId()));
            Object denetimCriteria = criteria.uniqueResult();
            bdntDenetim = (BDNTDenetim)denetimCriteria;
            if (bdntDenetim != null) {
                bdntDenetim.setTebligSecenegi(denetimTebligRequest.getDenetimTebligDTO().getTebligSecenegi().toString());
                bdntDenetim.setTebligAdi(denetimTebligRequest.getDenetimTebligDTO().getTebligAdi());
                bdntDenetim.setTebligSoyadi(denetimTebligRequest.getDenetimTebligDTO().getTebligSoyadi());
                bdntDenetim.setTebligTC(denetimTebligRequest.getDenetimTebligDTO().getTebligTC());
                bdntDenetim.setTebligIzahat(denetimTebligRequest.getDenetimTebligDTO().getTebligIzahat());
                bdntDenetim.setCrDate(new Date());
                bdntDenetim.setCrUser(crUser);
                session.update(bdntDenetim);
                tx.commit();
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,denetimTebligRequest.getBdntDenetimId());
            }
            else {
                utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,"bdntDenetimID bilgisi bos geldi, denetimID" + denetimTebligRequest.getBdntDenetimId()), null);
            }
        } catch (Exception ex) {
            LOG.debug("bdntDenetim Teblig guncellemesi esnasinda bir hata olustu");
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        } finally {
            return utilDenetimSaveDTO;
        }
    }

    public UtilDenetimSaveDTO setPassiveDenetimTespit(Long denetimTespitId) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;

        try {
            Session session = sessionFactory.openSession();
            Transaction tx = null;
            tx = session.beginTransaction();
            Object o = session.get(BDNTDenetimTespit.class,denetimTespitId);
            BDNTDenetimTespit bdntDenetimTespit = (BDNTDenetimTespit)o;
            bdntDenetimTespit.setIsActive(false);
            bdntDenetimTespit.setDeleteFlag("E");
            bdntDenetimTespit.setUpdDate(new Date());
            bdntDenetimTespit.setUpdUser(0l);
            for (BDNTDenetimTespitLine denetimTespitLine: bdntDenetimTespit.getBdntDenetimTespitLineList()) {
                denetimTespitLine.setIsActive(false);
                denetimTespitLine.setDeleteFlag("E");
            }
            session.update(bdntDenetimTespit);
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,null);
            tx.commit();
            session.close();
        } catch (Exception ex) {
            LOG.debug("bdntDenetimTespit pasife cekilirken bir hata olustu. bdntDenetimTespit="+denetimTespitId);
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        }
        return utilDenetimSaveDTO;
    }

    public UtilDenetimSaveDTO saveDenetimTespitKarar(DenetimTespitKararRequest denetimTespitKararRequest, HttpServletRequest request) {
        Long crUser = request.getHeader("UserId") != null ? Long.parseLong(request.getHeader("UserId")) : 0;
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;

        try {
            Session session = sessionFactory.openSession();
            Transaction tx = null;
            tx = session.beginTransaction();
            Object o = session.get(BDNTDenetimTespit.class,denetimTespitKararRequest.getDenetimTespitId());
            BDNTDenetimTespit bdntDenetimTespit = (BDNTDenetimTespit)o;
            bdntDenetimTespit.setDenetimAksiyonu(denetimTespitKararRequest.getAksiyon().toString());
            bdntDenetimTespit.setVerilenSure(denetimTespitKararRequest.getEkSure());
            bdntDenetimTespit.setKapamaBaslangicTarihi(denetimTespitKararRequest.getKapamaBaslangicTarihi());
            bdntDenetimTespit.setKapamaBitisTarihi(denetimTespitKararRequest.getKapamaBitisTarihi());
            bdntDenetimTespit.setCezaMiktari(denetimTespitKararRequest.getCezaMiktari());
            bdntDenetimTespit.setUpdDate(new Date());
            bdntDenetimTespit.setUpdUser(crUser);
            session.update(bdntDenetimTespit);
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,null);
            tx.commit();
            session.close();
        } catch (Exception ex) {
            LOG.debug("bdntDenetimTespit karar kaydedilirken bir hata olustu. bdntDenetimTespit="+denetimTespitKararRequest.getDenetimTespitId());
            LOG.debug("HATA MESAJI = " + ex.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false, new ErrorDTO(true,ex.getMessage()), null);
        }
        return utilDenetimSaveDTO;
    }


    public TespitGrubuDTO findTespitGrubuDTOById(Long tespitGrubuId) {
        List<TespitGrubuDTO> tespitGrubuDTOList = new ArrayList<>();
        String sql = "SELECT ID,TANIM,KAYITOZELISMI,IZAHAT,RAPORBASLIK,ALTBILGI FROM LDNTTESPITGRUBU WHERE ID=" + tespitGrubuId;
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
                String raporBaslik = (String) map.get("RAPORBASLIK");
                String altBilgi = (String) map.get("ALTBILGI");

                if(id != null)
                    tespitGrubuDTO.setId(id.longValue());
                if(tanim != null)
                    tespitGrubuDTO.setTanim(tanim);
                if(kayitOzelIsmi != null)
                    tespitGrubuDTO.setKayitOzelIsmi(kayitOzelIsmi);
                if(izahat != null)
                    tespitGrubuDTO.setIzahat(izahat);
                if(raporBaslik != null)
                    tespitGrubuDTO.setRaporBaslik(raporBaslik);
                if(altBilgi != null)
                    tespitGrubuDTO.setAltBilgi(altBilgi);

                tespitGrubuDTOList.add(tespitGrubuDTO);
            }
        }
        return tespitGrubuDTOList.get(0);
    }

    //TODO
    //
    //    public TespitGrubuDTO convertToTespitGrubuToTespitGrubuDTO(LDNTTespitGrubu ldntTespitGrubu) {
    //        //dto ya çevirilecek
    //    }

    public List<Long> getGecmisDenetimlerDenetimIdListByPaydasId(long paydasId) {
        List<Long> denetimIdList = new ArrayList<>();
        List<BDNTDenetim> bdntDenetimList = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(BDNTDenetim.class);
            criteria.add(Restrictions.eq("mpi1PaydasId", paydasId));
            criteria.add(Restrictions.eq("isActive", true));
            bdntDenetimList = criteria.list();
            denetimIdList = bdntDenetimList.stream().map(BDNTDenetim::getID).collect(Collectors.toList());

        } catch (Exception e) {
           LOG.debug("Denetim Yonetimi: gecmis denetimler Denetim Id listesi getirilirken bir hata olustu" + e.getMessage());
        }
        return denetimIdList;
    }

    public List<DenetimGecmisDenetimlerDTO> getGecmisDenetimTespitlerByDenetimIdList(List<Long> denetimIdList) {
        List<DenetimGecmisDenetimlerDTO> denetimGecmisDenetimlerDTOList = new ArrayList<>();
        try {
            if(!denetimIdList.isEmpty()) {
                Session session = sessionFactory.openSession();
                Criteria criteria = session.createCriteria(BDNTDenetimTespit.class);
                criteria.add(Restrictions.in("denetimId", denetimIdList));
                criteria.add(Restrictions.ne("denetimAksiyonu", "BELIRSIZ"));
                criteria.add(Restrictions.eq("isActive", true));
                criteria.add(Restrictions.or( Restrictions.isNotNull("tutanakNo"), Restrictions.isNotNull("cezaNo")));
                criteria.addOrder(Order.desc("crDate"));

                List list = criteria.list();

                for(int i = 0; i < list.size(); i++) {
                    BDNTDenetimTespit bdntDenetimTespit = (BDNTDenetimTespit) list.get(i);
                    DenetimGecmisDenetimlerDTO denetimGecmisDenetimlerDTO = new DenetimGecmisDenetimlerDTO();

                    if(bdntDenetimTespit.getDenetimAksiyonu() != null)
                        denetimGecmisDenetimlerDTO.setDenetimAksiyonu(bdntDenetimTespit.getDenetimAksiyonu());
                    if(bdntDenetimTespit.getID() != null)
                        denetimGecmisDenetimlerDTO.setDenetimTespitId(bdntDenetimTespit.getID());
                    if(bdntDenetimTespit.getLdntDenetimTuru().getKayitOzelIsmi() != null)
                        denetimGecmisDenetimlerDTO.setDenetimTuruAdi(bdntDenetimTespit.getLdntDenetimTuru().getKayitOzelIsmi());
                    if(bdntDenetimTespit.getLdntTespitGrubu().getKayitOzelIsmi() != null)
                        denetimGecmisDenetimlerDTO.setTespitGrubuAdi(bdntDenetimTespit.getLdntTespitGrubu().getKayitOzelIsmi());
                    if(bdntDenetimTespit.getCrDate() != null)
                        denetimGecmisDenetimlerDTO.setDenetimTespitTarih(new SimpleDateFormat("dd-MM-yyyy").format(bdntDenetimTespit.getCrDate()));

                    denetimGecmisDenetimlerDTOList.add(denetimGecmisDenetimlerDTO);
                }
            }
        } catch (Exception e) {
            LOG.debug("Denetim Yonetimi: Gecmis Denetim Tespitler getirilirken bir hata ile karsilasildi. " + e.getMessage());
        }

        return denetimGecmisDenetimlerDTOList;
    }

}
