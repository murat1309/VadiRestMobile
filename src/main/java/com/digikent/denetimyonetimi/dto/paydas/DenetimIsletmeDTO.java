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
}
