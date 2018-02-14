package com.digikent.demirbas.dao;

import com.digikent.demirbas.dto.DemirbasDTO;
import com.digikent.demirbas.dto.DemirbasHareketDTO;
import org.apache.http.client.HttpClient;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Kadir on 17/07/17.
 */
@Repository
public class DemirbasRepository {
    private final Logger LOG = LoggerFactory.getLogger(DemirbasRepository.class);
    private HttpClient client;

    @Autowired
    SessionFactory sessionFactory;


    public DemirbasDTO getDemirbasById(long demirbasId){
            String sql = " SELECT B.ID AS STOKLINEID, " +
                "         A.ID AS STOKID, " +
                "         A.TARIH, " +
                "         DECODE (A.FISTIPI,  'G', 'GIRIS',  'C', 'CIKIS',  '-') AS FISTIPI, " +
                "         MT2.F_GITTIGIYER (A.ID) AS GITTIGIYER, " +
                "         B.TASINIRINYILI, " +
                "         B.DEMIRBASSICILNO, " +
                "         D.TANIM AS FISTURU, " +
                "         C.TANIM AS MALZEMEADI, " +
                "         E.TANIM AS MALZEMEBIRIMIADI, " +
                "         B.MIKTAR, " +
                "         B.BIRIMTUTAR, " +
                "         NVL (B.TUTAR, 0), " +
                "         B.KDVORANI, " +
                "         B.KDVTUTARI, " +
                "         NVL (B.TUTAR, 0) + NVL (B.KDVTUTARI, 0) AS TOPLAMTUTAR, " +
                "         B.TASINIRKODU, " +
                "         A.AMT2AMBAR_ID, " +
                "         (SELECT EMT2IHALE.ID " +
                "            FROM EMT2IHALE " +
                "           WHERE UMT2TALEP_ID = A.UMT2TALEP_ID AND EMT2IHALE.UMT2TALEP_ID > 0) " +
                "            IHALE " +
                "    FROM RMT2STOK A, " +
                "         TMT2STOKLINE B, " +
                "         LMT2MALZEME C, " +
                "         TMT2FISTURU D, " +
                "         LMT2MALZEMEBIRIMI E, " +
                "         VMT2TASINIR F " +
                "   WHERE     A.ID = B.RMT2STOK_ID " +
                "         AND A.TMT2FISTURU_ID = D.ID " +
                "         AND B.LMT2MALZEME_ID = C.ID " +
                "         AND B.LMT2MALZEMEBIRIMI_ID = E.ID " +
                "         AND B.VMT2TASINIR_ID = F.ID " +
                "         AND A.FISTIPI = 'G' " +
                "         AND B.TASINIRKODU LIKE '2%' " +
                "         AND B.ID = " + demirbasId +
                " ORDER BY A.TARIH ";

        List<Object> list = new ArrayList();

        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        DemirbasDTO demirbasDTO = null;
        for(Object o : list){
            Map map = (Map)o;
            demirbasDTO = new DemirbasDTO();

            Date tarih = (Date)map.get("TARIH");
            BigDecimal tasinirYili = (BigDecimal)map.get("TASINIRINYILI");
            String fisTuru = (String)map.get("FISTURU");
            String malzemeAdi = (String)map.get("MALZEMEADI");
            String malzemeBirimi = (String)map.get("MALZEMEBIRIMIADI");
            BigDecimal miktar = (BigDecimal)map.get("MIKTAR");
            BigDecimal birimTutar = (BigDecimal)map.get("BIRIMTUTAR");
            BigDecimal tutar = (BigDecimal)map.get("NVL(B.TUTAR,0)");
            BigDecimal kdvTutari = (BigDecimal)map.get("KDVTUTARI");
            BigDecimal toplamTutar = (BigDecimal)map.get("TOPLAMTUTAR");
            BigDecimal amt2AmbarId = (BigDecimal)map.get("AMT2AMBAR_ID");
            String tasinirinKodu = (String)map.get("TASINIRKODU");
            BigDecimal demirbasSicilNo = (BigDecimal)map.get("DEMIRBASSICILNO");

            if(tarih != null)
                demirbasDTO.setTarih(tarih);
            if(tasinirYili != null)
                demirbasDTO.setTasinirinYili(tasinirYili.longValue());
            if(fisTuru != null)
                demirbasDTO.setFisTuru(fisTuru);
            if(malzemeAdi != null)
                demirbasDTO.setMalzemeAdi(malzemeAdi);
            if(malzemeBirimi != null)
                demirbasDTO.setMalzemeBirimi(malzemeBirimi);
            if(miktar != null)
                demirbasDTO.setMiktar(miktar.longValue());
            if(birimTutar != null)
                demirbasDTO.setBirimTutar(birimTutar.longValue());
            if(tutar != null)
                demirbasDTO.setTutar(tutar.longValue());
            if(kdvTutari != null)
                demirbasDTO.setKdvTutari(kdvTutari.longValue());
            if(toplamTutar != null)
                demirbasDTO.setToplamTutar(toplamTutar.longValue());
            if(amt2AmbarId != null)
                demirbasDTO.setAmt2AmbarId(amt2AmbarId.longValue());
            if(tasinirinKodu != null)
                demirbasDTO.setTasinirinKodu(tasinirinKodu);
            if(demirbasSicilNo != null)
                demirbasDTO.setDemirbasSicilNo(demirbasSicilNo.longValue());

            break;
        }

        return demirbasDTO;
    }

