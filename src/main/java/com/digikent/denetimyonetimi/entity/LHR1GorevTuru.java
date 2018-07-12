package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 27.02.2018.
 */
@Entity
@Table(name="LHR1GOREVTURU")
public class LHR1GorevTuru extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "lhr1gorevturu_seq", sequenceName = "LHR1GOREVTURU_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lhr1gorevturu_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "TANIM")
    private String tanim;

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
}
