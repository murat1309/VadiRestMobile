package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 27.02.2018.
 */
@Entity
@Table(name="IHR1PERSONEL")
public class IHR1Personel extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "ihr1personel_seq", sequenceName = "IHR1PERSONEL_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ihr1personel_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne
    @JoinColumn(name="LHR1GOREVTURU_ID")
    private LHR1GorevTuru lhr1GorevTuru = new LHR1GorevTuru();

    @Column(name = "ADI")
    private String adi;

    @Column(name = "SOYADI")
    private String soyadi;

    @Column(name = "KURUMSICILNUMARASI")
    private Long kurumSicilNumarasi;

    @Column(name = "TURU")
    private String turu;

    @Column(name = "ELEKTRONIKPOSTA")
    private String elektronikPosta;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public LHR1GorevTuru getLhr1GorevTuru() {
        return lhr1GorevTuru;
    }

    public void setLhr1GorevTuru(LHR1GorevTuru lhr1GorevTuru) {
        this.lhr1GorevTuru = lhr1GorevTuru;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public Long getKurumSicilNumarasi() {
        return kurumSicilNumarasi;
    }

    public void setKurumSicilNumarasi(Long kurumSicilNumarasi) {
        this.kurumSicilNumarasi = kurumSicilNumarasi;
    }

    public String getTuru() {
        return turu;
    }

    public void setTuru(String turu) {
        this.turu = turu;
    }

    public String getElektronikPosta() {
        return elektronikPosta;
    }

    public void setElektronikPosta(String elektronikPosta) {
        this.elektronikPosta = elektronikPosta;
    }
}
