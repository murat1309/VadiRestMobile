package com.digikent.sosyalyardim.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 14.05.2018.
 */
@Entity
@Table(name="TSY1TESPITSORUTURU")
public class TSY1TespitSoruTuru extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "tsy1tespitsoruturu_seq", sequenceName = "TSY1TESPITSORUTURU_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tsy1tespitsoruturu_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "IZAHAT")
    private String sira;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public String getSira() {
        return sira;
    }

    public void setSira(String sira) {
        this.sira = sira;
    }
}
