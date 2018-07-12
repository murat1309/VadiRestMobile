package com.digikent.sosyalyardim.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "TSY1AKTIVITEISLEM")
public class TSY1AktiviteIslem implements Serializable {

    @Id
    @SequenceGenerator(name = "tsy1aktiviteislem_seq", sequenceName = "TSY1AKTIVITEISLEM_ID",initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tsy1aktiviteislem_seq")
    @Column(name = "ID", nullable = false, updatable = false)
    private long ID;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name= "KAYITOZELISMI")
    private String kayıtOzelIsmı;

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public void setKayıtOzelIsmı(String kayıtOzelIsmı) {
        this.kayıtOzelIsmı = kayıtOzelIsmı;
    }

    public long getID() {

        return ID;
    }

    public String getTanim() {
        return tanim;
    }

    public String getKayıtOzelIsmı() {
        return kayıtOzelIsmı;
    }
}
