package com.digikent.vadirest.service.impl;

import com.digikent.vadirest.dao.ManagementDAO;
import com.digikent.vadirest.dto.*;
import com.digikent.vadirest.service.ManagementService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;


@Service("managementService")
@Transactional
public class ManagementServiceImpl implements ManagementService {

	@Autowired(required=true)
	private ManagementDAO managementDAO;

	public List<BankaDurumu> getBankStatus(long id, long year) {
		return managementDAO.getBankStatus(id,year);
		
	}
	
	public List<BankaDurumuDetay> getBankStatusDetail(long id, long accountId, long year, String startDate, String endDate){
		return managementDAO.getBankStatusDetail(id,accountId,year,startDate,endDate);
	}

	public List<PersonelBilgileri> getStaffInfomation() {
		return managementDAO.getStaffInfomation();
	}

	public List<PersonelBilgileri> getPersonelKadroInformation() {
		return managementDAO.getPersonelKadroInformation();
	}
	
	public List<PersonelBilgileriDetay> getStaffDetail(long servisGorevId, char turu){
		 return managementDAO.getStaffDetail(servisGorevId, turu);
	}

	public List<PersonelBilgileriDetay> getKadroDetay(long servisKadroId){
		 return managementDAO.getKadroDetay(servisKadroId);
	}
	
	public List<PersonelGrup> getStaffGroup(){
		return managementDAO.getStaffGroup();
	}

	public List<PersonelBilgileriDetay> getJobStarters(String startDate, String endDate){
		return managementDAO.getJobStarters(startDate, endDate);
	}

	public List<PersonelBilgileriDetay> getJobQuitters(String startDate, String endDate){
		return managementDAO.getJobQuitters(startDate, endDate);
	}

	public List<KurumBorc> getDebtStatus(long id, long year) {
		return managementDAO.getDebtStatus(id,year);
	}

	public List<YapilanOdemeler> getPayments(String startDate, String endDate) throws Throwable {
		return managementDAO.getPayments(startDate,endDate);
	}

	public List<FirmaOdeme> getAllPayments(long year, String startDate, String endDate, long personelId){
		return managementDAO.getAllPayments(year, startDate, endDate, personelId);
	}
	
	public List<Basvuru> getApplyCount(String startDate, String endDate) throws Throwable{
		return managementDAO.getApplyCount(startDate, endDate);
	}
	
	public List<BasvuruOzet> getApplySummary(long birimId, String startDate, String endDate) throws Throwable{
		return managementDAO.getApplySummary(birimId,startDate, endDate);
	}
	
	public BasvuruOzetDetay getApplySummaryDetail(long basvuruNo){
		return managementDAO.getApplySummaryDetail(basvuruNo);
	}
	
	public List<FirmaBorc> getFirmDebtStatus(long persid, long year){
		return managementDAO.getFirmDebtStatus(persid,year);
	}

	public List<FirmaAlacak> getFirmaAlacak(long year, long personelId){
		String paymentType = managementDAO.getFirmaAlacakType();
		if(paymentType.equalsIgnoreCase("E"))
			return managementDAO.getFirmaAlacakTypeE(year, personelId);
		else
			return  managementDAO.getFirmaAlacakTypeH(year, personelId);
	}

	public List<FirmaOdeme> getFirmaOdeme(long year, String startDate, String endDate){
		return managementDAO.getFirmaOdeme(year, startDate, endDate);
	}
	
	public List<Talep> getRequestCount(String timePeriod) throws Throwable{
		return managementDAO.getRequestCount(findTimePeriod(timePeriod));
	}
	
