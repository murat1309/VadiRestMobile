package com.digikent.denetimyonetimi.rest;

import com.digikent.config.Constants;
import com.digikent.denetimyonetimi.dto.denetim.*;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimIsletmeDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitGrubuDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitlerRequest;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.service.DenetimAddressService;
import com.digikent.denetimyonetimi.service.DenetimReportService;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.paydasiliskileri.service.PaydasIliskileriManagementService;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasRequestDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasResponseDTO;
import com.digikent.denetimyonetimi.service.DenetimService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Kadir on 25.01.2018.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/denetim")
public class DenetimResource {

    private final Logger LOG = LoggerFactory.getLogger(DenetimResource.class);

    @Autowired
    PaydasIliskileriManagementService paydasIliskileriManagementService;

    @Autowired
    DenetimService denetimService;

    @Autowired
    DenetimReportService denetimReportService;

    @Autowired
    DenetimAddressService denetimAddressService;

    /*
        denetimyonetimi - paydas search by single filter
    */
    @RequestMapping(value = "/paydas/search", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<DenetimPaydasResponseDTO> getPaydasListByCriteria(@RequestBody DenetimPaydasRequestDTO denetimPaydasRequestDTO) {
        LOG.debug("Denetim - paydas/search Gelen Paydas Arama Kriteri : " + denetimPaydasRequestDTO.getFilter());

        DenetimPaydasResponseDTO denetimPaydasResponseDTO = null;
        if(denetimPaydasRequestDTO.getFilter() != null && !denetimPaydasRequestDTO.getFilter().isEmpty()) {
            denetimPaydasResponseDTO = paydasIliskileriManagementService.getPaydasInfoByDenetimFilter(denetimPaydasRequestDTO);
        } else {
            denetimPaydasResponseDTO.setErrorDTO(new ErrorDTO(true, Constants.ERROR_MESSAGE_PAYDAS_MIN_CHARACHTER_SIZE));
        }

        return new ResponseEntity<DenetimPaydasResponseDTO>(denetimPaydasResponseDTO, OK);
    }

    /*
        taraflar bilgisi girildikten  sonra denetim kaydı oluşturuluyor
        eğer denetimId bilgisi null gelmezse, güncellenecek demektir.
    */
    @RequestMapping(value = "/save/denetim", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UtilDenetimSaveDTO> saveDenetim(@RequestBody DenetimRequest denetimRequest) {
        LOG.debug("Denetim kayit/guncelleme. paydaş ID : " + denetimRequest.getDenetimPaydasDTO().getPaydasNo());
        UtilDenetimSaveDTO utilDenetimSaveDTO = new UtilDenetimSaveDTO();
        utilDenetimSaveDTO = denetimService.saveDenetim(denetimRequest);
        LOG.debug("denetim kayit/guncelleme islemi tamamlandi. SONUC = " + utilDenetimSaveDTO.getSaved());
        LOG.debug("denetimID="+utilDenetimSaveDTO.getRecordId());
        return new ResponseEntity<UtilDenetimSaveDTO>(utilDenetimSaveDTO, OK);
    }

    /*
        Denetim türlerini getirir
     */
    @RequestMapping(value = "/list/denetimturu", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<DenetimTuruDTO>> getDenetimTuruList() {
        LOG.debug("REST request to get all denetim turu ");
        List<DenetimTuruDTO> denetimTuruDTOList = null;
        denetimTuruDTOList = denetimService.getDenetimTuruDTOList();

        return new ResponseEntity<List<DenetimTuruDTO>>(denetimTuruDTOList, OK);
    }

    /*
        Denetim Türü Id'sine göre tespit gruplarını getirir
    */
    @RequestMapping(value = "/list/tespitgrubu/{denetimTuruId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<TespitGrubuDTO>> getTespitGrubuListByDenetimTuru(@PathVariable("denetimTuruId") Long denetimTuruId) {
        LOG.debug("REST request to get all tespit grubu by denetim turu Id = " + denetimTuruId);
        List<TespitGrubuDTO> tespitGrubuDTOList = null;
        tespitGrubuDTOList = denetimService.getTespitGrubuDTOListByDenetimTuruId(denetimTuruId);

        return new ResponseEntity<List<TespitGrubuDTO>>(tespitGrubuDTOList, OK);
    }

    /*
        tespit grubu Id'sine göre tespitleri getirir
    */
    @RequestMapping(value = "/list/tespit/{tespitGrubuId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<TespitDTO>> getTespitDTOListByTespitGrubuId(@PathVariable("tespitGrubuId") Long tespitGrubuId) {
        LOG.debug("REST request to get all tespit by tespit grubu Id = " + tespitGrubuId);
        List<TespitDTO> tespitDTOs = null;
        tespitDTOs = denetimService.getTespitDTOListByTespitGrubuId(tespitGrubuId);

        return new ResponseEntity<List<TespitDTO>>(tespitDTOs, OK);
    }

    /*
        paydaş id'e göre işletme listesini getirir
    */
    @RequestMapping(value = "/list/isletme/{paydasId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<DenetimIsletmeDTO>> getIsletmeDTOListByPaydasId(@PathVariable("paydasId") Long paydasId) {
        LOG.debug("REST list/isletme/{paydasId} request to get isletme list by paydas Id = " + paydasId);
        List<DenetimIsletmeDTO> denetimIsletmeDTOList = null;
        denetimIsletmeDTOList = denetimService.getIsletmeDTOListByPaydasId(paydasId);

        return new ResponseEntity<List<DenetimIsletmeDTO>>(denetimIsletmeDTOList, OK);
    }

    /*
        şahıs/kurum paydaş kayıt
    */
    @RequestMapping(value = "/save/paydas", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UtilDenetimSaveDTO> saveSahisPaydas(@RequestBody DenetimPaydasDTO denetimPaydasDTO) {
        LOG.debug("Paydaş kayit islemi yapilacak");
        UtilDenetimSaveDTO utilDenetimSaveDTO = new UtilDenetimSaveDTO();
        utilDenetimSaveDTO = denetimService.savePaydasAllInformation(denetimPaydasDTO);
        LOG.debug("Paydaş kayit islemi tamamlandi. SONUC = " + utilDenetimSaveDTO.getSaved());
        LOG.debug("PaydasId="+utilDenetimSaveDTO.getRecordId());
        return new ResponseEntity<UtilDenetimSaveDTO>(utilDenetimSaveDTO, OK);
    }

    /*
        şahıs/kurum paydaş kayıt
    */
    @RequestMapping(value = "/save/isletme", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UtilDenetimSaveDTO> saveIsletme(@RequestBody DenetimIsletmeDTO denetimIsletmeDTO) {
        LOG.debug("Isletme kayit islemi yapilacak paydasID="+denetimIsletmeDTO.getPaydasId());
        UtilDenetimSaveDTO utilDenetimSaveDTO = new UtilDenetimSaveDTO();
        utilDenetimSaveDTO = denetimService.saveIsletme(denetimIsletmeDTO);
        LOG.debug("Isletme kayit islemi tamamlandi. SONUC = " + utilDenetimSaveDTO.getSaved());
        LOG.debug("isletme="+utilDenetimSaveDTO.getRecordId());
        return new ResponseEntity<UtilDenetimSaveDTO>(utilDenetimSaveDTO, OK);
    }

    /*
        denetim türü - Tespit Grubu kaydedilir
    */
    @RequestMapping(value = "/save/denetimtespit", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UtilDenetimSaveDTO> saveDenetimTespit(@RequestBody DenetimTespitRequest denetimTespitRequest) {
        LOG.debug("denetim - denetim türü - tespit grubu kayit/guncelleme islemi yapilacak denetimID="+denetimTespitRequest.getDenetimId());
        LOG.debug("denetim - denetim türü - tespit grubu kayit/guncelleme islemi yapilacak denetimturuID="+denetimTespitRequest.getDenetimTuruId());
        LOG.debug("denetim - denetim türü - tespit grubu kayit/guncelleme islemi yapilacak tespitGrubuId="+denetimTespitRequest.getTespitGrubuId());
        UtilDenetimSaveDTO utilDenetimSaveDTO = new UtilDenetimSaveDTO();
        utilDenetimSaveDTO = denetimService.saveDenetimTespit(denetimTespitRequest);
        LOG.debug("denetim - denetim türü - tespit grubu kayit/guncelleme islemi tamamlandi. SONUC = " + utilDenetimSaveDTO.getSaved());
        LOG.debug("denetimTespitID="+utilDenetimSaveDTO.getRecordId());
        return new ResponseEntity<UtilDenetimSaveDTO>(utilDenetimSaveDTO, OK);
    }

    /*
        denetime ait denetimtespit kayıtlarını getirir
    */
    @RequestMapping(value = "/list/denetimtespit/{denetimId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<DenetimTespitDTO>> getDenetimTespitByDenetimId(@PathVariable("denetimId") Long denetimId) {
        LOG.debug("REST denetimtespitler getirilecek. denetimID= " + denetimId);
        List<DenetimTespitDTO> denetimTespitDTOList = null;
        denetimTespitDTOList = denetimService.getDenetimTespitByDenetimId(denetimId);
        return new ResponseEntity<List<DenetimTespitDTO>>(denetimTespitDTOList, OK);
    }

    /*
        denetime ait denetimtespit kayıtlarını getirir
    */
    @RequestMapping(value = "/list/denetimler", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<DenetimDTO>> getDenetimList() {
        LOG.debug("REST denetimler geitirilecek");
        List<DenetimDTO> denetimDTOList = null;
        denetimDTOList = denetimService.getDenetimList();
        return new ResponseEntity<List<DenetimDTO>>(denetimDTOList, OK);
    }

    /*
        tespitler kaydedilir
    */
    @RequestMapping(value = "/save/tespitler", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UtilDenetimSaveDTO> saveTespits(@RequestBody TespitlerRequest tespitlerRequest) {
        LOG.debug("tespitler kayit/guncelleme islemi yapilacak");
        UtilDenetimSaveDTO utilDenetimSaveDTO = new UtilDenetimSaveDTO();
        utilDenetimSaveDTO = denetimService.saveTespitler(tespitlerRequest);
        LOG.debug("tespitler kayit islemi tamamlandi. SONUC = " + utilDenetimSaveDTO.getSaved());
        return new ResponseEntity<UtilDenetimSaveDTO>(utilDenetimSaveDTO, OK);
    }

    @RequestMapping(value = "/deneme", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Boolean> dene() {
        denetimService.saveTelefon(547898457l,2342342l,5000000l);
        return new ResponseEntity<Boolean>(true, OK);
    }

}
