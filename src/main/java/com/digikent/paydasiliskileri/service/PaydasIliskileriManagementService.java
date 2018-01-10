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

    public String getPaydasDebtInfoDynamicQueryContextByCriteria(PaydasSorguRequestDTO paydasSorguRequestDTO) {

        String query = new String();
        String baseQuery = "SELECT TAHAKKUKTARIHI ,(SELECT TANIM FROM GIN1GELIRTURU WHERE ID = GIN1GELIRTURU_ID) AS GELIRTURU, BORCTUTARI FROM JIN2TAHAKKUKVIEW, MPI1PAYDAS WHERE  JIN2TAHAKKUKVIEW.MPI1PAYDAS_ID = MPI1PAYDAS.ID " +
                " AND BORCTUTARI > 0 ";

        if(paydasSorguRequestDTO.getPaydasNo() != null)
            query = baseQuery + "AND MPI1PAYDAS.ID= " + paydasSorguRequestDTO.getPaydasNo();
        else if(paydasSorguRequestDTO.getSorguAdi() != null)
            query = baseQuery + " AND MPI1PAYDAS.SORGUADI LIKE '" + paydasSorguRequestDTO.getSorguAdi() + "%'";
        else if(paydasSorguRequestDTO.getVergiNo() != null)
            query = baseQuery + " AND MPI1PAYDAS.VERGINUMARASI= '" + paydasSorguRequestDTO.getVergiNo() + "'";
        else if(paydasSorguRequestDTO.getTcNo() != null)
            query = baseQuery + " AND MPI1PAYDAS.TCKIMLIKNO= " + paydasSorguRequestDTO.getTcNo();

        return query;
    }

    public String getPaydasAdvertInfoDynamicQueryContextByCriteria(PaydasSorguRequestDTO paydasSorguRequestDTO) {

        String query = new String();
        String baseQuery = "SELECT A.KAYITTARIHI,A.FIN7TARIFETURU_ID as TARIFETURU,A.TABELAENI,A.BOY,A.TABELAYUZU,A.ILANADEDI,A.ILANALANI,A.IZAHAT " +
                           " FROM CIN7BILDIRIMEK A,MPI1PAYDAS B where A.ID = B.ID AND B.";


        if(paydasSorguRequestDTO.getPaydasNo() != null)
            query = baseQuery + "ID= " + paydasSorguRequestDTO.getPaydasNo();
        else if(paydasSorguRequestDTO.getSorguAdi() != null)
            query = baseQuery + "SORGUADI LIKE '" + paydasSorguRequestDTO.getSorguAdi() + "%'";
        else if(paydasSorguRequestDTO.getVergiNo() != null)
            query = baseQuery + "VERGINUMARASI= '" + paydasSorguRequestDTO.getVergiNo() + "'";
        else if(paydasSorguRequestDTO.getTcNo() != null)
            query = baseQuery + "TCKIMLIKNO= " + paydasSorguRequestDTO.getTcNo();

        return query;

    }

    public PaydasSorguResponseDTO getPaydasInfoByCriteria(PaydasSorguRequestDTO paydasSorguRequestDTO) {
        PaydasSorguResponseDTO paydasSorguResponseDTO;
        String query = getPaydasInfoDynamicQueryContextByCriteria(paydasSorguRequestDTO);

        paydasSorguResponseDTO = paydasIliskileriRepository.getPaydasInfoByCriteria(paydasSorguRequestDTO, query);

        return paydasSorguResponseDTO;
    }

    public PaydasSorguResponseDTO getPaydasDebtInfoByCriteria(PaydasSorguResponseDTO paydasSorguResponseDTO,PaydasSorguRequestDTO paydasSorguRequestDTO) {


        String query = getPaydasDebtInfoDynamicQueryContextByCriteria(paydasSorguRequestDTO);
        paydasSorguResponseDTO = paydasIliskileriRepository.getPaydasDebtInfoByCriteria(paydasSorguResponseDTO,paydasSorguRequestDTO, query);

        return paydasSorguResponseDTO;
    }

    public PaydasSorguResponseDTO getPaydasAdvertInfoByCriteria(PaydasSorguResponseDTO paydasSorguResponseDTO, PaydasSorguRequestDTO paydasSorguRequestDTO) {

        String query = getPaydasAdvertInfoDynamicQueryContextByCriteria(paydasSorguRequestDTO);
        paydasSorguResponseDTO = paydasIliskileriRepository.getPaydasAdvertInfoByCriteria(paydasSorguResponseDTO, paydasSorguRequestDTO, query);

        return paydasSorguResponseDTO;
    }
}
