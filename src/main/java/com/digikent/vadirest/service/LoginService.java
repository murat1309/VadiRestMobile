package com.digikent.vadirest.service;

import com.digikent.vadirest.dto.BYSMenu;
import com.digikent.vadirest.dto.Message;
import com.digikent.vadirest.dto.UserAuthenticationInfo;
import com.digikent.vadirest.dto.UserAuthorizationInfo;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;


public interface LoginService {

	public UserAuthenticationInfo loginWithPassword(String userName, String password);
	public UserAuthenticationInfo loginWithoutPassword(String userName);
	public List<UserAuthorizationInfo> getAuthorizationInfo(String userName);
	public List<BYSMenu> getWholeBYSMenu(String userName);
	public List<BYSMenu> getPartialBYSMenu(String userName, long parentId);
	public long getTopNavigationMessageCount(long userId);
	public List<Message> getTopNavigationMessages(long userId);

}
