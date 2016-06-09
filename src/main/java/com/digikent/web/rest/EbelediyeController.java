package com.digikent.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tr.com.vadi.vadibo.*;

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
import com.vadi.digikent.base.exception.VadiException;
import com.vadi.digikent.base.model.BaseLogon;
import com.vadi.digikent.ebelediye.util.PrepareMessage;
import com.vadi.digikent.ebelediye.webservice.EBelediyeService;
import com.vadi.digikent.sosyalhizmetler.nkh.model.SR7NikahSalonu;

@RestController
@RequestMapping("/ebelediye")
public class EbelediyeController extends EBelediyeService {

	@Autowired(required = true)
	@Qualifier("ebelediyeServiceImpl")
	protected EbelediyeService ebelediyeService;


	@Override
	public SearchBasvuruResponseType searchBasvuru(
			SearchBasvuruRequestType searchBasvuruRequest) {
		// TODO Auto-generated method stub
		return super.searchBasvuru(searchBasvuruRequest);
	}

	@Override
	public SearchMudurlukResponseType searchMudurluk(
			SearchMudurlukRequestType searchMudurlukRequest) {
		// TODO Auto-generated method stub
		return super.searchMudurluk(searchMudurlukRequest);
	}


	@Override
	public SearchKapiResponseType searchKapi(
			SearchKapiRequestType searchKapiRequest) {
		// TODO Auto-generated method stub
		return super.searchKapi(searchKapiRequest);
	}