	public List<GelirGrubu> getIncomeGroup(String timePeriod) throws Throwable {
		List<GelirGrubu> gelirGrubuList = managementDAO.getIncomeGroup(findTimePeriod(timePeriod));
		List<GelirGrubu> retval = new ArrayList<GelirGrubu>();
		String key;
		double y;
		int length;
		long id;
		if(gelirGrubuList.size() >= 10)
			length = 10;
		else
			length = gelirGrubuList.size();
		for(int i=0; i<length ; i++){
			if(gelirGrubuList.get(i).getKey().length() > 15){
				id = gelirGrubuList.get(i).getId();
				key = gelirGrubuList.get(i).getKey().substring(0, 15);
				y = gelirGrubuList.get(i).getY();
				GelirGrubu gelirGrubu = new GelirGrubu();
				gelirGrubu.setId(id);
				gelirGrubu.setKey(key);
				gelirGrubu.setY(y);
				retval.add(gelirGrubu);
			}
			else{
				id = gelirGrubuList.get(i).getId();
				key = gelirGrubuList.get(i).getKey();
				y = gelirGrubuList.get(i).getY();
				GelirGrubu gelirGrubu = new GelirGrubu();
				gelirGrubu.setId(id);
				gelirGrubu.setKey(key);
				gelirGrubu.setY(y);
				retval.add(gelirGrubu);
			} 
		}
		return retval;
	}

	public List<GelirGrubuDetay> getIncomeGroupDetail(long id, String startDate, String endDate){
		return managementDAO.getIncomeGroupDetail(id,startDate,endDate);
	}
	
	public List<GelirTuru> getIncomeType(String timePeriod) throws Throwable{
		return managementDAO.getIncomeType(findTimePeriod(timePeriod));
	}

	public List<GelirTuru> getIncomeType(String startDate, String endDate) throws Throwable{
		return managementDAO.getIncomeType(startDate, endDate);
	}
	
	public List<GelirTuruDetay> getIncomeTypeDetail(long id, String startDate, String endDate){
		return managementDAO.getIncomeTypeDetail(id,startDate,endDate);
	}
	
	public List<GraphGeneral> getIncomeGroupDaily(){
		return managementDAO.getTodayIncomeGroup();
	}
	
	public List<GraphGeneral> getIncomeGroup(String startDate,String endDate){
		return managementDAO.getIncomeGroup(startDate,endDate);
	}
	
	public List<GraphGeneral> getIncomeType(long gelirGrubuId,String startDate,String endDate){
		return managementDAO.getIncomeType(gelirGrubuId, startDate, endDate);
	}

	public List<YbsMenu> getYbsMenuNgTree(String userName){
		List<YbsMenu> parents = managementDAO.getYbsParents(userName,Long.valueOf(-1));
		for(int i=0 ; i<parents.size() ; i++){
			List<YbsMenu> children = managementDAO.getYbsParents(userName, Long.parseLong(parents.get(i).getId()));
			parents.get(i).setNodes(children);
		}
		return parents;
	}
	
	public List<GraphGeneral> getIncomeManagementPayOffice(String startDate,String endDate){
		return managementDAO.getIncomeManagementPayOffice(startDate,endDate);
	}
	
	public List<GraphGeneral> getIncomeManagementPayment(String startDate,String endDate){
		return managementDAO.getIncomeManagementPayment(startDate,endDate);
	}
	
	public List<GraphGeneral> getIncomeManagementNotification(long year){
		return managementDAO.getIncomeManagementNotification(year);
	}
	
	public List<GraphGeneral> getIncomeManagementNotificationDetail(long id,long year){
		return managementDAO.getIncomeManagementNotificationDetail(id,year);
	}
	
	public List<GraphGeneral> getIncomeManagementStreet(String tur,long year){
		List<GraphGeneral> retval = new ArrayList<GraphGeneral>();
		if(tur.equals("tahakkuk")){
			retval = managementDAO.getIncomeManagementStreetTahakkuk(year);
		}
		else{
			retval = managementDAO.getIncomeManagementStreetBorc(year);
		}
		return retval;
	}
	
	public List<GelirlerYonetimiMahalle> getIncomeManagementStreetDetail(long id, String startDate, String endDate){
		return managementDAO.getIncomeManagementStreetDetail(id,startDate,endDate);
	}
	
