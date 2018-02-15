package com.digikent.ruhsat.service;

import com.digikent.ruhsat.dto.*;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Kadir on 13/10/17.
 */
@Service
public class TLI3RuhsatService {

    @Autowired
    SessionFactory sessionFactory;

    public String getruhsatCommonSQL() {
        return "select R.ID,r.RUHSATID,b.raporadi as ADSOYAD,ISYERIUNVANI," +
                "m.TANIM||' '||(decode (( select nvl(dre1mahalle.turu,'M') from dre1mahalle where id=m.id),'K','KÖYÜ','M','MAH.' )) as adres1,s.TANIM|| DECODE(r.DAIRENO,null,' ',' ')||DOSYA.BINAADI||' NO:'||r.KAPINO|| DECODE(r.DAIRENO,null,' ','/')||r.DAIRENO||' '||" +
                "(select TANIM from RRE1ILCE where id=m.RRE1ILCE_ID)||'/'||" +
                "(select TANIM from PRE1IL where id=(select PRE1IL_ID from RRE1ILCE where id=m.RRE1ILCE_ID))as adres2," +
                "r.KULLANIMALANI," +
                "dosya.DOSYAREFERANSNO," +
                "r.YILI, " +
                "r.ELI1RUHSATDOSYA_ID as DOSYANUMARASI, " +
                "(SELECT TANIM FROM GLI1FALIYET WHERE ID=r.GLI1FALIYET_ISYERI) as ISYERIANAFAALIYET, " +
                "(SELECT TANIM FROM ALI1ISLEMDURUMU WHERE ID=r.ALI1ISLEMDURUMU_ID) as RUHSATDURUMU,    " +
                "(SELECT TANIM FROM ALI1ISYERIDURUMU WHERE ID=r.ALI1ISYERIDURUMU_ID) as ISYERIDURUMU,  " +
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
                "   AND r.SLI1RUHSATTURU_ID IN (SELECT B.SLI1RUHSATTURU_ID " +
                "                                     FROM OLI1RUHSATTIPI A, " +
                "                                          PLI1RUHSATTIPILINE B " +
                "                                    WHERE     A.ID = B.OLI1RUHSATTIPI_ID " +
                "                                          AND A.KAYITOZELISMI = 'ISYERIRUHSAT') " +
                " and r.ELI1RUHSATDOSYA_ID=dosya.ID(+) ";
    }

    public String getRuhsatSQLWithERE1YAPI() {
        return "select R.ID, r.RUHSATID,b.raporadi as ADSOYAD,ISYERIUNVANI," +
                "m.TANIM||' '||(decode (( select nvl(dre1mahalle.turu,'M') from dre1mahalle where id=m.id),'K','KÖYÜ','M','MAH.' )) as adres1,s.TANIM|| DECODE(r.DAIRENO,null,' ',' ')||DOSYA.BINAADI||' NO:'||r.KAPINO|| DECODE(r.DAIRENO,null,' ','/')||r.DAIRENO||' '||" +
                "(select TANIM from RRE1ILCE where id=m.RRE1ILCE_ID)||'/'||" +
                "(select TANIM from PRE1IL where id=(select PRE1IL_ID from RRE1ILCE where id=m.RRE1ILCE_ID))as adres2," +
                "r.KULLANIMALANI," +
                "dosya.DOSYAREFERANSNO," +
                "y.ID as ERE1YAPI_ID," +
                "r.YILI, " +
                "r.ELI1RUHSATDOSYA_ID as DOSYANUMARASI, " +
                "(SELECT TANIM FROM GLI1FALIYET WHERE ID=r.GLI1FALIYET_ISYERI) as ISYERIANAFAALIYET, " +
                "(SELECT TANIM FROM ALI1ISLEMDURUMU WHERE ID=r.ALI1ISLEMDURUMU_ID) as RUHSATDURUMU,    " +
                "(SELECT TANIM FROM ALI1ISYERIDURUMU WHERE ID=r.ALI1ISYERIDURUMU_ID) as ISYERIDURUMU,  " +
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
                "  from TLI3RUHSAT r,MPI1PAYDAS b,DRE1MAHALLE m,SRE1SOKAK s,SLI1RUHSATTURU t,eli1ruhsatdosya dosya, ERE1YAPI y" +
                " where  r.MPI1PAYDAS_ID=b.ID" +
                " and r.DRE1MAHALLE_ID=m.ID" +
                " and r.SRE1SOKAK_ID=s.ID" +
                " and r.SLI1RUHSATTURU_ID=t.id " +
                "   AND SLI1RUHSATTURU_ID IN (SELECT B.SLI1RUHSATTURU_ID " +
                "                                     FROM OLI1RUHSATTIPI A, " +
                "                                          PLI1RUHSATTIPILINE B " +
                "                                    WHERE     A.ID = B.OLI1RUHSATTIPI_ID " +
                "                                          AND A.KAYITOZELISMI = 'ISYERIRUHSAT') " +
                "and r.ELI1RUHSATDOSYA_ID=dosya.ID(+) " +
                "and r.sre1sokak_id=y.SRE1SOKAK_ID " +
                "and y.DRE1MAHALLE_ID=m.id ";
    }

