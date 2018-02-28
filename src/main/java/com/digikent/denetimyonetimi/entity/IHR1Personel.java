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

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "ADI")
    private String adi;

    @Column(name = "SOYADI")
    private String soyadi;

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

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
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
}
