package com.digikent.demirbas.dto;

import java.util.Date;

/**
 * Created by Kadir on 17/07/17.
 */
public class DemirbasHareketDTO {

    private Date islemTarihi;
    private Long tifSiraNumarasi;
    private String gittigiYer;
    private String geldigiYer;

    public Date getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(Date islemTarihi) {
        this.islemTarihi = islemTarihi;
    }

    public Long getTifSiraNumarasi() {
        return tifSiraNumarasi;
    }

    public void setTifSiraNumarasi(Long tifSiraNumarasi) {
        this.tifSiraNumarasi = tifSiraNumarasi;
    }

    public String getGittigiYer() {
        return gittigiYer;
    }

    public void setGittigiYer(String gittigiYer) {
        this.gittigiYer = gittigiYer;
    }

    public String getGeldigiYer() {
        return geldigiYer;
    }

    public void setGeldigiYer(String geldigiYer) {
        this.geldigiYer = geldigiYer;
    }
}
