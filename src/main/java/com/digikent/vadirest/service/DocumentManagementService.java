package com.digikent.vadirest.service;

import com.digikent.vadirest.dto.*;
import com.digikent.web.rest.dto.DocumentRejectDTO;
import com.vadi.digikent.sistem.syn.model.SM1Roles;

import java.util.List;



public interface DocumentManagementService {

	public List<SM1Roles> getEBYSRollList(long persid);
	public List<EBYS> getEBYS(String type, long persid, long rolid, String startDate, String endDate);
	public List<EBYS> getWaitingEBYS(long persid, long rolid, String startDate, String endDate);
	public List<EBYSDetail> getEbysDocumentDetail(long documentId);
	public List<EBYSDetail> getEBYSAddition(long documentId);
	public List<Rol> getDocRollList(long persid, long mastid);
	public List<BelgeBasvuru> getApplyDoc(long rolid);
	public BelgeBasvuruDetay getApplyDocDetail(long docId);
	public Long getEbysMenuCount(long persid, long rolid, String tur);
	public CozumOrtagi isSolutionPartner(long rolid);
	public List<EBYSKlasorMenu> getEBYSFolderTree(long rolid);
	public List<EBYSBirimMenu> getEBYSUnitTree();
	public List<EBYSBirimMenu> getEBYSEbysTree();
	public List<BelgeYonetimKullanici> getDocManagementUserList(long persid);
	public List<GraphGeneral> getDocManagementGraphIncomingDoc(long year, long servisId, String result);
	public List<GraphGeneral> getDocManagementGraphOutgoingDoc(long year, long servisId, String result);
	public List<GraphGeneral> getEbysBusinessGraph(long rolid, long servisMudurluk, String action);
	public List<GraphGeneral> getEbysBusinessGraphDetail(long rolid, long servisMudurluk, String action, long rolPerformerId);
	public List<BasvuruOzet> getGelenBasvuruList(long organizationId, String startDate, String endDate);
	public List<BasvuruOzet> getGidenBasvuruList(long organizationId, String startDate, String endDate);
	public List<BasvuruOzet> getUrettiklerimList(long organizationId, String startDate, String endDate);
	public List<EBYSDetail> getEbysUnsignableAdditionDocument(long documentId);
	public Boolean rejectDocument(DocumentRejectDTO documentRejectDTO);
	public List<EBYS> getEBYSParaf(String type, long persid, long rolid, String startDate, String endDate);
	public List<EBYSParafDetailDTO> getEBYSParafDetail(String type, long documentId);
	public List<EBYSParafDetailDTO> getWaitingEBYSParafEkDetail(long documentId);

}
