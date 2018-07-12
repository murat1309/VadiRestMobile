package com.digikent.sosyalyardim.service;

import com.digikent.general.util.ErrorCode;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.sosyalyardim.dao.VSY1AktiviteRepository;
import com.digikent.sosyalyardim.dto.*;
import com.digikent.sosyalyardim.entity.VSY1Aktivite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import javax.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class VSY1AktiviteService {

    private final Logger LOG = LoggerFactory.getLogger(VSY1AktiviteService.class);

    @Inject
    VSY1AktiviteRepository vsy1AktiviteRepository;

    public VSY1AktiviteResponse getAktiviteByDosyaId(Long dosyaId) {
        VSY1AktiviteResponse vsy1AktiviteResponse = new VSY1AktiviteResponse();
        List<VSY1AktiviteDTO> aktiviteDTOList = null;
        List<VSY1Aktivite> aktiviteList = null;
        try {
            aktiviteList = vsy1AktiviteRepository.getAktiviteByDosyaId(dosyaId);
            if (aktiviteList != null && aktiviteList.size() != 0)
                aktiviteDTOList = convertAktiviteToAktiviteDTO(aktiviteList);
            vsy1AktiviteResponse.setVsy1AktiviteListDTO(aktiviteDTOList);
            vsy1AktiviteResponse.setErrorDTO(null);

        } catch (Exception ex) {
            vsy1AktiviteResponse.setVsy1AktiviteListDTO(null);
            vsy1AktiviteResponse.setErrorDTO(new ErrorDTO(true, ErrorCode.ERROR_CODE_603));
            LOG.error("HATA KODU : 603. Tespitler getirilirken hata oluştu.");
        }
        return vsy1AktiviteResponse;
    }

    private List<VSY1AktiviteDTO> convertAktiviteToAktiviteDTO(List<VSY1Aktivite> aktiviteList) {

        List<VSY1AktiviteDTO> aktiviteDTOList = new ArrayList<>();

        for (VSY1Aktivite item : aktiviteList) {
            try {
                VSY1AktiviteDTO aktiviteDTO = new VSY1AktiviteDTO();

                if (item.getBaslamaTarihi() != null) {
                    aktiviteDTO.setBaslamaTarihi(new SimpleDateFormat("dd-MM-yyyy").format(item.getBaslamaTarihi()));
                } else {
                    if (item.getCrDate() != null) {
                        aktiviteDTO.setBaslamaTarihi(new SimpleDateFormat("dd-MM-yyyy").format(item.getCrDate()));
                    }
                }
                aktiviteDTO.setAktiviteId((item.getID()));
                aktiviteDTO.setIhr1PersonelVeren((item.getIhr1PersonelVeren() != null ? item.getIhr1PersonelVeren().getAdi() + " " + item.getIhr1PersonelVeren().getSoyadi() : "-"));
                aktiviteDTO.setIhr1PersonelVerilen((item.getIhr1PersonelVerilen() != null ? item.getIhr1PersonelVerilen().getAdi() + " " + item.getIhr1PersonelVerilen().getSoyadi() : "-"));
                aktiviteDTO.setTsy1AktiviteIslem(item.getTsy1AktiviteIslem() != null ? item.getTsy1AktiviteIslem().getTanim() : "-");

                aktiviteDTOList.add(aktiviteDTO);
            } catch (Exception ex) {
                ex.printStackTrace();
                LOG.error("convertTespitToTespitDTO() hata oluştu, pas geçildi...");
            }
        }

        return aktiviteDTOList;
    }

    public VSY1AktiviteOlusturDTO createAktivite(VSY1AktiviteOlusturRequest vsy1AktiviteOlusturRequest) throws Exception {
        VSY1AktiviteOlusturDTO vsy1AktiviteOlusturDTO = null;

        try {
            if (vsy1AktiviteOlusturRequest.getDosyaId() == null
                    || vsy1AktiviteOlusturRequest.getFsm1UsersId() == null
                    || vsy1AktiviteOlusturRequest.getIhr1PersonelVerenId() == null
                    || vsy1AktiviteOlusturRequest.getIhr1PersonelVerilenId() == null
                    || vsy1AktiviteOlusturRequest.getTsy1AktiviteIslemId() == null) {
                vsy1AktiviteOlusturDTO = new VSY1AktiviteOlusturDTO(null, new ErrorDTO(true, "Gerekli alanlar boş bırakılamaz!"));
            } else {
                vsy1AktiviteOlusturDTO = vsy1AktiviteRepository.createAktivite(vsy1AktiviteOlusturRequest);
            }
        } catch (Exception e) {
            vsy1AktiviteOlusturDTO = new VSY1AktiviteOlusturDTO(null, new ErrorDTO(true, e.getMessage()));
        }
        return vsy1AktiviteOlusturDTO;
    }

    public VSY1AktiviteIslemlerDTO getAktiviteIslemler(){
        return vsy1AktiviteRepository.getAktiviteIslemler();
    }
    public List<VSY1AktivitePersonDTO> getAktivitePeopleList(){
        return vsy1AktiviteRepository.getAktivitePeopleList();
    }

}