    public String getRuhsatDurumuSQL() {
        String sql = "SELECT ELI1RUHSATDOSYA.ID,\n" +
                "       ELI1RUHSATDOSYA.EIN1GELIRGRUBU_ID,\n" +
                "       ELI1RUHSATDOSYA.DDM1ISAKISI_ID,\n" +
                "       ELI1RUHSATDOSYA.SLI1RUHSATTURU_ID,\n" +
                "       ELI1RUHSATDOSYA.DOSYAACILISTURU,\n" +
                "       ELI1RUHSATDOSYA.YILI,\n" +
                "       ELI1RUHSATDOSYA.DOSYAREFERANSNO,\n" +
                "       ELI1RUHSATDOSYA.KAYITTARIHI,\n" +
                "       ELI1RUHSATDOSYA.MPI1PAYDAS_ID,\n" +
                "       ELI1RUHSATDOSYA.IRE1PARSEL_ID,\n" +
                "       ELI1RUHSATDOSYA.DRE1BAGBOLUM_ID,\n" +
                "       ELI1RUHSATDOSYA.DRE1MAHALLE_ID,\n" +
                "       ELI1RUHSATDOSYA.SRE1SOKAK_ID,\n" +
                "       ELI1RUHSATDOSYA.KAPINO,\n" +
                "       ELI1RUHSATDOSYA.DAIRENO,\n" +
                "       ELI1RUHSATDOSYA.PAFTANO,\n" +
                "       ELI1RUHSATDOSYA.ADANO,\n" +
                "       ELI1RUHSATDOSYA.PARSELNO,\n" +
                "       ELI1RUHSATDOSYA.KAYITIPTALDURUMU,\n" +
                "       ELI1RUHSATDOSYA.IPTALEDILMENEDENI,\n" +
                "       ELI1RUHSATDOSYA.IZAHAT,\n" +
                "       ELI1RUHSATDOSYA.RE1ARSIVID,\n" +
                "       ELI1RUHSATDOSYA.ERE1YAPI_ID,\n" +
                "       ELI1RUHSATDOSYA.KAYITNUMARASI,\n" +
                "       ELI1RUHSATDOSYA.IHR1PERSONEL_ID,\n" +
                "       ELI1RUHSATDOSYA.PROJETARIHI,\n" +
                "       ELI1RUHSATDOSYA.PROJENUMARASI,\n" +
                "       ELI1RUHSATDOSYA.ISM2FALIYET_ID,\n" +
                "       ELI1RUHSATDOSYA.ARSIVNUMARASI,\n" +
                "       ELI1RUHSATDOSYA.ARSIVADISOYADI,\n" +
                "       ELI1RUHSATDOSYA.ONAYLAMAONAYI,\n" +
                "       ELI1RUHSATDOSYA.ONAYLAMATARIHI,\n" +
                "       ELI1RUHSATDOSYA.DURUMU,\n" +
                "       ELI1RUHSATDOSYA.BSM2SERVIS_ID,\n" +
                "       ELI1RUHSATDOSYA.SM2TUTANAK_ID,\n" +
                "       ELI1RUHSATDOSYA.BASVURUSEKLI,\n" +
                "       ELI1RUHSATDOSYA.RRE1SITE_ID,\n" +
                "       ELI1RUHSATDOSYA.DOSYAYOLU,\n" +
                "       ELI1RUHSATDOSYA.UPDUSER,\n" +
                "       ELI1RUHSATDOSYA.UPDDATE,\n" +
                "       ELI1RUHSATDOSYA.DELETEFLAG,\n" +
                "       ELI1RUHSATDOSYA.CRUSER,\n" +
                "       ELI1RUHSATDOSYA.CRDATE,\n" +
                "       ELI1RUHSATDOSYA.UPDSEQ,\n" +
                "       ELI1RUHSATDOSYA.DRE1BAGBOLUM_DIGER,\n" +
                "       (SELECT TANIM\n" +
                "          FROM ALI1DOSYADURUMU\n" +
                "         WHERE ID = ELI1RUHSATDOSYA.ALI1DOSYADURUMU_ID) AS DOSYADURUMU,\n" +
                "       MPI1PAYDAS.YEVTELEFONU,\n" +
                "       MPI1PAYDAS.YISTELEFONU,\n" +
                "       MPI1PAYDAS.YCEPTELEFONU,\n" +
                "       MPI1PAYDAS.YFAKSTELEFONU,\n" +
                "       MPI1PAYDAS.SORGUADI,\n" +
                "       ELI1RUHSATDOSYA.GLI1FALIYET_ID,\n" +
                "       (SELECT TANIM\n" +
                "          FROM GLI1FALIYET\n" +
                "         WHERE ID = ELI1RUHSATDOSYA.GLI1FALIYET_ID),\n" +
                "       ELI1RUHSATDOSYA.ADRESGIRISIPARSELDENALSIN,\n" +
                "       ELI1RUHSATDOSYA.ARSANINALANI,\n" +
                "       ELI1RUHSATDOSYA.KULLANIMALANI,\n" +
                "       ELI1RUHSATDOSYA.BINAADI,\n" +
                "       ELI1RUHSATDOSYA.GLI1FALIYET_ISYERI,\n" +
                "       ELI1RUHSATDOSYA.TASINMAZSAHIBI,\n" +
                "       ELI1RUHSATDOSYA.ALI1BINAADI_ID,\n" +
                "       (SELECT TANIM\n" +
                "          FROM ALI1BINAADI\n" +
                "         WHERE id = ELI1RUHSATDOSYA.ALI1BINAADI_ID) AS KAPI,\n" +
                "       (SELECT TANIM\n" +
                "          FROM SLI1RUHSATTURU\n" +
                "         WHERE ID = ELI1RUHSATDOSYA.SLI1RUHSATTURU_ID) AS RUHSATTURU,\n" +
                "       MPI1PAYDAS.ADI || ' ' || MPI1PAYDAS.SOYADI AS ADISOYADI,\n" +
                "       (SELECT TANIM\n" +
                "          FROM DRE1MAHALLE\n" +
                "         WHERE ID = ELI1RUHSATDOSYA.DRE1MAHALLE_ID)  AS MAHALLEADI,\n" +
                "       (SELECT TANIM\n" +
                "          FROM SRE1SOKAK\n" +
                "         WHERE ID = ELI1RUHSATDOSYA.SRE1SOKAK_ID) AS SOKAKADI,\n" +
                "       (SELECT ADI || ' ' || SOYADI\n" +
                "          FROM IHR1PERSONEL\n" +
                "         WHERE ID = ELI1RUHSATDOSYA.IHR1PERSONEL_ID),\n" +
                "       (SELECT TANIM\n" +
                "          FROM ISM2FALIYET\n" +
                "         WHERE ID = ELI1RUHSATDOSYA.ISM2FALIYET_ID),\n" +
                "       (SELECT DAIRENO\n" +
                "          FROM DRE1BAGBOLUM\n" +
                "         WHERE ID = ELI1RUHSATDOSYA.DRE1BAGBOLUM_ID) AS DAIRENOBOLUM,\n" +
                "       (SELECT TANIM\n" +
                "          FROM GLI1FALIYET\n" +
                "         WHERE ID = ELI1RUHSATDOSYA.GLI1FALIYET_ISYERI) AS ISYERIANAFAALIYET,\n" +
                "       ELI1RUHSATDOSYA.ISYERISINIFI,\n" +
                "       ELI1RUHSATDOSYA.FAALIYETKULLANIMALANI,\n" +
                "       ELI1RUHSATDOSYA.TALIFAALIYETKULLANIMALANI,\n" +
                "       ELI1RUHSATDOSYA.SOZLESMEBITISTARIHI,\n" +
                "       ELI1RUHSATDOSYA.ALI1DOSYADURUMU_ID,\n" +
                "       ELI1RUHSATDOSYA.BLOKNUMARASI,\n" +
                "       TLI3RUHSAT.RUHSATTARIHI,\n" +
                "       TLI3RUHSAT.ISYERIUNVANI\n" +
                "       FROM ELI1RUHSATDOSYA, MPI1PAYDAS, TLI3RUHSAT\n" +
                "       WHERE ELI1RUHSATDOSYA.MPI1PAYDAS_ID = MPI1PAYDAS.ID\n" +
                "       AND ELI1RUHSATDOSYA.ID = TLI3RUHSAT.ELI1RUHSATDOSYA_ID\n" +
                "       AND ELI1RUHSATDOSYA.ID>0\n" +
                "       AND ELI1RUHSATDOSYA.EIN1GELIRGRUBU_ID = (SELECT ID FROM EIN1GELIRGRUBU WHERE TURU='IKTISAT')" ;
        return sql;
    }
    public List<TLI3RuhsatDTO> getRuhsatDTOListRunSQL(String additionSQL) {
        String sql = addWhereCondition(additionSQL);
        List<Object> objectList = runRuhsatSQL(sql);
        return convertRuhsatToRuhsatDTOList(objectList);
    }
    public String addConditionsToRuhsatDurum(String additionSql){
        return getRuhsatDurumuSQL() + additionSql;
    }
    public List<RuhsatDurumuDTO> getRuhsatBasvuruDTOList(TLI3RuhsatDTO tli3RuhsatDTO, String additionSql) {
        List<Object> list = new ArrayList<>();
        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(addConditionsToRuhsatDurum(additionSql));
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        List<RuhsatDurumuDTO> ruhsatDurumuDTOList = new ArrayList();
        for(Object o : list) {
            Map map = (Map) o;
            RuhsatDurumuDTO ruhsatDurumuDTO = new RuhsatDurumuDTO();

            BigDecimal dosyaNumarasi = (BigDecimal) map.get("DOSYAREFERANSNO");
            BigDecimal yili = (BigDecimal) map.get("YILI");
            BigDecimal kullanimAlani = (BigDecimal) map.get("KULLANIMALANI");
            String adiSoyadi = (String) map.get("ADISOYADI");
            String isyeriAnaFaaliyet = (String) map.get("ISYERIANAFAALIYET");
            String mahalle = (String) map.get("MAHALLEADI");
            String sokak = (String) map.get("SOKAKADI");
            String kapi = (String) map.get("KAPI");
            String daire = (String) map.get("DAIRENOBOLUM");
            String ruhsatTuru = (String) map.get("RUHSATTURU");
            String ruhsatDurumu = (String) map.get("DOSYADURUMU");

            if(tli3RuhsatDTO.getMpi1PaydasId() != null)
                ruhsatDurumuDTO.setPaydasId(tli3RuhsatDTO.getMpi1PaydasId().longValue());
            if(yili != null)
                ruhsatDurumuDTO.setYili(yili.longValue());
            if(kullanimAlani != null)
                ruhsatDurumuDTO.setKullanimAlani(kullanimAlani.longValue());
            if(dosyaNumarasi != null)
                ruhsatDurumuDTO.setDosyaNumarasi(dosyaNumarasi.longValue());
            if(adiSoyadi != null)
                ruhsatDurumuDTO.setAdiSoyadi(adiSoyadi);
            if(isyeriAnaFaaliyet != null)
                ruhsatDurumuDTO.setIsyeriAnaFaaliyet(isyeriAnaFaaliyet);
            if(mahalle != null)
                ruhsatDurumuDTO.setMahalle(mahalle);
            if(sokak != null)
                ruhsatDurumuDTO.setSokak(sokak);
            if(ruhsatTuru != null)
                ruhsatDurumuDTO.setRuhsatTuru(ruhsatTuru);
            if(kapi != null)
                ruhsatDurumuDTO.setKapi(kapi);
            if(daire != null)
                ruhsatDurumuDTO.setDaire(daire);
            if(ruhsatDurumu != null)
                ruhsatDurumuDTO.setRuhsatDurumu(ruhsatDurumu);

            ruhsatDurumuDTOList.add(ruhsatDurumuDTO);
        }
        return ruhsatDurumuDTOList;
    }

