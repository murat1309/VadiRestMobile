package com.digikent.vadirest.dao;

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
import com.vadi.digikent.personel.per.model.HR1PersonelProfile;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;




public interface PersonalDAO {
	public Person findPersonelInformationById(long persid);
	public List<EILTelefon> getPhoneNumberById(long persid);
	public List<EILElektronikPosta> getMailById(long persid);
	public List<HR1PersonelProfile> findPersonalInformation();
	public List<HR3Hesap> getSalaryInformationByYear(long persid, int year);
	public List<HR1Egitim> getPersonalEducation(long persid);
	public List<HR1PersonelIslem> getServiceInformation(long persid);
	public List<PDKSInformation> getPDKSInformation(long persid);
	public List<Izin> getHolidaysEarned(long persid);
	public List<Izin> getHolidaysUsed(long persid);
	public List<Izin> getRemainingHolidays(long persid);
	public List<Izin> getCancelledHolidays(long persid);
	public List<OdulCeza> getRewardPenaltyInformation(long persid, String param);
	public List<AracTalep> getVehicleRequestInformation(long persid);

}
