package com.digikent.surecyonetimi.dto;




public class ImarSurecRequestDTO {

    private Long tcNo;
    private Long paydasNo;
    private String adaNo;
    private String parselNo;
    private String paftaNo;

    public Long getTcNo() {
        return tcNo;
    }

    public void setTcNo(Long tcNo) {
        this.tcNo = tcNo;
    }

    public Long getPaydasNo() {
        return paydasNo;
    }

    public void setPaydasNo(Long paydasNo) {
        this.paydasNo = paydasNo;
    }

    public String getAdaNo() {
        return adaNo;
    }

    public void setAdaNo(String adaNo) {
        this.adaNo = adaNo;
    }

    public String getParselNo() {
        return parselNo;
    }

    public void setParselNo(String parselNo) {
        this.parselNo = parselNo;
    }

    public String getPaftaNo() {
        return paftaNo;
    }

    public void setPaftaNo(String paftaNo) {
        this.paftaNo = paftaNo;
    }
}
