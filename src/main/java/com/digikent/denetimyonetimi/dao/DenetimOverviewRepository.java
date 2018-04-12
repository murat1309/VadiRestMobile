package com.digikent.denetimyonetimi.dao;

import com.digikent.denetimyonetimi.dto.adres.DenetimOlayYeriAdresi;
import com.digikent.denetimyonetimi.dto.adres.DenetimTebligatAdresi;
import com.digikent.denetimyonetimi.dto.denetim.DenetimObjectDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimObjectRequestDTO;
import com.digikent.denetimyonetimi.dto.denetim.DenetimTebligDTO;
import com.digikent.denetimyonetimi.dto.denetimtespit.DenetimTespitKararRequest;
import com.digikent.denetimyonetimi.dto.paydas.DenetimPaydasDTO;
import com.digikent.denetimyonetimi.dto.taraf.DenetimGoruntuleTarafDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitCevapDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitSaveDTO;
import com.digikent.denetimyonetimi.dto.tespit.TespitlerRequest;
import com.digikent.denetimyonetimi.dto.util.UtilDenetimSaveDTO;
import com.digikent.denetimyonetimi.entity.BDNTDenetim;
import com.digikent.denetimyonetimi.entity.BDNTDenetimTespit;
import com.digikent.denetimyonetimi.enums.DenetimTespitKararAksiyon;
import com.digikent.denetimyonetimi.enums.TebligSecenegi;
import com.digikent.mesajlasma.dto.ErrorDTO;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.digikent.denetimyonetimi.service.DenetimOverviewService.*;


/**
 * Created by Medet on 3/28/2018.
 */
@Repository
public class DenetimOverviewRepository {

