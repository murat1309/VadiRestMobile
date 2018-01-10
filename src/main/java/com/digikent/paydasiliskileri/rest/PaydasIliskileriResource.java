package com.digikent.paydasiliskileri.rest;

import com.digikent.paydasiliskileri.dao.PaydasIliskileriRepository;
import com.digikent.paydasiliskileri.dto.PaydasSorguRequestDTO;
import com.digikent.paydasiliskileri.dto.PaydasSorguResponseDTO;
import com.digikent.paydasiliskileri.service.PaydasIliskileriManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Medet on 1/2/2018.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("ybs/")
public class PaydasIliskileriResource {

    private final Logger LOG = LoggerFactory.getLogger(PaydasIliskileriResource.class);

    @Inject
    PaydasIliskileriRepository paydasIliskileriRepository;

    @Autowired
    PaydasIliskileriManagementService paydasIliskileriManagementService;

    @RequestMapping("paydassorgu")
    @Consumes(APPLICATION_JSON_VALUE)
    @Produces(APPLICATION_JSON_VALUE)
    public PaydasSorguResponseDTO getPaydasInfoByCriteria(@RequestBody PaydasSorguRequestDTO paydasSorguRequestDTO) {
        PaydasSorguResponseDTO paydasSorguResponseDTO;

        LOG.debug(" /paydassorgu Gelen Paydas Sorgu No : " + paydasSorguRequestDTO.getPaydasNo());
        LOG.debug(" /paydassorgu Gelen Paydas T.C Kimlik No : " + paydasSorguRequestDTO.getTcNo());
        LOG.debug(" /paydassorgu Gelen Paydas Vergi No : " + paydasSorguRequestDTO.getVergiNo());
        LOG.debug(" /paydassorgu Gelen Paydas Sorgu Adi : " + paydasSorguRequestDTO.getSorguAdi());

        paydasSorguResponseDTO = paydasIliskileriManagementService.getPaydasInfoByCriteria(paydasSorguRequestDTO);

        return paydasSorguResponseDTO;
    }

    @RequestMapping("paydasborcsorgu")
    @Consumes(APPLICATION_JSON_VALUE)
    @Produces(APPLICATION_JSON_VALUE)
    public PaydasSorguResponseDTO getPaydasInfoAndDebtInfo(@RequestBody PaydasSorguRequestDTO paydasSorguRequestDTO) {
        PaydasSorguResponseDTO paydasSorguResponseDTO = new PaydasSorguResponseDTO();

        LOG.debug(" /paydasborcsorgu Gelen Paydas Sorgu No : " + paydasSorguRequestDTO.getPaydasNo());
        LOG.debug(" /paydasborcsorgu Gelen Paydas T.C Kimlik No : " + paydasSorguRequestDTO.getTcNo());
        LOG.debug(" /paydasborcsorgu Gelen Paydas Vergi No : " + paydasSorguRequestDTO.getVergiNo());
        LOG.debug(" /paydasborcsorgu Gelen Paydas Sorgu Adi : " + paydasSorguRequestDTO.getSorguAdi());

        paydasSorguResponseDTO = paydasIliskileriManagementService.getPaydasInfoByCriteria(paydasSorguRequestDTO);
        paydasSorguResponseDTO = paydasIliskileriManagementService.getPaydasDebtInfoByCriteria(paydasSorguResponseDTO, paydasSorguRequestDTO);

        return paydasSorguResponseDTO;
    }

    @RequestMapping("paydasilansorgu")
    @Consumes(APPLICATION_JSON_VALUE)
    @Produces(APPLICATION_JSON_VALUE)
    public PaydasSorguResponseDTO getPaydasInfoAndAdvertInfo(@RequestBody PaydasSorguRequestDTO paydasSorguRequestDTO) {
        PaydasSorguResponseDTO paydasSorguResponseDTO = new PaydasSorguResponseDTO();

        LOG.debug(" /paydasilansorgu Gelen Paydas Sorgu No : " + paydasSorguRequestDTO.getPaydasNo());
        LOG.debug(" /paydasilansorgu Gelen Paydas T.C Kimlik No : " + paydasSorguRequestDTO.getTcNo());
        LOG.debug(" /paydasilansorgu Gelen Paydas Vergi No : " + paydasSorguRequestDTO.getVergiNo());
        LOG.debug(" /paydasilansorgu Gelen Paydas Sorgu Adi : " + paydasSorguRequestDTO.getSorguAdi());

        paydasSorguResponseDTO = paydasIliskileriManagementService.getPaydasInfoByCriteria(paydasSorguRequestDTO);
        paydasSorguResponseDTO = paydasIliskileriManagementService.getPaydasAdvertInfoByCriteria(paydasSorguResponseDTO, paydasSorguRequestDTO);

        return paydasSorguResponseDTO;
    }



}
