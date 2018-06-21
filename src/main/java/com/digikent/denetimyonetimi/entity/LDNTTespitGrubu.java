package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LDNTTESPITGRUBU")
public class LDNTTespitGrubu extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "ldnttespitgrubu_seq", sequenceName = "LDNTTESPITGRUBU_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ldnttespitgrubu_seq")
    @Column(name="ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "KAYITOZELISMI")
    private String kayitOzelIsmi;

    @Column(name = "IZAHAT")
    private String izahat;

    @Column(name = "ALTBILGI")
    private String altBilgi;

    @Column(name = "RAPORBASLIK")
    private String raporBaslik;

    @Column(name = "KARARVERILEBILIRMI")
    private String kararVerileBilirMi;

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

    public String getKayitOzelIsmi() {
        return kayitOzelIsmi;
    }

    public void setKayitOzelIsmi(String kayitOzelIsmi) {
        this.kayitOzelIsmi = kayitOzelIsmi;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public String getAltBilgi() {
        return altBilgi;
    }

    public void setAltBilgi(String altBilgi) {
        this.altBilgi = altBilgi;
    }

    public String getRaporBaslik() {
        return raporBaslik;
    }

    public void setRaporBaslik(String raporBaslik) {
        this.raporBaslik = raporBaslik;
    }

    public String getKararVerileBilirMi() {
        return kararVerileBilirMi;
    }

    public void setKararVerileBilirMi(String kararVerileBilirMi) {
        this.kararVerileBilirMi = kararVerileBilirMi;
    }
}