	@RequestMapping(value = "/applyBilgiEdinme", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public BilgiEdinmeResponseType applyBilgiEdinme(
			@RequestBody ApplyBilgiEdinmeRequestType applyBilgiEdinmeRequest) {
		// TODO Auto-generated method stub
		return super.applyBilgiEdinme(applyBilgiEdinmeRequest);
	}


	@RequestMapping(value = "/applyBasvuru", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public ApplyBasvuruResponseType applyBasvuru(
			@RequestBody ApplyBasvuruRequestType applyBasvuruRequest) {
		// TODO Auto-generated method stub
		return super.applyBasvuru(applyBasvuruRequest);
	}

	@RequestMapping(value = "/applyBasvuruEk", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public ApplyBasvuruResponseType applyBasvuruEk(
			@RequestBody ApplyBasvuruEkRequestType applyBasvuruEkRequest) {
		// TODO Auto-generated method stub
		return super.applyBasvuruEk(applyBasvuruEkRequest);
	}


	@Override
	public SearchIlResponseType searchIl(SearchIlRequestType searchIlRequestType) {
		// TODO Auto-generated method stub
		return super.searchIl(searchIlRequestType);
	}

	@Override
	public SearchIlceResponseType searchIlce(
			SearchIlceRequestType searchIlceRequest) {
		// TODO Auto-generated method stub
		return super.searchIlce(searchIlceRequest);
	}


	@RequestMapping(value = "/searchMahalle/{ilceid}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<RE1Mahalle> searchMahalle(@PathVariable("ilceid") long ilceid) {
		return ebelediyeService.findMahalleListByIlceId(ilceid);
	}

	@RequestMapping(value = "/getSokakRayicYillar", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<SokakRayicYillar> getSokakRayicYillar() {
		return ebelediyeService.getSokakRayicYillarList();
	}

	@RequestMapping(value = "/getCTVYillar", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<CTVYillar> getCTVYillar() {
		return ebelediyeService.getCTVYillarList();
	}

	@RequestMapping(value = "/getNihakSalonuList", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<SR7NikahSalonu> searchNikahSalon() {
		return ebelediyeService.searchNikahSalon();
	}

	@RequestMapping(value = "/searchBinaAsinmaOrani", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<BinaAsinmaOranlari> searchBinaAsinmaOrani() {
		return ebelediyeService.searchBinaAsinmaOrani();
	}

	@RequestMapping(value = "/searchCevreTemizlikTarife/{yil}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<CevreTemizlikTarife> searchCevreTemizlikTarife(@PathVariable("yil") long yil) {
		return ebelediyeService.searchCevreTemizlikTarife(yil);
	}

	@RequestMapping(value = "/searchSokakRayic/{mahalleid}/{yil}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<SokakRayic> searchSokakRayic(@PathVariable("mahalleid") long mahalleid, @PathVariable("yil") long yil) {
		return ebelediyeService.searchSokakRayic(mahalleid, yil);
	}

	@RequestMapping(value = "/searchPaydas/{adi}/{soyadi}/{babaadi}/{tckimlik}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<PaydasSicil> searchPaydas(@PathVariable("adi") String adi, @PathVariable("soyadi") String soyadi,
																				@PathVariable("babaadi") String babaadi, @PathVariable("tckimlik") long tckimlik) {
		return ebelediyeService.searchPaydas(adi, soyadi, babaadi, tckimlik);
	}

	@RequestMapping(value = "/searchNikahRezervasyon/{nikahtarihi}/{salonid}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<NikahRezervasyon> searchNikahRezervasyon(@PathVariable("nikahtarihi") String nikahtarihi, @PathVariable("salonid") long salonid) {
		// TODO Auto-generated method stub
		return ebelediyeService.searchNikahRezervasyon(nikahtarihi, salonid);
	}

	@RequestMapping(value = "/searchBasvuru/{basvuruno}/{eposta}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<BilgiEdinme> searchBasvuru(@PathVariable("basvuruno") long basvuruno, @PathVariable("eposta") String eposta) {
		// TODO Auto-generated method stub
		return ebelediyeService.searchBasvuru(basvuruno, eposta);
	}

	@RequestMapping(value = "/searchBelge/{referansno}/{parola}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<BelgeSorgula> searchBelge(@PathVariable("referansno") long referansno, @PathVariable("parola") String parola) {
		// TODO Auto-generated method stub
		return ebelediyeService.searchBelge(referansno, parola);
	}

	@RequestMapping(value = "/searchSurecBasvuru/{basvuruno}/{paydasno}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<SurecBasvuru> searchSurecBasvuru(@PathVariable("basvuruno") long basvuruno, @PathVariable("paydasno") long paydasno) {
		// TODO Auto-generated method stub
		return ebelediyeService.searchSurecBasvuru(basvuruno, paydasno);
	}


	@Override
	public SearchSokakResponseType searchSokak(
			SearchSokakRequestType searchSokakRequest) {
		// TODO Auto-generated method stub
		return super.searchSokak(searchSokakRequest);
	}

	@Override
	public SearchDaireResponseType searchDaire(
			SearchDaireRequestType searchDaireRequest) {
		// TODO Auto-generated method stub
		return super.searchDaire(searchDaireRequest);
	}


	@Override
	public String executeSql(String sql, String parameter) {
		// TODO Auto-generated method stub
		return super.executeSql(sql, parameter);
	}

	@Override
	public boolean postMailYeni(String recipients, String subject,
															String message, String fromUser) {
		// TODO Auto-generated method stub
		return super.postMailYeni(recipients, subject, message, fromUser);
	}

	@Override
	public Boolean sendEmail(Long id, String message, String eposta)
			throws MessagingException {
		// TODO Auto-generated method stub
		return super.sendEmail(id, message, eposta);
	}

	@Override
	public Long sendSMS(Long phoneNumber, Long id, String kayitParolasi) {
		// TODO Auto-generated method stub
		return super.sendSMS(phoneNumber, id, kayitParolasi);
	}

	@Override
	public PrepareMessage getPrepareMessage() {
		// TODO Auto-generated method stub
		return super.getPrepareMessage();
	}

	@Override
	public void setPrepareMessage(PrepareMessage prepareMessage) {
		// TODO Auto-generated method stub
		super.setPrepareMessage(prepareMessage);
	}

	@Override
	public SearchSosyalYardimResponseType readSosyalYardimByCriteria(
			SearchSosyalYardimReqType searchSosyalYardimReq) {
		// TODO Auto-generated method stub
		return super.readSosyalYardimByCriteria(searchSosyalYardimReq);
	}

	@Override
	public SorguGenelBorcResponseType sorguGenelBorc(
			SorguGenelBorcRequestType sorguGenelBorcRequest) {
		// TODO Auto-generated method stub
		return super.sorguGenelBorc(sorguGenelBorcRequest);
	}

	@Override
	public KaydetGenelTahsilatResponseType kaydetGenelTahsilat(
			KaydetGenelTahsilatRequestType kaydetGenelTahsilatRequest) {
		// TODO Auto-generated method stub
		return super.kaydetGenelTahsilat(kaydetGenelTahsilatRequest);
	}

	@Override
	public SorguGelirGrubuResponseType sorguGelirGrubu(
			SorguGelirGrubuRequestType sorguGelirGrubuRequest) {
		// TODO Auto-generated method stub
		return super.sorguGelirGrubu(sorguGelirGrubuRequest);
	}

	@Override
	public SorguVezneResponseType sorguVezne(
			SorguVezneRequestType sorguVezneRequest) {
		// TODO Auto-generated method stub
		return super.sorguVezne(sorguVezneRequest);
	}

	@Override
	public SorguVezneLOVResponseType sorguVezneLOV(
			SorguVezneLOVRequestType sorguVezneLOVRequest) {
		// TODO Auto-generated method stub
		return super.sorguVezneLOV(sorguVezneLOVRequest);
	}

	@Override
	public SorguTahsilatGenelResponseType sorguTahsilatGenel(
			SorguTahsilatGenelRequestType sorguTahsilatGenelRequest) {
		// TODO Auto-generated method stub
		return super.sorguTahsilatGenel(sorguTahsilatGenelRequest);
	}

	@Override
	public SorguTahsilatKaydiResponseType sorguTahsilatKaydi(
			SorguTahsilatKaydiRequestType sorguTahsilatKaydiRequest) {
		// TODO Auto-generated method stub
		return super.sorguTahsilatKaydi(sorguTahsilatKaydiRequest);
	}

	@Override
	public DeleteTahsilatResponseType deleteTahsilat(
			DeleteTahsilatRequestType deleteTahsilatRequest) {
		// TODO Auto-generated method stub
		return super.deleteTahsilat(deleteTahsilatRequest);
	}

	@Override
	public SearchCenazeResponseType searchCenaze(
			SearchCenazeRequestType searchCenazeRequest) {
		// TODO Auto-generated method stub
		return super.searchCenaze(searchCenazeRequest);
	}

	@Override
	public SearchBasvuruTipiResponseType searchBasvuruTipi(
			SearchBasvuruTipiRequestType searchBasvuruTipiRequestType) {
		// TODO Auto-generated method stub
		return super.searchBasvuruTipi(searchBasvuruTipiRequestType);
	}

	@Override
	@RequestMapping("/callData")
	@PreAuthorize("hasRole('ROLE_DEV')")
	public CagriDataResponseType getCallData(CagriDataRequestType callId)
			throws VadiException {
		CagriDataResponseType cagriDataResponseType = new CagriDataResponseType();
		CagriDataResponse resp = new CagriDataResponse();
		resp.setType("tip1");
		List<CagriDataResponse> resps = new ArrayList<CagriDataResponse>();
		resps.add(resp);
		cagriDataResponseType.setCagriDataResponse(resps);
		return cagriDataResponseType;
	}

	@Override
	@RequestMapping("/tahakkut")
	public TahakkuksuzTahsilatResponse tahakkuksuzTahsilat(
			TahakkuksuzTahsilatRequest tahakkuksuzTahsilatRequest)
			throws VadiException {
		// TODO Auto-generated method stub
		return super.tahakkuksuzTahsilat(tahakkuksuzTahsilatRequest);
	}

	public void testKep(BaseLogon baseLogon) throws VadiException {
		// TODO Auto-generated method stub
		//super.testKep(baseLogon);
	}

	//sosyal yard�m webservis bilgisi
	@RequestMapping(value = "/searchSosyalYardim", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<SosyalYardim> searchSosyalYardim() {
		return ebelediyeService.searchSosyalYardim();
	}

	//zab�ta denetim webservis bilgisi
	@RequestMapping(value = "/searchZabitaDenetim", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<ZabitaDenetim> searchZabitaDenetim() {
		return ebelediyeService.searchZabitaDenetim();
	}

	//cagri merkezi webservis bilgisi
	@RequestMapping(value = "/searchCagriMerkezi", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<CagriMerkezi> searchCagriMerkezi() {
		return ebelediyeService.searchCagriMerkezi();
	}

	//Emlak Beyan� webservis bilgisi
	@RequestMapping(value = "/searchEmlakBeyanBorclu", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<EmlakBeyan> searchEmlakBeyanBorclu() {
		return ebelediyeService.searchEmlakBeyanBorclu();

	}

	//Sokak rayi� webservis bilgisi
	@RequestMapping(value = "/searchSokakRayicGuncel", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<SokakRayic> searchSokakRayicGuncel() {
		return ebelediyeService.searchSokakRayicGuncel();
	}

	//i�yeri ruhsat webservis bilgisi
	@RequestMapping(value = "/searchIsyeriRuhsat", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_DEV')")
	public List<IsyeriRuhsat> searchIsyeriRuhsat() {
		return ebelediyeService.searchIsyeriRuhsat();
	}

}


