package com.digikent.paydasiliskileri.service;

import com.digikent.paydasiliskileri.dao.PaydasIliskileriRepository;
import com.digikent.paydasiliskileri.dto.PaydasSorguRequestDTO;
import com.digikent.paydasiliskileri.dto.PaydasSorguResponseDTO;
import com.digikent.zabita.dto.paydas.ZabitaPaydasRequestDTO;
import com.digikent.zabita.dto.paydas.ZabitaPaydasResponseDTO;
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

        paydasSorguResponseDTO = paydasIliskileriRepository.getPaydasInfoByCriteria(query);

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

    public ZabitaPaydasResponseDTO getPaydasInfoByZabitaFilter(ZabitaPaydasRequestDTO zabitaPaydasRequestDTO) {
        String query = "";
        ZabitaPaydasResponseDTO zabitaPaydasResponseDTO = null;

        String baseQuery = "select ID,SORGUADI, ADI,SOYADI,UNVAN,VERGINUMARASI,PI1.F_TELEFONPAYDAS(MPI1PAYDAS.ID) as TELEFON,IZAHAT,PAYDASTURU,TABELAADI,KAYITDURUMU, \n" +
                "(SELECT DRE1MAHALLE_ID  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1) AS DRE1MAHALLE_ID,\n" +
                "NVL((SELECT NVL(BINAADI,'-')  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1),'-') AS BINAADI,\n" +
                "NVL((SELECT NVL(BLOKNO,'-')  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1),'-') AS BLOKNO,\n" +
                "NVL((SELECT NVL(KAPINO,'-')  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1),'-') AS KAPINO,\n" +
                "NVL((SELECT NVL(RRE1ILCE_ADI,'-')  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1),'-') AS ILCEADI,\n" +
                "(SELECT KAPINOSAYI  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1) AS KAPINOSAYI,\n" +
                "NVL((SELECT NVL(KAPINOHARF,'-')  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1),'-') AS KAPINOHARF,\n" +
                "(SELECT DAIRENOSAYI  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1) AS DAIRENOSAYI,\n" +
                "NVL((SELECT NVL(DAIRENOHARF,'-')  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1),'-') AS DAIRENOHARF,\n" +
                "(SELECT SRE1SOKAK_ID  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1) AS SRE1SOKAK_ID,\n" +
                "(SELECT RRE1ILCE_ID  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1) AS RRE1ILCE_ID,\n" +
                "(SELECT KATSAYI  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1) AS KATSAYI,\n" +
                "NVL((SELECT NVL(KATHARF,'-')  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1),'-') AS KATHARF \n" +
                "from MPI1PAYDAS  ";

        if(zabitaPaydasRequestDTO.getFilter() != null && !zabitaPaydasRequestDTO.getFilter().isEmpty()) {
            query = baseQuery + " where ROWNUM <= 20 AND (SORGUADI LIKE '%" + zabitaPaydasRequestDTO.getFilter() + "%'" +
                    " OR VERGINUMARASI LIKE '%" + zabitaPaydasRequestDTO.getFilter() + "%'" +
                    " OR TCKIMLIKNO LIKE '%" + zabitaPaydasRequestDTO.getFilter() + "%')";
        }

        zabitaPaydasResponseDTO = paydasIliskileriRepository.getPaydasInformationZabitaByCriteria(query);

        return zabitaPaydasResponseDTO;
    }

}
