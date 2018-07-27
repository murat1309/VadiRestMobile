package com.digikent.ruhsat.dao;

import com.digikent.ruhsat.dto.*;
import com.digikent.ruhsat.service.TLI3RuhsatService;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<TLI3RuhsatDTO> getRuhsatInfo(TLI3RuhsatDTO tli3RuhsatDTO, String startDate, String endDate) {
        String additionSQL = "";
        if(!startDate.equalsIgnoreCase("null") && !endDate.equalsIgnoreCase("null")) {
            additionSQL += "AND R.RUHSATTARIHI BETWEEN TO_DATE('" + startDate + "', 'DD-MM-YYYY' ) AND TO_DATE('" + endDate + "', 'DD-MM-YYYY')";
        }
        if(tli3RuhsatDTO.getMpi1PaydasId() != null){
            additionSQL += " AND R.MPI1PAYDAS_ID=" + tli3RuhsatDTO.getMpi1PaydasId();
        }
        if(tli3RuhsatDTO.getFirmaAdı() != null && !tli3RuhsatDTO.getFirmaAdı().equalsIgnoreCase("")){
            additionSQL += " AND (ISYERIUNVANI LIKE '%" + tli3RuhsatDTO.getIsyeriUnvani().replace('i','İ') + "%' OR B.SORGUADI LIKE '%" + tli3RuhsatDTO.getFirmaAdı().replace('i','İ') + "%')";
        }
        return ruhsatService.getRuhsatDTOListRunSQL(additionSQL);
    }
    public List<RuhsatDurumuDTO> getRuhsatBasvuruDurumu(TLI3RuhsatDTO tli3RuhsatDTO, String startDate, String endDate) {
        String additionSQL = " AND TLI3RUHSAT.RUHSATTARIHI BETWEEN TO_DATE('" + startDate + "', 'DD-MM-YYYY' ) AND TO_DATE('" + endDate + "', 'DD-MM-YYYY')";
        if(tli3RuhsatDTO.getMpi1PaydasId() != null){
            additionSQL = additionSQL + " AND ELI1RUHSATDOSYA.MPI1PAYDAS_ID=" + tli3RuhsatDTO.getMpi1PaydasId();
        }
        if(tli3RuhsatDTO.getFirmaAdı() != null && !tli3RuhsatDTO.getFirmaAdı().equalsIgnoreCase("")){
            additionSQL = additionSQL + " AND (TLI3RUHSAT.ISYERIUNVANI LIKE '%" + tli3RuhsatDTO.getIsyeriUnvani().replace('i','İ') + "%' OR MPI1PAYDAS.SORGUADI LIKE '%" + tli3RuhsatDTO.getFirmaAdı().replace('i','İ') + "%')";
        }
        return ruhsatService.getRuhsatBasvuruDTOList(tli3RuhsatDTO, additionSQL);
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

    public List<TLI3RuhsatDTO> getRuhsatByAddressWithoutKapi(RuhsatAdresSorguDTO ruhsatAdresSorguDTO) {
        String additionSQL = "";
        if (ruhsatAdresSorguDTO.getSokakId() != null) {
            additionSQL = "and r.sre1sokak_id=" + ruhsatAdresSorguDTO.getSokakId() + " and s.ISACTIVE='E'";
        } else if (ruhsatAdresSorguDTO.getMahalleId() != null) {
            additionSQL = "and r.DRE1MAHALLE_ID=" + ruhsatAdresSorguDTO.getMahalleId() + " and m.ISACTIVE='E'";
        }

        return ruhsatService.getRuhsatDTOListRunSQL(additionSQL);
    }

    public List<TLI3RuhsatDTO> getRuhsatByAddressWithKapi(RuhsatAdresSorguDTO ruhsatAdresSorguDTO) {
        String additionSQL = ruhsatAdresSorguDTO.getSokakId() + " AND g.KAPINO= " + "'" + ruhsatAdresSorguDTO.getKapiValue() + "'";
        return ruhsatService.getRuhsatDTOListWithERE1YAPI(additionSQL);
    }

    public List<DRE1MahalleDTO> getMahalleList() {
        return ruhsatService.getMahalleList();
    }

    public List<SRE1SokakDTO> getSokakByMahalleId(Long mahId) {
        return ruhsatService.getSokakByMahalleId(mahId);
    }

    public List<ERE1YapiDTO> getKapiBySokakId(Long sokakId) {
        return ruhsatService.getKapiBySokakId(sokakId);
    }

}
