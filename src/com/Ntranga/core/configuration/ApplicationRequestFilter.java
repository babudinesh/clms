package com.Ntranga.core.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


@Component(value = "applicationRequestFilter")
public class ApplicationRequestFilter extends GenericFilterBean {

	private static Logger logger = Logger.getLogger(ApplicationRequestFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {		
		chain.doFilter(request, response);
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
		/*	System.out.println(httpRequest.getRequestedSessionId()+"::"+httpRequest.getCookies());
			
			Cookie[] cookies = ((HttpServletRequest) request).getCookies();
			String cookieName = "";
			
			for ( int i=0; i<cookies.length; i++) {
			  Cookie cookie = cookies[i];			 
			  cookieName =cookieName+ cookie.getValue();
			  }
			 System.out.println(cookieName);
		*/
		
		
			/*UserDetails userDetails = (UserDetails) httpRequest.getSession().getAttribute(UserDetails.SESSION_KEY);
			logger.debug("Accessing resource " + httpRequest.getServletPath());
			if (userDetails != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Valid user session identified " + userDetails.getUserID());
				}
				UserContext userContext = new UserContext();
				userContext.getAuditUser().setUserID(userDetails.getUserID());
				UserThreadLocal.set(userContext);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("No valid user session identified");
				}
			}
			chain.doFilter(request, response);
			if (logger.isDebugEnabled()) {
				logger.debug("Finished processing the request.");
				logger.debug("Unsetting the useraudit object from the current thread");
			}
			UserThreadLocal.unset();*/
		} catch (Exception ex) {
			logger.error("Exception while processing request ", ex);
		}
	}
}