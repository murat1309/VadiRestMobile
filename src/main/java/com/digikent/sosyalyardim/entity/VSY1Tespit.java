package com.digikent.sosyalyardim.entity;

import com.digikent.denetimyonetimi.entity.IHR1Personel;
import com.digikent.domain.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kadir on 18.05.2018.
 */
@Entity
@Table(name = "VSY1TESPIT")
public class VSY1Tespit extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "vsy1tespit_seq", sequenceName = "VSY1TESPIT_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vsy1tespit_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vsy1Tespit")
    @Where(clause = "isActive = 'E'")
    List<VSY1TespitLine> vsy1TespitLineList = new ArrayList<>();

    @Column(name = "VSY1DOSYA_ID")
    private Long dosyaId;

    @Column(name = "IHR1PERSONEL_ID")
    private Long ihr1PersonelId;

    @ManyToOne
    @JoinColumn(name = "VSY1AKTIVITE_ID")
    private VSY1Aktivite vsy1Aktivite;

    @ManyToOne
    @JoinColumn(name = "IHR1PERSONEL_TESPITYAPAN")
    private IHR1Personel ihr1PersonelTespitYapan;

    @Column(name = "TARIH")
    private Date tarih;

    @Column(name = "ALINANBILGI")
    private String alinanBilgi;

    public VSY1Tespit() {

    }

    public VSY1Tespit(Long id) {
        this.ID = id;
    }


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public List<VSY1TespitLine> getVsy1TespitLineList() {
        return vsy1TespitLineList;
    }

    public void setVsy1TespitLineList(List<VSY1TespitLine> vsy1TespitLineList) {
        this.vsy1TespitLineList = vsy1TespitLineList;
    }

    public Long getDosyaId() {
        return dosyaId;
    }

    public void setDosyaId(Long dosyaId) {
        this.dosyaId = dosyaId;
    }

    public Long getIhr1PersonelId() {
        return ihr1PersonelId;
    }

    public void setIhr1PersonelId(Long ihr1PersonelId) {
        this.ihr1PersonelId = ihr1PersonelId;
    }

    public VSY1Aktivite getVsy1Aktivite() {
        return vsy1Aktivite;
    }

    public void setVsy1Aktivite(VSY1Aktivite vsy1Aktivite) {
        this.vsy1Aktivite = vsy1Aktivite;
    }

    public IHR1Personel getIhr1PersonelTespitYapan() {
        return ihr1PersonelTespitYapan;
    }

    public void setIhr1PersonelTespitYapan(IHR1Personel ihr1PersonelTespitYapan) {
        this.ihr1PersonelTespitYapan = ihr1PersonelTespitYapan;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public String getAlinanBilgi() {
        return alinanBilgi;
    }

    public void setAlinanBilgi(String alinanBilgi) {
        this.alinanBilgi = alinanBilgi;
    }
}
