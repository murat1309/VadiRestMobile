package com.digikent.web.rest;

import com.digikent.vadirest.dto.BelgeBasvuru;
import com.digikent.vadirest.dto.BelgeBasvuruDetay;
import com.digikent.vadirest.dto.BelgeYonetimKullanici;
import com.digikent.vadirest.dto.CozumOrtagi;
import com.digikent.vadirest.dto.EBYSBekleyen;
import com.digikent.vadirest.dto.EBYSBirimMenu;
import com.digikent.vadirest.dto.EBYSKlasorMenu;
import com.digikent.vadirest.dto.GraphGeneral;
import com.digikent.vadirest.dto.Rol;
import com.digikent.vadirest.service.DocumentManagementService;
import com.vadi.digikent.sistem.syn.model.SM1Roles;

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
@RequestMapping("/belgeYonetim")
public class DocumentManagementController {

	@Autowired(required=true)
	private DocumentManagementService documentManagementService;
	
	//belge yonetim rol listesi
	@RequestMapping(value = "" +
			"/{persid}",method = RequestMethod.GET)
	public List<Rol> getEBYSRollList(@PathVariable("persid") long persid){
		System.out.println("--------get roll list---------");
		System.out.println(persid);
		List<SM1Roles> retval = documentManagementService.getEBYSRollList(persid);
		List<Rol> rolList= new ArrayList();
		for(int i=0 ; i<retval.size();i++){
			Rol rol = new Rol();
			rol.setId(retval.get(i).getId());
			rol.setTanim(retval.get(i).getTanim());
			rolList.add(rol);
			System.out.println(retval.get(i).getTanim());
		}
		return rolList;
	}
	
	//EBYS bekleyen belge
	@RequestMapping(value="EBYSBekleyen/{persid}/{rolid}",method = RequestMethod.GET)
	public List<EBYSBekleyen> getWaitingEBYS(@PathVariable("persid") long persid, @PathVariable("rolid") long rolid){
		System.out.println("--------ebysBekleyen--------");
		System.out.println(persid);
		System.out.println(rolid);
		return documentManagementService.getWaitingEBYS(persid, rolid);
	}
	
	//Belge Basvuru rollList
	@RequestMapping(value = "belgeBasvuruRol/{persid}/{mastid}",method = RequestMethod.GET)
	public List<Rol> getDocRollList(@PathVariable("persid") long persid,@PathVariable("mastid") long mastid){
		System.out.println("---------Belge basvuru rollList---------");
		System.out.println(persid);
		System.out.println(mastid);
		return documentManagementService.getDocRollList(persid, mastid);
		
	}
	
	//Belge Basuvuru
	@RequestMapping(value = "belgeBasvuru/{rolid}",method = RequestMethod.GET)
	public List<BelgeBasvuru> getApplyDoc(@PathVariable("rolid")long rolid){
		System.out.println("-----------BelgeBasvuru--------------");
		System.out.println(rolid);
		return documentManagementService.getApplyDoc(rolid);
	}
	
	//Belge Basuvuru detay
	@RequestMapping(value = "belgeBasvuruDetay/{docId}",method = RequestMethod.GET)
	public BelgeBasvuruDetay getApplyDocDetail(@PathVariable("docId") long docId){
		System.out.println("-------belge basvuru detay-------------");
		System.out.println(docId);
		return documentManagementService.getApplyDocDetail(docId);
	}
	
	//EBYS ture gore toplam degerler
	@RequestMapping(value = "ebysCount/{persid}/{rolid}/{tur}",method = RequestMethod.GET)
	public Long getEbysMenuCount(@PathVariable("persid") long persid,@PathVariable("rolid") long rolid,@PathVariable("tur") String tur){
		System.out.println("-------ebys count-------------");
		System.out.println(persid);
		return documentManagementService.getEbysMenuCount(persid,rolid,tur);
	}
	
