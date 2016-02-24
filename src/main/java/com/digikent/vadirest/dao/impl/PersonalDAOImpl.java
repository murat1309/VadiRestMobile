package com.digikent.vadirest.dao.impl;

import com.digikent.vadirest.dao.PersonalDAO;
import com.digikent.vadirest.dto.AracTalep;
import com.digikent.vadirest.dto.Izin;
import com.digikent.vadirest.dto.OdulCeza;
import com.digikent.vadirest.dto.PDKSInformation;
import com.digikent.vadirest.dto.Person;
import com.vadi.digikent.memur.ikm.model.HR3Hesap;
import com.vadi.digikent.memur.ikm.model.HR3HesapTuru;
import com.vadi.digikent.ortak.model.EILElektronikPosta;
import com.vadi.digikent.ortak.model.EILTelefon;
import com.vadi.digikent.ortak.model.EILTelefonTuru;
import com.vadi.digikent.personel.per.model.HR1Egitim;
import com.vadi.digikent.personel.per.model.HR1Personel;
import com.vadi.digikent.personel.per.model.HR1PersonelIslem;
import com.vadi.digikent.personel.per.model.HR1PersonelProfile;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;



@Repository("personalDao")
@Transactional
public class PersonalDAOImpl implements PersonalDAO {
	
