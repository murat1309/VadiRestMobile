package com.digikent.sosyalyardim.rest;

import com.digikent.sosyalyardim.dao.SY1DosyaRepository;
import com.digikent.sosyalyardim.dto.SY1DosyaDTO;
import com.digikent.sosyalyardim.dto.SYS1DosyaRequest;
import com.digikent.sosyalyardim.service.VSY1DosyaService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Serkan on 8/16/16.
 * Edited by Kadir on 05/11/18.
 */
@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/sosyalYardim/sy1dosya")
public class VSY1DosyaResource {

    @Inject
    private SY1DosyaRepository sy1DosyaRepository;

    @Inject
    private VSY1DosyaService VSY1DosyaService;

    @RequestMapping(method = POST, value = "/arama")
    @Transactional
    public ResponseEntity<List<SY1DosyaDTO>> getDosyaByCriteria(@RequestBody SYS1DosyaRequest sys1DosyaRequest) {
        List<SY1DosyaDTO> results = VSY1DosyaService.getDosyaByCriteria(sys1DosyaRequest);
        return new ResponseEntity<List<SY1DosyaDTO>>(results, OK);
    }


}
