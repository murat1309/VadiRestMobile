package com.digikent.surecyonetimi.izinonay.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "HHR1IZINTALEBILINE")
public class HHR1IzinTalebiLine extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "hhr1izintalebiline_seq", sequenceName = "HHR1IZINTALEBILINE_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hhr1izintalebiline_seq")
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Long ID;

    @Column(name = "HHR1IZINTALEBI_ID")
    private Long hhr1IzinTalebiId;

    @Column(name = "KULLANILACAKSAAT")
    private Long kullanilacakSaat;

    @Column(name = "KULLANILACAKGUN")
    private Long kullanilacakGun;

    @Column(name = "KULLANILACAKDAKIKA")
    private Long kullanilacakDakika;

    @Column(name = "KULLANILANGUN")
    private Long kullanilanGun;

    @Column(name = "KULLANILANSAAT")
    private Long kullanilanSaat;

    @Column(name = "KULLANILANDAKIKA")
    private Long kullanilanDakika;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getHhr1IzinTalebiId() {
        return hhr1IzinTalebiId;
    }

    public void setHhr1IzinTalebiId(Long hhr1IzinTalebiId) {
        this.hhr1IzinTalebiId = hhr1IzinTalebiId;
    }

    public Long getKullanilacakSaat() {
        return kullanilacakSaat;
    }

    public void setKullanilacakSaat(Long kullanilacakSaat) {
        this.kullanilacakSaat = kullanilacakSaat;
    }

    public Long getKullanilacakGun() {
        return kullanilacakGun;
    }

    public void setKullanilacakGun(Long kullanilacakGun) {
        this.kullanilacakGun = kullanilacakGun;
    }

    public Long getKullanilacakDakika() {
        return kullanilacakDakika;
    }

    public void setKullanilacakDakika(Long kullanilacakDakika) {
        this.kullanilacakDakika = kullanilacakDakika;
    }

    public Long getKullanilanGun() {
        return kullanilanGun;
    }

    public void setKullanilanGun(Long kullanilanGun) {
        this.kullanilanGun = kullanilanGun;
    }

    public Long getKullanilanSaat() {
        return kullanilanSaat;
    }

    public void setKullanilanSaat(Long kullanilanSaat) {
        this.kullanilanSaat = kullanilanSaat;
    }

    public Long getKullanilanDakika() {
        return kullanilanDakika;
    }

    public void setKullanilanDakika(Long kullanilanDakika) {
        this.kullanilanDakika = kullanilanDakika;
    }
}
