package com.digikent.paydasiliskileri.service;

import com.digikent.paydasiliskileri.dao.PaydasIliskileriRepository;
import com.digikent.paydasiliskileri.dto.PaydasSorguRequestDTO;
import com.digikent.paydasiliskileri.dto.PaydasSorguResponseDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by Medet on 1/2/2018.
 */

@Service
public class PaydasIliskileriManagementService {

    @Inject
    PaydasIliskileriRepository paydasIliskileriRepository;


    public String getPaydasInfoDynamicQueryContextByCriteria(PaydasSorguRequestDTO paydasSorguRequestDTO) {

        String query = new String();
        String baseQuery = "select ID,ADI,SOYADI,UNVAN,VERGINUMARASI,PI1.F_TELEFONPAYDAS(MPI1PAYDAS.ID) as TELEFON,IZAHAT," +
                            "PAYDASTURU,TABELAADI,KAYITDURUMU," +
                            "(select max( decode( NVL(MAHALLEADI,'-'), '-' ,'','MAHALLE: '||MAHALLEADI )||   DECODE( NVL(CADDESADI,'-'), '-' ,'',' CADDE: '||CADDESADI )||   DECODE( NVL(SOKAKADI,'-'), '-' ,'',' SOKAK: '||SOKAKADI )||    ' NO:'||KAPINO||' '||    ' D:'||DAIRENO||' '||  POSTAKODU||  ' ' || RRE1ILCE_ADI || '/' || PRE1IL_ADI) " +
                            "from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E') ADRES " +
                            "from MPI1PAYDAS ";

        if(paydasSorguRequestDTO.getPaydasNo() != null)
            query = baseQuery + " where ID= " + paydasSorguRequestDTO.getPaydasNo();
        else if(paydasSorguRequestDTO.getSorguAdi() != null)
            query = baseQuery + " where SORGUADI LIKE '" + paydasSorguRequestDTO.getSorguAdi() + "%'";
        else if(paydasSorguRequestDTO.getVergiNo() != null)
            query = baseQuery + " where VERGINUMARASI= '" + paydasSorguRequestDTO.getVergiNo() + "'";
        else if(paydasSorguRequestDTO.getTcNo() != null)
            query = baseQuery + " where TCKIMLIKNO= " + paydasSorguRequestDTO.getTcNo();

        return query;
    }



    public PaydasSorguResponseDTO getPaydasInfoByCriteria(PaydasSorguRequestDTO paydasSorguRequestDTO) {
        PaydasSorguResponseDTO paydasSorguResponseDTO;
        String query = getPaydasInfoDynamicQueryContextByCriteria(paydasSorguRequestDTO);

        paydasSorguResponseDTO = paydasIliskileriRepository.getPaydasInfoByCriteria(paydasSorguRequestDTO, query);

        return paydasSorguResponseDTO;
    }

    public PaydasSorguResponseDTO getPaydasDebtInfoByPaydasNo(PaydasSorguRequestDTO paydasSorguRequestDTO) {
        PaydasSorguResponseDTO paydasSorguResponseDTO;

        paydasSorguResponseDTO = paydasIliskileriRepository.getPaydasDebtInfoByPaydasNo(paydasSorguRequestDTO);

        return paydasSorguResponseDTO;
    }

    public PaydasSorguResponseDTO getPaydasAdvertInfoByPaydasNo(PaydasSorguRequestDTO paydasSorguRequestDTO) {
        PaydasSorguResponseDTO paydasSorguResponseDTO;


        paydasSorguResponseDTO = paydasIliskileriRepository.getPaydasAdvertInfoByPaydasNo(paydasSorguRequestDTO);

        return paydasSorguResponseDTO;
    }

    public PaydasSorguResponseDTO getPaydasTahakkukInfoByPaydasNo(PaydasSorguRequestDTO paydasSorguRequestDTO) {
        PaydasSorguResponseDTO paydasSorguResponseDTO;

        paydasSorguResponseDTO = paydasIliskileriRepository.getPaydasTahakkukInfoByPaydasNo(paydasSorguRequestDTO);

        return paydasSorguResponseDTO;
    }
}
