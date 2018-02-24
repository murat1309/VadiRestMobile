package com.digikent.denetimyonetimi.service;

import com.digikent.denetimyonetimi.dao.DenetimRepository;
import com.digikent.denetimyonetimi.dto.adres.*;
import com.digikent.denetimyonetimi.dto.denetim.DenetimDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimRequest;
import com.digikent.denetimyonetimi.dto.denetim.DenetimTespitRequest;
import com.digikent.denetimyonetimi.dto.denetim.DenetimTuruDTO;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitDTO;
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
    DenetimRepository denetimRepository;

    public UtilDenetimSaveDTO saveDenetim(DenetimRequest denetimRequest) {
        UtilDenetimSaveDTO utilDenetimSaveDTO = null;
        utilDenetimSaveDTO = denetimRepository.saveDenetim(denetimRequest);
        //denetim taraf objelerini setle
        if (utilDenetimSaveDTO.getSaved() && denetimRequest.getBdntDenetimId() != null) {
            UtilDenetimSaveDTO utilDenetimTarafSaveDTO = denetimRepository.saveDenetimTespitTaraf(denetimRequest,utilDenetimSaveDTO.getRecordId(),false);
        } else if (utilDenetimSaveDTO.getSaved() && denetimRequest.getBdntDenetimId() == null) {
            UtilDenetimSaveDTO utilDenetimTarafSaveDTO = denetimRepository.saveDenetimTespitTaraf(denetimRequest,utilDenetimSaveDTO.getRecordId(),true);
        }

        return utilDenetimSaveDTO;
    }

    @Cacheable(value = "sokaklar", key = "#root.methodName.toString() + #belediyeId")
    public List<MahalleSokakDTO> getMahalleSokakListByBelediyeId(Long belediyeId) {
        LOG.debug("searching getMahalleSokakListByBelediyeId");
        return denetimRepository.findMahalleAndSokakListByBelediyeId(belediyeId);
    }

    @Cacheable(value="belediyeler", key = "#root.methodName")
    public List<BelediyeDTO> getBelediyeList() {
        LOG.debug("searching getBelediyeList");
        return denetimRepository.findBelediyeList();
    }

    @Cacheable(value="mahalleler", key = "#belediyeId")
    public List<MahalleDTO> getMahalleByBelediyeId(Long belediyeId) {
        LOG.debug("searching getMahalleByBelediyeId");
        return denetimRepository.findMahalleListByBelediyeId(belediyeId);
    }

    @Cacheable(value = "sokaklar", key = "#mahalleId")
    public List<SokakDTO> getSokakByMahalleId(Long mahalleId) {
        LOG.debug("searching getSokakByMahalleId");
        return denetimRepository.findSokakListByMahalleId(mahalleId);
    }

    @Cacheable(value="mahalleler", key = "#root.methodName.toString()")
    public List<MahalleDTO> getMahalleListByCurrentBelediye() {
        LOG.debug("searching getMahalleListByCurrentBelediye");
        return denetimRepository.findMahalleListByCurrentBelediye();
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

        List<LDNTTespit> tespitList = denetimRepository.findTespitListByTespitGrubuId(tespitGrubuId);
        List<TespitDTO> tespitDTOList = ldntTespitToListTespitDTOList(tespitList);
        if (tespitDTOList != null) {
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
        return new KanunDTO(lsm2Kanun.getID(),lsm2Kanun.getTanim(),lsm2Kanun.getIzahat(),lsm2Kanun.getYayimTarihi());
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
        return denetimRepository.saveTespitler(tespitlerRequest);
    }


    public List<DenetimTespitDTO> getDenetimTespitByDenetimId(Long denetimId) {
        return denetimRepository.getDenetimTespitListByTespitId(denetimId);
    }
    @Cacheable(value = "iller", key = "#root.methodName.toString()")
    public List<IlDTO> getIlList() {
        return denetimRepository.findIlList();
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

    public List<DenetimDTO> getDenetimList() {
        return denetimRepository.findDenetimListBySql(getDenetimlerGeneralSql());
    }

    public String getDenetimlerGeneralSql() {
        String sql = "SELECT \n" +
                "ID,DENETIMTARIHI,SITEADI_OLAYYERI,BLOKNO_OLAYYERI,KAPINOSAYI_OLAYYERI,KAPINOHARF_OLAYYERI,DAIRENOSAYI_OLAYYERI,DAIRENOHARF_OLAYYERI,\n" +
                "(SELECT RAPORADI from MPI1PAYDAS where MPI1PAYDAS.ID=BDNTDENETIM.MPI1PAYDAS_ID AND rownum = 1) AS PAYDASADI,\n" +
                "(SELECT TANIM  from RRE1ILCE where ID=BDNTDENETIM.RRE1ILCE_OLAYYERI AND rownum = 1) AS ILCEADI,\n" +
                "(SELECT TANIM  from DRE1MAHALLE where ID=BDNTDENETIM.DRE1MAHALLE_OLAYYERI AND rownum = 1) AS MAHALLEADI,\n" +
                "(SELECT TANIM  from SRE1SOKAK where ID=BDNTDENETIM.SRE1SOKAK_OLAYYERI AND rownum = 1) AS SOKAKADI,\n" +
                "(SELECT RNAME  from VSYNROLETEAM where ID=BDNTDENETIM.VSYNROLETEAM_ID AND rownum = 1) AS TAKIMADI\n" +
                "FROM BDNTDENETIM";
        return sql;
    }

    public List<BDNTDenetimTespitTaraf> getDenetimTarafListByDenetimId(Long denetimId) {
        return denetimRepository.findDenetimTespitTarafListByDenetimId(denetimId);
    }
}
