package com.digikent.denetimyonetimi.service;

import com.digikent.denetimyonetimi.dao.DenetimRepository;
import com.digikent.denetimyonetimi.dao.DenetimTarafRepository;
import com.digikent.denetimyonetimi.dto.denetim.*;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitDTO;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitKararRequest;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitSearchRequest;
import com.digikent.denetimyonetimi.dto.paydas.DenetimIsletmeDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.tespit.*;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.entity.*;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 26.01.2018.
 * Updated by Erkan on 15.02.2018 -- added cache support
 */
@Service
public class DenetimService {

    private final Logger LOG = LoggerFactory.getLogger(DenetimService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    DenetimTarafRepository denetimTarafRepository;

    @Inject
    DenetimRepository denetimRepository;

    public UtilDenetimSaveDTO saveDenetim(DenetimRequest denetimRequest) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        utilDenetimSaveDTO = denetimRepository.saveDenetim(denetimRequest);
        //denetim taraf objelerini setle
        if (utilDenetimSaveDTO.getSaved() && denetimRequest.getBdntDenetimId() != null) {
            LOG.debug("Denetim Güncellendi BdntDenetimId : " + denetimRequest.getBdntDenetimId());
            UtilDenetimSaveDTO utilDenetimTarafSaveDTO = denetimTarafRepository.saveDenetimTespitTaraf(denetimRequest,utilDenetimSaveDTO.getRecordId(),false);
        } else if (utilDenetimSaveDTO.getSaved() && denetimRequest.getBdntDenetimId() == null) {
            LOG.debug("Denetim Oluşturuldu BdntDenetimId : " + denetimRequest.getBdntDenetimId());
            UtilDenetimSaveDTO utilDenetimTarafSaveDTO = denetimTarafRepository.saveDenetimTespitTaraf(denetimRequest,utilDenetimSaveDTO.getRecordId(),true);
        }

        return utilDenetimSaveDTO;
    }

    @Cacheable(value="denetim", key = "#root.methodName.toString()")
    public List<DenetimTuruDTO> getDenetimTuruDTOList() {
        LOG.debug("searching getDenetimTuruDTOList");
        return denetimRepository.getDenetimTuruDTOList();
    }

    @Cacheable(value="denetim", key = "#root.methodName.toString() + #denetimTuruId ")
    public List<TespitGrubuDTO> getTespitGrubuDTOListByDenetimTuruId(Long denetimTuruId) {
        LOG.debug("searching getTespitGrubuDTOListByDenetimTuruId");
        return denetimRepository.findTespitGrubuDTOListByDenetimTuruId(denetimTuruId);
    }
    //@Cacheable(value="denetim", key = "#root.methodName.toString() + #tespitGrubuId ")
    public List<TespitDTO> getTespitDTOListByTespitGrubuId(Long tespitGrubuId) {
        //List<ReportTespitDTO> tespitDTOList = denetimRepository.findTespitDTOListByTespitGrubuId(tespitGrubuId);
        LOG.debug("searching getTespitDTOListByTespitGrubuId");
        List<SecenekTuruDTO> secenekTuruDTOList = null;
        List<TespitTarifeDTO> tespitTarifeDTOList = null;
        List<TespitDTO> tespitDTOList = null;

        List<LDNTTespit> tespitList = denetimRepository.findTespitListByTespitGrubuId(tespitGrubuId);
        tespitDTOList = ldntTespitToListTespitDTOList(tespitList);
        if (tespitDTOList != null && tespitDTOList.size() > 0) {
            secenekTuruDTOList = denetimRepository.findSecenekDTOListByTespitGrubuId(tespitGrubuId);
            tespitTarifeDTOList = getTespitTarifeDTOList(tespitDTOList);
            tespitDTOList = groupingTespitAndTespitTarife(tespitDTOList, tespitTarifeDTOList);
            return groupingTespitAndSecenekTuru(tespitDTOList, secenekTuruDTOList);
        }
        return tespitDTOList;
    }

