package com.digikent.surecyonetimi.izinonay.dao;

import com.digikent.surecyonetimi.izinonay.dto.IzinSurecDetayDTO;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

        String sql = "select A.ID,A.VBPMPROCESSINSTANCE_ID,\n" +
                "(select ADI||' '||SOYADI from IHR1PERSONEL where ID= (select IHR1PERSONEL_ID from HHR1IZINTALEBI where ID=A.ID)) AS ADSOYAD,\n" +
                "(select DECODE( TURU,'S','Sözlesmeli','M','Memur','I','Isci','G','Geçici','F','Firma','L','Meclis','O','Stajyer','C','Geçici Memur','D','Diğer') from IHR1PERSONEL where id= (select IHR1PERSONEL_ID from HHR1IZINTALEBI where id=A.ID)) AS STATU,\n" +
                "(select TANIM from LHR1GOREVTURU where id =(select LHR1GOREVTURU_ID from IHR1PERSONEL where id= (select IHR1PERSONEL_ID from HHR1IZINTALEBI where id=A.ID))) AS GOREV,\n" +
                "(select CEPTELEFONU from HHR1IZINTALEBI where id=A.ID) AS CEPTELEFONU,\n" +
                "(select ELEKTRONIKPOSTA from IHR1PERSONEL where id= (select IHR1PERSONEL_ID from  HHR1IZINTALEBI where id=A.ID)) AS MAILADRESI,\n" +
                "(select  BASLAMATARIHI from HHR1IZINTALEBI where  id=A.ID) AS BASLAMATARIHI,\n" +
                "(select  IZINBASLAMASAAT from HHR1IZINTALEBI where id= A.ID) AS IZINBASLAMASAAT,\n" +
                "(select  BITISTARIHI from HHR1IZINTALEBI where id=A.ID) AS BITISTARIHI,\n" +
                "(select  DONUSTARIHI from HHR1IZINTALEBI where id=A.ID) AS DONUSTARIHI,\n" +
                "(select  IZINBITISSAAT from HHR1IZINTALEBI where id=A.ID) AS IZINBITISSAAT,\n" +
                "(select ADI||' '||SOYADI from IHR1PERSONEL where id= (select IHR1PERSONELYERINEBAKACAK_ID from HHR1IZINTALEBI where id=A.ID )) AS VEKILADSOYAD,\n" +
                "(select IZINADRES from HHR1IZINTALEBI where id=A.ID) AS IZINADRES,\n" +
                "(select TANIM from BSM2SERVIS where id=  (select BSM2SERVIS_GOREV from HHR1IZINTALEBI where id=A.ID)) AS MUDURLUK,\n" +
                "(select TANIM from YHR1IZINTURU where id= (select YHR1IZINTURU_ID from HHR1IZINTALEBI where id=A.ID )) AS IZINTURU \n" +
                "from HHR1IZINTALEBI A where VBPMPROCESSINSTANCE_ID=" + instanceId;

        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        list = query.list();

        for(Object o : list){
            Map map = (Map) o;
            BigDecimal id = (BigDecimal) map.get("ID");
            String adSoyad = (String) map.get("ADSOYAD");
            String statu = (String) map.get("STATU");
            String gorev = (String) map.get("GOREV");
            BigDecimal cepTelefonu = (BigDecimal) map.get("CEPTELEFONU");
            String mailAdresi = (String) map.get("MAILADRESI");
            Date baslamaTarihi = (Date) map.get("BASLAMATARIHI");
            Date izinBaslamaSaat = (Date) map.get("IZINBASLAMASAAT");
            Date bitisTarihi = (Date) map.get("BITISTARIHI");
            Date izinBitisSaat = (Date) map.get("IZINBITISSAAT");
            String vekilAdSoyad = (String) map.get("VEKILADSOYAD");
            String izinAdres = (String) map.get("IZINADRES");
            String mudurluk = (String) map.get("MUDURLUK");
            String izinTuru = (String) map.get("IZINTURU");

            izinSurecDetayDTO = new IzinSurecDetayDTO();

            if(id != null)
                izinSurecDetayDTO.setId(id.longValue());

            if(instanceId != null)
                izinSurecDetayDTO.setIzinNumarasi(instanceId.longValue());

            if(statu != null)
                izinSurecDetayDTO.setStatu(statu);

            if(adSoyad != null)
                izinSurecDetayDTO.setAdSoyad(adSoyad);

            if(gorev != null)
                izinSurecDetayDTO.setGorevi(gorev);

            if(cepTelefonu != null)
                izinSurecDetayDTO.setCepTelefonu(String.valueOf(cepTelefonu.longValue()));

            if(mailAdresi != null)
                izinSurecDetayDTO.setMailAdresi(mailAdresi);

            if(baslamaTarihi != null)
                izinSurecDetayDTO.setIzinBaslamaTarihi(dateFormat.format(baslamaTarihi));

            if(izinBaslamaSaat != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(izinBaslamaSaat);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                izinSurecDetayDTO.setIzinBaslamaSaat(hour + ":" + (minute==0 ? "00" : minute));
            }


            if(bitisTarihi != null)
                izinSurecDetayDTO.setIzinBitisTarihi(dateFormat.format(bitisTarihi));

            if(izinBitisSaat != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(izinBitisSaat);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                izinSurecDetayDTO.setIzinBitisSaat(hour + ":" + (minute==0 ? "00" : minute));
            }

            if(vekilAdSoyad != null)
                izinSurecDetayDTO.setVekilAdSoyad(vekilAdSoyad);

            if(izinAdres != null)
                izinSurecDetayDTO.setIzinAdres(izinAdres);

            if(mudurluk != null)
                izinSurecDetayDTO.setMudurluk(mudurluk);

            if(izinTuru != null)
                izinSurecDetayDTO.setIzinTuru(izinTuru);

            izinSurecDetayDTO.setIzinSuresi(getIzinSuresiByIzinTalebiId(izinSurecDetayDTO.getId()));

        }

        return izinSurecDetayDTO;
    }

    private String getIzinSuresiByIzinTalebiId(Long id) {

        String izinSuresiValue = "";
        Long eklenecekGun = 0l;
        Long toplamSaat = 0l;
        Long toplamGun = 0l;

        String sql = "SELECT KULLANILACAKGUN,KULLANILACAKSAAT FROM HHR1IZINTALEBILINE WHERE HHR1IZINTALEBI_ID="+id;

        List<Object> list = new ArrayList<Object>();
        Session session = sessionFactory.withOptions().interceptor(null).openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        list = query.list();

        for(Object o : list){
            Map map = (Map) o;
            BigDecimal kullanilacakGun = (BigDecimal) map.get("KULLANILACAKGUN");
            BigDecimal kullanilacakSaat = (BigDecimal) map.get("KULLANILACAKSAAT");

            if(kullanilacakGun != null && kullanilacakSaat != null){
                toplamGun = toplamGun + kullanilacakGun.longValue();
                toplamSaat = toplamSaat + kullanilacakSaat.longValue();
            }
        }

        if (toplamSaat != 0) {
            eklenecekGun = toplamSaat.longValue()/8;
            toplamGun = toplamGun + eklenecekGun;
            if (toplamSaat % 8 == 0) {
                izinSuresiValue = toplamGun + " Gün";
            } else if (toplamSaat % 8 != 0) {
                if (toplamGun == 0) {
                    izinSuresiValue = toplamSaat + " Saat";
                } else {
                    izinSuresiValue = toplamGun + " Gün " + (toplamSaat % 8) + " Saat";
                }
            }
        } else if (toplamSaat == 0) {
            izinSuresiValue = toplamGun + " Gün";
        }

        return izinSuresiValue;
    }


}
