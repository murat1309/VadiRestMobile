package com.digikent.denetimyonetimi.rest;

import com.digikent.denetimyonetimi.dto.adres.*;
import com.digikent.denetimyonetimi.service.AddressService;
import com.digikent.denetimyonetimi.service.DenetimService;
import com.digikent.denetimyonetimi.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Kadir on 2.03.2018.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/denetim/address")
public class DenetimAddressResource {

    private final Logger LOG = LoggerFactory.getLogger(DenetimAddressResource.class);

    @Autowired
    DenetimService denetimService;

    @Autowired
    ReportService reportService;

    @Autowired
    AddressService addressService;

    /*
        Adres bilgilerini getirir
    */
    @RequestMapping(value = "/mahalle/sokak/{belediyeId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<MahalleSokakDTO>> getMahalleAndSokakList(@PathVariable("belediyeId") Long belediyeId) {
        LOG.debug("/mahalle/sokak REST request to get mahalle-sokak List by belediye id = " + belediyeId);
        List<MahalleSokakDTO> mahalleSokakDTOs = addressService.getMahalleSokakListByBelediyeId(belediyeId);

        return new ResponseEntity<List<MahalleSokakDTO>>(mahalleSokakDTOs, OK);
    }

    /*
        İlçeye göre mahalleleri getirir
    */
    @RequestMapping(value = "/mahalle/{belediyeId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<MahalleDTO>> getMahalleList(@PathVariable("belediyeId") Long belediyeId) {
        LOG.debug("/mahalle REST request to get mahalle List by belediye id = " + belediyeId);
        List<MahalleDTO> mahalleDTOs = addressService.getMahalleByBelediyeId(belediyeId);

        return new ResponseEntity<List<MahalleDTO>>(mahalleDTOs, OK);
    }

    /*
        il bilgilerini getirir
    */
    @RequestMapping(value = "/list/il", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<IlDTO>> getIlList() {
        LOG.debug("/il iller getirilecek");
        List<IlDTO> ilDTOs = addressService.getIlList();

        return new ResponseEntity<List<IlDTO>>(ilDTOs, OK);
    }

    /*
        geçerli ilçeye ait mahalleleri getirir
    */
    @RequestMapping(value = "/mahalle", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<MahalleDTO>> getMahalleList() {
        LOG.debug("/mahalle REST request to get current mahalle List = ");
        List<MahalleDTO> mahalleDTOs = addressService.getMahalleListByCurrentBelediye();

        return new ResponseEntity<List<MahalleDTO>>(mahalleDTOs, OK);
    }

    /*
        Mahalleye göre sokakları getirir
    */
    @RequestMapping(value = "/sokak/{mahalleId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<SokakDTO>> getSokakList(@PathVariable("mahalleId") Long mahalleId) {
        LOG.debug("/mahalle REST request to get sokak List by mahalle id = " + mahalleId);
        List<SokakDTO> sokakDTOs = addressService.getSokakByMahalleId(mahalleId);

        return new ResponseEntity<List<SokakDTO>>(sokakDTOs, OK);
    }

    /*
        Geçerli ildeki belediye listesini getirir
    */
    @RequestMapping(value = "/belediyeler", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<BelediyeDTO>> getAllBelediyeListCurrentCity() {
        LOG.debug("/belediyeler REST request to get belediye list");
        List<BelediyeDTO> belediyeDTOList = addressService.getBelediyeList();

        return new ResponseEntity<List<BelediyeDTO>>(belediyeDTOList, OK);
    }

}
