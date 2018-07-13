package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kadir on 14.02.2018.
 */
@Entity
@Table(name="LSM2KANUN")
public class LSM2Kanun extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "lsm2kanun_seq", sequenceName = "LSM2KANUN_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsm2kanun_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "IZAHAT")
    private String izahat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "YAYIMTARIHI")
    private Date yayimTarihi = new Date();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="lsm2Kanun")
    List<LDNTTespit> ldntTespitList = new ArrayList<>();

    @Column(name = "MADDESI")
    private String madde;

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

    public Date getYayimTarihi() {
        return yayimTarihi;
    }

    public void setYayimTarihi(Date yayimTarihi) {
        this.yayimTarihi = yayimTarihi;
    }

    public List<LDNTTespit> getLdntTespitList() {
        return ldntTespitList;
    }

    public void setLdntTespitList(List<LDNTTespit> ldntTespitList) {
        this.ldntTespitList = ldntTespitList;
    }

    public String getMadde() {
        return madde;
    }

    public void setMadde(String madde) {
        this.madde = madde;
    }
}
