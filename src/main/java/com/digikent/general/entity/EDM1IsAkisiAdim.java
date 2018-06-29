package com.digikent.general.entity;

import com.digikent.denetimyonetimi.entity.IHR1Personel;
import com.digikent.denetimyonetimi.entity.MPI1Paydas;
import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EDM1ISAKISIADIM")
public class EDM1IsAkisiAdim extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "edm1isakisiadim_seq", sequenceName = "EDM1ISAKISIADIM_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edm1isakisiadim_seq")
    @Column(name = "ID")
    private Long ID;

    @Column(name = "ALC_MSM2ORGANIZASYON_ID")
    private Long alcMsm2OrganizasyonId;

    @Column(name = "GON_MSM2ORGANIZASYON_ID")
    private Long gonMsm2OrganizasyonId;

    @Column(name = "DURUMU")
    private String durumu;

    @Column(name = "SONUCDURUMU")
    private String sonucDurumu;

    @JoinColumn(name = "DDM1ISAKISI_ID")
    @OneToOne
    private DDM1IsAkisi ddm1IsAkisi;

    @JoinColumn(name = "GON_IHR1PERSONEL_ID")
    @OneToOne
    private IHR1Personel gonIhr1PersonelId;

    @JoinColumn(name = "GON_MPI1PAYDAS_ID")
    @OneToOne
    private MPI1Paydas gonMpi1PaydasId;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getAlcMsm2OrganizasyonId() {
        return alcMsm2OrganizasyonId;
    }

    public void setAlcMsm2OrganizasyonId(Long alcMsm2OrganizasyonId) {
        this.alcMsm2OrganizasyonId = alcMsm2OrganizasyonId;
    }

    public Long getGonMsm2OrganizasyonId() {
        return gonMsm2OrganizasyonId;
    }

    public void setGonMsm2OrganizasyonId(Long gonMsm2OrganizasyonId) {
        this.gonMsm2OrganizasyonId = gonMsm2OrganizasyonId;
    }

    public String getDurumu() {
        return durumu;
    }

    public void setDurumu(String durumu) {
        this.durumu = durumu;
    }

    public String getSonucDurumu() {
        return sonucDurumu;
    }

    public void setSonucDurumu(String sonucDurumu) {
        this.sonucDurumu = sonucDurumu;
    }

    public DDM1IsAkisi getDdm1IsAkisi() {
        return ddm1IsAkisi;
    }

    public void setDdm1IsAkisi(DDM1IsAkisi ddm1IsAkisi) {
        this.ddm1IsAkisi = ddm1IsAkisi;
    }

    public IHR1Personel getGonIhr1PersonelId() {
        return gonIhr1PersonelId;
    }

    public void setGonIhr1PersonelId(IHR1Personel gonIhr1PersonelId) {
        this.gonIhr1PersonelId = gonIhr1PersonelId;
    }

    public MPI1Paydas getGonMpi1PaydasId() {
        return gonMpi1PaydasId;
    }

    public void setGonMpi1PaydasId(MPI1Paydas gonMpi1PaydasId) {
        this.gonMpi1PaydasId = gonMpi1PaydasId;
    }
}
