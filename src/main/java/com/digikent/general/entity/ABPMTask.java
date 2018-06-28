package com.digikent.general.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ABPMTASK")
public class ABPMTask extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "abpmtask_seq", sequenceName = "ABPMTASK_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "abpmtask_seq")
    @Column(name = "ID")
    private Long ID;

    @Column(name = "EIMZAREQUIRED")
    private String eImzaRequired;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String geteImzaRequired() {
        return eImzaRequired;
    }

    public void seteImzaRequired(String eImzaRequired) {
        this.eImzaRequired = eImzaRequired;
    }
}