	public List<GraphGeneral> getIncomeManagementTaxPayer(String startDate,String endDate){
		return managementDAO.getIncomeManagementTaxPayer(startDate,endDate);
	}
	
	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncomeExpense(){
		return managementDAO.getFinancialManagementIncomeExpense();
	}

	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncome(long year, long personelId){
		return managementDAO.getFinancialManagementIncome(year, personelId);
	}

	public List<FinansmanYonetimiGelirGider> getFinancialManagementExpense(long year, long personelId){
		return managementDAO.getFinancialManagementExpense(year,personelId);
	}
	
	public List<FinansmanYonetimiGelirGiderAylik> getFinancialManagementIncomeExpenseMonthly(long year){
		return managementDAO.getFinancialManagementIncomeExpenseMonthly(year);
	}
	
	public List<FinansmanYonetimiGelirGiderAylik> getFinancialManagementIncomeExpenseDaily(long year,long month){
		return managementDAO.getFinancialManagementIncomeExpenseDaily(year,month);
	}
	
	public List<FinansmanYonetimiGelirGider> getFinancialManagementIncomeBudget() {
		return managementDAO.getFinancialManagementIncomeBudget();
	}

	public List<FinansmanYonetimiGelirGider> getFinancialManagementExpenseBudget() {
		return managementDAO.getFinancialManagementExpenseBudget();
	}
	
	public List<FinansmanYonetimiTahakkuk> getFinancialManagementTahakkukFirstLevel(long year){
		return managementDAO.getFinancialManagementTahakkukFirstLevel(year);
	}
	
	public List<FinansmanYonetimiTahakkuk> getFinancialManagementTahakkukOtherLevels(String kod,int level){
		return managementDAO.getFinancialManagementTahakkukOtherLevels(kod,level);
	}
	
	public List<GraphGeneral> getRealEstateManagement(){
		return managementDAO.getRealEstateManagement();
	}
	
	public List<GraphGeneral> getRealEstateManagementDetail(long id){
		return managementDAO.getRealEstateManagementDetail(id);
	}
	
	public List<GraphGeneral> getWeddingManagement(){
		return managementDAO.getWeddingManagement();
	}
	
	public List<GraphGeneral> getWeddingManagementMonthly(long year){
		return managementDAO.getWeddingManagementMonthly(year);
	}
	
	public List<GraphGeneral> getWeddingManagementDaily(long year,long month){
		return managementDAO.getWeddingManagementDaily(year,month);
	}
	
	public List<YbsWeddingList> getWeddingManagementDailyList(long year, long month, long day){
		return managementDAO.getWeddingManagementDailyList(year,month,day);
	}
	
	public List<GraphGeneral> getHealthManagement(){
		return managementDAO.getHealthManagement();
	}
	
	public List<GraphGeneral> getHealthManagementMonthly(long year){
		return managementDAO.getHealthManagementMonthly(year);
	}
	
	public List<GraphGeneral> getHealthManagementDaily(long year,long month){
		return managementDAO.getHealthManagementDaily(year,month);
	}
	
	public List<InsanKaynaklari> getHumanResourcesType(){
		return managementDAO.getHumanResourcesType();
	}
	
	public List<InsanKaynaklari> getHumanResourcesTypeMudurluk(String type){
		return managementDAO.getHumanResourcesTypeMudurluk(type);
	}
	
	public List<InsanKaynaklari> getHumanResourcesGender(){
		return managementDAO.getHumanResourcesGender();
	}
	
	public List<InsanKaynaklari> getHumanResourcesGenderMudurluk(String gender){
		return managementDAO.getHumanResourcesGenderMudurluk(gender);
	}
	
	public List<InsanKaynaklari> getHumanResourcesJob(){
		return managementDAO.getHumanResourcesJob();
	}
	
	public List<InsanKaynaklari> getHumanResourcesAge(){
		return managementDAO.getHumanResourcesAge();
	}
	
	public List<ToplantiYonetimi> getMeetingManagement(){
		return managementDAO.getMeetingManagement();
	}

