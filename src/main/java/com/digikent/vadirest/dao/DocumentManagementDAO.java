package com.digikent.vadirest.dao;

import com.digikent.vadirest.dto.BelgeBasvuru;
import com.digikent.vadirest.dto.BelgeBasvuruDetay;
import com.digikent.vadirest.dto.BelgeYonetimKullanici;
import com.digikent.vadirest.dto.CozumOrtagi;
import com.digikent.vadirest.dto.EBYSBekleyen;
import com.digikent.vadirest.dto.EBYSBirimMenu;
import com.digikent.vadirest.dto.EBYSKlasorMenu;
import com.digikent.vadirest.dto.GraphGeneral;
import com.digikent.vadirest.dto.Rol;
import com.vadi.digikent.sistem.syn.model.SM1Roles;

import java.util.List;


public interface DocumentManagementDAO {
	public List<SM1Roles> getEBYSRollList(long persid);
	public List<EBYSBekleyen> getWaitingEBYS(long persid, long rolid);
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
	
}
