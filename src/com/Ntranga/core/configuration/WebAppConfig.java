package com.Ntranga.core.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.MappingMediaTypeFileExtensionResolver;
import org.springframework.web.accept.MediaTypeFileExtensionResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@EnableWebMvc
@ComponentScan(basePackages = { "com.Ntranga.**.**" }, includeFilters = @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION))
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter  {

	private static Logger logger = Logger.getLogger(WebAppConfig.class);


	public InternalResourceViewResolver viewResolver() {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating view resolver object with prefix /WEB-INF/jsp/ and suffix .jsp ");
		}
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/pages/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(Ordered.LOWEST_PRECEDENCE);
		logger.debug("Setting view resolver");

		if (logger.isDebugEnabled()) {
			logger.debug("Exposing the bean \"properties\" as request attribute object use ${properties}  in jsp to refer.");
		}
		viewResolver.setExposedContextBeanNames(new String[] { "properties" });
		return viewResolver;
	}

	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		
		HashMap<String, MediaType> map = new HashMap<String, MediaType>();
		if (logger.isDebugEnabled()) {
			logger.debug("Adding mediatype json with MIME type " + MediaType.APPLICATION_JSON);
		}
		map.put("json", MediaType.APPLICATION_JSON);
		if (logger.isDebugEnabled()) {
			logger.debug("Adding mediatype xls with MIME type vnd.ms-excel");
		}
		map.put("xls", new MediaType("application", "x-msexcel"));
		if (logger.isDebugEnabled()) {
			logger.debug("Adding mediatype pdf with MIME type pdf");
		}
		map.put("pdf", new MediaType("application", "pdf"));
		MediaTypeFileExtensionResolver fileExtensionResolver = new MappingMediaTypeFileExtensionResolver(map);
		manager.addFileExtensionResolvers(fileExtensionResolver);
		
		resolver.setContentNegotiationManager(manager);
		List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
		viewResolvers.add(viewResolver());		
		resolver.setViewResolvers(viewResolvers);
		return resolver;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(104857600);
		return multipartResolver;
	}

}
