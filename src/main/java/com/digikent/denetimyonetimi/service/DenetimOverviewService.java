package com.digikent.denetimyonetimi.service;

import com.digikent.denetimyonetimi.dao.DenetimOverviewRepository;
import com.digikent.denetimyonetimi.dto.adres.DenetimOlayYeriAdresi;
import com.digikent.denetimyonetimi.dto.adres.DenetimTebligatAdresi;
import com.digikent.denetimyonetimi.dto.denetim.DenetimObjectDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimObjectRequestDTO;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitKararRequest;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Medet on 3/28/2018.
 */

@Service
public class DenetimOverviewService {

    private final Logger LOG = LoggerFactory.getLogger(DenetimOverviewService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    DenetimOverviewRepository denetimOverviewRepository;


    public static String getDenetimObjectAdresAndTebligSqlQuery(DenetimObjectRequestDTO denetimObjectRequestDTO) {
        String sql = "SELECT\n" +
                "ADANO_OLAYYERI,BLOKNO_OLAYYERI,DAIRENOHARF_OLAYYERI,DAIRENOSAYI_OLAYYERI,DRE1BAGBOLUM_OLAYYERI,\n" +
                "DRE1MAHALLE_OLAYYERI,ERE1YAPI_OLAYYERI,IRE1PARSEL_OLAYYERI,KAPINOHARF_OLAYYERI,\n" +
                "KAPINOSAYI_OLAYYERI,PAFTANO_OLAYYERI,PARSELNO_OLAYYERI,PRE1IL_OLAYYERI,RRE1ILCE_OLAYYERI,\n" +
                "SITEADI_OLAYYERI,SRE1SOKAK_OLAYYERI,\n" +
                "(SELECT TANIM  from PRE1IL where ID=BDNTDENETIM.PRE1IL_OLAYYERI AND rownum = 1) AS OLAYIILADI,\n" +
                "(SELECT TANIM  from RRE1ILCE where ID=BDNTDENETIM.RRE1ILCE_OLAYYERI AND rownum = 1) AS OLAYILCEADI,\n" +
                "(SELECT TANIM  from DRE1MAHALLE where ID=BDNTDENETIM.DRE1MAHALLE_OLAYYERI AND rownum = 1) AS OLAYMAHALLEADI,\n" +
                "(SELECT TANIM  from SRE1SOKAK where ID=BDNTDENETIM.SRE1SOKAK_OLAYYERI AND rownum = 1) AS OLAYSOKAKADI,\n" +
                "BLOKNO_TEBLIGAT,DAIRENOHARF_TEBLIGAT,DAIRENOSAYI_TEBLIGAT,DRE1MAHALLE_TEBLIGAT,KAPINOHARF_TEBLIGAT,KAPINOSAYI_TEBLIGAT,\n" +
                "PRE1IL_TEBLIGAT,RRE1ILCE_TEBLIGAT,SITEADI_TEBLIGAT,SRE1SOKAK_TEBLIGAT,\n" +
                "(SELECT TANIM  from PRE1IL where ID=BDNTDENETIM.PRE1IL_TEBLIGAT AND rownum = 1) AS TEBLIGATILADI,\n" +
                "(SELECT TANIM  from RRE1ILCE where ID=BDNTDENETIM.RRE1ILCE_TEBLIGAT AND rownum = 1) AS TEBLIGATILCEADI,\n" +
                "(SELECT TANIM  from DRE1MAHALLE where ID=BDNTDENETIM.DRE1MAHALLE_TEBLIGAT AND rownum = 1) AS TEBLIGATMAHALLEADI,\n" +
                "(SELECT TANIM  from SRE1SOKAK where ID=BDNTDENETIM.SRE1SOKAK_TEBLIGAT AND rownum = 1) AS TEBLIGATSOKAKADI,\n" +
                "TEBLIG_ADI,TEBLIG_SECENEGI,TEBLIG_SOYADI,TEBLIG_TC, TEBLIGIZAHAT\n" +
                "FROM BDNTDENETIM WHERE ID = " + denetimObjectRequestDTO.getDenetimId();


        return sql;
    }

    public static String getDenetimObjectPaydasInfoSqlQuery(DenetimObjectRequestDTO denetimObjectRequestDTO) {
        String sql = "SELECT ID,SORGUADI, TCKIMLIKNO, ADI,SOYADI,UNVAN,VERGINUMARASI,PI1.F_TELEFONPAYDAS(MPI1PAYDAS.ID) as TELEFON,IZAHAT,PAYDASTURU,TABELAADI,KAYITDURUMU,\n" +
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
                "(SELECT PRE1IL_ID  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1) AS PRE1IL_ID,\n" +
                "(SELECT KATSAYI  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1) AS KATSAYI,\n" +
                "(SELECT ADI from HPI1FIRMAYETKILI where MPI1PAYDAS_ID=MPI1PAYDAS.ID AND rownum = 1) AS FIRMAYETKILIADI,\n" +
                "(SELECT SOYADI from HPI1FIRMAYETKILI where MPI1PAYDAS_ID=MPI1PAYDAS.ID AND rownum = 1) AS FIRMAYETKILISOYADI,\n" +
                "(SELECT TCKIMLIKNO from HPI1FIRMAYETKILI where MPI1PAYDAS_ID=MPI1PAYDAS.ID AND rownum = 1) AS FIRMAYETKILITCKIMLIKNO,\n" +
                "NVL((SELECT NVL(KATHARF,'-')  from BPI1ADRES where MPI1PAYDAS_ID=MPI1PAYDAS.ID and MEKTUPGONDERIMADRESIMI='E' AND rownum = 1),'-') AS KATHARF\n" +
                "from MPI1PAYDAS WHERE MPI1PAYDAS.ID = " + denetimObjectRequestDTO.getPaydasId();

        return sql;
    }

    public static String getDenetimObjectKararInfoSqlQuery(DenetimObjectRequestDTO denetimObjectRequestDTO) {
        String sql = "SELECT ID, DENETIMAKSIYONU, VERILENSURE, KAPAMABASLANGICTARIHI, KAPAMABITISTARIHI, CEZAMIKTARI FROM BDNTDENETIMTESPIT WHERE ID = " + denetimObjectRequestDTO.getDenetimTespitId();

        return sql;
    }

    public  static String getDenetimObjectTespitInfoSqlQuery(DenetimObjectRequestDTO denetimObjectRequestDTO) {
        String sql = "SELECT LDNTTESPIT_ID,STRINGVALUE,TEXTVALUE,NUMBERVALUE,DATEVALUE,TUTARI, " +
                "(SELECT SECENEKTURU FROM LDNTTESPIT WHERE LDNTTESPIT.ID = BDNTDENETIMTESPITLINE.LDNTTESPIT_ID) AS SECENEKTURU " +
                " FROM BDNTDENETIMTESPITLINE WHERE ISACTIVE = 'E' AND BDNTDENETIMTESPIT_ID = " + denetimObjectRequestDTO.getDenetimTespitId();

        return sql;
    }

    public static String getDenetimObjectTarafInfoSqlQuery(DenetimObjectRequestDTO denetimObjectRequestDTO) {
        String sql = " SELECT TARAFTURU, ADI, SOYADI, GOREVI FROM BDNTDENETIMTESPITTARAF " +
                " WHERE TARAFTURU LIKE 'BELEDIYE' AND BDNTDENETIM_ID = " + denetimObjectRequestDTO.getDenetimId();

        return sql;
    }

    public DenetimObjectDTO getDenetimObjectByDenetimAndDenetimTespitId(DenetimObjectRequestDTO denetimObjectRequestDTO) {

        return denetimOverviewRepository.getDenetimObjectByDenetimAndDenetimTespitId(denetimObjectRequestDTO);
    }

    public UtilDenetimSaveDTO updateDenetimTebligatAdresiByDenetimId(DenetimTebligatAdresi denetimTebligatAdresi, Long denetimId, HttpServletRequest request) {

        return denetimOverviewRepository.updateDenetimTebligatAdresiByDenetimId(denetimTebligatAdresi, denetimId, request);
    }

    public UtilDenetimSaveDTO updateDenetimOlayYeriAdresiByDenetimId(DenetimOlayYeriAdresi denetimOlayYeriAdresi, Long denetimId, HttpServletRequest request) {

        return denetimOverviewRepository.updateDenetimOlayYeriAdresiByDenetimId(denetimOlayYeriAdresi, denetimId, request);
    }

    public UtilDenetimSaveDTO updateDenetimKararBilgileriByDenetimId(DenetimTespitKararRequest denetimTespitKararRequest, Long denetimTespitId, HttpServletRequest request) {

        return denetimOverviewRepository.updateDenetimKararBilgileriByDenetimId(denetimTespitKararRequest, denetimTespitId, request);
    }
}