	public List<ToplantiYonetimi> getMeetingManagementMonthly(long year){
		return managementDAO.getMeetingManagementMonthly(year);
	}
	
	public List<BelgeYonetim> getDocumentManagement(String startDate, String endDate){
		return managementDAO.getDocumentManagement(startDate,endDate);
	}
	
	public List<BelgeYonetim> getDocumentManagementMudurluk(String startDate,String endDate,String tur){
		return managementDAO.getDocumentManagementMudurluk(startDate,endDate,tur);
	}
	
	public List<GraphGeneral> getLicenseCount(){
		return managementDAO.getLicenseCount();
	}
	
	public List<GraphGeneral> getLicenseTypeCount(long year){
		return managementDAO.getLicenseTypeCount(year);
	}
	
	public List<GraphGeneral> getLicenseTypeMonthlyCount(long year,long id){
		return managementDAO.getLicenseTypeMonthlyCount(year,id);
	}
	
	public List<GraphGeneral> getLicenseTypeDailyCount(long year,long month,long id){
		return managementDAO.getLicenseTypeDailyCount(year,month,id);
	}
	
	public List<YbsLicenseList> getLicenseTypeDailyList(long year, long month, long day, long id){
		return managementDAO.getLicenseTypeDailyList(year,month,day,id);
	}
	
	public List<YbsGununOzeti> getDailySummary(){
		return managementDAO.getDailySummary();
	}
	
	public List<VezneTahsilat> getPayOfficeCollection(long persid, String startDate, String endDate){
		return managementDAO.getPayOfficeCollection(persid,startDate,endDate);
	}
	
	public List<VezneTahsilatDetay> getPayOfficeCollectionDetail(long id, String startDate, String endDate){
		return managementDAO.getPayOfficeCollectionDetail(id,startDate,endDate);
	}
	
	public List<VezneTahsilat> getLocationCollection(long persid,String startDate,String endDate){
		return managementDAO.getLocationCollection(persid,startDate,endDate);
	}
	
	public List<GununOzeti> getDailySummaryIncomeManagementTahakkuk(String requestDate){
		return managementDAO.getDailySummaryIncomeManagementTahakkuk(requestDate);
	}
	
	public List<GununOzeti> getDailySummaryIncomeManagementTahsilat(String requestDate){
		return managementDAO.getDailySummaryIncomeManagementTahsilat(requestDate);
	}
	
	public List<GununOzeti> getDailySummaryIncomeManagementEmlakBeyani(String requestDate){
		return managementDAO.getDailySummaryIncomeManagementEmlakBeyani(requestDate);
	}
	
	public List<GununOzeti> getDailySummaryIncomeManagementCevreBeyani(String requestDate){
		return managementDAO.getDailySummaryIncomeManagementCevreBeyani(requestDate);
	}
	
	public List<GununOzeti> getDailySummaryIncomeManagementReklamBeyani(String requestDate){
		return managementDAO.getDailySummaryIncomeManagementReklamBeyani(requestDate);
	}

	@Override
	public List<GununOzeti> getDailySummaryFinancialManagementBanka(String requestDate) {
		return managementDAO.getDailySummaryFinancialManagementBanka(requestDate);
	}

	@Override
	public List<GununOzeti> getDailySummaryFinancialManagementFirmaBorcu(String requestDate) {
		return managementDAO.getDailySummaryFinancialManagementFirmaBorcu(requestDate);
	}

	@Override
	public List<GununOzeti> getDailySummaryFinancialManagementVergiBorcu(String requestDate) {
		return managementDAO.getDailySummaryFinancialManagementVergiBorcu(requestDate);
	}

	@Override
	public List<GununOzeti> getDailySummaryFinancialManagementSosyalGuvenlikBorcu(String requestDate) {
		return managementDAO.getDailySummaryFinancialManagementSosyalGuvenlikBorcu(requestDate);
	}

