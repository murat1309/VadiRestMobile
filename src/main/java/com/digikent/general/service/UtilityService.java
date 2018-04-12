package com.digikent.general.service;

import com.digikent.general.dao.UtilityRepository;
import com.digikent.general.dto.BelediyeParamResponseDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by Medet on 4/9/2018.
 */
@Service
public class UtilityService {

    @Inject
    UtilityRepository utilityRepository;

    public static String getNSM2PARAMETRESqlQuery() {
        String sql = "SELECT RRE1ILCE_ID, RRE1ILCE_ADI, PRE1IL_ID, PRE1IL_ADI, RE1KURUMADI, RE1KURUMID,\n" +
                "BRE1ULKE_ADI, BRE1ULKE_ID,SM1KURUM_ID,KURUMKISALTMA, KURUMKODU, BASKANADISOYADI,\n" +
                "TELEFONALANKODU,BUYUKSEHIRMI FROM NSM2PARAMETRE";

        return sql;
    }

    public BelediyeParamResponseDTO getBelediyeParams() {

        return utilityRepository.getBelediyeParams();
    }
}
