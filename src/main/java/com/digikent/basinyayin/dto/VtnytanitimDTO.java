package com.digikent.basinyayin.dto;

import java.util.Date;

/**
 * Created by Serkan on 4/6/2016.
 */
public class VtnytanitimDTO {

    private Date planlananBaslangic;
    private Date planlananBitis;
    private Date gerceklesenBaslangic;
    private Date gerceklesenBitis;
    private String konusu;
    private String izahat;
    private Long barkodNumarasi;

    public Date getPlanlananBaslangic() {
        return planlananBaslangic;
    }

    public void setPlanlananBaslangic(Date planlananBaslangic) {
        this.planlananBaslangic = planlananBaslangic;
    }

    public Date getPlanlananBitis() {
        return planlananBitis;
    }

    public void setPlanlananBitis(Date planlananBitis) {
        this.planlananBitis = planlananBitis;
    }

    public Date getGerceklesenBaslangic() {
        return gerceklesenBaslangic;
    }

    public void setGerceklesenBaslangic(Date gerceklesenBaslangic) {
        this.gerceklesenBaslangic = gerceklesenBaslangic;
    }

    public Date getGerceklesenBitis() {
        return gerceklesenBitis;
    }

    public void setGerceklesenBitis(Date gerceklesenBitis) {
        this.gerceklesenBitis = gerceklesenBitis;
    }

    public String getKonusu() {
        return konusu;
    }

    public void setKonusu(String konusu) {
        this.konusu = konusu;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public Long getBarkodNumarasi() {
        return barkodNumarasi;
    }

    public void setBarkodNumarasi(Long barkodNumarasi) {
        this.barkodNumarasi = barkodNumarasi;
    }



}
