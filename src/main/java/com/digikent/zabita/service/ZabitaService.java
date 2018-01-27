package com.digikent.zabita.service;

import com.digikent.zabita.dao.ZabitaRepository;
import com.digikent.zabita.entity.BDNTDenetim;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by Kadir on 26.01.2018.
 */
@Service
public class ZabitaService {

    private final Logger LOG = LoggerFactory.getLogger(ZabitaService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    ZabitaRepository zabitaRepository;

    public Boolean saveZabitaDenetim(Long paydasId) {

        LOG.debug("Denetim KAYDI paydas ID = " + paydasId);

        BDNTDenetim bdntDenetim = new BDNTDenetim();
        bdntDenetim.setMpi1PaydasId(paydasId);
        bdntDenetim.setDenetimTarihi(new Date());
        //TODO buralara doğru şekilde setleme yap
        bdntDenetim.setCrUser(1l);
        bdntDenetim.setCrDate(new Date());
        bdntDenetim.setDeleteFlag("H");
        bdntDenetim.setIsActive(true);
        bdntDenetim.setDenetimTarafTipi("I");

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.save(bdntDenetim);
        tx.commit();

        return true;
    }
}
