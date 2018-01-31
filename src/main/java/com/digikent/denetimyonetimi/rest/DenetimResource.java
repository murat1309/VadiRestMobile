package com.digikent.denetimyonetimi.rest;

import com.digikent.config.Constants;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.paydasiliskileri.service.PaydasIliskileriManagementService;
import com.digikent.denetimyonetimi.dto.adres.BelediyeDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleSokakDTO;
import com.digikent.denetimyonetimi.dto.adres.SokakDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimTuruDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimTuruResponse;
import com.digikent.denetimyonetimi.dto.denetim.DenetimRequest;
import com.digikent.denetimyonetimi.dto.denetim.DenetimResponse;
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
        Denetim türlerini getirir
     */
    @RequestMapping(value = "/denetimturu", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<DenetimTuruResponse> getDenetimTuruList(@PathVariable("whom") Long personelId) {
        LOG.debug("REST request to get message list whom id : " + personelId);
        List<DenetimTuruDTO> denetimTuruDTOList = null;

        // denetimTuruDTOList =

        ErrorDTO errorDTO = new ErrorDTO(false,null);
        DenetimTuruResponse denetimTuruResponse = new DenetimTuruResponse();

        return new ResponseEntity<DenetimTuruResponse>(denetimTuruResponse, OK);
    }

    /*
    Geçerli ildeki belediye listesini getirir
*/
    @RequestMapping(value = "/velocity", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<String> getVelocityTemplate() {
        LOG.debug("/belediyeler REST request to get belediye list");

        String temp = denetimService.createVelocityTemplate();

        return new ResponseEntity<String>(temp, OK);
    }

}
