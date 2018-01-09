package com.digikent.vadirest.dao.impl;

import com.digikent.vadirest.dao.CorporateDAO;
import com.digikent.vadirest.dto.Person;
import com.vadi.digikent.personel.per.model.HR1EgitimGenel;
import com.vadi.digikent.personel.per.model.HR1GorevTuru;
import com.vadi.digikent.personel.per.model.HR1Personel;
import com.vadi.digikent.personel.per.model.HR1PersonelIslem;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vadi.smartkent.datamodel.domains.portal.pr1.PR1Haber;
import com.vadi.smartkent.datamodel.domains.portal.pr1.PR1KurumIndirim;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class CorporateDAOImpl implements CorporateDAO {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public List<PR1Haber> getAnnouncement(String param){
		String sql = "SELECT ID,BASLIK,TARIH,RESIM,IZAHAT,SUBSTR(BASLIK,1,23) KISA_BASLIK, "
				+ " SUBSTR(IZAHAT,1,220)||'...' KISA_IZAHAT,KIOSKGOSTER,KURUMICIPORTALGOSTER,MENUDEGOSTER,SONYAYINLANMATARIHI FROM BPR1HABER "
				+ " WHERE 1=1 AND KURUMSALPORTALGOSTER='E' AND TURU = '" + param +"' "
				+ " AND TO_DATE(  NVL(  SONYAYINLANMATARIHI,SYSDATE) ,'dd/MM/RRRR')   >=TO_DATE(SYSDATE,'dd/MM/RRRR') "
				+ " ORDER BY SIRANUMARASI ASC,TARIH DESC";

		List list = new ArrayList<Object>();
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		List<PR1Haber> haberList = new ArrayList<PR1Haber>();

		for (Object o : list) {
			Map map = (Map) o;
			PR1Haber haber = new PR1Haber();

			BigDecimal id = (BigDecimal) map.get("ID");
			String baslik = (String) map.get("BASLIK");
			Date tarih = (Date) map.get("TARIH");
			String izahat = clob2Str((Clob) map.get("IZAHAT"));
			char[] resim = (char[]) map.get("RESIM");
			String filename = (String) map.get("FILENAME");
			String filetype = (String) map.get("FILETYPE");
			String kurumsalPortalGoster = (String) map
					.get("KURUMSALPORTALGOSTER");
			BigDecimal updUser = (BigDecimal) map.get("UPDUSER");
			Date updDate = (Date) map.get("UPDDATE");
			// deleteflag
			BigDecimal crUser = (BigDecimal) map.get("CRUSER");
			Date crDate = (Date) map.get("CRDATE");
			Date sonYayinlanmaTarihi = (Date) map.get("SONYAYINLANMATARIHI");
			// updSeq
			String turu = (String) map.get("TURU");
			String kioskGoster = (String) map.get("KIOSKGOSTER");
			String menudeGoster = (String) map.get("MENUDEGOSTER");
			String kurumiciportalGoster = (String) map
					.get("KURUMICIPORTALGOSTER");
			String kisaBaslik = (String) map.get("KISA_BASLIK");
			String kisaIzahat = clob2Str((Clob) map.get("KISA_IZAHAT"));
			String ilanKategori = (String) map.get("ILANKATEGORI");

			
			if(id != null)
				haber.setID(id.longValue());
			if(baslik != null)
				haber.setBaslik(baslik);
			if(tarih != null)
				haber.setTarih(tarih);
			if(izahat != null)
				haber.setIzahat(izahat);
			if(filename != null)
				haber.setFilename(filename);
			if(filetype != null)
				haber.setFiletype(filetype);
			if(kurumsalPortalGoster != null)
				haber.setKurumsalportalgoster(kurumsalPortalGoster);
			if(updUser != null)
				haber.setUpdUser(updUser.longValue());
			if(updDate != null)
				haber.setUpdDate(updDate);
			if(crUser != null)
				haber.setCrUser(crUser.longValue());
			if(crDate != null)
				haber.setCrDate(crDate);
			if(sonYayinlanmaTarihi != null)
				haber.setSonyayinlanmatarihi(sonYayinlanmaTarihi);
			if(turu != null)
				haber.setTuru(turu);
			if(kioskGoster != null)
				haber.setKioskgoster(kioskGoster);
			if(menudeGoster != null)
				haber.setMenudegoster(menudeGoster);
			if(kurumiciportalGoster != null)
				haber.setKurumiciportalgoster(kurumiciportalGoster);
			if(kisaBaslik != null)
				haber.setKisaBaslik(kisaBaslik);
			if(kisaIzahat != null)
				haber.setKisaIzahat(kisaIzahat);
			if(resim != null)
				haber.setResim(resim);
			// haber.setDenemeicerik(denemeicerik);
			if(ilanKategori != null)
				haber.setPr1IlanKategori_c(ilanKategori);

			haberList.add(haber);

		}

		return haberList;
	}
	
	public List<PR1Haber> getCurrent(){
		String sql = "SELECT  B.ID, B.BASLIK, B.TARIH, B.IZAHAT, B.RESIM, B.FILENAME, B.FILETYPE, B.KURUMSALPORTALGOSTER, B.UPDUSER, B.UPDDATE, B.DELETEFLAG, B.CRUSER, B.CRDATE, B.UPDSEQ, B.TURU, SUBSTR(B.BASLIK,1,23) KISA_BASLIK, SUBSTR(B.IZAHAT,1,220)||'...' KISA_IZAHAT, B.KIOSKGOSTER, B.KURUMICIPORTALGOSTER, B.MENUDEGOSTER, (SELECT ICERIK FROM CPR1RESIM WHERE BPR1HABER_ID=B.ID AND ROWNUM=1) AS ICERIK FROM BPR1HABER B WHERE 1=1 AND B.KURUMSALPORTALGOSTER='E' AND B.TURU = 'H' AND TO_DATE(  NVL(  B.SONYAYINLANMATARIHI,SYSDATE) ,'DD/MM/RRRR')   >=TO_DATE(SYSDATE,'DD/MM/RRRR') ORDER BY B.SIRANUMARASI ASC,B.TARIH DESC";

		List list = new ArrayList<Object>();
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		List<PR1Haber> haberList = new ArrayList<PR1Haber>();

		for (Object o : list) {
			Map map = (Map) o;
			int imageLength;
			PR1Haber haber = new PR1Haber();

			BigDecimal id = (BigDecimal) map.get("ID");
			String baslik = (String) map.get("BASLIK");
			Date tarih = (Date) map.get("TARIH");
			String izahat = clob2Str((Clob) map.get("IZAHAT"));
			char[] resim = (char[]) map.get("RESIM");
			String filename = (String) map.get("FILENAME");
			String filetype = (String) map.get("FILETYPE");
			String kurumsalPortalGoster = (String) map
					.get("KURUMSALPORTALGOSTER");
			BigDecimal updUser = (BigDecimal) map.get("UPDUSER");
			Date updDate = (Date) map.get("UPDDATE");
			// deleteflag
			BigDecimal crUser = (BigDecimal) map.get("CRUSER");
			Date crDate = (Date) map.get("CRDATE");
			// updSeq
			String turu = (String) map.get("TURU");
			String kioskGoster = (String) map.get("KIOSKGOSTER");
			String menudeGoster = (String) map.get("MENUDEGOSTER");
			String kurumiciportalGoster = (String) map
					.get("KURUMICIPORTALGOSTER");
			String kisaBaslik = (String) map.get("KISA_BASLIK");
			String kisaIzahat = clob2Str((Clob) map.get("KISA_IZAHAT"));
			// byte[] denemeicerik = (byte[]) map.get("DENEMEICERIK");
			Blob denemeicerik = (Blob) map.get("ICERIK");

			if(id != null)
				haber.setID(id.longValue());
			if(baslik != null)
				haber.setBaslik(baslik);
			if(tarih != null)
				haber.setTarih(tarih);
			if(izahat != null)
				haber.setIzahat(escapeHtml(izahat, "basic"));
			if(filename != null)
				haber.setFilename(filename);
			if(filetype != null)
				haber.setFiletype(filetype);
			if(kurumsalPortalGoster != null)
				haber.setKurumsalportalgoster(kurumsalPortalGoster);
			if(updUser != null)
				haber.setUpdUser(updUser.longValue());
			if(updDate != null)
				haber.setUpdDate(updDate);
			if(crUser != null)
				haber.setCrUser(crUser.longValue());
			if(crDate != null)
				haber.setCrDate(crDate);
			if(turu != null)
				haber.setTuru(turu);
			if(kioskGoster != null)
				haber.setKioskgoster(kioskGoster);
			if(menudeGoster != null)
				haber.setMenudegoster(menudeGoster);
			if(kurumiciportalGoster != null)
				haber.setKurumiciportalgoster(kurumiciportalGoster);
			if(kisaBaslik != null)
				haber.setKisaBaslik(kisaBaslik);
			if(kisaIzahat != null)
				haber.setKisaIzahat(escapeHtml(kisaIzahat, "basic"));
			try {
				if (denemeicerik!=null){
					imageLength = (int) denemeicerik.length();
					haber.setDenemeicerik(denemeicerik.getBytes(1, imageLength));
					denemeicerik.free();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			haber.setIzahat_nohtml(escapeHtml(haber.getIzahat(), null));

			haber.setKisaIzahat_nohtml(escapeHtml(haber.getKisaIzahat(), null));
			if(resim != null)
				haber.setResim(resim);
			// haber.setDenemeicerik(denemeicerik);

			haberList.add(haber);

		}

		return haberList;
	}
	
	public List<HR1Personel> getSpecialCelebration(String columnName){
		String sql = "SELECT IP.ID,IP.ADI,IP.SOYADI,IP.BSM2SERVIS_GOREV,IP.LHR1GOREVTURU_ID, GT.TANIM, HR1EK.F_DAHILITELEFON(IP.ID) DAHILI,"
				+ "(SELECT TELEFONNUMARASI    FROM   AEILTELEFON"
				+ "          WHERE IHR1PERSONEL_ID =IP.ID"
				+ "              AND  AEILTELEFONTURU_ID =(SELECT ID FROM AEILTELEFONTURU WHERE KAYITOZELISMI='CEP')"
				+ "              AND NVL(ISACTIVE,'E')='E'  AND LENGTH(TELEFONNUMARASI)=10"
				+ "              AND ROWNUM=1              )  CEPTELEFONU"
				+ ", (SELECT TANIM FROM   BSM2SERVIS  WHERE ID=IP.BSM2SERVIS_GOREV) GOREVMUDURLUGU"
				+ " FROM IHR1PERSONEL IP, LHR1GOREVTURU GT"
				+ " where IP.id>0  AND IP.PERSONELDURUMU='CALISAN' AND  F_AY(IP."
				+ columnName
				+ ") = F_AY(SYSDATE) "
				+ " AND  F_GUN(IP."
				+ columnName
				+ ") = F_GUN(SYSDATE) AND IP.LHR1GOREVTURU_ID = GT.ID";

		List list = new ArrayList<Object>();
		List<HR1Personel> personelList = new ArrayList<HR1Personel>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {

			Map map = (Map) o;
			HR1Personel personel = new HR1Personel();

			BigDecimal id = (BigDecimal) map.get("ID");
			String adi = (String) map.get("ADI");
			String soyadi = (String) map.get("SOYADI");
			BigDecimal bsm2servis_gorev = (BigDecimal) map
					.get("BSM2SERVIS_GOREV");
			BigDecimal lhr1gorevturu_id = (BigDecimal) map
					.get("LHR1GOREVTURU_ID");
			String tanim = (String) map.get("TANIM");
			String dahili = (String) map.get("DAHILI");
			BigDecimal cepTelefonu = (BigDecimal)map.get("CEPTELEFONU");
			String gorevmudurlugu = (String) map.get("GOREVMUDURLUGU");

			if (id != null)
				personel.setId(id.longValue());
			if (adi != null)
				personel.setAdi(adi);
			if (soyadi != null)
				personel.setSoyadi(soyadi);
			if (bsm2servis_gorev != null)
				personel.setBsm2servisGorev(bsm2servis_gorev.longValue());
			if (lhr1gorevturu_id != null) {
				HR1GorevTuru gorevturu = new HR1GorevTuru();
				gorevturu.setId(lhr1gorevturu_id.longValue());
				gorevturu.setTanim(tanim);
				personel.setHr1GorevTuru(gorevturu);
			}
			if (dahili != null)
				personel.setDahiliTelefonNo(dahili);
			if (cepTelefonu != null)
				personel.setCepTelefonu(String.valueOf(cepTelefonu.longValue()));
			if (gorevmudurlugu != null)
				personel.setGorevMudurlugu(gorevmudurlugu);
			personelList.add(personel);

		}

		return personelList;
		
	}

	public List<Person> getCatalog(){
		
		String sql = "select ID,ADI,SOYADI,(SELECT TANIM FROM BSM2SERVIS WHERE ID=BSM2SERVIS_GOREV) GOREVMUDURLUK, "+
		             "IP.BSM2SERVIS_GOREV, HR1EK.F_DAHILITELEFON(IP.ID) DAHILI,KANGRUBU, "+ 
		             "(select FILENAME from JHR1PERSONELRESIM where IHR1PERSONEL_ID = ip.id AND ROWNUM <= 1) DOSYAADI, "+
                     "(select FILETYPE from JHR1PERSONELRESIM where IHR1PERSONEL_ID = ip.id AND ROWNUM <= 1) DOSYATURU , "+
                     "(select ICERIK from JHR1PERSONELRESIM where IHR1PERSONEL_ID = ip.id AND ROWNUM <= 1) ICERIK "+
		             "from ihr1personel IP where "+
		             "IP.ID>0 AND IP.BSM2SERVIS_GOREV IN ( SELECT DISTINCT BSM2SERVIS_GOREV GOREVMUDURLUK FROM "+
		             "ihr1personel IP where IP.ID>0 AND IP.PERSONELDURUMU='CALISAN' AND HR1EK.F_DAHILITELEFON(IP.ID) is not null ) "+
		             "AND IP.PERSONELDURUMU='CALISAN' and HR1EK.F_DAHILITELEFON(IP.ID) is not null ORDER BY IP.BSM2SERVIS_GOREV, "+
		             "IP.ADI,IP.SOYADI";


		List list = new ArrayList<Object>();
		List<Person> personelList = new ArrayList<Person>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {

			Map map = (Map) o;
			Person personel =  new Person();
			int imageLength;

			BigDecimal id = (BigDecimal) map.get("ID");
			String adi = (String) map.get("ADI");
			String soyadi = (String) map.get("SOYADI");
			String gorevmudurluk = (String) map.get("GOREVMUDURLUK");
			String dahili = (String) map.get("DAHILI");
			String kangrubu = (String) map.get("KANGRUBU");
			String dosyaAdi = (String) map.get("DOSYAADI");
			String dosyaTuru = (String) map.get("DOSYATURU");
			Blob icerik = (Blob) map.get("ICERIK");

			if (id != null)
				personel.setId(id.longValue());
			if (adi != null)
				personel.setAdi(adi);
			if (soyadi != null)
				personel.setSoyadi(soyadi);
			if (gorevmudurluk != null)
				personel.setGorevMudurlugu(gorevmudurluk);
			if (dahili != null)
				personel.setDahili(dahili);
			if (kangrubu != null)
				personel.setKanGrubu(kangrubu);
			if(dosyaAdi != null)
				personel.setDosyaAdi(dosyaAdi);
			if(dosyaTuru != null)
				personel.setDosyaTuru(dosyaTuru);
			
			try {
				if (icerik!=null){
					imageLength = (int) icerik.length();
					personel.setIcerik(icerik.getBytes(1, imageLength));
					icerik.free();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			personelList.add(personel);

		}

		return personelList;
	}
	
	public List<Person> getCatalogWithoutPicture(){
		String sql = "select ID,ADI,SOYADI,(SELECT TANIM FROM BSM2SERVIS WHERE ID=BSM2SERVIS_GOREV) GOREVMUDURLUK, "+
	             "IP.BSM2SERVIS_GOREV, HR1EK.F_DAHILITELEFON(IP.ID) DAHILI,KANGRUBU "+ 	    
	             "from ihr1personel IP where "+
	             "IP.ID>0 AND IP.BSM2SERVIS_GOREV IN ( SELECT DISTINCT BSM2SERVIS_GOREV GOREVMUDURLUK FROM "+
	             "ihr1personel IP where IP.ID>0 AND IP.PERSONELDURUMU='CALISAN' AND HR1EK.F_DAHILITELEFON(IP.ID) is not null ) "+
	             "AND IP.PERSONELDURUMU='CALISAN' and HR1EK.F_DAHILITELEFON(IP.ID) is not null ORDER BY " +
				 "NLSSORT(IP.ADI,'nls_sort=xturkish'),NLSSORT(IP.SOYADI,'nls_sort=xturkish') ";


		List list = new ArrayList<Object>();
		List<Person> personelList = new ArrayList<Person>();
	
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
	
		for (Object o : list) {
	
			Map map = (Map) o;
			Person personel =  new Person();
			int imageLength;
	
			BigDecimal id = (BigDecimal) map.get("ID");
			String adi = (String) map.get("ADI");
			String soyadi = (String) map.get("SOYADI");
			String gorevmudurluk = (String) map.get("GOREVMUDURLUK");
			String dahili = (String) map.get("DAHILI");
			String kangrubu = (String) map.get("KANGRUBU");
	
			if (id != null)
				personel.setId(id.longValue());
			if (adi != null)
				personel.setAdi(adi);
			if (soyadi != null)
				personel.setSoyadi(soyadi);
			if (gorevmudurluk != null)
				personel.setGorevMudurlugu(gorevmudurluk);
			if (dahili != null)
				personel.setDahili(dahili);
			if (kangrubu != null)
				personel.setKanGrubu(kangrubu);
		
			personelList.add(personel);
	
		}
	
		return personelList;
	}
	
	public List<HR1EgitimGenel> getEducation(long persid){
		String sql = "SELECT  A.TANIM, A.KONUSU, HR1.F_EgitimGenelEgitmenler(A.ID) EGITMENADI, A.YILI, A.TOPLAMSAAT, (SELECT TANIM FROM FHR1EGITIMYERI WHERE ID = A.FHR1EGITIMYERI_ID) VERILECEKYER FROM FHR1EGITIMGENEL A "
				+ "WHERE ID>0 AND NVL(IPTALEDILDI,'H')='H' ORDER BY YILI DESC ";

		List list = new ArrayList<Object>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		List<HR1EgitimGenel> egitimList = new ArrayList<HR1EgitimGenel>();

		for (Object o : list) {
			Map map = (Map) o;
			HR1EgitimGenel egitim = new HR1EgitimGenel();

			String tanim = (String) map.get("TANIM");
			String konusu = (String) map.get("KONUSU");
			String egitmenadi = (String) map.get("EGITMENADI");
			BigDecimal yili = (BigDecimal) map.get("YILI");
			BigDecimal toplamsaat = (BigDecimal) map.get("TOPLAMSAAT");
			String verilecekyer = (String) map.get("VERILECEKYER");

			if (tanim != null)
				egitim.setTanim(tanim);
			if (konusu != null)
				egitim.setKonusu(konusu);
			if (egitmenadi != null)
				egitim.setEgitmenAdi(egitmenadi);
			if (yili != null)
				egitim.setYili(yili.longValue());
			if (toplamsaat != null)
				egitim.setToplamSaat(toplamsaat.longValue());
			if (verilecekyer != null)
				egitim.setVerilecekYer(verilecekyer);

			egitimList.add(egitim);

		}

		return egitimList;
	}
	
	public List<PR1KurumIndirim> getDiscount() {
		String sql = "SELECT ID,TANIM,ORANIYUZDE,ADRES,BASLANGICTARIHI,BITISTARIHI,IZAHAT FROM APR1KURUMINDIRIM"
				+ " WHERE 1=1 "
				+ " AND TO_DATE(SYSDATE,'DD/MM/RRRR') BETWEEN TO_DATE(BASLANGICTARIHI,'DD/MM/RRRR') AND TO_DATE(BITISTARIHI ,'DD/MM/RRRR') "
				+ " ORDER BY BITISTARIHI DESC,BASLANGICTARIHI ASC";

//		String sql = "SELECT ID,TANIM,ORANIYUZDE,ADRES,BASLANGICTARIHI,BITISTARIHI,IZAHAT FROM APR1KURUMINDIRIM"
//				+ " WHERE 1=1 "
//				+ " ORDER BY BITISTARIHI DESC,BASLANGICTARIHI ASC";
		
		List list = new ArrayList<Object>();
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		List<PR1KurumIndirim> indirimList = new ArrayList<PR1KurumIndirim>();

		for (Object o : list) {
			Map map = (Map) o;
			PR1KurumIndirim indirim = new PR1KurumIndirim();

			BigDecimal id = (BigDecimal) map.get("ID");
			String tanim = (String) map.get("TANIM");
			BigDecimal oraniYuzde = (BigDecimal) map.get("ORANIYUZDE");
			String adres = (String) map.get("ADRES");
			Date baslangicTarihi = (Date) map.get("BASLANGICTARIHI");
			Date bitisTarihi = (Date) map.get("BITISTARIHI");
			String izahat = (String) map.get("IZAHAT");

			if(id != null)
				indirim.setID(id.longValue());
			if(tanim != null)
				indirim.setTanim(tanim);
			if(oraniYuzde != null)
				indirim.setOraniYuzde(oraniYuzde.longValue());
			if(adres != null)
				indirim.setAdres(adres);
			if(izahat != null)
				indirim.setIzahat(izahat);
			if(baslangicTarihi != null)
				indirim.setBaslangicTarihi(baslangicTarihi);
			if(bitisTarihi != null)
				indirim.setBitisTarihi(bitisTarihi);

			indirimList.add(indirim);

		}

		return indirimList;
	}

	public List<HR1PersonelIslem> getJobChange(){
		String sql = "SELECT GOREVLENDIRMETARIHI, PI.IHR1PERSONEL_ID, (SELECT ADI ||' '|| SOYADI FROM IHR1PERSONEL WHERE ID=PI.IHR1PERSONEL_ID) PERSONELADI, "
				+ "(SELECT TANIM FROM BSM2SERVIS WHERE ID = (SELECT BSM2SERVIS_GOREV FROM MHR1PERSONELISLEM WHERE GOREVLENDIRMETARIHI = (SELECT MAX (GOREVLENDIRMETARIHI) FROM MHR1PERSONELISLEM WHERE TURU = 'MG' AND IHR1PERSONEL_ID = PI.IHR1PERSONEL_ID AND GOREVLENDIRMETARIHI < (SELECT MAX (GOREVLENDIRMETARIHI) FROM MHR1PERSONELISLEM WHERE TURU = 'MG' AND IHR1PERSONEL_ID = PI.IHR1PERSONEL_ID)) AND TURU = 'MG' AND IHR1PERSONEL_ID = PI.IHR1PERSONEL_ID)) ESKIGOREVYERI, "
				+ "(SELECT TANIM FROM BSM2SERVIS WHERE ID=BSM2SERVIS_GOREV) AS GOREVYERI, "
				+ "(SELECT TANIM FROM LHR1GOREVTURU WHERE ID=(SELECT LHR1GOREVTURU_ID FROM IHR1PERSONEL WHERE ID=PI.IHR1PERSONEL_ID)) AS GOREVI "
				+ "FROM MHR1PERSONELISLEM PI "
				+ "WHERE (PI.TURU = 'MG' OR PI.TURU = 'H') "
				+ "   AND GOREVLENDIRMETARIHI BETWEEN SYSDATE-30 AND SYSDATE"
				+ " ORDER BY GOREVLENDIRMETARIHI";


		
		List list = new ArrayList<Object>();
		List<HR1PersonelIslem> hr1PersonelIslems = new ArrayList<HR1PersonelIslem>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {

			HR1PersonelIslem hr1PersonelIslem = new HR1PersonelIslem();

			Map map = (Map) o;

			Date gorevlendirmeTarihi = (Date) map.get("GOREVLENDIRMETARIHI");
			BigDecimal ihr1PersonelId = (BigDecimal) map.get("IHR1PERSONEL_ID");
			String eskigorevyeri = (String) map.get("ESKIGOREVYERI");
			String gorevyeri = (String) map.get("GOREVYERI");
			String gorevi = (String) map.get("GOREVI");

			if (ihr1PersonelId != null) {
				HR1Personel hr1Personel = new HR1Personel();
				hr1Personel.setId(ihr1PersonelId.longValue());
				hr1Personel.setAdiSoyadi((String) map.get("PERSONELADI"));
				hr1PersonelIslem.setHr1Personel(hr1Personel);
			}
			hr1PersonelIslem
					.setEskiGorevYeri((eskigorevyeri != null) ? eskigorevyeri
							: "-");
			hr1PersonelIslem
					.setGorevYeri((gorevyeri != null) ? gorevyeri : "-");
			hr1PersonelIslem.setGorevi((gorevi != null) ? gorevi : "-");
			hr1PersonelIslem.setGorevlendirmeTarihi(gorevlendirmeTarihi);

			hr1PersonelIslems.add(hr1PersonelIslem);
		}

		return hr1PersonelIslems;
	}
	

	private String clob2Str(Clob clob) {
		try {
			if (clob == null)
				return "";
			else if (((int) clob.length()) > 0) {
				return clob.getSubString(1, (int) clob.length());
			}
		} catch (SQLException e) {
		}
		return "";

	}
	
	public static String escapeHtml(String html, String type) {
		if (html == null)
			return "";
		org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(html);
		if (type != null && type.equals("basic"))
			doc = new org.jsoup.safety.Cleaner(
					org.jsoup.safety.Whitelist.basic()).clean(doc);
		else
			doc = new org.jsoup.safety.Cleaner(
					org.jsoup.safety.Whitelist.none()).clean(doc);
		doc.outputSettings().escapeMode(
				org.jsoup.nodes.Entities.EscapeMode.xhtml);
		return doc.body().html();
	}

	

}
