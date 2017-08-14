package com.digikent.vadirest.service.impl;

import com.digikent.vadirest.dao.DocumentManagementDAO;
import com.digikent.vadirest.dto.*;
import com.digikent.vadirest.service.DocumentManagementService;
import com.vadi.digikent.sistem.syn.model.SM1Roles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("documentManagementService")
@Transactional
public class DocumentManagementServiceImpl implements DocumentManagementService {
	
	@Autowired(required=true)
	private DocumentManagementDAO documentManagementDAO;

	@Transactional(readOnly = true)
	public List<SM1Roles> getEBYSRollList(long persid){
		return documentManagementDAO.getEBYSRollList(persid);
	}

	public List<EBYS> getEBYS(String type, long persid, long rolid, String startDate, String endDate){
		return documentManagementDAO.getEBYS(type,persid, rolid, startDate, endDate);
	}

	public List<EBYSDetail> getEBYSAddition(long documentId){
		return documentManagementDAO.getEBYSAddition(documentId);
	}

	public List<EBYS> getWaitingEBYS(long persid, long rolid, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return documentManagementDAO.getWaitingEBYS(persid, rolid, startDate, endDate);
	}

	public List<EBYSDetail> getEbysDocumentDetail(long documentId){
		return documentManagementDAO.getEbysDocumentDetail(documentId);
	}
	
	public List<Rol> getDocRollList(long persid, long mastid){
		return documentManagementDAO.getDocRollList(persid, mastid);
	}
	
	public List<BelgeBasvuru> getApplyDoc(long rolid){
		return documentManagementDAO.getApplyDoc(rolid);
	}

	public BelgeBasvuruDetay getApplyDocDetail(long docId) {
		return documentManagementDAO.getApplyDocDetail(docId);
	}

	@Override
	public Long getEbysMenuCount(long persid, long rolid, String tur) {
		return documentManagementDAO.getEbysMenuCount(persid,rolid,tur);
	}

	@Override
	public CozumOrtagi isSolutionPartner(long rolid) {
		return documentManagementDAO.isSolutionPartner(rolid);
	}

	@Override
	public List<EBYSKlasorMenu> getEBYSFolderTree(long rolid) {
		return documentManagementDAO.getEBYSFolderTree(rolid);
	}
	
	public List<EBYSBirimMenu> getEBYSUnitTree(){
		List<EBYSBirimMenu> tempMenu = documentManagementDAO.getEBYSUnitTree();
		for(int i=0 ; i< tempMenu.size();i++){
			List<EBYSBirimMenu> retMenu = documentManagementDAO.getEBYSServiceFolderTree(Long.parseLong(tempMenu.get(i).getId()));
			tempMenu.addAll(retMenu);
		}
		
		for(int i=0 ; i< tempMenu.size();i++){
			List<EBYSBirimMenu> retMenu = documentManagementDAO.getEBYSSubUnitTree((Long.parseLong(tempMenu.get(i).getId())));
			tempMenu.addAll(retMenu);
		}
		
		return tempMenu;
	}
	
	public List<EBYSBirimMenu> getEBYSEbysTree(){
		return documentManagementDAO.getEBYSUnitTree();
	}

	@Override
	public List<BelgeYonetimKullanici> getDocManagementUserList(long persid) {
		return documentManagementDAO.getDocManagementUserList(persid);
	}
	
	public List<GraphGeneral> getDocManagementGraphIncomingDoc(long year, long servisId, String result){
		return documentManagementDAO.getDocManagementGraphIncomingDoc(year,servisId,result);
	}
	
	public List<GraphGeneral> getDocManagementGraphOutgoingDoc(long year,long servisId,String result){
		return documentManagementDAO.getDocManagementGraphOutgoingDoc(year,servisId,result);
	}
	
	public List<GraphGeneral> getEbysBusinessGraph(long rolid,long servisMudurluk,String action){
		return documentManagementDAO.getEbysBusinessGraph(rolid,servisMudurluk,action);
	}
	
	public List<GraphGeneral> getEbysBusinessGraphDetail(long rolid,long servisMudurluk, String action,long rolPerformerId){
		return documentManagementDAO.getEbysBusinessGraphDetail(rolid,servisMudurluk,action,rolPerformerId);
	}

	public List<BasvuruOzet> getGelenBasvuruList(long organizationId, String startDate, String endDate){
		return documentManagementDAO.getGelenBasvuruList(organizationId,startDate,endDate);
	}

	public List<BasvuruOzet> getGidenBasvuruList(long organizationId, String startDate, String endDate){
		return documentManagementDAO.getGidenBasvuruList(organizationId,startDate,endDate);
	}

	public List<BasvuruOzet> getUrettiklerimList(long organizationId, String startDate, String endDate){
		return documentManagementDAO.getUrettiklerimList(organizationId,startDate,endDate);
	}

	public List<EBYSDetail> getEbysUnsignableAdditionDocument(long documentId){
		return documentManagementDAO.getEbysUnsignableAdditionDocument(documentId);
	}

}
