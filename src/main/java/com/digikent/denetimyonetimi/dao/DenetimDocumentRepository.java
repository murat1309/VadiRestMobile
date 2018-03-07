package com.digikent.denetimyonetimi.dao;

import com.digikent.denetimyonetimi.service.DenetimDocumentService;
import com.digikent.denetimyonetimi.service.DenetimTarafService;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Kadir on 7.03.2018.
 */
@Repository
public class DenetimDocumentRepository {
    private final Logger LOG = LoggerFactory.getLogger(DenetimDocumentRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    DenetimDocumentService denetimDocumentService;


}
