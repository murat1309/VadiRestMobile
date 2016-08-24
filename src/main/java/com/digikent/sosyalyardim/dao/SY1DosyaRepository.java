package com.digikent.sosyalyardim.dao;

import com.digikent.sosyalyardim.web.dto.SY1DosyaDTO;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Dosya;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Serkan on 4/1/2016.
 */
@Repository
public class SY1DosyaRepository {

    private final Logger LOG = LoggerFactory.getLogger(SY1DosyaRepository.class);

    @Autowired
    SessionFactory sessionFactory;


    public List<SY1DosyaDTO> list(){
        String sql ="select * from VSY1DOSYA";
        List<Object> list = new ArrayList();

        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        List<SY1DosyaDTO> sy1DosyaDTOList = new ArrayList();
        for(Object o : list){
            Map map = (Map)o;
            SY1DosyaDTO sy1DosyaDTO = new SY1DosyaDTO();

            String dosyaNumarasi = (String)map.get("DOSYANUMARASI");
            BigDecimal id = (BigDecimal)map.get("ID");
            BigDecimal tcKimlikNo = (BigDecimal)map.get("TCKIMLIKNO");
            String izahat =(String) map.get("IZAHAT");
            String sre1SokakAdi = (String) map.get("SRE1SOKAKADI");
            String sre1SokakCaddeAdi = (String) map.get("SRE1SOKAKCADDEADI");

            if(dosyaNumarasi != null)
                sy1DosyaDTO.setDosyanumarasi(dosyaNumarasi);
            if(id != null)
                sy1DosyaDTO.setId(id.longValue());
            if(tcKimlikNo != null)
                sy1DosyaDTO.setTckimlikno(tcKimlikNo.longValue());
            if(izahat != null)
                sy1DosyaDTO.setIzahat(izahat);
            if(sre1SokakAdi != null)
                sy1DosyaDTO.setSre1sokakAdi(sre1SokakAdi);
            if(sre1SokakCaddeAdi != null)
                sy1DosyaDTO.setSre1sokakCaddeadi(sre1SokakAdi);

            sy1DosyaDTOList.add(sy1DosyaDTO);
        }

        return sy1DosyaDTOList;
    }

    public List<SY1DosyaDTO> search(SY1Dosya sy1Dosya){

        String sqlQuery = "select d.ID,d.TANIM,d.IZAHAT, d.DRE1MAHALLE_ADI, d.SRE1SOKAK_CADDEADI, d.SRE1SOKAK_ADI," +
                          "d.KAPINO,d.DAIRENO,d.DOSYANUMARASI, mp.ADI, mp.SOYADI from VSY1DOSYA d,  mpi1paydas mp " +
                          " where  D.MPI1PAYDAS_ILGILI=MP.ID";


        if(sy1Dosya.getID() != null)
            sqlQuery += " and d.ID = " + sy1Dosya.getID();

        if(sy1Dosya.getTckimlikno() != null)
            sqlQuery += " and d.TCKIMLIKNO = " + sy1Dosya.getTckimlikno();

        if(sy1Dosya.getDosyanumarasi() != null && !sy1Dosya.getDosyanumarasi().isEmpty())
            sqlQuery += " and d.dosyanumarasi = '" + sy1Dosya.getDosyanumarasi() + "'";
        if(sy1Dosya.getMpi1paydas().getAdi() != null && !sy1Dosya.getMpi1paydas().getAdi().isEmpty())
            sqlQuery += " and mp.id in  (select id  from mpi1paydas where adi = '" + sy1Dosya.getMpi1paydas().getAdi() + "')";
        if(sy1Dosya.getMpi1paydas().getSoyadi() != null && !sy1Dosya.getMpi1paydas().getSoyadi().isEmpty())
            sqlQuery += " and mp.id in (select id from mpi1paydas where soyadi = '" + sy1Dosya.getMpi1paydas().getSoyadi() + "')";



        List<Object> list = new ArrayList();

        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
        LOG.debug("REST request to get sosyal yardim with sqlQuery : {}", sqlQuery);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        List<SY1DosyaDTO> sy1DosyaDTOList = new ArrayList();

        for(Object o : list){
            Map map = (Map)o;
            SY1DosyaDTO sy1DosyaDTO = new SY1DosyaDTO();

            String dosyaNumarasi = (String)map.get("DOSYANUMARASI");
            BigDecimal id = (BigDecimal)map.get("ID");
            BigDecimal tcKimlikNo = (BigDecimal)map.get("TCKIMLIKNO");
            String izahat =(String) map.get("IZAHAT");
            String sre1SokakAdi = (String) map.get("SRE1SOKAKADI");
            String sre1SokakCaddeAdi = (String) map.get("SRE1SOKAKCADDEADI");
            String tanim = (String)map.get("TANIM");
            String kapino = (String)map.get("KAPINO");
            String daireno = (String)map.get("DAIRENO");
            String dre1MahalleAdi = (String)map.get("DRE1MAHALLE_ADI");
            String adi = (String)map.get("ADI");
            String soyadi = (String)map.get("SOYADI");

            if(dosyaNumarasi != null)
                sy1DosyaDTO.setDosyanumarasi(dosyaNumarasi);
            if(id != null)
                sy1DosyaDTO.setId(id.longValue());
            if(tanim != null)
                sy1DosyaDTO.setTanim(tanim);
            if(kapino != null)
                sy1DosyaDTO.setKapino(kapino);
            if(daireno != null)
                sy1DosyaDTO.setDaireno(daireno);
            if(dre1MahalleAdi != null)
                sy1DosyaDTO.setDre1mahalleAdi(dre1MahalleAdi);
            if(tcKimlikNo != null)
                sy1DosyaDTO.setTckimlikno(tcKimlikNo.longValue());
            if(izahat != null)
                sy1DosyaDTO.setIzahat(izahat);
            if(sre1SokakAdi != null)
                sy1DosyaDTO.setSre1sokakAdi(sre1SokakAdi);
            if(sre1SokakCaddeAdi != null)
                sy1DosyaDTO.setSre1sokakCaddeadi(sre1SokakAdi);
            if(adi != null)
                sy1DosyaDTO.setAdi(adi);
            if(soyadi != null)
                sy1DosyaDTO.setSoyadi(soyadi);

            sy1DosyaDTOList.add(sy1DosyaDTO);
        }

        return sy1DosyaDTOList;
    }
}

