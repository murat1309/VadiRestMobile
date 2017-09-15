package com.digikent.mesajlasma.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 11/09/17.
 */
@Entity
@Table(name = "TEILMESAJILETIMGRUBULINE")
public class TeilMesajİletimGrubuLine extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "teilmesajgrubuline_seq", sequenceName = "VEILMESAJ_ID", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teilmesajgrubuline_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "ASM2SERVISTURU_ID")
    private Long asm2ServisTuruId;

    @Column(name = "BSM2SERVIS_ID")
    private Long bsm2ServisTuruId;

    @Column(name = "LHR1GOREVTURU_ID")
    private Long lhr1GorevTuruId;

    @Column(name = "IHR1PERSONEL_ID")
    private Long ihr1PersonelId;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getAsm2ServisTuruId() {
        return asm2ServisTuruId;
    }

    public void setAsm2ServisTuruId(Long asm2ServisTuruId) {
        this.asm2ServisTuruId = asm2ServisTuruId;
    }

    public Long getBsm2ServisTuruId() {
        return bsm2ServisTuruId;
    }

    public void setBsm2ServisTuruId(Long bsm2ServisTuruId) {
        this.bsm2ServisTuruId = bsm2ServisTuruId;
    }

    public Long getLhr1GorevTuruId() {
        return lhr1GorevTuruId;
    }

    public void setLhr1GorevTuruId(Long lhr1GorevTuruId) {
        this.lhr1GorevTuruId = lhr1GorevTuruId;
    }

    public Long getIhr1PersonelId() {
        return ihr1PersonelId;
    }

    public void setIhr1PersonelId(Long ihr1PersonelId) {
        this.ihr1PersonelId = ihr1PersonelId;
    }
}
