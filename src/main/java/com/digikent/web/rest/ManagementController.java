package com.digikent.web.rest;

import com.digikent.vadirest.dto.*;
import com.digikent.vadirest.service.ManagementService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/yonetim")
public class ManagementController {

	private final Logger LOG = LoggerFactory.getLogger(ManagementController.class);
	
	@Autowired(required=true)
	private ManagementService managementService;
	
	// banka borc durumu
	@RequestMapping(value = "bankaDurumu/{id}/{year}",method = RequestMethod.GET)
	public List<BankaDurumu> getBankStatus(@PathVariable("id") long id, @PathVariable("year") long year){
		System.out.println("-------------banka durumu----------");
		System.out.println(year);
		return managementService.getBankStatus(id,year);		
	}
	// banka borc durumu hesap plani bazinda 
	@RequestMapping(value = "bankaDurumu/{id}/{accountId}/{year}/{startDate}/{endDate}",method = RequestMethod.GET)
	public List<BankaDurumuDetay> getBankStatusDetail(@PathVariable("id") long id, @PathVariable("accountId") long accountId, @PathVariable("year") long year, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate){
		System.out.println("-------------banka durumu hesap plani bazinda----------");
		System.out.println(year);
		return managementService.getBankStatusDetail(id,accountId,year,startDate,endDate);		
	}
	//personel bilgisi
	@RequestMapping(value = "personelBilgileri", method = RequestMethod.GET)
	public List<PersonelBilgileri> getStaffInfomation(){
		System.out.println("----------belediye personel bilgileri---------");
		return managementService.getStaffInfomation();
	}
	//personel bilgisi
	@RequestMapping(value = "personelKadroBilgileri", method = RequestMethod.GET)
	public List<PersonelBilgileri> getPersonelKadroInformation(){
		LOG.debug("Rest Request to get personel kadro bilgileri");
		return managementService.getPersonelKadroInformation();
	}
	
	//kurum personel detay
	@RequestMapping(value = "personelGrubuDetay/{servisGorevId}/{turu}", method = RequestMethod.GET)
	public List<PersonelBilgileriDetay> getStaffDetail(@PathVariable("servisGorevId")long servisGorevId, @PathVariable("turu") char turu){
		System.out.println("--------------personel detay-----------------");
		return managementService.getStaffDetail(servisGorevId, turu);
	}
	
	//kurum personel detay butun
	@RequestMapping(value = "personelGrubuDetay/{servisGorevId}/", method = RequestMethod.GET)
	public List<PersonelBilgileriDetay> getStaff(@PathVariable("servisGorevId")long servisGorevId){
		System.out.println("--------------personel detay butun-----------------");
		System.out.println(servisGorevId);
		char turu =' ';
		return managementService.getStaffDetail(servisGorevId, turu);
	}

	//kurum personel Kadro detay butun
	@RequestMapping(value = "personelGrubuKadroDetay/{servisKadroId}", method = RequestMethod.GET)
	public List<PersonelBilgileriDetay> getKadroDetay(@PathVariable("servisKadroId")long servisKadroId){
		LOG.debug("Rest Request to get job quiters, startDate, endDate: {}", servisKadroId);
		return managementService.getKadroDetay(servisKadroId);
	}
	
	//personel Grubu
	@RequestMapping(value = "personelGrubu", method = RequestMethod.GET)
	public List<PersonelGrup> getStaffGroup(){
		System.out.println("--------personel grup-------------------------");
		return managementService.getStaffGroup();
	}

	//ise baslayanlar
	@RequestMapping(value = "iseBaslayanlar/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<PersonelBilgileriDetay> getJobStarters(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate){
		LOG.debug("Rest Request to get job starters, startDate, endDate: {}", startDate, endDate);
		return managementService.getJobStarters(startDate, endDate);
	}

	//ise baslayanlar
	@RequestMapping(value = "istenAyrılanlar/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<PersonelBilgileriDetay> getJobQuitters(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate){
		LOG.debug("Rest Request to get job quiters, startDate, endDate: {}", startDate, endDate);
		return managementService.getJobQuitters(startDate, endDate);
	}
	
	//kurum borc bilgisi
	@RequestMapping(value ="kurumBorc/{id}/{year}", method = RequestMethod.GET)
	public List<KurumBorc> getDebtStatus(@PathVariable("id") long id, @PathVariable("year") long year){
		System.out.println("--------kurum borc durumu----------");
		System.out.println(year);
		return managementService.getDebtStatus(id,year);
	}
	
	//yapilan odemeler
	@RequestMapping(value = "yapilanOdemeler/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<YapilanOdemeler> getPayments(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("--------yapilan odemeler----------");
		return managementService.getPayments(startDate,endDate);
	}

	//tum odemeler
	@RequestMapping(value ="tumOdemeler/{year}/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<FirmaOdeme> getAllPayments(@PathVariable("year") long year,
										   @PathVariable("startDate") String startDate,
										   @PathVariable("endDate") String endDate ){
		LOG.debug("Rest Request to get all payments year, startDate, endDate: {}", year, startDate, endDate);
		return managementService.getAllPayments(year, startDate, endDate);
	}
	
