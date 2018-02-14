package com.digikent.basvuruyonetimi.dto;

/**
 * Created by Serkan on 5/20/16.
 */
public class Dm1IsakisiAdimAttachmentDTO {

    private DM1IsAkısıAdımDTO dm1IsAkisiAdimDTO;
    private byte[] attachment;

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public DM1IsAkısıAdımDTO getDm1IsAkisiAdimDTO() {
        return dm1IsAkisiAdimDTO;
    }

    public void setDm1IsAkisiAdimDTO(DM1IsAkısıAdımDTO dm1IsAkisiAdimDTO) {
        this.dm1IsAkisiAdimDTO = dm1IsAkisiAdimDTO;
    }
}
