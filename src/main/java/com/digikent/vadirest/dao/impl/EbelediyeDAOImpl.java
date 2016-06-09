package com.digikent.vadirest.dao.impl;

import com.digikent.vadirest.dao.EbelediyeDAO;
import com.digikent.vadirest.dto.BelgeSorgula;
import com.digikent.vadirest.dto.BilgiEdinme;
import com.digikent.vadirest.dto.BinaAsinmaOranlari;
import com.digikent.vadirest.dto.CTVYillar;
import com.digikent.vadirest.dto.CagriMerkezi;
import com.digikent.vadirest.dto.CevreTemizlikTarife;
import com.digikent.vadirest.dto.EmlakBeyan;
import com.digikent.vadirest.dto.IsyeriRuhsat;
import com.digikent.vadirest.dto.NikahRezervasyon;
import com.digikent.vadirest.dto.PaydasSicil;
import com.digikent.vadirest.dto.SokakRayic;
import com.digikent.vadirest.dto.SokakRayicYillar;
import com.digikent.vadirest.dto.SosyalYardim;
import com.digikent.vadirest.dto.SurecBasvuru;
import com.digikent.vadirest.dto.ZabitaDenetim;
import com.vadi.digikent.abs.gmk.model.RE1Mahalle;
import com.vadi.digikent.sosyalhizmetler.nkh.model.SR7NikahSalonu;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository("ebelediyeDao")
@Transactional
public class EbelediyeDAOImpl implements EbelediyeDAO {

