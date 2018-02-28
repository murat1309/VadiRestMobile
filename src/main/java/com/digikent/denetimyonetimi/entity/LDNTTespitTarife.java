package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kadir on 14.02.2018.
 */
@Entity
@Table(name="LDNTTESPITTARIFE")
public class LDNTTespitTarife extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "ldnttespittarife_seq", sequenceName = "LDNTTESPITTARIFE_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ldnttespittarife_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "LDNTTESPIT_ID")
    private Long ldntTespitId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BASLANGICTARIHI", nullable = false)
    private Date baslangicTarihi = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BITISTARIHI", nullable = false)
    private Date bitisTarihi = new Date();

    @Column(name = "ALTLIMITTUTARI")
    private Long altLimitTutari;

    @Column(name = "USTLIMITTUTARI")
    private Long ustLimitTutari;

    @Column(name = "IZAHAT")
    private String izahat;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getLdntTespitId() {
        return ldntTespitId;
    }

    public void setLdntTespitId(Long ldntTespitId) {
        this.ldntTespitId = ldntTespitId;
    }

    public Date getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(Date baslangicTarihi) {
        this.baslangicTarihi = baslangicTarihi;
    }

    public Date getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(Date bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public Long getAltLimitTutari() {
        return altLimitTutari;
    }

    public void setAltLimitTutari(Long altLimitTutari) {
        this.altLimitTutari = altLimitTutari;
    }

    public Long getUstLimitTutari() {
        return ustLimitTutari;
    }

    public void setUstLimitTutari(Long ustLimitTutari) {
        this.ustLimitTutari = ustLimitTutari;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }
}
