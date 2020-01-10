package com.Ntranga.core.configuration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static Logger logger = Logger.getLogger(SecurityConfig.class);


	@Autowired(required = false)
	@Qualifier("applicationSecurityConfig")
	ApplicationSecurityConfig applicationSecurityConfig;

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/css/**", "/js/**");
		if (applicationSecurityConfig == null) {
			logger.error("No security customisation object found. Applying default matches for static resources. \"/images/**\", \"/css/**\", \"/js/**\"");
		} else {
			logger.info("Security customisation object found. Applying rules for static resources as defined in security config file");
			applicationSecurityConfig.configure(web);
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http.csrf().disable().formLogin().loginPage("/login.view?session_expired=1").failureUrl("/loginfailed.view").defaultSuccessUrl("/CustomerController/CustomerList.view")  // welcome  CustomerList
				.loginProcessingUrl("/j_spring_security_check").usernameParameter("j_username").passwordParameter("j_password").and()
			    .addFilterBefore(new ApplicationRequestFilter(), BasicAuthenticationFilter.class).logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/logout.view")
				.invalidateHttpSession(true).and().exceptionHandling().accessDeniedPage("/403").and().sessionManagement().maximumSessions(3).and().and().headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN));
		if (applicationSecurityConfig == null) {
			logger.error("No security customisation object found. No rules will be applied to URLs");
		} else {
			logger.info("Security customisation object found. Rules will be applied to URLs");
			applicationSecurityConfig.configure(http);
		}

	}
		
	
}