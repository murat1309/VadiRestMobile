package com.digikent.web.rest;

import com.digikent.vadirest.dto.AracTalep;
import com.digikent.vadirest.dto.Egitim;
import com.digikent.vadirest.dto.Izin;
import com.digikent.vadirest.dto.Maas;
import com.digikent.vadirest.dto.Mail;
import com.digikent.vadirest.dto.OdulCeza;
import com.digikent.vadirest.dto.PDKSInformation;
import com.digikent.vadirest.dto.Person;
import com.digikent.vadirest.dto.PersonelIslem;
import com.digikent.vadirest.dto.Telefon;
import com.digikent.vadirest.service.PersonalService;
import com.vadi.digikent.memur.ikm.model.HR3Hesap;
import com.vadi.digikent.ortak.model.EILElektronikPosta;
import com.vadi.digikent.ortak.model.EILTelefon;
import com.vadi.digikent.personel.per.model.HR1Egitim;
import com.vadi.digikent.personel.per.model.HR1PersonelIslem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/bireysel")
public class PersonalController {
	@Autowired(required=true)
	protected PersonalService personalService;

	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	
	//personel maas bilgileri yila gore
	@RequestMapping(value = "maas/{persid}/{year}",method=RequestMethod.GET)
    public List<Maas> getSalary(@PathVariable("persid") long persid,@PathVariable("year") int year){
		System.out.println("-----------------personel maas-------------");
		System.out.println(persid+" "+year);
		List<Maas> maasList = new ArrayList();
		List<HR3Hesap> retval = personalService.getSalaryInformationByYear(persid, year);
		for(int i=0; i< retval.size();i++){
			Maas  maas = new Maas();
			maas.setAy(retval.get(i).getAy());
			maas.setBrutTutar(retval.get(i).getBrutTutar());
			maas.setKesintiTutari(retval.get(i).getKesintiTutari());
			maas.setNetTutar(retval.get(i).getNetTutar());
			maas.setMaasAdi(retval.get(i).getMaasAdi());
			maasList.add(maas);
			System.out.println(retval.get(i).getBrutTutar());
		}
		return maasList;
	}
	
	//personel bilgileri
	@RequestMapping(value = "personelBilgi/{persid}",method=RequestMethod.GET)
	public Person getPersonalInformation(@PathVariable("persid") long persid){
		System.out.println("------------------personel bilgi---------------");
		System.out.println(persid);
		return personalService.findPersonelInformationById(persid);
	}
	
	//personel telefon bilgileri
	@RequestMapping(value = "telefon/{persid}",method=RequestMethod.GET)
	public List<Telefon> getPhoneNumber(@PathVariable("persid") long persid){
		System.out.println("----------------------personel telefon-----------------");
		System.out.println(persid);
		List<Telefon> telefonList = new ArrayList();
		List<EILTelefon> retval = personalService.getPhoneNumberById(persid);
		for(int i=0;i<retval.size();i++){
			Telefon telefon = new Telefon();
			telefon.setTelefonNumarasi(retval.get(i).getTelefonnumarasi());
			telefon.setDahili(retval.get(i).getDahili());
			telefon.setIzahat(retval.get(i).getIzahat());
			telefonList.add(telefon);
			System.out.println(retval.get(i).getDahili());
		}
		return telefonList;
	}
	
	//personel mail bilgileri
	@RequestMapping(value = "mail/{persid}",method=RequestMethod.GET)
	public List<Mail> getMail(@PathVariable("persid") long persid){
		System.out.println("---------------personel mail--------------");
		System.out.println(persid);
		List<Mail> mailList = new ArrayList();
		List<EILElektronikPosta> retval = personalService.getMailById(persid);
		for(int i=0;i<retval.size();i++){
			Mail mail = new Mail();
			mail.setElektronikPosta(retval.get(i).getElektronikposta());
			mail.setKurumsalEPosta(retval.get(i).getKurumsaleposta());
			mailList.add(mail);
			System.out.println(retval.get(i).getElektronikposta());
		}
		return mailList;
		
	}
	
