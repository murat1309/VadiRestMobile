package com.digikent.vadirest.dao.impl;

import com.digikent.vadirest.dao.LoginDAO;
import com.digikent.vadirest.dto.BYSMenu;
import com.digikent.vadirest.dto.Message;
import com.digikent.vadirest.dto.UserAuthenticationInfo;
import com.digikent.vadirest.dto.UserAuthorizationInfo;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository("loginDao")
@Transactional
public class LoginDAOImpl implements LoginDAO {
	
	@Autowired
	protected SessionFactory sessionFactory;

	public UserAuthenticationInfo loginWithPassword(String userName, String password) {
		String sql = "select A.ID, A.IHR1PERSONEL_ID,A.FIRSTNAME,A.LASTNAME,A.USERID,A.FSM1ROLES_ID, "
				   +"(SELECT MSM2ORGANIZASYON_ID FROM IHR1PERSONELORGANIZASYON WHERE IHR1PERSONEL_ID = A.IHR1PERSONEL_ID and rownum=1) AS  MSM2PERSONEL_ID, "
                   +"(SELECT IP.BSM2SERVIS_GOREV FROM IHR1PERSONEL IP WHERE IP.ID=A.IHR1PERSONEL_ID) SERVIS_ID, "
                   +"(SELECT BSM2SERVIS_MUDURLUK FROM FSM1ROLES WHERE ID=A.FSM1ROLES_ID) BSM2SERVIS_MUDURLUK , "
                   +"(SELECT MASTERID FROM MSM2ORGANIZASYON C,IHR1PERSONELORGANIZASYON B  WHERE B.IHR1PERSONEL_ID = A.IHR1PERSONEL_ID AND B.MSM2ORGANIZASYON_ID = C.ID and rownum=1) AS MASTERID "
				   +"from fsm1users A WHERE A.USERID ='"+ userName +"' and A.PASSWORD = F_MD5HASH('"+password+"')";
		
		List<Object> list = new ArrayList<Object>();
		Session session = sessionFactory.withOptions().interceptor(null).openSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		list = query.list();
		UserAuthenticationInfo userAuthenticationInfo = new UserAuthenticationInfo();
		
		for(Object o : list){
			int imageLength;
			Map map = (Map) o;
			
			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal personelId = (BigDecimal) map.get("IHR1PERSONEL_ID");
			String firstName = (String) map.get("FIRSTNAME");
			String lastName = (String) map.get("LASTNAME");
			BigDecimal msm2PersonelId = (BigDecimal)map.get("MSM2PERSONEL_ID");
			BigDecimal masterId = (BigDecimal) map.get("MASTERID");
			BigDecimal servisId = (BigDecimal) map.get("SERVIS_ID");
			BigDecimal bsm2ServisMudurluk = (BigDecimal) map.get("BSM2SERVIS_MUDURLUK");
			
			if(id != null)
				userAuthenticationInfo.setId(id.longValue());
			if(personelId != null)
				userAuthenticationInfo.setPersonelId(personelId.longValue());
			if(firstName != null)
				userAuthenticationInfo.setFirstName(firstName);
			if(lastName != null)
				userAuthenticationInfo.setLastName(lastName);
			if(msm2PersonelId != null)
				userAuthenticationInfo.setMsm2PersonelId(msm2PersonelId.longValue());
			if(masterId != null)
				userAuthenticationInfo.setMasterId(masterId.longValue());
			if(servisId != null)
				userAuthenticationInfo.setMasterId(servisId.longValue());
			if(bsm2ServisMudurluk != null)
				userAuthenticationInfo.setBsm2ServisMudurluk(bsm2ServisMudurluk.longValue());
			if(userName != null)
				userAuthenticationInfo.setUserName(userName);

		}
		return userAuthenticationInfo;
		
	}
	
