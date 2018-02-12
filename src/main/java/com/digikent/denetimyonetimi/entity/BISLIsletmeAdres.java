package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 10.02.2018.
 */
@Entity
@Table(name = "BISLISLETMEADRES")
public class BISLIsletmeAdres extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "bislisletmeadres_seq", sequenceName = "BISLISLETMEADRES_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bislisletmeadres_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "BISLISLETME_ID")
    private Long bislIsletmeId;

    @Column(name = "RRE1ILCE_ID")
    private Long rre1IlceId;

    @Column(name = "DRE1MAHALLE_ID")
    private Long dre1MahalleId;

    @Column(name = "DRE1BAGBOLUM_ID")
    private Long dre1BagBolumId;

    @Column(name = "SRE1SOKAK_ID")
    private Long sre1SokakId;

    @Column(name = "ERE1YAPI_ID")
    private Long ere1YapiId;

    @Column(name = "IRE1PARSEL_ID")
    private Long ire1ParselId;

    @Column(name = "KAPINOSAYI_ID")
    private Long kapiNoSayi;

    @Column(name = "DAIRENOSAYI_ID")
    private Long daireNoSayi;

    @Column(name = "PAFTANO_ID")
    private String paftano;

    @Column(name = "ADANO_ID")
    private String adaNo;

    @Column(name = "PARSELNO_ID")
    private String parselNo;

    @Column(name = "SITEADI_ID")
    private String siteAdi;

    @Column(name = "BLOKNO_ID")
    private String blokNo;

    @Column(name = "KAPINOHARF_ID")
    private String kapiNoHarf;

    @Column(name = "DAIRENOHARF_ID")
    private String daireNoHarf;

    @Column(name = "IZAHAT")
    private String izahat;

    @Column(name = "ILETISIMADRESIMI")
    private String iletisimAdresiMi;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getBislIsletmeId() {
        return bislIsletmeId;
    }

    public void setBislIsletmeId(Long bislIsletmeId) {
        this.bislIsletmeId = bislIsletmeId;
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

    public Long getDre1BagBolumId() {
        return dre1BagBolumId;
    }

    public void setDre1BagBolumId(Long dre1BagBolumId) {
        this.dre1BagBolumId = dre1BagBolumId;
    }

    public Long getSre1SokakId() {
        return sre1SokakId;
    }

    public void setSre1SokakId(Long sre1SokakId) {
        this.sre1SokakId = sre1SokakId;
    }

    public Long getEre1YapiId() {
        return ere1YapiId;
    }

    public void setEre1YapiId(Long ere1YapiId) {
        this.ere1YapiId = ere1YapiId;
    }

    public Long getIre1ParselId() {
        return ire1ParselId;
    }

    public void setIre1ParselId(Long ire1ParselId) {
        this.ire1ParselId = ire1ParselId;
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

    public String getPaftano() {
        return paftano;
    }

    public void setPaftano(String paftano) {
        this.paftano = paftano;
    }

    public String getAdaNo() {
        return adaNo;
    }

    public void setAdaNo(String adaNo) {
        this.adaNo = adaNo;
    }

    public String getParselNo() {
        return parselNo;
    }

    public void setParselNo(String parselNo) {
        this.parselNo = parselNo;
    }

    public String getSiteAdi() {
        return siteAdi;
    }

    public void setSiteAdi(String siteAdi) {
        this.siteAdi = siteAdi;
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

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public String getIletisimAdresiMi() {
        return iletisimAdresiMi;
    }

    public void setIletisimAdresiMi(String iletisimAdresiMi) {
        this.iletisimAdresiMi = iletisimAdresiMi;
    }
}
