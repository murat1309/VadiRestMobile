package com.digikent.denetimyonetimi.dto.tespit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 2.02.2018.
 */
public class TespitDTO implements Serializable {

    private Long id;
    private Long sirasi;
    private Long ekSure;
    private KanunDTO kanunDTO;
    private TespitTarifeDTO tespitTarifeDTO;
    private String kayitOzelIsmi;
    private String secenekTuru;
    private String aksiyon;
    private String ekSureVerilebilirMi;
    private String izahat;
    private String tanim;
    private List<SecenekTuruDTO> secenekTuruDTOList = new ArrayList<>();

    public TespitDTO() {
    }

    public TespitDTO(Long id, Long sirasi, Long ekSure, KanunDTO kanunDTO, String kayitOzelIsmi, String secenekTuru, String aksiyon, String ekSureVerilebilirMi, String izahat, String tanim, List<SecenekTuruDTO> secenekTuruDTOList) {
        this.id = id;
        this.sirasi = sirasi;
        this.ekSure = ekSure;
        this.kanunDTO = kanunDTO;
        this.kayitOzelIsmi = kayitOzelIsmi;
        this.secenekTuru = secenekTuru;
        this.aksiyon = aksiyon;
        this.ekSureVerilebilirMi = ekSureVerilebilirMi;
        this.izahat = izahat;
        this.tanim = tanim;
        this.secenekTuruDTOList = secenekTuruDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSirasi() {
        return sirasi;
    }

    public void setSirasi(Long sirasi) {
        this.sirasi = sirasi;
    }

    public Long getEkSure() {
        return ekSure;
    }

    public void setEkSure(Long ekSure) {
        this.ekSure = ekSure;
    }

    public KanunDTO getKanunDTO() {
        return kanunDTO;
    }

    public void setKanunDTO(KanunDTO kanunDTO) {
        this.kanunDTO = kanunDTO;
    }

    public String getKayitOzelIsmi() {
        return kayitOzelIsmi;
    }

    public void setKayitOzelIsmi(String kayitOzelIsmi) {
        this.kayitOzelIsmi = kayitOzelIsmi;
    }

    public String getSecenekTuru() {
        return secenekTuru;
    }

    public void setSecenekTuru(String secenekTuru) {
        this.secenekTuru = secenekTuru;
    }

    public String getAksiyon() {
        return aksiyon;
    }

    public void setAksiyon(String aksiyon) {
        this.aksiyon = aksiyon;
    }

    public String getEkSureVerilebilirMi() {
        return ekSureVerilebilirMi;
    }

    public void setEkSureVerilebilirMi(String ekSureVerilebilirMi) {
        this.ekSureVerilebilirMi = ekSureVerilebilirMi;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public List<SecenekTuruDTO> getSecenekTuruDTOList() {
        return secenekTuruDTOList;
    }

    public void setSecenekTuruDTOList(List<SecenekTuruDTO> secenekTuruDTOList) {
        this.secenekTuruDTOList = secenekTuruDTOList;
    }

    public TespitTarifeDTO getTespitTarifeDTO() {
        return tespitTarifeDTO;
    }

    public void setTespitTarifeDTO(TespitTarifeDTO tespitTarifeDTO) {
        this.tespitTarifeDTO = tespitTarifeDTO;
    }
}