	public UserAuthenticationInfo loginWithoutPassword(String userName) {
		String sql = "select A.ID, A.IHR1PERSONEL_ID,B.ADI,B.SOYADI,A.USERID,A.FSM1ROLES_ID, "
				+"(SELECT MSM2ORGANIZASYON_ID FROM IHR1PERSONELORGANIZASYON WHERE IHR1PERSONEL_ID = A.IHR1PERSONEL_ID and rownum=1) AS  MSM2PERSONEL_ID, "
				+"(SELECT IP.BSM2SERVIS_GOREV FROM IHR1PERSONEL IP WHERE IP.ID=A.IHR1PERSONEL_ID) SERVIS_ID, "
				+"(SELECT BSM2SERVIS_MUDURLUK FROM FSM1ROLES WHERE ID=A.FSM1ROLES_ID) BSM2SERVIS_MUDURLUK , "
				+"(SELECT MASTERID FROM MSM2ORGANIZASYON C,IHR1PERSONELORGANIZASYON B  WHERE B.IHR1PERSONEL_ID = A.IHR1PERSONEL_ID AND B.MSM2ORGANIZASYON_ID = C.ID and rownum=1) AS MASTERID "
				+"from fsm1users A, IHR1PERSONEL B WHERE A.USERID ='"+ userName +"' AND A.IKY_PERSONEL_ID=B.ID";
		
		List<Object> list = new ArrayList<Object>();
		Session session = sessionFactory.withOptions().interceptor(null).openSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		list = query.list();
		UserAuthenticationInfo userAuthenticationInfo = new UserAuthenticationInfo();

		for(Object o : list){
			int imageLength;
			Map map = (Map) o;
			String active = (String) map.get("ACTIVE");
			BigDecimal id = (BigDecimal)map.get("ID");
			BigDecimal personelId = (BigDecimal) map.get("IHR1PERSONEL_ID");
			String firstName = (String) map.get("ADI");
			String lastName = (String) map.get("SOYADI");
			BigDecimal msm2PersonelId = (BigDecimal)map.get("MSM2PERSONEL_ID");
			BigDecimal masterId = (BigDecimal) map.get("MASTERID");
			BigDecimal servisId = (BigDecimal) map.get("SERVIS_ID");
			BigDecimal bsm2ServisMudurluk = (BigDecimal) map.get("BSM2SERVIS_MUDURLUK");

			if(personelId != null)
				userAuthenticationInfo.setPersonelId(personelId.longValue());
			if(id != null)
				userAuthenticationInfo.setId(id.longValue());

			if (active != null) {
				if (active.equalsIgnoreCase("H")) {
					userAuthenticationInfo.setPersonelId(0);
					userAuthenticationInfo.setId(0);
				}
			}

			if(firstName != null)
				userAuthenticationInfo.setFirstName(firstName);
			if(lastName != null)
				userAuthenticationInfo.setLastName(lastName);
			if(msm2PersonelId != null)
				userAuthenticationInfo.setMsm2PersonelId(msm2PersonelId.longValue());
			if(masterId != null)
				userAuthenticationInfo.setMasterId(masterId.longValue());
			if(servisId != null)
				userAuthenticationInfo.setMasterId(servisId.longValue());
			if(bsm2ServisMudurluk != null)
				userAuthenticationInfo.setBsm2ServisMudurluk(bsm2ServisMudurluk.longValue());
			if(userName != null)
				userAuthenticationInfo.setUserName(userName);

		}
		return userAuthenticationInfo;
		
	}

