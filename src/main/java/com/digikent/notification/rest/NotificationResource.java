package com.digikent.notification.rest;


import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.notification.dto.NotificationTokenRequestDTO;
import com.digikent.notification.dto.NotificationTokenResponseDTO;
import com.digikent.notification.dto.RemoteNotificationRequestDTO;
import com.digikent.notification.dto.RemoteNotificationResponseDTO;
import com.digikent.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/notification")
public class NotificationResource {


    private final Logger LOG = LoggerFactory.getLogger(NotificationResource.class);

    @Autowired
    NotificationService notificationService;

    @Autowired
    HttpServletRequest request;


    @RequestMapping(value = "/push/instant", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    public ResponseEntity<RemoteNotificationResponseDTO> pushNotification(HttpServletRequest request, @RequestBody RemoteNotificationRequestDTO remoteNotificationRequestDTO) {
        RemoteNotificationResponseDTO remoteNotificationResponseDTO = notificationService.pushNotification(request, remoteNotificationRequestDTO);

        return new ResponseEntity<RemoteNotificationResponseDTO>(remoteNotificationResponseDTO, OK);
    }

    @RequestMapping(value = "/save/token", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationTokenResponseDTO> saveToken(@RequestBody NotificationTokenRequestDTO notificationTokenRequestDTO){

        NotificationTokenResponseDTO notificationTokenResponseDTO = notificationService.saveToken(notificationTokenRequestDTO);

        return new ResponseEntity<NotificationTokenResponseDTO>(notificationTokenResponseDTO, OK);
    }


}