package com.digikent.mesajlasma.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 11/09/17.
 */

@Entity
@Table(name = "TEILMESAJILETIMGRUBU")
public class TeilMesajIletimGrubu extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "teilmesajgrubu_seq", sequenceName = "TEILMESAJGRUP_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teilmesajgrubu_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "TANIM", nullable = false)
    private String tanim;

    @Column(name = "GRUPTURU", nullable = false)
    private String grupTuru;

    @Column(name = "IHR1PERSONEL_OLUSTURAN")
    private Long ihr1PersonelOlusturanId;

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
}
