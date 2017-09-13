package com.digikent.mesajlasma.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kadir on 11/09/17.
 */
@Entity
@Table(name = "VEILMESAJ")
public class VeilMesaj extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "veilmesaj_seq", sequenceName = "VEILMESAJ_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "veilmesaj_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Temporal(TemporalType.DATE)
    @Column(name = "GONDERIMZAMANI", nullable = false)
    private Date gonderimZamani;

    @Column(name = "IHR1PERSONEL_YAZAN", nullable = false)
    private Long ihr1PersonelYazanId;

    @Column(name = "GONDERIMTURU", nullable = false)
    private String gonderimTuru;

    @Column(name = "IHR1PERSONEL_ILETILEN")
    private Long ihr1PersonelIletilenId;

    @Column(name = "MESAJ")
    private String mesaj;

    @Temporal(TemporalType.DATE)
    @Column(name = "OKUNMAZAMANI")
    private Date okunmaZamani;

    @OneToOne
    @JoinColumn(name = "TEILMESAJILETIMGRUBU_ILETILEN")
    private TeilMesajIletimGrubu teilMesajIletimGrubu;


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Date getGonderimZamani() {
        return gonderimZamani;
    }

    public void setGonderimZamani(Date gonderimZamani) {
        this.gonderimZamani = gonderimZamani;
    }

    public Long getIhr1PersonelYazanId() {
        return ihr1PersonelYazanId;
    }

    public void setIhr1PersonelYazanId(Long ihr1PersonelYazanId) {
        this.ihr1PersonelYazanId = ihr1PersonelYazanId;
    }

    public String getGönderimTuru() {
        return gonderimTuru;
    }

    public void setGönderimTuru(String gönderimTuru) {
        this.gonderimTuru = gönderimTuru;
    }

    public Long getIhr1PersonelIletilenId() {
        return ihr1PersonelIletilenId;
    }

    public void setIhr1PersonelIletilenId(Long ihr1PersonelIletilenId) {
        this.ihr1PersonelIletilenId = ihr1PersonelIletilenId;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public Date getOkunmaZamani() {
        return okunmaZamani;
    }

    public void setOkunmaZamani(Date okunmaZamani) {
        this.okunmaZamani = okunmaZamani;
    }

    public TeilMesajIletimGrubu getTeilMesajIletimGrubu() {
        return teilMesajIletimGrubu;
    }

    public void setTeilMesajIletimGrubu(TeilMesajIletimGrubu teilMesajIletimGrubu) {
        this.teilMesajIletimGrubu = teilMesajIletimGrubu;
    }
}
