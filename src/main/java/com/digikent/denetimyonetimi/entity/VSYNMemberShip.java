package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;
import com.digikent.general.entity.FSM1Users;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 22.02.2018.
 */

@Entity
@Table(name="VSYNMEMBERSHIP")
public class VSYNMemberShip extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "vsynmembership_seq", sequenceName = "VSYNMEMBERSHIP_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vsynmembership_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @OneToOne
    @JoinColumn(name="PARENT_ID")
    private VSYNRoleTeam vsynRoleTeam;

/*    @OneToOne
    @JoinColumn(name="CHILD_ID")
    private FSM1Users fsm1Users;*/
    @Column(name = "CHILD_ID")
    private Long fsm1UsersId;

    @Column(name = "PARENTOBJECTNAME")
    private String parentObjectName;

    @Column(name = "CHILDOBJECTNAME")
    private String childObjectName;

    @Column(name = "PARENTNAME")
    private String parentName;

    @Column(name = "CHILDNAME")
    private String childName;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public VSYNRoleTeam getVsynRoleTeam() {
        return vsynRoleTeam;
    }

    public void setVsynRoleTeam(VSYNRoleTeam vsynRoleTeam) {
        this.vsynRoleTeam = vsynRoleTeam;
    }

    public Long getFsm1UsersId() {
        return fsm1UsersId;
    }

    public void setFsm1UsersId(Long fsm1UsersId) {
        this.fsm1UsersId = fsm1UsersId;
    }

    public String getParentObjectName() {
        return parentObjectName;
    }

    public void setParentObjectName(String parentObjectName) {
        this.parentObjectName = parentObjectName;
    }

    public String getChildObjectName() {
        return childObjectName;
    }

    public void setChildObjectName(String childObjectName) {
        this.childObjectName = childObjectName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
