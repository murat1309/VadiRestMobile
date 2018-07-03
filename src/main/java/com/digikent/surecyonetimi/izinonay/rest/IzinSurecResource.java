package com.digikent.surecyonetimi.izinonay.rest;

import com.digikent.surecyonetimi.izinonay.dto.IzinSurecDetayResponse;
import com.digikent.surecyonetimi.izinonay.dto.IzinSurecKararDTO;
import com.digikent.surecyonetimi.izinonay.dto.IzinSurecListResponse;
import com.digikent.surecyonetimi.izinonay.service.IzinSurecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Kadir on 13.06.2018.
 */
@Controller
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/izinsurec")
public class IzinSurecResource {

    private final Logger LOG = LoggerFactory.getLogger(IzinSurecResource.class);

    @Inject
    private IzinSurecService izinSurecService;

    /**
     * belediyeye ait izin süreçleri getirilecek
     * @params username : ldap username
     * @return
     */
    @RequestMapping(method = GET, value = "/list/{username}")
    @Transactional
    public ResponseEntity<IzinSurecListResponse> getIzinSurecListByUrl(@PathVariable("username") String username) {
        LOG.info("belediyeye ait izin surec listesi getirilecek. username=" + username);
        IzinSurecListResponse response = izinSurecService.getIzinSurecListByUsername(username);

        return new ResponseEntity<IzinSurecListResponse>(response, OK);
    }

    /**
     * instanceId'e göre izin detaylari getirilecek
     * @params instanceId : instanceId
     * @return
     */
    @RequestMapping(method = GET, value = "/detay/{instanceId}")
    @Transactional
    public ResponseEntity<IzinSurecDetayResponse> getIzinDetayDTOByInstanceId(@PathVariable("instanceId") Long instanceId) {
        LOG.info("instanceId'e göre izin detaylari getirilecek. instanceId=" +instanceId);
        IzinSurecDetayResponse response = izinSurecService.getIzinSurecDetayDTOByInstanceId(instanceId);

        return new ResponseEntity<IzinSurecDetayResponse>(response, OK);
    }

    /**
     * karar onay ya da red verilecek
     * @return
     */
    @RequestMapping(method = POST, value = "/action")
    @Transactional
    public ResponseEntity<Boolean> approveOrRejectIzinSureci(@RequestBody IzinSurecKararDTO izinSurecKararDTO) {
        LOG.info(" instanceID="+izinSurecKararDTO.getInstanceId() + ", Izin surec karari = "+izinSurecKararDTO.getKarar());
        Boolean result = izinSurecService.approveOrRejectedIzinSurec(izinSurecKararDTO.getInstanceId(), izinSurecKararDTO.getKarar());
        return new ResponseEntity<Boolean>(result, OK);
    }

}
