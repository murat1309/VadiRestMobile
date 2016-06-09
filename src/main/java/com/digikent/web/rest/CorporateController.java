package com.digikent.web.rest;

import com.digikent.vadirest.dto.Announcement;
import com.digikent.vadirest.dto.Egitim;
import com.digikent.vadirest.dto.KurumIndirim;
import com.digikent.vadirest.dto.Person;
import com.digikent.vadirest.dto.PersonelIslem;
import com.digikent.vadirest.service.CorporateService;
import com.vadi.digikent.personel.per.model.HR1EgitimGenel;
import com.vadi.digikent.personel.per.model.HR1Personel;
import com.vadi.digikent.personel.per.model.HR1PersonelIslem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.vadi.smartkent.datamodel.domains.portal.pr1.PR1Haber;
import com.vadi.smartkent.datamodel.domains.portal.pr1.PR1KurumIndirim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/kurumsal")
public class CorporateController {
	@Autowired(required=true)
	protected CorporateService corporateService;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	//kurum ilan 
	@RequestMapping(value = "/ilan",method=RequestMethod.GET)
	public List<Announcement> getAnnouncement(){
		System.out.println("---------------------kurumilan-------------------------");
		List<PR1Haber> retval = corporateService.getAnnouncement(String.valueOf('H'));
		List<Announcement> announcementList = new ArrayList();
		for(int i=0;i< retval.size();i++){
			Announcement announcement = new Announcement();
			announcement.setBaslik(retval.get(i).getBaslik());
			announcement.setIzahat(retval.get(i).getIzahat());
			announcement.setKisaIzahat(retval.get(i).getKisaIzahat());
			announcement.setKisaBaslik(retval.get(i).getKisaBaslik());
			announcement.setResim(retval.get(i).getResim());
			if(retval.get(i).getTarih() != null)				
				announcement.setTarih(dateFormat.format(retval.get(i).getTarih()));
			if(retval.get(i).getSonyayinlanmatarihi() != null)				
				announcement.setSonTarih(dateFormat.format(retval.get(i).getTarih()));
			announcementList.add(announcement);
		}
		return announcementList;
	}
	
	//kurum guncel
	@RequestMapping(value = "/guncel",method=RequestMethod.GET)
	public List<Announcement> getCurrent(){
		System.out.println("-------------------Guncel Haberler-----------------");
		List<PR1Haber> retval = corporateService.getCurrent();
		List<Announcement> announcementList = new ArrayList();
		for(int i=0 ;i<retval.size();i++){
			Announcement announcement = new Announcement();
			announcement.setBaslik(retval.get(i).getBaslik());
			announcement.setIzahat(retval.get(i).getIzahat());
			announcement.setKisaIzahat(retval.get(i).getKisaIzahat());
			announcement.setKisaBaslik(retval.get(i).getKisaBaslik());
			announcement.setResim(retval.get(i).getResim());
			if(retval.get(i).getTarih() != null)				
				announcement.setTarih(dateFormat.format(retval.get(i).getTarih()));
			if(retval.get(i).getSonyayinlanmatarihi() != null)				
				announcement.setSonTarih(dateFormat.format(retval.get(i).getTarih()));
			announcementList.add(announcement);
		}
		return announcementList;
	}
	
	//evlenenler
	@RequestMapping(value = "/evlenen",method=RequestMethod.GET)
	public List<Person> getMarriage(){
		System.out.println("-------------------bugun evlenen-----------------");
		List<HR1Personel> retval = corporateService.getSpecialCelebration("EVLENMETARIHI");
		List<Person> personList = new ArrayList();
		for(int i=0 ; i<retval.size();i++){
			Person person =new Person();
			person.setAdi(retval.get(i).getAdi());
			person.setSoyadi(retval.get(i).getSoyadi());
			person.setAdSoyad(retval.get(i).getAdiSoyadi());
			person.setGorevMudurlugu(retval.get(i).getGorevMudurlugu());
			person.setGorevi(retval.get(i).getGorevi());
			person.setKadro(retval.get(i).getKadro());
			person.setDahili(retval.get(i).getDahiliTelefonNo());
			person.setCepTelefonu(retval.get(i).getCepTelefonu());
			personList.add(person);
		}
		return personList;
	}
	
	//Dogum
	@RequestMapping(value = "/dogan",method=RequestMethod.GET)
	public List<Person> getBirth(){
		System.out.println("-----------------bugun dogan------------------");
		List<HR1Personel> retval = corporateService.getSpecialCelebration("DOGUMTARIHI");
		List<Person> personList = new ArrayList();
		for(int i=0 ; i<retval.size();i++){
			Person person =new Person();
			person.setAdi(retval.get(i).getAdi());
			person.setSoyadi(retval.get(i).getSoyadi());
			person.setAdSoyad(retval.get(i).getAdiSoyadi());
			person.setGorevMudurlugu(retval.get(i).getGorevMudurlugu());
			person.setGorevi(retval.get(i).getGorevi());
			person.setKadro(retval.get(i).getKadro());
			person.setDahili(retval.get(i).getDahiliTelefonNo());
			person.setCepTelefonu(retval.get(i).getCepTelefonu());
			person.setTanim(retval.get(i).getHr1GorevTuru().getTanim());
			personList.add(person);
		}
		return personList;
	}
	
