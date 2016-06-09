package com.digikent.vadirest.dao;

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


public interface EbelediyeDAO {
	public List<RE1Mahalle> findMahalleListByIlceId(long ilceid);
	public List<SokakRayicYillar> getSokakRayicYillarList();
	public List<CTVYillar> getCTVYillarList();
	public List<SR7NikahSalonu> searchNikahSalon();
	public List<BinaAsinmaOranlari> searchBinaAsinmaOrani();
	public List<CevreTemizlikTarife> searchCevreTemizlikTarife(long yil);
	public List<SokakRayic> searchSokakRayic(long mahalleid, long yil);
	public List<PaydasSicil> searchPaydas(String adi, String soyadi, String babaadi, long tckimlik);
	public List<NikahRezervasyon> searchNikahRezervasyon(String nikahtarihi, long salonid);
	public List<BilgiEdinme> searchBasvuru(long basvuruno, String eposta);
	public List<BelgeSorgula> searchBelge(long referansno, String parola);
	public List<SurecBasvuru> searchSurecBasvuru(long basvuruno, long paydasno);
	public List<IsyeriRuhsat> searchIsyeriRuhsat();
	public List<SosyalYardim> searchSosyalYardim();
	public List<SokakRayic> searchSokakRayicGuncel();
	public List<ZabitaDenetim> searchZabitaDenetim();
	public List<CagriMerkezi> searchCagriMerkezi();
	public List<EmlakBeyan> searchEmlakBeyanBorclu();

}
