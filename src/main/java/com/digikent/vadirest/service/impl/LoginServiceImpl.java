package com.digikent.vadirest.service.impl;

import com.digikent.vadirest.dao.LoginDAO;
import com.digikent.vadirest.dto.BYSMenu;
import com.digikent.vadirest.dto.Message;
import com.digikent.vadirest.dto.UserAuthenticationInfo;
import com.digikent.vadirest.dto.UserAuthorizationInfo;
import com.digikent.vadirest.service.LoginService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired(required = true)
	private LoginDAO loginDAO;
	
	public UserAuthenticationInfo loginWithPassword(String userName, String password) {
		return loginDAO.loginWithPassword(userName,password);
		
	}
	
	public UserAuthenticationInfo loginWithoutPassword(String userName) {
		return loginDAO.loginWithoutPassword(userName);
		
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

}
