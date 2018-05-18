package com.digikent.sosyalyardim.yeni.service;

import com.digikent.general.util.ErrorCode;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.sosyalyardim.yeni.dao.VSY1TespitRepository;
import com.digikent.sosyalyardim.yeni.dto.TSY1TespitKategoriDTO;
import com.digikent.sosyalyardim.yeni.dto.TSY1TespitSoruDTO;
import com.digikent.sosyalyardim.yeni.dto.TSY1TespitSoruTuruDTO;
import com.digikent.sosyalyardim.yeni.dto.VSY1TespitSorulariResponse;
import com.digikent.sosyalyardim.yeni.entity.TSY1TespitKategori;
import com.digikent.sosyalyardim.yeni.entity.TSY1TespitSoru;
import com.digikent.sosyalyardim.yeni.entity.TSY1TespitSoruTuru;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 14.05.2018.
 */
@Service
public class VSY1TespitService {

    private final Logger LOG = LoggerFactory.getLogger(VSY1TespitService.class);

    @Inject
    VSY1TespitRepository vsy1TespitRepository;

    @Cacheable(value = "tespitsorulari", key = "#root.methodName.toString()")
    public VSY1TespitSorulariResponse getTespitSorulariResponse() {
        VSY1TespitSorulariResponse tespitSorulariResponse = null;
        List<TSY1TespitKategoriDTO> vsy1TespitSorulariDTOList = null;
        List<TSY1TespitKategori> tsy1TespitKategoriList = null;

        tsy1TespitKategoriList = vsy1TespitRepository.findTespitSorulariList();

        if (tsy1TespitKategoriList != null && tsy1TespitKategoriList.size() != 0) {
            vsy1TespitSorulariDTOList = convertTespitKategoriToDTO(tsy1TespitKategoriList);
            if (vsy1TespitSorulariDTOList != null) {
                tespitSorulariResponse = new VSY1TespitSorulariResponse(vsy1TespitSorulariDTOList,new ErrorDTO(false,null));
            } else {
                tespitSorulariResponse = new VSY1TespitSorulariResponse(null,new ErrorDTO(true,ErrorCode.ERROR_CODE_602));
            }
        } else {
            tespitSorulariResponse = new VSY1TespitSorulariResponse(null,new ErrorDTO(true, ErrorCode.ERROR_CODE_601));
        }
        return tespitSorulariResponse;
    }

    private List<TSY1TespitKategoriDTO> convertTespitKategoriToDTO(List<TSY1TespitKategori> tsy1TespitKategoriList) {
        List<TSY1TespitKategoriDTO> tespitKategoriDTOList = new ArrayList<>();
        try {
            for (TSY1TespitKategori item:tsy1TespitKategoriList) {
                //soru yoksa, kategoriyi göndermeye gerek yok
                if (item.getTsy1TespitSoruList() != null && item.getTsy1TespitSoruList().size() != 0) {
                    TSY1TespitKategoriDTO tespitKategoriDTO = new TSY1TespitKategoriDTO();
                    tespitKategoriDTO.setTanim(item.getTanim());
                    tespitKategoriDTO.setAktif(item.getAktif());
                    tespitKategoriDTO.setDosyaBirey(item.getDosyabirey());
                    tespitKategoriDTO.setKayitOzelIsmi(item.getKayitOzelIsmi());
                    if (item.getTsy1TespitSoruList() != null && item.getTsy1TespitSoruList().size() != 0) {
                        tespitKategoriDTO.setSoruDTOList(convertTespitSoruListToDTOList(item.getTsy1TespitSoruList()));
                    }

                    tespitKategoriDTOList.add(tespitKategoriDTO);
                }
            }
        } catch (Exception ex) {
            LOG.error("Tespit kategorileri/sorulari donusturulurken bir hata olustu. Mesaj : " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }

        return tespitKategoriDTOList;
    }

    private List<TSY1TespitSoruDTO> convertTespitSoruListToDTOList(List<TSY1TespitSoru> tsy1TespitSoruList) {
        List<TSY1TespitSoruDTO> tespitSoruDTOList = new ArrayList<>();
        try {
            for (TSY1TespitSoru item: tsy1TespitSoruList) {
                TSY1TespitSoruDTO tespitSoruDTO = new TSY1TespitSoruDTO();
                tespitSoruDTO.setTanim(item.getTanim());
                tespitSoruDTO.setKayitOzelIsmi(item.getKayitOzelIsmi());
                tespitSoruDTO.setAktif(item.getAktif());
                tespitSoruDTO.setCevap(item.getCevap());
                tespitSoruDTO.setTespitSoruTuruDTO(convertTespitSoruTuruToDTO(item.getTsy1TespitSoruTuru()));
                tespitSoruDTO.setBilgi("");
                tespitSoruDTO.setCbrbdegeri(0l);
                tespitSoruDTO.setStnmdegeri("");
                tespitSoruDTOList.add(tespitSoruDTO);
            }
        } catch (Exception ex) {
            LOG.error("convertTespitSoruListToDTOList metotunda hata verdi. Devam ediliyor.");
            ex.printStackTrace();
        }


        return tespitSoruDTOList;
    }

    private TSY1TespitSoruTuruDTO convertTespitSoruTuruToDTO(TSY1TespitSoruTuru tsy1TespitSoruTuru) {
        TSY1TespitSoruTuruDTO tespitSoruTuruDTO = null;
        try {
            tespitSoruTuruDTO = new TSY1TespitSoruTuruDTO();
            tespitSoruTuruDTO.setTanim(tsy1TespitSoruTuru.getTanim());
            tespitSoruTuruDTO.setSira(tsy1TespitSoruTuru.getSira());
        } catch (Exception ex) {
            LOG.error("convertTespitSoruTuruToDTO metotunda hata verdi. Devam ediliyor.");
            ex.printStackTrace();
        }
        return tespitSoruTuruDTO;
    }
}
