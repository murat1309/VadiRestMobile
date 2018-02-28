package com.digikent.general.dao;

import com.digikent.denetimyonetimi.entity.VSYNMemberShip;
import com.digikent.denetimyonetimi.entity.VSYNRoleTeam;
import com.digikent.denetimyonetimi.entity.FSM1Users;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kadir on 22.02.2018.
 */
@Repository
public class TeamRepository {

    private final Logger LOG = LoggerFactory.getLogger(TeamRepository.class);

    @Autowired
    SessionFactory sessionFactory;


    /*
    * kullanıcının bulunduğu grupları bulur
    * */
    public List<VSYNMemberShip> findVSNYMemberShipListByUserId(Long userId) {

        //FSM1Users fsm1Users = findFsm1UsersById(userId);
        FSM1Users fsm1Users = new FSM1Users();
        fsm1Users.setID(userId);

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(VSYNMemberShip.class);
        criteria.add(Restrictions.eq("childObjectName", "FSM1USERS"));
        criteria.add(Restrictions.eq("parentObjectName", "VSYNROLETEAM"));
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("fsm1Users", fsm1Users));

        List<VSYNMemberShip> list = criteria.list();
        //findVSNYMemberShipListByVSYNRoleTeamId(list.get(0).getVsynRoleTeam());
        return list;
    }

    /*
    * grupların içerisindeki kullanıcıları bulur
    * */
    public List<VSYNMemberShip> findVSNYMemberShipListByVSYNRoleTeamIdList(List<VSYNRoleTeam> vsynRoleTeamList) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(VSYNMemberShip.class);
        criteria.add(Restrictions.eq("childObjectName", "FSM1USERS"));
        criteria.add(Restrictions.eq("parentObjectName", "VSYNROLETEAM"));
        criteria.add(Restrictions.eq("isActive", true));
        Object[] obj = new Object[] {};
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.addAll(vsynRoleTeamList);
        criteria.add(Restrictions.in("vsynRoleTeam", temp.toArray()));
        List<VSYNMemberShip> list = criteria.list();

        return list;
    }

    public FSM1Users findFsm1UsersById(Long id) {
        FSM1Users fsm1Users = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(FSM1Users.class);
        Object userObj = criteria.add(Restrictions.eq("ID", id)).uniqueResult();
        fsm1Users = (FSM1Users)userObj;
        return fsm1Users;
    }
}
