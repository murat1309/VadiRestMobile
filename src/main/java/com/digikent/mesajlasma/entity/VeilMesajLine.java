package com.digikent.mesajlasma.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kadir on 11/09/17.
 */

@Entity
@Table(name = "VEILMESAJLINE")
public class VeilMesajLine extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "veilmesajline_seq", sequenceName = "VEILMESAJLINE_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "veilmesajline_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "VEILMESAJ_ID")
    private VeilMesaj veilMesaj;

    @Column(name = "IHR1PERSONEL_ILETILEN")
    private Long ihr1PersonelIletilenId;

    @Temporal(TemporalType.DATE)
    @Column(name = "OKUNMAZAMANI")
    private Date okunmaZamani;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public VeilMesaj getVeilMesaj() {
        return veilMesaj;
    }

    public void setVeilMesaj(VeilMesaj veilMesaj) {
        this.veilMesaj = veilMesaj;
    }

    public Long getIhr1PersonelIletilenId() {
        return ihr1PersonelIletilenId;
    }

    public void setIhr1PersonelIletilenId(Long ihr1PersonelIletilenId) {
        this.ihr1PersonelIletilenId = ihr1PersonelIletilenId;
    }

    public Date getOkunmaZamani() {
        return okunmaZamani;
    }

    public void setOkunmaZamani(Date okunmaZamani) {
        this.okunmaZamani = okunmaZamani;
    }
}
