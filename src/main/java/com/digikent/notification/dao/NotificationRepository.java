package com.digikent.notification.dao;

import com.digikent.config.Constants;
import com.digikent.denetimyonetimi.entity.FSM1Users;
import com.digikent.general.dao.UtilityRepository;
import com.digikent.general.util.Params;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.notification.dto.NotificationTokenRequestDTO;
import com.digikent.notification.dto.NotificationTokenResponseDTO;
import com.digikent.notification.dto.RemoteNotificationRequestDTO;
import com.digikent.notification.dto.RemoteNotificationResponseDTO;
import com.mysema.query.types.Constant;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.*;


@Repository
public class NotificationRepository {


    private final Logger LOG = LoggerFactory.getLogger(UtilityRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public RemoteNotificationResponseDTO pushNotification(HttpServletRequest request, RemoteNotificationRequestDTO remoteNotificationRequestDTO) {

        RemoteNotificationResponseDTO remoteNotificationResponseDTO = new RemoteNotificationResponseDTO();
        ErrorDTO errorDTO = new ErrorDTO();

        try {
            if (isAuthenticated(request)) {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
                HttpEntity<String> msgHttpEntity = initNotificationMessage(remoteNotificationRequestDTO);

                String msgResponse = restTemplate.postForObject(Constants.FIREBASE_CLOUD_MESSAGING_URL, msgHttpEntity, String.class);
                JSONObject msgResponseData = new JSONObject(msgResponse);
                if (msgResponseData.get("success").equals(0)) {
                    errorDTO.setError(true);
                    errorDTO.setErrorMessage("Firebase message http request failed");
                    remoteNotificationResponseDTO.setErrorDTO(errorDTO);
                }
                LOG.debug("notification response: " + msgResponseData);
            }

        } catch (Exception e) {
            LOG.error("An error occured while sending notification");
            e.printStackTrace();
            errorDTO.setError(true);
            errorDTO.setErrorMessage("An error occured while sending notification");
            remoteNotificationResponseDTO.setErrorDTO(errorDTO);
        }
        return remoteNotificationResponseDTO;
    }

    public NotificationTokenResponseDTO saveToken(NotificationTokenRequestDTO notificationTokenRequestDTO) {
        NotificationTokenResponseDTO notificationTokenResponseDTO = new NotificationTokenResponseDTO();
        ErrorDTO errorDTO = new ErrorDTO();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Object fsm1UserObject = session.get(FSM1Users.class, notificationTokenRequestDTO.getFsm1UserId());
            FSM1Users fsm1User = (FSM1Users) fsm1UserObject;
            fsm1User.setNotificationToken(notificationTokenRequestDTO.getNotificationToken());

            if (notificationTokenRequestDTO.getOldFsm1UserId() != null) {
                Object oldFsm1UserObject = session.get(FSM1Users.class, notificationTokenRequestDTO.getOldFsm1UserId());
                FSM1Users oldFsm1User = (FSM1Users) oldFsm1UserObject;
                oldFsm1User.setNotificationToken(null);
                session.update(oldFsm1User);
            }

            session.update(fsm1User);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("An error has occured while updating notification token. Error code:" + e.getMessage());
            e.printStackTrace();
            errorDTO.setError(true);
            errorDTO.setErrorMessage("An error has occured while updating notification token.");
            notificationTokenResponseDTO.setErrorDTO(errorDTO);
        } finally {
            session.close();
        }

        return notificationTokenResponseDTO;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        boolean isAuthenticated = false;
        Map parameters = Params.getParametersByGroup(Constants.MOBIL, sessionFactory);
        String[] credentials = getCredentials(request);
        try {
            if (parameters.get(Constants.MOBILE_AUTH_FIELD[0]).equals(credentials[0]) && parameters.get(Constants.MOBILE_AUTH_FIELD[1]).equals(credentials[1])) {
                isAuthenticated = true;
            }
        } catch (Exception e) {
            LOG.error("An Error has occured while authenticating user. Error code:" + e.getMessage());
        }

        return isAuthenticated;
    }

    private String[] getCredentials(HttpServletRequest request) {
        try {
            final String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.startsWith("Basic")) {
                // Authorization: Basic base64credentials
                String base64Credentials = authorization.substring("Basic".length()).trim();
                String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                        Charset.forName("UTF-8"));
                // credentials = username:password
                return credentials.split(":", 2);
            }
        } catch (Exception e) {
            LOG.error("An Error has occured while getting credentials from request. Error code:" + e.getMessage());
        }

        return null;
    }

    private HttpEntity<String> initNotificationMessage(RemoteNotificationRequestDTO remoteNotificationRequestDTO) {
        HttpEntity<String> msgHttpEntity = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "key=" + Constants.FIREBASE_CLOUD_MESSAGING_API_KEY);
            httpHeaders.set("Content-Type", "application/json");

            Criteria criteria = session.createCriteria(FSM1Users.class)
                    .add(Restrictions.eq("ID", remoteNotificationRequestDTO.getFsm1UserId()));
            FSM1Users fsm1User = (FSM1Users) criteria.uniqueResult();

            String notificationToken = fsm1User.getNotificationToken();
            JSONObject msg = new JSONObject();
            JSONObject msgOnly = new JSONObject();

            msg.put("_body", (remoteNotificationRequestDTO.getNotificationMessage() != null ? remoteNotificationRequestDTO.getNotificationMessage() : Constants.DEFAULT_NOTIFICATION_MESSAGE));

            msgOnly.put("data", msg);
            msgOnly.put("to", notificationToken);
            msgOnly.put("priority", "high");

            msgHttpEntity = new HttpEntity<String>(msgOnly.toString(), httpHeaders);
            tx.commit();
        } catch (Exception e) {
            LOG.error("An error has occured while initializing notification message. Error code:" + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return msgHttpEntity;
    }
}
