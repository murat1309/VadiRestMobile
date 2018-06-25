package com.digikent.general.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TSM2PARAMS")
public class TSM2Params extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "tsm2params_seq", sequenceName = "TSM2PARAMS_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tsm2params_seq")
    @Column(name = "ID", nullable = false, updatable = false, unique = true)
    private Long ID;

    @OneToOne
    @JoinColumn(name = "RSM2PARAMGROUP_ID")
    private RSM2ParamGroup rsm2ParamGroup;

    @OneToOne
    @JoinColumn(name = "SSM2PARAMNAME_ID")
    private SSM2ParamName ssm2ParamName;

    @Column(name = "USM2PARAMVALUE_ID")
    private Long usm2ParamValueId;

    @Column(name = "PARAMADI")
    private String paramAdi;

    @Column(name = "PARAMVALUE")
    private String paramValue;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getUsm2ParamValueId() {
        return usm2ParamValueId;
    }

    public void setUsm2ParamValueId(Long usm2ParamValueId) {
        this.usm2ParamValueId = usm2ParamValueId;
    }

    public String getParamAdi() {
        return paramAdi;
    }

    public void setParamAdi(String paramAdi) {
        this.paramAdi = paramAdi;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public RSM2ParamGroup getRsm2ParamGroup() {
        return rsm2ParamGroup;
    }

    public void setRsm2ParamGroup(RSM2ParamGroup rsm2ParamGroup) {
        this.rsm2ParamGroup = rsm2ParamGroup;
    }

    public SSM2ParamName getSsm2ParamName() {
        return ssm2ParamName;
    }

    public void setSsm2ParamName(SSM2ParamName ssm2ParamName) {
        this.ssm2ParamName = ssm2ParamName;
    }
}
