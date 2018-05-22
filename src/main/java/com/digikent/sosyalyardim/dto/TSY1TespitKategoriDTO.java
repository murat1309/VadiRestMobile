package com.digikent.sosyalyardim.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 14.05.2018.
 */
public class TSY1TespitKategoriDTO {

    private Long id;
    private String tanim;
    private Long dosyaBirey;
    private Long aktif;
    private String kayitOzelIsmi;
    private List<TSY1TespitSoruDTO> soruDTOList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public Long getDosyaBirey() {
        return dosyaBirey;
    }

    public void setDosyaBirey(Long dosyaBirey) {
        this.dosyaBirey = dosyaBirey;
    }

    public Long getAktif() {
        return aktif;
    }

    public void setAktif(Long aktif) {
        this.aktif = aktif;
    }

    public String getKayitOzelIsmi() {
        return kayitOzelIsmi;
    }

    public void setKayitOzelIsmi(String kayitOzelIsmi) {
        this.kayitOzelIsmi = kayitOzelIsmi;
    }

    public List<TSY1TespitSoruDTO> getSoruDTOList() {
        return soruDTOList;
    }

    public void setSoruDTOList(List<TSY1TespitSoruDTO> soruDTOList) {
        this.soruDTOList = soruDTOList;
    }
}
