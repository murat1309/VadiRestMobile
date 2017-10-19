package com.digikent.ruhsat.dao;

import com.digikent.ruhsat.dto.*;
import com.digikent.ruhsat.service.TLI3RuhsatService;
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

    @Autowired
    TLI3RuhsatService ruhsatService;

    public List<TLI3RuhsatDTO> getRuhsatByPaydasNo(TLI3RuhsatDTO tli3RuhsatDTO) {
        String additionSQL = "and r.MPI1PAYDAS_ID=" + tli3RuhsatDTO.getMpi1PaydasId();
        return ruhsatService.getRuhsatDTOListRunSQL(additionSQL);
    }

    //barcodeId = id
    public List<TLI3RuhsatDTO> getRuhsatByBarcodeId(TLI3RuhsatDTO tli3RuhsatDTO) {
        String additionSQL = "and r.ID=" + tli3RuhsatDTO.getId();
        return ruhsatService.getRuhsatDTOListRunSQL(additionSQL);
    }

    public List<TLI3RuhsatTuruDTO> getRuhsatTuru() {
        return ruhsatService.getRuhsatTuruList();
    }

    public List<TLI3RuhsatDTO> getRuhsatByRuhsatTuru(TLI3RuhsatTuruRequestDTOList tli3RuhsatTuruRequestDTOList) {
        String additionSQL = "and r.SLI1RUHSATTURU_ID in (:turlist)";
        return ruhsatService.getRuhsatDTOListByRuhsatTuru(additionSQL, tli3RuhsatTuruRequestDTOList);
    }

    public List<TLI3RuhsatDTO> getRuhsatByAddressWithoutBina(TLI3RuhsatDTO tli3RuhsatDTO) {
        String additionSQL = "";
        if (tli3RuhsatDTO.getSokakId() != null) {
            additionSQL = "and r.sre1sokak_id=" + tli3RuhsatDTO.getSokakId() + " and s.ISACTIVE='E'";
        } else if (tli3RuhsatDTO.getMahalleId() != null) {
            additionSQL = "and r.DRE1MAHALLE_ID=" + tli3RuhsatDTO.getMahalleId() + " and m.ISACTIVE='E'";
        }

        return ruhsatService.getRuhsatDTOListRunSQL(additionSQL);
    }

    public List<TLI3RuhsatDTO> getRuhsatByAddressWithBina(TLI3RuhsatDTO tli3RuhsatDTO) {
        String additionSQL = "and y.ID=" + tli3RuhsatDTO.getBinaId();
        return ruhsatService.getRuhsatDTOListWithERE1YAPI(additionSQL);
    }

    public List<DRE1MahalleDTO> getMahalleList() {
        return ruhsatService.getMahalleList();
    }

    public List<SRE1SokakDTO> getSokakByMahalleId(Long mahId) {
        return ruhsatService.getSokakByMahalleId(mahId);
    }

    public List<ERE1YapiDTO> getBinaBySokakId(Long sokakId) {
        return ruhsatService.getBinaBySokakId(sokakId);
    }

}
