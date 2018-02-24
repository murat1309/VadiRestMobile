package com.digikent.paydasiliskileri.dao;

import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.paydasiliskileri.dto.*;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasResponseDTO;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
 * Created by Medet on 1/2/2018.
 */

@Repository
public class PaydasIliskileriRepository {

    private final Logger LOG = LoggerFactory.getLogger(PaydasIliskileriRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public PaydasSorguResponseDTO getPaydasInfoByCriteria(String sql) {

        PaydasSorguResponseDTO paydasSorguResponseDTO = new PaydasSorguResponseDTO();

        ErrorDTO errorDTO = new ErrorDTO();

        try {

            List list = new ArrayList<>();
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            if(!list.isEmpty()) {
                paydasSorguResponseDTO.setPaydasSorguResponse(new ArrayList<>());
                for(Object o : list) {
                    Map map = (Map) o;
                    PaydasSorguDTO paydasSorguDTO = new PaydasSorguDTO();

                    BigDecimal paydasNo = (BigDecimal) map.get("ID");
                    String adi = (String) map.get("ADI");
                    String soyAdi = (String) map.get("SOYADI");
                    String unvan = (String) map.get("UNVAN");
                    String vergiNo = (String) map.get("VERGINUMARASI");
                    String telNo = (String) map.get("TELEFON");
                    String paydasTuru = (String) map.get("PAYDASTURU");
                    String tabelaAdi = (String) map.get("TABELAADI");
                    String adres = (String) map.get("ADRES");
                    String izahat = (String) map.get("IZAHAT");
                    String kayitDurumu = (String) map.get("KAYITDURUMU");

                    if(paydasNo != null)
                        paydasSorguDTO.setPaydasNo(paydasNo);
                    if(adi != null)
                        paydasSorguDTO.setAdi(adi);
                    if(soyAdi != null)
                        paydasSorguDTO.setSoyAdi(soyAdi);
                    if(telNo != null)
                        paydasSorguDTO.setTelefon(telNo);
                    if(kayitDurumu != null)
                        paydasSorguDTO.setKayitDurumu(kayitDurumu);
                    if(unvan != null) // bUNU SOR? paydasTuru != null && paydasTuru.equalsIgnoreCase("S") &&
                        paydasSorguDTO.setUnvan(unvan);
                    if(paydasTuru != null)
                        paydasSorguDTO.setPaydasTuru(paydasTuru);
                    if(adres != null)
                        paydasSorguDTO.setAdres(adres);
                    if(paydasTuru != null && paydasTuru.equalsIgnoreCase("K")) {
                        if(vergiNo != null)
                            paydasSorguDTO.setVergiNo(vergiNo);
                        if(izahat != null)
                            paydasSorguDTO.setIzahat(izahat);
                        if(tabelaAdi != null)
                            paydasSorguDTO.setTabelaAdi(tabelaAdi);
                    }

                    paydasSorguResponseDTO.getPaydasSorguResponse().add(paydasSorguDTO);
                }

            }

            errorDTO.setErrorMessage(null);
            errorDTO.setError(false);
            paydasSorguResponseDTO.setErrorDTO(errorDTO);


        } catch (Exception e) {


            errorDTO.setErrorMessage("Bir hata meydana geldi.");
            errorDTO.setError(true);
            paydasSorguResponseDTO.setErrorDTO(errorDTO);
            //paydasSorguResponseDTO.getPaydasSorguResponse().add(paydasSorguDTO);
        }

        return paydasSorguResponseDTO;
    }

    public PaydasSorguResponseDTO getPaydasDebtInfoByPaydasNo(PaydasSorguRequestDTO paydasSorguRequestDTO) {

        PaydasSorguResponseDTO paydasSorguResponseDTO = new PaydasSorguResponseDTO();
        List<PaydasBorcSorguDTO> paydasBorcSorguList = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO();

        try {

            String sql = "SELECT TAHAKKUKTARIHI ,(SELECT TANIM FROM GIN1GELIRTURU WHERE ID = GIN1GELIRTURU_ID) AS GELIRTURU, BORCTUTARI FROM JIN2TAHAKKUKVIEW, MPI1PAYDAS WHERE  JIN2TAHAKKUKVIEW.MPI1PAYDAS_ID = MPI1PAYDAS.ID " +
                    " AND BORCTUTARI > 0 AND MPI1PAYDAS.ID = " + paydasSorguRequestDTO.getPaydasNo();


            List list = new ArrayList<>();
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            if(!list.isEmpty()) {
                for(Object o : list) {
                    Map map = (Map) o;
                    PaydasBorcSorguDTO paydasBorcSorguDTO = new PaydasBorcSorguDTO();

                    Date tarihIslem = (Date) map.get("TAHAKKUKTARIHI");
                    String gelirAdi = (String) map.get("GELIRTURU");
                    BigDecimal borcTutar = (BigDecimal) map.get("BORCTUTARI");

                    if(tarihIslem != null)
                        paydasBorcSorguDTO.setTarihIslem(tarihIslem);
                    if(gelirAdi != null)
                        paydasBorcSorguDTO.setGelirAdi(gelirAdi);
                    if(borcTutar != null)
                        paydasBorcSorguDTO.setBorcTutar(borcTutar);

                    paydasBorcSorguList.add(paydasBorcSorguDTO);
                }
            }

            errorDTO.setError(false);
            errorDTO.setErrorMessage(null);

        } catch (Exception e) {

            errorDTO.setError(true);
            errorDTO.setErrorMessage("Bir Hatayla Karşılaşıldı");
        }

        paydasSorguResponseDTO.setPaydasBorcSorguResponse(paydasBorcSorguList);
        paydasSorguResponseDTO.setErrorDTO(errorDTO);

        return paydasSorguResponseDTO;
    }

    /*
     * Name        Type (DB)
 ----------- ---------
 KAYITTARIHI DATE
 TARIFETURU  NUMBER
 TABELAENI   NUMBER
 BOY         NUMBER
 TABELAYUZU  NUMBER
 ILANADEDI   NUMBER
 ILANALANI   NUMBER
 IZAHAT      VARCHAR2
     */

    public PaydasSorguResponseDTO getPaydasAdvertInfoByPaydasNo(PaydasSorguRequestDTO paydasSorguRequestDTO) {

        PaydasSorguResponseDTO paydasSorguResponseDTO = new PaydasSorguResponseDTO();
        List<PaydasIlanSorguDTO> paydasIlanSorguList = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO();

        try {

            String sql = "SELECT A.KAYITTARIHI,A.FIN7TARIFETURU_ID as TARIFETURU,A.TABELAENI,A.BOY,A.TABELAYUZU,A.ILANADEDI,A.ILANALANI,A.IZAHAT " +
                    " FROM CIN7BILDIRIMEK A,MPI1PAYDAS B where A.ID = B.ID AND B.ID = " + paydasSorguRequestDTO.getPaydasNo();

            List list = new ArrayList<>();
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            if(!list.isEmpty()) {
                for(Object o : list) {

                    Map map = (Map) o;
                    PaydasIlanSorguDTO paydasIlanSorguDTO = new PaydasIlanSorguDTO();

                    Date kayitTarihi = (Date) map.get("KAYITTARIHI");
                    BigDecimal tarifeTuru = (BigDecimal) map.get("TARIFETURU");
                    BigDecimal tabelaEni = (BigDecimal) map.get("TABELAENI");
                    BigDecimal tabelaBoy = (BigDecimal) map.get("BOY");
                    BigDecimal tabelaYuzu = (BigDecimal) map.get("TABELAYUZU");
                    BigDecimal ilanAdedi = (BigDecimal) map.get("ILANADEDI");
                    BigDecimal ilanAlani = (BigDecimal) map.get("ILANALANI");
                    String izahat = (String) map.get("IZAHAT");

                    if(kayitTarihi != null)
                        paydasIlanSorguDTO.setKayitTarihi(kayitTarihi);
                    if(tarifeTuru != null)
                        paydasIlanSorguDTO.setTarifeTuru(tarifeTuru);
                    if(tabelaEni != null)
                        paydasIlanSorguDTO.setTabelaEni(tabelaEni);
                    if(tabelaBoy != null)
                        paydasIlanSorguDTO.setTabelaBoyu(tabelaBoy);
                    if(tabelaYuzu != null)
                        paydasIlanSorguDTO.setTabelaYuzu(tabelaYuzu);
                    if(ilanAdedi != null)
                        paydasIlanSorguDTO.setIlanAdedi(ilanAdedi);
                    if(ilanAlani != null)
                        paydasIlanSorguDTO.setIlanAlani(ilanAlani);
                    if(izahat != null)
                        paydasIlanSorguDTO.setIzahat(izahat);

                    paydasIlanSorguList.add(paydasIlanSorguDTO);

                }

                errorDTO.setError(false);
                errorDTO.setErrorMessage(null);
            }

        } catch(Exception e) {

            errorDTO.setError(true);
            errorDTO.setErrorMessage("Bir hatayla Karşılaşıldı.");
        }

        paydasSorguResponseDTO.setPaydasIlanSorguResponse(paydasIlanSorguList);
        paydasSorguResponseDTO.setErrorDTO(errorDTO);
        return paydasSorguResponseDTO;
    }

    public PaydasSorguResponseDTO getPaydasTahakkukInfoByPaydasNo(PaydasSorguRequestDTO paydasSorguRequestDTO) {


        PaydasSorguResponseDTO paydasSorguResponseDTO = new PaydasSorguResponseDTO();
        List<PaydasTahakkukSorguDTO> paydasTahakkukSorguList = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO();

        try {

            String sql = "SELECT SUM(TAHAKKUKTUTARI) AS TAHAKKUKTUTARI,SUM(BORCTUTARI) AS BORCTUTARI FROM JIN2TAHAKKUKVIEW\n" +
                    "WHERE  MPI1PAYDAS_ID = " + paydasSorguRequestDTO.getPaydasNo();

            List list = new ArrayList<>();
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            if(!list.isEmpty()) {
                for(Object o : list) {

                    Map map = (Map) o;
                    PaydasTahakkukSorguDTO paydasTahakkukSorguDTO = new PaydasTahakkukSorguDTO();

                    BigDecimal tahakkukTutar = (BigDecimal) map.get("TAHAKKUKTUTARI");
                    BigDecimal borcTutar = (BigDecimal) map.get("BORCTUTARI");

                    if(tahakkukTutar != null)
                        paydasTahakkukSorguDTO.setTahakkukTutar(tahakkukTutar);
                    if(borcTutar != null)
                        paydasTahakkukSorguDTO.setBorcTutar(borcTutar);

                    paydasTahakkukSorguList.add(paydasTahakkukSorguDTO);
                }

                errorDTO.setError(false);
                errorDTO.setErrorMessage(null);

            }



        } catch(Exception e) {

            errorDTO.setError(true);
            errorDTO.setErrorMessage("Bir hatayla Karşılaşıldı.");

        }

        paydasSorguResponseDTO.setPaydasTahakkukResponse(paydasTahakkukSorguList);
        paydasSorguResponseDTO.setErrorDTO(errorDTO);
        return paydasSorguResponseDTO;
    }

    public DenetimPaydasResponseDTO getPaydasInformationDenetimByCriteria(String sql) {

        DenetimPaydasResponseDTO denetimPaydasResponseDTO = new DenetimPaydasResponseDTO();

        ErrorDTO errorDTO = new ErrorDTO();

        try {
            List list = new ArrayList<>();
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            if(!list.isEmpty()) {
                denetimPaydasResponseDTO.setResponseDenetimPaydasList(new ArrayList<>());
                for(Object o : list) {
                    Map map = (Map) o;
                    DenetimPaydasDTO denetimPaydasDTO = new DenetimPaydasDTO();

                    BigDecimal paydasNo = (BigDecimal) map.get("ID");
                    String adi = (String) map.get("ADI");
                    String soyAdi = (String) map.get("SOYADI");
                    String unvan = (String) map.get("UNVAN");
                    String vergiNo = (String) map.get("VERGINUMARASI");
                    String telNo = (String) map.get("TELEFON");
                    String paydasTuru = (String) map.get("PAYDASTURU");
                    String tabelaAdi = (String) map.get("TABELAADI");
                    String izahat = (String) map.get("IZAHAT");
                    String kayitDurumu = (String) map.get("KAYITDURUMU");
                    String binaAdi = (String) map.get("BINAADI");
                    String blokNo = (String) map.get("BLOKNO");
                    String kapiNo = (String) map.get("KAPINO");
                    String ilceAdi = (String) map.get("ILCEADI");
                    String kapiNoHarf = (String) map.get("KAPINOHARF");
                    String daireNoHarf = (String) map.get("DAIRENOHARF");
                    String katHarf = (String) map.get("KATHARF");
                    BigDecimal kapinoSayi = (BigDecimal) map.get("KAPINOSAYI");
                    BigDecimal daireNoSayi = (BigDecimal) map.get("DAIRENOSAYI");
                    BigDecimal katSayi = (BigDecimal) map.get("KATSAYI");
                    BigDecimal dre1MahalleId = (BigDecimal) map.get("DRE1MAHALLE_ID");
                    BigDecimal sre1SokakId = (BigDecimal) map.get("SRE1SOKAK_ID");
                    BigDecimal rre1IlceId = (BigDecimal) map.get("RRE1ILCE_ID");
                    BigDecimal tcKimlikNo = (BigDecimal) map.get("TCKIMLIKNO");
                    BigDecimal pre1IlId = (BigDecimal) map.get("PRE1IL_ID");

                    if(paydasNo != null)
                        denetimPaydasDTO.setPaydasNo(paydasNo.longValue());
                    if(adi != null)
                        denetimPaydasDTO.setAdi(adi);
                    if(soyAdi != null)
                        denetimPaydasDTO.setSoyAdi(soyAdi);
                    if(telNo != null)
                        denetimPaydasDTO.setTelefon(telNo);
                    if(kayitDurumu != null)
                        denetimPaydasDTO.setKayitDurumu(kayitDurumu);
                    if(unvan != null) // bUNU SOR? paydasTuru != null && paydasTuru.equalsIgnoreCase("S") &&
                        denetimPaydasDTO.setUnvan(unvan);
                    if(paydasTuru != null)
                        denetimPaydasDTO.setPaydasTuru(paydasTuru);
                    if(paydasTuru != null && paydasTuru.equalsIgnoreCase("K")) {
                        if(vergiNo != null)
                            denetimPaydasDTO.setVergiNo(vergiNo);
                        if(izahat != null)
                            denetimPaydasDTO.setIzahat(izahat);
                        if(tabelaAdi != null)
                            denetimPaydasDTO.setTabelaAdi(tabelaAdi);
                    }
                    //adres
                    if(binaAdi != null)
                        denetimPaydasDTO.setBinaAdi(binaAdi);
                    if(blokNo != null)
                        denetimPaydasDTO.setBlokNo(blokNo);
                    if(kapiNo != null)
                        denetimPaydasDTO.setKapiNo(kapiNo);
                    if(ilceAdi != null)
                        denetimPaydasDTO.setIlceAdi(ilceAdi);
                    if(kapiNoHarf != null)
                        denetimPaydasDTO.setKapiNoHarf(kapiNoHarf);
                    if(daireNoHarf != null)
                        denetimPaydasDTO.setDaireNoHarf(daireNoHarf);
                    if(katHarf != null)
                        denetimPaydasDTO.setKatHarf(katHarf);
                    if(kapinoSayi != null)
                        denetimPaydasDTO.setKapiNoSayi(kapinoSayi.longValue());
                    if(daireNoSayi != null)
                        denetimPaydasDTO.setDaireNoSayi(daireNoSayi.longValue());
                    if(katSayi != null)
                        denetimPaydasDTO.setKatSayi(katSayi.longValue());
                    if(dre1MahalleId != null)
                        denetimPaydasDTO.setDre1MahalleId(dre1MahalleId.longValue());
                    if(sre1SokakId != null)
                        denetimPaydasDTO.setSre1SokakId(sre1SokakId.longValue());
                    if(rre1IlceId != null)
                        denetimPaydasDTO.setRre1IlceId(rre1IlceId.longValue());
                    if(pre1IlId != null)
                        denetimPaydasDTO.setPre1IlId(pre1IlId.longValue());
                    if(tcKimlikNo != null)
                        denetimPaydasDTO.setTcKimlikNo(tcKimlikNo.longValue());

                    denetimPaydasResponseDTO.getResponseDenetimPaydasList().add(denetimPaydasDTO);
                }

            }

            errorDTO.setErrorMessage(null);
            errorDTO.setError(false);
            denetimPaydasResponseDTO.setErrorDTO(errorDTO);


        } catch (Exception e) {


            errorDTO.setErrorMessage("Bir hata meydana geldi.");
            errorDTO.setError(true);
            denetimPaydasResponseDTO.setErrorDTO(errorDTO);
            //paydasSorguResponseDTO.getPaydasSorguResponse().add(paydasSorguDTO);
        }

        return denetimPaydasResponseDTO;
    }
}


