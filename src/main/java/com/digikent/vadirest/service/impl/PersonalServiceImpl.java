package com.digikent.vadirest.service.impl;

import com.digikent.vadirest.dao.PersonalDAO;
import com.digikent.vadirest.dto.AracTalep;
import com.digikent.vadirest.dto.Izin;
import com.digikent.vadirest.dto.OdulCeza;
import com.digikent.vadirest.dto.PDKSInformation;
import com.digikent.vadirest.dto.Person;
import com.digikent.vadirest.service.PersonalService;
import com.vadi.digikent.memur.ikm.model.HR3Hesap;
import com.vadi.digikent.ortak.model.EILElektronikPosta;
import com.vadi.digikent.ortak.model.EILTelefon;
import com.vadi.digikent.personel.per.model.HR1Egitim;
import com.vadi.digikent.personel.per.model.HR1PersonelIslem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;


@Service("personalService")
@Transactional
public class PersonalServiceImpl implements PersonalService {
	
	@Autowired(required=true)
	private PersonalDAO personalDAO;

	public Person findPersonelInformationById(long persid) {
		return personalDAO.findPersonelInformationById(persid);
	}

	public List<EILTelefon> getPhoneNumberById(long persid){
		return personalDAO.getPhoneNumberById(persid);
	}
	
	public List<EILElektronikPosta> getMailById(long persid) {
		return personalDAO.getMailById(persid);
	}
	
	public List<HR3Hesap> getSalaryInformationByYear(long persid, int year){
		return personalDAO.getSalaryInformationByYear(persid, year);
	}

	
	public List<HR1Egitim> getPersonalEducation(long persid){
		return personalDAO.getPersonalEducation(persid);
	}

	public List<HR1PersonelIslem> getServiceInformation(long persid) {
		return personalDAO.getServiceInformation(persid);
	}

	public List<PDKSInformation> getPDKSInformation(long persid){
		return personalDAO.getPDKSInformation(persid);
	}
	
	public List<Izin> getHolidaysEarned(long persid){
		return personalDAO.getHolidaysEarned(persid);
	}
	
	public List<Izin> getHolidaysUsed(long persid){
		return personalDAO.getHolidaysUsed(persid);
	}

	public List<Izin> getRemainingHolidays(long persid){
		return personalDAO.getRemainingHolidays(persid);
	}
	
	public List<Izin> getCancelledHolidays(long persid){
		return personalDAO.getCancelledHolidays(persid);
	}
	
	public List<OdulCeza> getRewardPenaltyInformation(long persid, String param){
		return personalDAO.getRewardPenaltyInformation(persid, param);
	}
	
	public List<AracTalep> getVehicleRequestInformation(long persid){
		return personalDAO.getVehicleRequestInformation(persid);
	}
}
