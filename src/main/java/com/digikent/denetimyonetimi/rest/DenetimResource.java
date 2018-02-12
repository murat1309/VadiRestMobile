package com.digikent.denetimyonetimi.rest;

import com.digikent.config.Constants;
import com.digikent.denetimyonetimi.dto.denetim.*;
import com.digikent.denetimyonetimi.dto.paydas.DenetimIsletmeDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitGrubuDTO;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.dto.velocity.ReportResponse;
import com.digikent.denetimyonetimi.service.ReportService;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.paydasiliskileri.service.PaydasIliskileriManagementService;
import com.digikent.denetimyonetimi.dto.adres.BelediyeDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleSokakDTO;
import com.digikent.denetimyonetimi.dto.adres.SokakDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasRequestDTO;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasResponseDTO;
import com.digikent.denetimyonetimi.service.DenetimService;
import com.documentum.xml.xpath.operations.Bool;
import com.vadi.smartkent.datamodel.domains.paydas.PI1Paydas;
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
    ReportService reportService;

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
        denetimyonetimi - paydas search by single filter
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<DenetimResponse> saveDenetim(@RequestBody DenetimRequest denetimRequest) {
        LOG.debug("Denetim kayıt. paydaş ID : " + denetimRequest.getDenetimPaydasDTO().getPaydasNo());

        DenetimResponse denetimResponse = new DenetimResponse();
        Boolean result = denetimService.saveDenetim(denetimRequest);

        if (result) {
            denetimResponse.setSuccessful(result);
        } else {
            denetimResponse.setSuccessful(result);
            denetimResponse.setErrorDTO(new ErrorDTO(true));
        }

        return new ResponseEntity<DenetimResponse>(denetimResponse, OK);
    }

    /*
        Adres bilgilerini getirir
    */
    @RequestMapping(value = "/mahalle/sokak/{belediyeId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<MahalleSokakDTO>> getMahalleAndSokakList(@PathVariable("belediyeId") Long belediyeId) {
        LOG.debug("/mahalle/sokak REST request to get mahalle-sokak List by belediye id = " + belediyeId);

        List<MahalleSokakDTO> mahalleSokakDTOs = denetimService.getMahalleSokakListByBelediyeId(belediyeId);

        return new ResponseEntity<List<MahalleSokakDTO>>(mahalleSokakDTOs, OK);
    }

    /*
        İlçeye göre mahalleleri getirir
    */
    @RequestMapping(value = "/mahalle/{belediyeId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<MahalleDTO>> getMahalleList(@PathVariable("belediyeId") Long belediyeId) {
        LOG.debug("/mahalle REST request to get mahalle List by belediye id = " + belediyeId);
        List<MahalleDTO> mahalleDTOs = denetimService.getMahalleByBelediyeId(belediyeId);

        return new ResponseEntity<List<MahalleDTO>>(mahalleDTOs, OK);
    }

    /*
        geçerli ilçeye ait mahalleleri getirir
    */
    @RequestMapping(value = "/mahalle", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<MahalleDTO>> getMahalleList() {
        LOG.debug("/mahalle REST request to get current mahalle List = ");
        List<MahalleDTO> mahalleDTOs = denetimService.getMahalleListByCurrentBelediye();

        return new ResponseEntity<List<MahalleDTO>>(mahalleDTOs, OK);
    }

    /*
        Mahalleye göre sokakları getirir
    */
    @RequestMapping(value = "/sokak/{mahalleId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<SokakDTO>> getSokakList(@PathVariable("mahalleId") Long mahalleId) {
        LOG.debug("/mahalle REST request to get sokak List by mahalle id = " + mahalleId);
        List<SokakDTO> sokakDTOs = denetimService.getSokakByMahalleId(mahalleId);

        return new ResponseEntity<List<SokakDTO>>(sokakDTOs, OK);
    }

    /*
        Geçerli ildeki belediye listesini getirir
    */
    @RequestMapping(value = "/belediyeler", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<BelediyeDTO>> getAllBelediyeListCurrentCity() {
        LOG.debug("/belediyeler REST request to get belediye list");

        List<BelediyeDTO> belediyeDTOList = denetimService.getBelediyeList();

        return new ResponseEntity<List<BelediyeDTO>>(belediyeDTOList, OK);
    }

    /*
        Denetim raporunu getirir
    */
    @RequestMapping(value = "/create/report", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<ReportResponse> createDenetimReport() {
        LOG.debug("/create/report REST request");

        String htmlContent = reportService.createDenetimReport();
        ReportResponse reportResponse = new ReportResponse(htmlContent,null);

        return new ResponseEntity<ReportResponse>(reportResponse, OK);
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






    @RequestMapping(value = "/deneme", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Boolean> dene() {
        denetimService.saveTelefon(547898457l,2342342l,5000000l);
        return new ResponseEntity<Boolean>(true, OK);
    }

}
