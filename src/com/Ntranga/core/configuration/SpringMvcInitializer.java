package com.Ntranga.core.configuration;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() { 
		return new Class[] {ApplicationConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
    protected Filter[] getServletFilters() {
        Filter [] singleton = { new CORSFilter()};
        return singleton;
    }
	
	@Override
	protected String[] getServletMappings() {
		if (logger.isDebugEnabled())
			logger.debug("DispatcherServlet handling urls = { '*.view','*.xlsx','*.xls','*.pdf','*.json','/j_spring_security_check' }");
		return new String[] { "*.view", "*.xlsx", "*.xls", "*.pdf", "*.json", "/j_spring_security_check"};
	}
	

	@Override
	protected Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
		FilterRegistration delegatingFilterProxy = servletContext.getFilterRegistration("springSecurityFilterChain");
		logger.debug("DelegatingFilterProxy = " + delegatingFilterProxy);
		if (delegatingFilterProxy != null) {
			delegatingFilterProxy.addMappingForUrlPatterns(null, true, "*.view");
			delegatingFilterProxy.addMappingForUrlPatterns(null, true, "*.xlsx");
			delegatingFilterProxy.addMappingForUrlPatterns(null, true, "*.xls");
			delegatingFilterProxy.addMappingForUrlPatterns(null, true, "*.pdf");
			delegatingFilterProxy.addMappingForUrlPatterns(null, true, "*.json");				
		}
		return super.registerServletFilter(servletContext, filter);
	}

}