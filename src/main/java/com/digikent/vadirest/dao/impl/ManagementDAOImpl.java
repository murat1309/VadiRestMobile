package com.digikent.vadirest.dao.impl;

import com.digikent.vadirest.dao.ManagementDAO;
import com.digikent.vadirest.dto.*;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("managementDao")
@Transactional
public class ManagementDAOImpl implements ManagementDAO {

	@Autowired
	protected SessionFactory sessionFactory;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	public List<BankaDurumu> getBankStatus(long id, long year) {
		String sql ="SELECT HPL.KODU HesapKodu, HPL.TANIM HesapAdi, HPL.TOPLAMALACAK TahakkukEden, " 
	               +"HPL.TOPLAMBORC Odenen, (HPL.TOPLAMBORC - HPL.TOPLAMALACAK) Bakiye FROM LFI2HESAPPLANI HPL "			
				   +"WHERE HPL.BFI1BUTCEDONEMI_ID = (select Max(a.ID) from BFI1BUTCEDONEMI a, FSM1USERS b where a.YILI = " + year 
				   +" And a.SM1KURUM_ID = b.MSM1KURUM_ID And b.ID =" + id + ")" 
			       +" AND (HPL.KODU LIKE '102%') AND HPL.ENALTDUZEY = 'E' AND (HPL.TOPLAMBORC - HPL.TOPLAMALACAK) != 0";

		List<Object> list= new ArrayList();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		list = query.list();
		List<BankaDurumu> bankaDurumuList = new ArrayList<BankaDurumu>();
		
		for(Object o : list){
			Map map = (Map) o;
			BankaDurumu bankaDurumu = new BankaDurumu();
			String hesapKodu = (String)map.get("HESAPKODU");
			String hesapAdi = (String)map.get("HESAPADI");
			BigDecimal tahakkukEden = (BigDecimal)map.get("TAHAKKUKEDEN");
			BigDecimal odenen = (BigDecimal)map.get("ODENEN");
			BigDecimal bakiye = (BigDecimal)map.get("BAKIYE");
			
			if(hesapKodu !=null)
				bankaDurumu.setHesapKodu(hesapKodu);
			if(hesapAdi != null)
				bankaDurumu.setHesapAdi(hesapAdi);
			if(tahakkukEden != null)
				bankaDurumu.setTahakkukEden(tahakkukEden.doubleValue());
			if(odenen != null)
				bankaDurumu.setOdenen(odenen.doubleValue());
			if(bakiye != null)
				bankaDurumu.setBakiye(bakiye.doubleValue());
			
			bankaDurumuList.add(bankaDurumu);
		}
		return bankaDurumuList;

	}

	public List<BankaDurumuDetay> getBankStatusDetail(long id, long accountId, long year, String startDate, String endDate){
		String sql = "select A.YEVMIYENUMARASI,A.YEVMIYETARIHI, A.IZAHAT," 
                    +" C.KODU HESAPKODU, C.TANIM HESAPADI, "
                    +" C.TOPLAMBORC, C.TOPLAMALACAK" 
                    +" from OFI2MUHASEBEFISI A,RFI2MUHASEBEFISILINE B, LFI2HESAPPLANI C "
                    +" Where A.ID = B.OFI2MUHASEBEFISI_ID "
                    +" And C.ID = B.LFI2HESAPPLANI_ID "
                    +" And A.BFI1BUTCEDONEMI_ID = (select Max(a.ID) from BFI1BUTCEDONEMI a, FSM1USERS b where a.YILI = " +year 
                    +" And a.SM1KURUM_ID = b.MSM1KURUM_ID And b.ID =" + id  +")"
                    +" And C.KODU =" + accountId
                    +" AND A.YevmiyeTarihi BETWEEN TO_DATE('"+ startDate +"', 'dd-MM-yyyy') and"  
                    +" TO_DATE ('"+ endDate +"', 'dd-MM-yyyy')"
                    +" order by A.YEVMIYETARIHI desc";
		
		List<Object> list= new ArrayList();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		list = query.list();
		List<BankaDurumuDetay> bankaDurumuDetayList = new ArrayList<BankaDurumuDetay>();
		
		for(Object o : list){
			Map map = (Map) o;
			BankaDurumuDetay bankaDurumuDetay = new BankaDurumuDetay();
			
			BigDecimal yevmiyeNumarasi = (BigDecimal)map.get("YEVMIYENUMARASI");
			Date yevmiyeTarihi = (Date)map.get("YEVMIYETARIHI");
			String izahat = (String)map.get("IZAHAT");
			String hesapKodu = (String)map.get("HESAPKODU");
			String hesapAdi = (String)map.get("HESAPADI");
			BigDecimal toplamBorc = (BigDecimal)map.get("TOPLAMBORC");
			BigDecimal toplamAlacak = (BigDecimal)map.get("TOPLAMALACAK");
			
			if(yevmiyeNumarasi != null)
				bankaDurumuDetay.setYevmiyeNumarasi(yevmiyeNumarasi.longValue());
			if(yevmiyeTarihi!=null)
				bankaDurumuDetay.setYevmiyeTarihi(dateFormat.format(yevmiyeTarihi));
			if(izahat != null)
				bankaDurumuDetay.setIzahat(izahat);
			if(hesapKodu !=null)
				bankaDurumuDetay.setHesapKodu(hesapKodu);
			if(hesapAdi != null)
				bankaDurumuDetay.setHesapAdi(hesapAdi);
			if(toplamBorc != null)
				bankaDurumuDetay.setToplamBorc(toplamBorc.doubleValue());
			if(toplamAlacak != null)
				bankaDurumuDetay.setToplamAlacak(toplamAlacak.doubleValue());
			
			bankaDurumuDetayList.add(bankaDurumuDetay);
		}
		return bankaDurumuDetayList;

	}
	
	public List<PersonelBilgileri> getStaffInfomation() {
		String sql ="select a.BSM2SERVIS_GOREV,b.TANIM BirimAdi,"
				   +"Count(*) ToplamPersonel,"
		           +"Sum( Case a.TURU When 'M' Then 1 Else 0 End ) MemurSayisi,"
		           +"Sum( Case a.TURU When 'I' Then 1 Else 0 End ) IsciSayisi,"		       
				   +"Sum( Case a.TURU When 'O' Then 1 Else 0 End ) StajyerSayisi,"				
				   +"Sum( Case a.TURU When 'F' Then 1 Else 0 End ) TaseronSayisi,"
		           +"Sum( Case a.TURU When 'S' Then 1 Else 0 End ) SozlesmeliSayisi,"
		           +"Sum( Case a.TURU When 'G' Then 1 Else 0 End ) GeciciIsciSayisi,"
		           +"Sum( Case a.TURU When 'L' Then 1 Else 0 End ) MeclisUyesiSayisi from IHR1PERSONEL a,BSM2SERVIS b Where a.BSM2SERVIS_GOREV = b.ID "
		           +"And a.CIKISTARIHI IS NULL AND A.PERSONELDURUMU='CALISAN'"
		           +"group by a.BSM2SERVIS_GOREV,b.TANIM"
				   +" order by BirimAdi";
				
		List<Object> list = new ArrayList<Object>();
		List<PersonelBilgileri> personelBilgileriList = new ArrayList<PersonelBilgileri>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			PersonelBilgileri personelBilgileri = new PersonelBilgileri();
			
			BigDecimal bsm2ServisGorev = (BigDecimal)map.get("BSM2SERVIS_GOREV");
			String birimAdi = (String)map.get("BIRIMADI");
			BigDecimal toplamPersonel = (BigDecimal)map.get("TOPLAMPERSONEL");
			BigDecimal memurSayisi = (BigDecimal)map.get("MEMURSAYISI");
			BigDecimal isciSayisi = (BigDecimal)map.get("ISCISAYISI");
			BigDecimal stajyerSayisi = (BigDecimal)map.get("STAJYERSAYISI");
			BigDecimal taseronSayisi = (BigDecimal)map.get("TASERONSAYISI");
			BigDecimal sozlesmeliSayisi = (BigDecimal)map.get("SOZLEMELISAYISI");
			BigDecimal gecisiIsciSayisi = (BigDecimal)map.get("GECICIISCISAYISI");
			BigDecimal meclisUyesiSayisi = (BigDecimal)map.get("MECLISUYESISAYISI");
			
			if(bsm2ServisGorev != null)
				personelBilgileri.setBsm2ServisGorev(bsm2ServisGorev.longValue());
			if(birimAdi != null)
				personelBilgileri.setBirimAdi(birimAdi);
			if(toplamPersonel != null)
				personelBilgileri.setToplamPersonel(toplamPersonel.intValue());
			if(memurSayisi != null)
				personelBilgileri.setMemurSayisi(memurSayisi.intValue());
			if(isciSayisi != null)
				personelBilgileri.setIsciSayisi(isciSayisi.intValue());
			if(stajyerSayisi != null)
				personelBilgileri.setStajyerSayisi(stajyerSayisi.intValue());
			if(taseronSayisi != null)
				personelBilgileri.setTaseronSayisi(taseronSayisi.intValue());
			if(sozlesmeliSayisi != null)
				personelBilgileri.setSozlemeliSayisi(sozlesmeliSayisi.intValue());
			if(gecisiIsciSayisi != null)
				personelBilgileri.setGeciciIsciSayisi(gecisiIsciSayisi.intValue());
			if(meclisUyesiSayisi != null)
				personelBilgileri.setMeclisUyesiSayisi(meclisUyesiSayisi.intValue());
			
			personelBilgileriList.add(personelBilgileri);
		}
		
