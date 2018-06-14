package com.digikent.surecyonetimi.izinonay.rest;

import com.digikent.surecyonetimi.izinonay.dto.IzinSurecListResponse;
import com.digikent.surecyonetimi.izinonay.service.IzinSurecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Kadir on 13.06.2018.
 */
@Controller
@RequestMapping("/izinsurec")
public class IzinSurecResource {

    private final Logger LOG = LoggerFactory.getLogger(IzinSurecResource.class);

    @Inject
    private IzinSurecService izinSurecService;

    /**
     * belediyeye ait izin onay url getirilir
     * @return
     */
    @RequestMapping(method = GET, value = "/list/{username}")
    @Transactional
    public ResponseEntity<IzinSurecListResponse> getIzinSurecListUrl(@PathVariable("username") String username) {
        LOG.info("belediyeye ait izin surec url'i getirilecek");
        IzinSurecListResponse response = izinSurecService.getIzinSurecListByUsername(username);

        return new ResponseEntity<IzinSurecListResponse>(response, OK);
    }

}
