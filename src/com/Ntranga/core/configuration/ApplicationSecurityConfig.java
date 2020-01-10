package com.Ntranga.core.configuration;

import org.apache.log4j.Logger;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

public abstract class ApplicationSecurityConfig {

	private static Logger logger = Logger.getLogger(ApplicationSecurityConfig.class);

	public void configure(WebSecurity web) throws Exception {
		logger.debug("ApplicationSecurityConfig.configure(WebSecurity web) not overridden applying default matches for static resources \"/images/**\", \"/css/**\", \"/js/**\" ");
		web.ignoring().antMatchers("/images/**", "/css/**", "/js/**");
	}

	public abstract void configure(HttpSecurity http) throws Exception;
}
