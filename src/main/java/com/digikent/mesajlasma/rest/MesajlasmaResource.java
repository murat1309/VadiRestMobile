package com.digikent.mesajlasma.rest;

import com.digikent.mesajlasma.dao.MesajlasmaRepository;
import com.digikent.mesajlasma.dto.*;
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
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/mesajlasma")
public class MesajlasmaResource {

    private final Logger LOG = LoggerFactory.getLogger(MesajlasmaResource.class);

    @Inject
    MesajlasmaRepository mesajlasmaRepository;

    /*
    Mesaj gönderilme apisi
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ErrorDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        LOG.debug("REST request to send message from " + messageDTO.getIhr1PersonelYazanId() + " to " + messageDTO.getIhr1PersonelIletilenId());
        LOG.debug("Charachter length = " + messageDTO.getMesaj().length());
        Boolean result = mesajlasmaRepository.savePersonalMessage(messageDTO);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError(!result);
        if (!result)
            errorDTO.setErrorMessage("Hata Oluştu");

        return new ResponseEntity<ErrorDTO>(errorDTO, OK);
    }

    /*
    tüm kullanıcılar çekilir. (parametredeki hariç)
     */
    @RequestMapping(value = "/users/{username}/", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<List<MessageUserDTO>> getDemirbasDTOAndDemirbasHareket(@PathVariable("username") String username) {
        LOG.debug("REST request to get userList");
        List<MessageUserDTO> userDTOList = mesajlasmaRepository.getUserList(username);

        return new ResponseEntity<List<MessageUserDTO>>(userDTOList, OK);
    }

    /*
    Grup oluştur denildiği zaman
     */
    @RequestMapping(value = "/create/group", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<GroupResponseDTO> createGroup(@RequestBody GroupRequest groupRequest) {
        LOG.debug("REST request to create group");
        Long groupId = mesajlasmaRepository.createGroup(groupRequest);
        GroupResponseDTO groupResponseDTO = new GroupResponseDTO();

        if (groupId.longValue() == 0) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError(true);
            errorDTO.setErrorMessage("Hata Oluştu");
            groupResponseDTO.setErrorDTO(errorDTO);
        } else {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError(false);
            errorDTO.setErrorMessage(null);
            groupResponseDTO.setGroupId(groupId);
            groupResponseDTO.setErrorDTO(errorDTO);
        }

        return new ResponseEntity<GroupResponseDTO>(groupResponseDTO, OK);
    }

    /*
    Mesajlaşma Sohbetler kısmındaki data
     */
    @RequestMapping(value = "/inbox/{whom}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<InboxResponseDTO> getInboxMessage(@PathVariable("whom") Long personelId) {
        LOG.debug("REST request to get message list whom id : " + personelId);
        List<InboxMessageDTO> inboxMessageDTOList = mesajlasmaRepository.getMessageLineList(personelId);

        ErrorDTO errorDTO = new ErrorDTO(false,null);
        InboxResponseDTO inboxResponseDTO = new InboxResponseDTO(errorDTO,inboxMessageDTOList);

        return new ResponseEntity<InboxResponseDTO>(inboxResponseDTO, OK);
    }

    /*
    Mesajlaşma ekranındaki data
     */
    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<MessageLineResponseDTO> updateMessageReaded(@RequestBody MessageLineRequestDTO messageLineRequestDTO) {
        LOG.debug("REST request to get messages, personelID = " + messageLineRequestDTO.getPersonelId() + ", getirilen " + messageLineRequestDTO.getIlentilenPersonelId());
        List<MessageLineDTO> messageLineDTOList = mesajlasmaRepository.getMessageLinesByPersonelId(messageLineRequestDTO);
        ErrorDTO errorDTO = new ErrorDTO(false,null);
        MessageLineResponseDTO messageLineResponseDTO = new MessageLineResponseDTO(errorDTO,messageLineDTOList);

        return new ResponseEntity<MessageLineResponseDTO>(messageLineResponseDTO, OK);
    }

}