	@Autowired
	protected SessionFactory sessionFactory;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public List<RE1Mahalle> findMahalleListByIlceId(long ilceid){
		String sql = "SELECT ID,TANIM FROM DRE1MAHALLE WHERE ISACTIVE='E' AND RRE1ILCE_ID = :ilceid order by tanim";

		List list = new ArrayList<Object>();
		List<RE1Mahalle> mahalleList = new ArrayList<RE1Mahalle>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("ilceid", ilceid);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			RE1Mahalle mahalle = new RE1Mahalle();
			BigDecimal id = (BigDecimal) map.get("ID");
			String tanim = (String) map.get("TANIM");

			if (tanim != null)
				mahalle.setTanim(tanim);

			if (id != null)
				mahalle.setId(id.longValue());
			mahalleList.add(mahalle);
		}
		return mahalleList;
	}

	public List<SokakRayicYillar> getSokakRayicYillarList(){
		String sql = "SELECT DISTINCT(YILI) YILI FROM AIN5RAYICKURALILINE WHERE YILI>0 ORDER BY YILI DESC";

		List list = new ArrayList<Object>();
		List<SokakRayicYillar> yilList = new ArrayList<SokakRayicYillar>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			SokakRayicYillar yil = new SokakRayicYillar();
			BigDecimal  yili = (BigDecimal ) map.get("YILI");

			if (yili != null)
				yil.setYili(yili.longValue());
			yilList.add(yil);
		}
		return yilList;
	}

	public List<CTVYillar> getCTVYillarList(){
		String sql = "SELECT DISTINCT(YILI) YILI FROM HIN3TARIFE WHERE YILI >0 ORDER BY  YILI DESC";

		List list = new ArrayList<Object>();
		List<CTVYillar> yilList = new ArrayList<CTVYillar>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			CTVYillar yil = new CTVYillar();
			BigDecimal  yili = (BigDecimal ) map.get("YILI");

			if (yili != null)
				yil.setYili(yili.longValue());
			yilList.add(yil);
		}
		return yilList;
	}

	public List<SR7NikahSalonu> searchNikahSalon(){
		String sql = "SELECT ID,TANIM FROM GSR7NIKAHSALONU WHERE ISACTIVE='E' AND INTERNETTENGORULEBILIR='E' order by tanim";

		List list = new ArrayList<Object>();
		List<SR7NikahSalonu> salonList = new ArrayList<SR7NikahSalonu>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			SR7NikahSalonu salon = new SR7NikahSalonu();
			BigDecimal id = (BigDecimal) map.get("ID");
			String tanim = (String) map.get("TANIM");

			if (tanim != null)
				salon.setTanim(tanim);

			if (id != null)
				salon.setId(id.longValue());
			salonList.add(salon);
		}
		return salonList;
	}

	public List<BinaAsinmaOranlari> searchBinaAsinmaOrani(){
		String sql = "SELECT A.TANIM, B.ALTSINIR ||' - '|| B.USTSINIR YILARALIK, B.ASINMAORANI FROM GIN5INSAATTURU A, DIN5BINAASINMAORANI B WHERE A.ID = B.IN5BINATUR_ID AND A.ID>0 AND B.ID>0 ORDER BY A.TANIM,B.ALTSINIR ASC ";

		List list = new ArrayList<Object>();
		List<BinaAsinmaOranlari> binaAsinmaList = new ArrayList<BinaAsinmaOranlari>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			BinaAsinmaOranlari binaasinma = new BinaAsinmaOranlari();
			String yilAraligi = (String) map.get("YILARALIK");
			BigDecimal asinmaOrani = (BigDecimal) map.get("ASINMAORANI");
			String binaTuru = (String) map.get("TANIM");

			if (yilAraligi != null)
				binaasinma.setAYilAraligi(yilAraligi);

			if (asinmaOrani != null)
				binaasinma.setAsinmaOrani(asinmaOrani.longValue());

			if (binaTuru != null)
				binaasinma.setBinaTuru(binaTuru);

			binaAsinmaList.add(binaasinma);
		}
		return binaAsinmaList;
	}

	public List<CevreTemizlikTarife> searchCevreTemizlikTarife(long yil){
		String sql = "SELECT YILI,DERECE,GRUP,MATRAH FROM HIN3TARIFE WHERE YILI=:yil ORDER BY GRUP ASC";

		List list = new ArrayList<Object>();
		List<CevreTemizlikTarife> cevreTemizlikList = new ArrayList<CevreTemizlikTarife>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("yil", yil);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			CevreTemizlikTarife cevretemizlik = new CevreTemizlikTarife();
			BigDecimal yili = (BigDecimal) map.get("YILI");
			BigDecimal grup = (BigDecimal) map.get("DERECE");
			BigDecimal derece = (BigDecimal) map.get("GRUP");
			BigDecimal yillikTutar = (BigDecimal) map.get("MATRAH");

			if (yili != null)
				cevretemizlik.setYil(yili.longValue());

			if (grup != null)
				cevretemizlik.setGrup(grup.longValue());

			if (derece != null)
				cevretemizlik.setDerece(derece.longValue());

			if (yillikTutar != null)
				cevretemizlik.setYillikTutar(yillikTutar.doubleValue());

			cevreTemizlikList.add(cevretemizlik);
		}
		return cevreTemizlikList;
	}

	public List<SokakRayic> searchSokakRayic(long mahalleid, long yil){
		String sql = "SELECT (SELECT TANIM FROM SRE1SOKAK WHERE ID=AIN5RAYICKURALI.SRE1SOKAK_ID) SOKAKADI ,RAYICDEGERI FROM AIN5RAYICKURALI,AIN5RAYICKURALILINE WHERE"+
					" AIN5RAYICKURALILINE.YILI=:yil AND DRE1MAHALLE_ID = :mahalleid AND  AIN5RAYICKURALI.ID = AIN5RAYICKURALILINE.AIN5RAYICKURALI_ID ORDER BY SOKAKADI ASC";

		List list = new ArrayList<Object>();
		List<SokakRayic> sokakRayicList = new ArrayList<SokakRayic>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("yil", yil);
		query.setParameter("mahalleid", mahalleid);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			SokakRayic sokakrayic = new SokakRayic();
			BigDecimal rayicTutar = (BigDecimal) map.get("RAYICDEGERI");
			String sokakAdi = (String) map.get("SOKAKADI");


			if (rayicTutar != null)
				sokakrayic.setRayicTutar(rayicTutar.doubleValue());

			if (sokakAdi != null)
				sokakrayic.setSokakAdi(sokakAdi);

			sokakRayicList.add(sokakrayic);
		}
		return sokakRayicList;
	}

	public List<PaydasSicil> searchPaydas(String adi, String soyadi, String babaadi, long tckimlik){
		String sql = "SELECT ID, ADI, SOYADI, BABAADI, TO_CHAR(DOGUMTARIHI,'YYYY') DOGUMTARIHI  FROM MPI1PAYDAS WHERE"+
					" NLS_UPPER(F_TurkceToEng(ADI),'NLS_SORT = XTURKISH')=NLS_UPPER(F_TurkceToEng(:adi),'NLS_SORT = XTURKISH') AND NLS_UPPER(F_TurkceToEng(SOYADI),'NLS_SORT = XTURKISH') = NLS_UPPER(F_TurkceToEng(:soyadi),'NLS_SORT = XTURKISH') "+
					" AND  NLS_UPPER(F_TurkceToEng(BABAADI),'NLS_SORT = XTURKISH') = NLS_UPPER(F_TurkceToEng(:babaadi),'NLS_SORT = XTURKISH') AND TCKIMLIKNO =:tckimlik ORDER BY ID ASC";

		List list = new ArrayList<Object>();
		List<PaydasSicil> paydasSicilList = new ArrayList<PaydasSicil>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("adi", adi);
		query.setParameter("soyadi", soyadi);
		query.setParameter("babaadi", babaadi);
		query.setParameter("tckimlik", tckimlik);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			PaydasSicil paydassicil = new PaydasSicil();
			BigDecimal paydasno = (BigDecimal) map.get("ID");
			String paydasadi = (String) map.get("ADI");
			String paydassoyadi = (String) map.get("SOYADI");
			String paydasbabaadi = (String) map.get("BABAADI");
			String paydasdogumyili = (String) map.get("DOGUMTARIHI");

			if (paydasno != null)
				paydassicil.setPaydasno(paydasno.longValue());

			if (paydasadi != null)
				paydassicil.setAdi(paydasadi);

			if (paydassoyadi != null)
				paydassicil.setSoyadi(paydassoyadi);

			if (paydasbabaadi != null)
				paydassicil.setBabaadi(paydasbabaadi);

			if (paydasdogumyili != null)
				paydassicil.setDogumyili(paydasdogumyili);

			paydasSicilList.add(paydassicil);
		}
		return paydasSicilList;
	}

	public List<NikahRezervasyon> searchNikahRezervasyon(String sorgunikahtarihi, long salonid){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = new GregorianCalendar(Integer.parseInt(sorgunikahtarihi.substring(6)),(Integer.parseInt(sorgunikahtarihi.substring(3, 5))-1),Integer.parseInt(sorgunikahtarihi.substring(0, 2)));
		String nikahtarihi = formatter.format(calendar.getTime());

		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		if(weekday==1){
			weekday=7;
		}else {
			weekday--;
		}

		String sql = "SELECT TO_CHAR(FSR7REZERVASYONPRM.BASLANGICSAATI,'HH24:MI') BASLANGICSAATI, TO_CHAR(FSR7REZERVASYONPRM.BITISSAATI,'HH24:MI') BITISSAATI, FSR7REZERVASYONPRM.SURESI, "+
					 "(CASE WHEN FSR7REZERVASYONPRM.HAFTANINGUNU=1 THEN 'Pazartesi' WHEN FSR7REZERVASYONPRM.HAFTANINGUNU=2 THEN 'Sal�' "+
                     "WHEN FSR7REZERVASYONPRM.HAFTANINGUNU=3 THEN '�ar�amba' WHEN FSR7REZERVASYONPRM.HAFTANINGUNU=4 THEN 'Per�embe' "+
                     "WHEN FSR7REZERVASYONPRM.HAFTANINGUNU=5 THEN 'Cuma' WHEN FSR7REZERVASYONPRM.HAFTANINGUNU=6 THEN 'Cumartesi' "+
                     "WHEN FSR7REZERVASYONPRM.HAFTANINGUNU=7 THEN 'Pazar'  ELSE '' END) HAFTANINGUNU, "+
					 "GSR7NIKAHSALONU.TANIM, DSR7REZERVASYON.BSR7NIKAH_ID FROM FSR7REZERVASYONPRM LEFT OUTER JOIN DSR7REZERVASYON "+
					 "ON TO_CHAR (FSR7REZERVASYONPRM.BASLANGICSAATI, 'HH24:MI') = TO_CHAR (DSR7REZERVASYON.NIKAHTARIHSAAT, 'HH24:MI') "+
					 "AND TO_DATE(DSR7REZERVASYON.NIKAHTARIHSAAT,'DD/MM/RRRR') = TO_DATE(:nikahtarihi,'DD/MM/RRRR') AND "+
					 "DSR7REZERVASYON.GSR7NIKAHSALONU_ID = FSR7REZERVASYONPRM.GSR7NIKAHSALONU_ID,GSR7NIKAHSALONU "+
					 "WHERE FSR7REZERVASYONPRM.GSR7NIKAHSALONU_ID = GSR7NIKAHSALONU.ID AND GSR7NIKAHSALONU.BELEDIYEICIMI = 'E' "+
					 "AND FSR7REZERVASYONPRM.HAFTANINGUNU = :haftaningunu "+
					 "AND FSR7REZERVASYONPRM.DSR7REZERVASYONDONEM_ID IN (SELECT ID FROM ESR7REZERVASYONDONEM WHERE "+
					 "TO_DATE (:nikahtarihi,'DD/MM/RRRR') BETWEEN TO_DATE(ESR7REZERVASYONDONEM.BASLAMATARIHI,'DD/MM/RRRR') "+
					 "AND TO_DATE(ESR7REZERVASYONDONEM.BITISTARIHI,'DD/MM/RRRR') ) "+
					 "AND GSR7NIKAHSALONU.ID = :salonid ORDER BY FSR7REZERVASYONPRM.GSR7NIKAHSALONU_ID, FSR7REZERVASYONPRM.BASLANGICSAATI";
		List list = new ArrayList<Object>();
		List<NikahRezervasyon> nikahRezervasyonList = new ArrayList<NikahRezervasyon>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("nikahtarihi", nikahtarihi);
		query.setParameter("salonid", salonid);
		query.setParameter("haftaningunu", weekday);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			NikahRezervasyon nikahrezervasyon = new NikahRezervasyon();
			String baslangicsaati = (String) map.get("BASLANGICSAATI");
			String bitissaati = (String) map.get("BITISSAATI");
			BigDecimal sure = (BigDecimal) map.get("SURESI");
			String haftaningunu = (String) map.get("HAFTANINGUNU");
			String salon = (String) map.get("TANIM");
			BigDecimal nikah_id = (BigDecimal) map.get("BSR7NIKAH_ID");

			if (baslangicsaati != null)
				nikahrezervasyon.setBaslangicsaati(baslangicsaati);

			if (bitissaati != null)
				nikahrezervasyon.setBitissaati(bitissaati);

			if (sure != null)
				nikahrezervasyon.setSure(sure.longValue());

			if (haftaningunu != null)
				nikahrezervasyon.setHaftaningunu(haftaningunu);

			if (salon != null)
				nikahrezervasyon.setSalon(salon);

			if (nikah_id != null)
				nikahrezervasyon.setNikah_id(nikah_id.longValue());

			nikahRezervasyonList.add(nikahrezervasyon);
		}
		return nikahRezervasyonList;
	}

	public List<BilgiEdinme> searchBasvuru(long basvuruno, String eposta){
		String sql = "SELECT ID,TO_CHAR(BASVURUTARIHI,'DD/MM/RRRR HH24:MI') BASVURUTARIHI,ADI || ' ' || SOYADI ADISOYADI, TCKIMLIKNO,  KONUOZETI, "
				+ "(SELECT TANIM FROM ABYBBASVURUDURUMU WHERE ID = ABYBBASVURUDURUMU_ID ) BASVURUDURUMU ,IZAHAT "
				+ "FROM ABYBBILGIEDINME WHERE ID=:basvuruno AND ELEKTRONIKPOSTA =:eposta";

		List list = new ArrayList<Object>();
		List<BilgiEdinme> bilgiEdinmeList = new ArrayList<BilgiEdinme>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("basvuruno", basvuruno);
		query.setParameter("eposta", eposta);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			BilgiEdinme bilgiedinme = new BilgiEdinme();
			BigDecimal sorgubasvuruno = (BigDecimal) map.get("ID");
			String tarihsaat = (String) map.get("BASVURUTARIHI");
			String adisoyadi = (String) map.get("ADISOYADI");
			BigDecimal tckimlikno = (BigDecimal) map.get("TCKIMLIKNO");
			String konuozeti = (String) map.get("KONUOZETI");
			String basvurudurumu = (String) map.get("BASVURUDURUMU");
			String basvurudetayi = (String) map.get("IZAHAT");


			if (sorgubasvuruno != null)
				bilgiedinme.setBasvuruno(sorgubasvuruno.longValue());

			if (tarihsaat != null)
				bilgiedinme.setTarihsaat(tarihsaat);

			if (adisoyadi != null)
				bilgiedinme.setAdisoyadi(adisoyadi);

			if (tckimlikno != null)
				bilgiedinme.setBasvuruno(tckimlikno.longValue());

			if (konuozeti != null)
				bilgiedinme.setKonuozeti(konuozeti);

			if (basvurudurumu != null)
				bilgiedinme.setBasvurudurumu(basvurudurumu);

			if (basvurudetayi != null)
				bilgiedinme.setBasvurudetayi(basvurudetayi);

			bilgiEdinmeList.add(bilgiedinme);
		}
		return bilgiEdinmeList;
	}

	public List<BelgeSorgula> searchBelge(long referansno, String parola){
		String sql = "SELECT C.ID as REFERANSNO, BPM.F_KIMDEN(D.ID) AS KIMDEN,C.KONUOZETI as KONUOZETI ,TO_CHAR(D.CREATIONDATE,'DD/MM/RRRR') as CREATIONDATE, D.EBYSBELGEDURUMU as EBYSBELGEDURUMU FROM "+
                 "EBYSBELGE C,ABPMWORKITEM D WHERE D.EBYSBELGE_ID = C.ID AND D.ISEBYSWORKITEM='E' "+
                 "and c.id=:referansno and c.parola=:parola order by d.creationDateTime desc ,d.id desc";

		List list = new ArrayList<Object>();
		List<BelgeSorgula> belgeList = new ArrayList<BelgeSorgula>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("referansno", referansno);
		query.setParameter("parola", parola);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			BelgeSorgula belge = new BelgeSorgula();
			BigDecimal sorgureferansno = (BigDecimal) map.get("REFERANSNO");
			String kimde = (String) map.get("KIMDEN");
			String konuozeti = (String) map.get("KONUOZETI");
			String tarih = (String) map.get("CREATIONDATE");
			String belgedurumu = (String) map.get("EBYSBELGEDURUMU");

			if (sorgureferansno != null)
				belge.setReferansno(sorgureferansno.longValue());

			if (kimde != null)
				belge.setKimde(kimde);

			if (konuozeti != null)
				belge.setKonuozeti(konuozeti);

			if (tarih != null)
				belge.setTarih(tarih);

			if (belgedurumu != null)
				belge.setBelgedurumu(belgedurumu);

			belgeList.add(belge);
		}
		return belgeList;
	}

	public List<SurecBasvuru> searchSurecBasvuru(long basvuruno, long paydasno){
		String sql = "SELECT TO_CHAR(A.ACTIVITY_NAME) ACTIVITY_NAME, TO_CHAR(B.FULL_NAME) FULL_NAME, TO_CHAR(A.CLOSE_DATETIME,'DD/MM/RRRR') CLOSE_DATETIME FROM BPMPD.LSW_TASK A, BPMPD.LSW_USR_XREF B, BPMPD.LSW_BPD_INSTANCE C "+
					"WHERE A.USER_ID = B.USER_ID AND A.USER_ID != 9 AND A.BPD_INSTANCE_ID = C.BPD_INSTANCE_ID "+
					"AND (C.BPD_INSTANCE_ID = (SELECT vbpmprocessinstance_id FROM vimrbasvuru WHERE ID = :basvuruno AND MPI1PAYDAS_ID=:paydasno) OR C.BPD_INSTANCE_ID = (SELECT vbpmprocessinstance_id FROM vydebasvuru WHERE ID = :basvuruno AND MPI1PAYDAS_ID=:paydasno)) ORDER BY A.TASK_ID ASC";
		List list = new ArrayList<Object>();
		List<SurecBasvuru> surecBasvuruList = new ArrayList<SurecBasvuru>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("basvuruno", basvuruno);
		query.setParameter("paydasno", paydasno);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			SurecBasvuru surecbasvuru = new SurecBasvuru();
			String gorevadi = (String) map.get("ACTIVITY_NAME");
			String sorumlusu = (String) map.get("FULL_NAME");
			String tamamlanmatarihi = (String) map.get("CLOSE_DATETIME");
			if (gorevadi != null)
				surecbasvuru.setGorevadi(gorevadi);

			if (sorumlusu != null)
				surecbasvuru.setSorumlusu(sorumlusu);

			if (tamamlanmatarihi != null)
				surecbasvuru.setTamamlanmatarihi(tamamlanmatarihi);

			surecBasvuruList.add(surecbasvuru);
		}
		return surecBasvuruList;
	}

	//Isyeri Ruhsat
	public List<IsyeriRuhsat> searchIsyeriRuhsat(){
		String sql = "SELECT YILI, ( SELECT TANIM FROM SLI1RUHSATTURU WHERE ID=T.SLI1RUHSATTURU_ID ) RUHSATTURU, "
				+ "(SELECT ENTEGRASYONILISKINO FROM DIGIKENT.SRE1SOKAK WHERE ID = T.DRE1MAHALLE_ID) MAHALLEID, (SELECT ENTEGRASYONILISKINO FROM DIGIKENT.SRE1SOKAK WHERE ID = T.SRE1SOKAK_ID)SOKAKID, DRE1BAGBOLUM_ID FROM TLI3RUHSAT T WHERE YILI = (select extract(year from SYSDATE) from dual) ";

	//	String mahalleparam = Long.toString(mahalleid);

		//if(mahalleparam != null && !mahalleparam.equals("0"))
			//sql += " and DRE1MAHALLE_ID =:mahalleid ";



	//	if (mahalleparam.equals("0"))
		//{
			//System.out.println("elseeee");
			//sql += " and 1=1 ";

	//	}

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

		/*if(mahalleparam != null && !mahalleparam.equals("0"))
		{
			System.out.println("mahalleparam:" + mahalleparam);
			query.setParameter("mahalleid", mahalleid);
		}
		*/

		List list = new ArrayList<Object>();
		List<IsyeriRuhsat> isyeriRuhsatList = new ArrayList<IsyeriRuhsat>();



		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			IsyeriRuhsat isyeriRuhsat = new IsyeriRuhsat();
			BigDecimal yili = (BigDecimal) map.get("YILI");
			String ruhsatturu = (String) map.get("RUHSATTURU");
			BigDecimal bbolum = (BigDecimal)map.get("DRE1BAGBOLUM_ID");
			BigDecimal sokak = (BigDecimal)map.get("SOKAKID");
			BigDecimal mahalle = (BigDecimal)map.get("MAHALLEID");


			if (yili != null)
				isyeriRuhsat.setRuhsat_yili(yili.longValue());

			if (ruhsatturu != null)
				isyeriRuhsat.setRuhsat_turu(ruhsatturu);

			if(bbolum != null)
				isyeriRuhsat.setBagbolum_id(bbolum.longValue());

			if(sokak != null)
				isyeriRuhsat.setSokak_id(sokak.longValue());

			if(mahalle != null)
				isyeriRuhsat.setMahalle_id(mahalle.longValue());

			isyeriRuhsatList.add(isyeriRuhsat);
		}
		return isyeriRuhsatList;
	}

	//Sosyal Yard�m
	public List<SosyalYardim> searchSosyalYardim(){

		String sql = "SELECT TO_CHAR(B.SONUCTAR,'DD/MM/RRRR') SONUCTAR,(SELECT TANIM FROM TSY1MALZEMEGRUBU WHERE ID = B.TSY1MALZEMEGRUBU_ID) MALZEMEGRUBU,"

		+ "(SELECT ENTEGRASYONILISKINO FROM DIGIKENT.DRE1MAHALLE WHERE ID = (SELECT DRE1MAHALLE_ID FROM VSY1DOSYA D WHERE D.ID=A.VSY1DOSYA_ID)) MAHALLE_ID,"

		+ "(SELECT DRE1BAGBOLUM_ID FROM VSY1DOSYA D WHERE D.ID=A.VSY1DOSYA_ID) BAGIMSIZBOLUM_ID,"

		+ "(SELECT ENTEGRASYONILISKINO FROM DIGIKENT.SRE1SOKAK WHERE ID = (SELECT SRE1SOKAK_ID FROM VSY1DOSYA D WHERE D.ID=A.VSY1DOSYA_ID)) SOKAK_ID,TO_CHAR(A.UPDDATE,'DD/MM/RRRR') UPDDATE,"

		+ "B.ID  FROM VSY1TALEP A,"

		+ "VSY1TALEPLINE B WHERE A.ID = B.VSY1TALEP_ID AND B.TSY1YARDIMSONUCU_ID in (2) AND B.SONUCTAR > SYSDATE - 30 AND "

		+ " B.TSY1MALZEMEGRUBU_ID IN(5020,5014,5021) ORDER BY B.SONUCTAR DESC";


		List list = new ArrayList<Object>();

		List<SosyalYardim> sosyalYardimList = new ArrayList<SosyalYardim>();


		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		list = query.list();


		for (Object o : list) {

		Map map = (Map) o;

		SosyalYardim sosyalyardim = new SosyalYardim();

		BigDecimal dre1bagbolum_id = (BigDecimal) map.get("BAGIMSIZBOLUM_ID");


		BigDecimal dre1mahalle_id = (BigDecimal) map.get("MAHALLE_ID");

		BigDecimal sre1sokak_id = (BigDecimal) map.get("SOKAK_ID");

		String tsy1malzemegrubu = (String) map.get("MALZEMEGRUBU");

		String tarih = (String) map.get("SONUCTAR");

       String guncelleme_tarihi = (String) map.get("UPDDATE");

       BigDecimal id = (BigDecimal) map.get("ID");


		if (dre1bagbolum_id != null)

		sosyalyardim.setBagbolum_id(dre1bagbolum_id.longValue());


		if (dre1mahalle_id != null)

		sosyalyardim.setMahalle_id(dre1mahalle_id.longValue());

		if (sre1sokak_id != null)

		sosyalyardim.setSokak_id(sre1sokak_id.longValue());

		if (tsy1malzemegrubu != null)
		sosyalyardim.setMalzemegrubu(tsy1malzemegrubu);

		if (tarih != null)

			 sosyalyardim.setTarih(tarih);

         if (guncelleme_tarihi != null)

         sosyalyardim.setGuncelleme_tarihi(guncelleme_tarihi);

 		if (id != null)

 			sosyalyardim.setId(id.longValue());

	    sosyalYardimList.add(sosyalyardim);

		}

		return sosyalYardimList;

	}

	//Sokak Rayi�
	public List<SokakRayic> searchSokakRayicGuncel(){
		String sql = "SELECT (SELECT TANIM FROM SRE1SOKAK WHERE ID=AIN5RAYICKURALI.SRE1SOKAK_ID) SOKAKADI ,(SELECT ENTEGRASYONILISKINO FROM DIGIKENT.SRE1SOKAK WHERE ID=AIN5RAYICKURALI.SRE1SOKAK_ID) SOKAK_ID,"
				+ " RAYICDEGERI FROM AIN5RAYICKURALI,AIN5RAYICKURALILINE WHERE "+
					" AIN5RAYICKURALILINE.YILI= (select extract(year from SYSDATE) from dual) AND  AIN5RAYICKURALI.ID = AIN5RAYICKURALILINE.AIN5RAYICKURALI_ID ORDER BY SOKAKADI ASC";

		List list = new ArrayList<Object>();
		List<SokakRayic> sokakRayicList = new ArrayList<SokakRayic>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			SokakRayic sokakrayic = new SokakRayic();
			BigDecimal rayicTutar = (BigDecimal) map.get("RAYICDEGERI");
			BigDecimal sokakId = (BigDecimal) map.get("SOKAK_ID");


			if (rayicTutar != null)
				sokakrayic.setRayicTutar(rayicTutar.doubleValue());

			if (sokakId != null)
				sokakrayic.setSokakId(sokakId.longValue());

			sokakRayicList.add(sokakrayic);
		}
		return sokakRayicList;
	}

	//Zab�ta Denetim
	public List<ZabitaDenetim> searchZabitaDenetim(){
		 String sql =     "SELECT (SELECT TANIM FROM AZBTFAALIYETTURU WHERE ID=AZBTFAALIYET.AZBTFAALIYETTURU_ID) FAALIYET,"

                          + "TO_CHAR(TARIH,'DD/MM/RRRR') TARIH,DRE1BAGBOLUM_ID,DRE1MAHALLE_ID,"

                          + "(SELECT ENTEGRASYONILISKINO FROM DRE1MAHALLE WHERE ID=AZBTFAALIYET.DRE1MAHALLE_ID) MAHALLE_ID,SRE1SOKAK_ID,"

                          + "(SELECT ENTEGRASYONILISKINO FROM SRE1SOKAK WHERE ID=AZBTFAALIYET.SRE1SOKAK_ID) SOKAK_ID,TO_CHAR(UPDDATE,'DD/MM/RRRR') UPDDATE,ID FROM AZBTFAALIYET "

                          + "WHERE TARIH > SYSDATE - 90 ORDER BY TARIH DESC";


             List list = new ArrayList<Object>();
             List<ZabitaDenetim> zabitaDenetimList = new ArrayList<ZabitaDenetim>();

             SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

             query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
             list = query.list();

             for (Object o : list) {
                    Map map = (Map) o;
                    ZabitaDenetim zabitadenetim = new ZabitaDenetim();

                    BigDecimal dre1bagbolum_id = (BigDecimal) map.get("DRE1BAGBOLUM_ID");

                    BigDecimal dre1mahalle_id = (BigDecimal) map.get("MAHALLE_ID");

                    BigDecimal sre1sokak_id = (BigDecimal) map.get("SOKAK_ID");

                    String azbtfaaliyetturu = (String) map.get("FAALIYET");

                    String tarih = (String) map.get("TARIH");

                    String guncelleme_tarihi = (String) map.get("UPDDATE");

                    BigDecimal id = (BigDecimal) map.get("ID");

                    if (dre1bagbolum_id != null)

                           zabitadenetim.setBagbolum_id(dre1bagbolum_id.longValue());

                    if (dre1mahalle_id != null)

                           zabitadenetim.setMahalle_id(dre1mahalle_id.longValue());

                    if (sre1sokak_id != null)

                          zabitadenetim.setSokak_id(sre1sokak_id.longValue());

                    if (azbtfaaliyetturu != null)

                    zabitadenetim.setFaaliyetturu(azbtfaaliyetturu);

                   if (tarih != null)

                  zabitadenetim.setTarih(tarih);

                  if (guncelleme_tarihi != null)

                   zabitadenetim.setGuncelleme_tarihi(guncelleme_tarihi);

                  if (id != null)

                      zabitadenetim.setId(id.longValue());

              zabitaDenetimList.add(zabitadenetim);
             }
             return zabitaDenetimList;

  }

	//�a�r� Merkezi
    public List<CagriMerkezi> searchCagriMerkezi(){
        String sql = "SELECT (SELECT TANIM FROM HDM1ISAKISITURU WHERE ID=DDM1ISAKISI.HDM1ISAKISITURU_ID)ISAKISITURU ,"

                          + "(SELECT ENTEGRASYONILISKINO  FROM DRE1MAHALLE WHERE ID=DDM1ISAKISI.DRE1MAHALLE_ID)MAHALLE_ID,"

                          + "(SELECT ENTEGRASYONILISKINO FROM SRE1SOKAK WHERE ID=DDM1ISAKISI.SRE1SOKAK_ID)SOKAK_ID,DRE1MAHALLE_ID,"

                          + "SRE1SOKAK_ID,SONUCDURUMU,TO_CHAR(TARIH,'DD/MM/RRRR')TARIH,TO_CHAR(UPDDATE,'DD/MM/RRRR') UPDDATE,ID FROM DDM1ISAKISI WHERE SONUCDURUMU='T'AND TARIH > "

                          + "SYSDATE - 30 ORDER BY TARIH DESC";


             List list = new ArrayList<Object>();
             List<CagriMerkezi> cagriMerkeziList = new ArrayList<CagriMerkezi>();

             SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);


             query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
             list = query.list();

             for (Object o : list) {
                    Map map = (Map) o;
                    CagriMerkezi cagrimerkezi = new CagriMerkezi();

                    BigDecimal dre1mahalle_id = (BigDecimal) map.get("MAHALLE_ID");

                    BigDecimal sre1sokak_id = (BigDecimal) map.get("SOKAK_ID");

                    String hdm1isakisituru = (String) map.get("ISAKISITURU");

                    String tarih = (String) map.get("TARIH");

                    String guncelleme_tarihi = (String) map.get("UPDDATE");

                    BigDecimal id = (BigDecimal) map.get("ID");

             if (dre1mahalle_id != null)
                           cagrimerkezi.setMahalle_id(dre1mahalle_id.longValue());

                    if (sre1sokak_id != null)
                          cagrimerkezi.setSokak_id(sre1sokak_id.longValue());

             if (hdm1isakisituru != null)
                          cagrimerkezi.setKonu_turu(hdm1isakisituru);

             if (tarih != null)

            	 cagrimerkezi.setTarih(tarih);


             if (guncelleme_tarihi != null)

            	 cagrimerkezi.setGuncelleme_tarihi(guncelleme_tarihi);

             if (id != null)
                 cagrimerkezi.setId(id.longValue());

             cagriMerkeziList.add(cagrimerkezi);
             }
             return cagriMerkeziList;

  }

    //Emlak Beyan
    public List<EmlakBeyan> searchEmlakBeyanBorclu(){
		String sql = "SELECT DISTINCT (SELECT ENTEGRASYONILISKINO FROM DIGIKENT.DRE1MAHALLE WHERE ID=A.DRE1MAHALLE_ID) MAHALLE_ID,(SELECT ENTEGRASYONILISKINO FROM DIGIKENT.SRE1SOKAK WHERE ID=A.SRE1SOKAK_ID) SOKAK_ID,A.ADANO,A.PARSELNO "
				+ " FROM  DIGIKENT.AIN2BILDIRIM A, DIGIKENT.JIN2TAHAKKUK J WHERE A.ID=J.AIN2BILDIRIM_ID  and J.BORCTUTARI>500 "
			 + " AND J.GIN1GELIRTURU_ID IN (1315200,1315100,1315300) AND J.MPI1PAYDAS_ID<>22953 GROUP BY A.DRE1MAHALLE_ID,A.SRE1SOKAK_ID,A.ADANO,A.PARSELNO HAVING SUM(J.BORCTUTARI) > 10000 ";

		List list = new ArrayList<Object>();
		List<EmlakBeyan> emlakBeyanList = new ArrayList<EmlakBeyan>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for (Object o : list) {
			Map map = (Map) o;
			EmlakBeyan emlakBeyan = new EmlakBeyan();

			String ada = (String) map.get("ADANO");
			String parsel = (String) map.get("PARSELNO");
			BigDecimal mahalle = (BigDecimal)map.get("MAHALLE_ID");
			BigDecimal sokak = (BigDecimal)map.get("SOKAK_ID");



			if (ada != null)
				emlakBeyan.setAda(ada);

			if (parsel != null)
				emlakBeyan.setParsel(parsel);



			if(sokak != null)
				emlakBeyan.setSokak_id(sokak.longValue());

			if(mahalle != null)
				emlakBeyan.setMahalle_id(mahalle.longValue());

			emlakBeyanList.add(emlakBeyan);
		}
		return emlakBeyanList;
	}


}
