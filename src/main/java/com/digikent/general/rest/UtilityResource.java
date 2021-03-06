package com.digikent.general.rest;

import com.digikent.general.dto.*;
import com.digikent.general.service.UtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;


import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Medet on 4/9/2018.
 */

@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/utility")
public class UtilityResource {

    private final Logger LOG = LoggerFactory.getLogger(UtilityResource.class);

    @Autowired
    UtilityService utilityService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value="/get/belediye/parameters", method = RequestMethod.GET)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    public ResponseEntity<BelediyeParamResponseDTO> getBelediyeParams() {
        BelediyeParamResponseDTO belediyeParamResponseDTO = null;

        belediyeParamResponseDTO = utilityService.getBelediyeParams();

        return new ResponseEntity<BelediyeParamResponseDTO>(belediyeParamResponseDTO, OK);
    }

    /**
     * @deprecated /mobile/log/exception
     * @return
     */
    @RequestMapping(value = "/mobil/exceptionhandler", method = RequestMethod.GET)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Boolean> getDenetimListByCriteria() {
        LOG.debug("MOBIL PATLADI");
        utilityService.saveMobileExceptionHandlerLog(request.getHeader("Message"),request.getHeader("ErrorLine"),request.getHeader("CurrentPage"));
        return new ResponseEntity<Boolean>(true, OK);
    }

    /*
        Mobil Uygulama patladığı zaman, log atacaktır.
    */
    @RequestMapping(value = "/mobile/log/exception", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getMobileAppException(@RequestBody MobileAppExceptionDTO mobileAppExceptionDTO) {
        LOG.debug("Mobile App Exception Has Been Thrown");
        utilityService.LogMobileAppException(mobileAppExceptionDTO);
        return new ResponseEntity<>(true, OK);
    }

    /**
     *
     * @param notificationRequestDTO
     * @return
        ana menüdeki bildirim sayılarını getirir.
        ebys de onay bekleyenler
        mesajlaşmada, bekleyen mesajlar
        başvuruda, onay bekleyenler
     */
    @RequestMapping(value = "/get/notifications", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationResponseDTO> getNotifications(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        NotificationResponseDTO notificationResponseDTO = utilityService.getNotifications(notificationRequestDTO);
        return new ResponseEntity<NotificationResponseDTO>(notificationResponseDTO, OK);
    }


}
