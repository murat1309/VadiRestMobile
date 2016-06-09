package com.digikent.vadirest.service;

import com.digikent.vadirest.dto.AracTalep;
import com.digikent.vadirest.dto.Izin;
import com.digikent.vadirest.dto.OdulCeza;
import com.digikent.vadirest.dto.PDKSInformation;
import com.digikent.vadirest.dto.Person;
import com.vadi.digikent.memur.ikm.model.HR3Hesap;
import com.vadi.digikent.ortak.model.EILElektronikPosta;
import com.vadi.digikent.ortak.model.EILTelefon;
import com.vadi.digikent.personel.per.model.HR1Egitim;
import com.vadi.digikent.personel.per.model.HR1PersonelIslem;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;


public interface PersonalService {
	//get personal information
	public Person findPersonelInformationById(long persid);
	
	//get personal phone number
	public List<EILTelefon> getPhoneNumberById(long persid);
	
	//get personal mail
	public List<EILElektronikPosta> getMailById(long persid);
	
	//Get personal salary info with year parameter
	public List<HR3Hesap> getSalaryInformationByYear(long persid, int year);
	
	//get personal education
	public List<HR1Egitim> getPersonalEducation(long persid);
	
	//get personal service information
	public List<HR1PersonelIslem> getServiceInformation(long personelId);
	
	//get PDKS information with personel id
	public List<PDKSInformation> getPDKSInformation(long persid);
	
	// hakedilen izin bilgileri
	public List<Izin> getHolidaysEarned(long persid);
	
	//kullanilan izin bilgileri
	public List<Izin> getHolidaysUsed(long persid);
	
	//kalan izin bilgileri
	public List<Izin> getRemainingHolidays(long persid);
	
	//iptal edilen izin Bilgileri
	public List<Izin> getCancelledHolidays(long persid);
	
	//parametreye gore odul ve ceza bilgilerini doner 'C':ceza 'O':odul
	public List<OdulCeza> getRewardPenaltyInformation(long persid, String param);
	
	//arac talep bilgileri listesi
	public List<AracTalep> getVehicleRequestInformation(long persid);

}
