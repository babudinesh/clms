package com.Ntranga.core.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ServletContextAware;

import com.Ntranga.core.StringHelper;


@Configuration
@ComponentScan(basePackages = { "com.Ntranga.**.**" }, excludeFilters = @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION))
@Import({ SecurityConfig.class, WebAppConfig.class })
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class ApplicationConfiguration implements ServletContextAware, ApplicationContextAware {

	private static Logger logger = Logger.getLogger(ApplicationConfiguration.class);

	private ServletContext servletContext;
	private ApplicationContext applicationContext;
	@Autowired
	private Environment env;

	private final static String DB_DRIVER = "db.driver";
	private final static String DB_URL = "db.url";
	private final static String DB_USERNAME = "db.username";
	private final static String DB_PASSWORD = "db.password";

	private final static String HIB_DIALECT = "hibernate.dialect";
	private final static String HIB_FORMAT_SQL = "hibernate.format_sql";
	private final static String HIB_SHOW_SQL = "hibernate.show_sql";
	private final static String HIB_HBM2DDL_AUTO  ="hibernate.hbm2ddl.auto";
	 
	
	private final static String JSP_PROPERTY_FILE_PATH = "/WEB-INF/properties/pageValidations/jsp.properties";
	private final static String PROPERTIES_PATH = "/WEB-INF/properties/settings/";

	// Email Properties
	private final static String EMAIL_HOST = "email.host";
	private final static String EMAIL_PORT = "email.port";
	private final static String EMAIL_USERNAME = "email.username";
	private final static String EMAIL_PASSWORD = "email.password";
	private final static String EMAIL_PROTOCOL = "email.protocol";
	private final static String EMAIL_SMTP_AUTH = "mail.smtp.auth";
	private final static String EMAIL_SMTP_STARTTLS = "mail.smtp.starttls.enable";
	private final static String EMAIL_SMTP_DEBUG = "mail.smtp.debug";
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

	@Bean
	public PropertySourcesPlaceholderConfigurer getPropertyPlaceholderConfigurer() throws IOException {
		AbstractApplicationContext context = (AbstractApplicationContext) applicationContext;
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		if (logger.isDebugEnabled()) {
			logger.debug("Property path directory relative directory " + PROPERTIES_PATH);
		}
		String realPath = servletContext.getRealPath(PROPERTIES_PATH);
		if (logger.isDebugEnabled()) {
			logger.debug("Property path directory real directory " + realPath);
		}
		File directory = new File(realPath);
		File[] files = directory.listFiles();
		if (logger.isDebugEnabled()) {
			 logger.debug("Found files " + files != null ? files.length : 0);
		}
		Resource resources[] = new FileSystemResource[files.length];
		int index = 0;
		for (File file : files) {
			if (logger.isDebugEnabled()) {
				logger.debug("Reading file " + file.getName());
			}
			resources[index++] = new FileSystemResource(file);
			context.getEnvironment().getPropertySources().addLast((new ResourcePropertySource(resources[index - 1])));
			PropertiesFactoryBean systemPropertiesFactoryBean = new PropertiesFactoryBean();
			systemPropertiesFactoryBean.setLocation(new FileSystemResource(file));
			beanFactory.initializeBean(systemPropertiesFactoryBean, file.getName().substring(0, file.getName().indexOf(".")));
			if (logger.isDebugEnabled()) {
				logger.debug("Processing file " + file.getName() + " adding as singleton bean with id " + file.getName().substring(0, file.getName().indexOf(".")));
			}
			BeanDefinition bdb = new GenericBeanDefinition();
			bdb.setBeanClassName(systemPropertiesFactoryBean.getClass().getName());
			bdb.setScope(BeanDefinition.SCOPE_SINGLETON);
			defaultListableBeanFactory.registerSingleton(file.getName().substring(0, file.getName().indexOf(".")), systemPropertiesFactoryBean);
			if (logger.isDebugEnabled()) {
				logger.debug("Registered file as bean sucessfully with bean id " + (file.getName().substring(0, file.getName().indexOf("."))));
			}
		}
		propertySourcesPlaceholderConfigurer.setLocations(resources);
		env = context.getEnvironment();	
		return propertySourcesPlaceholderConfigurer;
	}

	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating the datasource object");
		}
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(env.getProperty(DB_DRIVER));
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + DB_DRIVER + " and Driver = " + env.getProperty(DB_DRIVER));
		}
		driverManagerDataSource.setUrl(env.getProperty(DB_URL));
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + DB_URL + " and Database URL  = " + env.getProperty(DB_URL));
		}
		driverManagerDataSource.setUsername(env.getProperty(DB_USERNAME));
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + DB_USERNAME + " and Database UserName  = " + env.getProperty(DB_USERNAME));
		}
		driverManagerDataSource.setPassword(env.getProperty(DB_PASSWORD));
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + DB_PASSWORD + " and Database Password  = " + env.getProperty(DB_PASSWORD));
		}
		return driverManagerDataSource;
	}

	 @Bean
     public SessionFactory sessionFactory() {
             LocalSessionFactoryBuilder builder = 
			new LocalSessionFactoryBuilder(dataSource());
             builder.scanPackages("com.Ntranga.core.CLMS.entities")
                   .addProperties(getHibernateProperties());
             return builder.buildSessionFactory();
     }

	private Properties getHibernateProperties() {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating the Hibernate Properties ");
		}
		Properties prop = new Properties();
		prop.put(HIB_DIALECT,env.getProperty(HIB_DIALECT));
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + HIB_DIALECT + " and Driver = " + env.getProperty(HIB_DIALECT));
		} 
		prop.put(HIB_FORMAT_SQL,env.getProperty(HIB_FORMAT_SQL));
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + HIB_FORMAT_SQL + " and Driver = " + env.getProperty(HIB_FORMAT_SQL));
		}
		prop.put(HIB_SHOW_SQL,env.getProperty(HIB_SHOW_SQL));
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + HIB_SHOW_SQL + " and Driver = " + env.getProperty(HIB_SHOW_SQL));
		}
		prop.put(HIB_HBM2DDL_AUTO,env.getProperty(HIB_HBM2DDL_AUTO));
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + HIB_HBM2DDL_AUTO + " and Driver = " + env.getProperty(HIB_HBM2DDL_AUTO));
		}
		
        return prop;
     }
	
	
	//Create a transaction manager
	@Bean(name = "transactionManager")
     public HibernateTransactionManager txManager() {
             return new HibernateTransactionManager(sessionFactory());
     }

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		if (logger.isDebugEnabled()) {
			logger.debug("Code will be used as default message for missing properties  ");
		}
		messageSource.setUseCodeAsDefaultMessage(true);
		String resourceBundlesPath = env.getProperty("RESOURCEBUNDLES");
		if (logger.isDebugEnabled()) {
			logger.debug("Reading the property RESOURCEBUNDLES for resource bundles. The relative path is  " + resourceBundlesPath);
		}
		String[] baseNames = resourceBundlesPath.split(",");
		if (logger.isDebugEnabled()) {
			logger.debug("Total found base names  " + baseNames != null ? baseNames.length : 0);
		}
		if (logger.isDebugEnabled()) {
			for (String baseName : baseNames) {
				logger.debug("Reading the I18N resource bundle with base name = " + baseName);
			}
		}
		messageSource.setBasenames(baseNames);
		return messageSource;
	}

	

	@Bean(name = "properties")
	public PropertiesFactoryBean getPropertiesFactoryBean() {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating jsp properties object");
		}
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		String realPath = servletContext.getRealPath(JSP_PROPERTY_FILE_PATH);
		if (logger.isDebugEnabled()) {
			logger.debug("Reading the property " + JSP_PROPERTY_FILE_PATH + " and relative path is with real path " + realPath);
		}
		propertiesFactoryBean.setLocation(new FileSystemResource(realPath));
		return propertiesFactoryBean;
	}
	
	
	@Bean(name = "javaMailSender")
	public JavaMailSenderImpl setJavaMailSenderImpl() {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating the java mail sender object for email sending. ");
		}
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + EMAIL_HOST + " and Host = " + env.getProperty(EMAIL_HOST));
		}
		javaMailSenderImpl.setHost(env.getProperty(EMAIL_HOST));
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + EMAIL_PORT + " and Port = " + env.getProperty(EMAIL_PORT));
		}
		javaMailSenderImpl.setPort(Integer.parseInt(env.getProperty(EMAIL_PORT)));
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + EMAIL_USERNAME + " and User Name = " + env.getProperty(EMAIL_USERNAME));
		}
		javaMailSenderImpl.setUsername(env.getProperty(EMAIL_USERNAME));
		/*if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + EMAIL_PASSWORD + " and Password = " + env.getProperty(EMAIL_PASSWORD));
		}
		javaMailSenderImpl.setPassword(env.getProperty(EMAIL_PASSWORD));*/
		if (logger.isDebugEnabled()) {
			logger.debug("Reading property " + EMAIL_PROTOCOL + " and Protocol = " + env.getProperty(EMAIL_PROTOCOL));
		}
		javaMailSenderImpl.setProtocol(env.getProperty(EMAIL_PROTOCOL));

		/*Properties javaMailProperties = new Properties();
		if (logger.isDebugEnabled()) {
			logger.debug("setting property mail.smtps.auth to " + (StringHelper.isEmpty(env.getProperty(EMAIL_SMTP_AUTH)) ? "false" : env.getProperty(EMAIL_SMTP_AUTH)));
		}
		javaMailProperties.setProperty("mail.smtp.auth", StringHelper.isEmpty(env.getProperty(EMAIL_SMTP_AUTH)) ? "false" : env.getProperty(EMAIL_SMTP_AUTH));
		if (logger.isDebugEnabled()) {
			logger.debug("setting property mail.smtp.starttls.enable to " + (StringHelper.isEmpty(env.getProperty(EMAIL_SMTP_STARTTLS)) ? "false" : env.getProperty(EMAIL_SMTP_STARTTLS)));
		}
		javaMailProperties.setProperty("mail.smtp.starttls.enable", StringHelper.isEmpty(env.getProperty(EMAIL_SMTP_STARTTLS)) ? "false" : env.getProperty(EMAIL_SMTP_STARTTLS));
		if (logger.isDebugEnabled()) {
			logger.debug("setting property mail.smtp.debug to " + (StringHelper.isEmpty(env.getProperty(EMAIL_SMTP_DEBUG)) ? "false" : env.getProperty(EMAIL_SMTP_DEBUG)));
		}
		javaMailProperties.setProperty("mail.smtp.debug", StringHelper.isEmpty(env.getProperty(EMAIL_SMTP_DEBUG)) ? "false" : env.getProperty(EMAIL_SMTP_DEBUG));
		javaMailSenderImpl.setJavaMailProperties(javaMailProperties);*/
		return javaMailSenderImpl;
	}
	
}