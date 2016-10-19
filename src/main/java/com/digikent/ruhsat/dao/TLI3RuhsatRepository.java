package com.digikent.ruhsat.dao;

import com.digikent.ruhsat.dto.TLI3RuhsatDTO;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Serkan on 10/14/16.
 */
@Repository
public class TLI3RuhsatRepository {
    private final Logger LOG = LoggerFactory.getLogger(TLI3RuhsatRepository.class);


    @Autowired
    SessionFactory sessionFactory;

    public List<TLI3RuhsatDTO> get(TLI3RuhsatDTO tli3RuhsatDTO) {

        String sql = "select R.ID,(r.YILI||'/'|| r.RUHSATID) RUHSATNUMARASI,b.raporadi as ADSOYAD,ISYERIUNVANI," +
                "m.TANIM||' '||(decode (( select nvl(dre1mahalle.turu,'M') from dre1mahalle where id=m.id),'K','KÖYÜ','M','MAH.' )) as adres1,s.TANIM|| DECODE(r.DAIRENO,null,' ',' ')||DOSYA.BINAADI||' NO:'||r.KAPINO|| DECODE(r.DAIRENO,null,' ','/')||r.DAIRENO||' '||" +
                "(select TANIM from RRE1ILCE where id=m.RRE1ILCE_ID)||'/'||" +
                "(select TANIM from PRE1IL where id=(select PRE1IL_ID from RRE1ILCE where id=m.RRE1ILCE_ID))as adres2," +
                "r.KULLANIMALANI," +
                "dosya.DOSYAREFERANSNO," +
                "(select F_UPPER(TANIM) from ISM2FALIYET where id=r.ISM2FALIYET_ID) KISALTMA ," +
                "(LI1.F_RUHSAT_FALIYET_GETIR(r.id)) rhs_faliyet_adi," +
                "(select TANIM from GLI1FALIYET where id=r.GLI1FALIYET_ISYERI) rhs_faliyet_isyeri_adi," +
                "r.ACILISSAATI,r.KAPANISSAATI,R.MPI1PAYDAS_ID," +
                "SM2.F_PARAMETRE('RUHSATYONETIMI','RUHSATBASKAN_YARDIMCISI') RUHSATBASKANYARDIMCISI," +
                "SM2.F_PARAMETRE('RUHSATYONETIMI','RUHSATMUDURU') RUHSATMUDURU," +
                "(SELECT ADISOYADI FROM IHR1PERSONEL I WHERE I.ID = R.IHR1PERSONEL_MEMUR) MEMUR," +
                "r.IZAHAT" +
                ",t.KAYITOZELISMI AS RUHSATTURU" +
                ",nvl(decode(r.isyerisinifi,'Y','','L','LUKS SINIF ','1','1. SINIF ','2','2. SINIF ','3','3. SINIF ',r.isyerisinifi),' ') as isyerisinifi2, nvl(dosya.PAFTANO,'-') as PAFTANO ,nvl(dosya.ADANO,'-') as ADANO,nvl(dosya.PARSELNO,'-') as PARSELNO" +
                ",(select adi||' '|| soyadi from ihr1personel where id=(select SM2.F_Parametre('RUHSATYONETIMI','RUHSATSEFI') from dual)) as SEF" +
                "  from TLI3RUHSAT r,MPI1PAYDAS b,DRE1MAHALLE m,SRE1SOKAK s,SLI1RUHSATTURU t,eli1ruhsatdosya dosya" +
                " where  r.MPI1PAYDAS_ID=b.ID" +
                " and r.DRE1MAHALLE_ID=m.ID" +
                " and r.SRE1SOKAK_ID=s.ID" +
                " and r.SLI1RUHSATTURU_ID=t.id " +
                "and r.ELI1RUHSATDOSYA_ID=dosya.ID(+) " +
                "and r.id=" + tli3RuhsatDTO.getId();

        List<Object> list = new ArrayList();

        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        List<TLI3RuhsatDTO> tli3RuhsatDTOList = new ArrayList();
        for(Object o : list){
            Map map = (Map)o;
            TLI3RuhsatDTO sy1TespitDTO = new TLI3RuhsatDTO();

            BigDecimal id = (BigDecimal)map.get("ID");
            BigDecimal mpi1PaydasId = (BigDecimal)map.get("MPI1PAYDAS_ID");
            BigDecimal yili = (BigDecimal)map.get("yili");
            String ruhsatNumarasi = (String)map.get("RUHSATNUMARASI");
            String isyeriUnvani = (String)map.get("ISYERIUNVANI");
            String adres1 = (String)map.get("ADRES1");
            String adres2 = (String)map.get("ADRES2");
            String adaNo = (String)map.get("ADANO");
            String paftaNo = (String)map.get("PAFTANO");
            String parselNo = (String)map.get("PARSELNO");
            BigDecimal kullanimAlani = (BigDecimal)map.get("KULLANIMALANI");
            BigDecimal dosyaReferansNo = (BigDecimal)map.get("DOSYAREFERANSNO");
            String acilisSaati = (String)map.get("ACILISSAATI");
            String kapanisSaati = (String)map.get("KAPANISSAATI");
            String izahat = (String)map.get("IZAHAT");
            String kisaltma = (String)map.get("KISALTMA");
            String memur = (String)map.get("MEMUR");
            String ruhsatMuduru = (String)map.get("RUHSATMUDURU");
            String sef = (String)map.get("SEF");
            String ruhsatbaskanyardimcisi = (String)map.get("RUHSATBASKANYARDIMCISI");
            String ruhsatTuru = (String)map.get("RUHSATTURU");

            if(id != null)
                tli3RuhsatDTO.setId(id.longValue());
            if(mpi1PaydasId != null)
                tli3RuhsatDTO.setMpi1PaydasId(mpi1PaydasId.longValue());
            if(yili != null)
                tli3RuhsatDTO.setYili(yili.longValue());
            if(ruhsatNumarasi != null)
                tli3RuhsatDTO.setRuhsatNumarasi(ruhsatNumarasi);
            if(isyeriUnvani != null)
                tli3RuhsatDTO.setIsyeriUnvani(isyeriUnvani);
            if(adaNo != null)
                tli3RuhsatDTO.setAdaNo(adaNo);
            if(paftaNo != null)
                tli3RuhsatDTO.setPaftaNo(paftaNo);
            if(parselNo != null)
                tli3RuhsatDTO.setParselNo(parselNo);
            if(kullanimAlani != null)
                tli3RuhsatDTO.setKullanimAlani(kullanimAlani.longValue());
            if(dosyaReferansNo != null)
                tli3RuhsatDTO.setDosyaReferansNo(dosyaReferansNo.longValue());
            if(acilisSaati != null)
                tli3RuhsatDTO.setAcilisSaati(acilisSaati);
            if(kapanisSaati != null)
                tli3RuhsatDTO.setKapanisSaati(kapanisSaati);
            if(izahat != null)
                tli3RuhsatDTO.setIzahat(izahat);
            if(kisaltma != null)
                tli3RuhsatDTO.setFaaliyetKisaltma(kisaltma);
            if(memur != null)
                tli3RuhsatDTO.setMemur(memur);
            if(ruhsatMuduru != null)
                tli3RuhsatDTO.setRuhsatMuduru(ruhsatMuduru);
            if(sef != null)
                tli3RuhsatDTO.setSef(sef);
            if(ruhsatbaskanyardimcisi != null)
                tli3RuhsatDTO.setRuhsatBaskanYardimcisi(ruhsatbaskanyardimcisi);
            if(ruhsatTuru != null)
                tli3RuhsatDTO.setRuhsatTuru(ruhsatTuru);
            tli3RuhsatDTO.setAdres(adres1 + " " + adres2);


            tli3RuhsatDTOList.add(tli3RuhsatDTO);
        }

        return tli3RuhsatDTOList;
    }
}
