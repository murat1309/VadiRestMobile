package com.digikent.general.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RSM2PARAMGROUP")
public class RSM2ParamGroup extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "rsm2paramgroup_seq", sequenceName = "RSM2PARAMGROUP_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rsm2paramgroup_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "KODU")
    private String kodu;

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

    public String getKodu() {
        return kodu;
    }

    public void setKodu(String kodu) {
        this.kodu = kodu;
    }
}
