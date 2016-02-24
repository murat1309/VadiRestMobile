package com.digikent.web.rest;


import com.digikent.vadirest.dto.BYSMenu;
import com.digikent.vadirest.dto.Message;
import com.digikent.vadirest.dto.UserAuthenticationInfo;
import com.digikent.vadirest.dto.UserAuthorizationInfo;
import com.digikent.vadirest.service.LoginService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired(required = true)
	private LoginService loginService;
	
	@RequestMapping(value ="/current/{userName}/{password}" , method = RequestMethod.GET)
	public UserAuthenticationInfo loginWithPassword(@PathVariable("userName") String userName, @PathVariable("password") String password){
		System.out.println("----------login-------------");
		return loginService.loginWithPassword(userName, password);	
	}	
	
	@RequestMapping(value ="/currentWithhoutPass/{userName}" , method = RequestMethod.GET)
	public UserAuthenticationInfo loginWithoutPassword(@PathVariable("userName") String userName){
		System.out.println("----------login-------------");
		return loginService.loginWithoutPassword(userName);	
	}
	
	@RequestMapping(value = "/yetki/{username}" , method = RequestMethod.GET)
	public List<UserAuthorizationInfo> getAuthorizationInfo(@PathVariable("username") String userName){
		System.out.println("----------yetki---------");
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