    public List<TLI3RuhsatDTO> getRuhsatDTOListWithERE1YAPI(String additionSQL) {
        String sql = addWhereConditionWithERE1YAPI(additionSQL);
        List<Object> objectList = runRuhsatSQL(sql);
        return convertRuhsatToRuhsatDTOList(objectList);
    }

    public String addWhereCondition(String additionSQL) {
        return getruhsatCommonSQL() + additionSQL;
    }

    public String addWhereConditionWithERE1YAPI(String additionSQL) {
        return getRuhsatSQLWithERE1YAPI() + additionSQL;
    }

    public List<Object> runRuhsatSQL(String sqlCommand) {
        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sqlCommand);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public List<TLI3RuhsatDTO> convertRuhsatToRuhsatDTOList(List<Object> list) {
        List<TLI3RuhsatDTO> tli3RuhsatDTOList = new ArrayList();
        for(Object o : list){
            Map map = (Map)o;
            TLI3RuhsatDTO tli3RuhsatDTO = new TLI3RuhsatDTO();

            BigDecimal id = (BigDecimal)map.get("ID");
            BigDecimal mpi1PaydasId = (BigDecimal)map.get("MPI1PAYDAS_ID");
            BigDecimal yili = (BigDecimal)map.get("YILI");
            String ruhsatNumarasi = ((BigDecimal)map.get("RUHSATID")).toString();
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
            BigDecimal dosyaNumarasi = (BigDecimal)map.get("DOSYANUMARASI");
            String isyeriAnaFaaliyet = (String)map.get("ISYERIANAFAALIYET");
            String ruhsatDurumu = (String)map.get("RUHSATDURUMU");
            String isyeriDurumu = (String)map.get("ISYERIDURUMU");

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
            if(dosyaNumarasi != null)
                tli3RuhsatDTO.setDosyaNumarasi(dosyaNumarasi.longValue());
            if(isyeriAnaFaaliyet != null)
                tli3RuhsatDTO.setIsyeriAnaFaaliyet(isyeriAnaFaaliyet);
            if(ruhsatDurumu != null)
                tli3RuhsatDTO.setRuhsatDurumu(ruhsatDurumu);
            if(isyeriDurumu != null)
                tli3RuhsatDTO.setIsyeriDurumu(isyeriDurumu);


            tli3RuhsatDTOList.add(tli3RuhsatDTO);
        }

        return tli3RuhsatDTOList;
    }