	//personel egitim bilgileri
	@RequestMapping(value = "egitim/{persid}",method=RequestMethod.GET)
	public  List<Egitim> getPersonalEducation(@PathVariable("persid")long persid){
		System.out.println("---------------personel ed--------------");
		System.out.println(persid);
		List<Egitim> egitimList = new ArrayList();
		List<HR1Egitim> retval = personalService.getPersonalEducation(persid);
		
		
		for(int i=0;i<retval.size();i++){
			Egitim egitim = new Egitim();
			egitim.setAciklama(retval.get(i).getIzahat());
			egitim.setKatilimDurumu(retval.get(i).getKatilimDurumu());
			egitim.setKonusu(retval.get(i).getKonusu());
			egitim.setToplamSaat(retval.get(i).getSaat());
			if(retval.get(i).getTarih() != null)
				egitim.setTarih(dateFormat.format(retval.get(i).getTarih()));
			if(retval.get(i).getBitistarihi() != null)
				egitim.setBitisTarihi(dateFormat.format(retval.get(i).getBitistarihi()));
			egitimList.add(egitim);
		}
		return egitimList;
	}
	
	//personel hizmet bilgileri
	@RequestMapping(value = "hizmet/{persid}",method=RequestMethod.GET)
	public List<PersonelIslem> getServiceInformation(@PathVariable("persid") long persid){
		System.out.println("-------------hizmet bilgilerim----------");
		System.out.println(persid);
		List<HR1PersonelIslem> retval = personalService.getServiceInformation(persid);
		List<PersonelIslem> personelIslemList = new ArrayList();
		for(int i=0;i<retval.size();i++){
			PersonelIslem personelIslem = new PersonelIslem();
			personelIslem.setGorevYeri(retval.get(i).getGorevYeri());
			personelIslem.setGorevi(retval.get(i).getGorevi());
			personelIslem.setTuru(retval.get(i).getTuru());
			personelIslem.setKadroDerecesi(retval.get(i).getKadroDerecesi());
			personelIslemList.add(personelIslem);
		}
		return personelIslemList;
	}

	@RequestMapping(value = "PDKS/{persid}",method = RequestMethod.GET)
	public List<PDKSInformation> getPDKSInformation(@PathVariable("persid") long persid){
		System.out.println("-------PDKS bilgilerim---------");
		System.out.println(persid);
		return personalService.getPDKSInformation(persid);
	}

	@RequestMapping(value = "izin/{persid}",method = RequestMethod.GET)
	public List<Izin> getHolidaysEarned(@PathVariable("persid") long persid){
		System.out.println("-------izin bilgilerim-------");
		System.out.println("persid:" + persid);
		return personalService.getHolidaysEarned(persid);
	}
	
	@RequestMapping(value = "kullanilanIzin/{persid}",method = RequestMethod.GET)
	public List<Izin> getHolidaysUsed(@PathVariable("persid") long persid){
		System.out.println("--------kullanilan izin-------------");
		System.out.println("persid:" + persid);
		return personalService.getHolidaysUsed(persid);
	} 
	
	@RequestMapping(value = "kalanIzin/{persid}",method = RequestMethod.GET)
	public List<Izin> getRemainingHolidays(@PathVariable("persid") long persid){
		System.out.println("--------kalan izin-------------");
		System.out.println("persid:" + persid);
		return personalService.getRemainingHolidays(persid);
	} 
	
	@RequestMapping(value = "iptalIzin/{persid}",method = RequestMethod.GET)
	public List<Izin> getCancelledHolidays(@PathVariable("persid") long persid){
		System.out.println("--------iptal izin-------------");
		System.out.println("persid:" + persid);
		return personalService.getCancelledHolidays(persid);
	}
	
	@RequestMapping(value = "odulBilgilerim/{persid}" , method = RequestMethod.GET)
	public List<OdulCeza> getRewardInformation(@PathVariable("persid") long persid){
		System.out.println("------odul bilgilerim-------");
		System.out.println("persid:" + persid);
		return personalService.getRewardPenaltyInformation(persid,"O");
	}
	
	@RequestMapping(value = "cezaBilgilerim/{persid}" , method = RequestMethod.GET)
	public List<OdulCeza> getPenaltyInformation(@PathVariable("persid") long persid){
		System.out.println("------ceza bilgilerim-------");
		System.out.println("persid:" + persid);
		return personalService.getRewardPenaltyInformation(persid,"C");
	}
	
	@RequestMapping(value = "aracTalep/{persid}" , method = RequestMethod.GET)
	public List<AracTalep> getVehicleRequestInformation(@PathVariable("persid") long persid){
		System.out.println("------arac talep bilgilerim-------");
		System.out.println("persid:" + persid);
		return personalService.getVehicleRequestInformation(persid);
	}
}
