package com.digikent.general.dao;

import com.digikent.general.dto.*;
import com.digikent.general.entity.ABPMWorkItem;
import com.digikent.general.entity.EDM1IsAkisiAdim;
import com.digikent.general.util.ErrorCode;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.mesajlasma.entity.VeilMesajLine;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
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
        String yaziciTipi = (String) map.get("YAZICITIPI");
        String yaziciMarka = (String) map.get("YAZICIMARKA");
        String yaziciModel = (String) map.get("YAZICIMODEL");

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
        if (yaziciModel != null) {
            belediyeParamsDTO.setYaziciModel(yaziciModel);
        }
        if (yaziciMarka != null) {
            belediyeParamsDTO.setYaziciMarka(yaziciMarka);
        }
        if (yaziciTipi != null) {
            belediyeParamsDTO.setYaziciTipi(yaziciTipi);
        }

        return belediyeParamsDTO;
    }

    public BelediyeParamResponseDTO getBelediyeParams() {
        BelediyeParamResponseDTO belediyeParamResponseDTO = new BelediyeParamResponseDTO();
        BelediyeParamsDTO belediyeParamsDTO = null;
        List belediyeParamList = null;

        belediyeParamList = getNSM2PARAMETRETableContents();
        if (!belediyeParamList.isEmpty()) {
            belediyeParamsDTO = setBelediyeParams(belediyeParamList);

            belediyeParamResponseDTO.setBelediyeParamsDTO(belediyeParamsDTO);
            belediyeParamResponseDTO.setErrorDTO(new ErrorDTO(false, null));

        } else {
            belediyeParamResponseDTO.setBelediyeParamsDTO(belediyeParamsDTO);
            belediyeParamResponseDTO.setErrorDTO(new ErrorDTO(true, ErrorCode.ERROR_CODE_504));
        }

        return belediyeParamResponseDTO;
    }

    public NotificationResponseDTO getNotifications(NotificationRequestDTO notificationRequestDTO) {
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO();

        Long ebysNotificationCount = getEBYSNotificationCount(notificationRequestDTO.getFsm1userId());
        Long mesajNotificationCount = getMesajNotificationCount(notificationRequestDTO.getIhr1personelId());
        Long gelenBasvuruNotificationCount = getGelenBasvuruNotificationCount(notificationRequestDTO.getIhr1personelId());

        if (ebysNotificationCount != null)
            notificationResponseDTO.setEbysNotificationCount(ebysNotificationCount);
        if (mesajNotificationCount != null)
            notificationResponseDTO.setMesajNotificationCount(mesajNotificationCount);
        if (gelenBasvuruNotificationCount != null)
            notificationResponseDTO.setGelenBasvuruNotificationCount(gelenBasvuruNotificationCount);

        return notificationResponseDTO;
    }

    public Long getEBYSNotificationCount(Long fsm1userId) {
        Long ebysNotificationCount = 0L;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            ebysNotificationCount = (Long) session.createCriteria(ABPMWorkItem.class)
                    .createAlias("abpmTask", "a")
                    .add(Restrictions.eq("action", "PROGRESS"))
                    .add(Restrictions.eq("a.eImzaRequired", "EVET"))
                    .add(Restrictions.eq("fsm1UsersPerformer", fsm1userId))
                    .setProjection(Projections.rowCount())
                    .uniqueResult();
        } catch (Exception e) {
            LOG.debug("An error occurred while fetching the ebys notification count !");
        } finally {
            session.close();
        }

        return ebysNotificationCount;
    }

    public Long getMesajNotificationCount(Long ihr1personelId) {
        Long mesajNotificationCount = 0L;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            mesajNotificationCount = (Long) session.createCriteria(VeilMesajLine.class)
                    .add(Restrictions.isNull("okunmaZamani"))
                    .add(Restrictions.eq("isActive", true))
                    .add(Restrictions.eq("ihr1PersonelIletilenId", ihr1personelId))
                    .setProjection(Projections.rowCount())
                    .uniqueResult();
        } catch (Exception e) {
            LOG.debug("An error occurred while fetching the mesajlasma notification count !");
        } finally {
            session.close();
        }

        return mesajNotificationCount;
    }

    public Long getGelenBasvuruNotificationCount(Long ihr1personelId) {
        Long gelenBasvuruNotificationCount = 0L;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            gelenBasvuruNotificationCount = (Long) session.createCriteria(EDM1IsAkisiAdim.class)
                    .createAlias("ddm1IsAkisi", "d")
                    .add(Restrictions.eq("durumu", "S"))
                    .add(Restrictions.ne("sonucDurumu", "T"))
                    .add(Restrictions.isNotNull("alcMsm2OrganizasyonId"))
                    .add(Restrictions.gt("alcMsm2OrganizasyonId", 0L))
                    .add(Restrictions.neProperty("alcMsm2OrganizasyonId", "gonMsm2OrganizasyonId"))
                    .add(Restrictions.in("d.turu", Arrays.asList("S", "K")))
                    .add(Restrictions.eq("alcIhr1PersonelId", ihr1personelId))
                    .setProjection(Projections.rowCount())
                    .uniqueResult();

        } catch (Exception e) {
            LOG.debug("An error occurred while fetching the gelen basvuru notification count !");
        } finally {
            session.close();
        }

        return gelenBasvuruNotificationCount;
    }

    public RemoteNotificationResponseDTO pushNotification(RemoteNotificationRequestDTO remoteNotificationRequestDTO) {

        RemoteNotificationResponseDTO remoteNotificationResponseDTO = new RemoteNotificationResponseDTO();
        ErrorDTO errorDTO = new ErrorDTO();
        try {
            String androidFcmKey = "AAAAzs170ak:APA91bF_LFawwsxo0IdWew8k8DiTktQOiOwDw6MTfXXICZv-jh7r9SFB01NlVz6eHp0ok9QkJw26yPnsW-rR5rS3D0r84QAF4w0iKFcUtrhR0qklZtfByUXqJi36X-tdFXAaB2Vf6BMKmnLfBG_2MaYtY8B2LUk-pg";
            String androidFcmUrl = "https://fcm.googleapis.com/fcm/send";

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "key=" + androidFcmKey);
            httpHeaders.set("Content-Type", "application/json");
            JSONObject msg= new JSONObject();

            JSONObject msgOnly = new JSONObject();

            msg.put("_body", "Onay Bekleyen Evraklar Var. \uD83D\uDCDD");

            msgOnly.put("data",msg);
            msgOnly.put("to", remoteNotificationRequestDTO.getDeviceToken());
            msgOnly.put("priority", "high");

            HttpEntity<String> msgHttpEntity = new HttpEntity<String>(msgOnly.toString(), httpHeaders);
            String msgResponse = restTemplate.postForObject(androidFcmUrl, msgHttpEntity, String.class);
            JSONObject msgResponseData = new JSONObject(msgResponse);
            if (msgResponseData.get("success").equals(0)) {
                errorDTO.setError(true);
                errorDTO.setErrorMessage("Firebase message http request failed");
                remoteNotificationResponseDTO.setErrorDTO(errorDTO);
            }
            System.out.println(msgResponseData);

        } catch (JSONException e) {
            e.printStackTrace();
            errorDTO.setError(true);
            errorDTO.setErrorMessage("Notification gönderirken hata oluştu");
            remoteNotificationResponseDTO.setErrorDTO(errorDTO);
        }
        return remoteNotificationResponseDTO;
    }

}