	//birim bazinda basvuru sayisi
	@RequestMapping(value = "birimBasvuru/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<Basvuru> getApplyCount(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("--------basvuru sayisi----------");
		return managementService.getApplyCount(startDate, endDate);
	}
	
	//birim bazinda basvuru sayisi ozeti
	@RequestMapping(value = "birimBasvuruOzet/{birimId}/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<BasvuruOzet> getApplySummary(@PathVariable("birimId") long birimId, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate)throws Throwable{
		System.out.println("--------basvuru sayisi----------");
		return managementService.getApplySummary(birimId, startDate, endDate);
	}
	
	//birim bazinda basvuru sayisi
	@RequestMapping(value = "birimBasvuruOzetDetay/{birimId}/{basvuruNo}", method = RequestMethod.GET)
	public BasvuruOzetDetay getApplySummaryDetail(@PathVariable("basvuruNo") long basvuruNo){
		System.out.println("--------basvuru sayisi----------");
		return managementService.getApplySummaryDetail(basvuruNo);
	}
			
	//Firma Borc durumu
	@RequestMapping(value ="firmaBorc/{persid}/{year}", method = RequestMethod.GET)
	public List<FirmaBorc> getFirmDebtStatus(@PathVariable("persid") long persid, @PathVariable("year") long year){
		System.out.println("--------firma borc durumu----------");
		System.out.println(persid);
		return managementService.getFirmDebtStatus(persid,year);
	}

	//Firma Alacak durumu
	@RequestMapping(value ="firmaAlacak/{year}", method = RequestMethod.GET)
	public List<FirmaAlacak> getFirmaAlacak(@PathVariable("year") long year){
		LOG.debug("Rest Request to get firma alacak year: {}", year);
		return managementService.getFirmaAlacak(year);
	}

	//Firma Alacak durumu
	@RequestMapping(value ="firmaOdeme/{year}/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<FirmaOdeme> getFirmaOdeme(@PathVariable("year") long year,
										  @PathVariable("startDate") String startDate,
										  @PathVariable("endDate") String endDate ){
		LOG.debug("Rest Request to get firma odeme year: {}", year);
		return managementService.getFirmaOdeme(year, startDate, endDate);
	}
	
	//Konu bazinda talep sayisi
	@RequestMapping(value = "talep/{timePeriod}", method = RequestMethod.GET)
	public List<Talep> getRequestCount(@PathVariable("timePeriod") String timePeriod) throws Throwable{
		System.out.println("--------talep sayisi----------");
		System.out.println(timePeriod);
		return managementService.getRequestCount(timePeriod);
	}
	
	//Gelir Grubu bazinda 
	@RequestMapping(value = "gelirGrubu/{timePeriod}", method = RequestMethod.GET)
	public List<GelirGrubu> getIncomeGroup(@PathVariable("timePeriod") String timePeriod) throws Throwable{
		System.out.println("-----------gelir grubu----------");
		System.out.println(timePeriod);
		return managementService.getIncomeGroup(timePeriod);
	}
	
	//Gelir Grubu bazinda tahsilat
	@RequestMapping(value = "gelirGrubu/{id}/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GelirGrubuDetay> getIncomeGroupDetail(@PathVariable("id") long id, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("-----------gelir grubu----------");
		return managementService.getIncomeGroupDetail(id,startDate,endDate);
	}
	
	//Gelir Turu bazinda
	@RequestMapping(value = "gelirTuru/{timePeriod}", method = RequestMethod.GET)
	public List<GelirTuru> getIncomeType(@PathVariable("timePeriod")String timePeriod) throws Throwable{
		System.out.println("-----------gelir turu----------");
		System.out.println(timePeriod);
		return managementService.getIncomeType(timePeriod);
	}

	//Gelir Turu bazinda
	@RequestMapping(value = "gelirTuru/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GelirTuru> getIncomeType(@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("-----------gelir turu----------");
		return managementService.getIncomeType(startDate, endDate);
	}
	
	//Gelir Turu bazinda tahsilat
	@RequestMapping(value = "gelirTuruDetay/{id}/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GelirTuruDetay> getIncomeTypeDetail(@PathVariable("id")long id, @PathVariable("startDate")String startDate, @PathVariable("endDate")String endDate) throws Throwable{
		System.out.println("-----------gelir turu bazinda tahsilat----------");
		return managementService.getIncomeTypeDetail(id,startDate,endDate);
	}
	
	//Yonetim Bilgi Sistemi Menu Tree
	@RequestMapping(value = "ybsMenuNg/{userName}", method = RequestMethod.GET)
	public List<YbsMenu> getYbsMenuNgTree(@PathVariable("userName")String userName){
		System.out.println("---------ybs menu Ng tree--------");
		System.out.println(userName);
		return managementService.getYbsMenuNgTree(userName);
	}
	
