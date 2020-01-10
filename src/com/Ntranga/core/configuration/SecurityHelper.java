package com.Ntranga.core.configuration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

public class SecurityHelper {
	private static Logger logger = Logger.getLogger(SecurityHelper.class);

	public static String getErrorMessage(HttpServletRequest request) {
		Exception exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if (logger.isDebugEnabled()) {
			logger.debug("Reading the login failed exception details with key SPRING_SECURITY_LAST_EXCEPTION ", exception);
		}
		String error = "";
		if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof DisabledException) {
			error = "we are throwing disabling your account";
		} else {
			error = "Invalid username and password!";
		}
		return error;
	}
	
	public static void invalidateSession(HttpServletRequest request){
		request.getSession().invalidate();
	}
}
