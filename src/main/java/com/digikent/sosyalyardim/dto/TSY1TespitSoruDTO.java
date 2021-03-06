package com.digikent.sosyalyardim.dto;

/**
 * Created by Kadir on 14.05.2018.
 */
public class TSY1TespitSoruDTO {

    private Long id;
    private TSY1TespitSoruTuruDTO tespitSoruTuruDTO;
    private TSY1TespitKategoriDTO tespitKategoriDTO;
    private String tanim;
    private Long sira;
    private Long cevap;
    private Long aktif;
    private String kayitOzelIsmi;
    //UI için gerekli datalar
    //string number degeri
    private String stnmdegeri;
    //combobox radio buton değeri
    private Long cbrbdegeri;
    private String bilgi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TSY1TespitSoruTuruDTO getTespitSoruTuruDTO() {
        return tespitSoruTuruDTO;
    }

    public void setTespitSoruTuruDTO(TSY1TespitSoruTuruDTO tespitSoruTuruDTO) {
        this.tespitSoruTuruDTO = tespitSoruTuruDTO;
    }

    public TSY1TespitKategoriDTO getTespitKategoriDTO() {
        return tespitKategoriDTO;
    }

    public void setTespitKategoriDTO(TSY1TespitKategoriDTO tespitKategoriDTO) {
        this.tespitKategoriDTO = tespitKategoriDTO;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public Long getSira() {
        return sira;
    }

    public void setSira(Long sira) {
        this.sira = sira;
    }

    public Long getCevap() {
        return cevap;
    }

    public void setCevap(Long cevap) {
        this.cevap = cevap;
    }

    public Long getAktif() {
        return aktif;
    }

    public void setAktif(Long aktif) {
        this.aktif = aktif;
    }

    public String getKayitOzelIsmi() {
        return kayitOzelIsmi;
    }

    public void setKayitOzelIsmi(String kayitOzelIsmi) {
        this.kayitOzelIsmi = kayitOzelIsmi;
    }

    public String getStnmdegeri() {
        return stnmdegeri;
    }

    public void setStnmdegeri(String stnmdegeri) {
        this.stnmdegeri = stnmdegeri;
    }

    public Long getCbrbdegeri() {
        return cbrbdegeri;
    }

    public void setCbrbdegeri(Long cbrbdegeri) {
        this.cbrbdegeri = cbrbdegeri;
    }

    public String getBilgi() {
        return bilgi;
    }

    public void setBilgi(String bilgi) {
        this.bilgi = bilgi;
    }
}
