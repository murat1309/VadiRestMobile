package com.digikent.denetimyonetimi.dto.paydas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by Kadir on 6.02.2018.
 */
public class DenetimIsletmeDTO implements Serializable {

    private Long id;
    private Long paydasId;
    private String isletmeAdi;
    private String tabelaUnvani;
    private String faaliyetKonusu;
    private String sorumluAdSoyad;
    private Long vergiNo;
    private Long ticaretSicilNo;
    private Long telefonCep;
    private Long telefonIs;
    private String binaAdi;
    private String kapiNo;
    private String ilceAdi;
    private Long kapiNoSayi;
    private String kapiNoHarf;
    private String daireNoHarf;
    private Long daireNoSayi;
    private Long katSayi;
    private String katHarf;
    private String blokNo;
    private String siteAdi;
    private Long dre1MahalleId;
    private Long sre1SokakId;
    private Long rre1IlceId;
    private Long pre1IlId;
    private String vergiDairesi;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaydasId() {
        return paydasId;
    }

    public void setPaydasId(Long paydasId) {
        this.paydasId = paydasId;
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

    public String getFaaliyetKonusu() {
        return faaliyetKonusu;
    }

    public void setFaaliyetKonusu(String faaliyetKonusu) {
        this.faaliyetKonusu = faaliyetKonusu;
    }

    public String getSorumluAdSoyad() {
        return sorumluAdSoyad;
    }

    public void setSorumluAdSoyad(String sorumluAdSoyad) {
        this.sorumluAdSoyad = sorumluAdSoyad;
    }

    public Long getVergiNo() {
        return vergiNo;
    }

    public void setVergiNo(Long vergiNo) {
        this.vergiNo = vergiNo;
    }

    public Long getTicaretSicilNo() {
        return ticaretSicilNo;
    }

    public void setTicaretSicilNo(Long ticaretSicilNo) {
        this.ticaretSicilNo = ticaretSicilNo;
    }

    public Long getTelefonCep() {
        return telefonCep;
    }

    public void setTelefonCep(Long telefonCep) {
        this.telefonCep = telefonCep;
    }

    public Long getTelefonIs() {
        return telefonIs;
    }

    public void setTelefonIs(Long telefonIs) {
        this.telefonIs = telefonIs;
    }

    public String getBinaAdi() {
        return binaAdi;
    }

    public void setBinaAdi(String binaAdi) {
        this.binaAdi = binaAdi;
    }

    public String getKapiNo() {
        return kapiNo;
    }

    public void setKapiNo(String kapiNo) {
        this.kapiNo = kapiNo;
    }

    public String getIlceAdi() {
        return ilceAdi;
    }

    public void setIlceAdi(String ilceAdi) {
        this.ilceAdi = ilceAdi;
    }

    public Long getKapiNoSayi() {
        return kapiNoSayi;
    }

    public void setKapiNoSayi(Long kapiNoSayi) {
        this.kapiNoSayi = kapiNoSayi;
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

    public Long getDaireNoSayi() {
        return daireNoSayi;
    }

    public void setDaireNoSayi(Long daireNoSayi) {
        this.daireNoSayi = daireNoSayi;
    }

    public Long getKatSayi() {
        return katSayi;
    }

    public void setKatSayi(Long katSayi) {
        this.katSayi = katSayi;
    }

    public String getKatHarf() {
        return katHarf;
    }

    public void setKatHarf(String katHarf) {
        this.katHarf = katHarf;
    }

    public String getBlokNo() {
        return blokNo;
    }

    public void setBlokNo(String blokNo) {
        this.blokNo = blokNo;
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

    public Long getRre1IlceId() {
        return rre1IlceId;
    }

    public void setRre1IlceId(Long rre1IlceId) {
        this.rre1IlceId = rre1IlceId;
    }

    public String getVergiDairesi() {
        return vergiDairesi;
    }

    public void setVergiDairesi(String vergiDairesi) {
        this.vergiDairesi = vergiDairesi;
    }

    public String getSiteAdi() {
        return siteAdi;
    }

    public void setSiteAdi(String siteAdi) {
        this.siteAdi = siteAdi;
    }

    public Long getPre1IlId() {
        return pre1IlId;
    }

    public void setPre1IlId(Long pre1IlId) {
        this.pre1IlId = pre1IlId;
    }
}
