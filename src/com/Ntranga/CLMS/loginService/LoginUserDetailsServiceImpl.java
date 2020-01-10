package com.Ntranga.CLMS.loginService;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ntranga.CLMS.loginDao.LoginUserDetailsDao;
import com.Ntranga.core.CLMS.entities.MRoles;

@Service("userDetailsService")
public class LoginUserDetailsServiceImpl implements UserDetailsService {


		@Autowired
		private LoginUserDetailsDao loginUserDetailsDao;

		@Transactional(readOnly=true)
		@Override
		public UserDetails loadUserByUsername(final String username) 
			throws UsernameNotFoundException {
		
			com.Ntranga.core.CLMS.entities.MUsers user = loginUserDetailsDao.findByUserName(username);
			if(user==null){	           
	            throw new UsernameNotFoundException("Username not found");
	        }
			List<GrantedAuthority> authorities = 
	                                      buildUserAuthority(user.getMRoles());

			return buildUserForAuthentication(user, authorities);
			
		}

		private User buildUserForAuthentication(com.Ntranga.core.CLMS.entities.MUsers user, 
			List<GrantedAuthority> authorities) {
			return new User(user.getUserName(), user.getPassword(), 
				user.getIsActive().equals("Y") ? true : false, true, true, true, authorities);
		}

		private List<GrantedAuthority> buildUserAuthority(MRoles mRoles) {

			Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

			// Build user's authorities
			//for (MRoles userRole : mRoles) {
				setAuths.add(new SimpleGrantedAuthority(mRoles.getRoleName()));
			//}

			List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

			return Result;
		}

}
