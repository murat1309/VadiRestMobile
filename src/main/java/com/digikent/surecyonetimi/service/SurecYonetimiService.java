package com.digikent.surecyonetimi.service;

import com.digikent.surecyonetimi.dao.SurecYonetimiRepository;
import com.digikent.surecyonetimi.dto.*;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Medet on 12/28/2017.
 */
@Service
public class SurecYonetimiService {

    @Inject
    SurecYonetimiRepository surecYonetimiRepository;

    public SurecSorguResponseDTO getSurecSorguListesiBySorguNo(SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO;

        surecSorguResponseDTO = surecYonetimiRepository.getSurecSorguListesiBySorguNo(surecSorguRequestDTO);

        return surecSorguResponseDTO;
    }

    public SurecSorguResponseDTO getSurecInfoBySorguNo(SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO;

        surecSorguResponseDTO = surecYonetimiRepository.getSurecInfoBySorguNo(surecSorguRequestDTO);

        return surecSorguResponseDTO;
    }

    public SurecSorguResponseDTO getSurecCommentBySorguNo(SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO;

        surecSorguResponseDTO = surecYonetimiRepository.getSurecCommentBySorguNo(surecSorguRequestDTO);

        return surecSorguResponseDTO;
    }

    public SurecSorguResponseDTO getSurecDocumentBySorguNo(SurecSorguRequestDTO surecSorguRequestDTO) {

        SurecSorguResponseDTO surecSorguResponseDTO;

        surecSorguResponseDTO = surecYonetimiRepository.getSurecDocumentBySorguNo(surecSorguRequestDTO);

        return surecSorguResponseDTO;
    }

}
