package com.digikent.vadirest.service.impl;

import com.digikent.vadirest.dao.CorporateDAO;
import com.digikent.vadirest.dto.FavoriteWebSite;
import com.digikent.vadirest.dto.Person;
import com.digikent.vadirest.service.CorporateService;
import com.vadi.digikent.personel.per.model.HR1EgitimGenel;
import com.vadi.digikent.personel.per.model.HR1Personel;
import com.vadi.digikent.personel.per.model.HR1PersonelIslem;


import java.util.List;

import com.vadi.smartkent.datamodel.domains.portal.pr1.PR1Haber;
import com.vadi.smartkent.datamodel.domains.portal.pr1.PR1KurumIndirim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("corporateService")
@Transactional
public class CorporateServiceImpl implements CorporateService {
	
	@Autowired(required=true)
	private CorporateDAO corporateDAO;
	
	public List<PR1Haber> getAnnouncement(String param){
		return corporateDAO.getAnnouncement(param);	
	}
	public List<PR1Haber> getCurrent(){
		return corporateDAO.getCurrent();
	}
	public List<HR1Personel> getSpecialCelebration(String columnName) {
		return  corporateDAO.getSpecialCelebration(columnName);
	}
	public List<Person> getCatalog() {
		return corporateDAO.getCatalog();
	}
	public List<Person> getCatalogWithoutPicture(){
		return corporateDAO.getCatalogWithoutPicture();
	}
	public List<HR1EgitimGenel> getEducation(long persid) {
		return corporateDAO.getEducation(persid);
	}
	
	public List<PR1KurumIndirim> getDiscount(){
		return corporateDAO.getDiscount();
	}
	
	public List<HR1PersonelIslem> getJobChange(){
		return corporateDAO.getJobChange();
	}

}
