package com.digikent.surecyonetimi.izinonay.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "YHR1IZINTURU")
public class YHR1IzinTuru extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "yhr1izinturu_seq", sequenceName = "YHR1IZINTURU_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yhr1izinturu_seq")
    @Column(name = "ID")
    private Long ID;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "IZAHAT")
    private String izahat;

    @Column(name = "KISALTMA")
    private String kisaltma;

    @Column(name = "KAYITOZELISMI")
    private String kayitOzelIsmi;

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

    public String getKayitOzelIsmi() {
        return kayitOzelIsmi;
    }

    public void setKayitOzelIsmi(String kayitOzelIsmi) {
        this.kayitOzelIsmi = kayitOzelIsmi;
    }
}