    public List<TLI3RuhsatTuruDTO> getRuhsatTuruList() {
        String sql = "select ID,TANIM from SLI1RUHSATTURU where ISACTIVE='E' order by TANIM asc";
        List<Object> objList = runRuhsatSQL(sql);
        return convertObjectToRuhsatTuruDTO(objList);
    }

    public List<TLI3RuhsatTuruDTO> convertObjectToRuhsatTuruDTO(List<Object> objList) {
        List<TLI3RuhsatTuruDTO> ruhsatTuruDTOList = new ArrayList<>();
        for (Object item: objList) {
            Map map = (Map)item;
            BigDecimal id = (BigDecimal)map.get("ID");
            String tanim = (String)map.get("TANIM");
            TLI3RuhsatTuruDTO tli3RuhsatTuruDTO =  new TLI3RuhsatTuruDTO();

            if(id != null)
                tli3RuhsatTuruDTO.setId(id.longValue());

            if(tanim != null)
                tli3RuhsatTuruDTO.setTanim(tanim);

            ruhsatTuruDTOList.add(tli3RuhsatTuruDTO);
        }
        return ruhsatTuruDTOList;
    }

    public List<TLI3RuhsatDTO> getRuhsatDTOListByRuhsatTuru(String additionSQL, TLI3RuhsatTuruRequestDTOList tli3RuhsatTuruRequestDTOList) {
        String sql = addWhereCondition(additionSQL);
        List<Long> turlist = seperateRuhsatTuruDTO(tli3RuhsatTuruRequestDTOList);
        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        query.setParameterList("turlist", turlist);
        List<Object> objects = query.list();

        return convertRuhsatToRuhsatDTOList(objects);
    }

