package com.digikent.denetimyonetimi.dto.denetim;

public class DenetimGecmisDenetimlerDTO {

    private String denetimTuruAdi;
    private String tespitGrubuAdi;
    private String denetimAksiyonu;
    private Long denetimTespitId;
    private String denetimTespitTarih;

    public DenetimGecmisDenetimlerDTO() {
    }

    public String getDenetimTuruAdi() {
        return denetimTuruAdi;
    }

    public void setDenetimTuruAdi(String denetimTuruAdi) {
        this.denetimTuruAdi = denetimTuruAdi;
    }

    public String getTespitGrubuAdi() {
        return tespitGrubuAdi;
    }

    public void setTespitGrubuAdi(String tespitGrubuAdi) {
        this.tespitGrubuAdi = tespitGrubuAdi;
    }

    public String getDenetimAksiyonu() {
        return denetimAksiyonu;
    }

    public void setDenetimAksiyonu(String denetimAksiyonu) {
        this.denetimAksiyonu = denetimAksiyonu;
    }

    public Long getDenetimTespitId() {
        return denetimTespitId;
    }

    public void setDenetimTespitId(Long denetimTespitId) {
        this.denetimTespitId = denetimTespitId;
    }

    public String getDenetimTespitTarih() {
        return denetimTespitTarih;
    }

    public void setDenetimTespitTarih(String denetimTespitTarih) {
        this.denetimTespitTarih = denetimTespitTarih;
    }
}
