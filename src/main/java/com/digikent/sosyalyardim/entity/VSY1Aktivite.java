package com.digikent.sosyalyardim.entity;

import com.digikent.denetimyonetimi.entity.IHR1Personel;
import com.digikent.domain.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "VSY1AKTIVITE")
public class VSY1Aktivite extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "vsy1aktivite_seq", sequenceName = "VSY1AKTIVITE_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vsy1aktivite_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private long ID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vsy1Aktivite")
    @Where(clause = "isActive = 'E'")
    List<VSY1Tespit> vsy1TespitList = new ArrayList<>();

    @Column(name = "VSY1DOSYA_ID")
    private Long dosyaId;

    @Column(name = "BASLAMATARIHISAATI")
    private Date baslamaTarihi;

    @Column(name = "BITISTARIHISAATI")
    private Date bitisTarihi;

    @ManyToOne
    @JoinColumn(name = "IHR1PERSONEL_VEREN")
    private IHR1Personel ihr1PersonelVeren;

    @ManyToOne
    @JoinColumn(name = "IHR1PERSONEL_VERILEN")
    private IHR1Personel ihr1PersonelVerilen;

    @ManyToOne
    @JoinColumn(name = "TSY1AKTIVITEISLEM_ID")
    private TSY1AktiviteIslem tsy1AktiviteIslem;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public List<VSY1Tespit> getVsy1TespitList() {
        return vsy1TespitList;
    }

    public void setVsy1TespitList(List<VSY1Tespit> vsy1TespitList) {
        this.vsy1TespitList = vsy1TespitList;
    }

    public Long getDosyaId() {
        return dosyaId;
    }

    public void setDosyaId(Long dosyaId) {
        this.dosyaId = dosyaId;
    }

    public Date getBaslamaTarihi() {
        return baslamaTarihi;
    }

    public void setBaslamaTarihi(Date baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public Date getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(Date bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public IHR1Personel getIhr1PersonelVeren() {
        return ihr1PersonelVeren;
    }

    public void setIhr1PersonelVeren(IHR1Personel ihr1PersonelVeren) {
        this.ihr1PersonelVeren = ihr1PersonelVeren;
    }

    public IHR1Personel getIhr1PersonelVerilen() {
        return ihr1PersonelVerilen;
    }

    public void setIhr1PersonelVerilen(IHR1Personel ihr1PersonelVerilen) {
        this.ihr1PersonelVerilen = ihr1PersonelVerilen;
    }

    public TSY1AktiviteIslem getTsy1AktiviteIslem() {
        return tsy1AktiviteIslem;
    }

    public void setTsy1AktiviteIslem(TSY1AktiviteIslem tsy1AktiviteIslem) {
        this.tsy1AktiviteIslem = tsy1AktiviteIslem;
    }
}