    private List<TespitTarifeDTO> getTespitTarifeDTOList(List<TespitDTO> tespitDTOList) {
        List<LDNTTespitTarife> ldntTespitTarifeList = null;
        List<Long> tespitIdList = new ArrayList<>();
        for (TespitDTO tespitDTO:tespitDTOList) {
            tespitIdList.add(tespitDTO.getId());
        }
        ldntTespitTarifeList = denetimRepository.findTespitTarifeListByTespitIdList(tespitIdList);

        return ldntTespitTarifeToTespitTarifeDTO(ldntTespitTarifeList);
    }

    private List<TespitTarifeDTO> ldntTespitTarifeToTespitTarifeDTO(List<LDNTTespitTarife> ldntTespitTarifeList) {
        List<TespitTarifeDTO> tespitTarifeDTOList = new ArrayList<>();
        for (LDNTTespitTarife ldnTespitTarife:ldntTespitTarifeList) {
            TespitTarifeDTO tespitTarifeDTO = new TespitTarifeDTO();
            tespitTarifeDTO.setId(ldnTespitTarife.getID());
            tespitTarifeDTO.setAltLimitTutari(ldnTespitTarife.getAltLimitTutari());
            tespitTarifeDTO.setUstLimitTutari(ldnTespitTarife.getUstLimitTutari());
            tespitTarifeDTO.setTespitId(ldnTespitTarife.getLdntTespitId());

            tespitTarifeDTOList.add(tespitTarifeDTO);
        }
        return tespitTarifeDTOList;
    }

    private TespitDTO ldntTespitToTespitDTO(LDNTTespit ldntTespit) {

        TespitDTO tespitDTO = new TespitDTO();

        tespitDTO.setId((ldntTespit.getID() != null ? ldntTespit.getID().longValue() : null));
        tespitDTO.setKanunDTO(lsm2KanunTolsm2KanunDTO(ldntTespit.getLsm2Kanun()));
        tespitDTO.setTanim(ldntTespit.getTanim());
        tespitDTO.setKayitOzelIsmi(ldntTespit.getKayitOzelIsmi());
        tespitDTO.setIzahat(ldntTespit.getIzahat());
        tespitDTO.setAksiyon(ldntTespit.getAksiyon());
        tespitDTO.setSecenekTuru(ldntTespit.getSecenekTuru());
        tespitDTO.setEkSureVerilebilirMi(ldntTespit.getEkSureVerilebilirMi());
        tespitDTO.setEkSure((ldntTespit.getEkSure() != null ? ldntTespit.getEkSure().longValue() : null));
        tespitDTO.setSirasi((ldntTespit.getSirasi() != null ? ldntTespit.getSirasi().longValue() : null));
        tespitDTO.setTur(ldntTespit.getTur());

        return tespitDTO;
    }

    private List<TespitDTO> ldntTespitToListTespitDTOList(List<LDNTTespit> ldntTespitList) {
        List<TespitDTO> tespitDTOList = new ArrayList<>();
        for (LDNTTespit ldntTespit:ldntTespitList) {
            TespitDTO tespitDTO = ldntTespitToTespitDTO(ldntTespit);
            tespitDTOList.add(tespitDTO);
        }
        return tespitDTOList;
    }

    private KanunDTO lsm2KanunTolsm2KanunDTO(LSM2Kanun lsm2Kanun) {
        if (lsm2Kanun != null) {
            return new KanunDTO(lsm2Kanun.getID(),lsm2Kanun.getTanim(),lsm2Kanun.getIzahat(),lsm2Kanun.getYayimTarihi());
        } else {
            return null;
        }
    }

    private List<TespitDTO> groupingTespitAndSecenekTuru(List<TespitDTO> tespitDTOList, List<SecenekTuruDTO> secenekTuruDTOList) {

        for (TespitDTO tespitDTO : tespitDTOList) {
            if (tespitDTO.getSecenekTuru().equals("CHECKBOX")) {
                for (SecenekTuruDTO secenekTuruDTO : secenekTuruDTOList) {
                    if (tespitDTO.getId().longValue() == secenekTuruDTO.getTespitId().longValue()) {
                        tespitDTO.getSecenekTuruDTOList().add(secenekTuruDTO);
                    }
                }
            }
        }

        return tespitDTOList;
    }

