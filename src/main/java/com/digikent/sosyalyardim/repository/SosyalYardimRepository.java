package com.digikent.sosyalyardim.repository;

import com.vadi.smartkent.datamodel.domains.sosyalhizmetler.sya.SY1Dosya;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Serkan on 8/16/16.
 */
public interface SosyalYardimRepository  extends JpaRepository<SY1Dosya, Long>, QueryDslPredicateExecutor<SY1Dosya> {

    List<SY1Dosya> findByDeleteFlagIsNullOrDeleteFlagNotIn(Boolean deleteFlag);
}