	public UserAuthenticationInfo getUserInformationFromId(Long id) {
		String sql = "select A.ID, A.IHR1PERSONEL_ID,A.FIRSTNAME,A.LASTNAME,A.USERID,A.FSM1ROLES_ID, "
				+"(SELECT MSM2ORGANIZASYON_ID FROM IHR1PERSONELORGANIZASYON WHERE IHR1PERSONEL_ID = A.IHR1PERSONEL_ID and rownum=1) AS  MSM2PERSONEL_ID, "
				+"(SELECT IP.BSM2SERVIS_GOREV FROM IHR1PERSONEL IP WHERE IP.ID=A.IHR1PERSONEL_ID) SERVIS_ID, "
				+"(SELECT BSM2SERVIS_MUDURLUK FROM FSM1ROLES WHERE ID=A.FSM1ROLES_ID) BSM2SERVIS_MUDURLUK , "
				+"(SELECT MASTERID FROM MSM2ORGANIZASYON C,IHR1PERSONELORGANIZASYON B  WHERE B.IHR1PERSONEL_ID = A.IHR1PERSONEL_ID AND B.MSM2ORGANIZASYON_ID = C.ID and rownum=1) AS MASTERID "
				+"from fsm1users A WHERE A.ID ='"+ id +"'";

		List<Object> list = new ArrayList<Object>();
		Session session = sessionFactory.withOptions().interceptor(null).openSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		list = query.list();
		UserAuthenticationInfo userAuthenticationInfo = new UserAuthenticationInfo();

		for(Object o : list){
			int imageLength;
			Map map = (Map) o;
			BigDecimal personelId = (BigDecimal) map.get("IHR1PERSONEL_ID");
			String firstName = (String) map.get("FIRSTNAME");
			String lastName = (String) map.get("LASTNAME");
			String userId = (String) map.get("USERID");
			BigDecimal msm2PersonelId = (BigDecimal)map.get("MSM2PERSONEL_ID");
			BigDecimal masterId = (BigDecimal) map.get("MASTERID");
			BigDecimal servisId = (BigDecimal) map.get("SERVIS_ID");
			BigDecimal bsm2ServisMudurluk = (BigDecimal) map.get("BSM2SERVIS_MUDURLUK");


			if(id != null)
				userAuthenticationInfo.setId(id);
			if(personelId != null)
				userAuthenticationInfo.setPersonelId(personelId.longValue());
			if(firstName != null)
				userAuthenticationInfo.setFirstName(firstName);
			if(lastName != null)
				userAuthenticationInfo.setLastName(lastName);
			if(userId != null)
				userAuthenticationInfo.setUserName(userId);
			if(msm2PersonelId != null)
				userAuthenticationInfo.setMsm2PersonelId(msm2PersonelId.longValue());
			if(masterId != null)
				userAuthenticationInfo.setMasterId(masterId.longValue());
			if(servisId != null)
				userAuthenticationInfo.setMasterId(servisId.longValue());
			if(bsm2ServisMudurluk != null)
				userAuthenticationInfo.setBsm2ServisMudurluk(bsm2ServisMudurluk.longValue());
		}
		return userAuthenticationInfo;

	}


	public String getUserIdFromActiveDirectoryName(String adUserName){
		String sql = "SELECT USERID FROM FSM1USERS WHERE LOWER(ACTIVEDIRECTORYUSERNAME)=LOWER('" + adUserName + "')";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		String userId = (String)query.uniqueResult();
		return userId;
	}

	public UserAuthenticationInfo getUserNameFromActiveDirectoryName(String adUserName){
		String sql = "SELECT USERID FROM FSM1USERS WHERE LOWER(ACTIVEDIRECTORYUSERNAME)=LOWER('" + adUserName + "')";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		String userId = (String)query.uniqueResult();
		UserAuthenticationInfo userAuthenticationInfo = new UserAuthenticationInfo();
		userAuthenticationInfo.setUserName(userId);
		return userAuthenticationInfo;
	}

	@Override
	public List<UserAuthorizationInfo> getAuthorizationInfo(String userName) {
		String sql ="SELECT A.VSM1PROGS_ID,B.TYPE,B.NAME,B.DESCRIPTION,A.PARENT_ID,B.LINK,A.PARAMETER, "
			      + "A.KAYITDUZEYI,A.MENUID FROM (SELECT M.VSM1PROGS_ID,M.KODU,M.ISLEAF, M.PARENT_ID, "
			      + "M.PARAMETER,M.KAYITDUZEYI,M.ID MENUID FROM NSM1MENUS M WHERE M.VSM1PROGS_ID IN "
			      + "(SELECT DISTINCT VSM1PROGS_ID FROM YSM1PROGSEC WHERE ASM1ROLES_ID IN (SELECT A.ASM1ROLES_ID "
			      + "FROM ISM1USERROLES A,ASM1ROLES B,FSM1USERS C WHERE     USERID =" + "'"+userName+"' "
			      + "AND A.FSM1USERS_ID = C.ID AND NVL (B.KAYITOZELISMI, 'H') = 'MOBILE' AND A.ASM1ROLES_ID = B.ID "
			      + "AND B.TYPE = 'E'))) A, VSM1PROGS B WHERE A.VSM1PROGS_ID = B.ID ORDER BY A.KODU";
				
		List<Object> list = new ArrayList<Object>();
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		list = query.list();
		List<UserAuthorizationInfo> userAuthorizationInfoList = new ArrayList<UserAuthorizationInfo>();
		
		for(Object o : list){
			Map map = (Map) o;
			UserAuthorizationInfo userAuthorizationInfo = new UserAuthorizationInfo();
			
			BigDecimal  vsm1ProgsId = (BigDecimal)map.get("VSM1PROGS_ID");
			String type = (String)map.get("TYPE");
			String name = (String) map.get("NAME");
			String description = (String) map.get("DESCRIPTION");
			BigDecimal parentId = (BigDecimal) map.get("PARENT_ID");
			String link = (String)map.get("LINK");
			String parameter = (String) map.get("PARAMETER");
			BigDecimal kayitDuzeyi = (BigDecimal) map.get("KAYITDUZEYI");
			BigDecimal menuId = (BigDecimal) map.get("MENUID");
			
			if(vsm1ProgsId != null)
				userAuthorizationInfo.setVsm1ProgsId(vsm1ProgsId.longValue());
			if(type != null)
				userAuthorizationInfo.setType(type);
			if(name != null)
				userAuthorizationInfo.setName(name);
			if(description != null)
				userAuthorizationInfo.setDescription(description);
			if(parentId != null)
				userAuthorizationInfo.setParentId(parentId.longValue());
			if(link != null)
				userAuthorizationInfo.setLink(link);
			if(parameter != null)
				userAuthorizationInfo.setParameter(parameter);
			if(kayitDuzeyi != null)
				userAuthorizationInfo.setKayitDuzeyi(kayitDuzeyi.longValue());
			if(menuId != null)
				userAuthorizationInfo.setMenuId(menuId.longValue());
			
			userAuthorizationInfoList.add(userAuthorizationInfo);
		}
		
		return userAuthorizationInfoList;
	}

