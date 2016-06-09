package com.digikent.vadirest.service.impl;

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
import com.digikent.vadirest.service.EbelediyeService;
import com.vadi.digikent.abs.gmk.model.RE1Mahalle;
import com.vadi.digikent.sosyalhizmetler.nkh.model.SR7NikahSalonu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("ebelediyeServiceImpl")
@Transactional
public class EbelediyeServiceImpl implements EbelediyeService {
	
	@Autowired(required=true)
	private EbelediyeDAO ebelediyeDAO;
	
	public List<RE1Mahalle> findMahalleListByIlceId(long ilceid) {
		
		return ebelediyeDAO.findMahalleListByIlceId(ilceid);
	}

	public List<SokakRayicYillar> getSokakRayicYillarList() {
		
		return ebelediyeDAO.getSokakRayicYillarList();
	}
	
	public List<CTVYillar> getCTVYillarList() {
		
		return ebelediyeDAO.getCTVYillarList();
	}
	
	public List<SR7NikahSalonu> searchNikahSalon() {
		
		return ebelediyeDAO.searchNikahSalon();
	}
	
	public List<BinaAsinmaOranlari> searchBinaAsinmaOrani() {
		
		return ebelediyeDAO.searchBinaAsinmaOrani();
	}
	
	public List<CevreTemizlikTarife> searchCevreTemizlikTarife(long yil) {
		
		return ebelediyeDAO.searchCevreTemizlikTarife(yil);
	}
	
	public List<SokakRayic> searchSokakRayic(long mahalleid, long yil) {
		
		return ebelediyeDAO.searchSokakRayic(mahalleid, yil);
	}
	
	public List<PaydasSicil> searchPaydas(String adi, String soyadi, String babaadi, long tckimlik) {
		
		return ebelediyeDAO.searchPaydas(adi, soyadi, babaadi, tckimlik);
	}
	
	public List<NikahRezervasyon> searchNikahRezervasyon(String nikahtarihi, long salonid) {
		
		return ebelediyeDAO.searchNikahRezervasyon(nikahtarihi, salonid);
	}
	
	public List<BilgiEdinme> searchBasvuru(long basvuruno, String eposta) {
		
		return ebelediyeDAO.searchBasvuru(basvuruno, eposta);
	}
	
	public List<BelgeSorgula> searchBelge(long referansno, String parola) {
		
		return ebelediyeDAO.searchBelge(referansno, parola);
	}
	
	public List<SurecBasvuru> searchSurecBasvuru(long basvuruno, long paydasno) {
		
		return ebelediyeDAO.searchSurecBasvuru(basvuruno, paydasno);
	}
	
	public List<IsyeriRuhsat> searchIsyeriRuhsat() {
		
		return ebelediyeDAO.searchIsyeriRuhsat();
	}
	
	public List<SosyalYardim> searchSosyalYardim() {
		return ebelediyeDAO.searchSosyalYardim();
	}
	
	public List<SokakRayic> searchSokakRayicGuncel() {
		
		return ebelediyeDAO.searchSokakRayicGuncel();
	}
	
	public List<ZabitaDenetim> searchZabitaDenetim() {

		return ebelediyeDAO.searchZabitaDenetim();
	}

	public List<CagriMerkezi> searchCagriMerkezi() {

		return ebelediyeDAO.searchCagriMerkezi();
	}
		
	public List<EmlakBeyan> searchEmlakBeyanBorclu() {

		return ebelediyeDAO.searchEmlakBeyanBorclu();

	}

	

}
