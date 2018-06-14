package com.digikent.surecyonetimi.surecsorgulama.dto;

import java.util.Date;

/**
 * Created by Medet on 1/9/2018.
 */
public class SanalDepoHareketDTO {
    private Date islemTarihi;
    private String teslimEdilenPersonel;
    private String teslimEdilenUnvan;
    private String geldigiYer;
    private String gittigiYer;


    public SanalDepoHareketDTO() {
    }

    public Date getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(Date islemTarihi) {
        this.islemTarihi = islemTarihi;
    }

    public String getTeslimEdilenPersonel() {
        return teslimEdilenPersonel;
    }

    public void setTeslimEdilenPersonel(String teslimEdilenPersonel) {
        this.teslimEdilenPersonel = teslimEdilenPersonel;
    }

    public String getTeslimEdilenUnvan() {
        return teslimEdilenUnvan;
    }

    public void setTeslimEdilenUnvan(String teslimEdilenUnvan) {
        this.teslimEdilenUnvan = teslimEdilenUnvan;
    }

    public String getGeldigiYer() {
        return geldigiYer;
    }

    public void setGeldigiYer(String geldigiYer) {
        this.geldigiYer = geldigiYer;
    }

    public String getGittigiYer() {
        return gittigiYer;
    }

    public void setGittigiYer(String gittigiYer) {
        this.gittigiYer = gittigiYer;
    }
}
