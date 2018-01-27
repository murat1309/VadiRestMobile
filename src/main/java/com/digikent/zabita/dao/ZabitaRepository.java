package com.digikent.zabita.dao;

import com.digikent.mesajlasma.dao.MesajlasmaRepository;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Kadir on 26.01.2018.
 */
@Repository
public class ZabitaRepository {

    private final Logger LOG = LoggerFactory.getLogger(ZabitaRepository.class);

    @Autowired
    SessionFactory sessionFactory;

}