	//Gunluk Gelir Grubu bazinda 
	@RequestMapping(value = "gelirGrubuGunluk", method = RequestMethod.GET)
	public List<GraphGeneral> getIncomeGroupDaily() throws Throwable{
		System.out.println("----------- gelir grubu(gunluk)----------");
		return managementService.getIncomeGroupDaily();
	}
	
	//Tarih Araligi Gelir Grubu bazinda 
	@RequestMapping(value = "gelirGrubu/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GraphGeneral> getIncomeGroup(@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("----------- gelir grubu(aralik)----------");
		return managementService.getIncomeGroup(startDate,endDate);
	}
	@RequestMapping(value = "gelirTuru/{gelirGrubuId}/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GraphGeneral> getIncomeType(@PathVariable("gelirGrubuId")long gelirGrubuId,@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("----------- gelir turu gelir grubu bazli(aralik)----------");
		return managementService.getIncomeType(gelirGrubuId, startDate, endDate);
	}
	
	//Gelirler yonetimi vezne
	@RequestMapping(value = "gelirlerYonetimiVezne/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GraphGeneral> getIncomeManagementPayOffice(@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("----------- gelirler yonetimi vezne----------");
		System.out.println(startDate);
		System.out.println(endDate);
		return managementService.getIncomeManagementPayOffice(startDate,endDate);
	}
	
	//Gelirler yonetimi odeme
	@RequestMapping(value = "gelirlerYonetimiOdeme/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GraphGeneral> getIncomeManagementPayment(@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("----------- gelirler Yonetimi Odeme----------");
		System.out.println(startDate);
		System.out.println(endDate);
		return managementService.getIncomeManagementPayment(startDate,endDate);
	}
	
	//Gelirler yonetimi bildirim
	@RequestMapping(value = "gelirlerYonetimiBildirim/{year}", method = RequestMethod.GET)
	public List<GraphGeneral> getIncomeManagementNotification(@PathVariable("year") long year) throws Throwable{
		System.out.println("----------- gelirler Yonetimi Bildirim----------");
		System.out.println(year);
		return managementService.getIncomeManagementNotification(year);
	}
	
	//Gelirler yonetimi bildirim mahalle bazli
	@RequestMapping(value = "gelirlerYonetimiBildirimMahalle/{id}/{year}", method = RequestMethod.GET)
	public List<GraphGeneral> getIncomeManagementNotificationDetail(@PathVariable("id") long id,@PathVariable("year") long year) throws Throwable{
		System.out.println("----------- gelirler Yonetimi Bildirim Mahalle Bazli----------");
		System.out.println(year);
		return managementService.getIncomeManagementNotificationDetail(id,year);
	}
	
	//Gelirler yonetimi mahalle
	@RequestMapping(value = "gelirlerYonetimiMahalle/{tur}/{year}", method = RequestMethod.GET)
	public List<GraphGeneral> getIncomeManagementStreet(@PathVariable("tur") String tur,@PathVariable("year") long year) throws Throwable{
		System.out.println("----------- gelirler Yonetimi Mahalle----------");
		System.out.println(year);
		return managementService.getIncomeManagementStreet(tur, year);
	}
	
	//Gelirler yonetimi mahalle paydas bazinda
	@RequestMapping(value = "gelirlerYonetimiMahalle/{id}/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GelirlerYonetimiMahalle> getIncomeManagementStreetDetail(@PathVariable("id") long id, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("----------- gelirler Yonetimi Mahalle paydas bazinda----------");
		return managementService.getIncomeManagementStreetDetail(id, startDate, endDate);
	}
	
	//Gelirler yonetimi odeme yapan mukellef
	@RequestMapping(value = "gelirlerYonetimiOdemeYapanMukellef/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GraphGeneral> getIncomeManagementTaxPayer(@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("----------- gelirler Yonetimi Odeme----------");
		System.out.println(startDate);
		System.out.println(endDate);
		return managementService.getIncomeManagementTaxPayer(startDate,endDate);
	}
	
	//Finansman yonetimi gelir gider butcesi
	@RequestMapping(value = "finansmanYonetimiGelirGider", method = RequestMethod.GET)
	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncomeExpense() throws Throwable{
		System.out.println("----------- finansman Yonetimi gelir gider----------");
		return managementService.getFinancialManagementIncomeExpense();
	}
	
	//Finansman yonetimi gelir gider butcesi ay bazinda
	@RequestMapping(value = "finansmanYonetimiGelirGiderAyBazli/{year}", method = RequestMethod.GET)
	public List<FinansmanYonetimiGelirGiderAylik> getFinancialManagementIncomeExpenseMonthly(@PathVariable("year") long year) throws Throwable{
		System.out.println("----------- finansman Yonetimi gelir gider ay bazinda----------");
		return managementService.getFinancialManagementIncomeExpenseMonthly(year);
	}
	
