package com.digikent.mesajlasma.service;

import com.digikent.mesajlasma.dao.MesajlasmaRepository;
import com.digikent.mesajlasma.dto.*;

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


    public ErrorDTO groupLeaveByUserId(Long userId, Long groupId) {

        ErrorDTO errorDTO;

        errorDTO = mesajlasmaRepository.updateIsActiveInGroupLineByUserIdAndGroupId(userId, groupId, 'H');

        return errorDTO;
    }


    public ErrorDTO userDiscardByGroupAndUserId(Long userId, Long groupId) {

        ErrorDTO errorDTO;

        errorDTO = mesajlasmaRepository.updateIsActiveInGroupLineByUserIdAndGroupId(userId, groupId, 'H');

        return errorDTO;
    }

    public ErrorDTO groupUserAddByUserList(GroupRequest groupRequest) {

        ErrorDTO errorDTO;

        errorDTO = mesajlasmaRepository.groupUserAddByUserList(groupRequest);

        return errorDTO;
    }
}
