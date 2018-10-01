package com.digikent.vadirest.service;

import com.digikent.vadirest.dto.FavoriteWebSite;
import com.digikent.vadirest.dto.Person;
import com.vadi.digikent.personel.per.model.HR1EgitimGenel;
import com.vadi.digikent.personel.per.model.HR1Personel;
import com.vadi.digikent.personel.per.model.HR1PersonelIslem;
import com.vadi.smartkent.datamodel.domains.portal.pr1.PR1Haber;
import com.vadi.smartkent.datamodel.domains.portal.pr1.PR1KurumIndirim;

import java.util.List;


public interface CorporateService {
	// get Announcements and presidential message
	public List<PR1Haber> getAnnouncement(String param);
	//get Current news
	public List<PR1Haber> getCurrent();
	// get birth and marriage info
	public List<HR1Personel> getSpecialCelebration(String columnName);
	//get Catalog
	public List<Person> getCatalog();
	//get Catalog(Resimsiz)
	public List<Person> getCatalogWithoutPicture();
	//get all education
	public List<HR1EgitimGenel> getEducation(long persid);
	//get discounts
	public List<PR1KurumIndirim> getDiscount();
	//get role changes
	public List<HR1PersonelIslem> getJobChange();

}