		return personelBilgileriList;
	}

	public List<PersonelBilgileri> getPersonelKadroInformation() {
		String sql ="select a.BSM2SERVIS_KADRO,b.TANIM BirimAdi,"
				   +"Count(*) ToplamPersonel,"
		           +"Sum( Case a.TURU When 'M' Then 1 Else 0 End ) MemurSayisi,"
		           +"Sum( Case a.TURU When 'I' Then 1 Else 0 End ) IsciSayisi,"
				   +"Sum( Case a.TURU When 'O' Then 1 Else 0 End ) StajyerSayisi,"
				   +"Sum( Case a.TURU When 'F' Then 1 Else 0 End ) TaseronSayisi,"
		           +"Sum( Case a.TURU When 'S' Then 1 Else 0 End ) SozlesmeliSayisi,"
		           +"Sum( Case a.TURU When 'G' Then 1 Else 0 End ) GeciciIsciSayisi,"
		           +"Sum( Case a.TURU When 'L' Then 1 Else 0 End ) MeclisUyesiSayisi from IHR1PERSONEL a,BSM2SERVIS b Where a.BSM2SERVIS_KADRO = b.ID "
		           +"And a.CIKISTARIHI IS NULL AND A.PERSONELDURUMU='CALISAN'"
		           +"group by a.BSM2SERVIS_KADRO,b.TANIM"
				   +" order by BirimAdi";

		List<Object> list = new ArrayList<Object>();
		List<PersonelBilgileri> personelBilgileriList = new ArrayList<PersonelBilgileri>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map) o;
			PersonelBilgileri personelBilgileri = new PersonelBilgileri();

			BigDecimal bsm2ServisKadro = (BigDecimal)map.get("BSM2SERVIS_KADRO");
			String birimAdi = (String)map.get("BIRIMADI");
			BigDecimal toplamPersonel = (BigDecimal)map.get("TOPLAMPERSONEL");
			BigDecimal memurSayisi = (BigDecimal)map.get("MEMURSAYISI");
			BigDecimal isciSayisi = (BigDecimal)map.get("ISCISAYISI");
			BigDecimal stajyerSayisi = (BigDecimal)map.get("STAJYERSAYISI");
			BigDecimal taseronSayisi = (BigDecimal)map.get("TASERONSAYISI");
			BigDecimal sozlesmeliSayisi = (BigDecimal)map.get("SOZLEMELISAYISI");
			BigDecimal gecisiIsciSayisi = (BigDecimal)map.get("GECICIISCISAYISI");
			BigDecimal meclisUyesiSayisi = (BigDecimal)map.get("MECLISUYESISAYISI");

			if(bsm2ServisKadro != null)
				personelBilgileri.setBsm2ServisKadro(bsm2ServisKadro.longValue());
			if(birimAdi != null)
				personelBilgileri.setBirimAdi(birimAdi);
			if(toplamPersonel != null)
				personelBilgileri.setToplamPersonel(toplamPersonel.intValue());
			if(memurSayisi != null)
				personelBilgileri.setMemurSayisi(memurSayisi.intValue());
			if(isciSayisi != null)
				personelBilgileri.setIsciSayisi(isciSayisi.intValue());
			if(stajyerSayisi != null)
				personelBilgileri.setStajyerSayisi(stajyerSayisi.intValue());
			if(taseronSayisi != null)
				personelBilgileri.setTaseronSayisi(taseronSayisi.intValue());
			if(sozlesmeliSayisi != null)
				personelBilgileri.setSozlemeliSayisi(sozlesmeliSayisi.intValue());
			if(gecisiIsciSayisi != null)
				personelBilgileri.setGeciciIsciSayisi(gecisiIsciSayisi.intValue());
			if(meclisUyesiSayisi != null)
				personelBilgileri.setMeclisUyesiSayisi(meclisUyesiSayisi.intValue());

			personelBilgileriList.add(personelBilgileri);
		}

		return personelBilgileriList;
	}
	
	public List<PersonelGrup> getStaffGroup(){
		String sql="select "
        +"Sum( Case a.TURU When 'M' Then 1 Else 0 End ) MemurSayisi,"
        +"Sum( Case a.TURU When 'I' Then 1 Else 0 End ) IsciSayisi,"
        +"Sum( Case a.TURU When 'O' Then 1 Else 0 End ) StajyerSayisi,"
        +"Sum( Case a.TURU When 'F' Then 1 Else 0 End ) TaseronSayisi,"
        +"Sum( Case a.TURU When 'S' Then 1 Else 0 End ) SozlesmeliSayisi,"
        +"Sum( Case a.TURU When 'G' Then 1 Else 0 End ) GecisiIsciSayisi,"
        +"Sum( Case a.TURU When 'L' Then 1 Else 0 End ) MeclisUyesiSayisi from IHR1PERSONEL a Where  a.CIKISTARIHI IS NULL";
		
		List<Object> list = new ArrayList<Object>();
		List<PersonelGrup> personelGrubuList = new ArrayList<PersonelGrup>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			
			
			BigDecimal memurSayisi = (BigDecimal)map.get("MEMURSAYISI");
			BigDecimal isciSayisi = (BigDecimal)map.get("ISCISAYISI");
			BigDecimal stajyerSayisi = (BigDecimal)map.get("STAJYERSAYISI");
			BigDecimal taseronSayisi = (BigDecimal)map.get("TASERONSAYISI");
			BigDecimal sozlesmeliSayisi = (BigDecimal)map.get("SOZLEMELISAYISI");
			BigDecimal gecisiIsciSayisi = (BigDecimal)map.get("GECICIISCISAYISI");
			BigDecimal meclisUyesiSayisi = (BigDecimal)map.get("MECLISUYESISAYISI");
			
			if(memurSayisi != null){
				PersonelGrup personelGrup = new PersonelGrup();
				personelGrup.setKey("Memur");
				personelGrup.setY(memurSayisi.intValue());
				personelGrubuList.add(personelGrup);
			}
			if(isciSayisi != null){
				PersonelGrup personelGrup = new PersonelGrup();
				personelGrup.setKey("Isci");
				personelGrup.setY(isciSayisi.intValue());
				personelGrubuList.add(personelGrup);
			}
			if(stajyerSayisi != null){
				PersonelGrup personelGrup = new PersonelGrup();
				personelGrup.setKey("Stajyer");
				personelGrup.setY(stajyerSayisi.intValue());
				personelGrubuList.add(personelGrup);
			}
			if(taseronSayisi != null){
				PersonelGrup personelGrup = new PersonelGrup();
				personelGrup.setKey("Taseron");
				personelGrup.setY(taseronSayisi.intValue());
				personelGrubuList.add(personelGrup);
			}
			if(sozlesmeliSayisi != null){
				PersonelGrup personelGrup = new PersonelGrup();
				personelGrup.setKey("Sozlesmeli");
				personelGrup.setY(sozlesmeliSayisi.intValue());
				personelGrubuList.add(personelGrup);	
			}
			if(gecisiIsciSayisi != null){
				PersonelGrup personelGrup = new PersonelGrup();
				personelGrup.setKey("Gecici isci");
				personelGrup.setY(gecisiIsciSayisi.intValue());
				personelGrubuList.add(personelGrup);
			}
			if(meclisUyesiSayisi != null){
				PersonelGrup personelGrup = new PersonelGrup();
				personelGrup.setKey("Meclis Uyesi");
				personelGrup.setY(meclisUyesiSayisi.intValue());
				personelGrubuList.add(personelGrup);
			}			
		}
		return personelGrubuList;
	}


	public List<PersonelBilgileriDetay> getJobStarters(String startDate, String endDate){
		String sql = "Select A.ID, A.ADISOYADI from IHR1PERSONEL A " +
				"Where A.TURU NOT IN ('L','O','-','D') and A.GIRISTARIHI BETWEEN TO_DATE('"+startDate+"', 'dd-MM-yyyy') and " +
				"TO_DATE ('"+endDate+"', 'dd-MM-yyyy') ORDER BY A.GIRISTARIHI";

		List<Object> list = new ArrayList<Object>();
		List<PersonelBilgileriDetay> personelBilgileriDetayList = new ArrayList<PersonelBilgileriDetay>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map) o;
			PersonelBilgileriDetay personelBilgileriDetay = new PersonelBilgileriDetay();
			int imageLength;

			BigDecimal id = (BigDecimal)map.get("ID");
			String adiSoyadi = (String) map.get("ADISOYADI");

			if(id != null)
				personelBilgileriDetay.setId(id.longValue());
			if(adiSoyadi != null)
				personelBilgileriDetay.setAdiSoyadi(adiSoyadi);

			personelBilgileriDetayList.add(personelBilgileriDetay);
		}
		return personelBilgileriDetayList;
	}

	public List<PersonelBilgileriDetay> getJobQuitters(String startDate, String endDate){
		String sql = "Select A.ID, A.ADISOYADI from IHR1PERSONEL A " +
				"Where A.TURU NOT IN ('L','O','-','D') and A.CIKISTARIHI BETWEEN TO_DATE('"+startDate+"', 'dd-MM-yyyy') and " +
				"TO_DATE ('"+endDate+"', 'dd-MM-yyyy') ORDER BY A.CIKISTARIHI";

		List<Object> list = new ArrayList<Object>();
		List<PersonelBilgileriDetay> personelBilgileriDetayList = new ArrayList<PersonelBilgileriDetay>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map) o;
			PersonelBilgileriDetay personelBilgileriDetay = new PersonelBilgileriDetay();
			int imageLength;

			BigDecimal id = (BigDecimal)map.get("ID");
			String adiSoyadi = (String) map.get("ADISOYADI");

			if(id != null)
				personelBilgileriDetay.setId(id.longValue());
			if(adiSoyadi != null)
				personelBilgileriDetay.setAdiSoyadi(adiSoyadi);

			personelBilgileriDetayList.add(personelBilgileriDetay);
		}
		return personelBilgileriDetayList;
	}
	
	public List<PersonelBilgileriDetay> getStaffDetail(long servisGorevId, char turu){
		String sql;
		if(Character.toString(turu).equalsIgnoreCase(Character.toString(' '))){
			sql ="select a.ID, a.BSM2SERVIS_GOREV,b.TANIM BIRIMADI,a.TURU,a.ADISOYADI,a.TCKIMLIKNO, "
					   +"a.CEPTELEFONU,b.TELEFONNUMARASI,a.ELEKTRONIKPOSTA,a.DOGUMYERI,b.ADRES "
					   +" from IHR1PERSONEL a,BSM2SERVIS b Where a.BSM2SERVIS_GOREV = b.ID and a.BSM2SERVIS_GOREV = "+servisGorevId 
					   +" And a.CIKISTARIHI IS NULL and a.TURU NOT IN ('L','O','-','D')"
					   +" AND a.PERSONELDURUMU not in('EMEKLI','AYR') "
					   +" order by a.ADISOYADI";
		}
		else{
			sql ="select a.ID, a.BSM2SERVIS_GOREV,b.TANIM BIRIMADI,a.TURU,a.ADISOYADI,a.TCKIMLIKNO, "
					   +"a.CEPTELEFONU,b.TELEFONNUMARASI,a.ELEKTRONIKPOSTA,a.DOGUMYERI,b.ADRES "
					   +" from IHR1PERSONEL a,BSM2SERVIS b Where a.BSM2SERVIS_GOREV = b.ID and a.BSM2SERVIS_GOREV = "+servisGorevId 
					   +" And a.CIKISTARIHI IS NULL and a.TURU = '"+turu+"'"
					   +" AND a.PERSONELDURUMU not in('EMEKLI','AYR') "
					   +" order by a.ADISOYADI";
		}
		
		List<Object> list = new ArrayList<Object>();
		List<PersonelBilgileriDetay> personelBilgileriDetayList = new ArrayList<PersonelBilgileriDetay>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			PersonelBilgileriDetay personelBilgileriDetay = new PersonelBilgileriDetay();
			int imageLength;

			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal  bsm2ServisGorev = (BigDecimal) map.get("BSM2SERVIS_GOREV");
			String birimAdi = (String) map.get("BIRIMADI");
			String adiSoyadi = (String) map.get("ADISOYADI");
			BigDecimal tcKimlikNo = (BigDecimal) map.get("TCKIMLIKNO");
			String cepTelefonu = (String) map.get("CEPTELEFONU");
			String telefonNumarasi = (String) map.get("TELEFONNUMARASI");
			String elektronikPosta = (String) map.get("ELEKTRONIKPOSTA");
			String dogumYeri = (String) map.get("DOGUMYERI");
			String adres = (String) map.get("ADRES");

			if(id != null)
				personelBilgileriDetay.setId(id.longValue());
			if(bsm2ServisGorev != null)
				personelBilgileriDetay.setBsm2ServisGorev(bsm2ServisGorev.longValue());
			if(birimAdi != null)
				personelBilgileriDetay.setBirimAdi(birimAdi);
			if(adiSoyadi != null)
				personelBilgileriDetay.setAdiSoyadi(adiSoyadi);
			if(tcKimlikNo != null)
				personelBilgileriDetay.setTcKimlikNo(tcKimlikNo.longValue());
			if(cepTelefonu != null)
				personelBilgileriDetay.setCepTelefonu(cepTelefonu);
			if(telefonNumarasi != null)
				personelBilgileriDetay.setTelefonNumarasi(telefonNumarasi);
			if(elektronikPosta != null)
				personelBilgileriDetay.setElektronikPosta(elektronikPosta);
			if(dogumYeri != null)
				personelBilgileriDetay.setDogumYeri(dogumYeri);
			if(adres != null)
				personelBilgileriDetay.setAdres(adres);
			
			personelBilgileriDetayList.add(personelBilgileriDetay);
		}
		return personelBilgileriDetayList;
	}

	public List<PersonelBilgileriDetay> getKadroDetay(long servisKadroId){
		String sql ="select a.ID, a.BSM2SERVIS_KADRO,b.TANIM BIRIMADI,a.TURU,a.ADISOYADI,a.TCKIMLIKNO, "
				+"a.CEPTELEFONU,b.TELEFONNUMARASI,a.ELEKTRONIKPOSTA,a.DOGUMYERI,b.ADRES "
				+" from IHR1PERSONEL a,BSM2SERVIS b Where a.BSM2SERVIS_KADRO = b.ID and a.BSM2SERVIS_KADRO = "+servisKadroId
				+" And a.CIKISTARIHI IS NULL and a.TURU NOT IN ('L','O','-','D')"
				+" AND a.PERSONELDURUMU not in('EMEKLI','AYR') "
				+" order by a.ADISOYADI";

		List<Object> list = new ArrayList<Object>();
		List<PersonelBilgileriDetay> personelBilgileriDetayList = new ArrayList<PersonelBilgileriDetay>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map) o;
			PersonelBilgileriDetay personelBilgileriDetay = new PersonelBilgileriDetay();
			int imageLength;

			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal  bsm2ServisKadro = (BigDecimal) map.get("BSM2SERVIS_KADRO");
			String birimAdi = (String) map.get("BIRIMADI");
			String adiSoyadi = (String) map.get("ADISOYADI");
			BigDecimal tcKimlikNo = (BigDecimal) map.get("TCKIMLIKNO");
			String cepTelefonu = (String) map.get("CEPTELEFONU");
			String telefonNumarasi = (String) map.get("TELEFONNUMARASI");
			String elektronikPosta = (String) map.get("ELEKTRONIKPOSTA");
			String dogumYeri = (String) map.get("DOGUMYERI");
			String adres = (String) map.get("ADRES");

			if(id != null)
				personelBilgileriDetay.setId(id.longValue());
			if(bsm2ServisKadro != null)
				personelBilgileriDetay.setBsm2ServisKadro(bsm2ServisKadro.longValue());
			if(birimAdi != null)
				personelBilgileriDetay.setBirimAdi(birimAdi);
			if(adiSoyadi != null)
				personelBilgileriDetay.setAdiSoyadi(adiSoyadi);
			if(tcKimlikNo != null)
				personelBilgileriDetay.setTcKimlikNo(tcKimlikNo.longValue());
			if(cepTelefonu != null)
				personelBilgileriDetay.setCepTelefonu(cepTelefonu);
			if(telefonNumarasi != null)
				personelBilgileriDetay.setTelefonNumarasi(telefonNumarasi);
			if(elektronikPosta != null)
				personelBilgileriDetay.setElektronikPosta(elektronikPosta);
			if(dogumYeri != null)
				personelBilgileriDetay.setDogumYeri(dogumYeri);
			if(adres != null)
				personelBilgileriDetay.setAdres(adres);

			personelBilgileriDetayList.add(personelBilgileriDetay);
		}
		return personelBilgileriDetayList;
	}


	public List<KurumBorc> getDebtStatus(long id, long year){
		String sql = " SELECT 'VergiVeFonlar' Turu, HPL.TANIM Konu, HPL.TOPLAMALACAK TahakkukEden, HPL.TOPLAMBORC Odenen, "
				+" (HPL.TOPLAMALACAK - HPL.TOPLAMBORC) Borc, HPL.MPI1PAYDAS_ID FROM LFI2HESAPPLANI HPL "
				+" WHERE     HPL.BFI1BUTCEDONEMI_ID = (select Max(a.ID) from BFI1BUTCEDONEMI a, FSM1USERS b where a.YILI = " + year 
				+" And a.SM1KURUM_ID = b.MSM1KURUM_ID And b.ID =" + id + ")" 
				+" AND HPL.KODU LIKE '360%' AND HPL.ENALTDUZEY = 'E' AND (HPL.TOPLAMALACAK - HPL.TOPLAMBORC) != 0 UNION ALL SELECT 'SosyalGuvenlik' Turu, HPL.TANIM Konu, "
				+" HPL.TOPLAMALACAK TahakkukEden, HPL.TOPLAMBORC Odenen, (HPL.TOPLAMALACAK - HPL.TOPLAMBORC) Borc, HPL.MPI1PAYDAS_ID FROM LFI2HESAPPLANI HPL "
				+" WHERE     HPL.BFI1BUTCEDONEMI_ID = (select Max(a.ID) from BFI1BUTCEDONEMI a, FSM1USERS b where a.YILI = " + year 
				+" And a.SM1KURUM_ID = b.MSM1KURUM_ID And b.ID =" + id + ")" 
				+" AND HPL.KODU LIKE '361%' AND HPL.ENALTDUZEY = 'E' AND (HPL.TOPLAMALACAK - HPL.TOPLAMBORC) != 0 UNION ALL SELECT 'KurumlaraBorclar' Turu, "
				+" HPL.TANIM Konu, HPL.TOPLAMALACAK TahakkukEden, HPL.TOPLAMBORC Odenen, (HPL.TOPLAMALACAK - HPL.TOPLAMBORC) Borc, HPL.MPI1PAYDAS_ID FROM LFI2HESAPPLANI HPL "
				+" WHERE     HPL.BFI1BUTCEDONEMI_ID = (select Max(a.ID) from BFI1BUTCEDONEMI a, FSM1USERS b where a.YILI = " + year 
				+" And a.SM1KURUM_ID = b.MSM1KURUM_ID And b.ID =" + id + ")" 
				+" AND (HPL.KODU LIKE '362%' OR HPL.KODU LIKE '363%') AND HPL.ENALTDUZEY = 'E' AND (HPL.TOPLAMALACAK - HPL.TOPLAMBORC) != 0 ";
		
		
		List<Object> list = new ArrayList<Object>();
		List<KurumBorc> kurumBorcList =new ArrayList<KurumBorc>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			KurumBorc kurumBorc = new KurumBorc();
			
			String tur = (String)map.get("TURU");
			String konu = (String)map.get("KONU");
			BigDecimal tahakkukEden = (BigDecimal)map.get("TAHAKKUKEDEN");
			BigDecimal odenen = (BigDecimal)map.get("ODENEN");
			BigDecimal borc = (BigDecimal)map.get("BORC");
			BigDecimal mpi1PaydasId = (BigDecimal)map.get("MPI1PAYDAS_ID");
			
			if(tur != null)
				kurumBorc.setTur(tur);
			if(konu != null)
				kurumBorc.setKonu(konu);
			if(tahakkukEden != null)
				kurumBorc.setTahakkukEden(tahakkukEden.doubleValue());
			if(odenen != null)
				kurumBorc.setOdenen(odenen.doubleValue());
			if(borc != null)
				kurumBorc.setBorc(borc.doubleValue());
			if(mpi1PaydasId != null)
				kurumBorc.setId(mpi1PaydasId.longValue());
			
			kurumBorcList.add(kurumBorc);
		}
		return kurumBorcList;
	}

	public List<YapilanOdemeler> getPayments(String startDate, String endDate) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String today = dateFormat.format(date);
		
		String sql = "SELECT B.ID, A.YEVMIYENUMARASI YEVMIYE_NO, A.YEVMIYETARIHI, A.IZAHAT, "
				    +"(SELECT TANIM FROM LFI2HESAPPLANI WHERE ID = B.LFI2HESAPPLANI_ID) "
				    +"BANKAADI,B.TUTAR,B.EVRAKNUMARASI,B.SIRA "
				    +"FROM OFI2MUHASEBEFISI A, KFI2CEK B WHERE A.ID = B.OFI2MUHASEBEFISI_ID "
				    +"AND A.YevmiyeTarihi BETWEEN TO_DATE('"+startDate+"', 'dd-MM-yyyy') and " 
                    +"TO_DATE ('"+endDate+"', 'dd-MM-yyyy') ORDER BY A.ID";
		
		
		List<Object> list = new ArrayList<Object>();
		List<YapilanOdemeler> yapilanOdemelerList = new ArrayList<YapilanOdemeler>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			YapilanOdemeler yapilanOdemeler = new YapilanOdemeler();
			
			BigDecimal id = (BigDecimal)map.get("ID");
			String firmaAdi = (String) map.get("IZAHAT");
			String bankaAdi = (String) map.get("BANKAADI");
			BigDecimal tutar = (BigDecimal)map.get("TUTAR");
			BigDecimal yevmiyeNo = (BigDecimal)map.get("YEVMIYE_NO");
			Date yevmiyeTarihi = (Date) map.get("YEVMIYETARIHI");
			
			if(bankaAdi != null)
				yapilanOdemeler.setBankaAdi(bankaAdi);
			if(firmaAdi != null)
				yapilanOdemeler.setFirmaAdi(firmaAdi);
			if(id != null)
				yapilanOdemeler.setId(id.longValue());
			if(tutar != null)
				yapilanOdemeler.setTutar(tutar.doubleValue());
			if(yevmiyeNo != null)
				yapilanOdemeler.setYevmiyeNo(yevmiyeNo.longValue());
			if(yevmiyeTarihi != null)
				yapilanOdemeler.setDate(dateFormat.format(yevmiyeTarihi));
			
			yapilanOdemelerList.add(yapilanOdemeler);			
		}
		return yapilanOdemelerList;
	}

	public List<FirmaOdeme> getAllPayments(long year, String startDate, String endDate, long personelId){
		String sql = "SELECT A.YEVMIYENUMARASI, A.YEVMIYETARIHI, A.IZAHAT, " +
				" B.TUTAR FROM OFI2MUHASEBEFISI A, KFI2CEK B, RFI2MUHASEBEFISILINE FL " +
				" WHERE FL.OFI2MUHASEBEFISI_ID = A.ID AND FL.KFI2CEK_ID = B.ID " +
				" AND A.ID != 0 AND A.YEVMIYETARIHI BETWEEN TO_DATE('"+startDate+"', 'dd-MM-yyyy') and  " +
				" TO_DATE ('"+endDate+"', 'dd-MM-yyyy') " +
				" AND FL.LFI2HESAPPLANI_ID IN (SELECT ID FROM LFI2HESAPPLANI WHERE BFI1BUTCEDONEMI_ID IN (SELECT ID FROM BFI1BUTCEDONEMI WHERE YILI="+ year + " AND SM1KURUM_ID IN (SELECT MSM1KURUM_ID  FROM FSM1USERS WHERE ID=" + personelId + "))" +
				" AND KODU LIKE '103%') " +
				" AND  A.BFI1BUTCEDONEMI_ID IN (SELECT ID FROM BFI1BUTCEDONEMI WHERE YILI="+ year + " AND SM1KURUM_ID IN (SELECT MSM1KURUM_ID  FROM FSM1USERS WHERE ID=" + personelId + "))" +
				" AND A.FISTIPI not like 'U' " +
				" AND NOT EXISTS (SELECT 1 FROM ASM1PAYDASYETKI AA, ASM1PAYDASYETKILINE BB WHERE AA.ID = BB.ASM1PAYDASYETKI_ID " +
				" AND AA.MPI1PAYDAS_ID = A.MPI1PAYDAS_ID AND BB.FSM1USERS_ID = 0)" +
				" AND NVL(A.FISKAYNAGI,'MUHASEBE') = 'MUHASEBE' ORDER BY A.ID";

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		List<Object> list = new ArrayList<Object>();
		List<FirmaOdeme> firmaOdemelist = new ArrayList<FirmaOdeme>();

		list = query.list();
		for(Object o : list){
			Map map = (Map)o;
			FirmaOdeme firmaOdeme = new FirmaOdeme();

			BigDecimal yevmiyeNumarasi = (BigDecimal)map.get("YEVMIYENUMARASI");
			Date yevmiyeTarihi = (Date)map.get("YEVMIYETARIHI");
			String izahat = (String)map.get("IZAHAT");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(yevmiyeNumarasi != null)
				firmaOdeme.setYevmiyeNumarasi(yevmiyeNumarasi.longValue());
			if(yevmiyeTarihi != null)
				firmaOdeme.setYevmiyeTarihi(yevmiyeTarihi);
			if(izahat != null)
				firmaOdeme.setIzahat(izahat);
			if(tutar != null)
				firmaOdeme.setTutar(tutar.doubleValue());
			firmaOdemelist.add(firmaOdeme);
		}
		return firmaOdemelist;
	}
	
	public List<Basvuru> getApplyCount(String startDate, String endDate){

		String sql= "select b.ALC_BSM2SERVIS_ID,c.TANIM BIRIMADI,Count( Distinct a.ID ) BASVURUSAYISI "
				+"from DDM1ISAKISI a, EDM1ISAKISIADIM b,BSM2SERVIS c "
				+"Where a.ID = b.DDM1ISAKISI_ID "
				+"And c.ID = b.ALC_BSM2SERVIS_ID And b.TARIH BETWEEN TO_DATE('"+startDate+"', 'dd-MM-yyyy') "
 				+"AND TO_DATE ('"+endDate+"', 'dd-MM-yyyy') "
				+"and b.ALC_MSM2ORGANIZASYON_ID <> b.GON_MSM2ORGANIZASYON_ID And a.turu = 'S' "
				+"Group By b.ALC_BSM2SERVIS_ID,c.TANIM "
				+"Order By 3 Desc";
		
		
		List<Object> list = new ArrayList<Object>();
		List<Basvuru> basvuruList = new ArrayList<Basvuru>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			Basvuru basvuru =new Basvuru();
			
			BigDecimal alcBsm2ServisId=(BigDecimal)map.get("ALC_BSM2SERVIS_ID");
			String birimAdi = (String) map.get("BIRIMADI");
			BigDecimal basvuruSayisi = (BigDecimal) map.get("BASVURUSAYISI");
			
			if(alcBsm2ServisId != null)
				basvuru.setId(alcBsm2ServisId.longValue());
			if(birimAdi != null)
				basvuru.setBirimAdi(birimAdi);
			if(basvuruSayisi != null)
				basvuru.setBasvuruSayisi(basvuruSayisi.intValue());
			
			basvuruList.add(basvuru);
		}
		return basvuruList;
	}

	public List<BasvuruOzet> getApplySummary(long birimId, String startDate, String endDate){

		String sql ="select a.ID BASVURUNO, (SELECT x.TANIM FROM ADM1BILDIRIMTURU X WHERE X.ID = a.ADM1BILDIRIMTURU_ID ) BildirimTuru, "
				+"a.ADI, a.SOYADI, a.ISAKISIKONUOZETI, a.KONUSU, a.RRE1ILCE_ADI, a.DRE1MAHALLE_ADI, a.SRE1SOKAK_ADI, a.YEVTELEFONU, "
				+"a.YISTELEFONU, a.YCEPTELEFONU, a.TARIH, a.SONUCDURUMU, a.SONUCU From DDM1ISAKISI a Where a.ID IN ( "
				+"Select Distinct aa.ID from DDM1ISAKISI aa, EDM1ISAKISIADIM bb,BSM2SERVIS cc Where aa.ID = bb.DDM1ISAKISI_ID "
				+"And cc.ID = bb.ALC_BSM2SERVIS_ID And bb.TARIH BETWEEN TO_DATE('"+startDate+"', 'dd-MM-yyyy') "
 				+"AND TO_DATE ('"+endDate+"', 'dd-MM-yyyy') "
				+"and bb.ALC_MSM2ORGANIZASYON_ID <> bb.GON_MSM2ORGANIZASYON_ID and bb.ALC_BSM2SERVIS_ID ="+birimId 
				+" And aa.turu = 'S')";
	
		
		List<Object> list = new ArrayList<Object>();
		List<BasvuruOzet> basvuruOzetList = new ArrayList<BasvuruOzet>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			BasvuruOzet basvuruOzet = new BasvuruOzet();
			
			BigDecimal basvuruNo = (BigDecimal)map.get("BASVURUNO");
			String bildirimTuru = (String) map.get("BILDIRIMTURU");
			String adi = (String) map.get("ADI");
			String soyAdi = (String) map.get("SOYADI");
			String isAkisiKonuOzeti = (String) map.get("ISAKISIKONUOZETI");
			String konusu = (String) map.get("KONUSU");
			String ilceAdi = (String) map.get("RRE1ILCE_ADI");
			String mahalleAdi = (String) map.get("DRE1MAHALLE_ADI");
			String sokakAdi = (String) map.get("SRE1SOKAK_ADI");
			BigDecimal evTelefonu = (BigDecimal) map.get("YEVTELEFONU");
			BigDecimal isTelefonu = (BigDecimal) map.get("YISTELEFONU");
			BigDecimal cepTelefonu = (BigDecimal) map.get("YCEPTELEFONU");
			Date tarih = (Date) map.get("TARIH");
			String sonucDurumu = (String) map.get("SONUCDURUMU");
			String sonucu = (String) map.get("SONUCU");
			
			if(basvuruNo != null)
				basvuruOzet.setBasvuruNo(basvuruNo.longValue());
			if(bildirimTuru != null)
				basvuruOzet.setBildirimTuru(bildirimTuru);
			if(adi != null)
				basvuruOzet.setAdi(adi);
			if(soyAdi != null)
				basvuruOzet.setSoyAdi(soyAdi);
			if(isAkisiKonuOzeti != null)
				basvuruOzet.setIsAkisOzeti(isAkisiKonuOzeti);
			if(konusu != null)
				basvuruOzet.setKonusu(konusu);
			if(ilceAdi != null)
				basvuruOzet.setIlceAdi(ilceAdi);
			if(mahalleAdi != null)
				basvuruOzet.setMahalleAdi(mahalleAdi);
			if(sokakAdi != null)
				basvuruOzet.setSokakAdi(sokakAdi);
			if(evTelefonu != null)
				basvuruOzet.setEvTelefonu(evTelefonu.longValue());
			if(isTelefonu != null)
				basvuruOzet.setIsTelefonu(isTelefonu.longValue());
			if(cepTelefonu != null)
				basvuruOzet.setCepTelefonu(cepTelefonu.longValue());
			if(tarih != null)
				basvuruOzet.setTarih(dateFormat.format(tarih));
			if(sonucDurumu != null)
				basvuruOzet.setSonucDurumu(sonucDurumu);
			if(sonucu != null)
				basvuruOzet.setSonuc(sonucu);
			
			basvuruOzetList.add(basvuruOzet);
		}
		return basvuruOzetList;
	} 
	
	public BasvuruOzetDetay getApplySummaryDetail(long basvuruNo){
		String sql ="select a.ID BASVURUNO, (SELECT x.TANIM FROM ADM1BILDIRIMTURU X WHERE X.ID = a.ADM1BILDIRIMTURU_ID ) BildirimTuru, "
				+"(SELECT x.TANIM FROM HDM1ISAKISITURU X WHERE X.ID = a.HDM1ISAKISITURU_ID ) KonuTuru, "
				+"a.MPI1PAYDAS_ID, a.TCKIMLIKNO, a.ADI, a.SOYADI, a.ISAKISIKONUOZETI, a.KONUSU, a.RRE1ILCE_ADI, "
				+"a.DRE1MAHALLE_ADI, a.SRE1SOKAK_ADI, a.ERE1YAPI_ADI, a.RRE1SITE_ADI, a.KAPINO, a.DAIRENO, "
				+"a.PAFTANO, a.PARSELNO, a.ADANO, a.YEVTELEFONU, a.YISTELEFONU, a.YCEPTELEFONU, a.TARIH, "
				+"a.BEKLENENBITISTARIHI, a.SONUCTARIHI, a.SONUCDURUMU, a.SONUCU "
				+"From DDM1ISAKISI a Where a.ID =" + basvuruNo;
		 
		List<Object> list = new ArrayList<Object>();
		BasvuruOzetDetay basvuruOzetDetay = new BasvuruOzetDetay();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			
			String bildirimTuru = (String) map.get("BILDIRIMTURU");
			String konuTuru = (String) map.get("KONUTURU");
			BigDecimal mpi1PaydasId = (BigDecimal) map.get("MPI1PAYDAS_ID");
			BigDecimal tcKimlikNo = (BigDecimal) map.get("TCKIMLIKNO");
			String adi = (String) map.get("ADI");
			String soyAdi = (String) map.get("SOYADI");
			String isAkisiKonuOzeti = (String) map.get("ISAKISIKONUOZETI");
			String konusu = (String) map.get("KONUSU");
			String ilceAdi = (String) map.get("RRE1ILCE_ADI");
			String mahalleAdi = (String) map.get("DRE1MAHALLE_ADI");
			String sokakAdi = (String) map.get("SRE1SOKAK_ADI");
			String yapiAdi = (String) map.get("ERE1YAPI_ADI");
			String siteAdi = (String) map.get("RRE1SITE_ADI");
			String kapiNo = (String) map.get("KAPINO");
			String daireNo = (String) map.get("DAIRENO");
			String paftaNo = (String) map.get("PAFTANO");
			String adaNo = (String) map.get("ADANO");
			BigDecimal evTelefonu = (BigDecimal) map.get("YEVTELEFONU");
			BigDecimal isTelefonu = (BigDecimal) map.get("YISTELEFONU");
			BigDecimal cepTelefonu = (BigDecimal) map.get("YCEPTELEFONU");
			Date tarih = (Date) map.get("TARIH");
			Date beklenenBitisTarihi = (Date) map.get("BEKLENENBITISTARIHI");
			Date sonucTarihi = (Date) map.get("SONUCTARIHI");
			String sonucDurumu = (String) map.get("SONUCDURUMU");
			String sonucu = (String) map.get("SONUCU");
			
			basvuruOzetDetay.setBasvuruNo(basvuruNo);
			if(bildirimTuru != null)
				basvuruOzetDetay.setBildirimTuru(bildirimTuru);
			if(konuTuru != null)
				basvuruOzetDetay.setKonuTuru(konuTuru);
			if(mpi1PaydasId != null)
				basvuruOzetDetay.setMpi1PaydasId(mpi1PaydasId.longValue());
			if(tcKimlikNo != null)
				basvuruOzetDetay.setTcKimlikNo(tcKimlikNo.longValue());
			if(adi != null)
				basvuruOzetDetay.setAdi(adi);
			if(soyAdi != null)
				basvuruOzetDetay.setSoyAdi(soyAdi);
			if(isAkisiKonuOzeti != null)
				basvuruOzetDetay.setIsAkisOzeti(isAkisiKonuOzeti);
			if(konusu != null)
				basvuruOzetDetay.setKonusu(konusu);
			if(ilceAdi != null)
				basvuruOzetDetay.setIlceAdi(ilceAdi);
			if(mahalleAdi != null)
				basvuruOzetDetay.setMahalleAdi(mahalleAdi);
			if(sokakAdi != null)
				basvuruOzetDetay.setSokakAdi(sokakAdi);
			if(yapiAdi != null)
				basvuruOzetDetay.setYapiAdi(yapiAdi);
			if(siteAdi != null)
				basvuruOzetDetay.setSiteAdi(siteAdi);
			if(kapiNo != null)
				basvuruOzetDetay.setKapiNo(kapiNo);
			if(daireNo != null)
				basvuruOzetDetay.setDaireNo(daireNo);
			if(paftaNo != null)
				basvuruOzetDetay.setPaftaNo(paftaNo);
			if(adaNo != null)
				basvuruOzetDetay.setAdaNo(adaNo);
			if(evTelefonu != null)
				basvuruOzetDetay.setEvTelefonu(evTelefonu.longValue());
			if(isTelefonu != null)
				basvuruOzetDetay.setIsTelefonu(isTelefonu.longValue());
			if(cepTelefonu != null)
				basvuruOzetDetay.setCepTelefonu(cepTelefonu.longValue());
			if(tarih != null)
				basvuruOzetDetay.setTarih(dateFormat.format(tarih));
			if(beklenenBitisTarihi != null)
				basvuruOzetDetay.setBeklenenBitisTarihi(dateFormat.format(beklenenBitisTarihi));
			if(sonucTarihi != null)
				basvuruOzetDetay.setSonucTarihi(dateFormat.format(sonucTarihi));
			if(sonucDurumu != null)
				basvuruOzetDetay.setSonucDurumu(sonucDurumu);
			if(sonucu != null)
				basvuruOzetDetay.setSonuc(sonucu);
			
		}
				
		return basvuruOzetDetay;
	}
	
	public List<FirmaBorc> getFirmDebtStatus(long persid, long year){
		String sql = "SELECT A.ID,A.TARIH,A.ODENEKNO,A.MPI1PAYDAS_ID, DECODE(B.PAYDASTURU,'S',B.ADI"
				+ "||' '||B.SOYADI,B.SOYADI) BIREY_ADI,(SELECT SOYADI FROM MPI1PAYDAS WHERE ID = "
				+ "A.MPI1PAYDAS_ID) BIREY_SOYADI,A.ODENEKKONUSU,A.BSM2SERVIS_ID,(SELECT "
				+ "SUM(KABULEDILENODENEKTUTARI) FROM IFI1ODENEKLINE WHERE HFI1ODENEK_ID = A.ID) "
				+ "KABULEDILENODENEKTUTARI,(SELECT SUM(FO.BORCTUTARI+FO.ALACAKTUTARI) "
				+ "FROM OFI2MUHASEBEFISI FB,RFI2MUHASEBEFISILINE FO,LFI2HESAPPLANI HP WHERE FB.ID "
				+ "= FO.OFI2MUHASEBEFISI_ID AND FO.LFI2HESAPPLANI_ID = HP.ID AND HFI1ODENEK_ID ="
				+ " A.ID AND ( HP.KODU LIKE '103%' OR HP.KODU LIKE '102%' OR HP.KODU LIKE '320%') )"
				+ " ODENECEKNETTUTAR,(SELECT SUM(FO.BORCTUTARI+FO.ALACAKTUTARI) FROM "
				+ "OFI2MUHASEBEFISI FB,RFI2MUHASEBEFISILINE FO,LFI2HESAPPLANI HP WHERE FB.ID "
				+ "= FO.OFI2MUHASEBEFISI_ID AND FO.LFI2HESAPPLANI_ID = HP.ID AND HFI1ODENEK_ID "
				+ "= A.ID AND ( HP.KODU LIKE '103%' OR HP.KODU LIKE '102%') AND FB.FISTIPI = 'I') "
				+ "ODENENNETTUTAR,(SELECT SUM(FO.BORCTUTARI+FO.ALACAKTUTARI) FROM OFI2MUHASEBEFISI"
				+ " FB,RFI2MUHASEBEFISILINE FO,LFI2HESAPPLANI HP WHERE FB.ID = FO.OFI2MUHASEBEFISI_ID"
				+ " AND FO.LFI2HESAPPLANI_ID = HP.ID AND HFI1ODENEK_ID = A.ID AND HP.KODU LIKE '320%'"
				+ " AND FB.FISTIPI = 'I') EMANETTUTAR1,(SELECT SUM(FO.BORCTUTARI+FO.ALACAKTUTARI) "
				+ "FROM OFI2MUHASEBEFISI FB,RFI2MUHASEBEFISILINE FO,LFI2HESAPPLANI HP WHERE "
				+ "FB.ID = FO.OFI2MUHASEBEFISI_ID AND FO.LFI2HESAPPLANI_ID = HP.ID AND "
				+ "HFI1ODENEK_ID = A.ID AND HP.KODU LIKE '320%' AND FB.FISTIPI = 'R')"
				+ " REDDIYATTUTAR FROM HFI1ODENEK A, MPI1PAYDAS B  where 1=1 and b.id = "
				+ "a.MPI1PAYDAS_ID  and a.BFI1BUTCEDONEMI_ID = " + year
				+ "AND NOT EXISTS (SELECT 1 	FROM ASM1PAYDASYETKI AA, ASM1PAYDASYETKILINE BB 	"
				+ "WHERE     AA.ID = BB.ASM1PAYDASYETKI_ID 	AND AA.MPI1PAYDAS_ID = A.MPI1PAYDAS_ID "
				+ "	AND BB.FSM1USERS_ID = " + persid
				+ ") ORDER BY A.TARIH DESC" ;
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		List<Object> list = new ArrayList<Object>();
		List<FirmaBorc> firmaBorclist = new ArrayList<FirmaBorc>();
		
		list = query.list();
		for(Object o : list){
			Map map = (Map)o;
			FirmaBorc firmaBorc = new FirmaBorc();
			
			Date tarih = (Date)map.get("TARIH");
			String adiSoyadi = (String)map.get("BIREY_ADI");
			String odenekKonusu = (String)map.get("ODENEKKONUSU");
			BigDecimal kabulEdilenOdenekTutari = (BigDecimal)map.get("KABULEDILENODENEKTUTARI");
			BigDecimal odenecekNetTutar = (BigDecimal) map.get("ODENECEKNETTUTAR");
			BigDecimal odenenNetTutar = (BigDecimal) map.get("ODENENNETTUTAR");
			
			if(tarih != null)
				firmaBorc.setTarih(dateFormat.format(tarih));
			if(adiSoyadi != null)
				firmaBorc.setAdi(adiSoyadi);
			if(odenekKonusu != null)
				firmaBorc.setOdenekKonusu(odenekKonusu);
			if(kabulEdilenOdenekTutari != null)
				firmaBorc.setOdenecekTutar(kabulEdilenOdenekTutari.doubleValue());
			if(odenecekNetTutar != null)
				firmaBorc.setOdecekNetTutar(odenecekNetTutar.doubleValue());
			if(odenenNetTutar != null)
				firmaBorc.setOdenenNetTutar(odenenNetTutar.doubleValue());
				
			firmaBorclist.add(firmaBorc);
		}
		return firmaBorclist;
				
	}

	public String getFirmaAlacakType(){
		String sql = "SELECT NVL(SM2.F_PARAMETRE('MUHASEBE','MUHASEBEOZETI_FIRMAYI_DEFTERDEN_ALSIN'),'H') FROM DUAL";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		String firmPaymentType = (String)query.uniqueResult();

		return firmPaymentType;

	}

	public List<FirmaAlacak> getFirmaAlacakTypeE(long year, long personelId){
		String sql = "SELECT  ADISOYADI AS TANIM ,SUM(TUTARI) BORC,SUM(ODENEN) ALACAK\n" +
				",SUM(KALAN) BAKIYE  ,  NVL(MPI1PAYDAS_ID,0) AS PAYDAS\n" +
				"FROM AFI2BUTCEEMANETALACAKVIEW  WHERE BFI1BUTCEDONEMI_ID IN (SELECT ID FROM BFI1BUTCEDONEMI WHERE YILI="+ year + " AND SM1KURUM_ID IN (SELECT MSM1KURUM_ID  FROM FSM1USERS WHERE ID=" + personelId + "))" +
				"AND NVL(KALAN,0) >= 0\n" +
				"GROUP BY ADISOYADI,NVL(MPI1PAYDAS_ID,0)\n" +
				"ORDER BY ADISOYADI";

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		List<Object> list = new ArrayList<Object>();
		List<FirmaAlacak> firmaAlacaklist = new ArrayList<FirmaAlacak>();

		list = query.list();
		for(Object o : list){
			Map map = (Map)o;
			FirmaAlacak firmaAlacak = new FirmaAlacak();

			String tanim = (String)map.get("TANIM");
			BigDecimal borc = (BigDecimal)map.get("BORC");
			BigDecimal alacak = (BigDecimal) map.get("ALACAK");
			BigDecimal bakiye = (BigDecimal) map.get("BAKIYE");
			BigDecimal paydas = (BigDecimal) map.get("PAYDAS");


			if(tanim != null)
				firmaAlacak.setTanim(tanim);
			if(borc != null)
				firmaAlacak.setBorc(borc.doubleValue());
			if(alacak != null)
				firmaAlacak.setAlacak(alacak.doubleValue());
			if(bakiye != null)
				firmaAlacak.setBakiye(bakiye.doubleValue());
			if(paydas != null)
				firmaAlacak.setPaydas(paydas.longValue());


			firmaAlacaklist.add(firmaAlacak);
		}
		return firmaAlacaklist;
	}

	public List<FirmaAlacak> getFirmaAlacakTypeH(long year, long personelId){
		String sql = "SELECT   TANIM , SUM(TOPLAMBORC) AS BORC\n" +
				", SUM(TOPLAMALACAK) AS ALACAK \n" +
				",  CASE WHEN SUM(TOPLAMALACAK-TOPLAMBORC) >0 THEN  SUM(TOPLAMALACAK-TOPLAMBORC)\n" +
				"            WHEN SUM(TOPLAMBORC-TOPLAMALACAK) >0 THEN  SUM(TOPLAMBORC-TOPLAMALACAK)\n" +
				"   END BAKIYE\n" +
				", MPI1PAYDAS_ID AS PAYDAS \n" +
				"  FROM LFI2HESAPPLANI WHERE BFI1BUTCEDONEMI_ID IN (SELECT ID FROM BFI1BUTCEDONEMI WHERE YILI="+ year + " AND SM1KURUM_ID IN (SELECT MSM1KURUM_ID  FROM FSM1USERS WHERE ID=" + personelId + "))" +
				"  AND ENALTDUZEY='E'  AND ( (KODU LIKE '320%'   ) )  AND MPI1PAYDAS_ID>0\n" +
				" GROUP BY TANIM,MPI1PAYDAS_ID  ORDER BY MPI1PAYDAS_ID";

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		List<Object> list = new ArrayList<Object>();
		List<FirmaAlacak> firmaAlacaklist = new ArrayList<FirmaAlacak>();

		list = query.list();
		for(Object o : list){
			Map map = (Map)o;
			FirmaAlacak firmaAlacak = new FirmaAlacak();

			String tanim = (String)map.get("TANIM");
			BigDecimal borc = (BigDecimal)map.get("BORC");
			BigDecimal alacak = (BigDecimal) map.get("ALACAK");
			BigDecimal bakiye = (BigDecimal) map.get("BAKIYE");
			BigDecimal paydas = (BigDecimal) map.get("PAYDAS");


			if(tanim != null)
				firmaAlacak.setTanim(tanim);
			if(borc != null)
				firmaAlacak.setBorc(borc.doubleValue());
			if(alacak != null)
				firmaAlacak.setAlacak(alacak.doubleValue());
			if(bakiye != null)
				firmaAlacak.setBakiye(bakiye.doubleValue());
			if(paydas != null)
				firmaAlacak.setPaydas(paydas.longValue());


			firmaAlacaklist.add(firmaAlacak);
		}
		return firmaAlacaklist;
	}

	public List<FirmaOdeme> getFirmaOdeme(long year, String startDate, String endDate){

		String sql = "SELECT A.YEVMIYENUMARASI,\n" +
				"         A.YEVMIYETARIHI,\n" +
				"         A.IZAHAT,\n" +
				"         (RFI2MUHASEBEFISILINE.BORCTUTARI) TUTAR\n" +
				"    FROM OFI2MUHASEBEFISI A,\n" +
				"         RFI2MUHASEBEFISILINE,\n" +
				"         LFI2HESAPPLANI        \n" +
				"   WHERE     (A.ID = RFI2MUHASEBEFISILINE.OFI2MUHASEBEFISI_ID)\n" +
				"         AND (RFI2MUHASEBEFISILINE.LFI2HESAPPLANI_ID = LFI2HESAPPLANI.ID)\n" +
				"                  AND LFI2HESAPPLANI.KODU LIKE '320%'\n" +
				"         AND A.BFI1BUTCEDONEMI_ID IN (SELECT ID\n" +
				"                                        FROM BFI1BUTCEDONEMI\n" +
				"                                       WHERE YILI = " + year + ")\n" +
				"         AND A.FISTIPI NOT LIKE 'U'\n" +
				"         AND A.YEVMIYETARIHI BETWEEN TO_DATE('" + startDate + "', 'dd-MM-yyyy')\n" +
				"                                 AND TO_DATE ('" + endDate + "', 'dd-MM-yyyy')\n" +
				"         AND RFI2MUHASEBEFISILINE.BORCTUTARI > 0\n" +
				"ORDER BY A.YEVMIYETARIHI, A.YEVMIYENUMARASI";


		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		List<Object> list = new ArrayList<Object>();
		List<FirmaOdeme> firmaOdemelist = new ArrayList<FirmaOdeme>();

		list = query.list();
		for(Object o : list){
			Map map = (Map)o;
			FirmaOdeme firmaOdeme = new FirmaOdeme();

			BigDecimal yevmiyeNumarasi = (BigDecimal)map.get("YEVMIYENUMARASI");
			Date yevmiyeTarihi = (Date)map.get("YEVMIYETARIHI");
			String izahat = (String)map.get("IZAHAT");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(yevmiyeNumarasi != null)
				firmaOdeme.setYevmiyeNumarasi(yevmiyeNumarasi.longValue());
			if(yevmiyeTarihi != null)
				firmaOdeme.setYevmiyeTarihi(yevmiyeTarihi);
			if(izahat != null)
				firmaOdeme.setIzahat(izahat);
			if(tutar != null)
				firmaOdeme.setTutar(tutar.doubleValue());
			firmaOdemelist.add(firmaOdeme);
		}
		return firmaOdemelist;
	}
	
	public List<Talep> getRequestCount(String timePeriod){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String today = dateFormat.format(date);
		
//		String sql ="select a.HDM1ISAKISITURU_ID,c.TANIM KONUTURU,Count( Distinct a.ID )TALEPSAYISI from DDM1ISAKISI a, EDM1ISAKISIADIM b,HDM1ISAKISITURU c  Where a.ID = b.DDM1ISAKISI_ID "
//				   +"And c.ID = a.HDM1ISAKISITURU_ID "
//				   +"And b.TARIH BETWEEN TO_DATE('"+timePeriod+"', 'dd/MM/yyyy') "
//				   +"AND TO_DATE ('"+today+"', 'dd/MM/yyyy') "
//				   +"and b.ALC_MSM2ORGANIZASYON_ID <> b.GON_MSM2ORGANIZASYON_ID Group By a.HDM1ISAKISITURU_ID,c.TANIM Order By 3 Desc";
//				  
		String sql ="select a.HDM1ISAKISITURU_ID,c.TANIM KONUTURU,Count( Distinct a.ID )TALEPSAYISI from DDM1ISAKISI a, EDM1ISAKISIADIM b,HDM1ISAKISITURU c  Where a.ID = b.DDM1ISAKISI_ID "
				   +"And c.ID = a.HDM1ISAKISITURU_ID "
				   +"and b.ALC_MSM2ORGANIZASYON_ID <> b.GON_MSM2ORGANIZASYON_ID Group By a.HDM1ISAKISITURU_ID,c.TANIM Order By 3 Desc";
		List<Object> list  = new ArrayList<Object>();
		List<Talep> talepList = new ArrayList<Talep>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			Talep talep = new Talep();
			
			BigDecimal id = (BigDecimal) map.get("HDM1ISAKISITURU_ID");
			String konuTuru = (String) map.get("KONUTURU");
			BigDecimal talepSayisi = (BigDecimal)map.get("TALEPSAYISI");
			
			if(id != null)
				talep.setId(id.longValue());
			if(konuTuru != null)
				talep.setKonuTuru(konuTuru);
			if(talepSayisi != null)
				talep.setTalepSayisi(talepSayisi.intValue());
			
			talepList.add(talep);
		}
		
		return talepList;
	}

	public List<GelirGrubu> getIncomeGroup(String timePeriod) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String today = dateFormat.format(date);
		
		String sql = "SELECT A.EIN1GELIRGRUBU_ID, C.TANIM GELIR_GRUBU_ADI, SUM (A.TUTAR) TUTAR,"
		           +"COUNT (DISTINCT (B.MPI1PAYDAS_ID)) PAYDASSAYISI "
		           +"FROM LIN2TAHSILAT A, NIN2TAHSILATGENEL B, EIN1GELIRGRUBU C "
		           +"WHERE A.NIN2TAHSILATGENEL_ID = B.ID "
		           +"AND A.EIN1GELIRGRUBU_ID = C.ID "
		           +"AND B.TIP = 'K' "
		           +"And B.ISLEMTARIHI BETWEEN TO_DATE('"+timePeriod+"', 'dd/MM/yyyy') "
 				   +"AND TO_DATE ('"+today+"', 'dd/MM/yyyy') "
		           +"AND B.TURU != 'M' "
		           +"GROUP BY A.EIN1GELIRGRUBU_ID, C.TANIM "
		           +"ORDER BY A.EIN1GELIRGRUBU_ID, C.TANIM ";

		
		List<Object> list = new ArrayList<Object>();
		List<GelirGrubu> gelirGrubuList = new ArrayList<GelirGrubu>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GelirGrubu gelirGrubu = new GelirGrubu();
			
			BigDecimal id = (BigDecimal)map.get("EIN1GELIRGRUBU_ID");
			String gelirGrubuAdi = (String) map.get("GELIR_GRUBU_ADI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");
			System.out.println(id);

			if(id != null)
				gelirGrubu.setId(id.longValue());
			if(gelirGrubuAdi != null)
				gelirGrubu.setKey(gelirGrubuAdi);
			if(tutar != null)
				gelirGrubu.setY(tutar.doubleValue());

			
			gelirGrubuList.add(gelirGrubu);
		}
		return gelirGrubuList;
	}
	
	public List<GelirGrubuDetay> getIncomeGroupDetail(long id, String startDate, String endDate){
		String sql = "select a.MPI1PAYDAS_ID, B.ADI, B.SOYADI, a.EIN1GELIRGRUBU_ID, a.GIN1GELIRTURU_ID, a.YILI, a.DONEMI, a.TUTAR,A.ISLEMTARIHI,C.TANIM" 
				    +" FROM LIN2TAHSILAT A, MPI1PAYDAS B,EIN1GELIRGRUBU C" 
				    +" where A.MPI1PAYDAS_ID = B.ID "
				    +" And a.EIN1GELIRGRUBU_ID = C.ID"
				    +" And A.ISLEMTARIHI BETWEEN TO_DATE('"+ startDate +"', 'dd-MM-yyyy')" 
				    +" AND TO_DATE ('"+ endDate +"', 'dd-MM-yyyy')";
		
		if(id !=-1)
			sql += " AND A.EIN1GELIRGRUBU_ID  = " + id;
		
		List<Object> list = new ArrayList<Object>();
		List<GelirGrubuDetay> gelirGrubuDetayList = new ArrayList<GelirGrubuDetay>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			GelirGrubuDetay gelirGrubuDetay = new GelirGrubuDetay();
			
			BigDecimal mpi1PaydasId = (BigDecimal)map.get("MPI1PAYDAS_ID");
			String ad = (String)map.get("ADI");
			String soyad = (String)map.get("SOYADI");
			BigDecimal ein1GelirGrubuId = (BigDecimal) map.get("EIN1GELIRGRUBU_ID");
			BigDecimal gin1GelirTuruId = (BigDecimal) map.get("GIN1GELIRTURU_ID");
			BigDecimal year = (BigDecimal)map.get("YILI");
			BigDecimal donemi = (BigDecimal)map.get("DONEMI");
			BigDecimal tutar = (BigDecimal)map.get("TUTAR");
			Date islemTarihi = (Date)map.get("ISLEMTARIHI");
			String tanim = (String)map.get("TANIM");
			
			if(mpi1PaydasId !=null)
				gelirGrubuDetay.setMp1PaydasId(mpi1PaydasId.longValue());
			if(ad != null && soyad != null)
				gelirGrubuDetay.setAdSoyad(ad + " " + soyad);
			if(ein1GelirGrubuId != null)
				gelirGrubuDetay.setEin1GelirGrubuId(ein1GelirGrubuId.longValue());
			if(gin1GelirTuruId != null)
				gelirGrubuDetay.setGin1GelirTuruId(gin1GelirTuruId.longValue());
			if(year != null)
				gelirGrubuDetay.setYear(year.longValue());
			if(donemi != null)
				gelirGrubuDetay.setDonemi(donemi.longValue());
			if(tutar != null)
				gelirGrubuDetay.setTutar(tutar.doubleValue());
			if(islemTarihi != null)
				gelirGrubuDetay.setIslemTarihi(dateFormat.format(islemTarihi));
			if(tanim != null)
				gelirGrubuDetay.setTanim(tanim);
			
			gelirGrubuDetayList.add(gelirGrubuDetay);
		}
		return gelirGrubuDetayList;
	}

	public List<GelirTuru> getIncomeType(String timePeriod){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String today = dateFormat.format(date);
		
		String sql = "SELECT A.GIN1GELIRTURU_ID, C.TANIM GELIR_ADI, SUM (A.TUTAR) TUTAR,"
		           +" COUNT (DISTINCT (B.MPI1PAYDAS_ID)) PAYDASSAYISI "
		           +"FROM LIN2TAHSILAT A, NIN2TAHSILATGENEL B, GIN1GELIRTURU C "
		           +"WHERE A.NIN2TAHSILATGENEL_ID = B.ID "
		           +"AND A.GIN1GELIRTURU_ID = C.ID "
		           +"AND B.TIP = 'K' "
		           +"And B.ISLEMTARIHI BETWEEN TO_DATE('"+timePeriod+"', 'dd/MM/yyyy') "
		           +"AND TO_DATE ('"+today+"', 'dd/MM/yyyy') "
		           +"AND B.TURU != 'M' "
		           +"GROUP BY A.GIN1GELIRTURU_ID, C.TANIM "
		           +"ORDER BY TUTAR DESC,A.GIN1GELIRTURU_ID, C.TANIM";
		
		List<Object> list = new ArrayList<Object>();
		List<GelirTuru> gelirTuruList = new ArrayList<GelirTuru>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			GelirTuru gelirTuru = new GelirTuru();
			
			BigDecimal gelirTuruId = (BigDecimal) map.get("GIN1GELIRTURU_ID");
			String gelirTuruAdi    = (String) map.get("GELIR_ADI");
			BigDecimal tutar       = (BigDecimal) map.get("TUTAR");
			
			if(gelirTuruId != null)
				gelirTuru.setId(gelirTuruId.longValue());
			if(gelirTuruAdi != null)
				gelirTuru.setGelirTuruAdi(gelirTuruAdi);
			if(tutar != null)
				gelirTuru.setTutar(tutar.doubleValue());
			
			gelirTuruList.add(gelirTuru);
		}
		return gelirTuruList;
	}

	public List<GelirTuru> getIncomeType(String startDate, String endDate){


		String sql = "SELECT A.GIN1GELIRTURU_ID, C.TANIM GELIR_ADI, SUM (A.TUTAR) TUTAR,"
				+" COUNT (DISTINCT (B.MPI1PAYDAS_ID)) PAYDASSAYISI "
				+"FROM LIN2TAHSILAT A, NIN2TAHSILATGENEL B, GIN1GELIRTURU C "
				+"WHERE A.NIN2TAHSILATGENEL_ID = B.ID "
				+"AND A.GIN1GELIRTURU_ID = C.ID "
				+"AND B.TIP = 'K' "
				+"And B.ISLEMTARIHI BETWEEN TO_DATE('"+startDate+"', 'dd-MM-yyyy') "
				+"AND TO_DATE ('"+endDate+"', 'dd-MM-yyyy') "
				+"AND B.TURU != 'M' "
				+"GROUP BY A.GIN1GELIRTURU_ID, C.TANIM "
				+"ORDER BY TUTAR DESC,A.GIN1GELIRTURU_ID, C.TANIM";

		List<Object> list = new ArrayList<Object>();
		List<GelirTuru> gelirTuruList = new ArrayList<GelirTuru>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map)o;
			GelirTuru gelirTuru = new GelirTuru();

			BigDecimal gelirTuruId = (BigDecimal) map.get("GIN1GELIRTURU_ID");
			String gelirTuruAdi    = (String) map.get("GELIR_ADI");
			BigDecimal tutar       = (BigDecimal) map.get("TUTAR");

			if(gelirTuruId != null)
				gelirTuru.setId(gelirTuruId.longValue());
			if(gelirTuruAdi != null)
				gelirTuru.setGelirTuruAdi(gelirTuruAdi);
			if(tutar != null)
				gelirTuru.setTutar(tutar.doubleValue());

			gelirTuruList.add(gelirTuru);
		}
		return gelirTuruList;
	}
	
	public List<GelirTuruDetay> getIncomeTypeDetail(long id, String startDate, String endDate){
		String sql = "select a.MPI1PAYDAS_ID, B.ADI, B.SOYADI, a.EIN1GELIRGRUBU_ID, a.GIN1GELIRTURU_ID, a.YILI, a.DONEMI, a.TUTAR ,A.ISLEMTARIHI,C.TANIM"
				     +" FROM LIN2TAHSILAT A, MPI1PAYDAS B, GIN1GELIRTURU C "
				     +" where A.MPI1PAYDAS_ID = B.ID "
				     +" And a.GIN1GELIRTURU_ID = C.ID"
				     +" AND A.GIN1GELIRTURU_ID = " + id
				     +" And A.ISLEMTARIHI BETWEEN TO_DATE('"+ startDate +"', 'dd-MM-yyyy')" 
				     +" AND TO_DATE ('"+ endDate +"', 'dd-MM-yyyy')"; 
		
		List<Object> list = new ArrayList<Object>();
		List<GelirTuruDetay> gelirTuruDetayList = new ArrayList<GelirTuruDetay>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			GelirTuruDetay gelirTuruDetay = new GelirTuruDetay();
			
			BigDecimal mpi1PaydasId = (BigDecimal)map.get("MPI1PAYDAS_ID");
			String ad = (String)map.get("ADI");
			String soyad = (String)map.get("SOYADI");
			BigDecimal ein1GelirGrubuId = (BigDecimal) map.get("EIN1GELIRGRUBU_ID");
			BigDecimal gin1GelirTuruId = (BigDecimal) map.get("GIN1GELIRTURU_ID");
			BigDecimal year = (BigDecimal)map.get("YILI");
			BigDecimal donemi = (BigDecimal)map.get("DONEMI");
			BigDecimal tutar = (BigDecimal)map.get("TUTAR");
			Date islemTarihi = (Date)map.get("ISLEMTARIHI");
			String tanim = (String)map.get("TANIM");
			
			if(mpi1PaydasId !=null)
				gelirTuruDetay.setMp1PaydasId(mpi1PaydasId.longValue());
			if(ad != null && soyad != null)
				gelirTuruDetay.setAdSoyad(ad + " " + soyad);
			if(ein1GelirGrubuId != null)
				gelirTuruDetay.setEin1GelirGrubuId(ein1GelirGrubuId.longValue());
			if(gin1GelirTuruId != null)
				gelirTuruDetay.setGin1GelirTuruId(gin1GelirTuruId.longValue());
			if(year != null)
				gelirTuruDetay.setYear(year.longValue());
			if(donemi != null)
				gelirTuruDetay.setDonemi(donemi.longValue());
			if(tutar != null)
				gelirTuruDetay.setTutar(tutar.doubleValue());
			if(islemTarihi != null)
				gelirTuruDetay.setIslemTarihi(dateFormat.format(islemTarihi));
			if(tanim != null)
				gelirTuruDetay.setTanim(tanim);
			
			gelirTuruDetayList.add(gelirTuruDetay);
		}
		return gelirTuruDetayList;
	}
	
	@Override
	public List<YbsMenu> getYbsParents(String userName, Long parentID) {
		String sql = "select A.VSM1PROGS_ID,B.TYPE,B.NAME,B.DESCRIPTION,A.PARENT_ID,B.LINK,A.PARAMETER,a.KAYITDUZEYI,"
				+ "DECODE ("
				+ "(SELECT COUNT(*) FROM NSM1MENUS M1 WHERE PARENT_ID=A.VSM1PROGS_ID),"
				+ "0, 'E',"
				+ "'H')ISLEAF "
				+ ",A.id, a.kodu  from "
				+ "( select m.id,m.VSM1PROGS_ID,M.KODU,m.isleaf,m.PARENT_id,m.PARAMETER,m.KAYITDUZEYI from NSM1MENUS m "
				+ " WHERE  m.VSM1PROGS_ID in "
				+ "(select distinct VSM1PROGS_ID from "
				+ "	YSM1PROGSEC "
				+ "	where  ASM1ROLES_ID in "
				+ "	( select a.ASM1ROLES_ID from "
				+ " ISM1USERROLES a, ASM1ROLES b,FSM1USERS c "
				+ "	where  userid = '"
				+ userName + "'"
				+ " AND NVL(B.YBS,'H') ='E'" 
				+ " and a.FSM1USERS_ID = c.id and a.ASM1ROLES_ID = b.id and b.type='E' AND A.ACTIVE='E' "
				+ ")) ";					
		if (parentID==-1)
			sql	+= " and m.kayitduzeyi=2";		
		
		sql	+= "	) A, VSM1PROGS B WHERE A.VSM1PROGS_ID = B.ID ";
		
		if (parentID!=-1)
			sql	+= " and A.parent_id =" + parentID ;
		
		sql += " ORDER BY A.KODU";

		List<Object> list = new ArrayList<Object>();
		List<YbsMenu> ybsMenuList = new ArrayList<YbsMenu>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			YbsMenu ybsMenu = new YbsMenu();
			
			BigDecimal vsm1ProgsId = (BigDecimal) map.get("VSM1PROGS_ID");
			String type = (String)map.get("TYPE");
			String name = (String) map.get("NAME");
			String description = (String)map.get("DESCRIPTION");
			BigDecimal parentId = (BigDecimal)map.get("PARENT_ID");
			String link = (String)map.get("LINK");
			String parameter = (String)map.get("PARAMETER");
			BigDecimal kayitDuzeyi = (BigDecimal) map.get("KAYITDUZEYI");
			String isLeaf = (String)map.get("isLeaf");
			BigDecimal id = (BigDecimal)map.get("ID");
			
			if(vsm1ProgsId != null)
				ybsMenu.setId(String.valueOf(vsm1ProgsId.longValue()));
			if(type != null)
				ybsMenu.setType(type);
			if(name != null)
				ybsMenu.setTitle(name);
			if(description != null)
				ybsMenu.setDescription(description);
			if(parentId != null){
				if(parentId.longValue() == -1)
					ybsMenu.setParent(String.valueOf("#"));
				else
					ybsMenu.setParent(String.valueOf(parentId.longValue()));
			}
			if(link != null)
				ybsMenu.setData(link);
			if(parameter != null)
				ybsMenu.setParameter(parameter);
			if(kayitDuzeyi != null)
				ybsMenu.setKayitDuzeyi(kayitDuzeyi.intValue());
			if(isLeaf != null)
				ybsMenu.setIsLeaf(isLeaf);
			
			ybsMenuList.add(ybsMenu);
		}
		
		return ybsMenuList;
	}

	public List<GraphGeneral> getTodayIncomeGroup(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String today = dateFormat.format(date);
		
		String sql="Select a.EIN1GELIRGRUBU_ID, b.TANIM, SUM(a.TUTAR)TUTAR FROM LIN2TAHSILAT A ,EIN1GELIRGRUBU B "
                + " Where  a.EIN1GELIRGRUBU_ID=b.id and  A.NIN2TAHSILATGENEL_ID>0"
                + " AND   a.ISLEMTARIHI=to_date('" + today + "','dd/MM/yyyy') "
                + " GROUP BY a.EIN1GELIRGRUBU_ID ,b.TANIM";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal)map.get("EIN1GELIRGRUBU_ID");
			String gelirGrubuAdi = (String) map.get("TANIM");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(id!= null)
				graphGeneral.setId(id.longValue());
			if(gelirGrubuAdi != null)
				graphGeneral.setAdi(gelirGrubuAdi);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;

	}
	
	public List<GraphGeneral> getIncomeGroup(String startDate,String endDate){
		String sql = "SELECT A.EIN1GELIRGRUBU_ID, C.TANIM GELIR_GRUBU_ADI, SUM (A.TUTAR) TUTAR,"
		           +"COUNT (DISTINCT (B.MPI1PAYDAS_ID)) PAYDASSAYISI "
		           +"FROM LIN2TAHSILAT A, NIN2TAHSILATGENEL B, EIN1GELIRGRUBU C "
		           +"WHERE A.NIN2TAHSILATGENEL_ID = B.ID "
		           +"AND A.EIN1GELIRGRUBU_ID = C.ID "
		           +"AND B.TIP = 'K' "
		           +"And B.ISLEMTARIHI BETWEEN TO_DATE('"+startDate+"', 'dd-MM-yyyy') "
				   +"AND TO_DATE ('"+endDate+"', 'dd-MM-yyyy') "
		           +"AND B.TURU != 'M' "
		           +"GROUP BY A.EIN1GELIRGRUBU_ID, C.TANIM "
		           +"ORDER BY A.EIN1GELIRGRUBU_ID, C.TANIM ";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal)map.get("EIN1GELIRGRUBU_ID");
			String gelirGrubuAdi = (String) map.get("GELIR_GRUBU_ADI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(id!= null)
				graphGeneral.setId(id.longValue());
			if(gelirGrubuAdi != null)
				graphGeneral.setAdi(gelirGrubuAdi);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
		
	}

	public List<GraphGeneral> getIncomeType(long gelirGrubuId,String startDate,String endDate){
		String    sql = "Select A.GIN1GELIRTURU_ID,B.TANIM as gelirturu,SUM(TUTAR) TUTAR"
                + " FROM LIN2TAHSILAT A , GIN1GELIRTURU B"
                + " Where A.ISLEMTARIHI BETWEEN TO_DATE('"+startDate+"', 'dd-MM-yyyy') "
		        + " AND TO_DATE ('"+endDate+"', 'dd-MM-yyyy') " 
		        + " And A.EIN1GELIRGRUBU_ID = " + gelirGrubuId
                + " AND A.NIN2TAHSILATGENEL_ID>0 and B.id=a.GIN1GELIRTURU_ID "
                + " GROUP BY A.GIN1GELIRTURU_ID,B.TANIM  ";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal)map.get("GIN1GELIRTURU_ID");
			String gelirGrubuAdi = (String) map.get("GELIRTURU");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(id!= null)
				graphGeneral.setId(id.longValue());
			if(gelirGrubuAdi != null)
				graphGeneral.setAdi(gelirGrubuAdi);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
		

	}
	
	public List<GraphGeneral> getIncomeManagementPayOffice(String startDate,String endDate){
		String sql="select A.FINCVEZNE_ID, (Select TANIM FROM FINCVEZNE B WHERE B.ID = A.FINCVEZNE_ID) VeznedarAdi,  Sum(B.Tutar) Tutar "
                + " From NIN2TAHSILATGENEL A,LIN2TAHSILAT B "
                + " Where A.ID = B.NIN2TAHSILATGENEL_ID "
                + " And A.ISLEMTARIHI BETWEEN to_date('"+ startDate +"','dd-MM-yyyy') AND to_date('"+ endDate +"','dd-MM-yyyy') "
                + " Group By A.FINCVEZNE_ID ";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal vezneId = (BigDecimal)map.get("FINCVEZNE_ID");
			String veznedarAdi = (String) map.get("VEZNEDARADI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(vezneId != null)
				graphGeneral.setId(vezneId.longValue());
			if(veznedarAdi != null)
				graphGeneral.setAdi(veznedarAdi);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;

	}
	
	public List<GraphGeneral> getIncomeManagementPayment(String startDate,String endDate){
		String sql = "select A.AINCODEMETURU_ID,(SELECT TANIM FROM AINCODEMETURU WHERE ID=A.AINCODEMETURU_ID) ODEMETURUADI,Sum(B.Tutar) Tutar"
				+ " From NIN2TAHSILATGENEL A,LIN2TAHSILAT B"
				+ " Where A.ID = B.NIN2TAHSILATGENEL_ID"
				+ " And A.ISLEMTARIHI BETWEEN to_date('"+ startDate +"','dd-MM-yyyy') AND to_date('"+ endDate +"','dd-MM-yyyy')  Group By A.AINCODEMETURU_ID";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal odemeId = (BigDecimal)map.get("AINCODEMETURU_ID");
			String odemeAdi = (String) map.get("ODEMETURUADI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(odemeId != null)
				graphGeneral.setId(odemeId.longValue());
			if(odemeAdi != null)
				graphGeneral.setAdi(odemeAdi);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	
	}
	
	public List<GraphGeneral> getIncomeManagementNotification(long year){
		String sql = " select A.EIN1GELIRGRUBU_ID, A.YILI, "
				+ " (Select B.TANIM FROM EIN1GELIRGRUBU B WHERE B.ID = A.EIN1GELIRGRUBU_ID) Turu, Count(*) TOPLAM"
				+ " from AIN2BILDIRIM A "
				+ " Where A.YILI =" + year + " And A.KAYITDURUMU = 'A' "
				+ " Group By EIN1GELIRGRUBU_ID, A.YILI ";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal ein1GelirGrubuId = (BigDecimal)map.get("EIN1GELIRGRUBU_ID");
			String turu = (String) map.get("TURU");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");

			if(ein1GelirGrubuId != null)
				graphGeneral.setId(ein1GelirGrubuId.longValue());
			if(turu != null)
				graphGeneral.setAdi(turu);
			if(toplam != null)
				graphGeneral.setTutar(toplam.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getIncomeManagementNotificationDetail(long gelirGrubuId,long year){
		String sql="select "+ 
				" A.DRE1MAHALLE_ID,"+
				" (Select case when B.TANIM is null then ' ' else B.TANIM end FROM DRE1MAHALLE B WHERE B.ID = A.DRE1MAHALLE_ID) MahalleAdi, "+
				" Count(*) TOPLAM" +
				" from AIN2BILDIRIM A "+
				" Where A.YILI =" + year + 
				" AND A.EIN1GELIRGRUBU_ID ="+ gelirGrubuId+ 
				" And A.KAYITDURUMU = 'A' "+
				" Group By A.DRE1MAHALLE_ID ";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal dre1MahalleId = (BigDecimal)map.get("DRE1MAHALLE_ID");
			String turu = (String) map.get("MAHALLEADI");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");

			if(dre1MahalleId != null)
				graphGeneral.setId(dre1MahalleId.longValue());
			if(turu != null)
				graphGeneral.setAdi(turu);
			if(toplam != null)
				graphGeneral.setTutar(toplam.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getIncomeManagementStreetTahakkuk(long year){
		String sql = " select  B.DRE1MAHALLE_ID, A.YILI,  "
				+ " (Select M.TANIM FROM DRE1MAHALLE M WHERE ID = B.DRE1MAHALLE_ID) MahalleAdi,"
				+ " Sum( DECODE('TAHAKKUK','TAHAKKUK',TahakkukTutari, 'BORC',BorcTutari, 'TAHSILAT',(TahakkukTutari-BorcTutari)) ) Tutari"
				+ " from JIN2TAHAKKUK A, AIN2BILDIRIM B"
				+ " Where A.AIN2BILDIRIM_ID = B.ID  And A.YILI = " + year +" And DRE1MAHALLE_ID is not null"
				+ " Group By B.DRE1MAHALLE_ID, A.YILI  ";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal)map.get("DRE1MAHALLE_ID");
			String turu = (String) map.get("MAHALLEADI");
			BigDecimal tutar = (BigDecimal) map.get("TUTARI");

			if(id != null)
				graphGeneral.setId(id.longValue());
			if(turu != null)
				graphGeneral.setAdi(turu);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
		
	}
	
	public List<GelirlerYonetimiMahalle> getIncomeManagementStreetDetail(long id, String startDate, String endDate){
		String sql = "select a.MPI1PAYDAS_ID, B.ADI, B.SOYADI, a.EIN1GELIRGRUBU_ID, a.GIN1GELIRTURU_ID, a.YILI, a.DONEMI, a.TUTAR , A.ISLEMTARIHI"
				+" FROM LIN2TAHSILAT A, MPI1PAYDAS B, AIN2BILDIRIM C "
				+" where A.MPI1PAYDAS_ID = B.ID "
				+" AND A.AIN2BILDIRIM_ID = C.ID "
				+" AND C.DRE1MAHALLE_ID = " + id
				+" AND A.ISLEMTARIHI BETWEEN to_date('"+startDate+"','dd-MM-yyyy') AND"
				+" to_date('"+endDate+"','dd-MM-yyyy')"; 
		
		List<Object> list = new ArrayList<Object>();
		List<GelirlerYonetimiMahalle> gelirlerYonetimiMahalleList = new ArrayList<GelirlerYonetimiMahalle>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GelirlerYonetimiMahalle gelirlerYonetimiMahalle = new GelirlerYonetimiMahalle();
			
			BigDecimal mpi1PaydasId = (BigDecimal)map.get("MPI1PAYDAS_ID");
			String adi = (String) map.get("ADI");
			String soyadi = (String) map.get("SOYADI");
			BigDecimal ein1GelirGrubuId = (BigDecimal)map.get("EIN1GELIRGRUBU_ID");
			BigDecimal gin1GelirTuruId = (BigDecimal)map.get("GIN1GELIRTURU_ID");
			BigDecimal year = (BigDecimal)map.get("YILI");
			BigDecimal donemi = (BigDecimal)map.get("DONEMI");
			BigDecimal tutar = (BigDecimal)map.get("TUTAR");
			Date islemTarihi = (Date)map.get("ISLEMTARIHI");

			if(mpi1PaydasId != null)
				gelirlerYonetimiMahalle.setMp1PaydasId(mpi1PaydasId.longValue());
			if(adi!= null && soyadi !=null)
				gelirlerYonetimiMahalle.setAdSoyad(adi + " " + soyadi);
			if(ein1GelirGrubuId != null)
				gelirlerYonetimiMahalle.setEin1GelirGrubuId(ein1GelirGrubuId.longValue());
			if(gin1GelirTuruId != null)
				gelirlerYonetimiMahalle.setGinGelirTuruId(gin1GelirTuruId.longValue());
			if(year != null)
				gelirlerYonetimiMahalle.setYear(year.longValue());
			if(donemi != null)
				gelirlerYonetimiMahalle.setDonemi(donemi.longValue());
			if(tutar != null)
				gelirlerYonetimiMahalle.setTutar(tutar.doubleValue());
			if(islemTarihi != null)
				gelirlerYonetimiMahalle.setDate(dateFormat.format(islemTarihi));
			gelirlerYonetimiMahalleList.add(gelirlerYonetimiMahalle);
		}
		return gelirlerYonetimiMahalleList;
		
	}
	
	public List<GraphGeneral> getIncomeManagementStreetBorc(long year){
		String sql="select  B.DRE1MAHALLE_ID, A.YILI, "
				+ " (Select M.TANIM FROM DRE1MAHALLE M WHERE ID = B.DRE1MAHALLE_ID) MahalleAdi,"
				+ " Sum( DECODE('BORC','TAHAKKUK',TahakkukTutari, 'BORC',BorcTutari, 'TAHSILAT',(TahakkukTutari-BorcTutari)) ) Tutari"
				+ " from JIN2TAHAKKUK A, AIN2BILDIRIM B"
				+ "  Where A.AIN2BILDIRIM_ID = B.ID  And A.YILI = " + year +" And DRE1MAHALLE_ID is not null "
				+ " Group By B.DRE1MAHALLE_ID, A.YILI  ";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal)map.get("DRE1MAHALLE_ID");
			String turu = (String) map.get("MAHALLEADI");
			BigDecimal tutar = (BigDecimal) map.get("TUTARI");

			if(id != null)
				graphGeneral.setId(id.longValue());
			if(turu != null)
				graphGeneral.setAdi(turu);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}

	public List<GraphGeneral> getIncomeManagementTaxPayer(String startDate,String endDate){
		String sql = "Select  to_char( to_date(A.ISLEMTARIHI, 'dd/MM/yyyy')) as gun, COUNT(DISTINCT A.MPI1PAYDAS_ID) as paydasSayi"
				+ " FROM NIN2TAHSILATGENEL A,LIN2TAHSILAT B  "
				+ "  WHERE A.ID = B.NIN2TAHSILATGENEL_ID   AND A.TIP = 'K'  "
				+ "  AND A.ISLEMTARIHI >= to_Date('"+startDate+" 00:00:00','dd-MM-yyyy hh24:mi:ss') AND A.ISLEMTARIHI <= to_Date('"+endDate+" 23:59:59','dd-MM-yyyy hh24:mi:ss')   AND A.TURU != 'M'"
				+ " GROUP BY to_date(A.ISLEMTARIHI, 'dd/MM/yyyy'  ) order by  to_date(A.ISLEMTARIHI, 'dd/MM/yyyy'  )  ";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			String gun = (String) map.get("GUN");
			BigDecimal tutar = (BigDecimal) map.get("PAYDASSAYI");

			if(gun != null)
				graphGeneral.setAdi(gun);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}

	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncomeExpense(){

		String sql = "select d.yili, " +
				" (Select Sum(BUTCETUTARI) FROM AFI1BUTCE " +
				" Where BFI1BUTCEDONEMI_ID = a.BFI1BUTCEDONEMI_ID " +
				" And TURU = 'A' AND KAYITDUZEYI = 1) GiderButcesi, " +
				" Sum(Decode(SubStr(c.KODU,1,3),'830',b.BORCTUTARI- b.ALACAKTUTARI,0)) Gider_Gerceklesme, " +
				"       (Select Sum(BUTCETUTARI) FROM AFI1BUTCE " +
				" Where BFI1BUTCEDONEMI_ID = a.BFI1BUTCEDONEMI_ID " +
				" And TURU = 'B' AND KAYITDUZEYI = 1) GelirButcesi, " +
				"       Sum(Decode(SubStr(c.KODU,1,3),'800',b.ALACAKTUTARI - b.BORCTUTARI,0)) Gelir_Gerceklesme, " +
				"     a.BFI1BUTCEDONEMI_ID ,d.TANIM from OFI2MUHASEBEFISI a,RFI2MUHASEBEFISILINE b, " +
				"       LFI2HESAPPLANI c,BFI1BUTCEDONEMI d where a.id = b.OFI2MUHASEBEFISI_ID " +
				"      and b.LFI2HESAPPLANI_ID = c.id and a.BFI1BUTCEDONEMI_ID = d.ID " +
				"       AND A.FISTIPI <> 'U'group by d.yili ,a.BFI1BUTCEDONEMI_ID,d.TANIM " +
				"              having Sum(Decode(SubStr(c.kodU,1,3),'800',b.ALACAKTUTARI,b.BORCTUTARI,0))!=0 " +
				"       ORDER BY D.YILI DESC ";


		
		List<Object> list = new ArrayList<Object>();
		List<FinansmanYonetimiGelirGider> finansmanYonetimiGelirGiderList = new ArrayList<FinansmanYonetimiGelirGider>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			FinansmanYonetimiGelirGider finansmanYonetimiGelirGider = new FinansmanYonetimiGelirGider();

			BigDecimal year = (BigDecimal) map.get("YILI");
			BigDecimal bfi1ButceDonemiId = (BigDecimal) map.get("BFI1BUTCEDONEMI_ID");
			BigDecimal gelir = (BigDecimal) map.get("GELIRBUTCESI");
			BigDecimal gelirGerceklesme = (BigDecimal) map.get("GELIR_GERCEKLESME");
			BigDecimal gider = (BigDecimal) map.get("GIDERBUTCESI");
			BigDecimal giderGerceklesme = (BigDecimal) map.get("GIDER_GERCEKLESME");
			String tanim = (String) map.get("TANIM");

			if(year != null)
				finansmanYonetimiGelirGider.setYear(year.longValue());;
			if(bfi1ButceDonemiId != null)
				finansmanYonetimiGelirGider.setId(bfi1ButceDonemiId.longValue());
			if(gelir != null)
				finansmanYonetimiGelirGider.setGelir(gelir.doubleValue());
			if(gelirGerceklesme != null)
				finansmanYonetimiGelirGider.setGelirGerceklesme(gelirGerceklesme.doubleValue());
			if(gider != null)
				finansmanYonetimiGelirGider.setGider(gider.doubleValue());
			if(giderGerceklesme != null)
				finansmanYonetimiGelirGider.setGiderGerceklesme(giderGerceklesme.doubleValue());
			if(tanim != null)
				finansmanYonetimiGelirGider.setTanim(tanim);
			
		
			finansmanYonetimiGelirGiderList.add(finansmanYonetimiGelirGider);
		}
		return finansmanYonetimiGelirGiderList;
	}

	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncome(long year, long personelId){

		String sql = "Select KODU,TANIM,BUTCETUTARI,ENALTDUZEY,GERCEKLESENTUTAR, " +
		"		( '% '||Round(( GERCEKLESENTUTAR / BUTCETUTARI ) * 100,2) ) YUZDE " +
		" FROM AFI1BUTCE " +
		" Where  BFI1BUTCEDONEMI_ID IN (SELECT ID FROM BFI1BUTCEDONEMI WHERE YILI="+ year + " AND SM1KURUM_ID IN (SELECT MSM1KURUM_ID  FROM FSM1USERS WHERE ID=" + personelId + "))" +
		" And TURU = 'B' " +
		" AND BUTCETUTARI > 0 " +
		" Order By KODU ";


		List<Object> list = new ArrayList<Object>();
		List<FinansmanYonetimiGelirGider> finansmanYonetimiGelirGiderList = new ArrayList<FinansmanYonetimiGelirGider>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list ){
			Map map = (Map) o;
			FinansmanYonetimiGelirGider finansmanYonetimiGelirGider = new FinansmanYonetimiGelirGider();

			BigDecimal gelir = (BigDecimal) map.get("BUTCETUTARI");
			BigDecimal gelirGerceklesme = (BigDecimal) map.get("GERCEKLESENTUTAR");
			String tanim = (String) map.get("TANIM");
			String kodu = (String) map.get("KODU");
			String gelirGerceklesmeYuzdesi = (String) map.get("YUZDE");
			String enAltDuzey = (String) map.get("ENALTDUZEY");

			if(gelir != null)
				finansmanYonetimiGelirGider.setGelir(gelir.doubleValue());
			if(gelirGerceklesme != null)
				finansmanYonetimiGelirGider.setGelirGerceklesme(gelirGerceklesme.doubleValue());
			if(tanim != null)
				finansmanYonetimiGelirGider.setTanim(tanim);
			if(kodu != null)
				finansmanYonetimiGelirGider.setKodu(kodu);
			if(gelirGerceklesmeYuzdesi != null)
				finansmanYonetimiGelirGider.setGelirGerceklesmeYuzdesi(gelirGerceklesmeYuzdesi);
			if(enAltDuzey != null)
				finansmanYonetimiGelirGider.setEnAltDuzey(enAltDuzey);

			finansmanYonetimiGelirGiderList.add(finansmanYonetimiGelirGider);
		}
		return finansmanYonetimiGelirGiderList;
	}

	public List<FinansmanYonetimiGelirGider> getFinancialManagementExpense(long year, long personelId){

		String sql = "Select KODU,TANIM,Sum(BUTCETUTARI) BUTCETUTARI,SUM(GERCEKLESENTUTAR) GERCEKLESENTUTAR, " +
				"       ( '% '||Round(( Sum(GERCEKLESENTUTAR) / Sum(BUTCETUTARI) ) * 100,2) ) GERCEKLESMEYUZDESI, " +
				"        '% '||round(100*(Sum(BUTCETUTARI) / sum(Sum(BUTCETUTARI)) over ()),2) BUTCEPAYI " +
				"  FROM AFI1BUTCE " +
				" Where  BFI1BUTCEDONEMI_ID IN (SELECT ID FROM BFI1BUTCEDONEMI WHERE YILI="+ year + " AND SM1KURUM_ID IN (SELECT MSM1KURUM_ID  FROM FSM1USERS WHERE ID=" + personelId + "))" +
				"  And TURU = 'A' " +
				"  AND KAYITDUZEYI = 4 " +
				"  AND BUTCETUTARI > 0 " +
				" and KODU LIKE " +
				"       (SELECT NSM2PARAMETRE.KURUMKODU || bsm2servis.analitikbutcekodu from NSM2PARAMETRE,bsm2servis " +
				"       where bsm2servis.ID=(select bsm2servis_gorev from ihr1personel where ID=(select IKY_PERSONEL_ID from FSM1USERS where ID=" + personelId + "))) " +
				" GROUP BY KODU,TANIM " +
				" Order By KODU ";

		List<Object> list = new ArrayList<Object>();
		List<FinansmanYonetimiGelirGider> finansmanYonetimiGelirGiderList = new ArrayList<FinansmanYonetimiGelirGider>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list ){
			Map map = (Map) o;
			FinansmanYonetimiGelirGider finansmanYonetimiGelirGider = new FinansmanYonetimiGelirGider();

			BigDecimal gider = (BigDecimal) map.get("BUTCETUTARI");
			BigDecimal giderGerceklesme = (BigDecimal) map.get("GERCEKLESENTUTAR");
			String tanim = (String) map.get("TANIM");
			String kodu = (String) map.get("KODU");
			String giderGerceklesmeYuzdesi = (String) map.get("GERCEKLESMEYUZDESI");
			String butcePayi = (String) map.get("BUTCEPAYI");

			if(gider != null)
				finansmanYonetimiGelirGider.setGider(gider.doubleValue());
			if(giderGerceklesme != null)
				finansmanYonetimiGelirGider.setGiderGerceklesme(giderGerceklesme.doubleValue());
			if(tanim != null)
				finansmanYonetimiGelirGider.setTanim(tanim);
			if(kodu != null)
				finansmanYonetimiGelirGider.setKodu(kodu);
			if(giderGerceklesmeYuzdesi != null)
				finansmanYonetimiGelirGider.setGiderGerceklesmeYuzdesi(giderGerceklesmeYuzdesi);
			if(butcePayi != null)
				finansmanYonetimiGelirGider.setButcePayi(butcePayi);

			finansmanYonetimiGelirGiderList.add(finansmanYonetimiGelirGider);
		}
		return finansmanYonetimiGelirGiderList;
	}

	public List<FinansmanYonetimiGelirGiderAylik> getFinancialManagementIncomeExpenseMonthly(long year){
		String sql = "select aylar.ay, decode(aylar.ay,'1','Ock','2','Sbt','3','Mar','4','Nis','5','May',"
				+" '6','Haz','7','Tem','8','Agu','9','Eyl','10','Ekm','11','Kas','12','Ara') mnth,"
				+" NVL(c.Gelir,0) GELIR,NVL(c.Gider,0) GIDER from(select month(a.yevmiyetarihi) ay," 
				+" Sum(Decode(SubStr(c.KODU,1,3),'800',(b.ALACAKTUTARI - b.BORCTUTARI),0)) Gelir,"  
				+" Sum(Decode(SubStr(c.KODU,1,3),'830',b.BORCTUTARI,0)) Gider   from OFI2MUHASEBEFISI" 
				+" a,RFI2MUHASEBEFISILINE b,LFI2HESAPPLANI c  where a.id = b.OFI2MUHASEBEFISI_ID"  
				+" and b.LFI2HESAPPLANI_ID = c.id  and a.BFI1BUTCEDONEMI_ID =" + year 
				+" group by month(a.YEVMIYETARIHI)) c,CSM2AYLAR aylar  where aylar.ay=c.ay(+)"
				+" and aylar.ay>0 order by aylar.ay"; 
		
		List<Object> list = new ArrayList<Object>();
		List<FinansmanYonetimiGelirGiderAylik> finansmanYonetimiGelirGiderAylikList = new ArrayList<FinansmanYonetimiGelirGiderAylik>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			FinansmanYonetimiGelirGiderAylik finansmanYonetimiGelirGiderAylik = new FinansmanYonetimiGelirGiderAylik();
			
			BigDecimal ay = (BigDecimal) map.get("AY");
			String month = (String) map.get("MNTH");
			BigDecimal gelir = (BigDecimal) map.get("GELIR");
			BigDecimal gider = (BigDecimal) map.get("GIDER");	

			if(ay != null)
				finansmanYonetimiGelirGiderAylik.setAy(ay.longValue());;
			if(gider != null)
				finansmanYonetimiGelirGiderAylik.setGider(gider.doubleValue());
			if(gelir != null)
				finansmanYonetimiGelirGiderAylik.setGelir(gelir.doubleValue());
			if(month != null)
				finansmanYonetimiGelirGiderAylik.setMonth(month);
			
		
			finansmanYonetimiGelirGiderAylikList.add(finansmanYonetimiGelirGiderAylik);
		}
		return finansmanYonetimiGelirGiderAylikList;
	}
	
	public List<FinansmanYonetimiGelirGiderAylik> getFinancialManagementIncomeExpenseDaily(long year,long month){
		String sql = "select gunler.gun, NVL(c.Gelir,0) GELIR, NVL(c.Gider,0) GIDER from   (select"  
				+" F_GUN(a.yevmiyetarihi) gun,  Sum(Decode(SubStr(c.KODU,1,3),'800',"
				+" (b.ALACAKTUTARI - b.BORCTUTARI),0)) Gelir, Sum(Decode(SubStr(c.KODU,1,3),'830',"
				+" b.BORCTUTARI,0)) Gider  from OFI2MUHASEBEFISI a,RFI2MUHASEBEFISILINE b,LFI2HESAPPLANI c" 
				+" where a.id = b.OFI2MUHASEBEFISI_ID   and F_AY(a.YEVMIYETARIHI)=" + month 
				+" and b.LFI2HESAPPLANI_ID = c.id   and a.BFI1BUTCEDONEMI_ID =" + year 
				+" group by F_GUN(a.YEVMIYETARIHI)) c," 
				+" JSM2GUNLEr gunler   where gunler.gun=c.gun(+)  order by gunler.gun";
		
		List<Object> list = new ArrayList<Object>();
		List<FinansmanYonetimiGelirGiderAylik> finansmanYonetimiGelirGiderGunlukList = new ArrayList<FinansmanYonetimiGelirGiderAylik>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			FinansmanYonetimiGelirGiderAylik finansmanYonetimiGelirGiderGunluk = new FinansmanYonetimiGelirGiderAylik();
			
			BigDecimal gun = (BigDecimal) map.get("GUN");
			BigDecimal gelir = (BigDecimal) map.get("GELIR");
			BigDecimal gider = (BigDecimal) map.get("GIDER");	

			if(gun != null)
				finansmanYonetimiGelirGiderGunluk.setAy(gun.longValue());;
			if(gider != null)
				finansmanYonetimiGelirGiderGunluk.setGider(gider.doubleValue());
			if(gelir != null)
				finansmanYonetimiGelirGiderGunluk.setGelir(gelir.doubleValue());
			
		
			finansmanYonetimiGelirGiderGunlukList.add(finansmanYonetimiGelirGiderGunluk);
		}
		return finansmanYonetimiGelirGiderGunlukList;
	}
	
	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncomeBudget(){
		String sql = "select d.yili, (Select Sum(BUTCETUTARI) FROM AFI1BUTCE "
				+ " Where BFI1BUTCEDONEMI_ID = a.BFI1BUTCEDONEMI_ID And TURU = 'B' AND KAYITDUZEYI = 1)"
				+ " GelirButcesi, Sum(Decode(SubStr(c.KODU,1,3),'800',b.ALACAKTUTARI,B.BORCTUTARI,0))" 
				+ " GelirGerceklesme, a.BFI1BUTCEDONEMI_ID , d.TANIM from OFI2MUHASEBEFISI a," 
				+ " RFI2MUHASEBEFISILINE b, LFI2HESAPPLANI c, BFI1BUTCEDONEMI d where a.id ="
				+ " b.OFI2MUHASEBEFISI_ID  and b.LFI2HESAPPLANI_ID = c.id and a.BFI1BUTCEDONEMI_ID ="
				+ " d.ID AND A.FISTIPI <> 'U' group by d.yili ,a.BFI1BUTCEDONEMI_ID,d.TANIM "
				+ " having Sum(Decode(SubStr(c.kodU,1,3),'800',b.ALACAKTUTARI,B.BORCTUTARI,0))!=0" 
				+ " ORDER BY D.YILI DESC";
		
		List<Object> list = new ArrayList<Object>();
		List<FinansmanYonetimiGelirGider> finansmanYonetimiGelirGiderList = new ArrayList<FinansmanYonetimiGelirGider>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			FinansmanYonetimiGelirGider finansmanYonetimiGelirGider = new FinansmanYonetimiGelirGider();
			
			BigDecimal year = (BigDecimal) map.get("YILI");
			BigDecimal bfi1ButceDonemiId = (BigDecimal) map.get("BFI1BUTCEDONEMI_ID");
			BigDecimal gelirGerceklesme = (BigDecimal) map.get("GELIRGERCEKLESME");
			BigDecimal gelirButcesi = (BigDecimal) map.get("GELIRBUTCESI");
			String tanim = (String) map.get("TANIM");

			if(year != null)
				finansmanYonetimiGelirGider.setYear(year.longValue());
			if(bfi1ButceDonemiId != null)
				finansmanYonetimiGelirGider.setId(bfi1ButceDonemiId.longValue());
			if(gelirGerceklesme != null)
				finansmanYonetimiGelirGider.setGider(gelirGerceklesme.doubleValue());
			if(gelirButcesi != null)
				finansmanYonetimiGelirGider.setGelir(gelirButcesi.doubleValue());
			if(tanim != null)
				finansmanYonetimiGelirGider.setTanim(tanim);
			
		
			finansmanYonetimiGelirGiderList.add(finansmanYonetimiGelirGider);
		}
		return finansmanYonetimiGelirGiderList;
	}
	
    public List<FinansmanYonetimiGelirGider> getFinancialManagementExpenseBudget(){
		String sql = "select d.yili, (Select Sum(BUTCETUTARI) FROM AFI1BUTCE Where" 
				+" BFI1BUTCEDONEMI_ID = a.BFI1BUTCEDONEMI_ID And TURU = 'A' AND KAYITDUZEYI = 1)" 
				+" GiderButcesi, Sum(Decode(SubStr(c.KODU,1,3),'830',b.BORCTUTARI,0)) GiderGerceklesme,"
				+" a.BFI1BUTCEDONEMI_ID , d.TANIM from OFI2MUHASEBEFISI a, RFI2MUHASEBEFISILINE b," 
				+" LFI2HESAPPLANI c, BFI1BUTCEDONEMI d where a.id = b.OFI2MUHASEBEFISI_ID and" 
				+" b.LFI2HESAPPLANI_ID = c.id and a.BFI1BUTCEDONEMI_ID = d.ID group by d.yili ,"
				+" a.BFI1BUTCEDONEMI_ID,d.TANIM having Sum(Decode(SubStr(c.kodU,1,3),'830',"
				+" b.BORCTUTARI,0))!=0 ORDER BY D.YILI DESC";
		
		List<Object> list = new ArrayList<Object>();
		List<FinansmanYonetimiGelirGider> finansmanYonetimiGelirGiderList = new ArrayList<FinansmanYonetimiGelirGider>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			FinansmanYonetimiGelirGider finansmanYonetimiGelirGider = new FinansmanYonetimiGelirGider();
			
			BigDecimal year = (BigDecimal) map.get("YILI");
			BigDecimal bfi1ButceDonemiId = (BigDecimal) map.get("BFI1BUTCEDONEMI_ID");
			BigDecimal giderGerceklesme = (BigDecimal) map.get("GIDERGERCEKLESME");
			BigDecimal giderButcesi = (BigDecimal) map.get("GIDERBUTCESI");
			String tanim = (String) map.get("TANIM");

			if(year != null)
				finansmanYonetimiGelirGider.setYear(year.longValue());
			if(bfi1ButceDonemiId != null)
				finansmanYonetimiGelirGider.setId(bfi1ButceDonemiId.longValue());
			if(giderGerceklesme != null)
				finansmanYonetimiGelirGider.setGider(giderGerceklesme.doubleValue());
			if(giderButcesi != null)
				finansmanYonetimiGelirGider.setGelir(giderButcesi.doubleValue());
			if(tanim != null)
				finansmanYonetimiGelirGider.setTanim(tanim);
			
			finansmanYonetimiGelirGiderList.add(finansmanYonetimiGelirGider);
		}
		return finansmanYonetimiGelirGiderList;
		
	}
	
	public List<FinansmanYonetimiTahakkuk> getFinancialManagementTahakkukFirstLevel(long year){
		String sql = "SELECT  A.KODU, A.DUZEYKODU, A.TANIM, A.BUTCETUTARI FROM  AFI1BUTCE A,"
				+ "BFI1BUTCEDONEMI B  WHERE  A.BFI1BUTCEDONEMI_ID=B.ID  AND B.ID>0  AND A.TURU = 'B'" 
				+ "AND A.KAYITDUZEYI = 1  AND A.BUTCETUTARI>0  AND B.YILI=" + year;
		
		List<Object> list = new ArrayList<Object>();
		List<FinansmanYonetimiTahakkuk> finansmanYonetimiTahakkukList = new ArrayList<FinansmanYonetimiTahakkuk>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			FinansmanYonetimiTahakkuk finansmanYonetimiTahakkuk= new FinansmanYonetimiTahakkuk();
			
			String kodu = (String) map.get("KODU");
			String duzeyKodu = (String) map.get("DUZEYKODU");
			String tanim = (String) map.get("TANIM");
			BigDecimal giderButcesi = (BigDecimal) map.get("BUTCETUTARI");

			if(kodu != null)
				finansmanYonetimiTahakkuk.setKodu(kodu);
			if(duzeyKodu !=null)
				finansmanYonetimiTahakkuk.setDuzeyKodu(duzeyKodu);
			if(tanim != null)
				finansmanYonetimiTahakkuk.setTanim(tanim);
			if(giderButcesi != null)
				finansmanYonetimiTahakkuk.setTutar(giderButcesi.doubleValue());
			
			finansmanYonetimiTahakkukList.add(finansmanYonetimiTahakkuk);
		}
		return finansmanYonetimiTahakkukList;
	}
	
	public List<FinansmanYonetimiTahakkuk> getFinancialManagementTahakkukOtherLevels(String kod,int level){
		String sql = "SELECT  A.KODU, A.TANIM, B.YILI, A.BUTCETUTARI FROM AFI1BUTCE A,BFI1BUTCEDONEMI B"
				+" WHERE  A.BFI1BUTCEDONEMI_ID=B.ID AND  A.TURU = 'B'  AND B.YILI=2014 AND  A.KAYITDUZEYI =" + level
				+" AND SUBSTR(A.KODU,1,"+level+") =" + kod;
		
		List<Object> list = new ArrayList<Object>();
		List<FinansmanYonetimiTahakkuk> finansmanYonetimiTahakkukList = new ArrayList<FinansmanYonetimiTahakkuk>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			FinansmanYonetimiTahakkuk finansmanYonetimiTahakkuk= new FinansmanYonetimiTahakkuk();
			
			String kodu = (String) map.get("KODU");
			String tanim = (String) map.get("TANIM");
			BigDecimal yili = (BigDecimal) map.get("YILI");
			BigDecimal giderButcesi = (BigDecimal) map.get("BUTCETUTARI");

			if(kodu != null)
				finansmanYonetimiTahakkuk.setKodu(kodu);
			if(tanim != null)
				finansmanYonetimiTahakkuk.setTanim(tanim);
			if(yili != null)
				finansmanYonetimiTahakkuk.setYil(yili.longValue());
			if(giderButcesi != null)
				finansmanYonetimiTahakkuk.setTutar(giderButcesi.doubleValue());
			
			finansmanYonetimiTahakkukList.add(finansmanYonetimiTahakkuk);
		}
		return finansmanYonetimiTahakkukList;
	}
	
	public List<GraphGeneral> getRealEstateManagement(){
		String sql = "select A.DRE1MAHALLE_ID,B.TANIM,Count(*) TOPLAM FROM ERE1YAPI A,DRE1MAHALLE B"  
				    +" WHERE A.DRE1MAHALLE_ID=B.ID  Group By A.DRE1MAHALLE_ID ,B.TANIM ORDER BY B.TANIM";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal) map.get("DRE1MAHALLE_ID");
			String tanim = (String) map.get("TANIM");
			BigDecimal tutar = (BigDecimal) map.get("TOPLAM");
			
			if(id != null)
				graphGeneral.setId(id.longValue());
			if(tanim != null)
				graphGeneral.setAdi(tanim);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}

	public List<GraphGeneral> getRealEstateManagementDetail(long kod){
		String sql = "select B.ID,B.TANIM, Count(*) TOPLAM FROM ERE1YAPI A,SRE1SOKAK B WHERE B.DRE1MAHALLE_ID =" + kod
				     +" AND B.ID=A.SRE1SOKAK_ID Group By B.ID,B.TANIM ORDER BY B.TANIM";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal) map.get("ID");
			String tanim = (String) map.get("TANIM");
			BigDecimal tutar = (BigDecimal) map.get("TOPLAM");
			
			if(id != null)
				graphGeneral.setId(id.longValue());
			if(tanim != null)
				graphGeneral.setAdi(tanim);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}

	public List<GraphGeneral> getWeddingManagement(){
		String sql = "select F_YIL(c.EVLILIKTARIHI) YIL,Count(*) TOPLAM from BSR7NIKAH c where id> 0 and" 
				+ " c.EVLILIKTARIHI is not null  GROUP BY F_YIL(c.EVLILIKTARIHI) order by" 
				+ " F_YIL(c.EVLILIKTARIHI)";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal yil = (BigDecimal) map.get("YIL");
			BigDecimal tutar = (BigDecimal) map.get("TOPLAM");
			
			if(yil != null)
				graphGeneral.setId(yil.longValue());
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}

	public List<GraphGeneral> getWeddingManagementMonthly(long year){
		String sql = "select b.ay,decode(b.ay,'1','Ock','2','Sbt','3','Mar','4','Nis','5','May','6',"
				+" 'Haz','7','Tem','8','Agu','9','Eyl','10','Ekim','11','Kas','12','Ara') TANIM,nvl(a.cnt,0)TOPLAM"
				+" from (  select Count(*) cnt,F_AY(EVLILIKTARIHI) ay from BSR7NIKAH"   
				+" where F_YIL(EVLILIKTARIHI)= " + year 
				+" group by F_AY(EVLILIKTARIHI)) a,CSM2AYLAR b   where a.ay(+) = b.ay"  
				+" and b.ay>0 order by b.ay";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal ay = (BigDecimal) map.get("AY");
			String tanim = (String) map.get("TANIM");
			BigDecimal tutar = (BigDecimal) map.get("TOPLAM");
			
			if(ay != null)
				graphGeneral.setId(ay.longValue());
			if(tanim != null)
				graphGeneral.setAdi(tanim);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}

	public List<GraphGeneral> getWeddingManagementDaily(long year,long month){
		String sql = "select F_GUN(EVLILIKTARIHI) GUN,count(*) TOPLAM from BSR7NIKAH where "
				+" F_YIL(EVLILIKTARIHI)="+ year + " and F_AY(EVLILIKTARIHI)="+ month + " group by "
				+" F_GUN(EVLILIKTARIHI) order by f_GUN(EVLILIKTARIHI)";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal gun = (BigDecimal) map.get("GUN");
			BigDecimal tutar = (BigDecimal) map.get("TOPLAM");
			
			if(gun != null)
				graphGeneral.setId(gun.longValue());
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}

	public List<YbsWeddingList> getWeddingManagementDailyList(long year, long month, long day){
		String sql = "SELECT ID,EVLILIKCUZDANNUMARASI,TELEFONNUMARASI,IHR1PERSONEL_MEMUR,DMPI1PAYDAS_ID,"
				+ "DAMATCUZDANNO,DAMATKIMLIKNO,DAMATADI,DAMATSOYADI,DAMATBABAADI,DAMATANNEADI,DAMATDOGUMYERI,"
				+ "DRRE1ILCE_ID_DOGYER,DAMATDOGUMTARIHI,DAMATCINSIYET,DAMATMEDENIHALI,DAMATDINI,DAMATASKERLIKNO,"
				+ "DAMATUYRUK,DAMATUYRUKKODU,DPRE1IL_ID,DRRE1ILCE_ID,DDRE1MAHALLE_ID,DAMATCILTNO,DAMATSAYFANO,"
				+ "DAMATSIRANUMARASI,DAMATVERYER,DAMATVERNEDENI,DAMATCUZKAYITNO,DAMATVERTARIH,GELINMPI1PAYDAS_ID,"
				+ "GELINCUZDANNO,GELINKIMLIKID,GELINADI,GELINSOYADI,GELINBABAADI,GELINANNEADI,GELINDOGUMYERI,"
				+ "GRRE1ILCE_ID_DOGYER,GELINDOGUMTARIHI,GELINCINSIYET,GELINMEDENIHALI,GELINDINI,GELINONCKIZSOYADI,"
				+ "GELINUYRUK,GELINUYRUKKODU,GPRE1IL_ID,GRRE1ILCE_ID,GDRE1MAHALLE_ID,GELINCILTNO,GELINSAYFANO,"
				+ "GELINSIRANO,GELINVERYER,GELINVERNEDENI,GELINCUZKAYITNO,GELINVERTARIH,AKRABALIKDERECESI,"
				+ "GELINSRAPOR,DAMATSRAPOR,IDDETI,GELINZUHREVI,DAMATZUHREVI,GELINEHLIYET,DAMATEHLIYET,NISANTARIHI,"
				+ "BILDIRIMTARIHI,GELINADRES,DAMATADRES,TASDIKADI,TASDIKSOYADI,TASDIKUNVANI,DUZENLEMETARIHI,"
				+ "GELINVASIADI,DAMATVASIADI,GELINVASISOYADI,DAMATVASISOYADI,GELINBABASOYADI,DAMATBABASOYADI,"
				+ "GELINANNESOYADI,DAMATANNESOYADI,VATGEC,EVLILIKTARIHI,EVLILIKTURU,EVLILIKYERIILCE,EVLILIKYERITURU,"
				+ "EVLILIKYERTURKODU,DUZENLEYENADI,DUZENLEYENSOYADI,DUZENLEYENUNVANI,ONAYLAYANADI,ONAYLAYANSOYADI,"
				+ "ONAYLAYANUNVANI,AILENO,NUFUSIDARESI,MERNISDUZENLEMETARIHI,MERNISONAYTARIHI,CRUSER,CRDATE,DOSYANO,"
				+ "DDM1ISAKISI_ID,BASILIEVRAKTUTARI,KAYITSURETTUTARI,MUAFIYET,TAHAKKUKTUTARI,DAMATILCEKODU,"
				+ "GELINILCEKODU,GELINVATANDEGISIM,EVLILIKTURKODU,DBRE1ULKE_ID,GBRE1ULKE_ID,EVLENMETESCILNO,"
				+ "GELINKADKOCAHANEBSN,MAHKEMEIZINTARIHI,MAHKEMEIZINSAYISI,RRE1ILCE_MUSTEREK,DRE1MAHALLE_MUSTEREK,"
				+ "SRE1SOKAK_MUSTEREK,DAIREMUSTEREK,GELINMESLEK,PRE1IL_MUSTEREK,IKAMETTARIHMUSTEREK,ONCEKISOYADKULLANIMI,"
				+ "POSTAKODUMUSTEREK,KAPIMUSTEREK,DAMATMESLEK,KADINKUTUKTESCILTAR,IZAHAT,MAHKEMEADI,DAMATCUZDANSERI,"
				+ "GELINCUZDANSERI,OZELKUTUKSIRANO,KAYITTARIHI,KAYITNUMARASI,UPDUSER,UPDDATE,DELETEFLAG,UPDSEQ,"
				+ "DAMATEVLENME,GELINEVLENME,DAMATBASKAULKEVATANDASI,GELINBASKAULKEVATANDASI,IZIN,VGFNO,DAMATSOSYALKURUM,"
				+ "GELINSOSYALKURUM,GELINEVLENMEDENONCEKISOYADI,SITEBLOKMUSTEREK,DAMATDOGUMTARIHIYAZI,GELINDOGUMTARIHIYAZI,"
				+ "EVLILIKTARIHIYAZI,DAMATDOGUMYERIMERNIS,GELINDOGUMYERIMERNIS,DAMATDOGUMYERIMERNISKODU,GELINDOGUMYERIMERNISKODU,"
				+ "GELINBOSANMATARIHI,DAMATMAHKEMEIZINTAR,DAMATMAHKEMEIZINSAYI,DAMATMAHKEMEADI,DAMATIZAHAT,DDRE1MAHALLE_ADI,"
				+ "GDRE1MAHALLE_ADI,IZINSAYISI,DAMATNOTERADI,DAMATNOTERIZINSAYI,DAMATNOTERIZINTAR,GELINNOTERADI,GELINNOTERIZINSAYI"
				+ ",GELINNOTERIZINTAR,DAMATTELEFON,SM1KURUM_ID,EVLILIKSAATI,DAMATIZINKURUMADI,DAMATIZINILILCEADI,DAMATIZINBELGETAR,"
				+ "DAMATIZINBELGESAYI,GELINIZINKURUMADI,GELINIZINILILCEADI,GELINIZINBELGETARIHI,GELINIZINBELGESAYI,DEFTER_KAYIT_NO,"
				+ "SAYFANO,IHR1PERSONEL_ONAYLAYAN_MEMUR,IZAHAT_BEYAN,GELINPASAPORTNO,DAMATPASAPORTNO,NIKAHIPTAL,IPTALDURUMU,"
				+ "IPTALDURUMUIZAHAT,DOSYANUMARASI,KARARNO,MADDE129AKRABA,VEKALETALAN,D_VEKALETALAN,GPRE1IL_ADI_EVADRESI,"
				+ "GRRE1ILCE_ADI_EVADRESI,DPRE1IL_ADI_EVADRESI,DRRE1ILCE_ADI_EVADRESI,PRE1IL_MUSTEREKADI,RRE1ILCE_MUSTEREKADI,"
				+ "DRE1MAHALLE_MUSTEREK_ADI,SRE1SOKAK_MUSTEREK_ADI,TSR7BEYANDURUMU_ID FROM BSR7NIKAH WHERE 1=1 and ID > 0 AND  "
				+ "F_GUN(EVLILIKTARIHI)=" + day
				+ " AND F_YIL(EVLILIKTARIHI)= " + year
				+ "AND F_AY(EVLILIKTARIHI)=" + month;
		
		List<Object> list = new ArrayList<Object>();
		List<YbsWeddingList> ybsWeddingListList = new ArrayList<YbsWeddingList>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			YbsWeddingList ybsWeddingList = new YbsWeddingList();
			
			String dosyaNo = (String)map.get("DOSYANO");
			String erkekKimlikNo = (String)map.get("DAMATKIMLIKNO");
			String erkekAd = (String)map.get("DAMATADI");
			String erkekSoyad = (String)map.get("DAMATSOYADI");
			String kadinKimlikNo = (String)map.get("GELINKIMLIKID");
			String kadinAd = (String) map.get("GELINADI");
			String kadinSoyad = (String)map.get("GELINSOYADI");
			String evlenmeKutukNo = (String)map.get("EVLENMETESCILNO");
			Date kayitTarihi = (Date)map.get("KAYITTARIHI");
			Date evlilikTarihi = (Date)map.get("EVLILIKTARIHI");
			
			if(dosyaNo != null)
				ybsWeddingList.setDosyaNo(dosyaNo);
			if(erkekKimlikNo != null)
				ybsWeddingList.setErkekTcKimlikNo(erkekKimlikNo);
			if(erkekAd != null)
				ybsWeddingList.setErkekAd(erkekAd);
			if(erkekSoyad != null)
				ybsWeddingList.setErkekSoyad(erkekSoyad);
			if(kadinKimlikNo != null)
				ybsWeddingList.setKadinTcKimlikNo(kadinKimlikNo);
			if(kadinAd != null)
				ybsWeddingList.setKadinAd(kadinAd);
			if(kadinSoyad != null)
				ybsWeddingList.setKadinSoyad(kadinSoyad);
			if(evlenmeKutukNo != null)
				ybsWeddingList.setEvlenmeKutukSiraNo(evlenmeKutukNo);
			if(kayitTarihi != null)
				ybsWeddingList.setKayitTarihi(dateFormat.format(kayitTarihi));
			if(evlilikTarihi != null)
				ybsWeddingList.setEvlilikTarihi(dateFormat.format(evlilikTarihi));
		
			ybsWeddingListList.add(ybsWeddingList);
		}
		return ybsWeddingListList;
	}
	
	public List<GraphGeneral> getHealthManagement(){
		String sql = "select F_YIL(c.teshistarihi) YIL,Count(*) TOPLAM from LHE3MUAYENE  c where id> 0 and"
				    +" c.teshistarihi is not null group by  F_YIL(c.teshistarihi)";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal yil = (BigDecimal) map.get("YIL");
			BigDecimal tutar = (BigDecimal) map.get("TOPLAM");
			
			if(yil != null)
				graphGeneral.setId(yil.longValue());
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}

	public List<GraphGeneral> getHealthManagementMonthly(long year){
		String sql = "select b.ay,decode(b.ay,'1','Ock','2','Sbt','3','Mar','4','Nis','5','May',"
				+" '6','Haz','7','Tem','8','Agu','9','Eyl','10','Ekim','11','Kas','12','Ara') TANIM,"
				+" nvl(a.cnt,0) TOPLAM from (  select Count(*) cnt,F_AY(TESHISTARIHI) ay from LHE3MUAYENE"  
				+" where F_YIL(TESHISTARIHI)=" + year
				+" group by F_AY(TESHISTARIHI)) a,CSM2AYLAR b where a.ay(+) = b.ay  and b.ay>0 order by b.ay";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal ay = (BigDecimal) map.get("AY");
			String tanim = (String) map.get("TANIM");
			BigDecimal tutar = (BigDecimal) map.get("TOPLAM");
			
			if(ay != null)
				graphGeneral.setId(ay.longValue());
			if(tanim != null)
				graphGeneral.setAdi(tanim);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}

	public List<GraphGeneral> getHealthManagementDaily(long year,long month){
		String sql = "select gunler.gun,NVL(c.cnt,0) TOPLAM from(  select F_GUN(TESHISTARIHI) gun, "
				+" count(*) cnt  from LHE3MUAYENE   where  F_AY(TESHISTARIHI)=" + month + "and" 
				+" F_YIL(TESHISTARIHI)=" + year 
				+" group by F_GUN(TESHISTARIHI)  order by f_GUN(TESHISTARIHI)) c, JSM2GUNLEr gunler"
				+" where gunler.gun=c.gun(+)  order by gunler.gun" ;
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal gun = (BigDecimal) map.get("GUN");
			BigDecimal tutar = (BigDecimal) map.get("TOPLAM");
			
			if(gun != null)
				graphGeneral.setId(gun.longValue());
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<InsanKaynaklari> getHumanResourcesType(){
		String sql = "select TURU, (SELECT TANIM FROM AHR1PERSONELTURU WHERE AHR1PERSONELTURU.TURU="
				+" IHR1PERSONEL.TURU) TuruAciklama,COUNT(*) TOPLAM from IHR1PERSONEL Where NVL(Id,0)!=0"  
				+" and PERSONELDURUMU='CALISAN' AND CIKISTARIHI IS NULL  AND TURU IS NOT NULL"
				+" GROUP BY TURU  ORDER BY TURU";
		
		List<Object> list = new ArrayList<Object>();
		List<InsanKaynaklari> insanKaynaklariList = new ArrayList<InsanKaynaklari>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			InsanKaynaklari insanKaynaklari = new InsanKaynaklari();
			
			String tur = (String) map.get("TURU");
			String turuAciklama = (String) map.get("TURUACIKLAMA");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");
			
			if(tur !=null)
				insanKaynaklari.setTur(tur);
			if(turuAciklama != null)
				insanKaynaklari.setTurAciklama(turuAciklama);
			if(toplam!= null)
				insanKaynaklari.setToplam(toplam.longValue());
			
		
			insanKaynaklariList.add(insanKaynaklari);
		}
		return insanKaynaklariList;
	}
	
	public List<InsanKaynaklari> getHumanResourcesTypeMudurluk(String type){
		String sql = "select B.TANIM,A.TURU,  DECODE(A.TURU,'M','Memur','I','Isci','D','Daimi Isci','G','Gecici Isci',"
				+" 'S','Sozlesmeli','F','Firma Personeli','L','Meclis Uyesi','O','Stajyer') "
				+" TuruAciklama,  COUNT(*) TOPLAM,B.ID   from IHR1PERSONEL A,BSM2SERVIS B  "
				+" Where A.ID>0  and A.PERSONELDURUMU='CALISAN'   And A.CIKISTARIHI IS NULL  AND"
				+" A.TURU='" + type + "'"  
				+" And A.BSM2SERVIS_GOREV = B.ID   GROUP BY B.TANIM,A.TURU,B.ID   ORDER  BY B.TANIM";
		
		List<Object> list = new ArrayList<Object>();
		List<InsanKaynaklari> insanKaynaklariList = new ArrayList<InsanKaynaklari>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			InsanKaynaklari insanKaynaklari = new InsanKaynaklari();
			
			String tanim = (String) map.get("TANIM");
			String tur = (String) map.get("TURU");
			String turuAciklama = (String) map.get("TURUACIKLAMA");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");
			BigDecimal id = (BigDecimal) map.get("ID");
			
			if(tanim !=null)
				insanKaynaklari.setTanim(tanim);
			if(tur !=null)
				insanKaynaklari.setTur(tur);
			if(turuAciklama != null)
				insanKaynaklari.setTurAciklama(turuAciklama);
			if(toplam!= null)
				insanKaynaklari.setToplam(toplam.longValue());
			if(id!= null)
				insanKaynaklari.setId(id.longValue());
			
		
			insanKaynaklariList.add(insanKaynaklari);
		}
		return insanKaynaklariList;
		
	}

	public List<InsanKaynaklari> getHumanResourcesGender(){
		String sql = "SELECT CINSIYET, DECODE(CINSIYET, 'E','BAY', 'K','BAYAN') TuruAciklama,"
				+" COUNT(*) TOPLAM FROM IHR1PERSONEL WHERE CIKISTARIHI IS NULL AND ID!=0 AND CINSIYET" 
				+" IS NOT NULL GROUP BY CINSIYET";
		
		List<Object> list = new ArrayList<Object>();
		List<InsanKaynaklari> insanKaynaklariList = new ArrayList<InsanKaynaklari>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			InsanKaynaklari insanKaynaklari = new InsanKaynaklari();
			
			String tur = (String) map.get("CINSIYET");
			String turuAciklama = (String) map.get("TURUACIKLAMA");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");
			
			if(tur !=null)
				insanKaynaklari.setTur(tur);
			if(turuAciklama != null)
				insanKaynaklari.setTurAciklama(turuAciklama);
			if(toplam!= null)
				insanKaynaklari.setToplam(toplam.longValue());
			
		
			insanKaynaklariList.add(insanKaynaklari);
		}
		return insanKaynaklariList;
	}
	
	public List<InsanKaynaklari> getHumanResourcesGenderMudurluk(String gender){
		String sql = "select B.ID,B.TANIM,COUNT(*) TOPLAM,A.CINSIYET   from IHR1PERSONEL A,BSM2SERVIS B "
				+" Where A.Id != 0  And A.CIKISTARIHI IS NULL  AND A.CINSIYET='" + gender + "'" 
				+" And A.BSM2SERVIS_GOREV = B.ID  GROUP BY B.TANIM,B.ID,A.CINSIYET  ORDER  BY B.TANIM"; 
		List<Object> list = new ArrayList<Object>();
		List<InsanKaynaklari> insanKaynaklariList = new ArrayList<InsanKaynaklari>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			InsanKaynaklari insanKaynaklari = new InsanKaynaklari();
			
			BigDecimal id = (BigDecimal) map.get("ID");
			String tanim = (String) map.get("TANIM");
			String tur = (String) map.get("CINSIYET");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");
			
			if(id != null)
				insanKaynaklari.setId(id.longValue());
			if(tur !=null)
				insanKaynaklari.setTur(tur);
			if(tanim != null)
				insanKaynaklari.setTanim(tanim);
			if(toplam!= null)
				insanKaynaklari.setToplam(toplam.longValue());
			
			insanKaynaklariList.add(insanKaynaklari);
		}
		return insanKaynaklariList;
	}
	
	public List<InsanKaynaklari> getHumanResourcesJob(){
		String sql = "select B.ID,B.TANIM,Count(*) TOPLAM from IHR1PERSONEL A, LHR1GOREVTURU B "
				+" WHERE A.LHR1GOREVTURU_ID = B.ID AND A.CIKISTARIHI IS NULL AND A.ID != 0" 
				+" AND B.ID != 0 GROUP BY B.ID,B.TANIM order by TOPLAM DESC";
		
		List<Object> list = new ArrayList<Object>();
		List<InsanKaynaklari> insanKaynaklariList = new ArrayList<InsanKaynaklari>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			InsanKaynaklari insanKaynaklari = new InsanKaynaklari();
			
			BigDecimal id = (BigDecimal) map.get("ID");
			String tanim = (String) map.get("TANIM");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");
			
			if(id !=null)
				insanKaynaklari.setId(id.longValue());
			if(tanim != null)
				insanKaynaklari.setTanim(tanim);
			if(toplam!= null)
				insanKaynaklari.setToplam(toplam.longValue());
			
		
			insanKaynaklariList.add(insanKaynaklari);
		}
		return insanKaynaklariList;
	}
	
	public List<InsanKaynaklari> getHumanResourcesAge(){
		String sql = "SELECT (F_Yil(SysDate) - F_Yil(DOGUMTARIHI)) as Yas,COUNT(*) TOPLAM FROM "
				+" IHR1PERSONEL Where Id > 0 AND CIKISTARIHI IS NULL AND DOGUMTARIHI IS NOT"
				+" NULL GROUP BY (F_Yil(SysDate) - F_Yil(DOGUMTARIHI)) ORDER BY (F_Yil(SysDate) -  "
				+" F_Yil(DOGUMTARIHI)) ";
		
		List<Object> list = new ArrayList<Object>();
		List<InsanKaynaklari> insanKaynaklariList = new ArrayList<InsanKaynaklari>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			InsanKaynaklari insanKaynaklari = new InsanKaynaklari();
			
			BigDecimal yas = (BigDecimal) map.get("YAS");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");
			
			if(yas != null)
				insanKaynaklari.setYas(yas.longValue());
			if(toplam!= null)
				insanKaynaklari.setToplam(toplam.longValue());
			
		
			insanKaynaklariList.add(insanKaynaklari);
		}
		return insanKaynaklariList;
		
	}
	
	public List<ToplantiYonetimi> getMeetingManagement(){
		String sql = "select f_yil(karar.tarih)YIL ,count(*) TOPLAM from( select gundem.id gundemPR from"
				+" ( select b.id anahtar from AME1TOPLANTIDONEM a,FME1TOPLANTIDONEMOTURUM b"  
				+" where a.id=b.AME1TOPLANTIDONEM_ID and a.TIP='E') enc,BME1TOPLANTIGUNDEM  gundem" 
				+" where enc.anahtar= gundem.FME1TOPLANTIDONEMOTURUM_ID) gundem,DME1GUNDEMKARAR karar" 
				+" where karar.BME1TOPLANTIGUNDEM_ID=gundemPR   group by f_yil(karar.tarih)"
				+" ORDER BY f_yil(karar.tarih)";
		
		List<Object> list = new ArrayList<Object>();
		List<ToplantiYonetimi> toplantiYonetimiList = new ArrayList<ToplantiYonetimi>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			ToplantiYonetimi toplantiYonetimi = new ToplantiYonetimi();

			BigDecimal yil = (BigDecimal) map.get("YIL");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");
			
			if(yil!=null)
				toplantiYonetimi.setYil(yil.longValue());
			if(toplam!= null)
				toplantiYonetimi.setToplam(toplam.longValue());
			
			toplantiYonetimiList.add(toplantiYonetimi);
		}
		return toplantiYonetimiList;
	}
	
	public List<ToplantiYonetimi> getMeetingManagementMonthly(long year){
		String sql = "select ay.ay, decode(ay.ay,'1','Ock','2','Sbt','3','Mar','4','Nis','5','May',"
				+" '6','Haz','7','Tem','8','Agu','9','Eyl','10','Ekm','11','Kas','12','Ara') mnth  ,"
				+" NVL(kararlar.cnt,0) TOPLAM from     (select f_ay(karar.tarih) kararAy ,count(*) cnt "
				+" from(select gundem.id gundemPR from( select b.id anahtar from AME1TOPLANTIDONEM a,"
				+" FME1TOPLANTIDONEMOTURUM b where a.id=b.AME1TOPLANTIDONEM_ID and a.TIP='E') enc,"
				+" BME1TOPLANTIGUNDEM  gundem     where enc.anahtar= gundem.FME1TOPLANTIDONEMOTURUM_ID) gundem,"
				+" DME1GUNDEMKARAR karar where  karar.BME1TOPLANTIGUNDEM_ID=gundemPR and  f_yil(karar.tarih)=" + year
				+" group by f_ay(karar.tarih)ORDER BY  f_ay(karar.tarih))kararlar ,csm2aylar ay where ay.ay>0 and" 
				+" ay.ay=kararlar.kararAy(+) group by ay.ay,ay.ayadi,kararlar.cnt    order by ay.ay";
		
		List<Object> list = new ArrayList<Object>();
		List<ToplantiYonetimi> toplantiYonetimiList = new ArrayList<ToplantiYonetimi>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			ToplantiYonetimi toplantiYonetimi = new ToplantiYonetimi();

			BigDecimal ay = (BigDecimal) map.get("AY");
			String ayText = (String) map.get("MNTH");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");
			
			if(ay!=null)
				toplantiYonetimi.setAy(ay.longValue());
			if(ayText != null)
				toplantiYonetimi.setAyText(ayText);
			if(toplam!= null)
				toplantiYonetimi.setToplam(toplam.longValue());
			
			toplantiYonetimiList.add(toplantiYonetimi);
		}
		return toplantiYonetimiList;
	}
	
	public List<BelgeYonetim> getDocumentManagement(String startDate, String endDate){
		String sql = "select A.TURU,A.SONUCDURUMU, DECODE(A.TURU,'E','EVRAK','D','DILEKCE',"
				+" 'S','SIKAYET') TURU_1, DECODE(A.SONUCDURUMU,'B','BASLATILMAMIS','I','ISLEMDE',"
				+" 'E','ERTELENDI','T','TAMAMLANDI') SONUCDURUMU_1,  Count(*) TOPLAM  from DDM1ISAKISI A"
				+" Where 1=1  And A.TARIH BETWEEN to_date('"+startDate+"','dd-MM-yyyy') AND"
				+" to_date('"+endDate+"','dd-MM-yyyy' ) AND NVL(A.TURU,'@')!='@'  "
				+" AND NVL(A.SONUCDURUMU,'@')!='@'  Group By A.TURU,A.SONUCDURUMU " 
				+" Order By A.TURU,A.SONUCDURUMU ";
		
		List<Object> list = new ArrayList<Object>();
		List<BelgeYonetim> belgeYonetimList = new ArrayList<BelgeYonetim>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			BelgeYonetim belgeYonetim = new BelgeYonetim();

			String turu = (String) map.get("TURU");
			String sonucDurumu = (String) map.get("SONUCDURUMU");
			String turuText = (String) map.get("TURU_1");
			String sonucDurumuText = (String) map.get("SONUCDURUMU_1");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");
			

			if(turu !=null)
				belgeYonetim.setTuru(turu);
			if(sonucDurumu !=null)
				belgeYonetim.setSonucDurumu(sonucDurumu);
			if(turuText !=null)
				belgeYonetim.setTuruText(turuText);
			if(sonucDurumuText !=null)
				belgeYonetim.setSonucDurumuText(sonucDurumuText);
			if(toplam!= null)
				belgeYonetim.setToplam(toplam.longValue());
			
			belgeYonetimList.add(belgeYonetim);
		}
		return belgeYonetimList;
		
	}
	
	public List<BelgeYonetim> getDocumentManagementMudurluk(String startDate,String endDate,String tur){
		String sql = "select A.BSM2SERVIS_ID, (SELECT B.TANIM FROM BSM2SERVIS B WHERE B.ID =" 
				+" A.BSM2SERVIS_ID) BirimAdi, Count(*) TOPLAM,A.TURU, A.SONUCDURUMU  from DDM1ISAKISI A" 
				+" Where 1=1  And A.TARIH BETWEEN to_date('"+ startDate +"','dd-MM-yyyy')"
				+" AND to_date('"+ endDate +"','dd-MM-yyyy')"  
				+" And A.TURU = '"+ tur +"'"  
				+" Group By A.BSM2SERVIS_ID , A.TURU ,A.SONUCDURUMU";
		
		List<Object> list = new ArrayList<Object>();
		List<BelgeYonetim> belgeYonetimList = new ArrayList<BelgeYonetim>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			BelgeYonetim belgeYonetim = new BelgeYonetim();

			String birimAdi = (String) map.get("BIRIMADI");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");
			

			if(birimAdi !=null)
				belgeYonetim.setBirimAdi(birimAdi);
			if(toplam!= null)
				belgeYonetim.setToplam(toplam.longValue());
			
			belgeYonetimList.add(belgeYonetim);
		}
		return belgeYonetimList;
	}
	
	public List<GraphGeneral> getLicenseCount(){
		String sql = "select  A.YILI ,count(*) TUTAR  from NLI1RUHSAT A, SLI1RUHSATTURU B  "
				   + "Where A.SLI1RUHSATTURU_ID= B.ID AND A.YILI>1999  Group By "
				   + " A.YILI order by A.YILI desc";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal)map.get("YILI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(id!= null)
				graphGeneral.setId(id.longValue());
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getLicenseTypeCount(long year){
		String sql = "select A.SLI1RUHSATTURU_ID,nvl(b.TANIM,' ') TANIM, count(*) TUTAR "
				+ "from NLI1RUHSAT A, SLI1RUHSATTURU B    Where A.SLI1RUHSATTURU_ID= B.ID"
				+ "  AND (b.kisaltma is null or B.KISALTMA!=',') AND A.YILI= " + year
				+ " Group By  b.TANIM,A.SLI1RUHSATTURU_ID order by b.TANIM";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal)map.get("SLI1RUHSATTURU_ID");
			String tanim = (String)map.get("TANIM");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(id!= null)
				graphGeneral.setId(id.longValue());
			if(tanim != null)
				graphGeneral.setAdi(tanim);
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getLicenseTypeMonthlyCount(long year,long id){
		String sql = "select nvl(a.turid,0), b.ay,decode(b.ay,'1','Ock','2','Sbt','3','Mar','4','Nis','5',"
				+ "'May','6','Haz','7','Tem','8','Agu','9','Eyl','10','Ekm','11','Kas','12','Ara') mnth "
				+ ",nvl(a.cnt,0) TUTAR from (select a.SLI1RUHSATTURU_ID turid, F_AY(A.RUHSATTARIHI) ay,count(*)  "
				+ "cnt  from NLI1RUHSAT A, SLI1RUHSATTURU B  Where A.SLI1RUHSATTURU_ID= " + id
				+ "AND A.SLI1RUHSATTURU_ID= B.ID and F_YIL(A.RUHSATTARIHI)= " + year
				+ "Group By  F_AY(A.RUHSATTARIHI),a.SLI1RUHSATTURU_ID) a,CSM2AYLAR b where a.ay(+) = "
				+ "b.ay and b.ay>0 order by b.ay";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal ay = (BigDecimal) map.get("AY");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(ay!= null)
				graphGeneral.setId(ay.longValue());
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getLicenseTypeDailyCount(long year,long month,long id){
		String sql = "select  F_GUN(A.RUHSATTARIHI) gun,count(*) cnt,YILI ,F_AY(A.RUHSATTARIHI) ,A.SLI1RUHSATTURU_ID"
				+ "   from NLI1RUHSAT A, SLI1RUHSATTURU B  Where A.SLI1RUHSATTURU_ID= " + id
				+ " AND A.SLI1RUHSATTURU_ID= "
				+ " B.ID and A.YILI="  +year
				+ " AND  F_AY(A.RUHSATTARIHI)=" + month
				+ " Group By  F_GUN(A.RUHSATTARIHI),YILI,"
				+ "F_AY(A.RUHSATTARIHI) ,A.SLI1RUHSATTURU_ID  order by  F_GUN(A.RUHSATTARIHI)";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal gun = (BigDecimal) map.get("GUN");
			BigDecimal tutar = (BigDecimal) map.get("CNT");

			if(gun != null)
				graphGeneral.setId(gun.longValue());
			if(tutar != null)
				graphGeneral.setTutar(tutar.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<YbsLicenseList> getLicenseTypeDailyList(long year, long month, long day, long id){
		String sql = "SELECT  (select tanim from SLI1RUHSATTURU where id= NLI1RUHSAT.SLI1RUHSATTURU_ID  )TANIM"
				+ ", NLI1RUHSAT.ID,NLI1RUHSAT.ELI1RUHSATDOSYA_ID,NLI1RUHSAT.EIN1GELIRGRUBU_ID,"
				+ "NLI1RUHSAT.SLI1RUHSATTURU_ID,NLI1RUHSAT.YILI,NLI1RUHSAT.RUHSATID,NLI1RUHSAT.RUHSATTARIHI,"
				+ "NLI1RUHSAT.BASLAMATARIHI,NLI1RUHSAT.BITISTARIHI,NLI1RUHSAT.KAYITDURUMU,NLI1RUHSAT.KAYITTARIHI,"
				+ "NLI1RUHSAT.ERE1YAPI_ID,NLI1RUHSAT.IZAHAT,NLI1RUHSAT.ENCUMENKARARTARIHI,NLI1RUHSAT.ENCUMENKARARNO,"
				+ "NLI1RUHSAT.ISYERIUNVANI,NLI1RUHSAT.KANUNMADDESI,NLI1RUHSAT.KANUNFIKRASI,NLI1RUHSAT.MPI1PAYDAS_ID,"
				+ "NLI1RUHSAT.MEVKI,NLI1RUHSAT.TABELABOYU,NLI1RUHSAT.TABELAENI,NLI1RUHSAT.DURUMU,"
				+ "NLI1RUHSAT.BSM2SERVIS_ID,NLI1RUHSAT.ACILISTURU,NLI1RUHSAT.DEVIRNUMARASI,NLI1RUHSAT.UPDUSER,"
				+ "NLI1RUHSAT.UPDDATE,NLI1RUHSAT.DELETEFLAG,NLI1RUHSAT.CRUSER,NLI1RUHSAT.CRDATE,NLI1RUHSAT."
				+ "UPDSEQ,NLI1RUHSAT.GLI1FALIYET_ID,ELI1RUHSATDOSYA.DRE1MAHALLE_ID,ELI1RUHSATDOSYA.SRE1SOKAK_ID,"
				+ " ELI1RUHSATDOSYA.DAIRENO,NLI1RUHSAT.ONAYNO,ELI1RUHSATDOSYA.KAPINO,NLI1RUHSAT.ARACPLAKA,"
				+ "NLI1RUHSAT.ARACMODELI,NLI1RUHSAT.SUKAYNAKADI,NLI1RUHSAT.KAYNAKIZIN,NLI1RUHSAT.IHR1PERSONEL_BELGESORUMLU,"
				+ "NLI1RUHSAT.IHR1PERSONEL_TEKNIKSORUMLU,RUHSATONAYTARIHI,ELI1RUHSATDOSYA.DOSYAREFERANSNO,"
				+ "(SELECT SORGUADI FROM MPI1PAYDAS WHERE MPI1PAYDAS.ID=NLI1RUHSAT.MPI1PAYDAS_ID) ADSOYAD,NLI1RUHSAT.KAPINOHARF"
				+ ",NLI1RUHSAT.KAPINOSAYI,NLI1RUHSAT.DAIRENOHARF,NLI1RUHSAT.DAIRENOSAYI,ELI1RUHSATDOSYA.DRE1BAGBOLUM_ID,"
				+ "NLI1RUHSAT.KULLANIMALANI  FROM NLI1RUHSAT,MPI1PAYDAS,ELI1RUHSATDOSYA where NLI1RUHSAT.id>0 AND"
				+ " NLI1RUHSAT.ELI1RUHSATDOSYA_ID=ELI1RUHSATDOSYA.ID AND NLI1RUHSAT.MPI1PAYDAS_ID=MPI1PAYDAS.ID AND"
				+ " NLI1RUHSAT.YILI = '" + year + "'"
				+ " AND F_AY(NLI1RUHSAT.RUHSATTARIHI)  = '" + month + "'"
				+ " AND F_GUN(NLI1RUHSAT.RUHSATTARIHI)  = '" + day + "'";
				if(id != -1){
					sql += "AND NLI1RUHSAT.SLI1RUHSATTURU_ID   = '" + id + "'";
				}
				
		
		List<Object> list = new ArrayList<Object>();
		List<YbsLicenseList> ybsLicenseListList = new ArrayList<YbsLicenseList>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			YbsLicenseList ybsLicenseList = new YbsLicenseList();
			
			String tanim = (String)map.get("TANIM");
			String adSoyad = (String)map.get("ADSOYAD");
			BigDecimal yil = (BigDecimal)map.get("YILI");
			Date date =(Date)map.get("RUHSATTARIHI");
			String isyeriUnvani = (String) map.get("ISYERIUNVANI");
			BigDecimal ruhsatNo = (BigDecimal)map.get("RUHSATID");
			
			if(tanim != null)
				ybsLicenseList.setTanim(tanim);
			if(adSoyad !=null)
				ybsLicenseList.setAdSoyad(adSoyad);
			if(yil != null)
				ybsLicenseList.setYear(yil.longValue());
			if(date != null)
				ybsLicenseList.setDate(dateFormat.format(date));
			if(isyeriUnvani != null)
				ybsLicenseList.setIsyeriUnvani(isyeriUnvani);
			if(ruhsatNo != null)
				ybsLicenseList.setRuhsatNo(ruhsatNo.longValue());
		
			ybsLicenseListList.add(ybsLicenseList);
		}
		return ybsLicenseListList;
	}
	
	public List<YbsGununOzeti> getDailySummary(){
		String sql =" SELECT " + 
				" MIS.P_BILGIGETIR('GELIRLERYONETIMI',1) gelirlerYonetimi1, " +
				" MIS.P_BILGIGETIR('GELIRLERYONETIMI',2) gelirlerYonetimi2, " +
				" MIS.P_BILGIGETIR('GELIRLERYONETIMI',3) gelirlerYonetimi3, " +
				" MIS.P_BILGIGETIR('GELIRLERYONETIMI',4) gelirlerYonetimi4, " +
				" MIS.P_BILGIGETIR('GELIRLERYONETIMI',5) gelirlerYonetimi5, " +
				" MIS.P_BILGIGETIR('GELIRLERYONETIMI',6) gelirlerYonetimi6, " +
				" MIS.P_BILGIGETIR('GELIRLERYONETIMI',7) gelirlerYonetimi7, " +
			    
				" MIS.P_BILGIGETIR('FINANSMANYONETIMI',1) finansmanYonetimi1, " +
				" MIS.P_BILGIGETIR('FINANSMANYONETIMI',2) finansmanYonetimi2, " +
				" MIS.P_BILGIGETIR('FINANSMANYONETIMI',3) finansmanYonetimi3, " +
				" MIS.P_BILGIGETIR('FINANSMANYONETIMI',4) finansmanYonetimi4, " +
				" MIS.P_BILGIGETIR('FINANSMANYONETIMI',5) finansmanYonetimi5, " +
				" MIS.P_BILGIGETIR('FINANSMANYONETIMI',6) finansmanYonetimi6, " +
			    
				" MIS.P_BILGIGETIR('BELGEYONETIMI',1) belgeYonetim1, " +
				" MIS.P_BILGIGETIR('BELGEYONETIMI',2) belgeYonetim2, " +
				" MIS.P_BILGIGETIR('BELGEYONETIMI',3) belgeYonetim3, " +

				" MIS.P_BILGIGETIR('INSANKAYNAKLARIYONETIMI',1) insanKaynaklariYonetimi1, " +
				" MIS.P_BILGIGETIR('INSANKAYNAKLARIYONETIMI',2) insanKaynaklariYonetimi2, " +
				" MIS.P_BILGIGETIR('INSANKAYNAKLARIYONETIMI',3) insanKaynaklariYonetimi3, " +
			    
				" MIS.P_BILGIGETIR('RUHSATYONETIMI',1) ruhsatYonetimi1, " +
				" MIS.P_BILGIGETIR('RUHSATYONETIMI',2) ruhsatYonetimi2, " +
			    
				" MIS.P_BILGIGETIR('GAYRIMENKULYONETIMI',1) gayrimenkulYonetimi1, " +
				" MIS.P_BILGIGETIR('GAYRIMENKULYONETIMI',2) gayrimenkulYonetimi2, " +
				" MIS.P_BILGIGETIR('GAYRIMENKULYONETIMI',3) gayrimenkulYonetimi3, " +
				" MIS.P_BILGIGETIR('GAYRIMENKULYONETIMI',4) gayrimenkulYonetimi4, " +
			    
				" MIS.P_BILGIGETIR('HIZMETYONETIMI',1) hizmetYonetimi1, " +
				" MIS.P_BILGIGETIR('HIZMETYONETIMI',2) hizmetYonetimi2, " +
				
				" MIS.P_BILGIGETIR('INSANKAYNAKLARIYONETIMI',4) insanKaynaklariYonetimi4, " + 
				" MIS.P_BILGIGETIR('INSANKAYNAKLARIYONETIMI',5) insanKaynaklariYonetimi5, " + 
				" MIS.P_BILGIGETIR('INSANKAYNAKLARIYONETIMI',6) insanKaynaklariYonetimi6, " + 
				
				" MIS.P_BILGIGETIR('FINANSMANYONETIMI',9) finansmanYonetimi9, " +
				" MIS.P_BILGIGETIR('FINANSMANYONETIMI',10) finansmanYonetimi10 " +
				
				" FROM DUAL";
		
		List<Object> list = new ArrayList<Object>();
		List<YbsGununOzeti> ybsGununOzetiList = new ArrayList<YbsGununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			YbsGununOzeti ybsGununOzeti = new YbsGununOzeti();
			
			String gelirlerYonetimi1 = (String)map.get("GELIRLERYONETIMI1");
			String gelirlerYonetimi2 = (String)map.get("GELIRLERYONETIMI2");
			String gelirlerYonetimi3 = (String)map.get("GELIRLERYONETIMI3");
			String gelirlerYonetimi4 = (String)map.get("GELIRLERYONETIMI4");
			String gelirlerYonetimi5 = (String)map.get("GELIRLERYONETIMI5");
			String gelirlerYonetimi6 = (String)map.get("GELIRLERYONETIMI6");
			String gelirlerYonetimi7 = (String)map.get("GELIRLERYONETIMI7");
			
			String finansmanYonetimi1 = (String)map.get("FINANSMANYONETIMI1");
			String finansmanYonetimi2 = (String)map.get("FINANSMANYONETIMI2");
			String finansmanYonetimi3 = (String)map.get("FINANSMANYONETIMI3");
			String finansmanYonetimi4 = (String)map.get("FINANSMANYONETIMI4");
			String finansmanYonetimi5 = (String)map.get("FINANSMANYONETIMI5");
			String finansmanYonetimi6 = (String)map.get("FINANSMANYONETIMI6");
			
			String belgeYonetim1 = (String)map.get("BELGEYONETIM1");
			String belgeYonetim2 = (String)map.get("BELGEYONETIM2");
			String belgeYonetim3 = (String)map.get("BELGEYONETIM3");
			
			String insanKaynaklariYonetimi1 = (String)map.get("INSANKAYNAKLARIYONETIMI1");
			String insanKaynaklariYonetimi2 = (String)map.get("INSANKAYNAKLARIYONETIMI2");
			String insanKaynaklariYonetimi3 = (String)map.get("INSANKAYNAKLARIYONETIMI3");
			
			String ruhsatYonetimi1 = (String)map.get("RUHSATYONETIMI1");
			String ruhsatYonetimi2 = (String)map.get("RUHSATYONETIMI2");
			
			String gayrimenkulYonetimi1 = (String)map.get("GAYRIMENKULYONETIMI1");
			String gayrimenkulYonetimi2 = (String)map.get("GAYRIMENKULYONETIMI2");
			String gayrimenkulYonetimi3 = (String)map.get("GAYRIMENKULYONETIMI3");
			String gayrimenkulYonetimi4 = (String)map.get("GAYRIMENKULYONETIMI4");
			
			String hizmetYonetimi1 = (String)map.get("HIZMETYONETIMI1");
			String hizmetYonetimi2 = (String)map.get("HIZMETYONETIMI2");
			
			String insanKaynaklariYonetimi4 = (String)map.get("INSANKAYNAKLARIYONETIMI4");
			String insanKaynaklariYonetimi5 = (String)map.get("INSANKAYNAKLARIYONETIMI5");
			String insanKaynaklariYonetimi6 = (String)map.get("INSANKAYNAKLARIYONETIMI6");

			String finansmanYonetimi9  = (String)map.get("FINANSMANYONETIMI9");
			String finansmanYonetimi10 = (String)map.get("FINANSMANYONETIMI10");
			
			if(gelirlerYonetimi1 != null)
				ybsGununOzeti.setGelirlerYonetimi1(gelirlerYonetimi1);
			if(gelirlerYonetimi2 != null)
				ybsGununOzeti.setGelirlerYonetimi2(gelirlerYonetimi2);
			if(gelirlerYonetimi3 != null)
				ybsGununOzeti.setGelirlerYonetimi3(gelirlerYonetimi3);
			if(gelirlerYonetimi4 != null)
				ybsGununOzeti.setGelirlerYonetimi4(gelirlerYonetimi4);
			if(gelirlerYonetimi5 != null)
				ybsGununOzeti.setGelirlerYonetimi5(gelirlerYonetimi5);
			if(gelirlerYonetimi6 != null)
				ybsGununOzeti.setGelirlerYonetimi6(gelirlerYonetimi6);
			if(gelirlerYonetimi7 != null)
				ybsGununOzeti.setGelirlerYonetimi7(gelirlerYonetimi7);
			
			if(finansmanYonetimi1 != null)
				ybsGununOzeti.setFinansmanYonetimi1(finansmanYonetimi1);
			if(finansmanYonetimi2 != null)
				ybsGununOzeti.setFinansmanYonetimi2(finansmanYonetimi2);
			if(finansmanYonetimi3 != null)
				ybsGununOzeti.setFinansmanYonetimi3(finansmanYonetimi3);
			if(finansmanYonetimi4 != null)
				ybsGununOzeti.setFinansmanYonetimi4(finansmanYonetimi4);
			if(finansmanYonetimi5 != null)
				ybsGununOzeti.setFinansmanYonetimi5(finansmanYonetimi5);
			if(finansmanYonetimi6 != null)
				ybsGununOzeti.setFinansmanYonetimi6(finansmanYonetimi6);
			
			if(belgeYonetim1 != null)
				ybsGununOzeti.setBelgeYonetim1(belgeYonetim1);
			if(belgeYonetim2 != null)
				ybsGununOzeti.setBelgeYonetim2(belgeYonetim2);
			if(belgeYonetim3 != null)
				ybsGununOzeti.setBelgeYonetim3(belgeYonetim3);
			
			if(insanKaynaklariYonetimi1 != null)
				ybsGununOzeti.setInsanKaynaklariYonetimi1(insanKaynaklariYonetimi1);
			if(insanKaynaklariYonetimi2 != null)
				ybsGununOzeti.setInsanKaynaklariYonetimi2(insanKaynaklariYonetimi2);
			if(insanKaynaklariYonetimi3 != null)
				ybsGununOzeti.setInsanKaynaklariYonetimi2(insanKaynaklariYonetimi3);
			
			if(ruhsatYonetimi1 != null)
				ybsGununOzeti.setRuhsatYonetimi1(ruhsatYonetimi1);
			if(ruhsatYonetimi2 != null)
				ybsGununOzeti.setRuhsatYonetimi2(ruhsatYonetimi2);
			
			if(gayrimenkulYonetimi1 != null)
				ybsGununOzeti.setGayrimenkulYonetimi1(gayrimenkulYonetimi1);
			if(gayrimenkulYonetimi2 != null)
				ybsGununOzeti.setGayrimenkulYonetimi2(gayrimenkulYonetimi2);
			if(gayrimenkulYonetimi3 != null)
				ybsGununOzeti.setGayrimenkulYonetimi3(gayrimenkulYonetimi3);
			if(gayrimenkulYonetimi4 != null)
				ybsGununOzeti.setGayrimenkulYonetimi4(gayrimenkulYonetimi4);
			
			if(hizmetYonetimi1 != null)
				ybsGununOzeti.setHizmetYonetimi1(hizmetYonetimi1);
			if(hizmetYonetimi2 != null)
				ybsGununOzeti.setHizmetYonetimi2(hizmetYonetimi2);
			
			if(insanKaynaklariYonetimi4 != null)
				ybsGununOzeti.setInsanKaynaklariYonetimi4(insanKaynaklariYonetimi4);
			if(insanKaynaklariYonetimi5 != null)
				ybsGununOzeti.setInsanKaynaklariYonetimi5(insanKaynaklariYonetimi5);
			if(insanKaynaklariYonetimi5 != null)
				ybsGununOzeti.setInsanKaynaklariYonetimi6(insanKaynaklariYonetimi6);
		
			if(finansmanYonetimi9 != null)
				ybsGununOzeti.setFinansmanYonetimi9(finansmanYonetimi9);
			if(finansmanYonetimi10 != null)
				ybsGununOzeti.setFinansmanYonetimi10(finansmanYonetimi10);
			
			ybsGununOzetiList.add(ybsGununOzeti);
		}
		return ybsGununOzetiList;
	}
	
	public List<VezneTahsilat> getPayOfficeCollection(long persid, String startDate, String endDate){
		String sql = "Select B.FINCVEZNE_ID,(SELECT TANIM FROM FINCVEZNE WHERE ID = B.FINCVEZNE_ID)"
				+ " VezneAdi,SUM(B.TUTAR) TUTAR, COUNT(DISTINCT A.MPI1PAYDAS_ID) KisiSayisi "
				+ "FROM NIN2TAHSILATGENEL A,LIN2TAHSILAT B WHERE A.ID = B.NIN2TAHSILATGENEL_ID AND "
				+ "A.TIP = 'K'   AND A.ISLEMTARIHI >= to_Date('"+startDate + "'"
				+ ",'dd-MM-yyyy')   AND  A.ISLEMTARIHI <= to_Date('"+ endDate + "'"
				+ ",'dd-MM-yyyy')   AND NOT EXISTS (SELECT 1 	"
				+ "FROM ASM1PAYDASYETKI AA, ASM1PAYDASYETKILINE BB 	WHERE     AA.ID = "
				+ "BB.ASM1PAYDASYETKI_ID 	AND AA.MPI1PAYDAS_ID = A.MPI1PAYDAS_ID 	AND "
				+ "BB.FSM1USERS_ID = " + persid
				+ ") AND A.TURU != 'M'  GROUP BY B.FINCVEZNE_ID";
		
		List<Object> list = new ArrayList<Object>();
		List<VezneTahsilat> vezneTahsilatList = new ArrayList<VezneTahsilat>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			VezneTahsilat vezneTahsilat = new VezneTahsilat();
			
			BigDecimal vezneId = (BigDecimal) map.get("FINCVEZNE_ID");
			String tanim = (String) map.get("VEZNEADI");
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(vezneId != null)
				vezneTahsilat.setId(vezneId.longValue());
			if(tanim != null)
				vezneTahsilat.setTanim(tanim);
			if(kisiSayisi != null)
				vezneTahsilat.setKisiSayisi(kisiSayisi.longValue());
			if(tutar != null)
				vezneTahsilat.setTutar(tutar.doubleValue());
			
			vezneTahsilatList.add(vezneTahsilat);
		}
		return vezneTahsilatList;
	}

	public List<VezneTahsilatDetay> getPayOfficeCollectionDetail(long id, String startDate, String endDate){
		String sql = "select a.MPI1PAYDAS_ID, B.ADI, B.SOYADI, a.EIN1GELIRGRUBU_ID, a.GIN1GELIRTURU_ID, a.YILI, a.DONEMI, a.TUTAR,A.ISLEMTARIHI,C.TANIM" 
				    +" FROM LIN2TAHSILAT A, MPI1PAYDAS B, GIN1GELIRTURU C" 
				    +" where A.MPI1PAYDAS_ID = B.ID" 
				    +" And a.GIN1GELIRTURU_ID = C.ID"
				    +" AND A.FINCVEZNE_ID = " + id
				    +" AND  A.ISLEMTARIHI >= to_Date('" + startDate + "'"
				    + ",'dd-MM-yyyy')   AND  A.ISLEMTARIHI <= to_Date('"+ endDate + "'"
				    + ",'dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<VezneTahsilatDetay> vezneTahsilatDetayList = new ArrayList<VezneTahsilatDetay>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			VezneTahsilatDetay vezneTahsilatDetay = new VezneTahsilatDetay();
			
			BigDecimal mpi1PaydasId = (BigDecimal)map.get("MPI1PAYDAS_ID");
			String ad = (String)map.get("ADI");
			String soyad = (String)map.get("SOYADI");
			BigDecimal ein1GelirGrubuId = (BigDecimal) map.get("EIN1GELIRGRUBU_ID");
			BigDecimal gin1GelirTuruId = (BigDecimal) map.get("GIN1GELIRTURU_ID");
			BigDecimal year = (BigDecimal)map.get("YILI");
			BigDecimal donemi = (BigDecimal)map.get("DONEMI");
			BigDecimal tutar = (BigDecimal)map.get("TUTAR");
			Date islemTarihi = (Date)map.get("ISLEMTARIHI");
			String tanim = (String)map.get("TANIM");

			if(mpi1PaydasId != null)
				vezneTahsilatDetay.setMp1PaydasId(mpi1PaydasId.longValue());
			if(ad != null && soyad != null)
				vezneTahsilatDetay.setAdSoyad(ad + " " +soyad);
			if(ein1GelirGrubuId != null)
				vezneTahsilatDetay.setEin1GelirGrubuId(ein1GelirGrubuId.longValue());
			if(gin1GelirTuruId != null)
				vezneTahsilatDetay.setGin1GelirTuruId(gin1GelirTuruId.longValue());
			if(year != null)
				vezneTahsilatDetay.setYear(year.longValue());
			if(donemi != null)
				vezneTahsilatDetay.setDonemi(donemi.longValue());
			if(tutar != null)
				vezneTahsilatDetay.setTutar(tutar.doubleValue());
			if(islemTarihi != null)
				vezneTahsilatDetay.setIslemTarihi(dateFormat.format(islemTarihi));
			if(tanim != null)
				vezneTahsilatDetay.setGelirAdi(tanim);
			
			vezneTahsilatDetayList.add(vezneTahsilatDetay);
		}
		return vezneTahsilatDetayList;
	}
	
	public List<VezneTahsilat> getLocationCollection(long persid,String startDate,String endDate){
		String sql = "Select C.EINCODEMEYERI_ID, (SELECT TANIM FROM EINCODEMEYERI WHERE ID = "
				+ "C.EINCODEMEYERI_ID ) OdemeYeri,SUM(B.TUTAR) TUTAR,COUNT(DISTINCT A.MPI1PAYDAS_ID) "
				+ "KisiSayisi FROM NIN2TAHSILATGENEL A,LIN2TAHSILAT B,FINCVEZNE C WHERE A.ID = "
				+ "B.NIN2TAHSILATGENEL_ID AND C.ID = A.FINCVEZNE_ID AND A.TIP = 'K' AND "
				+ "A.ISLEMTARIHI >= to_Date('" + startDate + "'"
				+ ",'dd-MM-yyyy')   AND  A.ISLEMTARIHI <= to_Date('"+ endDate + "'"
				+ ",'dd-MM-yyyy')   AND NOT EXISTS (SELECT 1 	FROM ASM1PAYDASYETKI AA,"
				+ " ASM1PAYDASYETKILINE BB 	WHERE     AA.ID = BB.ASM1PAYDASYETKI_ID 	"
				+ "AND AA.MPI1PAYDAS_ID = A.MPI1PAYDAS_ID 	AND BB.FSM1USERS_ID = " + persid
				+ ") AND A.TURU != 'M'  GROUP BY C.EINCODEMEYERI_ID  ";
		
		List<Object> list = new ArrayList<Object>();
		List<VezneTahsilat> vezneTahsilatList = new ArrayList<VezneTahsilat>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			VezneTahsilat vezneTahsilat = new VezneTahsilat();
			
			BigDecimal vezneId = (BigDecimal) map.get("EINCODEMEYERI_ID");
			String tanim = (String) map.get("ODEMEYERI");
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(vezneId != null)
				vezneTahsilat.setId(vezneId.longValue());
			if(tanim != null)
				vezneTahsilat.setTanim(tanim);
			if(kisiSayisi != null)
				vezneTahsilat.setKisiSayisi(kisiSayisi.longValue());
			if(tutar != null)
				vezneTahsilat.setTutar(tutar.doubleValue());
			
			vezneTahsilatList.add(vezneTahsilat);
		}
		return vezneTahsilatList;
	}

	public List<GununOzeti> getDailySummaryIncomeManagementTahakkuk(String requestDate){
		String textValue = "Tahakkuk"; 
		String sql = "SELECT Count(Distinct(MPI1PAYDAS_ID))KISISAYISI,Nvl(SUM(TAHAKKUKTUTARI),0) TUTAR"
                   +" FROM JIN2TAHAKKUK"
                   +" WHERE TAHAKKUKTARIHI = to_Date( '" + requestDate + "','dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");


			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			if(tutar != null)
				gununOzeti.setValue(tutar.doubleValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}
	
	public List<GununOzeti> getDailySummaryIncomeManagementTahsilat(String requestDate){
		String textValue = "Tahsilat"; 
		
		String sql = "SELECT Count(Distinct(A.MPI1PAYDAS_ID))KISISAYISI,NVL(SUM(B.TUTAR),0) TUTAR"
                   +" FROM NIN2TAHSILATGENEL A,LIN2TAHSILAT B"
                   +" WHERE A.ID          = B.NIN2TAHSILATGENEL_ID"
                   +" AND A.ISLEMTARIHI =to_Date( '" + requestDate + "','dd-MM-yyyy')"
                   +" AND A.TURU IN ('N','P','B')";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");


			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			if(tutar != null)
				gununOzeti.setValue(tutar.doubleValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}
	
	public List<GununOzeti> getDailySummaryIncomeManagementEmlakBeyani(String requestDate){
		String textValue = "Emlak Beyani"; 			
		String sql = "SELECT Count(Distinct(MPI1PAYDAS_ID)) KISISAYISI,COUNT(*) TUTAR" 
                   +" FROM EIN5BILDIRIM"
                   +" WHERE BILDIRIMTARIHI = to_Date( '" + requestDate + "','dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");


			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			if(tutar != null)
				gununOzeti.setValue(tutar.doubleValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}
	
	public List<GununOzeti> getDailySummaryIncomeManagementCevreBeyani(String requestDate){
		String textValue = "Cevre Beyani";
		
		String sql = "SELECT Count(Distinct(MPI1PAYDAS_ID)) KISISAYISI,COUNT(*) TUTAR"
                   	+" FROM AIN3BILDIRIM"
                    +" WHERE BILDIRIMTARIHI = to_Date( '" + requestDate + "','dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");


			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			if(tutar != null)
				gununOzeti.setValue(tutar.doubleValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}
	
	public List<GununOzeti> getDailySummaryIncomeManagementReklamBeyani(String requestDate){
		String textValue = "Reklam Beyani";
		
		String sql = "SELECT Count(Distinct(MPI1PAYDAS_ID))KISISAYISI,COUNT(*) TUTAR"
                    +" FROM CIN7BILDIRIM"
                    +" WHERE BILDIRIMTARIHI = to_Date( '" + requestDate + "','dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");


			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			if(tutar != null)
				gununOzeti.setValue(tutar.doubleValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryFinancialManagementBanka(String requestDate) {
		String textValue = "Bankadaki Para";
		
		String sql = " SELECT SUM(TOPLAMBORC-TOPLAMALACAK) TUTAR"
                    +" FROM LFI2HESAPPLANI"
                    +" WHERE KODU = '102' "
                    +" AND BFI1BUTCEDONEMI_ID = " + requestDate;
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(tutar != null)
				gununOzeti.setValue(tutar.doubleValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryFinancialManagementFirmaBorcu(String requestDate) {
		String textValue = "Firmalarin Borcu";
		
		String sql = "SELECT SUM(TOPLAMALACAK-TOPLAMBORC) TUTAR"
				    +" FROM LFI2HESAPPLANI"
				    +" WHERE KODU = '320' "
                    +" AND BFI1BUTCEDONEMI_ID = " +  requestDate;
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(tutar != null)
				gununOzeti.setValue(tutar.doubleValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryFinancialManagementVergiBorcu(String requestDate) {
		String textValue = "Vergi Borcu";
		
		String sql = "SELECT SUM(TOPLAMALACAK-TOPLAMBORC) TUTAR " 
                    +" FROM LFI2HESAPPLANI"
                    +" WHERE KODU = '360' "
                    +" AND BFI1BUTCEDONEMI_ID =" + requestDate;
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(tutar != null)
				gununOzeti.setValue(tutar.doubleValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryFinancialManagementSosyalGuvenlikBorcu(String requestDate) {
		String textValue = "Sosyal Guvenlik Borcu";
		
		
		String sql = "SELECT SUM(TOPLAMALACAK-TOPLAMBORC) TUTAR"
                    +" FROM LFI2HESAPPLANI"
                    +" WHERE KODU In ('361','36201')" 
                    +" AND BFI1BUTCEDONEMI_ID = " + requestDate;
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(tutar != null)
				gununOzeti.setValue(tutar.doubleValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryFinancialManagementToplamBorc(String requestDate) {
		String textValue = "Toplam Borc";
		
		String sql = "SELECT SUM(TOPLAMALACAK-TOPLAMBORC) TUTAR"
                    +" FROM LFI2HESAPPLANI"
                    +" WHERE KODU IN ('320','360','361','362') "
                    +" AND BFI1BUTCEDONEMI_ID =" + requestDate;
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal tutar = (BigDecimal) map.get("TUTAR");

			if(tutar != null)
				gununOzeti.setValue(tutar.doubleValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryDocumentManagementEvrak(String requestDate) {
		String textValue = "Evrak";		
		
		String sql = "SELECT COUNT(*) KISISAYISI  FROM DDM1ISAKISI"
                  	+" WHERE TARIH = to_Date( '" + requestDate + "','dd-MM-yyyy')"
                    +" AND TURU = 'E'";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");

			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryDocumentManagementDilekce(String requestDate) {
		String textValue = "Dilekce";		
		
		
		String sql = "SELECT COUNT(*) KISISAYISI FROM DDM1ISAKISI"
                    +" WHERE TARIH = to_Date( '" + requestDate + "','dd-MM-yyyy')"
                    +" AND TURU = 'D'";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");

			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryDocumentManagementSikayet(String requestDate) {
		String textValue = "Sikayet";		
		
		String sql = "SELECT COUNT(*) KISISAYISI FROM DDM1ISAKISI"
                  	+" WHERE TARIH = to_Date( '" + requestDate + "','dd-MM-yyyy')"
                    +" AND TURU = 'S'";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");

			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<PersonelBilgileri> getDailySummaryHumanResourcesCounts() {
		
		String sql = "select "
                   +" Sum( Case a.TURU When 'M' Then 1 Else 0 End ) MemurSayisi,"
                   +" Sum( Case a.TURU When 'I' Then 1 Else 0 End ) IsciSayisi, "           
                   +" Sum( Case a.TURU When 'O' Then 1 Else 0 End ) StajyerSayisi, "               
                   +" Sum( Case a.TURU When 'F' Then 1 Else 0 End ) TaseronSayisi,"
                   +" Sum( Case a.TURU When 'S' Then 1 Else 0 End ) SozlesmeliSayisi,"
                   +" Sum( Case a.TURU When 'G' Then 1 Else 0 End ) GeciciIsciSayisi,"
                   +" Sum( Case a.TURU When 'L' Then 1 Else 0 End ) MeclisUyesiSayisi from IHR1PERSONEL a,BSM2SERVIS b Where a.BSM2SERVIS_GOREV = b.ID" 
                   +" And a.CIKISTARIHI IS NULL";
		
		List<Object> list = new ArrayList<Object>();
		List<PersonelBilgileri> personelBilgileriList = new ArrayList<PersonelBilgileri>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			PersonelBilgileri personelBilgileri = new PersonelBilgileri();
			
			BigDecimal memurSayisi = (BigDecimal)map.get("MEMURSAYISI");
			BigDecimal isciSayisi = (BigDecimal)map.get("ISCISAYISI");
			BigDecimal stajyerSayisi = (BigDecimal)map.get("STAJYERSAYISI");
			BigDecimal taseronSayisi = (BigDecimal)map.get("TASERONSAYISI");
			BigDecimal sozlesmeliSayisi = (BigDecimal)map.get("SOZLEMELISAYISI");
			BigDecimal gecisiIsciSayisi = (BigDecimal)map.get("GECICIISCISAYISI");
			BigDecimal meclisUyesiSayisi = (BigDecimal)map.get("MECLISUYESISAYISI");
			
			if(memurSayisi != null)
				personelBilgileri.setMemurSayisi(memurSayisi.intValue());
			if(isciSayisi != null)
				personelBilgileri.setIsciSayisi(isciSayisi.intValue());
			if(stajyerSayisi != null)
				personelBilgileri.setStajyerSayisi(stajyerSayisi.intValue());
			if(taseronSayisi != null)
				personelBilgileri.setTaseronSayisi(taseronSayisi.intValue());
			if(sozlesmeliSayisi != null)
				personelBilgileri.setSozlemeliSayisi(sozlesmeliSayisi.intValue());
			if(gecisiIsciSayisi != null)
				personelBilgileri.setGeciciIsciSayisi(gecisiIsciSayisi.intValue());
			if(meclisUyesiSayisi != null)
				personelBilgileri.setMeclisUyesiSayisi(meclisUyesiSayisi.intValue());
			
			personelBilgileriList.add(personelBilgileri);
		}
		
		return personelBilgileriList;
	}

	@Override
	public List<GununOzeti> getDailySummaryRealEstateManagementBina() {
		String textValue = "Bina";		
		
		String sql = "SELECT Nvl(COUNT(*),0) KISISAYISI  FROM ERE1YAPI";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");

			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryRealEstateManagementNumarataj() {
		String textValue = "Numarataj";		
		
		String sql = "SELECT COUNT(*) KISISAYISI  FROM FRE1KAPITAHSIS";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");

			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryRealEstateManagementBagimsizBolum() {
		String textValue = "Bagimsiz Bolum";		
		
		String sql = "SELECT COUNT(*) KISISAYISI FROM DRE1BAGBOLUM";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");

			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryLicenseManagementRuhsatBasvurusu(String requestDate) {
		String textValue = "Ruhsat Basvurusu";		
		
		String sql = "SELECT COUNT(*) KISISAYISI FROM ELI1RUHSATDOSYA"
                    +" WHERE KAYITTARIHI = to_Date( '" + requestDate + "','dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");

			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryLicenseManagementVerilenRuhsat(String requestDate) {
		String textValue = "Verilen Ruhsat";		
		
		String sql = "SELECT COUNT(*) KISISAYISI FROM NLI1RUHSAT"
				    +" WHERE KAYITTARIHI = to_Date( '" + requestDate + "','dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");

			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}

	@Override
	public List<GununOzeti> getDailySummaryServiceManagementNikah(String requestDate) {
		String textValue = "Nikah";		
		
		String sql = "SELECT Nvl(COUNT(*),0) KISISAYISI FROM BSR7NIKAH"
		            +" WHERE EVLILIKTARIHI = to_Date( '" + requestDate + "','dd-MM-yyyy')"; 
		
		List<Object> list = new ArrayList<Object>();
		List<GununOzeti> gununOzetiList = new ArrayList<GununOzeti>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GununOzeti gununOzeti = new GununOzeti();
			
			BigDecimal kisiSayisi = (BigDecimal) map.get("KISISAYISI");

			if(kisiSayisi != null)
				gununOzeti.setPersonCount(kisiSayisi.longValue());
			gununOzeti.setTextValue(textValue);
			gununOzetiList.add(gununOzeti);
		}
		return gununOzetiList;
	}
	
	public List<GelirlerYonetimiTahakkuk> getIncomeManagementTahakkukDetail(String startDate, String endDate){
		String sql = "SELECT b.ADI,B.SOYADI,a.TAHAKKUKTUTARI,A.YILI,A.DONEMI,A.IZAHAT"
                   	+" FROM JIN2TAHAKKUK  A, MPI1PAYDAS B"
                   	+" WHERE   A.MPI1PAYDAS_ID = B.ID"
                   	+" and TAHAKKUKTARIHI  BETWEEN TO_DATE('"+ startDate +"', 'dd-MM-yyyy') and"  
                    +" TO_DATE ('"+ endDate +"', 'dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<GelirlerYonetimiTahakkuk> gelirlerYonetimiTahakkukList = new ArrayList<GelirlerYonetimiTahakkuk>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			GelirlerYonetimiTahakkuk gelirlerYonetimiTahakkuk = new GelirlerYonetimiTahakkuk();
			
			String ad = (String)map.get("ADI");
			String soyad = (String)map.get("SOYADI");
			BigDecimal tahakkukTutari = (BigDecimal) map.get("TAHAKKUKTUTARI");
			BigDecimal year = (BigDecimal)map.get("YILI");
			BigDecimal donemi = (BigDecimal)map.get("DONEMI");
			String izahat = (String)map.get("IZAHAT");
			

			if(ad != null && soyad != null)
				gelirlerYonetimiTahakkuk.setAdSoyad(ad + " " + soyad);

			if(year != null)
				gelirlerYonetimiTahakkuk.setYil(year.longValue());;
			if(donemi != null)
				gelirlerYonetimiTahakkuk.setDonem(donemi.longValue());
			if(tahakkukTutari != null)
				gelirlerYonetimiTahakkuk.setTutar(tahakkukTutari.doubleValue());
			if(izahat != null)
				gelirlerYonetimiTahakkuk.setIzahat(izahat);
			
			gelirlerYonetimiTahakkukList.add(gelirlerYonetimiTahakkuk);
		}
		return gelirlerYonetimiTahakkukList;
	}
	
	public List<GelirlerYonetimiEmlakBeyani> getIncomeManagementEmlakBeyani(String startDate, String endDate){
		String sql = "select A.MPI1PAYDAS_ID ,c.ADI,c.SOYADI,A.BILDIRIMTARIHI"
					+" from AIN2BILDIRIM a, EIN1GELIRGRUBU b,MPI1PAYDAS c"
					+" Where EIN1GELIRGRUBU_ID = b.ID"
					+" And b.TURU = 'EMLAK'"
					+" and A.MPI1PAYDAS_ID = c.id "
					+" and A.BILDIRIMTARIHI between to_date('" + startDate +"','dd-MM-yyyy')"
					+" and to_date('" + endDate +"','dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<GelirlerYonetimiEmlakBeyani> gelirlerYonetimiEmlakBeyaniList = new ArrayList<GelirlerYonetimiEmlakBeyani>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			GelirlerYonetimiEmlakBeyani gelirlerYonetimiEmlakBeyani = new GelirlerYonetimiEmlakBeyani();
			
			BigDecimal mpi1PaydasId = (BigDecimal)map.get("MPI1PAYDAS_ID");
			String ad = (String)map.get("ADI");
			String soyad = (String)map.get("SOYADI");
			Date bildirimTarihi = (Date)map.get("BILDIRIMTARIHI");
			
			if(mpi1PaydasId != null)
				gelirlerYonetimiEmlakBeyani.setMpi1PaydasId(mpi1PaydasId.longValue());
			if(ad != null && soyad != null)
				gelirlerYonetimiEmlakBeyani.setAdSoyad(ad + " " + soyad);
			if(bildirimTarihi != null)
				gelirlerYonetimiEmlakBeyani.setBildirimTarihi(dateFormat.format(bildirimTarihi));
			
			gelirlerYonetimiEmlakBeyaniList.add(gelirlerYonetimiEmlakBeyani);
		}
		return gelirlerYonetimiEmlakBeyaniList;
	}
	
	public List<GelirlerYonetimiCevreBeyani> getIncomeManagementCevreBeyani(String startDate, String endDate){
		String sql = "select A.MPI1PAYDAS_ID ,c.ADI,c.SOYADI,A.BILDIRIMTARIHI"
					+" from AIN2BILDIRIM a, EIN1GELIRGRUBU b,MPI1PAYDAS c"
					+" Where EIN1GELIRGRUBU_ID = b.ID"
					+" And b.TURU = 'CEVRE'"
					+" and A.MPI1PAYDAS_ID = c.id "
					+" and A.BILDIRIMTARIHI between to_date('" + startDate +"','dd-MM-yyyy')"
					+" and to_date('" + endDate +"','dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<GelirlerYonetimiCevreBeyani> gelirlerYonetimiCevreBeyaniList = new ArrayList<GelirlerYonetimiCevreBeyani>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			GelirlerYonetimiCevreBeyani gelirlerYonetimiCevreBeyani = new GelirlerYonetimiCevreBeyani();
			
			BigDecimal mpi1PaydasId = (BigDecimal)map.get("MPI1PAYDAS_ID");
			String ad = (String)map.get("ADI");
			String soyad = (String)map.get("SOYADI");
			Date bildirimTarihi = (Date)map.get("BILDIRIMTARIHI");
			
			if(mpi1PaydasId != null)
				gelirlerYonetimiCevreBeyani.setMpi1PaydasId(mpi1PaydasId.longValue());
			if(ad != null && soyad != null)
				gelirlerYonetimiCevreBeyani.setAdSoyad(ad + " " + soyad);
			if(bildirimTarihi != null)
				gelirlerYonetimiCevreBeyani.setBildirimTarihi(dateFormat.format(bildirimTarihi));
			
			gelirlerYonetimiCevreBeyaniList.add(gelirlerYonetimiCevreBeyani);
		}
		return gelirlerYonetimiCevreBeyaniList;
	}
	
	public List<GelirlerYonetimiReklamBeyani> getIncomeManagementReklamBeyani(String startDate, String endDate){
		String sql = "select A.MPI1PAYDAS_ID ,c.ADI,c.SOYADI,A.BILDIRIMTARIHI"
					+" from AIN2BILDIRIM a, EIN1GELIRGRUBU b,MPI1PAYDAS c"
					+" Where EIN1GELIRGRUBU_ID = b.ID"
					+" And b.TURU = 'REKLAM'"
					+" and A.MPI1PAYDAS_ID = c.id "
					+" and A.BILDIRIMTARIHI between to_date('" + startDate +"','dd-MM-yyyy')"
					+" and to_date('" + endDate +"','dd-MM-yyyy')";
		
		List<Object> list = new ArrayList<Object>();
		List<GelirlerYonetimiReklamBeyani> gelirlerYonetimiReklamBeyaniList = new ArrayList<GelirlerYonetimiReklamBeyani>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			GelirlerYonetimiReklamBeyani gelirlerYonetimiReklamBeyani = new GelirlerYonetimiReklamBeyani();
			
			BigDecimal mpi1PaydasId = (BigDecimal)map.get("MPI1PAYDAS_ID");
			String ad = (String)map.get("ADI");
			String soyad = (String)map.get("SOYADI");
			Date bildirimTarihi = (Date)map.get("BILDIRIMTARIHI");
			
			if(mpi1PaydasId != null)
				gelirlerYonetimiReklamBeyani.setMpi1PaydasId(mpi1PaydasId.longValue());
			if(ad != null && soyad != null)
				gelirlerYonetimiReklamBeyani.setAdSoyad(ad + " " + soyad);
			if(bildirimTarihi != null)
				gelirlerYonetimiReklamBeyani.setBildirimTarihi(dateFormat.format(bildirimTarihi));
			
			gelirlerYonetimiReklamBeyaniList.add(gelirlerYonetimiReklamBeyani);
		}
		return gelirlerYonetimiReklamBeyaniList;
	}

	private double sumAllIncome(List<FinansmanYonetimiGelirGider> finansmanYonetimiGelirGiderList) {
		double total = 0;
		for(FinansmanYonetimiGelirGider finansmanYonetimiGelirGider : finansmanYonetimiGelirGiderList){
			total = total + finansmanYonetimiGelirGider.getGelir();
		}
		return total;
	}

	private double sumAllIncomeActualize(List<FinansmanYonetimiGelirGider> finansmanYonetimiGelirGiderList) {
		double total = 0;
		for(FinansmanYonetimiGelirGider finansmanYonetimiGelirGider : finansmanYonetimiGelirGiderList){
			total = total + finansmanYonetimiGelirGider.getGelirGerceklesme();
		}
		return total;
	}
	
}
