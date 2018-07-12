package com.digikent.general.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SSM2PARAMNAME")
public class SSM2ParamName extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "ssm2paramname_seq", sequenceName = "SSM2PARAMNAME_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ssm2paramname_seq")
    @Column(name = "ID", nullable = false, updatable = false, unique = true)
    private Long ID;

    @Column(name = "TANIM")
    private String tanim;

    @Column(name = "KODU")
    private String kodu;

    @Column(name = "RSM2PARAMGROUP_ID")
    private Long rsm2ParamGroupId;

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

    public Long getRsm2ParamGroupId() {
        return rsm2ParamGroupId;
    }

    public void setRsm2ParamGroupId(Long rsm2ParamGroupId) {
        this.rsm2ParamGroupId = rsm2ParamGroupId;
    }
}
