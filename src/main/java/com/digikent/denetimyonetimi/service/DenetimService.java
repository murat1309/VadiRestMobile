package com.digikent.denetimyonetimi.service;

import com.digikent.denetimyonetimi.dao.DenetimRepository;
import com.digikent.denetimyonetimi.dto.adres.BelediyeDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleSokakDTO;
import com.digikent.denetimyonetimi.dto.adres.SokakDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimRequest;
import com.digikent.denetimyonetimi.dto.denetim.DenetimTuruDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimIsletmeDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.tespit.SecenekTuruDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitGrubuDTO;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.internal.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Kadir on 26.01.2018.
 */
@Service
public class DenetimService {

    private final Logger LOG = LoggerFactory.getLogger(DenetimService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    DenetimRepository denetimRepository;

    public Boolean saveDenetim(DenetimRequest denetimRequest) {
        return denetimRepository.saveDenetim(denetimRequest);
    }

    public List<MahalleSokakDTO> getMahalleSokakListByBelediyeId(Long belediyeId) {
        return denetimRepository.findMahalleAndSokakListByBelediyeId(belediyeId);
    }

    public List<BelediyeDTO> getBelediyeList() {
        return denetimRepository.findBelediyeList();
    }

    public List<MahalleDTO> getMahalleByBelediyeId(Long belediyeId) {
        return denetimRepository.findMahalleListByBelediyeId(belediyeId);
    }

    public List<SokakDTO> getSokakByMahalleId(Long mahalleId) {
        return denetimRepository.findSokakListByMahalleId(mahalleId);
    }

    public List<MahalleDTO> getMahalleListByCurrentBelediye() {
        return denetimRepository.findMahalleListByCurrentBelediye();
    }

    public List<DenetimTuruDTO> getDenetimTuruDTOList() {
        return denetimRepository.getDenetimTuruDTOList();
    }

    public List<TespitGrubuDTO> getTespitGrubuDTOListByDenetimTuruId(Long denetimTuruId) {
        return denetimRepository.findTespitGrubuDTOListByDenetimTuruId(denetimTuruId);
    }

    public List<TespitDTO> getTespitDTOListByTespitGrubuId(Long tespitGrubuId) {
        List<TespitDTO> tespitDTOList = denetimRepository.findTespitDTOListByTespitGrubuId(tespitGrubuId);
        List<SecenekTuruDTO> secenekTuruDTOList = denetimRepository.findSecenekDTOListByTespitGrubuId(tespitGrubuId);

        return groupingTespitAndSecenekTuru(tespitDTOList, secenekTuruDTOList);
    }

    public List<TespitDTO> groupingTespitAndSecenekTuru(List<TespitDTO> tespitDTOList, List<SecenekTuruDTO> secenekTuruDTOList) {

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
}
