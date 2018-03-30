package com.digikent.denetimyonetimi.rest;

import com.digikent.denetimyonetimi.dto.adres.DenetimOlayYeriAdresi;
import com.digikent.denetimyonetimi.dto.adres.DenetimTebligatAdresi;
import com.digikent.denetimyonetimi.dto.denetim.DenetimObjectDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimObjectRequestDTO;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitKararRequest;
import com.digikent.denetimyonetimi.dto.tespit.TespitDTO;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.service.DenetimOverviewService;
import com.digikent.denetimyonetimi.service.DenetimReportService;
import com.digikent.denetimyonetimi.service.DenetimService;
import com.digikent.paydasiliskileri.service.PaydasIliskileriManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Medet on 3/28/2018.
 */

@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/denetim/duzenleme")
public class DenetimOverviewResource {

    private final Logger LOG = LoggerFactory.getLogger(DenetimOverviewResource.class);

    @Autowired
    DenetimService denetimService;

    @Autowired
    DenetimOverviewService denetimOverviewService;

    @RequestMapping(value = "/get/denetimobject", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<DenetimObjectDTO> getDenetimObjectByDenetimAndDenetimTespitId(@RequestBody DenetimObjectRequestDTO denetimObjectRequestDTO) {

        LOG.debug("Rest request to get denetim object with denetim id : " + denetimObjectRequestDTO.getDenetimId());
        LOG.debug("Rest request to get denetim object with denetim tespit id : " + denetimObjectRequestDTO.getDenetimTespitId());
        LOG.debug("Rest request to get denetim object with paydas  id : " + denetimObjectRequestDTO.getPaydasId());
        LOG.debug("Rest request to get denetim object with tespit grubu  id : " + denetimObjectRequestDTO.getTespitGrubuId());
        DenetimObjectDTO denetimObjectDTO;
        List<TespitDTO> tespitDTOs = null;
        denetimObjectDTO = denetimOverviewService.getDenetimObjectByDenetimAndDenetimTespitId(denetimObjectRequestDTO);
        tespitDTOs = denetimService.getTespitDTOListByTespitGrubuId(denetimObjectRequestDTO.getTespitGrubuId());
        denetimObjectDTO.setTespitQuestionsData(tespitDTOs);

        return new ResponseEntity<DenetimObjectDTO>(denetimObjectDTO, OK);
    }

    @RequestMapping(value = "/update/tebligatadresi/{denetimId}", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UtilDenetimSaveDTO> updateDenetimTebligatAdresiByDenetimId(@RequestBody DenetimTebligatAdresi denetimTebligatAdresi, @PathVariable Long denetimId) {

        LOG.debug("Rest request to update tebligat adresi with denetim id : " + denetimId);
        UtilDenetimSaveDTO utilDenetimSaveDTO;
        utilDenetimSaveDTO = denetimOverviewService.updateDenetimTebligatAdresiByDenetimId(denetimTebligatAdresi, denetimId);

        return new ResponseEntity<UtilDenetimSaveDTO>(utilDenetimSaveDTO, OK);
    }

    @RequestMapping(value = "/update/olayadresi/{denetimId}", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UtilDenetimSaveDTO> updateDenetimOlayYeriAdresiByDenetimId(@RequestBody DenetimOlayYeriAdresi denetimOlayYeriAdresi, @PathVariable Long denetimId) {

        LOG.debug("Rest request to update olay adresi with denetim id : " + denetimId);
        UtilDenetimSaveDTO utilDenetimSaveDTO;
        utilDenetimSaveDTO = denetimOverviewService.updateDenetimOlayYeriAdresiByDenetimId(denetimOlayYeriAdresi, denetimId);

        return new ResponseEntity<UtilDenetimSaveDTO>(utilDenetimSaveDTO, OK);
    }

    @RequestMapping(value = "/update/karar/{denetimTespitId}", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UtilDenetimSaveDTO> updateDenetimKararBilgileriByDenetimId(@RequestBody DenetimTespitKararRequest denetimTespitKararRequest, @PathVariable Long denetimTespitId) {

        LOG.debug("Rest request to update karar bilgisi with denetim tespit id : " + denetimTespitId);
        UtilDenetimSaveDTO utilDenetimSaveDTO;
        utilDenetimSaveDTO = denetimOverviewService.updateDenetimKararBilgileriByDenetimId(denetimTespitKararRequest, denetimTespitId);

        return new ResponseEntity<UtilDenetimSaveDTO>(utilDenetimSaveDTO, OK);
    }
}