	@Autowired
	protected SessionFactory sessionFactory;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	
	public Person findPersonelInformationById(long persid) {
		String sql = "SELECT IP.ID ID,IP.ADI||' '||IP.SOYADI ADSOYAD ,IP.TCKIMLIKNO,IP.DOGUMTARIHI "
				+ ",(SELECT TANIM FROM   LHR1GOREVTURU  WHERE ID=LHR1GOREVTURU_ID) GOREVI "
				+ ",(SELECT TANIM FROM   BSM2SERVIS  WHERE ID=BSM2SERVIS_GOREV) GOREVMUDURLUGU "
				+ ",(SELECT TANIM FROM   BSM2SERVIS  WHERE ID=BSM2SERVIS_KADRO) KADROMUDURLUGU "
				+ ",(SELECT TANIM FROM AHR1KADROSINIFI WHERE ID =AHR1KADROSINIFI_ID) KADROSINIFI "
				+ ",(SELECT NVL(KADRODERECESI,0) FROM ZHR1KADRO WHERE ID=ZHR1KADRO_ID ) KADRODERECESI "
				+ ",(SELECT TANIM FROM LHR1GOREVTURU WHERE ID=(SELECT LHR1GOREVTURU_ID FROM ZHR1KADRO  WHERE ID=ZHR1KADRO_ID))  KADRO "
				+ ",TURU,KANGRUBU, "
				+ "(select FILENAME from JHR1PERSONELRESIM where IHR1PERSONEL_ID = ip.id AND ROWNUM <= 1) DOSYAADI, "   
				+ "(select FILETYPE from JHR1PERSONELRESIM where IHR1PERSONEL_ID = ip.id AND ROWNUM <= 1) DOSYATURU , "   
                + "(select ICERIK from JHR1PERSONELRESIM where IHR1PERSONEL_ID = ip.id AND ROWNUM <= 1) ICERIK "
				+ "FROM IHR1PERSONEL IP WHERE IP.ID= "
				+ persid + " ";

		List list = new ArrayList<Object>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		Person person = new Person();
		for (Object o : list) {
			Map map = (Map) o;
			int imageLength;

			BigDecimal id = (BigDecimal) map.get("ID");
			String adsoyad = (String) map.get("ADSOYAD");
			BigDecimal tcno = (BigDecimal) map.get("TCKIMLIKNO");
			Date dTarihi = (Date) map.get("DOGUMTARIHI");
			String gorevi = (String) map.get("GOREVI");
			String gorevmudurlugu = (String) map.get("GOREVMUDURLUGU");
			String kadromudurlugu = (String) map.get("KADROMUDURLUGU");
			String kadrosinifi = (String) map.get("KADROSINIFI");
			BigDecimal kadroderecesi = (BigDecimal) map.get("KADRODERECESI");
			String kadro = (String) map.get("KADRO");
			String turu = (String) map.get("TURU");
			String kangrubu = (String) map.get("KANGRUBU");		
			String dosyaAdi = (String) map.get("DOSYAADI");
			String dosyaTuru = (String) map.get("DOSYATURU");
			Blob icerik = (Blob) map.get("ICERIK");
			
			if(adsoyad != null)
				person.setAdSoyad(adsoyad);
			if(gorevi != null)
				person.setGorevi(gorevi);
			if(gorevmudurlugu != null)
				person.setGorevMudurlugu(gorevmudurlugu);
			if(id != null)
				person.setId(id.longValue());
			if(dTarihi != null)
				person.setDogumTarihi(dateFormat.format(dTarihi));
			if(kadromudurlugu != null)
				person.setKadro(kadromudurlugu);
			if(kadroderecesi != null)
				person.setKadroDerecesi(kadroderecesi.longValue());
			if(kadrosinifi != null)
				person.setKadroSinif(kadrosinifi);
			if(kangrubu != null)
				person.setKanGrubu(kangrubu);
			if(tcno != null)	
				person.setTcKimlikNo(tcno.longValue());
			if(turu != null)
				person.setTuru(turu);
			if(dosyaAdi != null)
				person.setDosyaAdi(dosyaAdi);
			if(dosyaTuru != null)
				person.setDosyaTuru(dosyaTuru);
			
			try {
				if (icerik!=null){
					imageLength = (int) icerik.length();
					person.setIcerik(icerik.getBytes(1, imageLength));
					icerik.free();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return person;
	}
	
	public List<EILTelefon> getPhoneNumberById(long persid){
		String sql = "SELECT B.TANIM, T.TELEFONNUMARASI TELNO, T.DAHILI DAHILI FROM AEILTELEFON T, AEILTELEFONTURU B"
				+ " WHERE T.IHR1PERSONEL_ID>0 AND T.AEILTELEFONTURU_ID = B.ID AND T.IHR1PERSONEL_ID = "
				+ persid + "";

		List list = new ArrayList<Object>();
		List<EILTelefon> telList = new ArrayList<EILTelefon>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {

			Map map = (Map) o;
			EILTelefon tel = new EILTelefon();
			EILTelefonTuru telTuru = new EILTelefonTuru();

			String tanim = (String) map.get("TANIM");
			BigDecimal telNo = (BigDecimal) map.get("TELNO");
			String dahili = (String) map.get("DAHILI");

			if (tanim != null)
				telTuru.setTanim(tanim);
			tel.setEILTelefonTuru(telTuru);
			if (telNo != null)
				tel.setTelefonnumarasi(telNo.longValue());
			if (dahili != null)
				tel.setDahili(dahili);

			telList.add(tel);

		}

		return telList;
	}
	
	public List<EILElektronikPosta> getMailById(long persid){
		String sql = "SELECT E.ELEKTRONIKPOSTA, E.KURUMSALEPOSTA FROM AEILELEKTRONIKPOSTA E  WHERE E.IHR1PERSONEL_ID>0 AND E.IHR1PERSONEL_ID = "
				+ persid + "";

		List list = new ArrayList<Object>();
		List<EILElektronikPosta> epostaList = new ArrayList<EILElektronikPosta>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {

			Map map = (Map) o;
			EILElektronikPosta ePosta = new EILElektronikPosta();

			String mail = (String) map.get("ELEKTRONIKPOSTA");
			String kurumsal = (String) map.get("KURUMSALEPOSTA");

			ePosta.setElektronikposta(mail);
			ePosta.setKurumsaleposta(kurumsal);

			epostaList.add(ePosta);
		}

		return epostaList;
	}

	public List<HR3Hesap> getSalaryInformationByYear(long persid, int year){
		
		String sql = "SELECT IB.ID,IB.IHR1PERSONEL_ID,IB.NHR3HESAPDONEM_ID,IB.MHR3HESAPTURU_ID,IB.YILI,IB.AY,IB.SIRA "
				+ ",IB.CRUSER,IB.CRDATE,IB.UPDUSER,IB.UPDDATE,IB.DELETEFLAG,IB.UPDSEQ,IB.BRUTTUTAR,IB.NETTUTAR,IB.ODENENTUTAR,IB.KESINTITUTARI "
				+ ",(select tanim from bsm2servis where id=IP.bsm2servis_kadro) as KADRO "
				+ ",(select tanim from bsm2servis where id=IP.bsm2servis_gorev) as GOREV "
				+ ",(SELECT ODEMETARIHI FROM NHR3HESAPDONEM WHERE ID=IB.NHR3HESAPDONEM_ID) ODEMETARIHI"
				+ ",(SELECT TANIM FROM NHR3HESAPDONEM WHERE ID=IB.NHR3HESAPDONEM_ID) MAASTANIM"
				+ " FROM GHR3HESAP IB,IHR1PERSONEL IP "
				+ "WHERE IB.IHR1PERSONEL_ID = IP.ID AND IB.IHR1PERSONEL_ID = "
				+ persid
				+ " AND IB.YILI = "
				+ year
				+ ""
				+ "AND EXISTS (SELECT 1 FROM NHR3HESAPDONEM WHERE ID=IB.NHR3HESAPDONEM_ID AND ODEMETARIHI IS NOT NULL ) "
				+ "order by  IB.YILI DESC ,IB.AY DESC ";

		List list = new ArrayList<Object>();
		List<HR3Hesap> maasBilgilerim = new ArrayList<HR3Hesap>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;

			HR3Hesap hesap = new HR3Hesap();

			BigDecimal id = (BigDecimal) map.get("ID");
			BigDecimal ihr1personel_id = (BigDecimal) map
					.get("IHR1PERSONEL_ID");
			BigDecimal nhr3hesapdonem_id = (BigDecimal) map
					.get("NHR3HESAPDONEM_ID");
			BigDecimal mhr3hesapturu_id = (BigDecimal) map
					.get("MHR3HESAPTURU_ID");

			BigDecimal yili = (BigDecimal) map.get("YILI");
			BigDecimal ay = (BigDecimal) map.get("AY");
			BigDecimal sira = (BigDecimal) map.get("SIRA");
			BigDecimal updUser = (BigDecimal) map.get("UPDUSER");
			Date updDate = (Date) map.get("UPDDATE");
			// deleteflag
			BigDecimal crUser = (BigDecimal) map.get("CRUSER");
			Date crDate = (Date) map.get("CRDATE");
			// updSeq
			BigDecimal bruttutar = (BigDecimal) map.get("BRUTTUTAR");
			BigDecimal nettutar = (BigDecimal) map.get("NETTUTAR");
			BigDecimal odenentutar = (BigDecimal) map.get("ODENENTUTAR");
			BigDecimal kesinti = (BigDecimal) map.get("KESINTITUTARI");

			String kadro = (String) map.get("KADRO");
			String gorev = (String) map.get("GOREV");

			Date odemeTarihi = (Date) map.get("ODEMETARIHI");
			String maasAdi = (String) map.get("MAASTANIM");

			hesap.setId(id.longValue());

			HR1Personel personel = new HR1Personel();
			personel.setId(ihr1personel_id.longValue());
			hesap.setHr1Personel(personel);

			// HR3HesapDonem
			HR3HesapTuru hesapTuru = new HR3HesapTuru();
			hesapTuru.setId(mhr3hesapturu_id.longValue());
			hesap.setHr3HesapTuru(hesapTuru);

			hesap.setYili(yili.longValue());
			hesap.setAy(ay.longValue());
			hesap.setSira(sira.longValue());
			if (updUser != null)
				hesap.setUpdUser(updUser.longValue());
			if (updDate != null)
				hesap.setUpdDate(updDate);
			hesap.setCrUser(crUser.longValue());
			hesap.setCrDate(crDate);
			hesap.setBrutTutar(bruttutar.doubleValue());
			hesap.setNetTutar(nettutar.doubleValue());
			hesap.setOdenenTutar(odenentutar.doubleValue());
			hesap.setKesintiTutari(kesinti.doubleValue());
			hesap.setKadro(kadro);
			hesap.setGorev(gorev);
			hesap.setOdemeTarihi(odemeTarihi);
			hesap.setMaasAdi(maasAdi);

			maasBilgilerim.add(hesap);

		}

		return maasBilgilerim;
	}

	public List<HR1PersonelProfile> findPersonalInformation() {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session
				.createCriteria(HR1PersonelProfile.class)
				.setProjection(
						Projections
								.projectionList()
								.add(Projections.property("adi"), "adi")
								.add(Projections.property("soyadi"), "soyadi")
								.add(Projections.property("bsm2ServisGorev"),
										"bsm2ServisGorev"))
				.setResultTransformer(
						Transformers.aliasToBean(HR1PersonelProfile.class));

		List<HR1PersonelProfile> list = cr.list();

		for (HR1PersonelProfile pp : list) {
			pp.setGorevMudurlugu(pp.getBsm2ServisGorev() != null ? pp
					.getBsm2ServisGorev().getTanim() : "");
		}

		return (List<HR1PersonelProfile>) list;
	}

	public List<HR1Egitim> getPersonalEducation(long persid){
		String sql = "SELECT ID, IHR1PERSONEL_ID, FHR1EGITIMTURU_ID, TARIH, IZAHAT, SONUCU, "
				+ " BITISTARIHI, GUNSAYISI, KONUSU, SAAT, "
				+ " FHR1EGITIMBIRIM_ID,  FHR1EGITIMGENELLINE_ID, "
				+ " KATILIMDURUMU, SERTIFIKAADI, SERTIFIKADURUMU, "
				+ " TUMSEANSLARAKATILIM FROM FHR1EGITIM "
				+ " WHERE IHR1PERSONEL_ID = "
				+ persid
				+ " AND NVL (fhr1egitimgenelline_id, 0) = 0 UNION ALL "
				+ " SELECT ID, IHR1PERSONEL_ID, FHR1EGITIMTURU_ID, TARIH, IZAHAT, SONUCU, "
				+ " BITISTARIHI, GUNSAYISI, KONUSU, SAAT, "
				+ " FHR1EGITIMBIRIM_ID,  FHR1EGITIMGENELLINE_ID, "
				+ " KATILIMDURUMU, SERTIFIKAADI, SERTIFIKADURUMU, TUMSEANSLARAKATILIM FROM FHR1EGITIM "
				+ " WHERE ihr1personel_id = "
				+ persid
				+ " AND ID = (SELECT MAX (ID) FROM fhr1egitim x "
				+ " WHERE x.fhr1egitimgenelline_id IN ( SELECT ID FROM fhr1egitimgenelline WHERE fhr1egitimgenel_id = "
				+ " (SELECT fhr1egitimgenel_id FROM fhr1egitimgenelline WHERE ID = fhr1egitim.fhr1egitimgenelline_id)) "
				+ " AND x.katilimdurumu = '1' AND x.ihr1personel_id = fhr1egitim.ihr1personel_id) order by tarih desc";

		List list = new ArrayList<Object>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		List<HR1Egitim> egitimList = new ArrayList<HR1Egitim>();

		for (Object o : list) {
			Map map = (Map) o;
			HR1Egitim egitim = new HR1Egitim();

			BigDecimal id = (BigDecimal) map.get("ID");
			// BigDecimal ihr1personel_id = (BigDecimal)
			// map.get("IHR1PERSONEL_ID");
			// BigDecimal fhr1egitimturu_id = (BigDecimal)
			// map.get("FHR1EGITIMTURU_ID");
			Date tarih = (Date) map.get("TARIH");
			String izahat = (String) map.get("IZAHAT");
			String sonucu = (String) map.get("SONUCU");
			Date bitisTarihi = (Date) map.get("BITISTARIHI");
			BigDecimal gunSayisi = (BigDecimal) map.get("GUNSAYISI");
			String konusu = (String) map.get("KONUSU");
			BigDecimal saat = (BigDecimal) map.get("SAAT");
			// BigDecimal fhr1egitimbirim_id = (BigDecimal)
			// map.get("FHR1EGITIMBIRIM_ID");
			// BigDecimal fhr1egitimgenelline_id = (BigDecimal)
			// map.get("FHR1EGITIMGENELLINE_ID");
			String katilimDurumu = (String) map.get("KATILIMDURUMU");
			String sertifikaAdi = (String) map.get("SERTIFIKAADI");
			String sertifikaDurumu = (String) map.get("SERTIFIKADURUMU");
			String tumseanslarakatilim = (String) map
					.get("TUMSEANSLARAKATILIM");

			if (id != null)
				egitim.setId(id.longValue());
			if (tarih != null)
				egitim.setTarih(tarih);
			if (izahat != null)
				egitim.setIzahat(izahat);
			if (sonucu != null)
				egitim.setSonucu(sonucu);
			if (bitisTarihi != null)
				egitim.setBitistarihi(bitisTarihi);
			if (gunSayisi != null)
				egitim.setGunSayisi(gunSayisi.longValue());
			if (konusu != null)
				egitim.setKonusu(konusu);
			if (saat != null)
				egitim.setSaat(saat.longValue());
			if (katilimDurumu != null)
				egitim.setKatilimDurumu(katilimDurumu);
			if (sertifikaAdi != null)
				egitim.setSertifikaAdi(sertifikaAdi);
			if (sertifikaDurumu != null)
				egitim.setSertifikaDurumu(sertifikaDurumu);
			if (tumseanslarakatilim != null)
				egitim.setTumSeanslaraKatilim(tumseanslarakatilim);

			egitimList.add(egitim);

		}

		return egitimList;
	}

	public List<HR1PersonelIslem> getServiceInformation(long persid) {
		String sql = "SELECT PI.ID, PI.IHR1PERSONEL_ID, PI.TURU, "
				+ "(SELECT TANIM FROM LHR1GOREVTURU WHERE ID= LHR1GOREVTURU_ID) AS GOREVI, "
				+ "(SELECT TANIM FROM BSM2SERVIS WHERE ID=BSM2SERVIS_KADRO) AS KADROYERI, "
				+ "(SELECT TANIM FROM BSM2SERVIS WHERE ID=BSM2SERVIS_GOREV) AS GOREVYERI, "
				+ "PI.BASLAMATARIHI, PI.AYRILMATARIHI, PI.ODENECEKAYLIGAESASEKGOSTERGE, PI.KAZANCEKGOSTERGE, PI.EMEKLIEKGOSTERGE, PI.KADRODERECESI "
				+ "FROM MHR1PERSONELISLEM PI "
				+ "WHERE PI.TURU NOT IN ('0','1') AND PI.PORTALDEGORUNTULENSIN = 'E' AND PI.IHR1PERSONEL_ID = "
				+ persid + ""
				+ " ORDER BY PI.BASLAMATARIHI DESC, PI.AYRILMATARIHI DESC";

		List list = new ArrayList<Object>();
		List<HR1PersonelIslem> hr1PersonelIslems = new ArrayList<HR1PersonelIslem>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {

			HR1PersonelIslem hr1PersonelIslem = new HR1PersonelIslem();

			Map map = (Map) o;

			BigDecimal id = (BigDecimal) map.get("ID");
			BigDecimal ihr1PersonelId = (BigDecimal) map.get("IHR1PERSONEL_ID");
			String turu = (String) map.get("TURU");
			String gorevi = (String) map.get("GOREVI");
			String kadroyeri = (String) map.get("KADROYERI");
			String gorevyeri = (String) map.get("GOREVYERI");
			Date baslamatarihi = (Date) map.get("BASLAMATARIHI");
			Date ayrilmatarihi = (Date) map.get("AYRILMATARIHI");
			BigDecimal odenecekayligaesasekgosterge = (BigDecimal) map
					.get("ODENECEKAYLIGAESASEKGOSTERGE");
			BigDecimal kazancekgosterge = (BigDecimal) map
					.get("KAZANCEKGOSTERGE");
			BigDecimal emekliekgosterge = (BigDecimal) map
					.get("EMEKLIEKGOSTERGE");
			BigDecimal kadroDerecesi = (BigDecimal) map.get("KADRODERECESI");

			if (id != null)
				hr1PersonelIslem.setId(id.longValue());
			if (ihr1PersonelId != null) {
				HR1Personel hr1Personel = new HR1Personel();
				hr1Personel.setId(ihr1PersonelId.longValue());
				hr1PersonelIslem.setHr1Personel(hr1Personel);
			}
			hr1PersonelIslem.setTuru((turu != null) ? turu : "-");
			hr1PersonelIslem.setGorevi((gorevi != null) ? gorevi : "-");
			hr1PersonelIslem
					.setKadroYeri((kadroyeri != null) ? kadroyeri : "-");
			hr1PersonelIslem
					.setGorevYeri((gorevyeri != null) ? gorevyeri : "-");
			hr1PersonelIslem.setBaslamaTarihi(baslamatarihi);
			hr1PersonelIslem.setAyrilmaTarihi(ayrilmatarihi);

			if (odenecekayligaesasekgosterge != null)
				hr1PersonelIslem
						.setOdenecekAyligaEsasEkGosterge(odenecekayligaesasekgosterge
								.longValue());
			if (kazancekgosterge != null)
				hr1PersonelIslem.setKazancEkGosterge(kazancekgosterge
						.longValue());
			if (emekliekgosterge != null)
				hr1PersonelIslem.setEmekliEkGosterge(emekliekgosterge
						.longValue());

			if (kadroDerecesi != null)
				hr1PersonelIslem.setKadroDerecesi(kadroDerecesi.longValue());

			hr1PersonelIslems.add(hr1PersonelIslem);
		}

		return hr1PersonelIslems;
	}

	public List<PDKSInformation> getPDKSInformation(long persid){
		String sql = "SELECT P.id ID "
				+ " , DECODE(P.TURU,'M','Memur','S','Sozlesmeli','I','Isci','G','Gecici','F','Firma Personeli','L','Meclis','O','Stajyer') TURU, ADI,SOYADI "
				+ " , P.PDKS_ID PDKSID"
				+ " , (SELECT TANIM FROM BSM2SERVIS WHERE ID=P.BSM2SERVIS_GOREV) DURUM "
				+ " , null TARIHSAAT "
				+ " , null ISLEM "
				+ " , DECODE(KURUMDISIMI ,'H','HAYIR','E','EVET')KURUMDISIMI "
				+ " , 0 GEREKCE_ID "
				+ " , (SELECT KAYITOZELISMI FROM YHR1IZINTURU WHERE ID= "
				+ " (SELECT YHR1IZINTURU_ID FROM UHR1IZIN WHERE IHR1PERSONEL_ID=P.ID AND TURU='K' AND TO_DATE(SYSDATE,'DD/MM/RRRR') "
				+ " BETWEEN AYRILMATARIHI AND DONUSTARIHI AND ROWNUM=1)) GEREKCEADI "
				+ " , 0 GIRISCIKIS_ID "
				+ " , 0 CIHAZNO,NULL CIHAZGRUBU,NULL CIHAZYERI, null TARIH "
				+ " FROM  IHR1PERSONEL P "
				+ " WHERE 1=1 AND CIKISTARIHI IS NULL AND PDKSDURUMU='K' "
				+ "and p.id = " + persid + "";

		List list = new ArrayList<Object>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		List<PDKSInformation> pdksList = new ArrayList<PDKSInformation>();

		for (Object o : list) {
			Map map = (Map) o;
			PDKSInformation pdksInformation = new PDKSInformation();

			BigDecimal id = (BigDecimal) map.get("ID");
			String turu = (String) map.get("TURU");
			String adi = (String) map.get("ADI");
			String soyadi = (String) map.get("SOYADI");
			BigDecimal pdksId = (BigDecimal) map.get("PDKSID");
			String durum = (String) map.get("DURUM");
			String tarihsaat = (String) map.get("TARIHSAAT");
			String islem = (String) map.get("ISLEM");
			String kurumdisimi = (String) map.get("KURUMDISIMI");
			// BigDecimal gerekceId = (BigDecimal) map.get("GEREKCE_ID");
			String gerekceAdi = (String) map.get("GEREKCEADI");
			// BigDecimal giriscikisId = (BigDecimal) map.get("GIRISCIKIS_ID");
			BigDecimal cihazNo = (BigDecimal) map.get("CIHAZNO");
			String cihazGrubu = (String) map.get("CIHAZGRUBU");
			String cihazYeri = (String) map.get("CIHAZYERI");
			Date tarih = (Date) map.get("TARIH");

			if (id != null)
				pdksInformation.setId(id.longValue());
			if (turu != null)
				pdksInformation.setTuru(turu);
			if (adi != null)
				pdksInformation.setAdi(adi);
			if (soyadi != null)
				pdksInformation.setSoyAdi(soyadi);
			if (durum != null)
				pdksInformation.setDurum(durum);
			if (tarihsaat != null)
				pdksInformation.setTarihSaat(tarihsaat);
			if (islem != null)
				pdksInformation.setIslem(islem);
			if (kurumdisimi != null)
				pdksInformation.setKurumDisiMi(kurumdisimi);
			if (tarih != null)
				pdksInformation.setTarih(dateFormat.format(tarih));

			pdksList.add(pdksInformation);
		}

		return pdksList;
	}

	public List<Izin> getHolidaysEarned(long persid){
		String sql="select id,syn_client_id,syn_language_id,syn_org_id,vas_task_id,vas_warehouse_id,vas_workflow_id,yhr1izinturu_id,(SELECT TANIM FROM YHR1IZINTURU WHERE ID=YHR1IZINTURU_ID) as izinTuru,"
				+ "hakedilen,hakedilengun,hakedilensaat,hakedilendakika,ihr1personel_id,yili,baslangictarihi,bitistarihi,crdate,cruser,"
				+ "deleteflag,isactive,upddate,updseq,upduser,ACIKLAMA,(SELECT TANIM FROM YHR1IZINTURU WHERE ID=UHR1IZINHAKEDIS.YHR1IZINTURU_ID) as izin_turu,"
				+ "NVL(HESAPLAMATURU,'GUN'),HR1V1.F_KULLANILAN_IZIN(UHR1IZINHAKEDIS.ID),HR1V1.F_IPTAL_IZIN(UHR1IZINHAKEDIS.ID)"
				+ " ,case when(HESAPLAMATURU='GUN') then to_char(hakedilenGun) || ' g�n '"
                +" when(HESAPLAMATURU='SAAT' ) then (case when ((floor(hakedilensaat/8))>0) then to_char(floor(hakedilensaat/8)) || ' g�n ' else '' end) "
                +"     ||  (case when (mod(hakedilensaat,8)>0) then to_char(mod(hakedilensaat,8)) || ' saat ' else ' ' end ) "
                +" when (HESAPLAMATURU='DAKIKA') then ( case when (floor( hakedilendakika/(8*60)) >0) then to_char( floor( hakedilendakika/(8*60)) || ' g�n ' ) else '' end)"
                +"     ||  (case when (mod( floor (hakedilendakika/60),8)>0) then to_char(mod( floor (hakedilendakika/60),8)) || ' saat ' else ' ' end ) "
                +"     ||  (case when (mod (hakedilendakika, 60)>0) then  to_char (mod (hakedilendakika, 60)) || ' dakika' else '' end)"
                +" else to_char(hakedilen) end as hk"
				+ "  from UHR1IZINHAKEDIS where ihr1personel_id = " + persid +" ORDER BY baslangictarihi DESC, YHR1IZINTURU_ID";
		
		List list = new ArrayList<Object>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		List<Izin> izinList = new ArrayList<Izin>();
		
		for(Object o : list){
			Map map = (Map) o;
			Izin izin = new Izin();
			
			String izinTuru = (String) map.get("IZINTURU");
			BigDecimal yil = (BigDecimal)map.get("YILI");
			BigDecimal hakedilen = (BigDecimal) map.get("HAKEDILEN");
			Date baslamaTarihi = (Date)map.get("BASLANGICTARIHI");
			Date bitisTarihi = (Date) map.get("BITISTARIHI");
			
			if(izinTuru != null)
				izin.setIzinTuru(izinTuru);
			if(yil != null)
				izin.setYil(yil.longValue());
			if(hakedilen != null)
				izin.setHakedilenGun(hakedilen.longValue());
			if(baslamaTarihi != null)
				izin.setBaslamaTarihi(dateFormat.format(hakedilen));
			if(bitisTarihi != null)
				izin.setBitisTarihi(dateFormat.format(bitisTarihi));

			izinList.add(izin);
			
		}
		return izinList;

	}

	public List<Izin> getHolidaysUsed(long persid){
		String sql ="select id,syn_client_id,syn_language_id,syn_org_id,vas_task_id,vas_warehouse_id,vas_workflow_id,yhr1izinturu_id,(SELECT TANIM FROM YHR1IZINTURU WHERE ID=YHR1IZINTURU_ID) as izinTuru,yili,kullanilan,kullanilangun,kullanilansaat,kullanilandakika,suretipi,"
				+" ihr1personel_id,ayrilistarihi,bitistarihi,donustarihi,to_char(baslamasaati, 'HH24:MI' ) as baslamasaati,to_char(bitissaati, 'HH24:MI' ) as bitissaati,"
				+" onaytarihi,onaysayisi,ihr1personel_yerinebakacak,yerinebakacakpersonel,izinadresi,izahat,crdate,cruser,"
				+" deleteflag,isactive,upddate,updseq,upduser, (SELECT TANIM FROM YHR1IZINTURU WHERE ID=YHR1IZINTURU_ID),NVL(HESAPLAMATURU,'GUN'),"
				+" (SELECT NVL(SUM(IPTALEDILENGUN),0) FROM UHR1IZINIPTAL WHERE UHR1IZINKULLANIM_ID = UHR1IZINKULLANIM.ID )"
				+" ,(case when(kullanilangun>0) then kullanilangun || ' g�n '  else '' end) "
				+" || (case when(kullanilansaat>0) then kullanilansaat || ' saat '  else '' end)"
				+" || (case when(kullanilandakika>0) then kullanilandakika || ' dakika'  else '' end) as kl"				
				+" from UHR1IZINKULLANIM WHERE IHR1PERSONEL_ID = " + persid + "  order by ayrilistarihi desc";
		
		List list = new ArrayList<Object>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		List<Izin> izinList = new ArrayList<Izin>();
		
		for(Object o : list){
			Map map = (Map) o;
			Izin izin = new Izin();
			
			String izinTuru = (String) map.get("IZINTURU");
			BigDecimal yil = (BigDecimal)map.get("YILI");
			BigDecimal kullanilan = (BigDecimal) map.get("KULLANILAN");
			String baslamaSaati = (String) map.get("BASLAMASAATI");
			String bitisSaati = (String) map.get("BITISSAATI");
			Date ayrilmaTarihi = (Date)map.get("AYRILISTARIHI");
			Date donusTarihi = (Date) map.get("DONUSTARIHI");
			
			if(izinTuru != null)
				izin.setIzinTuru(izinTuru);
			if(yil != null)
				izin.setYil(yil.longValue());
			if(kullanilan != null)
				izin.setKullanilanGun(kullanilan.longValue());
			if(baslamaSaati != null)
				izin.setBaslamaSaati(baslamaSaati);
			if(bitisSaati != null)
				izin.setBitisSaati(bitisSaati);
			if(ayrilmaTarihi != null)
				izin.setAyrilmaTarihi(dateFormat.format(ayrilmaTarihi));
			if(donusTarihi != null)
				izin.setDonusTarihi(dateFormat.format(donusTarihi));
			
			izinList.add(izin);
			
		}
		return izinList;
	}
	
	public List<Izin> getRemainingHolidays(long persid){
		String sql =" select  YHR1IZINTURU_ID,(SELECT TANIM FROM YHR1IZINTURU WHERE ID=YHR1IZINTURU_ID) as izinTuru,yili,baslangictarihi,bitistarihi, aciklama,tanim,HESAPLAMATURU,"
				+ " case when(HESAPLAMATURU='GUN') then (case when(hakedilen>0) then to_char(hakedilen) || ' g�n ' else '' end)"
				+ " when(HESAPLAMATURU='SAAT' ) then (case when ((floor(hakedilen/8))>0) then to_char(floor(hakedilen/8)) || ' g�n ' else '' end) "
				+ "     ||  (case when (mod(hakedilen,8)>0) then to_char(mod(hakedilen,8)) || ' saat ' else ' ' end ) "
				+ " when (HESAPLAMATURU='DAKIKA') then ( case when (floor( hakedilen/(8*60)) >0) then to_char( floor( hakedilen/(8*60)) || ' g�n ' ) else '' end)"
				+ "     ||  (case when (mod( floor (hakedilen/60),8)>0) then to_char(mod( floor (hakedilen/60),8)) || ' saat ' else ' ' end ) "
				+ "     ||  (case when (mod (hakedilen, 60)>0) then  to_char (mod (hakedilen, 60)) || ' dakika' else '' end)"
				+ " else to_char(hakedilen) end as hakedilen,"
				+ " case when(HESAPLAMATURU='GUN') then (case when(kullanilan>0) then to_char(kullanilan) || ' g�n ' else '' end)"
				+ " when(HESAPLAMATURU='SAAT' ) then (case when ((floor(kullanilan/8))>0) then to_char(floor(kullanilan/8)) || ' g�n ' else '' end) "
				+ "     ||  (case when (mod(kullanilan,8)>0) then to_char(mod(kullanilan,8)) || ' saat ' else ' ' end ) "
				+ " when (HESAPLAMATURU='DAKIKA') then ( case when (floor( kullanilan/(8*60)) >0) then to_char( floor( kullanilan/(8*60)) || ' g�n ' ) else '' end)"
				+ "     ||  (case when (mod( floor (kullanilan/60),8)>0) then to_char(mod( floor (kullanilan/60),8)) || ' saat ' else ' ' end ) "
				+ "     ||  (case when (mod (kullanilan, 60)>0) then  to_char (mod (kullanilan, 60)) || ' dakika' else '' end)"
				+ " else to_char(kullanilan) end as kullanilan,"
				+ " case when(HESAPLAMATURU='GUN') then (case when(iptaledilen>0) then to_char(iptaledilen) || ' g�n ' else '' end)"
				+ " when(HESAPLAMATURU='SAAT' ) then (case when ((floor(iptaledilen/8))>0) then to_char(floor(iptaledilen/8)) || ' g�n ' else '' end) "
				+ "     ||  (case when (mod(iptaledilen,8)>0) then to_char(mod(iptaledilen,8)) || ' saat ' else ' ' end ) "
				+ " when (HESAPLAMATURU='DAKIKA') then ( case when (floor( iptaledilen/(8*60)) >0) then to_char( floor( iptaledilen/(8*60)) || ' g�n ' ) else '' end)"
				+ "     ||  (case when (mod( floor (iptaledilen/60),8)>0) then to_char(mod( floor (iptaledilen/60),8)) || ' saat ' else ' ' end ) "
				+ "     ||  (case when (mod (iptaledilen, 60)>0) then  to_char (mod (iptaledilen, 60)) || ' dakika' else '' end)"
				+ " else to_char(iptaledilen) end as iptaledilen,"
				+ " case when(HESAPLAMATURU='GUN') then to_char(kalan) || ' g�n'"
				+ " when(HESAPLAMATURU='SAAT' ) then (case when ((floor(kalan/8))>0) then to_char(floor(kalan/8)) || ' g�n ' else '' end) "
				+ "     ||  (case when (mod(kalan,8)>0) then to_char(mod(kalan,8)) || ' saat ' else ' ' end ) "
				+ " when (HESAPLAMATURU='DAKIKA') then ( case when (floor( kalan/(8*60)) >0) then to_char( floor( kalan/(8*60)) || ' g�n ' ) else '' end)"
				+ "     ||  (case when (mod( floor (kalan/60),8)>0) then to_char(mod( floor (kalan/60),8)) || ' saat ' else ' ' end ) "
				+ "     ||  (case when (mod (kalan, 60)>0) then  to_char (mod (kalan, 60)) || ' dakika' else '' end)"
				+ " else to_char(kalan) end as kalanizin"
				+ " from ("
				+ "	 	select id,syn_client_id,syn_language_id,syn_org_id,vas_task_id,vas_warehouse_id,vas_workflow_id,yhr1izinturu_id,"
				+ "	 	hakedilen,hakedilengun,hakedilensaat,hakedilendakika,ihr1personel_id,yili,baslangictarihi,bitistarihi,crdate,cruser,"
				+ "  	deleteflag,isactive,upddate,updseq,upduser,ACIKLAMA,(SELECT TANIM FROM YHR1IZINTURU WHERE ID=UHR1IZINHAKEDIS.YHR1IZINTURU_ID) as tanim,"
				+ "  	NVL(HESAPLAMATURU,'GUN') as HESAPLAMATURU,HR1V1.F_KULLANILAN_IZIN(UHR1IZINHAKEDIS.ID) as kullanilan,HR1V1.F_IPTAL_IZIN(UHR1IZINHAKEDIS.ID) as iptaledilen,"
				+ "  	HR1V1.F_KALAN_IZIN(UHR1IZINHAKEDIS.ID) as kalan, 0 as kalan_saat, 0 as kalan_dakika"
				+ "  from UHR1IZINHAKEDIS where ihr1personel_id =" + persid
				+ ") order by yili desc, baslangictarihi desc, YHR1IZINTURU_ID";
		
		List list = new ArrayList<Object>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		List<Izin> izinList = new ArrayList<Izin>();
		
		for(Object o : list){
			Map map = (Map) o;
			Izin izin = new Izin();
			
			String izinTuru = (String) map.get("IZINTURU");
			BigDecimal yil = (BigDecimal)map.get("YILI");
			String hakedilen = (String) map.get("HAKEDILEN");
			String kullanilan = (String) map.get("KULLANILAN");
			String iptalEdilenGun = (String) map.get("IPTALEDILEN");
			String kalanIzin = (String) map.get("KALANIZIN");
			
			if(izinTuru != null)
				izin.setIzinTuru(izinTuru);
			if(yil != null)
				izin.setYil(yil.longValue());
			if(hakedilen != null)
				izin.setHakedilen(hakedilen);
			if(kullanilan != null)
				izin.setKullanilan(kullanilan);
			if(iptalEdilenGun != null)
				izin.setIptalEdilen(iptalEdilenGun);
			if(kalanIzin != null)
				izin.setKalanIzin(kalanIzin);
			
			izinList.add(izin);
			
		}
		return izinList;
	}

	public List<Izin> getCancelledHolidays(long persid){
		String sql ="select  id,syn_client_id,syn_language_id,syn_org_id,vas_task_id,vas_warehouse_id,vas_workflow_id,ihr1personel_id,uhr1izinkullanim_id,uhr1izinhakedis_id,"
				+"  (SELECT TANIM FROM YHR1IZINTURU WHERE ID=YHR1IZINTURU_ID) as izinTuru,yili,iptaledilengun,"
				+"  (SELECT NVL(HESAPLAMATURU,'GUN') FROM YHR1IZINTURU WHERE ID=YHR1IZINTURU_ID),"
				+"  crdate,cruser,deleteflag,isactive,upddate,updseq,upduser,uhr1izinkullanimline_id "
				+"  from UHR1IZINIPTAL where ihr1personel_id =" + persid + " order by yili desc, YHR1IZINTURU_ID";
		
		List list = new ArrayList<Object>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		List<Izin> izinList = new ArrayList<Izin>();
		
		for(Object o : list){
			Map map = (Map) o;
			Izin izin = new Izin();
			
			String izinTuru = (String) map.get("IZINTURU");
			BigDecimal yil = (BigDecimal)map.get("YILI");
			BigDecimal iptalEdilenGun = (BigDecimal) map.get("IPTALEDILENGUN");
			
			if(izinTuru != null)
				izin.setIzinTuru(izinTuru);
			if(yil != null)
				izin.setYil(yil.longValue());
			if(iptalEdilenGun != null)
				izin.setIptalEdilenGun(iptalEdilenGun.longValue());
			
			izinList.add(izin);
			
		}
		return izinList;
	}
	
	public List<OdulCeza> getRewardPenaltyInformation(long persid, String param){
		String sql = "SELECT ID, TURU, SM1KURUM_ID, IHR1PERSONEL_ID, ODULCEZATURU_ID,(SELECT TANIM FROM GHR1CEZATURU WHERE ID=C.ODULCEZATURU_ID) ODULCEZATURU, TARIH, KONUSU, IZAHAT, "
				+ " DECODE(CEZATURU,'I','Adli Ceza','D','Disiplin Cezasi',CEZATURU) CEZATURU, SAYI, VERENKURUM, ESASNUMARASI FROM FHR1CEZA C WHERE "
				+ "ID>0 AND IHR1PERSONEL_ID = "
				+ persid
				+ " AND TURU='"
				+ param + "'";
		
		List list = new ArrayList<Object>();
		List<OdulCeza> oduCezas = new ArrayList<OdulCeza>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			OdulCeza odulCeza = new OdulCeza();

			BigDecimal id = (BigDecimal) map.get("ID");
			String turu = (String) map.get("ODULCEZATURU");
			BigDecimal personel = (BigDecimal) map.get("IHR1PERSONEL_ID");
			BigDecimal odulcezaturu = (BigDecimal) map.get("ODULCEZATURU_ID");
			Date tarih = (Date) map.get("TARIH");
			String konusu = (String) map.get("KONUSU");
			String izahat = (String) map.get("IZAHAT");
			String cezaturu = (String) map.get("CEZATURU");
			String sayi = (String) map.get("SAYI");
			String verenkurum = (String) map.get("VERENKURUM");
			String esasnumarasi = (String) map.get("ESASNUMARASI");


			odulCeza.setTuru(turu != null ? turu : "-");
			odulCeza.setTarih(tarih != null ? dateFormat.format(tarih) : null);
			odulCeza.setKonusu(konusu != null ? konusu : "-");
			odulCeza.setCezaTuru(cezaturu != null ? cezaturu : "-");

			oduCezas.add(odulCeza);
		}

		return oduCezas;
	}
	
	public List<AracTalep> getVehicleRequestInformation(long persid){
		String sql = "select ID,TALEPTARIHI,ARACINKALKISYAPACAGIYER,ARACINGIDECEGIYER,TALEPEDILENCIKISTARIHI,TAHMINIDONUSTARIHI,IZAHAT,"
				+ " (select TANIM from AMT1SONUCDURUMU where id  = (select AMT1SONUCDURUMU_ID from HMT1GOREV where AMT1ARACTALEP_ID = A.id)) SONUCDURUMU"   
				+ " FROM AMT1ARACTALEP A WHERE IHR1PERSONEL_ID =" + persid;
		
		List list = new ArrayList<Object>();
		List<AracTalep> aracTalepList = new ArrayList<AracTalep>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			AracTalep aracTalep = new AracTalep();

			BigDecimal id = (BigDecimal) map.get("ID");
			Date talepTarihi = (Date) map.get("TALEPTARIHI");
			String kalkisYeri = (String) map.get("ARACINKALKISYAPACAGIYER");
			String gidecegiYer = (String) map.get("ARACINGIDECEGIYER");
			Date talepEdilenTarih = (Date) map.get("TALEPEDILENCIKISTARIHI");
			Date donusTarihi = (Date) map.get("TAHMINIDONUSTARIHI");
			String izahat = (String) map.get("IZAHAT");
			String sonucDurumu = (String) map.get("SONUCDURUMU");
			
			if(id != null)
				aracTalep.setId(id.longValue());
			if(talepTarihi != null)
				aracTalep.setTalepEdilenTarih(dateFormat.format(talepTarihi));
			if(kalkisYeri != null)
				aracTalep.setKalkisYeri(kalkisYeri);
			if(gidecegiYer != null)
				aracTalep.setGidecegiYer(gidecegiYer);
			if(talepEdilenTarih != null)
				aracTalep.setCikisTarihi(dateFormat.format(talepEdilenTarih));
			if(donusTarihi != null)
				aracTalep.setDonusTarihi(dateFormat.format(donusTarihi));
			if(izahat != null)
				aracTalep.setIzahat(izahat);
			if(sonucDurumu != null)
				aracTalep.setSonucDurumu(sonucDurumu);
			
		
			aracTalepList.add(aracTalep);
		}

		
		return aracTalepList;
	}
}
