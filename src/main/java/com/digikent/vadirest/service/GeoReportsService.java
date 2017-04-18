package com.digikent.vadirest.service;

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

import java.util.List;

public interface GeoReportsService {
	
	//get mahalle list
	public List<RE1Mahalle> findMahalleListByIlceId(long ilceid);
	
	//get sokak rayi� y�llar list
	public List<SokakRayicYillar> getSokakRayicYillarList();
	
	//get �tv y�llar list
	public List<CTVYillar> getCTVYillarList();
	
	//get nikah salonu list
	public List<SR7NikahSalonu> searchNikahSalon();
	
	//get bina a��nma list
	public List<BinaAsinmaOranlari> searchBinaAsinmaOrani();
	
	//get �evre temizlik tarifeleri list
	public List<CevreTemizlikTarife> searchCevreTemizlikTarife(long yil);
	
	//get sokak rayi� list
	public List<SokakRayic> searchSokakRayic(long mahalleid, long yil);
	
	//get payda� sicil sorgulama
	public List<PaydasSicil> searchPaydas(String adi, String soyadi, String babaadi, long tckimlik);
	
	//get nikah rezervasyon list
	public List<NikahRezervasyon> searchNikahRezervasyon(String nikahtarihi, long salonid);
	
	//get bilgi edinme  list
	public List<BilgiEdinme> searchBasvuru(long basvuruno, String eposta);
	
	//get belge sorgula list
	public List<BelgeSorgula> searchBelge(long referansno, String parola);
	
	//get s�re� ba�vuru sorgula list
	public List<SurecBasvuru> searchSurecBasvuru(long basvuruno, long paydasno);
	
	//get isyeri ruhsat list
	public List<IsyeriRuhsat> searchIsyeriRuhsat();
	
	//get sosyal yard�m list
	public List<SosyalYardim> searchSosyalYardim();
	
	//get sokak rayi� list
	public List<SokakRayic> searchSokakRayicGuncel();
	
	//get emlak beyan list
	public List<EmlakBeyan> searchEmlakBeyanBorclu();
	
	//get zab�ta denetim list
	public List<ZabitaDenetim> searchZabitaDenetim();

	//get �a�r� merkezi list
	public List<CagriMerkezi> searchCagriMerkezi();

}
