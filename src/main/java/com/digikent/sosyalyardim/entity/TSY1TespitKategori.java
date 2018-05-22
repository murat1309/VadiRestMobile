package com.digikent.sosyalyardim.entity;

import com.digikent.domain.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 14.05.2018.
 */
@Entity
@Table(name="TSY1TESPITKATEGORI")
public class TSY1TespitKategori extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "tsy1tespitkategori_seq", sequenceName = "TSY1TESPITKATEGORI_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tsy1tespitkategori_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "DOSYABIREY")
    private Long dosyabirey;

    @Column(name = "AKTIF")
    private Long aktif;

    @Column(name = "SIRA")
    private Long sira;

    @Column(name = "KAYITOZELISMI")
    private String kayitOzelIsmi;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="tsy1TespitKategori")
    @Where(clause = "isActive = 'E'")
    List<TSY1TespitSoru> tsy1TespitSoruList = new ArrayList<>();

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

    public Long getDosyabirey() {
        return dosyabirey;
    }

    public void setDosyabirey(Long dosyabirey) {
        this.dosyabirey = dosyabirey;
    }

    public Long getAktif() {
        return aktif;
    }

    public void setAktif(Long aktif) {
        this.aktif = aktif;
    }

    public Long getSira() {
        return sira;
    }

    public void setSira(Long sira) {
        this.sira = sira;
    }

    public String getKayitOzelIsmi() {
        return kayitOzelIsmi;
    }

    public void setKayitOzelIsmi(String kayitOzelIsmi) {
        this.kayitOzelIsmi = kayitOzelIsmi;
    }

    public List<TSY1TespitSoru> getTsy1TespitSoruList() {
        return tsy1TespitSoruList;
    }

    public void setTsy1TespitSoruList(List<TSY1TespitSoru> tsy1TespitSoruList) {
        this.tsy1TespitSoruList = tsy1TespitSoruList;
    }
}
