package com.digikent.sosyalyardim.yeni.entity;

import com.digikent.domain.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 18.05.2018.
 */
@Entity
@Table(name = "VSY1TESPIT")
public class VSY1Tespit  extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "vsy1tespit_seq", sequenceName = "VSY1TESPIT_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vsy1tespit_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="vsy1Tespit")
    @Where(clause = "isActive = 'E'")
    List<VSY1TespitLine> vsy1TespitLineList = new ArrayList<>();

    @Column(name = "VSY1DOSYA_ID")
    private Long dosyaId;

    @Column(name = "IHR1PERSONEL_ID")
    private Long ihr1PersonelId;

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
}
