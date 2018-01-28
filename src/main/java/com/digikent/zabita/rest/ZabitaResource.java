package com.digikent.zabita.rest;

import com.digikent.config.Constants;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.paydasiliskileri.service.PaydasIliskileriManagementService;
import com.digikent.zabita.dto.adres.MahalleSokakDTO;
import com.digikent.zabita.dto.denetim.DenetimTuruDTO;
import com.digikent.zabita.dto.denetim.DenetimTuruResponse;
import com.digikent.zabita.dto.denetim.ZabitaDenetimRequest;
import com.digikent.zabita.dto.denetim.ZabitaDenetimResponse;
import com.digikent.zabita.dto.paydas.ZabitaPaydasRequestDTO;
import com.digikent.zabita.dto.paydas.ZabitaPaydasResponseDTO;
import com.digikent.zabita.service.ZabitaService;
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
@RequestMapping("/zabita")
public class ZabitaResource {

    private final Logger LOG = LoggerFactory.getLogger(ZabitaResource.class);

    @Autowired
    PaydasIliskileriManagementService paydasIliskileriManagementService;

    @Autowired
    ZabitaService zabitaService;

    /*
        zabita - paydas search by single filter
    */
    @RequestMapping(value = "/paydas/search", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ZabitaPaydasResponseDTO> getPaydasListByCriteria(@RequestBody ZabitaPaydasRequestDTO zabitaPaydasRequestDTO) {
        LOG.debug("Zabita - paydas/search Gelen Paydas Arama Kriteri : " + zabitaPaydasRequestDTO.getFilter());

        ZabitaPaydasResponseDTO zabitaPaydasResponseDTO = null;
        if(zabitaPaydasRequestDTO.getFilter() != null && !zabitaPaydasRequestDTO.getFilter().isEmpty()) {
            zabitaPaydasResponseDTO = paydasIliskileriManagementService.getPaydasInfoByZabitaFilter(zabitaPaydasRequestDTO);
        } else {
            zabitaPaydasResponseDTO.setErrorDTO(new ErrorDTO(true, Constants.ERROR_MESSAGE_PAYDAS_MIN_CHARACHTER_SIZE));
        }

        return new ResponseEntity<ZabitaPaydasResponseDTO>(zabitaPaydasResponseDTO, OK);
    }

    /*
        zabita - paydas search by single filter
    */
    @RequestMapping(value = "/denetim/save", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Boolean> saveDenetim(@RequestBody ZabitaDenetimRequest zabitaDenetimRequest) {
        LOG.debug("Zabita - denetim kayıt. paydaş ID : " + zabitaDenetimRequest.getPaydasId());

        zabitaService.saveZabitaDenetim(zabitaDenetimRequest);

        return new ResponseEntity<Boolean>(true, OK);
    }

    /*
        Adres bilgilerini getirir
    */
    @RequestMapping(value = "/mahalle/sokak", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<MahalleSokakDTO>> getMahalleAndSokakList() {
        LOG.debug("/mahalle/sokak REST request to get mahalle-sokak List");

        List<MahalleSokakDTO> mahalleSokakDTOs = zabitaService.getMahalleSokakList();

        return new ResponseEntity<List<MahalleSokakDTO>>(mahalleSokakDTOs, OK);
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





}
