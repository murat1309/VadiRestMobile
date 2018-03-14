package com.digikent.denetimyonetimi.service;

import com.digikent.config.Constants;
import com.digikent.denetimyonetimi.dao.DenetimRepository;
import com.digikent.denetimyonetimi.dto.denetim.DenetimDTO;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitDTO;
import com.digikent.denetimyonetimi.dto.velocity.*;
import com.digikent.denetimyonetimi.entity.BDNTDenetimTespit;
import com.digikent.denetimyonetimi.entity.BDNTDenetimTespitLine;
import com.digikent.denetimyonetimi.entity.BDNTDenetimTespitTaraf;
import com.digikent.denetimyonetimi.entity.LDNTTespit;
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
import java.io.StringWriter;
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

    @Autowired
    DenetimService denetimService;

    @Autowired
    DenetimTarafService denetimTarafService;

    /**
     * Ceza raporu çıktısını html string objesi olarak döndürür
     * @param denetimTespitId
     * @return
     */
    public String createCezaDenetimReport(Long denetimTespitId) {

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate("templates/template.vm", "UTF-8");
        VelocityContext vc = new VelocityContext();

        BDNTDenetimTespit bdntDenetimTespit = denetimRepository.findDenetimTespitById(denetimTespitId);
        DenetimDTO denetimDTO = denetimService.getDenetimById(bdntDenetimTespit.getDenetimId());
        List<BDNTDenetimTespitTaraf> bdntDenetimTespitTarafList = denetimTarafService.getDenetimTarafListByDenetimId(bdntDenetimTespit.getDenetimId());
        List<BelediyeUserDTO> belediyeUserDTOList = new ArrayList<>();

        denetimDTO.setOlayYeriDaireNoHarf((denetimDTO.getOlayYeriDaireNoHarf() == null ? "" : denetimDTO.getOlayYeriDaireNoHarf()));
        denetimDTO.setOlayYeriKapiNoHarf((denetimDTO.getOlayYeriKapiNoHarf() == null ? "" : denetimDTO.getOlayYeriKapiNoHarf()));

        for (BDNTDenetimTespitTaraf item: bdntDenetimTespitTarafList) {
            if (Constants.DENETIM_TARAF_TURU_PAYDAS.equalsIgnoreCase(item.getTarafTuru())) {
                UserDTO userDTO = new UserDTO();
                userDTO.setAdiSoyadi(item.getAdi() + " " + item.getSoyadi());
                userDTO.setTckn(item.getTcKimlikNo());
                userDTO.setTarafTuru(item.getTarafTuru());
                vc.put("userDTO", userDTO);
            } else if (Constants.DENETIM_TARAF_TURU_BELEDIYE.equalsIgnoreCase(item.getTarafTuru())) {
                BelediyeUserDTO belediyeUserDTO = new BelediyeUserDTO();
                belediyeUserDTO.setAdiSoyadi(item.getAdi() + " " + item.getSoyadi());
                belediyeUserDTO.setGorevi(item.getGorevi());
                belediyeUserDTO.setTarafTuru(item.getTarafTuru());
                belediyeUserDTOList.add(belediyeUserDTO);
            }
        }

        vc.put("belediyeUserDTOList", belediyeUserDTOList);
        vc.put("locationDTO", getLocationReportDTOByDenetimDTO(denetimDTO));
        vc.put("documentDTO", new DocumentDTO(new SimpleDateFormat("dd-MM-yyyy").format(new Date()), "147852369"));
        vc.put("reportTespitDTOs", getTespitReportData(bdntDenetimTespit));
        vc.put("tebligEdilenBilgileri", getTebligBilgileri(denetimDTO));

        StringWriter sw = new StringWriter();
        t.merge(vc, sw);

        return sw.toString();
    }

    /**
     * Rapor için Lokasyon bilgilerini döndürür
     * @param denetimDTO
     * @return
     */
    private LocationDTO getLocationReportDTOByDenetimDTO(DenetimDTO denetimDTO) {
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
    }

    /**
     * Rapor için denetim tespite ait denetimleri setlenmiş şekilde getirir
     * @param bdntDenetimTespit
     * @return
     */
    public List<ReportTespitDTO> getTespitReportData(BDNTDenetimTespit bdntDenetimTespit) {
        //List<Long> tespitIdList = new ArrayList<>();
        Set<Long> tespitIdSet = new HashSet<>();
        for (BDNTDenetimTespitLine tespitLine:bdntDenetimTespit.getBdntDenetimTespitLineList()) {
            if (tespitLine.getIsActive()) {
                tespitIdSet.add(tespitLine.getTespitId());
            }
        }
        Map<Long,LDNTTespit> tespitMap = denetimRepository.findTespitMapByTespitIdList(tespitIdSet);

        List<ReportTespitDTO> reportTespitDTOs = new ArrayList<>();
        for (BDNTDenetimTespitLine denetimTespitLine:bdntDenetimTespit.getBdntDenetimTespitLineList()) {
            LDNTTespit ldntTespit = tespitMap.get(denetimTespitLine.getTespitId());
            if (ldntTespit.getAksiyon().equalsIgnoreCase(Constants.TESPIT_AKSIYON_TYPE_CEZA) || ldntTespit.getAksiyon().equalsIgnoreCase(Constants.TESPIT_AKSIYON_TYPE_EKBILGI) || ldntTespit.getAksiyon().equalsIgnoreCase(Constants.TESPIT_AKSIYON_TYPE_TUTANAK)) {
                ReportTespitDTO reportTespitDTO = new ReportTespitDTO();
                reportTespitDTO.setTespitAciklamasi(ldntTespit.getTanim());
                reportTespitDTO.setAciklama(denetimTespitLine.getTextValue());
                reportTespitDTO.setDayanakKanunu(ldntTespit.getLsm2Kanun().getTanim());
                //tespit secenek türüne göre setleme yapılıyor
                if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_CHECHBOX) && denetimTespitLine.getStringValue() != null) {
                    reportTespitDTO.setDeger(denetimTespitLine.getStringValue());
                } else if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_TEXT) && denetimTespitLine.getStringValue() != null) {
                    reportTespitDTO.setDeger(denetimTespitLine.getStringValue());
                } else if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_DATE) && denetimTespitLine.getDateValue() != null) {
                    reportTespitDTO.setDeger(denetimTespitLine.getDateValue().toString());
                } else if (ldntTespit.getSecenekTuru().equalsIgnoreCase(Constants.TESPIT_SECENEK_TURU_NUMBER) && denetimTespitLine.getNumberValue() != null) {
                    reportTespitDTO.setDeger(denetimTespitLine.getNumberValue().toString());
                } else {
                    reportTespitDTO.setDeger(" ");
                }
                reportTespitDTOs.add(reportTespitDTO);
            }
        }
        return reportTespitDTOs;
    }

    public TebligEdilenDTO getTebligBilgileri(DenetimDTO denetimDTO) {
        TebligEdilenDTO tebligEdilenDTO = new TebligEdilenDTO();
        tebligEdilenDTO.setAdi((denetimDTO.getTebligAdi() == null ? " " : denetimDTO.getTebligAdi()));
        tebligEdilenDTO.setSoyadi((denetimDTO.getTebligSoyadi() == null ? " " : denetimDTO.getTebligSoyadi()));
        tebligEdilenDTO.setTCKimlikNo((denetimDTO.getTebligTCKimlikNo() == null ? 0 : denetimDTO.getTebligTCKimlikNo()));
        return tebligEdilenDTO;
    }


}
