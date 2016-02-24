package com.digikent.vadirest.dao;

import com.digikent.vadirest.dto.Person;
import com.vadi.digikent.personel.per.model.HR1EgitimGenel;
import com.vadi.digikent.personel.per.model.HR1Personel;
import com.vadi.digikent.personel.per.model.HR1PersonelIslem;
import com.vadi.digikent.portal.pr1.model.PR1Haber;
import com.vadi.digikent.portal.pr1.model.PR1KurumIndirim;

import java.util.List;



public interface CorporateDAO {
	public List<PR1Haber> getAnnouncement(String param);
	public List<PR1Haber> getCurrent();
	public List<HR1Personel> getSpecialCelebration(String columnName);
	public List<Person> getCatalog();
	public List<Person> getCatalogWithoutPicture();
	public List<HR1EgitimGenel> getEducation(long persid);
	public List<PR1KurumIndirim> getDiscount();
	public List<HR1PersonelIslem> getJobChange();
}
