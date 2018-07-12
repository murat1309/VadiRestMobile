package com.digikent.general.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ABPMWORKITEM")
public class ABPMWorkItem extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "abpmworkitem_seq", sequenceName = "ABPMWORKITEM_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "abpmworkitem_seq")
    @Column(name = "ID")
    private Long ID;

    @Column(name = "ACTION")
    private String action;

    @JoinColumn(name = "ABPMTASK_ID")
    @OneToOne
    private ABPMTask abpmTask;

    @Column(name = "FSM1USERS_PERFORMER")
    private Long fsm1UsersPerformer;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ABPMTask getAbpmTask() {
        return abpmTask;
    }

    public void setAbpmTask(ABPMTask abpmTask) {
        this.abpmTask = abpmTask;
    }

    public Long getFsm1UsersPerformer() {
        return fsm1UsersPerformer;
    }

    public void setFsm1UsersPerformer(Long fsm1UsersPerformer) {
        this.fsm1UsersPerformer = fsm1UsersPerformer;
    }
}