    public List<Long> seperateRuhsatTuruDTO(TLI3RuhsatTuruRequestDTOList tli3RuhsatTuruRequestDTOList) {
        List<Long> longs = new ArrayList<>();
        for (TLI3RuhsatTuruRequestDTO item : tli3RuhsatTuruRequestDTOList.getTli3RuhsatTuruRequestDTOs()) {
            longs.add(item.getValue());
        }
        return longs;
    }

    public List<DRE1MahalleDTO> getMahalleList() {
        String sql = "select ID,TANIM||' '||decode(TURU,'K','KÖYÜ','M','MAH.') as TANIM from DRE1MAHALLE where RRE1ILCE_id =(select RRE1ILCE_id from nsm2parametre) and ISACTIVE='E' order by TANIM";
        List<Object> objList = runRuhsatSQL(sql);
        return convertObjectToDRE1MahalleDTO(objList);
    }

    public List<DRE1MahalleDTO> convertObjectToDRE1MahalleDTO(List<Object> objList) {
        List<DRE1MahalleDTO> mahalleDTOList = new ArrayList<>();
        for (Object item: objList) {
            Map map = (Map)item;
            BigDecimal id = (BigDecimal)map.get("ID");
            String tanim = (String)map.get("TANIM");
            DRE1MahalleDTO mahalleDTO =  new DRE1MahalleDTO();

            if(id != null)
                mahalleDTO.setId(id.longValue());

            if(tanim != null)
                mahalleDTO.setTanim(tanim);

            mahalleDTOList.add(mahalleDTO);
        }
        return mahalleDTOList;
    }

