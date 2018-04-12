package com.digikent.general.dao;

import com.digikent.general.dto.BelediyeParamResponseDTO;
import com.digikent.general.dto.BelediyeParamsDTO;
import com.digikent.general.util.ErrorCode;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.digikent.general.service.UtilityService.getNSM2PARAMETRESqlQuery;

/**
 * Created by Medet on 4/9/2018.
 */
@Repository
public class UtilityRepository {

    private final Logger LOG = LoggerFactory.getLogger(UtilityRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public List getNSM2PARAMETRETableContents() {
        List list = new ArrayList();
        String sql = getNSM2PARAMETRESqlQuery();

        try {
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

        } catch (Exception e) {
            LOG.debug("Belediye parametreleri getirilirken bir hata ile karsilasildi. Hata kodu : " + e.getMessage());
        }

        return list;
    }

    public BelediyeParamsDTO setBelediyeParams(List list) {
        BelediyeParamsDTO belediyeParamsDTO = new BelediyeParamsDTO();

        Map map = (Map) list.get(0);

        BigDecimal rre1ilce_id = (BigDecimal) map.get("RRE1ILCE_ID");
        String rre1ilce_adi = (String) map.get("RRE1ILCE_ADI");
        BigDecimal pre1il_id = (BigDecimal) map.get("PRE1IL_ID");
        String pre1il_adi = (String) map.get("PRE1IL_ADI");
        String re1kurumadi = (String) map.get("RE1KURUMADI");
        BigDecimal re1kurumid = (BigDecimal) map.get("RE1KURUMID");
        String bre1ulke_adi = (String) map.get("BRE1ULKE_ADI");
        BigDecimal bre1ulke_id = (BigDecimal) map.get("BRE1ULKE_ID");
        BigDecimal sm1kurum_id = (BigDecimal) map.get("SM1KURUM_ID");
        String kurumKisaltma = (String) map.get("KURUMKISALTMA");
        String kurumKodu = (String) map.get("KURUMKODU");
        String baskanAdiSoyadi = (String) map.get("BASKANADISOYADI");
        BigDecimal telefonAlanKodu = (BigDecimal) map.get("TELEFONALANKODU");
        String buyukSehirMi = (String) map.get("BUYUKSEHIRMI");

        if (rre1ilce_id != null) {
            belediyeParamsDTO.setRre1ilce_id(rre1ilce_id.longValue());
        }
        if (rre1ilce_adi != null) {
            belediyeParamsDTO.setRre1ilce_adi(rre1ilce_adi);
        }
        if (pre1il_id != null) {
            belediyeParamsDTO.setPre1il_id(pre1il_id.longValue());
        }
        if (pre1il_adi != null) {
            belediyeParamsDTO.setPre1il_adi(pre1il_adi);
        }
        if (re1kurumadi != null) {
            belediyeParamsDTO.setRe1kurumadi(re1kurumadi);
        }
        if (re1kurumid != null) {
            belediyeParamsDTO.setRe1kurumid(re1kurumid.longValue());
        }
        if (bre1ulke_adi != null) {
            belediyeParamsDTO.setBre1ulke_adi(bre1ulke_adi);
        }
        if (bre1ulke_id != null) {
            belediyeParamsDTO.setBre1ulke_id(bre1ulke_id.longValue());
        }
        if (sm1kurum_id != null) {
            belediyeParamsDTO.setSm1kurum_id(sm1kurum_id.longValue());
        }
        if (kurumKisaltma != null) {
            belediyeParamsDTO.setKurumKisaltma(kurumKisaltma);
        }
        if (kurumKodu != null) {
            belediyeParamsDTO.setKurumKodu(kurumKodu);
        }
        if (baskanAdiSoyadi != null) {
            belediyeParamsDTO.setBaskanAdiSoyadi(baskanAdiSoyadi);
        }
        if (telefonAlanKodu != null) {
            belediyeParamsDTO.setTelefonAlanKodu(telefonAlanKodu.longValue());
        }
        if (buyukSehirMi != null) {
            belediyeParamsDTO.setBuyukSehirMi(buyukSehirMi);
        }

        return belediyeParamsDTO;
    }

    public BelediyeParamResponseDTO getBelediyeParams() {
        BelediyeParamResponseDTO belediyeParamResponseDTO = new BelediyeParamResponseDTO();
        BelediyeParamsDTO belediyeParamsDTO = null;
        List belediyeParamList = null;

        belediyeParamList = getNSM2PARAMETRETableContents();
        if(!belediyeParamList.isEmpty()) {
            belediyeParamsDTO = setBelediyeParams(belediyeParamList);

            belediyeParamResponseDTO.setBelediyeParamsDTO(belediyeParamsDTO);
            belediyeParamResponseDTO.setErrorDTO(new ErrorDTO(false, null));

        } else {
            belediyeParamResponseDTO.setBelediyeParamsDTO(belediyeParamsDTO);
            belediyeParamResponseDTO.setErrorDTO(new ErrorDTO(true, ErrorCode.ERROR_CODE_504));
        }

        return belediyeParamResponseDTO;
    }
}