	public List<BYSMenu> getWholeBYSMenu(String userName){
		String sql = "select A.VSM1PROGS_ID,B.TYPE,B.NAME,B.DESCRIPTION,A.PARENT_ID,B.LINK,A.PARAMETER,a.KAYITDUZEYI,"
				+ "DECODE ("
				+ "(SELECT COUNT(*) FROM NSM1MENUS M1 WHERE PARENT_ID=A.VSM1PROGS_ID),"
				+ "0, 'E',"
				+ "'H')ISLEAF "
				+ ",A.id  from "
				+ "( select m.id,m.VSM1PROGS_ID,M.KODU,m.isleaf,m.PARENT_id,m.PARAMETER,m.KAYITDUZEYI from NSM1MENUS m "
				+ " WHERE  m.VSM1PROGS_ID in "
				+ "(select distinct VSM1PROGS_ID from "
				+ "	YSM1PROGSEC "
				+ "	where  ASM1ROLES_ID in "
				+ "	( select a.ASM1ROLES_ID from "
				+ " ISM1USERROLES a, ASM1ROLES b,FSM1USERS c "
				+ "	where  userid = '"
				+ userName
				+ "' and a.FSM1USERS_ID = c.id and a.ASM1ROLES_ID = b.id and b.type='E' AND A.ACTIVE='E' "
				+ ")) "
				+ "	) A, VSM1PROGS B WHERE A.VSM1PROGS_ID = B.ID "
				+ " ORDER BY A.KODU";
		
		List<BYSMenu> bysMenuList =  new ArrayList<BYSMenu>();
		List<Object> list = new ArrayList<Object>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		list = query.list();
		
		for(Object o : list){

			BYSMenu bysMenu = new BYSMenu();
			Map map = (Map)o;
			
			BigDecimal vsm1progsId =(BigDecimal) map.get("VSM1PROGS_ID");
			String type = (String)map.get("TYPE");
			String name = (String)map.get("NAME");
			String description = (String)map.get("DESCRIPTION");
			BigDecimal parentId = (BigDecimal)map.get("PARENT_ID");
			String link = (String)map.get("LINK");
			String parameter = (String) map.get("PARAMETER");
			BigDecimal kayitDuzeyi = (BigDecimal)map.get("KAYITDUZEYI");
			String isLeaf = (String) map.get("ISLEAF");
			BigDecimal id = (BigDecimal)map.get("ID");
			
			if(vsm1progsId != null)
				bysMenu.setId(String.valueOf(vsm1progsId.longValue()));
			if(type != null)
				bysMenu.setType(type);
			if(name != null)
				bysMenu.setText(name);
			if(description != null)
				bysMenu.setDescription(description);
			if(parentId != null){
				if(parentId.longValue() == -1)
					bysMenu.setParent(String.valueOf("#"));
				else
					bysMenu.setParent(String.valueOf(parentId.longValue()));
			}
			if(link != null)
				bysMenu.setData(link);
			if(parameter != null)
				bysMenu.setParameter(parameter);
			if(kayitDuzeyi != null)
				bysMenu.setKayitDuzeyi(kayitDuzeyi.intValue());
			if(isLeaf != null)
				bysMenu.setIsLeaf(isLeaf);

			
			bysMenuList.add(bysMenu);
				
		}
		return bysMenuList;
	}