	//Role gore cozum ortagi olup olmadigini doner
	@RequestMapping(value = "cozumOrtagi/{rolid}",method = RequestMethod.GET)
	public CozumOrtagi isSolutionPartner(@PathVariable("rolid") long rolid){
		System.out.println("------cozum Ortagi---------");
		System.out.println(rolid);
		return documentManagementService.isSolutionPartner(rolid);
	}
	
	//EBYS klasor Tree
	@RequestMapping(value = "klasor/{rolid}",method = RequestMethod.GET)
	public List<EBYSKlasorMenu> getEBYSFolderTree(@PathVariable("rolid") long rolid){
		System.out.println("------klasor agaci---------");
		System.out.println(rolid);
		return documentManagementService.getEBYSFolderTree(rolid);
	}
	
	//EBYS Birim Tree
	@RequestMapping(value = "birim",method = RequestMethod.GET)
	public List<EBYSBirimMenu> getEBYSUnitTree(){
		System.out.println("------birim agaci---------");
		return documentManagementService.getEBYSUnitTree();
	}
	
	
	@RequestMapping(value = "ebys",method = RequestMethod.GET)
	public List<EBYSBirimMenu> getEBYSEbysTree(){
		System.out.println("------EBYS agaci---------");
		return documentManagementService.getEBYSEbysTree();
	}
	
	@RequestMapping(value = "belgeYonetimKullanici/{persid}",method = RequestMethod.GET)
	public List<BelgeYonetimKullanici> getDocManagementUserList(@PathVariable("persid") long persid){
		System.out.println("------klasor agaci---------");
		System.out.println(persid);
		return documentManagementService.getDocManagementUserList(persid);
	}
	
	@RequestMapping(value = "belgeYonetimGrafikGelenBelge/{year}/{servisId}/{result}",method = RequestMethod.GET)
	public List<GraphGeneral> getDocManagementGraphIncomingDoc(@PathVariable("year") long year, @PathVariable("servisId") long servisId, @PathVariable("result") String result){
		System.out.println("------belge yonetim gelen belge grafik---------");
		System.out.println(result);
		return documentManagementService.getDocManagementGraphIncomingDoc(year, servisId, result);
	}
	
	@RequestMapping(value = "belgeYonetimGrafikGidenBelge/{year}/{servisId}/{result}",method = RequestMethod.GET)
	public List<GraphGeneral> getDocManagementGraphOutgoingDoc(@PathVariable("year") long year,@PathVariable("servisId") long servisId,@PathVariable("result") String result){
		System.out.println("------belge yonetim giden belge grafik---------");
		System.out.println(result);
		return documentManagementService.getDocManagementGraphOutgoingDoc(year, servisId, result);
	}
	
	@RequestMapping(value = "ebysIsGrafikleri/{rolid}/{servisMudurluk}/{action}",method = RequestMethod.GET)
	public List<GraphGeneral> getEbysBusinessGraph(@PathVariable("rolid")long rolid,@PathVariable("servisMudurluk") long servisMudurluk, @PathVariable("action")String action){
		System.out.println("------ebys is grafikleri---------");
		System.out.println(servisMudurluk);
		return documentManagementService.getEbysBusinessGraph(rolid,servisMudurluk,action);
	}
	
	@RequestMapping(value = "ebysIsGrafikleriDetay/{rolid}/{servisMudurluk}/{action}/{rolPerformerId}",method = RequestMethod.GET)
	public List<GraphGeneral> getEbysBusinessGraphDetail(@PathVariable("rolid") long rolid,@PathVariable("servisMudurluk") long servisMudurluk, @PathVariable("action")String action,@PathVariable("rolPerformerId") long rolPerformerId){
		System.out.println("------ebys is grafikleri---------");
		System.out.println(servisMudurluk);
		return documentManagementService.getEbysBusinessGraphDetail(rolid,servisMudurluk,action,rolPerformerId);
	}
}
