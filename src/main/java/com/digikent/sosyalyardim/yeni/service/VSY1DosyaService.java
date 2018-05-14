package com.digikent.sosyalyardim.yeni.service;

import com.digikent.sosyalyardim.yeni.dao.SY1DosyaRepository;
import com.digikent.sosyalyardim.yeni.dto.SY1DosyaDTO;
import com.digikent.sosyalyardim.yeni.dto.SYS1DosyaRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Kadir on 11.05.2018.
 */
@Service
public class VSY1DosyaService {

    @Inject
    SY1DosyaRepository sy1DosyaRepository;


    public List<SY1DosyaDTO> getDosyaByCriteria(SYS1DosyaRequest sys1DosyaRequest) {
        return sy1DosyaRepository.findDosyaByCriteria(sys1DosyaRequest);
    }
}
