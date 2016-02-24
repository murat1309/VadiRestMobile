package com.digikent.vadirest.dao.impl;

import com.digikent.vadirest.dao.DocumentManagementDAO;
import com.digikent.vadirest.dto.BelgeBasvuru;
import com.digikent.vadirest.dto.BelgeBasvuruDetay;
import com.digikent.vadirest.dto.BelgeYonetimKullanici;
import com.digikent.vadirest.dto.CozumOrtagi;
import com.digikent.vadirest.dto.EBYSBekleyen;
import com.digikent.vadirest.dto.EBYSBirimMenu;
import com.digikent.vadirest.dto.EBYSKlasorMenu;
import com.digikent.vadirest.dto.GraphGeneral;
import com.digikent.vadirest.dto.Rol;
import com.vadi.digikent.icerikyonetimi.bpm.model.BPMWorkitem;
import com.vadi.digikent.sistem.syn.model.SM1Roles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository("documentManagementDao")
@Transactional
public class DocumentManagementDAOImpl implements DocumentManagementDAO {

	@Autowired
	protected SessionFactory sessionFactory;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	public List<SM1Roles> getEBYSRollList(long persid) {
		
		String sql ="select FSM1ROLES_ID,"
				   +"(SELECT TANIM FROM FSM1ROLES WHERE ID=FSM1ROLES_ID) as TANIM " 
				   +"from fsm1rolesmember " 
				   +"where(SELECT NVL(ISACTIVE,'H') FROM FSM1ROLES WHERE ID=FSM1ROLES_ID) ='E' "  
				   +"AND  fsm1users_id = "+ persid +" ";	
		
		List<Object> list = new ArrayList();
		List<SM1Roles> rollList = new ArrayList();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			SM1Roles sm1roles = new SM1Roles();
			
			BigDecimal fsm1roles_id =(BigDecimal)map.get("FSM1ROLES_ID");
			String tanim = (String)map.get("TANIM");
			
			if(fsm1roles_id != null)
				sm1roles.setId(fsm1roles_id.longValue());			
			if(tanim != null)
				sm1roles.setTanim(tanim);
			rollList.add(sm1roles);
		}
		return rollList;
	}

	public List<EBYSBekleyen> getWaitingEBYS(long persid, long rolid) {
		String sql="SELECT id,action,creationdate,creationdatetime, "
				+"DECODE ((SELECT NVL (ABYOKONU_ID, 0)FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID), "
				+"0, CASE WHEN (SELECT BPM.F_READ_WORKFLOWVALUE_NUMBER (ABPMWORKFLOW_ID,'EBYSBELGE_ID')FROM DUAL) > 0 "
				+"THEN(SELECT KONUOZETI FROM EBYSBELGE WHERE ID =(SELECT BPM.F_READ_WORKFLOWVALUE_NUMBER (A.ABPMWORKFLOW_ID,'EBYSBELGE_ID')FROM DUAL)) "
				+"ELSE '' END,TASKSUBJECT)KONU, MESSAGE,((SELECT FIRSTNAME || ' ' || LASTNAME FROM FSM1USERS WHERE ID = fsm1users_sentby)|| '(' || (SELECT TANIM "
				+"FROM FSM1ROLES WHERE ID = fsm1roles_sentby) || ')' ) NAME, BPM.F_READ_WORKFLOWVALUE_NUMBER (ABPMWORKFLOW_ID, 'EBYSBELGE_ID') EBYSBELGE_ID, "
				+"(SELECT (SELECT processname FROM ABPMPROCESS WHERE id = ABPMWORKFLOW.ABPMPROCESS_ID) FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_id) TURU, "
				+"TASKCOMMENT, (SELECT BSM2SERVIS_MUDURLUK FROM EBYSBELGE WHERE ID = (SELECT EBYSBELGE_ID FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_ID)) BSM2SERVISID, "
				+"BPM.F_DocumentIdForWorkItem (A.ID) DOCID, EBYSGIDENKAYITNUMARASI, EBYSGELENKAYITNUMARASI, BPM.F_WorkItemPaydasAdres (ID) ADRES, (SELECT TANIM "
				+"FROM EBYSDOCUMENT  WHERE ID = EBYSDOCUMENT_ID) YAZIADI, (SELECT ABYOBASVURU_ID FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID)  ABYOBASVURU_ID, "
				+"(SELECT (SELECT DRE1MAHALLE_ADI  FROM ABYOBASVURU  WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) DRE1MAHALLEADI_BASVURU, "
				+"(SELECT (SELECT SRE1SOKAK_ADI FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) SRE1SOKAKADI_BASVURU, "
				+"(SELECT (SELECT (SELECT TANIM FROM ABYOBASVURUDURUMU WHERE ID = ABYOBASVURU.ABYOBASVURUDURUMU_ID) FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) "
				+"FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) BASVURUDURUMU_ADI, SUBSTR (TASKCOMMENT, 1, 20) AS COZUM, ebysiletimnedeni, "
				+"(SELECT BEKLENENBITISTARIHI FROM EBYSBELGE WHERE ID = A.EBYSBELGE_ID AND A.EBYSBELGE_ID > 0) BEKLENENBITISTARIHI,(SELECT COMPLETEDDATETIME "
				+"FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_ID) COMPLETEDDATETIME, NVL (READFLAG, 'H') READFLAG, TOPLUIMZALAMAYAPILACAK, (SELECT (SELECT B.TANIM "
				+"FROM BSM2SERVIS B WHERE B.ID = E.BSM2SERVIS_MUDURLUK) FROM EBYSBELGE E WHERE E.ID = EBYSBELGE_ID) URETENMUDURLUK    FROM ABPMWORKITEM A "
				+"WHERE A.id > 0 AND A.ACTION = 'PROGRESS' AND ( (A.FSM1ROLES_PERFORMER = " + rolid+" AND A.FSM1USERS_PERFORMER = " +persid+" ) "
				+"OR (A.FSM1ROLES_PERFORMER = " + rolid+" AND A.Fsm1Users_PERFORMER = 0)) ORDER BY NVL (BEKLENENBITISTARIHI, "
				+"TO_DATE ('01/01/9999 00:00:00', 'dd/mm/yyyy Hh24:MI:ss')) ASC, A.CREATIONDATETIME DESC, A.id DESC ";
		
		List<Object> list = new ArrayList();
		List<EBYSBekleyen> ebysBekleyenList = new ArrayList();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			EBYSBekleyen ebysBekleyen = new EBYSBekleyen();
			BPMWorkitem bpmWorkItem = new BPMWorkitem();

			Date creationDate = (Date)map.get("CREATIONDATE");
			String name = (String)map.get("NAME");
			String konu = (String)map.get("KONU");
			String message = (String) map.get("MESSAGE");
			BigDecimal docId = (BigDecimal) map.get("DOCID");
			
			if(creationDate != null)
				ebysBekleyen.setCreationDate(dateFormat.format(creationDate));
			if(name != null)
				ebysBekleyen.setName(name);
			if(konu != null)
				ebysBekleyen.setKonu(konu);
			if(message != null)
				ebysBekleyen.setMessage(message);
			if(docId != null)
				ebysBekleyen.setDocId(docId.longValue());
			
			ebysBekleyenList.add(ebysBekleyen);
		}
		return ebysBekleyenList;
	}

	public List<Rol> getDocRollList(long persid, long mastid) {
		String sql="SELECT A.ID,B.MSM2ORGANIZASYON_ID,A.ADI||' '||A.SOYADI||'('|| (SELECT TANIM FROM MSM2ORGANIZASYON WHERE ID = B.MSM2ORGANIZASYON_ID) ||')' as ROLNAME "
				+"FROM IHR1PERSONEL A,IHR1PERSONELORGANIZASYON B WHERE A.ID = B.IHR1PERSONEL_ID AND A.ID ="+ persid 
				+" union all SELECT A.ID,A.MSM2ORGANIZASYON_ID,A.ADI||' '||A.SOYADI||'('|| (SELECT TANIM FROM MSM2ORGANIZASYON WHERE ID = A.MSM2ORGANIZASYON_ID) ||')' " 
				+"FROM IHR1PERSONEL A WHERE A.MSM2ORGANIZASYON_ID IN (SELECT MSM2ORGANIZASYON_ID FROM MSM2ORGANIZASYON_YETKILI WHERE MSM2ORGANIZASYON_MASTER ="+ mastid+")";
		
		List<Object> list = new ArrayList();
		List<Rol> rolList = new ArrayList();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			Rol rol = new Rol();
			
			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal msm2OrganizationId =(BigDecimal)map.get("MSM2ORGANIZASYON_ID");
			String rolName = (String)map.get("ROLNAME");
			
			if(id != null)
				rol.setId(id.longValue());
			if(msm2OrganizationId != null)
				rol.setMsm2OrganizationId(msm2OrganizationId.longValue());
			if(rolName != null)
				rol.setTanim(rolName);
			
			rolList.add(rol);
		}
		
		return rolList;
	}
	
	public List<BelgeBasvuru> getApplyDoc(long rolid){
		String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		System.out.println(currentDate);
		
		Date d = new Date();//initialize your date to any date 
		Date dateBefore = new Date(d.getTime() - 7 * 24 * 3600 * 1000 );
		String lastWeekDate = new SimpleDateFormat("dd/MM/yyyy").format(dateBefore);
		String sql ="select  DB.ISAKISIKONUOZETI,DB.TARIH,DB.ID,DBI.GIRISDEFTERNUMARASI,DB.SONUCDURUMU,DB.TURU from EDM1ISAKISIADIM DBI, DDM1ISAKISI DB "
				+" WHERE DB.ID = DBI.DDM1ISAKISI_ID  and(DB.TARIH between TO_DATE('"+lastWeekDate+"','dd/MM/yyyy')  AND TO_DATE('"+currentDate+"','dd/MM/yyyy')) AND "
				+" DB.TURU IN ('E','K','D') AND DBI.ALC_MSM2ORGANIZASYON_ID = "+rolid +" AND DBI.ALC_MSM2ORGANIZASYON_ID <> DBI.GON_MSM2ORGANIZASYON_ID AND DBI.DURUMU = 'S' And DBI.SONUCDURUMU NOT IN ('T')"
				+"union select  DB.ISAKISIKONUOZETI,DB.TARIH,DB.ID,DBI.GIRISDEFTERNUMARASI,DB.SONUCDURUMU,DB.TURU from EDM1ISAKISIADIM DBI, DDM1ISAKISI DB "
				+" WHERE DB.ID = DBI.DDM1ISAKISI_ID  and DB.TURU IN ('S') AND DBI.ALC_MSM2ORGANIZASYON_ID = "+rolid+" AND DBI.ALC_MSM2ORGANIZASYON_ID <> DBI.GON_MSM2ORGANIZASYON_ID AND DBI.DURUMU = 'S' And DBI.SONUCDURUMU NOT IN ('T')"
				+" AND  DB.ID IN (SELECT DDM1ISAKISI_ID FROM EDM1ISAKISIADIM WHERE  (TARIH BETWEEN TO_DATE('"+lastWeekDate +"','dd/MM/yyyy') AND TO_DATE('"+currentDate+"','dd/MM/yyyy')))"
				+"order by TARIh DESC,ID";

//		String sql ="select  DB.ISAKISIKONUOZETI,DB.TARIH,DB.ID,DBI.GIRISDEFTERNUMARASI,DB.SONUCDURUMU,DB.TURU from EDM1ISAKISIADIM DBI, DDM1ISAKISI DB "
//				+" WHERE DB.ID = DBI.DDM1ISAKISI_ID  and "
//				+" DB.TURU IN ('E','K','D') AND DBI.ALC_MSM2ORGANIZASYON_ID = "+rolid +" AND DBI.ALC_MSM2ORGANIZASYON_ID <> DBI.GON_MSM2ORGANIZASYON_ID AND DBI.DURUMU = 'S' And DBI.SONUCDURUMU NOT IN ('T')"
//				+"union select  DB.ISAKISIKONUOZETI,DB.TARIH,DB.ID,DBI.GIRISDEFTERNUMARASI,DB.SONUCDURUMU,DB.TURU from EDM1ISAKISIADIM DBI, DDM1ISAKISI DB "
//				+" WHERE DB.ID = DBI.DDM1ISAKISI_ID  and DB.TURU IN ('S') AND DBI.ALC_MSM2ORGANIZASYON_ID = "+rolid+" AND DBI.ALC_MSM2ORGANIZASYON_ID <> DBI.GON_MSM2ORGANIZASYON_ID AND DBI.DURUMU = 'S' And DBI.SONUCDURUMU NOT IN ('T')"
//				+" AND  DB.ID IN (SELECT DDM1ISAKISI_ID FROM EDM1ISAKISIADIM )"
//				+"order by TARIh DESC,ID";

		List<Object> list = new ArrayList();
		List<BelgeBasvuru> belgeBasvuruList = new ArrayList();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		for(Object o : list){
			Map map = (Map)o;
			BelgeBasvuru belgeBasvuru = new BelgeBasvuru();
			
			BigDecimal girisDefterNumarasi = (BigDecimal)map.get("GIRISDEFTERNUMARASI");
			BigDecimal id = (BigDecimal)map.get("ID");
			String turu =(String) map.get("TURU");
			String isAkisiKonuOzeti = (String) map.get("ISAKISIKONUOZETI");
			Date tarih = (Date) map.get("TARIH");
			
			if(girisDefterNumarasi != null)
				belgeBasvuru.setGirisDefterNumarasi(girisDefterNumarasi.longValue());
			if(id != null)
				belgeBasvuru.setId(id.longValue());
			if(turu != null)
				belgeBasvuru.setTuru(turu);
			if(isAkisiKonuOzeti != null)
				belgeBasvuru.setIsAkisiKonuOzeti(isAkisiKonuOzeti);
			if(tarih != null)
				belgeBasvuru.setTarih(dateFormat.format(tarih));
			
			belgeBasvuruList.add(belgeBasvuru);
		}
		
		return belgeBasvuruList;
	}

	public BelgeBasvuruDetay getApplyDocDetail(long docId) {
		String sql ="select * from ddm1isakisi where ID=" + docId;
		
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		BelgeBasvuruDetay belgeBasvuruDetay = new BelgeBasvuruDetay();
		for(Object o : list){
			Map map = (Map)o;
			
			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal referansNo = (BigDecimal)map.get("ILGIID");
			String tip = (String) map.get("tip");
			BigDecimal disaridanGelenBelgeNo = (BigDecimal) map.get("DISARIDANGELENBELGENO");
			BigDecimal defterKaydiGirisNo = (BigDecimal) map.get("DEFTERKAYDIGIRISNO");
			Date tarih = (Date)map.get("TARIH");
			Timestamp tarihSaat = (Timestamp) map.get("TARIHSAAT");
			Date belgeTarih = (Date)map.get("BELGETARIHI");
			String uretimTipi = (String)map.get("URETIMTIPI");
		
			String merciKurum = (String) map.get("MERCIKURUM");
			String adi = (String) map.get("ADI");
			String soyadi = (String) map.get("SOYADI");
			String konusu = (String) map.get("KONUSU");
			String sdpKodu = (String) map.get("SDPKODU");
			String ekBilgi = (String) map.get("EKBILGI");
			String isAkisKonuOzeti = (String) map.get("ISAKISIKONUOZETI");
			String izahat = (String) map.get("IZAHAT");
			String gizlilik = (String) map.get("GIZLILIK");
			String onemDerecesi = (String)map.get("ONEMDERECESI");
			BigDecimal ekliSayfaSayisi = (BigDecimal) map.get("EKLISAYFASAYISI");
			String digerEkler = (String) map.get("DIGEREKLER");
			String dosyaNo = (String) map.get("DOSYANO");
			Date beklenenBitisTarihi = (Date) map.get("BEKLENENBITISTARIHI");
			String belgeDurumu = (String) map.get("BELGEDURUMU");
			String evrakiGetiren = (String) map.get("EVRAKIGETIREN");
			
			String mudurluk = (String) map.get("MUDURLUK");
			BigDecimal bsm2ServisSeflik = (BigDecimal) map.get("BSM2SERVIS_SEFLIK");
			BigDecimal msm2OrganizasyonId= (BigDecimal) map.get("MSM2ORGANIZASYON_ID");
			BigDecimal msm2OrganizasyonIdPilot= (BigDecimal) map.get("MSM2ORGANIZASYON_ID_PILOT");
			BigDecimal ihr1PersonelId = (BigDecimal) map.get("IHR1PERSONEL_ID");
			
			BigDecimal telefonNumarasi = (BigDecimal) map.get("TELEFONNUMARASI");
			BigDecimal isTelefonu = (BigDecimal) map.get("ISTELEFONU");
			BigDecimal cepTelefonu = (BigDecimal) map.get("CEPTELEFONU");
			String elektronikPosta = (String) map.get("ELEKTRONIKPOSTA");
			String geriDonusYapilsinMi =(String) map.get("GERIDONUSYAPILSINMI");
			String geriBildirimTuru =(String) map.get("GERIBILDIRIMTURU");
			String geriBildirimYapildi =(String) map.get("GERIBILDIRIMYAPILDI");
			
			String ilce = (String) map.get("RRE1ILCE_ADI");
			String mahalle = (String) map.get("RRE1ILCE_ADI");
			
			String babaAdi = (String) map.get("BABAADI");
			BigDecimal tcKimlikNo =(BigDecimal) map.get("TCKIMLIKNO");
			String bildirimdeBulunan=(String) map.get("BILDIRIMDEBULUNAN");
			BigDecimal adm1BildirimTuruId = (BigDecimal)map.get("ADM1BILDIRIMTURU_ID");
			
			String bildirimNiteligi = (String) map.get("BILDIRIMNITELIGI");
			String bildirimTuru = (String) map.get("BILDIRIMTURU");
			
			
			
			if(id != null)
				belgeBasvuruDetay.setId(id.longValue());
			if(referansNo != null)
				belgeBasvuruDetay.setReferansNo(referansNo.longValue());
			if(tip != null)
				belgeBasvuruDetay.setTip(tip);
			if(disaridanGelenBelgeNo != null)
				belgeBasvuruDetay.setDisaridanGelenBelgeNo(disaridanGelenBelgeNo.longValue());
			if(defterKaydiGirisNo != null)
				belgeBasvuruDetay.setDisaridanGelenBelgeNo(disaridanGelenBelgeNo.longValue());
			if(tarih != null)
				belgeBasvuruDetay.setTarih(dateFormat.format(tarih));
			if(tarihSaat != null)
				belgeBasvuruDetay.setTarihSaat(dateFormat.format(new Date(tarihSaat.getTime())));
			if(uretimTipi != null)
				belgeBasvuruDetay.setUretimTipi(uretimTipi);
			if(merciKurum != null)
				belgeBasvuruDetay.setMerciKurum(merciKurum);
			if(adi != null)
				belgeBasvuruDetay.setAdi(adi);
			if(soyadi != null)
				belgeBasvuruDetay.setSoyadi(soyadi);
			if(konusu != null)
				belgeBasvuruDetay.setKonusu(konusu);
			if(sdpKodu != null)
				belgeBasvuruDetay.setSdpKodu(sdpKodu);
			if(ekBilgi!= null)
				belgeBasvuruDetay.setEkBilgi(ekBilgi);
			if(isAkisKonuOzeti != null)
				belgeBasvuruDetay.setIsAkisKonuOzeti(isAkisKonuOzeti);
			if(izahat != null)
				belgeBasvuruDetay.setIzahat(izahat);
			if(gizlilik!= null)
				belgeBasvuruDetay.setIzahat(izahat);
			if(onemDerecesi != null)
				belgeBasvuruDetay.setOnemDerecesi(onemDerecesi);
			if(ekliSayfaSayisi != null)
				belgeBasvuruDetay.setEkliSayfaSayisi(ekliSayfaSayisi.longValue());
			if(digerEkler != null)
				belgeBasvuruDetay.setDigerEkler(digerEkler);
			if(dosyaNo != null)
				belgeBasvuruDetay.setDosyaNo(dosyaNo);
			if(beklenenBitisTarihi != null)
				belgeBasvuruDetay.setBeklenenBitisTarihi(dateFormat.format(beklenenBitisTarihi));
			if(belgeDurumu != null)
				belgeBasvuruDetay.setBelgeDurumu(belgeDurumu);
			if(evrakiGetiren != null)
				belgeBasvuruDetay.setEvrakiGetiren(evrakiGetiren);
			if(mudurluk != null)
				belgeBasvuruDetay.setMudurluk(mudurluk);
			if(bsm2ServisSeflik != null)
				belgeBasvuruDetay.setBsm2ServisSeflik(bsm2ServisSeflik.longValue());
			if(msm2OrganizasyonId != null)
				belgeBasvuruDetay.setMsm2OrganizasyonId(msm2OrganizasyonId.longValue());
			if(msm2OrganizasyonIdPilot != null)
				belgeBasvuruDetay.setMsm2OrganizasyonIdPilot(msm2OrganizasyonIdPilot.longValue());
			if(ihr1PersonelId != null)
				belgeBasvuruDetay.setIhr1PersonelId(ihr1PersonelId.longValue());
			if(telefonNumarasi != null)
				belgeBasvuruDetay.setTelefonNumarasi(telefonNumarasi.longValue());
			if(isTelefonu != null)
				belgeBasvuruDetay.setIsTelefonu(isTelefonu.longValue());
			if(cepTelefonu != null)
				belgeBasvuruDetay.setCepTelefonu(cepTelefonu.longValue());
			if(elektronikPosta != null)
				belgeBasvuruDetay.setElektronikPosta(elektronikPosta);
			if(geriDonusYapilsinMi != null)
				belgeBasvuruDetay.setGeriDonusYapilsinMi(geriDonusYapilsinMi);
			if(geriBildirimTuru != null)
				belgeBasvuruDetay.setGeriBildirimTuru(geriBildirimTuru);
			if(geriBildirimYapildi != null)
				belgeBasvuruDetay.setGeriBildirimYapildi(geriBildirimYapildi);
			if(ilce != null)
				belgeBasvuruDetay.setIlce(ilce);
			if(mahalle != null)
				belgeBasvuruDetay.setMahalle(mahalle);
			if(babaAdi != null)
				belgeBasvuruDetay.setBabaAdi(babaAdi);
			if(tcKimlikNo != null)
				belgeBasvuruDetay.setTcKimlikNo(tcKimlikNo.longValue());
			if(bildirimdeBulunan != null)
				belgeBasvuruDetay.setBildirimdeBulunan(bildirimdeBulunan);
			if(adm1BildirimTuruId != null)
				belgeBasvuruDetay.setAdm1BildirimTuruId(adm1BildirimTuruId.longValue());
			if(bildirimNiteligi != null)
				belgeBasvuruDetay.setBildirimNiteligi(bildirimNiteligi);
			if(bildirimTuru != null)
				belgeBasvuruDetay.setBildirimTuru(bildirimTuru);
		}
		
		return belgeBasvuruDetay;
	}

	@Override
	public Long getEbysMenuCount(long persid, long rolid, String tur) {
		String sql = " SELECT COUNT(*) CNT FROM ABPMWORKITEM WHERE ID>0  "
				+ " AND ((FSM1ROLES_PERFORMER = " + rolid
				+ " And FSM1USERS_PERFORMER = " + persid + ") "
				+ "   Or (FSM1ROLES_PERFORMER = " + rolid
				+ " And Fsm1Users_PERFORMER = 0)) ";

		if (tur.equals("BEKLEYEN")) {
			sql += " AND ACTION = 'PROGRESS'";

		} else if (tur.equals("ONAYBEKLEYEN")) {
			sql += " AND ACTION = 'PROGRESS' "
					+ " AND ABPMTASK_ID IN (50007,240,10090,40123,11000114,40137,10110,40113,180,10070,40121,11000112,40135,300,10130,40125,11000116,40139,120,10050,40119,11000110,40133,40132,5007 ) ";

		} else if (tur.equals("GONDERIMBEKLEYEN")) {
			sql += " AND ACTION = 'PROGRESS'  AND ABPMTASK_ID IN (10140,11000200,40128,40151,50008,5008, 5015, 40301, 40151) ";

		} else if (tur.equals("KISIYEATANAN")) {
			sql += " AND ACTION = 'PROGRESS'  AND ABPMTASK_ID IN (2001,40008,40198) ";

		} else if (tur.equals("IADEEDILEN")) {
			sql += " AND ACTION = 'PROGRESS'  AND ABPMTASK_ID IN (3010) ";

		} else if (tur.equals("DIGER")) {
			sql += "  AND ACTION = 'PROGRESS'  AND ABPMTASK_ID NOT IN (50008,50007,240,10090,40123,11000114,40137,10110,40113,180,10070,40121,11000112,40135,300,10130,40125,11000116,40139,120,10050,40119,11000110,40133,10140,11000200,40128,40151,2001,40008,40198,5007 ,5008, 5015, 40301, 40151) ";

		} else if (tur.equals("TAMAMLANAN")) {
			sql += " AND ACTION IN ('SEND','CANCEL','REJECT','DELEGATE') "
					+ " AND NVL(ACTIONDETAIL,'-') NOT IN ('GERICEKILDI') "
					+ " AND EXISTS (SELECT 1 FROM ABPMTASK WHERE ID=ABPMTASK_ID AND VISIBLEFORCOMPLETEDLIST='EVET') ";

		} else if (tur.equals("SURELIISLER")) {
			sql += " AND ACTION = 'SUSPEND' ";

		} else if (tur.equals("TOPLUIMZASEPETI")) {
			sql += " AND TOPLUIMZALAMAYAPILACAK='EVET' AND ACTION = 'PROGRESS' "
					+ " AND ABPMTASK_ID IN (50007,240,10090,40123,11000114,40137,10110,40113,180,10070,40121,11000112,40135,300,10130,40125,11000116,40139,120,10050,40119,11000110,40133,40132,5007 ) ";

		} else if (tur.equals("BYO_BEKLEYEN")) {
			sql += " AND ACTION = 'PROGRESS' AND ABPMTASK_ID IN (40307)";

		} else if (tur.equals("BYO_UZERIMDE_BEKLEYEN")) {
			sql += " AND ACTION = 'PROGRESS'  AND  ABPMTASK_ID IN (40307) "
					+ " AND EXISTS (SELECT 1 FROM ABPMWORKFLOW WHERE ID= ABPMWORKFLOW_ID "
					+ " AND EXISTS (SELECT 1 FROM ABYOBASVURU WHERE ID = ABPMWORKFLOW.ABYOBASVURU_ID "
					+ " 				AND FSM1USERS_ATANAN = " + persid + ") )";

		} else if (tur.equals("BYO_BEKLEMEYE_ALINAN")) {
			sql += " AND  ACTION = 'PROGRESS'  AND  ABPMTASK_ID IN (40307) "
					+ "  AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='BKLMD' AND ISACTIVE='E')";

		} else if (tur.equals("BYO_ACIK")) {
			sql += " AND  ACTION = 'PROGRESS'  AND  ABPMTASK_ID IN (40307) "
					+ "  AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='Open' AND ISACTIVE='E')";

		} else if (tur.equals("BYO_TAMAMLANAN")) {
			sql += " AND ACTION = 'SEND'  AND  ABPMTASK_ID IN (40307) ";

		} else if (tur.equals("BYO_ONAYLANAN")) {
			sql += " AND  ACTION = 'SEND'  AND  ABPMTASK_ID IN (40307) "
					+ " AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='Resolved' AND ISACTIVE='E')";

		} else if (tur.equals("BYO_ONAY_BEKLEYEN")) {
			sql += " AND  ACTION = 'SEND'  AND  ABPMTASK_ID IN (40307) "
					+ " AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='PendingApproval' AND ISACTIVE='E')";

		} else if (tur.equals("BYO_KAPALI")) {
			sql += " AND  ACTION = 'SEND'  AND  ABPMTASK_ID IN (40307) "
					+ "  AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='Closed' AND ISACTIVE='E')";

		} else if (tur.equals("BYO_TEKRAR_YONLENDIRME_BEKLEYEN")) {
			sql += " AND  ACTION = 'SEND'  AND  ABPMTASK_ID IN (40307) "
					+ "  AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='ReDirect' AND ISACTIVE='E')";

		} else if (tur.equals("BASLATTIGIMSURECLER")) {
			sql = " SELECT COUNT(*) CNT FROM ABPMWORKFLOW WHERE ID>0 AND FSM1USERS_CREATOR = "
					+ persid;

		} else if (tur.equals("IZLEDIGIMSURECLER")) {
			sql = " SELECT COUNT(*) CNT FROM ABPMWORKFLOW"
					+ " WHERE ID>0 "
					+ " AND ABPMWORKFLOW.ID IN (SELECT ABPMWORKFLOW_ID FROM ABPMWATCH WHERE nvl(ISACTIVE,'E')='E' AND FSM1USERS_ID ="
					+ persid + " )";

		} else if (tur.equals("MESAJLAR")) {
			sql = "SELECT COUNT(*) CNT FROM ABPMMESSAGES WHERE READFLAG = 'H'  AND FSM1ROLES_ID = "
					+ rolid;

		}  else if (tur.equals("UYARILAR")) {
			sql = "SELECT COUNT(*) CNT FROM ABPMMESSAGES WHERE READFLAG = 'H'  AND ACTIONTYPE IN ('GERICEKILDI','REDDEDILDI','SONLANDIRILDI') "
					+ " AND FSM1ROLES_ID = "
					+ rolid;

		}  else if (tur.equals("YORUMLAR")) {
			sql = "SELECT COUNT(*) CNT FROM ABPMMESSAGES WHERE READFLAG = 'H'  AND ACTIONTYPE = 'YORUM'  AND FSM1ROLES_ID = "
					+ rolid;

		}

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return ((BigDecimal) ((Map) query.list().get(0)).get("CNT"))
				.longValue();
	}

	public CozumOrtagi isSolutionPartner(long rolid) {
		String sql = "SELECT NVL(COZUMORTAGI ,'HAYIR') P1 FROM FSM1ROLES WHERE ID=" + rolid;
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		CozumOrtagi cozumOrtagi = new CozumOrtagi();
		String p1 = ((String) ((Map) query.list().get(0)).get("P1"));
		if(p1 != null)
			cozumOrtagi.setCozumOrtagi(p1);
		return cozumOrtagi;
	}

	@Override
	public List<EBYSKlasorMenu> getEBYSFolderTree(long rolid) {
		String sql = " select a.id,a.masterid,a.tanim ,a.kodu from EBYSFOLDER a where 1=1 "
				+ " AND 1=F_EBYSPERMISSION(A.ID,"
				+ rolid
				+ ") "
				+ " start with a.MASTERID = 1  "
				+ " connect by prior a.ID = a.MASTERID";
		
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<EBYSKlasorMenu> ebysKlasorMenuList = new ArrayList<EBYSKlasorMenu>();
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			EBYSKlasorMenu ebysKlasorMenu = new EBYSKlasorMenu();
			
			BigDecimal id =(BigDecimal)map.get("ID");
			BigDecimal masterId = (BigDecimal)map.get("MASTERID");
			String tanim = (String)map.get("TANIM");
			String kodu = (String)map.get("KODU");
			
			if(id != null)
				ebysKlasorMenu.setId(String.valueOf(id.longValue()));
			if(masterId != null)
				if(masterId.longValue() == 1 )
					ebysKlasorMenu.setParent(String.valueOf("#"));
				else
					ebysKlasorMenu.setParent(String.valueOf(masterId.longValue()));
			if(tanim != null || kodu!= null)
				ebysKlasorMenu.setText(kodu + "-" + tanim);
			if(id != null)
				ebysKlasorMenu.setData(String.valueOf(id.longValue()));
			
			ebysKlasorMenuList.add(ebysKlasorMenu);
		}
		
		return ebysKlasorMenuList;
	}

	public List<EBYSBirimMenu> getEBYSUnitTree(){
		String sql = " select a.id bsm2servis_id,a.masterid,a.tanim servisadi,a.ebyskodu,'-' from bsm2servis a "
				+ " where masterid=0 and a.ebyskodu is not null "
				+ " order by a.ebyskodu";
		
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<EBYSBirimMenu> ebysBirimMenuList = new ArrayList<EBYSBirimMenu>();
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			EBYSBirimMenu ebysBirimMenu = new EBYSBirimMenu();
			
			BigDecimal bsm2servis_id =(BigDecimal)map.get("BSM2SERVIS_ID");
			BigDecimal masterId = (BigDecimal)map.get("MASTERID");
			String servisAdi = (String)map.get("SERVISADI");
			String ebysKodu = (String)map.get("EBYSKODU");
			
			if(bsm2servis_id != null)
				ebysBirimMenu.setId(String.valueOf(bsm2servis_id.longValue()));
			if(masterId != null)
				if(masterId.longValue() == 0 )
					ebysBirimMenu.setParent(String.valueOf("#"));
				else
					ebysBirimMenu.setParent(String.valueOf(masterId.longValue()));
			if(servisAdi != null || ebysKodu!= null)
				ebysBirimMenu.setText(ebysKodu + "-" + servisAdi);
			if(bsm2servis_id != null)
				ebysBirimMenu.setData(String.valueOf(bsm2servis_id.longValue()));
			
			ebysBirimMenuList.add(ebysBirimMenu);
		}
		
		return ebysBirimMenuList;		
	}
	
	public List<EBYSBirimMenu> getEBYSSubUnitTree(long bsm2servis){
		String sql = " select a.id bsm2servis_id,a.masterid,a.tanim servisadi,a.ebyskodu,'-' from bsm2servis a "
				+ " where masterid="
				+ bsm2servis
				+ " and a.ebyskodu is not null " + " order by a.ebyskodu";
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<EBYSBirimMenu> ebysBirimMenuList = new ArrayList<EBYSBirimMenu>();
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			EBYSBirimMenu ebysBirimMenu = new EBYSBirimMenu();
			
			BigDecimal bsm2servis_id =(BigDecimal)map.get("BSM2SERVIS_ID");
			BigDecimal masterId = (BigDecimal)map.get("MASTERID");
			String servisAdi = (String)map.get("SERVISADI");
			String ebysKodu = (String)map.get("EBYSKODU");
			
			if(bsm2servis_id != null)
				ebysBirimMenu.setId(String.valueOf(bsm2servis_id.longValue()));
			if(masterId != null)
				if(masterId.longValue() == 0 )
					ebysBirimMenu.setParent(String.valueOf("#"));
				else
					ebysBirimMenu.setParent(String.valueOf(masterId.longValue()));
			if(servisAdi != null)
				ebysBirimMenu.setText( servisAdi);
			if(bsm2servis_id != null)
				ebysBirimMenu.setData(String.valueOf(bsm2servis_id.longValue()));
			
			ebysBirimMenuList.add(ebysBirimMenu);
		}
		
		return ebysBirimMenuList;
	}
	
	public List<EBYSBirimMenu> getEBYSServiceFolderTree(long bsm2servis){
		String sql = " select a.id bsm2servis_id,a.masterid,a.tanim servisadi,a.ebyskodu,b.foldername,b.id bsm2servisklasor_id "
				+ " from bsm2servis a,bsm2servisklasor b where a.id = b.bsm2servis_id and a.ebyskodu is not null "
				+ " and a.id = "
				+ bsm2servis
				+ " order by a.ebyskodu,b.foldername";
		
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<EBYSBirimMenu> ebysBirimMenuList = new ArrayList<EBYSBirimMenu>();
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			EBYSBirimMenu ebysBirimMenu = new EBYSBirimMenu();
			
			BigDecimal bsm2servis_id =(BigDecimal)map.get("BSM2SERVIS_ID");
			BigDecimal masterId = (BigDecimal)map.get("MASTERID");
			String servisAdi = (String)map.get("SERVISADI");
			String ebysKodu = (String)map.get("EBYSKODU");
			
			if(bsm2servis_id != null)
				ebysBirimMenu.setId(String.valueOf(bsm2servis_id.longValue()));
			if(masterId != null)
				if(masterId.longValue() == 0 )
					ebysBirimMenu.setParent(String.valueOf("#"));
				else
					ebysBirimMenu.setParent(String.valueOf(masterId.longValue()));
			if(servisAdi != null)
				ebysBirimMenu.setText( servisAdi);
			if(bsm2servis_id != null)
				ebysBirimMenu.setData(String.valueOf(bsm2servis_id.longValue()));
			
			ebysBirimMenuList.add(ebysBirimMenu);
		}
		
		return ebysBirimMenuList;
	}

	@Override
	public List<BelgeYonetimKullanici> getDocManagementUserList(long persid) {
		String sql =" SELECT A.ID||'-'||B.MSM2ORGANIZASYON_ID P1, " + 
			    " A.ADI||' '||A.SOYADI||'('|| (SELECT TANIM FROM MSM2ORGANIZASYON WHERE ID = B.MSM2ORGANIZASYON_ID) ||')' P2" +
			    " FROM IHR1PERSONEL A,IHR1PERSONELORGANIZASYON B " + 
			    " WHERE A.ID = B.IHR1PERSONEL_ID " +
			    " AND A.ID = " + persid ;
		
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<BelgeYonetimKullanici> belgeYonetimKullaniciList = new ArrayList<BelgeYonetimKullanici>();
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			BelgeYonetimKullanici belgeYonetimKullanici = new BelgeYonetimKullanici();
			
			String id = (String)map.get("P1");
			String tanim = (String)map.get("P2");
			
			if(id != null)
				belgeYonetimKullanici.setId(id);
			if(tanim != null)
				belgeYonetimKullanici.setTanim(tanim);
			
			belgeYonetimKullaniciList.add(belgeYonetimKullanici);
		}
		return belgeYonetimKullaniciList;
	}
	
	public List<GraphGeneral> getDocManagementGraphIncomingDoc(long year, long servisId, String result){
		String condSql = "";
		if(result.equals("A"))
			condSql = "";
		else if(result.equals("T"))
			condSql = "AND SONUCDURUMU = 'T'";
		else if(result.equals("I"))
			condSql = "AND SONUCDURUMU = 'I'";
		else if(result.equals("E"))
			condSql = "AND SONUCDURUMU = 'E'";
		else if(result.equals("B"))
			condSql = "AND SONUCDURUMU = 'B'";
		
		
		String sql = "select A.GON_BSM2SERVIS_ID,(SELECT TANIM FROM BSM2SERVIS WHERE ID = A.GON_BSM2SERVIS_ID) ADI,COUNT(*)TOPLAM "+ 
				 " from EDM1ISAKISIADIM A "+ 
				 " WHERE F_Yil(TARIHSAAT) =  " + year +  
				 " AND ALC_BSM2SERVIS_ID =  "+ servisId +
				 " AND GON_MSM2ORGANIZASYON_ID != ALC_MSM2ORGANIZASYON_ID "+ condSql +
				 " GROUP BY A.GON_BSM2SERVIS_ID";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal gonBsm2servisId = (BigDecimal)map.get("GON_BSM2SERVIS_ID");
			String adi = (String) map.get("ADI");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");

			if(gonBsm2servisId != null)
				graphGeneral.setId(gonBsm2servisId.longValue());
			if(adi != null)
				graphGeneral.setAdi(adi);
			if(toplam != null)
				graphGeneral.setTutar(toplam.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getDocManagementGraphOutgoingDoc(long year,long servisId,String result){
		String condSql = "";
		if(result.equals("A"))
			condSql = "";
		else if(result.equals("T"))
			condSql = "AND SONUCDURUMU = 'T'";
		else if(result.equals("I"))
			condSql = "AND SONUCDURUMU = 'I'";
		else if(result.equals("E"))
			condSql = "AND SONUCDURUMU = 'E'";
		else if(result.equals("B"))
			condSql = "AND SONUCDURUMU = 'B'";
		
		String sql = " select A.ALC_BSM2SERVIS_ID,(SELECT TANIM FROM BSM2SERVIS WHERE ID = A.ALC_BSM2SERVIS_ID) ADI,COUNT(*) TOPLAM"
				+" from EDM1ISAKISIADIM A"  
				+" WHERE F_Yil(TARIHSAAT) =" + year
				+" AND  a.alc_bsm2servis_id >0" 
				+" AND GON_BSM2SERVIS_ID= " + servisId 
				+" AND GON_MSM2ORGANIZASYON_ID != ALC_MSM2ORGANIZASYON_ID " + condSql
				+" GROUP BY A.ALC_BSM2SERVIS_ID";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal gonBsm2servisId = (BigDecimal)map.get("GON_BSM2SERVIS_ID");
			String adi = (String) map.get("ADI");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");

			if(gonBsm2servisId != null)
				graphGeneral.setId(gonBsm2servisId.longValue());
			if(adi != null)
				graphGeneral.setAdi(adi);
			if(toplam != null)
				graphGeneral.setTutar(toplam.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getEbysBusinessGraph(long rolid,long servisMudurluk,String action){
		String sql = "select b.FSM1ROLES_PERFORMER , (Select  substr(x.TANIM,0,30) From "
				+" FSM1ROLES x WHERE X.ID = b.FSM1ROLES_PERFORMER) ADI,COUNT(*) TOPLAM,(select birimyoneticisi"
				+" from fsm1roles where id ="+ rolid +")from  ABPMWORKFLOW a, ABPMWORKITEM b  WHERE "
				+" A.ID = b.ABPMWORKFLOW_ID  AND b.FSM1ROLES_PERFORMER > 0 AND NOT EXISTS "
				+" (SELECT 1 FROM FSM1ROLES WHERE ID=B.FSM1ROLES_PERFORMER AND NVL(ISACTIVE,'H')='H')" 
				+" AND EXISTS (SELECT 1 FROM FSM1ROLES AA WHERE ID=b.FSM1ROLES_PERFORMER AND AA.ISACTIVE='E')"
				+" AND B.BSM2SERVISMUDURLUK_PERFORMER   ="+ servisMudurluk   
				+" AND EXISTS (SELECT 1 FROM ABPMTASK AA WHERE AA.ID=B.ABPMTASK_ID) AND "
				+" B.ACTION='"+ action + "'"  
				+" Group By b.FSM1ROLES_PERFORMER ";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal)map.get("FSM1ROLES_PERFORMER");
			String adi = (String) map.get("ADI");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");

			if(id!= null)
				graphGeneral.setId(id.longValue());
			if(adi != null)
				graphGeneral.setAdi(adi);
			if(toplam != null)
				graphGeneral.setTutar(toplam.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getEbysBusinessGraphDetail(long rolid,long servisMudurluk, String action,long rolPerformerId){
		String sql = "select nvl(D.SPECIALNAME,'Diger')SPECIALNAME,DECODE(D.SPECIALNAME,NULL,"
				+" 'Diger','IADE','Iade Edilen','ONAYBEKLEYEN','Onay Bekleyen','GONDERIMBEKLEYEN',"
				+" 'Gonderim Bekleyen','GELEN','Kisiye Atanan',D.SPECIALNAME) TANIM,COUNT(*) TOPLAM,"
				+" b.FSM1ROLES_PERFORMER,(select birimyoneticisi from fsm1roles where id ="+ rolid + ")"
				+" from  ABPMWORKFLOW a, ABPMWORKITEM b,abpmtask c,abpmtaskgroup d  WHERE A.ID ="
				+" b.ABPMWORKFLOW_ID  AND b.ABPMTASK_ID = c.id  AND c.ABPMTASKGROUP_ID = D.id  "
				+" AND b.FSM1ROLES_PERFORMER > 0  AND NOT EXISTS (SELECT 1 FROM FSM1ROLES WHERE "
				+" ID=B.FSM1ROLES_PERFORMER AND NVL(ISACTIVE,'H')='H')  AND EXISTS "
				+" (SELECT 1 FROM FSM1ROLES AA WHERE ID=b.FSM1ROLES_PERFORMER AND AA.ISACTIVE='E')" 
				+" AND B.BSM2SERVISMUDURLUK_PERFORMER   ="+ servisMudurluk   
				+" AND EXISTS (SELECT 1 FROM ABPMTASK AA WHERE AA.ID=B.ABPMTASK_ID) AND B.ACTION='"+ action+"'" 
				+" and b.fsm1roles_performer=" + rolPerformerId 
				+" Group By SPECIALNAME ,b.FSM1ROLES_PERFORMER";   
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			String adi = (String) map.get("TANIM");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");

			if(adi != null)
				graphGeneral.setAdi(adi);
			if(toplam != null)
				graphGeneral.setTutar(toplam.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
}