	@Override
	public List<GununOzeti> getDailySummaryFinancialManagementToplamBorc(String requestDate) {
		return managementDAO.getDailySummaryFinancialManagementToplamBorc(requestDate);
	}
	

	@Override
	public List<GununOzeti> getDailySummaryDocumentManagementEvrak(String requestDate) {
		return managementDAO.getDailySummaryDocumentManagementEvrak(requestDate);
	}

	@Override
	public List<GununOzeti> getDailySummaryDocumentManagementDilekce(String requestDate) {
		return managementDAO.getDailySummaryDocumentManagementDilekce(requestDate);
	}

	@Override
	public List<GununOzeti> getDailySummaryDocumentManagementSikayet(String requestDate) {
		return managementDAO.getDailySummaryDocumentManagementSikayet(requestDate);
	}
	
	@Override
	public List<PersonelBilgileri> getDailySummaryHumanResourcesCounts() {
		return managementDAO.getDailySummaryHumanResourcesCounts();
	}
	
	@Override
	public List<GununOzeti> getDailySummaryRealEstateManagementBina() {
		return managementDAO.getDailySummaryRealEstateManagementBina();
	}

	@Override
	public List<GununOzeti> getDailySummaryRealEstateManagementNumarataj() {
		return managementDAO.getDailySummaryRealEstateManagementNumarataj();
	}

	@Override
	public List<GununOzeti> getDailySummaryRealEstateManagementBagimsizBolum() {
		return managementDAO.getDailySummaryRealEstateManagementBagimsizBolum();
	}
	
	@Override
	public List<GununOzeti> getDailySummaryLicenseManagementRuhsatBasvurusu(String requestDate) {
		return managementDAO.getDailySummaryLicenseManagementRuhsatBasvurusu(requestDate);
	}

	@Override
	public List<GununOzeti> getDailySummaryLicenseManagementVerilenRuhsat(String requestDate) {
		return managementDAO.getDailySummaryLicenseManagementVerilenRuhsat(requestDate);
	}
	
	@Override
	public List<GununOzeti> getDailySummaryServiceManagementNikah(String requestDate) {
		return managementDAO.getDailySummaryServiceManagementNikah(requestDate);
	}
	
	public List<GelirlerYonetimiTahakkuk> getIncomeManagementTahakkukDetail(String startDate, String endDate){
		return managementDAO.getIncomeManagementTahakkukDetail(startDate,endDate);
	}
	
	public List<GelirlerYonetimiEmlakBeyani> getIncomeManagementEmlakBeyani(String startDate, String endDate){
		return managementDAO.getIncomeManagementEmlakBeyani(startDate,endDate);
	}
	
	public List<GelirlerYonetimiCevreBeyani> getIncomeManagementCevreBeyani(String startDate, String endDate){
		return managementDAO.getIncomeManagementCevreBeyani(startDate,endDate);
	}
	
	public List<GelirlerYonetimiReklamBeyani> getIncomeManagementReklamBeyani(String startDate, String endDate){
		return managementDAO.getIncomeManagementReklamBeyani(startDate,endDate);
	}
	
	public String findTimePeriod(String timePeriod) throws Throwable{
		
		String retval = "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		
		
		if(timePeriod.equalsIgnoreCase("day")){
			calendar.add(Calendar.DATE, -1); 
			retval = dateFormat.format(calendar.getTime()); 
			System.out.println(retval);
		}
		else if(timePeriod.equalsIgnoreCase("week")){
			calendar.add(Calendar.DATE, -7);  
			retval = dateFormat.format(calendar.getTime()); 
			System.out.println(retval);
			
		}
		else if(timePeriod.equalsIgnoreCase("lastMonth")){
			calendar.add(Calendar.DATE, -30);  
			retval = dateFormat.format(calendar.getTime()); 
			System.out.println(retval);
		}
		else if(timePeriod.equalsIgnoreCase("last6Month")){
			calendar.add(Calendar.DATE, -180);  
			retval = dateFormat.format(calendar.getTime()); 
			System.out.println(retval);
		}
		return retval;
	}

		

}
