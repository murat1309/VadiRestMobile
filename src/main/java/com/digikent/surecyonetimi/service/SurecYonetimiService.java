package com.digikent.surecyonetimi.service;

import com.digikent.surecyonetimi.dao.SurecYonetimiRepository;
import com.digikent.surecyonetimi.dto.SurecSorguRequestDTO;
import com.digikent.surecyonetimi.dto.SurecSorguResponseDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

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
}
