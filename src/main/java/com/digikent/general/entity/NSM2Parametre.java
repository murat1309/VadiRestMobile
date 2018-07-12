package com.digikent.general.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "NSM2PARAMETRE")
public class NSM2Parametre extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "nsm2parametre_seq", sequenceName = "NSM2PARAMETRE_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nsm2parametre_seq")
    @Column(name = "ID", nullable = false, updatable = false, unique = true)
    private Long ID;

    @Column(name = "BASKANADISOYADI")
    private String baskanAdiSoyadi;

    @Column(name = "BRE1ULKE_ADI")
    private String bre1UlkeAdi;

    @Column(name = "BRE1ULKE_ID")
    private Long bre1UlkeId;

    @Column(name = "BUYUKSEHIRMI")
    private String buyukSehirMi;

    @Column(name = "EDEVLETBELEDIYEKODU")
    private String eDevletBelediyeKodu;

    @Column(name = "EDEVLETBELGEDOGRULAMAURL")
    private String eDevletBelgeDogrulamaUrl;

    @Column(name = "INFOEKSTREGOSTER")
    private String infoEkstreGoster;

    @Column(name = "KEPADRESI")
    private String kepAdresi;

    @Column(name = "KURUMDETSISNO")
    private Long kurumDetsisNo;

    @Column(name = "KURUMKISALTMA")
    private String kurumKisaltma;

    @Column(name = "KURUMKODU")
    private String kurumKodu;

    @Column(name = "MAKBUZDOKUMUNUGRUPLA")
    private String makbuzDokumunuGrupla;

    @Column(name = "MESAIBASLANGICZAMANI")
    private String mesaiBaslangicZamani;

    @Column(name = "MESAIBITISZAMANI")
    private String mesaiBitisZamani;

    @Column(name = "PRE1IL_ADI")
    private String pre1IlAdi;

    @Column(name = "PRE1IL_ID")
    private Long pre1IlId;

    @Column(name = "RAPORKODU")
    private String raporKodu;

    @Column(name = "RE1KURUMADI")
    private String re1KurumAdi;

    @Column(name = "RE1KURUMID")
    private Long re1KurumId;

    @Column(name = "REPORTPATH")
    private String reportPath;

    @Column(name = "RRE1ILCE_ADI")
    private String rre1IlceAdi;

    @Column(name = "RRE1ILCE_ID")
    private Long rre1IlceId;

    @Column(name = "SM1KURUM_ID")
    private Long sm1KurumId;

    @Column(name = "SYN_CLIENT_ID")
    private Long synClientId;

    @Column(name = "SYN_LANGUAGE_ID")
    private Long synLanguageId;

    @Column(name = "SYN_ORG_ID")
    private Long synOrgId;

    @Column(name = "TAHAKKUKCILTNO")
    private Long tahakkukCiltNo;

    @Column(name = "TAHAKKUKCILTSAYFASAYISI")
    private Long tahakkukCiltSayfaSayisi;

    @Column(name = "TAHAKKUKSIRANO")
    private Long tahakkukSiraNo;

    @Column(name = "TELEFONALANKODU")
    private Long telefonAlanKodu;

    @Column(name = "THSGELIRSEFI")
    private Long thsGelirSefi;

    @Column(name = "THSMALIHIZMETMUD")
    private Long thsMaliHizmetMud;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getBaskanAdiSoyadi() {
        return baskanAdiSoyadi;
    }

    public void setBaskanAdiSoyadi(String baskanAdiSoyadi) {
        this.baskanAdiSoyadi = baskanAdiSoyadi;
    }

    public String getBre1UlkeAdi() {
        return bre1UlkeAdi;
    }

    public void setBre1UlkeAdi(String bre1UlkeAdi) {
        this.bre1UlkeAdi = bre1UlkeAdi;
    }

    public Long getBre1UlkeId() {
        return bre1UlkeId;
    }

    public void setBre1UlkeId(Long bre1UlkeId) {
        this.bre1UlkeId = bre1UlkeId;
    }

    public String getBuyukSehirMi() {
        return buyukSehirMi;
    }

    public void setBuyukSehirMi(String buyukSehirMi) {
        this.buyukSehirMi = buyukSehirMi;
    }

    public String geteDevletBelediyeKodu() {
        return eDevletBelediyeKodu;
    }

    public void seteDevletBelediyeKodu(String eDevletBelediyeKodu) {
        this.eDevletBelediyeKodu = eDevletBelediyeKodu;
    }

    public String geteDevletBelgeDogrulamaUrl() {
        return eDevletBelgeDogrulamaUrl;
    }

    public void seteDevletBelgeDogrulamaUrl(String eDevletBelgeDogrulamaUrl) {
        this.eDevletBelgeDogrulamaUrl = eDevletBelgeDogrulamaUrl;
    }

    public String getInfoEkstreGoster() {
        return infoEkstreGoster;
    }

    public void setInfoEkstreGoster(String infoEkstreGoster) {
        this.infoEkstreGoster = infoEkstreGoster;
    }

    public String getKepAdresi() {
        return kepAdresi;
    }

    public void setKepAdresi(String kepAdresi) {
        this.kepAdresi = kepAdresi;
    }

    public Long getKurumDetsisNo() {
        return kurumDetsisNo;
    }

    public void setKurumDetsisNo(Long kurumDetsisNo) {
        this.kurumDetsisNo = kurumDetsisNo;
    }

    public String getKurumKisaltma() {
        return kurumKisaltma;
    }

    public void setKurumKisaltma(String kurumKisaltma) {
        this.kurumKisaltma = kurumKisaltma;
    }

    public String getKurumKodu() {
        return kurumKodu;
    }

    public void setKurumKodu(String kurumKodu) {
        this.kurumKodu = kurumKodu;
    }

    public String getMakbuzDokumunuGrupla() {
        return makbuzDokumunuGrupla;
    }

    public void setMakbuzDokumunuGrupla(String makbuzDokumunuGrupla) {
        this.makbuzDokumunuGrupla = makbuzDokumunuGrupla;
    }

    public String getMesaiBaslangicZamani() {
        return mesaiBaslangicZamani;
    }

    public void setMesaiBaslangicZamani(String mesaiBaslangicZamani) {
        this.mesaiBaslangicZamani = mesaiBaslangicZamani;
    }

    public String getMesaiBitisZamani() {
        return mesaiBitisZamani;
    }

    public void setMesaiBitisZamani(String mesaiBitisZamani) {
        this.mesaiBitisZamani = mesaiBitisZamani;
    }

    public String getPre1IlAdi() {
        return pre1IlAdi;
    }

    public void setPre1IlAdi(String pre1IlAdi) {
        this.pre1IlAdi = pre1IlAdi;
    }

    public Long getPre1IlId() {
        return pre1IlId;
    }

    public void setPre1IlId(Long pre1IlId) {
        this.pre1IlId = pre1IlId;
    }

    public String getRaporKodu() {
        return raporKodu;
    }

    public void setRaporKodu(String raporKodu) {
        this.raporKodu = raporKodu;
    }

    public String getRe1KurumAdi() {
        return re1KurumAdi;
    }

    public void setRe1KurumAdi(String re1KurumAdi) {
        this.re1KurumAdi = re1KurumAdi;
    }

    public Long getRe1KurumId() {
        return re1KurumId;
    }

    public void setRe1KurumId(Long re1KurumId) {
        this.re1KurumId = re1KurumId;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getRre1IlceAdi() {
        return rre1IlceAdi;
    }

    public void setRre1IlceAdi(String rre1IlceAdi) {
        this.rre1IlceAdi = rre1IlceAdi;
    }

    public Long getRre1IlceId() {
        return rre1IlceId;
    }

    public void setRre1IlceId(Long rre1IlceId) {
        this.rre1IlceId = rre1IlceId;
    }

    public Long getSm1KurumId() {
        return sm1KurumId;
    }

    public void setSm1KurumId(Long sm1KurumId) {
        this.sm1KurumId = sm1KurumId;
    }

    public Long getSynClientId() {
        return synClientId;
    }

    public void setSynClientId(Long synClientId) {
        this.synClientId = synClientId;
    }

    public Long getSynLanguageId() {
        return synLanguageId;
    }

    public void setSynLanguageId(Long synLanguageId) {
        this.synLanguageId = synLanguageId;
    }

    public Long getSynOrgId() {
        return synOrgId;
    }

    public void setSynOrgId(Long synOrgId) {
        this.synOrgId = synOrgId;
    }

    public Long getTahakkukCiltNo() {
        return tahakkukCiltNo;
    }

    public void setTahakkukCiltNo(Long tahakkukCiltNo) {
        this.tahakkukCiltNo = tahakkukCiltNo;
    }

    public Long getTahakkukCiltSayfaSayisi() {
        return tahakkukCiltSayfaSayisi;
    }

    public void setTahakkukCiltSayfaSayisi(Long tahakkukCiltSayfaSayisi) {
        this.tahakkukCiltSayfaSayisi = tahakkukCiltSayfaSayisi;
    }

    public Long getTahakkukSiraNo() {
        return tahakkukSiraNo;
    }

    public void setTahakkukSiraNo(Long tahakkukSiraNo) {
        this.tahakkukSiraNo = tahakkukSiraNo;
    }

    public Long getTelefonAlanKodu() {
        return telefonAlanKodu;
    }

    public void setTelefonAlanKodu(Long telefonAlanKodu) {
        this.telefonAlanKodu = telefonAlanKodu;
    }

    public Long getThsGelirSefi() {
        return thsGelirSefi;
    }

    public void setThsGelirSefi(Long thsGelirSefi) {
        this.thsGelirSefi = thsGelirSefi;
    }

    public Long getThsMaliHizmetMud() {
        return thsMaliHizmetMud;
    }

    public void setThsMaliHizmetMud(Long thsMaliHizmetMud) {
        this.thsMaliHizmetMud = thsMaliHizmetMud;
    }
}
