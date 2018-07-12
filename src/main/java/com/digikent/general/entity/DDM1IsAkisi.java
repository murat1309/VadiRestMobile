package com.digikent.general.entity;

import com.digikent.denetimyonetimi.entity.IHR1Personel;
import com.digikent.denetimyonetimi.entity.MPI1Paydas;
import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DDM1ISAKISI")
public class DDM1IsAkisi extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "ddm1isakisi_seq", sequenceName = "DDM1ISAKISI_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ddm1isakisi_seq")
    @Column(name = "ID")
    private Long ID;

    @Column(name = "TURU")
    private String turu;

    @JoinColumn(name = "MPI1PAYDAS_ID")
    @OneToOne
    private MPI1Paydas mpi1Paydas;

    @JoinColumn(name = "IHR1PERSONEL_ID")
    @OneToOne
    private IHR1Personel ihr1Personel;

    @JoinColumn(name = "IHR1PERSONEL_RAPORTOR_ID")
    @OneToOne
    private IHR1Personel ihr1PersonelRaportorId;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTuru() {
        return turu;
    }

    public void setTuru(String turu) {
        this.turu = turu;
    }

    public MPI1Paydas getMpi1Paydas() {
        return mpi1Paydas;
    }

    public void setMpi1Paydas(MPI1Paydas mpi1Paydas) {
        this.mpi1Paydas = mpi1Paydas;
    }

    public IHR1Personel getIhr1Personel() {
        return ihr1Personel;
    }

    public void setIhr1Personel(IHR1Personel ihr1Personel) {
        this.ihr1Personel = ihr1Personel;
    }

    public IHR1Personel getIhr1PersonelRaportorId() {
        return ihr1PersonelRaportorId;
    }

    public void setIhr1PersonelRaportorId(IHR1Personel ihr1PersonelRaportorId) {
        this.ihr1PersonelRaportorId = ihr1PersonelRaportorId;
    }
}