	//Finansman yonetimi gelir gider butcesi Gun bazinda
	@RequestMapping(value = "finansmanYonetimiGelirGiderGunBazli/{year}/{month}", method = RequestMethod.GET)
	public List<FinansmanYonetimiGelirGiderAylik> getFinancialManagementIncomeExpenseDaily(@PathVariable("year") long year,@PathVariable("month") long month) throws Throwable{
		System.out.println("----------- finansman Yonetimi gelir gider gunluk bazinda----------");
		return managementService.getFinancialManagementIncomeExpenseDaily(year,month);
	}
	
	//Finansman yonetimi gelir butcesi/gerceklesen 
	@RequestMapping(value = "finansmanYonetimiGelirButcesiGerceklesme", method = RequestMethod.GET)
	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncomeBudget() throws Throwable{
		System.out.println("----------- finansman Yonetimi gider butcesi/gerceklesen----------");
		return managementService.getFinancialManagementIncomeBudget();
	}
	
	//Finansman yonetimi gider butcesi/gerceklesen 
	@RequestMapping(value = "finansmanYonetimiGiderButcesiGerceklesme", method = RequestMethod.GET)
	public List<FinansmanYonetimiGelirGider> getFinancialManagementExpenseBudget() throws Throwable{
		System.out.println("----------- finansman Yonetimi gider butcesi/gerceklesen----------");
		return managementService.getFinancialManagementExpenseBudget();
	}
	
	//Finansman yonetimi tahakkuk 1. seviye
	@RequestMapping(value = "finansmanYonetimiTahakkukBirinciSeviye/{year}", method = RequestMethod.GET)
	public List<FinansmanYonetimiTahakkuk> getFinancialManagementTahakkukFirstLevel(@PathVariable("year") long year) throws Throwable{
		System.out.println("----------- finansman Yonetimi tahakkuk 1. seviye----------");
		return managementService.getFinancialManagementTahakkukFirstLevel(year);
	}
	
	//Finansman yonetimi tahakkuk ikinci seviye
	@RequestMapping(value = "finansmanYonetimiTahakkukIkinciSeviye/{kod}", method = RequestMethod.GET)
	public List<FinansmanYonetimiTahakkuk> getFinancialManagementTahakkukSecondLevel(@PathVariable("kod") String kod) throws Throwable{
		System.out.println("----------- finansman Yonetimi tahakkuk ikinci seviye----------");
		return managementService.getFinancialManagementTahakkukOtherLevels(kod,2);
	}
	
	//Finansman yonetimi tahakkuk ucuncu seviye
	@RequestMapping(value = "finansmanYonetimiTahakkukUcuncuSeviye/{kod}", method = RequestMethod.GET)
	public List<FinansmanYonetimiTahakkuk> getFinancialManagementTahakkukThirdLevel(@PathVariable("kod") String kod) throws Throwable{
		System.out.println("----------- finansman Yonetimi tahakkuk ucuncu seviye----------");
		return managementService.getFinancialManagementTahakkukOtherLevels(kod,3);
	}
	
	//Finansman yonetimi tahakkuk dorduncu seviye
	@RequestMapping(value = "finansmanYonetimiTahakkukDorduncuSeviye/{kod}", method = RequestMethod.GET)
	public List<FinansmanYonetimiTahakkuk> getFinancialManagementTahakkukFourthLevel(@PathVariable("kod") String kod) throws Throwable{
		System.out.println("----------- finansman Yonetimi tahakkuk dorduncu seviye----------");
		return managementService.getFinancialManagementTahakkukOtherLevels(kod,4);
	}
	
	//Gayrimenkul Yonetimi
	@RequestMapping(value = "gayrimenkulYonetimiYoneticiPaneli", method = RequestMethod.GET)
	public List<GraphGeneral> getRealEstateManagement() throws Throwable{
		System.out.println("----------- gayrimenkul yonetimi ----------");
		return managementService.getRealEstateManagement();
	}
	
	//Gayrimenkul Yonetimi Detay
	@RequestMapping(value = "gayrimenkulYonetimiYoneticiPaneliDetay/{id}", method = RequestMethod.GET)
	public List<GraphGeneral> getRealEstateManagementDetail(@PathVariable("id")long id) throws Throwable{
		System.out.println("----------- gayrimenkul yonetimi ----------");
		System.out.println(id);
		return managementService.getRealEstateManagementDetail(id);
	}
	
	//Nikah yonetici paneli
	@RequestMapping(value = "hizmetYonetimiNikahYoneticiPaneli", method = RequestMethod.GET)
	public List<GraphGeneral> getWeddingManagement() throws Throwable{
		System.out.println("----------- nikah yonetimi ----------");
		return managementService.getWeddingManagement();
	}
	
	//Nikah yonetici paneli ay bazinda
	@RequestMapping(value = "hizmetYonetimiNikahYoneticiPaneliAylik/{year}", method = RequestMethod.GET)
	public List<GraphGeneral> getWeddingManagementMonthly(@PathVariable("year") long year) throws Throwable{
		System.out.println("----------- nikah yonetimi aylik----------");
		System.out.println(year);
		return managementService.getWeddingManagementMonthly(year);
	}
	
