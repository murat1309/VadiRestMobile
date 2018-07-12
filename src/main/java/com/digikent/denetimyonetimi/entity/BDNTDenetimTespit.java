package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

    @OneToOne
    @JoinColumn(name = "LDNTTESPITGRUBU_ID")
    private LDNTTespitGrubu ldntTespitGrubu = new LDNTTespitGrubu();

//    @Column(name = "LDNTTESPITGRUBU_ID")
//    private Long tespitGrubuId;

    @Column(name = "VERILENSURE")
    private Long verilenSure;

    //@Column(name = "LDNTDENETIMTURU_ID")
    //private Long denetimTuruId;
    @OneToOne
    @JoinColumn(name="LDNTDENETIMTURU_ID")
    private LDNTDenetimTuru ldntDenetimTuru = new LDNTDenetimTuru();

    @Column(name = "DENETIMAKSIYONU")
    private String denetimAksiyonu;

    @Column(name = "IZAHAT")
    private String izahat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SONRAKIDENETIMTARIHI")
    private Date sonrakiDenetimTarihi = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "KAPAMABASLANGICTARIHI")
    private Date kapamaBaslangicTarihi = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "KAPAMABITISTARIHI")
    private Date kapamaBitisTarihi = new Date();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="bdntDenetimTespit")
    @Where(clause = "isActive = 'E'")
    private List<BDNTDenetimTespitLine> bdntDenetimTespitLineList = new ArrayList<>();

    @Column(name = "CEZAMIKTARI")
    private Long cezaMiktari;

    @Column(name = "YIL")
    private Long yil;

    @Column(name = "TUTANAKNO")
    private Long tutanakNo;

    @Column(name = "CEZANO")
    private Long cezaNo;

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

//    public Long getTespitGrubuId() {
//        return tespitGrubuId;
//    }

//    public void setTespitGrubuId(Long tespitGrubuId) {
//        this.tespitGrubuId = tespitGrubuId;
//    }

    public Long getVerilenSure() {
        return verilenSure;
    }

    public void setVerilenSure(Long verilenSure) {
        this.verilenSure = verilenSure;
    }

    public LDNTDenetimTuru getLdntDenetimTuru() {
        return ldntDenetimTuru;
    }

    public void setLdntDenetimTuru(LDNTDenetimTuru ldntDenetimTuru) {
        this.ldntDenetimTuru = ldntDenetimTuru;
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

    public Date getSonrakiDenetimTarihi() {
        return sonrakiDenetimTarihi;
    }

    public void setSonrakiDenetimTarihi(Date sonrakiDenetimTarihi) {
        this.sonrakiDenetimTarihi = sonrakiDenetimTarihi;
    }

    public Date getKapamaBaslangicTarihi() {
        return kapamaBaslangicTarihi;
    }

    public void setKapamaBaslangicTarihi(Date kapamaBaslangicTarihi) {
        this.kapamaBaslangicTarihi = kapamaBaslangicTarihi;
    }

    public Date getKapamaBitisTarihi() {
        return kapamaBitisTarihi;
    }

    public void setKapamaBitisTarihi(Date kapamaBitisTarihi) {
        this.kapamaBitisTarihi = kapamaBitisTarihi;
    }

    public Long getCezaMiktari() {
        return cezaMiktari;
    }

    public void setCezaMiktari(Long cezaMiktari) {
        this.cezaMiktari = cezaMiktari;
    }

    public Long getYil() {
        return yil;
    }

    public void setYil(Long yil) {
        this.yil = yil;
    }

    public Long getTutanakNo() {
        return tutanakNo;
    }

    public void setTutanakNo(Long tutanakNo) {
        this.tutanakNo = tutanakNo;
    }

    public Long getCezaNo() {
        return cezaNo;
    }

    public void setCezaNo(Long cezaNo) {
        this.cezaNo = cezaNo;
    }

    public LDNTTespitGrubu getLdntTespitGrubu() {
        return ldntTespitGrubu;
    }

    public void setLdntTespitGrubu(LDNTTespitGrubu ldntTespitGrubu) {
        this.ldntTespitGrubu = ldntTespitGrubu;
    }
}
