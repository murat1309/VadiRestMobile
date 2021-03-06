package com.digikent.denetimyonetimi.service;

import com.digikent.denetimyonetimi.dao.DenetimRepository;
import com.digikent.denetimyonetimi.dao.DenetimTarafRepository;
import com.digikent.denetimyonetimi.dto.denetim.*;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitDTO;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitKararRequest;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitNakitOdemeRequest;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitSearchRequest;
import com.digikent.denetimyonetimi.dto.paydas.DenetimIsletmeDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.tespit.*;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.entity.*;
import com.digikent.general.service.UtilityService;
import com.digikent.general.util.ErrorCode;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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

    @Inject
    UtilityService utilityService;

    public UtilDenetimSaveDTO saveDenetim(DenetimRequest denetimRequest, HttpServletRequest request) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        utilDenetimSaveDTO = denetimRepository.saveDenetim(denetimRequest, request);
        //denetim taraf objelerini setle
        if (utilDenetimSaveDTO.getSaved() && denetimRequest.getBdntDenetimId() != null) {
            LOG.debug("Denetim Güncellendi BdntDenetimId : " + denetimRequest.getBdntDenetimId());
            UtilDenetimSaveDTO utilDenetimTarafSaveDTO = denetimTarafRepository.saveDenetimTespitTaraf(denetimRequest,utilDenetimSaveDTO.getRecordId(),false, request);
        } else if (utilDenetimSaveDTO.getSaved() && denetimRequest.getBdntDenetimId() == null) {
            LOG.debug("Denetim Oluşturuldu BdntDenetimId : " + utilDenetimSaveDTO.getRecordId());
            UtilDenetimSaveDTO utilDenetimTarafSaveDTO = denetimTarafRepository.saveDenetimTespitTaraf(denetimRequest,utilDenetimSaveDTO.getRecordId(),true, request);
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
            return new KanunDTO(lsm2Kanun.getID(),lsm2Kanun.getTanim(),lsm2Kanun.getIzahat(),lsm2Kanun.getYayimTarihi(),lsm2Kanun.getMadde());
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

    public UtilDenetimSaveDTO savePaydasAllInformation(DenetimPaydasDTO denetimPaydasDTO, HttpServletRequest request) {
        //paydas kayit edilecek
        UtilDenetimSaveDTO utilDenetimSaveDTO = savePaydas(denetimPaydasDTO, request);
        if (utilDenetimSaveDTO.getSaved() && utilDenetimSaveDTO.getRecordId() != null) {
            //adresleri kayıt edilecek
            saveAdres(denetimPaydasDTO,utilDenetimSaveDTO.getRecordId(), request);
            //telefonlar kayıt edilecek
            saveTelefon(denetimPaydasDTO.getTelefonCep(),denetimPaydasDTO.getTelefonIs(),utilDenetimSaveDTO.getRecordId(), request);
        }
        return utilDenetimSaveDTO;
    }

    public UtilDenetimSaveDTO savePaydas(DenetimPaydasDTO denetimPaydasDTO, HttpServletRequest request) {
        UtilDenetimSaveDTO utilDenetimSaveDTO  = denetimRepository.savePaydas(denetimPaydasDTO, request);
        return utilDenetimSaveDTO;
    }

    public UtilDenetimSaveDTO saveAdres(DenetimPaydasDTO denetimPaydasDTO, Long paydasId, HttpServletRequest request) {
        return denetimRepository.saveAdres(denetimPaydasDTO,paydasId, request);
    }

    public UtilDenetimSaveDTO saveTelefon(Long telefonCep, Long telefonIs, Long paydasId, HttpServletRequest request) {
        return denetimRepository.saveTelefon(telefonCep,telefonIs, paydasId, request);
    }

    public UtilDenetimSaveDTO saveIsletme(DenetimIsletmeDTO denetimIsletmeDTO, HttpServletRequest request) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = denetimRepository.saveIsletme(denetimIsletmeDTO, request);
        if (utilDenetimSaveDTO.getSaved() && utilDenetimSaveDTO.getRecordId() != null) {
            //işletme adresi kaydedilecek
            UtilDenetimSaveDTO utilDenetimSaveDTOAdres = saveIsletmeAdresi(denetimIsletmeDTO,utilDenetimSaveDTO.getRecordId(), request);
            if (utilDenetimSaveDTOAdres.getSaved() && utilDenetimSaveDTOAdres.getRecordId() != null) {
                updateIsletme(utilDenetimSaveDTO.getRecordId(),utilDenetimSaveDTOAdres.getRecordId(), request);
            }
        }
        return utilDenetimSaveDTO;
    }

    private void updateIsletme(Long isletmeId, Long isletmeAdresId, HttpServletRequest request) {
        denetimRepository.updateIsletme(isletmeId,isletmeAdresId, request);
    }

    private UtilDenetimSaveDTO saveIsletmeAdresi(DenetimIsletmeDTO denetimIsletmeDTO, Long isletmeId, HttpServletRequest request) {
        return denetimRepository.saveIsletmeAdresi(denetimIsletmeDTO,isletmeId, request);
    }

    public UtilDenetimSaveDTO saveDenetimTespit(DenetimTespitRequest denetimTespitRequest, HttpServletRequest request) {
        return denetimRepository.saveDenetimTespit(denetimTespitRequest, request);
    }

    public UtilDenetimSaveDTO saveTespitler(TespitlerRequest tespitlerRequest, HttpServletRequest request) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        if (tespitlerRequest != null && tespitlerRequest.getSave() == false) {
            //güncelleme yapılacak
            LOG.debug("tespitler guncelleme islemi yapilacak denetimTespitId="+tespitlerRequest.getDenetimTespitId());
            utilDenetimSaveDTO = denetimRepository.updateTespitler(tespitlerRequest, request);
        } else {
            //ilk kayit
            LOG.debug("tespitler kayit islemi yapilacak");
            utilDenetimSaveDTO = denetimRepository.saveTespitler(tespitlerRequest, request);
        }

        return utilDenetimSaveDTO;
    }

    public List<DenetimTespitDTO> getDenetimTespitByDenetimId(Long denetimId) {
        return denetimRepository.getDenetimTespitListByTespitId(denetimId);
    }

    public DenetimDTO getDenetimById(Long id) {
        try {
            String sql = getDenetimlerGeneralSql();
            sql = sql + " WHERE ID="+id;
            List<DenetimDTO> denetimDTOList = denetimRepository.findDenetimListBySql(sql);
            if (denetimDTOList != null) {
                return denetimDTOList.get(0);
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOG.error("getDenetimById() hata. denetimID="+id);
            return null;
        }
    }

    public List<DenetimDTO> getDenetimList(DenetimTespitSearchRequest denetimTespitSearchRequest) {
        String beginQuery = "SELECT * FROM (" + getDenetimlerGeneralSql();
        String endOfQuery = " ) WHERE ROWNUM <= 50";

        //tarih seçilmişse tarih kriteri sorguya eklenir
        if (denetimTespitSearchRequest.getStartDate() != null && denetimTespitSearchRequest.getEndDate() != null) {
            beginQuery = beginQuery + " WHERE CRDATE BETWEEN TO_DATE('" + denetimTespitSearchRequest.getStartDate() + "','dd-mm-yyyy') AND TO_DATE('" + denetimTespitSearchRequest.getEndDate() + "', 'dd-mm-yyyy') ";
        }

        //inputa kriter(paydasadı paydassoyadı isletme tabela adı) girilmişse sorguya eklenir
        if (denetimTespitSearchRequest.getPaydasName() != null)
            beginQuery = beginQuery + " AND  (SELECT RAPORADI from MPI1PAYDAS where MPI1PAYDAS.ID=BDNTDENETIM.MPI1PAYDAS_ID AND rownum = 1) LIKE '%" + denetimTespitSearchRequest.getPaydasName() + "%'";

        //inputa kriter(vergino tcno paydasId) girilmişse sorguya eklenir
        if (denetimTespitSearchRequest.getCriteria() != null)
            beginQuery = beginQuery + " AND (MPI1PAYDAS_ID LIKE '%" + denetimTespitSearchRequest.getCriteria() + "%'" +
                    " OR TCKIMLIKNO LIKE '%" + denetimTespitSearchRequest.getCriteria() + "%' " +
                    " OR (SELECT VERGINUMARASI from BISLISLETME where BISLISLETME.ID=BDNTDENETIM.BISLISLETME_ID AND rownum = 1) LIKE '%" + denetimTespitSearchRequest.getCriteria() + "%') ";
        beginQuery = beginQuery + " ORDER BY CRDATE DESC";

        return denetimRepository.findDenetimListBySql(beginQuery + endOfQuery);
    }

    public String getDenetimlerGeneralSql() {
        String sql = "SELECT \n" +
                "ID,DENETIMTARIHI,SITEADI_OLAYYERI,BLOKNO_OLAYYERI,KAPINOSAYI_OLAYYERI,KAPINOHARF_OLAYYERI,DAIRENOSAYI_OLAYYERI,MPI1PAYDAS_ID,DAIRENOHARF_OLAYYERI,TEBLIG_SECENEGI,TEBLIG_ADI,TEBLIG_SOYADI,DENETIMTARAFTIPI,TEBLIG_TC,  \n" +
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
                "FROM BDNTDENETIM WHERE ID";

        return sql;
    }

    public UtilDenetimSaveDTO saveDenetimTeblig(DenetimTebligRequest denetimTebligRequest, HttpServletRequest request) {
        return denetimRepository.saveDenetimTeblig(denetimTebligRequest, request);
    }

    public UtilDenetimSaveDTO setPassiveDenetimTespit(Long denetimTespitId) {
        return denetimRepository.setPassiveDenetimTespit(denetimTespitId);
    }

    public UtilDenetimSaveDTO saveDenetimTespitKarar(DenetimTespitKararRequest denetimTespitKararRequest, HttpServletRequest request) {
        return denetimRepository.saveDenetimTespitKarar(denetimTespitKararRequest, request);
    }

    public List<DenetimGecmisDenetimlerDTO> getGecmisDenetimlerByPaydasId(long paydasId) {
        List<DenetimGecmisDenetimlerDTO> denetimGecmisDenetimlerDTOList= null;
        List<Long> denetimIdList = denetimRepository.getGecmisDenetimlerDenetimIdListByPaydasId(paydasId);
        denetimGecmisDenetimlerDTOList = denetimRepository.getGecmisDenetimTespitlerByDenetimIdList(denetimIdList);
        return denetimGecmisDenetimlerDTOList;
    }

    public UtilDenetimSaveDTO updateNakitOdeme(Long denetimTespitId, Boolean isNakitOdeme) {

        try {
            BDNTDenetimTespit bdntDenetimTespit = denetimRepository.findDenetimTespitById(denetimTespitId);
            LOG.info("bdntDenetimTespit indirim orani kaydedilecek. bdntDenetimTespit ID = " + bdntDenetimTespit.getID());
            bdntDenetimTespit.setNakitOdeme((isNakitOdeme));

            if (bdntDenetimTespit.getCezaMiktari() == null || bdntDenetimTespit.getCezaMiktari() == 0) {
                LOG.error("HATA = bdntDenetimTespit ceza miktari = " + bdntDenetimTespit.getCezaMiktari());
                UtilDenetimSaveDTO utilDenetimSaveDTO = new UtilDenetimSaveDTO(false,new ErrorDTO(true, ErrorCode.ERROR_CODE_508),null);
                return utilDenetimSaveDTO;
            }

            if (isNakitOdeme) {
                bdntDenetimTespit.setIndirimliCezaMiktari(calculateCezaMiktari(bdntDenetimTespit.getCezaMiktari()));
            } else if (!isNakitOdeme) {
                bdntDenetimTespit.setIndirimliCezaMiktari(null);
            }

            denetimRepository.updateDenetimTespit(bdntDenetimTespit);
            LOG.debug("bdntDenetimTespit de ceza indirim orani uygulandi. bdntDenetimTespitID = " + bdntDenetimTespit.getID());
        } catch (Exception ex) {
            LOG.error("bdntDenetimTespit indirim orani kaydedilirken bir hata olustu. " + ErrorCode.ERROR_CODE_507 + "  " + ex.getMessage());
            UtilDenetimSaveDTO utilDenetimSaveDTO = new UtilDenetimSaveDTO(false,new ErrorDTO(true, ErrorCode.ERROR_CODE_507),null);

            return utilDenetimSaveDTO;
        }
        UtilDenetimSaveDTO utilDenetimSaveDTO = new UtilDenetimSaveDTO(true, null, null);
        return utilDenetimSaveDTO;
    }

    public Long calculateCezaMiktari(Long cezaMiktari) throws Exception {
        Long indirimliCezaMiktari;
        try {
            Long indirimOrani = utilityService.getNakitOdemeIndirimi();
            indirimliCezaMiktari = cezaMiktari - (cezaMiktari * indirimOrani.longValue() / 100);
        } catch (Exception ex) {
            LOG.error("ERROR - Ceza indirimi hesaplanirken bir hata olustu");
            throw new Exception(ex.getMessage());
        }
        return indirimliCezaMiktari;
    }
}
