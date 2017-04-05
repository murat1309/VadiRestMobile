package com.digikent.vadirest.service.impl;

import com.digikent.vadirest.dao.GeoReportsDAO;
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
import com.digikent.vadirest.service.GeoReportsService;
import com.vadi.digikent.abs.gmk.model.RE1Mahalle;
import com.vadi.digikent.sosyalhizmetler.nkh.model.SR7NikahSalonu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("geoReportsServiceImpl")
@Transactional
public class GeoReportsServiceImpl implements GeoReportsService {
	
	@Autowired(required=true)
	private GeoReportsDAO geoReportsDao;
	
	public List<RE1Mahalle> findMahalleListByIlceId(long ilceid) {
		
		return geoReportsDao.findMahalleListByIlceId(ilceid);
	}

	public List<SokakRayicYillar> getSokakRayicYillarList() {
		
		return geoReportsDao.getSokakRayicYillarList();
	}
	
	public List<CTVYillar> getCTVYillarList() {
		
		return geoReportsDao.getCTVYillarList();
	}
	
	public List<SR7NikahSalonu> searchNikahSalon() {
		
		return geoReportsDao.searchNikahSalon();
	}
	
	public List<BinaAsinmaOranlari> searchBinaAsinmaOrani() {
		
		return geoReportsDao.searchBinaAsinmaOrani();
	}
	
	public List<CevreTemizlikTarife> searchCevreTemizlikTarife(long yil) {
		
		return geoReportsDao.searchCevreTemizlikTarife(yil);
	}
	
	public List<SokakRayic> searchSokakRayic(long mahalleid, long yil) {
		
		return geoReportsDao.searchSokakRayic(mahalleid, yil);
	}
	
	public List<PaydasSicil> searchPaydas(String adi, String soyadi, String babaadi, long tckimlik) {
		
		return geoReportsDao.searchPaydas(adi, soyadi, babaadi, tckimlik);
	}
	
	public List<NikahRezervasyon> searchNikahRezervasyon(String nikahtarihi, long salonid) {
		
		return geoReportsDao.searchNikahRezervasyon(nikahtarihi, salonid);
	}
	
	public List<BilgiEdinme> searchBasvuru(long basvuruno, String eposta) {
		
		return geoReportsDao.searchBasvuru(basvuruno, eposta);
	}
	
	public List<BelgeSorgula> searchBelge(long referansno, String parola) {
		
		return geoReportsDao.searchBelge(referansno, parola);
	}
	
	public List<SurecBasvuru> searchSurecBasvuru(long basvuruno, long paydasno) {
		
		return geoReportsDao.searchSurecBasvuru(basvuruno, paydasno);
	}
	
	public List<IsyeriRuhsat> searchIsyeriRuhsat() {
		
		return geoReportsDao.searchIsyeriRuhsat();
	}
	
	public List<SosyalYardim> searchSosyalYardim() {
		return geoReportsDao.searchSosyalYardim();
	}
	
	public List<SokakRayic> searchSokakRayicGuncel() {
		
		return geoReportsDao.searchSokakRayicGuncel();
	}
	
	public List<ZabitaDenetim> searchZabitaDenetim() {

		return geoReportsDao.searchZabitaDenetim();
	}

	public List<CagriMerkezi> searchCagriMerkezi() {

		return geoReportsDao.searchCagriMerkezi();
	}
		
	public List<EmlakBeyan> searchEmlakBeyanBorclu() {

		return geoReportsDao.searchEmlakBeyanBorclu();

	}

	

}
