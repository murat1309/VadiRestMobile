package com.digikent.denetimyonetimi.dto.denetim;

import java.util.Date;

/**
 * Created by Kadir on 24.02.2018.
 */
public class DenetimDTO {

    private Long id;
    private String paydasAdi;
    private Date denetimTarihi;
    private String takimAdi;
    private String olayYeriIlce;
    private String olayYeriMahalle;
    private String olayYeriSokak;
    private String olayYeriSiteAdi;
    private String olayYeriBlokNo;
    private String olayYeriKapiNoHarf;
    private String olayYeriDaireNoHarf;
    private Long olayYeriKapiNoSayi;
    private Long olayYeriDaireNoSayi;
    private String tebligSecenegi;
    private String tebligAdi;
    private String tebligSoyadi;
    private Long tebligTCKimlikNo;
    private Long paydasId;
    private String denetimTarafTipi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaydasAdi() {
        return paydasAdi;
    }

    public void setPaydasAdi(String paydasAdi) {
        this.paydasAdi = paydasAdi;
    }

    public Date getDenetimTarihi() {
        return denetimTarihi;
    }

    public void setDenetimTarihi(Date denetimTarihi) {
        this.denetimTarihi = denetimTarihi;
    }

    public String getTakimAdi() {
        return takimAdi;
    }

    public void setTakimAdi(String takimAdi) {
        this.takimAdi = takimAdi;
    }

    public String getOlayYeriIlce() {
        return olayYeriIlce;
    }

    public void setOlayYeriIlce(String olayYeriIlce) {
        this.olayYeriIlce = olayYeriIlce;
    }

    public String getOlayYeriMahalle() {
        return olayYeriMahalle;
    }

    public void setOlayYeriMahalle(String olayYeriMahalle) {
        this.olayYeriMahalle = olayYeriMahalle;
    }

    public String getOlayYeriSiteAdi() {
        return olayYeriSiteAdi;
    }

    public void setOlayYeriSiteAdi(String olayYeriSiteAdi) {
        this.olayYeriSiteAdi = olayYeriSiteAdi;
    }

    public String getOlayYeriBlokNo() {
        return olayYeriBlokNo;
    }

    public void setOlayYeriBlokNo(String olayYeriBlokNo) {
        this.olayYeriBlokNo = olayYeriBlokNo;
    }

    public String getOlayYeriKapiNoHarf() {
        return olayYeriKapiNoHarf;
    }

    public void setOlayYeriKapiNoHarf(String olayYeriKapiNoHarf) {
        this.olayYeriKapiNoHarf = olayYeriKapiNoHarf;
    }

    public String getOlayYeriDaireNoHarf() {
        return olayYeriDaireNoHarf;
    }

    public void setOlayYeriDaireNoHarf(String olayYeriDaireNoHarf) {
        this.olayYeriDaireNoHarf = olayYeriDaireNoHarf;
    }

    public Long getOlayYeriKapiNoSayi() {
        return olayYeriKapiNoSayi;
    }

    public void setOlayYeriKapiNoSayi(Long olayYeriKapiNoSayi) {
        this.olayYeriKapiNoSayi = olayYeriKapiNoSayi;
    }

    public Long getOlayYeriDaireNoSayi() {
        return olayYeriDaireNoSayi;
    }

    public void setOlayYeriDaireNoSayi(Long olayYeriDaireNoSayi) {
        this.olayYeriDaireNoSayi = olayYeriDaireNoSayi;
    }

    public String getOlayYeriSokak() {
        return olayYeriSokak;
    }

    public void setOlayYeriSokak(String olayYeriSokak) {
        this.olayYeriSokak = olayYeriSokak;
    }

    public String getTebligSecenegi() {
        return tebligSecenegi;
    }

    public void setTebligSecenegi(String tebligSecenegi) {
        this.tebligSecenegi = tebligSecenegi;
    }

    public String getTebligAdi() {
        return tebligAdi;
    }

    public void setTebligAdi(String tebligAdi) {
        this.tebligAdi = tebligAdi;
    }

    public String getTebligSoyadi() {
        return tebligSoyadi;
    }

    public void setTebligSoyadi(String tebligSoyadi) {
        this.tebligSoyadi = tebligSoyadi;
    }

    public Long getTebligTCKimlikNo() {
        return tebligTCKimlikNo;
    }

    public void setTebligTCKimlikNo(Long tebligTCKimlikNo) {
        this.tebligTCKimlikNo = tebligTCKimlikNo;
    }

    public Long getPaydasId() {
        return paydasId;
    }

    public void setPaydasId(Long paydasId) {
        this.paydasId = paydasId;
    }

    public String getDenetimTarafTipi() {
        return denetimTarafTipi;
    }

    public void setDenetimTarafTipi(String denetimTarafTipi) {
        this.denetimTarafTipi = denetimTarafTipi;
    }
}
