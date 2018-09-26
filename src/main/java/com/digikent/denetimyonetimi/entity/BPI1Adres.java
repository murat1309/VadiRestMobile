package com.digikent.denetimyonetimi.entity;

import com.vadi.digikent.base.model.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kadir on 8.02.2018.
 */
@Entity
@Table(name = "BPI1ADRES")
public class BPI1Adres implements Serializable {

    @Id
    @SequenceGenerator(name = "bpi1adres_seq", sequenceName = "BPI1ADRES_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bpi1adres_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "MPI1PAYDAS_ID")
    private Long mpi1paydasId;

    @Column(name = "RRE1ILCE_ID")
    private Long rre1IlceId;

    @Column(name = "DRE1MAHALLE_ID")
    private Long dre1MahalleId;

    @Column(name = "DRE1BAGBOLUM_ID")
    private Long dre1BagBolumId;

    @Column(name = "PRE1IL_ID")
    private Long pre1IlId;

    @Column(name = "SRE1SOKAK_ID")
    private Long sre1SokakId;

    @Column(name = "KAPINOSAYI")
    private Long kapiNoSayi;

    @Column(name = "DAIRENOSAYI")
    private Long daireNoSayi;

    @Column(name = "RRE1SITE_ADI")
    private String rre1SiteAdi;

    @Column(name = "BLOKNO")
    private String blokNo;

    @Column(name = "KAPINOHARF")
    private String kapiNoHarf;

    @Column(name = "DAIRENOHARF")
    private String daireNoHarf;

    @Column(name = "MEKTUPGONDERIMADRESIMI")
    private String mektupGonderimAdresiMi;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDDATE")
    private Date updDate;

    @Column(name = "UPDSEQ")
    private Long updSeq;

    @Column(name = "UPDUSER")
    private Long updUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRDATE")
    private Date crDate = new Date();

    @Column(name = "CRUSER")
    private Long crUser;

    @Column(name = "DELETEFLAG", length = 1)
    private String deleteFlag;

    @Type(type = "com.vadi.smartkent.datamodel.domains.base.EvetHayirType")
    @Column(name = "ISACTIVE", length = 1)
    private Boolean isActive;

    @Column(name = "SOKAKADI")
    private String sokakAdi;

    @Column(name = "MAHALLEADI")
    private String mahalleAdi;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getRre1IlceId() {
        return rre1IlceId;
    }

    public void setRre1IlceId(Long rre1IlceId) {
        this.rre1IlceId = rre1IlceId;
    }

    public Long getDre1MahalleId() {
        return dre1MahalleId;
    }

    public void setDre1MahalleId(Long dre1MahalleId) {
        this.dre1MahalleId = dre1MahalleId;
    }

    public Long getSre1SokakId() {
        return sre1SokakId;
    }

    public void setSre1SokakId(Long sre1SokakId) {
        this.sre1SokakId = sre1SokakId;
    }

    public Long getKapiNoSayi() {
        return kapiNoSayi;
    }

    public void setKapiNoSayi(Long kapiNoSayi) {
        this.kapiNoSayi = kapiNoSayi;
    }

    public Long getDaireNoSayi() {
        return daireNoSayi;
    }

    public void setDaireNoSayi(Long daireNoSayi) {
        this.daireNoSayi = daireNoSayi;
    }

    public String getRre1SiteAdi() {
        return rre1SiteAdi;
    }

    public void setRre1SiteAdi(String rre1SiteAdi) {
        this.rre1SiteAdi = rre1SiteAdi;
    }

    public String getBlokNo() {
        return blokNo;
    }

    public void setBlokNo(String blokNo) {
        this.blokNo = blokNo;
    }

    public String getKapiNoHarf() {
        return kapiNoHarf;
    }

    public void setKapiNoHarf(String kapiNoHarf) {
        this.kapiNoHarf = kapiNoHarf;
    }

    public String getDaireNoHarf() {
        return daireNoHarf;
    }

    public void setDaireNoHarf(String daireNoHarf) {
        this.daireNoHarf = daireNoHarf;
    }

    public Long getMpi1paydasId() {
        return mpi1paydasId;
    }

    public void setMpi1paydasId(Long mpi1paydasId) {
        this.mpi1paydasId = mpi1paydasId;
    }

    public Long getDre1BagBolumId() {
        return dre1BagBolumId;
    }

    public void setDre1BagBolumId(Long dre1BagBolumId) {
        this.dre1BagBolumId = dre1BagBolumId;
    }

    public Long getPre1IlId() {
        return pre1IlId;
    }

    public void setPre1IlId(Long pre1IlId) {
        this.pre1IlId = pre1IlId;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public Long getUpdSeq() {
        return updSeq;
    }

    public void setUpdSeq(Long updSeq) {
        this.updSeq = updSeq;
    }

    public Long getUpdUser() {
        return updUser;
    }

    public void setUpdUser(Long updUser) {
        this.updUser = updUser;
    }

    public Date getCrDate() {
        return crDate;
    }

    public void setCrDate(Date crDate) {
        this.crDate = crDate;
    }

    public Long getCrUser() {
        return crUser;
    }

    public void setCrUser(Long crUser) {
        this.crUser = crUser;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getMektupGonderimAdresiMi() {
        return mektupGonderimAdresiMi;
    }

    public void setMektupGonderimAdresiMi(String mektupGonderimAdresiMi) {
        this.mektupGonderimAdresiMi = mektupGonderimAdresiMi;
    }

    public String getSokakAdi() {
        return sokakAdi;
    }

    public void setSokakAdi(String sokakAdi) {
        this.sokakAdi = sokakAdi;
    }

    public String getMahalleAdi() {
        return mahalleAdi;
    }

    public void setMahalleAdi(String mahalleAdi) {
        this.mahalleAdi = mahalleAdi;
    }
}