    private List<TespitDTO> groupingTespitAndTespitTarife(List<TespitDTO> tespitDTOList, List<TespitTarifeDTO> tespitTarifeDTOList) {
        for (TespitDTO tespitDTO : tespitDTOList) {
            for (TespitTarifeDTO tespitTarifeDTO : tespitTarifeDTOList) {
                if (tespitDTO.getId() != null && tespitTarifeDTO.getTespitId() != null) {
                    if (tespitDTO.getId().longValue() == tespitTarifeDTO.getTespitId().longValue()) {
                        tespitDTO.setTespitTarifeDTO(tespitTarifeDTO);
                        break;
                    }
                }
            }
        }

        return tespitDTOList;
    }

    public List<DenetimIsletmeDTO> getIsletmeDTOListByPaydasId(Long paydasId) {
        return denetimRepository.findIsletmeDTOListByPaydasId(paydasId);
    }

    public UtilDenetimSaveDTO savePaydasAllInformation(DenetimPaydasDTO denetimPaydasDTO) {
        //paydas kayit edilecek
        UtilDenetimSaveDTO utilDenetimSaveDTO = savePaydas(denetimPaydasDTO);
        if (utilDenetimSaveDTO.getSaved() && utilDenetimSaveDTO.getRecordId() != null) {
            //adresleri kayıt edilecek
            saveAdres(denetimPaydasDTO,utilDenetimSaveDTO.getRecordId());
            //telefonlar kayıt edilecek
            saveTelefon(denetimPaydasDTO.getTelefonCep(),denetimPaydasDTO.getTelefonIs(),utilDenetimSaveDTO.getRecordId());
        }
        return utilDenetimSaveDTO;
    }

    public UtilDenetimSaveDTO savePaydas(DenetimPaydasDTO denetimPaydasDTO) {
        UtilDenetimSaveDTO utilDenetimSaveDTO  = denetimRepository.savePaydas(denetimPaydasDTO);
        return utilDenetimSaveDTO;
    }

    public UtilDenetimSaveDTO saveAdres(DenetimPaydasDTO denetimPaydasDTO, Long paydasId) {
        return denetimRepository.saveAdres(denetimPaydasDTO,paydasId);
    }

    public UtilDenetimSaveDTO saveTelefon(Long telefonCep, Long telefonIs, Long paydasId) {
        return denetimRepository.saveTelefon(telefonCep,telefonIs, paydasId);
    }