    private final Logger LOG = LoggerFactory.getLogger(DenetimRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    public DenetimOlayYeriAdresi setDenetimObjectOlayYeriAdresi(List list) {

        DenetimOlayYeriAdresi denetimOlayYeriAdresi = new DenetimOlayYeriAdresi();


        Object o = list.get(0);
        Map map = (Map) o;

        BigDecimal rre1IlceOlayYeri = (BigDecimal) map.get("RRE1ILCE_OLAYYERI");
        BigDecimal dre1MahalleOlayYeri = (BigDecimal) map.get("DRE1MAHALLE_OLAYYERI");
        BigDecimal sre1SokakOlayYeri = (BigDecimal) map.get("SRE1SOKAK_OLAYYERI");
        BigDecimal ere1YapiOlayYeri = (BigDecimal) map.get("ERE1YAPI_OLAYYERI");
        BigDecimal ire1ParselOlayYeri = (BigDecimal) map.get("PARSELNO_OLAYYERI");
        BigDecimal dre1BagBolumOlayYeri = (BigDecimal) map.get("DRE1BAGBOLUM_OLAYYERI");
        String paftaOlayYeri = (String) map.get("PAFTANO_OLAYYERI");
        String adaNoOlayYeri = (String) map.get("ADANO_OLAYYERI");
        String parselOlayYeri = (String) map.get("IRE1PARSEL_OLAYYERI");
        String siteAdiOlayYeri = (String) map.get("SITEADI_OLAYYERI");
        String blokNoOlayYeri = (String) map.get("BLOKNO_OLAYYERI");
        String kapiNoHarfOlayYeri = (String) map.get("KAPINOHARF_OLAYYERI");
        String daireNoHarfOlayYeri = (String) map.get("DAIRENOHARF_OLAYYERI");
        BigDecimal kapiNoSayiOlayYeri = (BigDecimal) map.get("KAPINOSAYI_OLAYYERI");
        BigDecimal daireNoSayiOlayYeri = (BigDecimal) map.get("DAIRENOSAYI_OLAYYERI");
        BigDecimal pre1IlOlayYeri = (BigDecimal) map.get("PRE1IL_OLAYYERI");
        String pre1IlOlayYeriAdi = (String) map.get("OLAYIILADI");
        String rre1IlceOlayYeriAdi = (String) map.get("OLAYILCEADI");
        String dre1MahalleOlayYeriAdi = (String) map.get("OLAYMAHALLEADI");
        String sre1SokakOlayYeriAdi = (String) map.get("OLAYSOKAKADI");

        if(rre1IlceOlayYeri != null)
            denetimOlayYeriAdresi.setRre1IlceOlayYeri(rre1IlceOlayYeri.longValue());
        if(dre1MahalleOlayYeri != null)
            denetimOlayYeriAdresi.setDre1MahalleOlayYeri(dre1MahalleOlayYeri.longValue());
        if(sre1SokakOlayYeri != null)
            denetimOlayYeriAdresi.setSre1SokakOlayYeri(sre1SokakOlayYeri.longValue());
        if(ere1YapiOlayYeri != null)
            denetimOlayYeriAdresi.setEre1YapiOlayYeri(ere1YapiOlayYeri.longValue());
        if(ire1ParselOlayYeri != null)
            denetimOlayYeriAdresi.setIre1ParselOlayYeri(ire1ParselOlayYeri.longValue());
        if(dre1BagBolumOlayYeri != null)
            denetimOlayYeriAdresi.setDre1BagBolumOlayYeri(dre1BagBolumOlayYeri.longValue());
        if (paftaOlayYeri != null) {
            denetimOlayYeriAdresi.setPaftaOlayYeri(paftaOlayYeri);
        }
        if (adaNoOlayYeri != null) {
            denetimOlayYeriAdresi.setAdaNoOlayYeri(adaNoOlayYeri);
        }
        if (siteAdiOlayYeri != null) {
            denetimOlayYeriAdresi.setSiteAdiOlayYeri(siteAdiOlayYeri);
        }
        if (blokNoOlayYeri != null) {
            denetimOlayYeriAdresi.setBlokNoOlayYeri(blokNoOlayYeri);
        }
        if (kapiNoHarfOlayYeri != null) {
            denetimOlayYeriAdresi.setKapiNoHarfOlayYeri(kapiNoHarfOlayYeri);
        }
        if (daireNoHarfOlayYeri != null) {
            denetimOlayYeriAdresi.setDaireNoHarfOlayYeri(daireNoHarfOlayYeri);
        }
        if (kapiNoSayiOlayYeri != null) {
            denetimOlayYeriAdresi.setKapiNoSayiOlayYeri(kapiNoSayiOlayYeri.longValue());
        }
        if (daireNoSayiOlayYeri != null) {
            denetimOlayYeriAdresi.setDaireNoSayiOlayYeri(daireNoSayiOlayYeri.longValue());
        }
        if (pre1IlOlayYeri != null) {
            denetimOlayYeriAdresi.setPre1IlOlayYeri(pre1IlOlayYeri.longValue());
        }
        if (pre1IlOlayYeriAdi != null) {
            denetimOlayYeriAdresi.setPre1IlOlayYeriAdi(pre1IlOlayYeriAdi);
        }
        if (rre1IlceOlayYeriAdi != null) {
            denetimOlayYeriAdresi.setRre1IlceOlayYeriAdi(rre1IlceOlayYeriAdi);
        }
        if (dre1MahalleOlayYeriAdi != null) {
            denetimOlayYeriAdresi.setDre1MahalleOlayYeriAdi(dre1MahalleOlayYeriAdi);
        }
        if (sre1SokakOlayYeriAdi != null) {
            denetimOlayYeriAdresi.setSre1SokakOlayYeriAdi(sre1SokakOlayYeriAdi);
        }



        return denetimOlayYeriAdresi;
    }


    public DenetimTebligatAdresi setDenetimObjectTebligatAdresi(List list) {
        DenetimTebligatAdresi denetimTebligatAdresi = new DenetimTebligatAdresi();

        Object o = list.get(0);
        Map map = (Map) o;

        String siteAdiTebligat = (String) map.get("SITEADI_TEBLIGAT");
        String blokNotebligat = (String) map.get("BLOKNO_TEBLIGAT");
        String kapiNoHarfTebligat = (String) map.get("KAPINOHARF_TEBLIGAT");
        BigDecimal kapiNoSayiTebligat = (BigDecimal) map.get("KAPINOSAYI_TEBLIGAT");
        String daireNoHarfTebligat = (String) map.get("DAIRENOHARF_TEBLIGAT");
        BigDecimal daireNoSayiTebligat = (BigDecimal) map.get("DAIRENOSAYI_TEBLIGAT");
        BigDecimal rre1ilceTebligat = (BigDecimal) map.get("RRE1ILCE_TEBLIGAT");
        BigDecimal dre1MahalleTebligat = (BigDecimal) map.get("DRE1MAHALLE_TEBLIGAT");
        BigDecimal sre1SokakTebligat = (BigDecimal) map.get("SRE1SOKAK_TEBLIGAT");
        BigDecimal pre1IlTebligat = (BigDecimal) map.get("PRE1IL_TEBLIGAT");
        String rre1ilceTebligatAdi = (String) map.get("TEBLIGATILCEADI");
        String dre1MahalleTebligatAdi = (String) map.get("TEBLIGATMAHALLEADI");
        String sre1SokakTebligatAdi = (String) map.get("TEBLIGATSOKAKADI");
        String pre1IlTebligatAdi = (String) map.get("TEBLIGATILADI");

        if (siteAdiTebligat != null)
            denetimTebligatAdresi.setSiteAdiTebligat(siteAdiTebligat);
        if (blokNotebligat != null)
            denetimTebligatAdresi.setBlokNotebligat(blokNotebligat);
        if (kapiNoHarfTebligat != null)
            denetimTebligatAdresi.setKapiNoHarfTebligat(kapiNoHarfTebligat);
        if (kapiNoSayiTebligat != null)
            denetimTebligatAdresi.setKapiNoSayiTebligat(kapiNoSayiTebligat.longValue());
        if (daireNoHarfTebligat != null)
            denetimTebligatAdresi.setDaireNoHarfTebligat(daireNoHarfTebligat);
        if (daireNoSayiTebligat != null)
            denetimTebligatAdresi.setDaireNoSayiTebligat(daireNoSayiTebligat.longValue());
        if (rre1ilceTebligat != null)
            denetimTebligatAdresi.setRre1ilceTebligat(rre1ilceTebligat.longValue());
        if (dre1MahalleTebligat != null)
            denetimTebligatAdresi.setDre1MahalleTebligat(dre1MahalleTebligat.longValue());
        if (sre1SokakTebligat != null)
            denetimTebligatAdresi.setSre1SokakTebligat(sre1SokakTebligat.longValue());
        if (pre1IlTebligat != null)
            denetimTebligatAdresi.setPre1IlTebligat(pre1IlTebligat.longValue());
        if (rre1ilceTebligatAdi != null)
            denetimTebligatAdresi.setRre1ilceTebligatAdi(rre1ilceTebligatAdi);
        if (dre1MahalleTebligatAdi != null)
            denetimTebligatAdresi.setDre1MahalleTebligatAdi(dre1MahalleTebligatAdi);
        if (sre1SokakTebligatAdi != null)
            denetimTebligatAdresi.setSre1SokakTebligatAdi(sre1SokakTebligatAdi);
        if (pre1IlTebligatAdi != null)
            denetimTebligatAdresi.setPre1IlTebligatAdi(pre1IlTebligatAdi);


        return denetimTebligatAdresi;
    }

    public DenetimTebligDTO setDenetimObjectTebligInfo(List list) {

        DenetimTebligDTO denetimTebligDTO = new DenetimTebligDTO();

        Object o = list.get(0);
        Map map = (Map) o;

        String tebligSecenegi = (String) map.get("TEBLIG_SECENEGI");
        String tebligAdi = (String) map.get("TEBLIG_ADI");
        String tebligSoyadi = (String) map.get("TEBLIG_SOYADI");
        BigDecimal tebligTC = (BigDecimal) map.get("TEBLIG_TC");
        String tebligIzahat = (String) map.get("TEBLIGIZAHAT");

        if (tebligSecenegi != null)
            denetimTebligDTO.setTebligSecenegi(TebligSecenegi.valueOf(tebligSecenegi));
        if (tebligAdi != null)
            denetimTebligDTO.setTebligAdi(tebligAdi);
        if (tebligSoyadi != null)
            denetimTebligDTO.setTebligSoyadi(tebligSoyadi);
        if (tebligTC != null)
            denetimTebligDTO.setTebligTC(tebligTC.longValue());
        if(tebligIzahat != null)
            denetimTebligDTO.setTebligIzahat(tebligIzahat);

        return denetimTebligDTO;

    }

    public DenetimPaydasDTO setDenetimObjectPaydasInfo(List list) {

        DenetimPaydasDTO denetimPaydasDTO = new DenetimPaydasDTO();

        Object o = list.get(0);
        Map map = (Map) o;

        BigDecimal paydasNo = (BigDecimal) map.get("ID");
        String adi = (String) map.get("ADI");
        String soyAdi = (String ) map.get("SOYADI");
        String unvan = (String) map.get("UNVAN");
        String vergiNo = (String) map.get("VERGINUMARASI");
        String telefon = (String) map.get("TELEFON");
        String paydasTuru = (String) map.get("PAYDASTURU");
        String tabelaAdi = (String) map.get("TABELAADI");
        String izahat = (String) map.get("IZAHAT");
        String kayitDurumu = (String) map.get("KAYITDURUMU");
        String binaAdi = (String) map.get("BINAADI");
        String kapiNo = (String) map.get("KAPINO");
        String ilceAdi = (String) map.get("ILCEADI");
        BigDecimal kapiNoSayi = (BigDecimal) map.get("KAPINOSAYI");
        String kapiNoHarf = (String) map.get("KAPINOHARF");
        String daireNoHarf = (String) map.get("DAIRENOHARF");
        BigDecimal daireNoSayi = (BigDecimal) map.get("DAIRENOSAYI");
        BigDecimal katSayi = (BigDecimal) map.get("KATSAYI");
        String katHarf = (String) map.get("KATHARF");
        String blokNo = (String) map.get("BLOKNO");
        BigDecimal dre1MahalleId = (BigDecimal) map.get("DRE1MAHALLE_ID");
        BigDecimal sre1SokakId = (BigDecimal) map.get("SRE1SOKAK_ID");
        BigDecimal rre1IlceId = (BigDecimal) map.get("RRE1ILCE_ID");
        BigDecimal pre1IlId = (BigDecimal) map.get("PRE1IL_ID");
        BigDecimal tcKimlikNo = (BigDecimal) map.get("TCKIMLIKNO");
        Long telefonCep = null;
        Long telefonIs = null;
        Long ticaretSicilNo = null;
        String vergiDairesi = null;
        String siteAdi = null;
        String firmaYetkiliAdi = (String) map.get("FIRMAYETKILIADI");
        String firmaYetkiliSoyadi = (String) map.get("FIRMAYETKILISOYADI");
        String firmaYetkiliTC = (String) map.get("FIRMAYETKILITCKIMLIKNO");

        if (paydasNo != null)
            denetimPaydasDTO.setPaydasNo(paydasNo.longValue());
        if (adi != null)
            denetimPaydasDTO.setAdi(adi);
        if (soyAdi != null)
            denetimPaydasDTO.setSoyAdi(soyAdi);
        if (unvan != null)
            denetimPaydasDTO.setUnvan(unvan);
        if (vergiNo != null)
            denetimPaydasDTO.setVergiNo(vergiNo);
        if (telefon != null)
            denetimPaydasDTO.setTelefon(telefon);
        if (paydasTuru != null)
            denetimPaydasDTO.setPaydasTuru(paydasTuru);
        if (tabelaAdi != null)
            denetimPaydasDTO.setTabelaAdi(tabelaAdi);
        if (izahat != null)
            denetimPaydasDTO.setIzahat(izahat);
        if (kayitDurumu != null)
            denetimPaydasDTO.setKayitDurumu(kayitDurumu);
        if (binaAdi != null)
            denetimPaydasDTO.setBinaAdi(binaAdi);
        if (kapiNo != null)
            denetimPaydasDTO.setKapiNo(kapiNo);
        if (ilceAdi != null)
            denetimPaydasDTO.setIlceAdi(ilceAdi);
        if (kapiNoSayi != null)
            denetimPaydasDTO.setKapiNoSayi(kapiNoSayi.longValue());
        if (kapiNoHarf != null)
            denetimPaydasDTO.setKapiNoHarf(kapiNoHarf);
        if (daireNoHarf != null)
            denetimPaydasDTO.setDaireNoHarf(daireNoHarf);
        if (daireNoSayi != null)
            denetimPaydasDTO.setDaireNoSayi(daireNoSayi.longValue());
        if (katSayi != null)
            denetimPaydasDTO.setKatSayi(katSayi.longValue());
        if (katHarf != null)
            denetimPaydasDTO.setKatHarf(katHarf);
        if (blokNo != null)
            denetimPaydasDTO.setBlokNo(blokNo);
        if (dre1MahalleId != null)
            denetimPaydasDTO.setDre1MahalleId(dre1MahalleId.longValue());
        if (sre1SokakId != null)
            denetimPaydasDTO.setSre1SokakId(sre1SokakId.longValue());
        if (rre1IlceId != null)
            denetimPaydasDTO.setRre1IlceId(rre1IlceId.longValue());
        if (pre1IlId != null)
            denetimPaydasDTO.setPre1IlId(pre1IlId.longValue());
        if (tcKimlikNo != null && tcKimlikNo.longValue() != 0)
            denetimPaydasDTO.setTcKimlikNo(tcKimlikNo.longValue());
        if (telefonCep != null)
            denetimPaydasDTO.setTelefonCep(telefonCep);
        if (telefonIs != null)
            denetimPaydasDTO.setTelefonIs(telefonIs);
        if (ticaretSicilNo != null)
            denetimPaydasDTO.setTicaretSicilNo(ticaretSicilNo);
        if (vergiDairesi != null)
            denetimPaydasDTO.setVergiDairesi(vergiDairesi);
        if (siteAdi != null)
            denetimPaydasDTO.setSiteAdi(siteAdi);
        if (firmaYetkiliAdi != null)
            denetimPaydasDTO.setFirmaYetkiliAdi(firmaYetkiliAdi);
        if (firmaYetkiliSoyadi != null)
            denetimPaydasDTO.setFirmaYetkiliSoyadi(firmaYetkiliSoyadi);
        if (firmaYetkiliTC != null)
            denetimPaydasDTO.setFirmaYetkiliTC(Long.parseLong(firmaYetkiliTC, 10));


        return denetimPaydasDTO;

    }

    public DenetimTespitKararRequest setDenetimObjectKararInfo(List list) {

        DenetimTespitKararRequest denetimTespitKararRequest = new DenetimTespitKararRequest();

        Object o = list.get(0);
        Map map = (Map) o;

        BigDecimal denetimTespitId = (BigDecimal) map.get("ID");
        String aksiyon = (String) map.get("DENETIMAKSIYONU");
        BigDecimal cezaMiktari = (BigDecimal) map.get("CEZAMIKTARI");
        Date kapamaBaslangicTarihi = (Date) map.get("KAPAMABASLANGICTARIHI");
        Date kapamaBitisTarihi = (Date) map.get("KAPAMABITISTARIHI");
        BigDecimal ekSure = (BigDecimal) map.get("VERILENSURE");

        if(denetimTespitId != null)
            denetimTespitKararRequest.setDenetimTespitId(denetimTespitId.longValue());
        if(aksiyon != null)
            denetimTespitKararRequest.setAksiyon(DenetimTespitKararAksiyon.valueOf(aksiyon));
        if(cezaMiktari != null)
            denetimTespitKararRequest.setCezaMiktari(cezaMiktari.longValue());
        if(kapamaBaslangicTarihi != null)
            denetimTespitKararRequest.setKapamaBaslangicTarihi(kapamaBaslangicTarihi);
        if(kapamaBitisTarihi != null)
            denetimTespitKararRequest.setKapamaBitisTarihi(kapamaBitisTarihi);
        if(ekSure != null)
            denetimTespitKararRequest.setEkSure(ekSure.longValue());

        return denetimTespitKararRequest;
    }


    public TespitlerRequest setDenetimObjectTespitInfo(List list, DenetimObjectRequestDTO denetimObjectRequestDTO) {
        TespitlerRequest tespitlerRequest = new TespitlerRequest();
        List<TespitSaveDTO> tespitSaveDTOList = new ArrayList<>();
        Map map;

        for(Object o : list) {

            TespitSaveDTO tespitSaveDTO = new TespitSaveDTO();
            TespitCevapDTO tespitCevapDTO = new TespitCevapDTO();
            map = (Map) o;

            BigDecimal tespitId = (BigDecimal) map.get("LDNTTESPIT_ID");
            Long ekSure = null;
            BigDecimal tutari = (BigDecimal) map.get("TUTARI");
            String secenekTuru = (String) map.get("SECENEKTURU");
            String stringValue = (String ) map.get("STRINGVALUE");
            String textValue = clob2Str((Clob) map.get("TEXTVALUE"));
            BigDecimal numberValue = (BigDecimal) map.get("NUMBERVALUE");
            Date dateValue = (Date) map.get("DATEVALUE");

            if(tutari != null) {
                tespitSaveDTO.setTutari(tutari.longValue());
            }
            if(tespitId != null)
                tespitSaveDTO.setTespitId(tespitId.longValue());
            if(ekSure != null)
                tespitSaveDTO.setEkSure(ekSure);
            if(secenekTuru != null)
                tespitSaveDTO.setSecenekTuru(secenekTuru);
            if(stringValue != null)
                tespitCevapDTO.setStringValue(stringValue);
            if(textValue != null)
                tespitCevapDTO.setTextValue(textValue);
            if(numberValue != null)
                tespitCevapDTO.setNumberValue(numberValue.longValue());
            if(dateValue != null)
                tespitCevapDTO.setDateValue(new SimpleDateFormat("dd-MM-yyyy").format(dateValue));
            if(tutari != null) {
                tespitSaveDTO.setCezaSelected(true);
            } else {
                tespitSaveDTO.setCezaSelected(false);
            }

            tespitSaveDTO.setTespitCevap(tespitCevapDTO);
            tespitSaveDTOList.add(tespitSaveDTO);
        }

        tespitlerRequest.setDenetimTespitId(denetimObjectRequestDTO.getDenetimTespitId());
        tespitlerRequest.setTespitSaveDTOList(tespitSaveDTOList);
        tespitlerRequest.setSave(null);

        return tespitlerRequest;
    }

    public BDNTDenetim setBDNTDenetimTebligatAdresi(DenetimTebligatAdresi denetimTebligatAdresi, BDNTDenetim bdntDenetim) {

        bdntDenetim.setSiteAdiTebligat(denetimTebligatAdresi.getSiteAdiTebligat());
        bdntDenetim.setBlokNotebligat(denetimTebligatAdresi.getBlokNotebligat());
        bdntDenetim.setKapiHarfNoTebligat(denetimTebligatAdresi.getKapiNoHarfTebligat());
        bdntDenetim.setKapiNoSayiTebligat(denetimTebligatAdresi.getKapiNoSayiTebligat());
        bdntDenetim.setDaireNoHarfTebligat(denetimTebligatAdresi.getDaireNoHarfTebligat());
        bdntDenetim.setDaireNoSayiTebligat(denetimTebligatAdresi.getDaireNoSayiTebligat());
        bdntDenetim.setRre1ilceTebligat(denetimTebligatAdresi.getRre1ilceTebligat());
        bdntDenetim.setDre1MahalleTebligat(denetimTebligatAdresi.getDre1MahalleTebligat());
        bdntDenetim.setSre1SokakTebligat(denetimTebligatAdresi.getSre1SokakTebligat());
        bdntDenetim.setPre1IlTebligat(denetimTebligatAdresi.getPre1IlTebligat());

        return bdntDenetim;
    }

    public BDNTDenetim setBDNTDenetimOlayAdresi(DenetimOlayYeriAdresi denetimOlayYeriAdresi, BDNTDenetim bdntDenetim) {

        bdntDenetim.setRre1IlceOlayYeri(denetimOlayYeriAdresi.getRre1IlceOlayYeri());
        bdntDenetim.setDre1MahalleOlayYeri(denetimOlayYeriAdresi.getDre1MahalleOlayYeri());
        bdntDenetim.setSre1SokakOlayYeri(denetimOlayYeriAdresi.getSre1SokakOlayYeri());
        bdntDenetim.setIre1ParselOlayYeri(denetimOlayYeriAdresi.getIre1ParselOlayYeri());
        bdntDenetim.setEre1YapiOlayYeri(denetimOlayYeriAdresi.getEre1YapiOlayYeri());
        bdntDenetim.setDre1BagBolumOlayYeri(denetimOlayYeriAdresi.getDre1BagBolumOlayYeri());
        bdntDenetim.setPaftaOlayYeri(denetimOlayYeriAdresi.getPaftaOlayYeri());
        bdntDenetim.setAdaNoOlayYeri(denetimOlayYeriAdresi.getAdaNoOlayYeri());
        bdntDenetim.setParselOlayYeri(denetimOlayYeriAdresi.getParselOlayYeri());
        bdntDenetim.setSiteAdiOlayYeri(denetimOlayYeriAdresi.getSiteAdiOlayYeri());
        bdntDenetim.setBlokNoOlayYeri(denetimOlayYeriAdresi.getBlokNoOlayYeri());
        bdntDenetim.setKapiNoHarfOlayYeri(denetimOlayYeriAdresi.getKapiNoHarfOlayYeri());
        bdntDenetim.setDaireNoHarfOlayYeri(denetimOlayYeriAdresi.getDaireNoHarfOlayYeri());
        bdntDenetim.setKapiNoSayiOlayYeri(denetimOlayYeriAdresi.getKapiNoSayiOlayYeri());
        bdntDenetim.setDaireNoSayiOlayYeri(denetimOlayYeriAdresi.getDaireNoSayiOlayYeri());
        bdntDenetim.setPre1IlOlayYeri(denetimOlayYeriAdresi.getPre1IlOlayYeri());

        return bdntDenetim;
    }

    private String clob2Str(Clob clob) {
        try {
            if (clob == null)
                return null;
            else if (((int) clob.length()) > 0) {
                return clob.getSubString(1, (int) clob.length());
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<DenetimGoruntuleTarafDTO> setDenetimObjectTarafInfo(List list) {
        List<DenetimGoruntuleTarafDTO> denetimGoruntuleTarafDTOList = new ArrayList<>();
        Map map;

        for(Object o : list) {
            map = (Map) o;
            DenetimGoruntuleTarafDTO denetimGoruntuleTarafDTO = new DenetimGoruntuleTarafDTO();

            String personelAdi = (String) map.get("ADI");
            String personelSoyadi = (String) map.get("SOYADI");
            String personelGorev = (String) map.get("GOREVI");
            String tarafTuru = (String) map.get("TARAFTURU");

            if(personelAdi != null)
                denetimGoruntuleTarafDTO.setPersonelAdi(personelAdi);
            if(personelSoyadi != null)
                denetimGoruntuleTarafDTO.setPersonelSoyadi(personelSoyadi);
            if(personelGorev != null)
                denetimGoruntuleTarafDTO.setPersonelGorev(personelGorev);
            if(tarafTuru != null)
                denetimGoruntuleTarafDTO.setTarafTuru(tarafTuru);

            denetimGoruntuleTarafDTOList.add(denetimGoruntuleTarafDTO);
        }

        return denetimGoruntuleTarafDTOList;
    }

    public BDNTDenetimTespit setBDNTDenetimTespitKararBilgisi(DenetimTespitKararRequest denetimTespitKararRequest, BDNTDenetimTespit bdntDenetimTespit) {

        bdntDenetimTespit.setDenetimAksiyonu(denetimTespitKararRequest.getAksiyon().toString());
        bdntDenetimTespit.setCezaMiktari(denetimTespitKararRequest.getCezaMiktari());
        bdntDenetimTespit.setKapamaBitisTarihi(denetimTespitKararRequest.getKapamaBitisTarihi());
        bdntDenetimTespit.setKapamaBaslangicTarihi(denetimTespitKararRequest.getKapamaBaslangicTarihi());
        bdntDenetimTespit.setVerilenSure(denetimTespitKararRequest.getEkSure());

        return bdntDenetimTespit;
    }

    public List getDenetimObjectBDNTDENETIMTableContents(DenetimObjectRequestDTO denetimObjectRequestDTO) {

        List list = new ArrayList<>();
        String sql = getDenetimObjectAdresAndTebligSqlQuery(denetimObjectRequestDTO);

        try {
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            return list;

        } catch (Exception e) {
            LOG.debug("Denetim Objesi Adres ve Teblig Bilgileri Cekilirken Bir Hata Olustu. Hata mesaji = " + e.getMessage());
            return list;
        }

    }

    public List getDenetimObjectMPI1PAYDASTableContents(DenetimObjectRequestDTO denetimObjectRequestDTO) {

        List list = new ArrayList<>();
        String sql = getDenetimObjectPaydasInfoSqlQuery(denetimObjectRequestDTO);

        try {
            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            return list;
        } catch (Exception e) {
            LOG.debug("Denetim Objesi Paydas Info Cekilirken Bir Hata Olustu. hata mesaji = " + e.getMessage());
            return list;
        }
    }

    public List getDenetimObjectBDNTDENETIMTESPITTableContents(DenetimObjectRequestDTO denetimObjectRequestDTO) {
        List list = new ArrayList<>();
        String sql = getDenetimObjectKararInfoSqlQuery(denetimObjectRequestDTO);

        try {

            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            return list;
        } catch (Exception e) {
            LOG.debug("Denetim Objesi Karar Info Cekilirken Bir Hata Olustu. Hata mesaji = " + e.getMessage());
            return list;
        }
    }

    public List getDenetimObjectBDNTDENETIMTESPITLINETableContents(DenetimObjectRequestDTO denetimObjectRequestDTO) {
        List list = new ArrayList<>();
        String sql = getDenetimObjectTespitInfoSqlQuery(denetimObjectRequestDTO);

        try {

            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

            return list;

        } catch (Exception e){
            LOG.debug("Denetim Objesi Tespit Info Cekilirken Bir Hata Olustu. Hata Mesaj = " + e.getMessage());
            return  list;
        }
    }

    public List getDenetimObjectBDNTDENETIMTESPITTARAFTableContents(DenetimObjectRequestDTO denetimObjectRequestDTO) {
        List list = new ArrayList();
        String sql = getDenetimObjectTarafInfoSqlQuery(denetimObjectRequestDTO);

        try {

            Session session = sessionFactory.withOptions().interceptor(null).openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            list = query.list();

        } catch (Exception e) {
            LOG.debug("\"Denetim Objesi Karar Info Cekilirken Bir Hata Olustu. Hata Mesaj = " + e.getMessage());
        }

        return list;

    }


    public DenetimObjectDTO getDenetimObjectByDenetimAndDenetimTespitId(DenetimObjectRequestDTO denetimObjectRequestDTO) {

        DenetimObjectDTO denetimObjectDTO = new DenetimObjectDTO();

        DenetimOlayYeriAdresi denetimOlayYeriAdresi = new DenetimOlayYeriAdresi();
        DenetimTebligatAdresi denetimTebligatAdresi = new DenetimTebligatAdresi();
        DenetimTebligDTO denetimTebligDTO = new DenetimTebligDTO();
        DenetimPaydasDTO denetimPaydasDTO = new DenetimPaydasDTO();
        DenetimTespitKararRequest denetimTespitKararDTO = new DenetimTespitKararRequest();
        TespitlerRequest tespitlerRequest = new TespitlerRequest();
        List<DenetimGoruntuleTarafDTO> denetimGoruntuleTarafDTOList = new ArrayList<>();

        List listDenetimInfo = getDenetimObjectBDNTDENETIMTableContents(denetimObjectRequestDTO);
        if(!listDenetimInfo.isEmpty()) {
            denetimOlayYeriAdresi = setDenetimObjectOlayYeriAdresi(listDenetimInfo);
            denetimTebligatAdresi = setDenetimObjectTebligatAdresi(listDenetimInfo);
            denetimTebligDTO = setDenetimObjectTebligInfo(listDenetimInfo);
        }

        List listPaydasInfo = getDenetimObjectMPI1PAYDASTableContents(denetimObjectRequestDTO);
        if(!listPaydasInfo.isEmpty()) {
            denetimPaydasDTO = setDenetimObjectPaydasInfo(listPaydasInfo);
        }

        List listKararInfo = getDenetimObjectBDNTDENETIMTESPITTableContents(denetimObjectRequestDTO);
        if(!listKararInfo.isEmpty()) {
            denetimTespitKararDTO = setDenetimObjectKararInfo(listKararInfo);
        }

        List listTespitAnswers = getDenetimObjectBDNTDENETIMTESPITLINETableContents(denetimObjectRequestDTO);
        if(!listTespitAnswers.isEmpty()) {
            tespitlerRequest = setDenetimObjectTespitInfo(listTespitAnswers, denetimObjectRequestDTO);
        }

        List listTarafInfo = getDenetimObjectBDNTDENETIMTESPITTARAFTableContents(denetimObjectRequestDTO);
        if(!listTarafInfo.isEmpty()) {
            denetimGoruntuleTarafDTOList = setDenetimObjectTarafInfo(listTarafInfo);
        }

        denetimObjectDTO.setDenetimOlayYeriAdresi(denetimOlayYeriAdresi);
        denetimObjectDTO.setDenetimTebligatAdresi(denetimTebligatAdresi);
        denetimObjectDTO.setDenetimTebligDTO(denetimTebligDTO);
        denetimObjectDTO.setDenetimPaydasDTO(denetimPaydasDTO);
        denetimObjectDTO.setDenetimTespitKararDTO(denetimTespitKararDTO);
        denetimObjectDTO.setTespitAnswersResponseData(tespitlerRequest);
        denetimObjectDTO.setDenetimGoruntuleTarafDTOList(denetimGoruntuleTarafDTOList);
        return denetimObjectDTO;
    }

    public UtilDenetimSaveDTO updateDenetimTebligatAdresiByDenetimId(DenetimTebligatAdresi denetimTebligatAdresi, Long denetimId) {

        UtilDenetimSaveDTO utilDenetimSaveDTO;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        BDNTDenetim bdntDenetim = null;

        try {
            Object o = session.get(BDNTDenetim.class,denetimId);
            bdntDenetim = (BDNTDenetim) o;
            bdntDenetim = setBDNTDenetimTebligatAdresi(denetimTebligatAdresi, bdntDenetim);
            session.update(bdntDenetim);
            tx.commit();
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,denetimId);

        } catch (Exception e) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError(true);
            errorDTO.setErrorMessage("Tebligat adresi guncellenirken bir hata ile karsilasildi");

            LOG.debug("Denetim duzenleme tebligat adresi guncellenirken bir hata ile karsilasildi : " + e.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false,errorDTO,denetimId);
        }

        return utilDenetimSaveDTO;
    }

    public UtilDenetimSaveDTO updateDenetimOlayYeriAdresiByDenetimId(DenetimOlayYeriAdresi denetimOlayYeriAdresi, Long denetimId) {

        UtilDenetimSaveDTO utilDenetimSaveDTO;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        BDNTDenetim bdntDenetim = null;

        try {
            Object o = session.get(BDNTDenetim.class,denetimId);
            bdntDenetim = (BDNTDenetim) o;
            bdntDenetim = setBDNTDenetimOlayAdresi(denetimOlayYeriAdresi, bdntDenetim);
            session.update(bdntDenetim);
            tx.commit();
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,denetimId);

        } catch (Exception e) {

            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError(true);
            errorDTO.setErrorMessage("Tebligat adresi guncellenirken bir hata ile karsilasildi");

            LOG.debug("Denetim duzenleme olay adresi guncellenirken bir hata ile karsilasildi : " + e.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false,errorDTO,denetimId);
        }

        return utilDenetimSaveDTO;
    }

    public UtilDenetimSaveDTO updateDenetimKararBilgileriByDenetimId(DenetimTespitKararRequest denetimTespitKararRequest, Long denetimTespitId) {

        UtilDenetimSaveDTO utilDenetimSaveDTO;
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        BDNTDenetimTespit bdntDenetimTespit = null;

        try {
            Object o = session.get(BDNTDenetimTespit.class,denetimTespitId);
            bdntDenetimTespit = (BDNTDenetimTespit) o;
            bdntDenetimTespit = setBDNTDenetimTespitKararBilgisi(denetimTespitKararRequest, bdntDenetimTespit);
            session.update(bdntDenetimTespit);
            tx.commit();
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(true,null,denetimTespitId);


        } catch (Exception e) {

            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError(true);
            errorDTO.setErrorMessage("Karar bilgisi guncellenirken bir hata ile karsilasildi");

            LOG.debug("Denetim duzenleme karar bilgisi guncellenirken bir hata ile karsilasildi : " + e.getMessage());
            utilDenetimSaveDTO = new UtilDenetimSaveDTO(false,errorDTO,denetimTespitId);

        }

        return utilDenetimSaveDTO;
    }

}
