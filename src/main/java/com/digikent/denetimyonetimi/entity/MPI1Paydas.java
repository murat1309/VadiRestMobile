package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 6.02.2018.
 */
@Entity
@Table(name="MPI1PAYDAS")
public class MPI1Paydas extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "mpi1paydas_seq", sequenceName = "MPI1PAYDAS_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mpi1paydas_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "KAYITDURUMU")
    private String kayitDurumu;

    @Column(name = "PAYDASTURU")
    private String paydasTuru;

    @Column(name = "RAPORADI")
    private String raporAdi;

    @Column(name = "SORGUADI")
    private String sorguAdi;

    @Column(name = "TCKIMLIKNO")
    private Long tcKimlikNo;

    @Column(name = "CEPTELEFONU")
    private Long cepTelefonu;

    @Column(name = "RRE1ILCE_ID")
    private Long rre1IlceId;

    @Column(name = "DRE1MAHALLE_ID")
    private Long dre1MahalleId;

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

    @Column(name = "UNVAN")
    private String unvan;

    @Column(name = "VERGINUMARASI")
    private String vergiNumarasi;

    @Column(name = "VERGIDAIRESI")
    private String vergiDairesi;

    @Column(name = "TICARETSICILNUMARASI")
    private Long ticaretSicilNo;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getKayitDurumu() {
        return kayitDurumu;
    }

    public void setKayitDurumu(String kayitDurumu) {
        this.kayitDurumu = kayitDurumu;
    }

    public String getPaydasTuru() {
        return paydasTuru;
    }

    public void setPaydasTuru(String paydasTuru) {
        this.paydasTuru = paydasTuru;
    }

    public String getRaporAdi() {
        return raporAdi;
    }

    public void setRaporAdi(String raporAdi) {
        this.raporAdi = raporAdi;
    }

    public String getSorguAdi() {
        return sorguAdi;
    }

    public void setSorguAdi(String sorguAdi) {
        this.sorguAdi = sorguAdi;
    }

    public Long getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(Long tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    public Long getCepTelefonu() {
        return cepTelefonu;
    }

    public void setCepTelefonu(Long cepTelefonu) {
        this.cepTelefonu = cepTelefonu;
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

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public String getVergiNumarasi() {
        return vergiNumarasi;
    }

    public void setVergiNumarasi(String vergiNumarasi) {
        this.vergiNumarasi = vergiNumarasi;
    }

    public Long getTicaretSicilNo() {
        return ticaretSicilNo;
    }

    public void setTicaretSicilNo(Long ticaretSicilNo) {
        this.ticaretSicilNo = ticaretSicilNo;
    }

    public String getVergiDairesi() {
        return vergiDairesi;
    }

    public void setVergiDairesi(String vergiDairesi) {
        this.vergiDairesi = vergiDairesi;
    }
}