    public UtilDenetimSaveDTO saveIsletme(DenetimIsletmeDTO denetimIsletmeDTO) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = denetimRepository.saveIsletme(denetimIsletmeDTO);
        if (utilDenetimSaveDTO.getSaved() && utilDenetimSaveDTO.getRecordId() != null) {
            //işletme adresi kaydedilecek
            UtilDenetimSaveDTO utilDenetimSaveDTOAdres = saveIsletmeAdresi(denetimIsletmeDTO,utilDenetimSaveDTO.getRecordId());
            if (utilDenetimSaveDTOAdres.getSaved() && utilDenetimSaveDTOAdres.getRecordId() != null) {
                updateIsletme(utilDenetimSaveDTO.getRecordId(),utilDenetimSaveDTOAdres.getRecordId());
            }
        }
        return utilDenetimSaveDTO;
    }

    private void updateIsletme(Long isletmeId, Long isletmeAdresId) {
        denetimRepository.updateIsletme(isletmeId,isletmeAdresId);
    }

    private UtilDenetimSaveDTO saveIsletmeAdresi(DenetimIsletmeDTO denetimIsletmeDTO, Long isletmeId) {
        return denetimRepository.saveIsletmeAdresi(denetimIsletmeDTO,isletmeId);
    }

    public UtilDenetimSaveDTO saveDenetimTespit(DenetimTespitRequest denetimTespitRequest) {
        return denetimRepository.saveDenetimTespit(denetimTespitRequest);
    }

    public UtilDenetimSaveDTO saveTespitler(TespitlerRequest tespitlerRequest) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        if (tespitlerRequest != null && tespitlerRequest.getSave() == false) {
            //güncelleme yapılacak
            LOG.debug("tespitler guncelleme islemi yapilacak denetimTespitId="+tespitlerRequest.getDenetimTespitId());
            utilDenetimSaveDTO = denetimRepository.updateTespitler(tespitlerRequest);
        } else {
            //ilk kayit
            LOG.debug("tespitler kayit islemi yapilacak");
            utilDenetimSaveDTO = denetimRepository.saveTespitler(tespitlerRequest);
        }

        return utilDenetimSaveDTO;
    }

    public List<DenetimTespitDTO> getDenetimTespitByDenetimId(Long denetimId) {
        return denetimRepository.getDenetimTespitListByTespitId(denetimId);
    }

    public DenetimDTO getDenetimById(Long id) {
        String sql = getDenetimlerGeneralSql();
        sql = sql + " WHERE ID="+id;
        List<DenetimDTO> denetimDTOList = denetimRepository.findDenetimListBySql(sql);
        if (denetimDTOList != null) {
            return denetimDTOList.get(0);
        } else {
            return null;
        }
    }

    public List<DenetimDTO> getDenetimList(DenetimTespitSearchRequest denetimTespitSearchRequest) {
        String endOfQuery = "WHERE ROWNUM <= 50 ";

        //inputa kriter girilmişse sorguya eklenir
        if (denetimTespitSearchRequest.getCriteria() != null)
            endOfQuery = endOfQuery + " AND (MPI1PAYDAS_ID LIKE '%" + denetimTespitSearchRequest.getCriteria() + "%'" +
                    " OR TCKIMLIKNO LIKE '%" + denetimTespitSearchRequest.getCriteria() + "%' " +
                    " OR (SELECT VERGINUMARASI from BISLISLETME where BISLISLETME.ID=BDNTDENETIM.BISLISLETME_ID AND rownum = 1) LIKE '%" + denetimTespitSearchRequest.getCriteria() + "%') ";

        //tarih seçilmişse tarih kriteri sorguya eklenir
        if (denetimTespitSearchRequest.getStartDate() != null && denetimTespitSearchRequest.getEndDate() != null) {
            endOfQuery = endOfQuery + " AND CRDATE BETWEEN TO_DATE('" + denetimTespitSearchRequest.getStartDate() + "','dd-mm-yyyy') AND TO_DATE('" + denetimTespitSearchRequest.getEndDate() + "', 'dd-mm-yyyy') ";
        }

        endOfQuery = endOfQuery + " ORDER BY CRDATE DESC";

        return denetimRepository.findDenetimListBySql(getDenetimlerGeneralSql() + endOfQuery);
    }

    public String getDenetimlerGeneralSql() {
        String sql = "SELECT \n" +
                "ID,DENETIMTARIHI,SITEADI_OLAYYERI,BLOKNO_OLAYYERI,KAPINOSAYI_OLAYYERI,KAPINOHARF_OLAYYERI,DAIRENOSAYI_OLAYYERI,MPI1PAYDAS_ID,DAIRENOHARF_OLAYYERI,TEBLIG_SECENEGI,TEBLIG_ADI,TEBLIG_SOYADI,TEBLIG_TC,  \n" +
                "(SELECT RAPORADI from MPI1PAYDAS where MPI1PAYDAS.ID=BDNTDENETIM.MPI1PAYDAS_ID AND rownum = 1) AS PAYDASADI,\n" +
                "(SELECT VERGINUMARASI from BISLISLETME where BISLISLETME.ID=BDNTDENETIM.BISLISLETME_ID AND rownum = 1) AS VERGINUMARASI,\n" +
                "(SELECT TANIM  from RRE1ILCE where ID=BDNTDENETIM.RRE1ILCE_OLAYYERI AND rownum = 1) AS ILCEADI,\n" +
                "(SELECT TANIM  from DRE1MAHALLE where ID=BDNTDENETIM.DRE1MAHALLE_OLAYYERI AND rownum = 1) AS MAHALLEADI,\n" +
                "(SELECT TANIM  from SRE1SOKAK where ID=BDNTDENETIM.SRE1SOKAK_OLAYYERI AND rownum = 1) AS SOKAKADI,\n" +
                "(SELECT RNAME  from VSYNROLETEAM where ID=BDNTDENETIM.VSYNROLETEAM_ID AND rownum = 1) AS TAKIMADI\n" +
                "FROM BDNTDENETIM ";
        return sql;
    }

    public String getDenetimOlayYeriAdresiSql() {

        String sql = "SELECT\n" +
                "ADANO_OLAYYERI,BLOKNO_OLAYYERI,DAIRENOHARF_OLAYYERI,DAIRENOSAYI_OLAYYERI,DRE1BAGBOLUM_OLAYYERI,\n" +
                "DRE1MAHALLE_OLAYYERI,ERE1YAPI_OLAYYERI,IRE1PARSEL_OLAYYERI,KAPINOHARF_OLAYYERI,\n" +
                "KAPINOSAYI_OLAYYERI,PAFTANO_OLAYYERI,PARSELNO_OLAYYERI,PRE1IL_OLAYYERI,RRE1ILCE_OLAYYERI,\n" +
                "SITEADI_OLAYYERI,SRE1SOKAK_OLAYYERI,\n" +
                "(SELECT TANIM  from PRE1IL where ID=BDNTDENETIM.PRE1IL_OLAYYERI AND rownum = 1) AS ILADI,\n" +
                "(SELECT TANIM  from RRE1ILCE where ID=BDNTDENETIM.RRE1ILCE_OLAYYERI AND rownum = 1) AS ILCEADI,\n" +
                "(SELECT TANIM  from DRE1MAHALLE where ID=BDNTDENETIM.DRE1MAHALLE_OLAYYERI AND rownum = 1) AS MAHALLEADI,\n" +
                "(SELECT TANIM  from SRE1SOKAK where ID=BDNTDENETIM.SRE1SOKAK_OLAYYERI AND rownum = 1) AS SOKAKADI\n" +
                "FROM BDNTDENETIM WHERE ID";;

        return sql;
    }

    public String getDenetimObjectSqlQueryString() {
        String sql = "SELECT\n" +
                "ADANO_OLAYYERI,BLOKNO_OLAYYERI,DAIRENOHARF_OLAYYERI,DAIRENOSAYI_OLAYYERI,DRE1BAGBOLUM_OLAYYERI,\n" +
                "DRE1MAHALLE_OLAYYERI,ERE1YAPI_OLAYYERI,IRE1PARSEL_OLAYYERI,KAPINOHARF_OLAYYERI,\n" +
                "KAPINOSAYI_OLAYYERI,PAFTANO_OLAYYERI,PARSELNO_OLAYYERI,PRE1IL_OLAYYERI,RRE1ILCE_OLAYYERI,\n" +
                "SITEADI_OLAYYERI,SRE1SOKAK_OLAYYERI,\n" +
                "(SELECT TANIM  from PRE1IL where ID=BDNTDENETIM.PRE1IL_OLAYYERI AND rownum = 1) AS OLAYIILADI,\n" +
                "(SELECT TANIM  from RRE1ILCE where ID=BDNTDENETIM.RRE1ILCE_OLAYYERI AND rownum = 1) AS OLAYILCEADI,\n" +
                "(SELECT TANIM  from DRE1MAHALLE where ID=BDNTDENETIM.DRE1MAHALLE_OLAYYERI AND rownum = 1) AS OLAYMAHALLEADI,\n" +
                "(SELECT TANIM  from SRE1SOKAK where ID=BDNTDENETIM.SRE1SOKAK_OLAYYERI AND rownum = 1) AS OLAYSOKAKADI,\n" +
                "BLOKNO_TEBLIGAT,DAIRENOHARF_TEBLIGAT,DAIRENOSAYI_TEBLIGAT,DRE1MAHALLE_TEBLIGAT,KAPINOHARF_TEBLIGAT,KAPINOSAYI_TEBLIGAT,\n" +
                "PRE1IL_TEBLIGAT,RRE1ILCE_TEBLIGAT,SITEADI_TEBLIGAT,SRE1SOKAK_TEBLIGAT,\n" +
                "(SELECT TANIM  from PRE1IL where ID=BDNTDENETIM.PRE1IL_TEBLIGAT AND rownum = 1) AS TEBLIGATILADI,\n" +
                "(SELECT TANIM  from RRE1ILCE where ID=BDNTDENETIM.RRE1ILCE_TEBLIGAT AND rownum = 1) AS TEBLIGATILCEADI,\n" +
                "(SELECT TANIM  from DRE1MAHALLE where ID=BDNTDENETIM.DRE1MAHALLE_TEBLIGAT AND rownum = 1) AS TEBLIGATMAHALLEADI,\n" +
                "(SELECT TANIM  from SRE1SOKAK where ID=BDNTDENETIM.SRE1SOKAK_TEBLIGAT AND rownum = 1) AS TEBLIGATSOKAKADI,\n" +
                "TEBLIG_ADI,TEBLIG_SECENEGI,TEBLIG_SOYADI,TEBLIG_TC\n" +
                "FROM BDNTDENETIM WHERE ID = ";;

        return sql;
    }