    public List<DemirbasHareketDTO> getDemirbasHareketByDemirbasDTO(DemirbasDTO demirbasDTO){
        String sql = " SELECT A.TARIH AS ISLEMTARIHI," +
                "         A.TIFSIRANUMARASI," +
                "         C.TANIM AS FISTURU," +
                "         DECODE (" +
                "            A.FISTIPI," +
                "            'C', DECODE (" +
                "                    C.KAYITOZELISMI," +
                "                    'DEVIRCIKIS', (SELECT X.TANIM" +
                "                                     FROM AMT2AMBAR X" +
                "                                    WHERE X.ID = NVL (A.AMT2AMBAR_GIDEN, 0))," +
                "                    MT2.F_GITTIGIYER (A.ID))," +
                "            '-')" +
                "            AS GITTIGIYER," +
                "         DECODE (C.KAYITOZELISMI," +
                "                 'DEVIRGIRIS', (SELECT X.TANIM" +
                "                                  FROM AMT2AMBAR X" +
                "                                 WHERE X.ID = NVL (A.AMT2AMBAR_GELEN, 0))," +
                "                 'ZIMMETGIRIS', (SELECT TRIM (X.ADI) || ' ' || X.SOYADI" +
                "                                   FROM IHR1PERSONEL X" +
                "                                  WHERE X.ID = NVL (A.IHR1PERSONEL_ZIMMET, 0))," +
                "                 '-')" +
                "            AS GELDIGIYER," +
                "         A.ID" +
                "    FROM RMT2STOK A, TMT2STOKLINE B, TMT2FISTURU C" +
                "   WHERE     A.ID = B.RMT2STOK_ID" +
                "         AND A.TMT2FISTURU_ID = C.ID" +
                "         AND A.AMT2AMBAR_ID = " + demirbasDTO.getAmt2AmbarId() +
                "         AND B.TASINIRINYILI = " + demirbasDTO.getTasinirinYili() +
                "         AND B.TASINIRKODU = " + demirbasDTO.getTasinirinKodu() +
                "         AND B.DEMIRBASSICILNO = " + demirbasDTO.getDemirbasSicilNo() +
                " ORDER BY A.TARIH DESC";

        List<Object> list = new ArrayList();

        SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        List<DemirbasHareketDTO> demirbasHareketDTOList = new ArrayList();

        for(Object o : list){
            Map map = (Map)o;
            DemirbasHareketDTO demirbasHareketDTO = new DemirbasHareketDTO();

            Date islemTarihi = (Date)map.get("ISLEMTARIHI");
            BigDecimal tifSiraNumarasi = (BigDecimal) map.get("TIFSIRANUMARASI");
            String gittigiYer = (String)map.get("GITTIGIYER");
            String geldigiYer = (String)map.get("GELDIGIYER");

            if(islemTarihi != null)
                demirbasHareketDTO.setIslemTarihi(islemTarihi);
            if(tifSiraNumarasi != null)
                demirbasHareketDTO.setTifSiraNumarasi(tifSiraNumarasi.longValue());
            if(gittigiYer != null)
                demirbasHareketDTO.setGittigiYer(gittigiYer);
            if(geldigiYer != null)
                demirbasHareketDTO.setGeldigiYer(geldigiYer);

            demirbasHareketDTOList.add(demirbasHareketDTO);
        }

        return demirbasHareketDTOList;
    }

}
