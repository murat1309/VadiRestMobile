package com.digikent.vadirest.service;

import com.digikent.vadirest.dto.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface ManagementService {
	// banka borc durumu
	public List<BankaDurumu> getBankStatus(long id, long year);
	//banka borc durumu hesap plani bazinda
	public List<BankaDurumuDetay> getBankStatusDetail(long id, long accountId, long year, String startDate, String endDate);
	//personel bilgileri
	public List<PersonelBilgileri> getStaffInfomation();
	//personel kadro
	public List<PersonelBilgileri> getPersonelKadroInformation();
	//kurum personel detay
	public List<PersonelBilgileriDetay> getStaffDetail(long servisGorevId, char turu);
	//kurum personel kadro detay
	public List<PersonelBilgileriDetay> getKadroDetay(long servisKadroId);
	//personel grubu
	public List<PersonelGrup> getStaffGroup();
	//ise baslayanlar
	public List<PersonelBilgileriDetay> getJobStarters(String startDate, String endDate);
	//isten ayrilanlar
	public List<PersonelBilgileriDetay> getJobQuitters(String startDate, String endDate);
	//Kurum borc durumu
	public List<KurumBorc> getDebtStatus(long id, long year);
	//Yapilan odemeler
	public List<YapilanOdemeler> getPayments(String startDate, String endDate) throws Throwable;
	//tum odemeler
	public List<FirmaOdeme> getAllPayments(long year, String startDate, String endDate, long getPayments);
	//mudurluk bazinda basvuru
	public List<Basvuru> getApplyCount(String startDate, String endDate) throws Throwable;
	//Basvuru sayisi ozet
	public List<BasvuruOzet> getApplySummary(long birimId, String startDate, String endDate)throws Throwable;
	//Basvuru Ozet detay
	public BasvuruOzetDetay getApplySummaryDetail(long basvuruNo);
	//Firma borc durumu
	public List<FirmaBorc> getFirmDebtStatus(long persid, long year);
	//Firma Alacak durumu
	public List<FirmaAlacak> getFirmaAlacak(long year, long personelId);
	//Firma Odeme durumu
	public List<FirmaOdeme> getFirmaOdeme(long year, String startDate, String endDate);
	//Konu bazinda talep sayisi
	public List<Talep> getRequestCount(String timePeriod) throws Throwable;
	//gelir grubu
	public List<GelirGrubu> getIncomeGroup(String timePeriod) throws Throwable;
	//gelir grubu tahsilat bazinda
	public List<GelirGrubuDetay> getIncomeGroupDetail(long id, String startDate, String endDate);
	//gelir turu
	public List<GelirTuru> getIncomeType(String timePeriod) throws Throwable;
	//gelir turu
	public List<GelirTuru> getIncomeType(String startDate, String endDate) throws Throwable;
	//gelir turu tahsilat bazinda
	public List<GelirTuruDetay> getIncomeTypeDetail(long id, String startDate, String endDate);
	//YBS menu Ng tree
	public List<YbsMenu> getYbsMenuNgTree(String userName);
	//gunluk gelir grubu
	public List<GraphGeneral> getIncomeGroupDaily();
	//gelir grubu tarih araligi
	public List<GraphGeneral> getIncomeGroup(String startDate, String endDate);
	//gelir grubu bazli gelir turu
	public List<GraphGeneral> getIncomeType(long gelirGrubuId, String startDate, String endDate);
	//gelirler yonetimi vezne
	public List<GraphGeneral> getIncomeManagementPayOffice(String startDate, String endDate);
	// gelirler yonetimi odemeler
	public List<GraphGeneral> getIncomeManagementPayment(String startDate, String endDate);
	//gelirler yonetimi bildirim sayisi
	public List<GraphGeneral> getIncomeManagementNotification(long year);
	//gelirler yonetimi bildirim sayisi mahalle bazli
	public List<GraphGeneral> getIncomeManagementNotificationDetail(long id, long year);
	//gelirler yonetimi mahalle
	public List<GraphGeneral> getIncomeManagementStreet(String tur, long year);
	//gelirler yonetimi mahalle paydas bazinda
	public List<GelirlerYonetimiMahalle> getIncomeManagementStreetDetail(long id, String startDate, String endDate);
	//gelirler yonetimi odeme yapan mukellef 
	public List<GraphGeneral> getIncomeManagementTaxPayer(String startDate, String endDate);
	//finansman yonetimi gelir gider 
	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncomeExpense();
	//finansman yonetimi gelir
	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncome(long year, long personelId);
	//finansman yonetimi gider
	public List<FinansmanYonetimiGelirGider> getFinancialManagementExpense(long year, long personelId);
	//finansman yonetimi gelir gider ay bazinda
	public List<FinansmanYonetimiGelirGiderAylik> getFinancialManagementIncomeExpenseMonthly(long year);
	//finansman yonetimi gelir gider gun bazinda
	public List<FinansmanYonetimiGelirGiderAylik> getFinancialManagementIncomeExpenseDaily(long year, long month);
	//finansman yonetimi gelir butcesi/gerceklesen
	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncomeBudget();
	//finansman yonetimi gider butcesi/gerceklesen
	public List<FinansmanYonetimiGelirGider> getFinancialManagementExpenseBudget();
	//finansman yonetimi 1. derece tahakkuk
	public List<FinansmanYonetimiTahakkuk> getFinancialManagementTahakkukFirstLevel(long year);
	//finansman yonetimi diger seviyeler tahakkuk
	public List<FinansmanYonetimiTahakkuk> getFinancialManagementTahakkukOtherLevels(String kod, int level);
	//gayrimenkul yonetimi yonetici paneli
	public List<GraphGeneral> getRealEstateManagement();
	//gayrimenkul yonetimi yonetici paneli detay
	public List<GraphGeneral> getRealEstateManagementDetail(long id);
	//nikah yonetimi yonetici paneli
	public List<GraphGeneral> getWeddingManagement();
	//nikah yonetimi yonetici paneli aylik
	public List<GraphGeneral> getWeddingManagementMonthly(long year);
	//nikah yonetimi yonetici paneli gunluk
	public List<GraphGeneral> getWeddingManagementDaily(long year, long month);
	//nikah yonetimi yonetici paneli gunluk liste
	public List<YbsWeddingList> getWeddingManagementDailyList(long year, long month, long day);
	//saglik yonetimi yonetici paneli
	public List<GraphGeneral> getHealthManagement();
	//saglik yonetimi yonetici paneli aylik
	public List<GraphGeneral> getHealthManagementMonthly(long year);
	//saglik yonetimi yonetici paneli gunluk
	public List<GraphGeneral> getHealthManagementDaily(long year, long month);
	//insan kaynaklari tipe gore
	public List<InsanKaynaklari> getHumanResourcesType();
	//insan kaynaklari tipe gore mudurluk bazinda
	public List<InsanKaynaklari> getHumanResourcesTypeMudurluk(String type);
	//insan kaynaklari cinsiyete gore
	public List<InsanKaynaklari> getHumanResourcesGender();
	//insan kaynaklari cinsiyete gore mudurluk bazinda
	public List<InsanKaynaklari> getHumanResourcesGenderMudurluk(String gender);
	//insan kaynaklari meslege gore
	public List<InsanKaynaklari> getHumanResourcesJob();
	//insan kaynaklari yasa gore
	public List<InsanKaynaklari> getHumanResourcesAge();
	//toplanti yonetimi 
	public List<ToplantiYonetimi> getMeetingManagement();
	//toplanti yonetimi ay bazinda
	public List<ToplantiYonetimi> getMeetingManagementMonthly(long year);
	//belge yonetim yonetici paneli
	public List<BelgeYonetim> getDocumentManagement(String startDate, String endDate);
	//belge yonetim yonetici paneli mudurluk bazinda
	public List<BelgeYonetim> getDocumentManagementMudurluk(String startDate, String endDate, String tur);
	//Ruhsat yonetim yonetici paneli
	public List<GraphGeneral> getLicenseCount();
	//Ruhsat yonetim yonetici paneli ture gore
	public List<GraphGeneral> getLicenseTypeCount(long year);
	//Ruhsat yonetim yonetici paneli ture gore aylik
	public List<GraphGeneral> getLicenseTypeMonthlyCount(long year, long id);
	//Ruhsat yonetim yonetici paneli ture gore gunluk
	public List<GraphGeneral> getLicenseTypeDailyCount(long year, long month, long id);
	//Ruhsat yonetimi liste(gune gore)
	public List<YbsLicenseList> getLicenseTypeDailyList(long year, long month, long day, long id);
	//Ybs Gunun Ozeti
	public List<YbsGununOzeti> getDailySummary();
	//Ybs vezne bazinda tahsilat 
	public List<VezneTahsilat> getPayOfficeCollection(long persid, String startDate, String endDate);
	//Ybs vezne bazinda tahsilat detay
	public List<VezneTahsilatDetay> getPayOfficeCollectionDetail(long id, String startDate, String endDate);
	//Ybs lokasyon bazinda tahsilat
	public List<VezneTahsilat> getLocationCollection(long persid, String startDate, String endDate);
	//Gunun ozeti gelirler yonetimi tahakkuk
	public List<GununOzeti> getDailySummaryIncomeManagementTahakkuk(String requestDate);
	//Gunun ozeti gelirler yonetimi tahsilat
	public List<GununOzeti> getDailySummaryIncomeManagementTahsilat(String requestDate);
	//Gunun ozeti gelirler yonetimi emlak beyani
	public List<GununOzeti> getDailySummaryIncomeManagementEmlakBeyani(String requestDate);
	//Gunun ozeti gelirler yonetimi cevre beyani
	public List<GununOzeti> getDailySummaryIncomeManagementCevreBeyani(String requestDate);
	//Gunun ozeti gelirler yonetimi reklam beyani
	public List<GununOzeti> getDailySummaryIncomeManagementReklamBeyani(String requestDate);
	//Gunun ozeti finansman yonetimi banka borcu
	public List<GununOzeti> getDailySummaryFinancialManagementBanka(String requestDate);
	//Gunun ozeti finansman yonetimi firma borcu
	public List<GununOzeti> getDailySummaryFinancialManagementFirmaBorcu(String requestDate);
	//Gunun ozeti finansman yonetimi vergi borcu
	public List<GununOzeti> getDailySummaryFinancialManagementVergiBorcu(String requestDate);
	//Gunun ozeti finansman yonetimi sosyal guvenlik borcu
	public List<GununOzeti> getDailySummaryFinancialManagementSosyalGuvenlikBorcu(String requestDate);
	//Gunun ozeti finansman yonetimi toplam borc
	public List<GununOzeti> getDailySummaryFinancialManagementToplamBorc(String requestDate);
	//Gunun ozeti belge yonetimi alinan evrak
	public List<GununOzeti> getDailySummaryDocumentManagementEvrak(String requestDate);
	//Gunun ozeti belge yonetimi alinan dilekce
	public List<GununOzeti> getDailySummaryDocumentManagementDilekce(String requestDate);
	//Gunun ozeti belge yonetimi alinan sikayet
	public List<GununOzeti> getDailySummaryDocumentManagementSikayet(String requestDate);
	//Gunun ozeti insan kaynaklari calisan sayilari
	public List<PersonelBilgileri> getDailySummaryHumanResourcesCounts();
	//Gunun ozeti ruhsat yonetimi bina sayisi
	public List<GununOzeti> getDailySummaryRealEstateManagementBina();
	//Gunun ozeti ruhsat yonetimi numarataj sayisi
	public List<GununOzeti> getDailySummaryRealEstateManagementNumarataj();
	//Gunun ozeti ruhsat yonetimi bagimsiz bolum sayisi
	public List<GununOzeti> getDailySummaryRealEstateManagementBagimsizBolum();
	//Gunun ozeti ruhsat yonetimi ruhsat basvurusu sayisi
	public List<GununOzeti> getDailySummaryLicenseManagementRuhsatBasvurusu(String requestDate);
	//Gunun ozeti ruhsat yonetimi verilen ruhsat sayisi
	public List<GununOzeti> getDailySummaryLicenseManagementVerilenRuhsat(String requestDate);
	//Gunun ozeti hizmet yonetimi nikah sayisi
	public List<GununOzeti> getDailySummaryServiceManagementNikah(String requestDate);
	//Gelirler yonetimi tahakkuk detay
	public List<GelirlerYonetimiTahakkuk> getIncomeManagementTahakkukDetail(String startDate, String endDate);
	//gelirler yonetimi emlak beyani
	public List<GelirlerYonetimiEmlakBeyani> getIncomeManagementEmlakBeyani(String startDate, String endDate);
	//gelirler yonetimi cevre beyani
	public List<GelirlerYonetimiCevreBeyani> getIncomeManagementCevreBeyani(String startDate, String endDate);
	//gelirler yonetimi reklam beyani
	public List<GelirlerYonetimiReklamBeyani> getIncomeManagementReklamBeyani(String startDate, String endDate);
}
