package com.digikent.basvuruyonetimi.dto;


import com.vadi.smartkent.datamodel.domains.icerikyonetimi.dm1.DM1IsAkisiAdim;

/**
 * Created by Serkan on 5/20/16.
 */
public class Dm1IsakisiAdimAttachmentDTO {

    private DM1IsAkisiAdim dm1IsAkisiAdim;
    private byte[] attachment;

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public DM1IsAkisiAdim getDm1IsAkisiAdim() {
        return dm1IsAkisiAdim;
    }

    public void setDm1IsAkisiAdim(DM1IsAkisiAdim dm1IsAkisiAdim) {
        this.dm1IsAkisiAdim = dm1IsAkisiAdim;
    }

}
