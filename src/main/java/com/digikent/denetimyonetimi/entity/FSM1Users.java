package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 22.02.2018.
 */
@Entity
@Table(name="FSM1USERS")
public class FSM1Users extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "fsm1users_seq", sequenceName = "FSM1USERS_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fsm1users_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "IKY_PERSONEL_ID")
    private Long ikyPersonelId;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getIkyPersonelId() {
        return ikyPersonelId;
    }

    public void setIkyPersonelId(Long ikyPersonelId) {
        this.ikyPersonelId = ikyPersonelId;
    }
}
