package com.digikent.web.rest;


import com.digikent.config.Constants;
import com.digikent.vadirest.dto.*;
import com.digikent.vadirest.service.LoginService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class LoginController {

	private final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired(required = true)
	private LoginService loginService;
	
	@RequestMapping(value ="/current" , method = RequestMethod.PUT)
	public UserAuthenticationInfo loginWithPassword(@RequestBody UserDTO user){
		String userName = user.getUserName();
		String password = user.getPassword();
		LOG.debug("REST request to login:{}", userName);
		UserAuthenticationInfo userAuthenticationInfo = loginService.activeDirectoryLogin(userName,password);
		if(userAuthenticationInfo == null)
			return loginService.loginWithPassword(userName, password);
		else
			return userAuthenticationInfo;
	}
	@RequestMapping(value ="/currentWithhoutPass" , method = RequestMethod.PUT)
	public UserAuthenticationInfo loginWithoutPassword(@RequestBody UserDTO user){
		String userName = user.getUserName();
		LOG.debug("REST request to login:{}", userName);
		return loginService.loginWithoutPassword(userName);	
	}

	@RequestMapping(value ="/userInformation" , method = RequestMethod.PUT)
	public UserAuthenticationInfo getUserInformationFromId(@RequestBody UserDTO user){
		Long id = user.getId();
		LOG.debug("REST request to login:{}", id);
		return loginService.getUserInformationFromId(id);
	}

	@RequestMapping(value ="/userName" , method = RequestMethod.PUT)
	public UserAuthenticationInfo getUserIdFromActiveDirectoryName(@RequestBody UserDTO user){
		LOG.debug("REST request to get userId with LDAP :{}", user.getActiveDirectoryUserName());
		return loginService.getUserNameFromActiveDirectoryName(user.getActiveDirectoryUserName());
	}

	@RequestMapping(value ="/baseUrl" , method = RequestMethod.GET)
	public List<BaseUrlDTO> getBaseUrl(){
		LOG.debug("application is old version. will send upgrade link");
		return null;
	}

	@RequestMapping(value ="/baseUrl/{version}" , method = RequestMethod.GET)
	public ResponseBaseUrlDTO getBaseUrlWithVersion(@PathVariable("version") String version){
		LOG.debug("version = " + version);
		Boolean isVersionUsable = loginService.isVersionUsable(version);
		ResponseBaseUrlDTO responseBaseUrlDTO = new ResponseBaseUrlDTO();

		if (!isVersionUsable) {
			responseBaseUrlDTO.setMessageId(Constants.MESSAGE_OLD_VERSION);
			responseBaseUrlDTO.setBaseUrlDTOList(null);
		} else {
			LOG.debug("REST request to get baseUrl");
			responseBaseUrlDTO.setMessageId(Constants.MESSAGE_CURRENT_VERSION);
			responseBaseUrlDTO.setBaseUrlDTOList(loginService.getBaseUrl());
		}

		return responseBaseUrlDTO;
	}
	
	@RequestMapping(value = "/yetki/{username}/" , method = RequestMethod.GET)
	public List<UserAuthorizationInfo> getAuthorizationInfo(@PathVariable("username") String userName){
		LOG.debug("Rest request to get yetki:{}", userName);
		return loginService.getAuthorizationInfo(userName);
		
	}
	
	@RequestMapping(value = "/BYSMenu/{userName}" , method = RequestMethod.GET)
	public List<BYSMenu> getWholeBYSMenu(@PathVariable("userName") String userName){
		System.out.println("----------get BYS menu--------------");
		return loginService.getWholeBYSMenu(userName);
	}
	
	@RequestMapping(value = "/BYSMenu/{userName}/{parentId}" , method = RequestMethod.GET)
	public List<BYSMenu> getPartialBYSMenu(@PathVariable("userName") String userName, @PathVariable("parentId") long parentId){
		System.out.println("----------get partial BYS menu--------------");
		System.out.println(parentId);
		return loginService.getPartialBYSMenu(userName,parentId);
	}
	
	@RequestMapping(value = "/topNavigationMesajSayisi/{userId}" , method = RequestMethod.GET)
	public long getTopNavigationMessageCount(@PathVariable("userId") long userId){
		System.out.println("----------get topnav message count--------------");
		System.out.println(userId);
		return loginService.getTopNavigationMessageCount(userId);
	}
	
	@RequestMapping(value = "/topNavigationMesaj/{userId}" , method = RequestMethod.GET)
	public List<Message> getTopNavigationMessages(@PathVariable("userId") long userId){
		System.out.println("----------get topnav messages--------------");
		System.out.println(userId);
		return loginService.getTopNavigationMessages(userId);
	}
	
}
