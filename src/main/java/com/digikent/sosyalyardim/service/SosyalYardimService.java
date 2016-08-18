package com.digikent.sosyalyardim.service;

import com.digikent.sosyalyardim.repository.predicate.CommonPredicateBuilder;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Dosya;
import org.springframework.stereotype.Service;
import tr.com.vadi.dto.SY1DosyaDTO;
import tr.com.vadi.provider.SY1DosyaProvider;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by Serkan on 8/16/16.
 */
@Service
public class SosyalYardimService {

    @Inject
    private SY1DosyaProvider dosyaProvider;

    public List<SY1DosyaDTO> search(SY1Dosya filter){
        CommonPredicateBuilder<SY1Dosya> builder = new CommonPredicateBuilder<>(SY1Dosya.class);
        builder.with("ID", ":", filter.getID());
        builder.with("tckimlikno", ":", filter.getTckimlikno());
        builder.with("dosyanumarasi", ":", filter.getDosyanumarasi());
        Optional.ofNullable(filter.getMpi1paydas386()).ifPresent(mp1paydas->builder.with("mp1paydas.adi", ":", mp1paydas.getAdi()));
        Optional.ofNullable(filter.getMpi1paydas386()).ifPresent(mp1paydas->builder.with("mp1paydas.soyadi", ":", mp1paydas.getSoyadi()));
        return dosyaProvider.findAll(builder.build());
    }
}
