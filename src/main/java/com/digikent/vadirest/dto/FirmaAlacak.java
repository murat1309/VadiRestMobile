package com.digikent.vadirest.dto;

/**
 * Created by Serkan on 5/13/16.
 */
public class FirmaAlacak {
    private String tanim;
    private double borc;
    private double alacak;
    private double bakiye;
    private long paydas;

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public double getBorc() {
        return borc;
    }

    public void setBorc(double borc) {
        this.borc = borc;
    }

    public double getAlacak() {
        return alacak;
    }

    public void setAlacak(double alacak) {
        this.alacak = alacak;
    }

    public double getBakiye() {
        return bakiye;
    }

    public void setBakiye(double bakiye) {
        this.bakiye = bakiye;
    }

    public long getPaydas() {
        return paydas;
    }

    public void setPaydas(long paydas) {
        this.paydas = paydas;
    }
}