	//Nikah yonetici paneli gun bazinda
	@RequestMapping(value = "hizmetYonetimiNikahYoneticiPaneliGunluk/{year}/{month}", method = RequestMethod.GET)
	public List<GraphGeneral> getWeddingManagementDaily(@PathVariable("year") long year,@PathVariable("month") long month) throws Throwable{
		System.out.println("----------- nikah yonetimi gunluk----------");
		System.out.println(year);
		return managementService.getWeddingManagementDaily(year,month);
	}
	
	//Nikah yonetici paneli gun bazinda list
	@RequestMapping(value = "hizmetYonetimiNikahYoneticiPaneliGunluk/{year}/{month}/{day}", method = RequestMethod.GET)
	public List<YbsWeddingList> getWeddingManagementDailyList(@PathVariable("year") long year, @PathVariable("month") long month, @PathVariable("day") long day) throws Throwable{
		System.out.println("----------- nikah yonetimi gunluk liste----------");
		System.out.println(year);
		return managementService.getWeddingManagementDailyList(year,month,day);
	}
	
	//saglik yonetici paneli
	@RequestMapping(value = "hizmetYonetimiSaglikYoneticiPaneli", method = RequestMethod.GET)
	public List<GraphGeneral> getHealthManagement() throws Throwable{
		System.out.println("----------- saglik yonetimi ----------");
		return managementService.getHealthManagement();
	}
	
	//saglik yonetici paneli ay bazinda
	@RequestMapping(value = "hizmetYonetimiSaglikYoneticiPaneliAylik/{year}", method = RequestMethod.GET)
	public List<GraphGeneral> getHealthManagementMonthly(@PathVariable("year") long year) throws Throwable{
		System.out.println("----------- saglik yonetimi aylik----------");
		System.out.println(year);
		return managementService.getHealthManagementMonthly(year);
	}
	
	//saglik yonetici paneli gun bazinda
	@RequestMapping(value = "hizmetYonetimiSaglikYoneticiPaneliGunluk/{year}/{month}", method = RequestMethod.GET)
	public List<GraphGeneral> getHealthManagementDaily(@PathVariable("year") long year,@PathVariable("month") long month) throws Throwable{
		System.out.println("----------- saglik yonetimi gunluk----------");
		System.out.println(year);
		return managementService.getHealthManagementDaily(year,month);
	}
	
	//Insan kaynaklari personel yonetici paneli tip
	@RequestMapping(value = "insanKaynaklariPersonelYoneticiPaneliTip", method = RequestMethod.GET)
	public List<InsanKaynaklari> getHumanResourcesType() throws Throwable{
		System.out.println("----------- insan kaynaklari tip ----------");
		return managementService.getHumanResourcesType();
	}
	
	//Insan kaynaklari personel yonetici paneli tip
	@RequestMapping(value = "insanKaynaklariPersonelYoneticiPaneliTipMudurluk/{type}", method = RequestMethod.GET)
	public List<InsanKaynaklari> getHumanResourcesTypeMudurluk(@PathVariable("type")String type) throws Throwable{
		System.out.println("----------- insan kaynaklari tip mudurluk ----------");
		return managementService.getHumanResourcesTypeMudurluk(type);
	}
	
	//Insan kaynaklari personel yonetici paneli cinsiyet
	@RequestMapping(value = "insanKaynaklariPersonelYoneticiPaneliCinsiyet", method = RequestMethod.GET)
	public List<InsanKaynaklari> getHumanResourcesGender() throws Throwable{
		System.out.println("----------- insan kaynaklari cinsiyet ----------");
		return managementService.getHumanResourcesGender();
	}
	
	//Insan kaynaklari personel yonetici paneli cinsiyet mudurluk bazinda
	@RequestMapping(value = "insanKaynaklariPersonelYoneticiPaneliCinsiyetMudurluk/{gender}", method = RequestMethod.GET)
	public List<InsanKaynaklari> getHumanResourcesGenderMudurluk(@PathVariable("gender")String gender) throws Throwable{
		System.out.println("----------- insan kaynaklari cinsiyet mudurluk ----------");
		return managementService.getHumanResourcesGenderMudurluk(gender);
	}
	
	//Insan kaynaklari personel yonetici paneli yas
	@RequestMapping(value = "insanKaynaklariPersonelYoneticiPaneliYas", method = RequestMethod.GET)
	public List<InsanKaynaklari> getHumanResourcesAge() throws Throwable{
		System.out.println("----------- insan kaynaklari yas ----------");
		return managementService.getHumanResourcesAge();
	}
	