//                "paydasNo": 1289644,
//                "adi": "medet",
//                "soyAdi": "can",
//                "unvan": null,
//                "vergiNo": null,
//                "telefon": "[ İş:1234568565] ",
//                "paydasTuru": "S",
//                "tabelaAdi": null,
//                "izahat": null,
//                "kayitDurumu": "A",
//                "binaAdi": "-",
//                "kapiNo": "-",
//                "ilceAdi": "-",
//                "kapiNoSayi": 2,
//                "kapiNoHarf": "-",
//                "daireNoHarf": "-",
//                "daireNoSayi": 3,
//                "katSayi": null,
//                "katHarf": "-",
//                "blokNo": "1",
//                "dre1MahalleId": 80010,
//                "sre1SokakId": 124389,
//                "rre1IlceId": 10114,
//                "pre1IlId": 0,
//                "tcKimlikNo": 12345678903,
//                "telefonCep": null,
//                "telefonIs": null,
//                "ticaretSicilNo": null,
//                "vergiDairesi": null,
//                "siteAdi": null,
//                "firmaYetkiliAdi": null,
//                "firmaYetkiliSoyadi": null,
//                "firmaYetkiliTC": null

    public String getDenetimObjectPaydasInfoSqlQueryString() {
        String sql = "SELECT ADI,SOYADI,TELEFONNUMARASI,UNVAN FROM MPI1PAYDAS WHERE MPI1PAYDAS.ID = ";

        return sql;
    }

    public UtilDenetimSaveDTO saveDenetimTeblig(DenetimTebligRequest denetimTebligRequest) {
        return denetimRepository.saveDenetimTeblig(denetimTebligRequest);
    }

    public UtilDenetimSaveDTO setPassiveDenetimTespit(Long denetimTespitId) {
        return denetimRepository.setPassiveDenetimTespit(denetimTespitId);
    }

    public UtilDenetimSaveDTO saveDenetimTespitKarar(DenetimTespitKararRequest denetimTespitKararRequest) {
        return denetimRepository.saveDenetimTespitKarar(denetimTespitKararRequest);
    }

    public DenetimObjectDTO getDenetimObjectByDenetimAndDenetimTespitId(DenetimObjectRequestDTO denetimObjectRequestDTO) {


        return denetimRepository.getDenetimObjectByDenetimAndDenetimTespitId(denetimObjectRequestDTO, getDenetimObjectSqlQueryString(), getDenetimObjectPaydasInfoSqlQueryString());
    }
}
