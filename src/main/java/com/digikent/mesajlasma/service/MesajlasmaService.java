package com.digikent.mesajlasma.service;

import com.digikent.mesajlasma.dao.MesajlasmaRepository;
import com.digikent.mesajlasma.dto.*;

import java.math.BigDecimal;

import net.sf.saxon.trans.Err;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Medet on 12/13/2017.
 */
@Service
public class MesajlasmaService {

    @Inject
    MesajlasmaRepository mesajlasmaRepository;

    public GroupInfoResponseDTO getGroupInfoByGroupId(Long groupId) {
        GroupInfoResponseDTO groupInfoResponseDTO = new GroupInfoResponseDTO();

        List<MessageUserDTO> userList = mesajlasmaRepository.getGroupUsersByGroupId(groupId); // Implementation of Database side of things
        groupInfoResponseDTO.setUserList(userList);

        return groupInfoResponseDTO;

    }


    public ErrorDTO deleteGroupByGroupId(Long groupId) {

        ErrorDTO errorDTO;

        errorDTO = mesajlasmaRepository.deleteGroupByGroupId(groupId);

        return errorDTO;
    }

}
