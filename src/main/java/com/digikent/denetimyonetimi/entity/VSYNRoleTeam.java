package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 22.02.2018.
 */
@Entity
@Table(name="VSYNROLETEAM")
public class VSYNRoleTeam extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "vsynroleteam_seq", sequenceName = "VSYNROLETEAM_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vsynroleteam_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Column(name = "RNAME")
    private String rolName;

    @Column(name = "ROLETYPE")
    private String roleType;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="vsynRoleTeam")
    List<VSYNMemberShip> vsynMemberShipList = new ArrayList<>();

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public List<VSYNMemberShip> getVsynMemberShipList() {
        return vsynMemberShipList;
    }

    public void setVsynMemberShipList(List<VSYNMemberShip> vsynMemberShipList) {
        this.vsynMemberShipList = vsynMemberShipList;
    }
}
