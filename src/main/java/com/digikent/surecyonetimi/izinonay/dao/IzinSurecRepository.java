package com.digikent.surecyonetimi.izinonay.dao;

import com.digikent.config.Constants;
import com.digikent.general.entity.TSM2Params;
import com.digikent.general.util.ErrorCode;
import com.digikent.surecyonetimi.izinonay.dto.IzinSurecDetayDTO;
import com.digikent.surecyonetimi.izinonay.entity.HHR1IzinTalebi;
import com.digikent.surecyonetimi.izinonay.entity.HHR1IzinTalebiLine;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by Kadir on 18.06.2018.
 */
@Repository
public class IzinSurecRepository {

    private final Logger LOG = LoggerFactory.getLogger(IzinSurecRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public IzinSurecDetayDTO getIzinSurecDTOByInstanceId(Long instanceId) {

        IzinSurecDetayDTO izinSurecDetayDTO = null;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(HHR1IzinTalebi.class);
        criteria.add(Restrictions.eq("vbpmProcessInstanceId", instanceId));
        List list =  criteria.list();

        if(!list.isEmpty()) {
                HHR1IzinTalebi hhr1IzinTalebi = (HHR1IzinTalebi) list.get(0);

                BigDecimal id = BigDecimal.valueOf(hhr1IzinTalebi.getID());
                String adSoyad = (String) hhr1IzinTalebi.getIhr1Personel().getAdi() + " " + hhr1IzinTalebi.getIhr1Personel().getSoyadi();
                String statu = (String) Constants.IHR1PERSONEL_STATU.get(hhr1IzinTalebi.getIhr1Personel().getTuru());
                String gorev = (String) hhr1IzinTalebi.getIhr1Personel().getLhr1GorevTuru().getTanim();
                BigDecimal cepTelefonu = BigDecimal.valueOf(hhr1IzinTalebi.getCepTelefonu());
                String mailAdresi = (String) hhr1IzinTalebi.getIhr1Personel().getElektronikPosta();
                Date baslamaTarihi = (Date) hhr1IzinTalebi.getBaslamaTarihi();
                Date izinBaslamaSaat = (Date) hhr1IzinTalebi.getIzinBaslamaSaat();
                Date bitisTarihi = (Date) hhr1IzinTalebi.getBitisTarihi();
                Date izinBitisSaat = (Date) hhr1IzinTalebi.getIzinBitisSaat();
                String vekilAdSoyad = (String) hhr1IzinTalebi.getIhr1PersonelYerineBakacak().getAdi() + " " + hhr1IzinTalebi.getIhr1PersonelYerineBakacak().getSoyadi();
                String izinAdres = (String) hhr1IzinTalebi.getIzinAdres();
                String mudurluk = (String) hhr1IzinTalebi.getBsm2Servis().getTanim();
                String izinTuru = (String) hhr1IzinTalebi.getYhr1IzinTuru().getTanim();

                izinSurecDetayDTO = new IzinSurecDetayDTO();

                if (id != null)
                    izinSurecDetayDTO.setId(id.longValue());

                if (instanceId != null)
                    izinSurecDetayDTO.setIzinNumarasi(instanceId.longValue());

                if (statu != null)
                    izinSurecDetayDTO.setStatu(statu);

                if (adSoyad != null)
                    izinSurecDetayDTO.setAdSoyad(adSoyad);

                if (gorev != null)
                    izinSurecDetayDTO.setGorevi(gorev);

                if (cepTelefonu != null)
                    izinSurecDetayDTO.setCepTelefonu(String.valueOf(cepTelefonu.longValue()));

                if (mailAdresi != null)
                    izinSurecDetayDTO.setMailAdresi(mailAdresi);

                if (baslamaTarihi != null)
                    izinSurecDetayDTO.setIzinBaslamaTarihi(dateFormat.format(baslamaTarihi));

                if (izinBaslamaSaat != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(izinBaslamaSaat);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    int minute = cal.get(Calendar.MINUTE);
                    izinSurecDetayDTO.setIzinBaslamaSaat(hour + ":" + (minute == 0 ? "00" : minute));
                }


                if (bitisTarihi != null)
                    izinSurecDetayDTO.setIzinBitisTarihi(dateFormat.format(bitisTarihi));

                if (izinBitisSaat != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(izinBitisSaat);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    int minute = cal.get(Calendar.MINUTE);
                    izinSurecDetayDTO.setIzinBitisSaat(hour + ":" + (minute == 0 ? "00" : minute));
                }

                if (vekilAdSoyad != null)
                    izinSurecDetayDTO.setVekilAdSoyad(vekilAdSoyad);

                if (izinAdres != null)
                    izinSurecDetayDTO.setIzinAdres(izinAdres);

                if (mudurluk != null)
                    izinSurecDetayDTO.setMudurluk(mudurluk);

                if (izinTuru != null)
                    izinSurecDetayDTO.setIzinTuru(izinTuru);

                izinSurecDetayDTO.setIzinSuresi(getIzinSuresiByIzinTalebiId(izinSurecDetayDTO.getId()));
        }

        return izinSurecDetayDTO;
    }

    private Long getKullanilacakToplamIzinSuresiByIzinTalebiId(Long id) {

        Long kullanilacakToplamIzinSuresiDakika = 0L;
        List<HHR1IzinTalebiLine> hhr1IzinTalebiLineList = new ArrayList<>();

        try {
            Session session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(HHR1IzinTalebiLine.class);
            criteria.add(Restrictions.eq("hhr1IzinTalebiId", id));
            hhr1IzinTalebiLineList = criteria.list();

            if(!hhr1IzinTalebiLineList.isEmpty()) {
                Long kullanilacakToplamGun = hhr1IzinTalebiLineList.stream().map(HHR1IzinTalebiLine::getKullanilacakGun).mapToLong(Long::longValue).sum();
                Long kullanilacakToplamSaat = hhr1IzinTalebiLineList.stream().map(HHR1IzinTalebiLine::getKullanilacakSaat).mapToLong(Long::longValue).sum();
                Long kullanilacakToplamDakika = hhr1IzinTalebiLineList.stream().map(HHR1IzinTalebiLine::getKullanilacakDakika).mapToLong(Long::longValue).sum();
                kullanilacakToplamIzinSuresiDakika = kullanilacakToplamGun * 8 * 60 + kullanilacakToplamSaat * 60 + kullanilacakToplamDakika;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.debug("Kullanilacak izin süresi getirilirken bir hata ile karsilasildi hhr1IzinTalebiId : " + id);
            return kullanilacakToplamIzinSuresiDakika;
        }
        return kullanilacakToplamIzinSuresiDakika;
    }

    public String calculateTheKullanilacakIzinSuresiByDakika(Long toplamIzinSuresiDakika) {

        String izinSuresiText = "";
        if(toplamIzinSuresiDakika != null) {

            Long toplamGun = toplamIzinSuresiDakika / (8 * 60);
            izinSuresiText = toplamGun.equals(0L) ? "" : toplamGun + " Gun ";

            Long toplamSaat = ( toplamIzinSuresiDakika % (8 * 60) ) / 60;
            izinSuresiText += toplamSaat.equals(0L) ? "" : toplamSaat + " Saat ";

            Long toplamDakika = (toplamIzinSuresiDakika % (8 * 60) ) % 60;
            izinSuresiText += toplamDakika.equals(0L) ? "" : toplamDakika + " Dakika ";
        }

        return izinSuresiText;
    }

    private String getIzinSuresiByIzinTalebiId(Long id) {

        String izinSuresiText = "";
        Long toplamIzinSuresiDakika= getKullanilacakToplamIzinSuresiByIzinTalebiId(id);
        izinSuresiText = calculateTheKullanilacakIzinSuresiByDakika(toplamIzinSuresiDakika);

        return izinSuresiText;
    }

    public Map getIzinSurecParameters() throws Exception {
        Map<String, String> paramDict = new HashMap<>();

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TSM2Params.class)
                .createAlias("rsm2ParamGroup", "r")
                .createAlias("ssm2ParamName", "s")
                .add(Restrictions.eq("r.kodu", "ISSURECLERI"))
                .add(Restrictions.in("s.kodu", Constants.BPM_IZIN_SUREC));

        List list = criteria.list();
        if(!list.isEmpty()) {
            for(int i = 0; i < list.size(); i++) {
                TSM2Params tsm2Params = (TSM2Params) list.get(i);
                String tanim = tsm2Params.getSsm2ParamName().getKodu();
                String kodu = tsm2Params.getParamValue();
                if(tanim.equals(null) || kodu.equals(null)) {
                    LOG.info(ErrorCode.ERROR_CODE_705);
                    throw new Exception("Izin surecleri parametrelerini getir: Parametre degeri null");
                }
                paramDict.put(tanim, kodu);
            }
        }

        return paramDict;
    }



}
