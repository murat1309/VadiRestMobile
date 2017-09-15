package com.digikent.mesajlasma.dto;

import com.digikent.mesajlasma.entity.TeilMesajIletimGrubu;

import java.util.Date;

/**
 * Created by Kadir on 09/11/17.
 */
public class MessageDTO {

    private Long ihr1PersonelYazanId;
    private String gonderimTuru;
    private Long ihr1PersonelIletilenId;
    private String mesaj;
    private Long groupId;

    public MessageDTO() {
    }

    public MessageDTO(Long ihr1PersonelYazanId, String gonderimTuru, Long ihr1PersonelIletilenId, String mesaj, Long groupId) {
        this.ihr1PersonelYazanId = ihr1PersonelYazanId;
        this.gonderimTuru = gonderimTuru;
        this.ihr1PersonelIletilenId = ihr1PersonelIletilenId;
        this.mesaj = mesaj;
        this.groupId = groupId;
    }

    public Long getIhr1PersonelYazanId() {
        return ihr1PersonelYazanId;
    }

    public void setIhr1PersonelYazanId(Long ihr1PersonelYazanId) {
        this.ihr1PersonelYazanId = ihr1PersonelYazanId;
    }

    public Long getIhr1PersonelIletilenId() {
        return ihr1PersonelIletilenId;
    }

    public void setIhr1PersonelIletilenId(Long ihr1PersonelIletilenId) {
        this.ihr1PersonelIletilenId = ihr1PersonelIletilenId;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getGonderimTuru() {
        return gonderimTuru;
    }

    public void setGonderimTuru(String gonderimTuru) {
        this.gonderimTuru = gonderimTuru;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
