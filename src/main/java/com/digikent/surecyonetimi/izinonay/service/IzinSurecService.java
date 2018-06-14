package com.digikent.surecyonetimi.izinonay.service;

import com.digikent.surecyonetimi.izinonay.dto.IzinSurecListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Base64;

/**
 * Created by Kadir on 13.06.2018.
 */
@Service
public class IzinSurecService {

    private final Logger LOG = LoggerFactory.getLogger(IzinSurecService.class);

    @Inject
    private Environment env;

    private RestTemplate restTemplate = new RestTemplate();

    public IzinSurecListResponse getIzinSurecListByUsername(String username) {IzinSurecListResponse izinSurecListResponse = null;

        try {
            String izinSurecUrl = env.getProperty("izinSurecUrl") + username;
            HttpEntity<String> entity = getHttpEntityForIzinOnayProcess();
            ResponseEntity<String> response = restTemplate.exchange(izinSurecUrl, HttpMethod.POST, entity, String.class);
            System.out.println(response);

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return null;
    }

    public HttpEntity<String> getHttpEntityForIzinOnayProcess() {
        HttpHeaders headers = new HttpHeaders();
        String credential = "admin:admin";

        byte[] plainCredsBytes = credential.getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        headers.add("Authorization", "Basic " + base64Creds);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity<String>("parameters", headers);
    }
}
