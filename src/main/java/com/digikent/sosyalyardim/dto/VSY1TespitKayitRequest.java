package com.digikent.sosyalyardim.dto;

import java.util.List;

/**
 * Created by Kadir on 18.05.2018.
 */
public class VSY1TespitKayitRequest {

    private List<TSY1TespitKategoriDTO> tespitKategoriDTOList;
    private Long dosyaId;
    private Long ihr1personelId;
    private Long fsm1UsersId;

    public List<TSY1TespitKategoriDTO> getTespitKategoriDTOList() {
        return tespitKategoriDTOList;
    }

    public void setTespitKategoriDTOList(List<TSY1TespitKategoriDTO> tespitKategoriDTOList) {
        this.tespitKategoriDTOList = tespitKategoriDTOList;
    }

    public Long getDosyaId() {
        return dosyaId;
    }

    public void setDosyaId(Long dosyaId) {
        this.dosyaId = dosyaId;
    }

    public Long getIhr1personelId() {
        return ihr1personelId;
    }

    public void setIhr1personelId(Long ihr1personelId) {
        this.ihr1personelId = ihr1personelId;
    }

    public Long getFsm1UsersId() {
        return fsm1UsersId;
    }

    public void setFsm1UsersId(Long fsm1UsersId) {
        this.fsm1UsersId = fsm1UsersId;
    }
}
