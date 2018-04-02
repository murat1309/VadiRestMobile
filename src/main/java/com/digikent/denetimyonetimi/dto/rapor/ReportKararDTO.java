package com.digikent.denetimyonetimi.dto.rapor;

/**
 * Created by Kadir on 2.04.2018.
 */
public class ReportKararDTO {

    private String ceza;
    private String cezaMiktari;
    private String kapama;
    private String kapamaBaslangicTarihi;
    private String kapamaBitisTarihi;
    private String ekSure;
    private String ekSureZaman;
    private String belirsiz;

    public String getCeza() {
        return ceza;
    }

    public void setCeza(String ceza) {
        this.ceza = ceza;
    }

    public String getKapama() {
        return kapama;
    }

    public void setKapama(String kapama) {
        this.kapama = kapama;
    }

    public String getKapamaBaslangicTarihi() {
        return kapamaBaslangicTarihi;
    }

    public void setKapamaBaslangicTarihi(String kapamaBaslangicTarihi) {
        this.kapamaBaslangicTarihi = kapamaBaslangicTarihi;
    }

    public String getKapamaBitisTarihi() {
        return kapamaBitisTarihi;
    }

    public void setKapamaBitisTarihi(String kapamaBitisTarihi) {
        this.kapamaBitisTarihi = kapamaBitisTarihi;
    }

    public String getEkSure() {
        return ekSure;
    }

    public void setEkSure(String ekSure) {
        this.ekSure = ekSure;
    }

    public String getBelirsiz() {
        return belirsiz;
    }

    public void setBelirsiz(String belirsiz) {
        this.belirsiz = belirsiz;
    }

    public String getCezaMiktari() {
        return cezaMiktari;
    }

    public void setCezaMiktari(String cezaMiktari) {
        this.cezaMiktari = cezaMiktari;
    }

    public String getEkSureZaman() {
        return ekSureZaman;
    }

    public void setEkSureZaman(String ekSureZaman) {
        this.ekSureZaman = ekSureZaman;
    }
}