	//kurum fihrist
	@RequestMapping(value = "/kurumFihrist",method=RequestMethod.GET)
	public List<Person> getCatalog(){
		System.out.println("-----------kurum fihrist---------------");
		return corporateService.getCatalog();
	}
	
	//kurum fihrist
	@RequestMapping(value = "/rehberResimsiz",method=RequestMethod.GET)
	public List<Person> getCatalogWithoutPicture(){
		System.out.println("-----------kurum rehber---------------");
		return corporateService.getCatalogWithoutPicture();
	}
	
	//kurum egitim
	@RequestMapping(value="/kurumegitim/{persid}",method=RequestMethod.GET)
	public List<Egitim> getEducation(@PathVariable("persid") long persid){
		System.out.println("--------------kurum egitim--------------");
		List<HR1EgitimGenel> retval = corporateService.getEducation(persid);
		List<Egitim> kurumEgitimList = new ArrayList();
		for(int i=0;i<retval.size();i++){
			Egitim kurumEgitim = new Egitim();
			kurumEgitim.setEgitmenAdi(retval.get(i).getEgitmenAdi());
			kurumEgitim.setKonusu(retval.get(i).getKonusu());
			kurumEgitim.setTanim(retval.get(i).getTanim());
			kurumEgitim.setToplamSaat(retval.get(i).getToplamSaat());
			kurumEgitim.setVerilecekYer(retval.get(i).getVerilecekYer());
			kurumEgitim.setYili(retval.get(i).getYili());
			kurumEgitimList.add(kurumEgitim);
		}		
		return kurumEgitimList;
	}
	
	//kurum indirim
	@RequestMapping(value="/indirim",method=RequestMethod.GET)
	public List<KurumIndirim> getDiscount(){
		System.out.println("---------------indirimli kurumlar----------");
		List<PR1KurumIndirim> retval = corporateService.getDiscount();
		List<KurumIndirim> kurumIndirimList = new ArrayList();
		for(int i=0;i<retval.size();i++){
			KurumIndirim kurumIndirim = new KurumIndirim();
			kurumIndirim.setAdres(retval.get(i).getAdres());
			if(retval.get(i).getBaslangicTarihi()!= null)
				kurumIndirim.setBaslangicTarihi(dateFormat.format(retval.get(i).getBaslangicTarihi()));
			if(retval.get(i).getBitisTarihi() != null)
				kurumIndirim.setBitisTarihi(dateFormat.format(retval.get(i).getBitisTarihi()));
			kurumIndirim.setOraniYuzde(retval.get(i).getOraniYuzde());
			kurumIndirim.setTanim(retval.get(i).getTanim());
			kurumIndirimList.add(kurumIndirim);
			System.out.println(retval.get(i).getAdres());
		}		
		return kurumIndirimList;
	}
	
	//kurum gorev degisiklik
		@RequestMapping(value="/gorevdegisiklik",method=RequestMethod.GET)
		public List<PersonelIslem> getJobChange(){
			System.out.println("---------------gorev degisiklik----------");
			List<HR1PersonelIslem> retval = corporateService.getJobChange();
			List <PersonelIslem> personelIslemList = new ArrayList();
			for(int i=0;i<retval.size();i++){
				PersonelIslem personelIslem = new PersonelIslem();
				personelIslem.setAdSoyad(retval.get(i).getHr1Personel().getAdiSoyadi());
				personelIslem.setEskiGorevYeri(retval.get(i).getEskiGorevYeri());
				personelIslem.setGorevi(retval.get(i).getGorevi());
				if(retval.get(i).getGorevlendirmeTarihi() != null)
					personelIslem.setGorevlendirmeTarihi(dateFormat.format(retval.get(i).getGorevlendirmeTarihi()));
				personelIslem.setGorevYeri(retval.get(i).getGorevYeri());
				personelIslemList.add(personelIslem);
				System.out.println(retval.get(i).getEskiGorevYeri());
			}		
			return personelIslemList;
		}
		
	//Baskandan mesaj
	@RequestMapping(value="/baskandanMesaj",method=RequestMethod.GET)
	public List<Announcement> getPresidentialMessage(){
			System.out.println("---------------------Baskandan mesaj-------------------------");
			List<PR1Haber> retval = corporateService.getAnnouncement(String.valueOf('B'));
			List<Announcement> announcementList = new ArrayList();
			for(int i=0;i< retval.size();i++){
				Announcement announcement = new Announcement();
				announcement.setBaslik(retval.get(i).getBaslik());
				announcement.setIzahat(retval.get(i).getIzahat());
				announcement.setKisaIzahat(retval.get(i).getKisaIzahat());
				announcement.setKisaBaslik(retval.get(i).getKisaBaslik());
				announcement.setResim(retval.get(i).getResim());
				if(retval.get(i).getTarih() != null)				
					announcement.setTarih(dateFormat.format(retval.get(i).getTarih()));
				if(retval.get(i).getSonyayinlanmatarihi() != null)				
					announcement.setSonTarih(dateFormat.format(retval.get(i).getTarih()));
				announcementList.add(announcement);
			}
			return announcementList;
		}

}
