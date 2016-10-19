package com.digikent.vadirest.service.impl;

import com.digikent.vadirest.dao.LoginDAO;
import com.digikent.vadirest.dto.*;
import com.digikent.vadirest.service.LoginService;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.documentum.xml.xpath.operations.Bool;
import com.vadi.digikent.base.util.FileUtil;
import com.vadi.digikent.ws.ad.model.ActiveDirectoryAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.deser.Deserializers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;


@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService {

	private final Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired(required = true)
	private LoginDAO loginDAO;
	
	public UserAuthenticationInfo loginWithPassword(String userName, String password) {
		return loginDAO.loginWithPassword(userName,password);
		
	}
	
	public UserAuthenticationInfo loginWithoutPassword(String userName) {
		return loginDAO.loginWithoutPassword(userName);
		
	}

	public UserAuthenticationInfo getUserInformationFromId(Long id){
		return loginDAO.getUserInformationFromId(id);
	}

	public String getUserIdFromActiveDirectoryName(String adUserName){
		return loginDAO.getUserIdFromActiveDirectoryName(adUserName);
	}

	public UserAuthenticationInfo getUserNameFromActiveDirectoryName(String adUserName){
		return loginDAO.getUserNameFromActiveDirectoryName(adUserName);
	}

	public UserAuthenticationInfo getUserNameFromActiveDirectoryUserName(String activeDirectoryUserName){
		return null;
	}

	public List<BaseUrlDTO> getBaseUrl(){
		return readFromUrlFile();
	}

	@Override
	public List<UserAuthorizationInfo> getAuthorizationInfo(String userName) {
		// TODO Auto-generated method stub
		return loginDAO.getAuthorizationInfo(userName);
	}
	
	public List<BYSMenu> getWholeBYSMenu(String userName){
		return loginDAO.getWholeBYSMenu(userName);
	}
	
	public List<BYSMenu> getPartialBYSMenu(String userName,long parentId){
		List<BYSMenu> parentList =  loginDAO.getPartialBYSMenu(userName,parentId);
		List<BYSMenu> childList  =  loginDAO.getChilds(userName,parentId);
		parentList.addAll(childList);
		return parentList;
	}

	public long getTopNavigationMessageCount(long userId) {
		return loginDAO.getTopNavigationMessageCount(userId);
	}
	
	public List<Message> getTopNavigationMessages(long userId){
		return loginDAO.getTopNavigationMessages(userId);
	}


	private List<BaseUrlDTO> readFromUrlFile() {

		String digikentPath = System.getenv("DIGIKENT_PATH");
		Properties urlProp = FileUtil.getPropsFromFile(digikentPath
				+ "/services/baseUrl.properties");
		List<BaseUrlDTO> baseUrlDTOs = new ArrayList<BaseUrlDTO>();

		Enumeration<Object> keys = urlProp.keys();
		while (keys.hasMoreElements()) {
			BaseUrlDTO baseUrlDTO = new BaseUrlDTO();
			String key = (String) keys.nextElement();
			String value = urlProp.getProperty(key);
			LOG.debug("Key,value:{}", key, value);
			baseUrlDTO.setUrlName(key);
			baseUrlDTO.setUrlPath(value);
			baseUrlDTOs.add(baseUrlDTO);
		}
		return baseUrlDTOs;
	}

	private void readFromVadiPropFile() {

		String digikentPath = System.getenv("DIGIKENT_PATH");
		Properties urlProp = FileUtil.getPropsFromFile(digikentPath
				+ "/services/VadiProp.properties");
		List<BaseUrlDTO> baseUrlDTOs = new ArrayList<BaseUrlDTO>();

		String adDomainName = urlProp.getProperty("AdDomainName");
		String adProviderUrl = urlProp.getProperty("AdProviderUrl");
		String adUsersContext = urlProp.getProperty("AdUsersContext");
		String adGroupsContext = urlProp.getProperty("AdGroupsContext");
	}

	private Boolean logonHelper(String userName, String password){
		LOG.debug("Login service activeDirectory helper:{}", userName);
		String digikentPath = System.getenv("DIGIKENT_PATH");
		Properties urlProp = FileUtil.getPropsFromFile(digikentPath + "/services/VadiProp.properties");

		String adDomainName    = urlProp.getProperty("AdDomainName");
		String adProviderUrl   = urlProp.getProperty("AdProviderUrl");

		Hashtable env = new Hashtable();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION,"simple");
		env.put(Context.SECURITY_PRINCIPAL, adDomainName + "\\" + userName);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.PROVIDER_URL, adProviderUrl);
		env.put("java.naming.ldap.attributes.binary", "objectGUID");
		env.put("java.naming.referral", "follow");

		DirContext ctx = null;
		try
		{
			ctx = new InitialDirContext(env);
			return true;
		}
		catch (NamingException nEx)
		{
			ctx = null;
			nEx.printStackTrace();
			return false;
		}
	}

	public UserAuthenticationInfo activeDirectoryLogin(String userName, String password){

		String digikentPath = System.getenv("DIGIKENT_PATH");
		Properties urlProp = FileUtil.getPropsFromFile(digikentPath + "/services/VadiProp.properties");

		String adDomainName    = urlProp.getProperty("AdDomainName");
		String adProviderUrl   = urlProp.getProperty("AdProviderUrl");
		String adUsersContext  = urlProp.getProperty("AdUsersContext");
		String adGroupsContext = urlProp.getProperty("AdGroupsContext");

		String dkUserName = loginDAO.getUserIdFromActiveDirectoryName(userName);
		LOG.debug("Login service activeDirectory dkUserName:{}", dkUserName);

		if (dkUserName!=null && !dkUserName.equals("")) {

			ActiveDirectoryAction ad = new ActiveDirectoryAction();
			ad.setDomainName(adDomainName);
			ad.setProviderUrl(adProviderUrl);
			ad.setUsersContext(adUsersContext);
			ad.setGroupsContext(adGroupsContext);

			if (logonHelper(userName, password)) {
				UserAuthenticationInfo userAuthenticationInfo = loginDAO.loginWithoutPassword(dkUserName);
				LOG.debug("Login with active directory succesfull:{}", userAuthenticationInfo.getUserName(), userAuthenticationInfo.getId());
				return userAuthenticationInfo;
			} else
				LOG.debug("Login with active directory failed:{}", userName);
		}

		return null;
	}



}
