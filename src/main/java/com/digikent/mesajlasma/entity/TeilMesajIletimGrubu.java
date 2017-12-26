package com.digikent.mesajlasma.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 11/09/17.
 */
@Entity
@Table(name = "TEILMESAJILETIMGRUBU")
public class TeilMesajIletimGrubu extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "teilmesajgrubu_seq", sequenceName = "TEILMESAJILETIMGRUBU_ID", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teilmesajgrubu_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "TANIM", nullable = false)
    private String tanim;

    @Column(name = "GRUPTURU", nullable = false)
    private String grupTuru;

    @Column(name = "IHR1PERSONEL_OLUSTURAN")
    private Long ihr1PersonelOlusturanId;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="TEILMESAJILETIMGRUBU_ID", nullable=false)
    List<TeilMesajİletimGrubuLine> teilMesajİletimGrubuLineList = new ArrayList<TeilMesajİletimGrubuLine>();

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

    public String getGrupTuru() {
        return grupTuru;
    }

    public void setGrupTuru(String grupTuru) {
        this.grupTuru = grupTuru;
    }

    public Long getIhr1PersonelOlusturanId() {
        return ihr1PersonelOlusturanId;
    }

    public void setIhr1PersonelOlusturanId(Long ihr1PersonelOlusturanId) {
        this.ihr1PersonelOlusturanId = ihr1PersonelOlusturanId;
    }

    public List<TeilMesajİletimGrubuLine> getTeilMesajİletimGrubuLineList() {
        return teilMesajİletimGrubuLineList;
    }

    public void setTeilMesajİletimGrubuLineList(List<TeilMesajİletimGrubuLine> teilMesajİletimGrubuLineList) {
        this.teilMesajİletimGrubuLineList = teilMesajİletimGrubuLineList;
    }
}
