package com.digikent.surecyonetimi.izinonay.service;

import com.digikent.general.util.ErrorCode;
import com.digikent.mesajlasma.dto.ErrorDTO;
import com.digikent.surecyonetimi.izinonay.dao.IzinSurecRepository;
import com.digikent.surecyonetimi.izinonay.dto.IzinSurecDTO;
import com.digikent.surecyonetimi.izinonay.dto.IzinSurecDetayDTO;
import com.digikent.surecyonetimi.izinonay.dto.IzinSurecDetayResponse;
import com.digikent.surecyonetimi.izinonay.dto.IzinSurecListResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * Created by Kadir on 13.06.2018.
 */
@Service
public class IzinSurecService {

    private final Logger LOG = LoggerFactory.getLogger(IzinSurecService.class);

    @Inject
    private Environment env;

    @Autowired
    IzinSurecRepository izinSurecRepository;

    private RestTemplate restTemplate = new RestTemplate();

    public IzinSurecListResponse getIzinSurecListByUsername(String username) {

        IzinSurecListResponse izinSurecListResponse = new IzinSurecListResponse();
        List<IzinSurecDTO> izinSurecDTOList = null;
        try {
            String izinSurecUrl = env.getProperty("izinSurecUrl") + username;
            HttpEntity<String> entity = getHttpEntityForIzinOnayProcess();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            ResponseEntity<String> response = restTemplate.exchange(izinSurecUrl, HttpMethod.POST, entity, String.class);
            izinSurecDTOList = getIzinSurecDTOList(response);
            if (izinSurecDTOList != null && izinSurecDTOList.size() > 0) {
                izinSurecListResponse.setErrorDTO(null);
                izinSurecListResponse.setIzinSurecDTOList(izinSurecDTOList);
                LOG.info("Getirilen izin surec sayisi = " + izinSurecDTOList.size());
            } else {
                LOG.info("ERROR_CODE_701 Izin sureci bulunamamistir.");
                izinSurecListResponse.setErrorDTO(new ErrorDTO(true, ErrorCode.ERROR_CODE_701));
                izinSurecListResponse.setIzinSurecDTOList(null);
            }

        } catch (Exception ex) {
            LOG.info("ERROR_CODE_702 Izin surecleri getirilirken bir hata olustu.");
            ex.printStackTrace();
            izinSurecListResponse.setErrorDTO(new ErrorDTO(true, ErrorCode.ERROR_CODE_702));
            izinSurecListResponse.setIzinSurecDTOList(null);
        }

        return izinSurecListResponse;
    }

    private List<IzinSurecDTO> getIzinSurecDTOList(ResponseEntity<String> response) throws Exception {
        List<IzinSurecDTO> izinSurecDTOList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONObject js = new JSONObject(jsonObject.get("data").toString());
            if (js.get("data").toString().equalsIgnoreCase("null")) {
                //200 dönmüş ancak süreç bulunamamıştır
                return null;
            }
            JSONArray  jsonArray = new JSONArray(js.get("data").toString());

            for (int i = 0; i<jsonArray.length(); i++){
                String instanceName = (String)new JSONObject(jsonArray.get(i).toString()).get("instanceName");
                JSONObject jo = new JSONObject(jsonArray.get(i).toString());
                Integer instanceIdValue = (Integer)(jo.get("instanceId"));
                Long instanceId = Long.valueOf(instanceIdValue.longValue());
                IzinSurecDTO izinSurecDTO = new IzinSurecDTO(instanceName,instanceId);
                izinSurecDTOList.add(izinSurecDTO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }

        return izinSurecDTOList;
    }

    public HttpEntity<String> getHttpEntityForIzinOnayProcess() {
        HttpHeaders headers = new HttpHeaders();
        String credential = "admin:admin";

        byte[] plainCredsBytes = credential.getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        headers.add("Authorization", "Basic " + base64Creds);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers .add("Content-Type", "text/html; charset=utf-8");

        return new HttpEntity<String>("parameters", headers);
    }

    public IzinSurecDetayResponse getIzinSurecDetayDTOByInstanceId(Long instanceId) {
        IzinSurecDetayResponse izinSurecDetayResponse = new IzinSurecDetayResponse();
        try {
            IzinSurecDetayDTO izinSurecDetayDTO = izinSurecRepository.getIzinSurecDTOByInstanceId(instanceId);
            izinSurecDetayResponse.setIzinSurecDetayDTO(izinSurecDetayDTO);
            if (izinSurecDetayDTO == null)
                izinSurecDetayResponse.setErrorDTO(new ErrorDTO(true,ErrorCode.ERROR_CODE_704));
        } catch (Exception ex) {
            LOG.info("ERROR_CODE_703 Izin surec detayi getirilirken bir hata olustu.");
            ex.printStackTrace();
            izinSurecDetayResponse.setErrorDTO(new ErrorDTO(true, ErrorCode.ERROR_CODE_703));
            izinSurecDetayResponse.setIzinSurecDetayDTO(null);
        }

        return izinSurecDetayResponse;
    }
}
