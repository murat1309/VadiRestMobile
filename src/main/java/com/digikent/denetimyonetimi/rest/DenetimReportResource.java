package com.digikent.denetimyonetimi.rest;

import com.digikent.denetimyonetimi.dto.rapor.ReportResponse;
import com.digikent.denetimyonetimi.service.DenetimService;
import com.digikent.denetimyonetimi.service.DenetimReportService;
import com.digikent.general.util.ErrorCode;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Kadir on 2.03.2018.
 */
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/denetim/report")
public class DenetimReportResource {
    private final Logger LOG = LoggerFactory.getLogger(DenetimReportResource.class);

    @Autowired
    DenetimService denetimService;

    @Autowired
    DenetimReportService denetimReportService;

    @Autowired
    HttpServletRequest request;

    /**
     * Ceza Denetim Raporunu getirir
     * //TODO henüz ceza yada tutanak için ayrım yapılmadı
     * @param denetimTespitId
     * @return
     */

    //TODO HEADER EKLENECEK ?
    @RequestMapping(value = "/create/yenidenetim/{denetimtespitid}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<ReportResponse> createCezaDenetimReportByDenetimTespitId(@PathVariable("denetimtespitid") Long denetimTespitId) {
        LOG.debug("Yeni Denetim/ rapor hazirlanacak bdntDenetimTespitID="+denetimTespitId);
        ReportResponse reportResponse = new ReportResponse();
        reportResponse = denetimReportService.createNewDenetimReport(denetimTespitId);

        return new ResponseEntity<ReportResponse>(reportResponse, OK);

    }

    @RequestMapping(value = "/create/view/{denetimtespitid}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<ReportResponse> createReport(@PathVariable("denetimtespitid") Long denetimTespitId) {
        LOG.debug("Denetim Goruntuleme / Rapor hazirlanacak bdntDenetimTespitID="+denetimTespitId);
        ReportResponse reportResponse = null;
        if (denetimReportService.isReportNoAlreadyExist(denetimTespitId)) {
            reportResponse = denetimReportService.createDenetimReport(denetimTespitId);
        } else {
            reportResponse = new ReportResponse(null,new ErrorDTO(true, ErrorCode.ERROR_CODE_505));
        }

        return new ResponseEntity<ReportResponse>(reportResponse, OK);
    }

}
