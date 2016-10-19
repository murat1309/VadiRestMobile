package com.digikent.vadirest.service;

import com.digikent.vadirest.dto.*;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface LoginService {

	public UserAuthenticationInfo loginWithPassword(String userName, String password);
	public UserAuthenticationInfo loginWithoutPassword(String userName);
	public UserAuthenticationInfo getUserInformationFromId(Long id);
	public String getUserIdFromActiveDirectoryName(String adUserName);
	public UserAuthenticationInfo getUserNameFromActiveDirectoryName(String adUserName);
	public List<BaseUrlDTO> getBaseUrl();
	public List<UserAuthorizationInfo> getAuthorizationInfo(String userName);
	public List<BYSMenu> getWholeBYSMenu(String userName);
	public List<BYSMenu> getPartialBYSMenu(String userName, long parentId);
	public long getTopNavigationMessageCount(long userId);
	public List<Message> getTopNavigationMessages(long userId);
	public UserAuthenticationInfo activeDirectoryLogin(String userName, String password);

}