	//Insan kaynaklari personel yonetici paneli meslek
	@RequestMapping(value = "insanKaynaklariPersonelYoneticiPaneliMeslek", method = RequestMethod.GET)
	public List<InsanKaynaklari> getHumanResourcesJob() throws Throwable{
		System.out.println("----------- insan kaynaklari meslek ----------");
		return managementService.getHumanResourcesJob();
	}
	
	//Toplanti yonetimi
	@RequestMapping(value = "toplantiYonetimiYoneticiPaneli", method = RequestMethod.GET)
	public List<ToplantiYonetimi> getMeetingManagement() throws Throwable{
		System.out.println("----------- toplanti Yonetimi ----------");
		return managementService.getMeetingManagement();
	}
	
	//Toplanti yonetimi ay bazinda
	@RequestMapping(value = "toplantiYonetimiYoneticiPaneli/{year}", method = RequestMethod.GET)
	public List<ToplantiYonetimi> getMeetingManagementMonthly(@PathVariable("year") long year) throws Throwable{
		System.out.println("----------- toplanti Yonetimi ----------");
		return managementService.getMeetingManagementMonthly(year);
	}
	
	//Belge yonetici paneli 
	@RequestMapping(value = "belgeYonetimiBelgeYoneticiPaneli/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<BelgeYonetim> getDocumentManagement(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("----------- belge yonetimi ----------");
		return managementService.getDocumentManagement(startDate,endDate);
	}
	
	//Belge yonetici paneli mudurluk bazinda
	@RequestMapping(value = "belgeYonetimiBelgeYoneticiPaneliMudurluk/{startDate}/{endDate}/{tur}", method = RequestMethod.GET)
	public List<BelgeYonetim> getDocumentManagementMudurluk(@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate,@PathVariable("tur") String tur) throws Throwable{
		System.out.println("----------- belge yonetimi Mudurluk----------");
		return managementService.getDocumentManagementMudurluk(startDate,endDate,tur);
	}
	
	//Ruhsat Yonetimi
	@RequestMapping(value = "ruhsatYonetimi", method = RequestMethod.GET)
	public List<GraphGeneral> getLicenseCount() throws Throwable{
		System.out.println("----------- Ruhsat Yonetimi----------");
		return managementService.getLicenseCount();
	}
	
	//Ruhsat Yonetimi Ture Gore
	@RequestMapping(value = "ruhsatYonetimi/{year}", method = RequestMethod.GET)
	public List<GraphGeneral> getLicenseTypeCount(@PathVariable("year") long year) throws Throwable{
		System.out.println("----------- Ruhsat Yonetimi----------");
		return managementService.getLicenseTypeCount(year);
	}
	
	//Ruhsat Yonetimi Ture Gore Ay Bazinda
	@RequestMapping(value = "ruhsatYonetimi/{year}/{id}", method = RequestMethod.GET)
	public List<GraphGeneral> getLicenseTypeMonthlyCount(@PathVariable("year") long year,@PathVariable("id") long id) throws Throwable{
		System.out.println("----------- Ruhsat Yonetimi----------");
		return managementService.getLicenseTypeMonthlyCount(year,id);
	}
	
	//Ruhsat Yonetimi Ture Gore Ay Bazinda
	@RequestMapping(value = "ruhsatYonetimi/{year}/{month}/{id}", method = RequestMethod.GET)
	public List<GraphGeneral> getLicenseTypeDailyCount(@PathVariable("year") long year,@PathVariable("month") long month,@PathVariable("id") long id) throws Throwable{
		System.out.println("----------- Ruhsat Yonetimi----------");
		return managementService.getLicenseTypeDailyCount(year,month,id);
	}
	
	//Ruhsat Yonetimi Liste
	@RequestMapping(value = "ruhsatYonetimi/{year}/{month}/{day}/{id}", method = RequestMethod.GET)
	public List<YbsLicenseList> getLicenseTypeDailyList(@PathVariable("year") long year, @PathVariable("month") long month, @PathVariable("day") long day, @PathVariable("id") long id) throws Throwable{
		System.out.println("----------- Ruhsat Yonetimi----------");
		return managementService.getLicenseTypeDailyList(year,month,day,id);
	}
	
	//Gunun Ozeti
	@RequestMapping(value = "gununOzeti", method = RequestMethod.GET)
	public List<YbsGununOzeti> getDailySummary() throws Throwable{
		System.out.println("----------- Gunun Ozeti----------");
		return managementService.getDailySummary();
	}
	
	//Vezne bazinda tahsilat 
	@RequestMapping(value = "vezneBazindaTahsilat/{persid}/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<VezneTahsilat> getPayOfficeCollection(@PathVariable("persid") long persid, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("----------- vezne Bazinda Tahsilat ----------");
		return managementService.getPayOfficeCollection(persid,startDate,endDate);
	}
	
	//Vezne bazinda tahsilat detay
	@RequestMapping(value = "vezneBazindaTahsilatDetay/{id}/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<VezneTahsilatDetay> getPayOfficeCollectionDetail(@PathVariable("id") long id, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("----------- vezne Bazinda Tahsilat ----------");
		return managementService.getPayOfficeCollectionDetail(id,startDate,endDate);
	}
	
