package com.digikent.denetimyonetimi.service;

import com.digikent.config.Constants;
import com.digikent.denetimyonetimi.dao.DenetimReportRepository;
import com.digikent.denetimyonetimi.dao.DenetimRepository;
import com.digikent.denetimyonetimi.dto.denetim.DenetimDTO;
import com.digikent.denetimyonetimi.dto.rapor.*;
import com.digikent.denetimyonetimi.dto.tespit.TespitGrubuDTO;
import com.digikent.denetimyonetimi.entity.*;
import com.digikent.denetimyonetimi.enums.DenetimTespitKararAksiyon;
import com.digikent.general.util.ErrorCode;
import com.digikent.general.util.UtilOperationSystem;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kadir on 1.02.2018.
 */
@Service
public class DenetimReportService {

    private final Logger LOG = LoggerFactory.getLogger(DenetimReportService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    DenetimRepository denetimRepository;

    @Inject
    DenetimReportRepository denetimReportRepository;

    @Autowired
    DenetimService denetimService;

    @Autowired
    DenetimTarafService denetimTarafService;

    /**
     * Ceza raporu çıktısını html string objesi olarak döndürür
     * @param denetimTespitId
     * @return
     */
    public ReportResponse createCezaDenetimReport(Long denetimTespitId) {

        ReportResponse reportResponse = new ReportResponse();

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate("templates/template.vm", "UTF-8");
        VelocityContext vc = new VelocityContext();

        BDNTDenetimTespit bdntDenetimTespit = denetimRepository.findDenetimTespitById(denetimTespitId);
        if (bdntDenetimTespit == null)
            return throwReportResponse(ErrorCode.ERROR_CODE_501,"denetimTespitId",denetimTespitId);

        DenetimDTO denetimDTO = denetimService.getDenetimById(bdntDenetimTespit.getDenetimId());
        if (denetimDTO == null)
            return throwReportResponse(ErrorCode.ERROR_CODE_502,"denetimID",bdntDenetimTespit.getDenetimId());

        List<BDNTDenetimTespitTaraf> bdntDenetimTespitTarafList = denetimTarafService.getDenetimTarafListByDenetimId(bdntDenetimTespit.getDenetimId());
        if (bdntDenetimTespitTarafList == null)
            return throwReportResponse(ErrorCode.ERROR_CODE_503,"denetimID",bdntDenetimTespit.getDenetimId());

        denetimDTO.setOlayYeriDaireNoHarf((denetimDTO.getOlayYeriDaireNoHarf() == null ? "" : denetimDTO.getOlayYeriDaireNoHarf()));
        denetimDTO.setOlayYeriKapiNoHarf((denetimDTO.getOlayYeriKapiNoHarf() == null ? "" : denetimDTO.getOlayYeriKapiNoHarf()));

        if (Constants.DENETIM_TARAFTIPI_SAHIS.equalsIgnoreCase(denetimDTO.getDenetimTarafTipi())) {
            vc.put("userDTO", getSahisByDenetimTarafList(bdntDenetimTespitTarafList));
        } else if (Constants.DENETIM_TARAFTIPI_KURUM.equalsIgnoreCase(denetimDTO.getDenetimTarafTipi())) {
            vc.put("kurumDTO", getKurumPaydasInformation(denetimDTO.getPaydasId()));
        }

        List<BelediyeUserDTO> belediyeUserDTOList = getBelediyeUserDTOListByDenetimTarafList(bdntDenetimTespitTarafList);
        Nsm2Parametre nsm2Parametre = denetimReportRepository.getNSM2Parametre();
        TespitGrubuDTO tespitGrubuDTO = denetimRepository.findTespitGrubuDTOById(bdntDenetimTespit.getTespitGrubuId());
        ReportKararDTO reportKararDTO = getReportKararDTO(bdntDenetimTespit);


        vc.put("belediyeUserDTOList", (belediyeUserDTOList.size() == 0 ? null : belediyeUserDTOList));
        vc.put("locationDTO", getLocationReportDTOByDenetimDTO(denetimDTO));
        vc.put("documentDTO", new DocumentDTO(new SimpleDateFormat("dd-MM-yyyy").format(new Date()), "147852369"));
        vc.put("reportTespitDTOs", getTespitReportDataByTespitTur(bdntDenetimTespit, Constants.TESPIT_TUR_TESPIT));
        vc.put("reportEkBilgiDTOs", getTespitReportDataByTespitTur(bdntDenetimTespit, Constants.TESPIT_TUR_EKBILGI));
        vc.put("tebligEdilenBilgileri", getTebligBilgileri(denetimDTO));
        vc.put("logoBase64", getBase64StringLOGO());
        vc.put("belediyeAdi", (nsm2Parametre.getBelediyeAdi() == null ? "" : nsm2Parametre.getBelediyeAdi()));
        vc.put("ilAdi", (nsm2Parametre.getIlAdi() == null ? "" : nsm2Parametre.getIlAdi()));
        vc.put("tespitBaslik", (tespitGrubuDTO != null ? tespitGrubuDTO.getKayitOzelIsmi() : " "));
        vc.put("karar", getReportKararDTO(bdntDenetimTespit));


        StringWriter sw = new StringWriter();
        t.merge(vc, sw);
        reportResponse.setHtmlContent(sw.toString());

        return reportResponse;
    }

    private UserDTO getKurumPaydasInformation(Long paydasId) {
        UserDTO userDTO = new UserDTO();
        MPI1Paydas paydas = denetimTarafService.getMpi1PaydasById(paydasId);
        userDTO.setKurum("KURUM");
        userDTO.setUnvan((paydas.getUnvan() != null ? paydas.getUnvan() : "-"));
        userDTO.setVergiNo((paydas.getVergiNumarasi() != null ? paydas.getVergiNumarasi() : "-"));

        return userDTO;
    }

    private ReportKararDTO getReportKararDTO(BDNTDenetimTespit bdntDenetimTespit) {
        ReportKararDTO reportKararDTO = new ReportKararDTO();
        if (DenetimTespitKararAksiyon.CEZA.toString().equalsIgnoreCase(bdntDenetimTespit.getDenetimAksiyonu())) {
            reportKararDTO.setCeza(DenetimTespitKararAksiyon.CEZA.toString());
            reportKararDTO.setCezaMiktari((bdntDenetimTespit.getCezaMiktari() != null ? bdntDenetimTespit.getCezaMiktari().toString() + " TL" : "-"));
        }

        if (DenetimTespitKararAksiyon.KAPAMA.toString().equalsIgnoreCase(bdntDenetimTespit.getDenetimAksiyonu())) {
            reportKararDTO.setKapama(DenetimTespitKararAksiyon.KAPAMA.toString());
            reportKararDTO.setKapamaBaslangicTarihi((bdntDenetimTespit.getKapamaBaslangicTarihi() != null ? (new SimpleDateFormat("dd-MM-yyyy").format(bdntDenetimTespit.getKapamaBaslangicTarihi())) : "-"));
            reportKararDTO.setKapamaBitisTarihi((bdntDenetimTespit.getKapamaBitisTarihi() != null ? (new SimpleDateFormat("dd-MM-yyyy").format(bdntDenetimTespit.getKapamaBitisTarihi())) : "-"));
        }

        if (DenetimTespitKararAksiyon.EKSURE.toString().equalsIgnoreCase(bdntDenetimTespit.getDenetimAksiyonu())) {
            reportKararDTO.setEkSure(DenetimTespitKararAksiyon.EKSURE.toString());
            reportKararDTO.setEkSureZaman((bdntDenetimTespit.getVerilenSure() != null ? bdntDenetimTespit.getVerilenSure() + " GÜN" : "-"));
        }

        if (DenetimTespitKararAksiyon.BELIRSIZ.toString().equalsIgnoreCase(bdntDenetimTespit.getDenetimAksiyonu())) {
            reportKararDTO.setBelirsiz(DenetimTespitKararAksiyon.BELIRSIZ.toString());
        }

        return reportKararDTO;
    }

    private List<BelediyeUserDTO> getBelediyeUserDTOListByDenetimTarafList(List<BDNTDenetimTespitTaraf> bdntDenetimTespitTarafList) {
        List<BelediyeUserDTO> belediyeUserDTOList = new ArrayList<>();
        for (BDNTDenetimTespitTaraf item: bdntDenetimTespitTarafList) {
            try {
                if (Constants.DENETIM_TARAF_TURU_BELEDIYE.equalsIgnoreCase(item.getTarafTuru())) {
                    BelediyeUserDTO belediyeUserDTO = new BelediyeUserDTO();
                    belediyeUserDTO.setAdiSoyadi(item.getAdi() + " " + item.getSoyadi());
                    belediyeUserDTO.setGorevi((item.getGorevi() == null ? " " : item.getGorevi()));
                    belediyeUserDTO.setTarafTuru((item.getTarafTuru() == null ? " " : item.getTarafTuru()));
                    belediyeUserDTOList.add(belediyeUserDTO);
                }
            } catch (Exception ex) {
                LOG.error("getBelediyeUserDTOListByDenetimTarafList() hata olustu.  ERROR=" + ex.getMessage());
                return null;
            }
        }
        return belediyeUserDTOList;
    }

    private UserDTO getSahisByDenetimTarafList(List<BDNTDenetimTespitTaraf> bdntDenetimTespitTarafList) {
        UserDTO paydas = null;
        try {
            for (BDNTDenetimTespitTaraf item: bdntDenetimTespitTarafList) {
                if (Constants.DENETIM_TARAF_TURU_PAYDAS.equalsIgnoreCase(item.getTarafTuru())) {
                    paydas = new UserDTO();
                    paydas.setAdiSoyadi((item.getAdi() != null && item.getSoyadi() != null) ? item.getAdi() + " " + item.getSoyadi() : "-");
                    paydas.setTckn((item.getTcKimlikNo() != null ? item.getTcKimlikNo().toString() : "-"));
                    paydas.setTarafTuru((item.getTarafTuru() != null ? item.getTarafTuru() : "-"));
                    return paydas;
                }
            }
        } catch (Exception ex) {
            LOG.error("getSahisByDenetimTarafList() hata olustu. ERROR="+ex.getMessage());
            return null;
        }
        return paydas;
    }

    private ReportResponse throwReportResponse(String errorMessage,String variableId,Long id){
        LOG.error(errorMessage + ". " + variableId + "=" +id);
        ReportResponse reportResponse = new ReportResponse();
        reportResponse.setErrorDTO(new ErrorDTO(true,errorMessage));
        return reportResponse;
    }

    /**
     * DIGIKENT_PATH altındaki logonun base64 halini string olarak döndürür
     * @return
     */
    private String getBase64StringLOGO() {
        try {
            String logoPath = System.getenv("DIGIKENT_PATH") + "\\logo\\logo.png";
            if (!UtilOperationSystem.isWindows()) {
                logoPath = System.getenv("DIGIKENT_PATH") + "/logo/logo.png";
            }

            File file = new File(logoPath);
            FileInputStream fs = null;
            String encodedFile = "";
            try {
                encodedFile = new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray(file)));
            } catch (IOException e) {
                LOG.debug("Logo getirilirken bir hata olustu");
                LOG.debug("Logo bulunamamis olabilir.");
                e.printStackTrace();
            }
            return encodedFile;
        } catch (Exception ex) {
            LOG.error("getBase64StringLOGO() LOGO getirilirken hata olustu");
            return null;
        }

    }

    /**
     * Rapor için Lokasyon bilgilerini döndürür
     * @param denetimDTO
     * @return
     */
    private LocationDTO getLocationReportDTOByDenetimDTO(DenetimDTO denetimDTO) {
        try {
            LocationDTO locationDTO = new LocationDTO();
            if (denetimDTO != null) {
                locationDTO.getTamAdres();
                locationDTO.setDaireBilgisi((denetimDTO.getOlayYeriDaireNoSayi() != null ? denetimDTO.getOlayYeriDaireNoSayi().toString() + "/" + denetimDTO.getOlayYeriDaireNoHarf() : denetimDTO.getOlayYeriDaireNoHarf()));
                locationDTO.setKapiBilgisi((denetimDTO.getOlayYeriKapiNoSayi() != null ? denetimDTO.getOlayYeriKapiNoSayi().toString() + "/" + denetimDTO.getOlayYeriKapiNoHarf() : denetimDTO.getOlayYeriKapiNoHarf()));
                locationDTO.setIlceAdi(denetimDTO.getOlayYeriIlce());
                locationDTO.setMahalleAdi(denetimDTO.getOlayYeriMahalle());
                locationDTO.setSokakAdi(denetimDTO.getOlayYeriSokak());
            }

            return locationDTO;
        } catch (Exception ex) {
            LOG.error("getLocationReportDTOByDenetimDTO() hata olustu. ERROR="+ex.getMessage());
            return null;
        }

    }

    /**
     * Rapor için denetim tespite ait denetimleri setlenmiş şekilde getirir
     * @param bdntDenetimTespit
     * @return
     */
    public List<ReportTespitDTO> getTespitReportDataByTespitTur(BDNTDenetimTespit bdntDenetimTespit, String tur) {
        //List<Long> tespitIdList = new ArrayList<>();
        Set<Long> tespitIdSet = new HashSet<>();
        for (BDNTDenetimTespitLine tespitLine:bdntDenetimTespit.getBdntDenetimTespitLineList()) {
            if (tespitLine.getIsActive()) {
                tespitIdSet.add(tespitLine.getTespitId());
            }
        }
        Map<Long,LDNTTespit> tespitMap = denetimRepository.findTespitMapByTespitIdList(tespitIdSet);
        if (tespitMap==null)
            return null;

        List<ReportTespitDTO> reportTespitDTOs = new ArrayList<>();
        for (BDNTDenetimTespitLine denetimTespitLine:bdntDenetimTespit.getBdntDenetimTespitLineList()) {
            try {
                LDNTTespit ldntTespit = tespitMap.get(denetimTespitLine.getTespitId());
                ReportTespitDTO reportTespitDTO = new ReportTespitDTO();
                if (tur != null && tur.equalsIgnoreCase(ldntTespit.getTur())) {
                    reportTespitDTO.setTespitAciklamasi(ldntTespit.getTanim());
                    reportTespitDTO.setAciklama(denetimTespitLine.getTextValue());
                    reportTespitDTO.setDayanakKanunu(ldntTespit.getLsm2Kanun().getTanim());
                    reportTespitDTO.setTur(ldntTespit.getTur());
                    //tespit secenek türüne göre setleme yapılıyor
                    if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_CHECHBOX) && denetimTespitLine.getStringValue() != null) {
                        reportTespitDTO.setDeger(denetimTespitLine.getStringValue());
                    } else if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_TEXT) && denetimTespitLine.getStringValue() != null) {
                        reportTespitDTO.setDeger(denetimTespitLine.getStringValue());
                    } else if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_DATE) && denetimTespitLine.getDateValue() != null) {
                        reportTespitDTO.setDeger(new SimpleDateFormat("dd-MM-yyyy").format(denetimTespitLine.getDateValue()).toString());
                    } else if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_NUMBER) && denetimTespitLine.getNumberValue() != null) {
                        reportTespitDTO.setDeger(denetimTespitLine.getNumberValue().toString());
                    } else {
                        reportTespitDTO.setDeger(" ");
                    }
                    reportTespitDTOs.add(reportTespitDTO);
                }
            } catch (Exception ex) {
                LOG.error("getTespitReportDataByTespitTur() setleme problemi yasandi. Pas gecilen tespitLineID=" + denetimTespitLine.getID());
                LOG.error("HATA="+ex.getMessage());
            }

        }
        return reportTespitDTOs;
    }

    public TebligEdilenDTO getTebligBilgileri(DenetimDTO denetimDTO) {
        try {
            TebligEdilenDTO tebligEdilenDTO = new TebligEdilenDTO();
            tebligEdilenDTO.setAdi((denetimDTO.getTebligAdi() == null ? " " : denetimDTO.getTebligAdi()));
            tebligEdilenDTO.setSoyadi((denetimDTO.getTebligSoyadi() == null ? " " : denetimDTO.getTebligSoyadi()));
            tebligEdilenDTO.setTCKimlikNo((denetimDTO.getTebligTCKimlikNo() == null ? 0 : denetimDTO.getTebligTCKimlikNo()));
            return tebligEdilenDTO;
        } catch (Exception ex) {
            LOG.error("getTebligBilgileri() hata olustu. TebligTCNO="+denetimDTO.getTebligTCKimlikNo());
            return null;
        }
    }


}
