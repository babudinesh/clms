package com.Ntranga.CLMS.loginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class LoginAuthenticationProvider  extends DaoAuthenticationProvider{
	
	
	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}

	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			Authentication auth = super.authenticate(authentication);
			System.out.println(authentication.getName());
			//userDetailsDao.resetFailAttempts(authentication.getName());
			return auth;
		} catch (BadCredentialsException e) {
			if(logger.isDebugEnabled()){
				logger.error("Exception while login ",e);
			}
			//userDetailsDao.updateFailAttempts(authentication.getName());
			throw e;
		} catch (LockedException e) {
			if(logger.isDebugEnabled()){
				logger.error("Exception while login ",e);
			}
			throw e;
		} catch (DisabledException e) {
			if(logger.isDebugEnabled()){
				logger.error("Exception while login ",e);
			}
			throw new DisabledException("we are disabling your account");
		}

	}

}
