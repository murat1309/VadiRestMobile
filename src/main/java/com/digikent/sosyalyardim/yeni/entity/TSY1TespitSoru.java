package com.digikent.sosyalyardim.yeni.entity;

import com.digikent.domain.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 14.05.2018.
 */
@Entity
@Table(name = "TSY1TESPITSORU")
public class TSY1TespitSoru extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "tsy1tespitsoru_seq", sequenceName = "TSY1TESPITSORU_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tsy1tespitsoru_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "TSY1TESPITSORUTURU_ID")
    private TSY1TespitSoruTuru tsy1TespitSoruTuru;

    @ManyToOne
    @JoinColumn(name = "TSY1TESPITKATEGORI_ID")
    private TSY1TespitKategori tsy1TespitKategori;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "SIRA")
    private Long sira;

    @Column(name = "CEVAP")
    private Long cevap;

    @Column(name = "AKTIF")
    private Long aktif;

    @Column(name = "KAYITOZELISMI")
    private String kayitOzelIsmi;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public TSY1TespitSoruTuru getTsy1TespitSoruTuru() {
        return tsy1TespitSoruTuru;
    }

    public void setTsy1TespitSoruTuru(TSY1TespitSoruTuru tsy1TespitSoruTuru) {
        this.tsy1TespitSoruTuru = tsy1TespitSoruTuru;
    }

    public TSY1TespitKategori getTsy1TespitKategori() {
        return tsy1TespitKategori;
    }

    public void setTsy1TespitKategori(TSY1TespitKategori tsy1TespitKategori) {
        this.tsy1TespitKategori = tsy1TespitKategori;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public Long getSira() {
        return sira;
    }

    public void setSira(Long sira) {
        this.sira = sira;
    }

    public Long getCevap() {
        return cevap;
    }

    public void setCevap(Long cevap) {
        this.cevap = cevap;
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
}
