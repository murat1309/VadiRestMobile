package com.digikent.sosyalyardim.entity;

import com.digikent.domain.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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

    @ManyToOne
    @JoinColumn(name = "TSY1TESPITKATEGORI_ID")
    private TSY1TespitKategori kategori;

    @ManyToOne
    @JoinColumn(name = "TSY1TESPITSORU_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    private TSY1TespitSoru soru;

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

    public TSY1TespitKategori getKategori() {
        return kategori;
    }

    public void setKategori(TSY1TespitKategori kategori) {
        this.kategori = kategori;
    }

    public TSY1TespitSoru getSoru() {
        return soru;
    }

    public void setSoru(TSY1TespitSoru soru) {
        this.soru = soru;
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
