package com.digikent.sosyalyardim.service;

import com.digikent.sosyalyardim.repository.SosyalYardimRepository;
import com.digikent.sosyalyardim.repository.predicate.CommonPredicateBuilder;
import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Dosya;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Serkan on 8/16/16.
 */
@Service
public class SosyalYardimService {

    @Inject
    private SosyalYardimRepository repository;

    public SY1Dosya save(SY1Dosya saklamaPlani) {
        SY1Dosya saved = repository.save(saklamaPlani);
        return saved;
    }

    public SY1Dosya save(Long id, SY1Dosya sy1Dosya) {
        sy1Dosya.setID(id);
        return repository.save(sy1Dosya);
    }

    public List<SY1Dosya> list() {
        return repository.findByDeleteFlagIsNullOrDeleteFlagNotIn(Boolean.TRUE);
    }

    public void delete(SY1Dosya saklamaPlani) {
        repository.delete(saklamaPlani);
    }

    public void delete(Long id) {
        SY1Dosya entity = repository.findOne(id);
        entity.setDeleteFlag("true");
        repository.delete(entity);
    }

    public List<SY1Dosya> search(SY1Dosya filter){
        CommonPredicateBuilder<SY1Dosya> builder = new CommonPredicateBuilder<>(SY1Dosya.class);
        builder.with("ID", ":", filter.getID());
        builder.with("tckimlikno", ":", filter.getTckimlikno());
        builder.with("dosyanumarasi", ":", filter.getDosyanumarasi());
        Optional.ofNullable(filter.getMpi1paydas386()).ifPresent(mp1paydas->builder.with("mp1paydas.adi", ":", mp1paydas.getAdi()));
        Optional.ofNullable(filter.getMpi1paydas386()).ifPresent(mp1paydas->builder.with("mp1paydas.soyadi", ":", mp1paydas.getSoyadi()));
        return StreamSupport.stream(repository.findAll(builder.build()).spliterator(), false).collect(Collectors.toList());
    }
}
