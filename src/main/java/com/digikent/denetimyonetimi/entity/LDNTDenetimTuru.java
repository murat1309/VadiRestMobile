package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="LDNTDENETIMTURU")
public class LDNTDenetimTuru extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "ldntdenetimturu_seq", sequenceName = "LDNTDENETIMTURU_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ldntdenetimturu_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "IZAHAT")
    private String izahat;

    @Column(name = "KAYITOZELISMI")
    private String kayitOzelIsmi;

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

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public String getKayitOzelIsmi() {
        return kayitOzelIsmi;
    }

    public void setKayitOzelIsmi(String kayitOzelIsmi) {
        this.kayitOzelIsmi = kayitOzelIsmi;
    }
}