    public List<SRE1SokakDTO> getSokakByMahalleId(Long mahId) {
        String sql = " SELECT S.ID, S.TANIM " +
                " FROM DRE1MAHALLESOKAK MS , SRE1SOKAK S, DRE1MAHALLE M " +
                " WHERE MS.SRE1SOKAK_ID =S.ID  AND MS.DRE1MAHALLE_ID =M.ID " +
                " AND NVL(M.ISACTIVE,'E')='E' AND NVL(S.ISACTIVE,'E')='E'  AND M.ID IN (" + mahId + ")  ORDER BY S.TANIM ";
        List<Object> objList = runRuhsatSQL(sql);
        return convertObjectToSRE1SokakDTO(objList);
    }

    public List<SRE1SokakDTO> convertObjectToSRE1SokakDTO(List<Object> objList) {
        List<SRE1SokakDTO> sokakDTOList = new ArrayList<>();
        for (Object item: objList) {
            Map map = (Map)item;
            BigDecimal id = (BigDecimal)map.get("ID");
            String tanim = (String)map.get("TANIM");
            SRE1SokakDTO sokakDTO =  new SRE1SokakDTO();

            if(id != null)
                sokakDTO.setId(id.longValue());

            if(tanim != null)
                sokakDTO.setTanim(tanim);

            sokakDTOList.add(sokakDTO);
        }
        return sokakDTOList;
    }

    public List<ERE1YapiDTO> getBinaBySokakId(Long sokakId) {
        //String sql = "SELECT ERE1YAPI_ID,(SELECT BINAADI FROM ERE1YAPI WHERE ID=ERE1YAPI_ID) AS BINA FROM FRE1KAPITAHSIS WHERE ERE1YAPI_ID != 0 and SRE1SOKAK_ID=" + sokakId +" group by ERE1YAPI_ID order by BINA";
        String sql = "SELECT " +
                " ERE1YAPI.ID," +
                " ERE1YAPI.BINAADI " +
                " FROM FRE1KAPITAHSIS,ERE1YAPI, SRE1SOKAK " +
                " WHERE FRE1KAPITAHSIS.ERE1YAPI_ID = ERE1YAPI.ID " +
                " AND (FRE1KAPITAHSIS.SRE1SOKAK_ID = SRE1SOKAK.ID) " +
                " AND ERE1YAPI.ID > 0 " +
                " AND SRE1SOKAK.ID = " + sokakId;
        List<Object> objList = runRuhsatSQL(sql);
        return convertObjectToERE1YapiDTO(objList);
    }

    public List<ERE1YapiDTO> convertObjectToERE1YapiDTO(List<Object> objList) {
        List<ERE1YapiDTO> binaDTOList = new ArrayList<>();
        for (Object item: objList) {
            Map map = (Map)item;
            BigDecimal id = (BigDecimal)map.get("ID");
            String tanim = (String)map.get("BINAADI");
            ERE1YapiDTO binaDTO =  new ERE1YapiDTO();

            if(id != null)
                binaDTO.setId(id.longValue());

            if(tanim != null) {
                binaDTO.setTanim(tanim);
            } else {
                binaDTO.setTanim("");
            }


            binaDTOList.add(binaDTO);
        }
        return binaDTOList;
    }

}
