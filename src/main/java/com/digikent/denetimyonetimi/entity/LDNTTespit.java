package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 14.02.2018.
 */
@Entity
@Table(name="LDNTTESPIT")
public class LDNTTespit extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "ldnttespit_seq", sequenceName = "LDNTTESPIT_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ldnttespit_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "LSM2KANUN_ID")
    private LSM2Kanun lsm2Kanun;

    @Column(name = "LDNTTESPITGRUBU_ID")
    private Long tespitGrubuId;

    @Column(name = "SIRASI")
    private Long sirasi;

    @Column(name = "EKSURE")
    private Long ekSure;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "KAYITOZELISMI")
    private String kayitOzelIsmi;

    @Column(name = "SECENEKTURU")
    private String secenekTuru;

    @Column(name = "AKSIYON")
    private String aksiyon;

    @Column(name = "EKSUREVERILEBILIRMI")
    private String ekSureVerilebilirMi;

    @Column(name = "IZAHAT")
    private String izahat;

    @Column(name = "TUR")
    private String tur;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getTespitGrubuId() {
        return tespitGrubuId;
    }

    public void setTespitGrubuId(Long tespitGrubuId) {
        this.tespitGrubuId = tespitGrubuId;
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

    public LSM2Kanun getLsm2Kanun() {
        return lsm2Kanun;
    }

    public void setLsm2Kanun(LSM2Kanun lsm2Kanun) {
        this.lsm2Kanun = lsm2Kanun;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }
}
