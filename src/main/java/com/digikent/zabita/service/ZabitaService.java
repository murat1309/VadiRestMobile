package com.digikent.zabita.service;

import com.digikent.zabita.dao.ZabitaRepository;
import com.digikent.zabita.dto.adres.BelediyeDTO;
import com.digikent.zabita.dto.adres.MahalleSokakDTO;
import com.digikent.zabita.dto.denetim.ZabitaDenetimRequest;
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
import java.util.List;

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

    public Boolean saveZabitaDenetim(ZabitaDenetimRequest zabitaDenetimRequest) {
        return zabitaRepository.saveZabitaDenetim(zabitaDenetimRequest);
    }

    public List<MahalleSokakDTO> getMahalleSokakListByBelediyeId(Long belediyeId) {
        return zabitaRepository.findMahalleAndSokakListByBelediyeId(belediyeId);
    }

    public List<BelediyeDTO> getBelediyeList() {
        return zabitaRepository.findBelediyeList();
    }
}
