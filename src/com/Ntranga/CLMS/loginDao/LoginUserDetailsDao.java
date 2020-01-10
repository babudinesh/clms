package com.Ntranga.CLMS.loginDao;

import com.Ntranga.core.UserDetails;
import com.Ntranga.core.CLMS.entities.MUsers;

public interface LoginUserDetailsDao {

	public  MUsers findByUserName(String username);

	public UserDetails getUserDetails(String username);
	
	public UserDetails validateUser(String username,String password);

	public String getSchemaNameByUserId(String userId);
}
