package com.digikent.vadirest.dao;

import com.digikent.vadirest.dto.*;
import com.vadi.digikent.sistem.syn.model.SM1Roles;

import java.util.List;


public interface DocumentManagementDAO {
	public List<SM1Roles> getEBYSRollList(long persid);
	public List<EBYSBekleyen> getWaitingEBYS(long persid, long rolid, String startDate, String endDate);
	public List<Rol> getDocRollList(long persid, long mastid);
	public List<BelgeBasvuru> getApplyDoc(long rolid);
	public BelgeBasvuruDetay getApplyDocDetail(long docId);
	public Long getEbysMenuCount(long persid, long rolid, String tur);
	public CozumOrtagi isSolutionPartner(long rolid);
	public List<EBYSKlasorMenu> getEBYSFolderTree(long rolid);
	public List<EBYSBirimMenu> getEBYSUnitTree();
	public List<EBYSBirimMenu> getEBYSSubUnitTree(long bsm2servis);
	public List<EBYSBirimMenu> getEBYSServiceFolderTree(long bsm2servis);
	public List<BelgeYonetimKullanici> getDocManagementUserList(long persid);
	public List<GraphGeneral> getDocManagementGraphIncomingDoc(long year, long servisId, String result);
	public List<GraphGeneral> getDocManagementGraphOutgoingDoc(long year, long servisId, String result);
	public List<GraphGeneral> getEbysBusinessGraph(long rolid, long servisMudurluk, String action);
	public List<GraphGeneral> getEbysBusinessGraphDetail(long rolid, long servisMudurluk, String action, long rolPerformerId);
	public List<BasvuruOzet> getGelenBasvuruList(long organizationId, String startDate, String endDate);
	public List<BasvuruOzet> getGidenBasvuruList(long organizationId, String startDate, String endDate);
	public List<BasvuruOzet> getUrettiklerimList(long organizationId, String startDate, String endDate);

}
