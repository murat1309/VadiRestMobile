package com.digikent.denetimyonetimi.entity;

import javax.persistence.*;

/**
 * Created by Kadir on 6.02.2018.
 */
@Entity
@Table(name="MPI1PAYDAS")
public class MPI1Paydas {

    @Id
    @SequenceGenerator(name = "mpi1paydas_seq", sequenceName = "MPI1PAYDAS_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mpi1paydas_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "KAYIT_DURUMU")
    private String kayitDurumu;

    @Column(name = "PAYDASTURU")
    private String paydasTuru;

    @Column(name = "RAPOR_ADI")
    private String raporAdi;

    @Column(name = "SORGU_ADI")
    private String sorguAdi;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getKayitDurumu() {
        return kayitDurumu;
    }

    public void setKayitDurumu(String kayitDurumu) {
        this.kayitDurumu = kayitDurumu;
    }

    public String getPaydasTuru() {
        return paydasTuru;
    }

    public void setPaydasTuru(String paydasTuru) {
        this.paydasTuru = paydasTuru;
    }

    public String getRaporAdi() {
        return raporAdi;
    }

    public void setRaporAdi(String raporAdi) {
        this.raporAdi = raporAdi;
    }

    public String getSorguAdi() {
        return sorguAdi;
    }

    public void setSorguAdi(String sorguAdi) {
        this.sorguAdi = sorguAdi;
    }
}