	//lokasyon bazinda tahsilat 
	@RequestMapping(value = "lokasyonBazindaTahsilat/{persid}/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<VezneTahsilat> getLocationCollection(@PathVariable("persid") long persid,@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) throws Throwable{
		System.out.println("----------- vezne Bazinda Tahsilat ----------");
		return managementService.getLocationCollection(persid,startDate,endDate);
	}
	
	//Gunun Ozeti Gelirler Yonetimi Tahakkuk
	@RequestMapping(value = "gununOzetiGelirlerYonetimiTahakkuk/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryIncomeManagementTahakkuk(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti gelirler yonetimi tahakkuk---------");
		return managementService.getDailySummaryIncomeManagementTahakkuk(requestDate);
	}
	
	//Gunun Ozeti Gelirler Yonetimi tahsilat
	@RequestMapping(value = "gununOzetiGelirlerYonetimiTahsilat/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryIncomeManagementTahsilat(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti gelirler yonetimi tahsilat---------");
		return managementService.getDailySummaryIncomeManagementTahsilat(requestDate);
	}
	
	//Gunun Ozeti Gelirler Yonetimi emlak beyani
	@RequestMapping(value = "gununOzetiGelirlerYonetimiEmlakBeyani/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryIncomeManagementEmlakBeyani(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti gelirler yonetimi emlak beyani---------");
		return managementService.getDailySummaryIncomeManagementEmlakBeyani(requestDate);
	}
	
	//Gunun Ozeti Gelirler Yonetimi cevre beyani
	@RequestMapping(value = "gununOzetiGelirlerYonetimiCevreBeyani/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryIncomeManagementCevreBeyani(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti gelirler yonetimi cevre Beyani---------");
		return managementService.getDailySummaryIncomeManagementCevreBeyani(requestDate);
	}
	
	//Gunun Ozeti Gelirler Yonetimi reklam beyani
	@RequestMapping(value = "gununOzetiGelirlerYonetimiReklamBeyani/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryIncomeManagementReklamBeyani(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti gelirler yonetimi reklam Beyani---------");
		return managementService.getDailySummaryIncomeManagementReklamBeyani(requestDate);
	}
	
	//Gunun Ozeti Finansman Yonetimi bankadaki para
	@RequestMapping(value = "gununOzetiFinansmanYonetimiBanka/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryFinancialManagementBanka(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti finansal yonetim banka---------");
		return managementService.getDailySummaryFinancialManagementBanka(requestDate);
	}
	
	//Gunun Ozeti Finansman Yonetimi firmalarin borcu
	@RequestMapping(value = "gununOzetiFinansmanYonetimiFirmaBorcu/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryFinancialManagementFirmaBorcu(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti finansal yonetim firma borcu---------");
		return managementService.getDailySummaryFinancialManagementFirmaBorcu(requestDate);
	}
	
	//Gunun Ozeti Finansman Yonetimi vergi borcu
	@RequestMapping(value = "gununOzetiFinansmanYonetimiVergiBorcu/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryFinancialManagementVergiBorcu(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti finansal yonetim vergi borcu---------");
		return managementService.getDailySummaryFinancialManagementVergiBorcu(requestDate);
	}
	
	//Gunun Ozeti Finansman Yonetimi Sosyal G�venlik borcu
	@RequestMapping(value = "gununOzetiFinansmanYonetimiSosyalGuvenlikBorcu/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryFinancialManagementSosyalGuvenlikBorcu(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti finansal yonetim sosyal guvenlik borcu---------");
		return managementService.getDailySummaryFinancialManagementSosyalGuvenlikBorcu(requestDate);
	}
	
	//Gunun Ozeti Finansman Yonetimi toplam Borc
	@RequestMapping(value = "gununOzetiFinansmanYonetimiToplamBorc/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryFinancialManagementToplamBorc(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti finansal yonetim toplam borc---------");
		return managementService.getDailySummaryFinancialManagementToplamBorc(requestDate);
	}
	
	//Gunun Ozeti Belge Yonetimi alinan evrak
	@RequestMapping(value = "gununOzetiDokumanYonetimiEvrak/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryDocumentManagementEvrak(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti belge yonetimi alinan evrak---------");
		return managementService.getDailySummaryDocumentManagementEvrak(requestDate);
	}
	
	//Gunun Ozeti Belge Yonetimi alinan dilekce
	@RequestMapping(value = "gununOzetiDokumanYonetimiDilekce/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryDocumentManagementDilekce(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti belge yonetimi alinan dilekce---------");
		return managementService.getDailySummaryDocumentManagementDilekce(requestDate);
	}
	
	//Gunun Ozeti Belge Yonetimi alinan sikayet
	@RequestMapping(value = "gununOzetiDokumanYonetimiSikayet/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryDocumentManagementSikayet(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti belge yonetimi alinan sikayet---------");
		return managementService.getDailySummaryDocumentManagementSikayet(requestDate);
	}
	
	//Gunun Ozeti Insan Kaynaklari Yonetimi calisan sayisi
	@RequestMapping(value = "gununOzetiHumanResourcesCounts", method = RequestMethod.GET)
	public List<PersonelBilgileri> getDailySummaryHumanResourcesCounts(){
		System.out.println("----------gunun ozeti belge yonetimi alinan sikayet---------");
		return managementService.getDailySummaryHumanResourcesCounts();
	}
	
	//Gunun Ozeti Ruhsat Kaynaklari Yonetimi bina sayisi
	@RequestMapping(value = "gununOzetiGayrimenkulYonetimiBina", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryRealEstateManagementBina(){
		System.out.println("----------gunun ozeti ruhsat yonetimi bina sayisi---------");
		return managementService.getDailySummaryRealEstateManagementBina();
	}
	
	//Gunun Ozeti Ruhsat Kaynaklari Yonetimi numarataj sayisi
	@RequestMapping(value = "gununOzetiGayrimenkulYonetimiNumarataj", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryRealEstateManagementNumarataj(){
		System.out.println("----------gunun ozeti ruhsat yonetimi bina sayisi---------");
		return managementService.getDailySummaryRealEstateManagementNumarataj();
	}
	
	//Gunun Ozeti Ruhsat Kaynaklari Yonetimi bina sayisi
	@RequestMapping(value = "gununOzetiGayrimenkulYonetimiBagimsizBolum", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryRealEstateManagementBagimsizBolum(){
		System.out.println("----------gunun ozeti ruhsat yonetimi bagimsiz bolum sayisi---------");
		return managementService.getDailySummaryRealEstateManagementBagimsizBolum();
	}
	
	//Gunun ozeti ruhsat yonetimi ruhsat basvurusu sayisi
	@RequestMapping(value = "gununOzetiRuhsatYonetimiRuhsatBasvurusu/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryLicenseManagementRuhsatBasvurusu(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti ruhsat yonetimi ruhsat basvurusu---------");
		return managementService.getDailySummaryLicenseManagementRuhsatBasvurusu(requestDate);
	}
	
	//Gunun ozeti ruhsat yonetimi verilen ruhsat sayisi 
	@RequestMapping(value = "gununOzetiRuhsatYonetimiRuhsatVerilen/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryLicenseManagementVerilenRuhsat(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti ruhsat yonetimi ruhsat basvurusu---------");
		return managementService.getDailySummaryLicenseManagementVerilenRuhsat(requestDate);
	}
	
	//Gunun ozeti hizmet yonetimi nikah sayisi 
	@RequestMapping(value = "gununOzetiHizmetYonetimiNikah/{requestDate}", method = RequestMethod.GET)
	public List<GununOzeti> getDailySummaryServiceManagementNikah(@PathVariable("requestDate")String requestDate){
		System.out.println("----------gunun ozeti hizmet yonetimi nikah sayisi---------");
		return managementService.getDailySummaryServiceManagementNikah(requestDate);
	}
	
	//tahakkuk detay 
	@RequestMapping(value = "gelirlerYonetimiTahakkuk/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GelirlerYonetimiTahakkuk> getIncomeManagementTahakkukDetail(@PathVariable("startDate")String startDate, @PathVariable("endDate")String endDate){
		System.out.println("----------gunun ozeti gelirler yonetimi tahakkuk---------");
		return managementService.getIncomeManagementTahakkukDetail(startDate,endDate);
	}
	
	//emlak beyani detay 
	@RequestMapping(value = "gelirlerYonetimiEmlakBeyani/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GelirlerYonetimiEmlakBeyani> getIncomeManagementEmlakBeyani(@PathVariable("startDate")String startDate, @PathVariable("endDate")String endDate){
		System.out.println("----------gunun ozeti gelirler yonetimi emlak beyani---------");
		return managementService.getIncomeManagementEmlakBeyani(startDate,endDate);
	}
	
	//cevre beyani detay 
	@RequestMapping(value = "gelirlerYonetimiCevreBeyani/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GelirlerYonetimiCevreBeyani> getIncomeManagementCevreBeyani(@PathVariable("startDate")String startDate, @PathVariable("endDate")String endDate){
		System.out.println("----------gunun ozeti gelirler yonetimi cevre beyani---------");
		return managementService.getIncomeManagementCevreBeyani(startDate,endDate);
	}
	
	//reklam beyani detay 
	@RequestMapping(value = "gelirlerYonetimiReklamBeyani/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<GelirlerYonetimiReklamBeyani> getIncomeManagementReklamBeyani(@PathVariable("startDate")String startDate, @PathVariable("endDate")String endDate){
		System.out.println("----------gunun ozeti gelirler yonetimi reklam beyani---------");
		return managementService.getIncomeManagementReklamBeyani(startDate,endDate);
	}
	
}
