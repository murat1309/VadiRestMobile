package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 23.02.2018.
 */
@Entity
@Table(name="BDNTDENETIMTESPITTARAF")
public class BDNTDenetimTespitTaraf extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "bdntdenetimtespittaraf_seq", sequenceName = "BDNTDENETIMTESPITTARAF_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bdntdenetimtespittaraf_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "BDNTDENETIM_ID")
    private Long bdntDenetimId;

    @Column(name = "IHR1PERSONEL_ID")
    private Long ihr1PersonelId;

    @Column(name = "MPI1PAYDAS_ID")
    private Long mpi1PaydasId;

    @Column(name = "TCKIMLIKNO")
    private Long tcKimlikNo;

    @Column(name = "TARAFTURU")
    private String tarafTuru;

    @Column(name = "ADI")
    private String adi;

    @Column(name = "SOYADI")
    private String soyadi;

    @Column(name = "GOREVI")
    private String gorevi;

    @Column(name = "IZAHAT")
    private String izahat;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getBdntDenetimId() {
        return bdntDenetimId;
    }

    public void setBdntDenetimId(Long bdntDenetimId) {
        this.bdntDenetimId = bdntDenetimId;
    }

    public Long getIhr1PersonelId() {
        return ihr1PersonelId;
    }

    public void setIhr1PersonelId(Long ihr1PersonelId) {
        this.ihr1PersonelId = ihr1PersonelId;
    }

    public Long getMpi1PaydasId() {
        return mpi1PaydasId;
    }

    public void setMpi1PaydasId(Long mpi1PaydasId) {
        this.mpi1PaydasId = mpi1PaydasId;
    }

    public Long getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(Long tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    public String getTarafTuru() {
        return tarafTuru;
    }

    public void setTarafTuru(String tarafTuru) {
        this.tarafTuru = tarafTuru;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public String getGorevi() {
        return gorevi;
    }

    public void setGorevi(String gorevi) {
        this.gorevi = gorevi;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }
}
