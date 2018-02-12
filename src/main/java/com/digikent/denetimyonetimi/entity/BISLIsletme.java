package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kadir on 26.01.2018.
 */
@Entity
@Table(name = "BISLISLETME")
public class BISLIsletme extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "bislisletme_seq", sequenceName = "BISLISLETME_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bislisletme_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "MPI1PAYDAS_ID")
    private Long mpi1PaydasId;

    @Column(name = "BISLISLETMEADRES_ID")
    private Long bislIsletmeAdresId;

    @Column(name = "VERGINUMARASI")
    private Long vergiNumarasi;

    @Column(name = "TCKIMLIKNO")
    private Long tcKimlikNo;

    @Column(name = "LISLFALIYET_ID")
    private Long lislfaliyetId;

    @Column(name = "PERSONELSAYISI")
    private Long personelSayisi;

    @Column(name = "ACIKKULLANIMALANI")
    private Long acikKullanimAlani;

    @Column(name = "KAPALIKULLANIMALANI")
    private Long kapaliKullanimAlani;

    @Column(name = "ISLETMAADI")
    private String isletmeAdi;

    @Column(name = "TABELAUNVANI")
    private String tabelaUnvani;

    @Column(name = "VERGIDAIRESI")
    private String vergiDairesi;

    @Column(name = "SORUMLUADI")
    private String sorumluAdi;

    @Column(name = "SORUMLUSOYADI")
    private String sorumluSoyadi;

    @Column(name = "BABAADI")
    private String babaAdi;

    @Column(name = "DOGUMYERI")
    private String dogumYeri;

    @Column(name = "FAALIYETKONUSU")
    private String faaliyetKonusu;

    @Column(name = "PAZARESNAFIMI")
    private String pazarEsnafiMi;

    @Column(name = "HEKIMVARMI")
    private String hekimVarMi;

    @Column(name = "YAZISMA_EH")
    private String yazismaEH;

    @Column(name = "IZAHAT")
    private String izahat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DOGUMTARIHI")
    private Date dogumTarihi;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACILISTARIHI")
    private Date acilisTarihi;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "KAPANISTARIHI")
    private Date kapanisTarihi;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getMpi1PaydasId() {
        return mpi1PaydasId;
    }

    public void setMpi1PaydasId(Long mpi1PaydasId) {
        this.mpi1PaydasId = mpi1PaydasId;
    }

    public Long getBislIsletmeAdresId() {
        return bislIsletmeAdresId;
    }

    public void setBislIsletmeAdresId(Long bislIsletmeAdresId) {
        this.bislIsletmeAdresId = bislIsletmeAdresId;
    }

    public Long getVergiNumarasi() {
        return vergiNumarasi;
    }

    public void setVergiNumarasi(Long vergiNumarasi) {
        this.vergiNumarasi = vergiNumarasi;
    }

    public Long getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(Long tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    public Long getLislfaliyetId() {
        return lislfaliyetId;
    }

    public void setLislfaliyetId(Long lislfaliyetId) {
        this.lislfaliyetId = lislfaliyetId;
    }

    public Long getPersonelSayisi() {
        return personelSayisi;
    }

    public void setPersonelSayisi(Long personelSayisi) {
        this.personelSayisi = personelSayisi;
    }

    public Long getAcikKullanimAlani() {
        return acikKullanimAlani;
    }

    public void setAcikKullanimAlani(Long acikKullanimAlani) {
        this.acikKullanimAlani = acikKullanimAlani;
    }

    public Long getKapaliKullanimAlani() {
        return kapaliKullanimAlani;
    }

    public void setKapaliKullanimAlani(Long kapaliKullanimAlani) {
        this.kapaliKullanimAlani = kapaliKullanimAlani;
    }

    public String getIsletmeAdi() {
        return isletmeAdi;
    }

    public void setIsletmeAdi(String isletmeAdi) {
        this.isletmeAdi = isletmeAdi;
    }

    public String getTabelaUnvani() {
        return tabelaUnvani;
    }

    public void setTabelaUnvani(String tabelaUnvani) {
        this.tabelaUnvani = tabelaUnvani;
    }

    public String getVergiDairesi() {
        return vergiDairesi;
    }

    public void setVergiDairesi(String vergiDairesi) {
        this.vergiDairesi = vergiDairesi;
    }

    public String getSorumluAdi() {
        return sorumluAdi;
    }

    public void setSorumluAdi(String sorumluAdi) {
        this.sorumluAdi = sorumluAdi;
    }

    public String getSorumluSoyadi() {
        return sorumluSoyadi;
    }

    public void setSorumluSoyadi(String sorumluSoyadi) {
        this.sorumluSoyadi = sorumluSoyadi;
    }

    public String getBabaAdi() {
        return babaAdi;
    }

    public void setBabaAdi(String babaAdi) {
        this.babaAdi = babaAdi;
    }

    public String getDogumYeri() {
        return dogumYeri;
    }

    public void setDogumYeri(String dogumYeri) {
        this.dogumYeri = dogumYeri;
    }

    public String getFaaliyetKonusu() {
        return faaliyetKonusu;
    }

    public void setFaaliyetKonusu(String faaliyetKonusu) {
        this.faaliyetKonusu = faaliyetKonusu;
    }

    public String getPazarEsnafiMi() {
        return pazarEsnafiMi;
    }

    public void setPazarEsnafiMi(String pazarEsnafiMi) {
        this.pazarEsnafiMi = pazarEsnafiMi;
    }

    public String getHekimVarMi() {
        return hekimVarMi;
    }

    public void setHekimVarMi(String hekimVarMi) {
        this.hekimVarMi = hekimVarMi;
    }

    public String getYazismaEH() {
        return yazismaEH;
    }

    public void setYazismaEH(String yazismaEH) {
        this.yazismaEH = yazismaEH;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public Date getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(Date dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public Date getAcilisTarihi() {
        return acilisTarihi;
    }

    public void setAcilisTarihi(Date acilisTarihi) {
        this.acilisTarihi = acilisTarihi;
    }

    public Date getKapanisTarihi() {
        return kapanisTarihi;
    }

    public void setKapanisTarihi(Date kapanisTarihi) {
        this.kapanisTarihi = kapanisTarihi;
    }
}
