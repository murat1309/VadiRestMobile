package com.digikent.denetimyonetimi.service;

import com.digikent.denetimyonetimi.dao.DenetimRepository;
import com.digikent.denetimyonetimi.entity.BDNTDenetimTespitTaraf;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Kadir on 23.02.2018.
 */
@Service
public class TarafService {

    private final Logger LOG = LoggerFactory.getLogger(TarafService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    DenetimRepository denetimRepository;

    @Autowired
    DenetimService denetimService;

    public List<BDNTDenetimTespitTaraf> getDenetimTarafListByDenetimId(Long denetimId) {
        return denetimRepository.findDenetimTespitTarafListByDenetimId(denetimId);
    }

    public List<BDNTDenetimTespitTaraf> getDenetimTarafListByDenetimIdAndTarafTuru(Long denetimId, String tarafTuru) {
        return denetimRepository.findDenetimTespitTarafListByDenetimIdAndTarafTuru(denetimId,tarafTuru);
    }

}
