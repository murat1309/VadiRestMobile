package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 12.02.2018.
 */
@Entity
@Table(name="BDNTDENETIMTESPIT")
public class BDNTDenetimTespit extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "bdntdenetimtespit_seq", sequenceName = "BDNTDENETIMTESPIT_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bdntdenetimtespit_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "BDNTDENETIM_ID")
    private Long denetimId;

    @Column(name = "LDNTTESPITGRUBU_ID")
    private Long tespitGrubuId;

    @Column(name = "VERILENSURE")
    private Long verilenSure;

    @Column(name = "LDNTDENETIMTURU_ID")
    private Long denetimTuruId;

    @Column(name = "DENETIMAKSIYONU")
    private String denetimAksiyonu;

    @Column(name = "IZAHAT")
    private String izahat;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="bdntDenetimTespit")
    List<BDNTDenetimTespitLine> bdntDenetimTespitLineList = new ArrayList<>();

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getDenetimId() {
        return denetimId;
    }

    public void setDenetimId(Long denetimId) {
        this.denetimId = denetimId;
    }

    public Long getTespitGrubuId() {
        return tespitGrubuId;
    }

    public void setTespitGrubuId(Long tespitGrubuId) {
        this.tespitGrubuId = tespitGrubuId;
    }

    public Long getVerilenSure() {
        return verilenSure;
    }

    public void setVerilenSure(Long verilenSure) {
        this.verilenSure = verilenSure;
    }

    public Long getDenetimTuruId() {
        return denetimTuruId;
    }

    public void setDenetimTuruId(Long denetimTuruId) {
        this.denetimTuruId = denetimTuruId;
    }

    public String getDenetimAksiyonu() {
        return denetimAksiyonu;
    }

    public void setDenetimAksiyonu(String denetimAksiyonu) {
        this.denetimAksiyonu = denetimAksiyonu;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public List<BDNTDenetimTespitLine> getBdntDenetimTespitLineList() {
        return bdntDenetimTespitLineList;
    }

    public void setBdntDenetimTespitLineList(List<BDNTDenetimTespitLine> bdntDenetimTespitLineList) {
        this.bdntDenetimTespitLineList = bdntDenetimTespitLineList;
    }
}
