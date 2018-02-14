package com.digikent.denetimyonetimi.dao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Kadir on 6.02.2018.
 */
@Repository
public class ReportRepository {

    private final Logger LOG = LoggerFactory.getLogger(ReportRepository.class);

    @Autowired
    SessionFactory sessionFactory;



}
