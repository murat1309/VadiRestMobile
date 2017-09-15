package com.digikent.mesajlasma.dto;

/**
 * Created by Kadir on 13/09/17.
 */
public class GroupResponseDTO {

    private ErrorDTO errorDTO;
    private Long groupId;

    public GroupResponseDTO() {
    }

    public GroupResponseDTO(ErrorDTO errorDTO, Long groupId) {
        this.errorDTO = errorDTO;
        this.groupId = groupId;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
