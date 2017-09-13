package com.digikent.mesajlasma.rest;

import com.digikent.mesajlasma.dao.MesajlasmaRepository;
import com.digikent.mesajlasma.dto.MessageDTO;
import com.digikent.mesajlasma.dto.MessageUserDTO;
import com.digikent.vadirest.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Serkan on 11/09/17.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/mesajlasma")
public class MesajlasmaResource {

    private final Logger LOG = LoggerFactory.getLogger(MesajlasmaResource.class);

    @Inject
    MesajlasmaRepository mesajlasmaRepository;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        LOG.debug("REST request to send message from " + messageDTO.getIhr1PersonelYazanId() + " to " + messageDTO.getIhr1PersonelIletilenId());
        LOG.debug("Charachter length = " + messageDTO.getMesaj().length());
        mesajlasmaRepository.savePersonalMessage(messageDTO);
        return new ResponseEntity<MessageDTO>(messageDTO, OK);
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<MessageUserDTO>> getDemirbasDTOAndDemirbasHareket(@PathVariable("username") String username) {
        LOG.debug("REST request to get userList");
        List<MessageUserDTO> userDTOList = mesajlasmaRepository.getUserList(username);

        return new ResponseEntity<List<MessageUserDTO>>(userDTOList, OK);
    }


}
