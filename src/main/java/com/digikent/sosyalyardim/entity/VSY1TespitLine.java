package com.digikent.sosyalyardim.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 18.05.2018.
 */
@Entity
@Table(name = "VSY1TESPITLINE")
public class VSY1TespitLine extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "vsy1tespitline_seq", sequenceName = "VSY1TESPITLINE_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vsy1tespitline_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "VSY1TESPIT_ID")
    private VSY1Tespit vsy1Tespit;

    @Column(name = "VSY1DOSYA_ID")
    private Long dosyaId;

    @Column(name = "TSY1TESPITKATEGORI_ID")
    private Long kategoriId;

    @Column(name = "TSY1TESPITSORU_ID")
    private Long soruId;

    @Column( name = "BILGI")
    private String bilgi;

    @Column(name = "DEGER")
    private Long deger;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public VSY1Tespit getVsy1Tespit() {
        return vsy1Tespit;
    }

    public void setVsy1Tespit(VSY1Tespit vsy1Tespit) {
        this.vsy1Tespit = vsy1Tespit;
    }

    public Long getDosyaId() {
        return dosyaId;
    }

    public void setDosyaId(Long dosyaId) {
        this.dosyaId = dosyaId;
    }

    public Long getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(Long kategoriId) {
        this.kategoriId = kategoriId;
    }

    public Long getSoruId() {
        return soruId;
    }

    public void setSoruId(Long soruId) {
        this.soruId = soruId;
    }

    public String getBilgi() {
        return bilgi;
    }

    public void setBilgi(String bilgi) {
        this.bilgi = bilgi;
    }

    public Long getDeger() {
        return deger;
    }

    public void setDeger(Long deger) {
        this.deger = deger;
    }
}
