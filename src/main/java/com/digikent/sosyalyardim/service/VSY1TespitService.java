package com.digikent.sosyalyardim.service;

import com.digikent.denetimyonetimi.dto.tespit.TespitDTO;
import com.digikent.general.util.ErrorCode;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.sosyalyardim.dao.VSY1TespitRepository;
import com.digikent.sosyalyardim.dto.*;
import com.digikent.sosyalyardim.entity.*;
import com.digikent.sosyalyardim.util.UtilSosyalYardimSaveDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                    tespitKategoriDTO.setId(item.getID());
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
                tespitSoruDTO.setId(item.getID());
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

    public UtilSosyalYardimSaveDTO saveSosyalYardimTespit(VSY1TespitKayitRequest tespitKayitRequest) throws Exception {
        UtilSosyalYardimSaveDTO utilSosyardimSaveDTO = null;
        try {
            VSY1Tespit vsy1Tespit = new VSY1Tespit();
            List<VSY1TespitLine> tespitLineList = createTespitLineList(tespitKayitRequest,vsy1Tespit);
            if (tespitLineList == null || tespitLineList.size() == 0) {
                LOG.info("Kayıt edilecek veri girişi bulunamadı");
                utilSosyardimSaveDTO = new UtilSosyalYardimSaveDTO(false,new ErrorDTO(true,"Kayıt edilecek veri girişi bulunamadı"),null);
            } else {
                vsy1TespitRepository.saveTespit(tespitKayitRequest, tespitLineList,vsy1Tespit);
                utilSosyardimSaveDTO = new UtilSosyalYardimSaveDTO(true,null,null);
            }
        } catch (Exception e) {
            utilSosyardimSaveDTO = new UtilSosyalYardimSaveDTO(false,new ErrorDTO(true,e.getMessage()),null);
            throw new Exception();
        }

        return utilSosyardimSaveDTO;
    }

    private List<VSY1TespitLine> createTespitLineList(VSY1TespitKayitRequest tespitKayitRequest,VSY1Tespit vsy1tespit) throws Exception {
        List<VSY1TespitLine> tespitLineList = new ArrayList<>();

        try {
            for (TSY1TespitKategoriDTO kategoriDTO: tespitKayitRequest.getTespitKategoriDTOList()) {

                for (TSY1TespitSoruDTO soruDTO: kategoriDTO.getSoruDTOList()) {
                    if (
                            (soruDTO.getCbrbdegeri() != null && soruDTO.getCbrbdegeri().longValue() == 1)
                                    || (soruDTO.getStnmdegeri() != null && !soruDTO.getStnmdegeri().trim().equalsIgnoreCase(""))
                                    || (soruDTO.getBilgi() != null && !soruDTO.getBilgi().trim().equalsIgnoreCase(""))
                            ) {
                        VSY1TespitLine tespitLine = new VSY1TespitLine();
                        tespitLine.setCrDate(new Date());
                        tespitLine.setDosyaId(tespitKayitRequest.getDosyaId());
                        tespitLine.setVsy1Tespit(vsy1tespit);

                        TSY1TespitSoru tespitSoru = new TSY1TespitSoru();
                        tespitSoru.setID(soruDTO.getId());
                        tespitLine.setSoru(tespitSoru);

                        TSY1TespitKategori tespitKategori = new TSY1TespitKategori();
                        tespitKategori.setID(kategoriDTO.getId());
                        tespitLine.setKategori(tespitKategori);

                        tespitLine.setDeger((soruDTO.getCbrbdegeri() != null && soruDTO.getCbrbdegeri().longValue() == 1 ? soruDTO.getCbrbdegeri() : (soruDTO.getStnmdegeri() != null && !soruDTO.getStnmdegeri().trim().equalsIgnoreCase("") ? Long.valueOf(soruDTO.getStnmdegeri().trim()) : null)));
                        tespitLine.setBilgi(soruDTO.getBilgi());
                        tespitLine.setCrDate(new Date());
                        tespitLine.setCrUser(tespitKayitRequest.getFsm1UsersId());
                        tespitLine.setIsActive(true);

                        tespitLineList.add(tespitLine);
                    }
                }

            }
        } catch (NumberFormatException ex) {
            LOG.error("Long a cevirimde hata oluştu");
            throw new Exception("Lütfen değer alanlarını sayı giriniz");
        } catch (Exception ex) {
            LOG.error("Tespit line lar oluşturulurken bir hata oluştu");
            throw new Exception("Tespit line lar oluşturulurken bir hata oluştu");
        }

        return tespitLineList;
    }

    public VSY1TespitResponse getTespitByDosyaId(Long dosyaId, Long aktiviteId) {
        VSY1TespitResponse vsy1TespitResponse = new VSY1TespitResponse();
        List<VSY1TespitDTO> tespitDTOList = null;
        List<VSY1Tespit> tespitList = null;
        try {
            tespitList = vsy1TespitRepository.findTespitByDosyaId(dosyaId, aktiviteId);
            if (tespitList != null && tespitList.size() != 0)
                tespitDTOList = convertTespitToTespitDTO(tespitList);
            vsy1TespitResponse.setVsy1TespitDTOList(tespitDTOList);
            vsy1TespitResponse.setErrorDTO(null);
        } catch (Exception ex) {
            vsy1TespitResponse.setVsy1TespitDTOList(null);
            vsy1TespitResponse.setErrorDTO(new ErrorDTO(true,ErrorCode.ERROR_CODE_603));
            LOG.error("HATA KODU : 603. Tespitler getirilirken hata oluştu.");
        }

        return vsy1TespitResponse;
    }

    private List<VSY1TespitDTO> convertTespitToTespitDTO(List<VSY1Tespit> tespitList) {
        List<VSY1TespitDTO> tespitDTOList = new ArrayList<>();

        for (VSY1Tespit item: tespitList) {
            try {
                VSY1TespitDTO tespitDTO = new VSY1TespitDTO();
                tespitDTO.setTespitBilgisi(item.getAlinanBilgi());

                if (item.getTarih() != null) {
                    tespitDTO.setTespitTarihi(new SimpleDateFormat("dd-MM-yyyy").format(item.getTarih()));
                } else {
                    if (item.getCrDate() != null) {
                        tespitDTO.setTespitTarihi(new SimpleDateFormat("dd-MM-yyyy").format(item.getCrDate()));
                    }
                }

                tespitDTO.setTespitYapan((item.getIhr1PersonelTespitYapan() != null ? item.getIhr1PersonelTespitYapan().getAdi()+" "+item.getIhr1PersonelTespitYapan().getSoyadi() : "-"));

                tespitDTO.setTespitLineDTOList((item.getVsy1TespitLineList().size() > 0 ? convertTespitLineDTOList(item.getVsy1TespitLineList()) : null));

                tespitDTOList.add(tespitDTO);
            } catch (Exception ex) {
                ex.printStackTrace();
                LOG.error("convertTespitToTespitDTO() hata oluştu, pas geçildi...");
            }
        }

        return tespitDTOList;
    }

    private List<VSY1TespitLineDTO> convertTespitLineDTOList(List<VSY1TespitLine> vsy1TespitLineList) {
        List<VSY1TespitLineDTO> vsy1TespitLineDTOList = new ArrayList<>();


            for (VSY1TespitLine item:vsy1TespitLineList) {
                try {
                    VSY1TespitLineDTO tespitLineDTO = new VSY1TespitLineDTO();
                    tespitLineDTO.setBilgi(item.getBilgi());
                    tespitLineDTO.setDeger(item.getDeger());

                    if (item.getSoru() != null) {
                        tespitLineDTO.setSoru(item.getSoru().getTanim());
                        tespitLineDTO.setSoruId(item.getSoru().getID());

                        if (item.getSoru().getTsy1TespitSoruTuru() != null) {
                            tespitLineDTO.setSoruTuru(item.getSoru().getTsy1TespitSoruTuru().getTanim());
                        }
                    }

                    vsy1TespitLineDTOList.add(tespitLineDTO);
                } catch (Exception ex) {
                    LOG.error("convertTespitLineDTOList() hata oluştu, pas geçildi...");
                }
            }

        return vsy1TespitLineDTOList;
    }
}
