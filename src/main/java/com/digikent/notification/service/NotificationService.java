package com.digikent.notification.service;

import com.digikent.general.service.UtilityService;
import com.digikent.notification.dao.NotificationRepository;
import com.digikent.notification.dto.NotificationTokenRequestDTO;
import com.digikent.notification.dto.NotificationTokenResponseDTO;
import com.digikent.notification.dto.RemoteNotificationRequestDTO;
import com.digikent.notification.dto.RemoteNotificationResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Service
public class NotificationService {


    private final Logger LOG = LoggerFactory.getLogger(UtilityService.class);

    @Inject
    NotificationRepository notificationRepository;

    public RemoteNotificationResponseDTO pushNotification(HttpServletRequest request, RemoteNotificationRequestDTO remoteNotificationRequestDTO) {
        return notificationRepository.pushNotification(request, remoteNotificationRequestDTO);
    }

    public NotificationTokenResponseDTO saveToken(NotificationTokenRequestDTO notificationTokenRequestDTO) {
        return notificationRepository.saveToken(notificationTokenRequestDTO);
    }
}
