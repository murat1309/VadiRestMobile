package com.digikent.demirbas.dto;

import java.util.Date;

/**
 * Created by Kadir on 10/14/16.
 */
public class DemirbasDTO {

    private Date tarih;
    private Long tasinirinYili;
    private String fisTuru;
    private String malzemeAdi;
    private String malzemeBirimi;
    private Long miktar;
    private Long birimTutar;
    private Long tutar;
    private Long kdvTutari;
    private Long toplamTutar;
    private Long amt2AmbarId;
    private String tasinirinKodu;
    private Long demirbasSicilNo;

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public Long getTasinirinYili() {
        return tasinirinYili;
    }

    public void setTasinirinYili(Long tasinirinYili) {
        this.tasinirinYili = tasinirinYili;
    }

    public String getFisTuru() {
        return fisTuru;
    }

    public void setFisTuru(String fisTuru) {
        this.fisTuru = fisTuru;
    }

    public String getMalzemeAdi() {
        return malzemeAdi;
    }

    public void setMalzemeAdi(String malzemeAdi) {
        this.malzemeAdi = malzemeAdi;
    }

    public String getMalzemeBirimi() {
        return malzemeBirimi;
    }

    public void setMalzemeBirimi(String malzemeBirimi) {
        this.malzemeBirimi = malzemeBirimi;
    }

    public Long getMiktar() {
        return miktar;
    }

    public void setMiktar(Long miktar) {
        this.miktar = miktar;
    }

    public Long getBirimTutar() {
        return birimTutar;
    }

    public void setBirimTutar(Long birimTutar) {
        this.birimTutar = birimTutar;
    }

    public Long getTutar() {
        return tutar;
    }

    public void setTutar(Long tutar) {
        this.tutar = tutar;
    }

    public Long getKdvTutari() {
        return kdvTutari;
    }

    public void setKdvTutari(Long kdvTutari) {
        this.kdvTutari = kdvTutari;
    }

    public Long getToplamTutar() {
        return toplamTutar;
    }

    public void setToplamTutar(Long toplamTutar) {
        this.toplamTutar = toplamTutar;
    }

    public Long getAmt2AmbarId() {
        return amt2AmbarId;
    }

    public void setAmt2AmbarId(Long amt2AmbarId) {
        this.amt2AmbarId = amt2AmbarId;
    }

    public String getTasinirinKodu() {
        return tasinirinKodu;
    }

    public void setTasinirinKodu(String tasinirinKodu) {
        this.tasinirinKodu = tasinirinKodu;
    }

    public Long getDemirbasSicilNo() {
        return demirbasSicilNo;
    }

    public void setDemirbasSicilNo(Long demirbasSicilNo) {
        this.demirbasSicilNo = demirbasSicilNo;
    }
}
