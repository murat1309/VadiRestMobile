package com.digikent.surecyonetimi.izinonay.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BSM2SERVIS")
public class BSM2Servis extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "bsm2servis_seq", sequenceName = "BSM2SERVIS_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bsm2servis_seq")
    @Column(name = "ID")
    private Long ID;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "IZAHAT")
    private String izahat;

    @Column(name = "KISALTMA")
    private String kisaltma;

    @Column(name = "KODU")
    private String kodu;

    @Column(name = "TURU")
    private String turu;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public String getKisaltma() {
        return kisaltma;
    }

    public void setKisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
    }

    public String getKodu() {
        return kodu;
    }

    public void setKodu(String kodu) {
        this.kodu = kodu;
    }

    public String getTuru() {
        return turu;
    }

    public void setTuru(String turu) {
        this.turu = turu;
    }
}
