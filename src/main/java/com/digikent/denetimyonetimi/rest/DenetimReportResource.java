package com.digikent.denetimyonetimi.rest;

import com.digikent.denetimyonetimi.dto.velocity.ReportResponse;
import com.digikent.denetimyonetimi.service.DenetimService;
import com.digikent.denetimyonetimi.service.DenetimReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Kadir on 2.03.2018.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/denetim/report")
public class DenetimReportResource {
    private final Logger LOG = LoggerFactory.getLogger(DenetimReportResource.class);

    @Autowired
    DenetimService denetimService;

    @Autowired
    DenetimReportService denetimReportService;

    /**
     * Ceza Denetim Raporunu getirir
     * //TODO henüz ceza yada tutanak için ayrım yapılmadı
     * @param denetimTespitId
     * @return
     */
    @RequestMapping(value = "/create/ceza/{denetimtespitid}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<ReportResponse> createCezaDenetimReportByDenetimTespitId(@PathVariable("denetimtespitid") Long denetimTespitId) {
        LOG.debug("/create/ceza REST request");


        String htmlContent = denetimReportService.createCezaDenetimReport(denetimTespitId);
        ReportResponse reportResponse = new ReportResponse(htmlContent,null);

        return new ResponseEntity<ReportResponse>(reportResponse, OK);
    }

    /**
     * Tutanak Denetim Raporunu getirir
     * //TODO henüz ceza yada tutanak için ayrım yapılmadı
     * @param denetimTespitId
     * @return
     */
    @RequestMapping(value = "/create/tutanak/{denetimtespitid}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<ReportResponse> createTutanakDenetimReportByDenetimTespitId(@PathVariable("denetimtespitid") Long denetimTespitId) {
        LOG.debug("/create/tutanak REST request");

        String htmlContent = denetimReportService.createCezaDenetimReport(denetimTespitId);
        ReportResponse reportResponse = new ReportResponse(htmlContent,null);

        return new ResponseEntity<ReportResponse>(reportResponse, OK);
    }

}
