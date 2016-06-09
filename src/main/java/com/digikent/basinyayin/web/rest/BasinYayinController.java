package com.digikent.basinyayin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.digikent.basinyayin.dao.BasinYayinRepository;
import com.digikent.basinyayin.dto.AsmaIndirmeIslemiDTO;
import com.digikent.basinyayin.dto.TtnyekipDTO;
import com.digikent.basinyayin.dto.TtnylokasyonDTO;
import com.digikent.basinyayin.dto.VtnytanitimDTO;
import com.digikent.web.rest.errors.CustomParameterizedException;
import com.digikent.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by Serkan on 4/2/2016.
 */
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/basinYayin")
public class BasinYayinController {

    private final Logger LOG = LoggerFactory.getLogger(BasinYayinController.class);

    @Inject
    private BasinYayinRepository basinYayinRepository;

    @RequestMapping(value = "/ekip", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<TtnyekipDTO>> getEkipList() {
        LOG.debug("REST request to get ekipList : {}");

        return Optional.ofNullable(basinYayinRepository.getEkipList())
                .map(ttnyekip -> new ResponseEntity<>(ttnyekip, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{ekipId}/asma/bekleyen", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<AsmaIndirmeIslemiDTO>> getAsmaIslemiBekleyen(@PathVariable Long ekipId) {
        LOG.debug("REST request to get asma islemi bekleyen with ekipId : {}", ekipId);

        return Optional.ofNullable(basinYayinRepository.getAsmaIslemiBekleyen(ekipId))
                .map(asmaIslemiDTO -> new ResponseEntity<>(asmaIslemiDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{ekipId}/asma/yapilan", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<AsmaIndirmeIslemiDTO>> getAsmaIslemiYapilan(@PathVariable Long ekipId) {
        LOG.debug("REST request to get asma islemi yapilan with ekipId : {}", ekipId);

        return Optional.ofNullable(basinYayinRepository.getAsmaIslemiYapilan(ekipId))
                .map(asmaIslemiDTO -> new ResponseEntity<>(asmaIslemiDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{ekipId}/indirme/bekleyen", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<AsmaIndirmeIslemiDTO>> getIndirmeIslemiBekleyen(@PathVariable Long ekipId) {
        LOG.debug("REST request to get indirme islemi bekleyen with ekipId : {}", ekipId);

        return Optional.ofNullable(basinYayinRepository.getIndirmeIslemiBekleyen(ekipId))
                .map(asmaIslemiDTO -> new ResponseEntity<>(asmaIslemiDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{ekipId}/indirme/yapilan", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<AsmaIndirmeIslemiDTO>> getIndirmeIslemiYapilan(@PathVariable Long ekipId) {
        LOG.debug("REST request to get indirme islemi yapilan with ekipId : {}", ekipId);

        return Optional.ofNullable(basinYayinRepository.getIndirmeIslemiYapilan(ekipId))
                .map(asmaIslemiDTO -> new ResponseEntity<>(asmaIslemiDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{koordinatX}/{koordinatY}/{locationBarkod}/{presentationBarkod}/{ekipId}/asma", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<Long> createAsma(@PathVariable Double koordinatX,
                                           @PathVariable Double koordinatY,
                                           @PathVariable Long locationBarkod,
                                           @PathVariable Long presentationBarkod,
                                           @PathVariable Long ekipId) throws Exception {
        LOG.debug("REST request to post asma islemi with locationBarkod, presentationBarkod, ekipId : {}"
                , locationBarkod, presentationBarkod, ekipId);
        Long maxAsmaId = basinYayinRepository.getLastAsmaId(locationBarkod,presentationBarkod,ekipId);

        basinYayinRepository.updateAsmaIslemi(maxAsmaId, koordinatX, koordinatY);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("VTNYTANITIMUYGULAMA", String.valueOf(maxAsmaId)))
                .body(maxAsmaId);
    }

    @RequestMapping(value = "/{koordinatX}/{koordinatY}/{locationBarkod}/{presentationBarkod}/{ekipId}/indirme", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<Long> createIndirme(@PathVariable Double koordinatX,
                                              @PathVariable Double koordinatY,
                                              @PathVariable Long locationBarkod,
                                              @PathVariable Long presentationBarkod,
                                              @PathVariable Long ekipId) throws Exception {
        LOG.debug("REST request to post indirme islemi with locationBarkod, presentationBarkod, ekipId : {}"
                , locationBarkod, presentationBarkod, ekipId);
        Long maxIndirmeId = basinYayinRepository.getLastIndirmeId(locationBarkod,presentationBarkod,ekipId);

        basinYayinRepository.updateIndirmeIslemi(maxIndirmeId, koordinatX, koordinatY);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("VTNYTANITIMUYGULAMA", String.valueOf(maxIndirmeId)))
                .body(maxIndirmeId);
    }

    @RequestMapping(value = "/{barcodeNumber}/vtnytanitim", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<VtnytanitimDTO>> getVtnytanitimbyBarcodeNumber(@PathVariable Long barcodeNumber) {
        LOG.debug("REST request to get Vtnytanitim with barcodeNumber : {}", barcodeNumber);

        return Optional.ofNullable(basinYayinRepository.getVtnytanitimByBarcodeNumber(barcodeNumber))
                .map(vtnytanitimDTO -> new ResponseEntity<>(vtnytanitimDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{barcodeNumber}/ttnylokasyon", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<TtnylokasyonDTO>> getTtnylokasyonbyBarcodeNumber(@PathVariable Long barcodeNumber) {
        LOG.debug("REST request to get ttnylokasyon with barcodeNumber : {}", barcodeNumber);

        return Optional.ofNullable(basinYayinRepository.getTtnylokasyonByBarcodeNumber(barcodeNumber))
                .map(ttnylokasyonDTO -> new ResponseEntity<>(ttnylokasyonDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
