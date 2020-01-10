package com.Ntranga.CLMS.loginService;

import com.Ntranga.core.UserDetails;

public interface LoginService {

	public UserDetails loadUserByUsername(final String username);
	
	public UserDetails validateUser(String username,String password);
	
	public String getSchemaNameByUserId(String userId);
}
