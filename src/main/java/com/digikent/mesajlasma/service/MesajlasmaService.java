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


    public ErrorDTO deleteGroupByGroupId(GroupDeleteRequestDTO groupDeleteRequestDTO) {

        ErrorDTO errorDTO;

        errorDTO = mesajlasmaRepository.deleteGroupByGroupId(groupDeleteRequestDTO);

        return errorDTO;
    }


    public ErrorDTO groupLeaveByUserId(GroupLeaveRequestDTO groupLeaveRequestDTO) {

        ErrorDTO errorDTO;

        mesajlasmaRepository.sendDefaultMessageWhenDiscardOrLeave(groupLeaveRequestDTO);
        errorDTO = mesajlasmaRepository.updateIsActiveInGroupLineByUserIdAndGroupId(groupLeaveRequestDTO, 'H');


        return errorDTO;
    }


    public ErrorDTO userDiscardByGroupAndUserId(GroupLeaveRequestDTO groupLeaveRequestDTO) {

        ErrorDTO errorDTO;

        mesajlasmaRepository.sendDefaultMessageWhenDiscardOrLeave(groupLeaveRequestDTO);
        errorDTO = mesajlasmaRepository.updateIsActiveInGroupLineByUserIdAndGroupId(groupLeaveRequestDTO, 'H');

        return errorDTO;
    }

    public ErrorDTO groupUserAddByUserList(GroupRequest groupRequest) {

        ErrorDTO errorDTO;

        errorDTO = mesajlasmaRepository.groupUserAddByUserList(groupRequest);

        return errorDTO;
    }
}
