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
}
