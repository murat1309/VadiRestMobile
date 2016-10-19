package com.digikent.vadirest.dao;

import com.digikent.vadirest.dto.BYSMenu;
import com.digikent.vadirest.dto.Message;
import com.digikent.vadirest.dto.UserAuthenticationInfo;
import com.digikent.vadirest.dto.UserAuthorizationInfo;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;


public interface LoginDAO {
	public UserAuthenticationInfo loginWithPassword(String userName, String password);
	public UserAuthenticationInfo loginWithoutPassword(String userName);
	public UserAuthenticationInfo getUserInformationFromId(Long id);
	public String getUserIdFromActiveDirectoryName(String adUserName);
	public UserAuthenticationInfo getUserNameFromActiveDirectoryName(String adUserName);
	public List<UserAuthorizationInfo> getAuthorizationInfo(String userName);
	public List<BYSMenu> getWholeBYSMenu(String userName);
	public List<BYSMenu> getPartialBYSMenu(String userName, long parentId);
	public List<BYSMenu> getChilds(String userName, long parentId);
	public long getTopNavigationMessageCount(long userId);
	public List<Message> getTopNavigationMessages(long userId);

}
