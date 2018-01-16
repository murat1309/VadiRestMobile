package com.digikent.paydasiliskileri.dao;

import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.paydasiliskileri.dto.*;
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

    public PaydasSorguResponseDTO getPaydasInfoByCriteria(PaydasSorguRequestDTO paydasSorguRequestDTO, String sql) {

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
                        if(paydasTuru.equalsIgnoreCase("S")) {
                            paydasSorguDTO.setPaydasTuru("Şahıs");
                        } else {
                            paydasSorguDTO.setPaydasTuru("Kurum");
                        }
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

    public PaydasSorguResponseDTO getPaydasDebtInfoByCriteria(PaydasSorguResponseDTO paydasSorguResponseDTO, PaydasSorguRequestDTO paydasSorguRequestDTO, String sql) {

        
        List<PaydasBorcSorguDTO> paydasBorcSorguList = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO();

        try {

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

    public PaydasSorguResponseDTO getPaydasAdvertInfoByCriteria(PaydasSorguResponseDTO paydasSorguResponseDTO, PaydasSorguRequestDTO paydasSorguRequestDTO, String sql) {

        List<PaydasIlanSorguDTO> paydasIlanSorguList = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO();

        try {

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
}


