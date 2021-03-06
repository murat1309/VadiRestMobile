package com.digikent.vadirest.dao.impl;

import com.digikent.vadirest.dao.DocumentManagementDAO;
import com.digikent.vadirest.dto.*;
import com.digikent.web.rest.dto.DocumentRejectDTO;
import com.digikent.web.rest.dto.EBYSRequestDTO;
import com.vadi.digikent.icerikyonetimi.bpm.model.BPMWorkitem;
import com.vadi.digikent.sistem.syn.model.SM1Roles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;


@Repository("documentManagementDao")
@Transactional
public class DocumentManagementDAOImpl implements DocumentManagementDAO {

	private final Logger LOG = LoggerFactory.getLogger(DocumentManagementDAO.class);

	@Inject
	private Environment env;

	@Autowired
	protected SessionFactory sessionFactory;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private RestTemplate restTemplate = new RestTemplate();
	
	public List<SM1Roles> getEBYSRollList(long persid) {
		
		String sql ="select FSM1ROLES_ID,"
				   +"(SELECT TANIM FROM FSM1ROLES WHERE ID=FSM1ROLES_ID) as TANIM " 
				   +"from fsm1rolesmember " 
				   +"where(SELECT NVL(ISACTIVE,'H') FROM FSM1ROLES WHERE ID=FSM1ROLES_ID) ='E' "  
				   +"AND  fsm1users_id = "+ persid +" ";	
		
		List<Object> list = new ArrayList();
		List<SM1Roles> rollList = new ArrayList();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			SM1Roles sm1roles = new SM1Roles();
			
			BigDecimal fsm1roles_id =(BigDecimal)map.get("FSM1ROLES_ID");
			String tanim = (String)map.get("TANIM");
			
			if(fsm1roles_id != null)
				sm1roles.setId(fsm1roles_id.longValue());			
			if(tanim != null)
				sm1roles.setTanim(tanim);
			rollList.add(sm1roles);
		}
		return rollList;
	}

	public String getEBYSAdaAndParselQueryClause(EBYSRequestDTO ebysRequestDTO) {
		String sql = "";
		if(ebysRequestDTO.getAda() != null && ebysRequestDTO.getParsel() != null)
			sql += "SELECT EBYSDOCUMENT_ID\n" +
					"  FROM ((SELECT TABLEROWNO,\n" +
					"\t\t\t   EBYSDOCUMENT_ID\n" +
					"          FROM EBYSDOCUMENTVALUE\n" +
					"         WHERE DOCUMENTDEFINITIONKODU LIKE 'ADA' AND METINDEGERI LIKE '"+ ebysRequestDTO.getAda() +"'\n" +
					"\t\tAND EBYSVERSION_ID=(SELECT EBYSVERSION_LAST FROM EBYSDOCUMENT WHERE ID=EBYSDOCUMENT_ID )\n" +
					"\t   )\n" +
					"       INTERSECT\n" +
					"       (SELECT TABLEROWNO,\n" +
					"   \t\t\t   EBYSDOCUMENT_ID\n" +
					"          FROM EBYSDOCUMENTVALUE\n" +
					"         WHERE DOCUMENTDEFINITIONKODU LIKE 'PARSEL' AND METINDEGERI LIKE '"+ ebysRequestDTO.getParsel() +"'\n" +
					"        AND EBYSVERSION_ID=(select EBYSVERSION_LAST FROM EBYSDOCUMENT WHERE ID=EBYSDOCUMENT_ID )\n" +
					"       ))";
		else if(ebysRequestDTO.getAda() != null)
			sql += "SELECT \n" +
					"EBYSDOCUMENT_ID \n" +
					"FROM EBYSDOCUMENTVALUE \n" +
					"WHERE DOCUMENTDEFINITIONKODU LIKE 'ADA' AND METINDEGERI LIKE '" + ebysRequestDTO.getAda() + "' AND EBYSVERSION_ID=(select EBYSVERSION_LAST FROM EBYSDOCUMENT WHERE ID=EBYSDOCUMENT_ID )\n" +
					"GROUP BY EBYSDOCUMENT_ID";
		else if(ebysRequestDTO.getParsel() != null)
			sql += "SELECT \n" +
					"EBYSDOCUMENT_ID \n" +
					"FROM EBYSDOCUMENTVALUE \n" +
					"WHERE DOCUMENTDEFINITIONKODU LIKE 'PARSEL' AND METINDEGERI LIKE '" + ebysRequestDTO.getParsel() + "' AND EBYSVERSION_ID=(select EBYSVERSION_LAST FROM EBYSDOCUMENT WHERE ID=EBYSDOCUMENT_ID )\n" +
					"GROUP BY EBYSDOCUMENT_ID";
		return sql;
	}

	public String getEBYSQueryString(String type, EBYSRequestDTO ebysRequestDTO) {
		String sql="SELECT id as ID, ebysdocument_id as EBYSDOCUMENTID,action,creationdate,creationdatetime, "
				+"(SELECT EBYSPAKET_ID FROM EBYSPAKETLINE WHERE A.ebysdocument_id = EBYSDOCUMENT_ID AND TURU='USTYAZI_BIRIM') as PAKETID,"
				+"DECODE ((SELECT NVL (ABYOKONU_ID, 0)FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID), "
				+"0, CASE WHEN (SELECT BPM.F_READ_WORKFLOWVALUE_NUMBER (ABPMWORKFLOW_ID,'EBYSBELGE_ID')FROM DUAL) > 0 "
				+"THEN(SELECT KONUOZETI FROM EBYSBELGE WHERE ID =(SELECT BPM.F_READ_WORKFLOWVALUE_NUMBER (A.ABPMWORKFLOW_ID,'EBYSBELGE_ID')FROM DUAL)) "
				+"ELSE '' END,TASKSUBJECT)KONU, MESSAGE,((SELECT FIRSTNAME || ' ' || LASTNAME FROM FSM1USERS WHERE ID = fsm1users_sentby)|| '(' || (SELECT TANIM "
				+"FROM FSM1ROLES WHERE ID = fsm1roles_sentby) || ')' ) NAME, BPM.F_READ_WORKFLOWVALUE_NUMBER (ABPMWORKFLOW_ID, 'EBYSBELGE_ID') EBYSBELGE_ID, "
				+"(SELECT (SELECT processname FROM ABPMPROCESS WHERE id = ABPMWORKFLOW.ABPMPROCESS_ID) FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_id) TURU, "
				+"TASKCOMMENT, (SELECT BSM2SERVIS_MUDURLUK FROM EBYSBELGE WHERE ID = (SELECT EBYSBELGE_ID FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_ID)) BSM2SERVISID, "
				+"BPM.F_DocumentIdForWorkItem (A.ID) DOCID, EBYSGIDENKAYITNUMARASI, EBYSGELENKAYITNUMARASI, BPM.F_WorkItemPaydasAdres (ID) ADRES, (SELECT TANIM "
				+"FROM EBYSDOCUMENT  WHERE ID = EBYSDOCUMENT_ID) YAZIADI, (SELECT ABYOBASVURU_ID FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID)  ABYOBASVURU_ID, "
				+"(SELECT (SELECT DRE1MAHALLE_ADI  FROM ABYOBASVURU  WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) DRE1MAHALLEADI_BASVURU, "
				+"(SELECT (SELECT SRE1SOKAK_ADI FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) SRE1SOKAKADI_BASVURU, "
				+"(SELECT (SELECT (SELECT TANIM FROM ABYOBASVURUDURUMU WHERE ID = ABYOBASVURU.ABYOBASVURUDURUMU_ID) FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) "
				+"FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) BASVURUDURUMU_ADI, SUBSTR (TASKCOMMENT, 1, 20) AS COZUM, ebysiletimnedeni, "
				+"(SELECT BEKLENENBITISTARIHI FROM EBYSBELGE WHERE ID = A.EBYSBELGE_ID AND A.EBYSBELGE_ID > 0) BEKLENENBITISTARIHI,(SELECT COMPLETEDDATETIME "
				+"FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_ID) COMPLETEDDATETIME, NVL (READFLAG, 'H') READFLAG, TOPLUIMZALAMAYAPILACAK, (SELECT (SELECT B.TANIM "
				+"FROM BSM2SERVIS B WHERE B.ID = E.BSM2SERVIS_MUDURLUK) FROM EBYSBELGE E WHERE E.ID = EBYSBELGE_ID) URETENMUDURLUK, A.ABPMWORKFLOW_ID    FROM ABPMWORKITEM A "
				+" WHERE ( (A.FSM1ROLES_PERFORMER = " + ebysRequestDTO.getRolId() + " AND A.FSM1USERS_PERFORMER = " + ebysRequestDTO.getFsm1UserId()+ " ) "
				+ " OR (A.FSM1ROLES_PERFORMER = " + ebysRequestDTO.getRolId() +" AND A.Fsm1Users_PERFORMER = 0)) ";

		if(type.equalsIgnoreCase("ONAYBEKLEYEN")) {
			sql += " AND A.id > 0 AND A.ACTION = 'PROGRESS' AND A.ABPMTASK_ID IN (SELECT ID FROM ABPMTASK WHERE EIMZAREQUIRED = 'EVET')";
		} else if(type.equalsIgnoreCase("TAMAMLANAN")) {
			sql += " AND A.id > 0 AND  A.ACTION IN ('SEND','CANCEL','REJECT','DELEGATE') " +
					" AND NVL(A.ACTIONDETAIL,'-') NOT IN ('GERICEKILDI','REDDEDILDI') " +
					"AND A.ABPMTASK_ID IN (SELECT T.ID FROM ABPMTASK T WHERE NVL(T.TASLAKMI,'HAYIR') = 'HAYIR')" +
					"AND EXISTS (SELECT 1 FROM ABPMTASK WHERE ID=A.ABPMTASK_ID AND VISIBLEFORCOMPLETEDLIST='EVET')";
		} else {
			sql += " AND A.id > 0  AND A.ACTION = 'PROGRESS'  AND A.ABPMTASK_ID IN (SELECT ID FROM ABPMTASK WHERE KISIYEATANANMI = 'EVET')";
		}

		// tarih, ada, ve parsel filtreleri aşağıdaki kod bloğunda eklenmektedir.
		if(ebysRequestDTO.getAda() != null || ebysRequestDTO.getParsel() != null)
			sql +=" AND A.EBYSDOCUMENT_ID IN (" + getEBYSAdaAndParselQueryClause(ebysRequestDTO) + ") ";
		if(ebysRequestDTO.getStartDate() != null && ebysRequestDTO.getEndDate() != null)
			sql +=   " AND A.CREATIONDATE BETWEEN TO_DATE('"+ ebysRequestDTO.getStartDate() +"', 'dd-MM-yyyy') and"
				+" TO_DATE ('"+ ebysRequestDTO.getEndDate() +"', 'dd-MM-yyyy') ";


		sql += " ORDER BY NVL (BEKLENENBITISTARIHI, TO_DATE ('01/01/9999 00:00:00', 'dd/mm/yyyy Hh24:MI:ss')) ASC, A.CREATIONDATE DESC, A.id DESC ";

		return sql;
	}

	public List<EBYS> getEBYS(String type, EBYSRequestDTO ebysRequestDTO) {
//		String sql="SELECT id as ID, ebysdocument_id as EBYSDOCUMENTID,action,creationdate,creationdatetime, "
//				+"(SELECT EBYSPAKET_ID FROM EBYSPAKETLINE WHERE A.ebysdocument_id = EBYSDOCUMENT_ID AND TURU='USTYAZI_BIRIM') as PAKETID,"
//				+"DECODE ((SELECT NVL (ABYOKONU_ID, 0)FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID), "
//				+"0, CASE WHEN (SELECT BPM.F_READ_WORKFLOWVALUE_NUMBER (ABPMWORKFLOW_ID,'EBYSBELGE_ID')FROM DUAL) > 0 "
//				+"THEN(SELECT KONUOZETI FROM EBYSBELGE WHERE ID =(SELECT BPM.F_READ_WORKFLOWVALUE_NUMBER (A.ABPMWORKFLOW_ID,'EBYSBELGE_ID')FROM DUAL)) "
//				+"ELSE '' END,TASKSUBJECT)KONU, MESSAGE,((SELECT FIRSTNAME || ' ' || LASTNAME FROM FSM1USERS WHERE ID = fsm1users_sentby)|| '(' || (SELECT TANIM "
//				+"FROM FSM1ROLES WHERE ID = fsm1roles_sentby) || ')' ) NAME, BPM.F_READ_WORKFLOWVALUE_NUMBER (ABPMWORKFLOW_ID, 'EBYSBELGE_ID') EBYSBELGE_ID, "
//				+"(SELECT (SELECT processname FROM ABPMPROCESS WHERE id = ABPMWORKFLOW.ABPMPROCESS_ID) FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_id) TURU, "
//				+"TASKCOMMENT, (SELECT BSM2SERVIS_MUDURLUK FROM EBYSBELGE WHERE ID = (SELECT EBYSBELGE_ID FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_ID)) BSM2SERVISID, "
//				+"BPM.F_DocumentIdForWorkItem (A.ID) DOCID, EBYSGIDENKAYITNUMARASI, EBYSGELENKAYITNUMARASI, BPM.F_WorkItemPaydasAdres (ID) ADRES, (SELECT TANIM "
//				+"FROM EBYSDOCUMENT  WHERE ID = EBYSDOCUMENT_ID) YAZIADI, (SELECT ABYOBASVURU_ID FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID)  ABYOBASVURU_ID, "
//				+"(SELECT (SELECT DRE1MAHALLE_ADI  FROM ABYOBASVURU  WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) DRE1MAHALLEADI_BASVURU, "
//				+"(SELECT (SELECT SRE1SOKAK_ADI FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) SRE1SOKAKADI_BASVURU, "
//				+"(SELECT (SELECT (SELECT TANIM FROM ABYOBASVURUDURUMU WHERE ID = ABYOBASVURU.ABYOBASVURUDURUMU_ID) FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) "
//				+"FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) BASVURUDURUMU_ADI, SUBSTR (TASKCOMMENT, 1, 20) AS COZUM, ebysiletimnedeni, "
//				+"(SELECT BEKLENENBITISTARIHI FROM EBYSBELGE WHERE ID = A.EBYSBELGE_ID AND A.EBYSBELGE_ID > 0) BEKLENENBITISTARIHI,(SELECT COMPLETEDDATETIME "
//				+"FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_ID) COMPLETEDDATETIME, NVL (READFLAG, 'H') READFLAG, TOPLUIMZALAMAYAPILACAK, (SELECT (SELECT B.TANIM "
//				+"FROM BSM2SERVIS B WHERE B.ID = E.BSM2SERVIS_MUDURLUK) FROM EBYSBELGE E WHERE E.ID = EBYSBELGE_ID) URETENMUDURLUK, A.ABPMWORKFLOW_ID    FROM ABPMWORKITEM A ";
//
//		if(type.equalsIgnoreCase("ONAYBEKLEYEN"))
//			sql += "WHERE A.id > 0 AND A.ACTION = 'PROGRESS' AND A.ABPMTASK_ID IN (SELECT ID FROM ABPMTASK WHERE EIMZAREQUIRED = 'EVET')";
//		else if(type.equalsIgnoreCase("TAMAMLANAN"))
//			sql += "WHERE A.id > 0 AND  A.ACTION IN ('SEND','CANCEL','REJECT','DELEGATE') " +
//					" AND NVL(A.ACTIONDETAIL,'-') NOT IN ('GERICEKILDI','REDDEDILDI') " +
//					 "AND A.ABPMTASK_ID IN (SELECT T.ID FROM ABPMTASK T WHERE NVL(T.TASLAKMI,'HAYIR') = 'HAYIR')" +
//					"AND EXISTS (SELECT 1 FROM ABPMTASK WHERE ID=A.ABPMTASK_ID AND VISIBLEFORCOMPLETEDLIST='EVET')";
//		else if(type.equalsIgnoreCase("IADEEDILEN"))
//			sql += "WHERE A.id > 0  AND A.EBYSBELGEDURUMU = 'IADE' AND A.ISEBYSWORKITEM = 'E'";
//		else
//			sql += "WHERE A.id > 0  AND A.ACTION = 'PROGRESS'  AND A.ABPMTASK_ID IN (SELECT ID FROM ABPMTASK WHERE KISIYEATANANMI = 'EVET')";
//
//		sql +=   " AND A.CREATIONDATE BETWEEN TO_DATE('"+ ebysRequestDTO.getStartDate()  +"', 'dd-MM-yyyy') and"
//				+" TO_DATE ('"+ ebysRequestDTO.getEndDate() +"', 'dd-MM-yyyy')"
//				+" AND ( (A.FSM1ROLES_PERFORMER = " + ebysRequestDTO.getRolId() +" AND A.FSM1USERS_PERFORMER = " + ebysRequestDTO.getFsm1UserId() +" ) "
//				+"OR (A.FSM1ROLES_PERFORMER = " + ebysRequestDTO.getRolId() +" AND A.Fsm1Users_PERFORMER = 0)) ORDER BY NVL (BEKLENENBITISTARIHI, "
//				+"TO_DATE ('01/01/9999 00:00:00', 'dd/mm/yyyy Hh24:MI:ss')) ASC, A.CREATIONDATE DESC, A.id DESC ";

		String sql = getEBYSQueryString(type, ebysRequestDTO);

		List<Object> list = new ArrayList();
		List<EBYS> ebysList = new ArrayList();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map)o;
			EBYS ebys = new EBYS();
			BPMWorkitem bpmWorkItem = new BPMWorkitem();

			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal ebysDocumentId = (BigDecimal)map.get("EBYSDOCUMENTID");
			Date creationDate = (Date)map.get("CREATIONDATE");
			String name = (String)map.get("NAME");
			String konu = (String)map.get("KONU");
			String message = (String) map.get("MESSAGE");
			BigDecimal docId = (BigDecimal) map.get("DOCID");
			BigDecimal paketId = (BigDecimal)map.get("PAKETID");
			BigDecimal workFlowId = (BigDecimal)map.get("ABPMWORKFLOW_ID");

			if(id != null)
				ebys.setId(id.longValue());
			if(ebysDocumentId != null)
				ebys.setEbysDocumentId(ebysDocumentId.longValue());
			if(creationDate != null)
				ebys.setCreationDate(dateFormat.format(creationDate));
			if(name != null)
				ebys.setName(name);
			if(konu != null)
				ebys.setKonu(konu);
			if(message != null)
				ebys.setMessage(message);
			if(docId != null)
				ebys.setDocId(docId.longValue());
			if(paketId != null)
				ebys.setPaketId(paketId.longValue());
			if(workFlowId != null)
				ebys.setWorkFlowId(workFlowId.longValue());

			ebysList.add(ebys);
		}
		return ebysList;
	}

	public List<EBYSDetail> getEBYSAddition(long documentId) {
/*		String sql= "select C.ID,D.EBYSDOCUMENT_ID,(SELECT X.TANIM FROM EBYSDOCUMENT X WHERE X.ID = D.EBYSDOCUMENT_ID) DokumanAdi,DECODE(C.DOCUMENTDEFINITIONKODU,'ILGI','İlgi','DOCLINK','Eklenti','ILISKILIBELGE','İlişkili Belge','-')," +
				" C.CRUSER,C.DOCUMENTDEFINITIONKODU " +
				" from EBYSDOCUMENT A,EBYSVERSION B,EBYSDOCUMENTVALUE C,EBYSFOLDERLINK D " +
				" Where A.EBYSVERSION_LAST = B.ID And A.ID = C.EBYSDOCUMENT_ID AND A.EBYSVERSION_LAST = C.EBYSVERSION_ID AND  C.EBYSFOLDERLINK_ID = D.ID " +
				" And C.EBYSDOCUMENT_ID  = " + documentId +
				" And C.DOCUMENTDEFINITIONKODU IN ('DOCLINK','ILGI','ILISKILIBELGE') AND D.ID>0";*/



		String sql = "SELECT ID,TANIM,'ÜST YAZI' as TURU FROM EBYSDOCUMENT WHERE ID= " + documentId +
				"                               UNION " +
				"                             select D.EBYSDOCUMENT_ID, (SELECT X.TANIM FROM EBYSDOCUMENT X WHERE X.ID = D.EBYSDOCUMENT_ID)," +
				"							DECODE(C.DOCUMENTDEFINITIONKODU, 'ILGI','İlgi','DOCLINK','Eklenti','ILISKILIBELGE','İlişkili Belge','-')" +
				"                               from EBYSDOCUMENT A,EBYSVERSION B,EBYSDOCUMENTVALUE C,EBYSFOLDERLINK D" +
				"                               Where A.EBYSVERSION_LAST = B.ID And A.ID = C.EBYSDOCUMENT_ID" +
				"                               AND A.EBYSVERSION_LAST = C.EBYSVERSION_ID AND  C.EBYSFOLDERLINK_ID = D.ID" +
				"                               And C.EBYSDOCUMENT_ID  = " + documentId +
				"                              And C.DOCUMENTDEFINITIONKODU IN ('DOCLINK', 'ILGI','ILISKILIBELGE') AND D.ID>0";

		List<Object> list = new ArrayList();
		List<EBYSDetail> ebysDetailList = new ArrayList();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		System.out.println("bu");

		for(Object o : list){
			Map map = (Map)o;
			EBYSDetail ebysDetail = new EBYSDetail();

			BigDecimal cid = (BigDecimal)map.get("CID");
			BigDecimal aid =(BigDecimal)map.get("ID");
			BigDecimal onaySirasi =(BigDecimal)map.get("ONAYSIRASI");
			String tanim = (String)map.get("TANIM");
			String durumu = (String)map.get("DURUMU");
			String onayDurumu = (String)map.get("ONAYDURUMU");
			String onayTipi = (String)map.get("ONAYTIPI");
			String adSoyad = (String)map.get("ADSOYAD");
			BigDecimal ihrPersonelId =(BigDecimal)map.get("IHR1PERSONEL_ID");
			String dokumanTuru = (String)map.get("TURU");
			String hitapEki = (String)map.get("HITAPEKI");
			BigDecimal ebysVersionId=(BigDecimal)map.get("EBYSVERSION_ID");
			BigDecimal bpmTaskId=(BigDecimal)map.get("BPMTASKID");
			String eImzasizParaflama = (String)map.get("EIMZASIZPARAFLAMA");
			BigDecimal ebysDocumentOnay = (BigDecimal)map.get("EBYSDOCUMENT_ONAY");
			BigDecimal masterIdDagitim = (BigDecimal)map.get("MASTERID_DAGITIM");
			BigDecimal paketId = (BigDecimal)map.get("PAKETID") ;
			String onaySirasiTuru = (String)map.get("OS") ;
			BigDecimal contentId = (BigDecimal)map.get("CONTENTID");

			if(cid != null)
				ebysDetail.setOnayId(cid.longValue());
			if(aid != null)
				ebysDetail.setDocumentId(aid.longValue());
			if(onaySirasi != null)
				ebysDetail.setOnaySirasi(onaySirasi.longValue());
			if(tanim != null)
				ebysDetail.setTanim(tanim);
			if(durumu != null)
				ebysDetail.setDurumu(durumu);
			if(onayDurumu != null)
				ebysDetail.setOnayDurumu(onayDurumu);
			if(onayTipi != null)
				ebysDetail.setOnayTipi(onayTipi);
			if(adSoyad != null)
				ebysDetail.setAdSoyad(adSoyad);
			if(ihrPersonelId != null)
				ebysDetail.setIhr1PersonelId(ihrPersonelId.longValue());
			if(dokumanTuru != null)
				ebysDetail.setDokumanTuru(dokumanTuru);
			if(hitapEki != null)
				ebysDetail.setHitapEki(hitapEki);
			if(ebysVersionId != null)
				ebysDetail.setEbysversiyonId(ebysVersionId.longValue());
			if(bpmTaskId != null)
				ebysDetail.setBpmTaskId(bpmTaskId.longValue());
			if(eImzasizParaflama != null)
				ebysDetail.seteImzasizParaflama(eImzasizParaflama);
			if (ebysDocumentOnay != null)
				ebysDetail.setEbysDocumentOnayId(ebysDocumentOnay.longValue());
			if(masterIdDagitim != null)
				ebysDetail.setMasterIdDagitim(masterIdDagitim.longValue());
			if(paketId != null)
				ebysDetail.setPaketId(paketId.longValue());
			if(onaySirasiTuru != null)
				ebysDetail.setOnaySirasiTuru(onaySirasiTuru);
			if(contentId != null)
				ebysDetail.setContentId(contentId.longValue());

			ebysDetailList.add(ebysDetail);
		}
		return ebysDetailList;
	}



	public List<EBYS> getWaitingEBYS(long persid, long rolid, String startDate, String endDate) {
		String sql="SELECT id as ID, ebysdocument_id as EBYSDOCUMENTID,action,creationdate,creationdatetime, " +
				"(SELECT EBYSPAKET_ID FROM EBYSPAKETLINE WHERE A.ebysdocument_id = EBYSDOCUMENT_ID AND TURU='USTYAZI_BIRIM') as PAKETID,"
				+"DECODE ((SELECT NVL (ABYOKONU_ID, 0)FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID), "
				+"0, CASE WHEN (SELECT BPM.F_READ_WORKFLOWVALUE_NUMBER (ABPMWORKFLOW_ID,'EBYSBELGE_ID')FROM DUAL) > 0 "
				+"THEN(SELECT KONUOZETI FROM EBYSBELGE WHERE ID =(SELECT BPM.F_READ_WORKFLOWVALUE_NUMBER (A.ABPMWORKFLOW_ID,'EBYSBELGE_ID')FROM DUAL)) "
				+"ELSE '' END,TASKSUBJECT)KONU, MESSAGE,((SELECT FIRSTNAME || ' ' || LASTNAME FROM FSM1USERS WHERE ID = fsm1users_sentby)|| '(' || (SELECT TANIM "
				+"FROM FSM1ROLES WHERE ID = fsm1roles_sentby) || ')' ) NAME, BPM.F_READ_WORKFLOWVALUE_NUMBER (ABPMWORKFLOW_ID, 'EBYSBELGE_ID') EBYSBELGE_ID, "
				+"(SELECT (SELECT processname FROM ABPMPROCESS WHERE id = ABPMWORKFLOW.ABPMPROCESS_ID) FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_id) TURU, "
				+"TASKCOMMENT, (SELECT BSM2SERVIS_MUDURLUK FROM EBYSBELGE WHERE ID = (SELECT EBYSBELGE_ID FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_ID)) BSM2SERVISID, "
				+"BPM.F_DocumentIdForWorkItem (A.ID) DOCID, EBYSGIDENKAYITNUMARASI, EBYSGELENKAYITNUMARASI, BPM.F_WorkItemPaydasAdres (ID) ADRES, (SELECT TANIM "
				+"FROM EBYSDOCUMENT  WHERE ID = EBYSDOCUMENT_ID) YAZIADI, (SELECT ABYOBASVURU_ID FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID)  ABYOBASVURU_ID, "
				+"(SELECT (SELECT DRE1MAHALLE_ADI  FROM ABYOBASVURU  WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) DRE1MAHALLEADI_BASVURU, "
				+"(SELECT (SELECT SRE1SOKAK_ADI FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) SRE1SOKAKADI_BASVURU, "
				+"(SELECT (SELECT (SELECT TANIM FROM ABYOBASVURUDURUMU WHERE ID = ABYOBASVURU.ABYOBASVURUDURUMU_ID) FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) "
				+"FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID) BASVURUDURUMU_ADI, SUBSTR (TASKCOMMENT, 1, 20) AS COZUM, ebysiletimnedeni, "
				+"(SELECT BEKLENENBITISTARIHI FROM EBYSBELGE WHERE ID = A.EBYSBELGE_ID AND A.EBYSBELGE_ID > 0) BEKLENENBITISTARIHI,(SELECT COMPLETEDDATETIME "
				+"FROM ABPMWORKFLOW WHERE ID = A.ABPMWORKFLOW_ID) COMPLETEDDATETIME, NVL (READFLAG, 'H') READFLAG, TOPLUIMZALAMAYAPILACAK, (SELECT (SELECT B.TANIM "
				+"FROM BSM2SERVIS B WHERE B.ID = E.BSM2SERVIS_MUDURLUK) FROM EBYSBELGE E WHERE E.ID = EBYSBELGE_ID) URETENMUDURLUK    FROM ABPMWORKITEM A "
				+"WHERE A.id > 0 AND A.ACTION = 'PROGRESS' AND A.ABPMTASK_ID IN (SELECT ID FROM ABPMTASK WHERE ONAYBEKLEYENMI = 'EVET')"
				+" AND A.CREATIONDATE BETWEEN TO_DATE('"+ startDate +"', 'dd-MM-yyyy') and"
				+" TO_DATE ('"+ endDate +"', 'dd-MM-yyyy')"
				+" AND ( (A.FSM1ROLES_PERFORMER = " + rolid+" AND A.FSM1USERS_PERFORMER = " +persid+" ) "
				+"OR (A.FSM1ROLES_PERFORMER = " + rolid+" AND A.Fsm1Users_PERFORMER = 0)) ORDER BY NVL (BEKLENENBITISTARIHI, "
				+"TO_DATE ('01/01/9999 00:00:00', 'dd/mm/yyyy Hh24:MI:ss')) ASC, A.CREATIONDATE DESC, A.id DESC ";
		
		List<Object> list = new ArrayList();
		List<EBYS> ebysList = new ArrayList();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			EBYS ebys = new EBYS();
			BPMWorkitem bpmWorkItem = new BPMWorkitem();

			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal ebysDocumentId = (BigDecimal)map.get("EBYSDOCUMENTID");
			Date creationDate = (Date)map.get("CREATIONDATE");
			String name = (String)map.get("NAME");
			String konu = (String)map.get("KONU");
			String message = (String) map.get("MESSAGE");
			BigDecimal docId = (BigDecimal) map.get("DOCID");
			BigDecimal paketId = (BigDecimal)map.get("PAKETID");


			if(id != null)
				ebys.setId(id.longValue());
			if(ebysDocumentId != null)
				ebys.setEbysDocumentId(ebysDocumentId.longValue());
			if(creationDate != null)
				ebys.setCreationDate(dateFormat.format(creationDate));
			if(name != null)
				ebys.setName(name);
			if(konu != null)
				ebys.setKonu(konu);
			if(message != null)
				ebys.setMessage(message);
			if(docId != null)
				ebys.setDocId(docId.longValue());
			if(paketId != null)
				ebys.setPaketId(paketId.longValue());
			
			ebysList.add(ebys);
		}
		return ebysList;
	}


	public String getEbysDocumentDetailQueryString(long documentId) {
		String sql = "WITH TUMCE \n" +
				"     AS (SELECT /*+ inline*/ \n" +
				"               c.ID                    CID, \n" +
				"                a.ID                   AID, \n" +
				"                C.ONAYSIRASI, \n" +
				"                a.TANIM, \n" +
				"                (SELECT DURUMU \n" +
				"                   FROM EBYSVERSION \n" +
				"                  WHERE ID = c.EBYSVERSION_ID) \n" +
				"                   DURUMU, \n" +
				"                C.ONAYDURUMU, \n" +
				"                C.ONAYTIPI, \n" +
				"                (SELECT ADI || ' ' || SOYADI \n" +
				"                   FROM IHR1PERSONEL \n" +
				"                  WHERE ID = C.IHR1PERSONEL_ID) \n" +
				"                   ADSOYAD, \n" +
				"                C.IHR1PERSONEL_ID, \n" +
				"                c.DOKUMANTURU, \n" +
				"                c.hitapeki, \n" +
				"                C.EBYSVERSION_ID, \n" +
				"                NVL (C.ABPMTASK_ID, 0) BPMTASKID, \n" +
				"                (SELECT NVL (EIMZASIZPARAFLAMA, 'H') \n" +
				"                   FROM IHR1PERSONEL \n" +
				"                  WHERE ID = C.IHR1PERSONEL_ID) \n" +
				"                   EIMZASIZPARAFLAMA, \n" +
				"                C.EBYSDOCUMENT_ID      EBYSDOCUMENT_ONAY, \n" +
				"                a.MASTERID_DAGITIM, \n" +
				"               (SELECT EBYSPAKET_ID FROM EBYSPAKETLINE WHERE TURU = 'USTYAZI_BIRIM' AND EBYSDOCUMENT_ID = a.id) PAKETID,  \n" +
				"               (SELECT ONAYSIRASI FROM EBYSONAYTURU WHERE KAYITOZELISMI=C.ONAYTIPI) OS, \n" +
				"               (SELECT MAX(EBYSCONTENT_ID) FROM EBYSDOCUMENTVALUE V WHERE V.DOCUMENTDEFINITIONKODU='PDFDOKUMAN' AND V.EBYSDOCUMENT_ID=a.ID  ) CONTENTID\n" +
				"           FROM EBYSDOCUMENT a, EBYSONAY c \n" +
				"          WHERE a.EBYSVERSION_LAST = c.EBYSVERSION_ID) \n" +
				"SELECT * \n" +
				"  FROM TUMCE \n" +
				" WHERE (EBYSDOCUMENT_ONAY = " + documentId + ") \n" +
				"UNION \n" +
				"SELECT * \n" +
				"  FROM TUMCE \n" +
				" WHERE (MASTERID_DAGITIM = " + documentId + ") \n" +
				"ORDER BY 2, 3 ";

		return sql;
	}

	public List<EBYSDetail> getEbysDocumentDetail(long documentId){

		String sql = getEbysDocumentDetailQueryString(documentId);

		List<Object> list = new ArrayList();
		List<EBYSDetail> ebysDetailList = new ArrayList();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map)o;
			EBYSDetail ebysDetail = new EBYSDetail();

			BigDecimal cid = (BigDecimal)map.get("CID");
			BigDecimal aid =(BigDecimal)map.get("AID");
			BigDecimal onaySirasi =(BigDecimal)map.get("ONAYSIRASI");
			String tanim = (String)map.get("TANIM");
			String durumu = (String)map.get("DURUMU");
			String onayDurumu = (String)map.get("ONAYDURUMU");
			String onayTipi = (String)map.get("ONAYTIPI");
			String adSoyad = (String)map.get("ADSOYAD");
			BigDecimal ihrPersonelId =(BigDecimal)map.get("IHR1PERSONEL_ID");
			String dokumanTuru = (String)map.get("DOKUMANTURU");
			String hitapEki = (String)map.get("HITAPEKI");
			BigDecimal ebysVersionId=(BigDecimal)map.get("EBYSVERSION_ID");
			BigDecimal bpmTaskId=(BigDecimal)map.get("BPMTASKID");
			String eImzasizParaflama = (String)map.get("EIMZASIZPARAFLAMA");
			BigDecimal ebysDocumentOnay = (BigDecimal)map.get("EBYSDOCUMENT_ONAY");
			BigDecimal masterIdDagitim = (BigDecimal)map.get("MASTERID_DAGITIM");
			BigDecimal paketId = (BigDecimal)map.get("PAKETID") ;
			String onaySirasiTuru = (String)map.get("OS") ;
			BigDecimal contentId = (BigDecimal)map.get("CONTENTID");

			if(cid != null)
				ebysDetail.setOnayId(cid.longValue());
			if(aid != null)
				ebysDetail.setDocumentId(aid.longValue());
			if(onaySirasi != null)
				ebysDetail.setOnaySirasi(onaySirasi.longValue());
			if(tanim != null)
				ebysDetail.setTanim(tanim);
			if(durumu != null)
				ebysDetail.setDurumu(durumu);
			if(onayDurumu != null)
				ebysDetail.setOnayDurumu(onayDurumu);
			if(onayTipi != null)
				ebysDetail.setOnayTipi(onayTipi);
			if(adSoyad != null)
				ebysDetail.setAdSoyad(adSoyad);
			if(ihrPersonelId != null)
				ebysDetail.setIhr1PersonelId(ihrPersonelId.longValue());
			if(dokumanTuru != null) {
                if (dokumanTuru.contains("Asıl") && paketId != null && paketId.intValue() > 0) {
					LOG.debug("Asil dokuman, imzalama durumu kontrol edilecek. DocId = " + documentId);
					ebysDetail.setCanBeSign(isCanBeSign(ebysDocumentOnay, ihrPersonelId));
					LOG.debug("setCanBeSign değeri = " + ebysDetail.getCanBeSign());
				} else {
					LOG.debug("Asil dokuman olmadıgı için imzalanabilir deger true atandı");
					ebysDetail.setCanBeSign(true);
				}
				ebysDetail.setDokumanTuru(dokumanTuru);
            }
			if(hitapEki != null)
				ebysDetail.setHitapEki(hitapEki);
			if(ebysVersionId != null)
				ebysDetail.setEbysversiyonId(ebysVersionId.longValue());
			if(bpmTaskId != null)
				ebysDetail.setBpmTaskId(bpmTaskId.longValue());
			if(eImzasizParaflama != null)
				ebysDetail.seteImzasizParaflama(eImzasizParaflama);
			if (ebysDocumentOnay != null)
				ebysDetail.setEbysDocumentOnayId(ebysDocumentOnay.longValue());
			if(masterIdDagitim != null)
				ebysDetail.setMasterIdDagitim(masterIdDagitim.longValue());
			if(paketId != null)
				ebysDetail.setPaketId(paketId.longValue());
			if(onaySirasiTuru != null)
				ebysDetail.setOnaySirasiTuru(onaySirasiTuru);
			if(contentId != null)
				ebysDetail.setContentId(contentId.longValue());

			ebysDetailList.add(ebysDetail);
		}
		return ebysDetailList;
	}

    public Boolean isCanBeSign(BigDecimal docId, BigDecimal userId) {
		LOG.debug("iscanbeSign kontrol ediliyor");
        List<Object> list = new ArrayList();
        String sql = "SELECT COUNT (*)" +
                "  FROM EBYSONAY" +
                " WHERE     EBYSONAY.ONAYDURUMU = 'ONAYBEKLIYOR'" +
                "       AND EBYSONAY.IHR1PERSONEL_ID = " + userId +
                "       AND EBYSONAY.DOKUMANTURU LIKE 'E%' " +
                "   AND EBYSONAY.EBYSDOCUMENT_ID = " + docId;

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        list = query.list();

        for(Object o : list) {
            Map map = (Map) o;
			LOG.debug("COUNT degeri = " + ((BigDecimal)map.get("COUNT(*)")).longValue());
            return !((((BigDecimal)map.get("COUNT(*)")).longValue() > 0));
        }

        return false;
    }

	public List<Rol> getDocRollList(long persid, long mastid) {
		String sql="SELECT A.ID,B.MSM2ORGANIZASYON_ID,A.ADI||' '||A.SOYADI||' ('|| (SELECT TANIM FROM MSM2ORGANIZASYON WHERE ID = B.MSM2ORGANIZASYON_ID) ||')' as ROLNAME "
      +"FROM IHR1PERSONEL A,IHR1PERSONELORGANIZASYON B WHERE A.ID = B.IHR1PERSONEL_ID AND A.ID ="+ persid
      +" UNION all SELECT A.ID,A.MSM2ORGANIZASYON_ID,A.ADI||' '||A.SOYADI||' ('|| (SELECT TANIM FROM MSM2ORGANIZASYON WHERE ID = A.MSM2ORGANIZASYON_ID) ||')' "
      +"FROM IHR1PERSONEL A WHERE A.MSM2ORGANIZASYON_ID IN (SELECT MSM2ORGANIZASYON_ID FROM MSM2ORGANIZASYON_YETKILI WHERE MSM2ORGANIZASYON_MASTER ="+ mastid+")";

		List<Object> list = new ArrayList();
		List<Rol> rolList = new ArrayList();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for(Object o : list){
			Map map = (Map) o;
			Rol rol = new Rol();
			
			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal msm2OrganizationId =(BigDecimal)map.get("MSM2ORGANIZASYON_ID");
			String rolName = (String)map.get("ROLNAME");
			
			if(id != null)
				rol.setId(id.longValue());
			if(msm2OrganizationId != null)
				rol.setMsm2OrganizationId(msm2OrganizationId.longValue());
			if(rolName != null)
				rol.setTanim(rolName);
			
			rolList.add(rol);
		}
		
		return rolList;
	}
	
	public List<BelgeBasvuru> getApplyDoc(long rolid){
		String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		System.out.println(currentDate);
		
		Date d = new Date();//initialize your date to any date 
		Date dateBefore = new Date(d.getTime() - 7 * 24 * 3600 * 1000 );
		String lastWeekDate = new SimpleDateFormat("dd/MM/yyyy").format(dateBefore);
		String sql ="select  DB.ISAKISIKONUOZETI,DB.TARIH,DB.ID,DBI.GIRISDEFTERNUMARASI,DB.SONUCDURUMU,DB.TURU from EDM1ISAKISIADIM DBI, DDM1ISAKISI DB "
				+" WHERE DB.ID = DBI.DDM1ISAKISI_ID  and(DB.TARIH between TO_DATE('"+lastWeekDate+"','dd/MM/yyyy')  AND TO_DATE('"+currentDate+"','dd/MM/yyyy')) AND "
				+" DB.TURU IN ('E','K','D') AND DBI.ALC_MSM2ORGANIZASYON_ID = "+rolid +" AND DBI.ALC_MSM2ORGANIZASYON_ID <> DBI.GON_MSM2ORGANIZASYON_ID AND DBI.DURUMU = 'S' And DBI.SONUCDURUMU NOT IN ('T')"
				+"union select  DB.ISAKISIKONUOZETI,DB.TARIH,DB.ID,DBI.GIRISDEFTERNUMARASI,DB.SONUCDURUMU,DB.TURU from EDM1ISAKISIADIM DBI, DDM1ISAKISI DB "
				+" WHERE DB.ID = DBI.DDM1ISAKISI_ID  and DB.TURU IN ('S') AND DBI.ALC_MSM2ORGANIZASYON_ID = "+rolid+" AND DBI.ALC_MSM2ORGANIZASYON_ID <> DBI.GON_MSM2ORGANIZASYON_ID AND DBI.DURUMU = 'S' And DBI.SONUCDURUMU NOT IN ('T')"
				+" AND  DB.ID IN (SELECT DDM1ISAKISI_ID FROM EDM1ISAKISIADIM WHERE  (TARIH BETWEEN TO_DATE('"+lastWeekDate +"','dd/MM/yyyy') AND TO_DATE('"+currentDate+"','dd/MM/yyyy')))"
				+"order by TARIh DESC,ID";

//		String sql ="select  DB.ISAKISIKONUOZETI,DB.TARIH,DB.ID,DBI.GIRISDEFTERNUMARASI,DB.SONUCDURUMU,DB.TURU from EDM1ISAKISIADIM DBI, DDM1ISAKISI DB "
//				+" WHERE DB.ID = DBI.DDM1ISAKISI_ID  and "
//				+" DB.TURU IN ('E','K','D') AND DBI.ALC_MSM2ORGANIZASYON_ID = "+rolid +" AND DBI.ALC_MSM2ORGANIZASYON_ID <> DBI.GON_MSM2ORGANIZASYON_ID AND DBI.DURUMU = 'S' And DBI.SONUCDURUMU NOT IN ('T')"
//				+"union select  DB.ISAKISIKONUOZETI,DB.TARIH,DB.ID,DBI.GIRISDEFTERNUMARASI,DB.SONUCDURUMU,DB.TURU from EDM1ISAKISIADIM DBI, DDM1ISAKISI DB "
//				+" WHERE DB.ID = DBI.DDM1ISAKISI_ID  and DB.TURU IN ('S') AND DBI.ALC_MSM2ORGANIZASYON_ID = "+rolid+" AND DBI.ALC_MSM2ORGANIZASYON_ID <> DBI.GON_MSM2ORGANIZASYON_ID AND DBI.DURUMU = 'S' And DBI.SONUCDURUMU NOT IN ('T')"
//				+" AND  DB.ID IN (SELECT DDM1ISAKISI_ID FROM EDM1ISAKISIADIM )"
//				+"order by TARIh DESC,ID";

		List<Object> list = new ArrayList();
		List<BelgeBasvuru> belgeBasvuruList = new ArrayList();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		for(Object o : list){
			Map map = (Map)o;
			BelgeBasvuru belgeBasvuru = new BelgeBasvuru();
			
			BigDecimal girisDefterNumarasi = (BigDecimal)map.get("GIRISDEFTERNUMARASI");
			BigDecimal id = (BigDecimal)map.get("ID");
			String turu =(String) map.get("TURU");
			String isAkisiKonuOzeti = (String) map.get("ISAKISIKONUOZETI");
			Date tarih = (Date) map.get("TARIH");
			
			if(girisDefterNumarasi != null)
				belgeBasvuru.setGirisDefterNumarasi(girisDefterNumarasi.longValue());
			if(id != null)
				belgeBasvuru.setId(id.longValue());
			if(turu != null)
				belgeBasvuru.setTuru(turu);
			if(isAkisiKonuOzeti != null)
				belgeBasvuru.setIsAkisiKonuOzeti(isAkisiKonuOzeti);
			if(tarih != null)
				belgeBasvuru.setTarih(dateFormat.format(tarih));
			
			belgeBasvuruList.add(belgeBasvuru);
		}
		
		return belgeBasvuruList;
	}

	public boolean hasAuthorization(Map map, long msm2OrganizationId, long organizasyonGizlilikSeviyesi) {

		String kayitDerecesi = (String) map.get("KAYITDERECESI");
		long docMsm2OrganizationId = ((BigDecimal) map.get("MSM2ORGANIZASYON_ID")).longValue();
		boolean isOrgutlerAynimi = docMsm2OrganizationId == msm2OrganizationId;

		boolean isAuthorized;
		if(isOrgutlerAynimi) {
			isAuthorized = true;
		} else if(kayitDerecesi != null && organizasyonGizlilikSeviyesi >= Integer.parseInt(kayitDerecesi)) {
			isAuthorized = true;
		} else {
			isAuthorized =  kayitDerecesi == null;
		}

		return isAuthorized;
	}

	public void setBelgeBasvuruDetay(BelgeBasvuruDetay belgeBasvuruDetay, Map map, boolean isAuthorized) {

		String hideField = "**********";

		String adi = (String) map.get("ADISOYADI");
		String soyadi = (String) map.get("SOYADI");
		String konuTuru = (String) map.get("TANIM");
		String konusu = (String) map.get("KONUSU");
		String ekBilgi = (String) map.get("EKBILGI");
		String isAkisKonuOzeti = (String) map.get("ISAKISIKONUOZETI");
		String izahat = (String) map.get("IZAHAT");
		String telefonNumarasi = (String) map.get("TELEFONNUMARASI");
		String isTelefonu = (String) map.get("ISTELEFONU");
		BigDecimal cepTelefonu = (BigDecimal) map.get("CEPTELEFONU");
		String elektronikPosta = (String) map.get("ELEKTRONIKPOSTA");
		String babaAdi = (String) map.get("BABAADI");
		BigDecimal tcKimlikNo =(BigDecimal) map.get("TCKIMLIKNO");

		BigDecimal id = (BigDecimal)map.get("ID");
		BigDecimal referansNo = (BigDecimal)map.get("ILGIID");
		String tip = (String) map.get("tip");
		BigDecimal disaridanGelenBelgeNo = (BigDecimal) map.get("DISARIDANGELENBELGENO");
		BigDecimal defterKaydiGirisNo = (BigDecimal) map.get("DEFTERKAYDIGIRISNO");
		Date tarih = (Date) map.get("TARIH");
		Timestamp tarihSaat = (Timestamp) map.get("TARIHSAAT");
		Date belgeTarih = (Date)map.get("BELGETARIHI");
		String uretimTipi = (String)map.get("URETIMTIPI");
		BigDecimal edm1IsAkisiAdimId = (BigDecimal) map.get("EDM1ISAKISIADIM_ID");
		String merciKurum = (String) map.get("MERCIKURUM");
		String sdpKodu = (String) map.get("SDPKODU");

		String gizlilik = (String) map.get("GIZLILIK");
		String onemDerecesi = (String)map.get("ONEMDERECESI");
		BigDecimal ekliSayfaSayisi = (BigDecimal) map.get("EKLISAYFASAYISI");
		String digerEkler = (String) map.get("DIGEREKLER");
		String dosyaNo = (String) map.get("DOSYANO");
		Date beklenenBitisTarihi = (Date) map.get("BEKLENENBITISTARIHI");
		String belgeDurumu = (String) map.get("BELGEDURUMU");
		String evrakiGetiren = (String) map.get("EVRAKIGETIREN");

		String mudurluk = (String) map.get("MUDURLUK");
		BigDecimal bsm2ServisSeflik = (BigDecimal) map.get("BSM2SERVIS_SEFLIK");
		BigDecimal msm2OrganizasyonId= (BigDecimal) map.get("MSM2ORGANIZASYON_ID");
		BigDecimal msm2OrganizasyonIdPilot= (BigDecimal) map.get("MSM2ORGANIZASYON_ID_PILOT");
		BigDecimal ihr1PersonelId = (BigDecimal) map.get("IHR1PERSONEL_ID");

		String geriDonusYapilsinMi =(String) map.get("GERIDONUSYAPILSINMI");
		String geriBildirimTuru =(String) map.get("GERIBILDIRIMTURU");
		String geriBildirimYapildi =(String) map.get("GERIBILDIRIMYAPILDI");

		String ilce = (String) map.get("RRE1ILCE_ADI");
		String mahalle = (String) map.get("DRE1MAHALLE_ADI");
		String sokakAdi = (String) map.get("SRE1SOKAK_ADI");
		String siteAdi = (String) map.get("RRE1SITE_ADI");
		String kapiNo = (String) map.get("KAPINO");
		String daireNo = (String) map.get("DAIRENO");

		String bildirimdeBulunan=(String) map.get("BILDIRIMDEBULUNAN");
		BigDecimal adm1BildirimTuruId = (BigDecimal)map.get("ADM1BILDIRIMTURU_ID");

		String bildirimNiteligi = (String) map.get("BILDIRIMNITELIGI");
		String bildirimTuru = (String) map.get("BILDIRIMTURU");

		if(adi != null)
			belgeBasvuruDetay.setAdi(isAuthorized ? adi : hideField);
		if(soyadi != null)
			belgeBasvuruDetay.setSoyadi(isAuthorized ? soyadi : hideField);
		if(konuTuru != null)
			belgeBasvuruDetay.setKonuTuru(isAuthorized ? konuTuru : hideField);
		if(konusu != null)
			belgeBasvuruDetay.setKonusu(isAuthorized ? konusu : hideField);
		if(ekBilgi!= null)
			belgeBasvuruDetay.setEkBilgi(isAuthorized ? ekBilgi : hideField);
		if(isAkisKonuOzeti != null)
			belgeBasvuruDetay.setIsAkisKonuOzeti(isAuthorized ? isAkisKonuOzeti : hideField);
		if(izahat != null)
			belgeBasvuruDetay.setIzahat(isAuthorized ? izahat : hideField);
		if(telefonNumarasi != null)
			belgeBasvuruDetay.setTelefonNumarasi(isAuthorized ? telefonNumarasi : hideField);
		if(isTelefonu != null)
			belgeBasvuruDetay.setIsTelefonu(isAuthorized ? isTelefonu : hideField);
		if(cepTelefonu != null)
			belgeBasvuruDetay.setCepTelefonu(isAuthorized ? Long.toString(cepTelefonu.longValue()) : hideField);
		if(elektronikPosta != null)
			belgeBasvuruDetay.setElektronikPosta(isAuthorized ? elektronikPosta : hideField);


		if(id != null)
			belgeBasvuruDetay.setId(id.longValue());
		if(referansNo != null)
			belgeBasvuruDetay.setReferansNo(referansNo.longValue());
		if(tip != null)
			belgeBasvuruDetay.setTip(tip);
		if(disaridanGelenBelgeNo != null)
			belgeBasvuruDetay.setDisaridanGelenBelgeNo(disaridanGelenBelgeNo.longValue());
		if(defterKaydiGirisNo != null)
			belgeBasvuruDetay.setDisaridanGelenBelgeNo(disaridanGelenBelgeNo.longValue());
		if(tarih != null)
			belgeBasvuruDetay.setTarih(dateFormat.format(tarih));
		if(tarihSaat != null)
			belgeBasvuruDetay.setTarihSaat(dateFormat.format(new Date(tarihSaat.getTime())));
		if(uretimTipi != null)
			belgeBasvuruDetay.setUretimTipi(uretimTipi);
		if(edm1IsAkisiAdimId != null)
			belgeBasvuruDetay.setEdm1IsAkisiAdimId(edm1IsAkisiAdimId.longValue());
		if(merciKurum != null)
			belgeBasvuruDetay.setMerciKurum(merciKurum);


		if(sdpKodu != null)
			belgeBasvuruDetay.setSdpKodu(sdpKodu);
		if(gizlilik!= null)
			belgeBasvuruDetay.setIzahat(izahat);
		if(onemDerecesi != null)
			belgeBasvuruDetay.setOnemDerecesi(onemDerecesi);
		if(ekliSayfaSayisi != null)
			belgeBasvuruDetay.setEkliSayfaSayisi(ekliSayfaSayisi.longValue());
		if(digerEkler != null)
			belgeBasvuruDetay.setDigerEkler(digerEkler);
		if(dosyaNo != null)
			belgeBasvuruDetay.setDosyaNo(dosyaNo);
		if(beklenenBitisTarihi != null)
			belgeBasvuruDetay.setBeklenenBitisTarihi(dateFormat.format(beklenenBitisTarihi));
		if(belgeDurumu != null)
			belgeBasvuruDetay.setBelgeDurumu(belgeDurumu);
		if(evrakiGetiren != null)
			belgeBasvuruDetay.setEvrakiGetiren(evrakiGetiren);
		if(mudurluk != null)
			belgeBasvuruDetay.setMudurluk(mudurluk);
		if(bsm2ServisSeflik != null)
			belgeBasvuruDetay.setBsm2ServisSeflik(bsm2ServisSeflik.longValue());
		if(msm2OrganizasyonId != null)
			belgeBasvuruDetay.setMsm2OrganizasyonId(msm2OrganizasyonId.longValue());
		if(msm2OrganizasyonIdPilot != null)
			belgeBasvuruDetay.setMsm2OrganizasyonIdPilot(msm2OrganizasyonIdPilot.longValue());
		if(ihr1PersonelId != null)
			belgeBasvuruDetay.setIhr1PersonelId(ihr1PersonelId.longValue());
		if(geriDonusYapilsinMi != null)
			belgeBasvuruDetay.setGeriDonusYapilsinMi(geriDonusYapilsinMi);
		if(geriBildirimTuru != null)
			belgeBasvuruDetay.setGeriBildirimTuru(geriBildirimTuru);
		if(geriBildirimYapildi != null)
			belgeBasvuruDetay.setGeriBildirimYapildi(geriBildirimYapildi);
		if(ilce != null)
			belgeBasvuruDetay.setIlce(ilce);
		if(mahalle != null)
			belgeBasvuruDetay.setMahalle(mahalle);
		if(sokakAdi != null)
			belgeBasvuruDetay.setSokakAdi(sokakAdi);
		if(siteAdi != null)
			belgeBasvuruDetay.setSiteAdi(siteAdi);
		if(kapiNo != null)
			belgeBasvuruDetay.setKapiNo(kapiNo);
		if(daireNo != null)
			belgeBasvuruDetay.setDaireNo(daireNo);
		if(bildirimdeBulunan != null)
			belgeBasvuruDetay.setBildirimdeBulunan(bildirimdeBulunan);
		if(adm1BildirimTuruId != null)
			belgeBasvuruDetay.setAdm1BildirimTuruId(adm1BildirimTuruId.longValue());
		if(bildirimNiteligi != null)
			belgeBasvuruDetay.setBildirimNiteligi(bildirimNiteligi);
		if(bildirimTuru != null)
			belgeBasvuruDetay.setBildirimTuru(bildirimTuru);

	}

	public long getOrganizasyonGizlilikSeviyesi(long msm2OrganizationId) {

		String sql = "SELECT nvl(GIZLILIKSEVIYESI,'0') AS GIZLILIKSEVIYESI FROM MSM2ORGANIZASYON WHERE ID = " + msm2OrganizationId;
		long gizlilikSeviyesi = 0L;
		try {
			List list = sessionFactory.getCurrentSession()
						  .createSQLQuery(sql)
						  .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
						  .list();

			Map map = (Map) list.get(0);
			gizlilikSeviyesi = Long.parseLong((String) map.get("GIZLILIKSEVIYESI"));

		} catch (Exception e) {
			LOG.debug("Basvuru yonetim belge detay organizasyon gizlilik seviyesi getirilirken bir hata ile karsilasildi msm2OrganizationId:" + msm2OrganizationId);
		}

		return gizlilikSeviyesi;
	}

	public BelgeBasvuruDetay getApplyDocDetail(long docId, long msm2OrganizationId) {
		String sql ="SELECT HDM1ISAKISITURU.TANIM, DDM1ISAKISI.ADI || ' ' || DDM1ISAKISI.SOYADI as ADISOYADI, "
				  + "DDM1ISAKISI.* FROM HDM1ISAKISITURU JOIN DDM1ISAKISI ON HDM1ISAKISITURU.ID = DDM1ISAKISI.HDM1ISAKISITURU_ID "
		          + "WHERE DDM1ISAKISI.ID=" + docId;

		List<Object> list = new ArrayList();
		BelgeBasvuruDetay belgeBasvuruDetay = new BelgeBasvuruDetay();
		long gizlilikSeviyesi = getOrganizasyonGizlilikSeviyesi(msm2OrganizationId);
		boolean isAuthorized = false;

		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){

			Map map = (Map) o;
			isAuthorized = hasAuthorization(map, msm2OrganizationId, gizlilikSeviyesi);
			setBelgeBasvuruDetay(belgeBasvuruDetay, map, isAuthorized);
		}
		
		return belgeBasvuruDetay;
	}

	@Override
	public Long getEbysMenuCount(long persid, long rolid, String tur) {
		String sql = " SELECT COUNT(*) CNT FROM ABPMWORKITEM WHERE ID>0  "
				+ " AND ((FSM1ROLES_PERFORMER = " + rolid
				+ " And FSM1USERS_PERFORMER = " + persid + ") "
				+ "   Or (FSM1ROLES_PERFORMER = " + rolid
				+ " And Fsm1Users_PERFORMER = 0)) ";

		if (tur.equals("BEKLEYEN")) {
			sql += " AND ACTION = 'PROGRESS'";

		} else if (tur.equals("ONAYBEKLEYEN")) {
			sql += " AND ACTION = 'PROGRESS' "
					+ " AND ABPMTASK_ID IN (50007,240,10090,40123,11000114,40137,10110,40113,180,10070,40121,11000112,40135,300,10130,40125,11000116,40139,120,10050,40119,11000110,40133,40132,5007 ) ";

		} else if (tur.equals("GONDERIMBEKLEYEN")) {
			sql += " AND ACTION = 'PROGRESS'  AND ABPMTASK_ID IN (10140,11000200,40128,40151,50008,5008, 5015, 40301, 40151) ";

		} else if (tur.equals("KISIYEATANAN")) {
			sql += " AND ACTION = 'PROGRESS'  AND ABPMTASK_ID IN (2001,40008,40198) ";

		} else if (tur.equals("IADEEDILEN")) {
			sql += " AND ACTION = 'PROGRESS'  AND ABPMTASK_ID IN (3010) ";

		} else if (tur.equals("DIGER")) {
			sql += "  AND ACTION = 'PROGRESS'  AND ABPMTASK_ID NOT IN (50008,50007,240,10090,40123,11000114,40137,10110,40113,180,10070,40121,11000112,40135,300,10130,40125,11000116,40139,120,10050,40119,11000110,40133,10140,11000200,40128,40151,2001,40008,40198,5007 ,5008, 5015, 40301, 40151) ";

		} else if (tur.equals("TAMAMLANAN")) {
			sql += " AND ACTION IN ('SEND','CANCEL','REJECT','DELEGATE') "
					+ " AND NVL(ACTIONDETAIL,'-') NOT IN ('GERICEKILDI') "
					+ " AND EXISTS (SELECT 1 FROM ABPMTASK WHERE ID=ABPMTASK_ID AND VISIBLEFORCOMPLETEDLIST='EVET') ";

		} else if (tur.equals("SURELIISLER")) {
			sql += " AND ACTION = 'SUSPEND' ";

		} else if (tur.equals("TOPLUIMZASEPETI")) {
			sql += " AND TOPLUIMZALAMAYAPILACAK='EVET' AND ACTION = 'PROGRESS' "
					+ " AND ABPMTASK_ID IN (50007,240,10090,40123,11000114,40137,10110,40113,180,10070,40121,11000112,40135,300,10130,40125,11000116,40139,120,10050,40119,11000110,40133,40132,5007 ) ";

		} else if (tur.equals("BYO_BEKLEYEN")) {
			sql += " AND ACTION = 'PROGRESS' AND ABPMTASK_ID IN (40307)";

		} else if (tur.equals("BYO_UZERIMDE_BEKLEYEN")) {
			sql += " AND ACTION = 'PROGRESS'  AND  ABPMTASK_ID IN (40307) "
					+ " AND EXISTS (SELECT 1 FROM ABPMWORKFLOW WHERE ID= ABPMWORKFLOW_ID "
					+ " AND EXISTS (SELECT 1 FROM ABYOBASVURU WHERE ID = ABPMWORKFLOW.ABYOBASVURU_ID "
					+ " 				AND FSM1USERS_ATANAN = " + persid + ") )";

		} else if (tur.equals("BYO_BEKLEMEYE_ALINAN")) {
			sql += " AND  ACTION = 'PROGRESS'  AND  ABPMTASK_ID IN (40307) "
					+ "  AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='BKLMD' AND ISACTIVE='E')";

		} else if (tur.equals("BYO_ACIK")) {
			sql += " AND  ACTION = 'PROGRESS'  AND  ABPMTASK_ID IN (40307) "
					+ "  AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='Open' AND ISACTIVE='E')";

		} else if (tur.equals("BYO_TAMAMLANAN")) {
			sql += " AND ACTION = 'SEND'  AND  ABPMTASK_ID IN (40307) ";

		} else if (tur.equals("BYO_ONAYLANAN")) {
			sql += " AND  ACTION = 'SEND'  AND  ABPMTASK_ID IN (40307) "
					+ " AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='Resolved' AND ISACTIVE='E')";

		} else if (tur.equals("BYO_ONAY_BEKLEYEN")) {
			sql += " AND  ACTION = 'SEND'  AND  ABPMTASK_ID IN (40307) "
					+ " AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='PendingApproval' AND ISACTIVE='E')";

		} else if (tur.equals("BYO_KAPALI")) {
			sql += " AND  ACTION = 'SEND'  AND  ABPMTASK_ID IN (40307) "
					+ "  AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='Closed' AND ISACTIVE='E')";

		} else if (tur.equals("BYO_TEKRAR_YONLENDIRME_BEKLEYEN")) {
			sql += " AND  ACTION = 'SEND'  AND  ABPMTASK_ID IN (40307) "
					+ "  AND (SELECT (SELECT ABYOBASVURUDURUMU_ID FROM ABYOBASVURU WHERE ID = ABYOBASVURU_ID) FROM ABPMWORKFLOW WHERE ID=ABPMWORKFLOW_ID) IN(SELECT ID FROM ABYOBASVURUDURUMU WHERE KODU='ReDirect' AND ISACTIVE='E')";

		} else if (tur.equals("BASLATTIGIMSURECLER")) {
			sql = " SELECT COUNT(*) CNT FROM ABPMWORKFLOW WHERE ID>0 AND FSM1USERS_CREATOR = "
					+ persid;

		} else if (tur.equals("IZLEDIGIMSURECLER")) {
			sql = " SELECT COUNT(*) CNT FROM ABPMWORKFLOW"
					+ " WHERE ID>0 "
					+ " AND ABPMWORKFLOW.ID IN (SELECT ABPMWORKFLOW_ID FROM ABPMWATCH WHERE nvl(ISACTIVE,'E')='E' AND FSM1USERS_ID ="
					+ persid + " )";

		} else if (tur.equals("MESAJLAR")) {
			sql = "SELECT COUNT(*) CNT FROM ABPMMESSAGES WHERE READFLAG = 'H'  AND FSM1ROLES_ID = "
					+ rolid;

		}  else if (tur.equals("UYARILAR")) {
			sql = "SELECT COUNT(*) CNT FROM ABPMMESSAGES WHERE READFLAG = 'H'  AND ACTIONTYPE IN ('GERICEKILDI','REDDEDILDI','SONLANDIRILDI') "
					+ " AND FSM1ROLES_ID = "
					+ rolid;

		}  else if (tur.equals("YORUMLAR")) {
			sql = "SELECT COUNT(*) CNT FROM ABPMMESSAGES WHERE READFLAG = 'H'  AND ACTIONTYPE = 'YORUM'  AND FSM1ROLES_ID = "
					+ rolid;

		}

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return ((BigDecimal) ((Map) query.list().get(0)).get("CNT"))
				.longValue();
	}

	public CozumOrtagi isSolutionPartner(long rolid) {
		String sql = "SELECT NVL(COZUMORTAGI ,'HAYIR') P1 FROM FSM1ROLES WHERE ID=" + rolid;
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		CozumOrtagi cozumOrtagi = new CozumOrtagi();
		String p1 = ((String) ((Map) query.list().get(0)).get("P1"));
		if(p1 != null)
			cozumOrtagi.setCozumOrtagi(p1);
		return cozumOrtagi;
	}

	@Override
	public List<EBYSKlasorMenu> getEBYSFolderTree(long rolid) {
		String sql = " select a.id,a.masterid,a.tanim ,a.kodu from EBYSFOLDER a where 1=1 "
				+ " AND 1=F_EBYSPERMISSION(A.ID,"
				+ rolid
				+ ") "
				+ " start with a.MASTERID = 1  "
				+ " connect by prior a.ID = a.MASTERID";
		
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<EBYSKlasorMenu> ebysKlasorMenuList = new ArrayList<EBYSKlasorMenu>();
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			EBYSKlasorMenu ebysKlasorMenu = new EBYSKlasorMenu();
			
			BigDecimal id =(BigDecimal)map.get("ID");
			BigDecimal masterId = (BigDecimal)map.get("MASTERID");
			String tanim = (String)map.get("TANIM");
			String kodu = (String)map.get("KODU");
			
			if(id != null)
				ebysKlasorMenu.setId(String.valueOf(id.longValue()));
			if(masterId != null)
				if(masterId.longValue() == 1 )
					ebysKlasorMenu.setParent(String.valueOf("#"));
				else
					ebysKlasorMenu.setParent(String.valueOf(masterId.longValue()));
			if(tanim != null || kodu!= null)
				ebysKlasorMenu.setText(kodu + "-" + tanim);
			if(id != null)
				ebysKlasorMenu.setData(String.valueOf(id.longValue()));
			
			ebysKlasorMenuList.add(ebysKlasorMenu);
		}
		
		return ebysKlasorMenuList;
	}

	public List<EBYSBirimMenu> getEBYSUnitTree(){
		String sql = " select a.id bsm2servis_id,a.masterid,a.tanim servisadi,a.ebyskodu,'-' from bsm2servis a "
				+ " where masterid=0 and a.ebyskodu is not null "
				+ " order by a.ebyskodu";
		
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<EBYSBirimMenu> ebysBirimMenuList = new ArrayList<EBYSBirimMenu>();
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			EBYSBirimMenu ebysBirimMenu = new EBYSBirimMenu();
			
			BigDecimal bsm2servis_id =(BigDecimal)map.get("BSM2SERVIS_ID");
			BigDecimal masterId = (BigDecimal)map.get("MASTERID");
			String servisAdi = (String)map.get("SERVISADI");
			String ebysKodu = (String)map.get("EBYSKODU");
			
			if(bsm2servis_id != null)
				ebysBirimMenu.setId(String.valueOf(bsm2servis_id.longValue()));
			if(masterId != null)
				if(masterId.longValue() == 0 )
					ebysBirimMenu.setParent(String.valueOf("#"));
				else
					ebysBirimMenu.setParent(String.valueOf(masterId.longValue()));
			if(servisAdi != null || ebysKodu!= null)
				ebysBirimMenu.setText(ebysKodu + "-" + servisAdi);
			if(bsm2servis_id != null)
				ebysBirimMenu.setData(String.valueOf(bsm2servis_id.longValue()));
			
			ebysBirimMenuList.add(ebysBirimMenu);
		}
		
		return ebysBirimMenuList;		
	}
	
	public List<EBYSBirimMenu> getEBYSSubUnitTree(long bsm2servis){
		String sql = " select a.id bsm2servis_id,a.masterid,a.tanim servisadi,a.ebyskodu,'-' from bsm2servis a "
				+ " where masterid="
				+ bsm2servis
				+ " and a.ebyskodu is not null " + " order by a.ebyskodu";
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<EBYSBirimMenu> ebysBirimMenuList = new ArrayList<EBYSBirimMenu>();
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			EBYSBirimMenu ebysBirimMenu = new EBYSBirimMenu();
			
			BigDecimal bsm2servis_id =(BigDecimal)map.get("BSM2SERVIS_ID");
			BigDecimal masterId = (BigDecimal)map.get("MASTERID");
			String servisAdi = (String)map.get("SERVISADI");
			String ebysKodu = (String)map.get("EBYSKODU");
			
			if(bsm2servis_id != null)
				ebysBirimMenu.setId(String.valueOf(bsm2servis_id.longValue()));
			if(masterId != null)
				if(masterId.longValue() == 0 )
					ebysBirimMenu.setParent(String.valueOf("#"));
				else
					ebysBirimMenu.setParent(String.valueOf(masterId.longValue()));
			if(servisAdi != null)
				ebysBirimMenu.setText( servisAdi);
			if(bsm2servis_id != null)
				ebysBirimMenu.setData(String.valueOf(bsm2servis_id.longValue()));
			
			ebysBirimMenuList.add(ebysBirimMenu);
		}
		
		return ebysBirimMenuList;
	}
	
	public List<EBYSBirimMenu> getEBYSServiceFolderTree(long bsm2servis){
		String sql = " select a.id bsm2servis_id,a.masterid,a.tanim servisadi,a.ebyskodu,b.foldername,b.id bsm2servisklasor_id "
				+ " from bsm2servis a,bsm2servisklasor b where a.id = b.bsm2servis_id and a.ebyskodu is not null "
				+ " and a.id = "
				+ bsm2servis
				+ " order by a.ebyskodu,b.foldername";
		
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<EBYSBirimMenu> ebysBirimMenuList = new ArrayList<EBYSBirimMenu>();
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			EBYSBirimMenu ebysBirimMenu = new EBYSBirimMenu();
			
			BigDecimal bsm2servis_id =(BigDecimal)map.get("BSM2SERVIS_ID");
			BigDecimal masterId = (BigDecimal)map.get("MASTERID");
			String servisAdi = (String)map.get("SERVISADI");
			String ebysKodu = (String)map.get("EBYSKODU");
			
			if(bsm2servis_id != null)
				ebysBirimMenu.setId(String.valueOf(bsm2servis_id.longValue()));
			if(masterId != null)
				if(masterId.longValue() == 0 )
					ebysBirimMenu.setParent(String.valueOf("#"));
				else
					ebysBirimMenu.setParent(String.valueOf(masterId.longValue()));
			if(servisAdi != null)
				ebysBirimMenu.setText( servisAdi);
			if(bsm2servis_id != null)
				ebysBirimMenu.setData(String.valueOf(bsm2servis_id.longValue()));
			
			ebysBirimMenuList.add(ebysBirimMenu);
		}
		
		return ebysBirimMenuList;
	}

	@Override
	public List<BelgeYonetimKullanici> getDocManagementUserList(long persid) {
		String sql =" SELECT A.ID||'-'||B.MSM2ORGANIZASYON_ID P1, " + 
			    " A.ADI||' '||A.SOYADI||'('|| (SELECT TANIM FROM MSM2ORGANIZASYON WHERE ID = B.MSM2ORGANIZASYON_ID) ||')' P2" +
			    " FROM IHR1PERSONEL A,IHR1PERSONELORGANIZASYON B " + 
			    " WHERE A.ID = B.IHR1PERSONEL_ID " +
			    " AND A.ID = " + persid ;
		
		List<Object> list = new ArrayList();
		
		SQLQuery query =sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<BelgeYonetimKullanici> belgeYonetimKullaniciList = new ArrayList<BelgeYonetimKullanici>();
		list = query.list();
		
		for(Object o : list){
			Map map = (Map)o;
			BelgeYonetimKullanici belgeYonetimKullanici = new BelgeYonetimKullanici();
			
			String id = (String)map.get("P1");
			String tanim = (String)map.get("P2");
			
			if(id != null)
				belgeYonetimKullanici.setId(id);
			if(tanim != null)
				belgeYonetimKullanici.setTanim(tanim);
			
			belgeYonetimKullaniciList.add(belgeYonetimKullanici);
		}
		return belgeYonetimKullaniciList;
	}
	
	public List<GraphGeneral> getDocManagementGraphIncomingDoc(long year, long servisId, String result){
		String condSql = "";
		if(result.equals("A"))
			condSql = "";
		else if(result.equals("T"))
			condSql = "AND SONUCDURUMU = 'T'";
		else if(result.equals("I"))
			condSql = "AND SONUCDURUMU = 'I'";
		else if(result.equals("E"))
			condSql = "AND SONUCDURUMU = 'E'";
		else if(result.equals("B"))
			condSql = "AND SONUCDURUMU = 'B'";
		
		
		String sql = "select A.GON_BSM2SERVIS_ID,(SELECT TANIM FROM BSM2SERVIS WHERE ID = A.GON_BSM2SERVIS_ID) ADI,COUNT(*)TOPLAM "+ 
				 " from EDM1ISAKISIADIM A "+ 
				 " WHERE F_Yil(TARIHSAAT) =  " + year +  
				 " AND ALC_BSM2SERVIS_ID =  "+ servisId +
				 " AND GON_MSM2ORGANIZASYON_ID != ALC_MSM2ORGANIZASYON_ID "+ condSql +
				 " GROUP BY A.GON_BSM2SERVIS_ID";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal gonBsm2servisId = (BigDecimal)map.get("GON_BSM2SERVIS_ID");
			String adi = (String) map.get("ADI");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");

			if(gonBsm2servisId != null)
				graphGeneral.setId(gonBsm2servisId.longValue());
			if(adi != null)
				graphGeneral.setAdi(adi);
			if(toplam != null)
				graphGeneral.setTutar(toplam.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getDocManagementGraphOutgoingDoc(long year,long servisId,String result){
		String condSql = "";
		if(result.equals("A"))
			condSql = "";
		else if(result.equals("T"))
			condSql = "AND SONUCDURUMU = 'T'";
		else if(result.equals("I"))
			condSql = "AND SONUCDURUMU = 'I'";
		else if(result.equals("E"))
			condSql = "AND SONUCDURUMU = 'E'";
		else if(result.equals("B"))
			condSql = "AND SONUCDURUMU = 'B'";
		
		String sql = " select A.ALC_BSM2SERVIS_ID,(SELECT TANIM FROM BSM2SERVIS WHERE ID = A.ALC_BSM2SERVIS_ID) ADI,COUNT(*) TOPLAM"
				+" from EDM1ISAKISIADIM A"  
				+" WHERE F_Yil(TARIHSAAT) =" + year
				+" AND  a.alc_bsm2servis_id >0" 
				+" AND GON_BSM2SERVIS_ID= " + servisId 
				+" AND GON_MSM2ORGANIZASYON_ID != ALC_MSM2ORGANIZASYON_ID " + condSql
				+" GROUP BY A.ALC_BSM2SERVIS_ID";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal gonBsm2servisId = (BigDecimal)map.get("GON_BSM2SERVIS_ID");
			String adi = (String) map.get("ADI");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");

			if(gonBsm2servisId != null)
				graphGeneral.setId(gonBsm2servisId.longValue());
			if(adi != null)
				graphGeneral.setAdi(adi);
			if(toplam != null)
				graphGeneral.setTutar(toplam.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getEbysBusinessGraph(long rolid,long servisMudurluk,String action){
		String sql = "select b.FSM1ROLES_PERFORMER , (Select  substr(x.TANIM,0,30) From "
				+" FSM1ROLES x WHERE X.ID = b.FSM1ROLES_PERFORMER) ADI,COUNT(*) TOPLAM,(select birimyoneticisi"
				+" from fsm1roles where id ="+ rolid +")from  ABPMWORKFLOW a, ABPMWORKITEM b  WHERE "
				+" A.ID = b.ABPMWORKFLOW_ID  AND b.FSM1ROLES_PERFORMER > 0 AND NOT EXISTS "
				+" (SELECT 1 FROM FSM1ROLES WHERE ID=B.FSM1ROLES_PERFORMER AND NVL(ISACTIVE,'H')='H')" 
				+" AND EXISTS (SELECT 1 FROM FSM1ROLES AA WHERE ID=b.FSM1ROLES_PERFORMER AND AA.ISACTIVE='E')"
				+" AND B.BSM2SERVISMUDURLUK_PERFORMER   ="+ servisMudurluk   
				+" AND EXISTS (SELECT 1 FROM ABPMTASK AA WHERE AA.ID=B.ABPMTASK_ID) AND "
				+" B.ACTION='"+ action + "'"  
				+" Group By b.FSM1ROLES_PERFORMER ";
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			BigDecimal id = (BigDecimal)map.get("FSM1ROLES_PERFORMER");
			String adi = (String) map.get("ADI");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");

			if(id!= null)
				graphGeneral.setId(id.longValue());
			if(adi != null)
				graphGeneral.setAdi(adi);
			if(toplam != null)
				graphGeneral.setTutar(toplam.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}
	
	public List<GraphGeneral> getEbysBusinessGraphDetail(long rolid,long servisMudurluk, String action,long rolPerformerId){
		String sql = "select nvl(D.SPECIALNAME,'Diger')SPECIALNAME,DECODE(D.SPECIALNAME,NULL,"
				+" 'Diger','IADE','Iade Edilen','ONAYBEKLEYEN','Onay Bekleyen','GONDERIMBEKLEYEN',"
				+" 'Gonderim Bekleyen','GELEN','Kisiye Atanan',D.SPECIALNAME) TANIM,COUNT(*) TOPLAM,"
				+" b.FSM1ROLES_PERFORMER,(select birimyoneticisi from fsm1roles where id ="+ rolid + ")"
				+" from  ABPMWORKFLOW a, ABPMWORKITEM b,abpmtask c,abpmtaskgroup d  WHERE A.ID ="
				+" b.ABPMWORKFLOW_ID  AND b.ABPMTASK_ID = c.id  AND c.ABPMTASKGROUP_ID = D.id  "
				+" AND b.FSM1ROLES_PERFORMER > 0  AND NOT EXISTS (SELECT 1 FROM FSM1ROLES WHERE "
				+" ID=B.FSM1ROLES_PERFORMER AND NVL(ISACTIVE,'H')='H')  AND EXISTS "
				+" (SELECT 1 FROM FSM1ROLES AA WHERE ID=b.FSM1ROLES_PERFORMER AND AA.ISACTIVE='E')" 
				+" AND B.BSM2SERVISMUDURLUK_PERFORMER   ="+ servisMudurluk   
				+" AND EXISTS (SELECT 1 FROM ABPMTASK AA WHERE AA.ID=B.ABPMTASK_ID) AND B.ACTION='"+ action+"'" 
				+" and b.fsm1roles_performer=" + rolPerformerId 
				+" Group By SPECIALNAME ,b.FSM1ROLES_PERFORMER";   
		
		List<Object> list = new ArrayList<Object>();
		List<GraphGeneral> graphGeneralList = new ArrayList<GraphGeneral>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		for (Object o : list ){
			Map map = (Map) o;
			GraphGeneral graphGeneral = new GraphGeneral();
			
			String adi = (String) map.get("TANIM");
			BigDecimal toplam = (BigDecimal) map.get("TOPLAM");

			if(adi != null)
				graphGeneral.setAdi(adi);
			if(toplam != null)
				graphGeneral.setTutar(toplam.doubleValue());
		
			graphGeneralList.add(graphGeneral);
		}
		return graphGeneralList;
	}

	public List<BasvuruOzet> getGelenBasvuruList(long organizationId, String startDate, String endDate){
		String sql = "SELECT DB.ID,\n" +
				"       DBI.ID as DM1ISAKISIADIMID,\n" +
				"       DB.ADI,\n" +
				"       DB.TARIH,\n" +
				"       DB.SOYADI\n" +
				"FROM EDM1ISAKISIADIM DBI, DDM1ISAKISI DB\n" +
				"WHERE  (DB.ID = DBI.DDM1ISAKISI_ID)\n" +
				"AND DB.TURU IN('S','K') \n" +
				"AND NVL (DBI.ALC_MSM2ORGANIZASYON_ID, 0) > 0\n" +
				"AND DBI.ALC_MSM2ORGANIZASYON_ID = " + organizationId + "\n" +
				"AND DBI.ALC_MSM2ORGANIZASYON_ID <> DBI.GON_MSM2ORGANIZASYON_ID\n" +
				"AND DBI.DURUMU = 'S'\n" +
				"AND DBI.SONUCDURUMU NOT IN ('T')\n" +
				"AND DB.ID IN (SELECT DDM1ISAKISI_ID\n" +
				"FROM EDM1ISAKISIADIM\n" +
				"WHERE TARIH BETWEEN TO_DATE ('" + startDate +"',     'dd-MM-yyyy')   AND TO_DATE('"+ endDate +"','dd-MM-yyyy')    ) \n" +
				"ORDER BY DB.TARIHSAAT DESC,DB.ID";

		List<Object> list = new ArrayList<Object>();
		List<BasvuruOzet> basvuruOzetList = new ArrayList<BasvuruOzet>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map) o;
			BasvuruOzet basvuruOzet = new BasvuruOzet();
			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal adimId = (BigDecimal)map.get("DM1ISAKISIADIMID");
			String adi = (String) map.get("ADI");
			String soyAdi = (String) map.get("SOYADI");
			Date tarih = (Date) map.get("TARIH");

			if(id != null)
				basvuruOzet.setBasvuruNo(id.longValue());
			if(adimId != null)
				basvuruOzet.setAdimId(adimId.longValue());
			if(adi != null)
				basvuruOzet.setAdi(adi);
			if(soyAdi != null)
				basvuruOzet.setSoyAdi(soyAdi);
			if(tarih != null)
				basvuruOzet.setTarih(dateFormat.format(tarih));


			basvuruOzetList.add(basvuruOzet);
		}
		return basvuruOzetList;
	}

	public List<BasvuruOzet> getGidenBasvuruList(long organizationId, String startDate, String endDate){
		String sql = "SELECT * FROM (\n" +
				"SELECT DB.ID,\n" +
				"       DBI.ID as DM1ISAKISIADIMID,\n" +
				"       DB.ADI,\n" +
				"       DB.TARIH,\n" +
				"       DB.SOYADI\n" +
				"FROM EDM1ISAKISIADIM DBI, DDM1ISAKISI DB\n" +
				"WHERE     (DB.ID = DBI.DDM1ISAKISI_ID)\n" +
				"         AND DB.TURU IN ('S', 'K')\n" +
				"         AND NVL (DBI.GON_MSM2ORGANIZASYON_ID, 0) > 0\n" +
				"         AND DBI.GON_MSM2ORGANIZASYON_ID = " + organizationId  +"\n" +
				"         AND DBI.ALC_MSM2ORGANIZASYON_ID <> DBI.GON_MSM2ORGANIZASYON_ID\n" +
				"         AND DB.TARIH BETWEEN TO_DATE ('" + startDate +"',     'dd-MM-yyyy')   AND TO_DATE('"+ endDate +"','dd-MM-yyyy')  \n" +
				"ORDER BY DB.TARIH DESC, DB.ID DESC) WHERE ROWNUM <= 100";



		List<Object> list = new ArrayList<Object>();
		List<BasvuruOzet> basvuruOzetList = new ArrayList<BasvuruOzet>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map) o;
			BasvuruOzet basvuruOzet = new BasvuruOzet();
			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal adimId = (BigDecimal)map.get("DM1ISAKISIADIMID");
			String adi = (String) map.get("ADI");
			String soyAdi = (String) map.get("SOYADI");
			Date tarih = (Date) map.get("TARIH");

			if(id != null)
				basvuruOzet.setBasvuruNo(id.longValue());
			if(adimId != null)
				basvuruOzet.setAdimId(adimId.longValue());
			if(adi != null)
				basvuruOzet.setAdi(adi);
			if(soyAdi != null)
				basvuruOzet.setSoyAdi(soyAdi);
			if(tarih != null)
				basvuruOzet.setTarih(dateFormat.format(tarih));


			basvuruOzetList.add(basvuruOzet);
		}
		return basvuruOzetList;
	}

	public List<BasvuruOzet> getUrettiklerimList(long organizationId, String startDate, String endDate){
		String sql = "SELECT * FROM (\n" +
				"SELECT DB.ID,\n" +
				"       DBI.ID as DM1ISAKISIADIMID,\n" +
				"       DB.ADI,\n" +
				"       DB.TARIH,\n" +
				"       DB.SOYADI\n" +
				"FROM EDM1ISAKISIADIM DBI, DDM1ISAKISI DB\n" +
				"WHERE     (DB.ID = DBI.DDM1ISAKISI_ID)\n" +
				"       AND NVL (DBI.ALC_MSM2ORGANIZASYON_ID, 0) > 0\n" +
				"       AND NVL (DBI.ALC_MSM2ORGANIZASYON_ID, 0) = " + organizationId  +"\n" +
				"       AND NVL (DBI.ALC_MSM2ORGANIZASYON_ID, 0) =   NVL (DBI.GON_MSM2ORGANIZASYON_ID, 0)\n" +
				"       AND NVL (DBI.ILETIMYERI, '-') = '-'\n" +
				"       AND NVL (DB.TURU, '-') IN ('S','K')\n" +
				"         AND NVL (DB.TARIH, SYSDATE) >= TO_DATE ('" + startDate +"',   'dd/MM/yyyy')  AND NVL(DB.TARIH,SYSDATE) <= TO_DATE('"+ endDate +"','dd/MM/yyyy') \n"+
				"       ORDER BY NVL(DB.TARIH,SYSDATE) DESC,DB.ID DESC) WHERE ROWNUM <= 100";





		List<Object> list = new ArrayList<Object>();
		List<BasvuruOzet> basvuruOzetList = new ArrayList<BasvuruOzet>();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map) o;
			BasvuruOzet basvuruOzet = new BasvuruOzet();
			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal adimId = (BigDecimal)map.get("DM1ISAKISIADIMID");
			String adi = (String) map.get("ADI");
			String soyAdi = (String) map.get("SOYADI");
			Date tarih = (Date) map.get("TARIH");

			if(id != null)
				basvuruOzet.setBasvuruNo(id.longValue());
			if(adimId != null)
				basvuruOzet.setAdimId(adimId.longValue());
			if(adi != null)
				basvuruOzet.setAdi(adi);
			if(soyAdi != null)
				basvuruOzet.setSoyAdi(soyAdi);
			if(tarih != null)
				basvuruOzet.setTarih(dateFormat.format(tarih));


			basvuruOzetList.add(basvuruOzet);
		}
		return basvuruOzetList;
	}

	@Override
	public List<EBYSDetail> getEbysUnsignableAdditionDocument(long documentId) {

		String sql = "                             select D.EBYSDOCUMENT_ID, (SELECT X.TANIM FROM EBYSDOCUMENT X WHERE X.ID = D.EBYSDOCUMENT_ID) AS TANIM," +
				"							DECODE(C.DOCUMENTDEFINITIONKODU, 'ILGI','İlgi','DOCLINK','Eklenti','ILISKILIBELGE','İlişkili Belge','-') AS TURU" +
				"                               from EBYSDOCUMENT A,EBYSVERSION B,EBYSDOCUMENTVALUE C,EBYSFOLDERLINK D" +
				"                               Where A.EBYSVERSION_LAST = B.ID And A.ID = C.EBYSDOCUMENT_ID" +
				"                               AND A.EBYSVERSION_LAST = C.EBYSVERSION_ID AND  C.EBYSFOLDERLINK_ID = D.ID" +
				"                               And C.EBYSDOCUMENT_ID  = " + documentId +
				"                              And C.DOCUMENTDEFINITIONKODU IN ('DOCLINK', 'ILGI','ILISKILIBELGE') AND D.ID>0" +
				" AND D.EBYSDOCUMENT_ID NOT IN " +
				"( SELECT" +
						" a.ID                   AID " +
						" FROM EBYSDOCUMENT a, EBYSONAY c " +
						" WHERE a.EBYSVERSION_LAST = c.EBYSVERSION_ID " +
						" and C.EBYSDOCUMENT_ID = " + documentId +")";

		List<Object> list = new ArrayList();
		List<EBYSDetail> ebysDetailList = new ArrayList();

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = query.list();

		for(Object o : list){
			Map map = (Map)o;
			EBYSDetail ebysDetail = new EBYSDetail();

			BigDecimal cid = (BigDecimal)map.get("EBYSDOCUMENT_ID");
			String tanim = (String)map.get("TANIM");
			String dokumanTuru = (String)map.get("TURU");

			if(cid != null)
				ebysDetail.setOnayId(cid.longValue());


			if(dokumanTuru != null) {
				ebysDetail.setDokumanTuru(dokumanTuru);
			}

			if(tanim != null) {
				ebysDetail.setTanim(tanim);
			}

			ebysDetail.setDocumentId(cid.longValue());

			ebysDetailList.add(ebysDetail);
		}
		return ebysDetailList;
	}

	@Override
	public Boolean documentReject(DocumentRejectDTO documentRejectDTO) {
		String url = env.getProperty("eyazisma") + "paket/reddet";
		HttpHeaders headers = new HttpHeaders();
		//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DocumentRejectDTO> request = new HttpEntity<>(documentRejectDTO, headers);
		ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
		LOG.debug("belge reddi başarıyla gerçekleşti. response Code = " + response.getStatusCode().toString());
		return true;
	}

	//TODO tamamlandı.
	public String getEBYSParafQueryString(String type, long persid, long rolid, String startDate, String endDate) {

		String progress = type.equalsIgnoreCase("ONAYBEKLIYOR") ? "PROGRESS" : "SEND";

		String queryString = "SELECT \n" +
				"A.id, \n" +
				"abpmworkflow_id, \n" +
				"BPM.F_DocumentIdForWorkItem (A.ID) DOCID, \n" +
				"(SELECT FIRSTNAME || ' ' || LASTNAME FROM FSM1USERS WHERE ID = fsm1users_sentby) || '(' || (SELECT TANIM FROM FSM1ROLES WHERE ID = fsm1roles_sentby) || ')' NAME, \n" +
				"DECODE ((SELECT NVL (ABYOKONU_ID, 0) FROM ABPMWORKFLOW WHERE ID = ABPMWORKFLOW_ID), 0, \n" +
				"\t\t\tCASE WHEN (SELECT BPM.F_READ_WORKFLOWVALUE_NUMBER (ABPMWORKFLOW_ID,'EBYSBELGE_ID') FROM DUAL) > 0 \n" +
				"\t\t\tTHEN\n" +
				"          \t   (SELECT KONUOZETI FROM EBYSBELGE WHERE ID = (SELECT BPM.F_READ_WORKFLOWVALUE_NUMBER ( A.ABPMWORKFLOW_ID, 'EBYSBELGE_ID') FROM DUAL)) ELSE '' END, TASKSUBJECT)\n" +
				"KONU, \n" +
				"EBYSDOCUMENT_ID, \n" +
				"creationdate, \n" +
				"MESSAGE, \n" +
				"(SELECT EBYSPAKETLINE.EBYSPAKET_ID FROM EBYSPAKETLINE WHERE EBYSPAKETLINE.TURU = 'USTYAZI_BIRIM' AND EBYSPAKETLINE.EBYSDOCUMENT_ID = BPM.F_DocumentIdForWorkItem (A.ID)) AS PAKETID \n" +
				"FROM ABPMWORKITEM A, ABPMTASK TSK\n" +
				"WHERE A.id > 0 AND \n" +
				"A.ABPMTASK_ID = TSK.ID AND \n" +
				"NVL (TSK.BPMTASKMI, 'H') = 'H' AND \n" +
				"A.ACTION NOT IN ('CANCEL') AND \n" +
				"EXISTS\n" +
				" (SELECT 1 FROM EBYSONAY X, EBYSONAYTURU Y, EBYSONAYGRUBU Z \n" +
				" \t\t   WHERE X.FSM1ROLES_ID ="+ rolid + " AND \n" +
				" \t\t   \t\t X.IHR1PERSONEL_ID = " + persid + " AND \n" +
				" \t\t   \t\t X.ONAYDURUMU = '"+ type +"' AND \n" +
				" \t\t   \t\t X.ABPMWORKITEM_ID = A.ID AND \n" +
				" \t\t   \t\t X.ONAYTIPI = Y.KAYITOZELISMI AND \n" +
				" \t\t   \t\t Y.EBYSONAYGRUBU_ID = Z.ID  AND \n" +
				" \t\t   \t\t Z.KAYITOZELISMI = 'PARAF') AND\n" +
				"A.FSM1ROLES_PERFORMER = " + rolid +" \n" +
				"AND \n" +
				"A.ACTION = '"+ progress +"' \n" +
				" AND A.CREATIONDATE BETWEEN TO_DATE('"+ startDate +"', 'dd-MM-yyyy') and"
				+" TO_DATE ('"+ endDate +"', 'dd-MM-yyyy')" +
				" ORDER BY A.CREATIONDATETIME DESC, A.id DESC";

		return queryString;
	}

	public List<EBYS> getEBYSParaf(String type, long persid, long rolid, String startDate, String endDate) {

	    List<EBYS> parafList = new ArrayList<>();

		try {
			String sql = getEBYSParafQueryString(type, persid, rolid, startDate, endDate);
			List<HashMap> list = sessionFactory.getCurrentSession()
					.createSQLQuery(sql)
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
					.list();

			if(!list.isEmpty()) {
				for(Map<String ,Object> map : list) {

					EBYS ebys = new EBYS();

					BigDecimal id = (BigDecimal)map.get("ID");
					BigDecimal ebysDocumentId = (BigDecimal)map.get("EBYSDOCUMENTID");
					Date creationDate = (Date)map.get("CREATIONDATE");
					String name = (String)map.get("NAME");
					String konu = (String)map.get("KONU");
					String message = (String) map.get("MESSAGE");
					BigDecimal docId = (BigDecimal) map.get("DOCID");
					BigDecimal paketId = (BigDecimal)map.get("PAKETID");
					BigDecimal workFlowId = (BigDecimal)map.get("ABPMWORKFLOW_ID");

					if(id != null)
						ebys.setId(id.longValue());
					if(ebysDocumentId != null)
						ebys.setEbysDocumentId(ebysDocumentId.longValue());
					if(creationDate != null)
						ebys.setCreationDate(dateFormat.format(creationDate));
					if(name != null)
						ebys.setName(name);
					if(konu != null)
						ebys.setKonu(konu);
					if(message != null)
						ebys.setMessage(message);
					if(docId != null)
						ebys.setDocId(docId.longValue());
					if(paketId != null)
						ebys.setPaketId(paketId.longValue());
					if(workFlowId != null)
						ebys.setWorkFlowId(workFlowId.longValue());

					parafList.add(ebys);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorString = String .format("Ebys paraf bilgileri getirilirken bir hata ile karsilasildi onay durumu: %s, persid: %d, rolid: %d", type, persid, rolid);
			LOG.debug(errorString);
		}

		return parafList;
	}

	public String getCompletedEBYSParafDetailQueryString(long documentId) {
		String sql = "SELECT \n" +
				"EBYSDOCUMENT.ID,\n" +
				"EBYSDOCUMENT.TANIM,\n" +
				"'ÜST YAZI' as TURU,  \n" +
                "(SELECT ADI || ' ' || SOYADI FROM IHR1PERSONEL WHERE ID = EBYSONAY.IHR1PERSONEL_ID) ADSOYAD, \n" +
				"EBYSONAY.DOKUMANTURU, \n" +
				"EBYSONAY.IHR1PERSONEL_ID \n" +
				"FROM EBYSDOCUMENT, EBYSONAYGRUBU, EBYSONAY, EBYSONAYTURU, ABPMWORKITEM\n" +
				"WHERE EBYSONAYGRUBU.KAYITOZELISMI = 'PARAF' AND\n" +
				"\t  ABPMWORKITEM.ID = EBYSONAY.ABPMWORKITEM_ID AND\n" +
				"\t  EBYSONAY.EBYSDOCUMENT_ID = EBYSDOCUMENT.ID AND\n" +
				"\t  EBYSONAYTURU.KAYITOZELISMI = EBYSONAY.ONAYTIPI AND\n" +
				"   \t  EBYSONAYTURU.EBYSONAYGRUBU_ID = EBYSONAYGRUBU.ID AND\n" +
				"\t  ABPMWORKITEM.ACTION <> 'PROGRESS' AND\n" +
				"\t  EBYSONAY.ONAYDURUMU <> 'ONAYBEKLIYOR' AND\n" +
				"\t  EBYSDOCUMENT.ID= " + documentId + " \n" +
				"\t  UNION\n" +
				"\tselect EBYSFOLDERLINK.EBYSDOCUMENT_ID, \n" +
				"\t\t   (SELECT EBYSDOCUMENT.TANIM FROM EBYSDOCUMENT WHERE EBYSDOCUMENT.ID = EBYSFOLDERLINK.EBYSDOCUMENT_ID),\n" +
				"\t\t   DECODE(EBYSDOCUMENTVALUE.DOCUMENTDEFINITIONKODU, 'ILGI','İlgi','DOCLINK','Eklenti','ILISKILIBELGE','İlişkili Belge','-'), \n" +
				"\t\t   (SELECT ADI || ' ' || SOYADI FROM IHR1PERSONEL WHERE ID = EBYSONAY.IHR1PERSONEL_ID) ADSOYAD, \n" +
				"\t\t   EBYSONAY.DOKUMANTURU, \n" +
				"\t\t   EBYSONAY.IHR1PERSONEL_ID \n" +
				"\t\t   from EBYSDOCUMENT, EBYSVERSION,EBYSDOCUMENTVALUE, EBYSFOLDERLINK, ABPMWORKITEM, EBYSONAY, EBYSONAYTURU, EBYSONAYGRUBU\n" +
				"\t\t   Where EBYSDOCUMENT.EBYSVERSION_LAST = EBYSVERSION.ID And \n" +
				"\t\t   \t\t EBYSDOCUMENT.ABPMWORKITEM_ID = ABPMWORKITEM.ID AND\n" +
				"\t\t   \t\t ABPMWORKITEM.ACTION <> 'PROGRESS' AND\n" +
				"\t\t   \t\t EBYSONAY.ABPMWORKITEM_ID = EBYSDOCUMENT.ABPMWORKITEM_ID AND\n" +
				"\t\t   \t\t EBYSONAY.EBYSDOCUMENT_ID = EBYSDOCUMENT.ID AND\n" +
				"\t\t   \t\t EBYSONAYTURU.KAYITOZELISMI = EBYSONAY.ONAYTIPI AND\n" +
				"\t\t   \t\t EBYSONAYTURU.EBYSONAYGRUBU_ID = EBYSONAYGRUBU.ID AND\n" +
				"\t\t   \t\t EBYSONAYGRUBU.KAYITOZELISMI = 'PARAF' AND\n" +
				"\t\t   \t\t EBYSDOCUMENT.ID = EBYSDOCUMENTVALUE.EBYSDOCUMENT_ID AND \n" +
				"\t\t   \t\t EBYSDOCUMENT.EBYSVERSION_LAST = EBYSDOCUMENTVALUE.EBYSVERSION_ID AND  \n" +
				"\t\t   \t\t EBYSDOCUMENTVALUE.EBYSFOLDERLINK_ID = EBYSFOLDERLINK.ID And \n" +
				"\t\t   \t\t EBYSDOCUMENTVALUE.EBYSDOCUMENT_ID  = " + documentId  + " And \n" +
				"\t\t   \t\t EBYSDOCUMENTVALUE.DOCUMENTDEFINITIONKODU IN ('DOCLINK', 'ILGI','ILISKILIBELGE') AND \n" +
				"\t\t   \t\t EBYSFOLDERLINK.ID>0 AND \n" +
				"\t\t   \t\t EBYSONAY.ONAYDURUMU <> 'ONAYBEKLIYOR'";

		return sql;
	}

	public String getWaitingEBYSParafDetailQueryString(long documentId) {
		String sql = "WITH TUMCE AS \n" +
				"\t(SELECT  \n" +
				"\t\ta.ID AS ID, \n" +
				"\t\ta.TANIM, \n" +
				"\t\t(SELECT DURUMU FROM EBYSVERSION WHERE ID = c.EBYSVERSION_ID) DURUMU,\n" +
				"\t\tC.ONAYDURUMU,\n" +
				"\t\tC.ONAYTIPI,\n" +
				"\t\t(SELECT ADI || ' ' || SOYADI FROM IHR1PERSONEL WHERE ID = C.IHR1PERSONEL_ID) ADSOYAD,\n" +
				"\t\tC.IHR1PERSONEL_ID,                 \n" +
				"\t\tc.DOKUMANTURU AS TURU,                \n" +
				"\t\tc.hitapeki,                 \n" +
				"\t\tC.EBYSVERSION_ID,                 \n" +
				"\t\tNVL (C.ABPMTASK_ID, 0) BPMTASKID,                 \n" +
				"\t\t(SELECT NVL (EIMZASIZPARAFLAMA, 'H') FROM IHR1PERSONEL WHERE ID = C.IHR1PERSONEL_ID) EIMZASIZPARAFLAMA,\n" +
				"\t\tC.EBYSDOCUMENT_ID\n" +
				"\t\tEBYSDOCUMENT_ONAY,\n" +
				"\t\ta.MASTERID_DAGITIM,\n" +
				"\t\t(SELECT EBYSPAKET_ID FROM EBYSPAKETLINE WHERE TURU = 'USTYAZI_BIRIM' AND EBYSDOCUMENT_ID = a.id) PAKETID,   \n" +
				"\t\t(SELECT ONAYSIRASI FROM EBYSONAYTURU WHERE KAYITOZELISMI=C.ONAYTIPI) OS,               \n" +
				"\t\t(SELECT MAX(EBYSCONTENT_ID) FROM EBYSDOCUMENTVALUE V WHERE V.DOCUMENTDEFINITIONKODU='PDFDOKUMAN' AND V.EBYSDOCUMENT_ID=a.ID  ) CONTENTID   \n" +
				"\t\tFROM EBYSDOCUMENT a, EBYSONAY c          \n" +
				"\t\tWHERE a.EBYSVERSION_LAST = c.EBYSVERSION_ID) \n" +
				"\t\t\tSELECT * FROM TUMCE  WHERE (EBYSDOCUMENT_ONAY = "+ documentId +" ) UNION SELECT *   FROM TUMCE  WHERE (MASTERID_DAGITIM = "+ documentId + " ) ORDER BY 2, 3";
		return sql;
	}

	public String getEBYSParafDetailQueryString(String type, long documentId) {
		if(type.equalsIgnoreCase("ONAYBEKLIYOR"))
			return getWaitingEBYSParafDetailQueryString(documentId);
		else
			return getCompletedEBYSParafDetailQueryString(documentId);
	}

	public List<EBYSParafDetailDTO> getEBYSParafDetail(String type, long documentId) {
		List<EBYSParafDetailDTO> ebysParafDetailDTOList = new ArrayList<>();

		try {
			String sql = getEBYSParafDetailQueryString(type, documentId);
			List<HashMap> list = sessionFactory.getCurrentSession()
					.createSQLQuery(sql)
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
					.list();
			if(!list.isEmpty()) {
				for(Map<String, Object> map : list) {

					EBYSParafDetailDTO ebysParafDetailDTO = new EBYSParafDetailDTO();

					String tanim = (String) map.get("TANIM");
					String turu = (String) map.get("TURU");
					BigDecimal ebysDocumentId = (BigDecimal) map.get("ID");
					String adSoyad = (String) map.get("ADSOYAD");
					BigDecimal ihr1PersonelId = (BigDecimal) map.get("IHR1PERSONEL_ID");

					if(tanim != null)
						ebysParafDetailDTO.setTanim(tanim);
					if(turu != null)
						ebysParafDetailDTO.setTuru(turu);
					if(ebysDocumentId != null)
						ebysParafDetailDTO.setEbysDocumentId(ebysDocumentId.longValue());
					if(adSoyad != null)
						ebysParafDetailDTO.setAdSoyad(adSoyad);
					if (ihr1PersonelId != null)
						ebysParafDetailDTO.setIhr1PersonelId(ihr1PersonelId.longValue());

					ebysParafDetailDTOList.add(ebysParafDetailDTO);

				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			LOG.debug("EBYS Paraf " + type + " belge detayi getirilirken bir hata ile karsilasildi. documentId: " + documentId);
		}

		return ebysParafDetailDTOList;
	}

	public String getWaitingEBYSParafEkDetailQueryString(long documentId) {
		String sql = "SELECT \n" +
				"EBYSFOLDERLINK.EBYSDOCUMENT_ID,\n" +
				"(SELECT X.TANIM FROM EBYSDOCUMENT X WHERE X.ID = EBYSFOLDERLINK.EBYSDOCUMENT_ID) AS TANIM,\n" +
				"DECODE(EBYSDOCUMENTVALUE.DOCUMENTDEFINITIONKODU, 'ILGI','İlgi','DOCLINK','Eklenti','ILISKILIBELGE','İlişkili Belge','-') AS TURU\n" +
				"FROM EBYSDOCUMENT ,EBYSVERSION, EBYSDOCUMENTVALUE,EBYSFOLDERLINK\n" +
				"WHERE EBYSDOCUMENT.EBYSVERSION_LAST = EBYSVERSION.ID AND \n" +
				"EBYSDOCUMENT.ID = EBYSDOCUMENTVALUE.EBYSDOCUMENT_ID  AND \n" +
				"EBYSDOCUMENT.EBYSVERSION_LAST = EBYSDOCUMENTVALUE.EBYSVERSION_ID AND  \n" +
				"EBYSDOCUMENTVALUE.EBYSFOLDERLINK_ID = EBYSFOLDERLINK.ID AND \n" +
				"EBYSDOCUMENTVALUE.EBYSDOCUMENT_ID  = "+ documentId + " AND \n" +
				"EBYSDOCUMENTVALUE.DOCUMENTDEFINITIONKODU IN ('DOCLINK', 'ILGI','ILISKILIBELGE') AND \n" +
				"EBYSFOLDERLINK.ID>0 AND \n" +
				"EBYSFOLDERLINK.EBYSDOCUMENT_ID NOT IN ( SELECT EBYSDOCUMENT.ID FROM EBYSDOCUMENT , EBYSONAY WHERE EBYSDOCUMENT.EBYSVERSION_LAST = EBYSONAY.EBYSVERSION_ID  and EBYSONAY.EBYSDOCUMENT_ID = "+ documentId +" )";

		return sql;
	}

	public List<EBYSParafDetailDTO> getWaitingEBYSParafEkDetail(long documentId) {
		List<EBYSParafDetailDTO> ebysParafDetailDTOList = new ArrayList<>();

		try {
			String sql = getWaitingEBYSParafEkDetailQueryString(documentId);
			List<HashMap> list = sessionFactory.getCurrentSession()
					.createSQLQuery(sql)
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
					.list();

			if(!list.isEmpty()) {
				for(Map<String, Object> map : list) {

					EBYSParafDetailDTO ebysParafDetailDTO = new EBYSParafDetailDTO();

					String tanim = (String) map.get("TANIM");
					String turu = (String) map.get("TURU");
					BigDecimal ebysDocumentId = (BigDecimal) map.get("EBYSDOCUMENT_ID");

					if(tanim != null)
						ebysParafDetailDTO.setTanim(tanim);
					if(turu != null)
						ebysParafDetailDTO.setTuru(turu);
					if(ebysDocumentId != null)
						ebysParafDetailDTO.setEbysDocumentId(ebysDocumentId.longValue());

					ebysParafDetailDTOList.add(ebysParafDetailDTO);
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			LOG.debug("EBYS Paraf onay bekleyen belge ekleri getirilirken bir hata ile karsilasildi. documentId: " + documentId);
		}

		return ebysParafDetailDTOList;
	}
}