	@Override
	public List<BYSMenu> getPartialBYSMenu(String userName, long parentId) {
		String sql = "select A.VSM1PROGS_ID,B.TYPE,B.NAME,B.DESCRIPTION,A.PARENT_ID,B.LINK,A.PARAMETER,a.KAYITDUZEYI,"
				+ "DECODE ("
				+ "(SELECT COUNT(*) FROM NSM1MENUS M1 WHERE PARENT_ID=A.VSM1PROGS_ID),"
				+ "0, 'E',"
				+ "'H')ISLEAF "
				+ ",A.id  from "
				+ "( select m.id,m.VSM1PROGS_ID,M.KODU,m.isleaf,m.PARENT_id,m.PARAMETER,m.KAYITDUZEYI from NSM1MENUS m "
				+ " WHERE  m.PARENT_id = "
				+ parentId
				+ " and m.VSM1PROGS_ID in "
				+ "(select distinct VSM1PROGS_ID from "
				+ "	YSM1PROGSEC "
				+ "	where  ASM1ROLES_ID in "
				+ "	( select a.ASM1ROLES_ID from "
				+ " ISM1USERROLES a, ASM1ROLES b,FSM1USERS c "
				+ "	where  userid = '"
				+ userName
				+ "' and a.FSM1USERS_ID = c.id and a.ASM1ROLES_ID = b.id and b.type='E' AND A.ACTIVE='E' "
				+ ")) "
				+ "	) A, VSM1PROGS B WHERE A.VSM1PROGS_ID = B.ID "
				+ " ORDER BY A.KODU";
		
		List<BYSMenu> bysMenuList =  new ArrayList<BYSMenu>();
		List<Object> list = new ArrayList<Object>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		list = query.list();
		
		for(Object o : list){

			BYSMenu bysMenu = new BYSMenu();
			Map map = (Map)o;
			
			BigDecimal vsm1progsId =(BigDecimal) map.get("VSM1PROGS_ID");
			String type = (String)map.get("TYPE");
			String name = (String)map.get("NAME");
			String description = (String)map.get("DESCRIPTION");
			BigDecimal parent = (BigDecimal)map.get("PARENT_ID");
			String link = (String)map.get("LINK");
			String parameter = (String) map.get("PARAMETER");
			BigDecimal kayitDuzeyi = (BigDecimal)map.get("KAYITDUZEYI");
			String isLeaf = (String) map.get("ISLEAF");
			BigDecimal id = (BigDecimal)map.get("ID");
			
			if(vsm1progsId != null)
				bysMenu.setId(String.valueOf(vsm1progsId.longValue()));
			if(type != null)
				bysMenu.setType(type);
			if(name != null)
				bysMenu.setText(name);
			if(description != null)
				bysMenu.setDescription(description);
			if(parent != null){
				if(parent.longValue() == -1)
					bysMenu.setParent(String.valueOf("#"));
				else
					bysMenu.setParent(String.valueOf(parent.longValue()));
			}
			if(link != null)
				bysMenu.setData(link);
			if(parameter != null)
				bysMenu.setParameter(parameter);
			if(kayitDuzeyi != null)
				bysMenu.setKayitDuzeyi(kayitDuzeyi.intValue());
			if(isLeaf != null)
				bysMenu.setIsLeaf(isLeaf);

			
			bysMenuList.add(bysMenu);
				
		}
		return bysMenuList;
	}
	
