package com.digikent.general.service;

import com.digikent.general.dao.UtilityRepository;
import com.digikent.general.dto.*;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by Medet on 09/04/2018.
 * Edited by Kadir on 16/04/2018.
 */
@Service
public class UtilityService {

    private final Logger LOG = LoggerFactory.getLogger(UtilityService.class);

    @Inject
    UtilityRepository utilityRepository;

    public static String getNSM2PARAMETRESqlQuery() {
        String sql = "SELECT RRE1ILCE_ID, RRE1ILCE_ADI, PRE1IL_ID, PRE1IL_ADI, RE1KURUMADI, RE1KURUMID,\n" +
                "BRE1ULKE_ADI, BRE1ULKE_ID,SM1KURUM_ID,KURUMKISALTMA, KURUMKODU, BASKANADISOYADI,\n" +
                "TELEFONALANKODU,BUYUKSEHIRMI, " +
                "SM2.F_PARAMETRE('MOBIL', 'YAZICIMARKA') AS YAZICIMARKA, " +
                "SM2.F_PARAMETRE('MOBIL', 'YAZICITIPI') AS YAZICITIPI, " +
                "SM2.F_PARAMETRE('MOBIL', 'YAZICIMODEL') AS YAZICIMODEL " +
                "FROM NSM2PARAMETRE";

        return sql;
    }

    public BelediyeParamResponseDTO getBelediyeParams() {

        return utilityRepository.getBelediyeParams();
    }

    public void saveMobileExceptionHandlerLog(String message, String errorLine, String currentPage) {
        LOG.error("mobil hata mesaji = " + message);
        LOG.error("mobil hata router = " + currentPage);
        LOG.error("mobil hata bundle satiri = " + errorLine);
    }

    public void LogMobileAppException(MobileAppExceptionDTO mobileAppExceptionDTO) {
        LOG.error(mobileAppExceptionDTO.getErrorMessage());
    }

    public NotificationResponseDTO getNotifications(NotificationRequestDTO notificationRequestDTO) {
        return utilityRepository.getNotifications(notificationRequestDTO);
    }

    public Long getNakitOdemeIndirimi() throws Exception {
        return utilityRepository.getNakitOdemeIndirimi();
    }

}
