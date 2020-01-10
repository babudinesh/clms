package com.Ntranga.CLMS.loginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.loginDao.LoginUserDetailsDao;
import com.Ntranga.core.UserDetails;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginUserDetailsDao loginUserDetailsDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		return loginUserDetailsDao.getUserDetails(username);
	}

	@Override
	public UserDetails validateUser(String username, String password) {
		// TODO Auto-generated method stub
		return loginUserDetailsDao.validateUser(username, password);
	}

	@Override
	public String getSchemaNameByUserId(String userId) {
		// TODO Auto-generated method stub
		return loginUserDetailsDao.getSchemaNameByUserId(userId);
	}

}