	@Override
	public List<BYSMenu> getChilds(String userName, long parentId) {
		String sql = "select A.VSM1PROGS_ID,B.TYPE,B.NAME,B.DESCRIPTION,A.PARENT_ID,B.LINK,A.PARAMETER,a.KAYITDUZEYI,"
				+ "DECODE ("
				+ "(SELECT COUNT(*) FROM NSM1MENUS M1 WHERE PARENT_ID=A.VSM1PROGS_ID),"
				+ "0, 'E',"
				+ "'H')ISLEAF "
				+ ",A.id  from "
				+ "( select m.id,m.VSM1PROGS_ID,M.KODU,m.isleaf,m.PARENT_id,m.PARAMETER,m.KAYITDUZEYI from NSM1MENUS m "
				+ " WHERE  m.PARENT_id in "
				+ "(select  m.VSM1PROGS_ID from NSM1MENUS m "
	            + " WHERE  m.PARENT_id = " 
				+ parentId +")"
				+ " and m.VSM1PROGS_ID in "
				+ "(select distinct VSM1PROGS_ID from "
				+ "	YSM1PROGSEC "
				+ "	where  ASM1ROLES_ID in "
				+ "	( select a.ASM1ROLES_ID from "
				+ " ISM1USERROLES a, ASM1ROLES b,FSM1USERS c "
				+ "	where  userid = '"
				+ userName
				+ "' and a.FSM1USERS_ID = c.id and a.ASM1ROLES_ID = b.id and b.type='E' AND A.ACTIVE='E' "
				+ ")) "
				+ "	) A, VSM1PROGS B WHERE A.VSM1PROGS_ID = B.ID "
				+ " ORDER BY A.KODU";
		
		List<BYSMenu> bysMenuList =  new ArrayList<BYSMenu>();
		List<Object> list = new ArrayList<Object>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		list = query.list();
		
		for(Object o : list){

			BYSMenu bysMenu = new BYSMenu();
			Map map = (Map)o;
			
			BigDecimal vsm1progsId =(BigDecimal) map.get("VSM1PROGS_ID");
			String type = (String)map.get("TYPE");
			String name = (String)map.get("NAME");
			String description = (String)map.get("DESCRIPTION");
			BigDecimal parent = (BigDecimal)map.get("PARENT_ID");
			String link = (String)map.get("LINK");
			String parameter = (String) map.get("PARAMETER");
			BigDecimal kayitDuzeyi = (BigDecimal)map.get("KAYITDUZEYI");
			String isLeaf = (String) map.get("ISLEAF");
			BigDecimal id = (BigDecimal)map.get("ID");
			
			if(vsm1progsId != null)
				bysMenu.setId(String.valueOf(vsm1progsId.longValue()));
			if(type != null)
				bysMenu.setType(type);
			if(name != null)
				bysMenu.setText(name);
			if(description != null)
				bysMenu.setDescription(description);
			if(parent != null){
				if(parent.longValue() == -1)
					bysMenu.setParent(String.valueOf("#"));
				else
					bysMenu.setParent(String.valueOf(parent.longValue()));
			}
			if(link != null)
				bysMenu.setData(link);
			if(parameter != null)
				bysMenu.setParameter(parameter);
			if(kayitDuzeyi != null)
				bysMenu.setKayitDuzeyi(kayitDuzeyi.intValue());
			if(isLeaf != null)
				bysMenu.setIsLeaf(isLeaf);

			
			bysMenuList.add(bysMenu);
				
		}
		return bysMenuList;
	}
	
	public List<BYSMenu> getWholeYBSMenu(String userName){
		
		return null;
	}

	@Override
	public long getTopNavigationMessageCount(long userId) {
		String sql = "select count(*) Toplam from VSM1MESAJ WHERE OKUNDU='H'";
		
		List<Object> list = new ArrayList<Object>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		list = query.list();
		BigDecimal toplam = null;
		
		for(Object o : list){
			Map map = (Map)o;
			toplam =(BigDecimal) map.get("TOPLAM");
		}
		return toplam.longValue();
	}
	
	public List<Message> getTopNavigationMessages(long userId){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		String sql = "select id, mesaj, okundu, crdate from VSM1MESAJ WHERE fsm1users_id =" +userId
                    +" and (isactive is null or isactive!='H')"
                    +" order by okundu desc, crdate desc" ;
		
		List<Message> messageList =  new ArrayList<Message>();
		List<Object> list = new ArrayList<Object>();
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		list = query.list();
		
		for(Object o : list){

			Message message = new Message();
			Map map = (Map)o;
			
			BigDecimal id = (BigDecimal)map.get("ID");
			String messageText = (String)map.get("MESAJ");
			String isRead = (String)map.get("OKUDU");
			Date crDate = (Date)map.get("CRDATE");
			
			if(id != null)
				message.setId(id.longValue());
			if(messageText != null)
				message.setMessageText(messageText);
			if(isRead !=  null)
				message.setIsRead(isRead);
			if(crDate != null)
				message.setCreateDate(dateFormat.format(crDate));
			
			messageList.add(message);
		}
		
		return messageList;
	}
	

}
